/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.float64.quaternion;

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

//note that many implementations of tensors on the internet treat them as generalized matrices.
//they do not worry about upper and lower indices or even matching shapes. They do element by
//element ops like sin() of each elem. That will not be my approach.

//do I skip Vector and Matrix and even Scalar?

// this implies it is an OrderedField. Do tensors have an ordering? abs() exists in TensorFlow.
//@Override
//public void abs(TensorMember a, TensorMember b) {}
// tensorflow also has trigonometric and hyperbolic

//public void takeDiagonal(TensorMember a, Object b) {} // change Object to Vector
//many more

import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64CartesianTensorProduct
	implements
		TensorLikeProduct<QuaternionFloat64CartesianTensorProduct,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64Algebra,QuaternionFloat64Member>,
		Norm<QuaternionFloat64CartesianTensorProductMember,Float64Member>,
		Scale<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64Member>,
		Rounding<Float64Member,QuaternionFloat64CartesianTensorProductMember>,
		Infinite<QuaternionFloat64CartesianTensorProductMember>,
		NaN<QuaternionFloat64CartesianTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat64CartesianTensorProductMember>,
		ScaleByRational<QuaternionFloat64CartesianTensorProductMember>,
		ScaleByDouble<QuaternionFloat64CartesianTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat64CartesianTensorProductMember>,
		ScaleByTwo<QuaternionFloat64CartesianTensorProductMember>,
		Tolerance<Float64Member, QuaternionFloat64CartesianTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64Member>
{
	@Override
	public QuaternionFloat64CartesianTensorProductMember construct() {
		return new QuaternionFloat64CartesianTensorProductMember();
	}

	@Override
	public QuaternionFloat64CartesianTensorProductMember construct(QuaternionFloat64CartesianTensorProductMember other) {
		return new QuaternionFloat64CartesianTensorProductMember(other);
	}

	@Override
	public QuaternionFloat64CartesianTensorProductMember construct(String s) {
		return new QuaternionFloat64CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QDBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember from, QuaternionFloat64CartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.QDBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat64CartesianTensorProductMember> ZER =
			new Procedure1<QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> NEG =
			new Procedure2<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QDBL, G.QDBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat64CartesianTensorProductMember,Float64Member> NORM =
			new Procedure2<QuaternionFloat64CartesianTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.QDBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64CartesianTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> CONJ =
			new Procedure2<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QDBL, G.QDBL.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.QDBL, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.QDBL, scalar, G.QDBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			QuaternionFloat64Member tmp = G.QDBL.construct();
			G.QDBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> MUL =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorContract.compute(G.QDBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QDBL_TEN, G.QDBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.QDBL_TEN, G.QDBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorPower.compute(G.QDBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat64CartesianTensorProductMember,QuaternionFloat64CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat64CartesianTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember result) {
			TensorUnity.compute(G.QDBL_TEN, G.QDBL, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat64CartesianTensorProductMember> NAN =
			new Procedure1<QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a) {
			FillNaN.compute(G.QDBL, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat64CartesianTensorProductMember> INF =
			new Procedure1<QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a) {
			FillInfinite.compute(G.QDBL, a);
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat64CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorRound.compute(G.QDBL_TEN, G.QDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member factor, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member factor, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			QuaternionFloat64Member invFactor = G.QDBL.construct();
			G.QDBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat64CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b, QuaternionFloat64CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.QDBL_TEN, G.QDBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.QDBL_TEN, G.QDBL, new QuaternionFloat64Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64CartesianTensorProductMember a, QuaternionFloat64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.QDBL_TEN, G.QDBL, new QuaternionFloat64Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64CartesianTensorProductMember, QuaternionFloat64CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}
}
