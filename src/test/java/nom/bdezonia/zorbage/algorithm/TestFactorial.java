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

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.data.intunlim.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFactorial {

	@Test
	public void ints() {
		SignedInt32Member b = G.INT32.construct();
		
		try {
			Factorial.compute(G.INT32, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.INT32, 0, b);
		assertEquals(1, b.v());
		
		Factorial.compute(G.INT32, 1, b);
		assertEquals(1, b.v());

		Factorial.compute(G.INT32, 2, b);
		assertEquals(2, b.v());

		Factorial.compute(G.INT32, 3, b);
		assertEquals(6, b.v());

		Factorial.compute(G.INT32, 6, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void longs() {
		SignedInt64Member b = G.INT64.construct();
		
		try {
			Factorial.compute(G.INT64, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.INT64, 0, b);
		assertEquals(1, b.v());
		
		Factorial.compute(G.INT64, 1, b);
		assertEquals(1, b.v());

		Factorial.compute(G.INT64, 2, b);
		assertEquals(2, b.v());

		Factorial.compute(G.INT64, 3, b);
		assertEquals(6, b.v());

		Factorial.compute(G.INT64, 6, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void unboundInts() {
		UnboundedIntMember b = G.INT_UNLIM.construct();
		
		try {
			Factorial.compute(G.INT_UNLIM, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.INT_UNLIM, 0, b);
		assertEquals(BigInteger.valueOf(1), b.v());
		
		Factorial.compute(G.INT_UNLIM, 1, b);
		assertEquals(BigInteger.valueOf(1), b.v());

		Factorial.compute(G.INT_UNLIM, 2, b);
		assertEquals(BigInteger.valueOf(2), b.v());

		Factorial.compute(G.INT_UNLIM, 3, b);
		assertEquals(BigInteger.valueOf(6), b.v());

		Factorial.compute(G.INT_UNLIM, 6, b);
		assertEquals(BigInteger.valueOf(720), b.v());

		Factorial.compute(G.INT_UNLIM, 45, b);
		assertEquals(new BigInteger("119622220865480194561963161495657715064383733760000000000"), b.v());
	}
	
	@Test
	public void doubles() {
		Float64Member b = G.DBL.construct();
		
		try {
			Factorial.compute(G.DBL, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.DBL, 0, b);
		assertEquals(1, b.v(), 0);
		
		Factorial.compute(G.DBL, 1, b);
		assertEquals(1, b.v(), 0);

		Factorial.compute(G.DBL, 2, b);
		assertEquals(2, b.v(), 0);

		Factorial.compute(G.DBL, 3, b);
		assertEquals(6, b.v(), 0);

		Factorial.compute(G.DBL, 6, b);
		assertEquals(720, b.v(), 0);
	}
}
