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

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

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
		
		nums = ArrayStorage.allocateDoubles(new double[] {});
		value.setV(5);
		assertEquals(-1, BinarySearchRight.compute(G.DBL, value, nums));

		nums = ArrayStorage.allocateDoubles(new double[] {1,2,3});
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

		nums = ArrayStorage.allocateDoubles(new double[] {1,1,2,2,2,3,3,3,3});
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

		nums = ArrayStorage.allocateDoubles(new double[] {0,1,1,2,2,2,3,3,3,3,4});
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
