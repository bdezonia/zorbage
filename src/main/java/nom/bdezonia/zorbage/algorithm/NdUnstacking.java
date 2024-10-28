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
package nom.bdezonia.zorbage.algorithm;

import java.util.ArrayList;
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
public class NdUnstacking {
	
	// do not instantiate
	
	private NdUnstacking() { }

	/**
	 * Generate a list of data sources by unstacking a data source along it's outermost axis.
	 * The data sources each live in a space one dimension lower (e.g. unstack a 3d data source
	 * into a set of 2d data sources). The data sources each have the same shape and the same
	 * number of dimensions.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param dataSource
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		List<DimensionedDataSource<U>> compute(T alg, DimensionedDataSource<U> dataSource)
	{
		// check correctness of inputs
		
		int numDims = dataSource.numDimensions();
		
		if (numDims < 2)
			throw new IllegalArgumentException("cannot unstack data sources with less than 2 dimensions");

		// find the overall dimensions of the fully stacked data source

		long[] outputDims = new long[numDims - 1];
		for (int d = 0; d < outputDims.length; d++) {
			outputDims[d] = dataSource.dimension(d);
		}
		
		// generate each slice as an output
		
		List<DimensionedDataSource<U>> outputs = new ArrayList<>();
		
		for (long i = 0; i < dataSource.dimension(dataSource.numDimensions()-1); i++) {
			
			// create an output data source

			DimensionedDataSource<U> oneOutput = DimensionedStorage.allocate(alg.construct(), outputDims);

			// now fill the data from the inputs

			U value = alg.construct();
			IntegerIndex idxI = new IntegerIndex(numDims);
			IntegerIndex idxO = new IntegerIndex(numDims-1);
			
			// choose the plane from which the input data will be placed in the output data source
			
			idxI.set(numDims-1, i);
		
			// iterate this single input source
			
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(outputDims);
			
			while (iter.hasNext()) {

				// find the next output point
				
				iter.next(idxO);
				
				// set the input point coords from the output point. remember numDims entry already set
				
				for (int d = 0; d < idxO.numDimensions(); d++) {
					idxI.set(d, idxO.get(d));
				}
				
				// get the value at the input point
				
				dataSource.get(idxI, value);
				

				// set the value at output point
				
				oneOutput.set(idxO, value);
			}
			
			outputs.add(oneOutput);
		}
		
		return outputs;
	}
}
