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
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class InPlaceSort {

	// do not instantiate
	
	private InPlaceSort() {}

	/**
	 * Sort data in place, never allocating any additional memory or disk space. This sort
	 * is modeled upon quicksort and may show poor performance on worst case data
	 * arrangements.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param storage
	 */
	public static <T extends Algebra<T,U> & Ordered<U> ,U>
		void compute(T alg, IndexedDataSource<U> storage)
	{
		compute(alg, alg.isLess(), storage);
	}

	/**
	 * Sort data in place, never allocating any additional memory or disk space. This sort
	 * is modeled upon quicksort and may show poor performance on worst case data
	 * arrangements.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param compare
	 * @param storage
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Function2<Boolean,U,U> compare, IndexedDataSource<U> storage)
	{
		qsort(alg, compare, 0, storage.size()-1, storage);
	}

	private static <T extends Algebra<T,U>, U>
		void qsort(T alg, Function2<Boolean,U,U> compare, long left, long right, IndexedDataSource<U> storage)
	{
		if (left < right) {
			// small list? 20 was determined optimal after many test runs
			if (right - left <= 20) {
				InsertionSort.compute(alg, compare, storage, left, right);
			}
			else {
				long pivotPoint = partition(alg, compare, left, right, storage);
				qsort(alg, compare, left, pivotPoint-1, storage);
				qsort(alg, compare, pivotPoint+1, right, storage);
			}
		}
	}
	
	// this method never called unless list length is greater than 20
	
	private static <T extends Algebra<T,U> ,U>
		long partition(T alg, Function2<Boolean,U,U> isLeftOf, long left, long right, IndexedDataSource<U> storage)
	{
		long midpt = (left >> 1) + (right >> 1) + (left & right & 1);
		
		U pivotValue = alg.construct();
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		U tmp3 = alg.construct();
		
		storage.get(left, tmp1);
		storage.get(midpt, tmp2);
		storage.get(right, tmp3);

		// Find median value of three as the pivot. This speeds up bad cases but does not
		//   entirely eliminate them.
		
		if (isLeftOf.call(tmp1, tmp2)) {
			// tmp1 left of tmp2
			if (isLeftOf.call(tmp2, tmp3)) {
				// tmp2 left of tmp3
				alg.assign().call(tmp2, pivotValue);
			}
			else {
				//tmp3 left of or equal tmp2
				alg.assign().call(tmp3, pivotValue);
			}
		}
		else {
			// tmp2 left of tmp1
			if (isLeftOf.call(tmp1, tmp3)) {
				// tmp1 left of tmp3
				alg.assign().call(tmp1, pivotValue);
			}
			else {
				//tmp1 right of or equal tmp3
				alg.assign().call(tmp3, pivotValue);
			}
		}
		
		// Now move things back and forth around the pivot
		
		long leftmark = left+1;
		long rightmark = right;
	
		boolean done = false;
		while (!done) {
	
			while (true) {
				if (leftmark > rightmark) break;
				storage.get(leftmark, tmp1);
				boolean isRightOf =
						!isLeftOf.call(tmp1, pivotValue) &&
						(isLeftOf.call(tmp1, pivotValue) != isLeftOf.call(pivotValue, tmp1));
				if (isRightOf) break;
				leftmark++;
			}
	
			while (true) {
				storage.get(rightmark, tmp1);
				if (isLeftOf.call(tmp1, pivotValue)) break;
				if (rightmark < leftmark) break;
				rightmark--;
			}
	
			if (rightmark < leftmark)
				done = true;
			else {
				storage.get(leftmark, tmp1);
				storage.get(rightmark, tmp2);
				storage.set(leftmark,tmp2);
				storage.set(rightmark, tmp1);
			}
		}
		
		storage.get(left, tmp1);
		storage.get(rightmark, tmp2);
		storage.set(left, tmp2);
		storage.set(rightmark, tmp1);
	
		return rightmark;
	}

}
