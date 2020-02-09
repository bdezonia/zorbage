/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.tuple;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple6;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple6Algebra<A extends Algebra<A,B>,B,
							C extends Algebra<C,D>,D,
							E extends Algebra<E,F>,F,
							G extends Algebra<G,H>,H,
							I extends Algebra<I,J>,J,
							K extends Algebra<K,L>,L>
	implements Algebra<Tuple6Algebra<A,B,C,D,E,F,G,H,I,J,K,L>, Tuple6<B,D,F,H,J,L>>
{
	private final A alg1;
	private final C alg2;
	private final E alg3;
	private final G alg4;
	private final I alg5;
	private final K alg6;
	private final B z1;
	private final D z2;
	private final F z3;
	private final H z4;
	private final J z5;
	private final L z6;
	
	public Tuple6Algebra(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.alg3 = alg3;
		this.alg4 = alg4;
		this.alg5 = alg5;
		this.alg6 = alg6;
		this.z1 = alg1.construct();
		this.z2 = alg2.construct();
		this.z3 = alg3.construct();
		this.z4 = alg4.construct();
		this.z5 = alg5.construct();
		this.z6 = alg6.construct();
	}
	
	@Override
	public Tuple6<B,D,F,H,J,L> construct() {
		return new Tuple6<B,D,F,H,J,L>(
				alg1.construct(),
				alg2.construct(),
				alg3.construct(),
				alg4.construct(),
				alg5.construct(),
				alg6.construct());
	}

	@Override
	public Tuple6<B,D,F,H,J,L> construct(Tuple6<B,D,F,H,J,L> other) {
		Tuple6<B,D,F,H,J,L> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple6<B,D,F,H,J,L> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> EQ =
			new Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>>()
	{
		@Override
		public Boolean call(Tuple6<B,D,F,H,J,L> a, Tuple6<B,D,F,H,J,L> b) {
			return alg1.isEqual().call(a.a(), b.a()) &&
					alg2.isEqual().call(a.b(), b.b()) &&
					alg3.isEqual().call(a.c(), b.c()) &&
					alg4.isEqual().call(a.d(), b.d()) &&
					alg5.isEqual().call(a.e(), b.e()) &&
					alg6.isEqual().call(a.f(), b.f());
		}
	};

	@Override
	public Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> NEQ =
			new Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>>()
	{
		@Override
		public Boolean call(Tuple6<B,D,F,H,J,L> a, Tuple6<B,D,F,H,J,L> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> ASSIGN =
			new Procedure2<Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>>()
	{
		@Override
		public void call(Tuple6<B,D,F,H,J,L> a, Tuple6<B,D,F,H,J,L> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
			alg3.assign().call(a.c(), b.c());
			alg4.assign().call(a.d(), b.d());
			alg5.assign().call(a.e(), b.e());
			alg6.assign().call(a.f(), b.f());
		}
	};

	@Override
	public Procedure2<Tuple6<B,D,F,H,J,L>, Tuple6<B,D,F,H,J,L>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple6<B,D,F,H,J,L>> ISZERO =
			new Function1<Boolean, Tuple6<B,D,F,H,J,L>>()
	{
		@Override
		public Boolean call(Tuple6<B,D,F,H,J,L> a) {
			return alg1.isZero().call(a.a()) &&
					alg2.isZero().call(a.b()) &&
					alg3.isZero().call(a.c()) &&
					alg4.isZero().call(a.d()) &&
					alg5.isZero().call(a.e()) &&
					alg6.isZero().call(a.f());
		}
	};

	@Override
	public Function1<Boolean, Tuple6<B,D,F,H,J,L>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple6<B,D,F,H,J,L>> ZERO =
			new Procedure1<Tuple6<B,D,F,H,J,L>>()
	{
		@Override
		public void call(Tuple6<B,D,F,H,J,L> a) {
			alg1.assign().call(z1, a.a());
			alg2.assign().call(z2, a.b());
			alg2.assign().call(z2, a.b());
			alg3.assign().call(z3, a.c());
			alg4.assign().call(z4, a.d());
			alg5.assign().call(z5, a.e());
			alg6.assign().call(z6, a.f());
		}
	};
	
	@Override
	public Procedure1<Tuple6<B,D,F,H,J,L>> zero() {
		return ZERO;
	}
}
