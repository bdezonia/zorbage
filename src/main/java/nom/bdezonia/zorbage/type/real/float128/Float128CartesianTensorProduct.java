/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.real.float128;

import java.lang.Integer;
import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
import nom.bdezonia.zorbage.algorithm.TensorShape;
import nom.bdezonia.zorbage.algorithm.TensorUnity;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float128CartesianTensorProduct
	implements
		TensorProduct<Float128CartesianTensorProduct,Float128CartesianTensorProductMember,Float128Algebra,Float128Member>,
		Norm<Float128CartesianTensorProductMember,Float128Member>,
		Scale<Float128CartesianTensorProductMember,Float128Member>,
		Rounding<Float128Member,Float128CartesianTensorProductMember>,
		Infinite<Float128CartesianTensorProductMember>,
		NaN<Float128CartesianTensorProductMember>,
		ScaleByHighPrec<Float128CartesianTensorProductMember>,
		ScaleByRational<Float128CartesianTensorProductMember>,
		ScaleByDouble<Float128CartesianTensorProductMember>,
		ScaleByOneHalf<Float128CartesianTensorProductMember>,
		ScaleByTwo<Float128CartesianTensorProductMember>,
		Tolerance<Float128Member, Float128CartesianTensorProductMember>,
		ArrayLikeMethods<Float128CartesianTensorProductMember, Float128Member>
{
	@Override
	public String typeDescription() {
		return "128-bit based real tensor";
	}

	@Override
	public Float128CartesianTensorProductMember construct() {
		return new Float128CartesianTensorProductMember();
	}

	@Override
	public Float128CartesianTensorProductMember construct(Float128CartesianTensorProductMember other) {
		return new Float128CartesianTensorProductMember(other);
	}

	@Override
	public Float128CartesianTensorProductMember construct(String s) {
		return new Float128CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> EQ =
			new Function2<Boolean,Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> NEQ =
			new Function2<Boolean,Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> ASSIGN =
			new Procedure2<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember from, Float128CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float128CartesianTensorProductMember> ZER =
			new Procedure1<Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float128CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> NEG =
			new Procedure2<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QUAD, G.QUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> ADDEL =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> SUBEL =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float128CartesianTensorProductMember,Float128Member> NORM =
			new Procedure2<Float128CartesianTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.QUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float128CartesianTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> SCALE =
			new Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QUAD, G.QUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			Float128Member tmp = G.QUAD.construct();
			G.QUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> MULEL =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> MUL =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128CartesianTensorProductMember,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorContract.compute(G.QUAD, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QUAD_TEN, G.QUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.QUAD_TEN, G.QUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> POWER =
			new Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorPower.compute(G.QUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float128CartesianTensorProductMember,Float128CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float128CartesianTensorProductMember> UNITY =
			new Procedure1<Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember result) {
			TensorUnity.compute(G.QUAD_TEN, G.QUAD, result);
		}
	};
	
	@Override
	public Procedure1<Float128CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float128CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float128CartesianTensorProductMember> NAN =
			new Procedure1<Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a) {
			FillNaN.compute(G.QUAD, a);
		}
	};
	
	@Override
	public Procedure1<Float128CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float128CartesianTensorProductMember> ISINF =
			new Function1<Boolean, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float128CartesianTensorProductMember> INF =
			new Procedure1<Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a) {
			FillInfinite.compute(G.QUAD, a);
		}
	};
			
	@Override
	public Procedure1<Float128CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorRound.compute(G.QUAD_TEN, G.QUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float128CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> SBD =
			new Procedure3<Double, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128Member factor, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128Member factor, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			Float128Member invFactor = G.QUAD.construct();
			G.QUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float128CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> OUTER =
			new Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b, Float128CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.QUAD_TEN, G.QUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float128CartesianTensorProductMember, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QUAD_TEN, G.QUAD, new Float128Member(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float128CartesianTensorProductMember a, Float128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QUAD_TEN, G.QUAD, new Float128Member(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128CartesianTensorProductMember, Float128CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float128CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, Float128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.QUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, Float128CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
