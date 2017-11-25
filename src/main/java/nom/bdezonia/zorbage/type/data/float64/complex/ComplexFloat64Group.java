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

// Note: some code here adapted from Imglib2 code written by Barry DeZonia
//
// * ImgLib2: a general-purpose, multidimensional image processing library.
// * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
// * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
// * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
// * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
// * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.


package zorbage.type.data.float64.complex;

import zorbage.type.algebra.Conjugate;
import zorbage.type.algebra.Constants;
import zorbage.type.algebra.Exponential;
import zorbage.type.algebra.Field;
import zorbage.type.algebra.Hyperbolic;
import zorbage.type.algebra.Infinite;
import zorbage.type.algebra.InverseHyperbolic;
import zorbage.type.algebra.InverseTrigonometric;
import zorbage.type.algebra.Norm;
import zorbage.type.algebra.Power;
import zorbage.type.algebra.Random;
import zorbage.type.algebra.Roots;
import zorbage.type.algebra.Rounding;
import zorbage.type.algebra.Trigonometric;
import zorbage.type.algebra.RealUnreal;
import zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Group
  implements
    Field<ComplexFloat64Group, ComplexFloat64Member>,
    Norm<ComplexFloat64Member, Float64Member>,
    Constants<ComplexFloat64Member>,
    Exponential<ComplexFloat64Member>,
    Trigonometric<ComplexFloat64Member>,
    InverseTrigonometric<ComplexFloat64Member>,
    Hyperbolic<ComplexFloat64Member>,
    InverseHyperbolic<ComplexFloat64Member>,
    Roots<ComplexFloat64Member>,
    Power<ComplexFloat64Member>,
    Rounding<ComplexFloat64Member>,
    Infinite<ComplexFloat64Member>,
    Conjugate<ComplexFloat64Member>,
    Random<ComplexFloat64Member>,
    RealUnreal<ComplexFloat64Member,Float64Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	private static final ComplexFloat64Member ONE = new ComplexFloat64Member(1,0);
	private static final ComplexFloat64Member TWO = new ComplexFloat64Member(2,0);
	private static final ComplexFloat64Member MINUS_ONE = new ComplexFloat64Member(-1,0);
	private static final ComplexFloat64Member PI = new ComplexFloat64Member(Math.PI,0);
	private static final ComplexFloat64Member E = new ComplexFloat64Member(Math.E,0);
	private static final ComplexFloat64Member ONE_HALF = new ComplexFloat64Member(0.5,0);
	private static final ComplexFloat64Member I = new ComplexFloat64Member(0,1);
	private static final ComplexFloat64Member I_OVER_TWO = new ComplexFloat64Member(0,0.5);
	private static final ComplexFloat64Member TWO_I = new ComplexFloat64Member(0,2);
	private static final ComplexFloat64Member MINUS_I = new ComplexFloat64Member(0,-1);
	private static final ComplexFloat64Member MINUS_I_OVER_TWO = new ComplexFloat64Member(0,-0.5);
	private static final ComplexFloat64Member NaN = new ComplexFloat64Member(Double.NaN,Double.NaN);

	public ComplexFloat64Group() {
	}
	
	@Override
	public void multiply(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		// for safety must use tmps
		double r = a.r()*b.r() - a.i()*b.i();
		double i = a.i()*b.r() + a.r()*b.i();
		c.setR( r );
		c.setI( i );
	}

	@Override
	public void power(int power, ComplexFloat64Member a, ComplexFloat64Member b) {
		if (power == 0 && isEqual(a, ZERO)) {
			assign(NaN, b);
			return;
		}
		double rToTheN = Math.pow(Math.hypot(a.r(), a.i()), power);
		double nTheta = power * getArgument(a);
		b.setR(rToTheN * Math.cos(nTheta));
		b.setI(rToTheN * Math.sin(nTheta));
	}

	@Override
	public void zero(ComplexFloat64Member a) {
		assign(ZERO,a);
	}

	@Override
	public void negate(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( -a.r() );
		b.setI( -a.i() );
	}

	@Override
	public void add(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.setR( a.r() + b.r() );
		c.setI( a.i() + b.i() );
	}

	@Override
	public void subtract(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		c.setR( a.r() - b.r() );
		c.setI( a.i() - b.i() );
	}

	@Override
	public boolean isEqual(ComplexFloat64Member a, ComplexFloat64Member b) {
		return a.r() == b.r() && a.i() == b.i();
	}

	@Override
	public boolean isNotEqual(ComplexFloat64Member a, ComplexFloat64Member b) {
		return a.r() != b.r() || a.i() != b.i();
	}

	@Override
	public ComplexFloat64Member construct() {
		return new ComplexFloat64Member();
	}

	@Override
	public ComplexFloat64Member construct(ComplexFloat64Member other) {
		return new ComplexFloat64Member(other);
	}

	@Override
	public ComplexFloat64Member construct(String s) {
		return new ComplexFloat64Member(s);
	}

	@Override
	public void assign(ComplexFloat64Member from, ComplexFloat64Member to) {
		to.setR( from.r() );
		to.setI( from.i() );
	}

	@Override
	public void unity(ComplexFloat64Member a) {
		assign(ONE,a);
	}

	@Override
	public void invert(ComplexFloat64Member a, ComplexFloat64Member b) {
		divide(ONE,a,b);
	}

	@Override
	public void divide(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		// for safety must use tmps
		double mod2 = b.r()*b.r() + b.i()*b.i();
		double r = (a.r()*b.r() + a.i()*b.i()) / mod2;
		double i = (a.i()*b.r() - a.r()*b.i()) / mod2;
		c.setR( r );
		c.setI( i );
	}
	
	@Override
	public void conjugate(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( a.r() );
		b.setI( -a.i() );
	}

	@Override
	public void norm(ComplexFloat64Member a, Float64Member b) {
		b.setV( Math.hypot(a.r(),a.i()) );
	}

	@Override
	public void PI(ComplexFloat64Member a) {
		assign(PI, a);
	}

	@Override
	public void E(ComplexFloat64Member a) {
		assign(E, a);
	}

	@Override
	public void asin(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member ia = new ComplexFloat64Member();
		ComplexFloat64Member aSquared = new ComplexFloat64Member();
		ComplexFloat64Member miniSum = new ComplexFloat64Member();
		ComplexFloat64Member root = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member logSum = new ComplexFloat64Member();
		
		multiply(I, a, ia);
		multiply(a, a, aSquared);
		subtract(ONE, aSquared, miniSum);
		pow(miniSum, ONE_HALF, root);
		add(ia, root, sum);
		log(sum, logSum);
		multiply(MINUS_I, logSum, b);
	}

	@Override
	public void acos(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member aSquared = new ComplexFloat64Member();
		ComplexFloat64Member miniSum = new ComplexFloat64Member();
		ComplexFloat64Member root = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member logSum = new ComplexFloat64Member();

		multiply(a, a,aSquared);
		subtract(aSquared, ONE, miniSum);
		pow(miniSum, ONE_HALF, root);
		add(a, root, sum);
		log(sum, logSum);
		multiply(MINUS_I, logSum, b);
	}

	@Override
	public void atan(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member ia = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();
		ComplexFloat64Member quotient = new ComplexFloat64Member();
		ComplexFloat64Member log = new ComplexFloat64Member();
		
		multiply(I, a, ia);
		add(ONE, ia, sum);
		subtract(ONE, ia, diff);
		divide(sum, diff, quotient);
		log(quotient, log);
		multiply(MINUS_I_OVER_TWO, log, b);
	}

	//@Override
	public void acsc(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member recipA = new ComplexFloat64Member();
		
		invert(a, recipA);
		asin(recipA, b);
	}

	//@Override
	public void asec(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member recipA = new ComplexFloat64Member();
		
		invert(a, recipA);
		acos(recipA, b);
	}

	//@Override
	public void acot(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member ia = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();
		ComplexFloat64Member quotient = new ComplexFloat64Member();
		ComplexFloat64Member log = new ComplexFloat64Member();

		multiply(I, a, ia);
		add(ia, ONE, sum);
		subtract(ia, ONE, diff);
		divide(sum, diff, quotient);
		log(quotient, log);
		multiply(I_OVER_TWO, log, b);
	}

	@Override
	public void asinh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member aSquared = new ComplexFloat64Member();
		ComplexFloat64Member miniSum = new ComplexFloat64Member();
		ComplexFloat64Member root = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();

		multiply(a, a, aSquared);
		add(aSquared, ONE, miniSum);
		pow(miniSum, ONE_HALF, root);
		add(a, root, sum);
		log(sum, b);
	}

	@Override
	public void acosh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member aSquared = new ComplexFloat64Member();
		ComplexFloat64Member miniSum = new ComplexFloat64Member();
		ComplexFloat64Member root = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();

		multiply(a, a, aSquared);
		subtract(aSquared, ONE, miniSum);
		pow(miniSum, ONE_HALF, root);
		add(a, root, sum);
		log(sum, b);
	}

	@Override
	public void atanh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();
		ComplexFloat64Member quotient = new ComplexFloat64Member();
		ComplexFloat64Member log = new ComplexFloat64Member();

		add(ONE, a, sum);
		subtract(ONE, a, diff);
		divide(sum, diff, quotient);
		log(quotient, log);
		multiply(ONE_HALF, log, b);
	}

	//@Override
	public void acsch(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member recipA = new ComplexFloat64Member();

		invert(a, recipA);
		asinh(recipA, b);
	}

	//@Override
	public void asech(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member recipA = new ComplexFloat64Member();

		invert(a, recipA);
		acosh(recipA, b);
	}

	//@Override
	public void acoth(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();
		ComplexFloat64Member quotient = new ComplexFloat64Member();
		ComplexFloat64Member log = new ComplexFloat64Member();

		add(a, ONE, sum);
		subtract(a, ONE, diff);
		divide(sum, diff, quotient);
		log(quotient, log);
		multiply(ONE_HALF, log, b);
	}

	@Override
	public void sin(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member IA = new ComplexFloat64Member();
		ComplexFloat64Member minusIA = new ComplexFloat64Member();
		ComplexFloat64Member expIA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();

		multiply(a, I, IA);
		multiply(a, MINUS_I, minusIA);
		exp(IA, expIA);
		exp(minusIA, expMinusIA);
		
		subtract(expIA, expMinusIA, diff);
		divide(diff, TWO_I, b);
	}

	@Override
	public void cos(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member IA = new ComplexFloat64Member();
		ComplexFloat64Member minusIA = new ComplexFloat64Member();
		ComplexFloat64Member expIA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();

		multiply(a, I, IA);
		multiply(a, MINUS_I, minusIA);
		exp(IA, expIA);
		exp(minusIA, expMinusIA);
		
		add(expIA, expMinusIA, sum);
		divide(sum, TWO, b);
	}

	@Override
	public void sinAndCos(ComplexFloat64Member a, ComplexFloat64Member s, ComplexFloat64Member c) {
		ComplexFloat64Member IA = new ComplexFloat64Member();
		ComplexFloat64Member minusIA = new ComplexFloat64Member();
		ComplexFloat64Member expIA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();

		multiply(a, I, IA);
		multiply(a, MINUS_I, minusIA);
		exp(IA, expIA);
		exp(minusIA, expMinusIA);
		
		subtract(expIA, expMinusIA, diff);
		divide(diff, TWO_I, s);

		add(expIA, expMinusIA, sum);
		divide(sum, TWO, c);
	}
	
	@Override
	public void tan(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sin = new ComplexFloat64Member();
		ComplexFloat64Member cos = new ComplexFloat64Member();

		sinAndCos(a, sin, cos);
		divide(sin, cos, b);
	}

	//@Override
	public void csc(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sin = new ComplexFloat64Member();

		sin(a, sin);
		invert(sin, b);
	}

	//@Override
	public void sec(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member cos = new ComplexFloat64Member();

		cos(a, cos);
		invert(cos, b);
	}

	//@Override
	public void cot(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member tan = new ComplexFloat64Member();

		tan(a, tan);
		invert(tan, b);
	}

	@Override
	public void sinh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member expA = new ComplexFloat64Member();
		ComplexFloat64Member minusA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusA = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();

		exp(a, expA);
		multiply(a, MINUS_ONE, minusA);
		exp(minusA, expMinusA);
		
		subtract(expA, expMinusA, diff);
		divide(diff, TWO, b);
	}

	@Override
	public void cosh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member expA = new ComplexFloat64Member();
		ComplexFloat64Member minusA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusA = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();

		exp(a, expA);
		multiply(a, MINUS_ONE, minusA);
		exp(minusA, expMinusA);
		
		add(expA, expMinusA, sum);
		divide(sum, TWO, b);
	}

	@Override
	public void sinhAndCosh(ComplexFloat64Member a, ComplexFloat64Member s, ComplexFloat64Member c) {
		ComplexFloat64Member expA = new ComplexFloat64Member();
		ComplexFloat64Member minusA = new ComplexFloat64Member();
		ComplexFloat64Member expMinusA = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();

		exp(a, expA);
		multiply(a, MINUS_ONE, minusA);
		exp(minusA, expMinusA);
		
		subtract(expA, expMinusA, sum);
		divide(sum, TWO, s);

		add(expA, expMinusA, sum);
		divide(sum, TWO, c);
	}
	
	@Override
	public void tanh(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sinh = new ComplexFloat64Member();
		ComplexFloat64Member cosh = new ComplexFloat64Member();

		sinhAndCosh(a, sinh, cosh);
		divide(sinh, cosh, b);
	}

	//@Override
	public void csch(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member sinh = new ComplexFloat64Member();

		sinh(a, sinh);
		invert(sinh, b);
	}

	//@Override
	public void sech(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member cosh = new ComplexFloat64Member();

		cosh(a, cosh);
		invert(cosh, b);
	}

	//@Override
	public void coth(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member tanh = new ComplexFloat64Member();

		tanh(a, tanh);
		invert(tanh, b);
	}

	@Override
	public void exp(ComplexFloat64Member a, ComplexFloat64Member b) {
		double constant = Math.exp(a.r());
		b.setR( constant * Math.cos(a.i()) );
		b.setI( constant * Math.sin(a.i()) );
	}

	// TODO: make an accurate implementation
	
	public void expm1(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		exp(a, tmp);
		subtract(tmp, ONE, b);
	}

	@Override
	public void log(ComplexFloat64Member a, ComplexFloat64Member b) {
		double modulus = Math.hypot(a.r(), a.i());
		double argument = getArgument(a);
		b.setR( Math.log(modulus) );
		b.setI( getPrincipalArgument(argument) );
	}
	
	private double getArgument(ComplexFloat64Member a) {
		double x = a.r();
		double y = a.i();
		double theta;
		if (x == 0) {
			if (y > 0)
				theta = Math.PI / 2;
			else if (y < 0)
				theta = -Math.PI / 2;
			else // y == 0 : theta indeterminate
				theta = Double.NaN;
		}
		else if (y == 0) {
			if (x > 0)
				theta = 0;
			else // (x < 0)
				theta = Math.PI;
		}
		else // x && y both != 0
			theta = Math.atan2(y,x);
		
		return theta;
	}
	
	private double getPrincipalArgument(double angle) {
		double arg = angle;
		while (arg <= -Math.PI) arg += 2*Math.PI;
		while (arg > Math.PI) arg -= 2*Math.PI;
		return arg;
	}

	// TODO: make an accurate implementation
	
	public void log1p(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		add(a, ONE, tmp);
		log(tmp, b);
	}

	@Override
	public void sqrt(ComplexFloat64Member a, ComplexFloat64Member b) {
		pow(a,ONE_HALF,b);
	}

	// TODO: make an accurate implementation
	
	@Override
	public void cbrt(ComplexFloat64Member a, ComplexFloat64Member b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member(1.0/3,0);
		pow(a,tmp,b);
	}

	@Override
	public void roundTowardsZero(ComplexFloat64Member a, ComplexFloat64Member b) {
		if (a.r() < 0)
			b.setR( Math.ceil(a.r()) );
		else
			b.setR( Math.floor(a.r()) );
		if (a.i() < 0)
			b.setI( Math.ceil(a.i()) );
		else
			b.setI( Math.floor(a.i()) );
	}

	@Override
	public void roundAwayFromZero(ComplexFloat64Member a, ComplexFloat64Member b) {
		if (a.r() > 0)
			b.setR( Math.ceil(a.r()) );
		else
			b.setR( Math.floor(a.r()) );
		if (a.i() > 0)
			b.setI( Math.ceil(a.i()) );
		else
			b.setI( Math.floor(a.i()) );
	}

	@Override
	public void roundNegative(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( Math.floor(a.r()) );
		b.setI( Math.floor(a.i()) );
	}

	@Override
	public void roundPositive(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( Math.ceil(a.r()) );
		b.setI( Math.ceil(a.i()) );
	}

	@Override
	public void roundNearest(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( Math.round(a.r()) );
		b.setI( Math.round(a.i()) );
	}

	@Override
	public void roundNearestEven(ComplexFloat64Member a, ComplexFloat64Member b) {
		b.setR( Math.rint(a.r()) );
		b.setI( Math.rint(a.i()) );
	}

	@Override
	public boolean isNaN(ComplexFloat64Member a) {
		// true if either component is NaN
		return Double.isNaN(a.r()) || Double.isNaN(a.i());
	}

	@Override
	public boolean isInfinite(ComplexFloat64Member a) {
		// true if neither is NaN and one or both is Inf
		return !isNaN(a) && (Double.isInfinite(a.r()) || Double.isInfinite(a.i()));
	}

	@Override
	public void pow(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
		ComplexFloat64Member logA = new ComplexFloat64Member();
		ComplexFloat64Member bLogA = new ComplexFloat64Member();
		log(a, logA);
		multiply(b, logA, bLogA);
		exp(bLogA, c);
	}

	@Override
	public void random(ComplexFloat64Member a) {
		a.setR(rng.nextDouble());
		a.setI(rng.nextDouble());
	}

	@Override
	public void real(ComplexFloat64Member a, Float64Member b) {
		b.setV(a.r());
	}

	@Override
	public void unreal(ComplexFloat64Member a, ComplexFloat64Member b) {
		assign(a,b);
		b.setR(0);
	}
}
