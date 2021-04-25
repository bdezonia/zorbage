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
import nom.bdezonia.zorbage.type.color.RgbMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRgbAlgebra {

	@Test
	public void test1() {
		RgbMember a = G.RGB.construct();
		assertEquals(0, a.r());
		assertEquals(0, a.g());
		assertEquals(0, a.b());

		RgbMember b = G.RGB.construct(a);
		assertEquals(0, b.r());
		assertEquals(0, b.g());
		assertEquals(0, b.b());
		a.setR(100);
		a.setG(75);
		a.setB(50);
		b = G.RGB.construct(a);
		assertEquals(100, b.r());
		assertEquals(75, b.g());
		assertEquals(50, b.b());

		RgbMember c = G.RGB.construct();
		G.RGB.assign().call(b, c);
		assertEquals(100, c.r());
		assertEquals(75, c.g());
		assertEquals(50, c.b());

		RgbMember d = G.RGB.construct("[1,2,3]");
		assertEquals(1, d.r());
		assertEquals(2, d.g());
		assertEquals(3, d.b());

		a.setR(1);
		a.setG(2);
		a.setB(3);
		assertFalse(G.RGB.isEqual().call(a, b));
		assertTrue(G.RGB.isEqual().call(b, c));

		assertTrue(G.RGB.isNotEqual().call(a, b));
		assertFalse(G.RGB.isNotEqual().call(b, c));

		assertFalse(G.RGB.isZero().call(a));
		G.RGB.zero().call(a);
		assertTrue(G.RGB.isZero().call(a));
		
		G.RGB.maxBound().call(a);
		assertEquals(255, a.r());
		assertEquals(255, a.g());
		assertEquals(255, a.b());
		
		G.RGB.minBound().call(a);
		assertEquals(0, a.r());
		assertEquals(0, a.g());
		assertEquals(0, a.b());
		
		G.RGB.random().call(a);
	}
	
	@Test
	public void test2() {
		
		RgbMember rgb = G.RGB.construct();
		G.RGB.minBound().call(rgb);
		for (int r = 0; r < 256; r++) {
			for (int g = 0; g < 256; g++) {
				for (int b = 0; b < 256; b++) {
					assertEquals(r, rgb.r());
					assertEquals(g, rgb.g());
					assertEquals(b, rgb.b());
					G.RGB.succ().call(rgb, rgb);
				}
			}
		}
		assertEquals(0, rgb.r());
		assertEquals(0, rgb.g());
		assertEquals(0, rgb.b());
	}

	
	@Test
	public void test3() {
		
		RgbMember rgb = G.RGB.construct();
		G.RGB.maxBound().call(rgb);
		for (int r = 0; r < 256; r++) {
			for (int g = 0; g < 256; g++) {
				for (int b = 0; b < 256; b++) {
					assertEquals(255 - r, rgb.r());
					assertEquals(255 - g, rgb.g());
					assertEquals(255 - b, rgb.b());
					G.RGB.pred().call(rgb, rgb);
				}
			}
		}
		assertEquals(255, rgb.r());
		assertEquals(255, rgb.g());
		assertEquals(255, rgb.b());
	}
}
