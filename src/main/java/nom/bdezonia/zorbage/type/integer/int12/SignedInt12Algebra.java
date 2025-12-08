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
package nom.bdezonia.zorbage.type.integer.int12;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.algebra.Integer;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class SignedInt12Algebra
	implements
		Integer<SignedInt12Algebra, SignedInt12Member>,
		Bounded<SignedInt12Member>,
		BitOperations<SignedInt12Member>,
		Random<SignedInt12Member>,
		Tolerance<SignedInt12Member,SignedInt12Member>,
		ScaleByOneHalf<SignedInt12Member>,
		ScaleByTwo<SignedInt12Member>,
		ConstructibleFromBytes<SignedInt12Member>,
		ConstructibleFromShorts<SignedInt12Member>,
		ConstructibleFromInts<SignedInt12Member>,
		ConstructibleFromLongs<SignedInt12Member>,
		ConstructibleFromFloats<SignedInt12Member>,
		ConstructibleFromDoubles<SignedInt12Member>,
		ConstructibleFromBigIntegers<SignedInt12Member>,
		ConstructibleFromBigDecimals<SignedInt12Member>,
		Conjugate<SignedInt12Member>,
		ExactlyConstructibleFromBytes<SignedInt12Member>
{
	@Override
	public String typeDescription() {
		return "12-bit signed int";
	}

	@Override
	public SignedInt12Member construct() {
		return new SignedInt12Member();
	}

	@Override
	public SignedInt12Member construct(SignedInt12Member other) {
		return new SignedInt12Member(other);
	}

	@Override
	public SignedInt12Member construct(String str) {
		return new SignedInt12Member(str);
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> EQ =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> NEQ =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt12Member, SignedInt12Member> ASSIGN =
			new Procedure2<SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt12Member> ZER =
			new Procedure1<SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt12Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt12Member,SignedInt12Member> NEG =
			new Procedure2<SignedInt12Member,SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			if (a.v == -2048)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> ADD =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> SUB =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> MUL =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt12Member a, SignedInt12Member b) {
			PowerNonNegative.compute(G.INT12, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt12Member> UNITY =
			new Procedure1<SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt12Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> LESS =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> LE =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> GREAT =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt12Member, SignedInt12Member> GE =
			new Function2<Boolean, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a, SignedInt12Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt12Member, SignedInt12Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt12Member, SignedInt12Member> CMP =
			new Function2<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt12Member a, SignedInt12Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt12Member, SignedInt12Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt12Member> SIG =
			new Function1<java.lang.Integer, SignedInt12Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt12Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt12Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> MIN =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> MAX =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt12Member, SignedInt12Member> ABS =
			new Procedure2<SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			if (a.v == -2048)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> GCD =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			SteinGcd.compute(G.INT12, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> LCM =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			SteinLcm.compute(G.INT12, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt12Member> EVEN =
			new Function1<Boolean, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt12Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt12Member> ODD =
			new Function1<Boolean, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt12Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> DIV =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member d) {
			if (b.v == -1 && a.v == -2048)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> MOD =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt12Member, SignedInt12Member, SignedInt12Member, SignedInt12Member> DIVMOD =
			new Procedure4<SignedInt12Member, SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member d, SignedInt12Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt12Member, SignedInt12Member, SignedInt12Member, SignedInt12Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt12Member, SignedInt12Member> PRED =
			new Procedure2<SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			if (a.v == -2048)
				b.v = 2047;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt12Member, SignedInt12Member> SUCC =
			new Procedure2<SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			if (a.v == 2047)
				b.v = -2048;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> POW =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			PowerNonNegative.compute(G.INT12, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt12Member> RAND =
			new Procedure1<SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(0x1000)-2048);
		}
	};
	
	@Override
	public Procedure1<SignedInt12Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> AND =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> OR =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> XOR =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt12Member, SignedInt12Member> NOT =
			new Procedure2<SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> ANDNOT =
			new Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a, SignedInt12Member b, SignedInt12Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt12Member a, SignedInt12Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 12;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt12Member a, SignedInt12Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else {
				int val = a.v >> count;
				if (a.v < 0 && val == 0) {
					// sign extend
					b.v = -1;
				}
				else
					b.setV( val );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt12Member a, SignedInt12Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt12Member> MAXBOUND =
			new Procedure1<SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt12Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt12Member> MINBOUND =
			new Procedure1<SignedInt12Member>()
	{
		@Override
		public void call(SignedInt12Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt12Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt12Member> ISZERO =
			new Function1<Boolean, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt12Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt12Member, SignedInt12Member, SignedInt12Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt12Member b, SignedInt12Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt12Member b, SignedInt12Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt12Member, SignedInt12Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt12Member, SignedInt12Member> SBR =
			new Procedure3<RationalMember, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt12Member b, SignedInt12Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt12Member, SignedInt12Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt12Member, SignedInt12Member> SBD =
			new Procedure3<Double, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(Double a, SignedInt12Member b, SignedInt12Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt12Member, SignedInt12Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt12Member, SignedInt12Member> SBDR =
			new Procedure3<Double, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(Double a, SignedInt12Member b, SignedInt12Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt12Member, SignedInt12Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt12Member, SignedInt12Member, SignedInt12Member> WITHIN =
			new Function3<Boolean, SignedInt12Member, SignedInt12Member, SignedInt12Member>()
	{
		
		@Override
		public Boolean call(SignedInt12Member tol, SignedInt12Member a, SignedInt12Member b) {
			return NumberWithin.compute(G.INT12, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt12Member, SignedInt12Member, SignedInt12Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt12Member a, SignedInt12Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt12Member a, SignedInt12Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt12Member, SignedInt12Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt12Member> ISUNITY =
			new Function1<Boolean, SignedInt12Member>()
	{
		@Override
		public Boolean call(SignedInt12Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt12Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt12Member, SignedInt12Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt12Member constructExactly(byte... vals) {
		SignedInt12Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(BigDecimal... vals) {
		SignedInt12Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(BigInteger... vals) {
		SignedInt12Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(double... vals) {
		SignedInt12Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(float... vals) {
		SignedInt12Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(long... vals) {
		SignedInt12Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(int... vals) {
		SignedInt12Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(short... vals) {
		SignedInt12Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt12Member construct(byte... vals) {
		SignedInt12Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
