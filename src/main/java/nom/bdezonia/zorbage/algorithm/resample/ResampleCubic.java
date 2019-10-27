/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm.resample;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ResampleCubic {

	private ResampleCubic() { }

	/**
	 * Cubicly resamples one multidim dataset into another multidim dataset using 4 points per axis.
	 * The algorithm computes a series of cubicly interpolated values from the four nearest points
	 * in a line along the axis. Then that series of values (1 per axis) is averaged.
	 * Note: The input datasource should be padded. This algorithm can poke outside the input boundaries.
	 * 
	 * @param alg
	 * @param newDims
	 * @param input
	 * @param maxPieces
	 * @return
	 */
	public static <T extends Algebra<T,U> & Addition<U> & nom.bdezonia.zorbage.type.algebra.ScaleByDouble<U>, 
					U extends Allocatable<U>>
		MultiDimDataSource<U> compute(T alg, long[] newDims, MultiDimDataSource<U> input, int maxPieces)
	{
		int numD = input.numDimensions();
		
		if (newDims.length != numD)
			throw new IllegalArgumentException("mismatched dims in Resample");

		U value = alg.construct();
		
		MultiDimDataSource<U> output = MultiDimStorage.allocate(newDims, value);

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
		
		long pieces = maxPieces;
		
		if (pieces > maxDim)
			pieces = maxDim; // 1 thread per piped
		
		if (pieces > Integer.MAX_VALUE)
			pieces = Integer.MAX_VALUE;

		final Thread[] threads = new Thread[(int)pieces];
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
				end = start + (maxDim/pieces) - 1;
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
				throw new IllegalArgumentException("Thread execution error in ParallelResampler");
			}
		}
		return output;
	}
	
	private static class Computer<T extends Algebra<T,U> & Addition<U> & nom.bdezonia.zorbage.type.algebra.ScaleByDouble<U>, U> implements Runnable {
	
		private final int numD;
		private final long[] newDims;
		private final long[] min;
		private final long[] max;
		private final T alg;
		private final MultiDimDataSource<U> input;
		private final MultiDimDataSource<U> output;
		
		public Computer(T alg, long[] newDims, long[] min, long[] max, MultiDimDataSource<U> input, MultiDimDataSource<U> output) {
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
			long[] inputDims = new long[numD];
			for (int i = 0; i < numD; i++) {
				inputDims[i] = input.dimension(i);
			}
			SamplingCartesianIntegerGrid sampling =
					new SamplingCartesianIntegerGrid(min, max);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			while (iter.hasNext()) {
				iter.next(outputPoint);
				computeValue(alg, input, inputDims, inputPoint, newDims, outputPoint, value);
				output.set(outputPoint, value);
			}
		}
		
		private void computeValue(T alg, MultiDimDataSource<U> input, long[] inputDims, IntegerIndex inputPoint,
								long[] outputDims, IntegerIndex outputPoint, U outVal)
		{
			int numD = inputDims.length;
			
			BigDecimal[] coords = new BigDecimal[numD];
			
			// find the in between point
			for (int i = 0; i < numD; i++) {
				coords[i] = BigDecimal.valueOf(outputPoint.get(i));
				coords[i] = coords[i].divide(BigDecimal.valueOf(outputDims[i]-1),HighPrecisionAlgebra.getContext());
				coords[i] = coords[i].multiply(BigDecimal.valueOf(inputDims[i]-1));
			}
			
			// get the base coord
			for (int i = 0; i < numD; i++) {
				inputPoint.set(i, coords[i].longValue());
			}
			
			// must find the various points and do a sum
			alg.zero().call(outVal);
			sum(alg, input, numD, coords, inputPoint, outVal);
			
			// now turn sum into average
			double scale = 1.0 / numD;
			alg.scaleByDouble().call(scale, outVal, outVal);
		}
		
		private void sum(T alg, MultiDimDataSource<U> input, int numD, BigDecimal[] coords,
								IntegerIndex inputPoint, U outVal)
		{
			U tmp = alg.construct();
			
			for (int d = 0; d < numD; d++) {
					
				// calc u
				BigDecimal t = coords[d].remainder(BigDecimal.ONE);
				
				cubicSolution(alg, input, inputPoint, d, t, tmp);
				
				// add to sum
				alg.add().call(outVal, tmp, outVal);
				
			}
		}
		
		// See https://dsp.stackexchange.com/questions/18265/bicubic-interpolation
		
		private void cubicSolution(T alg, MultiDimDataSource<U> input, IntegerIndex inputPoint,
									int dim, BigDecimal t, U outVal)
		{
			U ym1 = alg.construct();
			U y0 = alg.construct();
			U y1 = alg.construct();
			U y2 = alg.construct();
			
			input.get(inputPoint, y0);
			inputPoint.set(dim, inputPoint.get(dim) + 1);
			input.get(inputPoint, y1);
			inputPoint.set(dim, inputPoint.get(dim) + 1);
			input.get(inputPoint, y2);
			inputPoint.set(dim, inputPoint.get(dim) - 3);
			input.get(inputPoint, ym1);
			inputPoint.set(dim, inputPoint.get(dim) + 1);
			
			U a = alg.construct();
			U b = alg.construct();
			U c = alg.construct();
			U d = alg.construct();
	
			U tmp1 = alg.construct();
			U tmp2 = alg.construct();
			U tmp3 = alg.construct();
			U tmp4 = alg.construct();
			
			// f(x) = ax^3 + bx^2 + cx + d

			// find d
			alg.assign().call(y0, d);
			
			// find c
			alg.scaleByDouble().call(-0.5, ym1, tmp1);
			alg.scaleByDouble().call(0.5, y1, tmp2);
			alg.add().call(tmp1, tmp2, c);
			
			// find b
			alg.assign().call(ym1, tmp1);
			alg.scaleByDouble().call(-2.5, y0, tmp2);
			alg.scaleByDouble().call(2.0, y1, tmp3);
			alg.scaleByDouble().call(-0.5, y2, tmp4);
			alg.add().call(tmp1, tmp2, tmp1);
			alg.add().call(tmp3, tmp4, tmp3);
			alg.add().call(tmp1, tmp3, b);
			
			// find a
			alg.scaleByDouble().call(-0.5, ym1, tmp1);
			alg.scaleByDouble().call(1.5, y0, tmp2);
			alg.scaleByDouble().call(-1.5, y1, tmp3);
			alg.scaleByDouble().call(0.5, y2, tmp4);
			alg.add().call(tmp1, tmp2, tmp1);
			alg.add().call(tmp3, tmp4, tmp3);
			alg.add().call(tmp1, tmp3, a);
			
			// combine: f(x) = ax^3 + bx^2 + cx + d
			double x = t.doubleValue();
			alg.assign().call(d, tmp2);
			alg.scaleByDouble().call(x, c, tmp1);
			alg.add().call(tmp2, tmp1, tmp2);
			alg.scaleByDouble().call(x*x, b, tmp1);
			alg.add().call(tmp2, tmp1, tmp2);
			alg.scaleByDouble().call(x*x*x, a, tmp1);
			alg.add().call(tmp2, tmp1, tmp2);
			
			// assign the output value
			alg.assign().call(tmp2, outVal);
		}
	}
}
