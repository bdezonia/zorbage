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

import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class MinMaxElement {

	private MinMaxElement() {}

	/**
	 * 
	 * @param alg
	 * @param storage
	 * @param min
	 * @param max
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		void compute(T alg, IndexedDataSource<U> storage, U min, U max)
	{
		long size = storage.size();
		if (size <= 0)
			throw new IllegalArgumentException("minmax undefined for empty list");
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		storage.get(0, min);
		alg.assign().call(min, max);
		long i = 1;
		if ((size & 1) == 0) {
			storage.get(1, tmp1);
			if (alg.isGreater().call(tmp1, max)) {
				alg.assign().call(tmp1, max);
			}
			if (alg.isLess().call(tmp1, min)) {
				alg.assign().call(tmp1, min);
			}
			i++;
		}
		while (i < size) {
			storage.get(i, tmp1);
			storage.get(i+1, tmp2);
			if (alg.isGreater().call(tmp1, tmp2)) {
				if (alg.isGreater().call(tmp1, max)) {
					alg.assign().call(tmp1, max);
				}
				if (alg.isLess().call(tmp2, min)) {
					alg.assign().call(tmp2, min);
				}
			}
			else { // tmp2 >= tmp1
				if (alg.isGreater().call(tmp2, max)) {
					alg.assign().call(tmp2, max);
				}
				if (alg.isLess().call(tmp1, min)) {
					alg.assign().call(tmp1, min);
				}
			}
			i += 2;
		}
	}
}
