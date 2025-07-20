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
package nom.bdezonia.zorbage.tuple;

import nom.bdezonia.zorbage.accessor.AccessorA;
import nom.bdezonia.zorbage.accessor.AccessorB;
import nom.bdezonia.zorbage.accessor.AccessorC;
import nom.bdezonia.zorbage.accessor.AccessorD;
import nom.bdezonia.zorbage.accessor.AccessorE;
import nom.bdezonia.zorbage.accessor.AccessorF;
import nom.bdezonia.zorbage.accessor.AccessorG;
import nom.bdezonia.zorbage.accessor.AccessorH;
import nom.bdezonia.zorbage.accessor.AccessorI;
import nom.bdezonia.zorbage.accessor.AccessorJ;
import nom.bdezonia.zorbage.accessor.AccessorK;
import nom.bdezonia.zorbage.accessor.AccessorL;
import nom.bdezonia.zorbage.accessor.AccessorM;
import nom.bdezonia.zorbage.accessor.AccessorN;
import nom.bdezonia.zorbage.accessor.AccessorO;
import nom.bdezonia.zorbage.accessor.AccessorP;
import nom.bdezonia.zorbage.accessor.AccessorQ;
import nom.bdezonia.zorbage.accessor.AccessorR;
import nom.bdezonia.zorbage.accessor.AccessorS;
import nom.bdezonia.zorbage.accessor.AccessorT;
import nom.bdezonia.zorbage.accessor.AccessorU;
import nom.bdezonia.zorbage.accessor.AccessorV;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 * @param <F>
 * @param <G>
 * @param <H>
 * @param <I>
 * @param <J>
 * @param <K>
 * @param <L>
 * @param <M>
 * @param <N>
 * @param <O>
 * @param <P>
 * @param <Q>
 * @param <R>
 * @param <S>
 * @param <T>
 * @param <U>
 * @param <V>
 */
public class Tuple22<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V>
	implements
		AccessorA<A>, AccessorB<B>, AccessorC<C>, AccessorD<D>, AccessorE<E>, AccessorF<F>, AccessorG<G>,
		AccessorH<H>, AccessorI<I>, AccessorJ<J>, AccessorK<K>, AccessorL<L>, AccessorM<M>, AccessorN<N>,
		AccessorO<O>, AccessorP<P>, AccessorQ<Q>, AccessorR<R>, AccessorS<S>, AccessorT<T>, AccessorU<U>,
		AccessorV<V>,
		CompoundType
{
	
	private A a;
	private B b;
	private C c;
	private D d;
	private E e;
	private F f;
	private G g;
	private H h;
	private I i;
	private J j;
	private K k;
	private L l;
	private M m;
	private N n;
	private O o;
	private P p;
	private Q q;
	private R r;
	private S s;
	private T t;
	private U u;
	private V v;
	
	public Tuple22(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q, R r, S s, T t, U u, V v) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.i = i;
		this.j = j;
		this.k = k;
		this.l = l;
		this.m = m;
		this.n = n;
		this.o = o;
		this.p = p;
		this.q = q;
		this.r = r;
		this.s = s;
		this.t = t;
		this.u = u;
		this.v = v;
	}
	
	@Override
	public A a() { return a; }

	@Override
	public void setA(A a) { this.a = a; }

	@Override
	public B b() { return b; }

	@Override
	public void setB(B b) { this.b = b; }

	@Override
	public C c() { return c; }

	@Override
	public void setC(C c) { this.c = c; }

	@Override
	public D d() { return d; }

	@Override
	public void setD(D d) { this.d = d; }

	@Override
	public E e() { return e; }

	@Override
	public void setE(E e) { this.e = e; }

	@Override
	public F f() { return f; }

	@Override
	public void setF(F f) { this.f = f; }

	@Override
	public G g() { return g; }

	@Override
	public void setG(G g) { this.g = g; }

	@Override
	public H h() { return h; }

	@Override
	public void setH(H h) { this.h = h; }

	@Override
	public I i() { return i; }

	@Override
	public void setI(I i) { this.i = i; }

	@Override
	public J j() { return j; }

	@Override
	public void setJ(J j) { this.j = j; }

	@Override
	public K k() { return k; }

	@Override
	public void setK(K k) { this.k = k; }

	@Override
	public L l() { return l; }

	@Override
	public void setL(L l) { this.l = l; }

	@Override
	public M m() { return m; }

	@Override
	public void setM(M m) { this.m = m; }

	@Override
	public N n() { return n; }

	@Override
	public void setN(N n) { this.n = n; }

	@Override
	public O o() { return o; }

	@Override
	public void setO(O o) { this.o = o; }

	@Override
	public P p() { return p; }

	@Override
	public void setP(P p) { this.p = p; }

	@Override
	public Q q() { return q; }

	@Override
	public void setQ(Q q) { this.q = q; }

	@Override
	public R r() { return r; }

	@Override
	public void setR(R r) { this.r = r; }

	@Override
	public S s() { return s; }

	@Override
	public void setS(S s) { this.s = s; }

	@Override
	public T t() { return t; }

	@Override
	public void setT(T t) { this.t = t; }

	@Override
	public U u() { return u; }

	@Override
	public void setU(U u) { this.u = u; }

	@Override
	public V v() { return v; }

	@Override
	public void setV(V v) { this.v = v; }

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + a.hashCode();
		v = Hasher.PRIME * v + b.hashCode();
		v = Hasher.PRIME * v + c.hashCode();
		v = Hasher.PRIME * v + d.hashCode();
		v = Hasher.PRIME * v + e.hashCode();
		v = Hasher.PRIME * v + f.hashCode();
		v = Hasher.PRIME * v + g.hashCode();
		v = Hasher.PRIME * v + h.hashCode();
		v = Hasher.PRIME * v + i.hashCode();
		v = Hasher.PRIME * v + j.hashCode();
		v = Hasher.PRIME * v + k.hashCode();
		v = Hasher.PRIME * v + l.hashCode();
		v = Hasher.PRIME * v + m.hashCode();
		v = Hasher.PRIME * v + n.hashCode();
		v = Hasher.PRIME * v + o.hashCode();
		v = Hasher.PRIME * v + p.hashCode();
		v = Hasher.PRIME * v + q.hashCode();
		v = Hasher.PRIME * v + r.hashCode();
		v = Hasher.PRIME * v + s.hashCode();
		v = Hasher.PRIME * v + t.hashCode();
		v = Hasher.PRIME * v + u.hashCode();
		v = Hasher.PRIME * v + this.v.hashCode();
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof Tuple22) {
			@SuppressWarnings("rawtypes")
			Tuple22 other = (Tuple22) o;
			return
					a.equals(other.a) &&
					b.equals(other.b) &&
					c.equals(other.c) &&
					d.equals(other.d) &&
					e.equals(other.e) &&
					f.equals(other.f) &&
					g.equals(other.g) &&
					h.equals(other.h) &&
					i.equals(other.i) &&
					j.equals(other.j) &&
					k.equals(other.k) &&
					l.equals(other.l) &&
					m.equals(other.m) &&
					n.equals(other.n) &&
					this.o.equals(other.o) &&
					p.equals(other.p) &&
					q.equals(other.q) &&
					r.equals(other.r) &&
					s.equals(other.s) &&
					t.equals(other.t) &&
					u.equals(other.u) &&
					v.equals(other.v);
		}
		return false;
	}
}
