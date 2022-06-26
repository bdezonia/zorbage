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

import nom.bdezonia.zorbage.procedure.Procedure6;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;
import nom.bdezonia.zorbage.misc.ThreadingUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform6 {

	// do not instantiate
	
	private ParallelTransform6() { }
	
	/**
	 * Transform five lists into a sixth list using a function/procedure call at each point
	 * in the five lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure6<A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f)
	{
		compute(alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f);	
	}
	
	/**
	 * Transform five lists into a sixth list using a function/procedure call at each point
	 * in the five lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param algA
	 * @param algB
	 * @param algC
	 * @param algD
	 * @param algE
	 * @param algF
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, Procedure6<A,B,C,D,E,F> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f)
	{
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(a.size(),
										a.accessWithOneThread() ||
										b.accessWithOneThread() ||
										c.accessWithOneThread() ||
										d.accessWithOneThread() ||
										e.accessWithOneThread() ||
										f.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		final Thread[] threads = new Thread[pieces];
		long start = 0;
		for (int i = 0; i < pieces; i++) {
			long count;
			if (i != pieces-1) {
				count = elemsPerPiece;
			}
			else {
				count = a.size() - start;
			}
			IndexedDataSource<A> aTrimmed = new TrimmedDataSource<>(a, start, count);
			IndexedDataSource<B> bTrimmed = new TrimmedDataSource<>(b, start, count);
			IndexedDataSource<C> cTrimmed = new TrimmedDataSource<>(c, start, count);
			IndexedDataSource<D> dTrimmed = new TrimmedDataSource<>(d, start, count);
			IndexedDataSource<E> eTrimmed = new TrimmedDataSource<>(e, start, count);
			IndexedDataSource<F> fTrimmed = new TrimmedDataSource<>(f, start, count);
			Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F>(algA, algB, algC, algD, algE, algF, proc, aTrimmed, bTrimmed, cTrimmed, dTrimmed, eTrimmed, fTrimmed);
			threads[i] = new Thread(r);
			start += count;
		}

		for (int i = 0; i < pieces; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < pieces; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException ex) {
				throw new IllegalArgumentException("Thread execution error in ParallelTransform");
			}
		}
	}
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F>
		implements Runnable
	{
		private final AA algebraA;
		private final BB algebraB;
		private final CC algebraC;
		private final DD algebraD;
		private final EE algebraE;
		private final FF algebraF;
		private final IndexedDataSource<A> listA;
		private final IndexedDataSource<B> listB;
		private final IndexedDataSource<C> listC;
		private final IndexedDataSource<D> listD;
		private final IndexedDataSource<E> listE;
		private final IndexedDataSource<F> listF;
		private final Procedure6<A,B,C,D,E,F> proc;
		
		Computer(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, Procedure6<A,B,C,D,E,F> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f) {
			algebraA = algA;
			algebraB = algB;
			algebraC = algC;
			algebraD = algD;
			algebraE = algE;
			algebraF = algF;
			listA = a;
			listB = b;
			listC = c;
			listD = d;
			listE = e;
			listF = f;
			this.proc = proc;
		}
		
		public void run() {
			Transform6.compute(algebraA, algebraB, algebraC, algebraD, algebraE, algebraF, proc, listA, listB, listC, listD, listE, listF);
		}
	}
}
