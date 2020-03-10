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
package nom.bdezonia.zorbage.type.data.float16.real;

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
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
import nom.bdezonia.zorbage.algorithm.TensorShape;
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
public class Float16TensorProduct
	implements
		TensorProduct<Float16TensorProduct,Float16TensorProductMember,Float16Algebra,Float16Member>,
		ConstructibleNdLong<Float16TensorProductMember>,
		Norm<Float16TensorProductMember,Float16Member>,
		Scale<Float16TensorProductMember,Float16Member>,
		Rounding<Float16Member,Float16TensorProductMember>,
		Infinite<Float16TensorProductMember>,
		NaN<Float16TensorProductMember>,
		ScaleByHighPrec<Float16TensorProductMember>,
		ScaleByRational<Float16TensorProductMember>,
		ScaleByDouble<Float16TensorProductMember>,
		Tolerance<Float16Member, Float16TensorProductMember>
{
	@Override
	public Float16TensorProductMember construct() {
		return new Float16TensorProductMember();
	}

	@Override
	public Float16TensorProductMember construct(Float16TensorProductMember other) {
		return new Float16TensorProductMember(other);
	}

	@Override
	public Float16TensorProductMember construct(String s) {
		return new Float16TensorProductMember(s);
	}

	@Override
	public Float16TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new Float16TensorProductMember(s, nd);
	}

	private final Function2<Boolean,Float16TensorProductMember,Float16TensorProductMember> EQ =
			new Function2<Boolean,Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16TensorProductMember a, Float16TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float16TensorProductMember,Float16TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16TensorProductMember,Float16TensorProductMember> NEQ =
			new Function2<Boolean,Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16TensorProductMember a, Float16TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float16TensorProductMember,Float16TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float16TensorProductMember,Float16TensorProductMember> ASSIGN =
			new Procedure2<Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember from, Float16TensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.HLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16TensorProductMember,Float16TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float16TensorProductMember> ZER =
			new Procedure1<Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float16TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16TensorProductMember,Float16TensorProductMember> NEG =
			new Procedure2<Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.HLF, G.HLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16TensorProductMember,Float16TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> ADDEL =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> SUBEL =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float16TensorProductMember,Float16Member> NORM =
			new Procedure2<Float16TensorProductMember, Float16Member>() 
	{
		@Override
		public void call(Float16TensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.HLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float16TensorProductMember,Float16Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float16TensorProductMember,Float16TensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> SCALE =
			new Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.HLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> ADDSCALAR =
			new Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.HLF, scalar, G.HLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> SUBSCALAR =
			new Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16TensorProductMember a, Float16TensorProductMember b) {
			Float16Member tmp = G.HLF.construct();
			G.HLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16TensorProductMember,Float16TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> MULEL =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> DIVIDEEL =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF,G.HLF.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> MUL =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16TensorProductMember,Float16TensorProductMember,Float16TensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float16TensorProductMember,Float16TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float16TensorProductMember,Float16TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorContract.compute(G.HLF, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float16TensorProductMember,Float16TensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,Float16TensorProductMember,Float16TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,Float16TensorProductMember,Float16TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, Float16TensorProductMember a, Float16TensorProductMember b) {
			Float16Member val = G.HLF.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,Float16TensorProductMember,Float16TensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,Float16TensorProductMember,Float16TensorProductMember> POWER =
			new Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Integer power, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorPower.compute(G.HLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float16TensorProductMember,Float16TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float16TensorProductMember> UNITY =
			new Procedure1<Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember result) {
			Float16Member one = G.HLF.construct();
			G.HLF.unity().call(one);
			zero().call(result);
			IntegerIndex index = new IntegerIndex(result.rank());
			for (long d = 0; d < result.dimension(0); d++) {
				for (int r = 0; r < result.rank(); r++) {
					index.set(r, d);
				}
				result.setV(index, one);
			}
		}
	};
	
	@Override
	public Procedure1<Float16TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float16TensorProductMember> ISNAN =
			new Function1<Boolean, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16TensorProductMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float16TensorProductMember> NAN =
			new Procedure1<Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a) {
			FillNaN.compute(G.HLF, a);
		}
	};
	
	@Override
	public Procedure1<Float16TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float16TensorProductMember> ISINF =
			new Function1<Boolean, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16TensorProductMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16TensorProductMember> INF =
			new Procedure1<Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a) {
			FillInfinite.compute(G.HLF, a);
		}
	};
			
	@Override
	public Procedure1<Float16TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, Float16TensorProductMember, Float16TensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			Float16Member tmp = G.HLF.construct();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, tmp);
				G.HLF.round().call(mode, delta, tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, Float16TensorProductMember, Float16TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float16TensorProductMember> ISZERO =
			new Function1<Boolean, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16TensorProductMember a) {
			return SequenceIsZero.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float16TensorProductMember, Float16TensorProductMember> SBR =
			new Procedure3<RationalMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float16TensorProductMember, Float16TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float16TensorProductMember, Float16TensorProductMember> SBD =
			new Procedure3<Double, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Double factor, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float16TensorProductMember, Float16TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float16TensorProductMember, Float16TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float16TensorProductMember a, Float16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float16TensorProductMember, Float16TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, Float16TensorProductMember, Float16TensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, Float16TensorProductMember a, Float16TensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.HLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16TensorProductMember, Float16TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember> MULBYSCALAR =
			new Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16TensorProductMember a, Float16TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember> DIVBYSCALAR =
			new Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16TensorProductMember a, Float16TensorProductMember b) {
			Float16Member invFactor = G.HLF.construct();
			G.HLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16TensorProductMember, Float16TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember> RAISE =
		new Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16TensorProductMember a, Float16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember> LOWER =
		new Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16TensorProductMember a, Float16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float16TensorProductMember, Float16TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float16TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember> OUTER =
			new Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember>()
	{
		@Override
		public void call(Float16TensorProductMember a, Float16TensorProductMember b, Float16TensorProductMember c) {

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
			Float16Member aTmp = G.HLF.construct();
			Float16Member bTmp = G.HLF.construct();
			Float16Member cTmp = G.HLF.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.HLF.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<Float16TensorProductMember, Float16TensorProductMember, Float16TensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
