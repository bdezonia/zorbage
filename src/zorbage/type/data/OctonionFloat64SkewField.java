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

	@Override
	public void unity(OctonionFloat64Member a) {
		a.setR(1);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
	}

	@Override
	public void multiply(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
		// TODO crap this will be a pain
		/*
		 
			×	e0	e1	e2	e3	e4	e5	e6	e7
			e0	e0	e1	e2	e3	e4	e5	e6	e7
			e1	e1	−e0	e3	−e2	e5	−e4	−e7	e6
			e2	e2	−e3	−e0	e1	e6	e7	−e4	−e5
			e3	e3	e2	−e1	−e0	e7	−e6	e5	−e4
			e4	e4	−e5	−e6	−e7	−e0	e1	e2	e3
			e5	e5	e4	−e7	e6	−e1	−e0	−e3	e2
			e6	e6	e7	e4	−e5	−e2	e3	−e0	−e1
			e7	e7	−e6	e5	e4	−e3	−e2	e1	−e0

		 */
		
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
		Float64Member tmp = new Float64Member();
		norm(a, tmp);
		double norm2 = tmp.v() * tmp.v();
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
		b.setV(
			Math.sqrt(
				a.r()*a.r() + a.i()*a.i() + a.j()*a.j() + a.k()*a.k() +
				a.l()*a.l() + a.i0()*a.i0() + a.j0()*a.j0() + a.k0()*a.k0()
			)
		);
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
