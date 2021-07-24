/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.integer.int11;

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
public class UnsignedInt11Algebra
	implements
		Integer<UnsignedInt11Algebra, UnsignedInt11Member>,
		Bounded<UnsignedInt11Member>,
		BitOperations<UnsignedInt11Member>,
		Random<UnsignedInt11Member>,
		Tolerance<UnsignedInt11Member,UnsignedInt11Member>,
		ScaleByOneHalf<UnsignedInt11Member>,
		ScaleByTwo<UnsignedInt11Member>,
		ConstructibleFromInt<UnsignedInt11Member>
{
	@Override
	public String typeDescription() {
		return "11-bit unsigned int";
	}

	@Override
	public UnsignedInt11Member construct() {
		return new UnsignedInt11Member();
	}

	@Override
	public UnsignedInt11Member construct(UnsignedInt11Member other) {
		return new UnsignedInt11Member(other);
	}

	@Override
	public UnsignedInt11Member construct(String str) {
		return new UnsignedInt11Member(str);
	}

	@Override
	public UnsignedInt11Member construct(int... vals) {
		return new UnsignedInt11Member(vals[0]);
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> EQ =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> NEQ =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt11Member, UnsignedInt11Member> ASSIGN =
			new Procedure2<UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt11Member> ZER =
			new Procedure1<UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt11Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> ADD =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> SUB =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> MUL =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt11Member a, UnsignedInt11Member b) {
			PowerNonNegative.compute(G.UINT11, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt11Member> UNITY =
			new Procedure1<UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt11Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> LESS =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> LE =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> GREAT =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> GE =
			new Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a, UnsignedInt11Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt11Member, UnsignedInt11Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt11Member a, UnsignedInt11Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt11Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt11Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt11Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt11Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> MIN =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> MAX =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> GCD =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			SteinGcd.compute(G.UINT11, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> LCM =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			SteinLcm.compute(G.UINT11, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt11Member> EVEN =
			new Function1<Boolean, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt11Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt11Member> ODD =
			new Function1<Boolean, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt11Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> DIV =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> MOD =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> DIVMOD =
			new Procedure4<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member d, UnsignedInt11Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt11Member, UnsignedInt11Member> PRED =
			new Procedure2<UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b) {
			if (a.v == 0)
				b.v = 2047;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt11Member, UnsignedInt11Member> SUCC =
			new Procedure2<UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b) {
			if (a.v == 2047)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> POW =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			PowerNonNegative.compute(G.UINT11, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt11Member> RAND =
			new Procedure1<UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(2048) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt11Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> AND =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> OR =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> XOR =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt11Member, UnsignedInt11Member> NOT =
			new Procedure2<UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt11Member, UnsignedInt11Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> ANDNOT =
			new Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt11Member a, UnsignedInt11Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 11;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt11Member a, UnsignedInt11Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt11Member> MAXBOUND =
			new Procedure1<UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a) {
			a.v = 2047;
		}
	};

	@Override
	public Procedure1<UnsignedInt11Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt11Member> MINBOUND =
			new Procedure1<UnsignedInt11Member>()
	{
		@Override
		public void call(UnsignedInt11Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt11Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt11Member> ISZERO =
			new Function1<Boolean, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt11Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt11Member b, UnsignedInt11Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt11Member b, UnsignedInt11Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt11Member, UnsignedInt11Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt11Member, UnsignedInt11Member> SBR =
			new Procedure3<RationalMember, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt11Member b, UnsignedInt11Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt11Member, UnsignedInt11Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member> SBD =
			new Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(Double a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member> SBDR =
			new Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(Double a, UnsignedInt11Member b, UnsignedInt11Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt11Member, UnsignedInt11Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> WITHIN =
			new Function3<Boolean, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt11Member tol, UnsignedInt11Member a, UnsignedInt11Member b) {
			return NumberWithin.compute(G.UINT11, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt11Member, UnsignedInt11Member, UnsignedInt11Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt11Member a, UnsignedInt11Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt11Member a, UnsignedInt11Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt11Member, UnsignedInt11Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt11Member> ISUNITY =
			new Function1<Boolean, UnsignedInt11Member>()
	{
		@Override
		public Boolean call(UnsignedInt11Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt11Member> isUnity() {
		return ISUNITY;
	}
}
