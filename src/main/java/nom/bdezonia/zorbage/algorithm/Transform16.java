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

import nom.bdezonia.zorbage.procedure.Procedure16;
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
public class Transform16 {

	// do not instantiate
	
	private Transform16() { }
	
	/**
	 * Transform fifteen lists into a sixteenth list using a function/procedure call at each point
	 * in the fifteen lists. Use a parallel algorithm for extra speed.
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
	 * @param l
	 * @param m
	 * @param n
	 * @param o
	 * @param p
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure16<A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f, IndexedDataSource<A> g, IndexedDataSource<A> h, IndexedDataSource<A> i, IndexedDataSource<A> j, IndexedDataSource<A> k, IndexedDataSource<A> l, IndexedDataSource<A> m, IndexedDataSource<A> n, IndexedDataSource<A> o, IndexedDataSource<A> p)
	{
		compute(alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
	}
	
	/**
	 * Transform fifteen lists into a sixteenth list using a function/procedure call at each point
	 * in the fifteen lists. Use a parallel algorithm for extra speed.
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
	 * @param algL
	 * @param algM
	 * @param algN
	 * @param algO
	 * @param algP
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
	 * @param l
	 * @param m
	 * @param n
	 * @param o
	 * @param p
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, Procedure16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p)
	{
		long sz = a.size();
		
		if (sz == 0)
			return;

		if (b.size() != sz || c.size() != sz || d.size() != sz ||
				e.size() != sz || f.size() != sz || g.size() != sz ||
				h.size() != sz || ii.size() != sz || j.size() != sz ||
				k.size() != sz || l.size() != sz || m.size() != sz ||
				n.size() != sz || o.size() != sz || p.size() != sz)
			throw new IllegalArgumentException("mismatched list sizes");
		
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
										k.accessWithOneThread() ||
										l.accessWithOneThread() ||
										m.accessWithOneThread() ||
										n.accessWithOneThread() ||
										o.accessWithOneThread() ||
										p.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		if (pieces == 1) {

			Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N,OO,O,PP,P>(algA, algB, algC, algD, algE, algF, algG, algH, algI, algJ, algK, algL, algM, algN, algO, algP, proc, a, b, c, d, e, f, g, h, ii, j, k, l, m, n, o, p);
			r.run();
		}
		else {

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
				IndexedDataSource<L> lTrimmed = new TrimmedDataSource<>(l, start, count);
				IndexedDataSource<M> mTrimmed = new TrimmedDataSource<>(m, start, count);
				IndexedDataSource<N> nTrimmed = new TrimmedDataSource<>(n, start, count);
				IndexedDataSource<O> oTrimmed = new TrimmedDataSource<>(o, start, count);
				IndexedDataSource<P> pTrimmed = new TrimmedDataSource<>(p, start, count);
				Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N,OO,O,PP,P>(algA, algB, algC, algD, algE, algF, algG, algH, algI, algJ, algK, algL, algM, algN, algO, algP, proc, aTrimmed, bTrimmed, cTrimmed, dTrimmed, eTrimmed, fTrimmed, gTrimmed, hTrimmed, iTrimmed, jTrimmed, kTrimmed, lTrimmed, mTrimmed, nTrimmed, oTrimmed, pTrimmed);
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
	}
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P>
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
		private final LL algebraL;
		private final MM algebraM;
		private final NN algebraN;
		private final OO algebraO;
		private final PP algebraP;
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
		private final IndexedDataSource<L> listL;
		private final IndexedDataSource<M> listM;
		private final IndexedDataSource<N> listN;
		private final IndexedDataSource<O> listO;
		private final IndexedDataSource<P> listP;
		private final Procedure16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P> proc;
		
		Computer(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, Procedure16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> i, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p) {
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
			algebraL = algL;
			algebraM = algM;
			algebraN = algN;
			algebraO = algO;
			algebraP = algP;
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
			listL = l;
			listM = m;
			listN = n;
			listO = o;
			listP = p;
			this.proc = proc;
		}
		
		public void run() {
			transform(algebraA, algebraB, algebraC, algebraD, algebraE, algebraF, algebraG, algebraH, algebraI, algebraJ, algebraK, algebraL, algebraM, algebraN, algebraO, algebraP, proc, listA, listB, listC, listD, listE, listF, listG, listH, listI, listJ, listK, listL, listM, listN, listO, listP);
		}
	}

	private static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P>
		void transform(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, Procedure16<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p)
	{
		A valueA = algA.construct();
		B valueB = algB.construct();
		C valueC = algC.construct();
		D valueD = algD.construct();
		E valueE = algE.construct();
		F valueF = algF.construct();
		G valueG = algG.construct();
		H valueH = algH.construct();
		I valueI = algI.construct();
		J valueJ = algJ.construct();
		K valueK = algK.construct();
		L valueL = algL.construct();
		M valueM = algM.construct();
		N valueN = algN.construct();
		O valueO = algO.construct();
		P valueP = algP.construct();
	
		final long aSize = a.size();
		
		if (b.size() != aSize ||
				c.size() != aSize ||
				d.size() != aSize ||
				e.size() != aSize ||
				f.size() != aSize ||
				g.size() != aSize ||
				h.size() != aSize ||
				ii.size() != aSize ||
				j.size() != aSize ||
				k.size() != aSize ||
				l.size() != aSize ||
				m.size() != aSize ||
				n.size() != aSize ||
				o.size() != aSize ||
				p.size() != aSize)
			throw new IllegalArgumentException("mismatched list sizes");
	
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			b.get(i, valueB);
			c.get(i, valueC);
			d.get(i, valueD);
			e.get(i, valueE);
			f.get(i, valueF);
			g.get(i, valueG);
			h.get(i, valueH);
			ii.get(i, valueI);
			j.get(i, valueJ);
			k.get(i, valueK);
			l.get(i, valueL);
			m.get(i, valueM);
			n.get(i, valueN);
			o.get(i, valueO);
			proc.call(valueA, valueB, valueC, valueD, valueE, valueF, valueG, valueH, valueI, valueJ, valueK, valueL, valueM, valueN, valueO, valueP);
			p.set(i, valueP);
		}
	}
}
