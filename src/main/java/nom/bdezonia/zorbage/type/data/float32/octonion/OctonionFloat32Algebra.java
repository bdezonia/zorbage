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
package nom.bdezonia.zorbage.type.data.float32.octonion;

import java.util.concurrent.ThreadLocalRandom;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
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
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.ImaginaryConstants;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OctonionConstants;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.QuaternionConstants;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.SkewField;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.data.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;


/**
 * 
 * @author Barry DeZonia
 *
 */
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat32Algebra
  implements
    SkewField<OctonionFloat32Algebra, OctonionFloat32Member>,
    Conjugate<OctonionFloat32Member>,
    Norm<OctonionFloat32Member,Float32Member>,
    Infinite<OctonionFloat32Member>,
    NaN<OctonionFloat32Member>,
    Rounding<Float32Member,OctonionFloat32Member>,
    RealConstants<OctonionFloat32Member>,
    ImaginaryConstants<OctonionFloat32Member>,
    QuaternionConstants<OctonionFloat32Member>,
    OctonionConstants<OctonionFloat32Member>,
    Random<OctonionFloat32Member>,
    Exponential<OctonionFloat32Member>,
    Trigonometric<OctonionFloat32Member>,
    Hyperbolic<OctonionFloat32Member>,
    Power<OctonionFloat32Member>,
    Roots<OctonionFloat32Member>,
    RealUnreal<OctonionFloat32Member,Float32Member>,
    Scale<OctonionFloat32Member, OctonionFloat32Member>,
    Tolerance<Float32Member,OctonionFloat32Member>
{
	private static final OctonionFloat32Member ZERO = new OctonionFloat32Member(0, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member ONE_THIRD = new OctonionFloat32Member(1.0f/3, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member ONE_HALF = new OctonionFloat32Member(0.5f, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member ONE = new OctonionFloat32Member(1, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member TWO = new OctonionFloat32Member(2, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member E = new OctonionFloat32Member((float)Math.E, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member PI = new OctonionFloat32Member((float)Math.PI, 0, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member GAMMA = new OctonionFloat32Member((float)0.57721566490153286060,0,0,0,0,0,0,0);
	private static final OctonionFloat32Member PHI = new OctonionFloat32Member((float)1.61803398874989484820,0,0,0,0,0,0,0);
	private static final OctonionFloat32Member I = new OctonionFloat32Member(0, 1, 0, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member J = new OctonionFloat32Member(0, 0, 1, 0, 0, 0, 0, 0);
	private static final OctonionFloat32Member K = new OctonionFloat32Member(0, 0, 0, 1, 0, 0, 0, 0);
	private static final OctonionFloat32Member L = new OctonionFloat32Member(0, 0, 0, 0, 1, 0, 0, 0);
	private static final OctonionFloat32Member I0 = new OctonionFloat32Member(0, 0, 0, 0, 0, 1, 0, 0);
	private static final OctonionFloat32Member J0 = new OctonionFloat32Member(0, 0, 0, 0, 0, 0, 1, 0);
	private static final OctonionFloat32Member K0 = new OctonionFloat32Member(0, 0, 0, 0, 0, 0, 0, 1);

	public OctonionFloat32Algebra() { }
	
	private final Procedure1<OctonionFloat32Member> UNITY =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> unity() {
		return UNITY;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> MUL =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b, OctonionFloat32Member c) {
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
			OctonionFloat32Member tmp = new OctonionFloat32Member(ZERO);
	
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
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat32Member,OctonionFloat32Member> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat32Member a, OctonionFloat32Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.OFLT, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,OctonionFloat32Member,OctonionFloat32Member> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat32Member> ZER =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(ZERO, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> NEG =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> ADD =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b, OctonionFloat32Member c) {
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
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> SUB =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b, OctonionFloat32Member c) {
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
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> subtract() {
		return SUB;
	}

	private Function2<Boolean,OctonionFloat32Member,OctonionFloat32Member> EQ =
			new Function2<Boolean, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public Boolean call(OctonionFloat32Member a, OctonionFloat32Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k() &&
					a.l() == b.l() && a.i0() == b.i0() && a.j0() == b.j0() && a.k0() == b.k0();
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32Member,OctonionFloat32Member> isEqual() {
		return EQ;
	}

	private Function2<Boolean,OctonionFloat32Member,OctonionFloat32Member> NEQ =
			new Function2<Boolean, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public Boolean call(OctonionFloat32Member a, OctonionFloat32Member b) {
			return !isEqual().call(a,b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32Member,OctonionFloat32Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public OctonionFloat32Member construct() {
		return new OctonionFloat32Member();
	}

	@Override
	public OctonionFloat32Member construct(OctonionFloat32Member other) {
		return new OctonionFloat32Member(other);
	}

	@Override
	public OctonionFloat32Member construct(String s) {
		return new OctonionFloat32Member(s);
	}

	private Procedure2<OctonionFloat32Member,OctonionFloat32Member> ASSIGN =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member from, OctonionFloat32Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> assign() {
		return ASSIGN;
	}

	private Procedure2<OctonionFloat32Member,OctonionFloat32Member> INV =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
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
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> invert() {
		return INV;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> DIVIDE =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b, OctonionFloat32Member c) {
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> divide() {
		return DIVIDE;
	}

	private final Procedure4<Round.Mode,Float32Member,OctonionFloat32Member,OctonionFloat32Member> ROUND =
			new Procedure4<Round.Mode, Float32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, OctonionFloat32Member a, OctonionFloat32Member b) {
			Float32Member tmp = new Float32Member();
			tmp.setV(a.r());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setI(tmp.v());
			tmp.setV(a.j());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setJ(tmp.v());
			tmp.setV(a.k());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setK(tmp.v());
			tmp.setV(a.l());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setL(tmp.v());
			tmp.setV(a.i0());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setI0(tmp.v());
			tmp.setV(a.j0());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setJ0(tmp.v());
			tmp.setV(a.k0());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setK0(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,OctonionFloat32Member,OctonionFloat32Member> round() {
		return ROUND;
	}

	private Function1<Boolean,OctonionFloat32Member> ISNAN =
			new Function1<Boolean, OctonionFloat32Member>()
	{
		@Override
		public Boolean call(OctonionFloat32Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k()) ||
					Double.isNaN(a.l()) || Double.isNaN(a.i0()) || Double.isNaN(a.j0()) || Double.isNaN(a.k0());
		}
	};

	@Override
	public Function1<Boolean,OctonionFloat32Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat32Member> NAN =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
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
	public Procedure1<OctonionFloat32Member> nan() {
		return NAN;
	}

	private Function1<Boolean,OctonionFloat32Member> ISINF =
			new Function1<Boolean, OctonionFloat32Member>()
	{
		@Override
		public Boolean call(OctonionFloat32Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) ||
					Double.isInfinite(a.k()) || Double.isInfinite(a.l()) || Double.isInfinite(a.i0()) ||
					Double.isInfinite(a.j0()) || Double.isInfinite(a.k0()) );
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat32Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat32Member> INF =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
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
	public Procedure1<OctonionFloat32Member> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat32Member,Float32Member> NORM =
			new Procedure2<OctonionFloat32Member, Float32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, Float32Member b) {
			b.setV( (float) norm(a) );
		}
	};

	@Override
	public Procedure2<OctonionFloat32Member,Float32Member> norm() {
		return NORM;
	}

	private double norm(OctonionFloat32Member a) {
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
	
	private double norm2(OctonionFloat32Member a) {
		double norm = norm(a);
		return norm * norm;
	}

	private Procedure2<OctonionFloat32Member,OctonionFloat32Member> CONJ =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
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
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> conjugate() {
		return CONJ;
	}

	private final Procedure1<OctonionFloat32Member> PI_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> PI() {
		return PI_;
	}

	private final Procedure1<OctonionFloat32Member> E_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> E() {
		return E_;
	}
	
	private final Procedure1<OctonionFloat32Member> GAMMA_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<OctonionFloat32Member> PHI_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> PHI() {
		return PHI_;
	}

	private final Procedure1<OctonionFloat32Member> I_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> I() {
		return I_;
	}

	private final Procedure1<OctonionFloat32Member> J_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> J() {
		return J_;
	}

	private final Procedure1<OctonionFloat32Member> K_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> K() {
		return K_;
	}

	private final Procedure1<OctonionFloat32Member> L_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(L, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> L() {
		return L_;
	}

	private final Procedure1<OctonionFloat32Member> I0_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(I0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> I0() {
		return I0_;
	}

	private final Procedure1<OctonionFloat32Member> J0_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(J0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> J0() {
		return J0_;
	}

	private final Procedure1<OctonionFloat32Member> K0_ =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
			assign().call(K0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32Member> K0() {
		return K0_;
	}

	private final Procedure1<OctonionFloat32Member> RAND =
			new Procedure1<OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a) {
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
	public Procedure1<OctonionFloat32Member> random() {
		return RAND;
	}

	private final Procedure2<OctonionFloat32Member,Float32Member> REAL =
			new Procedure2<OctonionFloat32Member, Float32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, Float32Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,Float32Member> real() {
		return REAL;
	}
	
	private Procedure2<OctonionFloat32Member,OctonionFloat32Member> UNREAL =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SINH =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat32Member negA = new OctonionFloat32Member();
			OctonionFloat32Member sum = new OctonionFloat32Member();
			OctonionFloat32Member tmp1 = new OctonionFloat32Member();
			OctonionFloat32Member tmp2 = new OctonionFloat32Member();
		
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sinh() {
		return SINH;
	}

	private Procedure2<OctonionFloat32Member,OctonionFloat32Member> COSH =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat32Member negA = new OctonionFloat32Member();
			OctonionFloat32Member sum = new OctonionFloat32Member();
			OctonionFloat32Member tmp1 = new OctonionFloat32Member();
			OctonionFloat32Member tmp2 = new OctonionFloat32Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> cosh() {
		return COSH;
    }

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> SINHANDCOSH =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member s, OctonionFloat32Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat32Member negA = new OctonionFloat32Member();
			OctonionFloat32Member sum = new OctonionFloat32Member();
			OctonionFloat32Member tmp1 = new OctonionFloat32Member();
			OctonionFloat32Member tmp2 = new OctonionFloat32Member();

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
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> TANH =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			OctonionFloat32Member s = new OctonionFloat32Member();
			OctonionFloat32Member c = new OctonionFloat32Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SIN =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			b.setR((float)(sin * cosh));
			b.setI((float)(ws * a.i()));
			b.setJ((float)(ws * a.j()));
			b.setK((float)(ws * a.k()));
			b.setL((float)(ws * a.l()));
			b.setI0((float)(ws * a.i0()));
			b.setJ0((float)(ws * a.j0()));
			b.setK0((float)(ws * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> COS =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double wc = -sin * sinhc_pi;
			b.setR((float)(cos * cosh));
			b.setI((float)(wc * a.i()));
			b.setJ((float)(wc * a.j()));
			b.setK((float)(wc * a.k()));
			b.setL((float)(wc * a.l()));
			b.setI0((float)(wc * a.i0()));
			b.setJ0((float)(wc * a.j0()));
			b.setK0((float)(wc * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> cos() {
		return COS;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> SINANDCOS =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member s, OctonionFloat32Member c) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = FastMath.cos(a.r());
			double sin = FastMath.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = FastMath.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR((float)(sin * cosh));
			s.setI((float)(ws * a.i()));
			s.setJ((float)(ws * a.j()));
			s.setK((float)(ws * a.k()));
			s.setL((float)(ws * a.l()));
			s.setI0((float)(ws * a.i0()));
			s.setJ0((float)(ws * a.j0()));
			s.setK0((float)(ws * a.k0()));
			c.setR((float)(cos * cosh));
			c.setI((float)(wc * a.i()));
			c.setJ((float)(wc * a.j()));
			c.setK((float)(wc * a.k()));
			c.setL((float)(wc * a.l()));
			c.setI0((float)(wc * a.i0()));
			c.setJ0((float)(wc * a.j0()));
			c.setK0((float)(wc * a.k0()));
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> TAN =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			OctonionFloat32Member sin = new OctonionFloat32Member();
			OctonionFloat32Member cos = new OctonionFloat32Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> tan() {
		return TAN;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> EXP =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			double u = FastMath.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinc().call(z, z2);
			double w = z2.v();
			b.setR((float)(u * FastMath.cos(z.v())));
			b.setI((float)(u * w * a.i()));
			b.setJ((float)(u * w * a.j()));
			b.setK((float)(u * w * a.k()));
			b.setL((float)(u * w * a.l()));
			b.setI0((float)(u * w * a.i0()));
			b.setJ0((float)(u * w * a.j0()));
			b.setK0((float)(u * w * a.k0()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> exp() {
		return EXP;
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	
	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> LOG =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			double factor;
			OctonionFloat32Member unreal = new OctonionFloat32Member();
			ComplexFloat32Member tmp = new ComplexFloat32Member();
			Float32Member norm = new Float32Member();
			assign().call(a, b); // this should be safe if two variables or one
			unreal().call(a, unreal);
			norm().call(unreal, norm);
			tmp.setR(a.r());
			tmp.setI(norm.v());
			G.CFLT.log().call(tmp, tmp);
			if ( norm.v() == 0.0 ) {
				factor = tmp.i();
			} else {
				factor = tmp.i() / norm.v();
			}

			multiplier(tmp.r(), factor, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> log() {
		return LOG;
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	private void multiplier(double r, double factor, OctonionFloat32Member result) {
		if ( Double.isNaN( factor ) || Double.isInfinite( factor ) ) {
			if ( result.i() == 0 && result.j() == 0 && result.k() == 0 ) {
				result.setR((float)r);
				result.setI((float)(result.i() * factor));
				result.setJ((float)(result.j() * factor));
				result.setK((float)(result.k() * factor));
				result.setL((float)(result.l() * factor));
				result.setI0((float)(result.i0() * factor));
				result.setJ0((float)(result.j0() * factor));
				result.setK0((float)(result.k0() * factor));
			}
			else {
				double signum = Math.signum(factor);
				result.setR((float)r);
				if (result.i() == 0) result.setI((float)(signum * result.i())); else result.setI((float)(factor * result.i()));
				if (result.j() == 0) result.setJ((float)(signum * result.j())); else result.setJ((float)(factor * result.j()));
				if (result.k() == 0) result.setK((float)(signum * result.k())); else result.setK((float)(factor * result.k()));
				if (result.l() == 0) result.setL((float)(signum * result.l())); else result.setL((float)(factor * result.l()));
				if (result.i0() == 0) result.setI0((float)(signum * result.i0())); else result.setI0((float)(factor * result.i0()));
				if (result.j0() == 0) result.setJ0((float)(signum * result.j0())); else result.setJ0((float)(factor * result.j0()));
				if (result.k0() == 0) result.setK0((float)(signum * result.k0())); else result.setK0((float)(factor * result.k0()));
			}
		}
		else {
			result.setR((float)r);
			result.setI((float)(result.i() * factor));
			result.setJ((float)(result.j() * factor));
			result.setK((float)(result.k() * factor));
			result.setL((float)(result.l() * factor));
			result.setI0((float)(result.i0() * factor));
			result.setJ0((float)(result.j0() * factor));
			result.setK0((float)(result.k0() * factor));
		}
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> POW =
			new Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b, OctonionFloat32Member c) {
			OctonionFloat32Member logA = new OctonionFloat32Member();
			OctonionFloat32Member bLogA = new OctonionFloat32Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32Member,OctonionFloat32Member> pow() {
		return POW;
	}	

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SINCH =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Sinch.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SINCHPI =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Sinchpi.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SINC =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Sinc.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SINCPI =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			Sincpi.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> SQRT =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<OctonionFloat32Member,OctonionFloat32Member> CBRT =
			new Procedure2<OctonionFloat32Member, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32Member,OctonionFloat32Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, OctonionFloat32Member> ISZERO =
			new Function1<Boolean, OctonionFloat32Member>()
	{
		@Override
		public Boolean call(OctonionFloat32Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0 && a.l() == 0 &&
					a.i0() == 0 && a.j0() == 0 && a.k0() == 0;
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32Member, OctonionFloat32Member> scale() {
		return MUL;
	}

	private final Function3<Boolean, Float32Member, OctonionFloat32Member, OctonionFloat32Member> WITHIN =
			new Function3<Boolean, Float32Member, OctonionFloat32Member, OctonionFloat32Member>()
	{
		
		@Override
		public Boolean call(Float32Member tol, OctonionFloat32Member a, OctonionFloat32Member b) {
			return OctonionNumberWithin.compute(G.FLT, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float32Member, OctonionFloat32Member, OctonionFloat32Member> within() {
		return WITHIN;
	}

}
