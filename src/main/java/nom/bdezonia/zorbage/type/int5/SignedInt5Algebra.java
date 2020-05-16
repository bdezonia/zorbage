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
package nom.bdezonia.zorbage.type.int5;

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
public class SignedInt5Algebra
	implements
		Integer<SignedInt5Algebra, SignedInt5Member>,
		Bounded<SignedInt5Member>,
		BitOperations<SignedInt5Member>,
		Random<SignedInt5Member>,
		Tolerance<SignedInt5Member,SignedInt5Member>
{

	@Override
	public SignedInt5Member construct() {
		return new SignedInt5Member();
	}

	@Override
	public SignedInt5Member construct(SignedInt5Member other) {
		return new SignedInt5Member(other);
	}

	@Override
	public SignedInt5Member construct(String str) {
		return new SignedInt5Member(str);
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> EQ =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> NEQ =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> ASSIGN =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt5Member> ZER =
			new Procedure1<SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt5Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> NEG =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			if (a.v == -16)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> ADD =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> SUB =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> MUL =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt5Member a, SignedInt5Member b) {
			PowerNonNegative.compute(G.INT5, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt5Member> UNITY =
			new Procedure1<SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt5Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> LESS =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> LE =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> GREAT =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt5Member, SignedInt5Member> GE =
			new Function2<Boolean, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a, SignedInt5Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt5Member, SignedInt5Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt5Member, SignedInt5Member> CMP =
			new Function2<java.lang.Integer, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt5Member a, SignedInt5Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt5Member, SignedInt5Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt5Member> SIG =
			new Function1<java.lang.Integer, SignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt5Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt5Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> MIN =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> MAX =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> ABS =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			if (a.v == -16)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> GCD =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			Gcd.compute(G.INT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> LCM =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			Lcm.compute(G.INT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt5Member> EVEN =
			new Function1<Boolean, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt5Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt5Member> ODD =
			new Function1<Boolean, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt5Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> DIV =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member d) {
			if (b.v == -1 && a.v == -16)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> MOD =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt5Member, SignedInt5Member, SignedInt5Member, SignedInt5Member> DIVMOD =
			new Procedure4<SignedInt5Member, SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member d, SignedInt5Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt5Member, SignedInt5Member, SignedInt5Member, SignedInt5Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> PRED =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			if (a.v == -16)
				b.v = 15;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> SUCC =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			if (a.v == 15)
				b.v = -16;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> POW =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			PowerNonNegative.compute(G.INT5, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt5Member> RAND =
			new Procedure1<SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(32)-16);
		}
	};
	
	@Override
	public Procedure1<SignedInt5Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> AND =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> OR =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> XOR =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt5Member, SignedInt5Member> NOT =
			new Procedure2<SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt5Member, SignedInt5Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> ANDNOT =
			new Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a, SignedInt5Member b, SignedInt5Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt5Member a, SignedInt5Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 5;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt5Member a, SignedInt5Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt5Member a, SignedInt5Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt5Member, SignedInt5Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt5Member> MAXBOUND =
			new Procedure1<SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a) {
			a.v = 15;
		}
	};

	@Override
	public Procedure1<SignedInt5Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt5Member> MINBOUND =
			new Procedure1<SignedInt5Member>()
	{
		@Override
		public void call(SignedInt5Member a) {
			a.v = -16;
		}
	};

	@Override
	public Procedure1<SignedInt5Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt5Member> ISZERO =
			new Function1<Boolean, SignedInt5Member>()
	{
		@Override
		public Boolean call(SignedInt5Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt5Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt5Member, SignedInt5Member, SignedInt5Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt5Member b, SignedInt5Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt5Member b, SignedInt5Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt5Member, SignedInt5Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt5Member, SignedInt5Member> SBR =
			new Procedure3<RationalMember, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt5Member b, SignedInt5Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt5Member, SignedInt5Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt5Member, SignedInt5Member> SBD =
			new Procedure3<Double, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(Double a, SignedInt5Member b, SignedInt5Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt5Member, SignedInt5Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt5Member, SignedInt5Member> SBDR =
			new Procedure3<Double, SignedInt5Member, SignedInt5Member>()
	{
		@Override
		public void call(Double a, SignedInt5Member b, SignedInt5Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt5Member, SignedInt5Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt5Member, SignedInt5Member, SignedInt5Member> WITHIN =
			new Function3<Boolean, SignedInt5Member, SignedInt5Member, SignedInt5Member>()
	{
		
		@Override
		public Boolean call(SignedInt5Member tol, SignedInt5Member a, SignedInt5Member b) {
			return NumberWithin.compute(G.INT5, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt5Member, SignedInt5Member, SignedInt5Member> within() {
		return WITHIN;
	}

}
