/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.data;


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

import zorbage.type.algebra.TensorProduct;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64TensorProduct implements TensorProduct<Float64TensorProduct,Float64TensorProductMember,Float64Group,Float64Member>
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
	public boolean isEqual(Float64TensorProductMember a, Float64TensorProductMember b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqual(Float64TensorProductMember a, Float64TensorProductMember b) {
		return !isEqual(a,b);
	}

	@Override
	public void assign(Float64TensorProductMember from, Float64TensorProductMember to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zero(Float64TensorProductMember a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void negate(Float64TensorProductMember a, Float64TensorProductMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subtract(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multiply(Float64TensorProductMember a, Float64TensorProductMember b,
			Float64TensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void power(int power, Float64TensorProductMember a, Float64TensorProductMember b) {
		// is this wrong? should the class be invertible?
		if (power < 0)
			throw new IllegalArgumentException("negative power not allowed");
		else if (power == 0) {
			assign(a,b);
			unity(b);
		}
		else {
			Float64TensorProductMember tmp = new Float64TensorProductMember();
			assign(a,tmp);
			for (int i = 2; i <= power; i++) {
				multiply(a, tmp, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void unity(Float64TensorProductMember result) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void invert(Float64TensorProductMember a, Float64TensorProductMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void divide(Float64TensorProductMember a, Float64TensorProductMember b, Float64TensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

}
