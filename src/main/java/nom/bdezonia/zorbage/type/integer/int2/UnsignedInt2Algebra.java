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
package nom.bdezonia.zorbage.type.integer.int2;

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
import nom.bdezonia.zorbage.misc.C;
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
public class UnsignedInt2Algebra
	implements
		Integer<UnsignedInt2Algebra, UnsignedInt2Member>,
		Bounded<UnsignedInt2Member>,
		BitOperations<UnsignedInt2Member>,
		Random<UnsignedInt2Member>,
		Tolerance<UnsignedInt2Member,UnsignedInt2Member>,
		ScaleByOneHalf<UnsignedInt2Member>,
		ScaleByTwo<UnsignedInt2Member>
{

	@Override
	public UnsignedInt2Member construct() {
		return new UnsignedInt2Member();
	}

	@Override
	public UnsignedInt2Member construct(UnsignedInt2Member other) {
		return new UnsignedInt2Member(other);
	}

	@Override
	public UnsignedInt2Member construct(String str) {
		return new UnsignedInt2Member(str);
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> EQ =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> NEQ =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt2Member, UnsignedInt2Member> ASSIGN =
			new Procedure2<UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt2Member> ZER =
			new Procedure1<UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt2Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> ADD =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> SUB =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> MUL =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt2Member a, UnsignedInt2Member b) {
			PowerNonNegative.compute(G.UINT2, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt2Member> UNITY =
			new Procedure1<UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt2Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> LESS =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> LE =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> GREAT =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> GE =
			new Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a, UnsignedInt2Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt2Member, UnsignedInt2Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt2Member a, UnsignedInt2Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt2Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt2Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt2Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> MIN =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> MAX =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> GCD =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			SteinGcd.compute(G.UINT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> LCM =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			SteinLcm.compute(G.UINT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt2Member> EVEN =
			new Function1<Boolean, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt2Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt2Member> ODD =
			new Function1<Boolean, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt2Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> DIV =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> MOD =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> DIVMOD =
			new Procedure4<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member d, UnsignedInt2Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt2Member, UnsignedInt2Member> PRED =
			new Procedure2<UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b) {
			if (a.v == 0)
				b.v = 0x03;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt2Member, UnsignedInt2Member> SUCC =
			new Procedure2<UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b) {
			if (a.v == 0x03)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> POW =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			PowerNonNegative.compute(G.UINT2, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt2Member> RAND =
			new Procedure1<UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV( rng.nextInt(4) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt2Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> AND =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> OR =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> XOR =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt2Member, UnsignedInt2Member> NOT =
			new Procedure2<UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt2Member, UnsignedInt2Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> ANDNOT =
			new Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt2Member a, UnsignedInt2Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 2;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt2Member a, UnsignedInt2Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt2Member> MAXBOUND =
			new Procedure1<UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a) {
			a.v = 0x03;
		}
	};

	@Override
	public Procedure1<UnsignedInt2Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt2Member> MINBOUND =
			new Procedure1<UnsignedInt2Member>()
	{
		@Override
		public void call(UnsignedInt2Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt2Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt2Member> ISZERO =
			new Function1<Boolean, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt2Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt2Member b, UnsignedInt2Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt2Member b, UnsignedInt2Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(C.ONE_HALF);
			else
				tmp = tmp.add(C.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt2Member, UnsignedInt2Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt2Member, UnsignedInt2Member> SBR =
			new Procedure3<RationalMember, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt2Member b, UnsignedInt2Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt2Member, UnsignedInt2Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member> SBD =
			new Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(Double a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member> SBDR =
			new Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(Double a, UnsignedInt2Member b, UnsignedInt2Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt2Member, UnsignedInt2Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> WITHIN =
			new Function3<Boolean, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt2Member tol, UnsignedInt2Member a, UnsignedInt2Member b) {
			return NumberWithin.compute(G.UINT2, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt2Member, UnsignedInt2Member, UnsignedInt2Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt2Member a, UnsignedInt2Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt2Member a, UnsignedInt2Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt2Member, UnsignedInt2Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt2Member> ISUNITY =
			new Function1<Boolean, UnsignedInt2Member>()
	{
		@Override
		public Boolean call(UnsignedInt2Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt2Member> isUnity() {
		return ISUNITY;
	}
}
