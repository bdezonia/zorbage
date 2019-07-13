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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Convolve1D {

	/**
	 * 
	 * @param alg
	 * @param filter
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U>
		void compute(T alg, IndexedDataSource<U> filter, IndexedDataSource<U> a, IndexedDataSource<U> b)
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
				long idx = x - i;
				a.get(idx, tmp);
				filter.get(i + n, f);
				alg.multiply().call(tmp, f, tmp);
				alg.add().call(sum, tmp, sum);
			}
			b.set(x, sum);
		}
	}
}
