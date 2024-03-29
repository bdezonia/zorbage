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
package nom.bdezonia.zorbage.type.integer.int3;

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
public class SignedInt3Algebra
	implements
		Integer<SignedInt3Algebra, SignedInt3Member>,
		Bounded<SignedInt3Member>,
		BitOperations<SignedInt3Member>,
		Random<SignedInt3Member>,
		Tolerance<SignedInt3Member,SignedInt3Member>,
		ScaleByOneHalf<SignedInt3Member>,
		ScaleByTwo<SignedInt3Member>,
		ConstructibleFromBytes<SignedInt3Member>,
		ConstructibleFromShorts<SignedInt3Member>,
		ConstructibleFromInts<SignedInt3Member>,
		ConstructibleFromLongs<SignedInt3Member>,
		ConstructibleFromFloats<SignedInt3Member>,
		ConstructibleFromDoubles<SignedInt3Member>,
		ConstructibleFromBigIntegers<SignedInt3Member>,
		ConstructibleFromBigDecimals<SignedInt3Member>,
		Conjugate<SignedInt3Member>,
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
		return "3-bit signed int";
	}

	@Override
	public SignedInt3Member construct() {
		return new SignedInt3Member();
	}

	@Override
	public SignedInt3Member construct(SignedInt3Member other) {
		return new SignedInt3Member(other);
	}

	@Override
	public SignedInt3Member construct(String str) {
		return new SignedInt3Member(str);
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> EQ =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> NEQ =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> ASSIGN =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt3Member> ZER =
			new Procedure1<SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt3Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> NEG =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			if (a.v == -4)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> ADD =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> SUB =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> MUL =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt3Member a, SignedInt3Member b) {
			PowerNonNegative.compute(G.INT3, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt3Member> UNITY =
			new Procedure1<SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt3Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> LESS =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> LE =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> GREAT =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt3Member, SignedInt3Member> GE =
			new Function2<Boolean, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a, SignedInt3Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt3Member, SignedInt3Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt3Member, SignedInt3Member> CMP =
			new Function2<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt3Member a, SignedInt3Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt3Member, SignedInt3Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt3Member> SIG =
			new Function1<java.lang.Integer, SignedInt3Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt3Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt3Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> MIN =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> MAX =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> ABS =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			if (a.v == -4)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> GCD =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			SteinGcd.compute(G.INT3, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> LCM =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			SteinLcm.compute(G.INT3, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt3Member> EVEN =
			new Function1<Boolean, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt3Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt3Member> ODD =
			new Function1<Boolean, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt3Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> DIV =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member d) {
			if (b.v == -1 && a.v == -4)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> MOD =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt3Member, SignedInt3Member, SignedInt3Member, SignedInt3Member> DIVMOD =
			new Procedure4<SignedInt3Member, SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member d, SignedInt3Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt3Member, SignedInt3Member, SignedInt3Member, SignedInt3Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> PRED =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			if (a.v == -4)
				b.v = 3;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> SUCC =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			if (a.v == 3)
				b.v = -4;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> POW =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			PowerNonNegative.compute(G.INT3, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt3Member> RAND =
			new Procedure1<SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(8)-4);
		}
	};
	
	@Override
	public Procedure1<SignedInt3Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> AND =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> OR =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> XOR =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt3Member, SignedInt3Member> NOT =
			new Procedure2<SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> ANDNOT =
			new Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a, SignedInt3Member b, SignedInt3Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt3Member a, SignedInt3Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 3;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt3Member a, SignedInt3Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt3Member a, SignedInt3Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt3Member> MAXBOUND =
			new Procedure1<SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt3Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt3Member> MINBOUND =
			new Procedure1<SignedInt3Member>()
	{
		@Override
		public void call(SignedInt3Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt3Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt3Member> ISZERO =
			new Function1<Boolean, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt3Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt3Member, SignedInt3Member, SignedInt3Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt3Member b, SignedInt3Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt3Member b, SignedInt3Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt3Member, SignedInt3Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt3Member, SignedInt3Member> SBR =
			new Procedure3<RationalMember, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt3Member b, SignedInt3Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt3Member, SignedInt3Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt3Member, SignedInt3Member> SBD =
			new Procedure3<Double, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(Double a, SignedInt3Member b, SignedInt3Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt3Member, SignedInt3Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt3Member, SignedInt3Member> SBDR =
			new Procedure3<Double, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(Double a, SignedInt3Member b, SignedInt3Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt3Member, SignedInt3Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt3Member, SignedInt3Member, SignedInt3Member> WITHIN =
			new Function3<Boolean, SignedInt3Member, SignedInt3Member, SignedInt3Member>()
	{
		
		@Override
		public Boolean call(SignedInt3Member tol, SignedInt3Member a, SignedInt3Member b) {
			return NumberWithin.compute(G.INT3, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt3Member, SignedInt3Member, SignedInt3Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt3Member a, SignedInt3Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt3Member a, SignedInt3Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt3Member, SignedInt3Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt3Member> ISUNITY =
			new Function1<Boolean, SignedInt3Member>()
	{
		@Override
		public Boolean call(SignedInt3Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt3Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt3Member, SignedInt3Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt3Member construct(BigDecimal... vals) {
		SignedInt3Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(BigInteger... vals) {
		SignedInt3Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(double... vals) {
		SignedInt3Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(float... vals) {
		SignedInt3Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(long... vals) {
		SignedInt3Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(int... vals) {
		SignedInt3Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(short... vals) {
		SignedInt3Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt3Member construct(byte... vals) {
		SignedInt3Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
