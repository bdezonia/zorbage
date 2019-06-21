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
package nom.bdezonia.zorbage.type.data.int8;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt8Algebra
  implements
    Integer<SignedInt8Algebra, SignedInt8Member>,
    Bounded<SignedInt8Member>,
    BitOperations<SignedInt8Member>,
    Random<SignedInt8Member>
{

	public SignedInt8Algebra() { }
	
	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> EQ =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> NEQ =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public SignedInt8Member construct() {
		return new SignedInt8Member();
	}

	@Override
	public SignedInt8Member construct(SignedInt8Member other) {
		return new SignedInt8Member(other);
	}

	@Override
	public SignedInt8Member construct(String s) {
		return new SignedInt8Member(s);
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> ASSIGN =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member from, SignedInt8Member to) {
			to.v = from.v;
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> ABS =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( Math.abs(a.v()) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MUL =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt8Member a, SignedInt8Member b) {
			PowerNonNegative.compute(G.INT8, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt8Member> ZER =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> NEG =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( -a.v() );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> ADD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> SUB =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt8Member> UNITY =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = 1;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> LESS =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> LE =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> GREATER =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isGreater() {
		return GREATER;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> GE =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt8Member,SignedInt8Member> CMP =
			new Function2<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt8Member,SignedInt8Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt8Member> SIG =
			new Function1<java.lang.Integer, SignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt8Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt8Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> DIV =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			if (b.v() == -1 && a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("cannot divide minint by -1");
			c.setV( (a.v() / b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MOD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() % b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt8Member,SignedInt8Member,SignedInt8Member,SignedInt8Member> DIVMOD =
			new Procedure4<SignedInt8Member, SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member d, SignedInt8Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt8Member,SignedInt8Member,SignedInt8Member,SignedInt8Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> GCD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Gcd.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> LCM =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Lcm.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt8Member> EVEN =
			new Function1<Boolean,SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return (a.v() & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt8Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt8Member> ODD =
			new Function1<Boolean,SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return (a.v() & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt8Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> PRED =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( (a.v() - 1) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> SUCC =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( (a.v() + 1) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt8Member> MAXBOUND =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = java.lang.Byte.MAX_VALUE;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt8Member> MINBOUND =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = java.lang.Byte.MIN_VALUE;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITAND =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() & b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITOR =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() | b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITXOR =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() ^ b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> BITNOT =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( ~a.v() );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITANDNOT =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() & ~b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 8;
				b.setV( (a.v() << count) );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( (a.v() >> count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( (a.v() >>> count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MIN =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Min.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MAX =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Max.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt8Member> RAND = 
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(256) - 128);
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> POW =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			power().call((int)b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> pow() {
		return POW;
	}

	private final Function1<Boolean, SignedInt8Member> ISZERO =
			new Function1<Boolean, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt8Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt8Member b, SignedInt8Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(G.ONE_HALF);
			else
				tmp = tmp.add(G.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, SignedInt8Member, SignedInt8Member> SBR =
			new Procedure3<RationalMember, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt8Member b, SignedInt8Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt8Member, SignedInt8Member> scaleByRational() {
		return SBR;
	}

}
