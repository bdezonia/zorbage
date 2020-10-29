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
package nom.bdezonia.zorbage.type.gauss16;

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
import nom.bdezonia.zorbage.type.gaussian16.GaussianInt16Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGaussianInt16 {

	@Test
	public void testStorageMethods() {
		
		IndexedDataSource<GaussianInt16Member> data = Storage.allocate(G.GAUSS16.construct(), 12000);
		GaussianInt16Member in = new GaussianInt16Member();
		GaussianInt16Member out = new GaussianInt16Member();
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
		
		GaussianInt16Member a = G.GAUSS16.construct();
		
		assertEquals(0, a.r());
		assertEquals(0, a.i());
		assertTrue(G.GAUSS16.isZero().call(a));
		
		GaussianInt16Member b = G.GAUSS16.construct("{4,-3}");
		
		assertEquals(4, b.r());
		assertEquals(-3, b.i());
		assertFalse(G.GAUSS16.isZero().call(b));
		assertTrue(G.GAUSS16.isNotEqual().call(a, b));

		GaussianInt16Member c = G.GAUSS16.construct(b);
		
		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS16.isZero().call(b));
		assertTrue(G.GAUSS16.isEqual().call(b, c));
		assertFalse(G.GAUSS16.isNotEqual().call(b, c));
		
		G.GAUSS16.zero().call(c);
		
		assertEquals(0, c.r());
		assertEquals(0, c.i());
		assertTrue(G.GAUSS16.isZero().call(c));
		assertTrue(G.GAUSS16.isEqual().call(a, c));
		assertFalse(G.GAUSS16.isNotEqual().call(a, c));
		
		G.GAUSS16.unity().call(c);
		
		assertEquals(1, c.r());
		assertEquals(0, c.i());
		assertFalse(G.GAUSS16.isZero().call(c));
		assertFalse(G.GAUSS16.isEqual().call(b, c));
		assertTrue(G.GAUSS16.isNotEqual().call(b, c));
		
		G.GAUSS16.assign().call(b, c);

		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS16.isZero().call(c));
		assertTrue(G.GAUSS16.isEqual().call(b, c));
		assertFalse(G.GAUSS16.isNotEqual().call(b, c));

		a.setR(0);
		a.setI(0);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(1);
		a.setI(0);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-1);
		a.setI(0);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(-1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(1);
		a.setI(1);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(-1);
		a.setI(-1);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(1);
		a.setI(-1);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(-1);
		a.setI(1);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(2);
		a.setI(0);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(2);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(-2);
		a.setI(0);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(-2);
		assertTrue(G.GAUSS16.isEven().call(a));
		assertFalse(G.GAUSS16.isOdd().call(a));

		a.setR(1);
		a.setI(2);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(1);
		a.setI(-2);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(2);
		a.setI(1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(2);
		a.setI(-1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-1);
		a.setI(2);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-1);
		a.setI(-2);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-2);
		a.setI(1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-2);
		a.setI(-1);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(3);
		a.setI(0);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(3);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(-3);
		a.setI(0);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(0);
		a.setI(-3);
		assertFalse(G.GAUSS16.isEven().call(a));
		assertTrue(G.GAUSS16.isOdd().call(a));

		a.setR(4);
		a.setI(-2);
		b.setR(-1);
		b.setI(7);
		
		G.GAUSS16.add().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(5, c.i());

		G.GAUSS16.subtract().call(a, b, c);
		
		assertEquals(5, c.r());
		assertEquals(-9, c.i());
		
		a.setR(2);
		a.setI(-2);
		b.setR(3);
		b.setI(1);
		
		G.GAUSS16.multiply().call(a, b, c);
		
		assertEquals(8, c.r());
		assertEquals(-4, c.i());
		
		G.GAUSS16.multiply().call(a, a, b);
		G.GAUSS16.multiply().call(b, a, b);
		G.GAUSS16.power().call(3, a, c);
		
		assertFalse(G.GAUSS16.isZero().call(b));
		assertFalse(G.GAUSS16.isZero().call(c));
		assertTrue(G.GAUSS16.isEqual().call(b, c));
		
		a.setR(-2);
		a.setI(7);
		
		G.GAUSS16.negate().call(a, c);

		assertEquals(2, c.r());
		assertEquals(-7, c.i());

		GaussianInt16Member d = G.GAUSS16.construct();
		GaussianInt16Member m = G.GAUSS16.construct();
		
		a.setR(18);
		a.setI(-1);
		b.setR(11);
		b.setI(7);

		G.GAUSS16.zero().call(d);
		G.GAUSS16.zero().call(m);
		G.GAUSS16.div().call(a, b, d);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());

		G.GAUSS16.zero().call(d);
		G.GAUSS16.zero().call(m);
		G.GAUSS16.mod().call(a, b, m);
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());

		G.GAUSS16.zero().call(d);
		G.GAUSS16.zero().call(m);
		G.GAUSS16.divMod().call(a, b, d, m);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());
		
		try {
			G.GAUSS16.gcd().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method gcd()");
			assertTrue(true);
		}
		
		try {
			G.GAUSS16.lcm().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method lcm()");
			assertTrue(true);
		}
		
		HighPrecisionMember hp = G.HP.construct();
		SignedInt32Member num = G.INT32.construct();
		
		a.setR(14);
		a.setI(-3);
		
		G.GAUSS16.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSS16.norm().call(a, num);

		assertEquals((14*14 + (-3)*(-3)), num.v());

		a.setR(-3);
		a.setI(7);
		
		G.GAUSS16.conjugate().call(a, b);
		
		assertEquals(-3, b.r());
		assertEquals(-7, b.i());

		GaussianInt16Member tol = G.GAUSS16.construct();
		
		a.setR(1);
		a.setI(2);
		b.setR(2);
		b.setI(4);

		tol.setR(-1);
		tol.setI(-1);
		try {
			G.GAUSS16.within().call(tol, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		tol.setR(0);
		tol.setI(0);
		assertFalse(G.GAUSS16.within().call(tol, a, b));

		tol.setR(1);
		tol.setI(1);
		assertFalse(G.GAUSS16.within().call(tol, a, b));

		tol.setR(2);
		tol.setI(2);
		assertTrue(G.GAUSS16.within().call(tol, a, b));

		a.setR(14);
		a.setI(12);
		b.setR(-4);
		b.setI(3);
		
		G.GAUSS16.multiply().call(a, b, c);
		G.GAUSS16.scale().call(a, b, d);
		
		assertTrue(G.GAUSS16.isEqual().call(c, d));

		a.setR(1);
		a.setI(2);
		
		G.GAUSS16.scaleByDouble().call(1.6, a, c);
		
		assertEquals(1, c.r());
		assertEquals(3, c.i());
		
		G.GAUSS16.scaleByDoubleAndRound().call(1.6, a, c);
		
		assertEquals(2, c.r());
		assertEquals(3, c.i());

		hp.setV(BigDecimal.valueOf(1.6));
		
		G.GAUSS16.scaleByHighPrec().call(hp, a, c);

		assertEquals(1, c.r());
		assertEquals(3, c.i());

		G.GAUSS16.scaleByHighPrecAndRound().call(hp, a, c);

		assertEquals(2, c.r());
		assertEquals(3, c.i());
		
		a.setR(23);
		a.setI(100);
		
		G.GAUSS16.scaleByRational().call(new RationalMember(3,4), a, c);
		
		assertEquals(17, c.r());
		assertEquals(75, c.i());

		a.setR(5);
		a.setI(-4);
		
		G.GAUSS16.scaleByOneHalf().call(1, a, c);
		
		assertEquals(2, c.r());
		assertEquals(-2, c.i());
		
		G.GAUSS16.scaleByOneHalf().call(2, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-1, c.i());
		
		a.setR(-1);
		a.setI(3);

		G.GAUSS16.scaleByTwo().call(1, a, c);
		
		assertEquals(-2, c.r());
		assertEquals(6, c.i());

		G.GAUSS16.scaleByTwo().call(2, a, c);
		
		assertEquals(-4, c.r());
		assertEquals(12, c.i());
		
		G.GAUSS16.random().call(a);
		int sames = 0;
		for (int i = 0; i < 100; i++) {
			G.GAUSS16.random().call(b);
			if (G.GAUSS16.isEqual().call(a, b))
				sames++;
		}
		assertFalse(sames > 10);
	}
}
