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

import nom.bdezonia.zorbage.procedure.Procedure11;
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
public class ParallelTransform11 {

	// do not instantiate
	
	private ParallelTransform11() { }
	
	/**
	 * Transform ten lists into a eleventh list using a function/procedure call at each point
	 * in the ten lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 * @param g
	 * @param h
	 * @param i
	 * @param j
	 * @param k
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure11<A,A,A,A,A,A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f, IndexedDataSource<A> g, IndexedDataSource<A> h, IndexedDataSource<A> i, IndexedDataSource<A> j, IndexedDataSource<A> k)
	{
		compute(alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f, g, h, i, j, k);	
	}
	
	/**
	 * Transform ten lists into a eleventh list using a function/procedure call at each point
	 * in the ten lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param algA
	 * @param algB
	 * @param algC
	 * @param algD
	 * @param algE
	 * @param algF
	 * @param algG
	 * @param algH
	 * @param algI
	 * @param algJ
	 * @param algK
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 * @param g
	 * @param h
	 * @param ii
	 * @param j
	 * @param k
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, Procedure11<A,B,C,D,E,F,G,H,I,J,K> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k)
	{
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(a.size(),
										a.accessWithOneThread() ||
										b.accessWithOneThread() ||
										c.accessWithOneThread() ||
										d.accessWithOneThread() ||
										e.accessWithOneThread() ||
										f.accessWithOneThread() ||
										g.accessWithOneThread() ||
										h.accessWithOneThread() ||
										ii.accessWithOneThread() ||
										j.accessWithOneThread() ||
										k.accessWithOneThread());
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
			IndexedDataSource<G> gTrimmed = new TrimmedDataSource<>(g, start, count);
			IndexedDataSource<H> hTrimmed = new TrimmedDataSource<>(h, start, count);
			IndexedDataSource<I> iTrimmed = new TrimmedDataSource<>(ii, start, count);
			IndexedDataSource<J> jTrimmed = new TrimmedDataSource<>(j, start, count);
			IndexedDataSource<K> kTrimmed = new TrimmedDataSource<>(k, start, count);
			Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K>(algA, algB, algC, algD, algE, algF, algG, algH, algI, algJ, algK, proc, aTrimmed, bTrimmed, cTrimmed, dTrimmed, eTrimmed, fTrimmed, gTrimmed, hTrimmed, iTrimmed, jTrimmed, kTrimmed);
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
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K>
		implements Runnable
	{
		private final AA algebraA;
		private final BB algebraB;
		private final CC algebraC;
		private final DD algebraD;
		private final EE algebraE;
		private final FF algebraF;
		private final GG algebraG;
		private final HH algebraH;
		private final II algebraI;
		private final JJ algebraJ;
		private final KK algebraK;
		private final IndexedDataSource<A> listA;
		private final IndexedDataSource<B> listB;
		private final IndexedDataSource<C> listC;
		private final IndexedDataSource<D> listD;
		private final IndexedDataSource<E> listE;
		private final IndexedDataSource<F> listF;
		private final IndexedDataSource<G> listG;
		private final IndexedDataSource<H> listH;
		private final IndexedDataSource<I> listI;
		private final IndexedDataSource<J> listJ;
		private final IndexedDataSource<K> listK;
		private final Procedure11<A,B,C,D,E,F,G,H,I,J,K> proc;
		
		Computer(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, Procedure11<A,B,C,D,E,F,G,H,I,J,K> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> i, IndexedDataSource<J> j, IndexedDataSource<K> k) {
			algebraA = algA;
			algebraB = algB;
			algebraC = algC;
			algebraD = algD;
			algebraE = algE;
			algebraF = algF;
			algebraG = algG;
			algebraH = algH;
			algebraI = algI;
			algebraJ = algJ;
			algebraK = algK;
			listA = a;
			listB = b;
			listC = c;
			listD = d;
			listE = e;
			listF = f;
			listG = g;
			listH = h;
			listI = i;
			listJ = j;
			listK = k;
			this.proc = proc;
		}
		
		public void run() {
			Transform11.compute(algebraA, algebraB, algebraC, algebraD, algebraE, algebraF, algebraG, algebraH, algebraI, algebraJ, algebraK, proc, listA, listB, listC, listD, listE, listF, listG, listH, listI, listJ, listK);
		}
	}
}
