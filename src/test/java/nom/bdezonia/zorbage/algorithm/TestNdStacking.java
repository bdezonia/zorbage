/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.dataview.ThreeDView;
import nom.bdezonia.zorbage.dataview.TwoDView;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNdStacking {

	@Test
	public void test() {
		List<DimensionedDataSource<SignedInt32Member>> datasets;
		
		// no data passed
		datasets = new ArrayList<>();
		
		try {
			NdStacking.compute(G.INT32, datasets);
			fail("should not get here");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// mixed sizes
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,2}));
		}
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,2}));

		try {
			NdStacking.compute(G.INT32, datasets);
			fail("should not get here");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// mixed dimensionality
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,2}));
		}
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,2,2}));
		
		try {
			NdStacking.compute(G.INT32, datasets);
			fail("should not get here");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// some vanilla test case
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3}));
		}

		try {
			NdStacking.compute(G.INT32, datasets);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail("should not get here");
		}

		// now do an in depth value test on a constructed data source
		
		SignedInt32Member val = G.INT32.construct();
		
		TwoDView<SignedInt32Member> view;
		
		view = new TwoDView<>(2, 3, datasets.get(0).rawData());
		val.setV(1);
		view.set(0, 0, val);
		val.setV(2);
		view.set(1, 0, val);
		val.setV(3);
		view.set(0, 1, val);
		val.setV(4);
		view.set(1, 1, val);
		
		view = new TwoDView<>(2, 3, datasets.get(1).rawData());
		val.setV(5);
		view.set(0, 0, val);
		val.setV(6);
		view.set(1, 0, val);
		val.setV(7);
		view.set(0, 1, val);
		val.setV(8);
		view.set(1, 1, val);
		
		view = new TwoDView<>(2, 3, datasets.get(2).rawData());
		val.setV(9);
		view.set(0, 0, val);
		val.setV(10);
		view.set(1, 0, val);
		val.setV(11);
		view.set(0, 1, val);
		val.setV(12);
		view.set(1, 1, val);
		
		view = new TwoDView<>(2, 3, datasets.get(3).rawData());
		val.setV(13);
		view.set(0, 0, val);
		val.setV(14);
		view.set(1, 0, val);
		val.setV(15);
		view.set(0, 1, val);
		val.setV(16);
		view.set(1, 1, val);
		
		DimensionedDataSource<SignedInt32Member> stack = NdStacking.compute(G.INT32, datasets);
		
		assertEquals(3, stack.numDimensions());
		assertEquals(2, stack.dimension(0));
		assertEquals(3, stack.dimension(1));
		assertEquals(4, stack.dimension(2));
		
		ThreeDView<SignedInt32Member> view3 = new ThreeDView<>(2, 3, 4, stack.rawData());
		
		view3.get(0, 0, 0, val);
		assertEquals(1, val.v());
		
		view3.get(1, 0, 0, val);
		assertEquals(2, val.v());
		
		view3.get(0, 1, 0, val);
		assertEquals(3, val.v());
		
		view3.get(1, 1, 0, val);
		assertEquals(4, val.v());
		
		view3.get(0, 0, 1, val);
		assertEquals(5, val.v());
		
		view3.get(1, 0, 1, val);
		assertEquals(6, val.v());
		
		view3.get(0, 1, 1, val);
		assertEquals(7, val.v());
		
		view3.get(1, 1, 1, val);
		assertEquals(8, val.v());
		
		view3.get(0, 0, 2, val);
		assertEquals(9, val.v());
		
		view3.get(1, 0, 2, val);
		assertEquals(10, val.v());
		
		view3.get(0, 1, 2, val);
		assertEquals(11, val.v());
		
		view3.get(1, 1, 2, val);
		assertEquals(12, val.v());
		
		view3.get(0, 0, 3, val);
		assertEquals(13, val.v());
		
		view3.get(1, 0, 3, val);
		assertEquals(14, val.v());
		
		view3.get(0, 1, 3, val);
		assertEquals(15, val.v());
		
		view3.get(1, 1, 3, val);
		assertEquals(16, val.v());
		
		view3.get(0, 2, 0, val);
		assertEquals(0, val.v());
		
		view3.get(1, 2, 0, val);
		assertEquals(0, val.v());
		
		view3.get(0, 2, 1, val);
		assertEquals(0, val.v());
		
		view3.get(1, 2, 1, val);
		assertEquals(0, val.v());
		
		view3.get(0, 2, 2, val);
		assertEquals(0, val.v());
		
		view3.get(1, 2, 2, val);
		assertEquals(0, val.v());
		
		view3.get(0, 2, 3, val);
		assertEquals(0, val.v());
		
		view3.get(1, 2, 3, val);
		assertEquals(0, val.v());
		
		// edge case: one list passed
		
		datasets = new ArrayList<>();
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3}));

		stack = NdStacking.compute(G.INT32, datasets);
		
		assertEquals(3, stack.numDimensions());
		assertEquals(2, stack.dimension(0));
		assertEquals(3, stack.dimension(1));
		assertEquals(1, stack.dimension(2));
		
		// edge case: a bunch of one element datasets
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1}));
		}

		stack = NdStacking.compute(G.INT32, datasets);
		
		assertEquals(2, stack.numDimensions());
		assertEquals(1, stack.dimension(0));
		assertEquals(4, stack.dimension(1));
	}
}
