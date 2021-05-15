/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.dataview.ThreeDView;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNdSplit {

	@Test
	public void test1() {
		
		DimensionedDataSource<SignedInt32Member> ds;
		SignedInt32Member value = G.INT32.construct();
		ThreeDView<SignedInt32Member> view3;
		
		ds = DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3,4});

		// along axis is negative
		
		try {
			NdSplit.compute(G.INT32, -1, 1, ds);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// along axis is beyond size of data source
		
		try {
			NdSplit.compute(G.INT32, 3, 1, ds);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// width is not positive
		
		try {
			NdSplit.compute(G.INT32, 2, 0, ds);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		

		// width does not evenly divide dimension along axis
		
		try {
			NdSplit.compute(G.INT32, 2, 3, ds);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// width does not evenly divide dimension along axis
		
		try {
			NdSplit.compute(G.INT32, 1, 2, ds);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// divide along last axis 1 plane at a time
		
		ds = DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3,4});

		view3 = new ThreeDView<>(ds);
		
		value.setV(1);
		view3.set(1, 1, 0, value);
		value.setV(2);
		view3.set(1, 1, 1, value);
		value.setV(3);
		view3.set(1, 1, 2, value);
		value.setV(4);
		view3.set(1, 1, 3, value);

		List<DimensionedDataSource<SignedInt32Member>> results =
				NdSplit.compute(G.INT32, 2, 1, ds);
		
		assertEquals(4, results.size());
		
		assertEquals(3, results.get(0).numDimensions());
		assertEquals(2, results.get(0).dimension(0));
		assertEquals(3, results.get(0).dimension(1));
		assertEquals(1, results.get(0).dimension(2));
		
		view3 = new ThreeDView<>(results.get(0));
		view3.get(1, 1, 0, value);
		assertEquals(1, value.v());
		
		assertEquals(3, results.get(1).numDimensions());
		assertEquals(2, results.get(1).dimension(0));
		assertEquals(3, results.get(1).dimension(1));
		assertEquals(1, results.get(1).dimension(2));
		
		view3 = new ThreeDView<>(results.get(1));
		view3.get(1, 1, 0, value);
		assertEquals(2, value.v());
		
		assertEquals(3, results.get(2).numDimensions());
		assertEquals(2, results.get(2).dimension(0));
		assertEquals(3, results.get(2).dimension(1));
		assertEquals(1, results.get(2).dimension(2));
		
		view3 = new ThreeDView<>(results.get(2));
		view3.get(1, 1, 0, value);
		assertEquals(3, value.v());
		
		assertEquals(3, results.get(3).numDimensions());
		assertEquals(2, results.get(3).dimension(0));
		assertEquals(3, results.get(3).dimension(1));
		assertEquals(1, results.get(3).dimension(2));
		
		view3 = new ThreeDView<>(results.get(3));
		view3.get(1, 1, 0, value);
		assertEquals(4, value.v());

		// divide along middle axis 2 planes at a time
		
		ds = DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,4,2});

		view3 = new ThreeDView<>(ds);
		
		value.setV(1);
		view3.set(2, 0, 0, value);
		value.setV(2);
		view3.set(2, 1, 0, value);
		value.setV(3);
		view3.set(2, 2, 0, value);
		value.setV(4);
		view3.set(2, 3, 0, value);

		results = NdSplit.compute(G.INT32, 1, 2, ds);
		
		assertEquals(2, results.size());
		
		assertEquals(3, results.get(0).numDimensions());
		assertEquals(3, results.get(0).dimension(0));
		assertEquals(2, results.get(0).dimension(1));
		assertEquals(2, results.get(0).dimension(2));
		
		view3 = new ThreeDView<>(results.get(0));
		view3.get(2, 0, 0, value);
		assertEquals(1, value.v());
		view3.get(2, 1, 0, value);
		assertEquals(2, value.v());
		
		assertEquals(3, results.get(1).numDimensions());
		assertEquals(3, results.get(1).dimension(0));
		assertEquals(2, results.get(1).dimension(1));
		assertEquals(2, results.get(1).dimension(2));
		
		view3 = new ThreeDView<>(results.get(1));
		view3.get(2, 0, 0, value);
		assertEquals(3, value.v());
		view3.get(2, 1, 0, value);
		assertEquals(4, value.v());

		// divide along first axis 3 planes at a time
		
		ds = DimensionedStorage.allocate(G.INT32.construct(), new long[] {6,3,2});

		view3 = new ThreeDView<>(ds);
		
		value.setV(1);
		view3.set(0, 0, 1, value);
		value.setV(2);
		view3.set(0, 1, 1, value);
		value.setV(3);
		view3.set(0, 2, 1, value);
		value.setV(4);
		view3.set(3, 0, 1, value);
		value.setV(5);
		view3.set(3, 1, 1, value);
		value.setV(6);
		view3.set(3, 2, 1, value);

		results = NdSplit.compute(G.INT32, 0, 3, ds);
		
		assertEquals(2, results.size());
		
		assertEquals(3, results.get(0).numDimensions());
		assertEquals(3, results.get(0).dimension(0));
		assertEquals(3, results.get(0).dimension(1));
		assertEquals(2, results.get(0).dimension(2));
		
		view3 = new ThreeDView<>(results.get(0));
		view3.get(0, 0, 1, value);
		assertEquals(1, value.v());
		view3.get(0, 1, 1, value);
		assertEquals(2, value.v());
		view3.get(0, 2, 1, value);
		assertEquals(3, value.v());
		
		assertEquals(3, results.get(1).numDimensions());
		assertEquals(3, results.get(1).dimension(0));
		assertEquals(3, results.get(1).dimension(1));
		assertEquals(2, results.get(1).dimension(2));
		
		view3 = new ThreeDView<>(results.get(1));
		view3.get(0, 0, 1, value);
		assertEquals(4, value.v());
		view3.get(0, 1, 1, value);
		assertEquals(5, value.v());
		view3.get(0, 2, 1, value);
		assertEquals(6, value.v());
	}
	
	@Test
	public void test2() {
		
		DimensionedDataSource<SignedInt32Member> ds =
			DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,4,5,6});
		
		Fill.compute(G.INT32, G.INT32.random(), ds.rawData());
		
		List<DimensionedDataSource<SignedInt32Member>> unstack =
				NdUnstacking.compute(G.INT32, ds);
		
		List<DimensionedDataSource<SignedInt32Member>> split =
				NdSplit.compute(G.INT32, 3, 1, ds);
		
		assertEquals(6, unstack.size());
		for (int i = 0; i < 6; i++) {
			DimensionedDataSource<SignedInt32Member> u = unstack.get(i);
			assertEquals(3, u.numDimensions());
			assertEquals(3, u.dimension(0));
			assertEquals(4, u.dimension(1));
			assertEquals(5, u.dimension(2));
		}
		
		assertEquals(6, split.size());
		for (int i = 0; i < 6; i++) {
			DimensionedDataSource<SignedInt32Member> s = split.get(i);
			assertEquals(4, s.numDimensions());
			assertEquals(3, s.dimension(0));
			assertEquals(4, s.dimension(1));
			assertEquals(5, s.dimension(2));
			assertEquals(1, s.dimension(3));
		}

		for (int i = 0; i < 6; i++) {
			DimensionedDataSource<SignedInt32Member> u = unstack.get(i);
			DimensionedDataSource<SignedInt32Member> s = split.get(i);
			assertTrue(Equal.compute(G.INT32, u.rawData(), s.rawData()));
		}
	}

}
