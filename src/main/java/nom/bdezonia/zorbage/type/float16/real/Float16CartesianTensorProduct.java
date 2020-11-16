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
package nom.bdezonia.zorbage.type.float16.real;

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
public class Float16CartesianTensorProduct
	implements
		TensorProduct<Float16CartesianTensorProduct,Float16CartesianTensorProductMember,Float16Algebra,Float16Member>,
		Norm<Float16CartesianTensorProductMember,Float16Member>,
		Scale<Float16CartesianTensorProductMember,Float16Member>,
		Rounding<Float16Member,Float16CartesianTensorProductMember>,
		Infinite<Float16CartesianTensorProductMember>,
		NaN<Float16CartesianTensorProductMember>,
		ScaleByHighPrec<Float16CartesianTensorProductMember>,
		ScaleByRational<Float16CartesianTensorProductMember>,
		ScaleByDouble<Float16CartesianTensorProductMember>,
		ScaleByOneHalf<Float16CartesianTensorProductMember>,
		ScaleByTwo<Float16CartesianTensorProductMember>,
		Tolerance<Float16Member, Float16CartesianTensorProductMember>,
		ArrayLikeMethods<Float16CartesianTensorProductMember, Float16Member>

{
	@Override
	public Float16CartesianTensorProductMember construct() {
		return new Float16CartesianTensorProductMember();
	}

	@Override
	public Float16CartesianTensorProductMember construct(Float16CartesianTensorProductMember other) {
		return new Float16CartesianTensorProductMember(other);
	}

	@Override
	public Float16CartesianTensorProductMember construct(String s) {
		return new Float16CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> EQ =
			new Function2<Boolean,Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> NEQ =
			new Function2<Boolean,Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> ASSIGN =
			new Procedure2<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember from, Float16CartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.HLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float16CartesianTensorProductMember> ZER =
			new Procedure1<Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float16CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> NEG =
			new Procedure2<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.HLF, G.HLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> ADDEL =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> SUBEL =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float16CartesianTensorProductMember,Float16Member> NORM =
			new Procedure2<Float16CartesianTensorProductMember, Float16Member>() 
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.HLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float16CartesianTensorProductMember,Float16Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> SCALE =
			new Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.HLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.HLF, scalar, G.HLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			Float16Member tmp = G.HLF.construct();
			G.HLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> MULEL =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> MUL =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16CartesianTensorProductMember,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorContract.compute(G.HLF, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.HLF_TEN, G.HLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.HLF_TEN, G.HLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> POWER =
			new Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorPower.compute(G.HLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float16CartesianTensorProductMember,Float16CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float16CartesianTensorProductMember> UNITY =
			new Procedure1<Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember result) {
			TensorUnity.compute(G.HLF_TEN, G.HLF, result);
		}
	};
	
	@Override
	public Procedure1<Float16CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float16CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float16CartesianTensorProductMember> NAN =
			new Procedure1<Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a) {
			FillNaN.compute(G.HLF, a);
		}
	};
	
	@Override
	public Procedure1<Float16CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float16CartesianTensorProductMember> ISINF =
			new Function1<Boolean, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16CartesianTensorProductMember> INF =
			new Procedure1<Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a) {
			FillInfinite.compute(G.HLF, a);
		}
	};
			
	@Override
	public Procedure1<Float16CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorRound.compute(G.HLF_TEN, G.HLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float16CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> SBD =
			new Procedure3<Double, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			Float16Member invFactor = G.HLF.construct();
			G.HLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float16CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> OUTER =
			new Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b, Float16CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.HLF_TEN, G.HLF, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float16CartesianTensorProductMember, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			ScaleHelper.compute(G.HLF_TEN, G.HLF, new Float16Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float16CartesianTensorProductMember a, Float16CartesianTensorProductMember b) {
			ScaleHelper.compute(G.HLF_TEN, G.HLF, new Float16Member(0.5f), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16CartesianTensorProductMember, Float16CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}
}
