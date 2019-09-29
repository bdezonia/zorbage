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
package nom.bdezonia.zorbage.algorithm;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ResampleNearestNeighbor {

	private ResampleNearestNeighbor() { }

	/**
	 * Resample one multidim dataset into another multidim dataset using the nearest neighbor.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param newDims
	 * @param input
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		MultiDimDataSource<U> compute(T alg, long[] newDims, MultiDimDataSource<U> input)
	{
		int numD = input.numDimensions();
		if (newDims.length != numD)
			throw new IllegalArgumentException("mismatched dims in Resample");
		U value = alg.construct();
		MultiDimDataSource<U> output = MultiDimStorage.allocate(newDims, value);
		IntegerIndex inputPoint = new IntegerIndex(numD);
		IntegerIndex outputPoint = new IntegerIndex(numD);
		long[] min = new long[numD];
		long[] max = new long[numD];
		long[] inputDims = new long[numD];
		for (int i = 0; i < numD; i++) {
			max[i] = newDims[i] - 1;
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
		return output;
	}
	
	private static  <T extends Algebra<T,U>, U>
		void computeValue(T alg, MultiDimDataSource<U> input, long[] inputDims, IntegerIndex inputPoint,
							long[] outputDims, IntegerIndex outputPoint, U outVal)
	{
		int numD = inputDims.length;
		
		BigDecimal[] coords = new BigDecimal[numD];
		
		// find the in between point
		for (int i = 0; i < numD; i++) {
			coords[i] = BigDecimal.valueOf(outputPoint.get(i));
			coords[i] = coords[i].divide(BigDecimal.valueOf(outputDims[i]-1),HighPrecisionAlgebra.getContext());
			coords[i] = coords[i].multiply(BigDecimal.valueOf(inputDims[i]-1));
			// force rounding to nearest neighbor
			coords[i] = coords[i].add(G.ONE_HALF);
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
