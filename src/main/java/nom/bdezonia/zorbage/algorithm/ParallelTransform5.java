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
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform5 {

	// do not instantiate
	
	private ParallelTransform5() { }
	
	/**
	 * Transform four lists into a fifth list using a function/procedure call at each point
	 * in the four lists. Use a parallel algorithm for extra speed.
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
	 * in the four lists. Use a parallel algorithm for extra speed.
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
		long aSize = a.size();
		int numProcs = Runtime.getRuntime().availableProcessors();
		if (aSize < numProcs) {
			numProcs = (int) aSize;
		}
		if (a.accessWithOneThread() || b.accessWithOneThread() || c.accessWithOneThread() || d.accessWithOneThread() ||
				e.accessWithOneThread())
			numProcs = 1;
		final Thread[] threads = new Thread[numProcs];
		long thOffset = 0;
		long slice = aSize / numProcs;
		for (int i = 0; i < numProcs; i++) {
			long thSize;
			if (i != numProcs-1) {
				thSize = slice;
			}
			else {
				thSize = aSize;
			}
			IndexedDataSource<A> aTrimmed = new TrimmedDataSource<>(a, thOffset, thSize);
			IndexedDataSource<B> bTrimmed = new TrimmedDataSource<>(b, thOffset, thSize);
			IndexedDataSource<C> cTrimmed = new TrimmedDataSource<>(c, thOffset, thSize);
			IndexedDataSource<D> dTrimmed = new TrimmedDataSource<>(d, thOffset, thSize);
			IndexedDataSource<E> eTrimmed = new TrimmedDataSource<>(e, thOffset, thSize);
			Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E>(algA, algB, algC, algD, algE, proc, aTrimmed, bTrimmed, cTrimmed, dTrimmed, eTrimmed);
			threads[i] = new Thread(r);
			thOffset += slice;
			aSize -= slice;
		}
		for (int i = 0; i < numProcs; i++) {
			threads[i].start();
		}
		for (int i = 0; i < numProcs; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException exc) {
				throw new IllegalArgumentException("Thread execution error in ParallelTransform");
			}
		}
	}
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E>
		implements Runnable
	{
		private final AA algebraA;
		private final BB algebraB;
		private final CC algebraC;
		private final DD algebraD;
		private final EE algebraE;
		private final IndexedDataSource<A> listA;
		private final IndexedDataSource<B> listB;
		private final IndexedDataSource<C> listC;
		private final IndexedDataSource<D> listD;
		private final IndexedDataSource<E> listE;
		private final Procedure5<A,B,C,D,E> proc;
		
		Computer(AA algA, BB algB, CC algC, DD algD, EE algE, Procedure5<A,B,C,D,E> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e) {
			algebraA = algA;
			algebraB = algB;
			algebraC = algC;
			algebraD = algD;
			algebraE = algE;
			listA = a;
			listB = b;
			listC = c;
			listD = d;
			listE = e;
			this.proc = proc;
		}
		
		public void run() {
			Transform5.compute(algebraA, algebraB, algebraC, algebraD, algebraE, proc, listA, listB, listC, listD, listE);
		}
	}
}
