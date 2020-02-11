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
import nom.bdezonia.zorbage.tuple.Tuple5;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple5Algebra<A extends Algebra<A,B>,B,
							C extends Algebra<C,D>,D,
							E extends Algebra<E,F>,F,
							G extends Algebra<G,H>,H,
							I extends Algebra<I,J>,J>
	implements Algebra<Tuple5Algebra<A,B,C,D,E,F,G,H,I,J>, Tuple5<B,D,F,H,J>>
{
	private final A alg1;
	private final C alg2;
	private final E alg3;
	private final G alg4;
	private final I alg5;
	
	public Tuple5Algebra(A alg1, C alg2, E alg3, G alg4, I alg5) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.alg3 = alg3;
		this.alg4 = alg4;
		this.alg5 = alg5;
	}
	
	@Override
	public Tuple5<B,D,F,H,J> construct() {
		return new Tuple5<B,D,F,H,J>(
				alg1.construct(),
				alg2.construct(),
				alg3.construct(),
				alg4.construct(),
				alg5.construct());
	}

	@Override
	public Tuple5<B,D,F,H,J> construct(Tuple5<B,D,F,H,J> other) {
		Tuple5<B,D,F,H,J> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple5<B,D,F,H,J> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> EQ =
			new Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>>()
	{
		@Override
		public Boolean call(Tuple5<B,D,F,H,J> a, Tuple5<B,D,F,H,J> b) {
			return alg1.isEqual().call(a.a(), b.a()) &&
					alg2.isEqual().call(a.b(), b.b()) &&
					alg3.isEqual().call(a.c(), b.c()) &&
					alg4.isEqual().call(a.d(), b.d()) &&
					alg5.isEqual().call(a.e(), b.e());
		}
	};

	@Override
	public Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> NEQ =
			new Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>>()
	{
		@Override
		public Boolean call(Tuple5<B,D,F,H,J> a, Tuple5<B,D,F,H,J> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> ASSIGN =
			new Procedure2<Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>>()
	{
		@Override
		public void call(Tuple5<B,D,F,H,J> a, Tuple5<B,D,F,H,J> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
			alg3.assign().call(a.c(), b.c());
			alg4.assign().call(a.d(), b.d());
			alg5.assign().call(a.e(), b.e());
		}
	};

	@Override
	public Procedure2<Tuple5<B,D,F,H,J>, Tuple5<B,D,F,H,J>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple5<B,D,F,H,J>> ISZERO =
			new Function1<Boolean, Tuple5<B,D,F,H,J>>()
	{
		@Override
		public Boolean call(Tuple5<B,D,F,H,J> a) {
			return alg1.isZero().call(a.a()) &&
					alg2.isZero().call(a.b()) &&
					alg3.isZero().call(a.c()) &&
					alg4.isZero().call(a.d()) &&
					alg5.isZero().call(a.e());
		}
	};

	@Override
	public Function1<Boolean, Tuple5<B,D,F,H,J>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple5<B,D,F,H,J>> ZERO =
			new Procedure1<Tuple5<B,D,F,H,J>>()
	{
		@Override
		public void call(Tuple5<B,D,F,H,J> a) {
			alg1.zero().call(a.a());
			alg2.zero().call(a.b());
			alg3.zero().call(a.c());
			alg4.zero().call(a.d());
			alg5.zero().call(a.e());
		}
	};
	
	@Override
	public Procedure1<Tuple5<B,D,F,H,J>> zero() {
		return ZERO;
	}
}
