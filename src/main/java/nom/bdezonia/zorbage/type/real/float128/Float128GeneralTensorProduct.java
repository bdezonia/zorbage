/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
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
public class Float128GeneralTensorProduct
	implements
		TensorProduct<Float128GeneralTensorProduct,Float128GeneralTensorProductMember,Float128Algebra,Float128Member>,
		Norm<Float128GeneralTensorProductMember,Float128Member>,
		Scale<Float128GeneralTensorProductMember,Float128Member>,
		Rounding<Float128Member,Float128GeneralTensorProductMember>,
		Infinite<Float128GeneralTensorProductMember>,
		NaN<Float128GeneralTensorProductMember>,
		ScaleByHighPrec<Float128GeneralTensorProductMember>,
		ScaleByRational<Float128GeneralTensorProductMember>,
		ScaleByDouble<Float128GeneralTensorProductMember>,
		ScaleByOneHalf<Float128GeneralTensorProductMember>,
		ScaleByTwo<Float128GeneralTensorProductMember>,
		Tolerance<Float128Member, Float128GeneralTensorProductMember>,
		ArrayLikeMethods<Float128GeneralTensorProductMember, Float128Member>,
		MadeOfElements<Float128Algebra,Float128Member>,
		ConstructibleTensor<Float128GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based real tensor";
	}

	@Override
	public Float128GeneralTensorProductMember construct() {
		return new Float128GeneralTensorProductMember();
	}

	@Override
	public Float128GeneralTensorProductMember construct(Float128GeneralTensorProductMember other) {
		return new Float128GeneralTensorProductMember(other);
	}

	@Override
	public Float128GeneralTensorProductMember construct(String s) {
		return new Float128GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> EQ =
			new Function2<Boolean,Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> NEQ =
			new Function2<Boolean,Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> ASSIGN =
			new Procedure2<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember from, Float128GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float128GeneralTensorProductMember> ZER =
			new Procedure1<Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float128GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> NEG =
			new Procedure2<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QUAD, G.QUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> ADDEL =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> SUBEL =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float128GeneralTensorProductMember,Float128Member> NORM =
			new Procedure2<Float128GeneralTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.QUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float128GeneralTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> SCALE =
			new Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QUAD, G.QUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			Float128Member tmp = G.QUAD.construct();
			G.QUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> MULEL =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QUAD, G.QUAD.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> MUL =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128GeneralTensorProductMember,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorContract.compute(G.QUAD, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> SEMI =
			new Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QUAD_TEN, G.QUAD, index, a, b);
		}
	};
	
	public Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> COMMA =
			new Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorCommaDerivative.compute(G.QUAD_TEN, G.QUAD, index, a, b);
		}
	};
	
	public Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> POWER =
			new Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorPower.compute(G.QUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float128GeneralTensorProductMember,Float128GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float128GeneralTensorProductMember> UNITY =
			new Procedure1<Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember result) {
			TensorUnity.compute(G.QUAD_TEN, G.QUAD, result);
		}
	};
	
	@Override
	public Procedure1<Float128GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float128GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float128GeneralTensorProductMember> NAN =
			new Procedure1<Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a) {
			FillNaN.compute(G.QUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float128GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float128GeneralTensorProductMember> ISINF =
			new Function1<Boolean, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float128GeneralTensorProductMember> INF =
			new Procedure1<Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a) {
			FillInfinite.compute(G.QUAD, a.rawData());
		}
	};
			
	@Override
	public Procedure1<Float128GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorRound.compute(G.QUAD_TEN, G.QUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float128GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> SBD =
			new Procedure3<Double, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128Member factor, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128Member factor, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			Float128Member invFactor = G.QUAD.construct();
			G.QUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, Float128GeneralTensorProductMember metric, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QUAD, metric, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float128GeneralTensorProductMember metric, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QUAD, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float128GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> OUTER =
			new Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b, Float128GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QUAD_TEN, G.QUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float128GeneralTensorProductMember, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QUAD_TEN, G.QUAD, new Float128Member(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float128GeneralTensorProductMember a, Float128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QUAD_TEN, G.QUAD, new Float128Member(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128GeneralTensorProductMember, Float128GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float128GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, Float128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, Float128GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public Float128Algebra getElementAlgebra() {
		return G.QUAD;
	}

	@Override
	public Float128GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new Float128GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
