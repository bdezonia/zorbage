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
import nom.bdezonia.zorbage.tuple.Tuple14;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple14Algebra<AA extends Algebra<AA,A>, A,
							BB extends Algebra<BB,B>, B,
							CC extends Algebra<CC,C>, C,
							DD extends Algebra<DD,D>, D,
							EE extends Algebra<EE,E>, E,
							FF extends Algebra<FF,F>, F,
							GG extends Algebra<GG,G>, G,
							HH extends Algebra<HH,H>, H,
							II extends Algebra<II,I>, I,
							JJ extends Algebra<JJ,J>, J,
							KK extends Algebra<KK,K>, K,
							LL extends Algebra<LL,L>, L,
							MM extends Algebra<MM,M>, M,
							NN extends Algebra<NN,N>, N>
	implements Algebra<Tuple14Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>
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
	private final JJ algJ;
	private final KK algK;
	private final LL algL;
	private final MM algM;
	private final NN algN;
	
	public Tuple14Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN) {
		this.algA = algA;
		this.algB = algB;
		this.algC = algC;
		this.algD = algD;
		this.algE = algE;
		this.algF = algF;
		this.algG = algG;
		this.algH = algH;
		this.algI = algI;
		this.algJ = algJ;
		this.algK = algK;
		this.algL = algL;
		this.algM = algM;
		this.algN = algN;
	}
	
	@Override
	public Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> construct() {
		return new Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>(
				algA.construct(),
				algB.construct(),
				algC.construct(),
				algD.construct(),
				algE.construct(),
				algF.construct(),
				algG.construct(),
				algH.construct(),
				algI.construct(),
				algJ.construct(),
				algK.construct(),
				algL.construct(),
				algM.construct(),
				algN.construct());
	}

	@Override
	public Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> construct(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> other) {
		Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> construct(String str) {
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
		J j = (elements.length > 9 ? algJ.construct(elements[9]) : algJ.construct());
		K k = (elements.length > 10 ? algK.construct(elements[10]) : algK.construct());
		L l = (elements.length > 11 ? algL.construct(elements[11]) : algL.construct());
		M m = (elements.length > 12 ? algM.construct(elements[12]) : algM.construct());
		N n = (elements.length > 13 ? algN.construct(elements[13]) : algN.construct());
		return new Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>(a,b,c,d,e,f,g,h,i,j,k,l,m,n);
	}

	private final Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> EQ =
			new Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>()
	{
		@Override
		public Boolean call(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> a, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> b) {
			return algA.isEqual().call(a.a(), b.a()) &&
					algB.isEqual().call(a.b(), b.b()) &&
					algC.isEqual().call(a.c(), b.c()) &&
					algD.isEqual().call(a.d(), b.d()) &&
					algE.isEqual().call(a.e(), b.e()) &&
					algF.isEqual().call(a.f(), b.f()) &&
					algG.isEqual().call(a.g(), b.g()) &&
					algH.isEqual().call(a.h(), b.h()) &&
					algI.isEqual().call(a.i(), b.i()) &&
					algJ.isEqual().call(a.j(), b.j()) &&
					algK.isEqual().call(a.k(), b.k()) &&
					algL.isEqual().call(a.l(), b.l()) &&
					algM.isEqual().call(a.m(), b.m()) &&
					algN.isEqual().call(a.n(), b.n());
		}
	};

	@Override
	public Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> NEQ =
			new Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>()
	{
		@Override
		public Boolean call(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> a, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> ASSIGN =
			new Procedure2<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>()
	{
		@Override
		public void call(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> a, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> b) {
			algA.assign().call(a.a(), b.a());
			algB.assign().call(a.b(), b.b());
			algC.assign().call(a.c(), b.c());
			algD.assign().call(a.d(), b.d());
			algE.assign().call(a.e(), b.e());
			algF.assign().call(a.f(), b.f());
			algG.assign().call(a.g(), b.g());
			algH.assign().call(a.h(), b.h());
			algI.assign().call(a.i(), b.i());
			algJ.assign().call(a.j(), b.j());
			algK.assign().call(a.k(), b.k());
			algL.assign().call(a.l(), b.l());
			algM.assign().call(a.m(), b.m());
			algN.assign().call(a.n(), b.n());
		}
	};

	@Override
	public Procedure2<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> ISZERO =
			new Function1<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>()
	{
		@Override
		public Boolean call(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> a) {
			return algA.isZero().call(a.a()) &&
					algB.isZero().call(a.b()) &&
					algC.isZero().call(a.c()) &&
					algD.isZero().call(a.d()) &&
					algE.isZero().call(a.e()) &&
					algF.isZero().call(a.f()) &&
					algG.isZero().call(a.g()) &&
					algH.isZero().call(a.h()) &&
					algI.isZero().call(a.i()) &&
					algJ.isZero().call(a.j()) &&
					algK.isZero().call(a.k()) &&
					algL.isZero().call(a.l()) &&
					algM.isZero().call(a.m()) &&
					algN.isZero().call(a.n());
		}
	};

	@Override
	public Function1<Boolean, Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> ZERO =
			new Procedure1<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>>()
	{
		@Override
		public void call(Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N> a) {
			algA.zero().call(a.a());
			algB.zero().call(a.b());
			algC.zero().call(a.c());
			algD.zero().call(a.d());
			algE.zero().call(a.e());
			algF.zero().call(a.f());
			algG.zero().call(a.g());
			algH.zero().call(a.h());
			algI.zero().call(a.i());
			algJ.zero().call(a.j());
			algK.zero().call(a.k());
			algL.zero().call(a.l());
			algM.zero().call(a.m());
			algN.zero().call(a.n());
		}
	};
	
	@Override
	public Procedure1<Tuple14<A,B,C,D,E,F,G,H,I,J,K,L,M,N>> zero() {
		return ZERO;
	}
	
}
