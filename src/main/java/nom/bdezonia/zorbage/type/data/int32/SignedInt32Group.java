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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt32Group
  implements
    Integer<SignedInt32Group, SignedInt32Member>,
    Bounded<SignedInt32Member>,
    BitOperations<SignedInt32Member>,
    Random<SignedInt32Member>
{

	public SignedInt32Group() { }
	
	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> EQ =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> NEQ =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public SignedInt32Member construct() {
		return new SignedInt32Member();
	}

	@Override
	public SignedInt32Member construct(SignedInt32Member other) {
		return new SignedInt32Member(other);
	}

	@Override
	public SignedInt32Member construct(String s) {
		return new SignedInt32Member(s);
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> ASSIGN =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member from, SignedInt32Member to) {
			to.setV( from.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> assign() {
		return ASSIGN;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> ABS =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			if (a.v() == java.lang.Integer.MIN_VALUE)
				throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
			b.setV( Math.abs(a.v()) );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> MUL =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt32Member a, SignedInt32Member b) {
			PowerI.compute(G.INT32, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt32Member> ZER =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> zero() {
		return ZER;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> NEG =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			b.setV( -a.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> ADD =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> SUB =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt32Member> UNITY =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> LESS =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> LE =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> GREAT =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,SignedInt32Member,SignedInt32Member> GE =
			new Function2<Boolean, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a, SignedInt32Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt32Member,SignedInt32Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt32Member,SignedInt32Member> CMP =
			new Function2<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt32Member a, SignedInt32Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt32Member,SignedInt32Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt32Member> SIG =
			new Function1<java.lang.Integer, SignedInt32Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt32Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt32Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> DIV =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member d) {
			d.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> MOD =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member m) {
			m.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt32Member,SignedInt32Member,SignedInt32Member,SignedInt32Member> DIVMOD =
			new Procedure4<SignedInt32Member, SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member d, SignedInt32Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt32Member,SignedInt32Member,SignedInt32Member,SignedInt32Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> GCD =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			Gcd.compute(G.INT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> LCM =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			Lcm.compute(G.INT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> lcm() {
		return LCM;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> NORM =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			abs().call(a,b);
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> norm() {
		return NORM;
	}

	private final Function1<Boolean,SignedInt32Member> EVEN =
			new Function1<Boolean, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a) {
			return a.v() % 2 == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt32Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt32Member> ODD =
			new Function1<Boolean, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a) {
			return a.v() % 2 == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt32Member> isOdd() {
		return ODD;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> PRED =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			b.setV( a.v() - 1 );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> pred() {
		return PRED;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> SUCC =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			b.setV( a.v() + 1 );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt32Member> MAXBOUND =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			a.setV( java.lang.Integer.MAX_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt32Member> MINBOUND =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			a.setV( java.lang.Integer.MIN_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> BITAND =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() & b.v() );
		}
	};

	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> BITOR =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() | b.v() );
		}
	};

	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> BITXOR =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() ^ b.v() );
		}
	};

	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> bitXor() {
		return BITXOR;
	}

	private Procedure2<SignedInt32Member,SignedInt32Member> BITNOT =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			b.setV( ~a.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> BITANDNOT =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			c.setV( a.v() & ~b.v() );
		}
	};

	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt32Member a, SignedInt32Member b) {
			if (count < 0)
				bitShiftRight().call(Math.abs(count), a, b);
			else {
				count = count % 32;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt32Member a, SignedInt32Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt32Member a, SignedInt32Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	public Procedure3<java.lang.Integer,SignedInt32Member,SignedInt32Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> MIN =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			Min.compute(G.INT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> MAX =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			Max.compute(G.INT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt32Member> RAND =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt());
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> POW =
			new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
			power().call(b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> pow() {
		return POW;
	}

}
