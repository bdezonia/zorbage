/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.tuple;

import nom.bdezonia.zorbage.accessor.AccessorA;
import nom.bdezonia.zorbage.accessor.AccessorB;
import nom.bdezonia.zorbage.accessor.AccessorC;
import nom.bdezonia.zorbage.accessor.AccessorD;
import nom.bdezonia.zorbage.accessor.AccessorE;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 */
public class Tuple5<A,B,C,D,E>
	implements
		AccessorA<A>, AccessorB<B>, AccessorC<C>, AccessorD<D>, AccessorE<E>
{
	
	private A a;
	private B b;
	private C c;
	private D d;
	private E e;
	
	public Tuple5(A a, B b, C c, D d, E e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
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

}
