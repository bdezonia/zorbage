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
import nom.bdezonia.zorbage.tuple.Tuple7;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple7Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C,
							DD extends Algebra<DD,D>, D,
							EE extends Algebra<EE,E>, E,
							FF extends Algebra<FF,F>, F,
							GG extends Algebra<GG,G>, G>
	implements Algebra<Tuple7Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G>, Tuple7<A,B,C,D,E,F,G>>
{
	private final AA algA;
	private final BB algB;
	private final CC algC;
	private final DD algD;
	private final EE algE;
	private final FF algF;
	private final GG algG;
	
	public Tuple7Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
		this.algD = algD;
		this.algE = algE;
		this.algF = algF;
		this.algG = algG;
	}
	
	@Override
	public Tuple7<A,B,C,D,E,F,G> construct() {
		return new Tuple7<A,B,C,D,E,F,G>(
				algA.construct(),
				algB.construct(),
				algC.construct(),
				algD.construct(),
				algE.construct(),
				algF.construct(),
				algG.construct());
	}

	@Override
	public Tuple7<A,B,C,D,E,F,G> construct(Tuple7<A,B,C,D,E,F,G> other) {
		Tuple7<A,B,C,D,E,F,G> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple7<A,B,C,D,E,F,G> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		C c = (elements.length > 2 ? algC.construct(elements[2]) : algC.construct());
		D d = (elements.length > 3 ? algD.construct(elements[3]) : algD.construct());
		E e = (elements.length > 4 ? algE.construct(elements[4]) : algE.construct());
		F f = (elements.length > 5 ? algF.construct(elements[5]) : algF.construct());
		G g = (elements.length > 6 ? algG.construct(elements[6]) : algG.construct());
		return new Tuple7<A,B,C,D,E,F,G>(a,b,c,d,e,f,g);
	}

	private final Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> EQ =
			new Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>>()
	{
		@Override
		public Boolean call(Tuple7<A,B,C,D,E,F,G> a, Tuple7<A,B,C,D,E,F,G> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c()) &&
					algD.isEqual().call(a.d(), b.d()) &&
					algE.isEqual().call(a.e(), b.e()) &&
					algF.isEqual().call(a.f(), b.f()) &&
					algG.isEqual().call(a.g(), b.g());
		}
	};

	@Override
	public Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> NEQ =
			new Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>>()
	{
		@Override
		public Boolean call(Tuple7<A,B,C,D,E,F,G> a, Tuple7<A,B,C,D,E,F,G> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> ASSIGN =
			new Procedure2<Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>>()
	{
		@Override
		public void call(Tuple7<A,B,C,D,E,F,G> a, Tuple7<A,B,C,D,E,F,G> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
			algD.assign().call(a.d(), b.d());
			algE.assign().call(a.e(), b.e());
			algF.assign().call(a.f(), b.f());
			algG.assign().call(a.g(), b.g());
		}
	};

	@Override
	public Procedure2<Tuple7<A,B,C,D,E,F,G>, Tuple7<A,B,C,D,E,F,G>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple7<A,B,C,D,E,F,G>> ISZERO =
			new Function1<Boolean, Tuple7<A,B,C,D,E,F,G>>()
	{
		@Override
		public Boolean call(Tuple7<A,B,C,D,E,F,G> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c()) &&
					algD.isZero().call(a.d()) &&
					algE.isZero().call(a.e()) &&
					algF.isZero().call(a.f()) &&
					algG.isZero().call(a.g());
		}
	};

	@Override
	public Function1<Boolean, Tuple7<A,B,C,D,E,F,G>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple7<A,B,C,D,E,F,G>> ZERO =
			new Procedure1<Tuple7<A,B,C,D,E,F,G>>()
	{
		@Override
		public void call(Tuple7<A,B,C,D,E,F,G> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
			algD.zero().call(a.d());
			algE.zero().call(a.e());
			algF.zero().call(a.f());
			algG.zero().call(a.g());
		}
	};
	
	@Override
	public Procedure1<Tuple7<A,B,C,D,E,F,G>> zero() {
		return ZERO;
	}
}
