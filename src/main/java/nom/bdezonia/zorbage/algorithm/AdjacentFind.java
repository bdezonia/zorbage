/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class AdjacentFind {

	// do not instantiate
	
	private AdjacentFind() {}

	/**
	 * 
	 * @param algebra
	 * @param a
\	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, IndexedDataSource<U> a)
	{
		return compute(algebra, 0, a.size(), a);
	}
	
	/**
	 * 
	 * @param algebra
	 * @param a
	 * @param start
	 * @param count
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, long start, long count, IndexedDataSource<U> a)
	{
		if (start+count < 2) return start+count;
		U tmp1 = algebra.construct();
		U tmp2 = algebra.construct();
		a.get(start, tmp1);
		for (long i = 1; i < count; i++) {
			a.get(start+i, tmp2);
			if (algebra.isEqual().call(tmp1, tmp2))
				return start + i - 1;
			algebra.assign().call(tmp2, tmp1);
		}
		return start + count;
	}
}
