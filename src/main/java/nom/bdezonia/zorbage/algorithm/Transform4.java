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

import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform4 {

	private Transform4() { }

	/**
	 * 
	 * @param grpM
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param dStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param dStride
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static final <L extends Algebra<L,M>, M>
		void compute(L grpM, Procedure4<M,M,M,M> proc, long aStart, long bStart, long cStart, long dStart, long count, long aStride, long bStride, long cStride, long dStride, IndexedDataSource<?,M> a, IndexedDataSource<?,M> b, IndexedDataSource<?,M> c, IndexedDataSource<?,M> d)
	{
		compute(grpM, grpM, grpM, grpM, proc, aStart, bStart, cStart, dStart, count, aStride, bStride, cStride, dStride, a, b, c, d);
	}
	
	/**
	 * 
	 * @param grpM
	 * @param grpO
	 * @param grpQ
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
	public static final <L extends Algebra<L,M>, M, N extends Algebra<N,O>, O, P extends Algebra<P,Q>, Q>
		void compute(L grpM, N grpO, P grpQ, Procedure4<M,O,Q,Q> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<?,M> a, IndexedDataSource<?,O> b, IndexedDataSource<?,Q> c)
	{
		compute(grpM, grpO, grpQ, grpQ, proc, aStart, bStart, cStart, cStart, count, aStride, bStride, cStride, cStride, a, b, c, c);
	}

	/**
	 * 
	 * @param grpM
	 * @param grpO
	 * @param grpQ
	 * @param grpS
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param dStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param dStride
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static final <L extends Algebra<L,M>, M, N extends Algebra<N,O>, O, P extends Algebra<P,Q>, Q, R extends Algebra<R,S>, S>
		void compute(L grpM, N grpO, P grpQ, R grpS, Procedure4<M,O,Q,S> proc, long aStart, long bStart, long cStart, long dStart, long count, long aStride, long bStride, long cStride, long dStride, IndexedDataSource<?,M> a, IndexedDataSource<?,O> b, IndexedDataSource<?,Q> c, IndexedDataSource<?,S> d)
	{
		M valueM = grpM.construct();
		O valueO = grpO.construct();
		Q valueQ = grpQ.construct();
		S valueS = grpS.construct();
		for (long i = aStart, j = bStart, k = cStart, l = dStart, m = 0; m < count; m++) {
			a.get(i, valueM);
			b.get(j, valueO);
			c.get(k, valueQ);
			proc.call(valueM, valueO, valueQ, valueS);
			d.set(l, valueS);
			i += aStride;
			j += bStride;
			k += cStride;
			l += dStride;
		}
	}

	/**
	 * In place transformation of one whole list by a Procedure4.
	 * 
	 * @param grp
	 * @param proc
	 * @param a
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T grp, Procedure4<U,U,U,U> proc, IndexedDataSource<?,U> a)
	{
		U value1 = grp.construct();
		U value2 = grp.construct();
		for (long i = 0; i < a.size(); i++) {
			a.get(i, value1);
			proc.call(value1, value1, value1, value2);
			a.set(i, value2);
		}
	}
}
