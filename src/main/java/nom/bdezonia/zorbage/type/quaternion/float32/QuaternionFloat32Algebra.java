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
package nom.bdezonia.zorbage.type.quaternion.float32;

import java.lang.Integer;
import java.math.BigDecimal;
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
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat32Algebra
	implements
		SkewField<QuaternionFloat32Algebra,QuaternionFloat32Member>,
		RealConstants<QuaternionFloat32Member>,
		ImaginaryConstants<QuaternionFloat32Member>,
		QuaternionConstants<QuaternionFloat32Member>,
		Norm<QuaternionFloat32Member, Float32Member>,
		Conjugate<QuaternionFloat32Member>,
		Infinite<QuaternionFloat32Member>,
		NaN<QuaternionFloat32Member>,
		Rounding<Float32Member,QuaternionFloat32Member>,
		Random<QuaternionFloat32Member>,
		Exponential<QuaternionFloat32Member>,
		Trigonometric<QuaternionFloat32Member>,
		Hyperbolic<QuaternionFloat32Member>,
		Power<QuaternionFloat32Member>,
		Roots<QuaternionFloat32Member>,
		RealUnreal<QuaternionFloat32Member,Float32Member>,
		Scale<QuaternionFloat32Member,QuaternionFloat32Member>,
		ScaleByHighPrec<QuaternionFloat32Member>,
		ScaleByRational<QuaternionFloat32Member>,
		ScaleByDouble<QuaternionFloat32Member>,
		ScaleComponents<QuaternionFloat32Member, Float32Member>,
		Tolerance<Float32Member,QuaternionFloat32Member>,
		ScaleByOneHalf<QuaternionFloat32Member>,
		ScaleByTwo<QuaternionFloat32Member>,
		ConstructibleFromFloats<QuaternionFloat32Member>,
		ExactlyConstructibleFromBytes<QuaternionFloat32Member>,
		ExactlyConstructibleFromShorts<QuaternionFloat32Member>,
		ExactlyConstructibleFromFloats<QuaternionFloat32Member>
{
	private static final QuaternionFloat32Member ZERO = new QuaternionFloat32Member(0,0,0,0);
	private static final QuaternionFloat32Member ONE_THIRD = new QuaternionFloat32Member(1.0f/3,0,0,0);
	private static final QuaternionFloat32Member ONE_HALF = new QuaternionFloat32Member(0.5f,0,0,0);
	private static final QuaternionFloat32Member ONE = new QuaternionFloat32Member(1,0,0,0);
	private static final QuaternionFloat32Member TWO = new QuaternionFloat32Member(2,0,0,0);
	private static final QuaternionFloat32Member E = new QuaternionFloat32Member((float)Math.E,0,0,0);
	private static final QuaternionFloat32Member PI = new QuaternionFloat32Member((float)Math.PI,0,0,0);
	private static final QuaternionFloat32Member GAMMA = new QuaternionFloat32Member((float)0.57721566490153286060,0,0,0);
	private static final QuaternionFloat32Member PHI = new QuaternionFloat32Member((float)1.61803398874989484820,0,0,0);
	private static final QuaternionFloat32Member I = new QuaternionFloat32Member(0,1,0,0);
	private static final QuaternionFloat32Member J = new QuaternionFloat32Member(0,0,1,0);
	private static final QuaternionFloat32Member K = new QuaternionFloat32Member(0,0,0,1);
	
	@Override
	public String typeDescription() {
		return "32-bit based quaternion number";
	}

	public QuaternionFloat32Algebra() { }

	@Override
	public QuaternionFloat32Member construct() {
		return new QuaternionFloat32Member();
	}

	@Override
	public QuaternionFloat32Member construct(QuaternionFloat32Member other) {
		return new QuaternionFloat32Member(other);
	}

	@Override
	public QuaternionFloat32Member construct(String s) {
		return new QuaternionFloat32Member(s);
	}

	@Override
	public QuaternionFloat32Member construct(float... vals) {
		return new QuaternionFloat32Member(vals);
	}
	
	private final Procedure1<QuaternionFloat32Member> UNITY =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> unity() {
		return UNITY;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> MUL =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			// for safety must use tmps
			double r = a.r()*b.r() - a.i()*b.i() - a.j()*b.j() - a.k()*b.k();
			double i = a.r()*b.i() + a.i()*b.r() + a.j()*b.k() - a.k()*b.j();
			double j = a.r()*b.j() - a.i()*b.k() + a.j()*b.r() + a.k()*b.i();
			double k = a.r()*b.k() + a.i()*b.j() - a.j()*b.i() + a.k()*b.r();
			c.setR( (float) r );
			c.setI( (float) i );
			c.setJ( (float) j );
			c.setK( (float) k );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat32Member,QuaternionFloat32Member> POWER =
			new Procedure3<Integer, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat32Member a, QuaternionFloat32Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.QFLT, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat32Member,QuaternionFloat32Member> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat32Member> ZER =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> NEG =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> ADD =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
			c.setJ( a.j() + b.j() );
			c.setK( a.k() + b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> SUB =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
			c.setJ( a.j() - b.j() );
			c.setK( a.k() - b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat32Member,QuaternionFloat32Member> EQ =
			new Function2<Boolean, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k();
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32Member,QuaternionFloat32Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat32Member,QuaternionFloat32Member> NEQ =
			new Function2<Boolean, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32Member,QuaternionFloat32Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> ASSIGN =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member from, QuaternionFloat32Member to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> INV =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			QuaternionFloat32Member conjA = new QuaternionFloat32Member();
			QuaternionFloat32Member scale = new QuaternionFloat32Member();
			Float32Member nval = new Float32Member();
			norm().call(a, nval);
			scale.setR( (float) (1.0 / (nval.v() * nval.v())) );
			conjugate().call(a, conjA);
			multiply().call(scale, conjA, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> invert() {
		return INV;
	}
	
	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> DIVIDE =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			QuaternionFloat32Member tmp = new QuaternionFloat32Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> divide() {
		return DIVIDE;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> CONJ =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
			b.setJ( -a.j() );
			b.setK( -a.k() );
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat32Member,Float32Member> NORM =
			new Procedure2<QuaternionFloat32Member, Float32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, Float32Member b) {
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
				b.setV( (float) (max * Math.sqrt(sum)) );
			}
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,Float32Member> norm() {
		return NORM;
	}

	private final Procedure1<QuaternionFloat32Member> PI_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> PI() {
		return PI_;
	}

	private final Procedure1<QuaternionFloat32Member> E_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> E() {
		return E_;
	}

	private final Procedure1<QuaternionFloat32Member> GAMMA_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<QuaternionFloat32Member> PHI_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> PHI() {
		return PHI_;
	}

	private final Procedure1<QuaternionFloat32Member> I_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> I() {
		return I_;
	}

	private final Procedure1<QuaternionFloat32Member> J_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> J() {
		return J_;
	}

	private final Procedure1<QuaternionFloat32Member> K_ =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> K() {
		return K_;
	}

	private final Procedure4<Round.Mode,Float32Member,QuaternionFloat32Member,QuaternionFloat32Member> ROUND =
			new Procedure4<Round.Mode, Float32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, QuaternionFloat32Member a, QuaternionFloat32Member b) {
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
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,QuaternionFloat32Member,QuaternionFloat32Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat32Member> ISNAN =
			new Function1<Boolean, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat32Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<QuaternionFloat32Member> NAN =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			a.setR(Float.NaN);
			a.setI(Float.NaN);
			a.setJ(Float.NaN);
			a.setK(Float.NaN);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32Member> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat32Member> ISINF =
			new Function1<Boolean, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) || Double.isInfinite(a.k()));
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat32Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat32Member> INF =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			a.setR(Float.POSITIVE_INFINITY);
			a.setI(Float.POSITIVE_INFINITY);
			a.setJ(Float.POSITIVE_INFINITY);
			a.setK(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> EXP =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			QuaternionFloat32Member tmp = new QuaternionFloat32Member();
			double u = Math.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinc().call(z, z2);
			double w = z2.v();
			b.setR((float) (u * Math.cos(z.v())));
			b.setI((float) (u * w * a.i()));
			b.setJ((float) (u * w * a.j()));
			b.setK((float) (u * w * a.k()));
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> LOG =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Float32Member norm = new Float32Member(); 
			Float32Member term = new Float32Member(); 
			Float32Member v1 = new Float32Member();
			Float32Member v2 = new Float32Member();
			Float32Member v3 = new Float32Member();
			norm().call(a, norm);
			Float32Member multiplier = new Float32Member(a.r() / norm.v());
			v1.setV(a.i() * multiplier.v());
			v2.setV(a.j() * multiplier.v());
			v3.setV(a.k() * multiplier.v());
			G.FLT.acos().call(multiplier, term);
			b.setR((float) Math.log(norm.v()));
			b.setI(v1.v() * term.v());
			b.setJ(v2.v() * term.v());
			b.setK(v3.v() * term.v());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> log() {
		return LOG;
	}

	private final Procedure1<QuaternionFloat32Member> RAND =
			new Procedure1<QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextFloat());
			a.setI(rng.nextFloat());
			a.setJ(rng.nextFloat());
			a.setK(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32Member> random() {
		return RAND;
	}

	private final Procedure2<QuaternionFloat32Member,Float32Member> REAL =
			new Procedure2<QuaternionFloat32Member, Float32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, Float32Member b) {
			b.setV(a.r());
		}
	};

	@Override
	public Procedure2<QuaternionFloat32Member,Float32Member> real() {
		return REAL;
	}
	
	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> UNREAL =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SINH =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat32Member negA = new QuaternionFloat32Member();
			QuaternionFloat32Member sum = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp1 = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp2 = new QuaternionFloat32Member();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sinh() {
		return SINH;
	}
	
	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> COSH =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat32Member negA = new QuaternionFloat32Member();
			QuaternionFloat32Member sum = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp1 = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp2 = new QuaternionFloat32Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> cosh() {
		return COSH;
    }

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> SINHANDCOSH =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member s, QuaternionFloat32Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat32Member negA = new QuaternionFloat32Member();
			QuaternionFloat32Member sum = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp1 = new QuaternionFloat32Member();
			QuaternionFloat32Member tmp2 = new QuaternionFloat32Member();
			
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
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> TANH =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			QuaternionFloat32Member s = new QuaternionFloat32Member();
			QuaternionFloat32Member c = new QuaternionFloat32Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SIN =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			QuaternionFloat32Member tmp = new QuaternionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = Math.cosh(z.v());
			double ws = cos * sinhc_pi;
			b.setR((float) (sin * cosh));
			b.setI((float) (ws * a.i()));
			b.setJ((float) (ws * a.j()));
			b.setK((float) (ws * a.k()));
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> COS =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			QuaternionFloat32Member tmp = new QuaternionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = Math.cosh(z.v());
			double wc = -sin * sinhc_pi;
			b.setR((float) (cos * cosh));
			b.setI((float) (wc * a.i()));
			b.setJ((float) (wc * a.j()));
			b.setK((float) (wc * a.k()));
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> cos() {
		return COS;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> SINANDCOS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member s, QuaternionFloat32Member c) {
			Float32Member z = new Float32Member();
			Float32Member z2 = new Float32Member();
			QuaternionFloat32Member tmp = new QuaternionFloat32Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.FLT.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = Math.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR((float) (sin * cosh));
			s.setI((float) ws * a.i());
			s.setJ((float) ws * a.j());
			s.setK((float) ws * a.k());
			c.setR((float) (cos * cosh));
			c.setI((float) wc * a.i());
			c.setJ((float) wc * a.j());
			c.setK((float) wc * a.k());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> sinAndCos() {
		return SINANDCOS;
	}
	
	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> TAN =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			QuaternionFloat32Member sin = new QuaternionFloat32Member();
			QuaternionFloat32Member cos = new QuaternionFloat32Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> POW =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			QuaternionFloat32Member logA = new QuaternionFloat32Member();
			QuaternionFloat32Member bLogA = new QuaternionFloat32Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32Member,QuaternionFloat32Member> pow() {
		return POW;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SINCH =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Sinch.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SINCHPI =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Sinchpi.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SINC =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Sinc.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SINCPI =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			Sincpi.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> SQRT =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> CBRT =
			new Procedure2<QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32Member,QuaternionFloat32Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, QuaternionFloat32Member> ISZERO =
			new Function1<Boolean, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0;
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32Member, QuaternionFloat32Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat32Member, QuaternionFloat32Member> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.j()));
			c.setJ(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.k()));
			c.setK(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat32Member, QuaternionFloat32Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat32Member, QuaternionFloat32Member> SBR =
			new Procedure3<RationalMember, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
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
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat32Member, QuaternionFloat32Member> scaleByRational() {
		return SBR;
	}
	
	private final Procedure3<Double, QuaternionFloat32Member, QuaternionFloat32Member> SBD =
			new Procedure3<Double, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(Double a, QuaternionFloat32Member b, QuaternionFloat32Member c) {
			c.setR((float)(a * b.r()));
			c.setI((float)(a * b.i()));
			c.setJ((float)(a * b.j()));
			c.setK((float)(a * b.k()));
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat32Member, QuaternionFloat32Member> scaleByDouble() {
		return SBD;
	}
	
	private final Procedure3<Float32Member, QuaternionFloat32Member, QuaternionFloat32Member> SC =
			new Procedure3<Float32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(Float32Member factor, QuaternionFloat32Member a, QuaternionFloat32Member b) {
			b.setR(factor.v() * a.r());
			b.setI(factor.v() * a.i());
			b.setJ(factor.v() * a.j());
			b.setK(factor.v() * a.k());
		}
	};

	@Override
	public Procedure3<Float32Member, QuaternionFloat32Member, QuaternionFloat32Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float32Member, QuaternionFloat32Member, QuaternionFloat32Member> WITHIN =
			new Function3<Boolean, Float32Member, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		
		@Override
		public Boolean call(Float32Member tol, QuaternionFloat32Member a, QuaternionFloat32Member b) {
			return QuaternionNumberWithin.compute(G.FLT, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float32Member, QuaternionFloat32Member, QuaternionFloat32Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member> STWO =
			new Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat32Member a, QuaternionFloat32Member b) {
			ScaleHelper.compute(G.QFLT, G.QFLT, new QuaternionFloat32Member(2, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member> SHALF =
			new Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat32Member a, QuaternionFloat32Member b) {
			ScaleHelper.compute(G.QFLT, G.QFLT, new QuaternionFloat32Member(0.5f, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat32Member, QuaternionFloat32Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, QuaternionFloat32Member> ISUNITY =
			new Function1<Boolean, QuaternionFloat32Member>()
	{
		@Override
		public Boolean call(QuaternionFloat32Member a) {
			return G.QFLT.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32Member> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionFloat32Member constructExactly(byte... vals) {
		QuaternionFloat32Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat32Member constructExactly(short... vals) {
		QuaternionFloat32Member v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat32Member constructExactly(float... vals) {
		QuaternionFloat32Member v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}
}
