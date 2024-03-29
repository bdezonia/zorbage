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
package nom.bdezonia.zorbage.type.quaternion.float16;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.QuaternionType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat16Algebra
	implements
		SkewField<QuaternionFloat16Algebra,QuaternionFloat16Member>,
		RealConstants<QuaternionFloat16Member>,
		ImaginaryConstants<QuaternionFloat16Member>,
		QuaternionConstants<QuaternionFloat16Member>,
		Norm<QuaternionFloat16Member, Float16Member>,
		Conjugate<QuaternionFloat16Member>,
		Infinite<QuaternionFloat16Member>,
		NaN<QuaternionFloat16Member>,
		Rounding<Float16Member,QuaternionFloat16Member>,
		Random<QuaternionFloat16Member>,
		Exponential<QuaternionFloat16Member>,
		Trigonometric<QuaternionFloat16Member>,
		Hyperbolic<QuaternionFloat16Member>,
		Power<QuaternionFloat16Member>,
		Roots<QuaternionFloat16Member>,
		RealUnreal<QuaternionFloat16Member,Float16Member>,
		Scale<QuaternionFloat16Member,QuaternionFloat16Member>,
		ScaleByHighPrec<QuaternionFloat16Member>,
		ScaleByRational<QuaternionFloat16Member>,
		ScaleByDouble<QuaternionFloat16Member>,
		ScaleComponents<QuaternionFloat16Member, Float16Member>,
		Tolerance<Float16Member,QuaternionFloat16Member>,
		ScaleByOneHalf<QuaternionFloat16Member>,
		ScaleByTwo<QuaternionFloat16Member>,
		ConstructibleFromBytes<QuaternionFloat16Member>,
		ConstructibleFromShorts<QuaternionFloat16Member>,
		ConstructibleFromInts<QuaternionFloat16Member>,
		ConstructibleFromLongs<QuaternionFloat16Member>,
		ConstructibleFromFloats<QuaternionFloat16Member>,
		ConstructibleFromDoubles<QuaternionFloat16Member>,
		ConstructibleFromBigIntegers<QuaternionFloat16Member>,
		ConstructibleFromBigDecimals<QuaternionFloat16Member>,
		ExactlyConstructibleFromBytes<QuaternionFloat16Member>,
		ApproximateType,
		CompoundType,
		InfinityIncludedType,
		NanIncludedType,
		NumberType,
		QuaternionType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final QuaternionFloat16Member ZERO = new QuaternionFloat16Member(0,0,0,0);
	private static final QuaternionFloat16Member ONE_THIRD = new QuaternionFloat16Member((float)1.0/3,0,0,0);
	private static final QuaternionFloat16Member ONE_HALF = new QuaternionFloat16Member((float)0.5,0,0,0);
	private static final QuaternionFloat16Member ONE = new QuaternionFloat16Member(1,0,0,0);
	private static final QuaternionFloat16Member TWO = new QuaternionFloat16Member(2,0,0,0);
	private static final QuaternionFloat16Member E = new QuaternionFloat16Member((float)Math.E,0,0,0);
	private static final QuaternionFloat16Member PI = new QuaternionFloat16Member((float)Math.PI,0,0,0);
	private static final QuaternionFloat16Member GAMMA = new QuaternionFloat16Member((float)0.57721566490153286060,0,0,0);
	private static final QuaternionFloat16Member PHI = new QuaternionFloat16Member((float)1.61803398874989484820,0,0,0);
	private static final QuaternionFloat16Member I = new QuaternionFloat16Member(0,1,0,0);
	private static final QuaternionFloat16Member J = new QuaternionFloat16Member(0,0,1,0);
	private static final QuaternionFloat16Member K = new QuaternionFloat16Member(0,0,0,1);
	
	@Override
	public String typeDescription() {
		return "16-bit based quaternion number";
	}

	public QuaternionFloat16Algebra() { }

	@Override
	public QuaternionFloat16Member construct() {
		return new QuaternionFloat16Member();
	}

	@Override
	public QuaternionFloat16Member construct(QuaternionFloat16Member other) {
		return new QuaternionFloat16Member(other);
	}

	@Override
	public QuaternionFloat16Member construct(String s) {
		return new QuaternionFloat16Member(s);
	}
	
	private final Procedure1<QuaternionFloat16Member> UNITY =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> unity() {
		return UNITY;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> MUL =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
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
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat16Member,QuaternionFloat16Member> POWER =
			new Procedure3<Integer, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat16Member a, QuaternionFloat16Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.QHLF, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat16Member,QuaternionFloat16Member> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat16Member> ZER =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> NEG =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> ADD =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
			c.setJ( a.j() + b.j() );
			c.setK( a.k() + b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> SUB =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
			c.setJ( a.j() - b.j() );
			c.setK( a.k() - b.k() );
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat16Member,QuaternionFloat16Member> EQ =
			new Function2<Boolean, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k();
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16Member,QuaternionFloat16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat16Member,QuaternionFloat16Member> NEQ =
			new Function2<Boolean, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16Member,QuaternionFloat16Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> ASSIGN =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member from, QuaternionFloat16Member to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> INV =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			QuaternionFloat16Member conjA = new QuaternionFloat16Member();
			QuaternionFloat16Member scale = new QuaternionFloat16Member();
			Float16Member nval = new Float16Member();
			norm().call(a, nval);
			scale.setR( (float) (1.0 / (nval.v() * nval.v())) );
			conjugate().call(a, conjA);
			multiply().call(scale, conjA, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> invert() {
		return INV;
	}
	
	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> DIVIDE =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
			QuaternionFloat16Member tmp = new QuaternionFloat16Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> divide() {
		return DIVIDE;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> CONJ =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
			b.setJ( -a.j() );
			b.setK( -a.k() );
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat16Member,Float16Member> NORM =
			new Procedure2<QuaternionFloat16Member, Float16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, Float16Member b) {
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
	public Procedure2<QuaternionFloat16Member,Float16Member> norm() {
		return NORM;
	}

	private final Procedure1<QuaternionFloat16Member> PI_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> PI() {
		return PI_;
	}

	private final Procedure1<QuaternionFloat16Member> E_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> E() {
		return E_;
	}

	private final Procedure1<QuaternionFloat16Member> GAMMA_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<QuaternionFloat16Member> PHI_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> PHI() {
		return PHI_;
	}

	private final Procedure1<QuaternionFloat16Member> I_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> I() {
		return I_;
	}

	private final Procedure1<QuaternionFloat16Member> J_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> J() {
		return J_;
	}

	private final Procedure1<QuaternionFloat16Member> K_ =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> K() {
		return K_;
	}

	private final Procedure4<Round.Mode,Float16Member,QuaternionFloat16Member,QuaternionFloat16Member> ROUND =
			new Procedure4<Round.Mode, Float16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, QuaternionFloat16Member a, QuaternionFloat16Member b) {
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
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,QuaternionFloat16Member,QuaternionFloat16Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat16Member> ISNAN =
			new Function1<Boolean, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a) {
			return Double.isNaN(a.r()) || Double.isNaN(a.i()) || Double.isNaN(a.j()) || Double.isNaN(a.k());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat16Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<QuaternionFloat16Member> NAN =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			a.setR(Float.NaN);
			a.setI(Float.NaN);
			a.setJ(Float.NaN);
			a.setK(Float.NaN);
		}
	};

	@Override
	public Procedure1<QuaternionFloat16Member> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat16Member> ISINF =
			new Function1<Boolean, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a) {
			return !isNaN().call(a) && (
					Double.isInfinite(a.r()) || Double.isInfinite(a.i()) || Double.isInfinite(a.j()) || Double.isInfinite(a.k()));
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat16Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat16Member> INF =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			a.setR(Float.POSITIVE_INFINITY);
			a.setI(Float.POSITIVE_INFINITY);
			a.setJ(Float.POSITIVE_INFINITY);
			a.setK(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> EXP =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			QuaternionFloat16Member tmp = new QuaternionFloat16Member();
			double u = Math.exp(a.r());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinc().call(z, z2);
			double w = z2.v();
			b.setR((float) (u * Math.cos(z.v())));
			b.setI((float) (u * w * a.i()));
			b.setJ((float) (u * w * a.j()));
			b.setK((float) (u * w * a.k()));
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> LOG =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Float16Member norm = new Float16Member(); 
			Float16Member term = new Float16Member(); 
			Float16Member v1 = new Float16Member();
			Float16Member v2 = new Float16Member();
			Float16Member v3 = new Float16Member();
			norm().call(a, norm);
			Float16Member multiplier = new Float16Member(a.r() / norm.v());
			v1.setV(a.i() * multiplier.v());
			v2.setV(a.j() * multiplier.v());
			v3.setV(a.k() * multiplier.v());
			G.HLF.acos().call(multiplier, term);
			b.setR((float)Math.log(norm.v()));
			b.setI(v1.v() * term.v());
			b.setJ(v2.v() * term.v());
			b.setK(v3.v() * term.v());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> log() {
		return LOG;
	}

	private final Procedure1<QuaternionFloat16Member> RAND =
			new Procedure1<QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextFloat());
			a.setI(rng.nextFloat());
			a.setJ(rng.nextFloat());
			a.setK(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16Member> random() {
		return RAND;
	}

	private final Procedure2<QuaternionFloat16Member,Float16Member> REAL =
			new Procedure2<QuaternionFloat16Member, Float16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, Float16Member b) {
			b.setV(a.r());
		}
	};

	@Override
	public Procedure2<QuaternionFloat16Member,Float16Member> real() {
		return REAL;
	}
	
	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> UNREAL =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			assign().call(a, b);
			b.setR(0);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SINH =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat16Member negA = new QuaternionFloat16Member();
			QuaternionFloat16Member sum = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp1 = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp2 = new QuaternionFloat16Member();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sinh() {
		return SINH;
	}
	
	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> COSH =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat16Member negA = new QuaternionFloat16Member();
			QuaternionFloat16Member sum = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp1 = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp2 = new QuaternionFloat16Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> cosh() {
		return COSH;
    }

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> SINHANDCOSH =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member s, QuaternionFloat16Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat16Member negA = new QuaternionFloat16Member();
			QuaternionFloat16Member sum = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp1 = new QuaternionFloat16Member();
			QuaternionFloat16Member tmp2 = new QuaternionFloat16Member();
			
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
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> TANH =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			QuaternionFloat16Member s = new QuaternionFloat16Member();
			QuaternionFloat16Member c = new QuaternionFloat16Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SIN =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			QuaternionFloat16Member tmp = new QuaternionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
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
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> COS =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			QuaternionFloat16Member tmp = new QuaternionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
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
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> cos() {
		return COS;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> SINANDCOS =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member s, QuaternionFloat16Member c) {
			Float16Member z = new Float16Member();
			Float16Member z2 = new Float16Member();
			QuaternionFloat16Member tmp = new QuaternionFloat16Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HLF.sinch().call(z, z2);
			double cos = Math.cos(a.r());
			double sin = Math.sin(a.r());
			double sinhc_pi = z2.v();
			double cosh = Math.cosh(z.v());
			double ws = cos * sinhc_pi;
			double wc = -sin * sinhc_pi;
			s.setR((float) (sin * cosh));
			s.setI((float) (ws * a.i()));
			s.setJ((float) (ws * a.j()));
			s.setK((float) (ws * a.k()));
			c.setR((float) (cos * cosh));
			c.setI((float) (wc * a.i()));
			c.setJ((float) (wc * a.j()));
			c.setK((float) (wc * a.k()));
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> sinAndCos() {
		return SINANDCOS;
	}
	
	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> TAN =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			QuaternionFloat16Member sin = new QuaternionFloat16Member();
			QuaternionFloat16Member cos = new QuaternionFloat16Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> POW =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
			QuaternionFloat16Member logA = new QuaternionFloat16Member();
			QuaternionFloat16Member bLogA = new QuaternionFloat16Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16Member,QuaternionFloat16Member> pow() {
		return POW;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SINCH =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Sinch.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SINCHPI =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Sinchpi.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SINC =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Sinc.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SINCPI =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			Sincpi.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> SQRT =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> CBRT =
			new Procedure2<QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16Member,QuaternionFloat16Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, QuaternionFloat16Member> ISZERO =
			new Function1<Boolean, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a) {
			return a.r() == 0 && a.i() == 0 && a.j() == 0 && a.k() == 0;
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16Member, QuaternionFloat16Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat16Member, QuaternionFloat16Member> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
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
	public Procedure3<HighPrecisionMember, QuaternionFloat16Member, QuaternionFloat16Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat16Member, QuaternionFloat16Member> SBR =
			new Procedure3<RationalMember, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
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
	public Procedure3<RationalMember, QuaternionFloat16Member, QuaternionFloat16Member> scaleByRational() {
		return SBR;
	}
	
	private final Procedure3<Double, QuaternionFloat16Member, QuaternionFloat16Member> SBD =
			new Procedure3<Double, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(Double a, QuaternionFloat16Member b, QuaternionFloat16Member c) {
			c.setR((float)(a * b.r()));
			c.setI((float)(a * b.i()));
			c.setJ((float)(a * b.j()));
			c.setK((float)(a * b.k()));
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat16Member, QuaternionFloat16Member> scaleByDouble() {
		return SBD;
	}
	
	private final Procedure3<Float16Member, QuaternionFloat16Member, QuaternionFloat16Member> SC =
			new Procedure3<Float16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(Float16Member factor, QuaternionFloat16Member a, QuaternionFloat16Member b) {
			b.setR(factor.v() * a.r());
			b.setI(factor.v() * a.i());
			b.setJ(factor.v() * a.j());
			b.setK(factor.v() * a.k());
		}
	};

	@Override
	public Procedure3<Float16Member, QuaternionFloat16Member, QuaternionFloat16Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float16Member, QuaternionFloat16Member, QuaternionFloat16Member> WITHIN =
			new Function3<Boolean, Float16Member, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		
		@Override
		public Boolean call(Float16Member tol, QuaternionFloat16Member a, QuaternionFloat16Member b) {
			return QuaternionNumberWithin.compute(G.HLF, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float16Member, QuaternionFloat16Member, QuaternionFloat16Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member> STWO =
			new Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat16Member a, QuaternionFloat16Member b) {
			ScaleHelper.compute(G.QHLF, G.QHLF, new QuaternionFloat16Member(2, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member> SHALF =
			new Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat16Member a, QuaternionFloat16Member b) {
			ScaleHelper.compute(G.QHLF, G.QHLF, new QuaternionFloat16Member(0.5f, 0, 0, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat16Member, QuaternionFloat16Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, QuaternionFloat16Member> ISUNITY =
			new Function1<Boolean, QuaternionFloat16Member>()
	{
		@Override
		public Boolean call(QuaternionFloat16Member a) {
			return G.QHLF.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public QuaternionFloat16Member constructExactly(byte... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(BigDecimal... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(BigInteger... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(double... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(float... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(long... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(int... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(short... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public QuaternionFloat16Member construct(byte... vals) {
		QuaternionFloat16Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
