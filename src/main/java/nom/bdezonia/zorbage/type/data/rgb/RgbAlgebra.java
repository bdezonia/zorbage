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
package nom.bdezonia.zorbage.type.data.rgb;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RgbAlgebra
	implements Algebra<RgbAlgebra, RgbMember>, Bounded<RgbMember>, Random<RgbMember>
{

	@Override
	public RgbMember construct() {
		return new RgbMember();
	}

	@Override
	public RgbMember construct(RgbMember other) {
		return other.duplicate();
	}

	@Override
	public RgbMember construct(String str) {
		throw new IllegalArgumentException("to be implemented");
	}

	private final Function2<Boolean, RgbMember, RgbMember> EQ =
			new Function2<Boolean, RgbMember, RgbMember>()
	{
		@Override
		public Boolean call(RgbMember a, RgbMember b) {
			return a.r() == b.r() && a.b() == b.b() && a.g() == b.g();
		}
	};

	@Override
	public Function2<Boolean, RgbMember, RgbMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, RgbMember, RgbMember> NEQ =
			new Function2<Boolean, RgbMember, RgbMember>()
	{
		@Override
		public Boolean call(RgbMember a, RgbMember b) {
			return !EQ.call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean, RgbMember, RgbMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<RgbMember, RgbMember> AS =
			new Procedure2<RgbMember, RgbMember>()
	{
		@Override
		public void call(RgbMember a, RgbMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<RgbMember, RgbMember> assign() {
		return AS;
	}

	private final Function1<Boolean, RgbMember> ISZ =
			new Function1<Boolean, RgbMember>()
	{
		@Override
		public Boolean call(RgbMember a) {
			return a.r() == 0 && a.g() == 0 && a.b() == 0;
		}
	};

	@Override
	public Function1<Boolean, RgbMember> isZero() {
		return ISZ;
	}

	private final Procedure1<RgbMember> RAND =
			new Procedure1<RgbMember>()
	{
		@Override
		public void call(RgbMember a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextInt(256));
			a.setG(rng.nextInt(256));
			a.setB(rng.nextInt(256));
		}
	};

	@Override
	public Procedure1<RgbMember> random() {
		return RAND;
	}

	private final Procedure1<RgbMember> MAXB =
			new Procedure1<RgbMember>()
	{
		@Override
		public void call(RgbMember a) {
			a.setR(255);
			a.setG(255);
			a.setB(255);
		}
	};

	@Override
	public Procedure1<RgbMember> maxBound() {
		return MAXB;
	}

	private final Procedure1<RgbMember> MINB =
			new Procedure1<RgbMember>()
	{
		@Override
		public void call(RgbMember a) {
			a.setR(0);
			a.setG(0);
			a.setB(0);
		}
	};

	@Override
	public Procedure1<RgbMember> minBound() {
		return MINB;
	}

	private final Procedure1<RgbMember> ZERO =
			new Procedure1<RgbMember>()
	{
		@Override
		public void call(RgbMember a) {
			a.setR(0);
			a.setG(0);
			a.setB(0);
		}
	};

	@Override
	public Procedure1<RgbMember> zero() {
		return ZERO;
	}

	private int blendColor(double t, int c1, int c2) {
		return (int) Math.sqrt(((1 - t) * c1*c1) + (t * c2*c2));
	}
	
	private final Procedure4<Double, RgbMember, RgbMember, RgbMember> BLEND =
			new Procedure4<Double, RgbMember, RgbMember, RgbMember>()
	{
		@Override
		public void call(Double t, RgbMember a, RgbMember b, RgbMember c) {
			if (t < 0 || t > 1)
				throw new IllegalArgumentException("blending must be between 0 and 1");
			c.setR(blendColor(t, a.r(), b.r()));
			c.setG(blendColor(t, a.g(), b.g()));
			c.setB(blendColor(t, a.b(), b.b()));
		}
	};

	public Procedure4<Double, RgbMember, RgbMember, RgbMember> blend() {
		return BLEND;
	}
}
