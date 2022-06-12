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
package nom.bdezonia.zorbage.type.tuple;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple3;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple3Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C>
	implements Algebra<Tuple3Algebra<AA,A,BB,B,CC,C>, Tuple3<A,B,C>>
{
	private final AA algA;
	private final BB algB;
	private final CC algC;
	
	@Override
	public String typeDescription() {
		return "3 element tuple";
	}

	public Tuple3Algebra(AA algA, BB algB, CC algC) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
	}
	
	@Override
	public Tuple3<A,B,C> construct() {
		return new Tuple3<A,B,C>(
				algA.construct(),
				algB.construct(),
				algC.construct());
	}

	@Override
	public Tuple3<A,B,C> construct(Tuple3<A,B,C> other) {
		Tuple3<A,B,C> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple3<A,B,C> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		C c = (elements.length > 2 ? algC.construct(elements[2]) : algC.construct());
		return new Tuple3<A,B,C>(a,b,c);
	}

	private final Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>> EQ =
			new Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>>()
	{
		@Override
		public Boolean call(Tuple3<A,B,C> a, Tuple3<A,B,C> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c());
		}
	};

	@Override
	public Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>> NEQ =
			new Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>>()
	{
		@Override
		public Boolean call(Tuple3<A,B,C> a, Tuple3<A,B,C> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple3<A,B,C>, Tuple3<A,B,C>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple3<A,B,C>, Tuple3<A,B,C>> ASSIGN =
			new Procedure2<Tuple3<A,B,C>, Tuple3<A,B,C>>()
	{
		@Override
		public void call(Tuple3<A,B,C> a, Tuple3<A,B,C> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
		}
	};

	@Override
	public Procedure2<Tuple3<A,B,C>, Tuple3<A,B,C>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple3<A,B,C>> ISZERO =
			new Function1<Boolean, Tuple3<A,B,C>>()
	{
		@Override
		public Boolean call(Tuple3<A,B,C> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c());
		}
	};

	@Override
	public Function1<Boolean, Tuple3<A,B,C>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple3<A,B,C>> ZERO =
			new Procedure1<Tuple3<A,B,C>>()
	{
		@Override
		public void call(Tuple3<A,B,C> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
		}
	};
	
	@Override
	public Procedure1<Tuple3<A,B,C>> zero() {
		return ZERO;
	}
}
