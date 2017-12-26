/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt32Group {

	@Test
	public void testInts() {
		  
		SignedInt32Member a = G.INT32.construct("1");
		SignedInt32Member b = G.INT32.construct("4");
		SignedInt32Member sum = G.INT32.construct("99");

		G.INT32.add(a,b,sum);
		
		assertEquals(5, sum.v());
	}
	
	@Test
	public void testPower() {
		SignedInt32Member a = G.INT32.construct();
		SignedInt32Member b = G.INT32.construct();
		
		a.setV(2);
		
		try {
			G.INT32.power(-1, a, b);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		for (int i = 0; i < 100; i++) {
			G.INT32.power(i, a, b);
			assertEquals(pow(a.v(), i), b.v());
		}

	}
	
	private int pow(int base, int power) {
		int total = 1;
		for (int i = 0; i < power; i++) {
			total *= base;
		}
		return total;
	}
	
}
