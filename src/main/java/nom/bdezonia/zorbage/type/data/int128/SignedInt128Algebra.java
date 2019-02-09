/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
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
	private static final SignedInt128Member ONE = new SignedInt128Member((byte)0,(byte)1);

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
			if (a.hi == (byte)0x80 && a.lo == 0)
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
//			byte cLo = (byte)(a.lo + b.lo);
//			byte cHi = (byte)(a.hi + b.hi);
//			int correction = 0;
//			byte alh = (byte)(a.lo & 0x80);
//			byte blh = (byte)(b.lo & 0x80);
//			if (alh != 0 && blh != 0) {
//				correction = 1;
//			}
//			else if ((alh != 0 && blh == 0) || (alh == 0 && blh != 0)) {
//				byte all = (byte)(a.lo & 0x7f);
//				byte bll = (byte)(b.lo & 0x7f);
//				if ((all + bll) < 0)
//					correction = 1;
//			}
//			cHi += correction;
//			c.lo = cLo;
//			c.hi = cHi;
			// TODO - replace me with above commented out code and debug it when I can
			// define a passing test with this stopgap
			c.setV(a.v().add(b.v()));
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
			byte cHi = (byte)(a.hi - b.hi);
			byte cLo = (byte)(a.lo - b.lo);
			final int correction;
			byte alh = (byte)(a.lo & 0x80);
			byte blh = (byte)(b.lo & 0x80);
			if (alh == 0 && blh != 0)
				correction = 1;
			else if (alh != 0 && blh == 0) {
				correction = 0;
			}
			else { // alh == blh
				byte all = (byte)(a.lo & 0x7f);
				byte bll = (byte)(b.lo & 0x7f);
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
			byte along, blong;
			byte ab = (byte) (a.hi & 0x80);
			byte bb = (byte) (b.hi & 0x80);
			if (ab == 0 && bb != 0) {
				return 1;
			}
			else if (ab != 0 && bb == 0) {
				return -1;
			}
			else { // ab == bb
				along = (byte) (a.hi & 0x7f);
				blong = (byte) (b.hi & 0x7f);
				if (along < blong)
					return -1;
				else if (along > blong)
					return 1;
				else { // a.hi == b.hi
					ab = (byte) (a.lo & 0x80);
					bb = (byte) (b.lo & 0x80);
					if (ab == 0 && bb != 0) {
						return -1;
					}
					else if (ab != 0 && bb == 0) {
						return 1;
					}
					else { // ab == bb
						along = (byte) (a.lo & 0x7f);
						blong = (byte) (b.lo & 0x7f);
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
			else if ((a.hi & 0x80) != 0)
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
			// TODO - make a faster version that uses primitives
			BigInteger[] dm = a.v().divideAndRemainder(b.v());
			d.setV(dm[0]);
			m.setV(dm[1]);
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
			a.lo = (byte) rng.nextInt(0x100);
			a.hi = (byte) rng.nextInt(0x100);
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
			c.lo = (byte)(a.lo & b.lo);
			c.hi = (byte)(a.hi & b.hi);
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
			c.lo = (byte) (a.lo | b.lo);
			c.hi = (byte) (a.hi | b.hi);
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
			c.lo = (byte)(a.lo ^ b.lo);
			c.hi = (byte)(a.hi ^ b.hi);
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
			b.lo = (byte)(~a.lo);
			b.hi = (byte)(~a.hi);
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
			c.lo = (byte)(a.lo & ~b.lo);
			c.hi = (byte)(a.hi & ~b.hi);
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
				count = count % 16;
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
					if (tmp.hi == (byte)0xff && tmp.lo == (byte)0xff)
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
			else if (count > 15)
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
	
	public Procedure3<java.lang.Integer,SignedInt128Member,SignedInt128Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private Procedure1<SignedInt128Member> MAXBOUND =
			new Procedure1<SignedInt128Member>()
	{
		@Override
		public void call(SignedInt128Member a) {
			a.lo = (byte)0xff;
			a.hi = (byte)0x7f;
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
			a.hi = (byte)0x80;
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
		boolean transitionBit = (val.lo & (byte)0x80) != 0;
		val.lo = (byte)((val.lo) << 1);
		val.hi = (byte)((val.hi) << 1);
		if (transitionBit)
			val.hi |= (byte)1;
	}

	private void shiftRightOneBit(SignedInt128Member val) {
		//System.out.println("  in val.hi="+(val.hi & 0xff)+" val.lo="+(val.lo & 0xff));
		boolean transitionBit = (val.hi & 1) != 0;
		boolean loHBit = (val.lo & (byte)0x80) != 0;
		val.lo = (byte)((val.lo & 0x7f) >>> 1);
		if (loHBit) val.lo |= 0x40;
		boolean hiHBit = (val.hi & (byte)0x80) != 0;
		val.hi = (byte)((val.hi & 0x7f) >>> 1);
		if (hiHBit) val.hi |= 0x40;
		if (transitionBit)
			val.lo |= (byte)0x80;
		//System.out.println("  out val.hi="+(val.hi & 0xff)+" val.lo="+(val.lo & 0xff));
	}

	@Override
	public Procedure3<SignedInt128Member, SignedInt128Member, SignedInt128Member> scale() {
		return MUL;
	}

}
