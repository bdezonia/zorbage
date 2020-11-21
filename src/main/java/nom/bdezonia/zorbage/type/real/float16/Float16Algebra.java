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
package nom.bdezonia.zorbage.type.real.float16;

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
public class Float16Algebra
	implements
		OrderedField<Float16Algebra,Float16Member,Float16Member>,
		Bounded<Float16Member>,
		Norm<Float16Member,Float16Member>,
		RealConstants<Float16Member>,
		Exponential<Float16Member>,
		Trigonometric<Float16Member>,
		InverseTrigonometric<Float16Member>,
		Hyperbolic<Float16Member>,
		InverseHyperbolic<Float16Member>,
		Infinite<Float16Member>,
		NaN<Float16Member>,
		Roots<Float16Member>,
		Power<Float16Member>,
		Rounding<Float16Member,Float16Member>,
		Random<Float16Member>,
		RealUnreal<Float16Member,Float16Member>,
		ModularDivision<Float16Member>,
		Conjugate<Float16Member>,
		Scale<Float16Member,Float16Member>,
		ScaleByHighPrec<Float16Member>,
		ScaleByRational<Float16Member>,
		ScaleByDouble<Float16Member>,
		ScaleComponents<Float16Member, Float16Member>,
		Tolerance<Float16Member,Float16Member>,
		ScaleByOneHalf<Float16Member>,
		ScaleByTwo<Float16Member>,
		MiscFloat<Float16Member>
{
	private static final Float16Member PI = new Float16Member((float)Math.PI);
	private static final Float16Member E = new Float16Member((float)Math.E);
	private static final Float16Member GAMMA = new Float16Member((float)0.57721566490153286060);
	private static final Float16Member PHI = new Float16Member((float)1.61803398874989484820);

	public Float16Algebra() { }
	
	private final Function2<Boolean,Float16Member,Float16Member> EQ =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float16Member,Float16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16Member,Float16Member> NEQ =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float16Member,Float16Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float16Member construct() {
		return new Float16Member();
	}

	@Override
	public Float16Member construct(Float16Member other) {
		return new Float16Member(other);
	}

	@Override
	public Float16Member construct(String s) {
		return new Float16Member(s);
	}

	private final Procedure2<Float16Member,Float16Member> ASSIGN =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member from, Float16Member to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> assign() {
		return ASSIGN;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> ADD =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> add() {
		return ADD;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> SUB =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> subtract() {
		return SUB;
	}

	private final Procedure1<Float16Member> ZER =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<Float16Member> zero() {
		return ZER;
	}

	private final Procedure2<Float16Member,Float16Member> NEG =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( -a.v() );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> negate() {
		return NEG;
	}

	private final Procedure1<Float16Member> UN =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<Float16Member> unity() {
		return UN;
	}

	private final Procedure2<Float16Member,Float16Member> INV =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( 1.0f / a.v() );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> invert() {
		return INV;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> DIVIDE =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean,Float16Member,Float16Member> LS =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() < b.v();
		}
	};

	@Override
	public Function2<Boolean,Float16Member,Float16Member> isLess() {
		return LS;
	}

	private final Function2<Boolean,Float16Member,Float16Member> LE =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() <= b.v();
		}
	};

	@Override
	public Function2<Boolean,Float16Member,Float16Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,Float16Member,Float16Member> GR =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() > b.v();
		}
	};

	@Override
	public Function2<Boolean,Float16Member,Float16Member> isGreater() {
		return GR;
	}

	private final Function2<Boolean,Float16Member,Float16Member> GRE =
			new Function2<Boolean, Float16Member, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a, Float16Member b) {
			return a.v() >= b.v();
		}
	};

	@Override
	public Function2<Boolean,Float16Member,Float16Member> isGreaterEqual() {
		return GRE;
	}

	private final Function2<java.lang.Integer,Float16Member,Float16Member> CMP =
			new Function2<Integer, Float16Member, Float16Member>()
	{
		@Override
		public Integer call(Float16Member a, Float16Member b) {
			float x = a.v();
			float y = b.v();
			if (x < y) return -1;
			if (x > y) return  1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,Float16Member,Float16Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,Float16Member> SIG =
			new Function1<Integer, Float16Member>()
	{
		@Override
		public Integer call(Float16Member a) {
			float x = a.v();
			if (x < 0) return -1;
			if (x > 0) return  1;
			return 0;
		}
	};

	@Override
	public Function1<java.lang.Integer,Float16Member> signum() {
		return SIG;
	}

	private final Procedure1<Float16Member> MAXB =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV( Float16Util.convertHFloatToFloat((short) 0b0111101111111111) );
		}
	};

	@Override
	public Procedure1<Float16Member> maxBound() {
		return MAXB;
	}

	private final Procedure1<Float16Member> MINB =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV( Float16Util.convertHFloatToFloat((short) 0b1111101111111111) );
		}
	};

	@Override
	public Procedure1<Float16Member> minBound() {
		return MINB;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> MUL =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( a.v() * b.v() );
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float16Member,Float16Member> POWER =
			new Procedure3<Integer, Float16Member, Float16Member>()
	{
		@Override
		public void call(Integer power, Float16Member a, Float16Member b) {
			if (power == 0 && a.v() == 0) {
				b.setV(Float.NaN);
			}
			else
				b.setV( (float) FastMath.pow(a.v(), power) );
		}
	};

	@Override
	public Procedure3<java.lang.Integer,Float16Member,Float16Member> power() {
		return POWER;
	}

	private final Procedure2<Float16Member,Float16Member> ABS =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( Math.abs(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> abs() {
		return ABS;
	}

	private final Procedure2<Float16Member,Float16Member> NRM =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			abs().call(a, b);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> norm() {
		return NRM;
	}

	private final Procedure1<Float16Member> _PI =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			assign().call(PI, a);
		}
	};

	@Override
	public Procedure1<Float16Member> PI() {
		return _PI;
	}

	private final Procedure1<Float16Member> _E =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			assign().call(E, a);
		}
	};

	@Override
	public Procedure1<Float16Member> E() {
		return _E;
	}
	
	private final Procedure1<Float16Member> _GAMMA =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			assign().call(GAMMA, a);
		}
	};

	@Override
	public Procedure1<Float16Member> GAMMA() {
		return _GAMMA;
	}

	private final Procedure1<Float16Member> _PHI =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			assign().call(PHI, a);
		}
	};

	@Override
	public Procedure1<Float16Member> PHI() {
		return _PHI;
	}

	private final Procedure2<Float16Member,Float16Member> EXP =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.exp(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> exp() {
		return EXP;
	}

	private final Procedure2<Float16Member,Float16Member> EXPM1 =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.expm1(a.v()) );
		}
	};
	
	public Procedure2<Float16Member,Float16Member> expm1() {
		return EXPM1;
	}

	private final Procedure2<Float16Member,Float16Member> LOG =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.log(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> log() {
		return LOG;
	}

	private final Procedure2<Float16Member,Float16Member> LOG1P =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.log1p(a.v()) );
		}
	};

	public Procedure2<Float16Member,Float16Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<Float16Member,Float16Member> COS =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.cos(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> cos() {
		return COS;
	}

	private final Procedure2<Float16Member,Float16Member> SIN =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.sin(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sin() {
		return SIN;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> SINCOS =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member s, Float16Member c) {
			sin().call(a,s);
			cos().call(a,c);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> sinAndCos() {
		return SINCOS;
	}

	private final Procedure2<Float16Member,Float16Member> TAN =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.tan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> tan() {
		return TAN;
	}

	// ref: internet

	private final Procedure2<Float16Member,Float16Member> CSC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (1.0 / FastMath.sin(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> csc() {
		return CSC;
	}
	
	// ref: internet

	private final Procedure2<Float16Member,Float16Member> SEC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float)(1.0 / FastMath.cos(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> sec() {
		return SEC;
	}
	
	// ref: internet

	private final Procedure2<Float16Member,Float16Member> COT =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (1.0 / FastMath.tan(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> cot() {
		return COT;
	}
	
	private final Procedure2<Float16Member,Float16Member> COSH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (FastMath.cosh(a.v())) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> cosh() {
		return COSH;
	}

	private final Procedure2<Float16Member,Float16Member> SINH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (FastMath.sinh(a.v())) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sinh() {
		return SINH;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> SINHCOSH =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member s, Float16Member c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<Float16Member,Float16Member> TANH =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (FastMath.tanh(a.v())) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> tanh() {
		return TANH;
	}

	// ref: internet

	private final Procedure2<Float16Member,Float16Member> CSCH =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (1.0 / FastMath.sinh(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> csch() {
		return CSCH;
	}
	
	// ref: internet

	private final Procedure2<Float16Member,Float16Member> SECH =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (1.0 / FastMath.cosh(a.v())) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> sech() {
		return SECH;
	}

	// ref: internet

	private final Procedure2<Float16Member,Float16Member> COTH =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) (1.0 / FastMath.tanh(a.v())) );
		}
	};

	//@Override
	public Procedure2<Float16Member,Float16Member> coth(Float16Member a, Float16Member b) {
		return COTH;
	}
	
	private final Procedure2<Float16Member,Float16Member> ACOS =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.acos(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> acos() {
		return ACOS;
	}

	private final Procedure2<Float16Member,Float16Member> ASIN =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.asin(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> asin() {
		return ASIN;
	}

	private final Procedure2<Float16Member,Float16Member> ATAN =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.atan(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> atan() {
		return ATAN;
	}
	
	private final Procedure3<Float16Member,Float16Member,Float16Member> ATAN2 =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( (float) FastMath.atan2(a.v(), b.v()) );
		}
	};
	
	public Procedure3<Float16Member,Float16Member,Float16Member> atan2() {
		return ATAN2;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ACSC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			// acsc(x) = asin(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			asin().call(tmp, b);
		}
	};

	//@Override
	public Procedure2<Float16Member,Float16Member> acsc() {
		return ACSC;
	}

	// reference: Wolfram Alpha
	
	private final Procedure2<Float16Member,Float16Member> ASEC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			// asec(x) = acos(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			acos().call(tmp, b);
		}
	};

	//@Override
	public Procedure2<Float16Member,Float16Member> asec() {
		return ASEC;
	}
	
	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ACOT =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			// acot(x) = atan(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			atan().call(tmp, b);
		}
	};
	
	// @Override
	public Procedure2<Float16Member,Float16Member> acot() {
		return ACOT;
	}
	
	// reference: Mathworld

	private final Procedure2<Float16Member,Float16Member> ACOSH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			float x = a.v();
			b.setV( (float) Math.log(x + Math.sqrt(x*x - 1)) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> acosh() {
		return ACOSH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ASINH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			float x = a.v();
			b.setV( (float) Math.log(x + Math.sqrt(x*x + 1)) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> asinh() {
		return ASINH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ATANH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			float x = a.v();
			b.setV( (float) (0.5 * (-Math.log(1 - x) + Math.log(1 + x))) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> atanh() {
		return ATANH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ACSCH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			// acsch(x) = asinh(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			asinh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> acsch() {
		return ACSCH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float16Member,Float16Member> ASECH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			// asech(x) = acosh(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			acosh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> asech() {
		return ASECH;
	}

	// reference: Wolfram Alpha
	
	private final Procedure2<Float16Member,Float16Member> ACOTH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			// acoth(x) = atanh(1/x)
			Float16Member tmp = new Float16Member(1 / a.v());
			atanh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> acoth() {
		return ACOTH;
	}

	private final Function1<Boolean,Float16Member> ISNAN =
			new Function1<Boolean, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a) {
			return Float.isNaN(a.v());
		}
	};

	@Override
	public Function1<Boolean,Float16Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float16Member> NAN =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV(Float.NaN);
		}
	};
	
	@Override
	public Procedure1<Float16Member> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,Float16Member> ISINF =
			new Function1<Boolean, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a) {
			return Float.isInfinite(a.v());
		}
	};

	@Override
	public Function1<Boolean,Float16Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16Member> INF =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV(Float.POSITIVE_INFINITY);
		}
	};
			
	@Override
	public Procedure1<Float16Member> infinite() {
		return INF;
	}
	
	private final Procedure1<Float16Member> NINF =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			a.setV(Float.NEGATIVE_INFINITY);
		}
	};
			
	public Procedure1<Float16Member> negInfinite() {
		return NINF;
	}
	
	private final Procedure2<Float16Member,Float16Member> SQRT =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.sqrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<Float16Member,Float16Member> CBRT =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) FastMath.cbrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float16Member,Float16Member,Float16Member> ROUND =
			new Procedure4<Round.Mode, Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16Member a, Float16Member b) {
			Round.compute(G.HLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Round.Mode,Float16Member,Float16Member,Float16Member> round() {
		return ROUND;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> MIN =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			Min.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> min() {
		return MIN;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> MAX =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			Max.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> max() {
		return MAX;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> POW =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			if (a.v() == 0 && b.v() == 0)
				c.setV(Float.NaN);
			else
				c.setV( (float) FastMath.pow(a.v(), b.v()) );
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> pow() {
		return POW;
	}

	private final Procedure2<Float16Member,Float16Member> LOG10 =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.log10(a.v()) );
		}
	};

	public Procedure2<Float16Member,Float16Member> log10() {
		return LOG10;
	}
	
	private final Procedure2<Float16Member,Float16Member> DEG =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.toDegrees(a.v()) );
		}
	};

	public Procedure2<Float16Member,Float16Member> toDegrees() {
		return DEG;
	}
	
	private final Procedure2<Float16Member,Float16Member> RAD =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( (float) Math.toRadians(a.v()) );
		}
	};

	public Procedure2<Float16Member,Float16Member> toRadians() {
		return RAD;
	}
	
	private final Procedure1<Float16Member> RAND =
			new Procedure1<Float16Member>()
	{
		@Override
		public void call(Float16Member a) {
			
			// This code created by BDZ after studying http://prng.di.unimi.it/
			// Output numbers tested for uniformity by generating 500K numbers,
			// testing that all the values were in [0,1) and viewing the
			// histogram statistics using ImageJ.
			
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			int v = rng.nextInt(65536);
			float r = (v >>> 5) * 0x1.0p-11f;
			a.setV(r);
			
			// Note that if you look at the last bit in the float representation it is only 1 about one fourth
			// of the time. I thought this meant the this routine spit out odd numbers at a 1 to 3 ratio to
			// even numbers. So I then did the same test with Java's Math.nextFloat() results and got the same
			// 1 to 3 ratio. I must be misunderstanding how odd numbers are represented in bits. Or else both
			// my code and Java's code are inaccurate. They both rely on the same random number generators so
			// that is a possibility.
		}
	};
	
	@Override
	public Procedure1<Float16Member> random() {
		return RAND;
	}
	
	private final Procedure2<Float16Member,Float16Member> REAL =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV(a.v());
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> real() {
		return REAL;
	}
	
	private final Procedure2<Float16Member,Float16Member> UNREAL =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV(0);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> unreal() {
		return UNREAL;
	}


	private final Procedure3<Float16Member,Float16Member,Float16Member> DIV =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member d) {
			double v = a.v() / b.v();
			if (v > 0)
				v = Math.floor(v);
			else
				v = Math.ceil(v);
			d.setV((float) v);
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> div() {
		return DIV;
	}

	private final Procedure3<Float16Member,Float16Member,Float16Member> MOD =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member m) {
			m.setV(a.v() % b.v());
		}
	};

	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> mod() {
		return MOD;
	}

	private final Procedure4<Float16Member,Float16Member,Float16Member,Float16Member> DIVMOD =
			new Procedure4<Float16Member, Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member d, Float16Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};

	@Override
	public Procedure4<Float16Member,Float16Member,Float16Member,Float16Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<Float16Member,Float16Member> SINCH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			Sinch.compute(G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sinch() {
		return SINCH;
	}

	private final Procedure2<Float16Member,Float16Member> SINCHPI =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			Sinchpi.compute(G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float16Member,Float16Member> SINC =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			Sinc.compute(G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sinc() {
		return SINC;
	}

	private final Procedure2<Float16Member,Float16Member> SINCPI =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			Sincpi.compute(G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, Float16Member> ISZERO =
			new Function1<Boolean, Float16Member>()
	{
		@Override
		public Boolean call(Float16Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, Float16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<Float16Member, Float16Member, Float16Member> scale() {
		return MUL;
	}

	@Override
	public Procedure2<Float16Member, Float16Member> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember, Float16Member, Float16Member> SBHP =
			new Procedure3<HighPrecisionMember, Float16Member, Float16Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float16Member a, Float16Member b) {
			BigDecimal tmp;
			tmp = factor.v().multiply(BigDecimal.valueOf(a.v()));
			b.setV(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float16Member, Float16Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float16Member, Float16Member> SBR =
			new Procedure3<RationalMember, Float16Member, Float16Member>()
	{
		@Override
		public void call(RationalMember factor, Float16Member a, Float16Member b) {
			BigDecimal n = new BigDecimal(factor.n());
			BigDecimal d = new BigDecimal(factor.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(a.v());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			b.setV(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<RationalMember, Float16Member, Float16Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float16Member, Float16Member> SBD =
			new Procedure3<Double, Float16Member, Float16Member>()
	{
		@Override
		public void call(Double factor, Float16Member a, Float16Member b) {
			b.setV((float)(factor * a.v()));
		}
	};

	@Override
	public Procedure3<Double, Float16Member, Float16Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float16Member, Float16Member, Float16Member> SC =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member factor, Float16Member a, Float16Member b) {
			b.setV(factor.v() * a.v());
		}
	};

	@Override
	public Procedure3<Float16Member, Float16Member, Float16Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float16Member, Float16Member, Float16Member> WITHIN =
			new Function3<Boolean, Float16Member, Float16Member, Float16Member>()
	{
		
		@Override
		public Boolean call(Float16Member tol, Float16Member a, Float16Member b) {
			return NumberWithin.compute(G.HLF, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16Member, Float16Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, Float16Member, Float16Member> STWO =
			new Procedure3<java.lang.Integer, Float16Member, Float16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float16Member a, Float16Member b) {
			ScaleHelper.compute(G.HLF, G.HLF, new Float16Member(2), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float16Member, Float16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, Float16Member, Float16Member> SHALF =
			new Procedure3<java.lang.Integer, Float16Member, Float16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float16Member a, Float16Member b) {
			ScaleHelper.compute(G.HLF, G.HLF, new Float16Member(0.5f), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float16Member, Float16Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<Float16Member, Float16Member, Float16Member> CSGN =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member magnitude, Float16Member sign, Float16Member a) {
			a.setV( Math.abs(magnitude.v()) * Math.signum(sign.v()) );
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16Member, Float16Member> copySign() {
		return CSGN;
	}

	private final Function1<Integer, Float16Member> GETEXP =
			new Function1<Integer, Float16Member>()
	{
		@Override
		public Integer call(Float16Member a) {
			return Math.getExponent(a.v());
		}
	};

	@Override
	public Function1<Integer, Float16Member> getExponent() {
		return GETEXP;
	}

	private final Procedure3<Integer, Float16Member, Float16Member> SCALB =
			new Procedure3<Integer, Float16Member, Float16Member>()
	{
		@Override
		public void call(Integer scaleFactor, Float16Member a, Float16Member b) {
			b.setV( (float) (Math.scalb(a.v(), scaleFactor)) );
		}
	};

	@Override
	public Procedure3<Integer, Float16Member, Float16Member> scalb() {
		return SCALB;
	}

	private final Procedure2<Float16Member, Float16Member> ULP =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			short neigh;
			float ulp;
			if (isNaN().call(a)) {
				nan().call(b);
				return;
			}
			else if (isInfinite().call(a)) {
				infinite().call(b);
				return;
			}
			else if (a.v() < 0) {
				// neigh is next closer to 0
				neigh = Float16Util.next(a.encV());
				ulp = Float16Util.convertHFloatToFloat(neigh) - a.v();
			}
			else if (a.v() > 0) {
				// neigh is next closer to 0
				neigh = Float16Util.prev(a.encV());
				ulp = a.v() - Float16Util.convertHFloatToFloat(neigh);
			}
			else {
				// a.v() == 0
				// neigh is next away from zero towards +Inf
				neigh = Float16Util.next(a.encV());
				ulp = Float16Util.convertHFloatToFloat(neigh);
			}
			b.setV(ulp);
		}
	};

	@Override
	public Procedure2<Float16Member, Float16Member> ulp() {
		return ULP;
	}
}
