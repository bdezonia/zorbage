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
package nom.bdezonia.zorbage.type.data.tuple;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple18;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Tuple18Algebra<A extends Algebra<A,B>,B,
							C extends Algebra<C,D>,D,
							E extends Algebra<E,F>,F,
							G extends Algebra<G,H>,H,
							I extends Algebra<I,J>,J,
							K extends Algebra<K,L>,L,
							M extends Algebra<M,N>,N,
							O extends Algebra<O,P>,P,
							Q extends Algebra<Q,R>,R,
							S extends Algebra<S,T>,T,
							U extends Algebra<U,V>,V,
							W extends Algebra<W,X>,X,
							Y extends Algebra<Y,Z>,Z,
							AA extends Algebra<AA,BB>,BB,
							CC extends Algebra<CC,DD>,DD,
							EE extends Algebra<EE,FF>,FF,
							GG extends Algebra<GG,HH>,HH,
							II extends Algebra<II,JJ>,JJ>
	implements Algebra<Tuple18Algebra<A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,BB,CC,DD,EE,FF,GG,HH,II,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>
{
	private final A alg1;
	private final C alg2;
	private final E alg3;
	private final G alg4;
	private final I alg5;
	private final K alg6;
	private final M alg7;
	private final O alg8;
	private final Q alg9;
	private final S alg10;
	private final U alg11;
	private final W alg12;
	private final Y alg13;
	private final AA alg14;
	private final CC alg15;
	private final EE alg16;
	private final GG alg17;
	private final II alg18;
	
	public Tuple18Algebra(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7, O alg8, Q alg9, S alg10, U alg11, W alg12, Y alg13, AA alg14, CC alg15, EE alg16, GG alg17, II alg18) {
		this.alg1 = alg1;
		this.alg2 = alg2;
		this.alg3 = alg3;
		this.alg4 = alg4;
		this.alg5 = alg5;
		this.alg6 = alg6;
		this.alg7 = alg7;
		this.alg8 = alg8;
		this.alg9 = alg9;
		this.alg10 = alg10;
		this.alg11 = alg11;
		this.alg12 = alg12;
		this.alg13 = alg13;
		this.alg14 = alg14;
		this.alg15 = alg15;
		this.alg16 = alg16;
		this.alg17 = alg17;
		this.alg18 = alg18;
	}
	
	@Override
	public Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> construct() {
		return new Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>(
				alg1.construct(),
				alg2.construct(),
				alg3.construct(),
				alg4.construct(),
				alg5.construct(),
				alg6.construct(),
				alg7.construct(),
				alg8.construct(),
				alg9.construct(),
				alg10.construct(),
				alg11.construct(),
				alg12.construct(),
				alg13.construct(),
				alg14.construct(),
				alg15.construct(),
				alg16.construct(),
				alg17.construct(),
				alg18.construct());
	}

	@Override
	public Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> construct(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> other) {
		Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> result = construct();
		assign().call(other, result);
		return result;
	}

	@Override
	public Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> EQ =
			new Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>()
	{
		@Override
		public Boolean call(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> a, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> b) {
			return alg1.isEqual().call(a.a(), b.a()) &&
					alg2.isEqual().call(a.b(), b.b()) &&
					alg3.isEqual().call(a.c(), b.c()) &&
					alg4.isEqual().call(a.d(), b.d()) &&
					alg5.isEqual().call(a.e(), b.e()) &&
					alg6.isEqual().call(a.f(), b.f()) &&
					alg7.isEqual().call(a.g(), b.g()) &&
					alg8.isEqual().call(a.h(), b.h()) &&
					alg9.isEqual().call(a.i(), b.i()) &&
					alg10.isEqual().call(a.j(), b.j()) &&
					alg11.isEqual().call(a.k(), b.k()) &&
					alg12.isEqual().call(a.l(), b.l()) &&
					alg13.isEqual().call(a.m(), b.m()) &&
					alg14.isEqual().call(a.n(), b.n()) &&
					alg15.isEqual().call(a.o(), b.o()) &&
					alg16.isEqual().call(a.p(), b.p()) &&
					alg17.isEqual().call(a.q(), b.q()) &&
					alg18.isEqual().call(a.r(), b.r());
		}
	};

	@Override
	public Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> NEQ =
			new Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>()
	{
		@Override
		public Boolean call(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> a, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> ASSIGN =
			new Procedure2<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>()
	{
		@Override
		public void call(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> a, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> b) {
			alg1.assign().call(a.a(), b.a());
			alg2.assign().call(a.b(), b.b());
			alg3.assign().call(a.c(), b.c());
			alg4.assign().call(a.d(), b.d());
			alg5.assign().call(a.e(), b.e());
			alg6.assign().call(a.f(), b.f());
			alg7.assign().call(a.g(), b.g());
			alg8.assign().call(a.h(), b.h());
			alg9.assign().call(a.i(), b.i());
			alg10.assign().call(a.j(), b.j());
			alg11.assign().call(a.k(), b.k());
			alg12.assign().call(a.l(), b.l());
			alg13.assign().call(a.m(), b.m());
			alg14.assign().call(a.n(), b.n());
			alg15.assign().call(a.o(), b.o());
			alg16.assign().call(a.p(), b.p());
			alg17.assign().call(a.q(), b.q());
			alg18.assign().call(a.r(), b.r());
		}
	};

	@Override
	public Procedure2<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> ISZERO =
			new Function1<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>()
	{
		@Override
		public Boolean call(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> a) {
			return alg1.isZero().call(a.a()) &&
					alg2.isZero().call(a.b()) &&
					alg3.isZero().call(a.c()) &&
					alg4.isZero().call(a.d()) &&
					alg5.isZero().call(a.e()) &&
					alg6.isZero().call(a.f()) &&
					alg7.isZero().call(a.g()) &&
					alg8.isZero().call(a.h()) &&
					alg9.isZero().call(a.i()) &&
					alg10.isZero().call(a.j()) &&
					alg11.isZero().call(a.k()) &&
					alg12.isZero().call(a.l()) &&
					alg13.isZero().call(a.m()) &&
					alg14.isZero().call(a.n()) &&
					alg15.isZero().call(a.o()) &&
					alg16.isZero().call(a.p()) &&
					alg17.isZero().call(a.q()) &&
					alg18.isZero().call(a.r());
		}
	};

	@Override
	public Function1<Boolean, Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> isZero() {
		return ISZERO;
	}
	
	private final Procedure1<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> ZERO =
			new Procedure1<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>>()
	{
		@Override
		public void call(Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ> a) {
			alg1.zero().call(a.a());
			alg2.zero().call(a.b());
			alg2.zero().call(a.b());
			alg3.zero().call(a.c());
			alg4.zero().call(a.d());
			alg5.zero().call(a.e());
			alg6.zero().call(a.f());
			alg7.zero().call(a.g());
			alg8.zero().call(a.h());
			alg9.zero().call(a.i());
			alg10.zero().call(a.j());
			alg11.zero().call(a.k());
			alg12.zero().call(a.l());
			alg13.zero().call(a.m());
			alg14.zero().call(a.n());
			alg15.zero().call(a.o());
			alg16.zero().call(a.p());
			alg17.zero().call(a.q());
			alg18.zero().call(a.r());
		}
	};
	
	@Override
	public Procedure1<Tuple18<B,D,F,H,J,L,N,P,R,T,V,X,Z,BB,DD,FF,HH,JJ>> zero() {
		return ZERO;
	}
	
}