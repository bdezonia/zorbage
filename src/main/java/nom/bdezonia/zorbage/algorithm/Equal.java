/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Equal {

	private Equal() {}

	/**
	 * 
	 * @param Algebra
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		boolean compute(T Algebra, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		if (a.size() != b.size()) return false;
		return compute(Algebra, 0, 0, a.size(), a, b);
	}
	
	/**
	 * 
	 * @param Algebra
	 * @param a
	 * @param b
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		boolean compute(T Algebra, long aStart, long bStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		U tmp1 = Algebra.construct();
		U tmp2 = Algebra.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmp1);
			b.get(bStart+i, tmp2);
			if (Algebra.isNotEqual().call(tmp1, tmp2))
				return false;
		}
		return true;
	}
}
