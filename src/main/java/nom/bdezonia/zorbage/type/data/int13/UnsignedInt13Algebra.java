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
package nom.bdezonia.zorbage.type.data.int13;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class UnsignedInt13Algebra
	implements
		Integer<UnsignedInt13Algebra, UnsignedInt13Member>,
		Bounded<UnsignedInt13Member>,
		BitOperations<UnsignedInt13Member>,
		Random<UnsignedInt13Member>,
		Tolerance<UnsignedInt13Member,UnsignedInt13Member>
{

	@Override
	public UnsignedInt13Member construct() {
		return new UnsignedInt13Member();
	}

	@Override
	public UnsignedInt13Member construct(UnsignedInt13Member other) {
		return new UnsignedInt13Member(other);
	}

	@Override
	public UnsignedInt13Member construct(String str) {
		return new UnsignedInt13Member(str);
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> EQ =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> NEQ =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt13Member, UnsignedInt13Member> ASSIGN =
			new Procedure2<UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt13Member> ZER =
			new Procedure1<UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt13Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> ADD =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> SUB =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> MUL =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt13Member a, UnsignedInt13Member b) {
			PowerNonNegative.compute(G.UINT13, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt13Member> UNITY =
			new Procedure1<UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt13Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> LESS =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> LE =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> GREAT =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> GE =
			new Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a, UnsignedInt13Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt13Member, UnsignedInt13Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt13Member a, UnsignedInt13Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt13Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt13Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt13Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt13Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> MIN =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> MAX =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> GCD =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			Gcd.compute(G.UINT13, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> LCM =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			Lcm.compute(G.UINT13, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt13Member> EVEN =
			new Function1<Boolean, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt13Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt13Member> ODD =
			new Function1<Boolean, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt13Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> DIV =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> MOD =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> DIVMOD =
			new Procedure4<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member d, UnsignedInt13Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt13Member, UnsignedInt13Member> PRED =
			new Procedure2<UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b) {
			if (a.v == 0)
				b.v = 8191;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt13Member, UnsignedInt13Member> SUCC =
			new Procedure2<UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b) {
			if (a.v == 8191)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> POW =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			PowerNonNegative.compute(G.UINT13, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt13Member> RAND =
			new Procedure1<UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(8192) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt13Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> AND =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> OR =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> XOR =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt13Member, UnsignedInt13Member> NOT =
			new Procedure2<UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt13Member, UnsignedInt13Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> ANDNOT =
			new Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a, UnsignedInt13Member b, UnsignedInt13Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt13Member a, UnsignedInt13Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 13;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt13Member a, UnsignedInt13Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt13Member, UnsignedInt13Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt13Member> MAXBOUND =
			new Procedure1<UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a) {
			a.v = 8191;
		}
	};

	@Override
	public Procedure1<UnsignedInt13Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt13Member> MINBOUND =
			new Procedure1<UnsignedInt13Member>()
	{
		@Override
		public void call(UnsignedInt13Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt13Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt13Member> ISZERO =
			new Function1<Boolean, UnsignedInt13Member>()
	{
		@Override
		public Boolean call(UnsignedInt13Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt13Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt13Member, UnsignedInt13Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt13Member b, UnsignedInt13Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt13Member, UnsignedInt13Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, UnsignedInt13Member, UnsignedInt13Member> SBR =
			new Procedure3<RationalMember, UnsignedInt13Member, UnsignedInt13Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt13Member b, UnsignedInt13Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt13Member, UnsignedInt13Member> scaleByRational() {
		return SBR;
	}

	private final Function3<Boolean, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> WITHIN =
			new Function3<Boolean, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt13Member tol, UnsignedInt13Member a, UnsignedInt13Member b) {
			return NumberWithin.compute(G.UINT13, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt13Member, UnsignedInt13Member, UnsignedInt13Member> within() {
		return WITHIN;
	}

}
