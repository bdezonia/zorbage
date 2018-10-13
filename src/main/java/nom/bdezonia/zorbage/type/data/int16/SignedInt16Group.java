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
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt16Group
  implements
    Integer<SignedInt16Group, SignedInt16Member>,
    Bounded<SignedInt16Member>,
    BitOperations<SignedInt16Member>,
    Random<SignedInt16Member>
{

	public SignedInt16Group() { }

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> EQ =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> NEQ =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public SignedInt16Member construct() {
		return new SignedInt16Member();
	}

	@Override
	public SignedInt16Member construct(SignedInt16Member other) {
		return new SignedInt16Member(other);
	}

	@Override
	public SignedInt16Member construct(String s) {
		return new SignedInt16Member(s);
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> ASSIGN =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member from, SignedInt16Member to) {
			to.setV( from.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> ABS =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b) {
			if (a.v() == Short.MIN_VALUE)
				throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
			b.setV( (short) Math.abs(a.v()) );
		}
	};
	
	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> MUL =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short) (a.v() * b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt16Member a, SignedInt16Member b) {
			PowerNonNegative.compute(G.INT16, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt16Member> ZER =
			new Procedure1<SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a) {
			a.setV( (short) 0 );
		}
	};
	
	@Override
	public Procedure1<SignedInt16Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> NEG =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b) {
			b.setV( (short) -a.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> ADD =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() + b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> SUB =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() - b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt16Member> UNITY =
			new Procedure1<SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a) {
			a.setV( (short) 1 );
		}
	};
	
	@Override
	public Procedure1<SignedInt16Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> LESS =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> LE =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> GREAT =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,SignedInt16Member,SignedInt16Member> GE =
			new Function2<Boolean, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a, SignedInt16Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt16Member,SignedInt16Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt16Member,SignedInt16Member> CMP =
			new Function2<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt16Member a, SignedInt16Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt16Member,SignedInt16Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt16Member> SIG =
			new Function1<java.lang.Integer, SignedInt16Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt16Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt16Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> DIV =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member d) {
			if (b.v() == -1 && a.v() == Short.MIN_VALUE)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV( (short)(a.v() / b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> MOD =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member m) {
			m.setV( (short)(a.v() % b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt16Member,SignedInt16Member,SignedInt16Member,SignedInt16Member> DIVMOD =
			new Procedure4<SignedInt16Member, SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member d, SignedInt16Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt16Member,SignedInt16Member,SignedInt16Member,SignedInt16Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> GCD =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			Gcd.compute(G.INT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> LCM =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			Lcm.compute(G.INT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt16Member> EVEN =
			new Function1<Boolean, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a) {
			return a.v() % 2 == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt16Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt16Member> ODD =
			new Function1<Boolean, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a) {
			return a.v() % 2 == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt16Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> PRED =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b) {
			b.setV( (short)(a.v() - 1) );
		}
	};
	
	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> SUCC =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b) {
			b.setV( (short)(a.v() + 1) );
		}
	};
	
	@Override
	public final Procedure2<SignedInt16Member,SignedInt16Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt16Member> MAXBOUND =
			new Procedure1<SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a) {
			a.setV( java.lang.Short.MAX_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt16Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt16Member> MINBOUND =
			new Procedure1<SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a) {
			a.setV( java.lang.Short.MIN_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt16Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> BITAND =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() & b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> BITOR =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() | b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> BITXOR =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() ^ b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<SignedInt16Member,SignedInt16Member> BITNOT =
			new Procedure2<SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b) {
			b.setV( (short) ~a.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt16Member,SignedInt16Member> bitNot() {
		return BITNOT;
	}


	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> BITANDNOT =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			c.setV( (short)(a.v() & ~b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt16Member a, SignedInt16Member b) {
			if (count < 0)
				bitShiftRight().call(Math.abs(count), a, b);
			else {
				count = count % 16;
				b.setV( (short)(a.v() << count) );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt16Member a, SignedInt16Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( (short)(a.v() >> count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt16Member a, SignedInt16Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( (short)(a.v() >>> count) );
		}
	};
	
	public Procedure3<java.lang.Integer,SignedInt16Member,SignedInt16Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> MIN =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			Min.compute(G.INT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> min() {
		return MIN;
	}
	
	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> MAX =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			Max.compute(G.INT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt16Member> RAND =
			new Procedure1<SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV( (short) (java.lang.Short.MIN_VALUE + rng.nextInt(0x10000)));
		}
	};
	
	@Override
	public Procedure1<SignedInt16Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> POW =
			new Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
			power().call((int)b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt16Member,SignedInt16Member,SignedInt16Member> pow() {
		return POW;
	}

	private final Function1<Boolean, SignedInt16Member> ISZERO =
			new Function1<Boolean, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member b) {
			return b.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt16Member> isZero() {
		return ISZERO;
	}

}
