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
import nom.bdezonia.zorbage.tuple.Tuple4;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple4Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C,
							DD extends Algebra<DD,D>, D>
	implements Algebra<Tuple4Algebra<AA,A,BB,B,CC,C,DD,D>, Tuple4<A,B,C,D>>
{
	private final AA algA;
	private final BB algB;
	private final CC algC;
	private final DD algD;
	
	@Override
	public String typeDescription() {
		return "4 element tuple";
	}

	public Tuple4Algebra(AA algA, BB algB, CC algC, DD algD) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
		this.algD = algD;
	}
	
	@Override
	public Tuple4<A,B,C,D> construct() {
		return new Tuple4<A,B,C,D>(
				algA.construct(),
				algB.construct(),
				algC.construct(),
				algD.construct());
	}

	@Override
	public Tuple4<A,B,C,D> construct(Tuple4<A,B,C,D> other) {
		Tuple4<A,B,C,D> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple4<A,B,C,D> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		C c = (elements.length > 2 ? algC.construct(elements[2]) : algC.construct());
		D d = (elements.length > 3 ? algD.construct(elements[3]) : algD.construct());
		return new Tuple4<A,B,C,D>(a,b,c,d);
	}

	private final Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> EQ =
			new Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>>()
	{
		@Override
		public Boolean call(Tuple4<A,B,C,D> a, Tuple4<A,B,C,D> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c()) &&
					algD.isEqual().call(a.d(), b.d());
		}
	};

	@Override
	public Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> NEQ =
			new Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>>()
	{
		@Override
		public Boolean call(Tuple4<A,B,C,D> a, Tuple4<A,B,C,D> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> ASSIGN =
			new Procedure2<Tuple4<A,B,C,D>, Tuple4<A,B,C,D>>()
	{
		@Override
		public void call(Tuple4<A,B,C,D> a, Tuple4<A,B,C,D> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
			algD.assign().call(a.d(), b.d());
		}
	};

	@Override
	public Procedure2<Tuple4<A,B,C,D>, Tuple4<A,B,C,D>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple4<A,B,C,D>> ISZERO =
			new Function1<Boolean, Tuple4<A,B,C,D>>()
	{
		@Override
		public Boolean call(Tuple4<A,B,C,D> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c()) &&
					algD.isZero().call(a.d());
		}
	};

	@Override
	public Function1<Boolean, Tuple4<A,B,C,D>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple4<A,B,C,D>> ZERO =
			new Procedure1<Tuple4<A,B,C,D>>()
	{
		@Override
		public void call(Tuple4<A,B,C,D> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
			algD.zero().call(a.d());
		}
	};
	
	@Override
	public Procedure1<Tuple4<A,B,C,D>> zero() {
		return ZERO;
	}
}
