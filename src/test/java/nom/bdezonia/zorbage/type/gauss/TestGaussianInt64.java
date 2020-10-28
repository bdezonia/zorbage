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
package nom.bdezonia.zorbage.type.gauss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.gaussian.int64.GaussianInt64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGaussianInt64 {

	@Test
	public void testStorageMethods() {
		
		IndexedDataSource<GaussianInt64Member> data = Storage.allocate(G.GAUSS64.construct(), 12000);
		GaussianInt64Member in = new GaussianInt64Member();
		GaussianInt64Member out = new GaussianInt64Member();
		in.setR(0L);
		in.setI(0L);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = 0; i < data.size(); i++) {
			in.setR(i);
			in.setI((i+1));
			data.set(i, in);
			out.setR(in.r()-1);
			out.setI(in.i()-2);
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(0L);
		in.setI(0L);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR(i);
			in.setR(i+1);
			data.set(i, in);
			out.setR(in.r()-1);
			out.setI(in.i()-2);
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(0L);
		in.setI(0L);
		for (long i = data.size()-1; i >= 0; i--) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.r());
			assertEquals(0, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR(i);
			in.setI(i+1);
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
		
		GaussianInt64Member a = G.GAUSS64.construct();
		
		assertEquals(0, a.r());
		assertEquals(0, a.i());
		assertTrue(G.GAUSS64.isZero().call(a));
		
		GaussianInt64Member b = G.GAUSS64.construct("{4,-3}");
		
		assertEquals(4, b.r());
		assertEquals(-3, b.i());
		assertFalse(G.GAUSS64.isZero().call(b));
		assertTrue(G.GAUSS64.isNotEqual().call(a, b));

		GaussianInt64Member c = G.GAUSS64.construct(b);
		
		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS64.isZero().call(b));
		assertTrue(G.GAUSS64.isEqual().call(b, c));
		assertFalse(G.GAUSS64.isNotEqual().call(b, c));
		
		G.GAUSS64.zero().call(c);
		
		assertEquals(0, c.r());
		assertEquals(0, c.i());
		assertTrue(G.GAUSS64.isZero().call(c));
		assertTrue(G.GAUSS64.isEqual().call(a, c));
		assertFalse(G.GAUSS64.isNotEqual().call(a, c));
		
		G.GAUSS64.unity().call(c);
		
		assertEquals(1, c.r());
		assertEquals(0, c.i());
		assertFalse(G.GAUSS64.isZero().call(c));
		assertFalse(G.GAUSS64.isEqual().call(b, c));
		assertTrue(G.GAUSS64.isNotEqual().call(b, c));
		
		G.GAUSS64.assign().call(b, c);

		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS64.isZero().call(c));
		assertTrue(G.GAUSS64.isEqual().call(b, c));
		assertFalse(G.GAUSS64.isNotEqual().call(b, c));

		a.setR(0L);
		a.setI(0L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(1L);
		a.setI(0L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-1L);
		a.setI(0L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(-1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(1L);
		a.setI(1L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(-1L);
		a.setI(-1L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(1L);
		a.setI(-1L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(-1L);
		a.setI(1L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(2L);
		a.setI(0L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(2L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(-2L);
		a.setI(0L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(-2L);
		assertTrue(G.GAUSS64.isEven().call(a));
		assertFalse(G.GAUSS64.isOdd().call(a));

		a.setR(1L);
		a.setI(2L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(1L);
		a.setI(-2L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(2L);
		a.setI(1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(2L);
		a.setI(-1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-1L);
		a.setI(2L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-1L);
		a.setI(-2L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-2L);
		a.setI(1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-2L);
		a.setI(-1L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(3L);
		a.setI(0L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(3L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(-3L);
		a.setI(0L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(0L);
		a.setI(-3L);
		assertFalse(G.GAUSS64.isEven().call(a));
		assertTrue(G.GAUSS64.isOdd().call(a));

		a.setR(4L);
		a.setI(-2L);
		b.setR(-1L);
		b.setI(7L);
		
		G.GAUSS64.add().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(5, c.i());

		G.GAUSS64.subtract().call(a, b, c);
		
		assertEquals(5, c.r());
		assertEquals(-9, c.i());
		
		a.setR(2L);
		a.setI(-2L);
		b.setR(3L);
		b.setI(1L);
		
		G.GAUSS64.multiply().call(a, b, c);
		
		assertEquals(8, c.r());
		assertEquals(-4, c.i());
		
		G.GAUSS64.multiply().call(a, a, b);
		G.GAUSS64.multiply().call(b, a, b);
		G.GAUSS64.power().call(3, a, c);
		
		assertFalse(G.GAUSS64.isZero().call(b));
		assertFalse(G.GAUSS64.isZero().call(c));
		assertTrue(G.GAUSS64.isEqual().call(b, c));
		
		a.setR(-2L);
		a.setI(7L);
		
		G.GAUSS64.negate().call(a, c);

		assertEquals(2, c.r());
		assertEquals(-7, c.i());

		GaussianInt64Member d = G.GAUSS64.construct();
		GaussianInt64Member m = G.GAUSS64.construct();
		
		try {
			G.GAUSS64.div().call(a, b, d);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method div()");
			assertTrue(true);
		}

		try {
			G.GAUSS64.divMod().call(a, b, d, m);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method divMod()");
			assertTrue(true);
		}
		
		try {
			G.GAUSS64.mod().call(a, b, m);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method mod()");
			assertTrue(true);
		}
		
		try {
			G.GAUSS64.gcd().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method gcd()");
			assertTrue(true);
		}
		
		try {
			G.GAUSS64.lcm().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method lcm()");
			assertTrue(true);
		}
		
		HighPrecisionMember hp = G.HP.construct();
		UnboundedIntMember num = G.UNBOUND.construct();
		
		a.setR(14L);
		a.setI(-3L);
		
		G.GAUSS64.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSS64.norm().call(a, num);

		assertEquals((14*14 + (-3)*(-3)), num.v().longValue());

		a.setR(-3L);
		a.setI(7L);
		
		G.GAUSS64.conjugate().call(a, b);
		
		assertEquals(-3, b.r());
		assertEquals(-7, b.i());

		GaussianInt64Member tol = G.GAUSS64.construct();
		
		a.setR(1L);
		a.setI(2L);
		b.setR(2L);
		b.setI(4L);

		tol.setR(-1L);
		tol.setI(-1L);
		try {
			G.GAUSS64.within().call(tol, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		tol.setR(0L);
		tol.setI(0L);
		assertFalse(G.GAUSS64.within().call(tol, a, b));

		tol.setR(1L);
		tol.setI(1L);
		assertFalse(G.GAUSS64.within().call(tol, a, b));

		tol.setR(2L);
		tol.setI(2L);
		assertTrue(G.GAUSS64.within().call(tol, a, b));

		a.setR(14L);
		a.setI(12L);
		b.setR(-4L);
		b.setI(3L);
		
		G.GAUSS64.multiply().call(a, b, c);
		G.GAUSS64.scale().call(a, b, d);
		
		assertTrue(G.GAUSS64.isEqual().call(c, d));

		a.setR(1L);
		a.setI(2L);
		
		G.GAUSS64.scaleByDouble().call(1.6, a, c);
		
		assertEquals(1, c.r());
		assertEquals(3, c.i());
		
		G.GAUSS64.scaleByDoubleAndRound().call(1.6, a, c);
		
		assertEquals(2, c.r());
		assertEquals(3, c.i());

		hp.setV(BigDecimal.valueOf(1.6));
		
		G.GAUSS64.scaleByHighPrec().call(hp, a, c);

		assertEquals(1, c.r());
		assertEquals(3, c.i());

		G.GAUSS64.scaleByHighPrecAndRound().call(hp, a, c);

		assertEquals(2, c.r());
		assertEquals(3, c.i());
		
		a.setR(23L);
		a.setI(100L);
		
		G.GAUSS64.scaleByRational().call(new RationalMember(3,4), a, c);
		
		assertEquals(17, c.r());
		assertEquals(75, c.i());

		a.setR(5L);
		a.setI(-4L);
		
		G.GAUSS64.scaleByOneHalf().call(1, a, c);
		
		assertEquals(2, c.r());
		assertEquals(-2, c.i());
		
		G.GAUSS64.scaleByOneHalf().call(2, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-1, c.i());
		
		a.setR(-1L);
		a.setI(3L);

		G.GAUSS64.scaleByTwo().call(1, a, c);
		
		assertEquals(-2, c.r());
		assertEquals(6, c.i());

		G.GAUSS64.scaleByTwo().call(2, a, c);
		
		assertEquals(-4, c.r());
		assertEquals(12, c.i());
		
		G.GAUSS64.random().call(a);
		int sames = 0;
		for (int i = 0; i < 100; i++) {
			G.GAUSS64.random().call(b);
			if (G.GAUSS64.isEqual().call(a, b))
				sames++;
		}
		assertFalse(sames > 10);
	}
}
