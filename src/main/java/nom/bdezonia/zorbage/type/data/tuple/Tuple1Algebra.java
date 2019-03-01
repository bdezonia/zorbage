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
package nom.bdezonia.zorbage.type.data.tuple;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple1;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple1Algebra<A extends Algebra<A,B>,B>
	implements Algebra<Tuple1Algebra<A,B>, Tuple1<B>>
{
	private final A alg1;
	
	public Tuple1Algebra(A alg1) {
		this.alg1 = alg1;
	}
	
	@Override
	public Tuple1<B> construct() {
		return new Tuple1<B>(alg1.construct());
	}

	@Override
	public Tuple1<B> construct(Tuple1<B> other) {
		Tuple1<B> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple1<B> construct(String str) {
		// TODO: do something sensible
		return construct();
	}

	private final Function2<Boolean, Tuple1<B>, Tuple1<B>> EQ =
			new Function2<Boolean, Tuple1<B>, Tuple1<B>>()
	{
		@Override
		public Boolean call(Tuple1<B> a, Tuple1<B> b) {
			return alg1.isEqual().call(a.a(), b.a());
		}
	};

	@Override
	public Function2<Boolean, Tuple1<B>, Tuple1<B>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple1<B>, Tuple1<B>> NEQ =
			new Function2<Boolean, Tuple1<B>, Tuple1<B>>()
	{
		@Override
		public Boolean call(Tuple1<B> a, Tuple1<B> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple1<B>, Tuple1<B>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple1<B>, Tuple1<B>> ASSIGN =
			new Procedure2<Tuple1<B>, Tuple1<B>>()
	{
		@Override
		public void call(Tuple1<B> a, Tuple1<B> b) {
			alg1.assign().call(a.a(), b.a());
		}
	};

	@Override
	public Procedure2<Tuple1<B>, Tuple1<B>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple1<B>> ISZERO =
			new Function1<Boolean, Tuple1<B>>()
	{
		@Override
		public Boolean call(Tuple1<B> a) {
			return alg1.isZero().call(a.a());
		}
	};

	@Override
	public Function1<Boolean, Tuple1<B>> isZero() {
		return ISZERO;
	}
	
}
