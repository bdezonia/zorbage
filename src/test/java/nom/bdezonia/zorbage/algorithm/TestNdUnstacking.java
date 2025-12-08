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

import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.dataview.OneDView;
import nom.bdezonia.zorbage.dataview.ThreeDView;
import nom.bdezonia.zorbage.dataview.TwoDView;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNdUnstacking {

	@Test
	public void test() {
		
		DimensionedDataSource<SignedInt32Member> data;
		List<DimensionedDataSource<SignedInt32Member>> results;
		SignedInt32Member value = G.INT32.construct();
		OneDView<SignedInt32Member> view1;
		TwoDView<SignedInt32Member> view2;
		ThreeDView<SignedInt32Member> view3;
		
		data = DimensionedStorage.allocate(G.INT32.construct(), new long[] {4});
		try {
			results = NdUnstacking.compute(G.INT32, data);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		data = DimensionedStorage.allocate(G.INT32.construct(), new long[] {4, 3});
		
		view2 = new TwoDView<>(data);
		
		value.setV(1);
		view2.set(3, 0, value);
		value.setV(2);
		view2.set(3, 1, value);
		value.setV(3);
		view2.set(3, 2, value);
		
		results = NdUnstacking.compute(G.INT32, data);
		
		assertEquals(3, results.size());
		
		for (int i = 0; i < 3; i++) {
			
			assertEquals(1, results.get(i).numDimensions());
			assertEquals(4, results.get(i).dimension(0));
			
			view1 = new OneDView<>(results.get(i));
			
			view1.get(3, value);
			
			assertEquals(i+1, value.v());
		}

		data = DimensionedStorage.allocate(G.INT32.construct(), new long[] {4, 2, 3});
		
		view3 = new ThreeDView<>(data);
		
		value.setV(1);
		view3.set(3, 1, 0, value);
		value.setV(2);
		view3.set(3, 1, 1, value);
		value.setV(3);
		view3.set(3, 1, 2, value);
		
		results = NdUnstacking.compute(G.INT32, data);
		
		assertEquals(3, results.size());
		
		for (int i = 0; i < 3; i++) {
			
			assertEquals(2, results.get(i).numDimensions());
			assertEquals(4, results.get(i).dimension(0));
			assertEquals(2, results.get(i).dimension(1));
			
			view2 = new TwoDView<>(results.get(i));
			
			view2.get(3, 1, value);
			
			assertEquals(i+1, value.v());
		}

	}

}
