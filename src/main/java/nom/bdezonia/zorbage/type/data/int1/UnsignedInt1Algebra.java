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
package nom.bdezonia.zorbage.type.data.int1;

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
import nom.bdezonia.zorbage.type.algebra.LogicalOperations;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class UnsignedInt1Algebra
	implements
		Integer<UnsignedInt1Algebra, UnsignedInt1Member>,
		Bounded<UnsignedInt1Member>,
		BitOperations<UnsignedInt1Member>,
		Random<UnsignedInt1Member>,
		LogicalOperations<UnsignedInt1Member>,
		Tolerance<UnsignedInt1Member,UnsignedInt1Member>
{

	@Override
	public UnsignedInt1Member construct() {
		return new UnsignedInt1Member();
	}

	@Override
	public UnsignedInt1Member construct(UnsignedInt1Member other) {
		return new UnsignedInt1Member(other);
	}

	@Override
	public UnsignedInt1Member construct(String str) {
		return new UnsignedInt1Member(str);
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> EQ =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> NEQ =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt1Member, UnsignedInt1Member> ASSIGN =
			new Procedure2<UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt1Member> ZER =
			new Procedure1<UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt1Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> ADD =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> SUB =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> MUL =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt1Member a, UnsignedInt1Member b) {
			PowerNonNegative.compute(G.UINT1, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt1Member> UNITY =
			new Procedure1<UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt1Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> LESS =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> LE =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> GREAT =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> GE =
			new Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a, UnsignedInt1Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt1Member, UnsignedInt1Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt1Member a, UnsignedInt1Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt1Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt1Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt1Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt1Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> MIN =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> MAX =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> GCD =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			Gcd.compute(G.UINT1, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> LCM =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			Lcm.compute(G.UINT1, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt1Member> EVEN =
			new Function1<Boolean, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt1Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt1Member> ODD =
			new Function1<Boolean, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt1Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> DIV =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> MOD =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> DIVMOD =
			new Procedure4<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member d, UnsignedInt1Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt1Member, UnsignedInt1Member> PRED =
			new Procedure2<UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b) {
			if (a.v == 0)
				b.v = 1;
			else
				b.v = 0;
		}
	};

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt1Member, UnsignedInt1Member> SUCC =
			new Procedure2<UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b) {
			if (a.v == 1)
				b.v = 0;
			else
				b.v = 1;
		}
	};

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> POW =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			PowerNonNegative.compute(G.UINT1, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt1Member> RAND =
			new Procedure1<UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(0x2) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt1Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> AND =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> OR =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> XOR =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt1Member, UnsignedInt1Member> NOT =
			new Procedure2<UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b) {
			if (a.v == 0)
				b.v = 1;
			else
				b.v = 0;
		}
	};

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> ANDNOT =
			new Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a, UnsignedInt1Member b, UnsignedInt1Member c) {
			if (a.v == 0)
				c.v = 0;
			else {
				// a.v == 1
				if (b.v == 0)
					c.v = 1;
				else // b.v == 1
					c.v = 0;
			}
		}
	};

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt1Member a, UnsignedInt1Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count & 1;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt1Member a, UnsignedInt1Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt1Member, UnsignedInt1Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt1Member> MAXBOUND =
			new Procedure1<UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt1Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt1Member> MINBOUND =
			new Procedure1<UnsignedInt1Member>()
	{
		@Override
		public void call(UnsignedInt1Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt1Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt1Member> ISZERO =
			new Function1<Boolean, UnsignedInt1Member>()
	{
		@Override
		public Boolean call(UnsignedInt1Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt1Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> scale() {
		return MUL;
	}

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> logicalAnd() {
		return AND;
	}

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> logicalOr() {
		return OR;
	}

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> logicalXor() {
		return XOR;
	}

	@Override
	public Procedure2<UnsignedInt1Member, UnsignedInt1Member> logicalNot() {
		return NOT;
	}

	@Override
	public Procedure3<UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> logicalAndNot() {
		return ANDNOT;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt1Member b, UnsignedInt1Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt1Member b, UnsignedInt1Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt1Member, UnsignedInt1Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt1Member, UnsignedInt1Member> SBR =
			new Procedure3<RationalMember, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt1Member b, UnsignedInt1Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt1Member, UnsignedInt1Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member> SBD =
			new Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(Double a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV((int) (a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member> SBDR =
			new Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member>()
	{
		@Override
		public void call(Double a, UnsignedInt1Member b, UnsignedInt1Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt1Member, UnsignedInt1Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> WITHIN =
			new Function3<Boolean, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt1Member tol, UnsignedInt1Member a, UnsignedInt1Member b) {
			return NumberWithin.compute(G.UINT1, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt1Member, UnsignedInt1Member, UnsignedInt1Member> within() {
		return WITHIN;
	}

}
