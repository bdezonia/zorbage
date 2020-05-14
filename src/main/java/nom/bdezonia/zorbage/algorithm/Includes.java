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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Includes {

	private Includes() { }
	
	/**
	 * Return true if the sorted list includes the sorted sublist
	 * @param alg
	 * @param list
	 * @param sublist
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		boolean compute(T alg, IndexedDataSource<U> list, IndexedDataSource<U> sublist)
	{
		long listSize = list.size();
		long subSize = sublist.size();
		long li = 0;
		long si = 0;
		U tmpL = alg.construct();
		U tmpS = alg.construct();
		while (si != subSize) {
			if (li==listSize)
				return false;
			list.get(li, tmpL);
			sublist.get(si, tmpS);
			if (alg.isLess().call(tmpS,tmpL))
				return false;
			if (!alg.isLess().call(tmpL,tmpS))
				si++;
			li++;
		}
		return true;
	}
}
