/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;

//note that many implementations of tensors on the internet treat them as generalized matrices.
//they do not worry about upper and lower indices or even matching shapes. They do element by
//element ops like sin() of each elem.

//do I skip Vector and Matrix and even Scalar?

//TODO: make one tensor/member pair for each of float64, complex64, quat64, oct64
//TODO: determine if this is a field or something else or two things for float/complex vs. quat/oct

// this implies it is an OrderedField. Do tensors have an ordering? abs() exists in TensorFlow.
//@Override
//public void abs(TensorMember a, TensorMember b) {}
// tensorflow also has trigonometric and hyperbolic

//public void contract(int i, int j, TensorMember a, TensorMember b, TensorMember c) {}
//public void takeDiagonal(TensorMember a, Object b) {} // change Object to Vector
//many more

import nom.bdezonia.zorbage.type.algebra.TensorProduct;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64TensorProduct
	implements
		TensorProduct<Float64TensorProduct,Float64TensorProductMember,Float64Group,Float64Member>,
		ConstructibleNdLong<Float64TensorProductMember>,
		Norm<Float64TensorProductMember,Float64Member>,
		Scale<Float64TensorProductMember,Float64Member>,
		Rounding<Float64Member,Float64TensorProductMember>, Infinite<Float64TensorProductMember>,
		NaN<Float64TensorProductMember>
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
			if (!shapesMatch(a,b))
				return false;
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, aTmp);
				b.v(i, bTmp);
				if (G.DBL.isNotEqual().call(aTmp, bTmp))
					return false;
			}
			return true;
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
			return !isEqual().call(a,b);
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
			if (to == from) return;
			Float64Member tmp = new Float64Member();
			long[] dims = new long[from.numDimensions()];
			for (int i = 0; i < dims.length; i++) {
				dims[i] = from.dimension(i);
			}
			to.alloc(dims);
			long numElems = from.numElems();
			for (long i = 0; i < numElems; i++) {
				from.v(i, tmp);
				to.setV(i, tmp);
			}
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
			Float64Member tmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.setV(i, tmp);
			}
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
			Float64Member tmp = new Float64Member();
			assign().call(a,b);
			long numElems = b.numElems();
			for (long i = 0; i < numElems; i++) {
				b.v(i, tmp);
				G.DBL.negate().call(tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};
	
	@Override
	public Procedure2<Float64TensorProductMember,Float64TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> ADD =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!shapesMatch(a,b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			long[] newDims = new long[a.numDimensions()];
			for (int i = 0; i < newDims.length; i++) {
				newDims[i] = a.dimension(i);
			}
			c.alloc(newDims);
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			Float64Member cTmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, aTmp);
				b.v(i, bTmp);
				G.DBL.add().call(aTmp, bTmp, cTmp);
				c.setV(i, cTmp);
			}
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> add() {
		return ADD;
	}

	private final Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> SUB =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!shapesMatch(a,b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			long[] newDims = new long[a.numDimensions()];
			for (int i = 0; i < newDims.length; i++) {
				newDims[i] = a.dimension(i);
			}
			c.alloc(newDims);
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			Float64Member cTmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, aTmp);
				b.v(i, bTmp);
				G.DBL.subtract().call(aTmp, bTmp, cTmp);
				c.setV(i, cTmp);
			}
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> subtract() {
		return SUB;
	}

	private final Procedure2<Float64TensorProductMember,Float64Member> NORM =
			new Procedure2<Float64TensorProductMember, Float64Member>() 
	{
		@Override
		public void call(Float64TensorProductMember a, Float64Member b) {
			// TODO: to port to complex, quat, oct do I need to multiply() not by self but by the
			// conjugate of self. We need to transform a comp, quat, oct into a real.
			Float64Member max = new Float64Member();
			Float64Member value = new Float64Member();
			for (long i = 0; i < a.numElems(); i++) {
				a.v(i, value);
				G.DBL.norm().call(value, value);
				if (G.DBL.isGreater().call(value, max))
					G.DBL.assign().call(value, max);
			}
			Float64Member sum = new Float64Member();
			for (long i = 0; i < a.numElems(); i++) {
				a.v(i, value);
				G.DBL.divide().call(value, max, value);
				G.DBL.multiply().call(value, value, value); // See TODO above
				G.DBL.add().call(sum, value, sum);
			}
			G.DBL.sqrt().call(sum, sum);
			G.DBL.multiply().call(max, sum, sum);
			G.DBL.assign().call(sum, b);
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
			Float64Member tmp = new Float64Member();
			assign().call(a,b);
			for (long i = 0; i < b.numElems(); i++) {
				b.v(i, tmp);
				G.DBL.multiply().call(scalar, tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> ADDEL =
			new Procedure3<Float64Member, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64TensorProductMember a, Float64TensorProductMember b) {
			Float64Member tmp = new Float64Member();
			assign().call(a,b);
			for (long i = 0; i < b.numElems(); i++) {
				b.v(i, tmp);
				G.DBL.add().call(scalar, tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64TensorProductMember,Float64TensorProductMember> addToElements() {
		return ADDEL;
	}

	private Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> MULEL =
			new Procedure3<Float64TensorProductMember, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			long[] newDims = new long[a.numDimensions()];
			for (int i = 0; i < newDims.length; i++) {
				newDims[i] = a.dimension(i);
			}
			c.alloc(newDims);
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			Float64Member cTmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, aTmp);
				b.v(i, bTmp);
				G.DBL.multiply().call(aTmp, bTmp, cTmp);
				c.setV(i, cTmp);
			}
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
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			long[] newDims = new long[a.numDimensions()];
			for (int i = 0; i < newDims.length; i++) {
				newDims[i] = a.dimension(i);
			}
			c.alloc(newDims);
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			Float64Member cTmp = new Float64Member();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, aTmp);
				b.v(i, bTmp);
				G.DBL.divide().call(aTmp, bTmp, cTmp);
				c.setV(i, cTmp);
			}
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
			Float64Member aTmp = new Float64Member();
			Float64Member bTmp = new Float64Member();
			Float64Member cTmp = new Float64Member();
			long k = 0;
			for (long i = 0; i < a.numElems(); i++) {
				a.v(i, aTmp);
				for (long j = 0; j < b.numElems(); j++) {
					b.v(j, bTmp);
					G.DBL.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
	
	@Override
	public Procedure3<Float64TensorProductMember,Float64TensorProductMember,Float64TensorProductMember> multiply() {
		return MUL;
	}
	
	// https://en.wikipedia.org/wiki/Tensor_contraction

	private final Procedure4<java.lang.Integer,java.lang.Integer,Float64TensorProductMember,Float64TensorProductMember> CONTRACT =
			new Procedure4<Integer, Integer, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float64TensorProductMember a, Float64TensorProductMember b) {
			if (a.rank() < 2)
				throw new IllegalArgumentException("input tensor must be rank 2 or greater to contract");
			if (i < 0 || j < 0 || i >= a.rank() || j >= a.rank())
				throw new IllegalArgumentException("bad contraction indices given");
			if (i == j)
				throw new IllegalArgumentException("cannot contract along a single axis");
			if (a == b)
				throw new IllegalArgumentException("destination tensor cannot be one of the inputs");
			int rank = a.rank() - 2;
			long[] newDims = new long[rank];
			for (int k = 0; k < newDims.length; k++) {
				newDims[k] = a.dimension(0);
			}
			b.alloc(newDims);
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure4<java.lang.Integer,java.lang.Integer,Float64TensorProductMember,Float64TensorProductMember> contract() {
		return CONTRACT;
	}
		
	// http://mathworld.wolfram.com/CovariantDerivative.html

	private final Procedure1<java.lang.Integer> SEMI =
			new Procedure1<Integer>()
	{
		@Override
		public void call(Integer a) {
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure1<java.lang.Integer> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure1<java.lang.Integer> COMMA =
			new Procedure1<Integer>()
	{
		@Override
		public void call(Integer a) {
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure1<java.lang.Integer> commaDerivative() {
		return COMMA;
	}
	
	private boolean shapesMatch(Float64TensorProductMember a, Float64TensorProductMember b) {
		int numDims = a.numDimensions();
		if (numDims != b.numDimensions())
			return false;
		for (int i = 0; i < numDims; i++) {
			if (a.dimension(i) != b.dimension(i))
				return false;
		}
		return true;
	}

	private final Procedure3<java.lang.Integer,Float64TensorProductMember,Float64TensorProductMember> POWER =
			new Procedure3<Integer, Float64TensorProductMember, Float64TensorProductMember>()
	{
		@Override
		public void call(Integer power, Float64TensorProductMember a, Float64TensorProductMember b) {
			if (power < 0) {
				// TODO: is this right?
				throw new IllegalArgumentException("negative powers not supported");
			}
			else if (power == 0) {
				if (isZero().call(a)) {
					throw new IllegalArgumentException("0^0 is not a number");
				}
				assign().call(a, b);
				unity().call(b);
			}
			else if (power == 1) {
				assign().call(a, b);
			}
			else {
				// TODO: this method has a lot of memory overhead. Try to fix.
				Float64TensorProductMember tmp1 = new Float64TensorProductMember();
				Float64TensorProductMember tmp2 = new Float64TensorProductMember();
				multiply().call(a,a,tmp1);
				for (int i = 2; i < power; i++) {
					multiply().call(tmp1, a, tmp2);
					assign().call(tmp2, tmp1);
				}
				assign().call(tmp1, b);
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float64TensorProductMember,Float64TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float64TensorProductMember> UNITY =
			new Procedure1<Float64TensorProductMember>()
	{
		@Override
		public void call(Float64TensorProductMember result) {
			Float64Member one = new Float64Member();
			G.DBL.unity().call(one);
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
	public Procedure1<Float64TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float64TensorProductMember> ISNAN =
			new Function1<Boolean, Float64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64TensorProductMember b) {
			Float64Member value = G.DBL.construct();
			for (long i = 0; i < b.numElems(); i++) {
				b.v(i, value);
				if (G.DBL.isNaN().call(value))
					return true;
			}
			return false;
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
			throw new IllegalArgumentException("TODO");
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
			Float64Member value = G.DBL.construct();
			for (long i = 0; i < a.numElems(); i++) {
				a.v(i, value);
				if (G.DBL.isInfinite().call(value))
					return true;
			}
			return false;
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
			throw new IllegalArgumentException("TODO");
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
			if (a != b) {
				long[] newDims = new long[a.numDimensions()];
				for (int i = 0; i < newDims.length; i++) {
					newDims[i] = a.dimension(i);
				}
				b.alloc(newDims);
			}
			Float64Member tmp = G.DBL.construct();
			for (long i = 0; i < a.numElems(); i++) {
				a.v(i, tmp);
				G.DBL.round().call(mode, delta, tmp, tmp);
				b.setV(i, tmp);
			}
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
		public Boolean call(Float64TensorProductMember b) {
			Float64Member value = G.DBL.construct();
			for (long i = 0; i < b.numElems(); i++) {
				b.v(i, value);
				if (!G.DBL.isZero().call(value)) return false;
			}
			return true;
		}
	};

	@Override
	public Function1<Boolean, Float64TensorProductMember> isZero() {
		return ISZERO;
	}

}
