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
package nom.bdezonia.zorbage.type.real.float32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat32Algebra {

	private final float TOL = 0.0000001f;
	
	@Test
	public void testFloats() {
		  
		Float32Member a = G.FLT.construct("1.1");
		Float32Member b = G.FLT.construct("4.2");
		Float32Member sum = G.FLT.construct("99.3");

		G.FLT.add().call(a, b, sum);
		  
		assertEquals(5.3, sum.v(), 0.000001);
	}
	
	@Test
	public void testSinCos() {
		//timingSinCos();
		Float32Member angle = new Float32Member();
		Float32Member s1 = new Float32Member();
		Float32Member c1 = new Float32Member();
		Float32Member s2 = new Float32Member();
		Float32Member c2 = new Float32Member();
		
		for (float a = (float)(-6*Math.PI); a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.sin().call(angle, s1);
			G.FLT.cos().call(angle, c1);
			G.FLT.sinAndCos().call(angle, s2, c2);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}
	
	@Test
	public void testSinhCosh() {
		//timingSinhCosh();
		Float32Member angle = new Float32Member();
		Float32Member s1 = new Float32Member();
		Float32Member c1 = new Float32Member();
		Float32Member s2 = new Float32Member();
		Float32Member c2 = new Float32Member();
		
		for (float a = (float)(-6*Math.PI); a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.sinh().call(angle, s1);
			G.FLT.cosh().call(angle, c1);
			G.FLT.sinhAndCosh().call(angle, s2, c2);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}

	/*
	
	private void timingSinCos() {
		Float32Member angle = new Float32Member();
		Float32Member s1 = new Float32Member();
		Float32Member c1 = new Float32Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.sin(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.cos(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sin "+(q-p)+" cos "+(r-q));
	}

	private void timingSinhCosh() {
		Float32Member angle = new Float32Member();
		Float32Member s1 = new Float32Member();
		Float32Member c1 = new Float32Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.sinh(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.FLT.cosh(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sinh "+(q-p)+" cosh "+(r-q));
	}
	*/
	
	@Test
	public void divmod() {
		
		Float32Member a = G.FLT.construct();
		Float32Member b = G.FLT.construct();
		Float32Member d = G.FLT.construct();
		Float32Member m = G.FLT.construct();
		
		a.setV(0);
		b.setV(66);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(44);
		b.setV(66);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(44, m.v(), 0);

		a.setV(0.5f);
		b.setV(1);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(0.5, m.v(), TOL);
		
		a.setV(-0.5f);
		b.setV(1);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(-0.5, m.v(), TOL);
		
		a.setV(3.3f);
		b.setV(2);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), TOL);
		assertEquals(1.3, m.v(), TOL);
		
		a.setV(-3.3f);
		b.setV(2);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(-1, d.v(), TOL);
		assertEquals(-1.3, m.v(), TOL);
		
		a.setV(-4);
		b.setV(-2);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(2, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(4);
		b.setV(2.3f);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), TOL);
		assertEquals(1.7, m.v(), TOL);

		a.setV(17);
		b.setV(3);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), TOL);
		assertEquals(2, m.v(), TOL);

		a.setV(-17);
		b.setV(3);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), TOL);
		assertEquals(-2, m.v(), TOL);

		a.setV(17);
		b.setV(-3);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), TOL);
		assertEquals(2, m.v(), TOL);

		a.setV(-17);
		b.setV(-3);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), TOL);
		assertEquals(-2, m.v(), TOL);

		a.setV(3);
		b.setV(17);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(3, m.v(), TOL);

		a.setV(-3);
		b.setV(17);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(-3, m.v(), TOL);

		a.setV(3);
		b.setV(-17);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(3, m.v(), TOL);

		a.setV(-3);
		b.setV(-17);
		G.FLT.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), TOL);
		assertEquals(-3, m.v(), TOL);
	}
	
	@Test
	public void mathematicalMethods() {
		
		float tol = 0.0001f;
		
		Float32Member a = G.FLT.construct();
		Float32Member b = G.FLT.construct();
		Float32Member c = G.FLT.construct();
		Float32Member d = G.FLT.construct();

		// G.CDBL.acos();
		a.setV((float)Math.PI/16);
		G.FLT.acos().call(a, b);
		assertEquals(Math.acos(Math.PI/16), b.v(), 0.000001);
		
		// G.CDBL.acosh();
		
		// G.CDBL.acot();
		
		// G.CDBL.acoth();
		
		// G.CDBL.acsc();
		
		// G.CDBL.acsch();
		
		// G.CDBL.add();
		a.setV(1);
		b.setV(4);
		G.FLT.add().call(a, b, c);
		assertEquals(5, c.v(), 0);
		
		// G.CDBL.asec();
		
		// G.CDBL.asech();
		
		// G.CDBL.asin();
		a.setV((float)Math.PI/16);
		G.FLT.asin().call(a, b);
		assertEquals(Math.asin(Math.PI/16), b.v(), 0.000001);
		
		// G.CDBL.asinh();
		
		// G.CDBL.assign();
		a.setV(66);
		G.FLT.assign().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.atan();
		a.setV((float)Math.PI/16);
		G.FLT.atan().call(a, b);
		assertEquals(Math.atan(Math.PI/16), b.v(), tol);
		
		// G.CDBL.atanh();
		
		// G.CDBL.cbrt();
		a.setV(8);
		G.FLT.cbrt().call(a, b);
		assertEquals(2, b.v(), tol);
		
		// G.CDBL.conjugate();
		a.setV(66);
		G.FLT.conjugate().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.construct();
		a = G.FLT.construct();
		assertEquals(0, a.v(), 0);

		// G.CDBL.construct("{14}");
		a = G.FLT.construct("{14}");
		assertEquals(14, a.v(), 0);
		
		// G.CDBL.construct(other);
		b = G.FLT.construct(a);
		assertEquals(14, b.v(), 0);
		
		// G.CDBL.cos();
		a.setV((float)Math.PI/2);
		G.FLT.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cosh();
		a.setV((float)Math.PI/2);
		G.FLT.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cot();
		
		// G.CDBL.coth();
		
		// G.CDBL.csc();
		
		// G.CDBL.csch();
		
		// G.CDBL.divide();
		a = new Float32Member(7);
		b = new Float32Member(3);
		G.FLT.divide().call(a, b, c);
		assertEquals(7.0/3, c.v(), tol);

		// G.CDBL.E();
		G.FLT.E().call(a);
		assertEquals(Math.E, a.v(), tol);
		
		// G.CDBL.exp();
		a.setV(4);
		G.FLT.exp().call(a, b);
		assertEquals(Math.exp(4), b.v(), tol);
		
		// G.CDBL.expm1();
		a.setV(4);
		G.FLT.expm1().call(a, b);
		assertEquals(Math.expm1(4), b.v(), tol);
		
		// G.CDBL.infinite();
		a = G.FLT.construct();
		assertFalse(G.FLT.isInfinite().call(a));
		G.FLT.infinite().call(a);
		assertTrue(G.FLT.isInfinite().call(a));
		
		// G.CDBL.invert();
		a.setV(14);
		G.FLT.invert().call(a, b);
		G.FLT.unity().call(c);
		G.FLT.divide().call(c, a, c);
		assertEquals(c.v(), b.v(), tol);
		
		// G.CDBL.isEqual();
		a = new Float32Member(44);
		b = new Float32Member(12);
		assertFalse(G.FLT.isEqual().call(a, b));
		b = G.FLT.construct(a);
		assertTrue(G.FLT.isEqual().call(a, b));
		
		// G.CDBL.isInfinite();
		// tested by infinite() test above
		
		// G.CDBL.isNaN();
		// tested by nan() test below
		
		// G.CDBL.isNotEqual();
		a = new Float32Member(44);
		b = new Float32Member(12);
		assertTrue(G.FLT.isNotEqual().call(a, b));
		b = G.FLT.construct(a);
		assertFalse(G.FLT.isNotEqual().call(a, b));
		
		// G.CDBL.isZero();
		// tested by zero() test below
		
		// G.CDBL.log();
		a.setV((float)Math.PI/2);
		G.FLT.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.v(), tol);
		
		// G.CDBL.log1p();
		a.setV((float)Math.PI/2);
		G.FLT.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.v(), tol);
		
		// G.CDBL.multiply();
		a = new Float32Member(-8);
		b = new Float32Member(-2);
		G.FLT.multiply().call(a, b, c);
		assertEquals(16, c.v(), 0);
		
		// G.CDBL.nan();
		a = new Float32Member(44);
		assertFalse(G.FLT.isNaN().call(a));
		G.FLT.nan().call(a);
		assertTrue(G.FLT.isNaN().call(a));
		
		// G.CDBL.negate();
		a = new Float32Member(44);
		G.FLT.negate().call(a, b);
		assertEquals(-44, b.v(), 0);
		
		// G.CDBL.norm();
		a = new Float32Member(3);
		G.FLT.norm().call(a, d);
		assertEquals(3,d.v(),0);
		
		// G.CDBL.PI();
		G.FLT.PI().call(a);
		assertEquals(Math.PI, a.v(), tol);
		
		// G.CDBL.pow();
		a = new Float32Member(-7);
		b = new Float32Member(2);
		G.FLT.pow().call(a, b, c);
		Float32Member t = G.FLT.construct();
		G.FLT.multiply().call(a, a, t);
		assertEquals(t.v(), c.v(), tol);
		
		// G.CDBL.power();
		a = new Float32Member(-7);
		G.FLT.power().call(2, a, b);
		G.FLT.multiply().call(a, a, t);
		assertEquals(t.v(), b.v(), tol);
		
		// G.CDBL.random();
		// TODO: not sure how to test
		G.FLT.random().call(a);
		
		// G.CDBL.real();
		a = new Float32Member(0.1f);
		G.FLT.real().call(a, d);
		assertEquals(0.1, d.v(), tol);
		
		// G.CDBL.round();
		d = new Float32Member(1);
		a = new Float32Member(3.3f);
		G.FLT.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.v(), 0);
		
		// G.CDBL.scale();
		a = new Float32Member(3);
		b = new Float32Member(3);
		G.FLT.scale().call(a, b, c);
		assertEquals(9, c.v(), 0);
		
		// G.CDBL.sec();
		
		// G.CDBL.sech();
		
		// G.CDBL.sin();
		a.setV((float)Math.PI/2);
		G.FLT.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinAndCos();
		
		// G.CDBL.sinc();
		
		// G.CDBL.sinch();
		
		// G.CDBL.sinchpi();
		
		// G.CDBL.sincpi();
		
		// G.CDBL.sinh();
		a.setV((float)Math.PI/2);
		G.FLT.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinhAndCosh();
		
		// G.CDBL.sqrt();
		a.setV(8);
		G.FLT.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.v(), tol);
		
		// G.CDBL.subtract();
		a.setV(1);
		b.setV(4);
		G.FLT.subtract().call(a, b, c);
		assertEquals(-3, c.v(), 0);
		
		// G.CDBL.tan();
		a.setV((float)(Math.PI/2));
		G.FLT.tan().call(a, b);
		assertEquals((float)Math.tan((float)(Math.PI/2)), b.v(), tol);

		// G.CDBL.tanh();
		a.setV((float)Math.PI/2);
		G.FLT.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/2), b.v(), tol);

		// G.CDBL.unity();
		a = new Float32Member(0.1f);
		G.FLT.unity().call(a);
		assertEquals(1, a.v(), 0);
		
		// G.CDBL.unreal();
		a = new Float32Member(0.1f);
		G.FLT.unreal().call(a, b);
		assertEquals(0, b.v(), 0);
		
		// G.CDBL.zero();
		a = new Float32Member(0.1f);
		assertFalse(G.FLT.isZero().call(a));
		G.FLT.zero().call(a);
		assertTrue(G.FLT.isZero().call(a));
		
		// G.FLT.atan2();
		a.setV(-1);
		b.setV(4);
		G.FLT.atan2().call(a, b, c);
		assertEquals(Math.atan2(a.v(), b.v()), c.v(), tol);
		
		// G.FLT.compare();
		a.setV(4);
		b.setV(3);
		assertEquals(1, (int) G.FLT.compare().call(a, b));
		b.setV(4);
		assertEquals(0, (int) G.FLT.compare().call(a, b));
		b.setV(5);
		assertEquals(-1, (int) G.FLT.compare().call(a, b));
		
		// G.FLT.copySign();
		a.setV(14);
		b.setV(-1);
		G.FLT.copySign().call(a, b, c);
		assertEquals(Math.copySign(a.v(), b.v()), c.v(), 0);
		
		// G.FLT.div();
		a.setV(43);
		b.setV(4);
		G.FLT.div().call(a, b, c);
		assertEquals(Math.floor(a.v()/b.v()), c.v(), 0);
		
		// G.FLT.getExponent();
		a.setV(4.712345677532f);
		assertEquals(Math.getExponent(a.v()), G.FLT.getExponent().call(a), 0);

		// G.FLT.IEEEremainder();
		a.setV(4.712345677532f);
		b.setV(4);
		G.FLT.IEEEremainder().call(a, b, c);
		assertEquals(Math.IEEEremainder(a.v(), b.v()), c.v(), 0);

		// G.FLT.isGreater();
		// G.FLT.isGreaterEqual();
		// G.FLT.isLess();
		// G.FLT.isLessEqual();

		a.setV(4);
		
		b.setV(3);
		assertTrue(G.FLT.isGreater().call(a, b));
		assertTrue(G.FLT.isGreaterEqual().call(a, b));
		assertFalse(G.FLT.isLess().call(a, b));
		assertFalse(G.FLT.isLessEqual().call(a, b));
		
		b.setV(4);
		assertFalse(G.FLT.isGreater().call(a, b));
		assertTrue(G.FLT.isGreaterEqual().call(a, b));
		assertFalse(G.FLT.isLess().call(a, b));
		assertTrue(G.FLT.isLessEqual().call(a, b));
		
		b.setV(5);
		assertFalse(G.FLT.isGreater().call(a, b));
		assertFalse(G.FLT.isGreaterEqual().call(a, b));
		assertTrue(G.FLT.isLess().call(a, b));
		assertTrue(G.FLT.isLessEqual().call(a, b));
		
		// G.FLT.log10();
		a.setV(7);
		G.FLT.log10().call(a, b);
		assertEquals(Math.log10(7), b.v(), tol);
		
		// G.FLT.max();
		// G.FLT.min();
		a.setV(1);
		
		b.setV(2);
		G.FLT.max().call(a, b, c);
		assertEquals(2, c.v(), 0);
		G.FLT.min().call(a, b, c);
		assertEquals(1, c.v(), 0);

		b.setV(-1);
		G.FLT.max().call(a, b, c);
		assertEquals(1, c.v(), 0);
		G.FLT.min().call(a, b, c);
		assertEquals(-1, c.v(), 0);

		// G.FLT.maxBound();
		G.FLT.maxBound().call(a);
		assertEquals(Float.MAX_VALUE, a.v(), 0);
		
		// G.FLT.minBound();
		G.FLT.minBound().call(a);
		assertEquals(-Float.MAX_VALUE, a.v(), 0);
		
		// G.FLT.pred();
		// G.FLT.succ();
		a.setV(4);
		G.FLT.pred().call(a, b);
		assertEquals(Math.nextDown(a.v()), b.v(), 0);
		G.FLT.succ().call(a, b);
		assertEquals(Math.nextUp(a.v()), b.v(), 0);

		// G.FLT.mod();
		a.setV(4.2f);
		b.setV((float)-Math.PI);
		G.FLT.mod().call(a, b, c);
		assertEquals(a.v() % b.v(), c.v(), 0);
		
		// G.FLT.negInfinite();
		G.FLT.negInfinite().call(a);
		assertTrue(Float.isInfinite(a.v()));
		assertTrue(a.v() < 0);
		
		// G.FLT.scalb();
		a.setV(47.3107f);
		G.FLT.scalb().call(3, a, b);
		assertEquals(Math.scalb(a.v(), 3), b.v(), 0);
		
		// G.FLT.signum();
		a.setV(-5);
		assertEquals(-1, G.FLT.signum().call(a), 0);
		a.setV(0);
		assertEquals(0, G.FLT.signum().call(a), 0);
		a.setV(5);
		assertEquals(1, G.FLT.signum().call(a), 0);
		
		// G.FLT.ulp();
		a.setV(-14.2f);
		G.FLT.ulp().call(a, b);
		assertEquals(Math.ulp(a.v()), b.v(), 0);
	}
}
