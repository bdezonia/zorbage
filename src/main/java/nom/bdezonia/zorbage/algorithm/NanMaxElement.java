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
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.NaN;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NanMaxElement {

	// do not instantiate
	
	private NanMaxElement() {}

	/**
	 * Return the max element from a list while ignoring NaN values.
	 * 
	 * @param alg
	 * @param storage
	 * @param max
	 */
	public static <T extends Algebra<T,U> & Ordered<U> & NaN<U>, U>
		void compute(T alg, IndexedDataSource<U> storage, U max)
	{
		boolean foundSome = false;
		long size = storage.size();
		if (size == 0)
			throw new IllegalArgumentException("nan max element called with empty list");
		U tmp = alg.construct();
		for (long i = 0; i < size; i++) {
			storage.get(i, tmp);
			if (!alg.isNaN().call(tmp)) {
				if (!foundSome) {
					alg.assign().call(tmp, max);
				}
				foundSome = true;
				if (alg.isGreater().call(tmp, max)) {
					alg.assign().call(tmp, max);
				}
			}
		}
		if (!foundSome)
			alg.nan().call(max);
	}
}
