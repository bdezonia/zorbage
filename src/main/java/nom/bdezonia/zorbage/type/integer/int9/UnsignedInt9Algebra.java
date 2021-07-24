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
package nom.bdezonia.zorbage.type.integer.int9;

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
public class UnsignedInt9Algebra
	implements
		Integer<UnsignedInt9Algebra, UnsignedInt9Member>,
		Bounded<UnsignedInt9Member>,
		BitOperations<UnsignedInt9Member>,
		Random<UnsignedInt9Member>,
		Tolerance<UnsignedInt9Member,UnsignedInt9Member>,
		ScaleByOneHalf<UnsignedInt9Member>,
		ScaleByTwo<UnsignedInt9Member>,
		ConstructibleFromInt<UnsignedInt9Member>
{

	@Override
	public String typeDescription() {
		return "9-bit unsigned int";
	}

	@Override
	public UnsignedInt9Member construct() {
		return new UnsignedInt9Member();
	}

	@Override
	public UnsignedInt9Member construct(UnsignedInt9Member other) {
		return new UnsignedInt9Member(other);
	}

	@Override
	public UnsignedInt9Member construct(String str) {
		return new UnsignedInt9Member(str);
	}

	@Override
	public UnsignedInt9Member construct(int... vals) {
		return new UnsignedInt9Member(vals[0]);
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> EQ =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> NEQ =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt9Member, UnsignedInt9Member> ASSIGN =
			new Procedure2<UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt9Member> ZER =
			new Procedure1<UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt9Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> ADD =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> SUB =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> MUL =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt9Member a, UnsignedInt9Member b) {
			PowerNonNegative.compute(G.UINT9, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt9Member> UNITY =
			new Procedure1<UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt9Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> LESS =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> LE =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> GREAT =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> GE =
			new Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a, UnsignedInt9Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt9Member, UnsignedInt9Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt9Member a, UnsignedInt9Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt9Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt9Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt9Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt9Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> MIN =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> MAX =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> GCD =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			SteinGcd.compute(G.UINT9, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> LCM =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			SteinLcm.compute(G.UINT9, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt9Member> EVEN =
			new Function1<Boolean, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt9Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt9Member> ODD =
			new Function1<Boolean, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt9Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> DIV =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> MOD =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> DIVMOD =
			new Procedure4<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member d, UnsignedInt9Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt9Member, UnsignedInt9Member> PRED =
			new Procedure2<UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b) {
			if (a.v == 0)
				b.v = 511;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt9Member, UnsignedInt9Member> SUCC =
			new Procedure2<UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b) {
			if (a.v == 511)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> POW =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			PowerNonNegative.compute(G.UINT9, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt9Member> RAND =
			new Procedure1<UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(512) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt9Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> AND =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> OR =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> XOR =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt9Member, UnsignedInt9Member> NOT =
			new Procedure2<UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt9Member, UnsignedInt9Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> ANDNOT =
			new Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt9Member a, UnsignedInt9Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 9;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt9Member a, UnsignedInt9Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt9Member> MAXBOUND =
			new Procedure1<UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a) {
			a.v = 511;
		}
	};

	@Override
	public Procedure1<UnsignedInt9Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt9Member> MINBOUND =
			new Procedure1<UnsignedInt9Member>()
	{
		@Override
		public void call(UnsignedInt9Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt9Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt9Member> ISZERO =
			new Function1<Boolean, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt9Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt9Member b, UnsignedInt9Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt9Member b, UnsignedInt9Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt9Member, UnsignedInt9Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt9Member, UnsignedInt9Member> SBR =
			new Procedure3<RationalMember, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt9Member b, UnsignedInt9Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt9Member, UnsignedInt9Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member> SBD =
			new Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(Double a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member> SBDR =
			new Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(Double a, UnsignedInt9Member b, UnsignedInt9Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt9Member, UnsignedInt9Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> WITHIN =
			new Function3<Boolean, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt9Member tol, UnsignedInt9Member a, UnsignedInt9Member b) {
			return NumberWithin.compute(G.UINT9, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt9Member, UnsignedInt9Member, UnsignedInt9Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt9Member a, UnsignedInt9Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt9Member a, UnsignedInt9Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt9Member, UnsignedInt9Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt9Member> ISUNITY =
			new Function1<Boolean, UnsignedInt9Member>()
	{
		@Override
		public Boolean call(UnsignedInt9Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt9Member> isUnity() {
		return ISUNITY;
	}
}
