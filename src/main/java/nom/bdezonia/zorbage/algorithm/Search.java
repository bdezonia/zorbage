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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

// TODO - use boyer moore algorithm

// TODO - does SearchN need to be updated like this Search class was?

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Search {

	/**
	 * 
	 * @param group
	 * @param elements
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, IndexedDataSource<?, U> elements, IndexedDataSource<?, U> a)
	{
		return compute(group,elements,0, a.size(), a);
	}
	
	/**
	 * 
	 * @param group
	 * @param elements
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, IndexedDataSource<?, U> elements, long start, long count, IndexedDataSource<?, U> a)
	{
		U tmpA = group.construct();
		U element = group.construct();
		final long max = elements.size();
		for (long i = 0; i < count - max; i++) {
			for (long j = 0; j < max; j++) {
				a.get(start+i+j, tmpA);
				elements.get(j, element);
				if (group.isNotEqual(tmpA, element))
					break;
				if (j == max-1)
					return start+i;
			}
		}
		return start+count;
	}	

	/**
	 * 
	 * @param group
	 * @param cond
	 * @param elements
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, Condition<Tuple2<U,U>> cond, IndexedDataSource<?, U> elements, IndexedDataSource<?, U> a)
	{
		return compute(group, cond, elements, 0, a.size(), a);
	}
	
	/**
	 * 
	 * @param group
	 * @param cond
	 * @param elements
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, Condition<Tuple2<U,U>> cond, IndexedDataSource<?, U> elements, long start, long count, IndexedDataSource<?, U> a)
	{
		U tmpA = group.construct();
		U element = group.construct();
		Tuple2<U,U> tuple = new Tuple2<U,U>(tmpA, element);
		final long max = elements.size();
		for (long i = 0; i < count - max; i++) {
			for (long j = 0; j < max; j++) {
				a.get(start+i+j, tmpA);
				elements.get(j, element);
				if (!cond.isTrue(tuple))
					break;
				if (j == max-1)
					return start+i;
			}
		}
		return start+count;
	}	
}
