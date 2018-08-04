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
package nom.bdezonia.zorbage.type.data.int32;

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
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt32Group
  implements
    Integer<UnsignedInt32Group, UnsignedInt32Member>,
    Bounded<UnsignedInt32Member>,
    BitOperations<UnsignedInt32Member>,
    Random<UnsignedInt32Member>
{
	private static final UnsignedInt32Member ONE = new UnsignedInt32Member(1);
	private static final UnsignedInt32Member ZERO = new UnsignedInt32Member();
	
	public UnsignedInt32Group() { }
	
	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> EQ =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return a.v == b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> NEQ =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return a.v != b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public UnsignedInt32Member construct() {
		return new UnsignedInt32Member();
	}

	@Override
	public UnsignedInt32Member construct(UnsignedInt32Member other) {
		return new UnsignedInt32Member(other);
	}

	@Override
	public UnsignedInt32Member construct(String s) {
		return new UnsignedInt32Member(s);
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> ASSIGN =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member from, UnsignedInt32Member to) {
			to.set( from );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> ABS =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b) {
			assign().call(a,b);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> abs() {
		return ABS;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> MUL =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt32Member a, UnsignedInt32Member b) {
			PowerI.compute(G.UINT32, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> power() {
		return POWER;
	}

	private Procedure1<UnsignedInt32Member> ZER =
			new Procedure1<UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt32Member> zero() {
		return ZER;
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> NEG =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b) {
			assign().call(a,b); // ignore
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> negate() {
		return NEG;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> ADD =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v + b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> SUB =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v - b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> subtract() {
		return SUB;
	}

	private Procedure1<UnsignedInt32Member> UNITY =
			new Procedure1<UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt32Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> LESS =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return compare().call(a,b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> LE =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return compare().call(a,b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> GREAT =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return compare().call(a,b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> GE =
			new Function2<Boolean, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a, UnsignedInt32Member b) {
			return compare().call(a,b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt32Member,UnsignedInt32Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt32Member a, UnsignedInt32Member b) {
			long av = a.v();
			long bv = b.v();
			if (av < bv) return -1;
			if (av > bv) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnsignedInt32Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt32Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt32Member a) {
			if (a.v == 0) return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,UnsignedInt32Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> DIV =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member d) {
			d.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> MOD =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member m) {
			m.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> DIVMOD =
			new Procedure4<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member d, UnsignedInt32Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> GCD =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			Gcd.compute(G.UINT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> LCM =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			Lcm.compute(G.UINT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> norm() {
		return ASSIGN;
	}

	private final Function1<Boolean,UnsignedInt32Member> EVEN =
			new Function1<Boolean, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a) {
			return a.v % 2 == 0;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt32Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,UnsignedInt32Member> ODD =
			new Function1<Boolean, UnsignedInt32Member>()
	{
		@Override
		public Boolean call(UnsignedInt32Member a) {
			return a.v % 2 == 1;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt32Member> isOdd() {
		return ODD;
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> PRED =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b) {
			if (a.v == 0)
				b.setV(0xffffffff);
			else
				b.setV( a.v() - 1 );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> SUCC =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b) {
			if (a.v == -1)
				b.setV(0);
			else
				b.setV( a.v() + 1 );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> succ() {
		return SUCC;
	}

	private Procedure1<UnsignedInt32Member> MAXBOUND =
			new Procedure1<UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a) {
			a.setV( 0xffffffff );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt32Member> maxBound() {
		return MAXBOUND;
	}

	private Procedure1<UnsignedInt32Member> MINBOUND =
			new Procedure1<UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt32Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> BITAND =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v & b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> BITOR =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v | b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> BITXOR =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v ^ b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<UnsignedInt32Member,UnsignedInt32Member> BITNOT =
			new Procedure2<UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b) {
			b.setV( ~a.v );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt32Member,UnsignedInt32Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> BITANDNOT =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			c.setV( a.v & ~b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> BITSHL =
			new Procedure3<java.lang.Integer, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt32Member a, UnsignedInt32Member b) {
			if (count < 0)
				bitShiftRight().call(Math.abs(count), a, b);
			else {
				count = count % 32;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> BITSHR =
			new Procedure3<java.lang.Integer, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt32Member a, UnsignedInt32Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> BITSHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt32Member a, UnsignedInt32Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	public Procedure3<java.lang.Integer,UnsignedInt32Member,UnsignedInt32Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> MIN =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			Min.compute(G.UINT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> MAX =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			Max.compute(G.UINT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> max() {
		return MAX;
	}

	private Procedure1<UnsignedInt32Member> RAND =
			new Procedure1<UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt());
		}
	};
	
	@Override
	public Procedure1<UnsignedInt32Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> POW =
			new Procedure3<UnsignedInt32Member, UnsignedInt32Member, UnsignedInt32Member>()
	{
		@Override
		public void call(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			UnsignedInt32Member tmp = new UnsignedInt32Member(ONE);
			UnsignedInt32Member pow = new UnsignedInt32Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt32Member,UnsignedInt32Member,UnsignedInt32Member> pow() {
		return POW;
	}

}
