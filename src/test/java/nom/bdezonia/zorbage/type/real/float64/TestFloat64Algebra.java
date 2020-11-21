/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.real.float64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Algebra {

	@Test
	public void testFloats() {
		  
		Float64Member a = G.DBL.construct("1.1");
		Float64Member b = G.DBL.construct("4.2");
		Float64Member sum = G.DBL.construct("99.3");

		G.DBL.add().call(a, b, sum);
		  
		assertEquals(5.3, sum.v(), 0.000000001);
	}
	
	@Test
	public void testSinCos() {
		//timingSinCos();
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		Float64Member s2 = new Float64Member();
		Float64Member c2 = new Float64Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sin().call(angle, s1);
			G.DBL.cos().call(angle, c1);
			G.DBL.sinAndCos().call(angle, s2, c2);
			// TODO: is this good enough? Any more precise and it fails.
			//assertEquals(s1.v(), s2.v(), 0.00000000000226);
			//assertEquals(c1.v(), c2.v(), 0.00000000000226);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}
	
	@Test
	public void testSinhCosh() {
		//timingSinhCosh();
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		Float64Member s2 = new Float64Member();
		Float64Member c2 = new Float64Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sinh().call(angle, s1);
			G.DBL.cosh().call(angle, c1);
			G.DBL.sinhAndCosh().call(angle, s2, c2);
			// TODO: is this good enough? Any more precise and it fails.
			//assertEquals(s1.v(), s2.v(), 0.000000015);
			//assertEquals(c1.v(), c2.v(), 0.000000015);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}

	/*
	
	private void timingSinCos() {
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sin(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.cos(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sin "+(q-p)+" cos "+(r-q));
	}

	private void timingSinhCosh() {
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sinh(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.cosh(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sinh "+(q-p)+" cosh "+(r-q));
	}
	*/
	
	@Test
	public void divmod() {
		
		final double tol = 0.000000000000001;
		
		Float64Member a = G.DBL.construct();
		Float64Member b = G.DBL.construct();
		Float64Member d = G.DBL.construct();
		Float64Member m = G.DBL.construct();
		
		a.setV(0);
		b.setV(66);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(44);
		b.setV(66);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(44, m.v(), 0);

		a.setV(0.5);
		b.setV(1);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(0.5, m.v(), tol);
		
		a.setV(-0.5);
		b.setV(1);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-0.5, m.v(), tol);
		
		a.setV(3.3);
		b.setV(2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), tol);
		assertEquals(1.3, m.v(), tol);
		
		a.setV(-3.3);
		b.setV(2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(-1, d.v(), tol);
		assertEquals(-1.3, m.v(), tol);
		
		a.setV(-4);
		b.setV(-2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(2, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(4);
		b.setV(2.3);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), tol);
		assertEquals(1.7, m.v(), tol);

		a.setV(17);
		b.setV(3);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), tol);
		assertEquals(2, m.v(), tol);

		a.setV(-17);
		b.setV(3);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), tol);
		assertEquals(-2, m.v(), tol);

		a.setV(17);
		b.setV(-3);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), tol);
		assertEquals(2, m.v(), tol);

		a.setV(-17);
		b.setV(-3);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), tol);
		assertEquals(-2, m.v(), tol);

		a.setV(3);
		b.setV(17);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(3, m.v(), tol);

		a.setV(-3);
		b.setV(17);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-3, m.v(), tol);

		a.setV(3);
		b.setV(-17);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(3, m.v(), tol);

		a.setV(-3);
		b.setV(-17);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-3, m.v(), tol);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00000000001;
		
		Float64Member a = G.DBL.construct();
		Float64Member b = G.DBL.construct();
		Float64Member c = G.DBL.construct();
		Float64Member d = G.DBL.construct();

		// G.CDBL.acos();
		a.setV(Math.PI/2);
		G.DBL.acos().call(a, b);
		assertEquals(FastMath.acos(Math.PI/2), b.v(), 0);
		
		// G.CDBL.acosh();
		
		// G.CDBL.acot();
		
		// G.CDBL.acoth();
		
		// G.CDBL.acsc();
		
		// G.CDBL.acsch();
		
		// G.CDBL.add();
		a.setV(1);
		b.setV(4);
		G.DBL.add().call(a, b, c);
		assertEquals(5, c.v(), 0);
		
		// G.CDBL.asec();
		
		// G.CDBL.asech();
		
		// G.CDBL.asin();
		a.setV(Math.PI/2);
		G.DBL.asin().call(a, b);
		assertEquals(FastMath.asin(Math.PI/2), b.v(), 0);
		
		// G.CDBL.asinh();
		
		// G.CDBL.assign();
		a.setV(66);
		G.DBL.assign().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.atan();
		a.setV(Math.PI/2);
		G.DBL.atan().call(a, b);
		assertEquals(FastMath.atan(Math.PI/2), b.v(), 0);
		
		// G.CDBL.atanh();
		
		// G.CDBL.cbrt();
		a.setV(8);
		G.DBL.cbrt().call(a, b);
		assertEquals(2, b.v(), tol);
		
		// G.CDBL.conjugate();
		a.setV(66);
		G.DBL.conjugate().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.construct();
		a = G.DBL.construct();
		assertEquals(0, a.v(), 0);

		// G.CDBL.construct("{14}");
		a = G.DBL.construct("{14}");
		assertEquals(14, a.v(), 0);
		
		// G.CDBL.construct(other);
		b = G.DBL.construct(a);
		assertEquals(14, b.v(), 0);
		
		// G.CDBL.cos();
		a.setV(Math.PI/2);
		G.DBL.cos().call(a, b);
		assertEquals(FastMath.cos(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cosh();
		a.setV(Math.PI/2);
		G.DBL.cosh().call(a, b);
		assertEquals(FastMath.cosh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cot();
		
		// G.CDBL.coth();
		
		// G.CDBL.csc();
		
		// G.CDBL.csch();
		
		// G.CDBL.divide();
		a = new Float64Member(7);
		b = new Float64Member(3);
		G.DBL.divide().call(a, b, c);
		assertEquals(7.0/3, c.v(), 0);

		// G.CDBL.E();
		G.DBL.E().call(a);
		assertEquals(Math.E, a.v(), 0);
		
		// G.CDBL.exp();
		a.setV(4);
		G.DBL.exp().call(a, b);
		assertEquals(FastMath.exp(4), b.v(), tol);
		
		// G.CDBL.expm1();
		a.setV(4);
		G.DBL.expm1().call(a, b);
		assertEquals(FastMath.expm1(4), b.v(), tol);
		
		// G.CDBL.infinite();
		a = G.DBL.construct();
		assertFalse(G.DBL.isInfinite().call(a));
		G.DBL.infinite().call(a);
		assertTrue(G.DBL.isInfinite().call(a));
		
		// G.CDBL.invert();
		a.setV(14);
		G.DBL.invert().call(a, b);
		G.DBL.unity().call(c);
		G.DBL.divide().call(c, a, c);
		assertEquals(c.v(), b.v(), tol);
		
		// G.CDBL.isEqual();
		a = new Float64Member(44);
		b = new Float64Member(12);
		assertFalse(G.DBL.isEqual().call(a, b));
		b = G.DBL.construct(a);
		assertTrue(G.DBL.isEqual().call(a, b));
		
		// G.CDBL.isInfinite();
		// tested by infinite() test above
		
		// G.CDBL.isNaN();
		// tested by nan() test below
		
		// G.CDBL.isNotEqual();
		a = new Float64Member(44);
		b = new Float64Member(12);
		assertTrue(G.DBL.isNotEqual().call(a, b));
		b = G.DBL.construct(a);
		assertFalse(G.DBL.isNotEqual().call(a, b));
		
		// G.CDBL.isZero();
		// tested by zero() test below
		
		// G.CDBL.log();
		a.setV(Math.PI/2);
		G.DBL.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.v(), tol);
		
		// G.CDBL.log1p();
		a.setV(Math.PI/2);
		G.DBL.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.v(), tol);
		
		// G.CDBL.multiply();
		a = new Float64Member(-8);
		b = new Float64Member(-2);
		G.DBL.multiply().call(a, b, c);
		assertEquals(16, c.v(), 0);
		
		// G.CDBL.nan();
		a = new Float64Member(44);
		assertFalse(G.DBL.isNaN().call(a));
		G.DBL.nan().call(a);
		assertTrue(G.DBL.isNaN().call(a));
		
		// G.CDBL.negate();
		a = new Float64Member(44);
		G.DBL.negate().call(a, b);
		assertEquals(-44, b.v(), 0);
		
		// G.CDBL.norm();
		a = new Float64Member(3);
		G.DBL.norm().call(a, d);
		assertEquals(3,d.v(),0);
		
		// G.CDBL.PI();
		G.DBL.PI().call(a);
		assertEquals(Math.PI, a.v(), 0);
		
		// G.CDBL.pow();
		a = new Float64Member(-7);
		b = new Float64Member(2);
		G.DBL.pow().call(a, b, c);
		Float64Member t = G.DBL.construct();
		G.DBL.multiply().call(a, a, t);
		assertEquals(t.v(), c.v(), tol);
		
		// G.CDBL.power();
		a = new Float64Member(-7);
		G.DBL.power().call(2, a, b);
		G.DBL.multiply().call(a, a, t);
		assertEquals(t.v(), b.v(), tol);
		
		// G.CDBL.random();
		// TODO: not sure how to test
		G.DBL.random().call(a);
		
		// G.CDBL.real();
		a = new Float64Member(0.1);
		G.DBL.real().call(a, d);
		assertEquals(0.1, d.v(), 0);
		
		// G.CDBL.round();
		d = new Float64Member(1);
		a = new Float64Member(3.3);
		G.DBL.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.v(), 0);
		
		// G.CDBL.scale();
		a = new Float64Member(3);
		b = new Float64Member(3);
		G.DBL.scale().call(a, b, c);
		assertEquals(9, c.v(), 0);
		
		// G.CDBL.sec();
		
		// G.CDBL.sech();
		
		// G.CDBL.sin();
		a.setV(Math.PI/2);
		G.DBL.sin().call(a, b);
		assertEquals(FastMath.sin(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinAndCos();
		
		// G.CDBL.sinc();
		
		// G.CDBL.sinch();
		
		// G.CDBL.sinchpi();
		
		// G.CDBL.sincpi();
		
		// G.CDBL.sinh();
		a.setV(Math.PI/2);
		G.DBL.sinh().call(a, b);
		assertEquals(FastMath.sinh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinhAndCosh();
		
		// G.CDBL.sqrt();
		a.setV(8);
		G.DBL.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.v(), tol);
		
		// G.CDBL.subtract();
		a.setV(1);
		b.setV(4);
		G.DBL.subtract().call(a, b, c);
		assertEquals(-3, c.v(), 0);
		
		// G.CDBL.tan();
		a.setV(Math.PI/2);
		G.DBL.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/2), b.v(), tol);

		// G.CDBL.tanh();
		a.setV(Math.PI/2);
		G.DBL.tanh().call(a, b);
		assertEquals(FastMath.tanh(Math.PI/2), b.v(), tol);

		// G.CDBL.unity();
		a = new Float64Member(0.1);
		G.DBL.unity().call(a);
		assertEquals(1, a.v(), 0);
		
		// G.CDBL.unreal();
		a = new Float64Member(0.1);
		G.DBL.unreal().call(a, b);
		assertEquals(0, b.v(), 0);
		
		// G.CDBL.zero();
		a = new Float64Member(0.1);
		assertFalse(G.DBL.isZero().call(a));
		G.DBL.zero().call(a);
		assertTrue(G.DBL.isZero().call(a));
		
		// G.DBL.atan2();
		a.setV(-1);
		b.setV(4);
		G.DBL.atan2().call(a, b, c);
		assertEquals(Math.atan2(a.v(), b.v()), c.v(), 0);
		
		// G.DBL.compare();
		a.setV(4);
		b.setV(3);
		assertEquals(1, (int) G.DBL.compare().call(a, b));
		b.setV(4);
		assertEquals(0, (int) G.DBL.compare().call(a, b));
		b.setV(5);
		assertEquals(-1, (int) G.DBL.compare().call(a, b));
		
		// G.DBL.copySign();
		a.setV(14);
		b.setV(-1);
		G.DBL.copySign().call(a, b, c);
		assertEquals(Math.copySign(a.v(), b.v()), c.v(), 0);
		
		// G.DBL.div();
		a.setV(43);
		b.setV(4);
		G.DBL.div().call(a, b, c);
		assertEquals(Math.floor(a.v()/b.v()), c.v(), 0);
		
		// G.DBL.getExponent();
		a.setV(4.712345677532);
		assertEquals(Math.getExponent(a.v()), G.DBL.getExponent().call(a), 0);

		// G.DBL.IEEEremainder();
		a.setV(4.712345677532);
		b.setV(4);
		G.DBL.IEEEremainder().call(a, b, c);
		assertEquals(Math.IEEEremainder(a.v(), b.v()), c.v(), 0);

		// G.DBL.isGreater();
		// G.DBL.isGreaterEqual();
		// G.DBL.isLess();
		// G.DBL.isLessEqual();

		a.setV(4);
		
		b.setV(3);
		assertTrue(G.DBL.isGreater().call(a, b));
		assertTrue(G.DBL.isGreaterEqual().call(a, b));
		assertFalse(G.DBL.isLess().call(a, b));
		assertFalse(G.DBL.isLessEqual().call(a, b));
		
		b.setV(4);
		assertFalse(G.DBL.isGreater().call(a, b));
		assertTrue(G.DBL.isGreaterEqual().call(a, b));
		assertFalse(G.DBL.isLess().call(a, b));
		assertTrue(G.DBL.isLessEqual().call(a, b));
		
		b.setV(5);
		assertFalse(G.DBL.isGreater().call(a, b));
		assertFalse(G.DBL.isGreaterEqual().call(a, b));
		assertTrue(G.DBL.isLess().call(a, b));
		assertTrue(G.DBL.isLessEqual().call(a, b));
		
		// G.DBL.log10();
		a.setV(7);
		G.DBL.log10().call(a, b);
		assertEquals(Math.log10(7), b.v(), 0);
		
		// G.DBL.max();
		// G.DBL.min();
		a.setV(1);
		
		b.setV(2);
		G.DBL.max().call(a, b, c);
		assertEquals(2, c.v(), 0);
		G.DBL.min().call(a, b, c);
		assertEquals(1, c.v(), 0);

		b.setV(-1);
		G.DBL.max().call(a, b, c);
		assertEquals(1, c.v(), 0);
		G.DBL.min().call(a, b, c);
		assertEquals(-1, c.v(), 0);

		// G.DBL.maxBound();
		G.DBL.maxBound().call(a);
		assertEquals(Double.MAX_VALUE, a.v(), 0);
		
		// G.DBL.minBound();
		G.DBL.minBound().call(a);
		assertEquals(-Double.MAX_VALUE, a.v(), 0);
		
		// G.DBL.pred();
		// G.DBL.succ();
		a.setV(4);
		G.DBL.pred().call(a, b);
		assertEquals(Math.nextDown(a.v()), b.v(), 0);
		G.DBL.succ().call(a, b);
		assertEquals(Math.nextUp(a.v()), b.v(), 0);

		// G.DBL.mod();
		a.setV(4.2);
		b.setV(-Math.PI);
		G.DBL.mod().call(a, b, c);
		assertEquals(a.v() % b.v(), c.v(), 0);
		
		// G.DBL.negInfinite();
		G.DBL.negInfinite().call(a);
		assertTrue(Double.isInfinite(a.v()));
		assertTrue(a.v() < 0);
		
		// G.DBL.scalb();
		a.setV(47.3107);
		G.DBL.scalb().call(3, a, b);
		assertEquals(Math.scalb(a.v(), 3), b.v(), 0);
		
		// G.DBL.signum();
		a.setV(-5);
		assertEquals(-1, G.DBL.signum().call(a), 0);
		a.setV(0);
		assertEquals(0, G.DBL.signum().call(a), 0);
		a.setV(5);
		assertEquals(1, G.DBL.signum().call(a), 0);
		
		// G.DBL.ulp();
		a.setV(-14.2);
		G.DBL.ulp().call(a, b);
		assertEquals(Math.ulp(a.v()), b.v(), 0);
	}
}
