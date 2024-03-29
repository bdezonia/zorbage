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
package nom.bdezonia.zorbage.type.integer.int8;

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
public class SignedInt8Algebra
	implements
		Integer<SignedInt8Algebra, SignedInt8Member>,
		Bounded<SignedInt8Member>,
		BitOperations<SignedInt8Member>,
		Random<SignedInt8Member>,
		Tolerance<SignedInt8Member,SignedInt8Member>,
		ScaleByOneHalf<SignedInt8Member>,
		ScaleByTwo<SignedInt8Member>,
		ConstructibleFromBytes<SignedInt8Member>,
		ConstructibleFromShorts<SignedInt8Member>,
		ConstructibleFromInts<SignedInt8Member>,
		ConstructibleFromLongs<SignedInt8Member>,
		ConstructibleFromFloats<SignedInt8Member>,
		ConstructibleFromDoubles<SignedInt8Member>,
		ConstructibleFromBigIntegers<SignedInt8Member>,
		ConstructibleFromBigDecimals<SignedInt8Member>,
		Conjugate<SignedInt8Member>,
		ExactlyConstructibleFromBytes<SignedInt8Member>,
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
		return "8-bit signed int";
	}

	public SignedInt8Algebra() { }
	
	@Override
	public SignedInt8Member construct() {
		return new SignedInt8Member();
	}

	@Override
	public SignedInt8Member construct(SignedInt8Member other) {
		return new SignedInt8Member(other);
	}

	@Override
	public SignedInt8Member construct(String s) {
		return new SignedInt8Member(s);
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> EQ =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> NEQ =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> ASSIGN =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member from, SignedInt8Member to) {
			to.v = from.v;
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> ABS =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( Math.abs(a.v()) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MUL =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt8Member a, SignedInt8Member b) {
			PowerNonNegative.compute(G.INT8, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt8Member> ZER =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> NEG =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.setV( -a.v() );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> ADD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> SUB =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt8Member> UNITY =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = 1;
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> LESS =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> LE =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> GREATER =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isGreater() {
		return GREATER;
	}

	private final Function2<Boolean,SignedInt8Member,SignedInt8Member> GE =
			new Function2<Boolean, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a, SignedInt8Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt8Member,SignedInt8Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt8Member,SignedInt8Member> CMP =
			new Function2<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt8Member a, SignedInt8Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt8Member,SignedInt8Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt8Member> SIG =
			new Function1<java.lang.Integer, SignedInt8Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt8Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt8Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> DIV =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			if (b.v() == -1 && a.v() == Byte.MIN_VALUE)
				throw new IllegalArgumentException("cannot divide minint by -1");
			c.setV( (a.v() / b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MOD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() % b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt8Member,SignedInt8Member,SignedInt8Member,SignedInt8Member> DIVMOD =
			new Procedure4<SignedInt8Member, SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member d, SignedInt8Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt8Member,SignedInt8Member,SignedInt8Member,SignedInt8Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> GCD =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			SteinGcd.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> LCM =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			SteinLcm.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt8Member> EVEN =
			new Function1<Boolean,SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return (a.v() & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt8Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt8Member> ODD =
			new Function1<Boolean,SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return (a.v() & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt8Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> PRED =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( (a.v() - 1) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> SUCC =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( (a.v() + 1) );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt8Member> MAXBOUND =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = a.componentMax();
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt8Member> MINBOUND =
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			a.v = a.componentMin();
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITAND =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() & b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITOR =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() | b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITXOR =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() ^ b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<SignedInt8Member,SignedInt8Member> BITNOT =
			new Procedure2<SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b) {
			b.setV( ~a.v() );
		}
	};

	@Override
	public Procedure2<SignedInt8Member,SignedInt8Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> BITANDNOT =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			c.setV( (a.v() & ~b.v()) );
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> bitAndNot() {
		return BITANDNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 8;
				b.setV( (a.v() << count) );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( (a.v() >> count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt8Member a, SignedInt8Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( (a.v() >>> count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt8Member,SignedInt8Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MIN =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Min.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> MAX =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			Max.compute(G.INT8, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt8Member> RAND = 
			new Procedure1<SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(256) - 128);
		}
	};
	
	@Override
	public Procedure1<SignedInt8Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> POW =
			new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
			power().call((int)b.v(), a, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> pow() {
		return POW;
	}

	private final Function1<Boolean, SignedInt8Member> ISZERO =
			new Function1<Boolean, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt8Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt8Member b, SignedInt8Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt8Member b, SignedInt8Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt8Member, SignedInt8Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt8Member, SignedInt8Member> SBR =
			new Procedure3<RationalMember, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt8Member b, SignedInt8Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt8Member, SignedInt8Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt8Member, SignedInt8Member> SBD =
			new Procedure3<Double, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(Double a, SignedInt8Member b, SignedInt8Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt8Member, SignedInt8Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt8Member, SignedInt8Member> SBDR =
			new Procedure3<Double, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(Double a, SignedInt8Member b, SignedInt8Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt8Member, SignedInt8Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt8Member, SignedInt8Member, SignedInt8Member> WITHIN =
			new Function3<Boolean, SignedInt8Member, SignedInt8Member, SignedInt8Member>()
	{
		
		@Override
		public Boolean call(SignedInt8Member tol, SignedInt8Member a, SignedInt8Member b) {
			return NumberWithin.compute(G.INT8, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt8Member, SignedInt8Member, SignedInt8Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt8Member a, SignedInt8Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt8Member a, SignedInt8Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt8Member, SignedInt8Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt8Member> ISUNITY =
			new Function1<Boolean, SignedInt8Member>()
	{
		@Override
		public Boolean call(SignedInt8Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt8Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt8Member, SignedInt8Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt8Member constructExactly(byte... vals) {
		SignedInt8Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(BigDecimal... vals) {
		SignedInt8Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(BigInteger... vals) {
		SignedInt8Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(double... vals) {
		SignedInt8Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(float... vals) {
		SignedInt8Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(long... vals) {
		SignedInt8Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(int... vals) {
		SignedInt8Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(short... vals) {
		SignedInt8Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt8Member construct(byte... vals) {
		SignedInt8Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
