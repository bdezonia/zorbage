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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DimensionalPermutation {

	// do not instantiate
	
	private DimensionalPermutation() { }
	
	/**
	 * 
	 * @param alg
	 * @param permutation
	 * @param input
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, int[] permutation, DimensionedDataSource<U> input)
	{
		int numD = input.numDimensions();
		
		// test the correctness of the input data
		
		if (permutation.length != numD)
			throw new IllegalArgumentException("permutation dim count does not match input dataset");
		int[] counts = new int[numD];
		for (int i = 0; i < numD; i++) {
			int dim = permutation[i];
			if (dim < 0 || dim >= numD)
				throw new IllegalArgumentException("a permutation dim is outside the dimensional bounds of the input dataset");
			counts[dim]++;
		}
		for (int i = 0; i < numD; i++) {
			if (counts[i] != 1)
				throw new IllegalArgumentException("a permutation dim was specified more than once");
		}
		
		// create the output data
		
		long[] newDims = new long[numD];
		for (int i = 0; i < numD; i++) {
			newDims[permutation[i]] = input.dimension(i);
		}
		U value = alg.construct();
		DimensionedDataSource<U> output =
				DimensionedStorage.allocate(input.storageType(), newDims, value);
		
		// copy some metadata
		
		output.setValueType(input.getValueType());
		output.setValueUnit(input.getValueUnit());
		for (int i = 0; i < numD; i++) {
			output.setAxisEquation(permutation[i], input.getAxisEquation(i));
			output.setAxisType(permutation[i], input.getAxisType(i));
			output.setAxisUnit(permutation[i], input.getAxisUnit(i));
		}
		
		// copy all the sample data
		
		long[] minPt = new long[numD];
		long[] maxPt = new long[numD];
		for (int i = 0; i < numD; i++) {
			maxPt[i] = input.dimension(i) - 1;
		}
		IntegerIndex inputIndex = new IntegerIndex(numD);
		IntegerIndex outputIndex = new IntegerIndex(numD);
		SamplingCartesianIntegerGrid grid = new SamplingCartesianIntegerGrid(minPt, maxPt);
		SamplingIterator<IntegerIndex> iter = grid.iterator();
		while (iter.hasNext()) {
			iter.next(inputIndex);
			for (int i = 0; i < numD; i++) {
				outputIndex.set(permutation[i], inputIndex.get(i));
			}
			input.get(inputIndex, value);
			output.set(outputIndex, value);
		}
		
		// return the result
		
		return output;
	}
}
