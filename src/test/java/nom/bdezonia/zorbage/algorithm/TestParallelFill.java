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
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestParallelFill {

	@Test
	public void test1() {
		Float64Member type = new Float64Member();
		
		IndexedDataSource<Float64Member> data = Storage.allocate(1000, type);
		
		ParallelFill.compute(G.DBL, new Float64Member(17.4), data);
		data.get(999, type);
		assertEquals(17.4, type.v(), 0);
	}
	
	@Test
	public void test2() {

		Float64Member type = new Float64Member();
		
		IndexedDataSource<Float64Member> data = Storage.allocate(1000, type);
		
		ParallelFill.compute(G.DBL, G.DBL.zero(), data);
		data.get(999, type);
		assertEquals(0, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.unity(), data);
		data.get(999, type);
		assertEquals(1, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.minBound(), data);
		data.get(999, type);
		assertEquals(-Double.MAX_VALUE, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.maxBound(), data);
		data.get(999, type);
		assertEquals(Double.MAX_VALUE, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.E(), data);
		data.get(999, type);
		assertEquals(2.7182818284590452353602874713526624, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.PI(), data);
		data.get(999, type);
		assertEquals(3.1415926535897932384626433832795028, type.v(), 0);
		
		ParallelFill.compute(G.DBL, G.DBL.random(), data);
		
		ParallelFill.compute(G.DBL, G.DBL.nan(), data);
		data.get(999, type);
		assertTrue(Double.isNaN(type.v()));
		
		ParallelFill.compute(G.DBL, G.DBL.infinite(), data);
		data.get(999, type);
		assertTrue(Double.isInfinite(type.v()));
		assertTrue(type.v() > 0);
		
		ParallelFill.compute(G.DBL, G.DBL.negInfinite(), data);
		data.get(999, type);
		assertTrue(Double.isInfinite(type.v()));
		assertTrue(type.v() < 0);

	}
	
}