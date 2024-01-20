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
import nom.bdezonia.zorbage.tuple.Tuple18;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple18Algebra<AA extends Algebra<AA,A>, A,
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
							NN extends Algebra<NN,N>, N,
							OO extends Algebra<OO,O>, O,
							PP extends Algebra<PP,P>, P,
							QQ extends Algebra<QQ,Q>, Q,
							RR extends Algebra<RR,R>, R>
	implements
	
		Algebra<Tuple18Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N,OO,O,PP,P,QQ,Q,RR,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>,
		CompoundType
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
	private final OO algO;
	private final PP algP;
	private final QQ algQ;
	private final RR algR;
	
	@Override
	public String typeDescription() {
		return "18 element tuple";
	}

	public Tuple18Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, QQ algQ, RR algR) {
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
		this.algO = algO;
		this.algP = algP;
		this.algQ = algQ;
		this.algR = algR;
	}
	
	@Override
	public Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> construct() {
		return new Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>(
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
				algN.construct(),
				algO.construct(),
				algP.construct(),
				algQ.construct(),
				algR.construct());
	}

	@Override
	public Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> construct(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> other) {
		Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> construct(String str) {
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
		O o = (elements.length > 14 ? algO.construct(elements[14]) : algO.construct());
		P p = (elements.length > 15 ? algP.construct(elements[15]) : algP.construct());
		Q q = (elements.length > 16 ? algQ.construct(elements[16]) : algQ.construct());
		R r = (elements.length > 17 ? algR.construct(elements[17]) : algR.construct());
		return new Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r);
	}

	private final Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> EQ =
			new Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>()
	{
		@Override
		public Boolean call(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> a, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> b) {
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
					algN.isEqual().call(a.n(), b.n()) &&
					algO.isEqual().call(a.o(), b.o()) &&
					algP.isEqual().call(a.p(), b.p()) &&
					algQ.isEqual().call(a.q(), b.q()) &&
					algR.isEqual().call(a.r(), b.r());
		}
	};

	@Override
	public Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> NEQ =
			new Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>()
	{
		@Override
		public Boolean call(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> a, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> ASSIGN =
			new Procedure2<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>()
	{
		@Override
		public void call(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> a, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> b) {
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
			algO.assign().call(a.o(), b.o());
			algP.assign().call(a.p(), b.p());
			algQ.assign().call(a.q(), b.q());
			algR.assign().call(a.r(), b.r());
		}
	};

	@Override
	public Procedure2<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> ISZERO =
			new Function1<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>()
	{
		@Override
		public Boolean call(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> a) {
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
					algN.isZero().call(a.n()) &&
					algO.isZero().call(a.o()) &&
					algP.isZero().call(a.p()) &&
					algQ.isZero().call(a.q()) &&
					algR.isZero().call(a.r());
		}
	};

	@Override
	public Function1<Boolean, Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> ZERO =
			new Procedure1<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>>()
	{
		@Override
		public void call(Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R> a) {
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
			algO.zero().call(a.o());
			algP.zero().call(a.p());
			algQ.zero().call(a.q());
			algR.zero().call(a.r());
		}
	};
	
	@Override
	public Procedure1<Tuple18<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R>> zero() {
		return ZERO;
	}
	
}
