/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.procedure.Procedure26;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform26 {

	// do not instantiate
	
	private ParallelTransform26() { }
	
	/**
	 * Transform twenty five lists into a twenty sixth list using a function/procedure call at each point
	 * in the twenty five lists. Use a parallel algorithm for extra speed.
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
	 * @param q
	 * @param r
	 * @param s
	 * @param t
	 * @param u
	 * @param v
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure26<A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f, IndexedDataSource<A> g, IndexedDataSource<A> h, IndexedDataSource<A> i, IndexedDataSource<A> j, IndexedDataSource<A> k, IndexedDataSource<A> l, IndexedDataSource<A> m, IndexedDataSource<A> n, IndexedDataSource<A> o, IndexedDataSource<A> p, IndexedDataSource<A> q, IndexedDataSource<A> r, IndexedDataSource<A> s, IndexedDataSource<A> t, IndexedDataSource<A> u, IndexedDataSource<A> v, IndexedDataSource<A> w, IndexedDataSource<A> x, IndexedDataSource<A> y, IndexedDataSource<A> z)
	{
		compute(alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
	}
	
	/**
	 * Transform twenty five lists into a twenty sixth list using a function/procedure call at each point
	 * in the twenty five lists. Use a parallel algorithm for extra speed.
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
	 * @param algQ
	 * @param algR
	 * @param algS
	 * @param algT
	 * @param algU
	 * @param algV
	 * @param algW
	 * @param algX
	 * @param algY
	 * @param algZ
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
	 * @param q
	 * @param rr
	 * @param s
	 * @param t
	 * @param u
	 * @param v
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P, QQ extends Algebra<QQ,Q>, Q, RR extends Algebra<RR,R>, R, SS extends Algebra<SS,S>, S, TT extends Algebra<TT,T>, T, UU extends Algebra<UU,U>, U, VV extends Algebra<VV,V>, V, WW extends Algebra<WW,W>, W, XX extends Algebra<XX,X>, X, YY extends Algebra<YY,Y>, Y, ZZ extends Algebra<ZZ,Z>, Z>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, QQ algQ, RR algR, SS algS, TT algT, UU algU, VV algV, WW algW, XX algX, YY algY, ZZ algZ, Procedure26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p, IndexedDataSource<Q> q, IndexedDataSource<R> rr, IndexedDataSource<S> s, IndexedDataSource<T> t, IndexedDataSource<U> u, IndexedDataSource<V> v, IndexedDataSource<W> w, IndexedDataSource<X> x, IndexedDataSource<Y> y, IndexedDataSource<Z> z)
	{
		long aSize = a.size();
		int numProcs = Runtime.getRuntime().availableProcessors();
		if (aSize < numProcs) {
			numProcs = (int) aSize;
		}
		if (a.accessWithOneThread() || b.accessWithOneThread() || c.accessWithOneThread() || d.accessWithOneThread() ||
				e.accessWithOneThread() || f.accessWithOneThread() || g.accessWithOneThread() || h.accessWithOneThread() ||
				ii.accessWithOneThread() || j.accessWithOneThread() || k.accessWithOneThread() || l.accessWithOneThread() ||
				m.accessWithOneThread() || n.accessWithOneThread() || o.accessWithOneThread() || p.accessWithOneThread() ||
				q.accessWithOneThread() || rr.accessWithOneThread() || s.accessWithOneThread() || t.accessWithOneThread() ||
				u.accessWithOneThread() || v.accessWithOneThread() || w.accessWithOneThread() || x.accessWithOneThread() ||
				y.accessWithOneThread() || z.accessWithOneThread())
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
			IndexedDataSource<F> fTrimmed = new TrimmedDataSource<>(f, thOffset, thSize);
			IndexedDataSource<G> gTrimmed = new TrimmedDataSource<>(g, thOffset, thSize);
			IndexedDataSource<H> hTrimmed = new TrimmedDataSource<>(h, thOffset, thSize);
			IndexedDataSource<I> iTrimmed = new TrimmedDataSource<>(ii, thOffset, thSize);
			IndexedDataSource<J> jTrimmed = new TrimmedDataSource<>(j, thOffset, thSize);
			IndexedDataSource<K> kTrimmed = new TrimmedDataSource<>(k, thOffset, thSize);
			IndexedDataSource<L> lTrimmed = new TrimmedDataSource<>(l, thOffset, thSize);
			IndexedDataSource<M> mTrimmed = new TrimmedDataSource<>(m, thOffset, thSize);
			IndexedDataSource<N> nTrimmed = new TrimmedDataSource<>(n, thOffset, thSize);
			IndexedDataSource<O> oTrimmed = new TrimmedDataSource<>(o, thOffset, thSize);
			IndexedDataSource<P> pTrimmed = new TrimmedDataSource<>(p, thOffset, thSize);
			IndexedDataSource<Q> qTrimmed = new TrimmedDataSource<>(q, thOffset, thSize);
			IndexedDataSource<R> rTrimmed = new TrimmedDataSource<>(rr, thOffset, thSize);
			IndexedDataSource<S> sTrimmed = new TrimmedDataSource<>(s, thOffset, thSize);
			IndexedDataSource<T> tTrimmed = new TrimmedDataSource<>(t, thOffset, thSize);
			IndexedDataSource<U> uTrimmed = new TrimmedDataSource<>(u, thOffset, thSize);
			IndexedDataSource<V> vTrimmed = new TrimmedDataSource<>(v, thOffset, thSize);
			IndexedDataSource<W> wTrimmed = new TrimmedDataSource<>(w, thOffset, thSize);
			IndexedDataSource<X> xTrimmed = new TrimmedDataSource<>(x, thOffset, thSize);
			IndexedDataSource<Y> yTrimmed = new TrimmedDataSource<>(y, thOffset, thSize);
			IndexedDataSource<Z> zTrimmed = new TrimmedDataSource<>(z, thOffset, thSize);
			Runnable r = new Computer<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N,OO,O,PP,P,QQ,Q,RR,R,SS,S,TT,T,UU,U,VV,V,WW,W,XX,X,YY,Y,ZZ,Z>(algA, algB, algC, algD, algE, algF, algG, algH, algI, algJ, algK, algL, algM, algN, algO, algP, algQ, algR, algS, algT, algU, algV, algW, algX, algY, algZ, proc, aTrimmed, bTrimmed, cTrimmed, dTrimmed, eTrimmed, fTrimmed, gTrimmed, hTrimmed, iTrimmed, jTrimmed, kTrimmed, lTrimmed, mTrimmed, nTrimmed, oTrimmed, pTrimmed, qTrimmed, rTrimmed, sTrimmed, tTrimmed, uTrimmed, vTrimmed, wTrimmed, xTrimmed, yTrimmed, zTrimmed);
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
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P, QQ extends Algebra<QQ,Q>, Q, RR extends Algebra<RR,R>, R, SS extends Algebra<SS,S>, S, TT extends Algebra<TT,T>, T, UU extends Algebra<UU,U>, U, VV extends Algebra<VV,V>, V, WW extends Algebra<WW,W>, W, XX extends Algebra<XX,X>, X, YY extends Algebra<YY,Y>, Y, ZZ extends Algebra<ZZ,Z>, Z>
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
		private final QQ algebraQ;
		private final RR algebraR;
		private final SS algebraS;
		private final TT algebraT;
		private final UU algebraU;
		private final VV algebraV;
		private final WW algebraW;
		private final XX algebraX;
		private final YY algebraY;
		private final ZZ algebraZ;
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
		private final IndexedDataSource<Q> listQ;
		private final IndexedDataSource<R> listR;
		private final IndexedDataSource<S> listS;
		private final IndexedDataSource<T> listT;
		private final IndexedDataSource<U> listU;
		private final IndexedDataSource<V> listV;
		private final IndexedDataSource<W> listW;
		private final IndexedDataSource<X> listX;
		private final IndexedDataSource<Y> listY;
		private final IndexedDataSource<Z> listZ;
		private final Procedure26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> proc;
		
		Computer(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, QQ algQ, RR algR, SS algS, TT algT, UU algU, VV algV, WW algW, XX algX, YY algY, ZZ algZ, Procedure26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> i, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p, IndexedDataSource<Q> q, IndexedDataSource<R> r, IndexedDataSource<S> s, IndexedDataSource<T> t, IndexedDataSource<U> u, IndexedDataSource<V> v, IndexedDataSource<W> w, IndexedDataSource<X> x, IndexedDataSource<Y> y, IndexedDataSource<Z> z) {
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
			algebraQ = algQ;
			algebraR = algR;
			algebraS = algS;
			algebraT = algT;
			algebraU = algU;
			algebraV = algV;
			algebraW = algW;
			algebraX = algX;
			algebraY = algY;
			algebraZ = algZ;
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
			listQ = q;
			listR = r;
			listS = s;
			listT = t;
			listU = u;
			listV = v;
			listW = w;
			listX = x;
			listY = y;
			listZ = z;
			this.proc = proc;
		}
		
		public void run() {
			Transform26.compute(algebraA, algebraB, algebraC, algebraD, algebraE, algebraF, algebraG, algebraH, algebraI, algebraJ, algebraK, algebraL, algebraM, algebraN, algebraO, algebraP, algebraQ, algebraR, algebraS, algebraT, algebraU, algebraV, algebraW, algebraX, algebraY, algebraZ, proc, listA, listB, listC, listD, listE, listF, listG, listH, listI, listJ, listK, listL, listM, listN, listO, listP, listQ, listR, listS, listT, listU, listV, listW, listX, listY, listZ);
		}
	}
}
