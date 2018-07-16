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
package nom.bdezonia.zorbage.type.data.float16.real;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.MiscFloat;
import nom.bdezonia.zorbage.type.algebra.ModularDivision;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.PredSucc;
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
public class Float16Group
  implements
    OrderedField<Float16Group,Float16Member>,
    Bounded<Float16Member>,
    Norm<Float16Member,Float16Member>,
    Constants<Float16Member>,
    Exponential<Float16Member>,
    Trigonometric<Float16Member>,
    InverseTrigonometric<Float16Member>,
    Hyperbolic<Float16Member>,
    InverseHyperbolic<Float16Member>,
    Infinite<Float16Member>,
    Roots<Float16Member>,
    Power<Float16Member>,
    Rounding<Float16Member,Float16Member>,
    Random<Float16Member>,
    RealUnreal<Float16Member,Float16Member>,
    PredSucc<Float16Member>,
    MiscFloat<Float16Member>,
    ModularDivision<Float16Member>
{
	private static final Float16Member PI = new Float16Member(Math.PI);
	private static final Float16Member E = new Float16Member(Math.E);

	public Float16Group() { }
	
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
			to.setV( from.v() );
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

	private final Procedure3<Float16Member,Float16Member,Float16Member> DIV =
			new Procedure3<Float16Member, Float16Member, Float16Member>()
	{
		@Override
		public void call(Float16Member a, Float16Member b, Float16Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16Member,Float16Member> divide() {
		return DIV;
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
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return  1;
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
			if (a.v() < 0) return -1;
			if (a.v() > 0) return  1;
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
			a.setV( Float16Member.toDouble((short) 0b0111101111111111));
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
			a.setV( Float16Member.toDouble((short) 0b1111101111111111));
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
			else {
				b.setV( Math.pow(a.v(), power) );
			}
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
			b.setV( Math.exp(a.v()) );
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
			b.setV( Math.expm1(a.v()) );
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
			b.setV( Math.cos(a.v()) );
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
			b.setV( Math.sin(a.v()) );
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
			
			/*
			
			// This might be too inaccurate to be worth optimization
			
			double arg = a.v() % TWO_PI;  // this might be faster than some while (arg < 0 || arg >= TWO_PI) loops
			double cosine = Math.cos(arg);
			double sine = Math.sqrt(1 - cosine * cosine);
			if ( arg < 0) {
				if (arg < -Math.PI)
					sine = -sine;
			}+
			else { // arg >= 0
				if (arg > Math.PI)
					sine = -sine;
			}
			s.setV( sine );
			c.setV( cosine );
			*/
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
			b.setV( Math.tan(a.v()) );
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
			b.setV( 1.0 / Math.sin(a.v()) );
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
			b.setV( 1.0 / Math.cos(a.v()) );
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
			b.setV( 1.0 / Math.tan(a.v()) );
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
			b.setV( Math.cosh(a.v()) );
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
			b.setV( Math.sinh(a.v()) );
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
			// TODO - is there a speedup?
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
			b.setV( Math.tanh(a.v()) );
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
			b.setV( 1.0 / Math.sinh(a.v()) );
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
			b.setV( 1.0 / Math.cosh(a.v()) );
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
			b.setV( 1.0 / Math.tanh(a.v()) );
		}
	};

	//@Override
	public Procedure2<Float16Member,Float16Member> coth(Float16Member a, Float16Member b) {
		return COTH;
	}
	
	@Override
	public void acos(Float16Member a, Float16Member b) {
		b.setV( Math.acos(a.v()) );
	}

	@Override
	public void asin(Float16Member a, Float16Member b) {
		b.setV( Math.asin(a.v()) );
	}

	@Override
	public void atan(Float16Member a, Float16Member b) {
		b.setV( Math.atan(a.v()) );
	}
	
	public void atan2(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.atan2(a.v(), b.v()) );
	}

	// reference: Wolfram Alpha

	//@Override
	public void acsc(Float16Member a, Float16Member b) {
		// acsc(x) = asin(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		asin(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asec(Float16Member a, Float16Member b) {
		// asec(x) = acos(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		acos(tmp, b);
	}
	
	// reference: Wolfram Alpha

	// @Override
	public void acot(Float16Member a, Float16Member b) {
		// acot(x) = atan(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		atan(tmp, b);
	}
	
	// reference: Mathworld

	@Override
	public void acosh(Float16Member a, Float16Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() - 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void asinh(Float16Member a, Float16Member b) {
		b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() + 1)) );
	}

	// reference: Wolfram Alpha

	@Override
	public void atanh(Float16Member a, Float16Member b) {
		b.setV( 0.5 * (-Math.log(1 - a.v()) + Math.log(1 + a.v())) );
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acsch(Float16Member a, Float16Member b) {
		// acsch(x) = asinh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		asinh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void asech(Float16Member a, Float16Member b) {
		// asech(x) = acosh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		acosh(tmp, b);
	}

	// reference: Wolfram Alpha
	
	//@Override
	public void acoth(Float16Member a, Float16Member b) {
		// acoth(x) = atanh(1/x)
		Float16Member tmp = new Float16Member(1 / a.v());
		atanh(tmp, b);
	}

	@Override
	public boolean isNaN(Float16Member a) {
		return Double.isNaN(a.v());
	}

	@Override
	public boolean isInfinite(Float16Member a) {
		return Double.isInfinite(a.v());
	}

	@Override
	public void sqrt(Float16Member a, Float16Member b) {
		b.setV( Math.sqrt(a.v()) );
	}

	@Override
	public void cbrt(Float16Member a, Float16Member b) {
		b.setV( Math.cbrt(a.v()) );
	}

	@Override
	public void round(Round.Mode mode, Float16Member delta, Float16Member a, Float16Member b) {
		Round.compute(this, mode, delta, a, b);
	}

	@Override
	public void min(Float16Member a, Float16Member b, Float16Member c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(Float16Member a, Float16Member b, Float16Member c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void pow(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.pow(a.v(), b.v()) );
	}
	
	public void log10(Float16Member a, Float16Member b, Float16Member c) {
		b.setV( Math.log10(a.v()) );
	}
	
	public void toDegrees(Float16Member a, Float16Member b) {
		b.setV( Math.toDegrees(a.v()) );
	}
	
	public void toRadians(Float16Member a, Float16Member b) {
		b.setV( Math.toRadians(a.v()) );
	}
	
	@Override
	public void random(Float16Member a) {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		a.setV(rng.nextDouble());
	}
	
	@Override
	public void real(Float16Member a, Float16Member b) {
		b.setV(a.v());
	}
	
	@Override
	public void unreal(Float16Member a, Float16Member b) {
		b.setV(0);
	}


	@Override
	public void pred(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void succ(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void copySign(Float16Member a, Float16Member b, Float16Member c) {
		c.setV( Math.copySign(a.v(), b.v()) );
	}
	
	@Override
	public int getExponent(Float16Member a) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void scalb(int scaleFactor, Float16Member a, Float16Member b) {
		b.setV(Math.scalb(a.v(),scaleFactor));
	}

	@Override
	public void ulp(Float16Member a, Float16Member b) {
		// TODO
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void div(Float16Member a, Float16Member b, Float16Member d) {
		double v = a.v() / b.v();
		if (v > 0)
			v = Math.floor(v);
		else
			v = Math.ceil(v);
		d.setV(v);
	}

	@Override
	public void mod(Float16Member a, Float16Member b, Float16Member m) {
		m.setV(a.v() % b.v());
	}

	@Override
	public void divMod(Float16Member a, Float16Member b, Float16Member d, Float16Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void sinch(Float16Member a, Float16Member b) {
		Sinch.compute(this, a, b);
	}

	@Override
	public void sinchpi(Float16Member a, Float16Member b) {
		Sinchpi.compute(this, a, b);
	}

	@Override
	public void sinc(Float16Member a, Float16Member b) {
		Sinc.compute(this, a, b);
	}

	@Override
	public void sincpi(Float16Member a, Float16Member b) {
		Sincpi.compute(this, a, b);
	}
}