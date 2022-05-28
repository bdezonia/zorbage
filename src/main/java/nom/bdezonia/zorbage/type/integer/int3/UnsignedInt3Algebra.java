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
package nom.bdezonia.zorbage.type.integer.int3;

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
public class UnsignedInt3Algebra
	implements
		Integer<UnsignedInt3Algebra, UnsignedInt3Member>,
		Bounded<UnsignedInt3Member>,
		BitOperations<UnsignedInt3Member>,
		Random<UnsignedInt3Member>,
		Tolerance<UnsignedInt3Member,UnsignedInt3Member>,
		ScaleByOneHalf<UnsignedInt3Member>,
		ScaleByTwo<UnsignedInt3Member>,
		ConstructibleFromInt<UnsignedInt3Member>,
		Conjugate<UnsignedInt3Member>
{
	@Override
	public String typeDescription() {
		return "3-bit unsigned int";
	}

	@Override
	public UnsignedInt3Member construct() {
		return new UnsignedInt3Member();
	}

	@Override
	public UnsignedInt3Member construct(UnsignedInt3Member other) {
		return new UnsignedInt3Member(other);
	}

	@Override
	public UnsignedInt3Member construct(String str) {
		return new UnsignedInt3Member(str);
	}

	@Override
	public UnsignedInt3Member construct(int... vals) {
		return new UnsignedInt3Member(vals[0]);
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> EQ =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> NEQ =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt3Member, UnsignedInt3Member> ASSIGN =
			new Procedure2<UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt3Member> ZER =
			new Procedure1<UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt3Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> ADD =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> SUB =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> MUL =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt3Member a, UnsignedInt3Member b) {
			PowerNonNegative.compute(G.UINT3, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt3Member> UNITY =
			new Procedure1<UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt3Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> LESS =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> LE =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> GREAT =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> GE =
			new Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a, UnsignedInt3Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt3Member, UnsignedInt3Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt3Member a, UnsignedInt3Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt3Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt3Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt3Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt3Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> MIN =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> MAX =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> GCD =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			SteinGcd.compute(G.UINT3, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> LCM =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			SteinLcm.compute(G.UINT3, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt3Member> EVEN =
			new Function1<Boolean, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt3Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt3Member> ODD =
			new Function1<Boolean, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt3Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> DIV =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> MOD =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> DIVMOD =
			new Procedure4<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member d, UnsignedInt3Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt3Member, UnsignedInt3Member> PRED =
			new Procedure2<UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b) {
			if (a.v == 0)
				b.v = 7;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt3Member, UnsignedInt3Member> SUCC =
			new Procedure2<UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b) {
			if (a.v == 7)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> POW =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			PowerNonNegative.compute(G.UINT3, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt3Member> RAND =
			new Procedure1<UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV( rng.nextInt(8) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt3Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> AND =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> OR =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> XOR =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt3Member, UnsignedInt3Member> NOT =
			new Procedure2<UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> ANDNOT =
			new Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt3Member a, UnsignedInt3Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 3;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt3Member a, UnsignedInt3Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt3Member> MAXBOUND =
			new Procedure1<UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a) {
			a.v = 7;
		}
	};

	@Override
	public Procedure1<UnsignedInt3Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt3Member> MINBOUND =
			new Procedure1<UnsignedInt3Member>()
	{
		@Override
		public void call(UnsignedInt3Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt3Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt3Member> ISZERO =
			new Function1<Boolean, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt3Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt3Member b, UnsignedInt3Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt3Member b, UnsignedInt3Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt3Member, UnsignedInt3Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt3Member, UnsignedInt3Member> SBR =
			new Procedure3<RationalMember, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt3Member b, UnsignedInt3Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt3Member, UnsignedInt3Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member> SBD =
			new Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(Double a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member> SBDR =
			new Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(Double a, UnsignedInt3Member b, UnsignedInt3Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt3Member, UnsignedInt3Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> WITHIN =
			new Function3<Boolean, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt3Member tol, UnsignedInt3Member a, UnsignedInt3Member b) {
			return NumberWithin.compute(G.UINT3, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt3Member, UnsignedInt3Member, UnsignedInt3Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt3Member a, UnsignedInt3Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt3Member a, UnsignedInt3Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt3Member, UnsignedInt3Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt3Member> ISUNITY =
			new Function1<Boolean, UnsignedInt3Member>()
	{
		@Override
		public Boolean call(UnsignedInt3Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt3Member> isUnity() {
		return ISUNITY;
	}

	private final Procedure2<UnsignedInt3Member, UnsignedInt3Member> CONJ =
		new Procedure2<UnsignedInt3Member, UnsignedInt3Member>() {
			
			@Override
			public void call(UnsignedInt3Member a, UnsignedInt3Member b) {
				b.set(a);
			}
		};
		
	@Override
	public Procedure2<UnsignedInt3Member, UnsignedInt3Member> conjugate() {
		return CONJ;
	}
}
