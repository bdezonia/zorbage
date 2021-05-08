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

import java.util.List;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

//TODO: no metadata is copied with this algorithm

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdStacking {

	/**
	 * Generate a data source by stacking data sources along a new axis (one dimension higher).
	 * The data sources each have the same shape and the same number of dimensions. The output
	 * data source lives in a space one dimension higher than the input sources.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param dataSources
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, List<DimensionedDataSource<U>> dataSources)
	{
		// check correctness of inputs
		
		if (dataSources.size() < 1)
			throw new IllegalArgumentException("No data sources given to concat algorithm");
		
		int numDims = dataSources.get(0).numDimensions();
		DimensionedDataSource<U> firstDs = dataSources.get(0);
		for (int i = 1; i < dataSources.size(); i++) {
			DimensionedDataSource<U> ds = dataSources.get(i);
			if (ds.numDimensions() != numDims)
				throw new IllegalArgumentException("Cannot mix dimensionality of input datasets");
			for (int d = 0; d < numDims; d++) {
				if (ds.dimension(d) != firstDs.dimension(d))
					throw new IllegalArgumentException("Input data sources are not all the same shape");
			}
		}
		
		// find the overall dimensions of the fully stacked data source

		long[] outputDims = new long[firstDs.numDimensions()+1];
		for (int d = 0; d < numDims; d++) {
			outputDims[d] = firstDs.dimension(d);
		}
		outputDims[numDims] = dataSources.size();
		
		// create the output data source that fits the combined dims
		
		DimensionedDataSource<U> output = DimensionedStorage.allocate(alg.construct(), outputDims);
		
		// now fill the data from the inputs

		U value = alg.construct();
		IntegerIndex idxI = new IntegerIndex(numDims);
		IntegerIndex idxO = new IntegerIndex(numDims+1);
		
		// for each input source

		for (int i = 0; i < dataSources.size(); i++) {

			// choose the plane into which the input data will be placed in the output data source
			
			idxO.set(numDims, i);
			
			// get current input source
			
			DimensionedDataSource<U> ds = dataSources.get(i);
			
			// iterate this single input source
			
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(ds);
			
			while (iter.hasNext()) {

				// find next input point
				
				iter.next(idxI);
				
				// get the value at the input point
				
				ds.get(idxI, value);
				
				// set the output point coords from the input point. remember dims+1 entry already set
				
				for (int d = 0; d < numDims; d++) {
					idxO.set(d, idxI.get(i));
				}

				// set the value at output point
				
				output.set(idxO, value);
			}
		}
		
		return output;
	}
}
