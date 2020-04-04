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
package nom.bdezonia.zorbage.type.data.rgb;

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestArgbAlgebra {

	@Test
	public void test() {
		ArgbMember a = G.ARGB.construct();
		assertEquals(0, a.a());
		assertEquals(0, a.r());
		assertEquals(0, a.g());
		assertEquals(0, a.b());

		ArgbMember b = G.ARGB.construct(a);
		assertEquals(0, b.r());
		assertEquals(0, b.r());
		assertEquals(0, b.g());
		assertEquals(0, b.b());
		a.setA(99);
		a.setR(100);
		a.setG(75);
		a.setB(50);
		b = G.ARGB.construct(a);
		assertEquals(99, b.a());
		assertEquals(100, b.r());
		assertEquals(75, b.g());
		assertEquals(50, b.b());

		ArgbMember c = G.ARGB.construct();
		G.ARGB.assign().call(b, c);
		assertEquals(99, c.a());
		assertEquals(100, c.r());
		assertEquals(75, c.g());
		assertEquals(50, c.b());

		a.setA(66);
		a.setR(1);
		a.setG(2);
		a.setB(3);
		assertFalse(G.ARGB.isEqual().call(a, b));
		assertTrue(G.ARGB.isEqual().call(b, c));

		assertTrue(G.ARGB.isNotEqual().call(a, b));
		assertFalse(G.ARGB.isNotEqual().call(b, c));

		assertFalse(G.ARGB.isZero().call(a));
		G.ARGB.zero().call(a);
		assertTrue(G.ARGB.isZero().call(a));
		
		G.ARGB.maxBound().call(a);
		assertEquals(255, a.a());
		assertEquals(255, a.r());
		assertEquals(255, a.g());
		assertEquals(255, a.b());
		
		G.ARGB.minBound().call(a);
		assertEquals(0, a.a());
		assertEquals(0, a.r());
		assertEquals(0, a.g());
		assertEquals(0, a.b());
		
		G.ARGB.random().call(a);
	}

}
