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
package nom.bdezonia.zorbage.type.float64.octonion;

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
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;


/**
 * 
 * @author Barry DeZonia
 *
 */
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat64Algebra
	implements
		SkewField<OctonionFloat64Algebra, OctonionFloat64Member>,
		Conjugate<OctonionFloat64Member>,
		Norm<OctonionFloat64Member,Float64Member>,
		Infinite<OctonionFloat64Member>,
		NaN<OctonionFloat64Member>,
		Rounding<Float64Member,OctonionFloat64Member>,
		RealConstants<OctonionFloat64Member>,
		ImaginaryConstants<OctonionFloat64Member>,
		QuaternionConstants<OctonionFloat64Member>,
		OctonionConstants<OctonionFloat64Member>,
		Random<OctonionFloat64Member>,
		Exponential<OctonionFloat64Member>,
		Trigonometric<OctonionFloat64Member>,
		Hyperbolic<OctonionFloat64Member>,
		Power<OctonionFloat64Member>,
		Roots<OctonionFloat64Member>,
		RealUnreal<OctonionFloat64Member,Float64Member>,
		Scale<OctonionFloat64Member, OctonionFloat64Member>,
		ScaleByHighPrec<OctonionFloat64Member>,
		ScaleByRational<OctonionFloat64Member>,
		ScaleByDouble<OctonionFloat64Member>,
		ScaleComponents<OctonionFloat64Member, Float64Member>,
		Tolerance<Float64Member,OctonionFloat64Member>
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(0, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member ONE_THIRD = new OctonionFloat64Member(1.0/3, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member ONE_HALF = new OctonionFloat64Member(0.5, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member ONE = new OctonionFloat64Member(1, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member TWO = new OctonionFloat64Member(2, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member E = new OctonionFloat64Member(Math.E, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member PI = new OctonionFloat64Member(Math.PI, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member GAMMA = new OctonionFloat64Member(0.57721566490153286060,0,0,0,0,0,0,0);
	private static final OctonionFloat64Member PHI = new OctonionFloat64Member(1.61803398874989484820,0,0,0,0,0,0,0);
	private static final OctonionFloat64Member I = new OctonionFloat64Member(0, 1, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member J = new OctonionFloat64Member(0, 0, 1, 0, 0, 0, 0, 0);
	private static final OctonionFloat64Member K = new OctonionFloat64Member(0, 0, 0, 1, 0, 0, 0, 0);
	private static final OctonionFloat64Member L = new OctonionFloat64Member(0, 0, 0, 0, 1, 0, 0, 0);
	private static final OctonionFloat64Member I0 = new OctonionFloat64Member(0, 0, 0, 0, 0, 1, 0, 0);
	private static final OctonionFloat64Member J0 = new OctonionFloat64Member(0, 0, 0, 0, 0, 0, 1, 0);
	private static final OctonionFloat64Member K0 = new OctonionFloat64Member(0, 0, 0, 0, 0, 0, 0, 1);

	public OctonionFloat64Algebra() { }
	
	private final Procedure1<OctonionFloat64Member> UNITY =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> unity() {
		return UNITY;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> MUL =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
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
			OctonionFloat64Member tmp = new OctonionFloat64Member(ZERO);
	
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
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat64Member,OctonionFloat64Member> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat64Member a, OctonionFloat64Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.ODBL, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,OctonionFloat64Member,OctonionFloat64Member> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat64Member> ZER =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> NEG =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> ADD =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
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
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> SUB =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
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
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat64Member,OctonionFloat64Member> EQ =
			new Function2<Boolean, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public Boolean call(OctonionFloat64Member a, OctonionFloat64Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k() &&
					a.l() == b.l() && a.i0() == b.i0() && a.j0() == b.j0() && a.k0() == b.k0();
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64Member,OctonionFloat64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64Member,OctonionFloat64Member> NEQ =
			new Function2<Boolean, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public Boolean call(OctonionFloat64Member a, OctonionFloat64Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64Member,OctonionFloat64Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public OctonionFloat64Member construct() {
		return new OctonionFloat64Member();
	}

	@Override
	public OctonionFloat64Member construct(OctonionFloat64Member other) {
		return new OctonionFloat64Member(other);
	}

	@Override
	public OctonionFloat64Member construct(String s) {
		return new OctonionFloat64Member(s);
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> ASSIGN =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member from, OctonionFloat64Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> INV =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			double norm2 = norm2(a);
			conjugate().call(a, b);
			b.setR( b.r() / norm2 );
			b.setI( b.i() / norm2 );
			b.setJ( b.j() / norm2 );
			b.setK( b.k() / norm2 );
			b.setL( b.l() / norm2 );
			b.setI0( b.i0() / norm2 );
			b.setJ0( b.j0() / norm2 );
			b.setK0( b.k0() / norm2 );
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> invert() {
		return INV;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> DIVIDE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
			OctonionFloat64Member tmp = new OctonionFloat64Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> divide() {
		return DIVIDE;
	}

	private final Procedure4<Round.Mode,Float64Member,OctonionFloat64Member,OctonionFloat64Member> ROUND =
			new Procedure4<Round.Mode, Float64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64Member a, OctonionFloat64Member b) {
			Float64Member tmp = new Float64Member();
			tmp.setV(a.r());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setI(tmp.v());
			tmp.setV(a.j());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setJ(tmp.v());
			tmp.setV(a.k());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setK(tmp.v());
			tmp.setV(a.l());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setL(tmp.v());
			tmp.setV(a.i0());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setI0(tmp.v());
			tmp.setV(a.j0());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setJ0(tmp.v());
			tmp.setV(a.k0());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setK0(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,OctonionFloat64Member,OctonionFloat64Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat64Member> ISNAN =
			new Function1<Boolean, OctonionFloat64Member>()
	{
		@Override
		public Boolean call(OctonionFloat64Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k()) ||
					Double.isNaN(a.l()) || Double.isNaN(a.i0()) || Double.isNaN(a.j0()) || Double.isNaN(a.k0());
		}
	};

	@Override
	public Function1<Boolean,OctonionFloat64Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64Member> NAN =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			a.setR(Double.NaN);
			a.setI(Double.NaN);
			a.setJ(Double.NaN);
			a.setK(Double.NaN);
			a.setL(Double.NaN);
			a.setI0(Double.NaN);
			a.setJ0(Double.NaN);
			a.setK0(Double.NaN);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,OctonionFloat64Member> ISINF =
			new Function1<Boolean, OctonionFloat64Member>()
	{
		@Override
		public Boolean call(OctonionFloat64Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) ||
					Double.isInfinite(a.k()) || Double.isInfinite(a.l()) || Double.isInfinite(a.i0()) ||
					Double.isInfinite(a.j0()) || Double.isInfinite(a.k0()) );
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64Member> INF =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			a.setR(Double.POSITIVE_INFINITY);
			a.setI(Double.POSITIVE_INFINITY);
			a.setJ(Double.POSITIVE_INFINITY);
			a.setK(Double.POSITIVE_INFINITY);
			a.setL(Double.POSITIVE_INFINITY);
			a.setI0(Double.POSITIVE_INFINITY);
			a.setJ0(Double.POSITIVE_INFINITY);
			a.setK0(Double.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat64Member,Float64Member> NORM =
			new Procedure2<OctonionFloat64Member, Float64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, Float64Member b) {
			b.setV( norm(a) );
		}
	};

	@Override
	public Procedure2<OctonionFloat64Member,Float64Member> norm() {
		return NORM;
	}

	private double norm(OctonionFloat64Member a) {
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
	
	private double norm2(OctonionFloat64Member a) {
		double norm = norm(a);
		return norm * norm;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> CONJ =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
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
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> conjugate() {
		return CONJ;
	}

	private final Procedure1<OctonionFloat64Member> PI_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> PI() {
		return PI_;
	}

	private final Procedure1<OctonionFloat64Member> E_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> E() {
		return E_;
	}
	
	private final Procedure1<OctonionFloat64Member> GAMMA_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<OctonionFloat64Member> PHI_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> PHI() {
		return PHI_;
	}

	private final Procedure1<OctonionFloat64Member> I_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> I() {
		return I_;
	}

	private final Procedure1<OctonionFloat64Member> J_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> J() {
		return J_;
	}

	private final Procedure1<OctonionFloat64Member> K_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> K() {
		return K_;
	}

	private final Procedure1<OctonionFloat64Member> L_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(L, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> L() {
		return L_;
	}

	private final Procedure1<OctonionFloat64Member> I0_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(I0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> I0() {
		return I0_;
	}

	private final Procedure1<OctonionFloat64Member> J0_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(J0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> J0() {
		return J0_;
	}

	private final Procedure1<OctonionFloat64Member> K0_ =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			assign().call(K0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> K0() {
		return K0_;
	}

	private final Procedure1<OctonionFloat64Member> RAND =
			new Procedure1<OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextDouble());
			a.setI(rng.nextDouble());
			a.setJ(rng.nextDouble());
			a.setK(rng.nextDouble());
			a.setL(rng.nextDouble());
			a.setI0(rng.nextDouble());
			a.setJ0(rng.nextDouble());
			a.setK0(rng.nextDouble());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64Member> random() {
		return RAND;
	}

	private final Procedure2<OctonionFloat64Member,Float64Member> REAL =
			new Procedure2<OctonionFloat64Member, Float64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, Float64Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,Float64Member> real() {
		return REAL;
	}
	
	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> UNREAL =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SINH =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat64Member negA = new OctonionFloat64Member();
			OctonionFloat64Member sum = new OctonionFloat64Member();
			OctonionFloat64Member tmp1 = new OctonionFloat64Member();
			OctonionFloat64Member tmp2 = new OctonionFloat64Member();
		
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> COSH =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat64Member negA = new OctonionFloat64Member();
			OctonionFloat64Member sum = new OctonionFloat64Member();
			OctonionFloat64Member tmp1 = new OctonionFloat64Member();
			OctonionFloat64Member tmp2 = new OctonionFloat64Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> cosh() {
		return COSH;
    }

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> SINHANDCOSH =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member s, OctonionFloat64Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat64Member negA = new OctonionFloat64Member();
			OctonionFloat64Member sum = new OctonionFloat64Member();
			OctonionFloat64Member tmp1 = new OctonionFloat64Member();
			OctonionFloat64Member tmp2 = new OctonionFloat64Member();

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
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> TANH =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			OctonionFloat64Member s = new OctonionFloat64Member();
			OctonionFloat64Member c = new OctonionFloat64Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SIN =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Float64Member z = new Float64Member();
			Float64Member z2 = new Float64Member();
			OctonionFloat64Member tmp = new OctonionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			b.setR(sin * cosh);
			b.setI(ws * a.i());
			b.setJ(ws * a.j());
			b.setK(ws * a.k());
			b.setL(ws * a.l());
			b.setI0(ws * a.i0());
			b.setJ0(ws * a.j0());
			b.setK0(ws * a.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> COS =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Float64Member z = new Float64Member();
			Float64Member z2 = new Float64Member();
			OctonionFloat64Member tmp = new OctonionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double wc = -sin * sinhc_pi;
			b.setR(cos * cosh);
			b.setI(wc * a.i());
			b.setJ(wc * a.j());
			b.setK(wc * a.k());
			b.setL(wc * a.l());
			b.setI0(wc * a.i0());
			b.setJ0(wc * a.j0());
			b.setK0(wc * a.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> cos() {
		return COS;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> SINANDCOS =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member s, OctonionFloat64Member c) {
			Float64Member z = new Float64Member();
			Float64Member z2 = new Float64Member();
			OctonionFloat64Member tmp = new OctonionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR(sin * cosh);
			s.setI(ws * a.i());
			s.setJ(ws * a.j());
			s.setK(ws * a.k());
			s.setL(ws * a.l());
			s.setI0(ws * a.i0());
			s.setJ0(ws * a.j0());
			s.setK0(ws * a.k0());
			c.setR(cos * cosh);
			c.setI(wc * a.i());
			c.setJ(wc * a.j());
			c.setK(wc * a.k());
			c.setL(wc * a.l());
			c.setI0(wc * a.i0());
			c.setJ0(wc * a.j0());
			c.setK0(wc * a.k0());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> TAN =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			OctonionFloat64Member sin = new OctonionFloat64Member();
			OctonionFloat64Member cos = new OctonionFloat64Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> tan() {
		return TAN;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> EXP =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Float64Member z = new Float64Member();
			Float64Member z2 = new Float64Member();
			OctonionFloat64Member tmp = new OctonionFloat64Member();
			double u = FastMath.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinc().call(z, z2);
			double w = z2.v();
			b.setR(u * FastMath.cos(z.v()));
			b.setI(u * w * a.i());
			b.setJ(u * w * a.j());
			b.setK(u * w * a.k());
			b.setL(u * w * a.l());
			b.setI0(u * w * a.i0());
			b.setJ0(u * w * a.j0());
			b.setK0(u * w * a.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> exp() {
		return EXP;
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	
	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> LOG =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			double factor;
			OctonionFloat64Member unreal = new OctonionFloat64Member();
			ComplexFloat64Member tmp = new ComplexFloat64Member();
			Float64Member norm = new Float64Member();
			assign().call(a, b); // this should be safe if two variables or one
			unreal().call(a, unreal);
			norm().call(unreal, norm);
			tmp.setR(a.r());
			tmp.setI(norm.v());
			G.CDBL.log().call(tmp, tmp);
			if ( norm.v() == 0.0 ) {
				factor = tmp.i();
			} else {
				factor = tmp.i() / norm.v();
			}

			multiplier(tmp.r(), factor, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> log() {
		return LOG;
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	private void multiplier(double r, double factor, OctonionFloat64Member result) {
		if ( Double.isNaN( factor ) || Double.isInfinite( factor ) ) {
			if ( result.i() == 0 && result.j() == 0 && result.k() == 0 ) {
				result.setR(r);
				result.setI(result.i() * factor);
				result.setJ(result.j() * factor);
				result.setK(result.k() * factor);
				result.setL(result.l() * factor);
				result.setI0(result.i0() * factor);
				result.setJ0(result.j0() * factor);
				result.setK0(result.k0() * factor);
			}
			else {
				double signum = Math.signum(factor);
				result.setR(r);
				if (result.i() == 0) result.setI(signum * result.i()); else result.setI(factor * result.i());
				if (result.j() == 0) result.setJ(signum * result.j()); else result.setJ(factor * result.j());
				if (result.k() == 0) result.setK(signum * result.k()); else result.setK(factor * result.k());
				if (result.l() == 0) result.setL(signum * result.l()); else result.setL(factor * result.l());
				if (result.i0() == 0) result.setI0(signum * result.i0()); else result.setI0(factor * result.i0());
				if (result.j0() == 0) result.setJ0(signum * result.j0()); else result.setJ0(factor * result.j0());
				if (result.k0() == 0) result.setK0(signum * result.k0()); else result.setK0(factor * result.k0());
			}
		}
		else {
			result.setR(r);
			result.setI(result.i() * factor);
			result.setJ(result.j() * factor);
			result.setK(result.k() * factor);
			result.setL(result.l() * factor);
			result.setI0(result.i0() * factor);
			result.setJ0(result.j0() * factor);
			result.setK0(result.k0() * factor);
		}
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> POW =
			new Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
			OctonionFloat64Member logA = new OctonionFloat64Member();
			OctonionFloat64Member bLogA = new OctonionFloat64Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64Member,OctonionFloat64Member> pow() {
		return POW;
	}	

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SINCH =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Sinch.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SINCHPI =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Sinchpi.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SINC =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Sinc.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SINCPI =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			Sincpi.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> SQRT =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<OctonionFloat64Member,OctonionFloat64Member> CBRT =
			new Procedure2<OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64Member,OctonionFloat64Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, OctonionFloat64Member> ISZERO =
			new Function1<Boolean, OctonionFloat64Member>()
	{
		@Override
		public Boolean call(OctonionFloat64Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0 && a.l() == 0 &&
					a.i0() == 0 && a.j0() == 0 && a.k0() == 0;
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64Member, OctonionFloat64Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat64Member, OctonionFloat64Member> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat64Member b, OctonionFloat64Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j()));
			c.setJ(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k()));
			c.setK(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.l()));
			c.setL(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i0()));
			c.setI0(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j0()));
			c.setJ0(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k0()));
			c.setK0(tmp.doubleValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat64Member, OctonionFloat64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat64Member, OctonionFloat64Member> SBR =
			new Procedure3<RationalMember, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat64Member b, OctonionFloat64Member c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(b.r());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setR(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.i());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.j());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setJ(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.k());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setK(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.l());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setL(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.i0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI0(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.j0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setJ0(tmp.doubleValue());
			tmp = BigDecimal.valueOf(b.k0());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setK0(tmp.doubleValue());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat64Member, OctonionFloat64Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat64Member, OctonionFloat64Member> SBD =
			new Procedure3<Double, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(Double a, OctonionFloat64Member b, OctonionFloat64Member c) {
			c.setR(a * b.r());
			c.setI(a * b.i());
			c.setJ(a * b.j());
			c.setK(a * b.k());
			c.setL(a * b.l());
			c.setI0(a * b.i0());
			c.setJ0(a * b.j0());
			c.setK0(a * b.k0());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat64Member, OctonionFloat64Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float64Member, OctonionFloat64Member, OctonionFloat64Member> SC =
			new Procedure3<Float64Member, OctonionFloat64Member, OctonionFloat64Member>()
	{
		@Override
		public void call(Float64Member a, OctonionFloat64Member b, OctonionFloat64Member c) {
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
	public Procedure3<Float64Member, OctonionFloat64Member, OctonionFloat64Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float64Member, OctonionFloat64Member, OctonionFloat64Member> WITHIN =
			new Function3<Boolean, Float64Member,OctonionFloat64Member, OctonionFloat64Member>()
	{
		
		@Override
		public Boolean call(Float64Member tol, OctonionFloat64Member a, OctonionFloat64Member b) {
			return OctonionNumberWithin.compute(G.DBL, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float64Member, OctonionFloat64Member, OctonionFloat64Member> within() {
		return WITHIN;
	}

}
