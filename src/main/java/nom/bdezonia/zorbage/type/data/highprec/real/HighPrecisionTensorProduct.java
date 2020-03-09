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
package nom.bdezonia.zorbage.type.data.highprec.real;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
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
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Norm;
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
public class HighPrecisionTensorProduct
	implements
		TensorProduct<HighPrecisionTensorProduct,HighPrecisionTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		ConstructibleNdLong<HighPrecisionTensorProductMember>,
		Norm<HighPrecisionTensorProductMember,HighPrecisionMember>,
		Scale<HighPrecisionTensorProductMember,HighPrecisionMember>,
		ScaleByHighPrec<HighPrecisionTensorProductMember>,
		ScaleByRational<HighPrecisionTensorProductMember>,
		ScaleByDouble<HighPrecisionTensorProductMember>,
		Tolerance<HighPrecisionMember, HighPrecisionTensorProductMember>
{
	@Override
	public HighPrecisionTensorProductMember construct() {
		return new HighPrecisionTensorProductMember();
	}

	@Override
	public HighPrecisionTensorProductMember construct(HighPrecisionTensorProductMember other) {
		return new HighPrecisionTensorProductMember(other);
	}

	@Override
	public HighPrecisionTensorProductMember construct(String s) {
		return new HighPrecisionTensorProductMember(s);
	}

	@Override
	public HighPrecisionTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new HighPrecisionTensorProductMember(s, nd);
	}

	private final Function2<Boolean,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> EQ =
			new Function2<Boolean,HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			if (!shapesMatch(a, b))
				return false;
			return SequencesSimilar.compute(G.HP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> NEQ =
			new Function2<Boolean,HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> ASSIGN =
			new Procedure2<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember from, HighPrecisionTensorProductMember to) {
			shapeResult(from, to);
			Copy.compute(G.HP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<HighPrecisionTensorProductMember> ZER =
			new Procedure1<HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<HighPrecisionTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> NEG =
			new Procedure2<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.HP, G.HP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> ADDEL =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.HP, G.HP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> SUBEL =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.HP, G.HP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<HighPrecisionTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.HP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<HighPrecisionTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.HP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> ADDSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			FixedTransform2.compute(G.HP, scalar, G.HP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> SUBSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			HighPrecisionMember tmp = G.HP.construct();
			G.HP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> MULEL =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.HP, G.HP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> DIVIDEEL =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.HP,G.HP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> MUL =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionTensorProductMember,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> multiply() {
		return MUL;
	}
	
	// https://en.wikipedia.org/wiki/Tensor_contraction

	// Okay, I'm not finding predefined algorithms. I think in cartesian tensors each vector is its
	// own dual. So contraction doesn't have to worry about upper and lower indices. Follow the
	// general rules but treat every upper/lower alignment with simple multiplication and addition.
	
	private final Procedure4<Integer,Integer,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			if (i == j)
				throw new IllegalArgumentException("cannot contract along a single axis");
			if (i < 0 || j < 0)
				throw new IllegalArgumentException("negative contraction indices given");
			if (i >= a.rank() || j >= a.rank())
				throw new IllegalArgumentException("contraction indices cannot be out of bounds of input tensor's rank");
			if (a == b)
				throw new IllegalArgumentException("src cannot equal dest: contraction is not an in place operation");
			int newRank = a.rank() - 2;
			long[] newDims = new long[newRank];
			for (int k = 0; k < newDims.length; k++) {
				newDims[k] = a.dimension(0);
			}
			b.alloc(newDims);
			if (newRank == 0) {
				HighPrecisionMember sum = G.HP.construct();
				HighPrecisionMember tmp = G.HP.construct();
				IntegerIndex pos = new IntegerIndex(2);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					pos.set(i, idx);
					pos.set(j, idx);
					a.v(pos, tmp);
					G.HP.add().call(sum, tmp, sum);
				}
				b.setV(0, sum);
				return;
			}
			IntegerIndex point1 = new IntegerIndex(newRank);
			IntegerIndex point2 = new IntegerIndex(newRank);
			for (int k = 0; k < newDims.length; k++) {
				point2.set(k, newDims[k] - 1);
			}
			SamplingCartesianIntegerGrid sampling = new SamplingCartesianIntegerGrid(point1, point2);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			IntegerIndex contractedPos = new IntegerIndex(sampling.numDimensions());
			IntegerIndex origPos = new IntegerIndex(a.rank());
			HighPrecisionMember sum = G.HP.construct();
			HighPrecisionMember tmp = G.HP.construct();
			while (iter.hasNext()) {
				iter.next(contractedPos);
				int p = 0;
				for (int r = 0; r < a.rank(); r++) {
					if (r == i)
						origPos.set(i, 0);
					else if (r == j)
						origPos.set(j, 0);
					else
						origPos.set(r, contractedPos.get(p++));
				}
				G.HP.zero().call(sum);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					origPos.set(i, idx);
					origPos.set(j, idx);
					a.v(origPos, tmp);
					G.HP.add().call(sum, tmp, sum);
				}
				b.setV(contractedPos, sum);
			}
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> contract() {
		return CONTRACT;
	}
		
	// http://mathworld.wolfram.com/CovariantDerivative.html

	private final Procedure1<Object> SEMI =
			new Procedure1<Object>()
	{
		@Override
		public void call(Object a) {
			throw new IllegalArgumentException("TODO - implement semicolonDerivative()");
		}
	};
	
	@Override
	public Procedure1<Object> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure3<IntegerIndex,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			HighPrecisionMember val = G.HP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> POWER =
			new Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			if (power < 0) {
				// TODO: is this a valid limitation?
				throw new IllegalArgumentException("negative powers not supported");
			}
			else if (power == 0) {
				if (isZero().call(a)) {
					throw new IllegalArgumentException("0^0 is not a number");
				}
				shapeResult(a, b); // set the shape of result
				unity().call(b); // and make it have value 1
			}
			else if (power == 1) {
				assign().call(a, b);
			}
			else {
				HighPrecisionTensorProductMember tmp1 = construct();
				HighPrecisionTensorProductMember tmp2 = construct();
				multiply().call(a, a, tmp1);
				for (int i = 2; i < (power/2)*2; i += 2) {
					multiply().call(tmp1, a, tmp2);
					multiply().call(tmp2, a, tmp1);
				}
				// an odd power
				if (power > 2 && (power&1)==1) {
					assign().call(tmp1, tmp2);
					multiply().call(tmp2, a, tmp1);
				}
				assign().call(tmp1, b);
			}
		}
	};
	
	@Override
	public Procedure3<Integer,HighPrecisionTensorProductMember,HighPrecisionTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<HighPrecisionTensorProductMember> UNITY =
			new Procedure1<HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember result) {
			HighPrecisionMember one = G.HP.construct();
			G.HP.unity().call(one);
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
	public Procedure1<HighPrecisionTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, HighPrecisionTensorProductMember> ISZERO =
			new Function1<Boolean, HighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionTensorProductMember a) {
			return SequenceIsZero.compute(G.HP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> SBR =
			new Procedure3<RationalMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> SBD =
			new Procedure3<Double, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Double factor, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.HP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> MULBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> DIVBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			HighPrecisionMember invFactor = G.HP.construct();
			G.HP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> RAISE =
		new Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> LOWER =
		new Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> INNER =
		new Procedure5<Integer, Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			HighPrecisionTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> OUTER =
			new Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b, HighPrecisionTensorProductMember c) {

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
			HighPrecisionMember aTmp = G.HP.construct();
			HighPrecisionMember bTmp = G.HP.construct();
			HighPrecisionMember cTmp = G.HP.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.HP.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<HighPrecisionTensorProductMember, HighPrecisionTensorProductMember, HighPrecisionTensorProductMember> outerProduct() {
		return OUTER;
	}
	
	private void shapeResult(HighPrecisionTensorProductMember from, HighPrecisionTensorProductMember to) {
		if (from == to) return;
		long[] dims = new long[from.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = from.dimension(i);
		}
		to.alloc(dims);
	}

	private boolean shapesMatch(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
		if (a.numDimensions() != b.numDimensions())
			return false;
		for (int i = 0; i < a.numDimensions(); i++) {
			if (a.dimension(i) != b.dimension(i))
				return false;
		}
		return true;
	}

	/* future version
	
	private boolean shapesMatch(HighPrecisionTensorProductMember a, HighPrecisionTensorProductMember b) {
		int i = 0;
		int j = 0;
		while (i < a.numDimensions() && j < b.numDimensions()) {
			while (i < a.numDimensions() && a.dimension(i) == 1) i++;
			while (j < b.numDimensions() && b.dimension(i) == 1) j++;
			if (i < a.numDimensions() && j < b.numDimensions() && a.dimension(i) != b.dimension(j))
				return false;
			else {
				i++;
				j++;
			}
		}
		while (i < a.numDimensions() && a.dimension(i) == 1) i++;
		while (j < b.numDimensions() && b.dimension(i) == 1) j++;
		if (i != a.numDimensions() || j != b.numDimensions())
			return false;
		return true;
	}
	
	*/

}