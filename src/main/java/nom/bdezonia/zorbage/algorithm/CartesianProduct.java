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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CartesianProduct {

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <V>
	 * @param <W>
	 * @param <X>
	 * @param <Y>
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U>,
					U,
					V extends Algebra<V,W>,
					W,
					X extends Algebra<X,Y>,
					Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc,
						IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> result)
	{
		if (a.size() == 0 || b.size() == 0 || result.size() == 0)
			throw new IllegalArgumentException("empty data source error");
		if (result.size() / a.size() != b.size())
			throw new IllegalArgumentException("preallocation error");
		U u = algU.construct();
		W w = algW.construct();
		Y y = algY.construct();
		long i = 0;
		for (long r = 0; r < a.size(); r++) {
			a.get(r, u);
			for (long c = 0; c < b.size(); c++) {
				b.get(c, w);
				proc.call(u, w, y);
				result.set(i++, y);
			}
		}
	}
}
