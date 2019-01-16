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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform2 {

	private Transform2() { }

	/**
	 * 
	 * @param grp
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static final <T extends Algebra<T,U>,U>
		void compute(T grp, Procedure2<U,U> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		compute(grp, grp, proc, aStart, bStart, count, aStride, bStride, a, b);
	}

	/**
	 * 
	 * @param grp
	 * @param proc
	 * @param start
	 * @param count
	 * @param stride
	 * @param a
	 */
	public static final <T extends Algebra<T,U>,U>
		void compute(T grp, Procedure2<U,U> proc, long start, long count, long stride, IndexedDataSource<?,U> a)
	{
		compute(grp, grp, proc, start, start, count, stride, stride, a, a);
	}

	/**
	 * 
	 * @param grpU
	 * @param grpW
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static final <T extends Algebra<T,U>,U,V extends Algebra<V,W>,W>
		void compute(T grpU, V grpW, Procedure2<U,W> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b)
	{
		U valueU = grpU.construct();
		W valueW = grpW.construct();
		for (long i = aStart, j = bStart, c = 0; c < count; c++) {
			a.get(i, valueU);
			proc.call(valueU, valueW);
			b.set(j, valueW);
			i += aStride;
			j += bStride;
		}
	}

	/**
	 * In place transformation of one whole list by a Procedure2.
	 * 
	 * @param grp
	 * @param proc
	 * @param a
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T grp, Procedure2<U,U> proc, IndexedDataSource<?,U> a)
	{
		U value1 = grp.construct();
		U value2 = grp.construct();
		for (long i = 0; i < a.size(); i++) {
			a.get(i, value1);
			proc.call(value1, value2);
			a.set(i, value2);
		}
	}
}
