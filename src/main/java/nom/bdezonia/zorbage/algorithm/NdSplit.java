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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

// TODO: no metadata is copied with this algorithm

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdSplit {

	// do not instantiate
	
	private NdSplit() { }
	
	/**
	 * Generate data sources by splitting a data source along an axis. The data source is split
	 * along the axis at a specified width. The axis needs to be evenly divisible  by the width.
	 * The output data sources have the same number of dimensions that the input set has. You
	 * can think of this operation as cutting a data source apart along an axis.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param alongAxis
	 * @param width
	 * @param dataSource
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		List<DimensionedDataSource<U>> compute(T alg, int alongAxis, long width, DimensionedDataSource<U> dataSource)
	{
		// check correctness of inputs

		int numDims = dataSource.numDimensions();
		
		if (alongAxis < 0 || alongAxis >= numDims)
			throw new IllegalArgumentException("specified axis is outside dimensionality of input");
		
		if (width < 1)
			throw new IllegalArgumentException("target width of output pieces must be positive");
		
		if (dataSource.dimension(alongAxis) % width != 0)
			throw new IllegalArgumentException("axis splitting along is not evenly divisible by width");
			
		// find the overall dimensions of the split data sources

		long[] outDims = new long[numDims];
		for (int d = 0; d < numDims; d++) {
			outDims[d] = dataSource.dimension(d);
		}
		outDims[alongAxis] = width;

		// split each chunk into a data source
		
		List<DimensionedDataSource<U>> results = new ArrayList<>();
		for (long offset = 0; offset < dataSource.dimension(alongAxis); offset += width) {
		
			// create target data source
			
			DimensionedDataSource<U> output = DimensionedStorage.allocate(alg.construct(), outDims);
			
			// now fill the target from the input
			
			U val = alg.construct();
			IntegerIndex idx = new IntegerIndex(numDims);
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(outDims);
			while (iter.hasNext()) {
				
				// find index of next output point
				
				iter.next(idx);
				
				// transform the output point into input data source's coordinate space
				
				idx.set(alongAxis, idx.get(alongAxis) + offset);
				
				// get the value at the input point
				
				dataSource.get(idx, val);
				
				// transform the input point back to the output data source's coordinate space
				
				idx.set(alongAxis, idx.get(alongAxis) - offset);
				
				// set the value at output point
				
				output.set(idx, val);
			}
			
			results.add(output);
		}
		
		return results;
	}
}
