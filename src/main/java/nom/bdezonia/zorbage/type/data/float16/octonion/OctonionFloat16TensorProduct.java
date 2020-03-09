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
package nom.bdezonia.zorbage.type.data.float16.octonion;

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
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Rounding;
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
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
omplex */
public class OctonionFloat16TensorProduct
	implements
		TensorLikeProduct<OctonionFloat16TensorProduct,OctonionFloat16TensorProductMember,OctonionFloat16Algebra,OctonionFloat16Member>,
		ConstructibleNdLong<OctonionFloat16TensorProductMember>,
		Norm<OctonionFloat16TensorProductMember,Float16Member>,
		Scale<OctonionFloat16TensorProductMember,OctonionFloat16Member>,
		Rounding<Float16Member,OctonionFloat16TensorProductMember>,
		Infinite<OctonionFloat16TensorProductMember>,
		NaN<OctonionFloat16TensorProductMember>,
		ScaleByHighPrec<OctonionFloat16TensorProductMember>,
		ScaleByRational<OctonionFloat16TensorProductMember>,
		ScaleByDouble<OctonionFloat16TensorProductMember>,
		Tolerance<Float16Member, OctonionFloat16TensorProductMember>
{
	@Override
	public OctonionFloat16TensorProductMember construct() {
		return new OctonionFloat16TensorProductMember();
	}

	@Override
	public OctonionFloat16TensorProductMember construct(OctonionFloat16TensorProductMember other) {
		return new OctonionFloat16TensorProductMember(other);
	}

	@Override
	public OctonionFloat16TensorProductMember construct(String s) {
		return new OctonionFloat16TensorProductMember(s);
	}

	@Override
	public OctonionFloat16TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new OctonionFloat16TensorProductMember(s, nd);
	}

	private final Function2<Boolean,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			if (!shapesMatch(a, b))
				return false;
			return SequencesSimilar.compute(G.OHLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember from, OctonionFloat16TensorProductMember to) {
			shapeResult(from, to);
			Copy.compute(G.OHLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat16TensorProductMember> ZER =
			new Procedure1<OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> NEG =
			new Procedure2<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.OHLF, G.OHLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> ADDEL =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.OHLF, G.OHLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> SUBEL =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.OHLF, G.OHLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat16TensorProductMember,Float16Member> NORM =
			new Procedure2<OctonionFloat16TensorProductMember, Float16Member>() 
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.OHLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16TensorProductMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> CONJ =
			new Procedure2<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			OctonionFloat16Member tmp = G.OHLF.construct();
			long numElems = a.numElems();
			if (numElems == 0)
				numElems = 1;
			for (long i = 0; i < numElems; i++) {
				a.v(i, tmp);
				G.OHLF.conjugate().call(tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};

	@Override
	public Procedure2<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> SCALE =
			new Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OHLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			FixedTransform2.compute(G.OHLF, scalar, G.OHLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			OctonionFloat16Member tmp = G.OHLF.construct();
			G.OHLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> MULEL =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.OHLF, G.OHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> MUL =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> multiply() {
		return MUL;
	}
	
	// https://en.wikipedia.org/wiki/Tensor_contraction

	// Okay, I'm not finding predefined algorithms. I think in cartesian tensors each vector is its
	// own dual. So contraction doesn't have to worry about upper and lower indices. Follow the
	// general rules but treat every upper/lower alignment with simple multiplication and addition.
	
	private final Procedure4<Integer,Integer,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
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
				OctonionFloat16Member sum = G.OHLF.construct();
				OctonionFloat16Member tmp = G.OHLF.construct();
				IntegerIndex pos = new IntegerIndex(2);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					pos.set(i, idx);
					pos.set(j, idx);
					a.v(pos, tmp);
					G.OHLF.add().call(sum, tmp, sum);
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
			OctonionFloat16Member sum = G.OHLF.construct();
			OctonionFloat16Member tmp = G.OHLF.construct();
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
				G.OHLF.zero().call(sum);
				for (int idx = 0; idx < a.dimension(0); idx++) {
					origPos.set(i, idx);
					origPos.set(j, idx);
					a.v(origPos, tmp);
					G.OHLF.add().call(sum, tmp, sum);
				}
				b.setV(contractedPos, sum);
			}
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			OctonionFloat16Member val = G.OHLF.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
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
				OctonionFloat16TensorProductMember tmp1 = construct();
				OctonionFloat16TensorProductMember tmp2 = construct();
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
	public Procedure3<Integer,OctonionFloat16TensorProductMember,OctonionFloat16TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat16TensorProductMember> UNITY =
			new Procedure1<OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember result) {
			OctonionFloat16Member one = G.OHLF.construct();
			G.OHLF.unity().call(one);
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
	public Procedure1<OctonionFloat16TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat16TensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16TensorProductMember a) {
			return SequenceIsNan.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat16TensorProductMember> NAN =
			new Procedure1<OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a) {
			FillNaN.compute(G.OHLF, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat16TensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16TensorProductMember a) {
			return SequenceIsInf.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat16TensorProductMember> INF =
			new Procedure1<OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a) {
			FillInfinite.compute(G.OHLF, a);
		}
	};
			
	@Override
	public Procedure1<OctonionFloat16TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			OctonionFloat16Member tmp = G.OHLF.construct();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, tmp);
				G.OHLF.round().call(mode, delta, tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat16TensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16TensorProductMember a) {
			return SequenceIsZero.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.OHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member factor, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member factor, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			OctonionFloat16Member invFactor = G.OHLF.construct();
			G.OHLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat16TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> OUTER =
			new Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b, OctonionFloat16TensorProductMember c) {

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
			OctonionFloat16Member aTmp = G.OHLF.construct();
			OctonionFloat16Member bTmp = G.OHLF.construct();
			OctonionFloat16Member cTmp = G.OHLF.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.OHLF.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember, OctonionFloat16TensorProductMember> outerProduct() {
		return OUTER;
	}
	
	private void shapeResult(OctonionFloat16TensorProductMember from, OctonionFloat16TensorProductMember to) {
		if (from == to) return;
		long[] dims = new long[from.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = from.dimension(i);
		}
		to.alloc(dims);
	}

	private boolean shapesMatch(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
		if (a.numDimensions() != b.numDimensions())
			return false;
		for (int i = 0; i < a.numDimensions(); i++) {
			if (a.dimension(i) != b.dimension(i))
				return false;
		}
		return true;
	}

	/* future version
	
	private boolean shapesMatch(OctonionFloat16TensorProductMember a, OctonionFloat16TensorProductMember b) {
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