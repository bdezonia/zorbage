/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.ConcatenatedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SetUnion {

	/**
	 * SetUnion : calculate the set union of two possibly unsorted lists.
	 * The resulting data source has no duplicates and is sorted.
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
		IndexedDataSource<U> combined = new ConcatenatedDataSource<>(a, b);
		IndexedDataSource<U> tmpList = Storage.allocate(combined.size(), type);
		Copy.compute(alg, combined, tmpList);
		Sort.compute(alg, tmpList);
		long dupeCount = countDupes(alg,tmpList);
		long resultSize = tmpList.size() - dupeCount;
		IndexedDataSource<U> result = Storage.allocate(resultSize, type);
		copyWithoutDupes(alg, tmpList, result);
		return result;
	}
	
	private static <T extends Algebra<T,U>, U>
		long countDupes(T alg, IndexedDataSource<U> a)
	{
		long aSize = a.size();
		if (aSize <= 1)
			return 0;
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		a.get(0, tmp1);
		long count = 0;
		long ai = 1;
		while (ai < aSize) {
			a.get(ai++, tmp2);
			if (alg.isEqual().call(tmp1, tmp2))
				count++;
			alg.assign().call(tmp2, tmp1);
		}
		return count;
	}
	
	private static <T extends Algebra<T,U>, U>
		void copyWithoutDupes(T alg, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		long aSize = a.size();
		if (aSize <= 1) {
			Copy.compute(alg, a, b);
			return;
		}
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		a.get(0, tmp1);
		b.set(0, tmp1);
		long ai = 1;
		long bi = 1;
		while (ai < aSize) {
			a.get(ai++, tmp2);
			if (alg.isNotEqual().call(tmp1, tmp2)) {
				b.set(bi++, tmp2);
			}
			alg.assign().call(tmp2, tmp1);
		}
	}
}
