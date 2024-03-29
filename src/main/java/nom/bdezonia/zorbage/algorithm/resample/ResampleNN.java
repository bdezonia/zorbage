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
package nom.bdezonia.zorbage.algorithm.resample;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.misc.DataSourceUtils;
import nom.bdezonia.zorbage.misc.ThreadingUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ResampleNN {

	// do not instantiate
	
	private ResampleNN() { }

	/**
	 * Resample one multidim dataset into another multidim dataset using the nearest neighbor
	 * values across the n-d space.
	 * 
	 * @param alg
	 * @param newDims
	 * @param input
	 * @param maxPieces
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, long[] newDims, DimensionedDataSource<U> input, int maxPieces)
	{
		int numD = input.numDimensions();
		
		if (newDims.length != numD)
			throw new IllegalArgumentException("mismatched dims in Resample");

		U value = alg.construct();
		
		DimensionedDataSource<U> output = DimensionedStorage.allocate(input.storageType(), value, newDims);

		int index = -1;
		long maxDim = -1;
		for (int i = 0; i < numD; i++) {
			long dim = newDims[i];
			if (dim > maxDim) {
				index = i;
				maxDim = dim;
			}
		}
		if (maxDim <= 0)
			throw new IllegalArgumentException("invalid data dimensions");
		
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(numD,
										input.rawData().accessWithOneThread() ||
										output.rawData().accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();

		if (pieces == 1) {
			
			long[] min = new long[numD];
			long[] max = new long[numD];
			for (int j = 0; j < numD; j++) {
				max[j] = newDims[j] - 1;
			}
			Runnable r = new Computer<T,U>(alg, newDims, min, max, input, output);
			r.run();
		}
		else {
			
			final Thread[] threads = new Thread[pieces];
			long start = 0;
			for (int i = 0; i < pieces; i++) {
				long[] min = new long[numD];
				long[] max = new long[numD];
				for (int j = 0; j < numD; j++) {
					max[j] = newDims[j] - 1;
				}
				long end;
				// last piece?
				if (i == pieces-1) {
					end = maxDim-1;
				}
				else {
					end = start + elemsPerPiece - 1;
				}
				min[index] = start;
				max[index] = end;
				Computer<T,U> computer = new Computer<T,U>(alg, newDims, min, max, input, output);
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
					throw new IllegalArgumentException("Thread execution error");
				}
			}
		}
		
		return output;
	}
	
	private static class Computer<T extends Algebra<T,U>, U> implements Runnable {
	
		private final int numD;
		private final long[] newDims;
		private final long[] min;
		private final long[] max;
		private final T alg;
		private final DimensionedDataSource<U> input;
		private final DimensionedDataSource<U> output;
		
		public Computer(T alg, long[] newDims, long[] min, long[] max, DimensionedDataSource<U> input, DimensionedDataSource<U> output) {
			this.numD = newDims.length;
			this.newDims = newDims;
			this.min = min;
			this.max = max;
			this.alg = alg;
			this.input = input;
			this.output = output;
		}
		
		@Override
		public void run() {
			U value = alg.construct();
			IntegerIndex inputPoint = new IntegerIndex(numD);
			IntegerIndex outputPoint = new IntegerIndex(numD);
			long[] inputDims = DataSourceUtils.dimensions(input);
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(min, max);
			while (iter.hasNext()) {
				iter.next(outputPoint);
				computeValue(alg, input, inputDims, inputPoint, newDims, outputPoint, value);
				output.set(outputPoint, value);
			}
		}
		
		private void computeValue(T alg, DimensionedDataSource<U> input, long[] inputDims, IntegerIndex inputPoint,
									long[] outputDims, IntegerIndex outputPoint, U outVal)
		{
			BigDecimal[] coords = new BigDecimal[numD];
			
			// find the in between point
			for (int i = 0; i < numD; i++) {
				coords[i] = BigDecimal.valueOf(outputPoint.get(i));
				coords[i] = coords[i].divide(BigDecimal.valueOf(outputDims[i]-1),HighPrecisionAlgebra.getContext());
				coords[i] = coords[i].multiply(BigDecimal.valueOf(inputDims[i]-1));
				// force rounding to nearest neighbor
				coords[i] = coords[i].add(BigDecimalUtils.ONE_HALF);
			}
			
			// get the base coord
			for (int i = 0; i < numD; i++) {
				// truncate the biased value
				inputPoint.set(i, coords[i].longValue());
			}
	
			// set the value
			input.get(inputPoint, outVal);
		}
	}
}
