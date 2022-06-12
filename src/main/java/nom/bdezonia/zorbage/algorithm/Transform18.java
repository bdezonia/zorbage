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

import nom.bdezonia.zorbage.procedure.Procedure18;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform18 {

	// do not instantiate
	
	private Transform18() { }

	/**
	 * Transform seventeen lists into an eighteenth list using a function/procedure call at each point
	 * in the seventeen lists. Uses a single threaded approach since certain data structures do not
	 * handle parallel access very well.
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
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure18<A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f, IndexedDataSource<A> g, IndexedDataSource<A> h, IndexedDataSource<A> i, IndexedDataSource<A> j, IndexedDataSource<A> k, IndexedDataSource<A> l, IndexedDataSource<A> m, IndexedDataSource<A> n, IndexedDataSource<A> o, IndexedDataSource<A> p, IndexedDataSource<A> q, IndexedDataSource<A> r)
	{
		compute(alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r);
	}

	/**
	 * Transform seventeen lists into an eighteenth list using a function/procedure call at each point
	 * in the seventeen lists. Uses a single threaded approach since certain data structures do not
	 * handle parallel access very well.
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
	 * @param r
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M, NN extends Algebra<NN,N>, N, OO extends Algebra<OO,O>, O, PP extends Algebra<PP,P>, P, QQ extends Algebra<QQ,Q>, Q, RR extends Algebra<RR,R>, R>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, QQ algQ, RR algR, Procedure18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m, IndexedDataSource<N> n, IndexedDataSource<O> o, IndexedDataSource<P> p, IndexedDataSource<Q> q, IndexedDataSource<R> r)
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
		Q valueQ = algQ.construct();
		R valueR = algR.construct();

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
				p.size() != aSize ||
				q.size() != aSize ||
				r.size() != aSize)
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
			p.get(i, valueP);
			q.get(i, valueQ);
			proc.call(valueA, valueB, valueC, valueD, valueE, valueF, valueG, valueH, valueI, valueJ, valueK, valueL, valueM, valueN, valueO, valueP, valueQ, valueR);
			r.set(i, valueR);
		}
	}

}
