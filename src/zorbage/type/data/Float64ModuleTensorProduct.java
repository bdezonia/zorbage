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

import zorbage.type.algebra.ModuleTensorProduct;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64ModuleTensorProduct implements ModuleTensorProduct<Float64ModuleTensorProduct,Float64ModuleTensorProductMember,Float64Group,Float64Member>
{

	@Override
	public Float64ModuleTensorProductMember construct() {
		return new Float64ModuleTensorProductMember();
	}

	@Override
	public Float64ModuleTensorProductMember construct(Float64ModuleTensorProductMember other) {
		return new Float64ModuleTensorProductMember(other);
	}

	@Override
	public Float64ModuleTensorProductMember construct(String s) {
		return new Float64ModuleTensorProductMember(s);
	}

	@Override
	public boolean isEqual(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqual(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b) {
		return !isEqual(a,b);
	}

	@Override
	public void assign(Float64ModuleTensorProductMember from, Float64ModuleTensorProductMember to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zero(Float64ModuleTensorProductMember a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void negate(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b,
			Float64ModuleTensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subtract(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b,
			Float64ModuleTensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multiply(Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b,
			Float64ModuleTensorProductMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void power(int power, Float64ModuleTensorProductMember a, Float64ModuleTensorProductMember b) {
		// is this wrong? should the class be invertible?
		if (power < 0)
			throw new IllegalArgumentException("negative power not allowed");
		else if (power == 0) {
			assign(a,b);
			unity(b);
		}
		else {
			Float64ModuleTensorProductMember tmp = new Float64ModuleTensorProductMember();
			assign(a,tmp);
			for (int i = 2; i <= power; i++) {
				multiply(a, tmp, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void unity(Float64ModuleTensorProductMember result) {
		throw new IllegalArgumentException("TODO");
	}

}
