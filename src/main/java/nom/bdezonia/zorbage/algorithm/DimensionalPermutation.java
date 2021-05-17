/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.coordinates.IdentityCoordinateSpace;
import nom.bdezonia.zorbage.coordinates.LinearNdCoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
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
	 * Create a {@link DimensionedDataSource} whose data and axes are permuted
	 * from an input source as specified as a permutation argument.
	 * 
	 * The permutation argument is an array of integers. It maps the positions
	 * of the input axes to new positions in the output data source.
	 * 
	 * Example: [3,1,0,2] says:
	 *   - the input is 4d
	 *   - dimension 0 in the input should be dimension 3 in the output
	 *   - dimension 1 in the input should be dimension 1 in the output
	 *   - dimension 2 in the input should be dimension 0 in the output
	 *   - dimension 3 in the input should be dimension 2 in the output
	 * 
	 * @param alg
	 * @param permutation
	 * @param input
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, int[] permutation, DimensionedDataSource<U> input)
	{
		// make sure it is a space we know how to permute
		
		if (!(input.getCoordinateSpace() instanceof IdentityCoordinateSpace) &&
			!(input.getCoordinateSpace() instanceof LinearNdCoordinateSpace))
		
		{
			// some random space : we can't permute coordinate space
			
			throw new IllegalArgumentException("do not know how to permute this coordinate space");
		}

		int numD = input.numDimensions();
		
		long[] origDims = new long[numD];
		for (int i = 0; i < numD; i++) {
			origDims[i] = input.dimension(i);
		}
		
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
				DimensionedStorage.allocate(input.storageType(), value, newDims);
		
		// copy some metadata
		
		output.setName(input.getName());
		output.setValueType(input.getValueType());
		output.setValueUnit(input.getValueUnit());
		if (input.getCoordinateSpace() instanceof IdentityCoordinateSpace)
		{
			for (int i = 0; i < numD; i++) {
				output.setAxisType(permutation[i], input.getAxisType(i));
				output.setAxisUnit(permutation[i], input.getAxisUnit(i));
			}
			output.setCoordinateSpace(new IdentityCoordinateSpace(numD));
		}
		else if (input.getCoordinateSpace() instanceof LinearNdCoordinateSpace)
		{
			LinearNdCoordinateSpace inSpace = (LinearNdCoordinateSpace) input.getCoordinateSpace();
			BigDecimal[] scales = new BigDecimal[numD];
			BigDecimal[] offsets = new BigDecimal[numD];
			for (int i = 0; i < numD; i++) {
				scales[permutation[i]] = inSpace.getScale(i);
				offsets[permutation[i]] = inSpace.getOffset(i);
				output.setAxisType(permutation[i], input.getAxisType(i));
				output.setAxisUnit(permutation[i], input.getAxisUnit(i));
			}
			output.setCoordinateSpace(new LinearNdCoordinateSpace(scales, offsets));
		}
		
		// copy all the sample data
		
		IntegerIndex inputIndex = new IntegerIndex(numD);
		IntegerIndex outputIndex = new IntegerIndex(numD);
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(origDims);
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
