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
package nom.bdezonia.zorbage.type.float16.octonion;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.OctonionNumberWithin;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;


/**
 * 
 * @author Barry DeZonia
 *
 */
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat16Algebra
	implements
		SkewField<OctonionFloat16Algebra, OctonionFloat16Member>,
		Conjugate<OctonionFloat16Member>,
		Norm<OctonionFloat16Member,Float16Member>,
		Infinite<OctonionFloat16Member>,
		NaN<OctonionFloat16Member>,
		Rounding<Float16Member,OctonionFloat16Member>,
		RealConstants<OctonionFloat16Member>,
		ImaginaryConstants<OctonionFloat16Member>,
		QuaternionConstants<OctonionFloat16Member>,
		OctonionConstants<OctonionFloat16Member>,
		Random<OctonionFloat16Member>,
		Exponential<OctonionFloat16Member>,
		Trigonometric<OctonionFloat16Member>,
		Hyperbolic<OctonionFloat16Member>,
		Power<OctonionFloat16Member>,
		Roots<OctonionFloat16Member>,
		RealUnreal<OctonionFloat16Member,Float16Member>,
		Scale<OctonionFloat16Member, OctonionFloat16Member>,
		ScaleByHighPrec<OctonionFloat16Member>,
		ScaleByRational<OctonionFloat16Member>,
		ScaleByDouble<OctonionFloat16Member>,
		ScaleComponents<OctonionFloat16Member, Float16Member>,
		Tolerance<Float16Member,OctonionFloat16Member>,
		ScaleByOneHalf<OctonionFloat16Member>,
		ScaleByTwo<OctonionFloat16Member>
{
	private static final OctonionFloat16Member ZERO = new OctonionFloat16Member(0, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member ONE_THIRD = new OctonionFloat16Member((float)1.0/3, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member ONE_HALF = new OctonionFloat16Member((float)0.5, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member ONE = new OctonionFloat16Member(1, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member TWO = new OctonionFloat16Member(2, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member E = new OctonionFloat16Member((float)Math.E, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member PI = new OctonionFloat16Member((float)Math.PI, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat16Member GAMMA = new OctonionFloat16Member((float)0.57721566490153286060,0,0,0,0,0,0,0);
	private static final OctonionFloat16Member PHI = new OctonionFloat16Member((float)1.61803398874989484820,0,0,0,0,0,0,0);
	private static final OctonionFloat16Member I = new OctonionFloat16Member(0,1,0,0,0,0,0,0);
	private static final OctonionFloat16Member J = new OctonionFloat16Member(0,0,1,0,0,0,0,0);
	private static final OctonionFloat16Member K = new OctonionFloat16Member(0,0,0,1,0,0,0,0);
	private static final OctonionFloat16Member L = new OctonionFloat16Member(0,0,0,0,1,0,0,0);
	private static final OctonionFloat16Member I0 = new OctonionFloat16Member(0,0,0,0,0,1,0,0);
	private static final OctonionFloat16Member J0 = new OctonionFloat16Member(0,0,0,0,0,0,1,0);
	private static final OctonionFloat16Member K0 = new OctonionFloat16Member(0,0,0,0,0,0,0,1);

	public OctonionFloat16Algebra() { }
	
	private final Procedure1<OctonionFloat16Member> UNITY =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> unity() {
		return UNITY;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> MUL =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			/*
			 
			×	r	i	j	k	l	i0	j0	k0
				==============================
			r	r	i	j	k	l	i0	j0	k0
			i	i	−r	k	−j	i0	−l	−k0	j0
			j	j	−k	−r	i	j0	k0	−l	−i0
			k	k	j	−i	−r	k0	−j0	i0	−l
			l	l	−i0	−j0	−k0	−r	i	j	k
			i0	i0	l	−k0	j0	−i	−r	−k	j
			j0	j0	k0	l	−i0	−j	k	−r	−i
			k0	k0	−j0	i0	l	−k	−j	i	−r

		 */
			OctonionFloat16Member tmp = new OctonionFloat16Member(ZERO);
	
			// r * r = r
			tmp.setR(a.r() * b.r());
			// r * i = i
			tmp.setI(a.r() * b.i());
			// r * j = j
			tmp.setJ(a.r() * b.j());
			// r * k = k
			tmp.setK(a.r() * b.k());
			// r * l = l
			tmp.setL(a.r() * b.l());
			// r * i0 = i0
			tmp.setI0(a.r() * b.i0());
			// r * j0 = j0
			tmp.setJ0(a.r() * b.j0());
			// r * k0 = k0
			tmp.setK0(a.r() * b.k0());
	
			// i * r = i
			tmp.setI(tmp.i() + a.i() * b.r());
			// i * i = −r
			tmp.setR(tmp.r() - a.i() * b.i());
			// i * j = k
			tmp.setK(tmp.k() + a.i() * b.j());
			// i * k = −j
			tmp.setJ(tmp.j() - a.i() * b.k());
			// i * l = i0
			tmp.setI0(tmp.i0() + a.i() * b.l());
			// i * i0 = −l
			tmp.setL(tmp.l() - a.i() * b.i0());
			// i * j0 = −k0
			tmp.setK0(tmp.k0() - a.i() * b.j0());
			// i * k0 = j0
			tmp.setJ0(tmp.j0() + a.i() * b.k0());
	
			// j * r = j
			tmp.setJ(tmp.j() + a.j() * b.r());
			// j * i = −k
			tmp.setK(tmp.k() - a.j() * b.i());
			// j * j = −r
			tmp.setR(tmp.r() - a.j() * b.j());
			// j * k = i
			tmp.setI(tmp.i() + a.j() * b.k());
			// j * l = j0
			tmp.setJ0(tmp.j0() + a.j() * b.l());
			// j * i0 = k0
			tmp.setK0(tmp.k0() + a.j() * b.i0());
			// j * j0 = −l
			tmp.setL(tmp.l() - a.j() * b.j0());
			// j * k0 = -i0
			tmp.setI0(tmp.i0() - a.j() * b.k0());
	
			// k * r = k
			tmp.setK(tmp.k() + a.k() * b.r());
			// k * i = j
			tmp.setJ(tmp.j() + a.k() * b.i());
			// k * j = −i
			tmp.setI(tmp.i() - a.k() * b.j());
			// k * k = −r
			tmp.setR(tmp.r() - a.k() * b.k());
			// k * l = k0
			tmp.setK0(tmp.k0() + a.k() * b.l());
			// k * i0 = −j0
			tmp.setJ0(tmp.j0() - a.k() * b.i0());
			// k * j0 = i0
			tmp.setI0(tmp.i0() + a.k() * b.j0());
			// k * k0 = −l
			tmp.setL(tmp.l() - a.k() * b.k0());
	 
			// l * r = l
			tmp.setL(tmp.l() + a.l() * b.r());
			// l * i = −i0
			tmp.setI0(tmp.i0() - a.l() * b.i());
			// l * j = −j0
			tmp.setJ0(tmp.j0() - a.l() * b.j());
			// l * k = −k0
			tmp.setK0(tmp.k0() - a.l() * b.k());
			// l * l = −r
			tmp.setR(tmp.r() - a.l() * b.l());
			// l * i0 = i
			tmp.setI(tmp.i() + a.l() * b.i0());
			// l * j0 = j
			tmp.setJ(tmp.j() + a.l() * b.j0());
			// l * k0 = k
			tmp.setK(tmp.k() + a.l() * b.k0());
	
			// i0 * r = i0
			tmp.setI0(tmp.i0() + a.i0() * b.r());
			// i0 * i = l
			tmp.setL(tmp.l() + a.i0() * b.i());
			// i0 * j = −k0
			tmp.setK0(tmp.k0() - a.i0() * b.j());
			// i0 * k = j0
			tmp.setJ0(tmp.j0() + a.i0() * b.k());
			// i0 * l = −i
			tmp.setI(tmp.i() - a.i0() * b.l());
			// i0 * i0 = −r
			tmp.setR(tmp.r() - a.i0() * b.i0());
			// i0 * j0 = −k
			tmp.setK(tmp.k() - a.i0() * b.j0());
			// i0 * k0 = j
			tmp.setJ(tmp.j() + a.i0() * b.k0());
			
			// j0 * r = j0
			tmp.setJ0(tmp.j0() + a.j0() * b.r());
			// j0 * i = k0
			tmp.setK0(tmp.k0() + a.j0() * b.i());
			// j0 * j = l
			tmp.setL(tmp.l() + a.j0() * b.j());
			// j0 * k = −i0
			tmp.setI0(tmp.i0() - a.j0() * b.k());
			// j0 * l = −j
			tmp.setJ(tmp.j() - a.j0() * b.l());
			// j0 * i0 = k
			tmp.setK(tmp.k() + a.j0() * b.i0());
			// j0 * j0 = −r
			tmp.setR(tmp.r() - a.j0() * b.j0());
			// j0 * k0 = −i
			tmp.setI(tmp.i() - a.j0() * b.k0());
			
			// k0 * r = k0
			tmp.setK0(tmp.k0() + a.k0() * b.r());
			// k0 * i = −j0
			tmp.setJ0(tmp.j0() - a.k0() * b.i());
			// k0 * j = i0
			tmp.setI0(tmp.i0() + a.k0() * b.j());
			// k0 * k = l
			tmp.setL(tmp.l() + a.k0() * b.k());
			// k0 * l = −k
			tmp.setK(tmp.k() - a.k0() * b.l());
			// k0 * i0 = −j
			tmp.setJ(tmp.j() - a.k0() * b.i0());
			// k0 * j0 = i
			tmp.setI(tmp.i() + a.k0() * b.j0());
			// k0 * k0 = −r
			tmp.setR(tmp.r() - a.k0() * b.k0());
			
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat16Member,OctonionFloat16Member> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat16Member a, OctonionFloat16Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.OHLF, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,OctonionFloat16Member,OctonionFloat16Member> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat16Member> ZER =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> NEG =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> ADD =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
			c.setJ( a.j() + b.j() );
			c.setK( a.k() + b.k() );
			c.setL( a.l() + b.l() );
			c.setI0( a.i0() + b.i0() );
			c.setJ0( a.j0() + b.j0() );
			c.setK0( a.k0() + b.k0() );
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> SUB =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
			c.setJ( a.j() - b.j() );
			c.setK( a.k() - b.k() );
			c.setL( a.l() - b.l() );
			c.setI0( a.i0() - b.i0() );
			c.setJ0( a.j0() - b.j0() );
			c.setK0( a.k0() - b.k0() );
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat16Member,OctonionFloat16Member> EQ =
			new Function2<Boolean, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public Boolean call(OctonionFloat16Member a, OctonionFloat16Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k() &&
					a.l() == b.l() && a.i0() == b.i0() && a.j0() == b.j0() && a.k0() == b.k0();
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16Member,OctonionFloat16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat16Member,OctonionFloat16Member> NEQ =
			new Function2<Boolean, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public Boolean call(OctonionFloat16Member a, OctonionFloat16Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16Member,OctonionFloat16Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public OctonionFloat16Member construct() {
		return new OctonionFloat16Member();
	}

	@Override
	public OctonionFloat16Member construct(OctonionFloat16Member other) {
		return new OctonionFloat16Member(other);
	}

	@Override
	public OctonionFloat16Member construct(String s) {
		return new OctonionFloat16Member(s);
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> ASSIGN =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member from, OctonionFloat16Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> INV =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			double norm2 = norm2(a);
			conjugate().call(a, b);
			b.setR( (float) (b.r() / norm2) );
			b.setI( (float) (b.i() / norm2) );
			b.setJ( (float) (b.j() / norm2) );
			b.setK( (float) (b.k() / norm2) );
			b.setL( (float) (b.l() / norm2) );
			b.setI0( (float) (b.i0() / norm2) );
			b.setJ0( (float) (b.j0() / norm2) );
			b.setK0( (float) (b.k0() / norm2) );
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> invert() {
		return INV;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> DIVIDE =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> divide() {
		return DIVIDE;
	}

	private final Procedure4<Round.Mode,Float16Member,OctonionFloat16Member,OctonionFloat16Member> ROUND =
			new Procedure4<Round.Mode, Float16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, OctonionFloat16Member a, OctonionFloat16Member b) {
			Float16Member tmp = new Float16Member();
			tmp.setV(a.r());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setI(tmp.v());
			tmp.setV(a.j());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setJ(tmp.v());
			tmp.setV(a.k());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setK(tmp.v());
			tmp.setV(a.l());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setL(tmp.v());
			tmp.setV(a.i0());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setI0(tmp.v());
			tmp.setV(a.j0());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setJ0(tmp.v());
			tmp.setV(a.k0());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setK0(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,OctonionFloat16Member,OctonionFloat16Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat16Member> ISNAN =
			new Function1<Boolean, OctonionFloat16Member>()
	{
		@Override
		public Boolean call(OctonionFloat16Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k()) ||
					Double.isNaN(a.l()) || Double.isNaN(a.i0()) || Double.isNaN(a.j0()) || Double.isNaN(a.k0());
		}
	};

	@Override
	public Function1<Boolean,OctonionFloat16Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat16Member> NAN =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			a.setR(Float.NaN);
			a.setI(Float.NaN);
			a.setJ(Float.NaN);
			a.setK(Float.NaN);
			a.setL(Float.NaN);
			a.setI0(Float.NaN);
			a.setJ0(Float.NaN);
			a.setK0(Float.NaN);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,OctonionFloat16Member> ISINF =
			new Function1<Boolean, OctonionFloat16Member>()
	{
		@Override
		public Boolean call(OctonionFloat16Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) ||
					Double.isInfinite(a.k()) || Double.isInfinite(a.l()) || Double.isInfinite(a.i0()) ||
					Double.isInfinite(a.j0()) || Double.isInfinite(a.k0()) );
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat16Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat16Member> INF =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			a.setR(Float.POSITIVE_INFINITY);
			a.setI(Float.POSITIVE_INFINITY);
			a.setJ(Float.POSITIVE_INFINITY);
			a.setK(Float.POSITIVE_INFINITY);
			a.setL(Float.POSITIVE_INFINITY);
			a.setI0(Float.POSITIVE_INFINITY);
			a.setJ0(Float.POSITIVE_INFINITY);
			a.setK0(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat16Member,Float16Member> NORM =
			new Procedure2<OctonionFloat16Member, Float16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, Float16Member b) {
			b.setV( (float) norm(a) );
		}
	};

	@Override
	public Procedure2<OctonionFloat16Member,Float16Member> norm() {
		return NORM;
	}

	private double norm(OctonionFloat16Member a) {
		// a hypot()-like implementation that avoids overflow
		double max = Math.max(Math.abs(a.r()), Math.abs(a.i()));
		max = Math.max(max, Math.abs(a.j()));
		max = Math.max(max, Math.abs(a.k()));
		max = Math.max(max, Math.abs(a.l()));
		max = Math.max(max, Math.abs(a.i0()));
		max = Math.max(max, Math.abs(a.j0()));
		max = Math.max(max, Math.abs(a.k0()));
		if (max == 0) {
			return 0;
		}
		else {
			double sum = (a.r()/max) * (a.r()/max);
			sum += (a.i()/max) * (a.i()/max);
			sum += (a.j()/max) * (a.j()/max);
			sum += (a.k()/max) * (a.k()/max);
			sum += (a.l()/max) * (a.l()/max);
			sum += (a.i0()/max) * (a.i0()/max);
			sum += (a.j0()/max) * (a.j0()/max);
			sum += (a.k0()/max) * (a.k0()/max);
			return max * Math.sqrt(sum);
		}
	}
	
	private double norm2(OctonionFloat16Member a) {
		double norm = norm(a);
		return norm * norm;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> CONJ =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			b.setR(a.r());
			b.setI(-a.i());
			b.setJ(-a.j());
			b.setK(-a.k());
			b.setL(-a.l());
			b.setI0(-a.i0());
			b.setJ0(-a.j0());
			b.setK0(-a.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> conjugate() {
		return CONJ;
	}

	private final Procedure1<OctonionFloat16Member> PI_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> PI() {
		return PI_;
	}

	private final Procedure1<OctonionFloat16Member> E_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> E() {
		return E_;
	}
	
	private final Procedure1<OctonionFloat16Member> GAMMA_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<OctonionFloat16Member> PHI_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> PHI() {
		return PHI_;
	}

	private final Procedure1<OctonionFloat16Member> I_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> I() {
		return I_;
	}

	private final Procedure1<OctonionFloat16Member> J_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> J() {
		return J_;
	}

	private final Procedure1<OctonionFloat16Member> K_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> K() {
		return K_;
	}

	private final Procedure1<OctonionFloat16Member> L_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(L, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> L() {
		return L_;
	}

	private final Procedure1<OctonionFloat16Member> I0_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(I0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> I0() {
		return I0_;
	}

	private final Procedure1<OctonionFloat16Member> J0_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(J0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> J0() {
		return J0_;
	}

	private final Procedure1<OctonionFloat16Member> K0_ =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			assign().call(K0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> K0() {
		return K0_;
	}

	private final Procedure1<OctonionFloat16Member> RAND =
			new Procedure1<OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextFloat());
			a.setI(rng.nextFloat());
			a.setJ(rng.nextFloat());
			a.setK(rng.nextFloat());
			a.setL(rng.nextFloat());
			a.setI0(rng.nextFloat());
			a.setJ0(rng.nextFloat());
			a.setK0(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16Member> random() {
		return RAND;
	}

	private final Procedure2<OctonionFloat16Member,Float16Member> REAL =
			new Procedure2<OctonionFloat16Member, Float16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, Float16Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,Float16Member> real() {
		return REAL;
	}
	
	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> UNREAL =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SINH =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat16Member negA = new OctonionFloat16Member();
			OctonionFloat16Member sum = new OctonionFloat16Member();
			OctonionFloat16Member tmp1 = new OctonionFloat16Member();
			OctonionFloat16Member tmp2 = new OctonionFloat16Member();
		
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> COSH =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat16Member negA = new OctonionFloat16Member();
			OctonionFloat16Member sum = new OctonionFloat16Member();
			OctonionFloat16Member tmp1 = new OctonionFloat16Member();
			OctonionFloat16Member tmp2 = new OctonionFloat16Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> cosh() {
		return COSH;
    }

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> SINHANDCOSH =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member s, OctonionFloat16Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat16Member negA = new OctonionFloat16Member();
			OctonionFloat16Member sum = new OctonionFloat16Member();
			OctonionFloat16Member tmp1 = new OctonionFloat16Member();
			OctonionFloat16Member tmp2 = new OctonionFloat16Member();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);

			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, s);

			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> TANH =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			OctonionFloat16Member s = new OctonionFloat16Member();
			OctonionFloat16Member c = new OctonionFloat16Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SIN =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			b.setR((float) (sin * cosh));
			b.setI((float) (ws * a.i()));
			b.setJ((float) (ws * a.j()));
			b.setK((float) (ws * a.k()));
			b.setL((float) (ws * a.l()));
			b.setI0((float) (ws * a.i0()));
			b.setJ0((float) (ws * a.j0()));
			b.setK0((float) (ws * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> COS =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double wc = -sin * sinhc_pi;
			b.setR((float) (cos * cosh));
			b.setI((float) (wc * a.i()));
			b.setJ((float) (wc * a.j()));
			b.setK((float) (wc * a.k()));
			b.setL((float) (wc * a.l()));
			b.setI0((float) (wc * a.i0()));
			b.setJ0((float) (wc * a.j0()));
			b.setK0((float) (wc * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> cos() {
		return COS;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> SINANDCOS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member s, OctonionFloat16Member c) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR((float) (sin * cosh));
			s.setI((float) (ws * a.i()));
			s.setJ((float) (ws * a.j()));
			s.setK((float) (ws * a.k()));
			s.setL((float) (ws * a.l()));
			s.setI0((float) (ws * a.i0()));
			s.setJ0((float) (ws * a.j0()));
			s.setK0((float) (ws * a.k0()));
			c.setR((float) (cos * cosh));
			c.setI((float) (wc * a.i()));
			c.setJ((float) (wc * a.j()));
			c.setK((float) (wc * a.k()));
			c.setL((float) (wc * a.l()));
			c.setI0((float) (wc * a.i0()));
			c.setJ0((float) (wc * a.j0()));
			c.setK0((float) (wc * a.k0()));
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> TAN =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			OctonionFloat16Member sin = new OctonionFloat16Member();
			OctonionFloat16Member cos = new OctonionFloat16Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> tan() {
		return TAN;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> EXP =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			double u = FastMath.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinc().call(z, z2);
			double w = z2.v();
			b.setR((float) (u * FastMath.cos(z.v())));
			b.setI((float) (u * w * a.i()));
			b.setJ((float) (u * w * a.j()));
			b.setK((float) (u * w * a.k()));
			b.setL((float) (u * w * a.l()));
			b.setI0((float) (u * w * a.i0()));
			b.setJ0((float) (u * w * a.j0()));
			b.setK0((float) (u * w * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> exp() {
		return EXP;
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	
	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> LOG =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			double factor;
			OctonionFloat16Member unreal = new OctonionFloat16Member();
			ComplexFloat16Member tmp = new ComplexFloat16Member();
			Float16Member norm = new Float16Member();
			assign().call(a, b); // this should be safe if two variables or one
			unreal().call(a, unreal);
			norm().call(unreal, norm);
			tmp.setR(a.r());
			tmp.setI(norm.v());
			G.CHLF.log().call(tmp, tmp);
			if ( norm.v() == 0.0 ) {
				factor = tmp.i();
			} else {
				factor = tmp.i() / norm.v();
			}

			multiplier(tmp.r(), factor, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> log() {
		return LOG;
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	private void multiplier(double r, double factor, OctonionFloat16Member result) {
		if ( Double.isNaN( factor ) || Double.isInfinite( factor ) ) {
			if ( result.i() == 0 && result.j() == 0 && result.k() == 0 ) {
				result.setR((float) (r));
				result.setI((float) (result.i() * factor));
				result.setJ((float) (result.j() * factor));
				result.setK((float) (result.k() * factor));
				result.setL((float) (result.l() * factor));
				result.setI0((float) (result.i0() * factor));
				result.setJ0((float) (result.j0() * factor));
				result.setK0((float) (result.k0() * factor));
			}
			else {
				double signum = Math.signum(factor);
				result.setR((float) (r));
				if (result.i() == 0) result.setI((float) (signum * result.i())); else result.setI((float) (factor * result.i()));
				if (result.j() == 0) result.setJ((float) (signum * result.j())); else result.setJ((float) (factor * result.j()));
				if (result.k() == 0) result.setK((float) (signum * result.k())); else result.setK((float) (factor * result.k()));
				if (result.l() == 0) result.setL((float) (signum * result.l())); else result.setL((float) (factor * result.l()));
				if (result.i0() == 0) result.setI0((float) (signum * result.i0())); else result.setI0((float) (factor * result.i0()));
				if (result.j0() == 0) result.setJ0((float) (signum * result.j0())); else result.setJ0((float) (factor * result.j0()));
				if (result.k0() == 0) result.setK0((float) (signum * result.k0())); else result.setK0((float) (factor * result.k0()));
			}
		}
		else {
			result.setR((float) (r));
			result.setI((float) (result.i() * factor));
			result.setJ((float) (result.j() * factor));
			result.setK((float) (result.k() * factor));
			result.setL((float) (result.l() * factor));
			result.setI0((float) (result.i0() * factor));
			result.setJ0((float) (result.j0() * factor));
			result.setK0((float) (result.k0() * factor));
		}
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> POW =
			new Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			OctonionFloat16Member logA = new OctonionFloat16Member();
			OctonionFloat16Member bLogA = new OctonionFloat16Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16Member,OctonionFloat16Member> pow() {
		return POW;
	}	

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SINCH =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Sinch.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SINCHPI =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Sinchpi.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SINC =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Sinc.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SINCPI =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			Sincpi.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> SQRT =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<OctonionFloat16Member,OctonionFloat16Member> CBRT =
			new Procedure2<OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16Member,OctonionFloat16Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, OctonionFloat16Member> ISZERO =
			new Function1<Boolean, OctonionFloat16Member>()
	{
		@Override
		public Boolean call(OctonionFloat16Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0 && a.l() == 0 &&
					a.i0() == 0 && a.j0() == 0 && a.k0() == 0;
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16Member, OctonionFloat16Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat16Member, OctonionFloat16Member> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat16Member b, OctonionFloat16Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j()));
			c.setJ(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k()));
			c.setK(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.l()));
			c.setL(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i0()));
			c.setI0(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j0()));
			c.setJ0(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k0()));
			c.setK0(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat16Member, OctonionFloat16Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat16Member, OctonionFloat16Member> SBR =
			new Procedure3<RationalMember, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat16Member b, OctonionFloat16Member c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(b.r());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setR(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.i());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.j());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setJ(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.k());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setK(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.l());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setL(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.i0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI0(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.j0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setJ0(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.k0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setK0(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat16Member, OctonionFloat16Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat16Member, OctonionFloat16Member> SBD =
			new Procedure3<Double, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(Double a, OctonionFloat16Member b, OctonionFloat16Member c) {
			c.setR((float)(a * b.r()));
			c.setI((float)(a * b.i()));
			c.setJ((float)(a * b.j()));
			c.setK((float)(a * b.k()));
			c.setL((float)(a * b.l()));
			c.setI0((float)(a * b.i0()));
			c.setJ0((float)(a * b.j0()));
			c.setK0((float)(a * b.k0()));
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat16Member, OctonionFloat16Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float16Member, OctonionFloat16Member, OctonionFloat16Member> SC =
			new Procedure3<Float16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(Float16Member a, OctonionFloat16Member b, OctonionFloat16Member c) {
			c.setR(a.v() * b.r());
			c.setI(a.v() * b.i());
			c.setJ(a.v() * b.j());
			c.setK(a.v() * b.k());
			c.setL(a.v() * b.l());
			c.setI0(a.v() * b.i0());
			c.setJ0(a.v() * b.j0());
			c.setK0(a.v() * b.k0());
		}
	};

	@Override
	public Procedure3<Float16Member, OctonionFloat16Member, OctonionFloat16Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float16Member, OctonionFloat16Member, OctonionFloat16Member> WITHIN =
			new Function3<Boolean, Float16Member, OctonionFloat16Member, OctonionFloat16Member>()
	{
		
		@Override
		public Boolean call(Float16Member tol, OctonionFloat16Member a, OctonionFloat16Member b) {
			return OctonionNumberWithin.compute(G.HLF, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float16Member, OctonionFloat16Member, OctonionFloat16Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member> STWO =
			new Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionFloat16Member a, OctonionFloat16Member b) {
			assign().call(a, b);
			for (int i = 0; i < numTimes; i++)
				multiply().call(b, TWO, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member> SHALF =
			new Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionFloat16Member a, OctonionFloat16Member b) {
			assign().call(a, b);
			for (int i = 0; i < numTimes; i++)
				divide().call(b, TWO, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionFloat16Member, OctonionFloat16Member> scaleByOneHalf() {
		return SHALF;
	}

}
