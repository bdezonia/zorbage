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
 */
public class Tuple9<A,B,C,D,E,F,G,H,I>
	implements
		AccessorA<A>, AccessorB<B>, AccessorC<C>, AccessorD<D>, AccessorE<E>, AccessorF<F>, AccessorG<G>,
		AccessorH<H>, AccessorI<I>,
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
	
	public Tuple9(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
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
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof Tuple9) {
			@SuppressWarnings("rawtypes")
			Tuple9 other = (Tuple9) o;
			return
					a.equals(other.a) &&
					b.equals(other.b) &&
					c.equals(other.c) &&
					d.equals(other.d) &&
					e.equals(other.e) &&
					f.equals(other.f) &&
					g.equals(other.g) &&
					h.equals(other.h) &&
					i.equals(other.i);
		}
		return false;
	}
}
