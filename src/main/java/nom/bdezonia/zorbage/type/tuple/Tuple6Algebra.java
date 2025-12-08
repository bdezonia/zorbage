/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.tuple.Tuple6;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple6Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C,
							DD extends Algebra<DD,D>, D,
							EE extends Algebra<EE,E>, E,
							FF extends Algebra<FF,F>, F>
	
	implements
	
		Algebra<Tuple6Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F>, Tuple6<A,B,C,D,E,F>>
{
	private final AA algA;
	private final BB algB;
	private final CC algC;
	private final DD algD;
	private final EE algE;
	private final FF algF;
	
	@Override
	public String typeDescription() {
		return "6 element tuple";
	}

	public Tuple6Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
		this.algD = algD;
		this.algE = algE;
		this.algF = algF;
	}
	
	@Override
	public Tuple6<A,B,C,D,E,F> construct() {
		return new Tuple6<A,B,C,D,E,F>(
				algA.construct(),
				algB.construct(),
				algC.construct(),
				algD.construct(),
				algE.construct(),
				algF.construct());
	}

	@Override
	public Tuple6<A,B,C,D,E,F> construct(Tuple6<A,B,C,D,E,F> other) {
		Tuple6<A,B,C,D,E,F> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple6<A,B,C,D,E,F> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		C c = (elements.length > 2 ? algC.construct(elements[2]) : algC.construct());
		D d = (elements.length > 3 ? algD.construct(elements[3]) : algD.construct());
		E e = (elements.length > 4 ? algE.construct(elements[4]) : algE.construct());
		F f = (elements.length > 5 ? algF.construct(elements[5]) : algF.construct());
		return new Tuple6<A,B,C,D,E,F>(a,b,c,d,e,f);
	}

	private final Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> EQ =
			new Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>>()
	{
		@Override
		public Boolean call(Tuple6<A,B,C,D,E,F> a, Tuple6<A,B,C,D,E,F> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c()) &&
					algD.isEqual().call(a.d(), b.d()) &&
					algE.isEqual().call(a.e(), b.e()) &&
					algF.isEqual().call(a.f(), b.f());
		}
	};

	@Override
	public Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> NEQ =
			new Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>>()
	{
		@Override
		public Boolean call(Tuple6<A,B,C,D,E,F> a, Tuple6<A,B,C,D,E,F> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> ASSIGN =
			new Procedure2<Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>>()
	{
		@Override
		public void call(Tuple6<A,B,C,D,E,F> a, Tuple6<A,B,C,D,E,F> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
			algD.assign().call(a.d(), b.d());
			algE.assign().call(a.e(), b.e());
			algF.assign().call(a.f(), b.f());
		}
	};

	@Override
	public Procedure2<Tuple6<A,B,C,D,E,F>, Tuple6<A,B,C,D,E,F>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple6<A,B,C,D,E,F>> ISZERO =
			new Function1<Boolean, Tuple6<A,B,C,D,E,F>>()
	{
		@Override
		public Boolean call(Tuple6<A,B,C,D,E,F> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c()) &&
					algD.isZero().call(a.d()) &&
					algE.isZero().call(a.e()) &&
					algF.isZero().call(a.f());
		}
	};

	@Override
	public Function1<Boolean, Tuple6<A,B,C,D,E,F>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple6<A,B,C,D,E,F>> ZERO =
			new Procedure1<Tuple6<A,B,C,D,E,F>>()
	{
		@Override
		public void call(Tuple6<A,B,C,D,E,F> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
			algD.zero().call(a.d());
			algE.zero().call(a.e());
			algF.zero().call(a.f());
		}
	};
	
	@Override
	public Procedure1<Tuple6<A,B,C,D,E,F>> zero() {
		return ZERO;
	}
}
