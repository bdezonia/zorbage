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
public class Mismatch {

	/**
	 * 
	 * @param Algebra
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		Tuple2<Long,Long> compute(T algebra, IndexedDataSource<?, U> a, IndexedDataSource<?,U> b)
	{
		return compute(algebra, 0, 0, a.size(), a, b);
	}

	/**
	 * 
	 * @param Algebra
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		Tuple2<Long,Long> compute(T algebra, long aStart, long bStart, long count, IndexedDataSource<?, U> a, IndexedDataSource<?,U> b)
	{
		U tmpA = algebra.construct();
		U tmpB = algebra.construct();
		Tuple2<Long,Long> retVal = new Tuple2<Long, Long>(0L, 0L);
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpA);
			b.get(bStart+i, tmpB);
			if (algebra.isNotEqual().call(tmpA, tmpB)) {
				retVal.setA(aStart+i);
				retVal.setB(bStart+i);
				return retVal;
			}
				
		}
		retVal.setA(aStart+count);
		retVal.setB(bStart+count);
		return retVal;
	}
	
	/**
	 * 
	 * @param Algebra
	 * @param cond
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		Tuple2<Long,Long> compute(T algebra, Condition<Tuple2<U,U>> cond, IndexedDataSource<?, U> a, IndexedDataSource<?,U> b)
	{
		return compute(algebra, cond, 0, 0, a.size(), a, b);
	}

	/**
	 * 
	 * @param Algebra
	 * @param cond
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		Tuple2<Long,Long> compute(T algebra, Condition<Tuple2<U,U>> cond, long aStart, long bStart, long count, IndexedDataSource<?, U> a, IndexedDataSource<?,U> b)
	{
		U tmpA = algebra.construct();
		U tmpB = algebra.construct();
		Tuple2<Long,Long> retVal = new Tuple2<Long, Long>(0L, 0L);
		Tuple2<U,U> tuple = new Tuple2<U, U>(tmpA, tmpB);
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpA);
			b.get(bStart+i, tmpB);
			//tuple.setA(tmpA);
			//tuple.setB(tmpB);
			if (!cond.isTrue(tuple)) {
				retVal.setA(aStart+i);
				retVal.setB(bStart+i);
				return retVal;
			}
				
		}
		retVal.setA(aStart+count);
		retVal.setB(bStart+count);
		return retVal;
	}
	
}
