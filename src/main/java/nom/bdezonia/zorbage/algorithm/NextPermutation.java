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
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NextPermutation {

	// do not instantiate
	
	private NextPermutation() { }
	
	/**
	 * 
	 * @param alg
	 * @param a
	 */
	public static <T extends Algebra<T,U> & Ordered<U>,U>
		boolean compute(T alg, IndexedDataSource<U> a)
	{
		long first = 0;
		long last = a.size();
		if (first == last)
			return false;
		long i = first;
		i++;
		if (i == last)
			return false;
		i = last;
		i--;
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		for(;;) {
			long ii = i;
			i--;
			a.get(i, tmp1);
			a.get(ii, tmp2);
			if (alg.isLess().call(tmp1, tmp2)) {
				long j = last;
				j--;
				a.get(i, tmp1);
				a.get(j, tmp2);
				while (alg.isGreaterEqual().call(tmp1, tmp2)) {
					j--;
					a.get(j, tmp2);
				}
				//j--;  // BDZ added: needed?
				a.get(i, tmp1);
				a.get(j, tmp2);
				a.set(i, tmp2);
				a.set(j, tmp1);
				TrimmedDataSource<U> tmpList = new TrimmedDataSource<>(a, ii, last-ii);
				Reverse.compute(alg, tmpList);
				return true;
			}
			if (i == first) {
				TrimmedDataSource<U> tmpList = new TrimmedDataSource<>(a, first, last-first);
				Reverse.compute(alg, tmpList);
				return false;
			}
		}
	}
}
