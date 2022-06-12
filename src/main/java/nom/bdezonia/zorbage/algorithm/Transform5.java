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

import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform5 {

	// do not instantiate
	
	private Transform5() { }

	/**
	 * Transform four lists into a fifth list using a function/procedure call at each point
	 * in the four lists. Uses a single threaded approach since certain data structures do not
	 * handle parallel access very well.
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure5<A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e)
	{
		compute(alg, alg, alg, alg, alg, proc, a, b, c, d, e);
	}

	/**
	 * Transform four lists into a fifth list using a function/procedure call at each point
	 * in the four lists. Uses a single threaded approach since certain data structures do not
	 * handle parallel access very well.
	 * 
	 * @param algA
	 * @param algB
	 * @param algC
	 * @param algD
	 * @param algE
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, Procedure5<A,B,C,D,E> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e)
	{
		A valueA = algA.construct();
		B valueB = algB.construct();
		C valueC = algC.construct();
		D valueD = algD.construct();
		E valueE = algE.construct();

		final long aSize = a.size();
		
		if (b.size() != aSize ||
				c.size() != aSize ||
				d.size() != aSize ||
				e.size() != aSize)
			throw new IllegalArgumentException("mismatched list sizes");

		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			b.get(i, valueB);
			c.get(i, valueC);
			d.get(i, valueD);
			proc.call(valueA, valueB, valueC, valueD, valueE);
			e.set(i, valueE);
		}
	}

}
