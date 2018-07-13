/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Group;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class MinMaxElement {

	private MinMaxElement() {}

	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param min
	 * @param max
	 */
	public static <T extends Group<T,U> & Ordered<U> & Bounded<U>, U>
		void compute(T grp, IndexedDataSource<?,U> storage, U min, U max)
	{
		compute(grp, 0, storage.size(), storage, min, max);
	}

	/**
	 * 
	 * @param grp
	 * @param start
	 * @param count
	 * @param storage
	 * @param min
	 * @param max
	 */
	public static <T extends Group<T,U> & Ordered<U> & Bounded<U>, U>
		void compute(T grp, long start, long count, IndexedDataSource<?,U> storage, U min, U max)
	{
		if (count <= 0)
			throw new IllegalArgumentException("minmax undefined for empty list");
		U tmp1 = grp.construct();
		U tmp2 = grp.construct();
		grp.maxBound(min);
		grp.minBound(max);
		long i = 0;
		if ((count & 1) == 1) {
			storage.get(start, tmp1);
			if (grp.isGreater(tmp1, max)) {
				grp.assign(tmp1, max);
			}
			if (grp.isLess(tmp1, min)) {
				grp.assign(tmp1, min);
			}
			i++;
		}
		while (i < count) {
			storage.get(start+i, tmp1);
			storage.get(start+i+1, tmp2);
			if (grp.isGreater(tmp1, tmp2)) {
				if (grp.isGreater(tmp1, max)) {
					grp.assign(tmp1, max);
				}
				if (grp.isLess(tmp2, min)) {
					grp.assign(tmp2, min);
				}
			}
			else { // tmp2 >= tmp1
				if (grp.isGreater(tmp2, max)) {
					grp.assign(tmp2, max);
				}
				if (grp.isLess(tmp1, min)) {
					grp.assign(tmp1, min);
				}
			}
			i += 2;
		}
	}
}
