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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform3 {

	private Transform3() { }

	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure3<A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c)
	{
		compute(alg, alg, alg, proc, a, b, c);
	}
	
	/**
	 * 
	 * @param algA
	 * @param algB
	 * @param algC
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C>
		void compute(AA algA, BB algB, CC algC, Procedure3<A,B,C> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c)
	{
		A valueA = algA.construct();
		B valueB = algB.construct();
		C valueC = algC.construct();
		long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			b.get(i, valueB);
			proc.call(valueA, valueB, valueC);
			c.set(i, valueC);
		}
	}
}
