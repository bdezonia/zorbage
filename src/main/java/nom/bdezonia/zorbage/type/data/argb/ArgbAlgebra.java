/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.argb;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArgbAlgebra
	implements Algebra<ArgbAlgebra, ArgbMember>, Bounded<ArgbMember>, Random<ArgbMember>
{

	@Override
	public ArgbMember construct() {
		return new ArgbMember();
	}

	@Override
	public ArgbMember construct(ArgbMember other) {
		return other.duplicate();
	}

	@Override
	public ArgbMember construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, ArgbMember, ArgbMember> EQ =
			new Function2<Boolean, ArgbMember, ArgbMember>()
	{
		@Override
		public Boolean call(ArgbMember a, ArgbMember b) {
			return a.a() == b.a() && a.r() == b.r() && a.b() == b.b() && a.g() == b.g();
		}
	};

	@Override
	public Function2<Boolean, ArgbMember, ArgbMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, ArgbMember, ArgbMember> NEQ =
			new Function2<Boolean, ArgbMember, ArgbMember>()
	{
		@Override
		public Boolean call(ArgbMember a, ArgbMember b) {
			return !EQ.call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean, ArgbMember, ArgbMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ArgbMember, ArgbMember> AS =
			new Procedure2<ArgbMember, ArgbMember>()
	{
		@Override
		public void call(ArgbMember a, ArgbMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<ArgbMember, ArgbMember> assign() {
		return AS;
	}

	private final Function1<Boolean, ArgbMember> ISZ =
			new Function1<Boolean, ArgbMember>()
	{
		@Override
		public Boolean call(ArgbMember a) {
			return a.a() == 0 && a.r() == 0 && a.g() == 0 && a.b() == 0;
		}
	};

	@Override
	public Function1<Boolean, ArgbMember> isZero() {
		return ISZ;
	}

	private final Procedure1<ArgbMember> RAND =
			new Procedure1<ArgbMember>()
	{
		@Override
		public void call(ArgbMember a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setA(0);
			a.setR(rng.nextInt(256));
			a.setG(rng.nextInt(256));
			a.setB(rng.nextInt(256));
		}
	};

	@Override
	public Procedure1<ArgbMember> random() {
		return RAND;
	}

	private final Procedure1<ArgbMember> MAXB =
			new Procedure1<ArgbMember>()
	{
		@Override
		public void call(ArgbMember a) {
			a.setA(0);
			a.setR(255);
			a.setG(255);
			a.setB(255);
		}
	};

	@Override
	public Procedure1<ArgbMember> maxBound() {
		return MAXB;
	}

	private final Procedure1<ArgbMember> MINB =
			new Procedure1<ArgbMember>()
	{
		@Override
		public void call(ArgbMember a) {
			a.setA(0);
			a.setR(0);
			a.setG(0);
			a.setB(0);
		}
	};

	@Override
	public Procedure1<ArgbMember> minBound() {
		return MINB;
	}

}
