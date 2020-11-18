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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestAverageTwo {

	@Test
	public void test1() {
		
		SignedInt8Member aVal = G.INT8.construct();
		SignedInt8Member bVal = G.INT8.construct();
		SignedInt8Member result = G.INT8.construct();
		for (int a = Byte.MIN_VALUE; a <= Byte.MAX_VALUE; a++) {
			aVal.setV(a);
			for (int b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
				bVal.setV(b);
				AverageTwo.compute(G.INT8, aVal, bVal, result);
				int testVal = (a >> 1) + (b >> 1) + (a&b&1);
				assertEquals(testVal, result.v());
			}
		}
	}

	@Test
	public void test2() {
		
		UnsignedInt8Member aVal = G.UINT8.construct();
		UnsignedInt8Member bVal = G.UINT8.construct();
		UnsignedInt8Member result = G.UINT8.construct();
		for (int a = 0; a <= 255; a++) {
			aVal.setV(a);
			for (int b = 0; b <= 255; b++) {
				bVal.setV(b);
				AverageTwo.compute(G.UINT8, aVal, bVal, result);
				int testVal = (a >> 1) + (b >> 1) + (a&b&1);
				assertEquals(testVal, result.v());
			}
		}
	}
}
