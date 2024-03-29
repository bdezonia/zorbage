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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNextPermutation {

	@Test
	public void test1() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> nums;
		
		nums = Storage.allocate(G.INT32.construct(), new int[] {1,2});

		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		
		assertFalse(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
	}

	@Test
	public void test2() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> nums;
		
		nums = Storage.allocate(G.INT32.construct(), new int[] {1,2,3});
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		
		assertFalse(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
	}
	
	@Test
	public void test3() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> nums;
		
		nums = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4});
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(2, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(4, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(3, value.v());
		nums.get(1, value);
		assertEquals(4, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(1, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(3, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(1, value.v());
		nums.get(3, value);
		assertEquals(2, value.v());
		
		assertTrue(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(4, value.v());
		nums.get(1, value);
		assertEquals(3, value.v());
		nums.get(2, value);
		assertEquals(2, value.v());
		nums.get(3, value);
		assertEquals(1, value.v());
		
		assertFalse(NextPermutation.compute(G.INT32, nums));
		nums.get(0, value);
		assertEquals(1, value.v());
		nums.get(1, value);
		assertEquals(2, value.v());
		nums.get(2, value);
		assertEquals(3, value.v());
		nums.get(3, value);
		assertEquals(4, value.v());
	}
}
