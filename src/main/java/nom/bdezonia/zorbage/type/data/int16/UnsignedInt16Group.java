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
package nom.bdezonia.zorbage.type.data.int16;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
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
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt16Group
  implements
    Integer<UnsignedInt16Group, UnsignedInt16Member>,
    Bounded<UnsignedInt16Member>,
    BitOperations<UnsignedInt16Member>,
    Random<UnsignedInt16Member>
{
	
	public UnsignedInt16Group() { }
	
	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> EQ =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return a.v == b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> NEQ =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return a.v != b.v;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public UnsignedInt16Member construct() {
		return new UnsignedInt16Member();
	}

	@Override
	public UnsignedInt16Member construct(UnsignedInt16Member other) {
		return new UnsignedInt16Member(other);
	}

	@Override
	public UnsignedInt16Member construct(String s) {
		return new UnsignedInt16Member(s);
	}

	private Procedure2<UnsignedInt16Member,UnsignedInt16Member> ASSIGN =
			new Procedure2<UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member from, UnsignedInt16Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> assign() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> abs() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> MUL =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt16Member a, UnsignedInt16Member b) {
			PowerNonNegative.compute(G.UINT16, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt16Member> ZER =
			new Procedure1<UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt16Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> ADD =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v + b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> SUB =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v - b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> subtract() {
		return SUB;
	}

	private final Procedure1<UnsignedInt16Member> UNITY =
			new Procedure1<UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt16Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> LESS =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return compare().call(a,b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> LE =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return compare().call(a,b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> GREAT =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return compare().call(a,b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> GE =
			new Function2<Boolean, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a, UnsignedInt16Member b) {
			return compare().call(a,b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnsignedInt16Member,UnsignedInt16Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt16Member a, UnsignedInt16Member b) {
			int av = a.v();
			int bv = b.v();
			if (av < bv) return -1;
			if (av > bv) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnsignedInt16Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt16Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt16Member a) {
			if (a.v == 0) return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,UnsignedInt16Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> DIV =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member d) {
			d.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> MOD =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member m) {
			m.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> DIVMOD =
			new Procedure4<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member d, UnsignedInt16Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};

	@Override
	public Procedure4<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> GCD =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			Gcd.compute(G.UINT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> LCM =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			Lcm.compute(G.UINT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> norm() {
		return ASSIGN;
	}

	private final Function1<Boolean,UnsignedInt16Member> EVEN =
			new Function1<Boolean, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a) {
			return (a.v & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt16Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,UnsignedInt16Member> ODD =
			new Function1<Boolean, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a) {
			return (a.v & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,UnsignedInt16Member> isOdd() {
		return ODD;
	}

	private Procedure2<UnsignedInt16Member,UnsignedInt16Member> PRED =
			new Procedure2<UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b) {
			if (a.v == 0)
				b.setV(0xffff);
			else
				b.setV( a.v() - 1 );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> pred() {
		return PRED;
	}

	private Procedure2<UnsignedInt16Member,UnsignedInt16Member> SUCC =
			new Procedure2<UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b) {
			if (a.v == -1)
				b.setV(0);
			else
				b.setV( a.v() + 1 );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> succ() {
		return SUCC;
	}

	private final Procedure1<UnsignedInt16Member> MAXBOUND =
			new Procedure1<UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a) {
			a.setV( 0xffff );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt16Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt16Member> MINBOUND =
			new Procedure1<UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt16Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> BITAND =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v & b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> BITOR =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v | b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> BITXOR =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v ^ b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> bitXor() {
		return BITXOR;
	}

	private Procedure2<UnsignedInt16Member,UnsignedInt16Member> BITNOT =
			new Procedure2<UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b) {
			b.setV( ~a.v );
		}
	};
	
	@Override
	public Procedure2<UnsignedInt16Member,UnsignedInt16Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> BITANDNOT =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			c.setV( a.v & ~b.v );
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> BITSHL =
			new Procedure3<java.lang.Integer, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt16Member a, UnsignedInt16Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 16;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> bitShiftLeft() {
		return BITSHL;
	}

	@Override
	public Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return BITSHRZ;
	}

	private final Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> BITSHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt16Member a, UnsignedInt16Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	public Procedure3<java.lang.Integer,UnsignedInt16Member,UnsignedInt16Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> MIN =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			Min.compute(G.UINT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> MAX =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			Max.compute(G.UINT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> max() {
		return MAX;
	}

	private final Procedure1<UnsignedInt16Member> RAND =
			new Procedure1<UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(0x10000) );
		}
	};

	@Override
	public Procedure1<UnsignedInt16Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> POW =
			new Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member>()
	{
		@Override
		public void call(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
			power().call(b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt16Member,UnsignedInt16Member,UnsignedInt16Member> pow() {
		return POW;
	}

	private final Function1<Boolean, UnsignedInt16Member> ISZERO =
			new Function1<Boolean, UnsignedInt16Member>()
	{
		@Override
		public Boolean call(UnsignedInt16Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt16Member, UnsignedInt16Member, UnsignedInt16Member> scale() {
		return MUL;
	}

}
