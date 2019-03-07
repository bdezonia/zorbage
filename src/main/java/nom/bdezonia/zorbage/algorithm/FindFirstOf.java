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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindFirstOf {

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
		return compute(algebra, elements, 0, a.size(), a);
	}

	/**
	 * 
	 * @param algebra
	 * @param elements
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, IndexedDataSource<U> elements, long start, long count, IndexedDataSource<U> a)
	{
		U tmpA = algebra.construct();
		U element = algebra.construct();
		long elementsSize = elements.size();
		for (long i = 0; i < count-elementsSize; i++) {
			a.get(start+i, tmpA);
			for (long j = 0; j < elementsSize; j++) {
				elements.get(j, element);
				if (!algebra.isEqual().call(tmpA, element))
					break;
				if (j == elementsSize-1)
					return start + i;
				a.get(start+i+j+1, tmpA);
			}
		}
		return start + count;
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
		long compute(T algebra, Condition<Tuple2<U,U>> cond, IndexedDataSource<U> elements, IndexedDataSource<U> a)
	{
		return compute(algebra, cond, elements, 0, a.size(), a);
	}

	/**
	 * 
	 * @param algebra
	 * @param cond
	 * @param elements
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T algebra, Condition<Tuple2<U,U>> cond, IndexedDataSource<U> elements, long start, long count, IndexedDataSource<U> a)
	{
		U tmpA = algebra.construct();
		U element = algebra.construct();
		Tuple2<U,U> tuple = new Tuple2<U,U>(tmpA,element);
		long elementsSize = elements.size();
		for (long i = 0; i < count; i++) {
			a.get(start+i, tmpA);
			tuple.setA(tmpA);
			for (long j = 0; j < elementsSize; j++) {
				elements.get(j, element);
				tuple.setB(element);
				if (cond.isTrue(tuple))
					return start+i;
			}
		}
		return start + count;
	}
}
