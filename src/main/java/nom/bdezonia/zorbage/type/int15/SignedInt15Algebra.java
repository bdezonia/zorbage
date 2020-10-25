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
package nom.bdezonia.zorbage.type.int15;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

import nom.bdezonia.zorbage.algebra.Integer;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class SignedInt15Algebra
	implements
		Integer<SignedInt15Algebra, SignedInt15Member>,
		Bounded<SignedInt15Member>,
		BitOperations<SignedInt15Member>,
		Random<SignedInt15Member>,
		Tolerance<SignedInt15Member,SignedInt15Member>,
		ScaleByOneHalf<SignedInt15Member>,
		ScaleByTwo<SignedInt15Member>
{

	@Override
	public SignedInt15Member construct() {
		return new SignedInt15Member();
	}

	@Override
	public SignedInt15Member construct(SignedInt15Member other) {
		return new SignedInt15Member(other);
	}

	@Override
	public SignedInt15Member construct(String str) {
		return new SignedInt15Member(str);
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> EQ =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> NEQ =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt15Member, SignedInt15Member> ASSIGN =
			new Procedure2<SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt15Member> ZER =
			new Procedure1<SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt15Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt15Member,SignedInt15Member> NEG =
			new Procedure2<SignedInt15Member,SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			if (a.v == -16384)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> ADD =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> SUB =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> MUL =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt15Member a, SignedInt15Member b) {
			PowerNonNegative.compute(G.INT15, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt15Member> UNITY =
			new Procedure1<SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt15Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> LESS =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> LE =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> GREAT =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt15Member, SignedInt15Member> GE =
			new Function2<Boolean, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a, SignedInt15Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt15Member, SignedInt15Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt15Member, SignedInt15Member> CMP =
			new Function2<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt15Member a, SignedInt15Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt15Member, SignedInt15Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt15Member> SIG =
			new Function1<java.lang.Integer, SignedInt15Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt15Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt15Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> MIN =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> MAX =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt15Member, SignedInt15Member> ABS =
			new Procedure2<SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			if (a.v == -16384)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> GCD =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			Gcd.compute(G.INT15, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> LCM =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			Lcm.compute(G.INT15, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt15Member> EVEN =
			new Function1<Boolean, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt15Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt15Member> ODD =
			new Function1<Boolean, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt15Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> DIV =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member d) {
			if (b.v == -1 && a.v == -16384)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> MOD =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt15Member, SignedInt15Member, SignedInt15Member, SignedInt15Member> DIVMOD =
			new Procedure4<SignedInt15Member, SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member d, SignedInt15Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt15Member, SignedInt15Member, SignedInt15Member, SignedInt15Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt15Member, SignedInt15Member> PRED =
			new Procedure2<SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			if (a.v == -16384)
				b.v = 16383;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt15Member, SignedInt15Member> SUCC =
			new Procedure2<SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			if (a.v == 16383)
				b.v = -16384;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> POW =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			PowerNonNegative.compute(G.INT15, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt15Member> RAND =
			new Procedure1<SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(32768)-16384);
		}
	};
	
	@Override
	public Procedure1<SignedInt15Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> AND =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> OR =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> XOR =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt15Member, SignedInt15Member> NOT =
			new Procedure2<SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt15Member, SignedInt15Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> ANDNOT =
			new Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a, SignedInt15Member b, SignedInt15Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt15Member a, SignedInt15Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 15;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt15Member a, SignedInt15Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt15Member a, SignedInt15Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt15Member> MAXBOUND =
			new Procedure1<SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a) {
			a.v = 16383;
		}
	};

	@Override
	public Procedure1<SignedInt15Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt15Member> MINBOUND =
			new Procedure1<SignedInt15Member>()
	{
		@Override
		public void call(SignedInt15Member a) {
			a.v = -16384;
		}
	};

	@Override
	public Procedure1<SignedInt15Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt15Member> ISZERO =
			new Function1<Boolean, SignedInt15Member>()
	{
		@Override
		public Boolean call(SignedInt15Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt15Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt15Member, SignedInt15Member, SignedInt15Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt15Member b, SignedInt15Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt15Member b, SignedInt15Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(G.ONE_HALF);
			else
				tmp = tmp.add(G.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt15Member, SignedInt15Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt15Member, SignedInt15Member> SBR =
			new Procedure3<RationalMember, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt15Member b, SignedInt15Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt15Member, SignedInt15Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt15Member, SignedInt15Member> SBD =
			new Procedure3<Double, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(Double a, SignedInt15Member b, SignedInt15Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt15Member, SignedInt15Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt15Member, SignedInt15Member> SBDR =
			new Procedure3<Double, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(Double a, SignedInt15Member b, SignedInt15Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt15Member, SignedInt15Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt15Member, SignedInt15Member, SignedInt15Member> WITHIN =
			new Function3<Boolean, SignedInt15Member, SignedInt15Member, SignedInt15Member>()
	{
		
		@Override
		public Boolean call(SignedInt15Member tol, SignedInt15Member a, SignedInt15Member b) {
			return NumberWithin.compute(G.INT15, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt15Member, SignedInt15Member, SignedInt15Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt15Member a, SignedInt15Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt15Member a, SignedInt15Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt15Member, SignedInt15Member> scaleByOneHalf() {
		return SHALF;
	}

}
