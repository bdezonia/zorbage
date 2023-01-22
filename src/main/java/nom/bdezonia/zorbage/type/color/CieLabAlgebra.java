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
package nom.bdezonia.zorbage.type.color;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigDecimals;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigIntegers;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBytes;
import nom.bdezonia.zorbage.algebra.ConstructibleFromDoubles;
import nom.bdezonia.zorbage.algebra.ConstructibleFromFloats;
import nom.bdezonia.zorbage.algebra.ConstructibleFromInts;
import nom.bdezonia.zorbage.algebra.ConstructibleFromLongs;
import nom.bdezonia.zorbage.algebra.ConstructibleFromShorts;
import nom.bdezonia.zorbage.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CieLabAlgebra
	implements
		Algebra<CieLabAlgebra, CieLabMember>,
		Random<CieLabMember>,
		ConstructibleFromBytes<CieLabMember>,
		ConstructibleFromShorts<CieLabMember>,
		ConstructibleFromInts<CieLabMember>,
		ConstructibleFromLongs<CieLabMember>,
		ConstructibleFromFloats<CieLabMember>,
		ConstructibleFromDoubles<CieLabMember>,
		ConstructibleFromBigIntegers<CieLabMember>,
		ConstructibleFromBigDecimals<CieLabMember>
{
	@Override
	public String typeDescription() {
		return "192-bit CIE LAB color";
	}
	
	public CieLabAlgebra() { }
	
	@Override
	public CieLabMember construct() {
		return new CieLabMember();
	}

	@Override
	public CieLabMember construct(CieLabMember other) {
		return other.duplicate();
	}

	@Override
	public CieLabMember construct(String str) {
		return new CieLabMember(str);
	}

	private final Function2<Boolean, CieLabMember, CieLabMember> EQ =
			new Function2<Boolean, CieLabMember, CieLabMember>()
	{
		@Override
		public Boolean call(CieLabMember a, CieLabMember b) {
			return a.l() == b.l() && a.a() == b.a() && a.b() == b.b();
		}
	};

	@Override
	public Function2<Boolean, CieLabMember, CieLabMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, CieLabMember, CieLabMember> NEQ =
			new Function2<Boolean, CieLabMember, CieLabMember>()
	{
		@Override
		public Boolean call(CieLabMember a, CieLabMember b) {
			return !EQ.call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean, CieLabMember, CieLabMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<CieLabMember, CieLabMember> AS =
			new Procedure2<CieLabMember, CieLabMember>()
	{
		@Override
		public void call(CieLabMember a, CieLabMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<CieLabMember, CieLabMember> assign() {
		return AS;
	}

	private final Function1<Boolean, CieLabMember> ISZ =
			new Function1<Boolean, CieLabMember>()
	{
		@Override
		public Boolean call(CieLabMember a) {
			return a.l() == 0 && a.a() == 0 && a.b() == 0;
		}
	};

	@Override
	public Function1<Boolean, CieLabMember> isZero() {
		return ISZ;
	}

	private final Procedure1<CieLabMember> RAND =
			new Procedure1<CieLabMember>()
	{
		@Override
		public void call(CieLabMember a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setL(rng.nextDouble());
			a.setA(rng.nextDouble());
			a.setB(rng.nextDouble());
		}
	};

	@Override
	public Procedure1<CieLabMember> random() {
		return RAND;
	}

	private final Procedure1<CieLabMember> ZERO =
			new Procedure1<CieLabMember>()
	{
		@Override
		public void call(CieLabMember a) {
			a.setL(0);
			a.setA(0);
			a.setB(0);
		}
	};

	@Override
	public Procedure1<CieLabMember> zero() {
		return ZERO;
	}

	@Override
	public CieLabMember construct(BigDecimal... vals) {
		CieLabMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public CieLabMember construct(BigInteger... vals) {
		CieLabMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public CieLabMember construct(double... vals) {
		CieLabMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public CieLabMember construct(float... vals) {
		CieLabMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public CieLabMember construct(long... vals) {
		CieLabMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public CieLabMember construct(int... vals) {
		CieLabMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public CieLabMember construct(short... vals) {
		CieLabMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public CieLabMember construct(byte... vals) {
		CieLabMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
