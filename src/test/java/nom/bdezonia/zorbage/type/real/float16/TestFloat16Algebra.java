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
package nom.bdezonia.zorbage.type.real.float16;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat16Algebra {

	@Test
	public void test() {
		Float16Member max = G.HLF.construct();
		Float16Member min = G.HLF.construct();
		G.HLF.maxBound().call(max);
		G.HLF.minBound().call(min);
		//System.out.println("Half precision float: max = "+ max.v() + " min = " + min.v());
		assertEquals(65504.0f, max.v(), 0);
		assertEquals(-65504.0f, min.v(), 0);
	}
	@Test
	public void testFloats() {
		  
		Float16Member a = G.HLF.construct("1.1");
		Float16Member b = G.HLF.construct("4.2");
		Float16Member sum = G.HLF.construct("99.3");

		G.HLF.add().call(a,b,sum);
		  
		assertEquals(5.3, sum.v(), 0.01);
	}
	
	@Test
	public void testSinCos() {
		//timingSinCos();
		Float16Member angle = new Float16Member();
		Float16Member s1 = new Float16Member();
		Float16Member c1 = new Float16Member();
		Float16Member s2 = new Float16Member();
		Float16Member c2 = new Float16Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV((float)a);
			G.HLF.sin().call(angle, s1);
			G.HLF.cos().call(angle, c1);
			G.HLF.sinAndCos().call(angle, s2, c2);
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
		Float16Member angle = new Float16Member();
		Float16Member s1 = new Float16Member();
		Float16Member c1 = new Float16Member();
		Float16Member s2 = new Float16Member();
		Float16Member c2 = new Float16Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV((float)a);
			G.HLF.sinh().call(angle, s1);
			G.HLF.cosh().call(angle, c1);
			G.HLF.sinhAndCosh().call(angle, s2, c2);
			// TODO: is this good enough? Any more precise and it fails.
			//assertEquals(s1.v(), s2.v(), 0.000000015);
			//assertEquals(c1.v(), c2.v(), 0.000000015);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}

	/*
	
	private void timingSinCos() {
		Float16Member angle = new Float16Member();
		Float16Member s1 = new Float16Member();
		Float16Member c1 = new Float16Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.HLF.sin(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.HLF.cos(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sin "+(q-p)+" cos "+(r-q));
	}

	private void timingSinhCosh() {
		Float16Member angle = new Float16Member();
		Float16Member s1 = new Float16Member();
		Float16Member c1 = new Float16Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.HLF.sinh(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.HLF.cosh(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sinh "+(q-p)+" cosh "+(r-q));
	}
	*/
	
	@Test
	public void divmod() {
		
		final double tol = 0.001;
		
		Float16Member a = G.HLF.construct();
		Float16Member b = G.HLF.construct();
		Float16Member d = G.HLF.construct();
		Float16Member m = G.HLF.construct();
		
		a.setV(0);
		b.setV(66);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(44);
		b.setV(66);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(44, m.v(), 0);

		a.setV(0.5f);
		b.setV(1);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(0.5, m.v(), tol);
		
		a.setV(-0.5f);
		b.setV(1);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-0.5, m.v(), tol);
		
		a.setV(3.3f);
		b.setV(2);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), tol);
		assertEquals(1.3, m.v(), tol);
		
		a.setV(-3.3f);
		b.setV(2);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(-1, d.v(), tol);
		assertEquals(-1.3, m.v(), tol);
		
		a.setV(-4);
		b.setV(-2);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(2, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(4);
		b.setV(2.3f);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), tol);
		assertEquals(1.7, m.v(), tol);

		a.setV(17);
		b.setV(3);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), tol);
		assertEquals(2, m.v(), tol);

		a.setV(-17);
		b.setV(3);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), tol);
		assertEquals(-2, m.v(), tol);

		a.setV(17);
		b.setV(-3);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(-5, d.v(), tol);
		assertEquals(2, m.v(), tol);

		a.setV(-17);
		b.setV(-3);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(5, d.v(), tol);
		assertEquals(-2, m.v(), tol);

		a.setV(3);
		b.setV(17);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(3, m.v(), tol);

		a.setV(-3);
		b.setV(17);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-3, m.v(), tol);

		a.setV(3);
		b.setV(-17);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(3, m.v(), tol);

		a.setV(-3);
		b.setV(-17);
		G.HLF.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-3, m.v(), tol);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.005;
		
		Float16Member a = G.HLF.construct();
		Float16Member b = G.HLF.construct();
		Float16Member c = G.HLF.construct();
		Float16Member d = G.HLF.construct();

		// G.CDBL.acos();
		a.setV((float)Math.PI/2);
		G.HLF.acos().call(a, b);
		assertEquals(FastMath.acos(Math.PI/2), b.v(), 0);
		
		// G.CDBL.acosh();
		
		// G.CDBL.acot();
		
		// G.CDBL.acoth();
		
		// G.CDBL.acsc();
		
		// G.CDBL.acsch();
		
		// G.CDBL.add();
		a.setV(1);
		b.setV(4);
		G.HLF.add().call(a, b, c);
		assertEquals(5, c.v(), 0);
		
		// G.CDBL.asec();
		
		// G.CDBL.asech();
		
		// G.CDBL.asin();
		a.setV((float)Math.PI/2);
		G.HLF.asin().call(a, b);
		assertEquals(FastMath.asin(Math.PI/2), b.v(), 0);
		
		// G.CDBL.asinh();
		
		// G.CDBL.assign();
		a.setV(66);
		G.HLF.assign().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.atan();
		a.setV((float)Math.PI/2);
		G.HLF.atan().call(a, b);
		assertEquals(FastMath.atan(Math.PI/2), b.v(), 0.0005);
		
		// G.CDBL.atanh();
		
		// G.CDBL.cbrt();
		a.setV(8);
		G.HLF.cbrt().call(a, b);
		assertEquals(2, b.v(), tol);
		
		// G.CDBL.conjugate();
		a.setV(66);
		G.HLF.conjugate().call(a, b);
		assertEquals(66,b.v(),0);
		
		// G.CDBL.construct();
		a = G.HLF.construct();
		assertEquals(0, a.v(), 0);

		// G.CDBL.construct("{14}");
		a = G.HLF.construct("{14}");
		assertEquals(14, a.v(), 0);
		
		// G.CDBL.construct(other);
		b = G.HLF.construct(a);
		assertEquals(14, b.v(), 0);
		
		// G.CDBL.cos();
		a.setV((float)Math.PI/2);
		G.HLF.cos().call(a, b);
		assertEquals(FastMath.cos(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cosh();
		a.setV((float)(Math.PI/2));
		G.HLF.cosh().call(a, b);
		assertEquals(FastMath.cosh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.cot();
		
		// G.CDBL.coth();
		
		// G.CDBL.csc();
		
		// G.CDBL.csch();
		
		// G.CDBL.divide();
		a = new Float16Member(7);
		b = new Float16Member(3);
		G.HLF.divide().call(a, b, c);
		assertEquals(7.0/3, c.v(), tol);

		// G.CDBL.E();
		G.HLF.E().call(a);
		assertEquals(Math.E, a.v(), tol);
		
		// G.CDBL.exp();
		a.setV(4);
		G.HLF.exp().call(a, b);
		assertEquals(FastMath.exp(4), b.v(), tol);
		
		// G.CDBL.expm1();
		a.setV(4);
		G.HLF.expm1().call(a, b);
		assertEquals(FastMath.expm1(4), b.v(), tol);
		
		// G.CDBL.infinite();
		a = G.HLF.construct();
		assertFalse(G.HLF.isInfinite().call(a));
		G.HLF.infinite().call(a);
		assertTrue(G.HLF.isInfinite().call(a));
		
		// G.CDBL.invert();
		a.setV(14);
		G.HLF.invert().call(a, b);
		G.HLF.unity().call(c);
		G.HLF.divide().call(c, a, c);
		assertEquals(c.v(), b.v(), tol);
		
		// G.CDBL.isEqual();
		a = new Float16Member(44);
		b = new Float16Member(12);
		assertFalse(G.HLF.isEqual().call(a, b));
		b = G.HLF.construct(a);
		assertTrue(G.HLF.isEqual().call(a, b));
		
		// G.CDBL.isInfinite();
		// tested by infinite() test above
		
		// G.CDBL.isNaN();
		// tested by nan() test below
		
		// G.CDBL.isNotEqual();
		a = new Float16Member(44);
		b = new Float16Member(12);
		assertTrue(G.HLF.isNotEqual().call(a, b));
		b = G.HLF.construct(a);
		assertFalse(G.HLF.isNotEqual().call(a, b));
		
		// G.CDBL.isZero();
		// tested by zero() test below
		
		// G.CDBL.log();
		a.setV((float)Math.PI/2);
		G.HLF.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.v(), tol);
		
		// G.CDBL.log1p();
		a.setV((float)(Math.PI/2));
		G.HLF.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.v(), tol);
		
		// G.CDBL.multiply();
		a = new Float16Member(-8);
		b = new Float16Member(-2);
		G.HLF.multiply().call(a, b, c);
		assertEquals(16, c.v(), 0);
		
		// G.CDBL.nan();
		a = new Float16Member(44);
		assertFalse(G.HLF.isNaN().call(a));
		G.HLF.nan().call(a);
		assertTrue(G.HLF.isNaN().call(a));
		
		// G.CDBL.negate();
		a = new Float16Member(44);
		G.HLF.negate().call(a, b);
		assertEquals(-44, b.v(), 0);
		
		// G.CDBL.norm();
		a = new Float16Member(3);
		G.HLF.norm().call(a, d);
		assertEquals(3,d.v(),0);
		
		// G.CDBL.PI();
		G.HLF.PI().call(a);
		assertEquals(Math.PI, a.v(), tol);
		
		// G.CDBL.pow();
		a = new Float16Member(-7);
		b = new Float16Member(2);
		G.HLF.pow().call(a, b, c);
		Float16Member t = G.HLF.construct();
		G.HLF.multiply().call(a, a, t);
		assertEquals(t.v(), c.v(), tol);
		
		// G.CDBL.power();
		a = new Float16Member(-7);
		G.HLF.power().call(2, a, b);
		G.HLF.multiply().call(a, a, t);
		assertEquals(t.v(), b.v(), tol);
		
		// G.CDBL.random();
		// TODO: not sure how to test
		G.HLF.random().call(a);
		
		// G.CDBL.real();
		a = new Float16Member(0.1f);
		G.HLF.real().call(a, d);
		assertEquals(0.1, d.v(), tol);
		
		// G.CDBL.round();
		d = new Float16Member(1);
		a = new Float16Member(3.3f);
		G.HLF.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.v(), 0);
		
		// G.CDBL.scale();
		a = new Float16Member(3);
		b = new Float16Member(3);
		G.HLF.scale().call(a, b, c);
		assertEquals(9, c.v(), 0);
		
		// G.CDBL.sec();
		
		// G.CDBL.sech();
		
		// G.CDBL.sin();
		a.setV((float)(Math.PI/2));
		G.HLF.sin().call(a, b);
		assertEquals(FastMath.sin(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinAndCos();
		
		// G.CDBL.sinc();
		
		// G.CDBL.sinch();
		
		// G.CDBL.sinchpi();
		
		// G.CDBL.sincpi();
		
		// G.CDBL.sinh();
		a.setV((float)(Math.PI/2));
		G.HLF.sinh().call(a, b);
		assertEquals(FastMath.sinh(Math.PI/2), b.v(), tol);
		
		// G.CDBL.sinhAndCosh();
		
		// G.CDBL.sqrt();
		a.setV(8);
		G.HLF.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.v(), tol);
		
		// G.CDBL.subtract();
		a.setV(1);
		b.setV(4);
		G.HLF.subtract().call(a, b, c);
		assertEquals(-3, c.v(), 0);
		
		// G.CDBL.tan();
		a.setV((float)(Math.PI/4));
		G.HLF.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/4), b.v(), tol);

		// G.CDBL.tanh();
		a.setV((float)(Math.PI/4));
		G.HLF.tanh().call(a, b);
		assertEquals(FastMath.tanh(Math.PI/4), b.v(), tol);

		// G.CDBL.unity();
		a = new Float16Member(0.1f);
		G.HLF.unity().call(a);
		assertEquals(1, a.v(), 0);
		
		// G.CDBL.unreal();
		a = new Float16Member(0.1f);
		G.HLF.unreal().call(a, b);
		assertEquals(0, b.v(), 0);
		
		// G.CDBL.zero();
		a = new Float16Member(0.1f);
		assertFalse(G.HLF.isZero().call(a));
		G.HLF.zero().call(a);
		assertTrue(G.HLF.isZero().call(a));
		
		// G.HLF.atan2();
		a.setV(-1);
		b.setV(4);
		G.HLF.atan2().call(a, b, c);
		assertEquals(Math.atan2(a.v(), b.v()), c.v(), tol);
		
		// G.HLF.compare();
		a.setV(4);
		b.setV(3);
		assertEquals(1, (int) G.HLF.compare().call(a, b));
		b.setV(4);
		assertEquals(0, (int) G.HLF.compare().call(a, b));
		b.setV(5);
		assertEquals(-1, (int) G.HLF.compare().call(a, b));
		
		// G.HLF.div();
		a.setV(43);
		b.setV(4);
		G.HLF.div().call(a, b, c);
		assertEquals(Math.floor(a.v()/b.v()), c.v(), 0);
		
		// G.HLF.isGreater();
		// G.HLF.isGreaterEqual();
		// G.HLF.isLess();
		// G.HLF.isLessEqual();

		a.setV(4);
		
		b.setV(3);
		assertTrue(G.HLF.isGreater().call(a, b));
		assertTrue(G.HLF.isGreaterEqual().call(a, b));
		assertFalse(G.HLF.isLess().call(a, b));
		assertFalse(G.HLF.isLessEqual().call(a, b));
		
		b.setV(4);
		assertFalse(G.HLF.isGreater().call(a, b));
		assertTrue(G.HLF.isGreaterEqual().call(a, b));
		assertFalse(G.HLF.isLess().call(a, b));
		assertTrue(G.HLF.isLessEqual().call(a, b));
		
		b.setV(5);
		assertFalse(G.HLF.isGreater().call(a, b));
		assertFalse(G.HLF.isGreaterEqual().call(a, b));
		assertTrue(G.HLF.isLess().call(a, b));
		assertTrue(G.HLF.isLessEqual().call(a, b));
		
		// G.HLF.log10();
		a.setV(7);
		G.HLF.log10().call(a, b);
		assertEquals(Math.log10(7), b.v(), tol);
		
		// G.HLF.max();
		// G.HLF.min();
		a.setV(1);
		
		b.setV(2);
		G.HLF.max().call(a, b, c);
		assertEquals(2, c.v(), 0);
		G.HLF.min().call(a, b, c);
		assertEquals(1, c.v(), 0);

		b.setV(-1);
		G.HLF.max().call(a, b, c);
		assertEquals(1, c.v(), 0);
		G.HLF.min().call(a, b, c);
		assertEquals(-1, c.v(), 0);

		// G.HLF.maxBound();
		G.HLF.maxBound().call(a);
		assertEquals(65504, a.v(), 0);
		
		// G.HLF.minBound();
		G.HLF.minBound().call(a);
		assertEquals(-65504, a.v(), 0);
		
		// G.HLF.mod();
		a.setV(4.2f);
		b.setV((float)-Math.PI);
		G.HLF.mod().call(a, b, c);
		assertEquals(a.v() % b.v(), c.v(), 0);
		
		// G.HLF.negInfinite();
		G.HLF.negInfinite().call(a);
		assertTrue(Double.isInfinite(a.v()));
		assertTrue(a.v() < 0);
		
		// G.HLF.signum();
		a.setV(-5);
		assertEquals(-1, G.HLF.signum().call(a), 0);
		a.setV(0);
		assertEquals(0, G.HLF.signum().call(a), 0);
		a.setV(5);
		assertEquals(1, G.HLF.signum().call(a), 0);
		
	}
	
	@Test
	public void testUlp() {

		/*
		try {
			Float16Member a = G.HLF.construct();
			File file = new File("/tmp/bloog");
			FileOutputStream ostr = new FileOutputStream(file);
			BufferedOutputStream bstr = new BufferedOutputStream(ostr);
			PrintStream pstr = new PrintStream(bstr);
			for (int i = 0; i < 500000; i++) {
				G.HLF.random().call(a);
				pstr.println(a.v());
			}
			//for (int i = 0; i < 65536; i++) {
			//	pstr.println((0xffff & i) + " prev " + (0xffff & Float16Util.prev((short) i)) + " next " + (0xffff & Float16Util.next((short) i)));
			//}
			pstr.close();
		} catch (Exception e) {
			System.out.println("barooga");
		}
		*/
	
		//System.out.println("ulp of    0 is " + Math.ulp(0.0f));
		//System.out.println("ulp of  NaN is " + Math.ulp(Float.NaN));
		//System.out.println("ulp of  Inf is " + Math.ulp(Float.POSITIVE_INFINITY));
		//System.out.println("ulp of -Inf is " + Math.ulp(Float.NEGATIVE_INFINITY));
		
		Float16Member a = G.HLF.construct();
		Float16Member b = G.HLF.construct();
		
		//a.setV(0); System.out.println("zero encV == "+a.encV());
		//a.setV(1); System.out.println("one  encV == "+a.encV());

		for (int i = 0; i <= 0xffff; i++) {
			float val = Float16Util.convertHFloatToFloat((short) i);
			a.setV(val);
			G.HLF.ulp().call(a, b);
			if (b.v() < 0)
				System.out.println("negative ulp for val " + i + "  encoded " + a.encV() + "  ulp " + b.v());
		}

		/*
		int odds = 0;
		int evens = 0;
		for (int i = 0; i < 100000000; i++) {
			G.HLF.random().call(a);
			float v = a.v();
			if (v < 0 || v >= 1)
				System.out.println("one is out of bounds");
			if ((a.encV() & 1) == 1)
				odds++;
			else
				evens++;
		}
		System.out.println("odds  " + odds);
		System.out.println("evens " + evens);
		Random rng = new Random();
		for (int i = 0; i < 100000000; i++) {
			float v = rng.nextFloat();
			if (v < 0 || v >= 1)
				System.out.println("one is out of bounds");
			if ((Float.floatToIntBits(v) & 1) == 1)
				odds++;
			else
				evens++;
		}
		System.out.println("odds  " + odds);
		System.out.println("evens " + evens);
		 */
	}
}
