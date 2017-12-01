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
package nom.bdezonia.zorbage.type.data.float16.real;

import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.MiscFloat;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.PredSucc;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float16Group
  implements
    OrderedField<Float16Group,Float16Member>,
    Bounded<Float16Member>,
    Norm<Float16Member,Float16Member>,
    Constants<Float16Member>,
    Exponential<Float16Member>,
    Trigonometric<Float16Member>,
    InverseTrigonometric<Float16Member>,
    Hyperbolic<Float16Member>,
    InverseHyperbolic<Float16Member>,
    Infinite<Float16Member>,
    Roots<Float16Member>,
    Power<Float16Member>,
    Rounding<Float16Member>,
    Random<Float16Member>,
    RealUnreal<Float16Member,Float16Member>,
    PredSucc<Float16Member>,
    MiscFloat<Float16Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	
	private static final double TWO_PI = Math.PI * 2;
	
	public Float16Group() {
	}
	
	@Override
	public boolean isEqual(Float16Member a, Float16Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(Float16Member a, Float16Member b) {
		return a.v() != b.v();
	}

	@Override
	public Float16Member construct() {
		return new Float16Member();
	}

	@Override
	public Float16Member construct(Float16Member other) {
		return new Float16Member(other);
	}

	@Override
	public Float16Member construct(String s) {
		return new Float16Member(s);
	}

	@Override
	public void assign(Float16Member from, Float16Member to) {
		to.setV( from.v() );
	}

	@Override
	public void add(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( a.v() + b.v() );
	}

	@Override
	public void subtract(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( a.v() - b.v() );
	}

	@Override
	public void zero(Float16Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(Float16Member a, Float16Member b) {
		b.setV( -a.v() );
	}

	@Override
	public void unity(Float16Member a) {
		a.setV( 1 );
	}

	@Override
	public void invert(Float16Member a, Float16Member b) {
		b.setV( 1.0 / a.v() );
	}

	@Override
	public void divide(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( a.v() / b.v() );
	}

	@Override
	public boolean isLess(Float16Member a, Float16Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(Float16Member a, Float16Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(Float16Member a, Float16Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(Float16Member a, Float16Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(Float16Member a, Float16Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return  1;
		return 0;
	}

	@Override
	public int signum(Float16Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return  1;
		return 0;
	}

	@Override
	public void maxBound(Float16Member a) {
		a.setV( Float16Member.toDouble((short) 0b0111101111111111));
	}

	@Override
	public void minBound(Float16Member a) {
		a.setV( Float16Member.toDouble((short) 0b1111101111111111));
	}

	@Override
	public void multiply(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, Float16Member a, Float16Member b) {
		if (power == 0 && a.v() == 0) {
			b.setV(Double.NaN);
			return;
		}
		b.setV( Math.pow(a.v(), power) );
	}

	@Override
	public void abs(Float16Member a, Float16Member b) {
		b.setV( Math.abs(a.v()) );
	}

	@Override
	public void norm(Float16Member a, Float16Member b) {
		b.setV( Math.abs(a.v()) );
	}

	@Override
	public void PI(Float16Member a) {
		a.setV( Math.PI );
	}

	@Override
	public void E(Float16Member a) {
		a.setV( Math.E );
	}
	
	@Override
	public void exp(Float16Member a, Float16Member b) {
		b.setV( Math.exp(a.v()) );
	}

	public void expm1(Float16Member a, Float16Member b) {
		b.setV( Math.expm1(a.v()) );
	}

	@Override
	public void log(Float16Member a, Float16Member b) {
		b.setV( Math.log(a.v()) );
	}

	public void log1p(Float16Member a, Float16Member b) {
		b.setV( Math.log1p(a.v()) );
	}

	@Override
	public void cos(Float16Member a, Float16Member b) {
		b.setV( Math.cos(a.v()) );
	}

	@Override
	public void sin(Float16Member a, Float16Member b) {
		b.setV( Math.sin(a.v()) );
	}

	@Override
	public void sinAndCos(Float16Member a, Float16Member s, Float16Member c) {
		double arg = a.v() % TWO_PI;  // this might be faster than some while (arg < 0 || arg >= TWO_PI) loops
		double cosine = Math.cos(arg);
		double sine = Math.sqrt(1 - cosine * cosine);
		if ( arg < 0) {
			if (arg < -Math.PI)
				sine = -sine;
		}
		else { // arg >= 0
			if (arg > Math.PI)
				sine = -sine;
		}
		s.setV( sine );
		c.setV( cosine );
	}

	@Override
	public void tan(Float16Member a, Float16Member b) {
		b.setV( Math.tan(a.v()) );
	}

	// ref: internet

	//@Override
	public void csc(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.sin(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void sec(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.cos(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void cot(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.tan(a.v()) );
	}
	
	@Override
	public void cosh(Float16Member a, Float16Member b) {
		b.setV( Math.cosh(a.v()) );
	}

	@Override
	public void sinh(Float16Member a, Float16Member b) {
		b.setV( Math.sinh(a.v()) );
	}

	@Override
	public void sinhAndCosh(Float16Member a, Float16Member s, Float16Member c) {
		// TODO - is there a speedup?
		sinh(a, s);
		cosh(a, c);
	}
	
	@Override
	public void tanh(Float16Member a, Float16Member b) {
		b.setV( Math.tanh(a.v()) );
	}

	// ref: internet

	//@Override
	public void csch(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.sinh(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void sech(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.cosh(a.v()) );
	}

	// ref: internet

	//@Override
	public void coth(Float16Member a, Float16Member b) {
		b.setV( 1.0 / Math.tanh(a.v()) );
	}
	
	@Override
	public void acos(Float16Member a, Float16Member b) {
		b.setV( Math.acos(a.v()) );
	}

	@Override
	public void asin(Float16Member a, Float16Member b) {
		b.setV( Math.asin(a.v()) );
	}

	@Override
	public void atan(Float16Member a, Float16Member b) {
		b.setV( Math.atan(a.v()) );
	}
	
	public void atan2(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.atan2(a.v(), b.v()) );
	}

	// reference: Wolfram Alpha

	//@Override
	public void acsc(Float16Member a, Float16Member b) {
		// acsc(x) = asin(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		asin(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asec(Float16Member a, Float16Member b) {
		// asec(x) = acos(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		acos(tmp, b);
	}
	
	// reference: Wolfram Alpha

	// @Override
	public void acot(Float16Member a, Float16Member b) {
		// acot(x) = atan(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		atan(tmp, b);
	}
	
	// reference: Mathworld

	@Override
	public void acosh(Float16Member a, Float16Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() - 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void asinh(Float16Member a, Float16Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() + 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void atanh(Float16Member a, Float16Member b) {
		b.setV( 0.5 * (-Math.log(1 - a.v()) + Math.log(1 + a.v())) );
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acsch(Float16Member a, Float16Member b) {
		// acsch(x) = asinh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		asinh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asech(Float16Member a, Float16Member b) {
		// asech(x) = acosh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		acosh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acoth(Float16Member a, Float16Member b) {
		// acoth(x) = atanh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		atanh(tmp, b);
	}

	@Override
	public boolean isNaN(Float16Member a) {
		return Double.isNaN(a.v());
	}

	@Override
	public boolean isInfinite(Float16Member a) {
		return Double.isInfinite(a.v());
	}

	@Override
	public void sqrt(Float16Member a, Float16Member b) {
		b.setV( Math.sqrt(a.v()) );
	}

	@Override
	public void cbrt(Float16Member a, Float16Member b) {
		b.setV( Math.cbrt(a.v()) );
	}

	@Override
	public void roundTowardsZero(Float16Member a, Float16Member b) {
		if (a.v() > 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundAwayFromZero(Float16Member a, Float16Member b) {
		if (a.v() < 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundPositive(Float16Member a, Float16Member b) {
		b.setV( Math.ceil(a.v()) );
	}

	@Override
	public void roundNegative(Float16Member a, Float16Member b) {
		b.setV( Math.floor(a.v()) );
	}

	@Override
	public void roundNearest(Float16Member a, Float16Member b) {
		b.setV( Math.round(b.v()) );
	}

	@Override
	public void roundNearestEven(Float16Member a, Float16Member b) {
		b.setV( Math.rint(b.v()) );
	}

	@Override
	public void min(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.min(a.v(), b.v()) );
	}

	@Override
	public void max(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.max(a.v(), b.v()) );
	}

	@Override
	public void pow(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.pow(a.v(), b.v()) );
	}
	
	public void copySign(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.copySign(a.v(), b.v()) );
	}
	
	public void log10(Float16Member a, Float16Member b, Float16Member c) {
		b.setV( Math.log10(a.v()) );
	}
	
	public void toDegrees(Float16Member a, Float16Member b) {
		b.setV( Math.toDegrees(a.v()) );
	}
	
	public void toRadians(Float16Member a, Float16Member b) {
		b.setV( Math.toRadians(a.v()) );
	}
	
	public void random(Float16Member a) {
		a.setV(rng.nextDouble());
	}
	
	@Override
	public void real(Float16Member a, Float16Member b) {
		b.setV(a.v());
	}
	
	@Override
	public void unreal(Float16Member a, Float16Member b) {
		b.setV(0);
	}


	@Override
	public void pred(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void succ(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int getExponent(Float16Member a) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void scalb(int scaleFactor, Float16Member a, Float16Member b) {
		b.setV(Math.scalb(a.v(),scaleFactor));
	}

	@Override
	public void ulp(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}
}