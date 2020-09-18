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

import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MedianValues {

	private MedianValues() {}

	/**
	 * 
	 * @param alg
	 * @param storage
	 * @param result1
	 * @param result2
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U extends Allocatable<U>>
		void compute(T alg, IndexedDataSource<U> storage, U result1, U result2)
	{
		// Many IndexedDataSources only shallow copy on duplicate() call. To be safe
		// this method needs to do a deep copy because it will sort the data. So we
		// avoid sorting original data.
		IndexedDataSource<U> localStorage = DeepCopy.compute(alg, storage);
		// for now avoiding Sort because it is susceptible to quicksort worst case performance
		StableSort.compute(alg, localStorage);
		long localStorageSize = localStorage.size();
		if (localStorageSize == 0) {
			throw new IllegalArgumentException("MedianValues called on an empty list");
		}
		else if (localStorageSize % 2 == 0) {
			localStorage.get(localStorageSize/2 - 1, result1);
			localStorage.get(localStorageSize/2, result2);
		}
		else {
			localStorage.get(localStorageSize/2, result1);
			alg.assign().call(result1, result2);
		}
	}
}
