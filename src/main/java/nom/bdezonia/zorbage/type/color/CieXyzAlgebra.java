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
public class CieXyzAlgebra
	implements
		Algebra<CieXyzAlgebra, CieXyzMember>,
		Random<CieXyzMember>,
		ConstructibleFromBytes<CieXyzMember>,
		ConstructibleFromShorts<CieXyzMember>,
		ConstructibleFromInts<CieXyzMember>,
		ConstructibleFromLongs<CieXyzMember>,
		ConstructibleFromFloats<CieXyzMember>,
		ConstructibleFromDoubles<CieXyzMember>,
		ConstructibleFromBigIntegers<CieXyzMember>,
		ConstructibleFromBigDecimals<CieXyzMember>
{
	@Override
	public String typeDescription() {
		return "192-bit CIE LAB color";
	}
	
	public CieXyzAlgebra() { }
	
	@Override
	public CieXyzMember construct() {
		return new CieXyzMember();
	}

	@Override
	public CieXyzMember construct(CieXyzMember other) {
		return other.duplicate();
	}

	@Override
	public CieXyzMember construct(String str) {
		return new CieXyzMember(str);
	}

	private final Function2<Boolean, CieXyzMember, CieXyzMember> EQ =
			new Function2<Boolean, CieXyzMember, CieXyzMember>()
	{
		@Override
		public Boolean call(CieXyzMember a, CieXyzMember b) {
			return a.x() == b.x() && a.y() == b.y() && a.z() == b.z();
		}
	};

	@Override
	public Function2<Boolean, CieXyzMember, CieXyzMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, CieXyzMember, CieXyzMember> NEQ =
			new Function2<Boolean, CieXyzMember, CieXyzMember>()
	{
		@Override
		public Boolean call(CieXyzMember a, CieXyzMember b) {
			return !EQ.call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean, CieXyzMember, CieXyzMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<CieXyzMember, CieXyzMember> AS =
			new Procedure2<CieXyzMember, CieXyzMember>()
	{
		@Override
		public void call(CieXyzMember a, CieXyzMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<CieXyzMember, CieXyzMember> assign() {
		return AS;
	}

	private final Function1<Boolean, CieXyzMember> ISZ =
			new Function1<Boolean, CieXyzMember>()
	{
		@Override
		public Boolean call(CieXyzMember a) {
			return a.x() == 0 && a.y() == 0 && a.z() == 0;
		}
	};

	@Override
	public Function1<Boolean, CieXyzMember> isZero() {
		return ISZ;
	}

	private final Procedure1<CieXyzMember> RAND =
			new Procedure1<CieXyzMember>()
	{
		@Override
		public void call(CieXyzMember a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setX(rng.nextDouble());
			a.setY(rng.nextDouble());
			a.setZ(rng.nextDouble());
		}
	};

	@Override
	public Procedure1<CieXyzMember> random() {
		return RAND;
	}

	private final Procedure1<CieXyzMember> ZERO =
			new Procedure1<CieXyzMember>()
	{
		@Override
		public void call(CieXyzMember a) {
			a.setX(0);
			a.setY(0);
			a.setZ(0);
		}
	};

	@Override
	public Procedure1<CieXyzMember> zero() {
		return ZERO;
	}

	@Override
	public CieXyzMember construct(BigDecimal... vals) {
		CieXyzMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(BigInteger... vals) {
		CieXyzMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(double... vals) {
		CieXyzMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(float... vals) {
		CieXyzMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(long... vals) {
		CieXyzMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(int... vals) {
		CieXyzMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(short... vals) {
		CieXyzMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public CieXyzMember construct(byte... vals) {
		CieXyzMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
