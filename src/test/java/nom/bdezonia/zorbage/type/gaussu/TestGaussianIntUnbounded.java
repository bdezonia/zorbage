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
package nom.bdezonia.zorbage.type.gaussu;

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
import nom.bdezonia.zorbage.type.gaussianu.GaussianIntUnboundedMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGaussianIntUnbounded {

	@Test
	public void testStorageMethods() {
		
		IndexedDataSource<GaussianIntUnboundedMember> data = Storage.allocate(G.GAUSSU.construct(), 12000);
		GaussianIntUnboundedMember in = new GaussianIntUnboundedMember();
		GaussianIntUnboundedMember out = new GaussianIntUnboundedMember();
		in.setR(BigInteger.ZERO);
		in.setI(BigInteger.ZERO);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.r());
			assertEquals(BigInteger.ZERO, out.i());
		}
		for (long i = 0; i < data.size(); i++) {
			in.setR(BigInteger.valueOf(i));
			in.setI(BigInteger.valueOf(i+1));
			data.set(i, in);
			out.setR(in.r().subtract(BigInteger.ONE));
			out.setI(in.i().subtract(BigInteger.ONE).subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(BigInteger.ZERO);
		in.setI(BigInteger.ZERO);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.r());
			assertEquals(BigInteger.ZERO, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR(BigInteger.valueOf(i));
			in.setR(BigInteger.valueOf(i+1));
			data.set(i, in);
			out.setR(in.r().subtract(BigInteger.ONE));
			out.setI(in.i().subtract(BigInteger.ONE).subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
		in.setR(BigInteger.ZERO);
		in.setI(BigInteger.ZERO);
		for (long i = data.size()-1; i >= 0; i--) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.r());
			assertEquals(BigInteger.ZERO, out.i());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setR(BigInteger.valueOf(i));
			in.setI(BigInteger.valueOf(i+1));
			data.set(i, in);
			out.setR(in.r().subtract(BigInteger.ONE));
			out.setI(in.i().subtract(BigInteger.ONE).subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.r(), out.r());
			assertEquals(in.i(), out.i());
		}
	}

	@Test
	public void testmathematicalMethods() {
		
		GaussianIntUnboundedMember a = G.GAUSSU.construct();
		
		assertEquals(BigInteger.ZERO, a.r());
		assertEquals(BigInteger.ZERO, a.i());
		assertTrue(G.GAUSSU.isZero().call(a));
		
		GaussianIntUnboundedMember b = G.GAUSSU.construct("{4,-3}");
		
		assertEquals(BigInteger.valueOf(4), b.r());
		assertEquals(BigInteger.valueOf(-3), b.i());
		assertFalse(G.GAUSSU.isZero().call(b));
		assertTrue(G.GAUSSU.isNotEqual().call(a, b));

		GaussianIntUnboundedMember c = G.GAUSSU.construct(b);
		
		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSSU.isZero().call(b));
		assertTrue(G.GAUSSU.isEqual().call(b, c));
		assertFalse(G.GAUSSU.isNotEqual().call(b, c));
		
		G.GAUSSU.zero().call(c);
		
		assertEquals(BigInteger.ZERO, c.r());
		assertEquals(BigInteger.ZERO, c.i());
		assertTrue(G.GAUSSU.isZero().call(c));
		assertTrue(G.GAUSSU.isEqual().call(a, c));
		assertFalse(G.GAUSSU.isNotEqual().call(a, c));
		
		G.GAUSSU.unity().call(c);
		
		assertEquals(BigInteger.ONE, c.r());
		assertEquals(BigInteger.ZERO, c.i());
		assertFalse(G.GAUSSU.isZero().call(c));
		assertFalse(G.GAUSSU.isEqual().call(b, c));
		assertTrue(G.GAUSSU.isNotEqual().call(b, c));
		
		G.GAUSSU.assign().call(b, c);

		assertEquals(b.r(), c.r());
		assertEquals(b.i(), c.i());
		assertFalse(G.GAUSSU.isZero().call(c));
		assertTrue(G.GAUSSU.isEqual().call(b, c));
		assertFalse(G.GAUSSU.isNotEqual().call(b, c));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.ZERO);
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ONE);
		a.setI(BigInteger.ZERO);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.ONE);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.ZERO);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.valueOf(-1));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ONE);
		a.setI(BigInteger.ONE);
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.valueOf(-1));
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ONE);
		a.setI(BigInteger.valueOf(-1));
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.ONE);
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(2));
		a.setI(BigInteger.ZERO);
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.valueOf(2));
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-2));
		a.setI(BigInteger.ZERO);
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.valueOf(-2));
		assertTrue(G.GAUSSU.isEven().call(a));
		assertFalse(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ONE);
		a.setI(BigInteger.valueOf(2));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ONE);
		a.setI(BigInteger.valueOf(-2));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(2));
		a.setI(BigInteger.ONE);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(2));
		a.setI(BigInteger.valueOf(-1));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.valueOf(2));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.valueOf(-2));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-2));
		a.setI(BigInteger.ONE);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-2));
		a.setI(BigInteger.valueOf(-1));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(3));
		a.setI(BigInteger.ZERO);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.valueOf(3));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(-3));
		a.setI(BigInteger.ZERO);
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.ZERO);
		a.setI(BigInteger.valueOf(-3));
		assertFalse(G.GAUSSU.isEven().call(a));
		assertTrue(G.GAUSSU.isOdd().call(a));

		a.setR(BigInteger.valueOf(4));
		a.setI(BigInteger.valueOf(-2));
		b.setR(BigInteger.valueOf(-1));
		b.setI(BigInteger.valueOf(7));
		
		G.GAUSSU.add().call(a, b, c);
		
		assertEquals(BigInteger.valueOf(3), c.r());
		assertEquals(BigInteger.valueOf(5), c.i());

		G.GAUSSU.subtract().call(a, b, c);
		
		assertEquals(BigInteger.valueOf(5), c.r());
		assertEquals(BigInteger.valueOf(-9), c.i());
		
		a.setR(BigInteger.valueOf(2));
		a.setI(BigInteger.valueOf(-2));
		b.setR(BigInteger.valueOf(3));
		b.setI(BigInteger.valueOf(1));
		
		G.GAUSSU.multiply().call(a, b, c);
		
		assertEquals(BigInteger.valueOf(8), c.r());
		assertEquals(BigInteger.valueOf(-4), c.i());
		
		G.GAUSSU.multiply().call(a, a, b);
		G.GAUSSU.multiply().call(b, a, b);
		G.GAUSSU.power().call(3, a, c);
		
		assertFalse(G.GAUSSU.isZero().call(b));
		assertFalse(G.GAUSSU.isZero().call(c));
		assertTrue(G.GAUSSU.isEqual().call(b, c));
		
		a.setR(BigInteger.valueOf(-2));
		a.setI(BigInteger.valueOf(7));
		
		G.GAUSSU.negate().call(a, c);

		assertEquals(BigInteger.valueOf(2), c.r());
		assertEquals(BigInteger.valueOf(-7), c.i());

		GaussianIntUnboundedMember d = G.GAUSSU.construct();
		GaussianIntUnboundedMember m = G.GAUSSU.construct();
		
		a.setR(BigInteger.valueOf(18));
		a.setI(BigInteger.valueOf(-1));
		b.setR(BigInteger.valueOf(11));
		b.setI(BigInteger.valueOf(7));

		G.GAUSSU.zero().call(d);
		G.GAUSSU.zero().call(m);
		G.GAUSSU.div().call(a, b, d);
		
		assertEquals(BigInteger.valueOf(1), d.r());
		assertEquals(BigInteger.valueOf(-1), d.i());

		G.GAUSSU.zero().call(d);
		G.GAUSSU.zero().call(m);
		G.GAUSSU.mod().call(a, b, m);
		
		assertEquals(BigInteger.valueOf(0), m.r());
		assertEquals(BigInteger.valueOf(3), m.i());

		G.GAUSSU.zero().call(d);
		G.GAUSSU.zero().call(m);
		G.GAUSSU.divMod().call(a, b, d, m);
		
		assertEquals(BigInteger.valueOf(1), d.r());
		assertEquals(BigInteger.valueOf(-1), d.i());
		
		assertEquals(BigInteger.valueOf(0), m.r());
		assertEquals(BigInteger.valueOf(3), m.i());
		
		HighPrecisionMember hp = G.HP.construct();
		UnboundedIntMember num = G.UNBOUND.construct();
		
		a.setR(BigInteger.valueOf(14));
		a.setI(BigInteger.valueOf(-3));
		
		G.GAUSSU.abs().call(a, hp);
		
		assertEquals(0, hp.v().compareTo(BigDecimalMath.sqrt(BigDecimal.valueOf(14*14 + (-3)*(-3)), HighPrecisionAlgebra.getContext())));
		
		G.GAUSSU.norm().call(a, num);

		assertEquals(BigInteger.valueOf(14*14 + (-3)*(-3)), num.v());

		a.setR(BigInteger.valueOf(Long.MIN_VALUE));
		a.setI(BigInteger.valueOf(Long.MIN_VALUE));
		
		G.GAUSSU.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Long.MIN_VALUE).multiply(BigInteger.valueOf(Long.MIN_VALUE)).multiply(BigInteger.TWO), num.v());

		a.setR(BigInteger.valueOf(Long.MAX_VALUE));
		a.setI(BigInteger.valueOf(Long.MAX_VALUE));
		
		G.GAUSSU.norm().call(a, num);
		
		assertEquals(BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(Long.MAX_VALUE)).multiply(BigInteger.TWO), num.v());

		a.setR(BigInteger.valueOf(-3));
		a.setI(BigInteger.valueOf(7));
		
		G.GAUSSU.conjugate().call(a, b);
		
		assertEquals(BigInteger.valueOf(-3), b.r());
		assertEquals(BigInteger.valueOf(-7), b.i());

		GaussianIntUnboundedMember tol = G.GAUSSU.construct();
		
		a.setR(BigInteger.valueOf(1));
		a.setI(BigInteger.valueOf(2));
		b.setR(BigInteger.valueOf(2));
		b.setI(BigInteger.valueOf(4));

		tol.setR(BigInteger.valueOf(-1));
		tol.setI(BigInteger.valueOf(-1));
		try {
			G.GAUSSU.within().call(tol, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		tol.setR(BigInteger.valueOf(0));
		tol.setI(BigInteger.valueOf(0));
		assertFalse(G.GAUSSU.within().call(tol, a, b));

		tol.setR(BigInteger.valueOf(1));
		tol.setI(BigInteger.valueOf(1));
		assertFalse(G.GAUSSU.within().call(tol, a, b));

		tol.setR(BigInteger.valueOf(2));
		tol.setI(BigInteger.valueOf(2));
		assertTrue(G.GAUSSU.within().call(tol, a, b));

		a.setR(BigInteger.valueOf(14));
		a.setI(BigInteger.valueOf(12));
		b.setR(BigInteger.valueOf(-4));
		b.setI(BigInteger.valueOf(3));
		
		G.GAUSSU.multiply().call(a, b, c);
		G.GAUSSU.scale().call(a, b, d);
		
		assertTrue(G.GAUSSU.isEqual().call(c, d));

		a.setR(BigInteger.valueOf(1));
		a.setI(BigInteger.valueOf(2));
		
		G.GAUSSU.scaleByDouble().call(1.6, a, c);
		
		assertEquals(BigInteger.valueOf(1), c.r());
		assertEquals(BigInteger.valueOf(3), c.i());
		
		G.GAUSSU.scaleByDoubleAndRound().call(1.6, a, c);
		
		assertEquals(BigInteger.valueOf(2), c.r());
		assertEquals(BigInteger.valueOf(3), c.i());

		hp.setV(BigDecimal.valueOf(1.6));
		
		G.GAUSSU.scaleByHighPrec().call(hp, a, c);

		assertEquals(BigInteger.valueOf(1), c.r());
		assertEquals(BigInteger.valueOf(3), c.i());

		G.GAUSSU.scaleByHighPrecAndRound().call(hp, a, c);

		assertEquals(BigInteger.valueOf(2), c.r());
		assertEquals(BigInteger.valueOf(3), c.i());
		
		a.setR(BigInteger.valueOf(23));
		a.setI(BigInteger.valueOf(100));
		
		G.GAUSSU.scaleByRational().call(new RationalMember(3,4), a, c);
		
		assertEquals(BigInteger.valueOf(17), c.r());
		assertEquals(BigInteger.valueOf(75), c.i());

		a.setR(BigInteger.valueOf(5));
		a.setI(BigInteger.valueOf(-4));
		
		G.GAUSSU.scaleByOneHalf().call(1, a, c);
		
		assertEquals(BigInteger.valueOf(2), c.r());
		assertEquals(BigInteger.valueOf(-2), c.i());
		
		G.GAUSSU.scaleByOneHalf().call(2, a, c);
		
		assertEquals(BigInteger.valueOf(1), c.r());
		assertEquals(BigInteger.valueOf(-1), c.i());
		
		a.setR(BigInteger.valueOf(-1));
		a.setI(BigInteger.valueOf(3));

		G.GAUSSU.scaleByTwo().call(1, a, c);
		
		assertEquals(BigInteger.valueOf(-2), c.r());
		assertEquals(BigInteger.valueOf(6), c.i());

		G.GAUSSU.scaleByTwo().call(2, a, c);
		
		assertEquals(BigInteger.valueOf(-4), c.r());
		assertEquals(BigInteger.valueOf(12), c.i());
		
		try {
			G.GAUSSU.gcd().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method gcd()");
			assertTrue(true);
		}
		
		try {
			G.GAUSSU.lcm().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Not testing unfinished method lcm()");
			assertTrue(true);
		}
	}
	
}
