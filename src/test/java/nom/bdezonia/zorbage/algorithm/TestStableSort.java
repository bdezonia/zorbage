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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestStableSort {

	@Test
	public void test1() {
		IndexedDataSource<SignedInt32Member> storage = Storage.allocate(G.INT32.construct(), 
				new int[] {6,3,99,-1,66,-50,0,0,3});
		SignedInt32Member value = G.INT32.construct();
		
		StableSort.compute(G.INT32, storage);
		storage.get(0, value);
		assertEquals(-50, value.v());
		storage.get(1, value);
		assertEquals(-1, value.v());
		storage.get(2, value);
		assertEquals(0, value.v());
		storage.get(3, value);
		assertEquals(0, value.v());
		storage.get(4, value);
		assertEquals(3, value.v());
		storage.get(5, value);
		assertEquals(3, value.v());
		storage.get(6, value);
		assertEquals(6, value.v());
		storage.get(7, value);
		assertEquals(66, value.v());
		storage.get(8, value);
		assertEquals(99, value.v());
		
		StableSort.compute(G.INT32, storage);
		storage.get(0, value);
		assertEquals(-50, value.v());
		storage.get(1, value);
		assertEquals(-1, value.v());
		storage.get(2, value);
		assertEquals(0, value.v());
		storage.get(3, value);
		assertEquals(0, value.v());
		storage.get(4, value);
		assertEquals(3, value.v());
		storage.get(5, value);
		assertEquals(3, value.v());
		storage.get(6, value);
		assertEquals(6, value.v());
		storage.get(7, value);
		assertEquals(66, value.v());
		storage.get(8, value);
		assertEquals(99, value.v());

		StableSort.compute(G.INT32, G.INT32.isGreaterEqual(), storage);
		storage.get(8, value);
		assertEquals(-50, value.v());
		storage.get(7, value);
		assertEquals(-1, value.v());
		storage.get(6, value);
		assertEquals(0, value.v());
		storage.get(5, value);
		assertEquals(0, value.v());
		storage.get(4, value);
		assertEquals(3, value.v());
		storage.get(3, value);
		assertEquals(3, value.v());
		storage.get(2, value);
		assertEquals(6, value.v());
		storage.get(1, value);
		assertEquals(66, value.v());
		storage.get(0, value);
		assertEquals(99, value.v());
	}
	
	@Test
	public void test2() {
		
		for (int i = 0; i < 25; i++) {
			
			Float64Member value = G.DBL.construct();
			
			IndexedDataSource<Float64Member> nums = Storage.allocate(value, 1000+i);
			
			Fill.compute(G.DBL, G.DBL.random(), nums);
			
			Procedure2<Float64Member, Float64Member> proc =
					new Procedure2<Float64Member, Float64Member>()
			{
				@Override
				public void call(Float64Member a, Float64Member b) {
					b.setV(a.v() - 0.5);
				}
			};
			
			Transform2.compute(G.DBL, proc, nums, nums);
			
			StableSort.compute(G.DBL, nums);
			assertTrue(IsSorted.compute(G.DBL, nums));
			
			StableSort.compute(G.DBL, G.DBL.isGreater(), nums);
			assertTrue(IsSorted.compute(G.DBL, G.DBL.isGreaterEqual(), nums));
		}
	}
}
