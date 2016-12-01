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

import zorbage.type.algebra.Conjugate;
import zorbage.type.algebra.Constants;
import zorbage.type.algebra.Infinite;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.Rounding;
import zorbage.type.algebra.SkewField;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64SkewField
  implements
    SkewField<QuaternionFloat64SkewField,QuaternionFloat64Member>,
    Constants<QuaternionFloat64Member>,
    Norm<QuaternionFloat64Member, Float64Member>,
    Conjugate<QuaternionFloat64Member>,
    Infinite<QuaternionFloat64Member>,
    Rounding<QuaternionFloat64Member>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(0,0,0,0);
	private static final QuaternionFloat64Member ONE = new QuaternionFloat64Member(1,0,0,0);
	private static final QuaternionFloat64Member E = new QuaternionFloat64Member(Math.E,0,0,0);
	private static final QuaternionFloat64Member PI = new QuaternionFloat64Member(Math.PI,0,0,0);
	
	@Override
	public void unity(QuaternionFloat64Member a) {
		assign(ONE, a);
	}

	@Override
	public void multiply(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// for safety must use tmps
		double r = a.r()*b.r() - a.i()*b.i() - a.j()*b.j() - a.k()*b.k();
		double i = a.r()*b.i() + a.i()*b.r() + a.j()*b.k() - a.k()*b.j();
		double j = a.r()*b.j() - a.i()*b.k() + a.j()*b.r() + a.k()*b.i();
		double k = a.r()*b.k() + a.i()*b.j() - a.j()*b.i() + a.k()*b.r();
		c.setR( r );
		c.setI( i );
		c.setJ( j );
		c.setK( k );
	}

	@Override
	public void power(int power, QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// okay for power to be negative
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
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
	public void zero(QuaternionFloat64Member a) {
		assign(ZERO, a);
	}

	@Override
	public void negate(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		subtract(ZERO, a, b);
	}

	@Override
	public void add(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		c.setR( a.r() + b.r() );
		c.setI( a.i() + b.i() );
		c.setJ( a.j() + b.j() );
		c.setK( a.k() + b.k() );
	}

	@Override
	public void subtract(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		c.setR( a.r() - b.r() );
		c.setI( a.i() - b.i() );
		c.setJ( a.j() - b.j() );
		c.setK( a.k() - b.k() );
	}

	@Override
	public boolean isEqual(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k();
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
		to.setR( from.r() );
		to.setI( from.i() );
		to.setJ( from.j() );
		to.setK( from.k() );
	}

	@Override
	public void invert(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		QuaternionFloat64Member c = new QuaternionFloat64Member();
		QuaternionFloat64Member scale = new QuaternionFloat64Member();
		Float64Member nval = new Float64Member();
		norm(a, nval);
		scale.setR( (1.0 / (nval.v() * nval.v())) );
		conjugate(a, c);
		multiply(scale, c, b);
	}

	@Override
	public void divide(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		invert(b,tmp);
		multiply(a, tmp, c);
	}

	@Override
	public void conjugate(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		b.setR( a.r() );
		b.setI( -a.i() );
		b.setJ( -a.j() );
		b.setK( -a.k() );
	}

	@Override
	public void norm(QuaternionFloat64Member a, Float64Member b) {
		b.setV( Math.sqrt(a.r()*a.r() + a.i()*a.i() + a.j()*a.j() + a.k()*a.k()) );
	}

	@Override
	public void PI(QuaternionFloat64Member a) {
		assign(PI, a);
	}

	@Override
	public void E(QuaternionFloat64Member a) {
		assign(E, a);
	}

	@Override
	public void roundTowardsZero(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		if (a.r() < 0)
			b.setR( Math.ceil(a.r()) );
		else
			b.setR( Math.floor(a.r()) );
		if (a.i() < 0)
			b.setI( Math.ceil(a.i()) );
		else
			b.setI( Math.floor(a.i()) );
		if (a.j() < 0)
			b.setJ( Math.ceil(a.j()) );
		else
			b.setJ( Math.floor(a.j()) );
		if (a.k() < 0)
			b.setK( Math.ceil(a.k()) );
		else
			b.setK( Math.floor(a.k()) );
	}

	@Override
	public void roundAwayFromZero(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		if (a.r() > 0)
			b.setR( Math.ceil(a.r()) );
		else
			b.setR( Math.floor(a.r()) );
		if (a.i() > 0)
			b.setI( Math.ceil(a.i()) );
		else
			b.setI( Math.floor(a.i()) );
		if (a.j() > 0)
			b.setJ( Math.ceil(a.j()) );
		else
			b.setJ( Math.floor(a.j()) );
		if (a.k() > 0)
			b.setK( Math.ceil(a.k()) );
		else
			b.setK( Math.floor(a.k()) );
	}

	@Override
	public void roundPositive(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		b.setR( Math.ceil(a.r()) );
		b.setI( Math.ceil(a.i()) );
		b.setJ( Math.ceil(a.j()) );
		b.setK( Math.ceil(a.k()) );
	}

	@Override
	public void roundNegative(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		b.setR( Math.floor(a.r()) );
		b.setI( Math.floor(a.i()) );
		b.setJ( Math.floor(a.j()) );
		b.setK( Math.floor(a.k()) );
	}

	@Override
	public void roundNearest(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		b.setR( Math.rint(a.r()) );
		b.setI( Math.rint(a.i()) );
		b.setJ( Math.rint(a.j()) );
		b.setK( Math.rint(a.k()) );
	}

	@Override
	public boolean isNaN(QuaternionFloat64Member a) {
		return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
	}

	@Override
	public boolean isInfinite(QuaternionFloat64Member a) {
		return
			!isNaN(a) &&
			(Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) || Double.isInfinite(a.k()));
	}
}
