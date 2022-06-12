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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestClamp {

	@Test
	public void test() {
		SignedInt64Member min = new SignedInt64Member();
		SignedInt64Member max = new SignedInt64Member();
		SignedInt64Member value = new SignedInt64Member();
		SignedInt64Member result = new SignedInt64Member();
		
		min.setV(-100);
		max.setV(200);
		
		value.setV(-99);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(-99, result.v());
		
		value.setV(199);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(199, result.v());
		
		value.setV(-100);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(-100, result.v());
		
		value.setV(200);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(200, result.v());
		
		value.setV(-101);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(-100, result.v());
		
		value.setV(201);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(200, result.v());
		
		value.setV(-99);
		Clamp.compute(G.INT64, min, max, value, result);
		assertEquals(-99, result.v());

		min.setV(max.v()+1);
		try {
			Clamp.compute(G.INT64, min, max, value, result);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
