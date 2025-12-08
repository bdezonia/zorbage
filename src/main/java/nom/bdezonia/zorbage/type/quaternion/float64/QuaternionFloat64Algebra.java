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
package nom.bdezonia.zorbage.type.quaternion.float64;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.QuaternionNumberWithin;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
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
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64Algebra
	implements
		SkewField<QuaternionFloat64Algebra,QuaternionFloat64Member>,
		RealConstants<QuaternionFloat64Member>,
		ImaginaryConstants<QuaternionFloat64Member>,
		QuaternionConstants<QuaternionFloat64Member>,
		Norm<QuaternionFloat64Member, Float64Member>,
		Conjugate<QuaternionFloat64Member>,
		Infinite<QuaternionFloat64Member>,
		NaN<QuaternionFloat64Member>,
		Rounding<Float64Member,QuaternionFloat64Member>,
		Random<QuaternionFloat64Member>,
		Exponential<QuaternionFloat64Member>,
		Trigonometric<QuaternionFloat64Member>,
		Hyperbolic<QuaternionFloat64Member>,
		Power<QuaternionFloat64Member>,
		Roots<QuaternionFloat64Member>,
		RealUnreal<QuaternionFloat64Member,Float64Member>,
		Scale<QuaternionFloat64Member,QuaternionFloat64Member>,
		ScaleByHighPrec<QuaternionFloat64Member>,
		ScaleByRational<QuaternionFloat64Member>,
		ScaleByDouble<QuaternionFloat64Member>,
		ScaleComponents<QuaternionFloat64Member, Float64Member>,
		Tolerance<Float64Member,QuaternionFloat64Member>,
		ScaleByOneHalf<QuaternionFloat64Member>,
		ScaleByTwo<QuaternionFloat64Member>,
		ConstructibleFromBytes<QuaternionFloat64Member>,
		ConstructibleFromShorts<QuaternionFloat64Member>,
		ConstructibleFromInts<QuaternionFloat64Member>,
		ConstructibleFromLongs<QuaternionFloat64Member>,
		ConstructibleFromFloats<QuaternionFloat64Member>,
		ConstructibleFromDoubles<QuaternionFloat64Member>,
		ConstructibleFromBigIntegers<QuaternionFloat64Member>,
		ConstructibleFromBigDecimals<QuaternionFloat64Member>,
		ExactlyConstructibleFromBytes<QuaternionFloat64Member>,
		ExactlyConstructibleFromShorts<QuaternionFloat64Member>,
		ExactlyConstructibleFromInts<QuaternionFloat64Member>,
		ExactlyConstructibleFromFloats<QuaternionFloat64Member>,
		ExactlyConstructibleFromDoubles<QuaternionFloat64Member>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(0,0,0,0);
	private static final QuaternionFloat64Member ONE_THIRD = new QuaternionFloat64Member(1.0/3,0,0,0);
	private static final QuaternionFloat64Member ONE_HALF = new QuaternionFloat64Member(0.5,0,0,0);
	private static final QuaternionFloat64Member ONE = new QuaternionFloat64Member(1,0,0,0);
	private static final QuaternionFloat64Member TWO = new QuaternionFloat64Member(2,0,0,0);
	private static final QuaternionFloat64Member E = new QuaternionFloat64Member(Math.E,0,0,0);
	private static final QuaternionFloat64Member PI = new QuaternionFloat64Member(Math.PI,0,0,0);
	private static final QuaternionFloat64Member GAMMA = new QuaternionFloat64Member(0.57721566490153286060,0,0,0);
	private static final QuaternionFloat64Member PHI = new QuaternionFloat64Member(1.61803398874989484820,0,0,0);
	private static final QuaternionFloat64Member I = new QuaternionFloat64Member(0,1,0,0);
	private static final QuaternionFloat64Member J = new QuaternionFloat64Member(0,0,1,0);
	private static final QuaternionFloat64Member K = new QuaternionFloat64Member(0,0,0,1);
	
	@Override
	public String typeDescription() {
		return "64-bit based quaternion number";
	}

	public QuaternionFloat64Algebra() { }

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
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.QDBL, power, a, b);
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
			a.primitiveInit();
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
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64Member,QuaternionFloat64Member> isNotEqual() {
		return NEQ;
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
			QuaternionFloat64Member conjA = new QuaternionFloat64Member();
			QuaternionFloat64Member scale = new QuaternionFloat64Member();
			Float64Member nval = new Float64Member();
			norm().call(a, nval);
			scale.setR( (1.0 / (nval.v() * nval.v())) );
			conjugate().call(a, conjA);
			multiply().call(scale, conjA, b);
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
			invert().call(b, tmp);
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
			if (max == 0) {
				b.setV(0);
			}
			else {
				double sum = (a.r()/max) * (a.r()/max);
				sum += (a.i()/max) * (a.i()/max);
				sum += (a.j()/max) * (a.j()/max);
				sum += (a.k()/max) * (a.k()/max);
				b.setV( max * Math.sqrt(sum) );
			}
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

	private final Procedure1<QuaternionFloat64Member> GAMMA_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<QuaternionFloat64Member> PHI_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> PHI() {
		return PHI_;
	}

	private final Procedure1<QuaternionFloat64Member> I_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> I() {
		return I_;
	}

	private final Procedure1<QuaternionFloat64Member> J_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> J() {
		return J_;
	}

	private final Procedure1<QuaternionFloat64Member> K_ =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> K() {
		return K_;
	}

	private final Procedure4<Round.Mode,Float64Member,QuaternionFloat64Member,QuaternionFloat64Member> ROUND =
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

	private final Function1<Boolean,QuaternionFloat64Member> ISNAN =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<QuaternionFloat64Member> NAN =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			a.setR(Double.NaN);
			a.setI(Double.NaN);
			a.setJ(Double.NaN);
			a.setK(Double.NaN);
		}
	};

	@Override
	public Procedure1<QuaternionFloat64Member> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat64Member> ISINF =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) || Double.isInfinite(a.k()));
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat64Member> INF =
			new Procedure1<QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a) {
			a.setR(Double.POSITIVE_INFINITY);
			a.setI(Double.POSITIVE_INFINITY);
			a.setJ(Double.POSITIVE_INFINITY);
			a.setK(Double.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64Member> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat64Member,QuaternionFloat64Member> EXP =
			new Procedure2<QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64Member b) {
			Float64Member z = new Float64Member();
			Float64Member z2 = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			double u = Math.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinc().call(z, z2);
			double w = z2.v();
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
			// TODO adapted from Complex64Algebra: might be wrong
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
			// TODO adapted from Complex64Algebra: might be wrong
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
			// TODO adapted from Complex64Algebra: might be wrong
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
			Float64Member z2 = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
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
			Float64Member z2 = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
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
			Float64Member z2 = new Float64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.DBL.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
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

	private final Function1<Boolean, QuaternionFloat64Member> ISZERO =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0;
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64Member, QuaternionFloat64Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat64Member, QuaternionFloat64Member> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j()));
			c.setJ(tmp.doubleValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k()));
			c.setK(tmp.doubleValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat64Member, QuaternionFloat64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat64Member, QuaternionFloat64Member> SBR =
			new Procedure3<RationalMember, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
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
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat64Member, QuaternionFloat64Member> scaleByRational() {
		return SBR;
	}
	
	private final Procedure3<Double, QuaternionFloat64Member, QuaternionFloat64Member> SBD =
			new Procedure3<Double, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(Double a, QuaternionFloat64Member b, QuaternionFloat64Member c) {
			c.setR(a * b.r());
			c.setI(a * b.i());
			c.setJ(a * b.j());
			c.setK(a * b.k());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat64Member, QuaternionFloat64Member> scaleByDouble() {
		return SBD;
	}
	
	private final Procedure3<Float64Member, QuaternionFloat64Member, QuaternionFloat64Member> SC =
			new Procedure3<Float64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(Float64Member factor, QuaternionFloat64Member a, QuaternionFloat64Member b) {
			b.setR(factor.v() * a.r());
			b.setI(factor.v() * a.i());
			b.setJ(factor.v() * a.j());
			b.setK(factor.v() * a.k());
		}
	};

	@Override
	public Procedure3<Float64Member, QuaternionFloat64Member, QuaternionFloat64Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float64Member, QuaternionFloat64Member, QuaternionFloat64Member> WITHIN =
			new Function3<Boolean, Float64Member, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		
		@Override
		public Boolean call(Float64Member tol, QuaternionFloat64Member a, QuaternionFloat64Member b) {
			return QuaternionNumberWithin.compute(G.DBL, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float64Member, QuaternionFloat64Member, QuaternionFloat64Member> within() {
		return WITHIN;
	}
	
	private final Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member> STWO =
			new Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat64Member a, QuaternionFloat64Member b) {
			ScaleHelper.compute(G.QDBL, G.QDBL, new QuaternionFloat64Member(2, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member> SHALF =
			new Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat64Member a, QuaternionFloat64Member b) {
			ScaleHelper.compute(G.QDBL, G.QDBL, new QuaternionFloat64Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat64Member, QuaternionFloat64Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, QuaternionFloat64Member> ISUNITY =
			new Function1<Boolean, QuaternionFloat64Member>()
	{
		@Override
		public Boolean call(QuaternionFloat64Member a) {
			return G.QDBL.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public QuaternionFloat64Member constructExactly(byte... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member constructExactly(short... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member constructExactly(int... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member constructExactly(float... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member constructExactly(double... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(BigDecimal... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(BigInteger... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(double... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(float... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(long... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(int... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(short... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public QuaternionFloat64Member construct(byte... vals) {
		QuaternionFloat64Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
