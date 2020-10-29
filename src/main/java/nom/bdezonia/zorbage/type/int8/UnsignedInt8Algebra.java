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
package nom.bdezonia.zorbage.type.int8;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
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
public class UnsignedInt8Algebra
	implements
		Integer<UnsignedInt8Algebra, UnsignedInt8Member>,
		Bounded<UnsignedInt8Member>,
		BitOperations<UnsignedInt8Member>,
		Random<UnsignedInt8Member>,
		Tolerance<UnsignedInt8Member,UnsignedInt8Member>,
		ScaleByOneHalf<UnsignedInt8Member>,
		ScaleByTwo<UnsignedInt8Member>
{

	public UnsignedInt8Algebra() { }
	
	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> EQ =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return a.v == b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> NEQ =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return a.v != b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public UnsignedInt8Member construct() {
		return new UnsignedInt8Member();
	}

	@Override
	public UnsignedInt8Member construct(UnsignedInt8Member other) {
		return new UnsignedInt8Member(other);
	}

	@Override
	public UnsignedInt8Member construct(String s) {
		return new UnsignedInt8Member(s);
	}
	
	private final Procedure2<UnsignedInt8Member,UnsignedInt8Member> ASSIGN =
			new Procedure2<UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member from, UnsignedInt8Member to) {
			to.set( from );
		}
	};

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> assign() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> abs() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> MUL =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt8Member a, UnsignedInt8Member b) {
			PowerNonNegative.compute(G.UINT8, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt8Member> ZER =
			new Procedure1<UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a) {
			a.setV(0);
		}
	};
	
	@Override
	public Procedure1<UnsignedInt8Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> ADD =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v + b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> SUB =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v - b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> subtract() {
		return SUB;
	}

	private final Procedure1<UnsignedInt8Member> UNITY =
			new Procedure1<UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a) {
			a.setV(1);
		}
	};
	
	@Override
	public Procedure1<UnsignedInt8Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> LESS =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> LE =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> GREAT =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> GE =
			new Function2<Boolean, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a, UnsignedInt8Member b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt8Member a, UnsignedInt8Member b) {
			int av = a.v();
			int bv = b.v();
			if (av < bv) return -1;
			if (av > bv) return 1;
			return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnsignedInt8Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt8Member a) {
			if (a.v == 0) return 0;
			return 1;
		}
	};
	@Override
	public Function1<java.lang.Integer,UnsignedInt8Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> DIV =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> MOD =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> DIVMOD =
			new Procedure4<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member d, UnsignedInt8Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> GCD =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			SteinGcd.compute(G.UINT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> LCM =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			SteinLcm.compute(G.UINT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> norm() {
		return ASSIGN;
	}

	private final Function1<Boolean,UnsignedInt8Member> EVEN =
			new Function1<Boolean, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a) {
			return (a.v & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt8Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,UnsignedInt8Member> ODD =
			new Function1<Boolean, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a) {
			return (a.v & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt8Member> isOdd() {
		return ODD;
	}

	private final Procedure2<UnsignedInt8Member,UnsignedInt8Member> PRED =
			new Procedure2<UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b) {
			if (a.v == 0)
				b.setV(255);
			else
				b.setV( a.v() - 1 );
		}
	};

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt8Member,UnsignedInt8Member> SUCC =
			new Procedure2<UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b) {
			if (a.v == -1)
				b.setV(0);
			else
				b.setV( a.v() + 1 );
		}
	};

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> succ() {
		return SUCC;
	}

	private final Procedure1<UnsignedInt8Member> MAXBOUND =
			new Procedure1<UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a) {
			a.setV( 255 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt8Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt8Member> MINBOUND =
			new Procedure1<UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt8Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> BITAND =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v & b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> BITOR =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v | b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> BITXOR =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v ^ b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<UnsignedInt8Member,UnsignedInt8Member> BITNOT =
			new Procedure2<UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b) {
			b.setV( ~a.v );
		}
	};

	@Override
	public Procedure2<UnsignedInt8Member,UnsignedInt8Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> BITANDNOT =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV( a.v & ~b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> BITSHL =
			new Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt8Member a, UnsignedInt8Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 8;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> bitShiftLeft() {
		return BITSHL;
	}

	@Override
	public  Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return BITSHRZ;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> BITSHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt8Member a, UnsignedInt8Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt8Member,UnsignedInt8Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> MIN =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			Min.compute(G.UINT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> MAX =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			Max.compute(G.UINT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> max() {
		return MAX;
	}

	private final Procedure1<UnsignedInt8Member> RAND =
			new Procedure1<UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(0x100));
		}
	};
	
	@Override
	public Procedure1<UnsignedInt8Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> POW =
			new Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
			power().call(b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt8Member,UnsignedInt8Member,UnsignedInt8Member> pow() {
		return POW;
	}

	private final Function1<Boolean, UnsignedInt8Member> ISZERO =
			new Function1<Boolean, UnsignedInt8Member>()
	{
		@Override
		public Boolean call(UnsignedInt8Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt8Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt8Member b, UnsignedInt8Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt8Member b, UnsignedInt8Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt8Member, UnsignedInt8Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt8Member, UnsignedInt8Member> SBR =
			new Procedure3<RationalMember, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt8Member b, UnsignedInt8Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt8Member, UnsignedInt8Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member> SBD =
			new Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(Double a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member> SBDR =
			new Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(Double a, UnsignedInt8Member b, UnsignedInt8Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt8Member, UnsignedInt8Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member> WITHIN =
			new Function3<Boolean, UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt8Member tol, UnsignedInt8Member a, UnsignedInt8Member b) {
			return NumberWithin.compute(G.UINT8, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt8Member, UnsignedInt8Member, UnsignedInt8Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt8Member a, UnsignedInt8Member b) {
			bitShiftLeft().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt8Member a, UnsignedInt8Member b) {
			bitShiftRight().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt8Member, UnsignedInt8Member> scaleByOneHalf() {
		return SHALF;
	}

}
