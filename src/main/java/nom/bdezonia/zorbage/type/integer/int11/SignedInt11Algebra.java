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
package nom.bdezonia.zorbage.type.integer.int11;

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
public class SignedInt11Algebra
	implements
		Integer<SignedInt11Algebra, SignedInt11Member>,
		Bounded<SignedInt11Member>,
		BitOperations<SignedInt11Member>,
		Random<SignedInt11Member>,
		Tolerance<SignedInt11Member,SignedInt11Member>,
		ScaleByOneHalf<SignedInt11Member>,
		ScaleByTwo<SignedInt11Member>,
		ConstructibleFromBytes<SignedInt11Member>,
		ConstructibleFromShorts<SignedInt11Member>,
		ConstructibleFromInts<SignedInt11Member>,
		ConstructibleFromLongs<SignedInt11Member>,
		ConstructibleFromFloats<SignedInt11Member>,
		ConstructibleFromDoubles<SignedInt11Member>,
		ConstructibleFromBigIntegers<SignedInt11Member>,
		ConstructibleFromBigDecimals<SignedInt11Member>,
		Conjugate<SignedInt11Member>,
		ExactlyConstructibleFromBytes<SignedInt11Member>,
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
		return "11-bit signed int";
	}

	@Override
	public SignedInt11Member construct() {
		return new SignedInt11Member();
	}

	@Override
	public SignedInt11Member construct(SignedInt11Member other) {
		return new SignedInt11Member(other);
	}

	@Override
	public SignedInt11Member construct(String str) {
		return new SignedInt11Member(str);
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> EQ =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> NEQ =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt11Member, SignedInt11Member> ASSIGN =
			new Procedure2<SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt11Member> ZER =
			new Procedure1<SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt11Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt11Member,SignedInt11Member> NEG =
			new Procedure2<SignedInt11Member,SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			if (a.v == -1024)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> ADD =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> SUB =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> MUL =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt11Member a, SignedInt11Member b) {
			PowerNonNegative.compute(G.INT11, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt11Member> UNITY =
			new Procedure1<SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt11Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> LESS =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> LE =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> GREAT =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt11Member, SignedInt11Member> GE =
			new Function2<Boolean, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a, SignedInt11Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt11Member, SignedInt11Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt11Member, SignedInt11Member> CMP =
			new Function2<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt11Member a, SignedInt11Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt11Member, SignedInt11Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt11Member> SIG =
			new Function1<java.lang.Integer, SignedInt11Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt11Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt11Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> MIN =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> MAX =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt11Member, SignedInt11Member> ABS =
			new Procedure2<SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			if (a.v == -1024)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> GCD =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			SteinGcd.compute(G.INT11, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> LCM =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			SteinLcm.compute(G.INT11, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt11Member> EVEN =
			new Function1<Boolean, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt11Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt11Member> ODD =
			new Function1<Boolean, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt11Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> DIV =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member d) {
			if (b.v == -1 && a.v == -1024)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> MOD =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt11Member, SignedInt11Member, SignedInt11Member, SignedInt11Member> DIVMOD =
			new Procedure4<SignedInt11Member, SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member d, SignedInt11Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt11Member, SignedInt11Member, SignedInt11Member, SignedInt11Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt11Member, SignedInt11Member> PRED =
			new Procedure2<SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			if (a.v == -1024)
				b.v = 1023;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt11Member, SignedInt11Member> SUCC =
			new Procedure2<SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			if (a.v == 1023)
				b.v = -1024;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> POW =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			PowerNonNegative.compute(G.INT11, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt11Member> RAND =
			new Procedure1<SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(2048)-1024);
		}
	};
	
	@Override
	public Procedure1<SignedInt11Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> AND =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> OR =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> XOR =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt11Member, SignedInt11Member> NOT =
			new Procedure2<SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> ANDNOT =
			new Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a, SignedInt11Member b, SignedInt11Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt11Member a, SignedInt11Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 11;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt11Member a, SignedInt11Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt11Member a, SignedInt11Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt11Member> MAXBOUND =
			new Procedure1<SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt11Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt11Member> MINBOUND =
			new Procedure1<SignedInt11Member>()
	{
		@Override
		public void call(SignedInt11Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt11Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt11Member> ISZERO =
			new Function1<Boolean, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt11Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt11Member, SignedInt11Member, SignedInt11Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt11Member b, SignedInt11Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt11Member b, SignedInt11Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt11Member, SignedInt11Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt11Member, SignedInt11Member> SBR =
			new Procedure3<RationalMember, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt11Member b, SignedInt11Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt11Member, SignedInt11Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt11Member, SignedInt11Member> SBD =
			new Procedure3<Double, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(Double a, SignedInt11Member b, SignedInt11Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt11Member, SignedInt11Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt11Member, SignedInt11Member> SBDR =
			new Procedure3<Double, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(Double a, SignedInt11Member b, SignedInt11Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt11Member, SignedInt11Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt11Member, SignedInt11Member, SignedInt11Member> WITHIN =
			new Function3<Boolean, SignedInt11Member, SignedInt11Member, SignedInt11Member>()
	{
		
		@Override
		public Boolean call(SignedInt11Member tol, SignedInt11Member a, SignedInt11Member b) {
			return NumberWithin.compute(G.INT11, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt11Member, SignedInt11Member, SignedInt11Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt11Member a, SignedInt11Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt11Member a, SignedInt11Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt11Member, SignedInt11Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt11Member> ISUNITY =
			new Function1<Boolean, SignedInt11Member>()
	{
		@Override
		public Boolean call(SignedInt11Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt11Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt11Member, SignedInt11Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt11Member constructExactly(byte... vals) {
		SignedInt11Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(BigDecimal... vals) {
		SignedInt11Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(BigInteger... vals) {
		SignedInt11Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(double... vals) {
		SignedInt11Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(float... vals) {
		SignedInt11Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(long... vals) {
		SignedInt11Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(int... vals) {
		SignedInt11Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(short... vals) {
		SignedInt11Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt11Member construct(byte... vals) {
		SignedInt11Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
