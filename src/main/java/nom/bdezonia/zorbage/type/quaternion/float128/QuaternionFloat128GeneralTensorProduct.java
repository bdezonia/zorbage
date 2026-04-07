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
package nom.bdezonia.zorbage.type.quaternion.float128;

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
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
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
import nom.bdezonia.zorbage.type.real.float128.Float128Algebra;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat128GeneralTensorProduct
	implements
		TensorProduct<QuaternionFloat128GeneralTensorProduct,QuaternionFloat128GeneralTensorProductMember,Float128Algebra,Float128Member>,
		Norm<QuaternionFloat128GeneralTensorProductMember,Float128Member>,
		Scale<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128Member>,
		Rounding<Float128Member,QuaternionFloat128GeneralTensorProductMember>,
		Infinite<QuaternionFloat128GeneralTensorProductMember>,
		NaN<QuaternionFloat128GeneralTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat128GeneralTensorProductMember>,
		ScaleByRational<QuaternionFloat128GeneralTensorProductMember>,
		ScaleByDouble<QuaternionFloat128GeneralTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat128GeneralTensorProductMember>,
		ScaleByTwo<QuaternionFloat128GeneralTensorProductMember>,
		Tolerance<Float128Member, QuaternionFloat128GeneralTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128Member>,
		MadeOfElements<QuaternionFloat128Algebra,QuaternionFloat128Member>,
		ConstructibleTensor<QuaternionFloat128GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based quaternion tensor";
	}

	@Override
	public QuaternionFloat128GeneralTensorProductMember construct() {
		return new QuaternionFloat128GeneralTensorProductMember();
	}

	@Override
	public QuaternionFloat128GeneralTensorProductMember construct(QuaternionFloat128GeneralTensorProductMember other) {
		return new QuaternionFloat128GeneralTensorProductMember(other);
	}

	@Override
	public QuaternionFloat128GeneralTensorProductMember construct(String s) {
		return new QuaternionFloat128GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QQUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember from, QuaternionFloat128GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QQUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat128GeneralTensorProductMember> ZER =
			new Procedure1<QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> NEG =
			new Procedure2<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QQUAD, G.QQUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat128GeneralTensorProductMember,Float128Member> NORM =
			new Procedure2<QuaternionFloat128GeneralTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.QQUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128GeneralTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			QuaternionFloat128Member tmp = G.QQUAD.construct();
			G.QQUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> MUL =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorContract.compute(G.QQUAD, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorPower.compute(G.QQUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat128GeneralTensorProductMember,QuaternionFloat128GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat128GeneralTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember result) {
			TensorUnity.compute(G.QQUAD_TEN, G.QQUAD, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat128GeneralTensorProductMember> NAN =
			new Procedure1<QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a) {
			FillNaN.compute(G.QQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat128GeneralTensorProductMember> INF =
			new Procedure1<QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a) {
			FillInfinite.compute(G.QQUAD, a.rawData());
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat128GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorRound.compute(G.QQUAD_TEN, G.QQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member factor, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member factor, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			QuaternionFloat128Member invFactor = G.QQUAD.construct();
			G.QQUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, QuaternionFloat128GeneralTensorProductMember metricInverse, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QQUAD, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat128GeneralTensorProductMember metric, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QQUAD, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat128GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b, QuaternionFloat128GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QQUAD_TEN, G.QQUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QQUAD_TEN, G.QQUAD, new QuaternionFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128GeneralTensorProductMember a, QuaternionFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QQUAD_TEN, G.QQUAD, new QuaternionFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128GeneralTensorProductMember, QuaternionFloat128GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionFloat128GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionFloat128Algebra getElementAlgebra() {
		return G.QQUAD;
	}

	@Override
	public QuaternionFloat128GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new QuaternionFloat128GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
