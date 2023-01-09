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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FlipAlongDimension {

	// do not instantiate
	
	private FlipAlongDimension() { }

	/**
	 * Changes a data source in place. Given an axis number the data in the data source
	 * is flipped relative to that dimension. For instance if y axis chosen then y=0 value
	 * goes to y=max position and y=max value goes to y=0 position. All values along the
	 * axis are "flipped" around the center position of that axis.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg The algebra that can manipulate the type of values in the data source.
	 * @param axisNumber The axis number to flip about.
	 * @param data The data source to munge in place.
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T alg, int axisNumber, DimensionedDataSource<U> data)
	{
		int numD = data.numDimensions();
		
		if (axisNumber < 0 || axisNumber >= numD) {
			
			throw new IllegalArgumentException("axis number out of bounds");
		}

		long[] shrunkenDims = new long[numD];

		for (int d = 0; d < numD; d++) {
		
			shrunkenDims[d] = data.dimension(d);
		}
		
		shrunkenDims[axisNumber] /= 2;
		
		if (data.dimension(axisNumber) % 2 == 1) {
		
			shrunkenDims[axisNumber]++;
		}
			
		// move the sample data
		
		IntegerIndex regularIndex = new IntegerIndex(numD);
		
		IntegerIndex flippedIndex = new IntegerIndex(numD);
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(shrunkenDims);
		
		U value1 = alg.construct();
		
		U value2 = alg.construct();
		
		while (iter.hasNext()) {
		
			iter.next(regularIndex);
			
			for (int d = 0; d < numD; d++) {
				flippedIndex.set(d, regularIndex.get(d));
			}
			
			flippedIndex.set(axisNumber, data.dimension(axisNumber) - regularIndex.get(axisNumber) - 1);
			
			data.get(regularIndex, value1);
			data.get(flippedIndex, value2);
			
			data.set(regularIndex, value2);
			data.set(flippedIndex, value1);
		}
	}
}
