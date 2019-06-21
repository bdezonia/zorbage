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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.data.intunlim.UnboundedIntMember;

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
		
		UnboundedIntMember tmp = G.INT_UNLIM.construct();
		
		Fibonacci.compute(G.INT_UNLIM, 99, tmp);
		assertTrue(G.INT_UNLIM.isEqual().call(new UnboundedIntMember("218922995834555169026"), tmp));
	}
}