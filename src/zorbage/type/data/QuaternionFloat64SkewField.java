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

import zorbage.type.algebra.Constants;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.SkewField;

// TODO: do I need to add conjugate() to alg hierarchy?

// note that this is a biquaternion and might not be what is desired

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64SkewField
  implements
    SkewField<QuaternionFloat64SkewField,QuaternionFloat64Member>,
    Norm<QuaternionFloat64Member, Float64Member>,
    Constants<QuaternionFloat64Member>
{
	private static QuaternionFloat64Member TMP = new QuaternionFloat64Member();
	
	@Override
	public void unity(QuaternionFloat64Member a) {
		a.r = 1;
		a.i = 0;
		a.j = 0;
		a.k = 0;
	}

	@Override
	public void multiply(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void power(int power, QuaternionFloat64Member a, QuaternionFloat64Member b) {
		synchronized (TMP) {
			// TODO
		}
	}

	@Override
	public void zero(QuaternionFloat64Member z) {
		z.r = 0;
		z.i = 0;
		z.j = 0;
		z.k = 0;
	}

	@Override
	public void negate(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subtract(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEqual(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		return a.r == b.r && a.i == b.i && a.j == b.j && a.k == b.k;
	}

	@Override
	public boolean isNotEqual(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		return !isEqual(a,b);
	}

	@Override
	public QuaternionFloat64Member construct() {
		return new QuaternionFloat64Member();
	}

	@Override
	public QuaternionFloat64Member construct(QuaternionFloat64Member other) {
		return new QuaternionFloat64Member(other);
	}

	@Override
	public QuaternionFloat64Member construct(String s) {
		return new QuaternionFloat64Member(s);
	}

	@Override
	public void assign(QuaternionFloat64Member from, QuaternionFloat64Member to) {
		to.r = from.r;
		to.i = from.i;
		to.j = from.j;
		to.k = from.k;
	}

	@Override
	public void invert(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void divide(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// TODO Auto-generated method stub
		
	}

	// TODO - locate in algebra hierarchy
	//@Override
	public void conjugate(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO
		
	}

	@Override
	public void norm(QuaternionFloat64Member a, Float64Member b) {
		// TODO
		
	}

	@Override
	public void PI(QuaternionFloat64Member a) {
		a.r = Math.PI;
		a.i = 0;
		a.j = 0;
		a.k = 0;
	}

	@Override
	public void E(QuaternionFloat64Member a) {
		a.r = Math.E;
		a.i = 0;
		a.j = 0;
		a.k = 0;
	}
}
