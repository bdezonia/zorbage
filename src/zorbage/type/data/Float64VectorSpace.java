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

import zorbage.type.algebra.VectorSpace;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64VectorSpace
  implements
    VectorSpace<Float64VectorSpace,Float64VectorMember,Float64OrderedField,Float64Member>
{

	@Override
	public void zero(Float64VectorMember a) {
		for (int i = 0; i < a.length(); i++)
			a.setV(i, 0);
	}

	@Override
	public void negate(Float64VectorMember a, Float64VectorMember b) {
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++)
			b.setV(i, -a.v(i));
	}

	@Override
	public void add(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++)
			c.setV(i, a.v(i) + b.v(i));
		for (int i = max; i < c.length(); i++)
			c.setV(i, 0);
	}

	@Override
	public void subtract(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++)
			c.setV(i, a.v(i) - b.v(i));
		for (int i = max; i < c.length(); i++)
			c.setV(i, 0);
	}

	@Override
	public boolean isEqual(Float64VectorMember a, Float64VectorMember b) {
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++)
			if (a.v(i) != b.v(i))
				return false;
		return true;
	}

	@Override
	public boolean isNotEqual(Float64VectorMember a, Float64VectorMember b) {
		return !isEqual(a,b);
	}

	@Override
	public Float64VectorMember construct() {
		return new Float64VectorMember();
	}

	@Override
	public Float64VectorMember construct(Float64VectorMember other) {
		return new Float64VectorMember(other);
	}

	@Override
	public Float64VectorMember construct(String s) {
		return new Float64VectorMember(s);
	}

	@Override
	public void assign(Float64VectorMember from, Float64VectorMember to) {
		for (int i = 0; i < from.length(); i++) {
			to.setV(i, from.v(i));
		}
		for (int i = from.length(); i < to.length(); i++) {
			to.setV(i, 0);
		}
	}

	@Override
	public void norm(Float64VectorMember a, Float64Member b) {
		double norm2 = 0;
		for (int i = 0; i < a.length(); i++) {
			double tmp = a.v(i);
			norm2 += tmp * tmp;
		}
		double norm = Math.sqrt(norm2);
		b.setV(norm);
	}

	@Override
	public void scale(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
		// two loops minimizes memory allocations
		int min = Math.min(a.length(), b.length());
		for (int i = 0; i < min; i++) {
			b.setV(i, scalar.v() * a.v(i));
		}
		int max = Math.max(a.length(), b.length());
		for (int i = min; i < max; i++) {
			b.setV(i, scalar.v() * a.v(i));
		}
	}

	@Override
	public void crossProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		int min = Math.min(a.length(), b.length());
		double sum = 0;
		for (int i = 0; i < min; i++) {
			sum += a.v(i) * b.v(i);
		}
		c.setV(sum);
	}

	@Override
	public void perpDotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vectorTripleProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c,
			Float64VectorMember d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scalarTripleProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c,
			Float64Member d) {
		// TODO Auto-generated method stub
		
	}

}
