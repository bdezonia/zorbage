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
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.ColorMethods;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigDecimals;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigIntegers;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBytes;
import nom.bdezonia.zorbage.algebra.ConstructibleFromDoubles;
import nom.bdezonia.zorbage.algebra.ConstructibleFromFloats;
import nom.bdezonia.zorbage.algebra.ConstructibleFromInts;
import nom.bdezonia.zorbage.algebra.ConstructibleFromLongs;
import nom.bdezonia.zorbage.algebra.ConstructibleFromShorts;
import nom.bdezonia.zorbage.algebra.PredSucc;
import nom.bdezonia.zorbage.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RgbAlgebra
	implements
		Algebra<RgbAlgebra, RgbMember>, Bounded<RgbMember>, Random<RgbMember>,
		PredSucc<RgbMember>, ColorMethods<Double, RgbMember>,
		ConstructibleFromBytes<RgbMember>,
		ConstructibleFromShorts<RgbMember>,
		ConstructibleFromInts<RgbMember>,
		ConstructibleFromLongs<RgbMember>,
		ConstructibleFromFloats<RgbMember>,
		ConstructibleFromDoubles<RgbMember>,
		ConstructibleFromBigIntegers<RgbMember>,
		ConstructibleFromBigDecimals<RgbMember>
{
	@Override
	public String typeDescription() {
		return "24-bit RGB color";
	}

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
		return new RgbMember(str);
	}

	private final Function2<Boolean, RgbMember, RgbMember> EQ =
			new Function2<Boolean, RgbMember, RgbMember>()
	{
		@Override
		public Boolean call(RgbMember a, RgbMember b) {
			return a.r() == b.r() && a.g() == b.g() && a.b() == b.b();
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

	private final Procedure4<Double, RgbMember, RgbMember, RgbMember> BLEND =
			new Procedure4<Double, RgbMember, RgbMember, RgbMember>()
	{
		@Override
		public void call(Double t, RgbMember a, RgbMember b, RgbMember c) {
			c.setR(RgbUtils.blendColor(t, a.r(), b.r()));
			c.setG(RgbUtils.blendColor(t, a.g(), b.g()));
			c.setB(RgbUtils.blendColor(t, a.b(), b.b()));
		}
	};

	@Override
	public Procedure4<Double, RgbMember, RgbMember, RgbMember> blend() {
		return BLEND;
	}

	private final Procedure2<RgbMember, RgbMember> PRED =
			new Procedure2<RgbMember, RgbMember>()
	{
		@Override
		public void call(RgbMember a, RgbMember b) {
			if (a.b() == 0) {
				b.setB(255);
				if (a.g() == 0) {
					b.setG(255);
					if (a.r() == 0) {
						b.setR(255);
					}
					else {
						b.setR(a.r() - 1);
					}
				}
				else {
					b.setG(a.g() - 1);
					b.setR(a.r());
				}
			}
			else {
				b.setB(a.b() - 1);
				b.setG(a.g());
				b.setR(a.r());
			}
		}
	};

	@Override
	public Procedure2<RgbMember, RgbMember> pred() {
		return PRED;
	}

	private final Procedure2<RgbMember, RgbMember> SUCC =
			new Procedure2<RgbMember, RgbMember>()
	{
		@Override
		public void call(RgbMember a, RgbMember b) {
			if (a.b() == 255) {
				b.setB(0);
				if (a.g() == 255) {
					b.setG(0);
					if (a.r() == 255) {
						b.setR(0);
					}
					else {
						b.setR(a.r() + 1);
					}
				}
				else {
					b.setG(a.g() + 1);
					b.setR(a.r());
				}
			}
			else {
				b.setB(a.b() + 1);
				b.setG(a.g());
				b.setR(a.r());
			}
		}
	};

	@Override
	public Procedure2<RgbMember, RgbMember> succ() {
		return SUCC;
	}

	@Override
	public RgbMember construct(BigDecimal... vals) {
		RgbMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public RgbMember construct(BigInteger... vals) {
		RgbMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public RgbMember construct(double... vals) {
		RgbMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public RgbMember construct(float... vals) {
		RgbMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public RgbMember construct(long... vals) {
		RgbMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public RgbMember construct(int... vals) {
		RgbMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public RgbMember construct(short... vals) {
		RgbMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public RgbMember construct(byte... vals) {
		RgbMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
