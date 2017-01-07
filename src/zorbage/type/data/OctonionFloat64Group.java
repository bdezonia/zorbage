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
package zorbage.type.data;

import zorbage.type.algebra.Conjugate;
import zorbage.type.algebra.Constants;
import zorbage.type.algebra.Exponential;
import zorbage.type.algebra.Hyperbolic;
import zorbage.type.algebra.Infinite;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.Random;
import zorbage.type.algebra.Rounding;
import zorbage.type.algebra.SkewField;
import zorbage.type.algebra.Trigonometric;


/**
 * 
 * @author Barry DeZonia
 *
 */
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat64Group
  implements
    SkewField<OctonionFloat64Group, OctonionFloat64Member>,
    Conjugate<OctonionFloat64Member>,
    Norm<OctonionFloat64Member,Float64Member>,
    Infinite<OctonionFloat64Member>,
    Rounding<OctonionFloat64Member>,
    Constants<OctonionFloat64Member>,
    Random<OctonionFloat64Member>,
    Exponential<OctonionFloat64Member>,
    Trigonometric<OctonionFloat64Member>,
    Hyperbolic<OctonionFloat64Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(0, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member ONE = new OctonionFloat64Member(1, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member TWO = new OctonionFloat64Member(2, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member E = new OctonionFloat64Member(Math.E, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member PI = new OctonionFloat64Member(Math.PI, 0, 0, 0, 0, 0, 0, 0);
	private static final ComplexFloat64Group g = new ComplexFloat64Group();

	public OctonionFloat64Group() {
	}
	
	@Override
	public void unity(OctonionFloat64Member a) {
		assign(ONE, a);
	}

	@Override
	public void multiply(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
		/*
		 
			×	r	i	j	k	l	i0	j0	k0
				==============================
			r	r	i	j	k	l	i0	j0	k0
			i	i	−r	k	−j	i0	−l	−k0	j0
			j	j	−k	−r	i	j0	k0	−l	−i0
			k	k	j	−i	−r	k0	−j0	i0	−l
			l	l	−i0	−j0	−k0	−r	i	j	k
			i0	i0	l	−k0	j0	−i	−r	−k	j
			j0	j0	k0	l	−i0	−j	k	−r	−i
			k0	k0	−j0	i0	l	−k	−j	i	−r

		 */
		OctonionFloat64Member tmp = new OctonionFloat64Member(ZERO);

		// r * r = r
		tmp.setR(a.r() * b.r());
		// r * i = i
		tmp.setI(a.r() * b.i());
		// r * j = j
		tmp.setJ(a.r() * b.j());
		// r * k = k
		tmp.setK(a.r() * b.k());
		// r * l = l
		tmp.setL(a.r() * b.l());
		// r * i0 = i0
		tmp.setI0(a.r() * b.i0());
		// r * j0 = j0
		tmp.setJ0(a.r() * b.j0());
		// r * k0 = k0
		tmp.setK0(a.r() * b.k0());

		// i * r = i
		tmp.setI(tmp.i() + a.i() * b.r());
		// i * i = −r
		tmp.setR(tmp.r() - a.i() * b.i());
		// i * j = k
		tmp.setK(tmp.k() + a.i() * b.j());
		// i * k = −j
		tmp.setJ(tmp.j() - a.i() * b.k());
		// i * l = i0
		tmp.setI0(tmp.i0() + a.i() * b.l());
		// i * i0 = −l
		tmp.setL(tmp.l() - a.i() * b.i0());
		// i * j0 = −k0
		tmp.setK0(tmp.k0() - a.i() * b.j0());
		// i * k0 = j0
		tmp.setJ0(tmp.j0() + a.i() * b.k0());

		// j * r = j
		tmp.setJ(tmp.j() + a.j() * b.r());
		// j * i = −k
		tmp.setK(tmp.k() - a.j() * b.i());
		// j * j = −r
		tmp.setR(tmp.r() - a.j() * b.j());
		// j * k = i
		tmp.setI(tmp.i() + a.j() * b.k());
		// j * l = j0
		tmp.setJ0(tmp.j0() + a.j() * b.l());
		// j * i0 = k0
		tmp.setK0(tmp.k0() + a.j() * b.i0());
		// j * j0 = −l
		tmp.setL(tmp.l() - a.j() * b.j0());
		// j * k0 = -i0
		tmp.setI0(tmp.i0() - a.j() * b.k0());

		// k * r = k
		tmp.setK(tmp.k() + a.k() * b.r());
		// k * i = j
		tmp.setJ(tmp.j() + a.k() * b.i());
		// k * j = −i
		tmp.setI(tmp.i() - a.k() * b.j());
		// k * k = −r
		tmp.setR(tmp.r() - a.k() * b.k());
		// k * l = k0
		tmp.setK0(tmp.k0() + a.k() * b.l());
		// k * i0 = −j0
		tmp.setJ0(tmp.j0() - a.k() * b.i0());
		// k * j0 = i0
		tmp.setI0(tmp.i0() + a.k() * b.j0());
		// k * k0 = −l
		tmp.setL(tmp.l() - a.k() * b.k0());
 
		// l * r = l
		tmp.setL(tmp.l() + a.l() * b.r());
		// l * i = −i0
		tmp.setI0(tmp.i0() - a.l() * b.i());
		// l * j = −j0
		tmp.setJ0(tmp.j0() - a.l() * b.j());
		// l * k = −k0
		tmp.setK0(tmp.k0() - a.l() * b.k());
		// l * l = −r
		tmp.setR(tmp.r() - a.l() * b.l());
		// l * i0 = i
		tmp.setI(tmp.i() + a.l() * b.i0());
		// l * j0 = j
		tmp.setJ(tmp.j() + a.l() * b.j0());
		// l * k0 = k
		tmp.setK(tmp.k() + a.l() * b.k0());

		// i0 * r = i0
		tmp.setI0(tmp.i0() + a.i0() * b.r());
		// i0 * i = l
		tmp.setL(tmp.l() + a.i0() * b.i());
		// i0 * j = −k0
		tmp.setK0(tmp.k0() - a.i0() * b.j());
		// i0 * k = j0
		tmp.setJ0(tmp.j0() + a.i0() * b.k());
		// i0 * l = −i
		tmp.setI(tmp.i() - a.i0() * b.l());
		// i0 * i0 = −r
		tmp.setR(tmp.r() - a.i0() * b.i0());
		// i0 * j0 = −k
		tmp.setK(tmp.k() - a.i0() * b.j0());
		// i0 * k0 = j
		tmp.setJ(tmp.j() + a.i0() * b.k0());
		
		// j0 * r = j0
		tmp.setJ0(tmp.j0() + a.j0() * b.r());
		// j0 * i = k0
		tmp.setK0(tmp.k0() + a.j0() * b.i());
		// j0 * j = l
		tmp.setL(tmp.l() + a.j0() * b.j());
		// j0 * k = −i0
		tmp.setI0(tmp.i0() - a.j0() * b.k());
		// j0 * l = −j
		tmp.setJ(tmp.j() - a.j0() * b.l());
		// j0 * i0 = k
		tmp.setK(tmp.k() + a.j0() * b.i0());
		// j0 * j0 = −r
		tmp.setR(tmp.r() - a.j0() * b.j0());
		// j0 * k0 = −i
		tmp.setI(tmp.i() - a.j0() * b.k0());
		
		// k0 * r = k0
		tmp.setK0(tmp.k0() + a.k0() * b.r());
		// k0 * i = −j0
		tmp.setJ0(tmp.j0() - a.k0() * b.i());
		// k0 * j = i0
		tmp.setI0(tmp.i0() + a.k0() * b.j());
		// k0 * k = l
		tmp.setL(tmp.l() + a.k0() * b.k());
		// k0 * l = −k
		tmp.setK(tmp.k() - a.k0() * b.l());
		// k0 * i0 = −j
		tmp.setJ(tmp.j() - a.k0() * b.i0());
		// k0 * j0 = i
		tmp.setI(tmp.i() + a.k0() * b.j0());
		// k0 * k0 = −r
		tmp.setR(tmp.r() - a.k0() * b.k0());
		
		assign(tmp, c);
	}

	@Override
	public void power(int power, OctonionFloat64Member a, OctonionFloat64Member b) {
		// okay for power to be negative
		OctonionFloat64Member tmp = new OctonionFloat64Member();
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
	public void zero(OctonionFloat64Member a) {
		assign(ZERO, a);
	}

	@Override
	public void negate(OctonionFloat64Member a, OctonionFloat64Member b) {
		subtract(ZERO, a, b);
	}

	@Override
	public void add(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
		c.setR( a.r() + b.r() );
		c.setI( a.i() + b.i() );
		c.setJ( a.j() + b.j() );
		c.setK( a.k() + b.k() );
		c.setL( a.l() + b.l() );
		c.setI0( a.i0() + b.i0() );
		c.setJ0( a.j0() + b.j0() );
		c.setK0( a.k0() + b.k0() );
	}

	@Override
	public void subtract(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
		c.setR( a.r() - b.r() );
		c.setI( a.i() - b.i() );
		c.setJ( a.j() - b.j() );
		c.setK( a.k() - b.k() );
		c.setL( a.l() - b.l() );
		c.setI0( a.i0() - b.i0() );
		c.setJ0( a.j0() - b.j0() );
		c.setK0( a.k0() - b.k0() );
	}

	@Override
	public boolean isEqual(OctonionFloat64Member a, OctonionFloat64Member b) {
		return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k() &&
				a.l() == b.l() && a.i0() == b.i0() && a.j0() == b.j0() && a.k0() == b.k0();
	}

	@Override
	public boolean isNotEqual(OctonionFloat64Member a, OctonionFloat64Member b) {
		return !isEqual(a,b);
	}

	@Override
	public OctonionFloat64Member construct() {
		return new OctonionFloat64Member();
	}

	@Override
	public OctonionFloat64Member construct(OctonionFloat64Member other) {
		return new OctonionFloat64Member(other);
	}

	@Override
	public OctonionFloat64Member construct(String s) {
		return new OctonionFloat64Member(s);
	}

	@Override
	public void assign(OctonionFloat64Member from, OctonionFloat64Member to) {
		to.setR( from.r() );
		to.setI( from.i() );
		to.setJ( from.j() );
		to.setK( from.k() );
		to.setL( from.l() );
		to.setI0( from.i0() );
		to.setJ0( from.j0() );
		to.setK0( from.k0() );
	}

	@Override
	public void invert(OctonionFloat64Member a, OctonionFloat64Member b) {
		double norm2 = norm2(a);
		conjugate(a, b);
		b.setR( b.r() / norm2 );
		b.setI( b.i() / norm2 );
		b.setJ( b.j() / norm2 );
		b.setK( b.k() / norm2 );
		b.setL( b.l() / norm2 );
		b.setI0( b.i0() / norm2 );
		b.setJ0( b.j0() / norm2 );
		b.setK0( b.k0() / norm2 );
	}

	@Override
	public void divide(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		invert(b, tmp);
		multiply(a, tmp, c);
	}

	@Override
	public void roundTowardsZero(OctonionFloat64Member a, OctonionFloat64Member b) {
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
		if (a.l() < 0)
			b.setL( Math.ceil(a.l()) );
		else
			b.setL( Math.floor(a.l()) );
		if (a.i0() < 0)
			b.setI0( Math.ceil(a.i0()) );
		else
			b.setI0( Math.floor(a.i0()) );
		if (a.j0() < 0)
			b.setJ0( Math.ceil(a.j0()) );
		else
			b.setJ0( Math.floor(a.j0()) );
		if (a.k0() < 0)
			b.setK0( Math.ceil(a.k0()) );
		else
			b.setK0( Math.floor(a.k0()) );
	}

	@Override
	public void roundAwayFromZero(OctonionFloat64Member a, OctonionFloat64Member b) {
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
		if (a.l() > 0)
			b.setL( Math.ceil(a.l()) );
		else
			b.setL( Math.floor(a.l()) );
		if (a.i0() > 0)
			b.setI0( Math.ceil(a.i0()) );
		else
			b.setI0( Math.floor(a.i0()) );
		if (a.j0() > 0)
			b.setJ0( Math.ceil(a.j0()) );
		else
			b.setJ0( Math.floor(a.j0()) );
		if (a.k0() > 0)
			b.setK0( Math.ceil(a.k0()) );
		else
			b.setK0( Math.floor(a.k0()) );
	}

	@Override
	public void roundPositive(OctonionFloat64Member a, OctonionFloat64Member b) {
		b.setR( Math.ceil(a.r()) );
		b.setI( Math.ceil(a.i()) );
		b.setJ( Math.ceil(a.j()) );
		b.setK( Math.ceil(a.k()) );
		b.setL( Math.ceil(a.l()) );
		b.setI0( Math.ceil(a.i0()) );
		b.setJ0( Math.ceil(a.j0()) );
		b.setK0( Math.ceil(a.k0()) );
	}

	@Override
	public void roundNegative(OctonionFloat64Member a, OctonionFloat64Member b) {
		b.setR( Math.floor(a.r()) );
		b.setI( Math.floor(a.i()) );
		b.setJ( Math.floor(a.j()) );
		b.setK( Math.floor(a.k()) );
		b.setL( Math.floor(a.l()) );
		b.setI0( Math.floor(a.i0()) );
		b.setJ0( Math.floor(a.j0()) );
		b.setK0( Math.floor(a.k0()) );
	}

	@Override
	public void roundNearest(OctonionFloat64Member a, OctonionFloat64Member b) {
		b.setR( Math.round(a.r()) );
		b.setI( Math.round(a.i()) );
		b.setJ( Math.round(a.j()) );
		b.setK( Math.round(a.k()) );
		b.setL( Math.round(a.l()) );
		b.setI0( Math.round(a.i0()) );
		b.setJ0( Math.round(a.j0()) );
		b.setK0( Math.round(a.k0()) );
	}

	@Override
	public void roundNearestEven(OctonionFloat64Member a, OctonionFloat64Member b) {
		b.setR( Math.rint(a.r()) );
		b.setI( Math.rint(a.i()) );
		b.setJ( Math.rint(a.j()) );
		b.setK( Math.rint(a.k()) );
		b.setL( Math.rint(a.l()) );
		b.setI0( Math.rint(a.i0()) );
		b.setJ0( Math.rint(a.j0()) );
		b.setK0( Math.rint(a.k0()) );
	}

	@Override
	public boolean isNaN(OctonionFloat64Member a) {
		return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k()) ||
				Double.isNaN(a.l()) || Double.isNaN(a.i0()) || Double.isNaN(a.j0()) || Double.isNaN(a.k0());
	}

	@Override
	public boolean isInfinite(OctonionFloat64Member a) {
		return !isNaN(a) && (
				Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) ||
				Double.isInfinite(a.k()) || Double.isInfinite(a.l()) || Double.isInfinite(a.i0()) ||
				Double.isInfinite(a.j0()) || Double.isInfinite(a.k0()) );
	}

	@Override
	public void norm(OctonionFloat64Member a, Float64Member b) {
		b.setV( norm(a) );
	}

	private double norm(OctonionFloat64Member a) {
		// a hypot()-like implementation that avoids overflow
		double max = Math.max(Math.abs(a.r()), Math.abs(a.i()));
		max = Math.max(max, Math.abs(a.j()));
		max = Math.max(max, Math.abs(a.k()));
		max = Math.max(max, Math.abs(a.l()));
		max = Math.max(max, Math.abs(a.i0()));
		max = Math.max(max, Math.abs(a.j0()));
		max = Math.max(max, Math.abs(a.k0()));
		double sum = (a.r()/max) * (a.r()/max);
		sum += (a.i()/max) * (a.i()/max);
		sum += (a.j()/max) * (a.j()/max);
		sum += (a.k()/max) * (a.k()/max);
		sum += (a.l()/max) * (a.l()/max);
		sum += (a.i0()/max) * (a.i0()/max);
		sum += (a.j0()/max) * (a.j0()/max);
		sum += (a.k0()/max) * (a.k0()/max);
		return max * Math.sqrt(sum);
	}
	
	private double norm2(OctonionFloat64Member a) {
		double norm = norm(a);
		return norm * norm;
	}
	
	@Override
	public void conjugate(OctonionFloat64Member a, OctonionFloat64Member b) {
		b.setR(a.r());
		b.setI(-a.i());
		b.setJ(-a.j());
		b.setK(-a.k());
		b.setL(-a.l());
		b.setI0(-a.i0());
		b.setJ0(-a.j0());
		b.setK0(-a.k0());
	}

	@Override
	public void PI(OctonionFloat64Member a) {
		assign(PI, a);
	}

	@Override
	public void E(OctonionFloat64Member a) {
		assign(E, a);
	}
	
	@Override
	public void random(OctonionFloat64Member a) {
		a.setR(rng.nextDouble());
		a.setI(rng.nextDouble());
		a.setJ(rng.nextDouble());
		a.setK(rng.nextDouble());
		a.setL(rng.nextDouble());
		a.setI0(rng.nextDouble());
		a.setJ0(rng.nextDouble());
		a.setK0(rng.nextDouble());
	}

	public void real(OctonionFloat64Member a, Float64Member b) {
		b.setV(a.r());
	}
	
	public void unreal(OctonionFloat64Member a, OctonionFloat64Member b) {
		assign(a, b);
		b.setR(0);
	}

	@Override
	public void sinh(OctonionFloat64Member a, OctonionFloat64Member b) {
		OctonionFloat64Member negA = new OctonionFloat64Member();
		OctonionFloat64Member sum = new OctonionFloat64Member();
		OctonionFloat64Member tmp1 = new OctonionFloat64Member();
		OctonionFloat64Member tmp2 = new OctonionFloat64Member();
		negate(a, negA);
		exp(a, tmp1);
		exp(negA, tmp2);
		subtract(tmp1, tmp2, sum);
		divide(sum, TWO, b);
	}

	@Override
	public void cosh(OctonionFloat64Member a, OctonionFloat64Member b) {
		OctonionFloat64Member negA = new OctonionFloat64Member();
		OctonionFloat64Member sum = new OctonionFloat64Member();
		OctonionFloat64Member tmp1 = new OctonionFloat64Member();
		OctonionFloat64Member tmp2 = new OctonionFloat64Member();
		negate(a, negA);
		exp(a, tmp1);
		exp(negA, tmp2);
		add(tmp1, tmp2, sum);
		divide(sum, TWO, b);
    }

	@Override
	public void tanh(OctonionFloat64Member a, OctonionFloat64Member b) {
		OctonionFloat64Member n = new OctonionFloat64Member();
		OctonionFloat64Member d = new OctonionFloat64Member();
		sinh(a, n);
		cosh(a, d);
		divide(n, d, b);
	}

	@Override
	public void sin(OctonionFloat64Member a, OctonionFloat64Member b) {
		Float64Member z = new Float64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double w = Math.cos(a.r())*Float64Group.sinhc_pi(z.v());
		b.setR(Math.sin(a.r())*Math.cosh(z.v()));
		b.setI(w*a.i());
		b.setJ(w*a.j());
		b.setK(w*a.k());
		b.setL(w*a.l());
		b.setI0(w*a.i0());
		b.setJ0(w*a.j0());
		b.setK0(w*a.k0());
	}

	@Override
	public void cos(OctonionFloat64Member a, OctonionFloat64Member b) {
		Float64Member z = new Float64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double w = -Math.sin(a.r())*Float64Group.sinhc_pi(z.v());
		b.setR(Math.cos(a.r())*Math.cosh(z.v()));
		b.setI(w*a.i());
		b.setJ(w*a.j());
		b.setK(w*a.k());
		b.setL(w*a.l());
		b.setI0(w*a.i0());
		b.setJ0(w*a.j0());
		b.setK0(w*a.k0());
	}

	@Override
	public void tan(OctonionFloat64Member a, OctonionFloat64Member b) {
		OctonionFloat64Member n = new OctonionFloat64Member();
		OctonionFloat64Member d = new OctonionFloat64Member();
		sin(a, n);
		cos(a, d);
		divide(n, d, b);
	}

	@Override
	public void exp(OctonionFloat64Member a, OctonionFloat64Member b) {
		Float64Member z = new Float64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		double u = Math.exp(a.r());
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double w = Float64Group.sinc_pi(z.v());
		b.setR(u * Math.cos(z.v()));
		b.setI(u * w * a.i());
		b.setJ(u * w * a.j());
		b.setK(u * w * a.k());
		b.setL(u * w * a.l());
		b.setI0(u * w * a.i0());
		b.setJ0(u * w * a.j0());
		b.setK0(u * w * a.k0());
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	@Override
	public void log(OctonionFloat64Member a, OctonionFloat64Member b) {
		double factor;
		OctonionFloat64Member unreal = new OctonionFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		Float64Member norm = new Float64Member();
		assign(a, b); // this should be safe if two variables or one
		unreal(a, unreal);
		norm(unreal, norm);
		tmp.setR(a.r());
		tmp.setI(norm.v());
		g.log(tmp, tmp);
		if ( norm.v() == 0.0 ) {
			factor = tmp.i();
		} else {
			factor = tmp.i() / norm.v();
		}

		multiplier(tmp.r(), factor, b);
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	private void multiplier(double r, double factor, OctonionFloat64Member result) {
		if ( Double.isNaN( factor ) || Double.isInfinite( factor ) ) {
			if ( result.i() == 0 && result.j() == 0 && result.k() == 0 ) {
				result.setR(r);
				result.setI(result.i() * factor);
				result.setJ(result.j() * factor);
				result.setK(result.k() * factor);
				result.setL(result.l() * factor);
				result.setI0(result.i0() * factor);
				result.setJ0(result.j0() * factor);
				result.setK0(result.k0() * factor);
			}
			else {
				double signum = Math.signum(factor);
				result.setR(r);
				if (result.i() == 0) result.setI(signum * result.i()); else result.setI(factor * result.i());
				if (result.j() == 0) result.setJ(signum * result.j()); else result.setJ(factor * result.j());
				if (result.k() == 0) result.setK(signum * result.k()); else result.setK(factor * result.k());
				if (result.l() == 0) result.setL(signum * result.l()); else result.setL(factor * result.l());
				if (result.i0() == 0) result.setI0(signum * result.i0()); else result.setI0(factor * result.i0());
				if (result.j0() == 0) result.setJ0(signum * result.j0()); else result.setJ0(factor * result.j0());
				if (result.k0() == 0) result.setK0(signum * result.k0()); else result.setK0(factor * result.k0());
			}
		}
		else {
			result.setR(r);
			result.setI(result.i() * factor);
			result.setJ(result.j() * factor);
			result.setK(result.k() * factor);
			result.setL(result.l() * factor);
			result.setI0(result.i0() * factor);
			result.setJ0(result.j0() * factor);
			result.setK0(result.k0() * factor);
		}
	}
	
	/*
	 * From boost library headers
       template<typename T>
        inline octonion<T>                        exp(octonion<T> const & o)
        {
            using    ::std::exp;
            using    ::std::cos;
            
            using    ::boost::math::sinc_pi;
            
            T    u = exp(real(o));
            
            T    z = abs(unreal(o));
            
            T    w = sinc_pi(z);
            
            return(u*octonion<T>(cos(z),
                w*o.R_component_2(), w*o.R_component_3(),
                w*o.R_component_4(), w*o.R_component_5(),
                w*o.R_component_6(), w*o.R_component_7(),
                w*o.R_component_8()));
        }
        
        
        template<typename T>
        inline octonion<T>                        cos(octonion<T> const & o)
        {
            using    ::std::sin;
            using    ::std::cos;
            using    ::std::cosh;
            
            using    ::boost::math::sinhc_pi;
            
            T    z = abs(unreal(o));
            
            T    w = -sin(o.real())*sinhc_pi(z);
            
            return(octonion<T>(cos(o.real())*cosh(z),
                w*o.R_component_2(), w*o.R_component_3(),
                w*o.R_component_4(), w*o.R_component_5(),
                w*o.R_component_6(), w*o.R_component_7(),
                w*o.R_component_8()));
        }
        
        
        template<typename T>
        inline octonion<T>                        sin(octonion<T> const & o)
        {
            using    ::std::sin;
            using    ::std::cos;
            using    ::std::cosh;
            
            using    ::boost::math::sinhc_pi;
            
            T    z = abs(unreal(o));
            
            T    w = +cos(o.real())*sinhc_pi(z);
            
            return(octonion<T>(sin(o.real())*cosh(z),
                w*o.R_component_2(), w*o.R_component_3(),
                w*o.R_component_4(), w*o.R_component_5(),
                w*o.R_component_6(), w*o.R_component_7(),
                w*o.R_component_8()));
        }
        
        
        template<typename T>
        inline octonion<T>                        tan(octonion<T> const & o)
        {
            return(sin(o)/cos(o));
        }
        
        
        template<typename T>
        inline octonion<T>                        cosh(octonion<T> const & o)
        {
            return((exp(+o)+exp(-o))/static_cast<T>(2));
        }
        
        
        template<typename T>
        inline octonion<T>                        sinh(octonion<T> const & o)
        {
            return((exp(+o)-exp(-o))/static_cast<T>(2));
        }
        
        
        template<typename T>
        inline octonion<T>                        tanh(octonion<T> const & o)
        {
            return(sinh(o)/cosh(o));
        }
        
        
        template<typename T>
        octonion<T>                                pow(octonion<T> const & o,
                                                    int n)
        {
            if        (n > 1)
            {
                int    m = n>>1;
                
                octonion<T>    result = pow(o, m);
                
                result *= result;
                
                if    (n != (m<<1))
                {
                    result *= o; // n odd
                }
                
                return(result);
            }
            else if    (n == 1)
            {
                return(o);
            }
            else if    (n == 0)
            {
                return(octonion<T>(static_cast<T>(1)));
            }
            else    // n < 0
            {
                return(pow(octonion<T>(static_cast<T>(1))/o,-n));
            }
        }
        
      
	 */
}
