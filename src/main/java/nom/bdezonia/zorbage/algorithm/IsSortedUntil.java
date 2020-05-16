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
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IsSortedUntil {

	private IsSortedUntil() { }
	
	/**
	 * 
	 * @param alg
	 * @param storage
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>,U>
		long compute(T alg, IndexedDataSource<U> storage)
	{
		return checkSort(alg, alg.isLess(), storage);
	}
	
	/**
	 * 
	 * @param alg
	 * @param isLeftOf
	 * @param storage
	 * @return
	 */
	public static <T extends Algebra<T,U>,U>
		long compute(T alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage)
	{
		return checkSort(alg, isLeftOf, storage);
	}

	private static <T extends Algebra<T,U>, U>
		long checkSort(T alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		long first = 0;
		long last = storage.size();
		if (first==last) return first;
		long next = first;
		while (++next != last) {
			storage.get(next, tmp1);
			storage.get(first, tmp2);
			if (isLeftOf.call(tmp1, tmp2))
				return next;
			first++;
		}
		return last;
	}
}
