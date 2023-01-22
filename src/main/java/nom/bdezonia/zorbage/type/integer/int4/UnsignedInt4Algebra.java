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
package nom.bdezonia.zorbage.type.integer.int4;

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
public class UnsignedInt4Algebra
	implements
		Integer<UnsignedInt4Algebra, UnsignedInt4Member>,
		Bounded<UnsignedInt4Member>,
		BitOperations<UnsignedInt4Member>,
		Random<UnsignedInt4Member>,
		Tolerance<UnsignedInt4Member,UnsignedInt4Member>,
		ScaleByOneHalf<UnsignedInt4Member>,
		ScaleByTwo<UnsignedInt4Member>,
		ConstructibleFromBytes<UnsignedInt4Member>,
		ConstructibleFromShorts<UnsignedInt4Member>,
		ConstructibleFromInts<UnsignedInt4Member>,
		ConstructibleFromLongs<UnsignedInt4Member>,
		ConstructibleFromFloats<UnsignedInt4Member>,
		ConstructibleFromDoubles<UnsignedInt4Member>,
		ConstructibleFromBigIntegers<UnsignedInt4Member>,
		ConstructibleFromBigDecimals<UnsignedInt4Member>,
		Conjugate<UnsignedInt4Member>
{
	@Override
	public String typeDescription() {
		return "4-bit unsigned int";
	}

	@Override
	public UnsignedInt4Member construct() {
		return new UnsignedInt4Member();
	}

	@Override
	public UnsignedInt4Member construct(UnsignedInt4Member other) {
		return new UnsignedInt4Member(other);
	}

	@Override
	public UnsignedInt4Member construct(String str) {
		return new UnsignedInt4Member(str);
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> EQ =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> NEQ =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt4Member, UnsignedInt4Member> ASSIGN =
			new Procedure2<UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt4Member> ZER =
			new Procedure1<UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt4Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> ADD =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> SUB =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> MUL =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt4Member a, UnsignedInt4Member b) {
			PowerNonNegative.compute(G.UINT4, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt4Member> UNITY =
			new Procedure1<UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt4Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> LESS =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> LE =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> GREAT =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> GE =
			new Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a, UnsignedInt4Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt4Member, UnsignedInt4Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt4Member a, UnsignedInt4Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt4Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt4Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt4Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt4Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> MIN =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> MAX =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> GCD =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			SteinGcd.compute(G.UINT4, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> LCM =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			SteinLcm.compute(G.UINT4, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt4Member> EVEN =
			new Function1<Boolean, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt4Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt4Member> ODD =
			new Function1<Boolean, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt4Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> DIV =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> MOD =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> DIVMOD =
			new Procedure4<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member d, UnsignedInt4Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt4Member, UnsignedInt4Member> PRED =
			new Procedure2<UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b) {
			if (a.v == 0)
				b.v = 0xf;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt4Member, UnsignedInt4Member> SUCC =
			new Procedure2<UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b) {
			if (a.v == 0xf)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> POW =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			PowerNonNegative.compute(G.UINT4, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt4Member> RAND =
			new Procedure1<UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(16) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt4Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> AND =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> OR =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> XOR =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt4Member, UnsignedInt4Member> NOT =
			new Procedure2<UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> ANDNOT =
			new Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt4Member a, UnsignedInt4Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 4;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt4Member a, UnsignedInt4Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt4Member> MAXBOUND =
			new Procedure1<UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<UnsignedInt4Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt4Member> MINBOUND =
			new Procedure1<UnsignedInt4Member>()
	{
		@Override
		public void call(UnsignedInt4Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<UnsignedInt4Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt4Member> ISZERO =
			new Function1<Boolean, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt4Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt4Member b, UnsignedInt4Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt4Member b, UnsignedInt4Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt4Member, UnsignedInt4Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt4Member, UnsignedInt4Member> SBR =
			new Procedure3<RationalMember, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt4Member b, UnsignedInt4Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt4Member, UnsignedInt4Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member> SBD =
			new Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(Double a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member> SBDR =
			new Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(Double a, UnsignedInt4Member b, UnsignedInt4Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt4Member, UnsignedInt4Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> WITHIN =
			new Function3<Boolean, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt4Member tol, UnsignedInt4Member a, UnsignedInt4Member b) {
			return NumberWithin.compute(G.UINT4, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt4Member a, UnsignedInt4Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt4Member a, UnsignedInt4Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt4Member, UnsignedInt4Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt4Member> ISUNITY =
			new Function1<Boolean, UnsignedInt4Member>()
	{
		@Override
		public Boolean call(UnsignedInt4Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt4Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<UnsignedInt4Member, UnsignedInt4Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public UnsignedInt4Member construct(BigDecimal... vals) {
		UnsignedInt4Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(BigInteger... vals) {
		UnsignedInt4Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(double... vals) {
		UnsignedInt4Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(float... vals) {
		UnsignedInt4Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(long... vals) {
		UnsignedInt4Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(int... vals) {
		UnsignedInt4Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(short... vals) {
		UnsignedInt4Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public UnsignedInt4Member construct(byte... vals) {
		UnsignedInt4Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
