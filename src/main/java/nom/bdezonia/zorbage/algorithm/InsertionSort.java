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

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class InsertionSort {

	/**
	 * 
	 * @param alg
	 * @param storage
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		void compute(T alg, IndexedDataSource<U> storage)
	{
		compute(alg, alg.isLess(), storage);
	}
	
	/**
	 * 
	 * @param alg
	 * @param isLeftOf
	 * @param storage
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage)
	{
		compute(alg, isLeftOf, storage, 0, storage.size()-1);
	}

	/**
	 * 
	 * @param alg
	 * @param isLeftOf
	 * @param storage
	 * @param left
	 * @param right
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage, long left, long right)
	{
		U key = alg.construct();
		U tmp = alg.construct();
		U t2 = alg.construct();
		long n = right - left + 1;
		for (long i = 1; i < n; i++) {
			storage.get(left+i, key);
			long j = i-1; 
			storage.get(left+j, tmp);
			while (j >= 0 && isLeftOf.call(key, tmp)) {
				storage.get(left+j, t2);
				storage.set(left+j+1, t2);
				j = j - 1;
				if (j >= 0)
					storage.get(left+j, tmp);
			}
			storage.set(left+j+1, key);
		}
	}
}
