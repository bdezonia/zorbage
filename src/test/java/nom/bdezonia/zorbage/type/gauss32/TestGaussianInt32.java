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
package nom.bdezonia.zorbage.type.gauss32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.gaussian32.GaussianInt32Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGaussianInt32 {

	@Test
	public void testStorageMethods() {
		
		IndexedDataSource<GaussianInt32Member> data = Storage.allocate(G.GAUSS32.construct(), 12000);
		GaussianInt32Member in = new GaussianInt32Member();
		GaussianInt32Member out = new GaussianInt32Member();
		in.setR(0);
		in.setI(0);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = 0; i < data.size(); i++) {
			in.setR((int)i);
			in.setI((int)(i+1));
			data.set(i, in);
			out.setR(in.r()-1);
			out.setI(in.i()-2);
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(0);
		in.setI(0);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR((int)i);
			in.setR((int)i+1);
			data.set(i, in);
			out.setR(in.r()-1);
			out.setI(in.i()-2);
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(0);
		in.setI(0);
		for (long i = data.size()-1; i >= 0; i--) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR((int)i);
			in.setI((int)i+1);
			data.set(i, in);
			out.setR(in.r()-1);
			out.setI(in.i()-2);
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
	}

	@Test
	public void testmathematicalMethods() {
		
		GaussianInt32Member a = G.GAUSS32.construct();
		
		assertEquals(0, a.r());
		assertEquals(0, a.i());
		assertTrue(G.GAUSS32.isZero().call(a));
		
		GaussianInt32Member b = G.GAUSS32.construct("{4,-3}");
		
		assertEquals(4, b.r());
		assertEquals(-3, b.i());
		assertFalse(G.GAUSS32.isZero().call(b));
		assertTrue(G.GAUSS32.isNotEqual().call(a, b));

		GaussianInt32Member c = G.GAUSS32.construct(b);
		
		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS32.isZero().call(b));
		assertTrue(G.GAUSS32.isEqual().call(b, c));
		assertFalse(G.GAUSS32.isNotEqual().call(b, c));
		
		G.GAUSS32.zero().call(c);
		
		assertEquals(0, c.r());
		assertEquals(0, c.i());
		assertTrue(G.GAUSS32.isZero().call(c));
		assertTrue(G.GAUSS32.isEqual().call(a, c));
		assertFalse(G.GAUSS32.isNotEqual().call(a, c));
		
		G.GAUSS32.unity().call(c);
		
		assertEquals(1, c.r());
		assertEquals(0, c.i());
		assertFalse(G.GAUSS32.isZero().call(c));
		assertFalse(G.GAUSS32.isEqual().call(b, c));
		assertTrue(G.GAUSS32.isNotEqual().call(b, c));
		
		G.GAUSS32.assign().call(b, c);

		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS32.isZero().call(c));
		assertTrue(G.GAUSS32.isEqual().call(b, c));
		assertFalse(G.GAUSS32.isNotEqual().call(b, c));

		a.setR(0);
		a.setI(0);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(1);
		a.setI(0);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-1);
		a.setI(0);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(-1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(1);
		a.setI(1);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(-1);
		a.setI(-1);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(1);
		a.setI(-1);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(-1);
		a.setI(1);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(2);
		a.setI(0);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(2);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(-2);
		a.setI(0);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(-2);
		assertTrue(G.GAUSS32.isEven().call(a));
		assertFalse(G.GAUSS32.isOdd().call(a));

		a.setR(1);
		a.setI(2);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(1);
		a.setI(-2);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(2);
		a.setI(1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(2);
		a.setI(-1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-1);
		a.setI(2);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-1);
		a.setI(-2);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-2);
		a.setI(1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-2);
		a.setI(-1);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(3);
		a.setI(0);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(3);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(-3);
		a.setI(0);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(0);
		a.setI(-3);
		assertFalse(G.GAUSS32.isEven().call(a));
		assertTrue(G.GAUSS32.isOdd().call(a));

		a.setR(4);
		a.setI(-2);
		b.setR(-1);
		b.setI(7);
		
		G.GAUSS32.add().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(5, c.i());

		G.GAUSS32.subtract().call(a, b, c);
		
		assertEquals(5, c.r());
		assertEquals(-9, c.i());
		
		a.setR(2);
		a.setI(-2);
		b.setR(3);
		b.setI(1);
		
		G.GAUSS32.multiply().call(a, b, c);
		
		assertEquals(8, c.r());
		assertEquals(-4, c.i());
		
		G.GAUSS32.multiply().call(a, a, b);
		G.GAUSS32.multiply().call(b, a, b);
		G.GAUSS32.power().call(3, a, c);
		
		assertFalse(G.GAUSS32.isZero().call(b));
		assertFalse(G.GAUSS32.isZero().call(c));
		assertTrue(G.GAUSS32.isEqual().call(b, c));
		
		a.setR(-2);
		a.setI(7);
		
		G.GAUSS32.negate().call(a, c);

		assertEquals(2, c.r());
		assertEquals(-7, c.i());

		GaussianInt32Member d = G.GAUSS32.construct();
		GaussianInt32Member m = G.GAUSS32.construct();
		
		a.setR(18);
		a.setI(-1);
		b.setR(11);
		b.setI(7);

		G.GAUSS32.zero().call(d);
		G.GAUSS32.zero().call(m);
		G.GAUSS32.div().call(a, b, d);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());

		G.GAUSS32.zero().call(d);
		G.GAUSS32.zero().call(m);
		G.GAUSS32.mod().call(a, b, m);
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());

		G.GAUSS32.zero().call(d);
		G.GAUSS32.zero().call(m);
		G.GAUSS32.divMod().call(a, b, d, m);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());
		
		HighPrecisionMember hp = G.HP.construct();
		UnsignedInt64Member num = G.UINT64.construct();
		
		a.setR(14);
		a.setI(-3);
		
		G.GAUSS32.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSS32.norm().call(a, num);

		assertEquals(BigInteger.valueOf(14*14 + (-3)*(-3)), num.v());

		a.setR(Integer.MIN_VALUE);
		a.setI(Integer.MIN_VALUE);
		
		G.GAUSS32.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Integer.MIN_VALUE).multiply(BigInteger.valueOf(Integer.MIN_VALUE)).multiply(BigInteger.valueOf(2)), num.v());

		a.setR(Integer.MAX_VALUE);
		a.setI(Integer.MAX_VALUE);
		
		G.GAUSS32.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(Integer.MAX_VALUE)).multiply(BigInteger.valueOf(2)), num.v());

		a.setR(-3);
		a.setI(7);
		
		G.GAUSS32.conjugate().call(a, b);
		
		assertEquals(-3, b.r());
		assertEquals(-7, b.i());

		GaussianInt32Member tol = G.GAUSS32.construct();
		
		a.setR(1);
		a.setI(2);
		b.setR(2);
		b.setI(4);

		tol.setR(-1);
		tol.setI(-1);
		try {
			G.GAUSS32.within().call(tol, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		tol.setR(0);
		tol.setI(0);
		assertFalse(G.GAUSS32.within().call(tol, a, b));

		tol.setR(1);
		tol.setI(1);
		assertFalse(G.GAUSS32.within().call(tol, a, b));

		tol.setR(2);
		tol.setI(2);
		assertTrue(G.GAUSS32.within().call(tol, a, b));

		a.setR(14);
		a.setI(12);
		b.setR(-4);
		b.setI(3);
		
		G.GAUSS32.multiply().call(a, b, c);
		G.GAUSS32.scale().call(a, b, d);
		
		assertTrue(G.GAUSS32.isEqual().call(c, d));

		a.setR(1);
		a.setI(2);
		
		G.GAUSS32.scaleByDouble().call(1.6, a, c);
		
		assertEquals(1, c.r());
		assertEquals(3, c.i());
		
		G.GAUSS32.scaleByDoubleAndRound().call(1.6, a, c);
		
		assertEquals(2, c.r());
		assertEquals(3, c.i());

		hp.setV(BigDecimal.valueOf(1.6));
		
		G.GAUSS32.scaleByHighPrec().call(hp, a, c);

		assertEquals(1, c.r());
		assertEquals(3, c.i());

		G.GAUSS32.scaleByHighPrecAndRound().call(hp, a, c);

		assertEquals(2, c.r());
		assertEquals(3, c.i());
		
		a.setR(23);
		a.setI(100);
		
		G.GAUSS32.scaleByRational().call(new RationalMember(3,4), a, c);
		
		assertEquals(17, c.r());
		assertEquals(75, c.i());

		a.setR(5);
		a.setI(-4);
		
		G.GAUSS32.scaleByOneHalf().call(1, a, c);
		
		assertEquals(2, c.r());
		assertEquals(-2, c.i());
		
		G.GAUSS32.scaleByOneHalf().call(2, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-1, c.i());
		
		a.setR(-1);
		a.setI(3);

		G.GAUSS32.scaleByTwo().call(1, a, c);
		
		assertEquals(-2, c.r());
		assertEquals(6, c.i());

		G.GAUSS32.scaleByTwo().call(2, a, c);
		
		assertEquals(-4, c.r());
		assertEquals(12, c.i());
		
		G.GAUSS32.random().call(a);
		int sames = 0;
		for (int i = 0; i < 100; i++) {
			G.GAUSS32.random().call(b);
			if (G.GAUSS32.isEqual().call(a, b))
				sames++;
		}
		assertFalse(sames > 10);

		// gcd : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		G.GAUSS32.power().call(4, a, b);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(9, c.r());
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(70);
		b.setI(0);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(-10, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(104);
		a.setI(0);
		b.setR(12);
		b.setI(0);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(18000);
		a.setI(0);
		b.setR(250);
		b.setI(0);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(250, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS32.gcd().call(b, a, c);
		
		assertEquals(250, c.r());
		assertEquals(0, c.i());
		
		// gcd : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS32.power().call(4, a, b);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(1, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(2, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS32.power().call(2, b, c);
		G.GAUSS32.multiply().call(a, b, b);
		G.GAUSS32.assign().call(c, a);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(5, c.i());
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS32.power().call(2, b, c);
		G.GAUSS32.multiply().call(a, b, b);
		G.GAUSS32.assign().call(c, a);
		
		G.GAUSS32.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());
		
		G.GAUSS32.gcd().call(b, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());

		// lcm : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-18, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(70);
		b.setI(0);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-840, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(104);
		a.setI(0);
		b.setR(12);
		b.setI(0);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-312, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(18000);
		a.setI(0);
		b.setR(250);
		b.setI(0);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(18000, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS32.lcm().call(b, a, c);
		
		assertEquals(18000, c.r());
		assertEquals(0, c.i());
		
		// lcm : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS32.power().call(4, a, b);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(6, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS32.power().call(2, b, c);
		G.GAUSS32.multiply().call(a, b, b);
		G.GAUSS32.assign().call(c, a);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());  // TODO : why negative
		assertEquals(-38, c.i());  // TODO : why negative
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS32.power().call(2, b, c);
		G.GAUSS32.multiply().call(a, b, b);
		G.GAUSS32.assign().call(c, a);
		
		G.GAUSS32.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
		
		G.GAUSS32.lcm().call(b, a, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
	}
}
