/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int128;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.PowerI;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt128Group
	implements
		Integer<UnsignedInt128Group, UnsignedInt128Member>,
		Bounded<UnsignedInt128Member>,
		BitOperations<UnsignedInt128Member>,
		Random<UnsignedInt128Member>
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

	private Procedure2<UnsignedInt128Member,UnsignedInt128Member> ASSIGN =
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

	private Procedure1<UnsignedInt128Member> ZER =
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

	// TODO: this shows that unsigned numbers aren't quite ints. They should derive slightly differently
	// in the algebra hierarchy.
	
	private Procedure2<UnsignedInt128Member,UnsignedInt128Member> NEG =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			assign().call(a,b); // ignore
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> negate() {
		return NEG;
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
			UnsignedInt128Member bTmp = new UnsignedInt128Member(b);
			UnsignedInt128Member tmp = new UnsignedInt128Member();
			UnsignedInt128Member part = new UnsignedInt128Member(a);
			while (isNotEqual().call(bTmp,ZERO)) {
				if ((bTmp.lo & 1) != 0) {
					add().call(tmp, part, tmp);
				}
				shiftLeftOneBit(part);
				shiftRightOneBit(bTmp);
			}
			assign().call(tmp,c);
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
			PowerI.compute(G.UINT128, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> power() {
		return POWER;
	}

	private Procedure1<UnsignedInt128Member> UNITY =
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
			return compare().call(a,b) < 0;
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
			return compare().call(a,b) <= 0;
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
			return compare().call(a,b) > 0;
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
			return compare().call(a,b) >= 0;
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
			if (isEqual().call(b, ZERO)) {
				throw new IllegalArgumentException("divide by zero error in UnsignedInt128Group");
			}
			int comparison = compare().call(a,b);
			if (comparison == 0) { // a is equal b
				assign().call(ONE, d);
				assign().call(ZERO, m);
				return;
			}
			if (comparison < 0) { // a is less than b
				assign().call(ZERO, d);
				assign().call(a, m);
				return;
			}
			// if here a is greater than b
			UnsignedInt128Member quotient = new UnsignedInt128Member();
			UnsignedInt128Member dividend = new UnsignedInt128Member(a);
			UnsignedInt128Member divisor = new UnsignedInt128Member(b);
			int dividendLeadingNonzeroBit = leadingNonZeroBit(a);
			int divisorLeadingNonzeroBit = leadingNonZeroBit(b);
			bitShiftLeft().call((dividendLeadingNonzeroBit - divisorLeadingNonzeroBit), divisor, divisor);
			for (int i = 0; i < dividendLeadingNonzeroBit-divisorLeadingNonzeroBit+1; i++) {
				shiftLeftOneBit(quotient);
				if (isGreaterEqual().call(dividend, divisor)) {
					subtract().call(dividend, divisor, dividend);
					quotient.lo |= 1;
				}
				shiftRightOneBit(divisor);
			}
			assign().call(quotient, d);
			assign().call(dividend, m);
		}
	};
	
	@Override
	public Procedure4<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> divMod() {
		return DIVMOD;
	}

	private int leadingNonZeroBit(UnsignedInt128Member num) {
		long mask = 0x8000000000000000L;
		for (int i = 0; i < 64; i++) {
			if ((num.hi & mask) != 0) {
				return 127 - i;
			}
			mask >>>= 1;
		}
		mask = 0x8000000000000000L;
		for (int i = 0; i < 64; i++) {
			if ((num.lo & mask) != 0) {
				return 63 - i;
			}
			mask >>>= 1;
		}
		return -1;
	}
	
	private final Procedure3<UnsignedInt128Member,UnsignedInt128Member,UnsignedInt128Member> GCD = 
			new Procedure3<UnsignedInt128Member, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
			Gcd.compute(G.UINT128, a, b, c);
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
			Lcm.compute(G.UINT128, a, b, c);
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

	private Procedure2<UnsignedInt128Member,UnsignedInt128Member> PRED =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			subtract().call(a,ONE,b);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt128Member,UnsignedInt128Member> pred() {
		return PRED;
	}

	private Procedure2<UnsignedInt128Member,UnsignedInt128Member> SUCC =
			new Procedure2<UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a, UnsignedInt128Member b) {
			add().call(a,ONE,b);
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

	private Procedure1<UnsignedInt128Member> RAND =
			new Procedure1<UnsignedInt128Member>()
	{
		@Override
		public void call(UnsignedInt128Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.lo = rng.nextLong();
			a.hi = rng.nextLong();
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

	private Procedure2<UnsignedInt128Member,UnsignedInt128Member> BITNOT =
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
				bitShiftRight().call(Math.abs(count), a, b);
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
	
	private final Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> BITSHR = 
			new Procedure3<java.lang.Integer, UnsignedInt128Member, UnsignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt128Member a, UnsignedInt128Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
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
	public Procedure3<java.lang.Integer,UnsignedInt128Member,UnsignedInt128Member> bitShiftRight() {
		return BITSHR;
	}

	private Procedure1<UnsignedInt128Member> MAXBOUND =
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

	private Procedure1<UnsignedInt128Member> MINBOUND =
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

}
