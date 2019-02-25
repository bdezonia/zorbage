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
package nom.bdezonia.zorbage.type.data.float16.real;

import java.util.concurrent.ThreadLocalRandom;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.ModularDivision;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float16Algebra
  implements
    OrderedField<Float16Algebra,Float16Member>,
    Bounded<Float16Member>,
    Norm<Float16Member,Float16Member>,
    Constants<Float16Member>,
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
    ModularDivision<Float16Member>
{
	private static final Float16Member PI = new Float16Member((float)Math.PI);
	private static final Float16Member E = new Float16Member((float)Math.E);

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
			a.setV( 0 );
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
			b.setV( 1.0 / a.v() );
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

	private Procedure1<Float16Member> MAXB =
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

	private Procedure1<Float16Member> MINB =
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
				b.setV(Double.NaN);
			}
			else
				b.setV( FastMath.pow(a.v(), power) );
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
			abs().call(a,b);
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
	
	private final Procedure2<Float16Member,Float16Member> EXP =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( FastMath.exp(a.v()) );
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
			b.setV( FastMath.expm1(a.v()) );
		}
	};
	
	public Procedure2<Float16Member,Float16Member> expm1() {
		return EXPM1;
	}

	private Procedure2<Float16Member,Float16Member> LOG =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( Math.log(a.v()) );
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
			b.setV( Math.log1p(a.v()) );
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
			b.setV( FastMath.cos(a.v()) );
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
			b.setV( FastMath.sin(a.v()) );
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

	private Procedure2<Float16Member,Float16Member> TAN =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( FastMath.tan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float16Member,Float16Member> tan() {
		return TAN;
	}

	// ref: internet

	private Procedure2<Float16Member,Float16Member> CSC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( 1.0 / FastMath.sin(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> csc() {
		return CSC;
	}
	
	// ref: internet

	private Procedure2<Float16Member,Float16Member> SEC =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( 1.0 / FastMath.cos(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> sec() {
		return SEC;
	}
	
	// ref: internet

	private Procedure2<Float16Member,Float16Member> COT =
			new Procedure2<Float16Member, Float16Member>()
	{	
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( 1.0 / FastMath.tan(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float16Member,Float16Member> cot() {
		return COT;
	}
	
	private Procedure2<Float16Member,Float16Member> COSH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( FastMath.cosh(a.v()) );
		}
	};

	@Override
	public Procedure2<Float16Member,Float16Member> cosh() {
		return COSH;
	}

	private Procedure2<Float16Member,Float16Member> SINH =
			new Procedure2<Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b) {
			b.setV( FastMath.sinh(a.v()) );
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
			b.setV( FastMath.tanh(a.v()) );
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
			b.setV( 1.0 / FastMath.sinh(a.v()) );
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
			b.setV( 1.0 / FastMath.cosh(a.v()) );
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
			b.setV( 1.0 / FastMath.tanh(a.v()) );
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
			b.setV( FastMath.acos(a.v()) );
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
			b.setV( FastMath.asin(a.v()) );
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
			b.setV( FastMath.atan(a.v()) );
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
			c.setV( FastMath.atan2(a.v(), b.v()) );
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
			b.setV( Math.log(x + Math.sqrt(x*x - 1)) );
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
			b.setV( Math.log(x + Math.sqrt(x*x + 1)) );
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
			b.setV( 0.5 * (-Math.log(1 - x) + Math.log(1 + x)) );
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
			a.setV(Double.NaN);
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
			a.setV(Double.POSITIVE_INFINITY);
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
			a.setV(Double.NEGATIVE_INFINITY);
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
			b.setV( Math.sqrt(a.v()) );
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
			b.setV( FastMath.cbrt(a.v()) );
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
				c.setV(Double.NaN);
			else
				c.setV( FastMath.pow(a.v(), b.v()) );
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
			b.setV( Math.log10(a.v()) );
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
			b.setV( Math.toDegrees(a.v()) );
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
			b.setV( Math.toRadians(a.v()) );
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
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextFloat());
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
			d.setV(v);
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
}