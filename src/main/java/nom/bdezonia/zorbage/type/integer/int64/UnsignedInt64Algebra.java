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
package nom.bdezonia.zorbage.type.integer.int64;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.DivMod;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
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
public class UnsignedInt64Algebra
	implements
		Integer<UnsignedInt64Algebra, UnsignedInt64Member>,
		Bounded<UnsignedInt64Member>,
		BitOperations<UnsignedInt64Member>,
		Random<UnsignedInt64Member>,
		Tolerance<UnsignedInt64Member,UnsignedInt64Member>,
		ScaleByOneHalf<UnsignedInt64Member>,
		ScaleByTwo<UnsignedInt64Member>,
		ConstructibleFromBigInteger<UnsignedInt64Member>,
		ConstructibleFromLong<UnsignedInt64Member>,
		Conjugate<UnsignedInt64Member>
{
	private static final UnsignedInt64Member ZERO = new UnsignedInt64Member();
	private static final UnsignedInt64Member ONE = new UnsignedInt64Member(BigInteger.ONE);
	
	@Override
	public String typeDescription() {
		return "64-bit unsigned int";
	}

	public UnsignedInt64Algebra() { }

	@Override
	public UnsignedInt64Member construct() {
		return new UnsignedInt64Member();
	}

	@Override
	public UnsignedInt64Member construct(UnsignedInt64Member other) {
		return new UnsignedInt64Member(other);
	}

	@Override
	public UnsignedInt64Member construct(String s) {
		return new UnsignedInt64Member(s);
	}

	@Override
	public UnsignedInt64Member construct(BigInteger... vals) {
		return new UnsignedInt64Member(vals[0]);
	}

	@Override
	public UnsignedInt64Member construct(long... vals) {
		return new UnsignedInt64Member(vals[0]);
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> EQ =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return a.v == b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> NEQ =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return a.v != b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt64Member,UnsignedInt64Member> ASSIGN =
			new Procedure2<UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member from, UnsignedInt64Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> assign() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> abs() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> MUL =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v * b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt64Member a, UnsignedInt64Member b) {
			PowerNonNegative.compute(G.UINT64, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt64Member> ZER =
			new Procedure1<UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt64Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> ADD =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v + b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> SUB =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v - b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> subtract() {
		return SUB;
	}

	private final Procedure1<UnsignedInt64Member> UNITY =
			new Procedure1<UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a) {
			a.v = 1;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt64Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> LESS =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> LE =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> GREAT =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> GE =
			new Function2<Boolean, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a, UnsignedInt64Member b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt64Member,UnsignedInt64Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt64Member a, UnsignedInt64Member b) {
			long aHi = a.v & 0x8000000000000000L;
			long bHi = b.v & 0x8000000000000000L;
			if (aHi == 0 && bHi != 0) return -1;
			if (aHi != 0 && bHi == 0) return 1;
			long aLo = a.v & 0x7fffffffffffffffL;
			long bLo = b.v & 0x7fffffffffffffffL;
			if (aLo < bLo) return -1;
			if (aLo > bLo) return 1;
			return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnsignedInt64Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt64Member a) {
			if (a.v == 0) return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,UnsignedInt64Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> DIV =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member d) {
			UnsignedInt64Member m = G.UINT64.construct();
			DivMod.compute(G.UINT64, a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> MOD =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member m) {
			UnsignedInt64Member d = G.UINT64.construct();
			DivMod.compute(G.UINT64, a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> DIVMOD =
			new Procedure4<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member d, UnsignedInt64Member m) {
			DivMod.compute(G.UINT64, a, b, d, m);
		}
	};
	
	@Override
	public Procedure4<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> GCD =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			SteinGcd.compute(G.UINT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> LCM =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			SteinLcm.compute(G.UINT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> norm() {
		return ASSIGN;
	}

	private final Function1<Boolean,UnsignedInt64Member> EVEN =
			new Function1<Boolean,UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a) {
			return (a.v & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt64Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,UnsignedInt64Member> ODD =
			new Function1<Boolean,UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a) {
			return (a.v & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt64Member> isOdd() {
		return ODD;
	}

	private final Procedure2<UnsignedInt64Member,UnsignedInt64Member> PRED =
			new Procedure2<UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b) {
			if (a.v == 0)
				b.v = -1;
			else
				b.v = a.v - 1;
		}
	};
	
	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt64Member,UnsignedInt64Member> SUCC =
			new Procedure2<UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b) {
			if (a.v == -1)
				b.v = 0;
			else
				b.v = a.v + 1;
		}
	};
	
	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> succ() {
		return SUCC;
	}

	private final Procedure1<UnsignedInt64Member> MAXBOUND =
			new Procedure1<UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a) {
			a.v = -1;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt64Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt64Member> MINBOUND =
			new Procedure1<UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt64Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> BITAND =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v & b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> BITOR =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v | b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> BITXOR =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v ^ b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<UnsignedInt64Member,UnsignedInt64Member> BITNOT =
			new Procedure2<UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b) {
			b.v = ~a.v;
		}
	};
	
	@Override
	public Procedure2<UnsignedInt64Member,UnsignedInt64Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> BITANDNOT =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			c.v = a.v & ~b.v;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> BITSHL =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt64Member a, UnsignedInt64Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 64;
				b.v = a.v << count;
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> bitShiftLeft() {
		return BITSHL;
	}

	public Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return BITSHRZ;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> BITSHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt64Member a, UnsignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else if (count == 0)
				b.v = a.v;
			else {
				// count >= 1
				long val = (0x7FFFFFFFFFFFFFFFL & a.v) >> 1;
				if (a.v < 0) val |= 0x4000000000000000L;
				b.v = val >> (count-1);
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> MIN =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			Min.compute(G.UINT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> MAX =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			Max.compute(G.UINT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> max() {
		return MAX;
	}

	private final Procedure1<UnsignedInt64Member> RAND =
			new Procedure1<UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a) {
			// Safely generate a random long in the right range
			// I'm avoiding nextLong() because it uses a 48-bit generator so it can't cover the
			// entire space. This might not either but it might be a better approach.
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			BigInteger bi = new BigInteger(64, rng);
			a.v = bi.longValue();
		}
	};
	
	@Override
	public Procedure1<UnsignedInt64Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> POW =
			new Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			UnsignedInt64Member tmp = new UnsignedInt64Member(ONE);
			UnsignedInt64Member pow = new UnsignedInt64Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt64Member,UnsignedInt64Member,UnsignedInt64Member> pow() {
		return POW;
	}

	private final Function1<Boolean, UnsignedInt64Member> ISZERO =
			new Function1<Boolean, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, UnsignedInt64Member a, UnsignedInt64Member b) {
			BigDecimal tmp = factor.v();
			tmp = tmp.multiply(new BigDecimal(a.v()));
			b.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, UnsignedInt64Member a, UnsignedInt64Member b) {
			BigDecimal tmp = factor.v();
			tmp = tmp.multiply(new BigDecimal(a.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			b.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt64Member, UnsignedInt64Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt64Member, UnsignedInt64Member> SBR =
			new Procedure3<RationalMember, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(RationalMember factor, UnsignedInt64Member a, UnsignedInt64Member b) {
			BigInteger tmp = a.v();
			tmp = tmp.multiply(factor.n());
			tmp = tmp.divide(factor.d());
			b.setV(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt64Member, UnsignedInt64Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member> SBD =
			new Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(Double factor, UnsignedInt64Member a, UnsignedInt64Member b) {
			BigDecimal tmp = new BigDecimal(a.v());
			tmp = tmp.multiply(BigDecimal.valueOf(factor));
			b.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member> SBDR =
			new Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(Double factor, UnsignedInt64Member a, UnsignedInt64Member b) {
			BigDecimal tmp = new BigDecimal(a.v());
			tmp = tmp.multiply(BigDecimal.valueOf(factor));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			b.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt64Member, UnsignedInt64Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member> WITHIN =
			new Function3<Boolean, UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt64Member tol, UnsignedInt64Member a, UnsignedInt64Member b) {
			return NumberWithin.compute(G.UINT64, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt64Member, UnsignedInt64Member, UnsignedInt64Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt64Member a, UnsignedInt64Member b) {
			bitShiftLeft().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt64Member a, UnsignedInt64Member b) {
			bitShiftRight().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt64Member> ISUNITY =
			new Function1<Boolean, UnsignedInt64Member>()
	{
		@Override
		public Boolean call(UnsignedInt64Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt64Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<UnsignedInt64Member, UnsignedInt64Member> conjugate() {
		return ASSIGN;
	}
}
