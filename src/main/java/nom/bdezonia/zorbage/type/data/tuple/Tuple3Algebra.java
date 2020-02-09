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
import nom.bdezonia.zorbage.tuple.Tuple3;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple3Algebra<A extends Algebra<A,B>,B,
							C extends Algebra<C,D>,D,
							E extends Algebra<E,F>,F>
	implements Algebra<Tuple3Algebra<A,B,C,D,E,F>, Tuple3<B,D,F>>
{
	private final A alg1;
	private final C alg2;
	private final E alg3;
	private final B z1;
	private final D z2;
	private final F z3;
	
	public Tuple3Algebra(A alg1, C alg2, E alg3) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.alg3 = alg3;
		this.z1 = alg1.construct();
		this.z2 = alg2.construct();
		this.z3 = alg3.construct();
	}
	
	@Override
	public Tuple3<B,D,F> construct() {
		return new Tuple3<B,D,F>(
				alg1.construct(),
				alg2.construct(),
				alg3.construct());
	}

	@Override
	public Tuple3<B,D,F> construct(Tuple3<B,D,F> other) {
		Tuple3<B,D,F> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple3<B,D,F> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>> EQ =
			new Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>>()
	{
		@Override
		public Boolean call(Tuple3<B,D,F> a, Tuple3<B,D,F> b) {
			return alg1.isEqual().call(a.a(), b.a()) &&
					alg2.isEqual().call(a.b(), b.b()) &&
					alg3.isEqual().call(a.c(), b.c());
		}
	};

	@Override
	public Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>> NEQ =
			new Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>>()
	{
		@Override
		public Boolean call(Tuple3<B,D,F> a, Tuple3<B,D,F> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple3<B,D,F>, Tuple3<B,D,F>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple3<B,D,F>, Tuple3<B,D,F>> ASSIGN =
			new Procedure2<Tuple3<B,D,F>, Tuple3<B,D,F>>()
	{
		@Override
		public void call(Tuple3<B,D,F> a, Tuple3<B,D,F> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
			alg3.assign().call(a.c(), b.c());
		}
	};

	@Override
	public Procedure2<Tuple3<B,D,F>, Tuple3<B,D,F>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple3<B,D,F>> ISZERO =
			new Function1<Boolean, Tuple3<B,D,F>>()
	{
		@Override
		public Boolean call(Tuple3<B,D,F> a) {
			return alg1.isZero().call(a.a()) &&
					alg2.isZero().call(a.b()) &&
					alg3.isZero().call(a.c());
		}
	};

	@Override
	public Function1<Boolean, Tuple3<B,D,F>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple3<B,D,F>> ZERO =
			new Procedure1<Tuple3<B,D,F>>()
	{
		@Override
		public void call(Tuple3<B,D,F> a) {
			alg1.assign().call(z1, a.a());
			alg2.assign().call(z2, a.b());
			alg3.assign().call(z3, a.c());
		}
	};
	
	@Override
	public Procedure1<Tuple3<B,D,F>> zero() {
		return ZERO;
	}
}
