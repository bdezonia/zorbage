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
package nom.bdezonia.zorbage.type.integer.int5;

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
public class UnsignedInt5Algebra
	implements
		Integer<UnsignedInt5Algebra, UnsignedInt5Member>,
		Bounded<UnsignedInt5Member>,
		BitOperations<UnsignedInt5Member>,
		Random<UnsignedInt5Member>,
		Tolerance<UnsignedInt5Member,UnsignedInt5Member>,
		ScaleByOneHalf<UnsignedInt5Member>,
		ScaleByTwo<UnsignedInt5Member>,
		ConstructibleFromInt<UnsignedInt5Member>,
		Conjugate<UnsignedInt5Member>
{

	@Override
	public String typeDescription() {
		return "5-bit unsigned int";
	}

	@Override
	public UnsignedInt5Member construct() {
		return new UnsignedInt5Member();
	}

	@Override
	public UnsignedInt5Member construct(UnsignedInt5Member other) {
		return new UnsignedInt5Member(other);
	}

	@Override
	public UnsignedInt5Member construct(String str) {
		return new UnsignedInt5Member(str);
	}

	@Override
	public UnsignedInt5Member construct(int... vals) {
		return new UnsignedInt5Member(vals);
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> EQ =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> NEQ =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> ASSIGN =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt5Member> ZER =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt5Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> ADD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> SUB =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MUL =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt5Member a, UnsignedInt5Member b) {
			PowerNonNegative.compute(G.UINT5, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt5Member> UNITY =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> LESS =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> LE =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> GREAT =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> GE =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt5Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt5Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt5Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MIN =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MAX =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> GCD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			SteinGcd.compute(G.UINT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> LCM =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			SteinLcm.compute(G.UINT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt5Member> EVEN =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt5Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt5Member> ODD =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt5Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> DIV =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MOD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> DIVMOD =
			new Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member d, UnsignedInt5Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> PRED =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v == 0)
				b.v = 31;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> SUCC =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v == 31)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> POW =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			PowerNonNegative.compute(G.UINT5, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt5Member> RAND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(32) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt5Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> AND =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> OR =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> XOR =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> NOT =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> ANDNOT =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt5Member a, UnsignedInt5Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 5;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt5Member a, UnsignedInt5Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt5Member> MAXBOUND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt5Member> MINBOUND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt5Member> ISZERO =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt5Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt5Member b, UnsignedInt5Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt5Member b, UnsignedInt5Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt5Member, UnsignedInt5Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt5Member, UnsignedInt5Member> SBR =
			new Procedure3<RationalMember, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt5Member b, UnsignedInt5Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt5Member, UnsignedInt5Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member> SBD =
			new Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(Double a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member> SBDR =
			new Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(Double a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt5Member, UnsignedInt5Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> WITHIN =
			new Function3<Boolean, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt5Member tol, UnsignedInt5Member a, UnsignedInt5Member b) {
			return NumberWithin.compute(G.UINT5, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt5Member a, UnsignedInt5Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt5Member a, UnsignedInt5Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt5Member> ISUNITY =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt5Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> conjugate() {
		return ASSIGN;
	}
}
