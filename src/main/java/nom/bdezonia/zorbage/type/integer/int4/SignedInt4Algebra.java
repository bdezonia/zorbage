/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.integer.int4;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.SteinGcd;
import nom.bdezonia.zorbage.algorithm.SteinLcm;
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
public class SignedInt4Algebra
	implements
		Integer<SignedInt4Algebra, SignedInt4Member>,
		Bounded<SignedInt4Member>,
		BitOperations<SignedInt4Member>,
		Random<SignedInt4Member>,
		Tolerance<SignedInt4Member,SignedInt4Member>,
		ScaleByOneHalf<SignedInt4Member>,
		ScaleByTwo<SignedInt4Member>,
		ConstructibleFromInt<SignedInt4Member>,
		Conjugate<SignedInt4Member>
{
	@Override
	public String typeDescription() {
		return "4-bit signed int";
	}

	@Override
	public SignedInt4Member construct() {
		return new SignedInt4Member();
	}

	@Override
	public SignedInt4Member construct(SignedInt4Member other) {
		return new SignedInt4Member(other);
	}

	@Override
	public SignedInt4Member construct(String str) {
		return new SignedInt4Member(str);
	}

	@Override
	public SignedInt4Member construct(int... vals) {
		return new SignedInt4Member(vals);
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> EQ =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> NEQ =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> ASSIGN =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt4Member> ZER =
			new Procedure1<SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt4Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> NEG =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			if (a.v == -8)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> ADD =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> SUB =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> MUL =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt4Member a, SignedInt4Member b) {
			PowerNonNegative.compute(G.INT4, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt4Member> UNITY =
			new Procedure1<SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt4Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> LESS =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> LE =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> GREAT =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt4Member, SignedInt4Member> GE =
			new Function2<Boolean, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a, SignedInt4Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt4Member, SignedInt4Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt4Member, SignedInt4Member> CMP =
			new Function2<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt4Member a, SignedInt4Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt4Member, SignedInt4Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt4Member> SIG =
			new Function1<java.lang.Integer, SignedInt4Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt4Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt4Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> MIN =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> MAX =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> ABS =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			if (a.v == -8)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> GCD =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			SteinGcd.compute(G.INT4, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> LCM =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			SteinLcm.compute(G.INT4, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt4Member> EVEN =
			new Function1<Boolean, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt4Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt4Member> ODD =
			new Function1<Boolean, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt4Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> DIV =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member d) {
			if (b.v == -1 && a.v == -8)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> MOD =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt4Member, SignedInt4Member, SignedInt4Member, SignedInt4Member> DIVMOD =
			new Procedure4<SignedInt4Member, SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member d, SignedInt4Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt4Member, SignedInt4Member, SignedInt4Member, SignedInt4Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> PRED =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			if (a.v == -8)
				b.v = 7;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> SUCC =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			if (a.v == 7)
				b.v = -8;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> POW =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			PowerNonNegative.compute(G.INT4, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt4Member> RAND =
			new Procedure1<SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(16)-8);
		}
	};
	
	@Override
	public Procedure1<SignedInt4Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> AND =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> OR =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> XOR =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt4Member, SignedInt4Member> NOT =
			new Procedure2<SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> ANDNOT =
			new Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a, SignedInt4Member b, SignedInt4Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt4Member a, SignedInt4Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 4;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt4Member a, SignedInt4Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else {
				int val = a.v >> count;
				if (a.v < 0 && val == 0) {
					// sign extend
					b.v = -1;
				}
				else
					b.setV( val );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt4Member a, SignedInt4Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt4Member> MAXBOUND =
			new Procedure1<SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a) {
			a.v = 7;
		}
	};

	@Override
	public Procedure1<SignedInt4Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt4Member> MINBOUND =
			new Procedure1<SignedInt4Member>()
	{
		@Override
		public void call(SignedInt4Member a) {
			a.v = -8;
		}
	};

	@Override
	public Procedure1<SignedInt4Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt4Member> ISZERO =
			new Function1<Boolean, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt4Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt4Member, SignedInt4Member, SignedInt4Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt4Member b, SignedInt4Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt4Member b, SignedInt4Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt4Member, SignedInt4Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt4Member, SignedInt4Member> SBR =
			new Procedure3<RationalMember, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt4Member b, SignedInt4Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt4Member, SignedInt4Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt4Member, SignedInt4Member> SBD =
			new Procedure3<Double, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(Double a, SignedInt4Member b, SignedInt4Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt4Member, SignedInt4Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt4Member, SignedInt4Member> SBDR =
			new Procedure3<Double, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(Double a, SignedInt4Member b, SignedInt4Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt4Member, SignedInt4Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt4Member, SignedInt4Member, SignedInt4Member> WITHIN =
			new Function3<Boolean, SignedInt4Member, SignedInt4Member, SignedInt4Member>()
	{
		
		@Override
		public Boolean call(SignedInt4Member tol, SignedInt4Member a, SignedInt4Member b) {
			return NumberWithin.compute(G.INT4, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt4Member, SignedInt4Member, SignedInt4Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt4Member a, SignedInt4Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt4Member a, SignedInt4Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt4Member, SignedInt4Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt4Member> ISUNITY =
			new Function1<Boolean, SignedInt4Member>()
	{
		@Override
		public Boolean call(SignedInt4Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt4Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt4Member, SignedInt4Member> conjugate() {
		return ASSIGN;
	}
}
