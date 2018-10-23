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
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt64Group
  implements
    Integer<SignedInt64Group, SignedInt64Member>,
    Bounded<SignedInt64Member>,
    BitOperations<SignedInt64Member>,
    Random<SignedInt64Member>
{
	private static final SignedInt64Member ZERO = new SignedInt64Member();
	private static final SignedInt64Member ONE = new SignedInt64Member(1);
	
	public SignedInt64Group() { }
	
	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> EQ =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> NEQ =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public SignedInt64Member construct() {
		return new SignedInt64Member();
	}

	@Override
	public SignedInt64Member construct(SignedInt64Member other) {
		return new SignedInt64Member(other);
	}

	@Override
	public SignedInt64Member construct(String s) {
		return new SignedInt64Member(s);
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> ASSIGN =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member from, SignedInt64Member to) {
			to.setV( from.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> ABS =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			if (a.v() == Long.MIN_VALUE)
				throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
			b.setV( Math.abs(a.v()) );
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MUL =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt64Member a, SignedInt64Member b) {
			PowerNonNegative.compute(G.INT64, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt64Member> ZER =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.setV( 0 );
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> NEG =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.setV( -a.v() );
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> ADD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> SUB =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt64Member> UNITY =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.setV( 1 );
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> LESS =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> LE =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> GREAT =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> GE =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt64Member,SignedInt64Member> CMP =
			new Function2<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt64Member a, SignedInt64Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt64Member,SignedInt64Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt64Member> SIG =
			new Function1<java.lang.Integer, SignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt64Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt64Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> DIV =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MOD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt64Member,SignedInt64Member,SignedInt64Member,SignedInt64Member> DIVMOD =
			new Procedure4<SignedInt64Member, SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member d, SignedInt64Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt64Member,SignedInt64Member,SignedInt64Member,SignedInt64Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> GCD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Gcd.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> LCM =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Lcm.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt64Member> EVEN =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return a.v() % 2 == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt64Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt64Member> ODD =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return a.v() % 2 == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt64Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> PRED =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.setV( a.v() - 1 );
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> SUCC =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.setV( a.v() + 1 );
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt64Member> MAXBOUND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.setV( java.lang.Long.MAX_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt64Member> MINBOUND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.setV( java.lang.Long.MIN_VALUE );
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITAND =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() & b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITOR =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() | b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITXOR =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() ^ b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> BITNOT =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.setV( ~a.v() );
		}
	};
	
	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITANDNOT =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() & ~b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitAndNot() {
		return BITANDNOT;
	}

	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftRight().call(Math.abs(count), a, b);
			else {
				count = count % 64;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MIN =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Min.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MAX =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Max.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt64Member> RAND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextLong());
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> POW =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			if (b.v() < 0)
				throw new IllegalArgumentException("Cannot get negative powers from int64s");
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			SignedInt64Member tmp = new SignedInt64Member(ONE);
			SignedInt64Member pow = new SignedInt64Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> pow() {
		return POW;
	}

	private final Function1<Boolean, SignedInt64Member> ISZERO =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member> scale() {
		return MUL;
	}

}
