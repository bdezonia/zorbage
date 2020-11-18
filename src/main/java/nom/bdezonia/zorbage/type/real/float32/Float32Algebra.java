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
package nom.bdezonia.zorbage.type.real.float32;

import java.lang.Integer;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
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
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float32Algebra
	implements
		OrderedField<Float32Algebra,Float32Member,Float32Member>,
		Bounded<Float32Member>,
		Norm<Float32Member,Float32Member>,
		RealConstants<Float32Member>,
		Exponential<Float32Member>,
		Trigonometric<Float32Member>,
		InverseTrigonometric<Float32Member>,
		Hyperbolic<Float32Member>,
		InverseHyperbolic<Float32Member>,
		Infinite<Float32Member>,
		NaN<Float32Member>,
		Roots<Float32Member>,
		Power<Float32Member>,
		Rounding<Float32Member,Float32Member>,
		Random<Float32Member>,
		RealUnreal<Float32Member,Float32Member>,
		PredSucc<Float32Member>,
		MiscFloat<Float32Member>,
		ModularDivision<Float32Member>,
		Conjugate<Float32Member>,
		Scale<Float32Member,Float32Member>,
		ScaleByHighPrec<Float32Member>,
		ScaleByRational<Float32Member>,
		ScaleByDouble<Float32Member>,
		ScaleComponents<Float32Member, Float32Member>,
		Tolerance<Float32Member,Float32Member>,
		ScaleByOneHalf<Float32Member>,
		ScaleByTwo<Float32Member>
{
	private static final Float32Member PI = new Float32Member((float)Math.PI);
	private static final Float32Member E = new Float32Member((float)Math.E);
	private static final Float32Member GAMMA = new Float32Member((float)0.57721566490153286060);
	private static final Float32Member PHI = new Float32Member((float)1.61803398874989484820);
	
	public Float32Algebra() { }
	
	private final Function2<Boolean,Float32Member,Float32Member> EQ =
		new Function2<Boolean,Float32Member,Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float32Member,Float32Member> NEQ =
			new Function2<Boolean,Float32Member,Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() != b.v();
		}
	};
		
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float32Member construct() {
		return new Float32Member();
	}

	@Override
	public Float32Member construct(Float32Member other) {
		return new Float32Member(other);
	}

	@Override
	public Float32Member construct(String s) {
		return new Float32Member(s);
	}

	private final Procedure2<Float32Member,Float32Member> ASSIGN =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member from, Float32Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> assign() {
		return ASSIGN;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> ADD =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> add() {
		return ADD;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> SUB =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> subtract() {
		return SUB;
	}

	private final Procedure1<Float32Member> ZER =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float32Member> zero() {
		return ZER;
	}

	private final Procedure2<Float32Member,Float32Member> NEG =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( -a.v() );
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> negate() {
		return NEG;
	}

	private final Procedure1<Float32Member> UNITY =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<Float32Member> unity() {
		return UNITY;
	}

	private final Procedure2<Float32Member,Float32Member> INV =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( 1.0f / a.v() );
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> invert() {
		return INV;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> DIVIDE =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean,Float32Member,Float32Member> LESS =
			new Function2<Boolean, Float32Member, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,Float32Member,Float32Member> LE =
			new Function2<Boolean, Float32Member, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,Float32Member,Float32Member> GREAT =
			new Function2<Boolean, Float32Member, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,Float32Member,Float32Member> GE =
			new Function2<Boolean, Float32Member, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a, Float32Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float32Member,Float32Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,Float32Member,Float32Member> CMP =
			new Function2<java.lang.Integer, Float32Member, Float32Member>()
	{
		@Override
		public java.lang.Integer call(Float32Member a, Float32Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return  1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,Float32Member,Float32Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,Float32Member> SIG =
			new Function1<Integer, Float32Member>()
	{
		@Override
		public Integer call(Float32Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return  1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,Float32Member> signum() {
		return SIG;
	}
	
	private final Procedure1<Float32Member> MAXBOUND =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV( Float.MAX_VALUE );
		}
	};

	@Override
	public Procedure1<Float32Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<Float32Member> MINBOUND =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV( -Float.MAX_VALUE );
		}
	};

	@Override
	public Procedure1<Float32Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> MUL =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float32Member,Float32Member> POWER =
			new Procedure3<Integer, Float32Member, Float32Member>()
	{
		@Override
		public void call(Integer power, Float32Member a, Float32Member b) {
			if (power == 0 && a.v() == 0) {
				b.setV(Float.NaN);
			}
			else
				b.setV( (float) FastMath.pow(a.v(), power) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float32Member,Float32Member> power() {
		return POWER;
	}

	private final Procedure2<Float32Member,Float32Member> ABS =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( Math.abs(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> abs() {
		return ABS;
	}

	private final Procedure2<Float32Member,Float32Member> NORM =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			abs().call(a, b);
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> norm() {
		return NORM;
	}

	private final Procedure1<Float32Member> PI_ =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<Float32Member> PI() {
		return PI_;
	}

	private final Procedure1<Float32Member> E_ =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<Float32Member> E() {
		return E_;
	}
	
	private final Procedure1<Float32Member> GAMMA_ =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<Float32Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<Float32Member> PHI_ =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<Float32Member> PHI() {
		return PHI_;
	}

	private final Procedure2<Float32Member,Float32Member> EXP =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.exp(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> exp() {
		return EXP;
	}

	private final Procedure2<Float32Member,Float32Member> EXPM1 =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.expm1(a.v()) );
		}
	};
	
	public Procedure2<Float32Member,Float32Member> expm1() {
		return EXPM1;
	}

	private final Procedure2<Float32Member,Float32Member> LOG =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.log(a.v()) );
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> log() {
		return LOG;
	}

	private final Procedure2<Float32Member,Float32Member> LOG1P =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.log1p(a.v()) );
		}
	};

	public Procedure2<Float32Member,Float32Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<Float32Member,Float32Member> COS =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.cos(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> cos() {
		return COS;
	}

	private final Procedure2<Float32Member,Float32Member> SIN =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.sin(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> sin() {
		return SIN;
	}
	
	private final Procedure3<Float32Member,Float32Member,Float32Member> SINANDCOS =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member s, Float32Member c) {
			sin().call(a,s);
			cos().call(a,c);
		}
	};

	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float32Member,Float32Member> TAN =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.tan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> tan() {
		return TAN;
	}

	// ref: internet

	private final Procedure2<Float32Member,Float32Member> CSC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.sin(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> csc() {
		return CSC;
	}
	
	// ref: internet

	private final Procedure2<Float32Member,Float32Member> SEC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.cos(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> sec() {
		return SEC;
	}
	
	// ref: internet

	private final Procedure2<Float32Member,Float32Member> COT =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.tan(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> cot() {
		return COT;
	}
	
	private final Procedure2<Float32Member,Float32Member> COSH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.cosh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> cosh() {
		return COSH;
	}

	private final Procedure2<Float32Member,Float32Member> SINH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.sinh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> sinh() {
		return SINH;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> SINHANDCOSH =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member s, Float32Member c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float32Member,Float32Member> TANH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.tanh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> tanh() {
		return TANH;
	}

	// ref: internet

	private final Procedure2<Float32Member,Float32Member> CSCH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.sinh(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> csch() {
		return CSCH;
	}
	
	// ref: internet

	private final Procedure2<Float32Member,Float32Member> SECH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.cosh(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> sech() {
		return SECH;
	}

	// ref: internet

	private final Procedure2<Float32Member,Float32Member> COTH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (1.0 / FastMath.tanh(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> coth() {
		return COTH;
	}
	
	private final Procedure2<Float32Member,Float32Member> ACOS =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.acos(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> acos() {
		return ACOS;
	}

	private final Procedure2<Float32Member,Float32Member> ASIN =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.asin(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> asin() {
		return ASIN;
	}

	private final Procedure2<Float32Member,Float32Member> ATAN =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.atan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> atan() {
		return ATAN;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> ATAN2 =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( (float) FastMath.atan2(a.v(), b.v()) );
		}
	};
	
	public Procedure3<Float32Member,Float32Member,Float32Member> atan2() {
		return ATAN2;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ACSC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// acsc(x) = asin(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			asin().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> acsc() {
		return ACSC;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ASEC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// asec(x) = acos(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			acos().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> asec() {
		return ASEC;
	}
	
	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ACOT =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// asec(x) = acos(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			atan().call(tmp, b);
		}
	};
	
	// @Override
	public Procedure2<Float32Member,Float32Member> acot() {
		return ACOT;
	}
	
	// reference: Mathworld

	private final Procedure2<Float32Member,Float32Member> ACOSH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (Math.log(a.v() + Math.sqrt(a.v()*a.v() - 1))) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> acosh() {
		return ACOSH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ASINH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (Math.log(a.v() + Math.sqrt(a.v()*a.v() + 1))) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> asinh() {
		return ASINH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ATANH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) (0.5 * (-Math.log(1 - a.v()) + Math.log(1 + a.v()))) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> atanh() {
		return ATANH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ACSCH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// acsch(x) = asinh(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			asinh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> acsch() {
		return ACSCH;
	}

	// reference: Wolfram Alpha
	
	private final Procedure2<Float32Member,Float32Member> ASECH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// asech(x) = acosh(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			acosh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> asech() {
		return ASECH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float32Member,Float32Member> ACOTH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			// acoth(x) = atanh(1/x)
			Float32Member tmp = new Float32Member(1 / a.v());
			atanh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float32Member,Float32Member> acoth() {
		return ACOTH;
	}

	private final Function1<Boolean,Float32Member> ISNAN =
			new Function1<Boolean, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a) {
			return Float.isNaN(a.v());
		}
	};
	
	@Override
	public Function1<Boolean,Float32Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float32Member> NAN =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV(Float.NaN);
		}
	};
	
	@Override
	public Procedure1<Float32Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,Float32Member> ISINF =
			new Function1<Boolean, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a) {
			return Float.isInfinite(a.v());
		}
	};
	
	@Override
	public Function1<Boolean,Float32Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float32Member> INF =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<Float32Member> infinite() {
		return INF;
	}

	private final Procedure1<Float32Member> NINF =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			a.setV(Float.NEGATIVE_INFINITY);
		}
	};
	
	public Procedure1<Float32Member> negInfinite() {
		return NINF;
	}

	private final Procedure2<Float32Member,Float32Member> SQRT =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.sqrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<Float32Member,Float32Member> CBRT =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) FastMath.cbrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float32Member,Float32Member,Float32Member> ROUND =
			new Procedure4<Round.Mode, Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, Float32Member a, Float32Member b) {
			Round.compute(G.FLT, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,Float32Member,Float32Member> round() {
		return ROUND;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> MIN =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			Min.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> min() {
		return MIN;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> MAX =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			Max.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> max() {
		return MAX;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> POW =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			if (a.v() == 0 && b.v() == 0) {
				c.setV(Float.NaN);
			}
			else
				c.setV( (float) FastMath.pow(a.v(), b.v()) );
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> pow() {
		return POW;
	}
	
	private final Procedure3<Float32Member,Float32Member,Float32Member> IEEE =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV( (float) Math.IEEEremainder(a.v(), b.v()) );
		}
	};
	
	public Procedure3<Float32Member,Float32Member,Float32Member> IEEEremainder() {
		return IEEE;
	}

	private final Procedure2<Float32Member,Float32Member> LOG10 =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.log10(a.v()) );
		}
	};
	
	public Procedure2<Float32Member,Float32Member> log10() {
		return LOG10;
	}
	
	private final Procedure2<Float32Member,Float32Member> TODEG =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.toDegrees(a.v()) );
		}
	};
	
	public Procedure2<Float32Member,Float32Member> toDegrees() {
		return TODEG;
	}
	
	private final Procedure2<Float32Member,Float32Member> TORAD =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( (float) Math.toRadians(a.v()) );
		}
	};
	
	public Procedure2<Float32Member,Float32Member> toRadians() {
		return TORAD;
	}
	
	private final Procedure1<Float32Member> RAND =
			new Procedure1<Float32Member>()
	{
		@Override
		public void call(Float32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<Float32Member> random() {
		return RAND;
	}
	
	private final Procedure2<Float32Member,Float32Member> REAL =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV(a.v());
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> real() {
		return REAL;
	}
	
	private final Procedure2<Float32Member,Float32Member> UNREAL =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV(0);
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> unreal() {
		return UNREAL;
	}
	
	private final Procedure2<Float32Member,Float32Member> PRED =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( Math.nextAfter( a.v(), Double.NEGATIVE_INFINITY ) ) ; // Note that Double is intentional here
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> pred() {
		return PRED;
	}

	private final Procedure2<Float32Member,Float32Member> SUCC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV( Math.nextUp( a.v() ) );
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> succ() {
		return SUCC;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> CPS =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member c) {
			c.setV(Math.copySign(a.v(), b.v()));
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> copySign() {
		return CPS;
	}

	private final Function1<java.lang.Integer,Float32Member> GEXP =
			new Function1<java.lang.Integer,Float32Member>()
	{
		@Override
		public java.lang.Integer call(Float32Member a) {
			return Math.getExponent(a.v());
		}
	};
	
	@Override
	public Function1<java.lang.Integer,Float32Member> getExponent() {
		return GEXP;
	}

	private final Procedure3<java.lang.Integer,Float32Member,Float32Member> SCALB =
			new Procedure3<Integer, Float32Member, Float32Member>()
	{
		@Override
		public void call(Integer scaleFactor, Float32Member a, Float32Member b) {
			b.setV(Math.scalb(a.v(), scaleFactor));
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float32Member,Float32Member> scalb() {
		return SCALB;
	}

	private final Procedure2<Float32Member,Float32Member> ULP =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			b.setV(Math.ulp(a.v()));
		}
	};
	
	@Override
	public Procedure2<Float32Member,Float32Member> ulp() {
		return ULP;
	}

	// TODO: are the following two correctly divMod() or accidentally quotRem(). Investigate.
	// Later edit: I have proven by junit test that divMod() behavior matches ints. Will
	// need to implement quotRem() at some point.
	
	private final Procedure3<Float32Member,Float32Member,Float32Member> DIV =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member d) {
			double v = a.v() / b.v();
			if (v > 0)
				v = Math.floor(v);
			else
				v = Math.ceil(v);
			d.setV((float) v);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> div() {
		return DIV;
	}

	private final Procedure3<Float32Member,Float32Member,Float32Member> MOD =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member m) {
			m.setV(a.v() % b.v());
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32Member,Float32Member> mod() {
		return MOD;
	}

	private final Procedure4<Float32Member,Float32Member,Float32Member,Float32Member> DIVMOD =
			new Procedure4<Float32Member, Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b, Float32Member d, Float32Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<Float32Member,Float32Member,Float32Member,Float32Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<Float32Member,Float32Member> SINCH =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			Sinch.compute(G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> sinch() {
		return SINCH;
	}

	private final Procedure2<Float32Member,Float32Member> SINCHPI =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			Sinchpi.compute(G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float32Member,Float32Member> SINC =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			Sinc.compute(G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> sinc() {
		 return SINC;
	}

	private final Procedure2<Float32Member,Float32Member> SINCPI =
			new Procedure2<Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member a, Float32Member b) {
			Sincpi.compute(G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32Member,Float32Member> sincpi() {
		return SINCPI;
	}

	@Override
	public Procedure2<Float32Member,Float32Member> conjugate() {
		return ASSIGN;
	}

	private final Function1<Boolean, Float32Member> ISZERO =
			new Function1<Boolean, Float32Member>()
	{
		@Override
		public Boolean call(Float32Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, Float32Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<Float32Member, Float32Member, Float32Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, Float32Member, Float32Member> SBHP =
			new Procedure3<HighPrecisionMember, Float32Member, Float32Member>()
	{
		@Override
		public void call(HighPrecisionMember a, Float32Member b, Float32Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.v()));
			c.setV(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float32Member, Float32Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float32Member, Float32Member> SBR =
			new Procedure3<RationalMember, Float32Member, Float32Member>()
	{
		@Override
		public void call(RationalMember a, Float32Member b, Float32Member c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(b.v());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setV(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<RationalMember, Float32Member, Float32Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float32Member, Float32Member> SBD =
			new Procedure3<Double, Float32Member, Float32Member>()
	{
		@Override
		public void call(Double a, Float32Member b, Float32Member c) {
			c.setV((float)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, Float32Member, Float32Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float32Member, Float32Member, Float32Member> SC =
			new Procedure3<Float32Member, Float32Member, Float32Member>()
	{
		@Override
		public void call(Float32Member factor, Float32Member a, Float32Member b) {
			b.setV(factor.v() * a.v());
		}
	};

	@Override
	public Procedure3<Float32Member, Float32Member, Float32Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float32Member, Float32Member, Float32Member> WITHIN =
			new Function3<Boolean, Float32Member, Float32Member, Float32Member>()
	{
		
		@Override
		public Boolean call(Float32Member tol, Float32Member a, Float32Member b) {
			return NumberWithin.compute(G.FLT, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float32Member, Float32Member, Float32Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, Float32Member, Float32Member> STWO =
			new Procedure3<java.lang.Integer, Float32Member, Float32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float32Member a, Float32Member b) {
			ScaleHelper.compute(G.FLT, G.FLT, new Float32Member(2), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float32Member, Float32Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, Float32Member, Float32Member> SHALF =
			new Procedure3<java.lang.Integer, Float32Member, Float32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float32Member a, Float32Member b) {
			ScaleHelper.compute(G.FLT, G.FLT, new Float32Member(0.5f), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float32Member, Float32Member> scaleByOneHalf() {
		return SHALF;
	}

}