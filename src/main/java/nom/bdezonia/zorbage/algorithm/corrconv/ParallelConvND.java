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
package nom.bdezonia.zorbage.algorithm.corrconv;

import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.DimensionedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelConvND {

	// do not instantiate
	
	private ParallelConvND() { }
	
	/**
	 * 
	 * @param alg
	 * @param maxPieces
	 * @param indexer
	 * @param filter
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U>
		void compute(T alg, int maxPieces, Procedure4<DimensionedDataSource<U>,IntegerIndex,IntegerIndex,IntegerIndex> indexer, DimensionedDataSource<U> filter, DimensionedDataSource<U> a, DimensionedDataSource<U> b)
	{
		int numD = a.numDimensions();
		
		if (a == b)
			throw new IllegalArgumentException("source and dest datasets must be different");
		
		if (b.numDimensions() != numD)
			throw new IllegalArgumentException("source and dest have different number of dimensions!");
		
		if (filter.numDimensions() != numD)
			throw new IllegalArgumentException("filter and source have different number of dimensions!");
		
		if (filter.numElements() % 2 != 1)
			throw new IllegalArgumentException("filter dimensions should all be odd");

		int index = -1;
		long maxDim = -1;
		for (int i = 0; i < numD; i++) {
			long dim = a.dimension(i);
			if (dim > maxDim) {
				index = i;
				maxDim = dim;
			}
		}
		if (maxDim <= 0)
			throw new IllegalArgumentException("invalid data dimensions");
		
		long pieces = maxPieces;
		
		if (a.rawData().accessWithOneThread() || b.rawData().accessWithOneThread())
			pieces = 1;
		
		if (pieces > maxDim)
			pieces = maxDim; // 1 thread per piped
		
		if (pieces > Integer.MAX_VALUE)
			pieces = Integer.MAX_VALUE;

		final Thread[] threads = new Thread[(int)pieces];
		long start = 0;
		for (int i = 0; i < pieces; i++) {
			IntegerIndex minPt = new IntegerIndex(numD);
			IntegerIndex maxPt = new IntegerIndex(numD);
			for (int j = 0; j < numD; j++) {
				maxPt.set(j, a.dimension(j) - 1);
			}
			long end;
			// last piece?
			if (i == pieces-1) {
				end = maxDim-1;
			}
			else {
				end = start + (maxDim/pieces) - 1;
			}
			minPt.set(index, start);
			maxPt.set(index, end);
			Computer<T,U> computer = new Computer<T,U>(alg, numD, indexer, filter, a, b, minPt, maxPt);
			threads[i] = new Thread(computer);
			start = end + 1;
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException e) {
				throw new IllegalArgumentException("Thread execution error in ParallelCorrConvND");
			}
		}
	}
	
	private static class Computer<T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U>
		implements Runnable
	{
		private final T alg;
		private final int numD;
		private final DimensionedDataSource<U> filter;
		private final DimensionedDataSource<U> a;
		private final DimensionedDataSource<U> b;
		private final IntegerIndex dataMinPt;
		private final IntegerIndex dataMaxPt;
		private final Procedure4<DimensionedDataSource<U>,IntegerIndex,IntegerIndex,IntegerIndex> indexer;
		
		public Computer(T alg, int numD, Procedure4<DimensionedDataSource<U>,IntegerIndex,IntegerIndex,IntegerIndex> indexer, DimensionedDataSource<U> filter, DimensionedDataSource<U> a, DimensionedDataSource<U> b, IntegerIndex dataMinPt, IntegerIndex dataMaxPt) {
			this.alg = alg;
			this.numD = numD;
			this.indexer = indexer;
			this.filter = filter;
			this.a = a;
			this.b = b;
			this.dataMinPt = dataMinPt;
			this.dataMaxPt = dataMaxPt;
		}
		
		@Override
		public void run() {
			
			IntegerIndex filterMin = new IntegerIndex(numD);
			IntegerIndex filterMax = new IntegerIndex(numD);
			IntegerIndex dataPoint = new IntegerIndex(numD);
			IntegerIndex filterPoint = new IntegerIndex(numD);
			IntegerIndex pt = new IntegerIndex(numD);

			for (int i = 0; i < numD; i++) {
				filterMin.set(i, 0);
				filterMax.set(i, filter.dimension(i)-1);
			}
			
			U tmp = alg.construct();
			U f = alg.construct();
			U sum = alg.construct();
			SamplingIterator<IntegerIndex> dataPoints = GridIterator.compute(dataMinPt, dataMaxPt);
			SamplingIterator<IntegerIndex> filterPoints = GridIterator.compute(filterMin, filterMax);
			while (dataPoints.hasNext()) {
				dataPoints.next(dataPoint);
				alg.zero().call(sum);
				filterPoints.reset();
				while (filterPoints.hasNext()) {
					filterPoints.next(filterPoint);
					indexer.call(filter, dataPoint, filterPoint, pt);
					a.get(pt, tmp);
					filter.get(filterPoint, f);
					alg.multiply().call(tmp, f, tmp);
					alg.add().call(sum, tmp, sum);
				}
				b.set(dataPoint, sum);
			}
			
		}
		
	}
}
