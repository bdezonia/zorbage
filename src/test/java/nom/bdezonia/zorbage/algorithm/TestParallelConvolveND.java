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

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestParallelConvolveND {

	@Test
	public void test1() {
		Float64Member value = G.DBL.construct();
		DimensionedDataSource<Float64Member> filter = DimensionedStorage.allocate(value, new long[] {3, 3});
		DimensionedDataSource<Float64Member> a = DimensionedStorage.allocate(value, new long[] {400, 333});
		ZeroNdOOB<Float64Algebra, Float64Member> proc = new ZeroNdOOB<Float64Algebra, Float64Member>(G.DBL, a);
		ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member> padded = new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, a, proc);
		DimensionedDataSource<Float64Member> b1 = DimensionedStorage.allocate(value, new long[] {400, 333});
		DimensionedDataSource<Float64Member> b2 = DimensionedStorage.allocate(value, new long[] {400, 333});
		ParallelFill.compute(G.DBL, G.DBL.random(), a.rawData());
		IntegerIndex idx = new IntegerIndex(filter.numDimensions());
		idx.set(0, 0);
		idx.set(1, 0);
		value.setV(1);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 0);
		value.setV(2);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 0);
		value.setV(3);
		filter.set(idx, value);
		idx.set(0, 0);
		idx.set(1, 1);
		value.setV(4);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 1);
		value.setV(5);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 1);
		value.setV(6);
		filter.set(idx, value);
		idx.set(0, 0);
		idx.set(1, 2);
		value.setV(7);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 2);
		value.setV(8);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 2);
		value.setV(9);
		filter.set(idx, value);

		ConvolveND.compute(G.DBL, filter, padded, b1);
		ParallelConvolveND.compute(G.DBL, filter, padded, b2);
		
		IntegerIndex dataMin = new IntegerIndex(filter.numDimensions());
		IntegerIndex dataMax = new IntegerIndex(filter.numDimensions());
		for (int i = 0; i < a.numDimensions(); i++) {
			dataMax.set(i, a.dimension(i) - 1);
		}
		SamplingCartesianIntegerGrid dataBounds =
				new SamplingCartesianIntegerGrid(dataMin, dataMax);
		
		SamplingIterator<IntegerIndex> iter = dataBounds.iterator();
		
		Float64Member v1 = G.DBL.construct();
		Float64Member v2 = G.DBL.construct();
		while (iter.hasNext()) {
			iter.next(idx);
			b1.get(idx, v1);
			b2.get(idx, v2);
			assertEquals(v1.v(), v2.v(), 0);
		}
	}

	@Test
	public void test2() {
		Float64Member value = G.DBL.construct();
		DimensionedDataSource<Float64Member> filter = DimensionedStorage.allocate(value, new long[] {3, 3});
		DimensionedDataSource<Float64Member> a = DimensionedStorage.allocate(value, new long[] {400, 333});
		ZeroNdOOB<Float64Algebra, Float64Member> proc = new ZeroNdOOB<Float64Algebra, Float64Member>(G.DBL, a);
		ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, a, proc);
		DimensionedDataSource<Float64Member> b1 = DimensionedStorage.allocate(value, new long[] {400, 333});
		DimensionedDataSource<Float64Member> b2 = DimensionedStorage.allocate(value, new long[] {400, 333});
		ParallelFill.compute(G.DBL, G.DBL.random(), a.rawData());
		IntegerIndex idx = new IntegerIndex(filter.numDimensions());
		idx.set(0, 0);
		idx.set(1, 0);
		value.setV(1);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 0);
		value.setV(2);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 0);
		value.setV(3);
		filter.set(idx, value);
		idx.set(0, 0);
		idx.set(1, 1);
		value.setV(4);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 1);
		value.setV(5);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 1);
		value.setV(6);
		filter.set(idx, value);
		idx.set(0, 0);
		idx.set(1, 2);
		value.setV(7);
		filter.set(idx, value);
		idx.set(0, 1);
		idx.set(1, 2);
		value.setV(8);
		filter.set(idx, value);
		idx.set(0, 2);
		idx.set(1, 2);
		value.setV(9);
		filter.set(idx, value);

		int numTrials = 20;

		long t1 = System.currentTimeMillis();
		for (int i = 0; i < numTrials; i++) {
			ConvolveND.compute(G.DBL, filter, padded, b1);
		}
		long t2 = System.currentTimeMillis();
		for (int i = 0; i < numTrials; i++) {
			ParallelConvolveND.compute(G.DBL, filter, padded, b2);
		}
		long t3 = System.currentTimeMillis();
		
		System.out.println("Regular  convolveND : "+(t2-t1));
		System.out.println("Parallel convolveND : "+(t3-t2));
	}
}
