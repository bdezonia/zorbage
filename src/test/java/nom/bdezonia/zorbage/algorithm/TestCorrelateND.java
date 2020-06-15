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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;

import org.junit.Test;

import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.int64.SignedInt64Algebra;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCorrelateND {

	@Test
	public void test1() {

		SignedInt64Member value = G.INT64.construct();
		DimensionedDataSource<SignedInt64Member> ds = DimensionedStorage.allocate(new long[] {3,3}, value);
		ZeroNdOOB<SignedInt64Algebra,SignedInt64Member> oobProc = new ZeroNdOOB<>(G.INT64, ds);
		ProcedurePaddedDimensionedDataSource<SignedInt64Algebra,SignedInt64Member> padded = new ProcedurePaddedDimensionedDataSource<>(G.INT64, ds, oobProc);
		
		IntegerIndex idx = new IntegerIndex(ds.numDimensions());
		
		value.setV(1);
		idx.set(0, 0);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(2);
		idx.set(0, 1);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(3);
		idx.set(0, 2);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(4);
		idx.set(0, 0);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(5);
		idx.set(0, 1);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(6);
		idx.set(0, 2);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(7);
		idx.set(0, 0);
		idx.set(1, 2);
		ds.set(idx, value);
		
		value.setV(8);
		idx.set(0, 1);
		idx.set(1, 2);
		ds.set(idx, value);
		
		value.setV(9);
		idx.set(0, 2);
		idx.set(1, 2);
		ds.set(idx, value);
		
		DimensionedDataSource<SignedInt64Member> filter = DimensionedStorage.allocate(new long[] {3,3}, value);

		DimensionedDataSource<SignedInt64Member> out = DimensionedStorage.allocate(new long[] {3,3}, value);

		value.setV(1);
		
		idx.set(0, 0);
		idx.set(1, 0);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 0);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 0);
		filter.set(idx, value);
		
		idx.set(0, 0);
		idx.set(1, 1);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 1);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 1);
		filter.set(idx, value);
		
		idx.set(0, 0);
		idx.set(1, 2);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 2);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 2);
		filter.set(idx, value);

		CorrelateND.compute(G.INT64, filter, padded, out);

		idx.set(0, 0);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(12, value.v());
		
		idx.set(0, 1);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(21, value.v());
		
		idx.set(0, 2);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(16, value.v());
		
		idx.set(0, 0);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(27, value.v());
		
		idx.set(0, 1);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(45, value.v());
		
		idx.set(0, 2);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(33, value.v());
		
		idx.set(0, 0);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(24, value.v());
		
		idx.set(0, 1);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(39, value.v());
		
		idx.set(0, 2);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(28, value.v());
	}

	@Test
	public void test2() {

		SignedInt64Member value = G.INT64.construct();
		DimensionedDataSource<SignedInt64Member> ds = DimensionedStorage.allocate(new long[] {3,3}, value);
		ZeroNdOOB<SignedInt64Algebra,SignedInt64Member> oobProc = new ZeroNdOOB<>(G.INT64, ds);
		ProcedurePaddedDimensionedDataSource<SignedInt64Algebra,SignedInt64Member> padded = new ProcedurePaddedDimensionedDataSource<>(G.INT64, ds, oobProc);
		
		IntegerIndex idx = new IntegerIndex(ds.numDimensions());
		
		value.setV(1);
		idx.set(0, 0);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(2);
		idx.set(0, 1);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(3);
		idx.set(0, 2);
		idx.set(1, 0);
		ds.set(idx, value);
		
		value.setV(4);
		idx.set(0, 0);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(5);
		idx.set(0, 1);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(6);
		idx.set(0, 2);
		idx.set(1, 1);
		ds.set(idx, value);
		
		value.setV(7);
		idx.set(0, 0);
		idx.set(1, 2);
		ds.set(idx, value);
		
		value.setV(8);
		idx.set(0, 1);
		idx.set(1, 2);
		ds.set(idx, value);
		
		value.setV(9);
		idx.set(0, 2);
		idx.set(1, 2);
		ds.set(idx, value);
		
		DimensionedDataSource<SignedInt64Member> filter = DimensionedStorage.allocate(new long[] {3,3}, value);

		DimensionedDataSource<SignedInt64Member> out = DimensionedStorage.allocate(new long[] {3,3}, value);

		value.setV(1);
		
		idx.set(0, 0);
		idx.set(1, 0);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 0);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 0);
		filter.set(idx, value);
		
		value.setV(2);
		
		idx.set(0, 0);
		idx.set(1, 1);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 1);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 1);
		filter.set(idx, value);
		
		value.setV(3);
		
		idx.set(0, 0);
		idx.set(1, 2);
		filter.set(idx, value);
		
		idx.set(0, 1);
		idx.set(1, 2);
		filter.set(idx, value);
		
		idx.set(0, 2);
		idx.set(1, 2);
		filter.set(idx, value);

		CorrelateND.compute(G.INT64, filter, padded, out);

		idx.set(0, 0);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(2*1 + 2*2 + 3*4 + 3*5, value.v());
		
		idx.set(0, 1);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(2*1 + 2*2 + 2*3 + 3*4 + 3*5 + 3*6, value.v());
		
		idx.set(0, 2);
		idx.set(1, 0);
		out.get(idx, value);
		assertEquals(2*2 + 2*3 + 3*5 + 3*6, value.v());
		
		idx.set(0, 0);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(3*7 + 3*8 + 2*4 + 2*5 + 1*1 + 1*2, value.v());
		
		idx.set(0, 1);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(3*7 + 3*8 + 3*9 + 2*4 + 2*5 + 2*6 + 1*1 + 1*2 + 1*3, value.v());
		
		idx.set(0, 2);
		idx.set(1, 1);
		out.get(idx, value);
		assertEquals(3*8 + 3*9 + 2*5 + 2*6 + 1*2 + 1*3, value.v());
		
		idx.set(0, 0);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(2*7 + 2*8 + 1*4 + 1*5, value.v());
		
		idx.set(0, 1);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(2*7 + 2*8 + 2*9 + 1*4 + 1*5 + 1*6, value.v());
		
		idx.set(0, 2);
		idx.set(1, 2);
		out.get(idx, value);
		assertEquals(2*8 + 2*9 + 1*5 + 1*6, value.v());
	}
}
