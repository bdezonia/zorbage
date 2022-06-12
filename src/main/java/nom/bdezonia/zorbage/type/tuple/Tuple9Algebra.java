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
import nom.bdezonia.zorbage.tuple.Tuple9;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple9Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C,
							DD extends Algebra<DD,D>, D,
							EE extends Algebra<EE,E>, E,
							FF extends Algebra<FF,F>, F,
							GG extends Algebra<GG,G>, G,
							HH extends Algebra<HH,H>, H,
							II extends Algebra<II,I>, I>
	implements Algebra<Tuple9Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I>, Tuple9<A,B,C,D,E,F,G,H,I>>
{
	private final AA algA;
	private final BB algB;
	private final CC algC;
	private final DD algD;
	private final EE algE;
	private final FF algF;
	private final GG algG;
	private final HH algH;
	private final II algI;
	
	@Override
	public String typeDescription() {
		return "9 element tuple";
	}

	public Tuple9Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
		this.algD = algD;
		this.algE = algE;
		this.algF = algF;
		this.algG = algG;
		this.algH = algH;
		this.algI = algI;
	}
	
	@Override
	public Tuple9<A,B,C,D,E,F,G,H,I> construct() {
		return new Tuple9<A,B,C,D,E,F,G,H,I>(
				algA.construct(),
				algB.construct(),
				algC.construct(),
				algD.construct(),
				algE.construct(),
				algF.construct(),
				algG.construct(),
				algH.construct(),
				algI.construct());
	}

	@Override
	public Tuple9<A,B,C,D,E,F,G,H,I> construct(Tuple9<A,B,C,D,E,F,G,H,I> other) {
		Tuple9<A,B,C,D,E,F,G,H,I> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple9<A,B,C,D,E,F,G,H,I> construct(String str) {
		String[] elements = str.split(":");
		A a = (elements.length > 0 ? algA.construct(elements[0]) : algA.construct());
		B b = (elements.length > 1 ? algB.construct(elements[1]) : algB.construct());
		C c = (elements.length > 2 ? algC.construct(elements[2]) : algC.construct());
		D d = (elements.length > 3 ? algD.construct(elements[3]) : algD.construct());
		E e = (elements.length > 4 ? algE.construct(elements[4]) : algE.construct());
		F f = (elements.length > 5 ? algF.construct(elements[5]) : algF.construct());
		G g = (elements.length > 6 ? algG.construct(elements[6]) : algG.construct());
		H h = (elements.length > 7 ? algH.construct(elements[7]) : algH.construct());
		I i = (elements.length > 8 ? algI.construct(elements[8]) : algI.construct());
		return new Tuple9<A,B,C,D,E,F,G,H,I>(a,b,c,d,e,f,g,h,i);
	}

	private final Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> EQ =
			new Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>>()
	{
		@Override
		public Boolean call(Tuple9<A,B,C,D,E,F,G,H,I> a, Tuple9<A,B,C,D,E,F,G,H,I> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c()) &&
					algD.isEqual().call(a.d(), b.d()) &&
					algE.isEqual().call(a.e(), b.e()) &&
					algF.isEqual().call(a.f(), b.f()) &&
					algG.isEqual().call(a.g(), b.g()) &&
					algH.isEqual().call(a.h(), b.h()) &&
					algI.isEqual().call(a.i(), b.i());
		}
	};

	@Override
	public Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> NEQ =
			new Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>>()
	{
		@Override
		public Boolean call(Tuple9<A,B,C,D,E,F,G,H,I> a, Tuple9<A,B,C,D,E,F,G,H,I> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> ASSIGN =
			new Procedure2<Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>>()
	{
		@Override
		public void call(Tuple9<A,B,C,D,E,F,G,H,I> a, Tuple9<A,B,C,D,E,F,G,H,I> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
			algD.assign().call(a.d(), b.d());
			algE.assign().call(a.e(), b.e());
			algF.assign().call(a.f(), b.f());
			algG.assign().call(a.g(), b.g());
			algH.assign().call(a.h(), b.h());
			algI.assign().call(a.i(), b.i());
		}
	};

	@Override
	public Procedure2<Tuple9<A,B,C,D,E,F,G,H,I>, Tuple9<A,B,C,D,E,F,G,H,I>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>> ISZERO =
			new Function1<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>>()
	{
		@Override
		public Boolean call(Tuple9<A,B,C,D,E,F,G,H,I> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c()) &&
					algD.isZero().call(a.d()) &&
					algE.isZero().call(a.e()) &&
					algF.isZero().call(a.f()) &&
					algG.isZero().call(a.g()) &&
					algH.isZero().call(a.h()) &&
					algI.isZero().call(a.i());
		}
	};

	@Override
	public Function1<Boolean, Tuple9<A,B,C,D,E,F,G,H,I>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple9<A,B,C,D,E,F,G,H,I>> ZERO =
			new Procedure1<Tuple9<A,B,C,D,E,F,G,H,I>>()
	{
		@Override
		public void call(Tuple9<A,B,C,D,E,F,G,H,I> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
			algD.zero().call(a.d());
			algE.zero().call(a.e());
			algF.zero().call(a.f());
			algG.zero().call(a.g());
			algH.zero().call(a.h());
			algI.zero().call(a.i());
		}
	};
	
	@Override
	public Procedure1<Tuple9<A,B,C,D,E,F,G,H,I>> zero() {
		return ZERO;
	}
}
