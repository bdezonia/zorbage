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
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple2Algebra<A extends Algebra<A,B>,B,C extends Algebra<C,D>,D>
	implements Algebra<Tuple2Algebra<A,B,C,D>, Tuple2<B,D>>
{
	private final A alg1;
	private final C alg2;
	private final B z1;
	private final D z2;
	
	public Tuple2Algebra(A alg1, C alg2) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.z1 = alg1.construct();
		this.z2 = alg2.construct();
	}
	
	@Override
	public Tuple2<B,D> construct() {
		return new Tuple2<B,D>(alg1.construct(), alg2.construct());
	}

	@Override
	public Tuple2<B,D> construct(Tuple2<B,D> other) {
		Tuple2<B,D> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple2<B, D> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>> EQ =
			new Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>>()
	{
		@Override
		public Boolean call(Tuple2<B,D> a, Tuple2<B,D> b) {
			return alg1.isEqual().call(a.a(), b.a()) && alg2.isEqual().call(a.b(), b.b());
		}
	};

	@Override
	public Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>> NEQ =
			new Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>>()
	{
		@Override
		public Boolean call(Tuple2<B,D> a, Tuple2<B,D> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple2<B,D>, Tuple2<B,D>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple2<B,D>, Tuple2<B,D>> ASSIGN =
			new Procedure2<Tuple2<B,D>, Tuple2<B,D>>()
	{
		@Override
		public void call(Tuple2<B,D> a, Tuple2<B,D> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
		}
	};

	@Override
	public Procedure2<Tuple2<B,D>, Tuple2<B,D>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple2<B,D>> ISZERO =
			new Function1<Boolean, Tuple2<B,D>>()
	{
		@Override
		public Boolean call(Tuple2<B,D> a) {
			return alg1.isZero().call(a.a()) && alg2.isZero().call(a.b());
		}
	};

	@Override
	public Function1<Boolean, Tuple2<B,D>> isZero() {
		return ISZERO;
	}

	private final Procedure1<Tuple2<B, D>> ZERO =
			new Procedure1<Tuple2<B,D>>()
	{
		@Override
		public void call(Tuple2<B, D> a) {
			alg1.assign().call(z1, a.a());
			alg2.assign().call(z2, a.b());
		}
	};
	
	@Override
	public Procedure1<Tuple2<B, D>> zero() {
		return ZERO;
	}
	
}
