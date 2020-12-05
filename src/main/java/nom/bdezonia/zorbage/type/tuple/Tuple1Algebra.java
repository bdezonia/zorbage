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
package nom.bdezonia.zorbage.type.tuple;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple1;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple1Algebra<AA extends Algebra<AA,A>, A>
	implements Algebra<Tuple1Algebra<AA,A>, Tuple1<A>>
{
	private final AA algA;
	
	public Tuple1Algebra(AA algA) {
		this.algA = algA;
	}
	
	@Override
	public Tuple1<A> construct() {
		return new Tuple1<A>(algA.construct());
	}

	@Override
	public Tuple1<A> construct(Tuple1<A> other) {
		Tuple1<A> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple1<A> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		return new Tuple1<A>(a);
	}

	private final Function2<Boolean, Tuple1<A>, Tuple1<A>> EQ =
			new Function2<Boolean, Tuple1<A>, Tuple1<A>>()
	{
		@Override
		public Boolean call(Tuple1<A> a, Tuple1<A> b) {
			return algA.isEqual().call(a.a(), b.a());
		}
	};

	@Override
	public Function2<Boolean, Tuple1<A>, Tuple1<A>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple1<A>, Tuple1<A>> NEQ =
			new Function2<Boolean, Tuple1<A>, Tuple1<A>>()
	{
		@Override
		public Boolean call(Tuple1<A> a, Tuple1<A> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple1<A>, Tuple1<A>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple1<A>, Tuple1<A>> ASSIGN =
			new Procedure2<Tuple1<A>, Tuple1<A>>()
	{
		@Override
		public void call(Tuple1<A> a, Tuple1<A> b) {
			algA.assign().call(a.a(), b.a());
		}
	};

	@Override
	public Procedure2<Tuple1<A>, Tuple1<A>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple1<A>> ISZERO =
			new Function1<Boolean, Tuple1<A>>()
	{
		@Override
		public Boolean call(Tuple1<A> a) {
			return algA.isZero().call(a.a());
		}
	};

	@Override
	public Function1<Boolean, Tuple1<A>> isZero() {
		return ISZERO;
	}
	

	private final Procedure1<Tuple1<A>> ZERO =
			new Procedure1<Tuple1<A>>()
	{
		@Override
		public void call(Tuple1<A> a) {
			algA.zero().call(a.a());
		}
	};
	
	@Override
	public Procedure1<Tuple1<A>> zero() {
		return ZERO;
	}
	
}
