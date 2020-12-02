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
import nom.bdezonia.zorbage.tuple.Tuple26;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple26Algebra<AA extends Algebra<AA,A>, A,
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
							RR extends Algebra<RR,R>, R,
							SS extends Algebra<SS,S>, S,
							TT extends Algebra<TT,T>, T,
							UU extends Algebra<UU,U>, U,
							VV extends Algebra<VV,V>, V,
							WW extends Algebra<WW,W>, W,
							XX extends Algebra<XX,X>, X,
							YY extends Algebra<YY,Y>, Y,
							ZZ extends Algebra<ZZ,Z>, Z>
	implements Algebra<Tuple26Algebra<AA,A,BB,B,CC,C,DD,D,EE,E,FF,F,GG,G,HH,H,II,I,JJ,J,KK,K,LL,L,MM,M,NN,N,OO,O,PP,P,QQ,Q,RR,R,SS,S,TT,T,UU,U,VV,V,WW,W,XX,X,YY,Y,ZZ,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>
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
	private final SS algS;
	private final TT algT;
	private final UU algU;
	private final VV algV;
	private final WW algW;
	private final XX algX;
	private final YY algY;
	private final ZZ algZ;
	
	public Tuple26Algebra(AA algA, BB algB, CC algC, DD algD, EE algE, FF algF, GG algG, HH algH, II algI, JJ algJ, KK algK, LL algL, MM algM, NN algN, OO algO, PP algP, QQ algQ, RR algR, SS algS, TT algT, UU algU, VV algV, WW algW, XX algX, YY algY, ZZ algZ) {
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
		this.algS = algS;
		this.algT = algT;
		this.algU = algU;
		this.algV = algV;
		this.algW = algW;
		this.algX = algX;
		this.algY = algY;
		this.algZ = algZ;
	}
	
	@Override
	public Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> construct() {
		return new Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>(
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
				algR.construct(),
				algS.construct(),
				algT.construct(),
				algU.construct(),
				algV.construct(),
				algW.construct(),
				algX.construct(),
				algY.construct(),
				algZ.construct());
	}

	@Override
	public Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> construct(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> other) {
		Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> EQ =
			new Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>()
	{
		@Override
		public Boolean call(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> a, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> b) {
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
					algR.isEqual().call(a.r(), b.r()) &&
					algS.isEqual().call(a.s(), b.s()) &&
					algT.isEqual().call(a.t(), b.t()) &&
					algU.isEqual().call(a.u(), b.u()) &&
					algV.isEqual().call(a.v(), b.v()) &&
					algW.isEqual().call(a.w(), b.w()) &&
					algX.isEqual().call(a.x(), b.x()) &&
					algY.isEqual().call(a.y(), b.y()) &&
					algZ.isEqual().call(a.z(), b.z());
		}
	};

	@Override
	public Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> NEQ =
			new Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>()
	{
		@Override
		public Boolean call(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> a, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> ASSIGN =
			new Procedure2<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>()
	{
		@Override
		public void call(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> a, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> b) {
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
			algS.assign().call(a.s(), b.s());
			algT.assign().call(a.t(), b.t());
			algU.assign().call(a.u(), b.u());
			algV.assign().call(a.v(), b.v());
			algW.assign().call(a.w(), b.w());
			algX.assign().call(a.x(), b.x());
			algY.assign().call(a.y(), b.y());
			algZ.assign().call(a.z(), b.z());
		}
	};

	@Override
	public Procedure2<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> ISZERO =
			new Function1<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>()
	{
		@Override
		public Boolean call(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> a) {
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
					algR.isZero().call(a.r()) &&
					algS.isZero().call(a.s()) &&
					algT.isZero().call(a.t()) &&
					algU.isZero().call(a.u()) &&
					algV.isZero().call(a.v()) &&
					algW.isZero().call(a.w()) &&
					algX.isZero().call(a.x()) &&
					algY.isZero().call(a.y()) &&
					algZ.isZero().call(a.z());
		}
	};

	@Override
	public Function1<Boolean, Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> ZERO =
			new Procedure1<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>>()
	{
		@Override
		public void call(Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z> a) {
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
			algS.zero().call(a.s());
			algT.zero().call(a.t());
			algU.zero().call(a.u());
			algV.zero().call(a.v());
			algW.zero().call(a.w());
			algX.zero().call(a.x());
			algY.zero().call(a.y());
			algZ.zero().call(a.z());
		}
	};
	
	@Override
	public Procedure1<Tuple26<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z>> zero() {
		return ZERO;
	}
	
}
