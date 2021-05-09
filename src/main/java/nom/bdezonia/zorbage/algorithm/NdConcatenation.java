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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.oob.nd.ConstantNdOOB;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

// TODO: no metadata is copied with this algorithm

// TODO: I could use some ancillary data structures and algos to omprove the speed of this code

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdConcatenation {

	/**
	 * Generate a data source by concatenating data sources along an axis. The data sources can
	 * have different shapes but must have the same number of dimensions. The output data source
	 * has dimensions that engulf the dimensions of all the other data sources. A nodata value
	 * is needed for when one input data source is smaller than the others and gets queried
	 * outside its bounds.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param nodataValue
	 * @param alongAxis
	 * @param dataSources
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, U nodataValue, int alongAxis, List<DimensionedDataSource<U>> dataSources)
	{
		// check correctness of inputs
		
		if (dataSources.size() < 1)
			throw new IllegalArgumentException("No data sources given to concat algorithm");
		
		int numDims = dataSources.get(0).numDimensions();
		
		if (alongAxis < 0 || alongAxis >= numDims)
			throw new IllegalArgumentException("specified axis is outside dimensionality of inputs");

		BigInteger axisSize = BigInteger.valueOf(dataSources.get(0).dimension(alongAxis));

		for (int i = 1; i < dataSources.size(); i++) {
			DimensionedDataSource<U> ds = dataSources.get(i);
			if (ds.numDimensions() != numDims)
				throw new IllegalArgumentException("Cannot mix dimensionality of input datasets");
			axisSize = axisSize.add(BigInteger.valueOf(ds.dimension(alongAxis)));
		}

		if (axisSize.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
			throw new IllegalArgumentException("proposed concatenation would result in a axis dimension that exceeds Long.MAX_VALUE");
			
		// find the overall dimensions of the fully concatenated data source

		//   start with the first data source and init counting variables
		
		DimensionedDataSource<U> ds = dataSources.get(0);
		long sumDim = ds.dimension(alongAxis);
		long[] maxDims = new long[numDims];
		for (int d = 0; d < numDims; d++) {
			maxDims[d] = ds.dimension(d);
		}
		
		//   for each data source after the first update the max overall sizes
		
		for (int i = 1; i < dataSources.size(); i++) {
			ds = dataSources.get(i);
			sumDim += ds.dimension(alongAxis);
			for (int d = 0; d < numDims; d++) {
				if (ds.dimension(d) > maxDims[d])
					maxDims[d] = ds.dimension(d);
			}
		}
		
		// create the output data source that fits the combined dims
		
		long[] outputDims = maxDims.clone();
		outputDims[alongAxis] = sumDim;
		DimensionedDataSource<U> output = DimensionedStorage.allocate(nodataValue, outputDims);
		
		// pad the input data sources with the nodata value
		//   The input data sources share the same dimension but their extents can be different
		
		List<DimensionedDataSource<U>> paddeds = new ArrayList<>();
		for (int i = 0; i < dataSources.size(); i++) {
			DimensionedDataSource<U> data = dataSources.get(i);
			Procedure2<IntegerIndex,U> oobProc = new ConstantNdOOB<T,U>(alg, data, nodataValue);
			paddeds.add(new ProcedurePaddedDimensionedDataSource<T,U>(alg, data, oobProc));
		}
		
		// calc a way to identify which input DS to query when moving alongAxis in the output DS
		
		long[] offsets = new long[dataSources.size()];
		offsets[0] = 0;
		for (int i = 1; i < dataSources.size(); i++) {
			long prevOffset = offsets[i-1];
			long offset = 0;
			DimensionedDataSource<U> currDs = dataSources.get(i);
			for (int d = 0; d < numDims; d++) {
				offset = prevOffset + currDs.dimension(d);
			}
			offsets[i] = offset;
		}

		// now fill the data from the inputs using the nodata value when needed
		
		U val = alg.construct();
		IntegerIndex idx = new IntegerIndex(numDims);
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(output);
		while (iter.hasNext()) {
			
			// find index of next output point
			
			iter.next(idx);
			
			// find the input DS associated with the value of the coordinate alongAxis
			
			long axisVal = idx.get(alongAxis);
			int dsNum = -1;
			for (int i = 1; i < offsets.length; i++) {
				long offset = offsets[i];
				if (axisVal < offset) {
					dsNum = i;
					break;
				}
			}
			
			// transform the output point into input data source's coordinate space
			
			long offset = offsets[dsNum];
			idx.set(alongAxis, idx.get(alongAxis) - offset);
			
			// get the value at the input point
			
			paddeds.get(dsNum).get(idx, val);
			
			// transform the input point back to the output data source's coordinate space
			
			for (int i = 0; i < numDims; i++) {
				idx.set(alongAxis, idx.get(alongAxis) + offset);
			}
			
			// set the value at output point
			
			output.set(idx, val);
		}
		
		return output;
	}
}
