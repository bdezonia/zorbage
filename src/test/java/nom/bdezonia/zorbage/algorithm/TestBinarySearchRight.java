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

import static org.junit.Assert.assertEquals;

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBinarySearchRight {

	@Test
	public void test() {
		
		Float64Member value = G.DBL.construct();
		
		IndexedDataSource<Float64Member> nums;
		
		nums = Storage.allocate(G.DBL.construct(), new double[] {});
		value.setV(5);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));

		nums = Storage.allocate(G.DBL.construct(), new double[] {1,2,3});
		value.setV(1);
		assertEquals(0, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(2);
		assertEquals(1, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(3);
		assertEquals(2, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(0);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(4);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));

		nums = Storage.allocate(G.DBL.construct(), new double[] {1,1,2,2,2,3,3,3,3});
		value.setV(1);
		assertEquals(1, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(2);
		assertEquals(4, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(3);
		assertEquals(8, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(0);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(4);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));

		nums = Storage.allocate(G.DBL.construct(), new double[] {0,1,1,2,2,2,3,3,3,3,4});
		value.setV(-1);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(0);
		assertEquals(0, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(1);
		assertEquals(2, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(2);
		assertEquals(5, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(3);
		assertEquals(9, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(4);
		assertEquals(10, BinarySearchRight.compute(G.DBL, value, nums));
		value.setV(5);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));
	}

}
