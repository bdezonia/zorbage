/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat128CartesianTensorProduct
	implements
		TensorLikeProduct<QuaternionFloat128CartesianTensorProduct,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128Algebra,QuaternionFloat128Member>,
		Norm<QuaternionFloat128CartesianTensorProductMember,Float128Member>,
		Scale<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128Member>,
		Rounding<Float128Member,QuaternionFloat128CartesianTensorProductMember>,
		Infinite<QuaternionFloat128CartesianTensorProductMember>,
		NaN<QuaternionFloat128CartesianTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat128CartesianTensorProductMember>,
		ScaleByRational<QuaternionFloat128CartesianTensorProductMember>,
		ScaleByDouble<QuaternionFloat128CartesianTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat128CartesianTensorProductMember>,
		ScaleByTwo<QuaternionFloat128CartesianTensorProductMember>,
		Tolerance<Float128Member, QuaternionFloat128CartesianTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128Member>
{
	@Override
	public QuaternionFloat128CartesianTensorProductMember construct() {
		return new QuaternionFloat128CartesianTensorProductMember();
	}

	@Override
	public QuaternionFloat128CartesianTensorProductMember construct(QuaternionFloat128CartesianTensorProductMember other) {
		return new QuaternionFloat128CartesianTensorProductMember(other);
	}

	@Override
	public QuaternionFloat128CartesianTensorProductMember construct(String s) {
		return new QuaternionFloat128CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QQUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember from, QuaternionFloat128CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QQUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat128CartesianTensorProductMember> ZER =
			new Procedure1<QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> NEG =
			new Procedure2<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QQUAD, G.QQUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat128CartesianTensorProductMember,Float128Member> NORM =
			new Procedure2<QuaternionFloat128CartesianTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.QQUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128CartesianTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> CONJ =
			new Procedure2<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QQUAD, G.QQUAD.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.QQUAD, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.QQUAD, scalar, G.QQUAD.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			QuaternionFloat128Member tmp = G.QQUAD.construct();
			G.QQUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QQUAD, G.QQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> MUL =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorContract.compute(G.QQUAD, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QQUAD_TEN, G.QQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.QQUAD_TEN, G.QQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorPower.compute(G.QQUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat128CartesianTensorProductMember,QuaternionFloat128CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat128CartesianTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember result) {
			TensorUnity.compute(G.QQUAD_TEN, G.QQUAD, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat128CartesianTensorProductMember> NAN =
			new Procedure1<QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a) {
			FillNaN.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat128CartesianTensorProductMember> INF =
			new Procedure1<QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a) {
			FillInfinite.compute(G.QQUAD, a);
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat128CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorRound.compute(G.QQUAD_TEN, G.QQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member factor, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128Member factor, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			QuaternionFloat128Member invFactor = G.QQUAD.construct();
			G.QQUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat128CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b, QuaternionFloat128CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.QQUAD_TEN, G.QQUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QQUAD_TEN, G.QQUAD, new QuaternionFloat128Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128CartesianTensorProductMember a, QuaternionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QQUAD_TEN, G.QQUAD, new QuaternionFloat128Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128CartesianTensorProductMember, QuaternionFloat128CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionFloat128CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
