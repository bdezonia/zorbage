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

import nom.bdezonia.zorbage.predicate.Equal;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SearchN {

	// do not instantiate
	
	private SearchN() { }

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param algebra
	 * @param count
	 * @param value
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, long count, U value, IndexedDataSource<U> a)
	{
		return compute(algebra, new Equal<T,U>(algebra), count, value, a);
	}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param algebra
	 * @param count
	 * @param cond
	 * @param value
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, Function1<Boolean,Tuple2<U,U>> cond, long count, U value, IndexedDataSource<U> a)
	{
		U tmpA = algebra.construct();
		Tuple2<U,U> tuple = new Tuple2<U, U>(tmpA, value);
		long first = 0;
		long last = a.size();
		if (count <= 0) {
			return first;
		}
		for(; first != last; first++) {
			a.get(first, tmpA);
			if (!cond.call(tuple)) {
				continue;
			}

			long candidate = first;
			long cur_count = 0;
			
			while (true) {
				cur_count++;
				if (cur_count >= count) {
					// success
					return candidate;
				}
				first++;
				if (first == last) {
					// exhausted the list
					return last;
				}
				a.get(first, tmpA);
				if (!cond.call(tuple)) {
					// too few in a row
					break;
				}
			}
		}
		return last;
	}
}
