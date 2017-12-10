/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFactorial {

	@Test
	public void ints() {
		SignedInt32Member a = G.INT32.construct();
		SignedInt32Member b = G.INT32.construct();
		
		a.setV(-1);
		try {
			Factorial.compute(G.INT32, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		a.setV(0);
		Factorial.compute(G.INT32, a, b);
		assertEquals(1, b.v());
		
		a.setV(1);
		Factorial.compute(G.INT32, a, b);
		assertEquals(1, b.v());

		a.setV(2);
		Factorial.compute(G.INT32, a, b);
		assertEquals(2, b.v());

		a.setV(3);
		Factorial.compute(G.INT32, a, b);
		assertEquals(6, b.v());

		a.setV(6);
		Factorial.compute(G.INT32, a, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void longs() {
		SignedInt64Member a = G.INT64.construct();
		SignedInt64Member b = G.INT64.construct();
		
		a.setV(-1);
		try {
			Factorial.compute(G.INT64, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		a.setV(0);
		Factorial.compute(G.INT64, a, b);
		assertEquals(1, b.v());
		
		a.setV(1);
		Factorial.compute(G.INT64, a, b);
		assertEquals(1, b.v());

		a.setV(2);
		Factorial.compute(G.INT64, a, b);
		assertEquals(2, b.v());

		a.setV(3);
		Factorial.compute(G.INT64, a, b);
		assertEquals(6, b.v());

		a.setV(6);
		Factorial.compute(G.INT64, a, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void unboundInts() {
		UnboundedIntMember a = G.BIGINT.construct();
		UnboundedIntMember b = G.BIGINT.construct();
		
		a.setV(BigInteger.valueOf(-1));
		try {
			Factorial.compute(G.BIGINT, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		a.setV(BigInteger.valueOf(0));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(BigInteger.valueOf(1), b.v());
		
		a.setV(BigInteger.valueOf(1));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(BigInteger.valueOf(1), b.v());

		a.setV(BigInteger.valueOf(2));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(BigInteger.valueOf(2), b.v());

		a.setV(BigInteger.valueOf(3));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(BigInteger.valueOf(6), b.v());

		a.setV(BigInteger.valueOf(6));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(BigInteger.valueOf(720), b.v());

		a.setV(BigInteger.valueOf(45));
		Factorial.compute(G.BIGINT, a, b);
		assertEquals(new BigInteger("119622220865480194561963161495657715064383733760000000000"), b.v());
	}
	
	@Test
	public void doubles() {
		Float64Member a = G.DBL.construct();
		Float64Member b = G.DBL.construct();
		
		a.setV(-1);
		try {
			Factorial.compute(G.DBL, a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		a.setV(0);
		Factorial.compute(G.DBL, a, b);
		assertEquals(1, b.v(), 0);
		
		a.setV(1);
		Factorial.compute(G.DBL, a, b);
		assertEquals(1, b.v(), 0);

		a.setV(2);
		Factorial.compute(G.DBL, a, b);
		assertEquals(2, b.v(), 0);

		a.setV(3);
		Factorial.compute(G.DBL, a, b);
		assertEquals(6, b.v(), 0);

		a.setV(6);
		Factorial.compute(G.DBL, a, b);
		assertEquals(720, b.v(), 0);
		
		// TODO: this is a little weird. consider what is best behavior.
		// Trunc 4.5 to 4.0 and calc from there? Or leave as is? Or disallow
		// for reals? That seems too harsh. Could make the gamma function
		// and tell users to prefer that algorithm for reals but allow it
		// here.
		
		a.setV(4.5);
		Factorial.compute(G.DBL, a, b);
		assertEquals(59.0625, b.v(), 0);
	}
}
