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

import zorbage.type.algebra.Field;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Field implements Field<ComplexFloat64Field, ComplexFloat64Member> {

	protected static ComplexFloat64Member ONE = new ComplexFloat64Member(1,0);
	protected static ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	
	@Override
	public void multiply(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.rv = a.rv * b.rv - a.iv * b.iv;
		c.iv = a.iv * b.rv + a.rv * b.iv;
	}

	@Override
	public void power(int power, ComplexFloat64Member a, ComplexFloat64Member b) {
		// okay for power to be negative
		assign(ONE,b);
		if (power > 0) {
			for (int i = 1; i <= power; i++)
				multiply(b,a,b);
		}
		else if (power < 0) {
			power = -power;
			for (int i = 1; i <= power; i++)
				divide(b,a,b);
		}
	}

	@Override
	public void zero(ComplexFloat64Member z) {
		assign(ZERO,z);
	}

	@Override
	public void negate(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.rv = -a.rv;
		b.iv = -a.iv;
	}

	@Override
	public void add(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.rv = a.rv + b.rv;
		c.iv = a.iv + b.iv;
	}

	@Override
	public void subtract(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.rv = a.rv - b.rv;
		c.iv = a.iv - b.iv;
	}

	@Override
	public boolean isEqual(ComplexFloat64Member a, ComplexFloat64Member b) {
		return a.rv == b.rv && a.iv == b.iv;
	}

	@Override
	public boolean isNotEqual(ComplexFloat64Member a, ComplexFloat64Member b) {
		return a.rv != b.rv || a.iv != b.iv;
	}

	@Override
	public ComplexFloat64Member construct() {
		return new ComplexFloat64Member();
	}

	@Override
	public ComplexFloat64Member construct(ComplexFloat64Member other) {
		return new ComplexFloat64Member(other);
	}

	@Override
	public ComplexFloat64Member construct(String s) {
		return new ComplexFloat64Member(s);
	}

	@Override
	public void assign(ComplexFloat64Member from, ComplexFloat64Member to) {
		to.rv = from.rv;
		to.iv = from.iv;
	}

	@Override
	public void unity(ComplexFloat64Member a) {
		assign(ONE,a);
	}

	@Override
	public void invert(ComplexFloat64Member a, ComplexFloat64Member b) {
		divide(ONE,a,b);
	}

	@Override
	public void divide(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		double mod2 = b.rv * b.rv + b.iv * b.iv;
		c.rv = (a.rv * b.rv + a.iv * b.iv) / mod2;
		c.iv = (a.iv * b.rv - a.rv * b.iv) / mod2;
	}
	
	// TODO - locate in algebra hierarchy
	//@Override
	public void conjugate(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.rv = a.rv;
		b.iv = -a.iv;
	}

	// TODO - locate in algebra hierarchy : note that integers have int return type while complex and quat return reals
	// what do matrices and vectors return. matrix returns a real. vector returns a real.
	//@Override
	public void norm(ComplexFloat64Member a, Float64Member b) {  // this declaration would break generics
		b.v = Math.sqrt(a.rv*a.rv + a.iv*a.iv);
	}
}
