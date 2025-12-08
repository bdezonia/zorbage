/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;
import java.math.MathContext;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Sort {

	// do not instantiate
	
	private Sort() {}

	/**
	 * Sort a list of values into ascending order. Change the contents
	 * of the list in the process.
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
	 * Sort a list of values into an order defined by a comparison function
	 * that is provided as an input. Change the contents of the list in the
	 * process.
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
		long maxDepth;
		if (storage.size() == 0)
			maxDepth = 1;
		else
			maxDepth = (BigDecimalMath.log(BigDecimal.valueOf(storage.size()), new MathContext(3)).longValue() + 1) * 2;
		introsort(alg, lessOrEqual, 0, storage.size() - 1, maxDepth, storage);
	}

	private static <T extends Algebra<T,U>, U extends Allocatable<U>>
		void introsort(T alg, Function2<Boolean,U,U> lessOrEqual, long left, long right, long maxDepth, IndexedDataSource<U> a)
	{
		// small list? 20 was determined optimal after many test runs
		if (right - left <= 20) {
			InsertionSort.compute(alg, lessOrEqual, a, left, right);
		}
		else if (maxDepth == 0) {
			TrimmedDataSource<U> sublist = new TrimmedDataSource<U>(a, left, right-left+1);
			// TODO ideally we'd do heapsort rather than mergesort. This is how my code deviates from true introsort
			StableSort.compute(alg, lessOrEqual, sublist);
		}
		else {
			long p = partition(alg, lessOrEqual, left, right, a);
			introsort(alg, lessOrEqual, left, p-1, maxDepth-1, a);
			introsort(alg, lessOrEqual, p+1, right, maxDepth-1, a);
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
