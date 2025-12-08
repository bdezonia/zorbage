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
import nom.bdezonia.zorbage.dataview.OneDView;
import nom.bdezonia.zorbage.dataview.TwoDView;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNdConcatenation {

	@Test
	public void test() {
		List<DimensionedDataSource<SignedInt32Member>> datasets;
		DimensionedDataSource<SignedInt32Member> ds;
		
		// no data passed
		datasets = new ArrayList<>();
		
		try {
			NdConcatenation.compute(G.INT32, 1, datasets);
			fail("should not get here");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// axis out of bounds
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,2}));
		}

		try {
			NdConcatenation.compute(G.INT32, 4, datasets);
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
			NdConcatenation.compute(G.INT32, 1, datasets);
			fail("should not get here");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// stack along x
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3}));
		}

		try {
			ds = NdConcatenation.compute(G.INT32, 0, datasets);
			assertEquals(2, ds.numDimensions());
			assertEquals(8, ds.dimension(0));
			assertEquals(3, ds.dimension(1));
		} catch (IllegalArgumentException e) {
			fail("should not get here");
		}

		// stack along y
		
		datasets = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3}));
		}

		try {
			ds = NdConcatenation.compute(G.INT32, 1, datasets);
			assertEquals(2, ds.numDimensions());
			assertEquals(2, ds.dimension(0));
			assertEquals(12, ds.dimension(1));
		} catch (IllegalArgumentException e) {
			fail("should not get here");
		}

		// now do an in depth value test on a constructed data source or two
		
		// one along x axis
		
		datasets = new ArrayList<>();
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1,3}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {2,3}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,3}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1,3}));

		SignedInt32Member val = G.INT32.construct();
		
		TwoDView<SignedInt32Member> view;
		
		view = new TwoDView<>(1, 3, datasets.get(0).rawData());
		val.setV(3);
		view.set(0, 2, val);
		val.setV(2);
		view.set(0, 1, val);
		val.setV(1);
		view.set(0, 0, val);
		
		view = new TwoDView<>(2, 3, datasets.get(1).rawData());
		val.setV(9);
		view.set(1, 2, val);
		val.setV(8);
		view.set(1, 1, val);
		val.setV(7);
		view.set(1, 0, val);
		val.setV(6);
		view.set(0, 2, val);
		val.setV(5);
		view.set(0, 1, val);
		val.setV(4);
		view.set(0, 0, val);
		
		view = new TwoDView<>(3, 3, datasets.get(2).rawData());
		val.setV(18);
		view.set(2, 2, val);
		val.setV(17);
		view.set(2, 1, val);
		val.setV(16);
		view.set(2, 0, val);
		val.setV(15);
		view.set(1, 2, val);
		val.setV(14);
		view.set(1, 1, val);
		val.setV(13);
		view.set(1, 0, val);
		val.setV(12);
		view.set(0, 2, val);
		val.setV(11);
		view.set(0, 1, val);
		val.setV(10);
		view.set(0, 0, val);
		
		view = new TwoDView<>(1, 3, datasets.get(3).rawData());
		val.setV(21);
		view.set(0, 2, val);
		val.setV(20);
		view.set(0, 1, val);
		val.setV(19);
		view.set(0, 0, val);
		
		// try along x
		
		ds = NdConcatenation.compute(G.INT32, 0, datasets);
		
		assertEquals(2, ds.numDimensions());
		assertEquals(7, ds.dimension(0));
		assertEquals(3, ds.dimension(1));
		
		TwoDView<SignedInt32Member> view2 = new TwoDView<>(7, 3, ds.rawData());

		// from 1st ds
		
		view2.get(0, 0, val);
		assertEquals(1, val.v());
		
		view2.get(0, 1, val);
		assertEquals(2, val.v());
		
		view2.get(0, 2, val);
		assertEquals(3, val.v());
		
		// from 2nd ds
		
		view2.get(1, 0, val);
		assertEquals(4, val.v());
		
		view2.get(1, 1, val);
		assertEquals(5, val.v());
		
		view2.get(1, 2, val);
		assertEquals(6, val.v());
		
		view2.get(2, 0, val);
		assertEquals(7, val.v());
		
		view2.get(2, 1, val);
		assertEquals(8, val.v());
		
		view2.get(2, 2, val);
		assertEquals(9, val.v());
		
		// from 3rd ds
		
		view2.get(3, 0, val);
		assertEquals(10, val.v());
		
		view2.get(3, 1, val);
		assertEquals(11, val.v());
		
		view2.get(3, 2, val);
		assertEquals(12, val.v());
		
		view2.get(4, 0, val);
		assertEquals(13, val.v());
		
		view2.get(4, 1, val);
		assertEquals(14, val.v());
		
		view2.get(4, 2, val);
		assertEquals(15, val.v());
		
		view2.get(5, 0, val);
		assertEquals(16, val.v());
		
		view2.get(5, 1, val);
		assertEquals(17, val.v());
		
		view2.get(5, 2, val);
		assertEquals(18, val.v());
		
		// from 4th ds
		
		view2.get(6, 0, val);
		assertEquals(19, val.v());
		
		view2.get(6, 1, val);
		assertEquals(20, val.v());
		
		view2.get(6, 2, val);
		assertEquals(21, val.v());

		// one along y axis
		
		datasets = new ArrayList<>();
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,1}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,2}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,3}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {3,1}));

		view = new TwoDView<>(3, 1, datasets.get(0).rawData());
		val.setV(3);
		view.set(2, 0, val);
		val.setV(2);
		view.set(1, 0, val);
		val.setV(1);
		view.set(0, 0, val);
		
		view = new TwoDView<>(3, 2, datasets.get(1).rawData());
		val.setV(9);
		view.set(2, 1, val);
		val.setV(8);
		view.set(1, 1, val);
		val.setV(7);
		view.set(0, 1, val);
		val.setV(6);
		view.set(2, 0, val);
		val.setV(5);
		view.set(1, 0, val);
		val.setV(4);
		view.set(0, 0, val);
		
		view = new TwoDView<>(3, 3, datasets.get(2).rawData());
		val.setV(18);
		view.set(2, 2, val);
		val.setV(17);
		view.set(1, 2, val);
		val.setV(16);
		view.set(0, 2, val);
		val.setV(15);
		view.set(2, 1, val);
		val.setV(14);
		view.set(1, 1, val);
		val.setV(13);
		view.set(0, 1, val);
		val.setV(12);
		view.set(2, 0, val);
		val.setV(11);
		view.set(1, 0, val);
		val.setV(10);
		view.set(0, 0, val);
		
		view = new TwoDView<>(3, 1, datasets.get(3).rawData());
		val.setV(21);
		view.set(2, 0, val);
		val.setV(20);
		view.set(1, 0, val);
		val.setV(19);
		view.set(0, 0, val);
		
		// try along y
		
		ds = NdConcatenation.compute(G.INT32, 1, datasets);
		
		assertEquals(2, ds.numDimensions());
		assertEquals(3, ds.dimension(0));
		assertEquals(7, ds.dimension(1));
		
		view2 = new TwoDView<>(3, 7, ds.rawData());

		// from 1st ds
		
		view2.get(0, 0, val);
		assertEquals(1, val.v());
		
		view2.get(1, 0, val);
		assertEquals(2, val.v());
		
		view2.get(2, 0, val);
		assertEquals(3, val.v());
		
		// from 2nd ds
		
		view2.get(0, 1, val);
		assertEquals(4, val.v());
		
		view2.get(1, 1, val);
		assertEquals(5, val.v());
		
		view2.get(2, 1, val);
		assertEquals(6, val.v());
		
		view2.get(0, 2, val);
		assertEquals(7, val.v());
		
		view2.get(1, 2, val);
		assertEquals(8, val.v());
		
		view2.get(2, 2, val);
		assertEquals(9, val.v());
		
		// from 3rd ds
		
		view2.get(0, 3, val);
		assertEquals(10, val.v());
		
		view2.get(1, 3, val);
		assertEquals(11, val.v());
		
		view2.get(2, 3, val);
		assertEquals(12, val.v());
		
		view2.get(0, 4, val);
		assertEquals(13, val.v());
		
		view2.get(1, 4, val);
		assertEquals(14, val.v());
		
		view2.get(2, 4, val);
		assertEquals(15, val.v());
		
		view2.get(0, 5, val);
		assertEquals(16, val.v());
		
		view2.get(1, 5, val);
		assertEquals(17, val.v());
		
		view2.get(2, 5, val);
		assertEquals(18, val.v());
		
		// from 4th ds
		
		view2.get(0, 6, val);
		assertEquals(19, val.v());
		
		view2.get(1, 6, val);
		assertEquals(20, val.v());
		
		view2.get(2, 6, val);
		assertEquals(21, val.v());

		// edge case: one list passed
		
		datasets = new ArrayList<>();
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1,3}));
		
		view = new TwoDView<>(1, 3, datasets.get(0).rawData());
		val.setV(3);
		view.set(0, 2, val);
		val.setV(2);
		view.set(0, 1, val);
		val.setV(1);
		view.set(0, 0, val);
		
		//   try along x
		
		ds = NdConcatenation.compute(G.INT32, 0, datasets);
		
		assertEquals(2, ds.numDimensions());
		assertEquals(1, ds.dimension(0));
		assertEquals(3, ds.dimension(1));
		
		view = new TwoDView<>(1, 3, ds.rawData());
		
		view.get(0, 0, val);
		assertEquals(1, val.v());
		view.get(0, 1, val);
		assertEquals(2, val.v());
		view.get(0, 2, val);
		assertEquals(3, val.v());

		//   try along y
		
		ds = NdConcatenation.compute(G.INT32, 1, datasets);
		
		assertEquals(2, ds.numDimensions());
		assertEquals(1, ds.dimension(0));
		assertEquals(3, ds.dimension(1));
		
		view = new TwoDView<>(1, 3, ds.rawData());
		
		view.get(0, 0, val);
		assertEquals(1, val.v());
		view.get(0, 1, val);
		assertEquals(2, val.v());
		view.get(0, 2, val);
		assertEquals(3, val.v());

		// edge case: a bunch of one element datasets

		datasets = new ArrayList<>();
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1}));
		datasets.add(DimensionedStorage.allocate(G.INT32.construct(), new long[] {1}));
		
		OneDView<SignedInt32Member> view1;
		
		view1 = new OneDView<>(datasets.get(0));
		val.setV(1);
		view1.set(0, val);
		view1 = new OneDView<>(datasets.get(1));
		val.setV(2);
		view1.set(0, val);
		view1 = new OneDView<>(datasets.get(2));
		val.setV(3);
		view1.set(0, val);
		
		ds = NdConcatenation.compute(G.INT32, 0, datasets);
		
		assertEquals(1, ds.numDimensions());
		assertEquals(3, ds.dimension(0));
		
		view1 = new OneDView<>(ds);
		
		view1.get(0, val);
		assertEquals(1, val.v());
		view1.get(1, val);
		assertEquals(2, val.v());
		view1.get(2, val);
		assertEquals(3, val.v());

		/*
		
		for (long r = view2.d1()-1; r >= 0; r--) {
			for (long c = 0; c < view2.d0(); c++) {
				view2.get(c, r, val);
				System.out.print(val.v()+", ");
			}
			System.out.println();
		}
		 */
	}

}
