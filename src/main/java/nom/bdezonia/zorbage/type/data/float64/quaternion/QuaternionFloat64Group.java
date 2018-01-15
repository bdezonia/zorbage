/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.SkewField;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64Group
  implements
    SkewField<QuaternionFloat64Group,QuaternionFloat64Member>,
    Constants<QuaternionFloat64Member>,
    Norm<QuaternionFloat64Member, Float64Member>,
    Conjugate<QuaternionFloat64Member>,
    Infinite<QuaternionFloat64Member>,
    Rounding<Float64Member,QuaternionFloat64Member>,
    Random<QuaternionFloat64Member>,
    Exponential<QuaternionFloat64Member>,
    Trigonometric<QuaternionFloat64Member>,
    Hyperbolic<QuaternionFloat64Member>,
    Power<QuaternionFloat64Member>,
    RealUnreal<QuaternionFloat64Member,Float64Member>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(0,0,0,0);
	private static final QuaternionFloat64Member ONE = new QuaternionFloat64Member(1,0,0,0);
	private static final QuaternionFloat64Member TWO = new QuaternionFloat64Member(2,0,0,0);
	private static final QuaternionFloat64Member E = new QuaternionFloat64Member(Math.E,0,0,0);
	private static final QuaternionFloat64Member PI = new QuaternionFloat64Member(Math.PI,0,0,0);
	
	public QuaternionFloat64Group() {
	}
	
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
		nom.bdezonia.zorbage.algorithm.Power.compute(this, power, a, b);
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
		// a hypot()-like implementation that avoids overflow
		double max = Math.max(Math.abs(a.r()), Math.abs(a.i()));
		max = Math.max(max, Math.abs(a.j()));
		max = Math.max(max, Math.abs(a.k()));
		double sum = (a.r()/max) * (a.r()/max);
		sum += (a.i()/max) * (a.i()/max);
		sum += (a.j()/max) * (a.j()/max);
		sum += (a.k()/max) * (a.k()/max);
		b.setV( max * Math.sqrt(sum) );
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
	public void round(Round.Mode mode, Float64Member delta, QuaternionFloat64Member a, QuaternionFloat64Member b) {
		Float64Member tmp = new Float64Member();
		tmp.setV(a.r());
		Round.compute(G.DBL, mode, delta, tmp, tmp);
		b.setR(tmp.v());
		tmp.setV(a.i());
		Round.compute(G.DBL, mode, delta, tmp, tmp);
		b.setI(tmp.v());
		tmp.setV(a.j());
		Round.compute(G.DBL, mode, delta, tmp, tmp);
		b.setJ(tmp.v());
		tmp.setV(a.k());
		Round.compute(G.DBL, mode, delta, tmp, tmp);
		b.setK(tmp.v());
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

	@Override
	public void exp(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		Float64Member z = new Float64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		double u = Math.exp(a.r());
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double w = Float64Group.sinc_pi(z.v());
		b.setR(u * Math.cos(z.v()));
		b.setI(u * w * a.i());
		b.setJ(u * w * a.j());
		b.setK(u * w * a.k());
	}

	@Override
	public void log(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		Float64Member norm = new Float64Member(); 
		Float64Member term = new Float64Member(); 
		Float64Member v1 = new Float64Member();
		Float64Member v2 = new Float64Member();
		Float64Member v3 = new Float64Member();
		norm(a, norm);
		Float64Member multiplier = new Float64Member(a.r() / norm.v());
		v1.setV(a.i() * multiplier.v());
		v2.setV(a.j() * multiplier.v());
		v3.setV(a.k() * multiplier.v());
		G.DBL.acos(multiplier, term);
		b.setR(Math.log(norm.v()));
		b.setI(v1.v() * term.v());
		b.setJ(v2.v() * term.v());
		b.setK(v3.v() * term.v());
	}

	@Override
	public void random(QuaternionFloat64Member a) {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		a.setR(rng.nextDouble());
		a.setI(rng.nextDouble());
		a.setJ(rng.nextDouble());
		a.setK(rng.nextDouble());
	}
	

	@Override
	public void real(QuaternionFloat64Member a, Float64Member b) {
		b.setV(a.r());
	}
	
	@Override
	public void unreal(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		assign(a, b);
		b.setR(0);
	}

	@Override
	public void sinh(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO adapted from Complex64Group: might be wrong
		QuaternionFloat64Member negA = new QuaternionFloat64Member();
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();

		negate(a, negA);
		exp(a, tmp1);
		exp(negA, tmp2);
		
		subtract(tmp1, tmp2, sum);
		divide(sum, TWO, b);
	}

	@Override
	public void cosh(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO adapted from Complex64Group: might be wrong
		QuaternionFloat64Member negA = new QuaternionFloat64Member();
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
		
		negate(a, negA);
		exp(a, tmp1);
		exp(negA, tmp2);
		
		add(tmp1, tmp2, sum);
		divide(sum, TWO, b);
    }

	@Override
	public void sinhAndCosh(QuaternionFloat64Member a, QuaternionFloat64Member s, QuaternionFloat64Member c) {
		// TODO adapted from Complex64Group: might be wrong
		QuaternionFloat64Member negA = new QuaternionFloat64Member();
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
		
		negate(a, negA);
		exp(a, tmp1);
		exp(negA, tmp2);
		
		subtract(tmp1, tmp2, sum);
		divide(sum, TWO, s);

		add(tmp1, tmp2, sum);
		divide(sum, TWO, c);
	}
	
	@Override
	public void tanh(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		QuaternionFloat64Member s = new QuaternionFloat64Member();
		QuaternionFloat64Member c = new QuaternionFloat64Member();
		sinhAndCosh(a, s, c);
		divide(s, c, b);
	}

	@Override
	public void sin(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		Float64Member z = new Float64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double cos = Math.cos(a.r());
		double sin = Math.sin(a.r());
		double sinhc_pi = Float64Group.sinhc_pi(z.v());
		double cosh = Math.cosh(z.v());
		double ws = cos * sinhc_pi;
		b.setR(sin * cosh);
		b.setI(ws * a.i());
		b.setJ(ws * a.j());
		b.setK(ws * a.k());
	}

	@Override
	public void cos(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		Float64Member z = new Float64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double cos = Math.cos(a.r());
		double sin = Math.sin(a.r());
		double sinhc_pi = Float64Group.sinhc_pi(z.v());
		double cosh = Math.cosh(z.v());
		double wc = -sin * sinhc_pi;
		b.setR(cos * cosh);
		b.setI(wc * a.i());
		b.setJ(wc * a.j());
		b.setK(wc * a.k());
	}

	@Override
	public void sinAndCos(QuaternionFloat64Member a, QuaternionFloat64Member s, QuaternionFloat64Member c) {
		Float64Member z = new Float64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		unreal(a, tmp);
		norm(tmp, z); // TODO or abs() whatever that is in boost
		double cos = Math.cos(a.r());
		double sin = Math.sin(a.r());
		double sinhc_pi = Float64Group.sinhc_pi(z.v());
		double cosh = Math.cosh(z.v());
		double ws = cos * sinhc_pi;
		double wc = -sin * sinhc_pi;
		s.setR(sin * cosh);
		s.setI(ws * a.i());
		s.setJ(ws * a.j());
		s.setK(ws * a.k());
		c.setR(cos * cosh);
		c.setI(wc * a.i());
		c.setJ(wc * a.j());
		c.setK(wc * a.k());
	}
	
	@Override
	public void tan(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		QuaternionFloat64Member sin = new QuaternionFloat64Member();
		QuaternionFloat64Member cos = new QuaternionFloat64Member();
		sinAndCos(a, sin, cos);
		divide(sin, cos, b);
	}

	@Override
	public void pow(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void sinch(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO - improve accuracy near 0 by fitting polynomial
		if (isEqual(ZERO, a))
			assign(ONE, b);
		else {
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			sinh(a, tmp);
			divide(tmp, a, b);
		}
	}

	@Override
	public void sinchpi(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO - improve accuracy near 0 by fitting polynomial
		if (isEqual(ZERO, a))
			assign(ONE, b);
		else {
			QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp3 = new QuaternionFloat64Member();
			multiply(a, PI, tmp2);
			sinh(tmp2, tmp3);
			divide(tmp3, tmp2, b);
		}
	}

	@Override
	public void sinc(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO - improve accuracy near 0 by fitting polynomial
		if (isEqual(ZERO, a))
			assign(ONE, b);
		else {
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			sin(a, tmp);
			divide(tmp, a, b);
		}
	}

	@Override
	public void sincpi(QuaternionFloat64Member a, QuaternionFloat64Member b) {
		// TODO - improve accuracy near 0 by fitting polynomial
		if (isEqual(ZERO, a))
			assign(ONE, b);
		else {
			QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp3 = new QuaternionFloat64Member();
			multiply(a, PI, tmp2);
			sin(tmp2, tmp3);
			divide(tmp3, tmp2, b);
		}
	}
}
