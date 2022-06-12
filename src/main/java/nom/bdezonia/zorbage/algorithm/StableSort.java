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

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class StableSort {

	// do not instantiate
	
	private StableSort() { }

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param storage
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U extends Allocatable<U>>
		void compute(T alg, IndexedDataSource<U> storage)
	{
		compute(alg, alg.isLessEqual(), storage);
	}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param lessOrEqual
	 * @param storage
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		void compute(T alg, Function2<Boolean,U,U> lessOrEqual, IndexedDataSource<U> storage)
	{
		bottomUpMergeSort(alg, lessOrEqual, storage);
	}

	private static <T extends Algebra<T,U>, U extends Allocatable<U>>
		void bottomUpMergeSort(T alg, Function2<Boolean,U,U> leftOfEqual, IndexedDataSource<U> storage)
	{
		long n = storage.size();
		
		U type = alg.construct();
		IndexedDataSource<U> tmp = Storage.allocate(type, n);
		
		IndexedDataSource<U> a = storage;
		IndexedDataSource<U> b = tmp;
		IndexedDataSource<U> lastDest = null;
		
		// array A has the items to sort; array B is a work array

		// Each 1-element run in A is already "sorted".
		// Make successively longer sorted runs of length 2, 4, 8, 16... until whole array is sorted.
		for (long width = 1; width < n; width *= 2)
		{
			// Array A is full of runs of length width.
			for (long i = 0; i < n; i += 2*width)
			{
				// Merge two runs: A[i:i+width-1] and A[i+width:i+2*width-1] to B[]
				// or copy A[i:n-1] to B[] ( if (i+width >= n) )
				bottomUpMerge(alg, leftOfEqual, i, Math.min(i+width, n), Math.min(i+2*width, n), a, b);
			}
			// Now work array B is full of runs of length 2*width.
			// Swap the roles of A and B.
			lastDest = b;
			b = a;
			a = lastDest;
			// Now array A is full of runs of length 2*width.
		}
		// if on the last iteration the storage array was copied to the tmp array
		if (lastDest == tmp)
			Copy.compute(alg, tmp, storage);
	}

	// Left run is A[iLeft :iRight-1].
	// Right run is A[iRight:iEnd-1].
	private static <T extends Algebra<T,U>, U>
		void bottomUpMerge(T alg, Function2<Boolean,U,U> lessOrEqual, long iLeft, long iRight, long iEnd, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		U tmpI = alg.construct();
		U tmpJ = alg.construct();
		long i = iLeft;
		long j = iRight;
		// While there are elements in the left or right runs...
		for (long k = iLeft; k < iEnd; k++) {
			// under appropriate conditions preload variables for lessOrEqual call below
			if (i < iRight && j < iEnd) {
				a.get(i, tmpI);
				a.get(j, tmpJ);
			}
			// If left run head exists and is <= existing right run head.
			if (i < iRight && (j >= iEnd || lessOrEqual.call(tmpI, tmpJ))) {
				a.get(i, tmpI);
				b.set(k, tmpI);
				i++;
			}
			else {
				a.get(j, tmpJ);
				b.set(k, tmpJ);
				j++;
			}
		} 
	}
}
