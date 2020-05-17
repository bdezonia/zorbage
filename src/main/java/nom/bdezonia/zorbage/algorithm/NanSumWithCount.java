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
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NanSumWithCount {

	private NanSumWithCount() {}
	
	/**
	 * 
	 * @param alg
	 * @param storage
	 * @param sum
	 * @param count
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & NaN<U>, U>
		void compute(T alg, IndexedDataSource<U> storage, U sum, U count)
	{
		boolean foundSome = false;
		U tmpSum = alg.construct();
		U tmpCnt = alg.construct();
		U value = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		long size = storage.size();
		for (long i = 0; i < size; i++) {
			storage.get(i, value);
			if (!alg.isNaN().call(value)) {
				foundSome = true;
				alg.add().call(tmpSum, value, tmpSum);
				alg.add().call(tmpCnt, one, tmpCnt);
			}
		}
		if (foundSome) {
			alg.assign().call(tmpSum, sum);
			alg.assign().call(tmpCnt, count);
		}
		else {
			alg.nan().call(sum);
			alg.zero().call(count);
		}
	}
}