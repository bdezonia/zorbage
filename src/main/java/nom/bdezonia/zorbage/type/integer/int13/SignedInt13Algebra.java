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
package nom.bdezonia.zorbage.type.integer.int13;

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
import nom.bdezonia.zorbage.misc.C;
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
public class SignedInt13Algebra
	implements
		Integer<SignedInt13Algebra, SignedInt13Member>,
		Bounded<SignedInt13Member>,
		BitOperations<SignedInt13Member>,
		Random<SignedInt13Member>,
		Tolerance<SignedInt13Member,SignedInt13Member>,
		ScaleByOneHalf<SignedInt13Member>,
		ScaleByTwo<SignedInt13Member>
{

	@Override
	public SignedInt13Member construct() {
		return new SignedInt13Member();
	}

	@Override
	public SignedInt13Member construct(SignedInt13Member other) {
		return new SignedInt13Member(other);
	}

	@Override
	public SignedInt13Member construct(String str) {
		return new SignedInt13Member(str);
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> EQ =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> NEQ =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt13Member, SignedInt13Member> ASSIGN =
			new Procedure2<SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt13Member> ZER =
			new Procedure1<SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt13Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt13Member,SignedInt13Member> NEG =
			new Procedure2<SignedInt13Member,SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			if (a.v == -4096)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> ADD =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> SUB =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> MUL =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt13Member a, SignedInt13Member b) {
			PowerNonNegative.compute(G.INT13, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt13Member> UNITY =
			new Procedure1<SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt13Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> LESS =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> LE =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> GREAT =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt13Member, SignedInt13Member> GE =
			new Function2<Boolean, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a, SignedInt13Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt13Member, SignedInt13Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt13Member, SignedInt13Member> CMP =
			new Function2<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt13Member a, SignedInt13Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt13Member, SignedInt13Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt13Member> SIG =
			new Function1<java.lang.Integer, SignedInt13Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt13Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt13Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> MIN =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> MAX =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt13Member, SignedInt13Member> ABS =
			new Procedure2<SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			if (a.v == -4096)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> GCD =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			SteinGcd.compute(G.INT13, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> LCM =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			SteinLcm.compute(G.INT13, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt13Member> EVEN =
			new Function1<Boolean, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt13Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt13Member> ODD =
			new Function1<Boolean, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt13Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> DIV =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member d) {
			if (b.v == -1 && a.v == -4096)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> MOD =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt13Member, SignedInt13Member, SignedInt13Member, SignedInt13Member> DIVMOD =
			new Procedure4<SignedInt13Member, SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member d, SignedInt13Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt13Member, SignedInt13Member, SignedInt13Member, SignedInt13Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt13Member, SignedInt13Member> PRED =
			new Procedure2<SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			if (a.v == -4096)
				b.v = 4095;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt13Member, SignedInt13Member> SUCC =
			new Procedure2<SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			if (a.v == 4095)
				b.v = -4096;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> POW =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			PowerNonNegative.compute(G.INT13, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt13Member> RAND =
			new Procedure1<SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(8192)-4096);
		}
	};
	
	@Override
	public Procedure1<SignedInt13Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> AND =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> OR =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> XOR =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt13Member, SignedInt13Member> NOT =
			new Procedure2<SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt13Member, SignedInt13Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> ANDNOT =
			new Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a, SignedInt13Member b, SignedInt13Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt13Member a, SignedInt13Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 13;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt13Member a, SignedInt13Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt13Member a, SignedInt13Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt13Member> MAXBOUND =
			new Procedure1<SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a) {
			a.v = 4095;
		}
	};

	@Override
	public Procedure1<SignedInt13Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt13Member> MINBOUND =
			new Procedure1<SignedInt13Member>()
	{
		@Override
		public void call(SignedInt13Member a) {
			a.v = -4096;
		}
	};

	@Override
	public Procedure1<SignedInt13Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt13Member> ISZERO =
			new Function1<Boolean, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt13Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt13Member, SignedInt13Member, SignedInt13Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt13Member b, SignedInt13Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt13Member b, SignedInt13Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(C.ONE_HALF);
			else
				tmp = tmp.add(C.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt13Member, SignedInt13Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt13Member, SignedInt13Member> SBR =
			new Procedure3<RationalMember, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt13Member b, SignedInt13Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt13Member, SignedInt13Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt13Member, SignedInt13Member> SBD =
			new Procedure3<Double, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(Double a, SignedInt13Member b, SignedInt13Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt13Member, SignedInt13Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt13Member, SignedInt13Member> SBDR =
			new Procedure3<Double, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(Double a, SignedInt13Member b, SignedInt13Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt13Member, SignedInt13Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt13Member, SignedInt13Member, SignedInt13Member> WITHIN =
			new Function3<Boolean, SignedInt13Member, SignedInt13Member, SignedInt13Member>()
	{
		
		@Override
		public Boolean call(SignedInt13Member tol, SignedInt13Member a, SignedInt13Member b) {
			return NumberWithin.compute(G.INT13, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt13Member, SignedInt13Member, SignedInt13Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt13Member a, SignedInt13Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt13Member a, SignedInt13Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt13Member, SignedInt13Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt13Member> ISUNITY =
			new Function1<Boolean, SignedInt13Member>()
	{
		@Override
		public Boolean call(SignedInt13Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt13Member> isUnity() {
		return ISUNITY;
	}
}
