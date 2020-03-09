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
package nom.bdezonia.zorbage.type.data.highprec.octonion;

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
import nom.bdezonia.zorbage.type.algebra.TensorLikeProduct;

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
omplex */
public class OctonionHighPrecisionTensorProduct
	implements
		TensorLikeProduct<OctonionHighPrecisionTensorProduct,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionAlgebra,OctonionHighPrecisionMember>,
		ConstructibleNdLong<OctonionHighPrecisionTensorProductMember>,
		Norm<OctonionHighPrecisionTensorProductMember,HighPrecisionMember>,
		Scale<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionMember>,
		ScaleByHighPrec<OctonionHighPrecisionTensorProductMember>,
		ScaleByRational<OctonionHighPrecisionTensorProductMember>,
		ScaleByDouble<OctonionHighPrecisionTensorProductMember>,
		Tolerance<HighPrecisionMember, OctonionHighPrecisionTensorProductMember>
{
	@Override
	public OctonionHighPrecisionTensorProductMember construct() {
		return new OctonionHighPrecisionTensorProductMember();
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(OctonionHighPrecisionTensorProductMember other) {
		return new OctonionHighPrecisionTensorProductMember(other);
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(String s) {
		return new OctonionHighPrecisionTensorProductMember(s);
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new OctonionHighPrecisionTensorProductMember(s, nd);
	}

	private final Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> EQ =
			new Function2<Boolean,OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			if (!shapesMatch(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> NEQ =
			new Function2<Boolean,OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember from, OctonionHighPrecisionTensorProductMember to) {
			shapeResult(from, to);
			Copy.compute(G.OHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionHighPrecisionTensorProductMember> ZER =
			new Procedure1<OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> NEG =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.OHP, G.OHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ADDEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.OHP, G.OHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SUBEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.OHP, G.OHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.OHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> CONJ =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.OHP, G.OHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			FixedTransform2.compute(G.OHP, scalar, G.OHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember tmp = G.OHP.construct();
			G.OHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> MULEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.OHP, G.OHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.OHP, G.OHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> MUL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> multiply() {
		return MUL;
	}
	
	// https://en.wikipedia.org/wiki/Tensor_contraction

	// Okay, I'm not finding predefined algorithms. I think in cartesian tensors each vector is its
	// own dual. So contraction doesn't have to worry about upper and lower indices. Follow the
	// general rules but treat every upper/lower alignment with simple multiplication and addition.
	
	private final Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
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
				OctonionHighPrecisionMember sum = G.OHP.construct();
				OctonionHighPrecisionMember tmp = G.OHP.construct();
				IntegerIndex pos = new IntegerIndex(2);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					pos.set(i, idx);
					pos.set(j, idx);
					a.v(pos, tmp);
					G.OHP.add().call(sum, tmp, sum);
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
			OctonionHighPrecisionMember sum = G.OHP.construct();
			OctonionHighPrecisionMember tmp = G.OHP.construct();
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
				G.OHP.zero().call(sum);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					origPos.set(i, idx);
					origPos.set(j, idx);
					a.v(origPos, tmp);
					G.OHP.add().call(sum, tmp, sum);
				}
				b.setV(contractedPos, sum);
			}
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember val = G.OHP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> POWER =
			new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
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
				OctonionHighPrecisionTensorProductMember tmp1 = construct();
				OctonionHighPrecisionTensorProductMember tmp2 = construct();
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
	public Procedure3<Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionHighPrecisionTensorProductMember> UNITY =
			new Procedure1<OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember result) {
			OctonionHighPrecisionMember one = G.OHP.construct();
			G.OHP.unity().call(one);
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
	public Procedure1<OctonionHighPrecisionTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionHighPrecisionTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember invFactor = G.OHP.construct();
			G.OHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionHighPrecisionTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> OUTER =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {

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
			OctonionHighPrecisionMember aTmp = G.OHP.construct();
			OctonionHighPrecisionMember bTmp = G.OHP.construct();
			OctonionHighPrecisionMember cTmp = G.OHP.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.OHP.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> outerProduct() {
		return OUTER;
	}
	
	private void shapeResult(OctonionHighPrecisionTensorProductMember from, OctonionHighPrecisionTensorProductMember to) {
		if (from == to) return;
		long[] dims = new long[from.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = from.dimension(i);
		}
		to.alloc(dims);
	}

	private boolean shapesMatch(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
		if (a.numDimensions() != b.numDimensions())
			return false;
		for (int i = 0; i < a.numDimensions(); i++) {
			if (a.dimension(i) != b.dimension(i))
				return false;
		}
		return true;
	}

	/* future version
	
	private boolean shapesMatch(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
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
