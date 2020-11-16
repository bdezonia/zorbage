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
package nom.bdezonia.zorbage.type.float64.real;

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

import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64CartesianTensorProduct
	implements
		TensorProduct<Float64CartesianTensorProduct,Float64CartesianTensorProductMember,Float64Algebra,Float64Member>,
		Norm<Float64CartesianTensorProductMember,Float64Member>,
		Scale<Float64CartesianTensorProductMember,Float64Member>,
		Rounding<Float64Member,Float64CartesianTensorProductMember>,
		Infinite<Float64CartesianTensorProductMember>,
		NaN<Float64CartesianTensorProductMember>,
		ScaleByHighPrec<Float64CartesianTensorProductMember>,
		ScaleByRational<Float64CartesianTensorProductMember>,
		ScaleByDouble<Float64CartesianTensorProductMember>,
		ScaleByOneHalf<Float64CartesianTensorProductMember>,
		ScaleByTwo<Float64CartesianTensorProductMember>,
		Tolerance<Float64Member, Float64CartesianTensorProductMember>,
		ArrayLikeMethods<Float64CartesianTensorProductMember, Float64Member>
{
	@Override
	public Float64CartesianTensorProductMember construct() {
		return new Float64CartesianTensorProductMember();
	}

	@Override
	public Float64CartesianTensorProductMember construct(Float64CartesianTensorProductMember other) {
		return new Float64CartesianTensorProductMember(other);
	}

	@Override
	public Float64CartesianTensorProductMember construct(String s) {
		return new Float64CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> EQ =
			new Function2<Boolean,Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> NEQ =
			new Function2<Boolean,Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> ASSIGN =
			new Procedure2<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember from, Float64CartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.DBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float64CartesianTensorProductMember> ZER =
			new Procedure1<Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> NEG =
			new Procedure2<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.DBL, G.DBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> ADDEL =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> SUBEL =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float64CartesianTensorProductMember,Float64Member> NORM =
			new Procedure2<Float64CartesianTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.DBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float64CartesianTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> SCALE =
			new Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.DBL, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.DBL, scalar, G.DBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			Float64Member tmp = G.DBL.construct();
			G.DBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> MULEL =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> MUL =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64CartesianTensorProductMember,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorContract.compute(G.DBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.DBL_TEN, G.DBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.DBL_TEN, G.DBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> POWER =
			new Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorPower.compute(G.DBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float64CartesianTensorProductMember,Float64CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float64CartesianTensorProductMember> UNITY =
			new Procedure1<Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember result) {
			TensorUnity.compute(G.DBL_TEN, G.DBL, result);
		}
	};
	
	@Override
	public Procedure1<Float64CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float64CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float64CartesianTensorProductMember> NAN =
			new Procedure1<Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a) {
			FillNaN.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float64CartesianTensorProductMember> ISINF =
			new Function1<Boolean, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64CartesianTensorProductMember> INF =
			new Procedure1<Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a) {
			FillInfinite.compute(G.DBL, a);
		}
	};
			
	@Override
	public Procedure1<Float64CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorRound.compute(G.DBL_TEN, G.DBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float64CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> SBD =
			new Procedure3<Double, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			Float64Member invFactor = G.DBL.construct();
			G.DBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float64CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> OUTER =
			new Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b, Float64CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.DBL_TEN, G.DBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float64CartesianTensorProductMember, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.DBL_TEN, G.DBL, new Float64Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float64CartesianTensorProductMember a, Float64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.DBL_TEN, G.DBL, new Float64Member(0.5), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64CartesianTensorProductMember, Float64CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}
}
