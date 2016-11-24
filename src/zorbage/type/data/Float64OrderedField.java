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
import zorbage.type.algebra.OrderedField;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64OrderedField implements OrderedField<Float64OrderedField,Float64Member>, Bounded<Float64OrderedField,Float64Member> {

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
	public void max(Float64Member a) {
		a.v = Double.MAX_VALUE;
	}

	@Override
	public void min(Float64Member a) {
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

}