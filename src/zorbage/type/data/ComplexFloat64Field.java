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
import zorbage.type.algebra.Exponential;
import zorbage.type.algebra.Field;
import zorbage.type.algebra.Hyperbolic;
import zorbage.type.algebra.Infinite;
import zorbage.type.algebra.InverseHyperbolic;
import zorbage.type.algebra.InverseTrigonometric;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.Roots;
import zorbage.type.algebra.Rounding;
import zorbage.type.algebra.Trigonometric;

//TODO - finish unimplemented methods

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Field
  implements
    Field<ComplexFloat64Field, ComplexFloat64Member>,
    Norm<ComplexFloat64Member, Float64Member>,
    Constants<ComplexFloat64Member>,
    Exponential<ComplexFloat64Member>,
    Trigonometric<Float64Member,ComplexFloat64Member>,
    InverseTrigonometric<ComplexFloat64Member,Float64Member>,
    Hyperbolic<Float64Member,ComplexFloat64Member>,
    InverseHyperbolic<ComplexFloat64Member,Float64Member>,
    Roots<ComplexFloat64Member>,
    Rounding<ComplexFloat64Member>,
    Infinite<ComplexFloat64Member>
{

	private static final ComplexFloat64Member ONE = new ComplexFloat64Member(1,0);
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);

	@Override
	public void multiply(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.rv = a.rv * b.rv - a.iv * b.iv;
		c.iv = a.iv * b.rv + a.rv * b.iv;
	}

	@Override
	public void power(int power, ComplexFloat64Member a, ComplexFloat64Member b) {
		// okay for power to be negative
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		assign(ONE,tmp);
		if (power > 0) {
			for (int i = 1; i <= power; i++)
				multiply(tmp,a,tmp);
		}
		else if (power < 0) {
			power = -power;
			for (int i = 1; i <= power; i++)
				divide(tmp,a,tmp);
		}
		assign(tmp, b);
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

	@Override
	public void norm(ComplexFloat64Member a, Float64Member b) {  // this declaration would break generics
		b.v = Math.hypot(a.rv,a.iv);
	}

	@Override
	public void PI(ComplexFloat64Member a) {
		a.rv = Math.PI;
		a.iv = 0;
	}

	@Override
	public void E(ComplexFloat64Member a) {
		a.rv = Math.E;
		a.iv = 0;
	}

	@Override
	public void asin(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acos(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void atan(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acsc(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void asec(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acot(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void asinh(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acosh(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void atanh(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acsch(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void asech(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acoth(ComplexFloat64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void atan2(ComplexFloat64Member a, ComplexFloat64Member b, Float64Member c) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void sin(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void cos(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void tan(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void csc(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void sec(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void cot(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void sinh(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void cosh(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void tanh(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void csch(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void sech(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void coth(Float64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void exp(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void expm1(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void log(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void log1p(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void sqrt(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void cbrt(ComplexFloat64Member a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void roundTowardsZero(ComplexFloat64Member a, ComplexFloat64Member b) {
		if (a.rv < 0)
			b.rv = Math.ceil(a.rv);
		else
			b.rv = Math.floor(a.rv);
		if (a.iv < 0)
			b.iv = Math.ceil(a.iv);
		else
			b.iv = Math.floor(a.iv);
	}

	@Override
	public void roundAwayFromZero(ComplexFloat64Member a, ComplexFloat64Member b) {
		if (a.rv > 0)
			b.rv = Math.ceil(a.rv);
		else
			b.rv = Math.floor(a.rv);
		if (a.iv > 0)
			b.iv = Math.ceil(a.iv);
		else
			b.iv = Math.floor(a.iv);
	}

	@Override
	public void roundNegative(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.rv = Math.floor(a.rv);
		b.iv = Math.floor(a.iv);
	}

	@Override
	public void roundPositive(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.rv = Math.ceil(a.rv);
		b.iv = Math.ceil(a.iv);
	}

	@Override
	public void roundNearest(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.rv = Math.rint(a.rv);
		b.iv = Math.rint(a.iv);
	}

	@Override
	public boolean isNaN(ComplexFloat64Member a) {
		return Double.isNaN(a.rv*a.iv); // true if either component is NaN
	}

	@Override
	public boolean isInfinite(ComplexFloat64Member a) {
		return Double.isInfinite(a.rv*a.iv); // true if one or both is Inf and neither is NaN
	}
}
