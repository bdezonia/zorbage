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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

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
    Rounding<QuaternionFloat64Member>,
    Random<QuaternionFloat64Member>,
    Exponential<QuaternionFloat64Member>,
    Trigonometric<QuaternionFloat64Member>,
    Hyperbolic<QuaternionFloat64Member>,
    Power<QuaternionFloat64Member>,
    RealUnreal<QuaternionFloat64Member,Float64Member>
{
	
	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(0,0,0,0);
	private static final QuaternionFloat64Member ONE = new QuaternionFloat64Member(1,0,0,0);
	private static final QuaternionFloat64Member TWO = new QuaternionFloat64Member(2,0,0,0);
	private static final QuaternionFloat64Member E = new QuaternionFloat64Member(Math.E,0,0,0);
	private static final QuaternionFloat64Member PI = new QuaternionFloat64Member(Math.PI,0,0,0);
	private static final QuaternionFloat64Member NaN = new QuaternionFloat64Member(Double.NaN,Double.NaN,Double.NaN,Double.NaN);
	private static final Float64Group dbl = new Float64Group();
	
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
		b.setR( Math.round(a.r()) );
		b.setI( Math.round(a.i()) );
		b.setJ( Math.round(a.j()) );
		b.setK( Math.round(a.k()) );
	}

	@Override
	public void roundNearestEven(QuaternionFloat64Member a, QuaternionFloat64Member b) {
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
		dbl.acos(multiplier, term);
		b.setR(Math.log(norm.v()));
		b.setI(v1.v() * term.v());
		b.setJ(v2.v() * term.v());
		b.setK(v3.v() * term.v());
	}

	@Override
	public void random(QuaternionFloat64Member a) {
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

	
	// for log()
	// http://math.stackexchange.com/questions/2552/the-logarithm-of-quaternion
	// also wikipedia
	// if q = a + bi + cj + dk = a + v
	// then log q = log || q || + (v / || v ||) * acos(a / || q ||)
	
	//http://www.lce.hut.fi/~ssarkka/pub/quat.pdf
	// power: q1^q2 = exp(log(q1) ∗ q2).
	
	/*
	 * From boost library headers:

        // transcendentals
        // (please see the documentation)
        
        
        template<typename T>
        inline quaternion<T>                    exp(quaternion<T> const & q)
        {
            using    ::std::exp;
            using    ::std::cos;
            
            using    ::boost::math::sinc_pi;
            
            T    u = exp(real(q));
            
            T    z = abs(unreal(q));
            
            T    w = sinc_pi(z);
            
            return(u*quaternion<T>(cos(z),
                w*q.R_component_2(), w*q.R_component_3(),
                w*q.R_component_4()));
        }
        
        
        template<typename T>
        inline quaternion<T>                    cos(quaternion<T> const & q)
        {
            using    ::std::sin;
            using    ::std::cos;
            using    ::std::cosh;
            
            using    ::boost::math::sinhc_pi;
            
            T    z = abs(unreal(q));
            
            T    w = -sin(q.real())*sinhc_pi(z);
            
            return(quaternion<T>(cos(q.real())*cosh(z),
                w*q.R_component_2(), w*q.R_component_3(),
                w*q.R_component_4()));
        }
        
        
        template<typename T>
        inline quaternion<T>                    sin(quaternion<T> const & q)
        {
            using    ::std::sin;
            using    ::std::cos;
            using    ::std::cosh;
            
            using    ::boost::math::sinhc_pi;
            
            T    z = abs(unreal(q));
            
            T    w = +cos(q.real())*sinhc_pi(z);
            
            return(quaternion<T>(sin(q.real())*cosh(z),
                w*q.R_component_2(), w*q.R_component_3(),
                w*q.R_component_4()));
        }
        
        
        template<typename T>
        inline quaternion<T>                    tan(quaternion<T> const & q)
        {
            return(sin(q)/cos(q));
        }
        
        
        template<typename T>
        inline quaternion<T>                    cosh(quaternion<T> const & q)
        {
            return((exp(+q)+exp(-q))/static_cast<T>(2));
        }
        
        
        template<typename T>
        inline quaternion<T>                    sinh(quaternion<T> const & q)
        {
            return((exp(+q)-exp(-q))/static_cast<T>(2));
        }
        
        
        template<typename T>
        inline quaternion<T>                    tanh(quaternion<T> const & q)
        {
            return(sinh(q)/cosh(q));
        }
        
        
        template<typename T>
        quaternion<T>                            pow(quaternion<T> const & q,
                                                    int n)
        {
            if        (n > 1)
            {
                int    m = n>>1;
                
                quaternion<T>    result = pow(q, m);
                
                result *= result;
                
                if    (n != (m<<1))
                {
                    result *= q; // n odd
                }
                
                return(result);
            }
            else if    (n == 1)
            {
                return(q);
            }
            else if    (n == 0)
            {
                return(quaternion<T>(static_cast<T>(1)));
            }
            else    // n < 0
            {
                return(pow(quaternion<T>(static_cast<T>(1))/q,-n));
            }
        }
  

	 */
}
