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
package nom.bdezonia.zorbage.type.gaussian.int16;

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
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

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
		
		HighPrecisionMember hp = G.HP.construct();
		UnsignedInt32Member num = G.UINT32.construct();
		
		a.setR(14);
		a.setI(-3);
		
		G.GAUSS16.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSS16.norm().call(a, num);

		assertEquals((14*14 + (-3)*(-3)), num.v());

		a.setR((int) Short.MIN_VALUE);
		a.setI((int) Short.MIN_VALUE);
		
		G.GAUSS16.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Short.MIN_VALUE).multiply(BigInteger.valueOf(Short.MIN_VALUE)).multiply(BigInteger.valueOf(2)).longValue(), num.v());

		a.setR((int) Short.MAX_VALUE);
		a.setI((int) Short.MAX_VALUE);
		
		G.GAUSS16.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Short.MAX_VALUE).multiply(BigInteger.valueOf(Short.MAX_VALUE)).multiply(BigInteger.valueOf(2)).longValue(), num.v());
		
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

		// gcd : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		G.GAUSS16.power().call(4, a, b);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(9, c.r());
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(70);
		b.setI(0);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(-10, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(104);
		a.setI(0);
		b.setR(12);
		b.setI(0);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(18000);
		a.setI(0);
		b.setR(250);
		b.setI(0);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(250, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS16.gcd().call(b, a, c);
		
		assertEquals(250, c.r());
		assertEquals(0, c.i());
		
		// gcd : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS16.power().call(4, a, b);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(1, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(2, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS16.power().call(2, b, c);
		G.GAUSS16.multiply().call(a, b, b);
		G.GAUSS16.assign().call(c, a);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(5, c.i());
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS16.power().call(2, b, c);
		G.GAUSS16.multiply().call(a, b, b);
		G.GAUSS16.assign().call(c, a);
		
		G.GAUSS16.gcd().call(a, b, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());
		
		G.GAUSS16.gcd().call(b, a, c);
		
		assertEquals(1, c.r());
		assertEquals(-5, c.i());

		// lcm : real numbers
		
		a.setR(9);
		a.setI(0);
		b.setR(6);
		b.setI(0);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-18, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(120);
		a.setI(0);
		b.setR(70);
		b.setI(0);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-840, c.r());  // TODO : why negative
		assertEquals(0, c.i());

		a.setR(104);
		a.setI(0);
		b.setR(12);
		b.setI(0);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-312, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(18000);
		a.setI(0);
		b.setR(250);
		b.setI(0);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(18000, c.r());
		assertEquals(0, c.i());
		
		G.GAUSS16.lcm().call(b, a, c);
		
		assertEquals(18000, c.r());
		assertEquals(0, c.i());
		
		// lcm : imag numbers
		
		a.setR(1);
		a.setI(1);
		G.GAUSS16.power().call(4, a, b);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-4, c.r());  // TODO : why negative
		assertEquals(0, c.i());
		
		a.setR(3);
		a.setI(6);
		b.setR(1);
		b.setI(2);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(3, c.r());
		assertEquals(6, c.i());

		a.setR(1);
		a.setI(2);
		b.setR(1);
		b.setI(5);
		G.GAUSS16.power().call(2, b, c);
		G.GAUSS16.multiply().call(a, b, b);
		G.GAUSS16.assign().call(c, a);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());  // TODO : why negative
		assertEquals(-38, c.i());  // TODO : why negative
		
		a.setR(1);
		a.setI(-2);
		b.setR(1);
		b.setI(-5);
		G.GAUSS16.power().call(2, b, c);
		G.GAUSS16.multiply().call(a, b, b);
		G.GAUSS16.assign().call(c, a);
		
		G.GAUSS16.lcm().call(a, b, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
		
		G.GAUSS16.lcm().call(b, a, c);
		
		assertEquals(-44, c.r());
		assertEquals(38, c.i());
	}
}
