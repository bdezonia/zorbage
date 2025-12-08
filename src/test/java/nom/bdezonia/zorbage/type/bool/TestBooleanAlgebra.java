/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.bool;

import static org.junit.Assert.*;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBooleanAlgebra {

	@Test
	@SuppressWarnings("unused")
	public void test() {
		BooleanMember a = G.BOOL.construct();
		assertFalse(a.v());
		
		BooleanMember b = G.BOOL.construct("false");
		assertFalse(b.v());
		b = G.BOOL.construct("true");
		assertTrue(b.v());
		
		BooleanMember c = G.BOOL.construct(b);
		assertTrue(c.v());
		
		G.BOOL.assign().call(a, c);
		assertFalse(c.v());
		
		assertEquals(0, (int) G.BOOL.compare().call(a, a));
		assertEquals(1, (int) G.BOOL.compare().call(b, a));
		a.setV(false);
		b.setV(true);
		assertEquals(-1, (int) G.BOOL.compare().call(a, b));
		
		assertTrue(G.BOOL.isEqual().call(a, a));
		assertTrue(G.BOOL.isEqual().call(b, b));
		assertFalse(G.BOOL.isEqual().call(a, b));

		assertFalse(G.BOOL.isGreater().call(a, a));
		assertFalse(G.BOOL.isGreater().call(b, b));
		assertFalse(G.BOOL.isGreater().call(a, b));
		assertTrue(G.BOOL.isGreater().call(b, a));

		assertTrue(G.BOOL.isGreaterEqual().call(a, a));
		assertTrue(G.BOOL.isGreaterEqual().call(b, b));
		assertFalse(G.BOOL.isGreaterEqual().call(a, b));
		assertTrue(G.BOOL.isGreaterEqual().call(b, a));
		
		assertFalse(G.BOOL.isLess().call(a, a));
		assertFalse(G.BOOL.isLess().call(b, b));
		assertTrue(G.BOOL.isLess().call(a, b));
		assertFalse(G.BOOL.isLess().call(b, a));
		
		assertTrue(G.BOOL.isLessEqual().call(a, a));
		assertTrue(G.BOOL.isLessEqual().call(b, b));
		assertTrue(G.BOOL.isLessEqual().call(a, b));
		assertFalse(G.BOOL.isLessEqual().call(b, a));
		
		assertFalse(G.BOOL.isNotEqual().call(a, a));
		assertFalse(G.BOOL.isNotEqual().call(b, b));
		assertTrue(G.BOOL.isNotEqual().call(a, b));
		assertTrue(G.BOOL.isNotEqual().call(b, a));
		
		assertTrue(G.BOOL.isZero().call(a));
		assertFalse(G.BOOL.isZero().call(b));
		
		G.BOOL.logicalAnd().call(a, a, c);
		assertEquals(false && false, c.v());
		G.BOOL.logicalAnd().call(a, b, c);
		assertEquals(false && true, c.v());
		G.BOOL.logicalAnd().call(b, a, c);
		assertEquals(true && false, c.v());
		G.BOOL.logicalAnd().call(b, b, c);
		assertEquals(true && true, c.v());
		
		G.BOOL.logicalAndNot().call(a, a, c);
		assertEquals(false && !false, c.v());
		G.BOOL.logicalAndNot().call(a, b, c);
		assertEquals(false && !true, c.v());
		G.BOOL.logicalAndNot().call(b, a, c);
		assertEquals(true && !false, c.v());
		G.BOOL.logicalAndNot().call(b, b, c);
		assertEquals(true && !true, c.v());
		
		G.BOOL.logicalNot().call(a, c);
		assertTrue(c.v());
		G.BOOL.logicalNot().call(b, c);
		assertFalse(c.v());
		
		G.BOOL.logicalOr().call(a, a, c);
		assertEquals(false || false, c.v());
		G.BOOL.logicalOr().call(a, b, c);
		assertEquals(false || true, c.v());
		G.BOOL.logicalOr().call(b, a, c);
		assertEquals(true || false, c.v());
		G.BOOL.logicalOr().call(b, b, c);
		assertEquals(true || true, c.v());
		
		G.BOOL.logicalXor().call(a, a, c);
		assertEquals(false ^ false, c.v());
		G.BOOL.logicalXor().call(a, b, c);
		assertEquals(false ^ true, c.v());
		G.BOOL.logicalXor().call(b, a, c);
		assertEquals(true ^ false, c.v());
		G.BOOL.logicalXor().call(b, b, c);
		assertEquals(true ^ true, c.v());
		
		G.BOOL.max().call(a, a, c);
		assertEquals(a.v(), c.v());
		G.BOOL.max().call(a, b, c);
		assertEquals(b.v(), c.v());
		G.BOOL.max().call(b, a, c);
		assertEquals(b.v(), c.v());
		G.BOOL.max().call(b, b, c);
		assertEquals(b.v(), c.v());
		
		G.BOOL.min().call(a, a, c);
		assertEquals(a.v(), c.v());
		G.BOOL.min().call(a, b, c);
		assertEquals(a.v(), c.v());
		G.BOOL.min().call(b, a, c);
		assertEquals(a.v(), c.v());
		G.BOOL.min().call(b, b, c);
		assertEquals(b.v(), c.v());
		
		G.BOOL.maxBound().call(c);
		assertTrue(c.v());
		
		G.BOOL.minBound().call(c);
		assertFalse(c.v());
		
		G.BOOL.random().call(c);
		
		assertEquals(0, (int) G.BOOL.signum().call(a));
		assertEquals(1, (int) G.BOOL.signum().call(b));

		BooleanMember d = G.BOOL.construct();
		a.setV(false);
		b.setV(false);
		c.setV(false);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((false ? false : false), d.v());
		a.setV(false);
		b.setV(false);
		c.setV(true);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((false ? false : true), d.v());
		a.setV(false);
		b.setV(true);
		c.setV(false);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((false ? true : false), d.v());
		a.setV(false);
		b.setV(true);
		c.setV(true);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((false ? true : true), d.v());
		a.setV(true);
		b.setV(false);
		c.setV(false);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((true ? false : false), d.v());
		a.setV(true);
		b.setV(false);
		c.setV(true);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((true ? false : true), d.v());
		a.setV(true);
		b.setV(true);
		c.setV(false);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((true ? true : false), d.v());
		a.setV(true);
		b.setV(true);
		c.setV(true);
		G.BOOL.ternary().call(a, b, c, d);
		assertEquals((true ? true : true), d.v());
		
		G.BOOL.unity().call(c);
		assertTrue(c.v());
	}

}
