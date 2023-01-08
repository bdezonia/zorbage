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
package nom.bdezonia.zorbage.type.integer.int14;

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
public class SignedInt14Algebra
	implements
		Integer<SignedInt14Algebra, SignedInt14Member>,
		Bounded<SignedInt14Member>,
		BitOperations<SignedInt14Member>,
		Random<SignedInt14Member>,
		Tolerance<SignedInt14Member,SignedInt14Member>,
		ScaleByOneHalf<SignedInt14Member>,
		ScaleByTwo<SignedInt14Member>,
		ConstructibleFromInt<SignedInt14Member>,
		Conjugate<SignedInt14Member>
{
	@Override
	public String typeDescription() {
		return "14-bit signed int";
	}

	@Override
	public SignedInt14Member construct() {
		return new SignedInt14Member();
	}

	@Override
	public SignedInt14Member construct(SignedInt14Member other) {
		return new SignedInt14Member(other);
	}

	@Override
	public SignedInt14Member construct(String str) {
		return new SignedInt14Member(str);
	}

	@Override
	public SignedInt14Member construct(int... vals) {
		return new SignedInt14Member(vals);
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> EQ =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> NEQ =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt14Member, SignedInt14Member> ASSIGN =
			new Procedure2<SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt14Member> ZER =
			new Procedure1<SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt14Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt14Member,SignedInt14Member> NEG =
			new Procedure2<SignedInt14Member,SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			if (a.v == -8192)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> ADD =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> SUB =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> MUL =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt14Member a, SignedInt14Member b) {
			PowerNonNegative.compute(G.INT14, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt14Member> UNITY =
			new Procedure1<SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt14Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> LESS =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> LE =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> GREAT =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt14Member, SignedInt14Member> GE =
			new Function2<Boolean, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a, SignedInt14Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt14Member, SignedInt14Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt14Member, SignedInt14Member> CMP =
			new Function2<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt14Member a, SignedInt14Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt14Member, SignedInt14Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt14Member> SIG =
			new Function1<java.lang.Integer, SignedInt14Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt14Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt14Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> MIN =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> MAX =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt14Member, SignedInt14Member> ABS =
			new Procedure2<SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			if (a.v == -8192)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> GCD =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			SteinGcd.compute(G.INT14, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> LCM =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			SteinLcm.compute(G.INT14, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt14Member> EVEN =
			new Function1<Boolean, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt14Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt14Member> ODD =
			new Function1<Boolean, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt14Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> DIV =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member d) {
			if (b.v == -1 && a.v == -8192)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> MOD =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt14Member, SignedInt14Member, SignedInt14Member, SignedInt14Member> DIVMOD =
			new Procedure4<SignedInt14Member, SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member d, SignedInt14Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt14Member, SignedInt14Member, SignedInt14Member, SignedInt14Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt14Member, SignedInt14Member> PRED =
			new Procedure2<SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			if (a.v == -8192)
				b.v = 8191;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt14Member, SignedInt14Member> SUCC =
			new Procedure2<SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			if (a.v == 8191)
				b.v = -8192;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> POW =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			PowerNonNegative.compute(G.INT14, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt14Member> RAND =
			new Procedure1<SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(16384)-8192);
		}
	};
	
	@Override
	public Procedure1<SignedInt14Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> AND =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> OR =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> XOR =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt14Member, SignedInt14Member> NOT =
			new Procedure2<SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> ANDNOT =
			new Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a, SignedInt14Member b, SignedInt14Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt14Member a, SignedInt14Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 14;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt14Member a, SignedInt14Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt14Member a, SignedInt14Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt14Member> MAXBOUND =
			new Procedure1<SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt14Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt14Member> MINBOUND =
			new Procedure1<SignedInt14Member>()
	{
		@Override
		public void call(SignedInt14Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt14Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt14Member> ISZERO =
			new Function1<Boolean, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt14Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt14Member, SignedInt14Member, SignedInt14Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt14Member b, SignedInt14Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt14Member b, SignedInt14Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt14Member, SignedInt14Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt14Member, SignedInt14Member> SBR =
			new Procedure3<RationalMember, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt14Member b, SignedInt14Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt14Member, SignedInt14Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt14Member, SignedInt14Member> SBD =
			new Procedure3<Double, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(Double a, SignedInt14Member b, SignedInt14Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt14Member, SignedInt14Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt14Member, SignedInt14Member> SBDR =
			new Procedure3<Double, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(Double a, SignedInt14Member b, SignedInt14Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt14Member, SignedInt14Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt14Member, SignedInt14Member, SignedInt14Member> WITHIN =
			new Function3<Boolean, SignedInt14Member, SignedInt14Member, SignedInt14Member>()
	{
		
		@Override
		public Boolean call(SignedInt14Member tol, SignedInt14Member a, SignedInt14Member b) {
			return NumberWithin.compute(G.INT14, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt14Member, SignedInt14Member, SignedInt14Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt14Member a, SignedInt14Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt14Member a, SignedInt14Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt14Member, SignedInt14Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt14Member> ISUNITY =
			new Function1<Boolean, SignedInt14Member>()
	{
		@Override
		public Boolean call(SignedInt14Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt14Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt14Member, SignedInt14Member> conjugate() {
		return ASSIGN;
	}
}
