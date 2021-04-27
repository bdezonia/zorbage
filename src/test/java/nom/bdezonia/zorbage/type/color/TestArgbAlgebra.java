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
package nom.bdezonia.zorbage.type.color;

import static org.junit.Assert.*;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestArgbAlgebra {

	@Test
	public void test1() {
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

		ArgbMember d = G.ARGB.construct("[1,2,3,4]");
		assertEquals(1, d.a());
		assertEquals(2, d.r());
		assertEquals(3, d.g());
		assertEquals(4, d.b());
		
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
	
	@Test
	public void test2() {
		
		ArgbMember argb = G.ARGB.construct();
		G.ARGB.minBound().call(argb);
		for (int r = 0; r < 256; r++) {
			for (int g = 0; g < 256; g++) {
				for (int b = 0; b < 256; b++) {
					assertEquals(0, argb.a());
					assertEquals(r, argb.r());
					assertEquals(g, argb.g());
					assertEquals(b, argb.b());
					G.ARGB.succ().call(argb, argb);
				}
			}
		}
		assertEquals(0, argb.a());
		assertEquals(0, argb.r());
		assertEquals(0, argb.g());
		assertEquals(0, argb.b());
	}

	
	@Test
	public void test3() {
		
		ArgbMember argb = G.ARGB.construct();
		G.ARGB.maxBound().call(argb);
		for (int r = 0; r < 256; r++) {
			for (int g = 0; g < 256; g++) {
				for (int b = 0; b < 256; b++) {
					assertEquals(255, argb.a());
					assertEquals(255 - r, argb.r());
					assertEquals(255 - g, argb.g());
					assertEquals(255 - b, argb.b());
					G.ARGB.pred().call(argb, argb);
				}
			}
		}
		assertEquals(255, argb.a());
		assertEquals(255, argb.r());
		assertEquals(255, argb.g());
		assertEquals(255, argb.b());
	}
}
