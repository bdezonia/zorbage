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
package nom.bdezonia.zorbage.type.integer.int2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.BoundedType;
import nom.bdezonia.zorbage.algebra.type.markers.EnumerableType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.IntegerType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
public class SignedInt2Algebra
	implements
		Integer<SignedInt2Algebra, SignedInt2Member>,
		Bounded<SignedInt2Member>,
		BitOperations<SignedInt2Member>,
		Random<SignedInt2Member>,
		Tolerance<SignedInt2Member,SignedInt2Member>,
		ScaleByOneHalf<SignedInt2Member>,
		ScaleByTwo<SignedInt2Member>,
		ConstructibleFromBytes<SignedInt2Member>,
		ConstructibleFromShorts<SignedInt2Member>,
		ConstructibleFromInts<SignedInt2Member>,
		ConstructibleFromLongs<SignedInt2Member>,
		ConstructibleFromFloats<SignedInt2Member>,
		ConstructibleFromDoubles<SignedInt2Member>,
		ConstructibleFromBigIntegers<SignedInt2Member>,
		ConstructibleFromBigDecimals<SignedInt2Member>,
		Conjugate<SignedInt2Member>,
		BoundedType,
		EnumerableType,
		ExactType,
		IntegerType,
		NumberType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "2-bit signed int";
	}

	@Override
	public SignedInt2Member construct() {
		return new SignedInt2Member();
	}

	@Override
	public SignedInt2Member construct(SignedInt2Member other) {
		return new SignedInt2Member(other);
	}

	@Override
	public SignedInt2Member construct(String str) {
		return new SignedInt2Member(str);
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> EQ =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> NEQ =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> ASSIGN =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt2Member> ZER =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt2Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> NEG =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> ADD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> SUB =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MUL =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt2Member a, SignedInt2Member b) {
			PowerNonNegative.compute(G.INT2, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt2Member> UNITY =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt2Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> LESS =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> LE =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> GREAT =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> GE =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member> CMP =
			new Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt2Member> SIG =
			new Function1<java.lang.Integer, SignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt2Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt2Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MIN =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MAX =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> ABS =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> GCD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			SteinGcd.compute(G.INT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> LCM =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			SteinLcm.compute(G.INT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt2Member> EVEN =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt2Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt2Member> ODD =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt2Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> DIV =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member d) {
			if (b.v == -1 && a.v == -2)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MOD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member> DIVMOD =
			new Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member d, SignedInt2Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> PRED =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				b.v = 1;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> SUCC =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == 1)
				b.v = -2;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> POW =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			PowerNonNegative.compute(G.INT2, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt2Member> RAND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(4)-2);
		}
	};
	
	@Override
	public Procedure1<SignedInt2Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> AND =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> OR =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> XOR =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> NOT =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> ANDNOT =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 2;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt2Member> MAXBOUND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt2Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt2Member> MINBOUND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt2Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt2Member> ISZERO =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt2Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt2Member b, SignedInt2Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt2Member b, SignedInt2Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt2Member, SignedInt2Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt2Member, SignedInt2Member> SBR =
			new Procedure3<RationalMember, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt2Member b, SignedInt2Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt2Member, SignedInt2Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt2Member, SignedInt2Member> SBD =
			new Procedure3<Double, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(Double a, SignedInt2Member b, SignedInt2Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt2Member, SignedInt2Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt2Member, SignedInt2Member> SBDR =
			new Procedure3<Double, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(Double a, SignedInt2Member b, SignedInt2Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt2Member, SignedInt2Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt2Member, SignedInt2Member, SignedInt2Member> WITHIN =
			new Function3<Boolean, SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		
		@Override
		public Boolean call(SignedInt2Member tol, SignedInt2Member a, SignedInt2Member b) {
			return NumberWithin.compute(G.INT2, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt2Member, SignedInt2Member, SignedInt2Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt2Member a, SignedInt2Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt2Member a, SignedInt2Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt2Member> ISUNITY =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt2Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt2Member construct(BigDecimal... vals) {
		SignedInt2Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(BigInteger... vals) {
		SignedInt2Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(double... vals) {
		SignedInt2Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(float... vals) {
		SignedInt2Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(long... vals) {
		SignedInt2Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(int... vals) {
		SignedInt2Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(short... vals) {
		SignedInt2Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt2Member construct(byte... vals) {
		SignedInt2Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
