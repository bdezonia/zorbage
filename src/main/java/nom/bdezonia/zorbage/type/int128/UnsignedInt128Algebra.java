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
package nom.bdezonia.zorbage.type.int128;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.DivMod;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.Multiply;
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
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

import nom.bdezonia.zorbage.algebra.Integer;

//A note about how this was constructed: This class originally defined hi,
//lo, and masks in terms of bytes. There are only 64K such combinations. These
//numbers were then run through all the methods defined below and compared to
//java integer results exhaustively. Once all the kinks were worked out the
//bytes and masks were replaced by longs. We can now be confident that the
//code works for the larger range of values. Now we only sample the state
//space and try various combinations. But at its heart it relied on full
//testing.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt128Algebra
	implements
		Integer<UnsignedInt128Algebra, UnsignedInt128Member>,
		Bounded<UnsignedInt128Member>,
		BitOperations<UnsignedInt128Member>,
		Random<UnsignedInt128Member>,
		Tolerance<UnsignedInt128Member,UnsignedInt128Member>,
		ScaleByOneHalf<UnsignedInt128Member>,
		ScaleByTwo<UnsignedInt128Member>
{
	private static final UnsignedInt128Member ZERO = new UnsignedInt128Member();
	private static final UnsignedInt128Member ONE = new UnsignedInt128Member(0,1);

	@Override
	public UnsignedInt128Member construct() {
		return new UnsignedInt128Member();
	}

	@Override
	public UnsignedInt128Member construct(UnsignedInt128Member other) {
		return new UnsignedInt128Member(other);
	}

	@Override
	public UnsignedInt128Member construct(String str) {
		return new UnsignedInt128Member(str);
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> EQ =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return a.lo == b.lo && a.hi == b.hi;
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> NEQ =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt128Member,UnsignedInt128Member> ASSIGN =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member from, UnsignedInt128Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt128Member> ZER =
			new Procedure1<UnsignedInt128Member>()
	{
		
		@Override
		public void call(UnsignedInt128Member a) {
			a.hi = a.lo = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt128Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> ADD = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			long cLo = a.lo + b.lo;
			long cHi = a.hi + b.hi;
			int correction = 0;
			long alh = a.lo & 0x8000000000000000L;
			long blh = b.lo & 0x8000000000000000L;
			if (alh != 0 && blh != 0) {
				correction = 1;
			}
			else if ((alh != 0 && blh == 0) || (alh == 0 && blh != 0)) {
				long all = a.lo & 0x7fffffffffffffffL;
				long bll = b.lo & 0x7fffffffffffffffL;
				if ((all + bll) < 0)
					correction = 1;
			}
			cHi += correction;
			c.lo = cLo;
			c.hi = cHi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> SUB = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			long cHi = a.hi - b.hi;
			long cLo = a.lo - b.lo;
			final int correction;
			long alh = a.lo & 0x8000000000000000L;
			long blh = b.lo & 0x8000000000000000L;
			if (alh == 0 && blh != 0)
				correction = 1;
			else if (alh != 0 && blh == 0) {
				correction = 0;
			}
			else { // alh == blh
				long all = a.lo & 0x7fffffffffffffffL;
				long bll = b.lo & 0x7fffffffffffffffL;
				if (all < bll)
					correction = 1;
				else // (all >= bll)
					correction = 0;
			}
			cHi -= correction;
			c.lo = cLo;
			c.hi = cHi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> MUL = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			Multiply.compute(G.UINT128, a, b, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> POWER = 
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt128Member a, UnsignedInt128Member b) {
			PowerNonNegative.compute(G.UINT128, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt128Member> UNITY =
			new Procedure1<UnsignedInt128Member>()
	{
		
		@Override
		public void call(UnsignedInt128Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<UnsignedInt128Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> LESS =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return compare().call(a, b) < 0;
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> LE =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return compare().call(a, b) <= 0;
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> GREAT =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return compare().call(a, b) > 0;
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> GE =
			new Function2<Boolean, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a, UnsignedInt128Member b) {
			return compare().call(a, b) >= 0;
		}
	};

	@Override
	public Function2<Boolean,UnsignedInt128Member,UnsignedInt128Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt128Member a, UnsignedInt128Member b) {
			long along, blong;
			long ab = a.hi & 0x8000000000000000L;
			long bb = b.hi & 0x8000000000000000L;
			if (ab == 0 && bb != 0) {
				return -1;
			}
			else if (ab != 0 && bb == 0) {
				return 1;
			}
			else { // ab == bb
				along = a.hi & 0x7fffffffffffffffL;
				blong = b.hi & 0x7fffffffffffffffL;
				if (along < blong)
					return -1;
				else if (along > blong)
					return 1;
				else { // a.hi == b.hi
					ab = a.lo & 0x8000000000000000L;
					bb = b.lo & 0x8000000000000000L;
					if (ab == 0 && bb != 0) {
						return -1;
					}
					else if (ab != 0 && bb == 0) {
						return 1;
					}
					else { // ab == bb
						along = a.lo & 0x7fffffffffffffffL;
						blong = b.lo & 0x7fffffffffffffffL;
						if (along < blong)
							return -1;
						else if (along > blong)
							return 1;
						else
							return 0;
					}
				}
			}
		}
	};

	@Override
	public Function2<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnsignedInt128Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt128Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt128Member a) {
			if (isEqual().call(a, ZERO)) return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,UnsignedInt128Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> MIN = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			Min.compute(G.UINT128, a, b, c);
		}
	};
	
	@Override
	public final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> MAX = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			Max.compute(G.UINT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> DIV = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member d) {
			UnsignedInt128Member m = new UnsignedInt128Member();
			divMod().call(a,b,d,m);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> MOD = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member m) {
			UnsignedInt128Member d = new UnsignedInt128Member();
			divMod().call(a,b,d,m);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> DIVMOD =
			new Procedure4<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member d, UnsignedInt128Member m) {
			DivMod.compute(G.UINT128, a, b, d, m);
		}
	};
	
	@Override
	public Procedure4<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> GCD = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			SteinGcd.compute(G.UINT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> LCM = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			SteinLcm.compute(G.UINT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean,UnsignedInt128Member> EVEN =
			new Function1<Boolean, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a) {
			return (a.lo & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt128Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,UnsignedInt128Member> ODD =
			new Function1<Boolean, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a) {
			return (a.lo & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt128Member> isOdd() {
		return ODD;
	}

	private final Procedure2<UnsignedInt128Member,UnsignedInt128Member> PRED =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			subtract().call(a, ONE, b);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt128Member,UnsignedInt128Member> SUCC =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			add().call(a, ONE, b);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> POW = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			UnsignedInt128Member tmp = new UnsignedInt128Member(ONE);
			UnsignedInt128Member pow = new UnsignedInt128Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt128Member> RAND =
			new Procedure1<UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a) {
			// Safely generate a random long in the right range
			// I'm avoiding nextLong() because it uses a 48-bit generator so it can't cover the
			// entire space. This might not either but it might be a better approach.
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			BigInteger bi = new BigInteger(64, rng);
			a.lo = bi.longValue();
			bi = new BigInteger(64, rng);
			a.hi = bi.longValue();
		}
	};
	
	@Override
	public Procedure1<UnsignedInt128Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> BITAND = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			c.lo = a.lo & b.lo;
			c.hi = a.hi & b.hi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> BITOR = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			c.lo = a.lo | b.lo;
			c.hi = a.hi | b.hi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> BITXOR = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			c.lo = a.lo ^ b.lo;
			c.hi = a.hi ^ b.hi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<UnsignedInt128Member,UnsignedInt128Member> BITNOT =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			b.lo = ~a.lo;
			b.hi = ~a.hi;
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> BITANDNOT = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			c.lo = a.lo & ~b.lo;
			c.hi = a.hi & ~b.hi;
		}
	};
	
	@Override
	public Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> bitAndNot() {
		return BITANDNOT;
	}

	// TODO improve performance
	
	private final Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> BITSHL = 
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt128Member a, UnsignedInt128Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 0x80;
				UnsignedInt128Member tmp = new UnsignedInt128Member(a);
				for (int i = 0; i < count; i++) {
					shiftLeftOneBit(tmp);
				}
				assign().call(tmp, b);
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> bitShiftLeft() {
		return BITSHL;
	}

	// TODO improve performance
	
	private final Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> BITSHRZ = 
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt128Member a, UnsignedInt128Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else if (count > 0x7f)
				assign().call(ZERO, b);
			else {
				UnsignedInt128Member tmp = new UnsignedInt128Member(a);
				for (int i = 0; i < count; i++) {
					shiftRightOneBit(tmp);
				}
				assign().call(tmp, b);
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return BITSHRZ;
	}

	private final Procedure1<UnsignedInt128Member> MAXBOUND =
			new Procedure1<UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a) {
			a.lo = -1;
			a.hi = -1;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt128Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt128Member> MINBOUND =
			new Procedure1<UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a) {
			a.lo = 0;
			a.hi = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt128Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt128Member> ISZERO =
			new Function1<Boolean, UnsignedInt128Member>()
	{
		@Override
		public Boolean call(UnsignedInt128Member a) {
			return a.lo == 0 && a.hi == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt128Member> isZero() {
		return ISZERO;
	}

	private void shiftLeftOneBit(UnsignedInt128Member val) {
		boolean transitionBit = (val.lo & 0x8000000000000000L) != 0;
		val.lo = val.lo << 1;
		val.hi = val.hi << 1;
		if (transitionBit)
			val.hi |= 1;
	}

	private void shiftRightOneBit(UnsignedInt128Member val) {
		boolean transitionBit = (val.hi & 1) != 0;
		val.lo = val.lo >>> 1;
		val.hi = val.hi >>> 1;
		if (transitionBit)
			val.lo |= 0x8000000000000000L;
	}

	@Override
	public Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt128Member b, UnsignedInt128Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt128Member b, UnsignedInt128Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(C.ONE_HALF);
			else
				tmp = tmp.add(C.ONE_HALF);
			c.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt128Member, UnsignedInt128Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt128Member, UnsignedInt128Member> SBR =
			new Procedure3<RationalMember, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt128Member b, UnsignedInt128Member c) {
			BigInteger tmp = b.v();
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt128Member, UnsignedInt128Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member> SBD =
			new Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(Double a, UnsignedInt128Member b, UnsignedInt128Member c) {
			BigDecimal tmp = new BigDecimal(b.v());
			tmp = tmp.multiply(BigDecimal.valueOf(a));
			c.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member> SBDR =
			new Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(Double a, UnsignedInt128Member b, UnsignedInt128Member c) {
			BigDecimal tmp = new BigDecimal(b.v());
			tmp = tmp.multiply(BigDecimal.valueOf(a));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(C.ONE_HALF);
			else
				tmp = tmp.add(C.ONE_HALF);
			c.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt128Member, UnsignedInt128Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member> WITHIN =
			new Function3<Boolean, UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt128Member tol, UnsignedInt128Member a, UnsignedInt128Member b) {
			return NumberWithin.compute(G.UINT128, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt128Member a, UnsignedInt128Member b) {
			bitShiftLeft().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt128Member a, UnsignedInt128Member b) {
			bitShiftRight().call(numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member> scaleByOneHalf() {
		return SHALF;
	}

}
