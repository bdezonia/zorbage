/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMax {

	@Test
	public void test() {
		
		SignedInt16Member a = G.INT16.construct();
		SignedInt16Member b = G.INT16.construct();
		SignedInt16Member result = G.INT16.construct();
		
		a.setV(Short.MIN_VALUE);
		b.setV(Short.MIN_VALUE+1);
		Max.compute(G.INT16, a, b, result);
		assertEquals(Short.MIN_VALUE+1, result.v());
		
		b.setV(Short.MIN_VALUE);
		a.setV(Short.MIN_VALUE+1);
		Max.compute(G.INT16, a, b, result);
		assertEquals(Short.MIN_VALUE+1, result.v());
		
		a.setV(Short.MAX_VALUE);
		b.setV(Short.MAX_VALUE-1);
		Max.compute(G.INT16, a, b, result);
		assertEquals(Short.MAX_VALUE, result.v());
		
		b.setV(Short.MAX_VALUE);
		a.setV(Short.MAX_VALUE-1);
		Max.compute(G.INT16, a, b, result);
		assertEquals(Short.MAX_VALUE, result.v());
		
		a.setV(5);
		b.setV(2);
		Max.compute(G.INT16, a, b, result);
		assertEquals(5, result.v());
		
		a.setV(-5);
		b.setV(-2);
		Max.compute(G.INT16, a, b, result);
		assertEquals(-2, result.v());
		
		a.setV(-3);
		b.setV(0);
		Max.compute(G.INT16, a, b, result);
		assertEquals(0, result.v());
		
		b.setV(-3);
		a.setV(0);
		Max.compute(G.INT16, a, b, result);
		assertEquals(0, result.v());
		
		a.setV(3);
		b.setV(0);
		Max.compute(G.INT16, a, b, result);
		assertEquals(3, result.v());
		
		b.setV(3);
		a.setV(0);
		Max.compute(G.INT16, a, b, result);
		assertEquals(3, result.v());
		
		a.setV(67);
		b.setV(67);
		Max.compute(G.INT16, a, b, result);
		assertEquals(67, result.v());
	}
}
