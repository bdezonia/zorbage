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
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat64SkewField
  implements
    SkewField<OctonionFloat64SkewField, OctonionFloat64Member>,
    Conjugate<OctonionFloat64Member>,
    Norm<OctonionFloat64Member,Float64Member>,
    Infinite<OctonionFloat64Member>,
    Rounding<OctonionFloat64Member>,
    Constants<OctonionFloat64Member>
{

	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(0, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member ONE = new OctonionFloat64Member(1, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member PI = new OctonionFloat64Member(Math.PI, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member E = new OctonionFloat64Member(Math.E, 0, 0, 0, 0, 0, 0, 0);

	public OctonionFloat64SkewField() {
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

	// TODO: scale coeffs by -1 or multiply by (-1,0,0,0,0,0,0,0)? Same with Quats and Complexes.
	
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

	// TODO need generalized and accurate hypot() method for quats and octs
	
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

}
