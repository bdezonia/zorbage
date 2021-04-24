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
package nom.bdezonia.zorbage.type.integer.int14;

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
public class UnsignedInt14Algebra
	implements
		Integer<UnsignedInt14Algebra, UnsignedInt14Member>,
		Bounded<UnsignedInt14Member>,
		BitOperations<UnsignedInt14Member>,
		Random<UnsignedInt14Member>,
		Tolerance<UnsignedInt14Member,UnsignedInt14Member>,
		ScaleByOneHalf<UnsignedInt14Member>,
		ScaleByTwo<UnsignedInt14Member>
{

	@Override
	public UnsignedInt14Member construct() {
		return new UnsignedInt14Member();
	}

	@Override
	public UnsignedInt14Member construct(UnsignedInt14Member other) {
		return new UnsignedInt14Member(other);
	}

	@Override
	public UnsignedInt14Member construct(String str) {
		return new UnsignedInt14Member(str);
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> EQ =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> NEQ =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt14Member, UnsignedInt14Member> ASSIGN =
			new Procedure2<UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt14Member> ZER =
			new Procedure1<UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt14Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> ADD =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> SUB =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> MUL =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt14Member a, UnsignedInt14Member b) {
			PowerNonNegative.compute(G.UINT14, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt14Member> UNITY =
			new Procedure1<UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt14Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> LESS =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> LE =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> GREAT =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> GE =
			new Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a, UnsignedInt14Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt14Member, UnsignedInt14Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt14Member a, UnsignedInt14Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt14Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt14Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt14Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt14Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> MIN =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> MAX =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> GCD =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			SteinGcd.compute(G.UINT14, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> LCM =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			SteinLcm.compute(G.UINT14, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt14Member> EVEN =
			new Function1<Boolean, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt14Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt14Member> ODD =
			new Function1<Boolean, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt14Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> DIV =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> MOD =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> DIVMOD =
			new Procedure4<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member d, UnsignedInt14Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt14Member, UnsignedInt14Member> PRED =
			new Procedure2<UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b) {
			if (a.v == 0)
				b.v = 16383;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt14Member, UnsignedInt14Member> SUCC =
			new Procedure2<UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b) {
			if (a.v == 16383)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> POW =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			PowerNonNegative.compute(G.UINT14, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt14Member> RAND =
			new Procedure1<UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(16384) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt14Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> AND =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> OR =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> XOR =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt14Member, UnsignedInt14Member> NOT =
			new Procedure2<UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt14Member, UnsignedInt14Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> ANDNOT =
			new Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt14Member a, UnsignedInt14Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 14;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt14Member a, UnsignedInt14Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt14Member> MAXBOUND =
			new Procedure1<UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a) {
			a.v = 16383;
		}
	};

	@Override
	public Procedure1<UnsignedInt14Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt14Member> MINBOUND =
			new Procedure1<UnsignedInt14Member>()
	{
		@Override
		public void call(UnsignedInt14Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt14Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt14Member> ISZERO =
			new Function1<Boolean, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt14Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt14Member b, UnsignedInt14Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt14Member b, UnsignedInt14Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt14Member, UnsignedInt14Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt14Member, UnsignedInt14Member> SBR =
			new Procedure3<RationalMember, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt14Member b, UnsignedInt14Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt14Member, UnsignedInt14Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member> SBD =
			new Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(Double a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member> SBDR =
			new Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(Double a, UnsignedInt14Member b, UnsignedInt14Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt14Member, UnsignedInt14Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> WITHIN =
			new Function3<Boolean, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt14Member tol, UnsignedInt14Member a, UnsignedInt14Member b) {
			return NumberWithin.compute(G.UINT14, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt14Member, UnsignedInt14Member, UnsignedInt14Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt14Member a, UnsignedInt14Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt14Member a, UnsignedInt14Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt14Member, UnsignedInt14Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt14Member> ISUNITY =
			new Function1<Boolean, UnsignedInt14Member>()
	{
		@Override
		public Boolean call(UnsignedInt14Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt14Member> isUnity() {
		return ISUNITY;
	}
}
