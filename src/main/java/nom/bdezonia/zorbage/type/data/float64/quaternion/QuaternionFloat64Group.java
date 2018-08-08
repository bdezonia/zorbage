/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.SkewField;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64Group
  implements
    SkewField<QuaternionFloat64Group,QuaternionFloat64Member>,
    Constants<QuaternionFloat64Member>,
    Norm<QuaternionFloat64Member, Float64Member>,
    Conjugate<QuaternionFloat64Member>,
    Infinite<QuaternionFloat64Member>,
    Rounding<Float64Member,QuaternionFloat64Member>,
    Random<QuaternionFloat64Member>,
    Exponential<QuaternionFloat64Member>,
    Trigonometric<QuaternionFloat64Member>,
    Hyperbolic<QuaternionFloat64Member>,
    Power<QuaternionFloat64Member>,
    Roots<QuaternionFloat64Member>,
    RealUnreal<QuaternionFloat64Member,Float64Member>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(0,0,0,0);
	private static final QuaternionFloat64Member ONE_THIRD = new QuaternionFloat64Member(1.0/3,0,0,0);
	private static final QuaternionFloat64Member ONE_HALF = new QuaternionFloat64Member(0.5,0,0,0);
	private static final QuaternionFloat64Member ONE = new QuaternionFloat64Member(1,0,0,0);
	private static final QuaternionFloat64Member TWO = new QuaternionFloat64Member(2,0,0,0);
	private static final QuaternionFloat64Member E = new QuaternionFloat64Member(Math.E,0,0,0);
	private static final QuaternionFloat64Member PI = new QuaternionFloat64Member(Math.PI,0,0,0);
	
	public QuaternionFloat64Group() { }
	
	private final Procedure1<QuaternionFloat64Member> UNITY =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> unity() {
		return UNITY;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> MUL =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			// for safety must use tmps
			double r = a.r()*b.r() - a.i()*b.i() - a.j()*b.j() - a.k()*b.k();
			double i = a.r()*b.i() + a.i()*b.r() + a.j()*b.k() - a.k()*b.j();
			double j = a.r()*b.j() - a.i()*b.k() + a.j()*b.r() + a.k()*b.i();
			double k = a.r()*b.k() + a.i()*b.j() - a.j()*b.i() + a.k()*b.r();
			c.setR( r );
			c.setI( i );
			c.setJ( j );
			c.setK( k );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat64Member,QuaternionFloat64Member> POWER =
			new Procedure3<Integer, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat64Member a, QuaternionFloat64Member b) {
			nom.bdezonia.zorbage.algorithm.Power.compute(G.QDBL, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat64Member,QuaternionFloat64Member> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat64Member> ZER =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(ZERO, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> NEG =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> ADD =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
			c.setJ( a.j() + b.j() );
			c.setK( a.k() + b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> SUB =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
			c.setJ( a.j() - b.j() );
			c.setK( a.k() - b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat64Member,QuaternionFloat64Member> EQ =
			new Function2<Boolean, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k();
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64Member,QuaternionFloat64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64Member,QuaternionFloat64Member> NEQ =
			new Function2<Boolean, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			return !isEqual().call(a,b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64Member,QuaternionFloat64Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionFloat64Member construct() {
		return new QuaternionFloat64Member();
	}

	@Override
	public QuaternionFloat64Member construct(QuaternionFloat64Member other) {
		return new QuaternionFloat64Member(other);
	}

	@Override
	public QuaternionFloat64Member construct(String s) {
		return new QuaternionFloat64Member(s);
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> ASSIGN =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member from, QuaternionFloat64Member to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> INV =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			QuaternionFloat64Member c = new QuaternionFloat64Member();
			QuaternionFloat64Member scale = new QuaternionFloat64Member();
			Float64Member nval = new Float64Member();
			norm().call(a, nval);
			scale.setR( (1.0 / (nval.v() * nval.v())) );
			conjugate().call(a, c);
			multiply().call(scale, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> invert() {
		return INV;
	}
	
	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> DIVIDE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			invert().call(b,tmp);
			multiply().call(a, tmp, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> divide() {
		return DIVIDE;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> CONJ =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
			b.setJ( -a.j() );
			b.setK( -a.k() );
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat64Member,Float64Member> NORM =
			new Procedure2<QuaternionFloat64Member, Float64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, Float64Member b) {
			// a hypot()-like implementation that avoids overflow
			double max = Math.max(Math.abs(a.r()), Math.abs(a.i()));
			max = Math.max(max, Math.abs(a.j()));
			max = Math.max(max, Math.abs(a.k()));
			double sum = (a.r()/max) * (a.r()/max);
			sum += (a.i()/max) * (a.i()/max);
			sum += (a.j()/max) * (a.j()/max);
			sum += (a.k()/max) * (a.k()/max);
			b.setV( max * Math.sqrt(sum) );
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,Float64Member> norm() {
		return NORM;
	}

	private final Procedure1<QuaternionFloat64Member> PI_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> PI() {
		return PI_;
	}

	private final Procedure1<QuaternionFloat64Member> E_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> E() {
		return E_;
	}

	private Procedure4<Round.Mode,Float64Member,QuaternionFloat64Member,QuaternionFloat64Member> ROUND =
			new Procedure4<Round.Mode, Float64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, QuaternionFloat64Member a, QuaternionFloat64Member b) {
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
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,QuaternionFloat64Member,QuaternionFloat64Member> round() {
		return ROUND;
	}

	private Function1<Boolean,QuaternionFloat64Member> NAN =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64Member> isNaN() {
		return NAN;
	}

	private Function1<Boolean,QuaternionFloat64Member> INF =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64Member> isInfinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> EXP =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Float64Member z = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			double u = Math.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z); // TODO or abs() whatever that is in boost
			double w = Float64Group.sinc_pi(z.v());
			b.setR(u * Math.cos(z.v()));
			b.setI(u * w * a.i());
			b.setJ(u * w * a.j());
			b.setK(u * w * a.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> LOG =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Float64Member norm = new Float64Member(); 
			Float64Member term = new Float64Member(); 
			Float64Member v1 = new Float64Member();
			Float64Member v2 = new Float64Member();
			Float64Member v3 = new Float64Member();
			norm().call(a, norm);
			Float64Member multiplier = new Float64Member(a.r() / norm.v());
			v1.setV(a.i() * multiplier.v());
			v2.setV(a.j() * multiplier.v());
			v3.setV(a.k() * multiplier.v());
			G.DBL.acos().call(multiplier, term);
			b.setR(Math.log(norm.v()));
			b.setI(v1.v() * term.v());
			b.setJ(v2.v() * term.v());
			b.setK(v3.v() * term.v());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> log() {
		return LOG;
	}

	private final Procedure1<QuaternionFloat64Member> RAND =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextDouble());
			a.setI(rng.nextDouble());
			a.setJ(rng.nextDouble());
			a.setK(rng.nextDouble());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> random() {
		return RAND;
	}

	private final Procedure2<QuaternionFloat64Member,Float64Member> REAL =
			new Procedure2<QuaternionFloat64Member, Float64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, Float64Member b) {
			b.setV(a.r());
		}
	};

	@Override
	public Procedure2<QuaternionFloat64Member,Float64Member> real() {
		return REAL;
	}
	
	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> UNREAL =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SINH =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			// TODO adapted from Complex64Group: might be wrong
			QuaternionFloat64Member negA = new QuaternionFloat64Member();
			QuaternionFloat64Member sum = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sinh() {
		return SINH;
	}
	
	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> COSH =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			// TODO adapted from Complex64Group: might be wrong
			QuaternionFloat64Member negA = new QuaternionFloat64Member();
			QuaternionFloat64Member sum = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> cosh() {
		return COSH;
    }

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> SINHANDCOSH =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member s, QuaternionFloat64Member c) {
			// TODO adapted from Complex64Group: might be wrong
			QuaternionFloat64Member negA = new QuaternionFloat64Member();
			QuaternionFloat64Member sum = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp1 = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
			
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
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> TANH =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			QuaternionFloat64Member s = new QuaternionFloat64Member();
			QuaternionFloat64Member c = new QuaternionFloat64Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SIN =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Float64Member z = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z); // TODO or abs() whatever that is in boost
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = Float64Group.sinhc_pi(z.v());
			double cosh = Math.cosh(z.v());
			double ws = cos * sinhc_pi;
			b.setR(sin * cosh);
			b.setI(ws * a.i());
			b.setJ(ws * a.j());
			b.setK(ws * a.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> COS =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Float64Member z = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z); // TODO or abs() whatever that is in boost
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = Float64Group.sinhc_pi(z.v());
			double cosh = Math.cosh(z.v());
			double wc = -sin * sinhc_pi;
			b.setR(cos * cosh);
			b.setI(wc * a.i());
			b.setJ(wc * a.j());
			b.setK(wc * a.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> cos() {
		return COS;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> SINANDCOS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member s, QuaternionFloat64Member c) {
			Float64Member z = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z); // TODO or abs() whatever that is in boost
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = Float64Group.sinhc_pi(z.v());
			double cosh = Math.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR(sin * cosh);
			s.setI(ws * a.i());
			s.setJ(ws * a.j());
			s.setK(ws * a.k());
			c.setR(cos * cosh);
			c.setI(wc * a.i());
			c.setJ(wc * a.j());
			c.setK(wc * a.k());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> sinAndCos() {
		return SINANDCOS;
	}
	
	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> TAN =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			QuaternionFloat64Member sin = new QuaternionFloat64Member();
			QuaternionFloat64Member cos = new QuaternionFloat64Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> POW =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			QuaternionFloat64Member logA = new QuaternionFloat64Member();
			QuaternionFloat64Member bLogA = new QuaternionFloat64Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64Member,QuaternionFloat64Member> pow() {
		return POW;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SINCH =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Sinch.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SINCHPI =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Sinchpi.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SINC =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Sinc.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SINCPI =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Sincpi.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> SQRT =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> CBRT =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> cbrt() {
		return CBRT;
	}
}
