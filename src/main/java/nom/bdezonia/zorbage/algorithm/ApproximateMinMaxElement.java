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

import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class ApproximateMinMaxElement {

	// do not instantiate
	
	private ApproximateMinMaxElement() {}

	/**
	 * Find the best guess at both the min and max values of a list of values.
	 * This algorithm does not visit every location in the list. It visits a
	 * given fraction which represents the probability the returned values
	 * include the true min and the true max. This algorithm is useful when
	 * your list to scan is big and absolute accuracy is not necessary.
	 * 
	 * @param alg The algebra that manipulates the types as stored in the list.
	 * @param certainty A double less than 0.0 and less than or equal to 1.0.
	 * Affects the thoroughness of the performed search. 
	 * @param storage The list of data to scan.
	 * @param min The output storing the approximate min found. 
	 * @param max The output storing the approximate max found.
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		void compute(T alg, double certainty, IndexedDataSource<U> storage, U min, U max)
	{
		if (certainty <= 0 || certainty > 1)
			throw new IllegalArgumentException("certainty must be > 0 and <= 1");
		long size = storage.size();
		if (size <= 0)
			throw new IllegalArgumentException("min / max results undefined for empty list");
		ThreadLocalRandom rng = ThreadLocalRandom.current();
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
			
			// only check pixels within the certainty bounds
			
			if (rng.nextDouble() < certainty) {
				
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
			}
			i += 2;
		}
	}
}
