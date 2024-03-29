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
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SetDifference {

	// do not instantiate
	
	private SetDifference() { }
	
	/**
	 * SetDifference : calculate the set difference of two possibly unsorted lists.
	 * The result has no duplicates and is sorted.
	 * 
	 * @param alg
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U extends Allocatable<U>>
		IndexedDataSource<U> compute(T alg, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		U type = alg.construct();
		long aSize = a.size();
		long bSize = b.size();
		// the difference of a and b can't be larger than a
		IndexedDataSource<U> tmpList = Storage.allocate(type, aSize);
		long count = 0;
		U value = alg.construct();
		// kinda n^2 here. if the inputs were sorted this could be much faster. even naively
		// doing multiple binary searches.
		for (long ai = 0; ai < aSize; ai++) {
			a.get(ai, value);
			if (Find.compute(alg, value, b) >= bSize) {
				tmpList.set(count++, value);
			}
		}
		if (count == 0) {
			return Storage.allocate(type, 0);
		}
		// stamp the unfilled portion of tmpList with duplicated data
		tmpList.get(0, value);
		for (long i = count; i < aSize; i++)
			tmpList.set(i, value);
		Sort.compute(alg, tmpList);
		return Unique.compute(alg, tmpList);
	}
}
