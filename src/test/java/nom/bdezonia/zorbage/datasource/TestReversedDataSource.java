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
package nom.bdezonia.zorbage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReversedDataSource {

	@Test
	public void test() {
		
		SignedInt32Member value = G.INT32.construct();

		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5,6,7});
		ReversedDataSource<SignedInt32Member> revNums = new ReversedDataSource<>(nums);
		
		assertEquals(nums.size(), revNums.size());
		
		revNums.get(0, value);
		assertEquals(7, value.v());
		
		revNums.get(1, value);
		assertEquals(6, value.v());
		
		revNums.get(2, value);
		assertEquals(5, value.v());
		
		revNums.get(3, value);
		assertEquals(4, value.v());
		
		revNums.get(4, value);
		assertEquals(3, value.v());
		
		revNums.get(5, value);
		assertEquals(2, value.v());
		
		revNums.get(6, value);
		assertEquals(1, value.v());
		
		try {
			revNums.get(-1, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			revNums.get(7, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test2() {
		
		// Theoretical question: to reverse sort a list can I forw sort a reversed list?

		SignedInt32Member value = G.INT32.construct();

		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5,6,7});
		
		ReversedDataSource<SignedInt32Member> revNums = new ReversedDataSource<>(nums);
		
		Sort.compute(G.INT32, revNums);
		
		for (long i = 0; i < nums.size(); i++) {
			nums.get(i, value);
			assertEquals(nums.size()-i, value.v());
		}
	}
}
