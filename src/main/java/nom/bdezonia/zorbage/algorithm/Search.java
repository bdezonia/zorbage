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

import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

// TODO - use boyer moore algorithm

// TODO - does SearchN need to be updated like this Search class was?

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Search {

	private Search() { }
	
	/**
	 * 
	 * @param algebra
	 * @param elements
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, IndexedDataSource<U> elements, IndexedDataSource<U> a)
	{
		U tmpA = algebra.construct();
		U element = algebra.construct();
		final long max = elements.size();
		long aSize = a.size();
		for (long i = 0; i < aSize - max; i++) {
			for (long j = 0; j < max; j++) {
				a.get(i+j, tmpA);
				elements.get(j, element);
				if (algebra.isNotEqual().call(tmpA, element))
					break;
				if (j == max-1)
					return i;
			}
		}
		return aSize;
	}	

	/**
	 * 
	 * @param algebra
	 * @param cond
	 * @param elements
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, Predicate<Tuple2<U,U>> cond, IndexedDataSource<U> elements, IndexedDataSource<U> a)
	{
		U tmpA = algebra.construct();
		U element = algebra.construct();
		Tuple2<U,U> tuple = new Tuple2<U,U>(tmpA, element);
		final long max = elements.size();
		long aSize = a.size();
		for (long i = 0; i < aSize - max; i++) {
			for (long j = 0; j < max; j++) {
				a.get(i+j, tmpA);
				elements.get(j, element);
				if (!cond.isTrue(tuple))
					break;
				if (j == max-1)
					return i;
			}
		}
		return aSize;
	}	
}
