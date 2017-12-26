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
package nom.bdezonia.zorbage.type.data.float64.real;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.MiscFloat;
import nom.bdezonia.zorbage.type.algebra.ModularDivision;
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
public class Float64Group
  implements
    OrderedField<Float64Group,Float64Member>,
    Bounded<Float64Member>,
    Norm<Float64Member,Float64Member>,
    Constants<Float64Member>,
    Exponential<Float64Member>,
    Trigonometric<Float64Member>,
    InverseTrigonometric<Float64Member>,
    Hyperbolic<Float64Member>,
    InverseHyperbolic<Float64Member>,
    Infinite<Float64Member>,
    Roots<Float64Member>,
    Power<Float64Member>,
    Rounding<Float64Member>,
    Random<Float64Member>,
    RealUnreal<Float64Member,Float64Member>,
    PredSucc<Float64Member>,
    MiscFloat<Float64Member>,
    ModularDivision<Float64Member>
{
	private static final double taylor_0_bound = Math.ulp(1.0);
	private static final double taylor_2_bound = Math.sqrt(taylor_0_bound);
	private static final double taylor_n_bound = Math.sqrt(taylor_2_bound);
	private static final double TWO_PI = Math.PI * 2;
	
	public Float64Group() {
	}
	
	@Override
	public boolean isEqual(Float64Member a, Float64Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(Float64Member a, Float64Member b) {
		return a.v() != b.v();
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
		to.setV( from.v() );
	}

	@Override
	public void add(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( a.v() + b.v() );
	}

	@Override
	public void subtract(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( a.v() - b.v() );
	}

	@Override
	public void zero(Float64Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(Float64Member a, Float64Member b) {
		b.setV( -a.v() );
	}

	@Override
	public void unity(Float64Member a) {
		a.setV( 1 );
	}

	@Override
	public void invert(Float64Member a, Float64Member b) {
		b.setV( 1.0 / a.v() );
	}

	@Override
	public void divide(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( a.v() / b.v() );
	}

	@Override
	public boolean isLess(Float64Member a, Float64Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(Float64Member a, Float64Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(Float64Member a, Float64Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(Float64Member a, Float64Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(Float64Member a, Float64Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return  1;
		return 0;
	}

	@Override
	public int signum(Float64Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return  1;
		return 0;
	}

	@Override
	public void maxBound(Float64Member a) {
		a.setV( Double.MAX_VALUE );
	}

	@Override
	public void minBound(Float64Member a) {
		a.setV( Double.MIN_VALUE );
	}

	@Override
	public void multiply(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, Float64Member a, Float64Member b) {
		if (power == 0 && a.v() == 0) {
			b.setV(Double.NaN);
			return;
		}
		b.setV( Math.pow(a.v(), power) );
	}

	@Override
	public void abs(Float64Member a, Float64Member b) {
		b.setV( Math.abs(a.v()) );
	}

	@Override
	public void norm(Float64Member a, Float64Member b) {
		abs(a,b);
	}

	@Override
	public void PI(Float64Member a) {
		a.setV( Math.PI );
	}

	@Override
	public void E(Float64Member a) {
		a.setV( Math.E );
	}
	
	@Override
	public void exp(Float64Member a, Float64Member b) {
		b.setV( Math.exp(a.v()) );
	}

	public void expm1(Float64Member a, Float64Member b) {
		b.setV( Math.expm1(a.v()) );
	}

	@Override
	public void log(Float64Member a, Float64Member b) {
		b.setV( Math.log(a.v()) );
	}

	public void log1p(Float64Member a, Float64Member b) {
		b.setV( Math.log1p(a.v()) );
	}

	@Override
	public void cos(Float64Member a, Float64Member b) {
		b.setV( Math.cos(a.v()) );
	}

	@Override
	public void sin(Float64Member a, Float64Member b) {
		b.setV( Math.sin(a.v()) );
	}

	@Override
	public void sinAndCos(Float64Member a, Float64Member s, Float64Member c) {
		double arg = a.v() % TWO_PI;  // this might be faster than some while (arg < 0 || arg >= TWO_PI) loops
		double cosine = Math.cos(arg);
		double sine = Math.sqrt(1 - cosine * cosine);
		if ( arg < 0) {
			if (arg > -Math.PI)
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
	public void tan(Float64Member a, Float64Member b) {
		b.setV( Math.tan(a.v()) );
	}

	// ref: internet

	//@Override
	public void csc(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.sin(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void sec(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.cos(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void cot(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.tan(a.v()) );
	}
	
	@Override
	public void cosh(Float64Member a, Float64Member b) {
		b.setV( Math.cosh(a.v()) );
	}

	@Override
	public void sinh(Float64Member a, Float64Member b) {
		b.setV( Math.sinh(a.v()) );
	}

	@Override
	public void sinhAndCosh(Float64Member a, Float64Member s, Float64Member c) {
		
		double t1 = Math.exp(a.v());
		double t2 = 1/t1;
		double cosh = 0.5 * (t1+t2);
		double sinh = 0.5 * (t1-t2);
		s.setV(sinh);
		c.setV(cosh);
		// alternate approach: accuracy seems the same. investigate. speed compare too.
		//double arg = a.v();
		//double cosh = Math.cosh(arg);
		//double sinh = Math.sqrt(cosh * cosh - 1);
		//if ( arg < 0) {
		//	sinh = -sinh;
		//}
		//s.setV( sinh );
		//c.setV( cosh );
	}
	
	@Override
	public void tanh(Float64Member a, Float64Member b) {
		b.setV( Math.tanh(a.v()) );
	}

	// ref: internet

	//@Override
	public void csch(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.sinh(a.v()) );
	}
	
	// ref: internet

	//@Override
	public void sech(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.cosh(a.v()) );
	}

	// ref: internet

	//@Override
	public void coth(Float64Member a, Float64Member b) {
		b.setV( 1.0 / Math.tanh(a.v()) );
	}
	
	@Override
	public void acos(Float64Member a, Float64Member b) {
		b.setV( Math.acos(a.v()) );
	}

	@Override
	public void asin(Float64Member a, Float64Member b) {
		b.setV( Math.asin(a.v()) );
	}

	@Override
	public void atan(Float64Member a, Float64Member b) {
		b.setV( Math.atan(a.v()) );
	}
	
	public void atan2(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( Math.atan2(a.v(), b.v()) );
	}

	// reference: Wolfram Alpha

	//@Override
	public void acsc(Float64Member a, Float64Member b) {
		// acsc(x) = asin(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		asin(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asec(Float64Member a, Float64Member b) {
		// asec(x) = acos(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		acos(tmp, b);
	}
	
	// reference: Wolfram Alpha

	// @Override
	public void acot(Float64Member a, Float64Member b) {
		// acot(x) = atan(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		atan(tmp, b);
	}
	
	// reference: Mathworld

	@Override
	public void acosh(Float64Member a, Float64Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() - 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void asinh(Float64Member a, Float64Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() + 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void atanh(Float64Member a, Float64Member b) {
		b.setV( 0.5 * (-Math.log(1 - a.v()) + Math.log(1 + a.v())) );
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acsch(Float64Member a, Float64Member b) {
		// acsch(x) = asinh(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		asinh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asech(Float64Member a, Float64Member b) {
		// asech(x) = acosh(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		acosh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acoth(Float64Member a, Float64Member b) {
		// acoth(x) = atanh(1/x)
		Float64Member tmp = new Float64Member(1 / a.v());
		atanh(tmp, b);
	}

	@Override
	public boolean isNaN(Float64Member a) {
		return Double.isNaN(a.v());
	}

	@Override
	public boolean isInfinite(Float64Member a) {
		return Double.isInfinite(a.v());
	}

	@Override
	public void sqrt(Float64Member a, Float64Member b) {
		b.setV( Math.sqrt(a.v()) );
	}

	@Override
	public void cbrt(Float64Member a, Float64Member b) {
		b.setV( Math.cbrt(a.v()) );
	}

	@Override
	public void roundTowardsZero(Float64Member a, Float64Member b) {
		if (a.v() > 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundAwayFromZero(Float64Member a, Float64Member b) {
		if (a.v() < 0)
			roundNegative(a, b);
		else
			roundPositive(a, b);
	}

	@Override
	public void roundPositive(Float64Member a, Float64Member b) {
		b.setV( Math.ceil(a.v()) );
	}

	@Override
	public void roundNegative(Float64Member a, Float64Member b) {
		b.setV( Math.floor(a.v()) );
	}

	@Override
	public void roundNearest(Float64Member a, Float64Member b) {
		b.setV( Math.round(b.v()) );
	}

	@Override
	public void roundNearestEven(Float64Member a, Float64Member b) {
		b.setV( Math.rint(b.v()) );
	}

	@Override
	public void min(Float64Member a, Float64Member b, Float64Member c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(Float64Member a, Float64Member b, Float64Member c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void pow(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( Math.pow(a.v(), b.v()) );
	}
	
	public void IEEEremainder(Float64Member a, Float64Member b, Float64Member c) {
		c.setV( Math.IEEEremainder(a.v(), b.v()) );
	}

	public void log10(Float64Member a, Float64Member b, Float64Member c) {
		b.setV( Math.log10(a.v()) );
	}
	
	public void toDegrees(Float64Member a, Float64Member b) {
		b.setV( Math.toDegrees(a.v()) );
	}
	
	public void toRadians(Float64Member a, Float64Member b) {
		b.setV( Math.toRadians(a.v()) );
	}
	
	@Override
	public void random(Float64Member a) {
		a.setV(ThreadLocalRandom.current().nextDouble());
	}
	
	@Override
	public void real(Float64Member a, Float64Member b) {
		b.setV(a.v());
	}
	
	@Override
	public void unreal(Float64Member a, Float64Member b) {
		b.setV(0);
	}
	
	@Override
	public void pred(Float64Member a, Float64Member b) {
		b.setV(Math.nextDown(a.v()));
	}

	@Override
	public void succ(Float64Member a, Float64Member b) {
		b.setV(Math.nextUp(a.v()));
	}

	@Override
	public void copySign(Float64Member a, Float64Member b, Float64Member c) {
		c.setV(Math.copySign(a.v(), b.v()));
	}

	@Override
	public int getExponent(Float64Member a) {
		return Math.getExponent(a.v());
	}

	@Override
	public void scalb(int scaleFactor, Float64Member a, Float64Member b) {
		b.setV(Math.scalb(a.v(), scaleFactor));
	}

	@Override
	public void ulp(Float64Member a, Float64Member b) {
		b.setV(Math.ulp(a.v()));
	}

	@Override
	public void div(Float64Member a, Float64Member b, Float64Member d) {
		double v = a.v() / b.v();
		if (v > 0)
			v = Math.floor(v);
		else
			v = Math.ceil(v);
		d.setV(v);
	}

	@Override
	public void mod(Float64Member a, Float64Member b, Float64Member m) {
		m.setV(a.v() % b.v());
	}

	@Override
	public void divMod(Float64Member a, Float64Member b, Float64Member d, Float64Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	/*
	Boost Software License - Version 1.0 - August 17th, 2003

	Permission is hereby granted, free of charge, to any person or organization
	obtaining a copy of the software and accompanying documentation covered by
	this license (the "Software") to use, reproduce, display, distribute,
	execute, and transmit the Software, and to prepare derivative works of the
	Software, and to permit third-parties to whom the Software is furnished to
	do so, all subject to the following:

	The copyright notices in the Software and this entire statement, including
	the above license grant, this restriction and the following disclaimer,
	must be included in all copies of the Software, in whole or in part, and
	all derivative works of the Software, unless such copies or derivative
	works are solely in the form of machine-executable object code generated by
	a source language processor.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT
	SHALL THE COPYRIGHT HOLDERS OR ANYONE DISTRIBUTING THE SOFTWARE BE LIABLE
	FOR ANY DAMAGES OR OTHER LIABILITY, WHETHER IN CONTRACT, TORT OR OTHERWISE,
	ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
	DEALINGS IN THE SOFTWARE.
	*/

	// adapted from boost library: TODO write my own impl that fits a
	// curve near zero.

	public static double sinc_pi(double x) {

		if (Math.abs(x) >= taylor_n_bound) {
			return(Math.sin(x)/x);
		}
		else {
			// approximation by taylor series in x at 0 up to order 0
			double result = 1;
			if (Math.abs(x) >= taylor_0_bound) {
				double    x2 = x*x;

				// approximation by taylor series in x at 0 up to order 2
				result -= x2/6;
		
				if (Math.abs(x) >= taylor_2_bound) {
					// approximation by taylor series in x at 0 up to order 4
					result += (x2*x2)/120;
				}
			}
			
			return(result);
		}
	}
	
	// adapted from boost library: TODO write my own impl that fits a
	// curve near zero.

	public static double sinhc_pi(double x) {

		if (Math.abs(x) >= taylor_n_bound) {
			return(Math.sinh(x)/x);
		}
		else {
			// approximation by taylor series in x at 0 up to order 0
			double result = 1;
			if (Math.abs(x) >= taylor_0_bound) {
				double    x2 = x*x;

				// approximation by taylor series in x at 0 up to order 2
				result += x2/6;
		
				if (Math.abs(x) >= taylor_2_bound) {
					// approximation by taylor series in x at 0 up to order 4
					result += (x2*x2)/120;
				}
			}
			
			return(result);
		}
	}

}