/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.integer.int32;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.algebra.Integer;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt32Algebra
	implements
		Integer<SignedInt32Algebra, SignedInt32Member>,
		Bounded<SignedInt32Member>,
		BitOperations<SignedInt32Member>,
		Random<SignedInt32Member>,
		Tolerance<SignedInt32Member,SignedInt32Member>,
		ScaleByOneHalf<SignedInt32Member>,
		ScaleByTwo<SignedInt32Member>,
		ConstructibleFromInt<SignedInt32Member>
{

	public SignedInt32Algebra() { }

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

	@Override
	public SignedInt32Member construct(int... vals) {
		return new SignedInt32Member(vals[0]);
	}
	
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

	private final Procedure2<SignedInt32Member,SignedInt32Member> ASSIGN =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member from, SignedInt32Member to) {
			to.v = from.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt32Member,SignedInt32Member> ABS =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			if (a.v() == java.lang.Integer.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
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
			PowerNonNegative.compute(G.INT32, power, a, b);
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
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt32Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt32Member,SignedInt32Member> NEG =
			new Procedure2<SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a, SignedInt32Member b) {
			if (a.v() == java.lang.Integer.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
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
			a.v = 1 ;
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
			if (b.v() == -1 && a.v() == java.lang.Integer.MIN_VALUE)
				throw new IllegalArgumentException("cannot divide minint by -1");
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
			SteinGcd.compute(G.INT32, a, b, c);
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
			SteinLcm.compute(G.INT32, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt32Member,SignedInt32Member,SignedInt32Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt32Member,SignedInt32Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt32Member> EVEN =
			new Function1<Boolean, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a) {
			return (a.v() & 1) == 0;
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
			return (a.v() & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt32Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt32Member,SignedInt32Member> PRED =
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

	private final Procedure2<SignedInt32Member,SignedInt32Member> SUCC =
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
			a.v = java.lang.Integer.MAX_VALUE;
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
			a.v = java.lang.Integer.MIN_VALUE;
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

	private final Procedure2<SignedInt32Member,SignedInt32Member> BITNOT =
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
				bitShiftRight().call(-count, a, b);
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
				bitShiftLeft().call(-count, a, b);
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
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	@Override
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
			a.v = rng.nextInt();
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

	private final Function1<Boolean, SignedInt32Member> ISZERO =
			new Function1<Boolean, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt32Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt32Member b, SignedInt32Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt32Member b, SignedInt32Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt32Member, SignedInt32Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt32Member, SignedInt32Member> SBR =
			new Procedure3<RationalMember, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt32Member b, SignedInt32Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt32Member, SignedInt32Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt32Member, SignedInt32Member> SBD =
			new Procedure3<Double, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(Double a, SignedInt32Member b, SignedInt32Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt32Member, SignedInt32Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt32Member, SignedInt32Member> SBDR =
			new Procedure3<Double, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(Double a, SignedInt32Member b, SignedInt32Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt32Member, SignedInt32Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt32Member, SignedInt32Member, SignedInt32Member> WITHIN =
			new Function3<Boolean, SignedInt32Member, SignedInt32Member, SignedInt32Member>()
	{
		
		@Override
		public Boolean call(SignedInt32Member tol, SignedInt32Member a, SignedInt32Member b) {
			return NumberWithin.compute(G.INT32, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt32Member, SignedInt32Member, SignedInt32Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt32Member a, SignedInt32Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt32Member a, SignedInt32Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt32Member, SignedInt32Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt32Member> ISUNITY =
			new Function1<Boolean, SignedInt32Member>()
	{
		@Override
		public Boolean call(SignedInt32Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt32Member> isUnity() {
		return ISUNITY;
	}
}
