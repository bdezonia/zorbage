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

import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Constants;
import zorbage.type.algebra.Exponential;
import zorbage.type.algebra.Hyperbolic;
import zorbage.type.algebra.Infinite;
import zorbage.type.algebra.InverseHyperbolic;
import zorbage.type.algebra.InverseTrigonometric;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.OrderedField;
import zorbage.type.algebra.Roots;
import zorbage.type.algebra.Rounding;
import zorbage.type.algebra.Trigonometric;

// TODO - finish unimplemented methods

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64OrderedField
  implements
    OrderedField<Float64OrderedField,Float64Member>,
    Bounded<Float64Member>,
    Norm<Float64Member,Float64Member>,
    Constants<Float64Member>,
    Exponential<Float64Member>,
    Trigonometric<Float64Member,Float64Member>,
    InverseTrigonometric<Float64Member,Float64Member>,
    Hyperbolic<Float64Member,Float64Member>,
    InverseHyperbolic<Float64Member,Float64Member>,
    Infinite<Float64Member>,
    Roots<Float64Member>,
    Rounding<Float64Member>
{

	@Override
	public boolean isEqual(Float64Member a, Float64Member b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(Float64Member a, Float64Member b) {
		return a.v != b.v;
	}

	@Override
	public Float64Member construct() {
		return new Float64Member();
	}

	@Override
	public Float64Member construct(Float64Member other) {
		return new Float64Member(other);
	}

	@Override
	public Float64Member construct(String s) {
		return new Float64Member(s);
	}

	@Override
	public void assign(Float64Member from, Float64Member to) {
		to.v = from.v;
	}

	@Override
	public void add(Float64Member a, Float64Member b, Float64Member c) {
		c.v = a.v + b.v;
	}

	@Override
	public void subtract(Float64Member a, Float64Member b, Float64Member c) {
		c.v = a.v - b.v;
	}

	@Override
	public void zero(Float64Member z) {
		z.v = 0;
	}

	@Override
	public void negate(Float64Member a, Float64Member b) {
		b.v = -a.v;
	}

	@Override
	public void unity(Float64Member a) {
		a.v = 1;
	}

	@Override
	public void invert(Float64Member a, Float64Member b) {
		b.v = 1.0 / a.v;
	}

	@Override
	public void divide(Float64Member a, Float64Member b, Float64Member c) {
		c.v = a.v / b.v;
	}

	@Override
	public boolean isLess(Float64Member a, Float64Member b) {
		return a.v < b.v;
	}

	@Override
	public boolean isLessEqual(Float64Member a, Float64Member b) {
		return a.v <= b.v;
	}

	@Override
	public boolean isGreater(Float64Member a, Float64Member b) {
		return a.v > b.v;
	}

	@Override
	public boolean isGreaterEqual(Float64Member a, Float64Member b) {
		return a.v > b.v;
	}

	@Override
	public int compare(Float64Member a, Float64Member b) {
		if (a.v < b.v) return -1;
		if (a.v > b.v) return  1;
		return 0;
	}

	@Override
	public int signum(Float64Member a) {
		if (a.v < 0) return -1;
		if (a.v > 0) return  1;
		return 0;
	}

	@Override
	public void maxBound(Float64Member a) {
		a.v = Double.MAX_VALUE;
	}

	@Override
	public void minBound(Float64Member a) {
		a.v = Double.MIN_VALUE;
	}

	@Override
	public void multiply(Float64Member a, Float64Member b, Float64Member c) {
		c.v = a.v * b.v;
	}

	@Override
	public void power(int power, Float64Member a, Float64Member b) {
		b.v = Math.pow(a.v, power);
	}

	@Override
	public void abs(Float64Member a, Float64Member b) {
		b.v = Math.abs(a.v);
	}

	@Override
	public void norm(Float64Member a, Float64Member b) {
		b.v = Math.abs(a.v);
	}

	@Override
	public void PI(Float64Member a) {
		a.v = Math.PI;
	}

	@Override
	public void E(Float64Member a) {
		a.v = Math.E;
	}
	
	@Override
	public void exp(Float64Member a, Float64Member b) {
		b.v = Math.exp(a.v);
	}

	@Override
	public void expm1(Float64Member a, Float64Member b) {
		b.v = Math.expm1(a.v);
	}

	@Override
	public void log(Float64Member a, Float64Member b) {
		b.v = Math.log(a.v);
	}

	@Override
	public void log1p(Float64Member a, Float64Member b) {
		b.v = Math.log1p(a.v);
	}

	@Override
	public void cos(Float64Member a, Float64Member b) {
		b.v = Math.cos(a.v);
	}

	@Override
	public void sin(Float64Member a, Float64Member b) {
		b.v = Math.sin(a.v);
	}

	@Override
	public void tan(Float64Member a, Float64Member b) {
		b.v = Math.tan(a.v);
	}

	@Override
	public void csc(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void sec(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void cot(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void cosh(Float64Member a, Float64Member b) {
		b.v = Math.cosh(a.v);
	}

	@Override
	public void sinh(Float64Member a, Float64Member b) {
		b.v = Math.sinh(a.v);
	}

	@Override
	public void tanh(Float64Member a, Float64Member b) {
		b.v = Math.tanh(a.v);
	}

	@Override
	public void csch(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void sech(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void coth(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void acos(Float64Member a, Float64Member b) {
		b.v = Math.acos(a.v);
	}

	@Override
	public void asin(Float64Member a, Float64Member b) {
		b.v = Math.asin(a.v);
	}

	@Override
	public void atan(Float64Member a, Float64Member b) {
		b.v = Math.atan(a.v);
	}
	
	@Override
	public void atan2(Float64Member a, Float64Member b, Float64Member c) {
		c.v = Math.atan2(a.v, b.v);
	}

	@Override
	public void acsc(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void asec(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void acot(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void acosh(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void asinh(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void atanh(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}
	
	@Override
	public void acsch(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void asech(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void acoth(Float64Member a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public boolean isNaN(Float64Member a) {
		return Double.isNaN(a.v);
	}

	@Override
	public boolean isInfinite(Float64Member a) {
		return Double.isInfinite(a.v);
	}

	@Override
	public void sqrt(Float64Member a, Float64Member b) {
		b.v = Math.sqrt(a.v);
	}

	@Override
	public void cbrt(Float64Member a, Float64Member b) {
		b.v = Math.cbrt(a.v);
	}

	@Override
	public void roundTowardsZero(Float64Member a, Float64Member b) {
		if (a.v > 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundAwayFromZero(Float64Member a, Float64Member b) {
		if (a.v < 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundPositive(Float64Member a, Float64Member b) {
		b.v = Math.ceil(a.v);
	}

	@Override
	public void roundNegative(Float64Member a, Float64Member b) {
		b.v = Math.floor(a.v);
	}

	@Override
	public void roundNearest(Float64Member a, Float64Member b) {
		b.v = Math.rint(b.v);
	}

	@Override
	public void min(Float64Member a, Float64Member b, Float64Member c) {
		c.v = Math.min(a.v, b.v);
	}

	@Override
	public void max(Float64Member a, Float64Member b, Float64Member c) {
		c.v = Math.max(a.v, b.v);
	}

}