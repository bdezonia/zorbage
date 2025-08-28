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
package nom.bdezonia.zorbage.type.real.float64;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

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
public class Float64Algebra
	implements
		RandomIsZeroToOne,
		OrderedField<Float64Algebra,Float64Member,Float64Member>,
		Bounded<Float64Member>,
		Norm<Float64Member,Float64Member>,
		RealConstants<Float64Member>,
		Exponential<Float64Member>,
		Trigonometric<Float64Member>,
		InverseTrigonometric<Float64Member>,
		Hyperbolic<Float64Member>,
		InverseHyperbolic<Float64Member>,
		Infinite<Float64Member>,
		NegInfinite<Float64Member>,
		NaN<Float64Member>,
		Roots<Float64Member>,
		Power<Float64Member>,
		Rounding<Float64Member,Float64Member>,
		Random<Float64Member>,
		RealUnreal<Float64Member,Float64Member>,
		PredSucc<Float64Member>,
		MiscFloat<Float64Member>,
		ModularDivision<Float64Member>,
		Conjugate<Float64Member>,
		Scale<Float64Member,Float64Member>,
		ScaleByHighPrec<Float64Member>,
		ScaleByRational<Float64Member>,
		ScaleByDouble<Float64Member>,
		ScaleComponents<Float64Member, Float64Member>,
		Tolerance<Float64Member,Float64Member>,
		ScaleByOneHalf<Float64Member>,
		ScaleByTwo<Float64Member>,
		ConstructibleFromBytes<Float64Member>,
		ConstructibleFromShorts<Float64Member>,
		ConstructibleFromInts<Float64Member>,
		ConstructibleFromLongs<Float64Member>,
		ConstructibleFromFloats<Float64Member>,
		ConstructibleFromDoubles<Float64Member>,
		ConstructibleFromBigIntegers<Float64Member>,
		ConstructibleFromBigDecimals<Float64Member>,
		ExactlyConstructibleFromBytes<Float64Member>,
		ExactlyConstructibleFromShorts<Float64Member>,
		ExactlyConstructibleFromInts<Float64Member>,
		ExactlyConstructibleFromFloats<Float64Member>,
		ExactlyConstructibleFromDoubles<Float64Member>
{
	private static final Float64Member PI = new Float64Member(Math.PI);
	private static final Float64Member E = new Float64Member(Math.E);
	private static final Float64Member GAMMA = new Float64Member(0.57721566490153286060);
	private static final Float64Member PHI = new Float64Member(1.61803398874989484820);
	
	@Override
	public String typeDescription() {
		return "64-bit based real number";
	}

	public Float64Algebra() { }
	
	private final Function2<Boolean,Float64Member,Float64Member> EQ =
		new Function2<Boolean,Float64Member,Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64Member,Float64Member> NEQ =
			new Function2<Boolean,Float64Member,Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() != b.v();
		}
	};
		
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float64Member construct() {
		return new Float64Member();
	}

	@Override
	public Float64Member construct(Float64Member other) {
		return new Float64Member(other);
	}

	@Override
	public Float64Member construct(String s) {
		return new Float64Member(s);
	}

	private final Procedure2<Float64Member,Float64Member> ASSIGN =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member from, Float64Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> assign() {
		return ASSIGN;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> ADD =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> add() {
		return ADD;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> SUB =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> subtract() {
		return SUB;
	}

	private final Procedure1<Float64Member> ZER =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64Member> zero() {
		return ZER;
	}

	private final Procedure2<Float64Member,Float64Member> NEG =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( -a.v() );
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> negate() {
		return NEG;
	}

	private final Procedure1<Float64Member> UNITY =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<Float64Member> unity() {
		return UNITY;
	}

	private final Procedure2<Float64Member,Float64Member> INV =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / a.v() );
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> invert() {
		return INV;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> DIVIDE =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean,Float64Member,Float64Member> LESS =
			new Function2<Boolean, Float64Member, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,Float64Member,Float64Member> LE =
			new Function2<Boolean, Float64Member, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,Float64Member,Float64Member> GREAT =
			new Function2<Boolean, Float64Member, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,Float64Member,Float64Member> GE =
			new Function2<Boolean, Float64Member, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,Float64Member,Float64Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,Float64Member,Float64Member> CMP =
			new Function2<java.lang.Integer, Float64Member, Float64Member>()
	{
		@Override
		public java.lang.Integer call(Float64Member a, Float64Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return  1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,Float64Member,Float64Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,Float64Member> SIG =
			new Function1<Integer, Float64Member>()
	{
		@Override
		public Integer call(Float64Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return  1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,Float64Member> signum() {
		return SIG;
	}
	
	private final Procedure1<Float64Member> MAXBOUND =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<Float64Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<Float64Member> MINBOUND =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<Float64Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> MUL =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float64Member,Float64Member> POWER =
			new Procedure3<Integer, Float64Member, Float64Member>()
	{
		@Override
		public void call(Integer power, Float64Member a, Float64Member b) {
			if (power == 0 && a.v() == 0) {
				unity().call(b);
			}
			else
				b.setV( Math.pow(a.v(), power) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float64Member,Float64Member> power() {
		return POWER;
	}

	private final Procedure2<Float64Member,Float64Member> ABS =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.abs(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> abs() {
		return ABS;
	}

	private final Procedure2<Float64Member,Float64Member> NORM =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			abs().call(a, b);
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> norm() {
		return NORM;
	}

	private final Procedure1<Float64Member> PI_ =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<Float64Member> PI() {
		return PI_;
	}

	private final Procedure1<Float64Member> E_ =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<Float64Member> E() {
		return E_;
	}
	
	private final Procedure1<Float64Member> GAMMA_ =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<Float64Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<Float64Member> PHI_ =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<Float64Member> PHI() {
		return PHI_;
	}

	private final Procedure2<Float64Member,Float64Member> EXP =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.exp(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> exp() {
		return EXP;
	}

	private final Procedure2<Float64Member,Float64Member> EXPM1 =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.expm1(a.v()) );
		}
	};
	
	public Procedure2<Float64Member,Float64Member> expm1() {
		return EXPM1;
	}

	private final Procedure2<Float64Member,Float64Member> LOG =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.log(a.v()) );
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> log() {
		return LOG;
	}

	private final Procedure2<Float64Member,Float64Member> LOG1P =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.log1p(a.v()) );
		}
	};

	public Procedure2<Float64Member,Float64Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<Float64Member,Float64Member> COS =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.cos(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> cos() {
		return COS;
	}

	private final Procedure2<Float64Member,Float64Member> SIN =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.sin(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> sin() {
		return SIN;
	}
	
	private final Procedure3<Float64Member,Float64Member,Float64Member> SINANDCOS =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member s, Float64Member c) {
			sin().call(a,s);
			cos().call(a,c);

			/*
			
			// This might be too inaccurate to be worth optimization

			double arg = a.v() % TWO_PI;  // this might be faster than some while (arg < 0 || arg >= TWO_PI) loops
			double cosine = Math.cos(arg);
			double sine = Math.sqrt(1 - cosine * cosine);
			if ( arg < 0) {
				if (arg > -Math.PI)
					sine = -sine;
			}
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
	public Procedure3<Float64Member,Float64Member,Float64Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float64Member,Float64Member> TAN =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.tan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> tan() {
		return TAN;
	}

	// ref: internet

	private final Procedure2<Float64Member,Float64Member> CSC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.sin(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> csc() {
		return CSC;
	}
	
	// ref: internet

	private final Procedure2<Float64Member,Float64Member> SEC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.cos(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> sec() {
		return SEC;
	}
	
	// ref: internet

	private final Procedure2<Float64Member,Float64Member> COT =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.tan(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> cot() {
		return COT;
	}
	
	private final Procedure2<Float64Member,Float64Member> COSH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.cosh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> cosh() {
		return COSH;
	}

	private final Procedure2<Float64Member,Float64Member> SINH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.sinh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> sinh() {
		return SINH;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> SINHANDCOSH =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member s, Float64Member c) {
			sinh().call(a, s);
			cosh().call(a, c);
			// another approach: was preferred until accuracy concern
			//double t1 = Math.exp(a.v());
			//double t2 = 1/t1;
			//double cosh = 0.5 * (t1+t2);
			//double sinh = 0.5 * (t1-t2);
			//s.setV(sinh);
			//c.setV(cosh);
			// alternate approach: accuracy seems the same. investigate. speed compare too.
			//double arg = a.v();
			//double cosh = Math.cosh(arg);
			//double sinh = Math.sqrt(cosh * cosh - 1);
			//if ( arg < 0) {
			//	sinh = -sinh;
			//}
			//s.setV( sinh );
			//c.setV( cosh );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float64Member,Float64Member> TANH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.tanh(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> tanh() {
		return TANH;
	}

	// ref: internet

	private final Procedure2<Float64Member,Float64Member> CSCH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.sinh(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> csch() {
		return CSCH;
	}
	
	// ref: internet

	private final Procedure2<Float64Member,Float64Member> SECH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.cosh(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> sech() {
		return SECH;
	}

	// ref: internet

	private final Procedure2<Float64Member,Float64Member> COTH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 1.0 / Math.tanh(a.v()) );
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> coth() {
		return COTH;
	}
	
	private final Procedure2<Float64Member,Float64Member> ACOS =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.acos(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> acos() {
		return ACOS;
	}

	private final Procedure2<Float64Member,Float64Member> ASIN =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.asin(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> asin() {
		return ASIN;
	}

	private final Procedure2<Float64Member,Float64Member> ATAN =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.atan(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> atan() {
		return ATAN;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> ATAN2 =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( Math.atan2(a.v(), b.v()) );
		}
	};
	
	public Procedure3<Float64Member,Float64Member,Float64Member> atan2() {
		return ATAN2;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ACSC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// acsc(x) = asin(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			asin().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> acsc() {
		return ACSC;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ASEC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// asec(x) = acos(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			acos().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> asec() {
		return ASEC;
	}
	
	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ACOT =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// asec(x) = acos(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			atan().call(tmp, b);
		}
	};
	
	// @Override
	public Procedure2<Float64Member,Float64Member> acot() {
		return ACOT;
	}
	
	// reference: Mathworld

	private final Procedure2<Float64Member,Float64Member> ACOSH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() - 1)) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> acosh() {
		return ACOSH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ASINH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.log(a.v() + Math.sqrt(a.v()*a.v() + 1)) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> asinh() {
		return ASINH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ATANH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( 0.5 * (-Math.log(1 - a.v()) + Math.log(1 + a.v())) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> atanh() {
		return ATANH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ACSCH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// acsch(x) = asinh(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			asinh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> acsch() {
		return ACSCH;
	}

	// reference: Wolfram Alpha
	
	private final Procedure2<Float64Member,Float64Member> ASECH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// asech(x) = acosh(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			acosh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> asech() {
		return ASECH;
	}

	// reference: Wolfram Alpha

	private final Procedure2<Float64Member,Float64Member> ACOTH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			// acoth(x) = atanh(1/x)
			Float64Member tmp = new Float64Member(1 / a.v());
			atanh().call(tmp, b);
		}
	};
	
	//@Override
	public Procedure2<Float64Member,Float64Member> acoth() {
		return ACOTH;
	}

	private final Function1<Boolean,Float64Member> ISNAN =
			new Function1<Boolean, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a) {
			return Double.isNaN(a.v());
		}
	};
	
	@Override
	public Function1<Boolean,Float64Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float64Member> NAN =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.setV(Double.NaN);
		}
	};
	
	@Override
	public Procedure1<Float64Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,Float64Member> ISINF =
			new Function1<Boolean, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a) {
			return Double.isInfinite(a.v());
		}
	};
	
	@Override
	public Function1<Boolean,Float64Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64Member> INF =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.setV(Double.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<Float64Member> infinite() {
		return INF;
	}

	private final Procedure1<Float64Member> NINF =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			a.setV(Double.NEGATIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<Float64Member> negInfinite() {
		return NINF;
	}

	private final Procedure2<Float64Member,Float64Member> SQRT =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.sqrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<Float64Member,Float64Member> CBRT =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.cbrt(a.v()) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float64Member,Float64Member,Float64Member> ROUND =
			new Procedure4<Round.Mode, Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64Member a, Float64Member b) {
			Round.compute(G.DBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,Float64Member,Float64Member> round() {
		return ROUND;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> MIN =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			Min.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> min() {
		return MIN;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> MAX =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			Max.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> max() {
		return MAX;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> POW =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			if (a.v() == 0 && b.v() == 0) {
				c.setV(Double.NaN);
			}
			else
				c.setV( Math.pow(a.v(), b.v()) );
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> pow() {
		return POW;
	}
	
	private final Procedure3<Float64Member,Float64Member,Float64Member> IEEE =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV( Math.IEEEremainder(a.v(), b.v()) );
		}
	};
	
	public Procedure3<Float64Member,Float64Member,Float64Member> IEEEremainder() {
		return IEEE;
	}

	private final Procedure2<Float64Member,Float64Member> LOG10 =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.log10(a.v()) );
		}
	};
	
	public Procedure2<Float64Member,Float64Member> log10() {
		return LOG10;
	}
	
	private final Procedure2<Float64Member,Float64Member> TODEG =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.toDegrees(a.v()) );
		}
	};
	
	public Procedure2<Float64Member,Float64Member> toDegrees() {
		return TODEG;
	}
	
	private final Procedure2<Float64Member,Float64Member> TORAD =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.toRadians(a.v()) );
		}
	};
	
	public Procedure2<Float64Member,Float64Member> toRadians() {
		return TORAD;
	}
	
	private final Procedure1<Float64Member> RAND =
			new Procedure1<Float64Member>()
	{
		@Override
		public void call(Float64Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextDouble());
		}
	};
	
	@Override
	public Procedure1<Float64Member> random() {
		return RAND;
	}
	
	private final Procedure2<Float64Member,Float64Member> REAL =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(a.v());
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> real() {
		return REAL;
	}
	
	private final Procedure2<Float64Member,Float64Member> UNREAL =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(0);
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> unreal() {
		return UNREAL;
	}
	
	private final Procedure2<Float64Member,Float64Member> PRED =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV( Math.nextDown( a.v() ) );
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> pred() {
		return PRED;
	}

	private final Procedure2<Float64Member,Float64Member> SUCC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(Math.nextUp(a.v()));
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> succ() {
		return SUCC;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> CPS =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member c) {
			c.setV(Math.copySign(a.v(), b.v()));
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> copySign() {
		return CPS;
	}

	private final Function1<java.lang.Integer,Float64Member> GEXP =
			new Function1<java.lang.Integer,Float64Member>()
	{
		@Override
		public java.lang.Integer call(Float64Member a) {
			return Math.getExponent(a.v());
		}
	};
	
	@Override
	public Function1<java.lang.Integer,Float64Member> getExponent() {
		return GEXP;
	}

	private final Procedure3<java.lang.Integer,Float64Member,Float64Member> SCALB =
			new Procedure3<Integer, Float64Member, Float64Member>()
	{
		@Override
		public void call(Integer scaleFactor, Float64Member a, Float64Member b) {
			b.setV(Math.scalb(a.v(), scaleFactor));
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float64Member,Float64Member> scalb() {
		return SCALB;
	}

	private final Procedure2<Float64Member,Float64Member> ULP =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(Math.ulp(a.v()));
		}
	};
	
	@Override
	public Procedure2<Float64Member,Float64Member> ulp() {
		return ULP;
	}

	// TODO: are the following two correctly divMod() or accidentally quotRem(). Investigate.
	// Later edit: I have proven by junit test that divMod() behavior matches ints. Will
	// need to implement quotRem() at some point.
	
	private final Procedure3<Float64Member,Float64Member,Float64Member> DIV =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member d) {
			double v = a.v() / b.v();
			if (v > 0)
				v = Math.floor(v);
			else
				v = Math.ceil(v);
			d.setV(v);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> div() {
		return DIV;
	}

	private final Procedure3<Float64Member,Float64Member,Float64Member> MOD =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member m) {
			m.setV(a.v() % b.v());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64Member,Float64Member> mod() {
		return MOD;
	}

	private final Procedure4<Float64Member,Float64Member,Float64Member,Float64Member> DIVMOD =
			new Procedure4<Float64Member, Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b, Float64Member d, Float64Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<Float64Member,Float64Member,Float64Member,Float64Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<Float64Member,Float64Member> SINCH =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			Sinch.compute(G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> sinch() {
		return SINCH;
	}

	private final Procedure2<Float64Member,Float64Member> SINCHPI =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			Sinchpi.compute(G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float64Member,Float64Member> SINC =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			Sinc.compute(G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> sinc() {
		 return SINC;
	}

	private final Procedure2<Float64Member,Float64Member> SINCPI =
			new Procedure2<Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member a, Float64Member b) {
			Sincpi.compute(G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64Member,Float64Member> sincpi() {
		return SINCPI;
	}

	@Override
	public Procedure2<Float64Member,Float64Member> conjugate() {
		return ASSIGN;
	}

	private final Function1<Boolean, Float64Member> ISZERO =
			new Function1<Boolean, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, Float64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<Float64Member, Float64Member, Float64Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, Float64Member, Float64Member> SBHP =
			new Procedure3<HighPrecisionMember, Float64Member, Float64Member>()
	{
		@Override
		public void call(HighPrecisionMember a, Float64Member b, Float64Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.v()));
			c.setV(tmp.doubleValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float64Member, Float64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float64Member, Float64Member> SBR =
			new Procedure3<RationalMember, Float64Member, Float64Member>()
	{
		@Override
		public void call(RationalMember a, Float64Member b, Float64Member c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(b.v());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setV(tmp.doubleValue());
		}
	};

	@Override
	public Procedure3<RationalMember, Float64Member, Float64Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64Member, Float64Member> SBD =
			new Procedure3<Double, Float64Member, Float64Member>()
	{
		@Override
		public void call(Double a, Float64Member b, Float64Member c) {
			c.setV(a * b.v());
		}
	};

	@Override
	public Procedure3<Double, Float64Member, Float64Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float64Member, Float64Member, Float64Member> SC =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member factor, Float64Member a, Float64Member b) {
			b.setV(factor.v() * a.v());
		}
	};

	@Override
	public Procedure3<Float64Member, Float64Member, Float64Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float64Member, Float64Member, Float64Member> WITHIN =
			new Function3<Boolean, Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64Member a, Float64Member b) {
			return NumberWithin.compute(G.DBL, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64Member, Float64Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, Float64Member, Float64Member> STWO =
			new Procedure3<java.lang.Integer, Float64Member, Float64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float64Member a, Float64Member b) {
			ScaleHelper.compute(G.DBL, G.DBL, new Float64Member(2), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float64Member, Float64Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, Float64Member, Float64Member> SHALF =
			new Procedure3<java.lang.Integer, Float64Member, Float64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float64Member a, Float64Member b) {
			ScaleHelper.compute(G.DBL, G.DBL, new Float64Member(0.5), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, Float64Member, Float64Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, Float64Member> ISUNITY =
			new Function1<Boolean, Float64Member>()
	{
		@Override
		public Boolean call(Float64Member a) {
			return a.v() == 1;
		}
	};

	@Override
	public Function1<Boolean, Float64Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Float64Member constructExactly(byte... vals) {
		Float64Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public Float64Member constructExactly(short... vals) {
		Float64Member v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public Float64Member constructExactly(int... vals) {
		Float64Member v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public Float64Member constructExactly(float... vals) {
		Float64Member v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public Float64Member constructExactly(double... vals) {
		Float64Member v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public Float64Member construct(BigDecimal... vals) {
		Float64Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public Float64Member construct(BigInteger... vals) {
		Float64Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public Float64Member construct(double... vals) {
		Float64Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public Float64Member construct(float... vals) {
		Float64Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public Float64Member construct(long... vals) {
		Float64Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public Float64Member construct(int... vals) {
		Float64Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public Float64Member construct(short... vals) {
		Float64Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public Float64Member construct(byte... vals) {
		Float64Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}