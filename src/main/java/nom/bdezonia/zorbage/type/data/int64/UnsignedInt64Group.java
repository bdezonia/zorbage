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
package nom.bdezonia.zorbage.type.data.int64;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
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
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt64Group
  implements
    Integer<UnsignedInt64Group, UnsignedInt64Member>,
    Bounded<UnsignedInt64Member>,
    BitOperations<UnsignedInt64Member>,
    Random<UnsignedInt64Member>
{
	private static final UnsignedInt64Member ZERO = new UnsignedInt64Member();
	private static final UnsignedInt64Member ONE = new UnsignedInt64Member(BigInteger.ONE);
	
	public UnsignedInt64Group() { }
	
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

	private Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> POWER =
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
			return compare().call(a,b) < 0;
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
			return compare().call(a,b) <= 0;
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
			return compare().call(a,b) > 0;
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
			return compare().call(a,b) >= 0;
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
			d.v = a.v / b.v;
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
			m.v = a.v % b.v;
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
			d.v = a.v / b.v;
			m.v = a.v % b.v;
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
			Gcd.compute(G.UINT64, a, b, c);
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
			Lcm.compute(G.UINT64, a, b, c);
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
				bitShiftRight().call(Math.abs(count), a, b);
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

	private final Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> BITSHR =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt64Member a, UnsignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.v = a.v >> count;
		}
	};

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt64Member,UnsignedInt64Member> BITSHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt64Member, UnsignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt64Member a, UnsignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.v = a.v >>> count;
		}
	};

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
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.v = rng.nextLong();
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


}
