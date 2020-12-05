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
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple2Algebra<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B>
	implements Algebra<Tuple2Algebra<AA,A,BB,B>, Tuple2<A,B>>
{
	private final AA algA;
	private final BB algB;
	
	public Tuple2Algebra(AA algA, BB algB) {
		this.algA = algA;
		this.algB = algB;
	}
	
	@Override
	public Tuple2<A,B> construct() {
		return new Tuple2<A,B>(algA.construct(), algB.construct());
	}

	@Override
	public Tuple2<A,B> construct(Tuple2<A,B> other) {
		Tuple2<A,B> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple2<A,B> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		return new Tuple2<A,B>(a,b);
	}

	private final Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>> EQ =
			new Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>>()
	{
		@Override
		public Boolean call(Tuple2<A,B> a, Tuple2<A,B> b) {
			return algA.isEqual().call(a.a(), b.a()) && algB.isEqual().call(a.b(), b.b());
		}
	};

	@Override
	public Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>> NEQ =
			new Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>>()
	{
		@Override
		public Boolean call(Tuple2<A,B> a, Tuple2<A,B> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple2<A,B>, Tuple2<A,B>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple2<A,B>, Tuple2<A,B>> ASSIGN =
			new Procedure2<Tuple2<A,B>, Tuple2<A,B>>()
	{
		@Override
		public void call(Tuple2<A,B> a, Tuple2<A,B> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
		}
	};

	@Override
	public Procedure2<Tuple2<A,B>, Tuple2<A,B>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple2<A,B>> ISZERO =
			new Function1<Boolean, Tuple2<A,B>>()
	{
		@Override
		public Boolean call(Tuple2<A,B> a) {
			return algA.isZero().call(a.a()) && algB.isZero().call(a.b());
		}
	};

	@Override
	public Function1<Boolean, Tuple2<A,B>> isZero() {
		return ISZERO;
	}

	private final Procedure1<Tuple2<A,B>> ZERO =
			new Procedure1<Tuple2<A,B>>()
	{
		@Override
		public void call(Tuple2<A,B> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
		}
	};
	
	@Override
	public Procedure1<Tuple2<A,B>> zero() {
		return ZERO;
	}
	
}
