/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64TensorProduct
	implements
		TensorProduct<Float64TensorProduct,Float64TensorProductMember,Float64Group,Float64Member>,
		ConstructibleNdLong<Float64TensorProductMember>
{
	private Float64Group dbl = new Float64Group();
	
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
	public Float64TensorProductMember construct(MemoryConstruction m, StorageConstruction s, long[] nd) {
		return new Float64TensorProductMember(m, s, nd);
	}

	@Override
	public boolean isEqual(Float64TensorProductMember a, Float64TensorProductMember b) {
		if (!shapesMatch(a,b))
			return false;
		Float64Member aTmp = new Float64Member();
		Float64Member bTmp = new Float64Member();
		long numElems = a.numElems();
		for (long i = 0; i < numElems; i++) {
			a.v(i, aTmp);
			b.v(i, bTmp);
			if (dbl.isNotEqual(aTmp, bTmp))
				return false;
		}
		return true;
	}

	@Override
	public boolean isNotEqual(Float64TensorProductMember a, Float64TensorProductMember b) {
		return !isEqual(a,b);
	}

	@Override
	public void assign(Float64TensorProductMember from, Float64TensorProductMember to) {
		Float64Member tmp = new Float64Member();
		long[] dims = new long[from.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = from.dimension(i);
		}
		to.init(dims);
		long numElems = from.numElems();
		for (long i = 0; i < numElems; i++) {
			from.v(i, tmp);
			to.setV(i, tmp);
		}
	}

	@Override
	public void zero(Float64TensorProductMember a) {
		Float64Member tmp = new Float64Member();
		dbl.zero(tmp);
		long numElems = a.numElems();
		for (long i = 0; i < numElems; i++) {
			a.setV(i, tmp);
		}
	}

	@Override
	public void negate(Float64TensorProductMember a, Float64TensorProductMember b) {
		Float64Member tmp = new Float64Member();
		assign(a,b);
		long numElems = b.numElems();
		for (long i = 0; i < numElems; i++) {
			b.v(i, tmp);
			dbl.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c) {
		if (!shapesMatch(a,b))
			throw new IllegalArgumentException("tensor add shape mismatch");
		Float64Member aTmp = new Float64Member();
		Float64Member bTmp = new Float64Member();
		Float64Member cTmp = new Float64Member();
		long numElems = a.numElems();
		for (long i = 0; i < numElems; i++) {
			a.v(i, aTmp);
			b.v(i, bTmp);
			dbl.add(aTmp, bTmp, cTmp);
			c.setV(i, cTmp);
		}
	}

	@Override
	public void subtract(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c) {
		if (!shapesMatch(a,b))
			throw new IllegalArgumentException("tensor subtract shape mismatch");
		Float64Member aTmp = new Float64Member();
		Float64Member bTmp = new Float64Member();
		Float64Member cTmp = new Float64Member();
		long numElems = a.numElems();
		for (long i = 0; i < numElems; i++) {
			a.v(i, aTmp);
			b.v(i, bTmp);
			dbl.subtract(aTmp, bTmp, cTmp);
			c.setV(i, cTmp);
		}
	}

	@Override
	public void norm(Float64TensorProductMember a, Float64Member b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scale(Float64Member scalar, Float64TensorProductMember a, Float64TensorProductMember b) {
		Float64Member tmp = new Float64Member();
		assign(a,b);
		for (long i = 0; i < b.numElems(); i++) {
			b.v(i, tmp);
			dbl.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void crossProduct(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dotProduct(Float64TensorProductMember a, Float64TensorProductMember b, Float64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perpDotProduct(Float64TensorProductMember a, Float64TensorProductMember b, Float64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vectorTripleProduct(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c, Float64TensorProductMember d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scalarTripleProduct(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c, Float64Member d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conjugate(Float64TensorProductMember a, Float64TensorProductMember b) {
		assign(a,b);
		// real so nothing left to do
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
}
