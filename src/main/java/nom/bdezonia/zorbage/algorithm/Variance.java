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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * @author Barry DeZonia
 */
public class Variance {

	/**
	 *
	 * @param alg
	 * @param source
	 * @param result
	 * @param <T>
	 * @param <U>
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Unity<U>, U>
		void compute(T alg, IndexedDataSource<U> source, U result)
	{
		if (source.size() == 0)
			throw new IllegalArgumentException("variance called on an empty list");

		else if (source.size() == 1) {
			alg.zero().call(result);
			return;
		}
		U mean = alg.construct();
		U sum = alg.construct();
		U tmp = alg.construct();
		U n = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		Mean.compute(alg, source, mean);
		for (long i = 0; i < source.size(); i++) {
			source.get(i, tmp);
			alg.subtract().call(tmp, mean, tmp);
			alg.multiply().call(tmp, tmp, tmp);
			alg.add().call(sum, tmp, sum);
			alg.add().call(n, one, n);
		}
		alg.subtract().call(n, one, n);
		Divide.compute(alg, sum, n, result);
	}
}
