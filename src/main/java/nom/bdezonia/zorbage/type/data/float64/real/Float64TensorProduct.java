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
package nom.bdezonia.zorbage.type.data.float64.real;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
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
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;

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

import nom.bdezonia.zorbage.type.algebra.TensorProduct;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64TensorProduct
	implements
		TensorProduct<Float64TensorProduct,Float64TensorProductMember,Float64Algebra,Float64Member>,
		ConstructibleNdLong<Float64TensorProductMember>,
		Norm<Float64TensorProductMember,Float64Member>,
		Scale<Float64TensorProductMember,Float64Member>,
		Rounding<Float64Member,Float64TensorProductMember>,
		Infinite<Float64TensorProductMember>,
		NaN<Float64TensorProductMember>,
		ScaleByHighPrec<Float64TensorProductMember>,
		ScaleByRational<Float64TensorProductMember>,
		ScaleByDouble<Float64TensorProductMember>,
		Tolerance<Float64Member, Float64TensorProductMember>
{
	@Override
	public Float64TensorProductMember construct() {
		return new Float64TensorProductMember();
	}

	@Override
	public Float64TensorProductMember construct(Float64TensorProductMember other) {
		return new Float64TensorProductMember(other);
	}

	@Override
	public Float64TensorProductMember construct(String s) {
		return new Float64TensorProductMember(s);
	}

	@Override
	public Float64TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new Float64TensorProductMember(s, nd);
	}

	private final Function2<Boolean,Float64TensorProductMember,Float64TensorProductMember> EQ =
			new Function2<Boolean,Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember a, Float64TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float64TensorProductMember,Float64TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64TensorProductMember,Float64TensorProductMember> NEQ =
			new Function2<Boolean,Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember a, Float64TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64TensorProductMember,Float64TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float64TensorProductMember,Float64TensorProductMember> ASSIGN =
			new Procedure2<Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember from, Float64TensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.DBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64TensorProductMember,Float64TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float64TensorProductMember> ZER =
			new Procedure1<Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64TensorProductMember,Float64TensorProductMember> NEG =
			new Procedure2<Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.DBL, G.DBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64TensorProductMember,Float64TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> ADDEL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> SUBEL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float64TensorProductMember,Float64Member> NORM =
			new Procedure2<Float64TensorProductMember, Float64Member>() 
	{
		@Override
		public void call(Float64TensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.DBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float64TensorProductMember,Float64Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float64TensorProductMember,Float64TensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> SCALE =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.DBL, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> ADDSCALAR =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.DBL, scalar, G.DBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> SUBSCALAR =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64TensorProductMember a, Float64TensorProductMember b) {
			Float64Member tmp = G.DBL.construct();
			G.DBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> MULEL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> DIVIDEEL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL,G.DBL.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> MUL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float64TensorProductMember,Float64TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float64TensorProductMember,Float64TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorContract.compute(G.DBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float64TensorProductMember,Float64TensorProductMember> contract() {
		return CONTRACT;
	}
		
	private final Procedure1<Object> SEMI =
			new Procedure1<Object>()
	{
		@Override
		public void call(Object a) {
			TensorSemicolonDerivative.compute();
		}
	};
	
	@Override
	public Procedure1<Object> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure3<IntegerIndex,Float64TensorProductMember,Float64TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,Float64TensorProductMember,Float64TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, Float64TensorProductMember a, Float64TensorProductMember b) {
			Float64Member val = G.DBL.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,Float64TensorProductMember,Float64TensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,Float64TensorProductMember,Float64TensorProductMember> POWER =
			new Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer power, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorPower.compute(G.DBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float64TensorProductMember,Float64TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float64TensorProductMember> UNITY =
			new Procedure1<Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember result) {
			TensorUnity.compute(G.DBL_TEN, G.DBL, result);
		}
	};
	
	@Override
	public Procedure1<Float64TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float64TensorProductMember> ISNAN =
			new Function1<Boolean, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember a) {
			return SequenceIsNan.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float64TensorProductMember> NAN =
			new Procedure1<Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a) {
			FillNaN.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float64TensorProductMember> ISINF =
			new Function1<Boolean, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember a) {
			return SequenceIsInf.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64TensorProductMember> INF =
			new Procedure1<Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a) {
			FillInfinite.compute(G.DBL, a);
		}
	};
			
	@Override
	public Procedure1<Float64TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, Float64TensorProductMember, Float64TensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorRound.compute(G.DBL_TEN, G.DBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, Float64TensorProductMember, Float64TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float64TensorProductMember> ISZERO =
			new Function1<Boolean, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember a) {
			return SequenceIsZero.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float64TensorProductMember, Float64TensorProductMember> SBR =
			new Procedure3<RationalMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float64TensorProductMember, Float64TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64TensorProductMember, Float64TensorProductMember> SBD =
			new Procedure3<Double, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Double factor, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float64TensorProductMember, Float64TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float64TensorProductMember, Float64TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float64TensorProductMember a, Float64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float64TensorProductMember, Float64TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, Float64TensorProductMember, Float64TensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64TensorProductMember a, Float64TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64TensorProductMember, Float64TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember> MULBYSCALAR =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64TensorProductMember a, Float64TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember> DIVBYSCALAR =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64TensorProductMember a, Float64TensorProductMember b) {
			Float64Member invFactor = G.DBL.construct();
			G.DBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember> RAISE =
		new Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64TensorProductMember a, Float64TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember> LOWER =
		new Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64TensorProductMember a, Float64TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float64TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember> OUTER =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {

			// how an outer product is calculated:
			//   https://www.math3ma.com/blog/the-tensor-product-demystified
			
			if (c == a || c == b)
				throw new IllegalArgumentException("destination tensor cannot be one of the inputs");
			long dimA = a.dimension(0);
			long dimB = b.dimension(0);
			if (dimA != dimB)
				throw new IllegalArgumentException("dimension of tensors must match");
			int rankA = a.numDimensions();
			int rankB = b.numDimensions();
			int rankC = rankA + rankB;
			long[] cDims = new long[rankC];
			for (int i = 0; i < cDims.length; i++) {
				cDims[i] = dimA;
			}
			c.alloc(cDims);
			Float64Member aTmp = G.DBL.construct();
			Float64Member bTmp = G.DBL.construct();
			Float64Member cTmp = G.DBL.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.DBL.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
