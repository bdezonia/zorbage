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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SummaryStats {

	// do not instantiate
	
	private SummaryStats() { }
	
	/**
	 * Note that quartiles do not match R's calculations. There are many ways to calc quartiles by analyzing the
	 * specific dataset. That is what R does. Zorbage does something simpler. It finds the indexes of the median
	 * of the dataset. Then it locates quartile 1 at the median of the left sublist and quartile 3 at the median
	 * of the right sublist.
	 * 
	 * @param alg
	 * @param data
	 * @param min
	 * @param q1
	 * @param median
	 * @param mean
	 * @param q3
	 * @param max
	 * @param noDataCount
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U> & Multiplication<U> & NaN<U>,
					U extends Allocatable<U>>
		void computeSafe(T alg, IndexedDataSource<U> data, U min, U q1, U median, U mean, U q3, U max, SignedInt64Member noDataCount)
	{
		IndexedDataSource<U> trimmed = NonNanValues.compute(alg, data);
		noDataCount.setV(data.size() - trimmed.size());
		compute(alg, trimmed, min, q1, median, mean, q3, max);
	}
	
	/**
	 * 
	 * @param alg
	 * @param data
	 * @param min
	 * @param q1
	 * @param median
	 * @param mean
	 * @param q3
	 * @param max
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U> & Multiplication<U>,
					U extends Allocatable<U>>
		void compute(T alg, IndexedDataSource<U> data, U min, U q1, U median, U mean, U q3, U max)
	{
		long sz = data.size();
		
		if (sz == 0) {
			throw new IllegalArgumentException("cannot extract measures from empty list");
		}

		IndexedDataSource<U> copy = DeepCopy.compute(alg, data);
		
		StableSort.compute(alg, copy);
		
		U sum = alg.construct();
		U count = alg.construct();

		U one = alg.construct();
		U two = alg.construct();
		
		alg.unity().call(one);
		alg.add().call(one, one, two);
		
		U medL = alg.construct();
		U medR = alg.construct();
		U q1L = alg.construct();
		U q1R = alg.construct();
		U q3L = alg.construct();
		U q3R = alg.construct();
		U numer = alg.construct();

		long medIdxL; 
		long medIdxR; 
		long q1IdxL; 
		long q1IdxR; 
		long q3IdxL; 
		long q3IdxR; 

		medIdxL = leftIndex(0, sz-1);
		medIdxR = rightIndex(medIdxL, sz);
		
		if (sz == 1) {
			q1IdxL = 0;
			q1IdxR = 0;

			q3IdxL = 0;
			q3IdxR = 0;
		}
		else {
			q1IdxL = leftIndex(0, sz % 2 == 0 ? medIdxL : medIdxL - 1);
			q1IdxR = rightIndex(q1IdxL, sz % 2 == 1 ? medIdxL : medIdxL - 1);
			long tmp = sz % 2 == 0 ? medIdxR : medIdxR + 1;
			q3IdxL = tmp + leftIndex(tmp, sz-1);
			q3IdxR = rightIndex(q3IdxL, sz % 2 == 0 ? sz-medIdxR : sz-medIdxR-1);
		}

		/*
		System.out.println(sz + " case --------");
		System.out.println("  median " + medIdxL + " " + medIdxR);
		System.out.println("  q1     " + q1IdxL + " " + q1IdxR);
		System.out.println("  q3     " + q3IdxL + " " + q3IdxR);
		*/
		
		SumWithCount.compute(alg, copy, sum, count);

		copy.get(0, min);
		copy.get(sz-1, max);
		copy.get(medIdxL, medL);
		copy.get(medIdxR, medR);
		copy.get(q1IdxL, q1L);
		copy.get(q1IdxR, q1R);
		copy.get(q3IdxL, q3L);
		copy.get(q3IdxR, q3R);
		
		alg.add().call(medL, medR, numer);
		Divide.compute(alg, numer, two, median);
		alg.add().call(q1L, q1R, numer);
		Divide.compute(alg, numer, two, q1);
		alg.add().call(q3L, q3R, numer);
		Divide.compute(alg, numer, two, q3);
		Divide.compute(alg, sum, count, mean);
	}

	private static long leftIndex(long left, long right) {
		long sz = right - left + 1;
		// even case
		if (sz % 2 == 0) {
			return sz/2 - 1;
		}
		else { // odd case
			return sz/2;
		}
	}

	private static long rightIndex(long leftIdx, long sz) {
		// even case
		if (sz % 2 == 0) {
			return leftIdx + 1;
		}
		else { // odd case
			return leftIdx;
		}
	}
}
