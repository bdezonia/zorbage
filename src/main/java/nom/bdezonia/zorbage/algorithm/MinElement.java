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
public class MinElement {

	private MinElement() {}

	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param min
	 */
	public static <T extends Group<T,U> & Ordered<U> & Bounded<U>, U>
		void compute(T grp, IndexedDataSource<?,U> storage, U min)
	{
		compute(grp, 0, storage.size(), storage, min);
	}

	/**
	 * 
	 * @param grp
	 * @param start
	 * @param count
	 * @param storage
	 * @param min
	 */
	public static <T extends Group<T,U> & Ordered<U> & Bounded<U>, U>
		void compute(T grp, long start, long count, IndexedDataSource<?,U> storage, U min)
	{
		if (count <= 0)
			throw new IllegalArgumentException("min undefined for empty list");
		U tmp = grp.construct();
		grp.maxBound(min);
		for (long i = 0; i < count; i++) {
			storage.get(start+i, tmp);
			if (grp.isLess(tmp, min)) {
				grp.assign(tmp, min);
			}
		}
	}
}
