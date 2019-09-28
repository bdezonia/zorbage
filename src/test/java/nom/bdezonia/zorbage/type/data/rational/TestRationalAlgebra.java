/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRationalAlgebra {

	@Test
	public void test() {
		RationalMember a = G.RAT.construct();
		RationalMember b = G.RAT.construct("0.8");
		RationalMember c = G.RAT.construct(b);
		RationalMember s = G.RAT.construct();
		
		assertTrue(G.RAT.isZero().call(a));
		assertTrue(!G.RAT.isZero().call(b));
		assertTrue(!G.RAT.isZero().call(c));
		assertEquals(b.n, c.n);
		assertEquals(b.d, c.d);
		assertTrue(G.RAT.isEqual().call(b,c));
		assertEquals(BigInteger.valueOf(4), b.n);
		assertEquals(BigInteger.valueOf(5), b.d);

		G.RAT.assign().call(b, a);
		assertTrue(G.RAT.isEqual().call(b,a));
		
		a.setV(BigInteger.valueOf(-2));
		assertEquals(BigInteger.valueOf(-2), a.n);
		assertEquals(BigInteger.ONE, a.d);
		G.RAT.abs().call(a, a);;
		assertEquals(BigInteger.valueOf(2), a.n);
		assertEquals(BigInteger.ONE, a.d);

		a.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		b.setV(BigInteger.valueOf(1), BigInteger.valueOf(6));
		G.RAT.add().call(a, b, c);
		assertEquals(BigInteger.valueOf(5), c.n);
		assertEquals(BigInteger.valueOf(6), c.d);

		a.setV(BigInteger.valueOf(1), BigInteger.valueOf(4));
		b.setV(BigInteger.valueOf(1), BigInteger.valueOf(3));
		assertEquals(-1, (int) G.RAT.compare().call(a, b));
		assertEquals(1, (int) G.RAT.compare().call(b, a));
		a.setV(BigInteger.valueOf(-1), BigInteger.valueOf(4));
		b.setV(BigInteger.valueOf(-1), BigInteger.valueOf(3));
		assertEquals(1, (int) G.RAT.compare().call(a, b));
		assertEquals(-1, (int) G.RAT.compare().call(b, a));

		a.setV(BigInteger.valueOf(-4), BigInteger.valueOf(9));
		b.setV(BigInteger.valueOf(4));
		G.RAT.divide().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1), c.n);
		assertEquals(BigInteger.valueOf(9), c.d);
		
		a.setV(BigInteger.valueOf(-4), BigInteger.valueOf(9));
		G.RAT.invert().call(a, b);
		assertEquals(BigInteger.valueOf(-9), b.n);
		assertEquals(BigInteger.valueOf(4), b.d);
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(-1));
		
		assertTrue(G.RAT.isEqual().call(a, a));
		assertTrue(G.RAT.isEqual().call(b, b));
		assertTrue(G.RAT.isEqual().call(c, c));

		assertTrue(G.RAT.isGreater().call(a, b));
		assertTrue(G.RAT.isGreater().call(b, c));
		assertTrue(!G.RAT.isGreater().call(c, a));

		assertTrue(G.RAT.isGreaterEqual().call(a, b));
		assertTrue(G.RAT.isGreaterEqual().call(b, c));
		assertTrue(!G.RAT.isGreaterEqual().call(c, a));

		assertTrue(G.RAT.isLess().call(c, b));
		assertTrue(G.RAT.isLess().call(b, a));
		assertTrue(!G.RAT.isLess().call(a, c));

		G.RAT.isLessEqual();
		assertTrue(G.RAT.isLessEqual().call(c, b));
		assertTrue(G.RAT.isLessEqual().call(b, a));
		assertTrue(!G.RAT.isLessEqual().call(a, c));

		G.RAT.isNotEqual();
		assertTrue(G.RAT.isNotEqual().call(a, b));
		assertTrue(G.RAT.isNotEqual().call(b, c));
		assertTrue(G.RAT.isNotEqual().call(c, a));

		a.setV(BigInteger.valueOf(100), BigInteger.valueOf(4));
		b.setV(BigInteger.valueOf(101), BigInteger.valueOf(4));
		G.RAT.max().call(a, b, c);
		assertEquals(BigInteger.valueOf(101), c.n);
		assertEquals(BigInteger.valueOf(4), c.d);
		
		a.setV(BigInteger.valueOf(100), BigInteger.valueOf(4));
		b.setV(BigInteger.valueOf(101), BigInteger.valueOf(4));
		G.RAT.min().call(a, b, c);
		assertEquals(BigInteger.valueOf(25), c.n);
		assertEquals(BigInteger.valueOf(1), c.d);
		
		a.setV(BigInteger.valueOf(-2), BigInteger.valueOf(5));
		b.setV(BigInteger.valueOf(4), BigInteger.valueOf(8));
		G.RAT.multiply().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1), c.n);
		assertEquals(BigInteger.valueOf(5), c.d);

		a.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		G.RAT.negate().call(a, b);
		assertEquals(BigInteger.valueOf(-2), b.n);
		assertEquals(BigInteger.valueOf(3), b.d);
		a.setV(BigInteger.valueOf(-22), BigInteger.valueOf(3));
		G.RAT.negate().call(a, b);
		assertEquals(BigInteger.valueOf(22), b.n);
		assertEquals(BigInteger.valueOf(3), b.d);
		
		// won't test: norm() is abs() and abs() tested elsewhere
		//G.RAT.norm();
		
		a.setV(BigInteger.valueOf(4),BigInteger.valueOf(5));
		G.RAT.power().call(3, a, c);
		assertEquals(BigInteger.valueOf(4*4*4), c.n);
		assertEquals(BigInteger.valueOf(5*5*5), c.d);

		a.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		s.setV(BigInteger.ZERO);
		G.RAT.scale().call(s, a, b);
		assertEquals(BigInteger.ZERO, b.n);
		assertEquals(BigInteger.ONE, b.d);
		s.setV(BigInteger.valueOf(-2));
		G.RAT.scale().call(s, a, b);
		assertEquals(BigInteger.valueOf(-4), b.n);
		assertEquals(BigInteger.valueOf(3), b.d);
		G.RAT.scale().call(s, b, b);
		assertEquals(BigInteger.valueOf(8), b.n);
		assertEquals(BigInteger.valueOf(3), b.d);

		a.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		assertEquals(1,(int)G.RAT.signum().call(a));
		a.setV(BigInteger.valueOf(-2), BigInteger.valueOf(3));
		assertEquals(-1,(int)G.RAT.signum().call(a));
		a.setV(BigInteger.valueOf(0), BigInteger.valueOf(3));
		assertEquals(0,(int)G.RAT.signum().call(a));

		a.setV(BigInteger.valueOf(1), BigInteger.valueOf(4));
		b.setV(BigInteger.valueOf(1), BigInteger.valueOf(3));
		G.RAT.subtract().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1), c.n);
		assertEquals(BigInteger.valueOf(12), c.d);
		G.RAT.subtract().call(b, a, c);
		assertEquals(BigInteger.valueOf(1), c.n);
		assertEquals(BigInteger.valueOf(12), c.d);

		a.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		assertEquals("2/3", a.toString());
		
		c.setV(BigInteger.ONE, BigInteger.TEN);
		G.RAT.zero().call(c);
		assertEquals(BigInteger.ZERO, c.n);
		assertEquals(BigInteger.ONE, c.d);

		c.setV(BigInteger.valueOf(2), BigInteger.valueOf(3));
		G.RAT.unity().call(c);
		assertEquals(BigInteger.ONE, c.n);
		assertEquals(BigInteger.ONE, c.d);
		
		a.setV(BigInteger.ONE, BigInteger.valueOf(6));
		b.setV(BigInteger.valueOf(2), BigInteger.valueOf(6));
		c.setV(BigInteger.valueOf(3), BigInteger.valueOf(6));
		assertTrue(G.RAT.within().call(a, b, c));
		a.setV(BigInteger.ONE, BigInteger.valueOf(7));
		assertFalse(G.RAT.within().call(a, b, c));
		
		G.RAT.div().call(a, c, b);
		assertEquals(BigInteger.ZERO, b.n());
		assertEquals(BigInteger.ONE, b.d());
		G.RAT.mod().call(a, c, b);
		assertEquals(BigInteger.ONE, b.n());
		assertEquals(BigInteger.valueOf(7), b.d());
		
		c.setV(BigInteger.valueOf(3), BigInteger.valueOf(8));
		G.RAT.div().call(c, a, b);
		assertEquals(BigInteger.valueOf(2), b.n());
		assertEquals(BigInteger.ONE, b.d());
		G.RAT.mod().call(c, a, b);
		assertEquals(BigInteger.valueOf(5), b.n());
		assertEquals(BigInteger.valueOf(56), b.d());
	}
	
	// proof that rats and doubles behave the same
	
	@Test
	public void testX() {
		RationalMember a = G.RAT.construct();
		RationalMember b = G.RAT.construct();
		RationalMember c = G.RAT.construct();
		Float64Member af = G.DBL.construct();
		Float64Member bf = G.DBL.construct();
		Float64Member cf = G.DBL.construct();
		
		a.setV(BigInteger.valueOf(27), BigInteger.valueOf(5));
		af.setV(27.0/5);
		
		b.setV(BigInteger.valueOf(44), BigInteger.valueOf(13));
		bf.setV(44.0/13);
		
		G.RAT.mod().call(a, b, c);
		G.DBL.mod().call(af, bf, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		
		G.RAT.mod().call(b, a, c);
		G.DBL.mod().call(bf, af, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		a.setV(BigInteger.valueOf(-27), BigInteger.valueOf(5));
		af.setV(-27.0/5);
		
		b.setV(BigInteger.valueOf(44), BigInteger.valueOf(13));
		bf.setV(44.0/13);
		
		G.RAT.mod().call(a, b, c);
		G.DBL.mod().call(af, bf, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		
		G.RAT.mod().call(b, a, c);
		G.DBL.mod().call(bf, af, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		a.setV(BigInteger.valueOf(27), BigInteger.valueOf(5));
		af.setV(27.0/5);
		
		b.setV(BigInteger.valueOf(-44), BigInteger.valueOf(13));
		bf.setV(-44.0/13);
		
		G.RAT.mod().call(a, b, c);
		G.DBL.mod().call(af, bf, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		
		G.RAT.mod().call(b, a, c);
		G.DBL.mod().call(bf, af, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		a.setV(BigInteger.valueOf(-27), BigInteger.valueOf(5));
		af.setV(-27.0/5);
		
		b.setV(BigInteger.valueOf(-44), BigInteger.valueOf(13));
		bf.setV(-44.0/13);
		
		G.RAT.mod().call(a, b, c);
		G.DBL.mod().call(af, bf, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
		
		
		G.RAT.mod().call(b, a, c);
		G.DBL.mod().call(bf, af, cf);
		System.out.println("--------------------");
		System.out.println("BIGD " + c.v());
		System.out.println("DBL  " + cf);
	}
}
