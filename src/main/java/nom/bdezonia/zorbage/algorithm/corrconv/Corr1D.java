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
package nom.bdezonia.zorbage.algorithm.corrconv;

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Corr1D {

	// do not instantiate
	
	private Corr1D() { }
	
	/**
	 * 
	 * @param alg
	 * @param indexer
	 * @param filter
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Conjugate<U>, U>
		void compute(T alg, Function2<Long,Long,Long> indexer, IndexedDataSource<U> filter, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		if (a == b)
			throw new IllegalArgumentException("source and dest lists must be different");
		
		if (filter.size() % 2 != 1)
			throw new IllegalArgumentException("filter length should be odd");
		
		U tmp = alg.construct();
		U f = alg.construct();
		U sum = alg.construct();
		long n = filter.size() / 2;
		for (long x = 0; x < a.size(); x++) {
			alg.zero().call(sum);
			for (long i = -n; i <= n; i++) {
				long idx = indexer.call(x, i);
				a.get(idx, tmp);
				filter.get(i + n, f);
				alg.conjugate().call(tmp, tmp);
				alg.multiply().call(tmp, f, tmp);
				alg.add().call(sum, tmp, sum);
			}
			b.set(x, sum);
		}
	}
}
