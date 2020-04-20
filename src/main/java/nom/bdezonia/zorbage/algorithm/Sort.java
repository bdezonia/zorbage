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

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Sort {

	private Sort() {}

	/**
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
			// small list?
			if (right - left < 10) {
				InsertionSort.compute(alg, compare, storage, left, right);
			}
			else {
				long pivotPoint = partition(alg, compare, left, right, storage);
				qsort(alg, compare, left, pivotPoint-1, storage);
				qsort(alg, compare, pivotPoint+1, right, storage);
			}
		}
	}
	
	private static <T extends Algebra<T,U> ,U>
		long partition(T alg, Function2<Boolean,U,U> isLeftOf, long left, long right, IndexedDataSource<U> storage)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		
		U pivotValue = alg.construct();
		storage.get(left, pivotValue);
	
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
