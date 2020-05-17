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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

public class NanEqual {

	// do not instantiate

	private NanEqual() { }

	/**
	 * Determine if two data sources have equal contents while treating
	 *   NaN == NaN as true.
	 *
	 * @param algebra
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U> & NaN<U>, U>
	boolean compute(T algebra, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		long size = a.size();
		if (b.size() != size)
			return false;
		U tmp1 = algebra.construct();
		U tmp2 = algebra.construct();
		for (long i = 0; i < size; i++) {
			a.get(i, tmp1);
			b.get(i, tmp2);
			if (algebra.isNotEqual().call(tmp1, tmp2)) {
				if (algebra.isNaN().call(tmp1) && algebra.isNaN().call(tmp2)) {
					// ignore; treat NaNs as equal
				}
				else
					return false;
			}
		}
		return true;
	}

}