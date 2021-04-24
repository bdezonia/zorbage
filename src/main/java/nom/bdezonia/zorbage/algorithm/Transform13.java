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

import nom.bdezonia.zorbage.procedure.Procedure13;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform13 {

	private Transform13() { }

	/**
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
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure13<A,A,A,A,A,A,A,A,A,A,A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c, IndexedDataSource<A> d, IndexedDataSource<A> e, IndexedDataSource<A> f, IndexedDataSource<A> g, IndexedDataSource<A> h, IndexedDataSource<A> i, IndexedDataSource<A> j, IndexedDataSource<A> k, IndexedDataSource<A> l, IndexedDataSource<A> m)
	{
		compute(alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, alg, proc, a, b, c, d, e, f, g, h, i, j, k, l, m);
	}

	/**
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
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C, DD extends Algebra<DD,D>, D, EE extends Algebra<EE,E>, E, FF extends Algebra<FF,F>, F, GG extends Algebra<GG,G>, G, HH extends Algebra<HH,H>, H, II extends Algebra<II,I>, I, JJ extends Algebra<JJ,J>, J, KK extends Algebra<KK,K>, K, LL extends Algebra<LL,L>, L, MM extends Algebra<MM,M>, M>
		void compute(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, Procedure13<A,B,C,D,E,F,G,H,I,J,K,L,M> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c, IndexedDataSource<D> d, IndexedDataSource<E> e, IndexedDataSource<F> f, IndexedDataSource<G> g, IndexedDataSource<H> h, IndexedDataSource<I> ii, IndexedDataSource<J> j, IndexedDataSource<K> k, IndexedDataSource<L> l, IndexedDataSource<M> m)
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
		long aSize = a.size();
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
			proc.call(valueA, valueB, valueC, valueD, valueE, valueF, valueG, valueH, valueI, valueJ, valueK, valueL, valueM);
			m.set(i, valueM);
		}
	}

}
