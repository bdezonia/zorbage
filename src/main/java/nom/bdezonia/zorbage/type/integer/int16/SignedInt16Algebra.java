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
package nom.bdezonia.zorbage.type.integer.int16;

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
public class SignedInt16Algebra
	implements
		Integer<SignedInt16Algebra, SignedInt16Member>,
		Bounded<SignedInt16Member>,
		BitOperations<SignedInt16Member>,
		Random<SignedInt16Member>,
		Tolerance<SignedInt16Member,SignedInt16Member>,
		ScaleByOneHalf<SignedInt16Member>,
		ScaleByTwo<SignedInt16Member>
{

	public SignedInt16Algebra() { }

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
			to.v = from.v;
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
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( Math.abs(a.v()) );
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
			c.setV( a.v() * b.v() );
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
			a.v = 0;
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
			if (a.v() == Short.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( -a.v() );
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
			c.setV( a.v() + b.v() );
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
			c.setV( a.v() - b.v() );
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
			a.v = 1;
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
			d.setV( a.v() / b.v() );
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
			m.setV( a.v() % b.v() );
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
			SteinGcd.compute(G.INT16, a, b, c);
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
			SteinLcm.compute(G.INT16, a, b, c);
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
			return (a.v() & 1) == 0;
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
			return (a.v() & 1) == 1;
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
			b.setV( a.v() - 1 );
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
			b.setV( a.v() + 1 );
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
			a.v = java.lang.Short.MAX_VALUE;
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
			a.v = java.lang.Short.MIN_VALUE;
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
			c.setV( a.v() & b.v() );
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
			c.setV( a.v() | b.v() );
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
			c.setV( a.v() ^ b.v() );
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
			b.setV( ~a.v() );
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
			c.setV( a.v() & ~b.v() );
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
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 16;
				b.setV( a.v() << count );
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
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >> count );
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
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	@Override
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
			a.setV(rng.nextInt(65536) - 32768);
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
		public Boolean call(SignedInt16Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt16Member, SignedInt16Member, SignedInt16Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt16Member b, SignedInt16Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt16Member b, SignedInt16Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt16Member, SignedInt16Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt16Member, SignedInt16Member> SBR =
			new Procedure3<RationalMember, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt16Member b, SignedInt16Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt16Member, SignedInt16Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt16Member, SignedInt16Member> SBD =
			new Procedure3<Double, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(Double a, SignedInt16Member b, SignedInt16Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt16Member, SignedInt16Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt16Member, SignedInt16Member> SBDR =
			new Procedure3<Double, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(Double a, SignedInt16Member b, SignedInt16Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt16Member, SignedInt16Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt16Member, SignedInt16Member, SignedInt16Member> WITHIN =
			new Function3<Boolean, SignedInt16Member, SignedInt16Member, SignedInt16Member>()
	{
		
		@Override
		public Boolean call(SignedInt16Member tol, SignedInt16Member a, SignedInt16Member b) {
			return NumberWithin.compute(G.INT16, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt16Member, SignedInt16Member, SignedInt16Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt16Member a, SignedInt16Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt16Member a, SignedInt16Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt16Member, SignedInt16Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt16Member> ISUNITY =
			new Function1<Boolean, SignedInt16Member>()
	{
		@Override
		public Boolean call(SignedInt16Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt16Member> isUnity() {
		return ISUNITY;
	}
}
