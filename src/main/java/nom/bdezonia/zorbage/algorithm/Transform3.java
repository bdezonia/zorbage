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
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform3 {

	private Transform3() { }

	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static final <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure3<U,U,U> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c)
	{
		compute(algU, algU, algU, proc, aStart, bStart, cStart, count, aStride, bStride, cStride, a, b, c);
	}
	
	/**
	 * 
	 * @param algU
	 * @param algW
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static final <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W>
		void compute(T algU, V algW, Procedure3<U,W,W> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<U> a, IndexedDataSource<W> b)
	{
		compute(algU, algW, algW, proc, aStart, bStart, bStart, count, aStride, bStride, bStride, a, b, b);
	}

	/**
	 * 
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param a
	 * @param b
	 * @param c
	 */
	public static final <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> c)
	{
		U valueU = algU.construct();
		W valueW = algW.construct();
		Y valueY = algY.construct();
		for (long i = aStart, j = bStart, k = cStart, m = 0; m < count; m++) {
			a.get(i, valueU);
			b.get(j, valueW);
			proc.call(valueU, valueW, valueY);
			c.set(k, valueY);
			i += aStride;
			j += bStride;
			k += cStride;
		}
	}

	/**
	 * In place transformation of one whole list by a Procedure3.
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Procedure3<U,U,U> proc, IndexedDataSource<U> a)
	{
		U value1 = alg.construct();
		U value2 = alg.construct();
		long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, value1);
			proc.call(value1, value1, value2);
			a.set(i, value2);
		}
	}
}
