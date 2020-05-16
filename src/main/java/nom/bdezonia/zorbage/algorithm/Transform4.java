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

import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform4 {

	private Transform4() { }

	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static final <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure4<U,U,U,U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c, IndexedDataSource<U> d)
	{
		compute(algU, algU, algU, algU, proc, a, b, c, d);
	}

	/**
	 * 
	 * @param algM
	 * @param algO
	 * @param algQ
	 * @param algS
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static final <L extends Algebra<L,M>, M, N extends Algebra<N,O>, O, P extends Algebra<P,Q>, Q, R extends Algebra<R,S>, S>
		void compute(L algM, N algO, P algQ, R algS, Procedure4<M,O,Q,S> proc, IndexedDataSource<M> a, IndexedDataSource<O> b, IndexedDataSource<Q> c, IndexedDataSource<S> d)
	{
		M valueM = algM.construct();
		O valueO = algO.construct();
		Q valueQ = algQ.construct();
		S valueS = algS.construct();
		long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueM);
			b.get(i, valueO);
			c.get(i, valueQ);
			proc.call(valueM, valueO, valueQ, valueS);
			d.set(i, valueS);
		}
	}

}
