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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.integer.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFibonacci {

	@Test
	public void test1() {
		
		UnsignedInt13Member tmp = G.UINT13.construct();
		
		try {
			Fibonacci.compute(G.UINT13, -1, tmp);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Fibonacci.compute(G.UINT13, 0, tmp);
		assertEquals(0, tmp.v());
		
		Fibonacci.compute(G.UINT13, 1, tmp);
		assertEquals(1, tmp.v());
		
		Fibonacci.compute(G.UINT13, 2, tmp);
		assertEquals(1, tmp.v());
		
		Fibonacci.compute(G.UINT13, 3, tmp);
		assertEquals(2, tmp.v());
		
		Fibonacci.compute(G.UINT13, 4, tmp);
		assertEquals(3, tmp.v());
		
		Fibonacci.compute(G.UINT13, 5, tmp);
		assertEquals(5, tmp.v());
		
		Fibonacci.compute(G.UINT13, 6, tmp);
		assertEquals(8, tmp.v());
		
		Fibonacci.compute(G.UINT13, 7, tmp);
		assertEquals(13, tmp.v());
		
		Fibonacci.compute(G.UINT13, 8, tmp);
		assertEquals(21, tmp.v());
	}
	
	@Test
	public void test2() {
		
		UnboundedIntMember tmp = G.UNBOUND.construct();
		
		Fibonacci.compute(G.UNBOUND, 599, tmp);
		assertTrue(G.UNBOUND.isEqual().call(new UnboundedIntMember("68251391096100309964978446045087420307025606859722438323487946038808981838031799984351367205238184363410615527949660089420401"), tmp));
	}
}
