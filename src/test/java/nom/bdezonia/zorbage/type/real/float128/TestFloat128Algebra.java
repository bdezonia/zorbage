/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.real.float128;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128Algebra {

	private static final BigDecimal TOL = BigDecimal.valueOf(0.000000001);
	
	@Test
	public void testFloats() {
		  
		Float128Member a = G.QUAD.construct("1.1");
		Float128Member b = G.QUAD.construct("4.2");
		Float128Member sum = G.QUAD.construct("99.3");

		G.QUAD.add().call(a, b, sum);
		  
		assertTrue(isNear(5.3, sum.v(), TOL));
	}
	
	@Test
	public void testSinCos() {
		//timingSinCos();
		Float128Member angle = new Float128Member();
		Float128Member s1 = new Float128Member();
		Float128Member c1 = new Float128Member();
		Float128Member s2 = new Float128Member();
		Float128Member c2 = new Float128Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(BigDecimal.valueOf(a));
			G.QUAD.sin().call(angle, s1);
			G.QUAD.cos().call(angle, c1);
			G.QUAD.sinAndCos().call(angle, s2, c2);
			assertTrue(s1.v().compareTo(s2.v()) == 0);
			assertTrue(c1.v().compareTo(c2.v()) == 0);
		}
	}
	
	@Test
	public void testSinhCosh() {
		//timingSinhCosh();
		Float128Member angle = new Float128Member();
		Float128Member s1 = new Float128Member();
		Float128Member c1 = new Float128Member();
		Float128Member s2 = new Float128Member();
		Float128Member c2 = new Float128Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(BigDecimal.valueOf(a));
			G.QUAD.sinh().call(angle, s1);
			G.QUAD.cosh().call(angle, c1);
			G.QUAD.sinhAndCosh().call(angle, s2, c2);
			assertTrue(s1.v().compareTo(s2.v()) == 0);
			assertTrue(c1.v().compareTo(c2.v()) == 0);
		}
	}
	
	@Test
	public void divmod() {
		
		Float128Member a = G.QUAD.construct();
		Float128Member b = G.QUAD.construct();
		Float128Member d = G.QUAD.construct();
		Float128Member m = G.QUAD.construct();
		
		a.setV(BigDecimal.valueOf(0));
		b.setV(BigDecimal.valueOf(66));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(0, m.v(), TOL));

		a.setV(BigDecimal.valueOf(44));
		b.setV(BigDecimal.valueOf(66));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(44, m.v(), TOL));

		a.setV(BigDecimal.valueOf(0.5));
		b.setV(BigDecimal.valueOf(1));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(0.5, m.v(), TOL));
		
		a.setV(BigDecimal.valueOf(-0.5));
		b.setV(BigDecimal.valueOf(1));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(-0.5, m.v(), TOL));
		
		a.setV(BigDecimal.valueOf(3.3));
		b.setV(BigDecimal.valueOf(2));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(1, d.v(), TOL));
		assertTrue(isNear(1.3, m.v(), TOL));
		
		a.setV(BigDecimal.valueOf(-3.3));
		b.setV(BigDecimal.valueOf(2));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(-1, d.v(), TOL));
		assertTrue(isNear(-1.3, m.v(), TOL));
		
		a.setV(BigDecimal.valueOf(-4));
		b.setV(BigDecimal.valueOf(-2));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(2, d.v(), TOL));
		assertTrue(isNear(0, m.v(), TOL));

		a.setV(BigDecimal.valueOf(4));
		b.setV(BigDecimal.valueOf(2.3));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(1, d.v(), TOL));
		assertTrue(isNear(1.7, m.v(), TOL));

		a.setV(BigDecimal.valueOf(17));
		b.setV(BigDecimal.valueOf(3));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(5, d.v(), TOL));
		assertTrue(isNear(2, m.v(), TOL));

		a.setV(BigDecimal.valueOf(-17));
		b.setV(BigDecimal.valueOf(3));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(-5, d.v(), TOL));
		assertTrue(isNear(-2, m.v(), TOL));

		a.setV(BigDecimal.valueOf(17));
		b.setV(BigDecimal.valueOf(-3));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(-5, d.v(), TOL));
		assertTrue(isNear(2, m.v(), TOL));

		a.setV(BigDecimal.valueOf(-17));
		b.setV(BigDecimal.valueOf(-3));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(5, d.v(), TOL));
		assertTrue(isNear(-2, m.v(), TOL));

		a.setV(BigDecimal.valueOf(3));
		b.setV(BigDecimal.valueOf(17));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(3, m.v(), TOL));

		a.setV(BigDecimal.valueOf(-3));
		b.setV(BigDecimal.valueOf(17));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(-3, m.v(), TOL));

		a.setV(BigDecimal.valueOf(3));
		b.setV(BigDecimal.valueOf(-17));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(3, m.v(), TOL));

		a.setV(BigDecimal.valueOf(-3));
		b.setV(BigDecimal.valueOf(-17));
		G.QUAD.divMod().call(a, b, d, m);
		assertTrue(isNear(0, d.v(), TOL));
		assertTrue(isNear(-3, m.v(), TOL));
	}
	
	@Test
	public void mathematicalMethods() {
		
		Float128Member a = G.QUAD.construct();
		Float128Member b = G.QUAD.construct();
		Float128Member c = G.QUAD.construct();
		Float128Member d = G.QUAD.construct();

		// G.QUAD.acos();
		a.setV(BigDecimal.valueOf(Math.cos(Math.PI/2)));
		G.QUAD.acos().call(a, b);
		assertTrue(isNear(Math.acos(a.v().doubleValue()), b.v(), TOL));
		
		// G.QUAD.acosh();
		
		// G.QUAD.acot();
		
		// G.QUAD.acoth();
		
		// G.QUAD.acsc();
		
		// G.QUAD.acsch();
		
		// G.QUAD.add();
		a.setV(BigDecimal.valueOf(1));
		b.setV(BigDecimal.valueOf(4));
		G.QUAD.add().call(a, b, c);
		assertTrue(isNear(5, c.v(), TOL));
		
		// G.QUAD.asec();
		
		// G.QUAD.asech();
		
		// G.QUAD.asin();
		a.setV(BigDecimal.valueOf(Math.cos(Math.PI/2)));
		G.QUAD.asin().call(a, b);
		assertTrue(isNear(Math.asin(a.v().doubleValue()), b.v(), TOL));
		
		// G.QUAD.asinh();
		
		// G.QUAD.assign();
		a.setV(BigDecimal.valueOf(66));
		G.QUAD.assign().call(a, b);
		assertTrue(isNear(66,b.v(),TOL));
		
		// G.QUAD.atan();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.atan().call(a, b);
		assertTrue(isNear(Math.atan(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.atanh();
		
		// G.QUAD.cbrt();
		a.setV(BigDecimal.valueOf(8));
		G.QUAD.cbrt().call(a, b);
		assertTrue(isNear(2, b.v(), TOL));
		
		// G.QUAD.conjugate();
		a.setV(BigDecimal.valueOf(66));
		G.QUAD.conjugate().call(a, b);
		assertTrue(isNear(66,b.v(),TOL));
		
		// G.QUAD.construct();
		a = G.QUAD.construct();
		assertTrue(isNear(0, a.v(), TOL));

		// G.QUAD.construct("{14}");
		a = G.QUAD.construct("{14}");
		assertTrue(isNear(14, a.v(), TOL));
		
		// G.QUAD.construct(other);
		b = G.QUAD.construct(a);
		assertTrue(isNear(14, b.v(), TOL));
		
		// G.QUAD.cos();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.cos().call(a, b);
		assertTrue(isNear(Math.cos(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.cosh();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.cosh().call(a, b);
		assertTrue(isNear(Math.cosh(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.cot();
		
		// G.QUAD.coth();
		
		// G.QUAD.csc();
		
		// G.QUAD.csch();
		
		// G.QUAD.divide();
		a = new Float128Member(BigDecimal.valueOf(7));
		b = new Float128Member(BigDecimal.valueOf(3));
		G.QUAD.divide().call(a, b, c);
		assertTrue(isNear(7.0/3, c.v(), TOL));

		// G.QUAD.E();
		G.QUAD.E().call(a);
		assertTrue(isNear(Math.E, a.v(), TOL));
		
		// G.QUAD.exp();
		a.setV(BigDecimal.valueOf(4));
		G.QUAD.exp().call(a, b);
		assertTrue(isNear(Math.exp(4), b.v(), TOL));
		
		// G.QUAD.infinite();
		a = G.QUAD.construct();
		assertFalse(G.QUAD.isInfinite().call(a));
		G.QUAD.infinite().call(a);
		assertTrue(G.QUAD.isInfinite().call(a));
		
		// G.QUAD.invert();
		a.setV(BigDecimal.valueOf(14));
		G.QUAD.invert().call(a, b);
		G.QUAD.unity().call(c);
		G.QUAD.divide().call(c, a, c);
		assertTrue(isNear(c.v().doubleValue(), b.v(), TOL));
		
		// G.QUAD.isEqual();
		a = new Float128Member(BigDecimal.valueOf(44));
		b = new Float128Member(BigDecimal.valueOf(12));
		assertFalse(G.QUAD.isEqual().call(a, b));
		b = G.QUAD.construct(a);
		assertTrue(G.QUAD.isEqual().call(a, b));
		
		// G.QUAD.isInfinite();
		// tested by infinite() test above
		
		// G.QUAD.isNaN();
		// tested by nan() test below
		
		// G.QUAD.isNotEqual();
		a = new Float128Member(BigDecimal.valueOf(44));
		b = new Float128Member(BigDecimal.valueOf(12));
		assertTrue(G.QUAD.isNotEqual().call(a, b));
		b = G.QUAD.construct(a);
		assertFalse(G.QUAD.isNotEqual().call(a, b));
		
		// G.QUAD.isZero();
		// tested by zero() test below
		
		// G.QUAD.log();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.log().call(a, b);
		assertTrue(isNear(Math.log(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.multiply();
		a = new Float128Member(BigDecimal.valueOf(-8));
		b = new Float128Member(BigDecimal.valueOf(-2));
		G.QUAD.multiply().call(a, b, c);
		assertTrue(isNear(16, c.v(), TOL));
		
		// G.QUAD.nan();
		a = new Float128Member(BigDecimal.valueOf(44));
		assertFalse(G.QUAD.isNaN().call(a));
		G.QUAD.nan().call(a);
		assertTrue(G.QUAD.isNaN().call(a));
		
		// G.QUAD.negate();
		a = new Float128Member(BigDecimal.valueOf(44));
		G.QUAD.negate().call(a, b);
		assertTrue(isNear(-44, b.v(), TOL));
		
		// G.QUAD.norm();
		a = new Float128Member(BigDecimal.valueOf(3));
		G.QUAD.norm().call(a, d);
		assertTrue(isNear(3,d.v(),TOL));
		
		// G.QUAD.PI();
		G.QUAD.PI().call(a);
		assertTrue(isNear(Math.PI, a.v(), TOL));
		
		// G.QUAD.pow();
		a = new Float128Member(BigDecimal.valueOf(-7));
		b = new Float128Member(BigDecimal.valueOf(2));
		G.QUAD.pow().call(a, b, c);
		Float128Member t = G.QUAD.construct();
		G.QUAD.multiply().call(a, a, t);
		assertTrue(isNear(t.v().doubleValue(), c.v(), TOL));
		
		// G.QUAD.power();
		a = new Float128Member(BigDecimal.valueOf(-7));
		G.QUAD.power().call(2, a, b);
		G.QUAD.multiply().call(a, a, t);
		assertTrue(isNear(t.v().doubleValue(), b.v(), TOL));
		
		// G.QUAD.random();
		G.QUAD.random().call(a);
		assertTrue(a.num.compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.num.compareTo(BigDecimal.ONE) < 0);
		
		// G.QUAD.real();
		a = new Float128Member(BigDecimal.valueOf(0.1));
		G.QUAD.real().call(a, d);
		assertTrue(isNear(0.1, d.v(), TOL));
		
		// G.QUAD.round();
		d = new Float128Member(BigDecimal.valueOf(1));
		a = new Float128Member(BigDecimal.valueOf(3.3));
		G.QUAD.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertTrue(isNear(3, b.v(), TOL));
		
		// G.QUAD.scale();
		a = new Float128Member(BigDecimal.valueOf(3));
		b = new Float128Member(BigDecimal.valueOf(3));
		G.QUAD.scale().call(a, b, c);
		assertTrue(isNear(9, c.v(), TOL));
		
		// G.QUAD.sec();
		
		// G.QUAD.sech();
		
		// G.QUAD.sin();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.sin().call(a, b);
		assertTrue(isNear(Math.sin(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.sinAndCos();
		
		// G.QUAD.sinc();
		
		// G.QUAD.sinch();
		
		// G.QUAD.sinchpi();
		
		// G.QUAD.sincpi();
		
		// G.QUAD.sinh();
		a.setV(BigDecimal.valueOf(Math.PI/2));
		G.QUAD.sinh().call(a, b);
		assertTrue(isNear(Math.sinh(Math.PI/2), b.v(), TOL));
		
		// G.QUAD.sinhAndCosh();
		
		// G.QUAD.sqrt();
		a.setV(BigDecimal.valueOf(8));
		G.QUAD.sqrt().call(a, b);
		assertTrue(isNear(Math.sqrt(8), b.v(), TOL));
		
		// G.QUAD.subtract();
		a.setV(BigDecimal.valueOf(1));
		b.setV(BigDecimal.valueOf(4));
		G.QUAD.subtract().call(a, b, c);
		assertTrue(isNear(-3, c.v(), TOL));
		
		// G.QUAD.tan();
		a.setV(BigDecimal.valueOf(Math.PI/16));
		G.QUAD.tan().call(a, b);
		assertTrue(isNear(Math.tan(Math.PI/16), b.v(), TOL));

		// G.QUAD.tanh();
		a.setV(BigDecimal.valueOf(Math.PI/16));
		G.QUAD.tanh().call(a, b);
		assertTrue(isNear(Math.tanh(Math.PI/16), b.v(), TOL));

		// G.QUAD.unity();
		a = new Float128Member(BigDecimal.valueOf(0.1));
		G.QUAD.unity().call(a);
		assertTrue(isNear(1, a.v(), TOL));
		
		// G.QUAD.unreal();
		a = new Float128Member(BigDecimal.valueOf(0.1));
		G.QUAD.unreal().call(a, b);
		assertTrue(isNear(0, b.v(), TOL));
		
		// G.QUAD.zero();
		a = new Float128Member(BigDecimal.valueOf(0.1));
		assertFalse(G.QUAD.isZero().call(a));
		G.QUAD.zero().call(a);
		assertTrue(G.QUAD.isZero().call(a));
		
		// G.QUAD.compare();
		a.setV(BigDecimal.valueOf(4));
		b.setV(BigDecimal.valueOf(3));
		assertEquals(1, (int) G.QUAD.compare().call(a, b));
		b.setV(BigDecimal.valueOf(4));
		assertEquals(0, (int) G.QUAD.compare().call(a, b));
		b.setV(BigDecimal.valueOf(5));
		assertEquals(-1, (int) G.QUAD.compare().call(a, b));
		
		// G.QUAD.copySign();
		a.setV(BigDecimal.valueOf(14));
		b.setV(BigDecimal.valueOf(-1));
		G.QUAD.copySign().call(a, b, c);
		assertTrue(isNear(Math.copySign(a.v().doubleValue(), b.v().doubleValue()), c.v(), TOL));
		
		// G.QUAD.div();
		a.setV(BigDecimal.valueOf(43));
		b.setV(BigDecimal.valueOf(4));
		G.QUAD.div().call(a, b, c);
		assertTrue(isNear(Math.floor(a.v().doubleValue()/b.v().doubleValue()), c.v(), TOL));
		
		// G.QUAD.getExponent();
		a.setV(BigDecimal.valueOf(4.712345677532));
		
		System.out.println(Math.getExponent(a.v().doubleValue()));
		System.out.println(G.QUAD.getExponent().call(a));
		
		assertTrue(Math.getExponent(a.v().doubleValue()) == G.QUAD.getExponent().call(a));

		// G.QUAD.isGreater();
		// G.QUAD.isGreaterEqual();
		// G.QUAD.isLess();
		// G.QUAD.isLessEqual();

		a.setV(BigDecimal.valueOf(4));
		
		b.setV(BigDecimal.valueOf(3));
		assertTrue(G.QUAD.isGreater().call(a, b));
		assertTrue(G.QUAD.isGreaterEqual().call(a, b));
		assertFalse(G.QUAD.isLess().call(a, b));
		assertFalse(G.QUAD.isLessEqual().call(a, b));
		
		b.setV(BigDecimal.valueOf(4));
		assertFalse(G.QUAD.isGreater().call(a, b));
		assertTrue(G.QUAD.isGreaterEqual().call(a, b));
		assertFalse(G.QUAD.isLess().call(a, b));
		assertTrue(G.QUAD.isLessEqual().call(a, b));
		
		b.setV(BigDecimal.valueOf(5));
		assertFalse(G.QUAD.isGreater().call(a, b));
		assertFalse(G.QUAD.isGreaterEqual().call(a, b));
		assertTrue(G.QUAD.isLess().call(a, b));
		assertTrue(G.QUAD.isLessEqual().call(a, b));
		
		// G.QUAD.max();
		// G.QUAD.min();
		a.setV(BigDecimal.valueOf(1));
		
		b.setV(BigDecimal.valueOf(2));
		G.QUAD.max().call(a, b, c);
		assertTrue(isNear(2, c.v(), TOL));
		G.QUAD.min().call(a, b, c);
		assertTrue(isNear(1, c.v(), TOL));

		b.setV(BigDecimal.valueOf(-1));
		G.QUAD.max().call(a, b, c);
		assertTrue(isNear(1, c.v(), TOL));
		G.QUAD.min().call(a, b, c);
		assertTrue(isNear(-1, c.v(), TOL));

		// G.QUAD.maxBound();
		G.QUAD.maxBound().call(a);
		assertTrue(Float128Member.MAX_NORMAL.compareTo(a.v()) == 0);
		
		// G.QUAD.minBound();
		G.QUAD.minBound().call(a);
		assertTrue(Float128Member.MAX_NORMAL.negate().compareTo(a.v()) == 0);
		
		// G.QUAD.pred();
		// G.QUAD.succ();
		// TODO can't test these two yet

		// G.QUAD.mod();
		a.setV(BigDecimal.valueOf(4.2));
		b.setV(BigDecimal.valueOf(-Math.PI));
		G.QUAD.mod().call(a, b, c);
		assertTrue(isNear(a.v().doubleValue() % b.v().doubleValue(), c.v(), TOL));
		
		// G.QUAD.infinite();
		G.QUAD.infinite().call(a);
		assertTrue(a.isPosInf());
		assertTrue(a.isInfinite());
		assertTrue(G.QUAD.signum().call(a) > 0);
		
		// G.QUAD.negInfinite();
		G.QUAD.negInfinite().call(a);
		assertTrue(a.isNegInf());
		assertTrue(a.isInfinite());
		assertTrue(G.QUAD.signum().call(a) < 0);
		
		// G.QUAD.scalb();
		a.setV(BigDecimal.valueOf(47.3107));
		G.QUAD.scalb().call(3, a, b);
		assertTrue(isNear(Math.scalb(a.v().doubleValue(), 3), b.v(), TOL));
		
		// G.QUAD.signum();
		a.setV(BigDecimal.valueOf(-5));
		assertEquals(-1, (int) G.QUAD.signum().call(a));
		a.setV(BigDecimal.valueOf(0));
		assertEquals(0, (int) G.QUAD.signum().call(a));
		a.setV(BigDecimal.valueOf(5));
		assertEquals(1, (int) G.QUAD.signum().call(a));
		
		// G.QUAD.ulp();
		// TODO can't test this one yet
	}
	
	private boolean isNear(double a, BigDecimal b, BigDecimal tol) {
		return BigDecimal.valueOf(a).subtract(b).abs().compareTo(tol) < 0;
	}
}
