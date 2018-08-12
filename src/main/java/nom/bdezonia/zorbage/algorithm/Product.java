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

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Product {

	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param result
	 */
	public static <T extends Group<T,U> & Multiplication<U> & Unity<U>, U>
		void compute(T grp, IndexedDataSource<?,U> storage, U result)
	{
		compute(grp, 0, storage.size(), storage, result);
	}

	/**
	 * 
	 * @param grp
	 * @param start
	 * @param count
	 * @param storage
	 * @param result
	 */
	public static <T extends Group<T,U> & Multiplication<U> & Unity<U>, U>
		void compute(T grp, long start, long count, IndexedDataSource<?,U> storage, U result)
	{
		if (start < 0) throw new IllegalArgumentException("start index must be >= 0 in Product method");
		if (count < 0) throw new IllegalArgumentException("count must be >= 0 in Product method");
		if (start + count > storage.size()) throw new IllegalArgumentException("start+count must be <= storage length in Product method");
	
		U value = grp.construct();
		U prod = grp.construct();
		if (count > 0) {
			grp.unity().call(prod);
			for (long i = 0; i < count; i++) {
				storage.get(start+i, value);
				grp.multiply().call(prod, value, prod);
			}
		}
		grp.assign().call(prod, result);
	}

}
