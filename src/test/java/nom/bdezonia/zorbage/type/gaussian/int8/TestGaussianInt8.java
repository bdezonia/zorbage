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
package nom.bdezonia.zorbage.type.gaussian.int8;

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
import nom.bdezonia.zorbage.type.gaussian.int8.GaussianInt8Member;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGaussianInt8 {

	@Test
	public void testStorageMethods() {
		
		IndexedDataSource<GaussianInt8Member> data = Storage.allocate(G.GAUSS8.construct(), 12000);
		GaussianInt8Member in = new GaussianInt8Member();
		GaussianInt8Member out = new GaussianInt8Member();
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
		
		GaussianInt8Member a = G.GAUSS8.construct();
		
		assertEquals(0, a.r());
		assertEquals(0, a.i());
		assertTrue(G.GAUSS8.isZero().call(a));
		
		GaussianInt8Member b = G.GAUSS8.construct("{4,-3}");
		
		assertEquals(4, b.r());
		assertEquals(-3, b.i());
		assertFalse(G.GAUSS8.isZero().call(b));
		assertTrue(G.GAUSS8.isNotEqual().call(a, b));

		GaussianInt8Member c = G.GAUSS8.construct(b);
		
		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS8.isZero().call(b));
		assertTrue(G.GAUSS8.isEqual().call(b, c));
		assertFalse(G.GAUSS8.isNotEqual().call(b, c));
		
		G.GAUSS8.zero().call(c);
		
		assertEquals(0, c.r());
		assertEquals(0, c.i());
		assertTrue(G.GAUSS8.isZero().call(c));
		assertTrue(G.GAUSS8.isEqual().call(a, c));
		assertFalse(G.GAUSS8.isNotEqual().call(a, c));
		
		G.GAUSS8.unity().call(c);
		
		assertEquals(1, c.r());
		assertEquals(0, c.i());
		assertFalse(G.GAUSS8.isZero().call(c));
		assertFalse(G.GAUSS8.isEqual().call(b, c));
		assertTrue(G.GAUSS8.isNotEqual().call(b, c));
		
		G.GAUSS8.assign().call(b, c);

		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSS8.isZero().call(c));
		assertTrue(G.GAUSS8.isEqual().call(b, c));
		assertFalse(G.GAUSS8.isNotEqual().call(b, c));

		a.setR(0);
		a.setI(0);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(1);
		a.setI(0);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-1);
		a.setI(0);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(-1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(1);
		a.setI(1);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(-1);
		a.setI(-1);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(1);
		a.setI(-1);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(-1);
		a.setI(1);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(2);
		a.setI(0);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(2);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(-2);
		a.setI(0);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(-2);
		assertTrue(G.GAUSS8.isEven().call(a));
		assertFalse(G.GAUSS8.isOdd().call(a));

		a.setR(1);
		a.setI(2);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(1);
		a.setI(-2);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(2);
		a.setI(1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(2);
		a.setI(-1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-1);
		a.setI(2);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-1);
		a.setI(-2);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-2);
		a.setI(1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-2);
		a.setI(-1);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(3);
		a.setI(0);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(3);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(-3);
		a.setI(0);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(0);
		a.setI(-3);
		assertFalse(G.GAUSS8.isEven().call(a));
		assertTrue(G.GAUSS8.isOdd().call(a));

		a.setR(4);
		a.setI(-2);
		b.setR(-1);
		b.setI(7);
		
		G.GAUSS8.add().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(5, c.i());

		G.GAUSS8.subtract().call(a, b, c);
		
		assertEquals(5, c.r());
		assertEquals(-9, c.i());
		
		a.setR(2);
		a.setI(-2);
		b.setR(3);
		b.setI(1);
		
		G.GAUSS8.multiply().call(a, b, c);
		
		assertEquals(8, c.r());
		assertEquals(-4, c.i());
		
		G.GAUSS8.multiply().call(a, a, b);
		G.GAUSS8.multiply().call(b, a, b);
		G.GAUSS8.power().call(3, a, c);
		
		assertFalse(G.GAUSS8.isZero().call(b));
		assertFalse(G.GAUSS8.isZero().call(c));
		assertTrue(G.GAUSS8.isEqual().call(b, c));
		
		a.setR(-2);
		a.setI(7);
		
		G.GAUSS8.negate().call(a, c);

		assertEquals(2, c.r());
		assertEquals(-7, c.i());

		GaussianInt8Member d = G.GAUSS8.construct();
		GaussianInt8Member m = G.GAUSS8.construct();
		
		a.setR(18);
		a.setI(-1);
		b.setR(11);
		b.setI(7);

		G.GAUSS8.zero().call(d);
		G.GAUSS8.zero().call(m);
		G.GAUSS8.div().call(a, b, d);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());

		G.GAUSS8.zero().call(d);
		G.GAUSS8.zero().call(m);
		G.GAUSS8.mod().call(a, b, m);
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());

		G.GAUSS8.zero().call(d);
		G.GAUSS8.zero().call(m);
		G.GAUSS8.divMod().call(a, b, d, m);
		
		assertEquals(1, d.r());
		assertEquals(-1, d.i());
		
		assertEquals(0, m.r());
		assertEquals(3, m.i());
		
		HighPrecisionMember hp = G.HP.construct();
		UnsignedInt16Member num = G.UINT16.construct();
		
		a.setR(14);
		a.setI(-3);
		
		G.GAUSS8.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSS8.norm().call(a, num);

		assertEquals((14*14 + (-3)*(-3)), num.v());

		a.setR((int) Byte.MIN_VALUE);
		a.setI((int) Byte.MIN_VALUE);
		
		G.GAUSS8.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Byte.MIN_VALUE).multiply(BigInteger.valueOf(Byte.MIN_VALUE)).multiply(BigInteger.valueOf(2)).intValue(), num.v());

		a.setR((int) Byte.MAX_VALUE);
		a.setI((int) Byte.MAX_VALUE);
		
		G.GAUSS8.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Byte.MAX_VALUE).multiply(BigInteger.valueOf(Byte.MAX_VALUE)).multiply(BigInteger.valueOf(2)).intValue(), num.v());

		a.setR(-3);
		a.setI(7);
		
		G.GAUSS8.conjugate().call(a, b);
		
		assertEquals(-3, b.r());
		assertEquals(-7, b.i());

		GaussianInt8Member tol = G.GAUSS8.construct();
		
		a.setR(1);
		a.setI(2);
		b.setR(2);
		b.setI(4);

		tol.setR(-1);
		tol.setI(-1);
		try {
			G.GAUSS8.within().call(tol, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		tol.setR(0);
		tol.setI(0);
		assertFalse(G.GAUSS8.within().call(tol, a, b));

		tol.setR(1);
		tol.setI(1);
		assertFalse(G.GAUSS8.within().call(tol, a, b));

		tol.setR(2);
		tol.setI(2);
		assertTrue(G.GAUSS8.within().call(tol, a, b));

		a.setR(14);
		a.setI(12);
		b.setR(-4);
		b.setI(3);
		
		G.GAUSS8.multiply().call(a, b, c);
		G.GAUSS8.scale().call(a, b, d);
		
		assertTrue(G.GAUSS8.isEqual().call(c, d));

		a.setR(1);
		a.setI(2);
		
		G.GAUSS8.scaleByDouble().call(1.6, a, c);
		
		assertEquals(1, c.r());
		assertEquals(3, c.i());
		
		G.GAUSS8.scaleByDoubleAndRound().call(1.6, a, c);
		
		assertEquals(2, c.r());
		assertEquals(3, c.i());

		hp.setV(BigDecimal.valueOf(1.6));
		
		G.GAUSS8.scaleByHighPrec().call(hp, a, c);

		assertEquals(1, c.r());
		assertEquals(3, c.i());

		G.GAUSS8.scaleByHighPrecAndRound().call(hp, a, c);

		assertEquals(2, c.r());
		assertEquals(3, c.i());
		
		a.setR(23);
		a.setI(100);
		
		G.GAUSS8.scaleByRational().call(new RationalMember(3,4), a, c);
		
		assertEquals(17, c.r());
		assertEquals(75, c.i());

		a.setR(5);
		a.setI(-4);
		
		G.GAUSS8.scaleByOneHalf().call(1, a, c);
		
		assertEquals(2, c.r());
		assertEquals(-2, c.i());
		
		G.GAUSS8.scaleByOneHalf().call(2, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-1, c.i());
		
		a.setR(-1);
		a.setI(3);

		G.GAUSS8.scaleByTwo().call(1, a, c);
		
		assertEquals(-2, c.r());
		assertEquals(6, c.i());

		G.GAUSS8.scaleByTwo().call(2, a, c);
		
		assertEquals(-4, c.r());
		assertEquals(12, c.i());
		
		G.GAUSS8.random().call(a);
		int sames = 0;
		for (int i = 0; i < 100; i++) {
			G.GAUSS8.random().call(b);
			if (G.GAUSS8.isEqual().call(a, b))
				sames++;
		}
		assertFalse(sames > 10);
		
		// gcd : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		G.GAUSS8.power().call(2, a, b);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(9, c.r());
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(70);
		b.setI(0);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(-10, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(104);
		a.setI(0);
		b.setR(12);
		b.setI(0);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(25);
		b.setI(0);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(-5, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS8.gcd().call(b, a, c);
		
		assertEquals(-5, c.r());
		assertEquals(0, c.i());
		
		// gcd : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS8.power().call(4, a, b);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(1, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(2, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS8.power().call(2, b, c);
		G.GAUSS8.multiply().call(a, b, b);
		G.GAUSS8.assign().call(c, a);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(5, c.i());
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS8.power().call(2, b, c);
		G.GAUSS8.multiply().call(a, b, b);
		G.GAUSS8.assign().call(c, a);
		
		G.GAUSS8.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());
		
		G.GAUSS8.gcd().call(b, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());

		// lcm : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(-18, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(12);
		a.setI(0);
		b.setR(7);
		b.setI(0);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(-84, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(52);
		a.setI(0);
		b.setR(4);
		b.setI(0);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(52, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(18);
		a.setI(0);
		b.setR(21);
		b.setI(0);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(126, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS8.lcm().call(b, a, c);
		
		assertEquals(126, c.r());
		assertEquals(0, c.i());
		
		// lcm : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS8.power().call(4, a, b);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(6, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS8.power().call(2, b, c);
		G.GAUSS8.multiply().call(a, b, b);
		G.GAUSS8.assign().call(c, a);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());  // TODO : why negative
		assertEquals(-38, c.i());  // TODO : why negative
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS8.power().call(2, b, c);
		G.GAUSS8.multiply().call(a, b, b);
		G.GAUSS8.assign().call(c, a);
		
		G.GAUSS8.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
		
		G.GAUSS8.lcm().call(b, a, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
	}
}
