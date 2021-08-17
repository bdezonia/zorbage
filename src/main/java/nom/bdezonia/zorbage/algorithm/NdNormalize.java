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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdNormalize {

	/**
	 * Create a data source from one that might have some dimensions of size 1.
	 * The created data source does not have any dimensions of size 1 but
	 * removes them (thus having a lower dimensionality than the input data
	 * source). An example: an input data source of dims 300 x 200 x 1 x 100 x 1
	 * will be remapped to a data source of dimensions 300 x 200 x 100. All the
	 * original data values are preserved.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, DimensionedDataSource<U> a)
	{
		int count = 0;
		for (int d = 0; d < a.numDimensions(); d++) {
			if (a.dimension(d) > 1) {
				count++;
			}
		}
		
		long[] newDims;
		if (count == 0) {
			if (a.numDimensions() == 0) {
				newDims = new long[0];
			}
			else {
				newDims = new long[1];
				newDims[0] = 1;
			}
		}
		else {
			newDims = new long[count];
			int i = 0;
			for (int d = 0; d < a.numDimensions(); d++) {
				if (a.dimension(d) > 1) {
					newDims[i++] = a.dimension(d);
				}
			}
		}
		
		// TODO: this is really stupid. Instead just pass original rawdata and newdims
		//   to an n-d data constructor. The rawdata could be shared by two people this
		//   way if not careful. But it is way way faster!
		
		DimensionedDataSource<U> newData =
			DimensionedStorage.allocate(alg.construct(), newDims);
		
		Copy.compute(alg, a.rawData(), newData.rawData());

		return newData;
	}
}
