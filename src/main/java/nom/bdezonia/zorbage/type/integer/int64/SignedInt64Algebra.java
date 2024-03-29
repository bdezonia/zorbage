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
package nom.bdezonia.zorbage.type.integer.int64;

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
public class SignedInt64Algebra
	implements
		Integer<SignedInt64Algebra, SignedInt64Member>,
		Bounded<SignedInt64Member>,
		BitOperations<SignedInt64Member>,
		Random<SignedInt64Member>,
		Tolerance<SignedInt64Member,SignedInt64Member>,
		ScaleByOneHalf<SignedInt64Member>,
		ScaleByTwo<SignedInt64Member>,
		ConstructibleFromBytes<SignedInt64Member>,
		ConstructibleFromShorts<SignedInt64Member>,
		ConstructibleFromInts<SignedInt64Member>,
		ConstructibleFromLongs<SignedInt64Member>,
		ConstructibleFromFloats<SignedInt64Member>,
		ConstructibleFromDoubles<SignedInt64Member>,
		ConstructibleFromBigIntegers<SignedInt64Member>,
		ConstructibleFromBigDecimals<SignedInt64Member>,
		Conjugate<SignedInt64Member>,
		ExactlyConstructibleFromBytes<SignedInt64Member>,
		ExactlyConstructibleFromShorts<SignedInt64Member>,
		ExactlyConstructibleFromInts<SignedInt64Member>,
		ExactlyConstructibleFromLongs<SignedInt64Member>,
		BoundedType,
		EnumerableType,
		ExactType,
		IntegerType,
		NumberType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final SignedInt64Member ZERO = new SignedInt64Member();
	private static final SignedInt64Member ONE = new SignedInt64Member(1);
	
	@Override
	public String typeDescription() {
		return "64-bit signed int";
	}

	public SignedInt64Algebra() { }

	@Override
	public SignedInt64Member construct() {
		return new SignedInt64Member();
	}

	@Override
	public SignedInt64Member construct(SignedInt64Member other) {
		return new SignedInt64Member(other);
	}

	@Override
	public SignedInt64Member construct(String s) {
		return new SignedInt64Member(s);
	}
	
	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> EQ =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() == b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> NEQ =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() != b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> ASSIGN =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member from, SignedInt64Member to) {
			to.v = from.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> ABS =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			if (a.v() == Long.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = Math.abs(a.v);
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> abs() {
		return ABS;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MUL =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() * b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt64Member a, SignedInt64Member b) {
			PowerNonNegative.compute(G.INT64, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt64Member> ZER =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> NEG =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			if (a.v() == Long.MIN_VALUE)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> ADD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() + b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> SUB =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() - b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> subtract() {
		return SUB;
	}

	private final Procedure1<SignedInt64Member> UNITY =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.v  = 1;
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> LESS =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() < b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> LE =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() <= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> GREAT =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() > b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,SignedInt64Member,SignedInt64Member> GE =
			new Function2<Boolean, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a, SignedInt64Member b) {
			return a.v() >= b.v();
		}
	};
	
	@Override
	public Function2<Boolean,SignedInt64Member,SignedInt64Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,SignedInt64Member,SignedInt64Member> CMP =
			new Function2<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt64Member a, SignedInt64Member b) {
			if (a.v() < b.v()) return -1;
			if (a.v() > b.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,SignedInt64Member,SignedInt64Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,SignedInt64Member> SIG =
			new Function1<java.lang.Integer, SignedInt64Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt64Member a) {
			if (a.v() < 0) return -1;
			if (a.v() > 0) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,SignedInt64Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> DIV =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			if (b.v() == -1 && a.v() == Long.MIN_VALUE)
				throw new IllegalArgumentException("cannot divide minint by -1");
			c.setV( a.v() / b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MOD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() % b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt64Member,SignedInt64Member,SignedInt64Member,SignedInt64Member> DIVMOD =
			new Procedure4<SignedInt64Member, SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member d, SignedInt64Member m) {
			div().call(a,b,d);
			mod().call(a,b,m);
		}
	};
	
	@Override
	public Procedure4<SignedInt64Member,SignedInt64Member,SignedInt64Member,SignedInt64Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> GCD =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			SteinGcd.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> LCM =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			SteinLcm.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> lcm() {
		return LCM;
	}

	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> norm() {
		return ABS;
	}

	private final Function1<Boolean,SignedInt64Member> EVEN =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return (a.v() & 1) == 0;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt64Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean,SignedInt64Member> ODD =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return (a.v() & 1) == 1;
		}
	};
	
	@Override
	public Function1<Boolean,SignedInt64Member> isOdd() {
		return ODD;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> PRED =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.v = a.v - 1;
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> SUCC =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.v = a.v + 1;
		}
	};
	
	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> succ() {
		return SUCC;
	}

	private final Procedure1<SignedInt64Member> MAXBOUND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.v = a.componentMax();
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt64Member> MINBOUND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			a.v = a.componentMin();
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> minBound() {
		return MINBOUND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITAND =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() & b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitAnd() {
		return BITAND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITOR =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() | b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitOr() {
		return BITOR;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITXOR =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() ^ b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitXor() {
		return BITXOR;
	}

	private final Procedure2<SignedInt64Member,SignedInt64Member> BITNOT =
			new Procedure2<SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b) {
			b.setV( ~a.v() );
		}
	};
	
	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> BITANDNOT =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			c.setV( a.v() & ~b.v() );
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> bitAndNot() {
		return BITANDNOT;
	}

	@Override
	public Procedure2<SignedInt64Member,SignedInt64Member> bitNot() {
		return BITNOT;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHL =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 64;
				b.setV( a.v() << count );
			}
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftLeft() {
		return BITSHL;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHR =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftRight() {
		return BITSHR;
	}

	private final Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> BITSHRZ =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt64Member a, SignedInt64Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v() >>> count );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,SignedInt64Member,SignedInt64Member> bitShiftRightFillZero() {
		return BITSHRZ;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MIN =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Min.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> MAX =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			Max.compute(G.INT64, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> max() {
		return MAX;
	}

	private final Procedure1<SignedInt64Member> RAND =
			new Procedure1<SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a) {
			// Safely generate a random long in the right range
			// I'm avoiding nextLong() because it uses a 48-bit generator so it can't cover the
			// entire space. This might not either but it might be a better approach.
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			BigInteger bi = new BigInteger(64, rng);
			a.v = bi.longValue();
		}
	};
	
	@Override
	public Procedure1<SignedInt64Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> POW =
			new Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
			if (b.v() < 0)
				throw new IllegalArgumentException("Cannot get negative powers from int64s");
			if (signum().call(a) == 0 && signum().call(b) == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			SignedInt64Member tmp = new SignedInt64Member(ONE);
			SignedInt64Member pow = new SignedInt64Member(b);
			while (!isEqual().call(pow, ZERO)) {
				multiply().call(tmp, a, tmp);
				pred().call(pow,pow);
			}
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt64Member,SignedInt64Member,SignedInt64Member> pow() {
		return POW;
	}

	private final Function1<Boolean, SignedInt64Member> ISZERO =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt64Member, SignedInt64Member, SignedInt64Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, SignedInt64Member a, SignedInt64Member b) {
			BigDecimal tmp = factor.v();
			tmp = tmp.multiply(new BigDecimal(a.v()));
			b.setV(tmp.longValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, SignedInt64Member a, SignedInt64Member b) {
			BigDecimal tmp = factor.v();
			tmp = tmp.multiply(new BigDecimal(a.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			b.setV(tmp.longValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt64Member, SignedInt64Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt64Member, SignedInt64Member> SBR =
			new Procedure3<RationalMember, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(RationalMember factor, SignedInt64Member a, SignedInt64Member b) {
			BigInteger tmp = BigInteger.valueOf(a.v());
			tmp = tmp.multiply(factor.n());
			tmp = tmp.divide(factor.d());
			b.setV(tmp.longValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt64Member, SignedInt64Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt64Member, SignedInt64Member> SBD =
			new Procedure3<Double, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(Double factor, SignedInt64Member a, SignedInt64Member b) {
			BigDecimal tmp = BigDecimal.valueOf(factor).multiply(BigDecimal.valueOf(a.v()));
			b.setV(tmp.longValue());
		}
	};

	@Override
	public Procedure3<Double, SignedInt64Member, SignedInt64Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt64Member, SignedInt64Member> SBDR =
			new Procedure3<Double, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(Double factor, SignedInt64Member a, SignedInt64Member b) {
			BigDecimal tmp = BigDecimal.valueOf(factor).multiply(BigDecimal.valueOf(a.v()));
			if (tmp.signum() < 0)
				tmp = tmp.subtract(BigDecimalUtils.ONE_HALF);
			else
				tmp = tmp.add(BigDecimalUtils.ONE_HALF);
			b.setV(tmp.longValue());
		}
	};

	@Override
	public Procedure3<Double, SignedInt64Member, SignedInt64Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt64Member, SignedInt64Member, SignedInt64Member> WITHIN =
			new Function3<Boolean, SignedInt64Member, SignedInt64Member, SignedInt64Member>()
	{
		
		@Override
		public Boolean call(SignedInt64Member tol, SignedInt64Member a, SignedInt64Member b) {
			return NumberWithin.compute(G.INT64, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt64Member, SignedInt64Member, SignedInt64Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt64Member a, SignedInt64Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt64Member a, SignedInt64Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt64Member, SignedInt64Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt64Member> ISUNITY =
			new Function1<Boolean, SignedInt64Member>()
	{
		@Override
		public Boolean call(SignedInt64Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt64Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt64Member, SignedInt64Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt64Member constructExactly(long... vals) {
		SignedInt64Member v = construct();
		v.setFromLongsExact(vals);
		return v;
	}

	@Override
	public SignedInt64Member constructExactly(int... vals) {
		SignedInt64Member v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public SignedInt64Member constructExactly(short... vals) {
		SignedInt64Member v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public SignedInt64Member constructExactly(byte... vals) {
		SignedInt64Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(BigDecimal... vals) {
		SignedInt64Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(BigInteger... vals) {
		SignedInt64Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(double... vals) {
		SignedInt64Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(float... vals) {
		SignedInt64Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(long... vals) {
		SignedInt64Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(int... vals) {
		SignedInt64Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(short... vals) {
		SignedInt64Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt64Member construct(byte... vals) {
		SignedInt64Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
