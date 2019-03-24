/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.DivMod;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.Multiply;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.data.bigdec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// A note about how this was constructed: This class originally defined hi,
// lo, and masks in terms of bytes. There are only 64K such combinations. These
// numbers were then run through all the methods defined below and compared to
// java integer results exhaustively. Once all the kinks were worked out the
// bytes and masks were replaced by longs. We can now be confident that the
// code works for the larger range of values. Now we only sample the state
// space and try various combinations. But at its heart it relied on full
// testing.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt128Algebra
	implements
		Integer<SignedInt128Algebra, SignedInt128Member>,
		Bounded<SignedInt128Member>,
		BitOperations<SignedInt128Member>,
		Random<SignedInt128Member>
{
	private static final SignedInt128Member ZERO = new SignedInt128Member();
	private static final SignedInt128Member ONE = new SignedInt128Member(0,1);

	@Override
	public SignedInt128Member construct() {
		return new SignedInt128Member();
	}

	@Override
	public SignedInt128Member construct(SignedInt128Member other) {
		return new SignedInt128Member(other);
	}

	@Override
	public SignedInt128Member construct(String str) {
		return new SignedInt128Member(str);
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> EQ =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return a.lo == b.lo && a.hi == b.hi;
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> NEQ =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isNotEqual() {
		return NEQ;
	}

	private Procedure2<SignedInt128Member,SignedInt128Member> ASSIGN =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member from, SignedInt128Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> assign() {
		return ASSIGN;
	}

	private Procedure1<SignedInt128Member> ZER =
			new Procedure1<SignedInt128Member>()
	{
		
		@Override
		public void call(SignedInt128Member a) {
			a.hi = a.lo = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt128Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt128Member,SignedInt128Member> NEG =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b) {
			if (a.hi == 0x8000000000000000L && a.lo == 0)
				throw new IllegalArgumentException("can't negate -minint");
			subtract().call(ZERO, a, b);
		}
	};

	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> ADD = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			long cLo = (a.lo + b.lo);
			long cHi = (a.hi + b.hi);
			int correction = 0;
			long alh = (a.lo & 0x8000000000000000L);
			long blh = (b.lo & 0x8000000000000000L);
			if (alh != 0 && blh != 0) {
				correction = 1;
			}
			else if ((alh != 0 && blh == 0) || (alh == 0 && blh != 0)) {
				long all = (a.lo & 0x7fffffffffffffffL);
				long bll = (b.lo & 0x7fffffffffffffffL);
				if ((all + bll) < 0)
					correction = 1;
			}
			cHi += correction;
			c.lo = cLo;
			c.hi = cHi;
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> SUB = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			long cHi = (a.hi - b.hi);
			long cLo = (a.lo - b.lo);
			final int correction;
			long alh = (a.lo & 0x8000000000000000L);
			long blh = (b.lo & 0x8000000000000000L);
			if (alh == 0 && blh != 0)
				correction = 1;
			else if (alh != 0 && blh == 0) {
				correction = 0;
			}
			else { // alh == blh
				long all = (a.lo & 0x7fffffffffffffffL);
				long bll = (b.lo & 0x7fffffffffffffffL);
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
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> MUL = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			Multiply.compute(G.INT128, a, b, c);
		}
	};

	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> POWER = 
			new Procedure3<java.lang.Integer, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt128Member a, SignedInt128Member b) {
			PowerNonNegative.compute(G.INT128, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> power() {
		return POWER;
	}

	private Procedure1<SignedInt128Member> UNITY =
			new Procedure1<SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<SignedInt128Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> LESS =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return compare().call(a,b) < 0;
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> LE =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return compare().call(a,b) <= 0;
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> GREAT =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return compare().call(a,b) > 0;
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,SignedInt128Member,SignedInt128Member> GE =
			new Function2<Boolean, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a, SignedInt128Member b) {
			return compare().call(a,b) >= 0;
		}
	};

	@Override
	public Function2<Boolean,SignedInt128Member,SignedInt128Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt128Member,SignedInt128Member> CMP =
			new Function2<java.lang.Integer, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt128Member a, SignedInt128Member b) {
			long along, blong;
			long ab = (a.hi & 0x8000000000000000L);
			long bb = (b.hi & 0x8000000000000000L);
			if (ab == 0 && bb != 0) {
				return 1;
			}
			else if (ab != 0 && bb == 0) {
				return -1;
			}
			else { // ab == bb
				along = (a.hi & 0x7fffffffffffffffL);
				blong = (b.hi & 0x7fffffffffffffffL);
				if (along < blong)
					return -1;
				else if (along > blong)
					return 1;
				else { // a.hi == b.hi
					ab = (a.lo & 0x8000000000000000L);
					bb = (b.lo & 0x8000000000000000L);
					if (ab == 0 && bb != 0) {
						return -1;
					}
					else if (ab != 0 && bb == 0) {
						return 1;
					}
					else { // ab == bb
						along = (a.lo & 0x7fffffffffffffffL);
						blong = (b.lo & 0x7fffffffffffffffL);
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
	public Function2<java.lang.Integer,SignedInt128Member,SignedInt128Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt128Member> SIG =
			new Function1<java.lang.Integer, SignedInt128Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt128Member a) {
			if (isEqual().call(a, ZERO))
				return 0;
			else if ((a.hi & 0x8000000000000000L) != 0)
				return -1;
			else
				return 1;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt128Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> MIN = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			Min.compute(G.INT128, a, b, c);
		}
	};
	
	@Override
	public final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> MAX = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			Max.compute(G.INT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt128Member,SignedInt128Member> ABS =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b) {
			if (compare().call(a, ZERO) < 0) {
				negate().call(a, b);
			}
			else {
				assign().call(a, b);
			}
		}
	};
			
	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> DIV = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member d) {
			SignedInt128Member m = new SignedInt128Member();
			divMod().call(a,b,d,m);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> MOD = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member m) {
			SignedInt128Member d = new SignedInt128Member();
			divMod().call(a,b,d,m);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt128Member,SignedInt128Member,SignedInt128Member,SignedInt128Member> DIVMOD =
			new Procedure4<SignedInt128Member, SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member d, SignedInt128Member m) {
			if (a.hi == 0x8000000000000000L && a.lo == 0 && b.hi == 0xffffffffffffffffL && b.lo == 0xffffffffffffffffL)
				throw new IllegalArgumentException("cannot divide -minint by -1");
			DivMod.compute(G.INT128, a, b, d, m);
		}
	};
	
	@Override
	public Procedure4<SignedInt128Member,SignedInt128Member,SignedInt128Member,SignedInt128Member> divMod() {
		return DIVMOD;
	}
	
	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> GCD = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			Gcd.compute(G.INT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> LCM = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			Lcm.compute(G.INT128, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean,SignedInt128Member> EVEN =
			new Function1<Boolean, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a) {
			return (a.lo & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt128Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt128Member> ODD =
			new Function1<Boolean, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a) {
			return (a.lo & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt128Member> isOdd() {
		return ODD;
	}

	private Procedure2<SignedInt128Member,SignedInt128Member> PRED =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b) {
			subtract().call(a,ONE,b);
		}
	};
	
	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> pred() {
		return PRED;
	}

	private Procedure2<SignedInt128Member,SignedInt128Member> SUCC =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b) {
			add().call(a,ONE,b);
		}
	};
	
	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> POW = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			SignedInt128Member tmp = new SignedInt128Member(ONE);
			SignedInt128Member pow = new SignedInt128Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> pow() {
		return POW;
	}

	private Procedure1<SignedInt128Member> RAND =
			new Procedure1<SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.lo = rng.nextLong();
			a.hi = rng.nextLong();
		}
	};
	
	@Override
	public Procedure1<SignedInt128Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> BITAND = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			c.lo = (a.lo & b.lo);
			c.hi = (a.hi & b.hi);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> BITOR = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			c.lo = (a.lo | b.lo);
			c.hi = (a.hi | b.hi);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> BITXOR = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			c.lo = (a.lo ^ b.lo);
			c.hi = (a.hi ^ b.hi);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> bitXor() {
		return BITXOR;
	}

	private Procedure2<SignedInt128Member,SignedInt128Member> BITNOT =
			new Procedure2<SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b) {
			b.lo = (~a.lo);
			b.hi = (~a.hi);
		}
	};
	
	@Override
	public Procedure2<SignedInt128Member,SignedInt128Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> BITANDNOT = 
			new Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a, SignedInt128Member b, SignedInt128Member c) {
			c.lo = (a.lo & ~b.lo);
			c.hi = (a.hi & ~b.hi);
		}
	};
	
	@Override
	public Procedure3<SignedInt128Member,SignedInt128Member,SignedInt128Member> bitAndNot() {
		return BITANDNOT;
	}

	// TODO improve performance
	
	private final Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> BITSHL = 
			new Procedure3<java.lang.Integer, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt128Member a, SignedInt128Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 128;
				SignedInt128Member tmp = new SignedInt128Member(a);
				for (int i = 0; i < count; i++) {
					shiftLeftOneBit(tmp);
				}
				assign().call(tmp, b);
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt128Member a, SignedInt128Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else {
				SignedInt128Member tmp = new SignedInt128Member(a);
				for (int i = 0; i < count; i++) {
					if (tmp.hi == 0 && tmp.lo == 0)
						break;
					if (tmp.hi == 0xffffffffffffffffL && tmp.lo == 0xffffffffffffffffL)
						break;
					shiftRightOneBit(tmp);
				}
				assign().call(tmp, b);
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> bitShiftRight() {
		return BITSHR;
	}

	// TODO improve performance
	
	private final Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> BITSHRZ = 
			new Procedure3<java.lang.Integer, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt128Member a, SignedInt128Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else if (count > 127)
				assign().call(ZERO, b);
			else {
				SignedInt128Member tmp = new SignedInt128Member(a);
				for (int i = 0; i < count; i++) {
					shiftRightOneBit(tmp);
				}
				assign().call(tmp, b);
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private Procedure1<SignedInt128Member> MAXBOUND =
			new Procedure1<SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a) {
			a.lo = 0xffffffffffffffffL;
			a.hi = 0x7fffffffffffffffL;
		}
	};
	
	@Override
	public Procedure1<SignedInt128Member> maxBound() {
		return MAXBOUND;
	}

	private Procedure1<SignedInt128Member> MINBOUND =
			new Procedure1<SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a) {
			a.lo = 0;
			a.hi = 0x8000000000000000L;
		}
	};
	
	@Override
	public Procedure1<SignedInt128Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt128Member> ISZERO =
			new Function1<Boolean, SignedInt128Member>()
	{
		@Override
		public Boolean call(SignedInt128Member a) {
			return a.lo == 0 && a.hi == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt128Member> isZero() {
		return ISZERO;
	}

	private void shiftLeftOneBit(SignedInt128Member val) {
		boolean transitionBit = (val.lo & 0x8000000000000000L) != 0;
		val.lo = ((val.lo) << 1);
		val.hi = ((val.hi) << 1);
		if (transitionBit)
			val.hi |= 1;
	}

	// TODO: why did UINT128 shift not require all the extra ops this one does?
	
	private void shiftRightOneBit(SignedInt128Member val) {
		boolean neg = (val.hi & (0x8000000000000000L)) != 0;
		boolean transitionBit = (val.hi & 1) != 0;
		boolean loHBit = (val.lo & 0x8000000000000000L) != 0;
		val.lo = ((val.lo & 0x7fffffffffffffffL) >>> 1);
		if (loHBit) val.lo |= 0x4000000000000000L;
		boolean hiHBit = (val.hi & 0x8000000000000000L) != 0;
		val.hi = ((val.hi & 0x7fffffffffffffffL) >>> 1);
		if (hiHBit) val.hi |= 0x4000000000000000L;
		if (transitionBit)
			val.lo |= 0x8000000000000000L;
		if (neg)
			val.hi |= 0x8000000000000000L;
	}

	@Override
	public Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt128Member, SignedInt128Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt128Member b, SignedInt128Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt128Member, SignedInt128Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, SignedInt128Member, SignedInt128Member> SBR =
			new Procedure3<RationalMember, SignedInt128Member, SignedInt128Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt128Member b, SignedInt128Member c) {
			BigInteger tmp = b.v();
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt128Member, SignedInt128Member> scaleByRational() {
		return SBR;
	}

}
