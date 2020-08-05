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
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReduce {

	@Test
	public void test1() {
		
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(
				new double[] {1,2,3,4,5,6,7,8}
				);
		
		Float64Member reduction = G.DBL.construct();
		
		Reduce.compute(G.DBL, G.DBL.add(), nums, reduction);
		
		assertEquals(36, reduction.v(), 0);
		
		Reduce.compute(G.DBL, G.DBL.multiply(), nums, reduction);
		
		assertEquals(40320, reduction.v(), 0);
	}

	@Test
	public void test2() {
		
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(
				new double[] {5}
				);
		
		Float64Member reduction = G.DBL.construct();
		
		Reduce.compute(G.DBL, G.DBL.add(), nums, reduction);
		
		assertEquals(5, reduction.v(), 0);
		
		Reduce.compute(G.DBL, G.DBL.multiply(), nums, reduction);
		
		assertEquals(5, reduction.v(), 0);
	}

	@Test
	public void test3() {
		
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(
				new double[] {}
				);
		
		Float64Member reduction = G.DBL.construct();
		
		try {
			Reduce.compute(G.DBL, G.DBL.add(), nums, reduction);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			Reduce.compute(G.DBL, G.DBL.multiply(), nums, reduction);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
	}
}
