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
import nom.bdezonia.zorbage.tuple.Tuple9;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple9Algebra<A extends Algebra<A,B>,B,
							C extends Algebra<C,D>,D,
							E extends Algebra<E,F>,F,
							G extends Algebra<G,H>,H,
							I extends Algebra<I,J>,J,
							K extends Algebra<K,L>,L,
							M extends Algebra<M,N>,N,
							O extends Algebra<O,P>,P,
							Q extends Algebra<Q,R>,R>
	implements Algebra<Tuple9Algebra<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple9<B,D,F,H,J,L,N,P,R>>
{
	private final A alg1;
	private final C alg2;
	private final E alg3;
	private final G alg4;
	private final I alg5;
	private final K alg6;
	private final M alg7;
	private final O alg8;
	private final Q alg9;
	
	public Tuple9Algebra(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7, O alg8, Q alg9) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.alg3 = alg3;
		this.alg4 = alg4;
		this.alg5 = alg5;
		this.alg6 = alg6;
		this.alg7 = alg7;
		this.alg8 = alg8;
		this.alg9 = alg9;
	}
	
	@Override
	public Tuple9<B,D,F,H,J,L,N,P,R> construct() {
		return new Tuple9<B,D,F,H,J,L,N,P,R>(
				alg1.construct(),
				alg2.construct(),
				alg3.construct(),
				alg4.construct(),
				alg5.construct(),
				alg6.construct(),
				alg7.construct(),
				alg8.construct(),
				alg9.construct());
	}

	@Override
	public Tuple9<B,D,F,H,J,L,N,P,R> construct(Tuple9<B,D,F,H,J,L,N,P,R> other) {
		Tuple9<B,D,F,H,J,L,N,P,R> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple9<B,D,F,H,J,L,N,P,R> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> EQ =
			new Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>>()
	{
		@Override
		public Boolean call(Tuple9<B,D,F,H,J,L,N,P,R> a, Tuple9<B,D,F,H,J,L,N,P,R> b) {
			return alg1.isEqual().call(a.a(), b.a()) &&
					alg2.isEqual().call(a.b(), b.b()) &&
					alg3.isEqual().call(a.c(), b.c()) &&
					alg4.isEqual().call(a.d(), b.d()) &&
					alg5.isEqual().call(a.e(), b.e()) &&
					alg6.isEqual().call(a.f(), b.f()) &&
					alg7.isEqual().call(a.g(), b.g()) &&
					alg8.isEqual().call(a.h(), b.h()) &&
					alg9.isEqual().call(a.i(), b.i());
		}
	};

	@Override
	public Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> NEQ =
			new Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>>()
	{
		@Override
		public Boolean call(Tuple9<B,D,F,H,J,L,N,P,R> a, Tuple9<B,D,F,H,J,L,N,P,R> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> ASSIGN =
			new Procedure2<Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>>()
	{
		@Override
		public void call(Tuple9<B,D,F,H,J,L,N,P,R> a, Tuple9<B,D,F,H,J,L,N,P,R> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
			alg3.assign().call(a.c(), b.c());
			alg4.assign().call(a.d(), b.d());
			alg5.assign().call(a.e(), b.e());
			alg6.assign().call(a.f(), b.f());
			alg7.assign().call(a.g(), b.g());
			alg8.assign().call(a.h(), b.h());
			alg9.assign().call(a.i(), b.i());
		}
	};

	@Override
	public Procedure2<Tuple9<B,D,F,H,J,L,N,P,R>, Tuple9<B,D,F,H,J,L,N,P,R>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>> ISZERO =
			new Function1<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>>()
	{
		@Override
		public Boolean call(Tuple9<B,D,F,H,J,L,N,P,R> a) {
			return alg1.isZero().call(a.a()) &&
					alg2.isZero().call(a.b()) &&
					alg3.isZero().call(a.c()) &&
					alg4.isZero().call(a.d()) &&
					alg5.isZero().call(a.e()) &&
					alg6.isZero().call(a.f()) &&
					alg7.isZero().call(a.g()) &&
					alg8.isZero().call(a.h()) &&
					alg9.isZero().call(a.i());
		}
	};

	@Override
	public Function1<Boolean, Tuple9<B,D,F,H,J,L,N,P,R>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple9<B,D,F,H,J,L,N,P,R>> ZERO =
			new Procedure1<Tuple9<B,D,F,H,J,L,N,P,R>>()
	{
		@Override
		public void call(Tuple9<B,D,F,H,J,L,N,P,R> a) {
			alg1.zero().call(a.a());
			alg2.zero().call(a.b());
			alg3.zero().call(a.c());
			alg4.zero().call(a.d());
			alg5.zero().call(a.e());
			alg6.zero().call(a.f());
			alg7.zero().call(a.g());
			alg8.zero().call(a.h());
			alg9.zero().call(a.i());
		}
	};
	
	@Override
	public Procedure1<Tuple9<B,D,F,H,J,L,N,P,R>> zero() {
		return ZERO;
	}
}
