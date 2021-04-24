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
package nom.bdezonia.zorbage.type.integer.int7;

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
public class UnsignedInt7Algebra
	implements
		Integer<UnsignedInt7Algebra, UnsignedInt7Member>,
		Bounded<UnsignedInt7Member>,
		BitOperations<UnsignedInt7Member>,
		Random<UnsignedInt7Member>,
		Tolerance<UnsignedInt7Member,UnsignedInt7Member>,
		ScaleByOneHalf<UnsignedInt7Member>,
		ScaleByTwo<UnsignedInt7Member>
{

	@Override
	public UnsignedInt7Member construct() {
		return new UnsignedInt7Member();
	}

	@Override
	public UnsignedInt7Member construct(UnsignedInt7Member other) {
		return new UnsignedInt7Member(other);
	}

	@Override
	public UnsignedInt7Member construct(String str) {
		return new UnsignedInt7Member(str);
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> EQ =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> NEQ =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt7Member, UnsignedInt7Member> ASSIGN =
			new Procedure2<UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt7Member> ZER =
			new Procedure1<UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt7Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> ADD =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> SUB =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> MUL =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt7Member a, UnsignedInt7Member b) {
			PowerNonNegative.compute(G.UINT7, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt7Member> UNITY =
			new Procedure1<UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt7Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> LESS =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> LE =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> GREAT =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> GE =
			new Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a, UnsignedInt7Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt7Member, UnsignedInt7Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt7Member a, UnsignedInt7Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt7Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt7Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt7Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt7Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> MIN =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> MAX =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> GCD =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			SteinGcd.compute(G.UINT7, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> LCM =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			SteinLcm.compute(G.UINT7, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt7Member> EVEN =
			new Function1<Boolean, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt7Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt7Member> ODD =
			new Function1<Boolean, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt7Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> DIV =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> MOD =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> DIVMOD =
			new Procedure4<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member d, UnsignedInt7Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt7Member, UnsignedInt7Member> PRED =
			new Procedure2<UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b) {
			if (a.v == 0)
				b.v = 127;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt7Member, UnsignedInt7Member> SUCC =
			new Procedure2<UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b) {
			if (a.v == 127)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> POW =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			PowerNonNegative.compute(G.UINT7, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt7Member> RAND =
			new Procedure1<UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(128) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt7Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> AND =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> OR =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> XOR =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt7Member, UnsignedInt7Member> NOT =
			new Procedure2<UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt7Member, UnsignedInt7Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> ANDNOT =
			new Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt7Member a, UnsignedInt7Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 7;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt7Member a, UnsignedInt7Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt7Member> MAXBOUND =
			new Procedure1<UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a) {
			a.v = 127;
		}
	};

	@Override
	public Procedure1<UnsignedInt7Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt7Member> MINBOUND =
			new Procedure1<UnsignedInt7Member>()
	{
		@Override
		public void call(UnsignedInt7Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt7Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt7Member> ISZERO =
			new Function1<Boolean, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt7Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt7Member b, UnsignedInt7Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt7Member b, UnsignedInt7Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt7Member, UnsignedInt7Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt7Member, UnsignedInt7Member> SBR =
			new Procedure3<RationalMember, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt7Member b, UnsignedInt7Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt7Member, UnsignedInt7Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member> SBD =
			new Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(Double a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member> SBDR =
			new Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(Double a, UnsignedInt7Member b, UnsignedInt7Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt7Member, UnsignedInt7Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> WITHIN =
			new Function3<Boolean, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt7Member tol, UnsignedInt7Member a, UnsignedInt7Member b) {
			return NumberWithin.compute(G.UINT7, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt7Member, UnsignedInt7Member, UnsignedInt7Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt7Member a, UnsignedInt7Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt7Member a, UnsignedInt7Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt7Member, UnsignedInt7Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt7Member> ISUNITY =
			new Function1<Boolean, UnsignedInt7Member>()
	{
		@Override
		public Boolean call(UnsignedInt7Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt7Member> isUnity() {
		return ISUNITY;
	}
}
