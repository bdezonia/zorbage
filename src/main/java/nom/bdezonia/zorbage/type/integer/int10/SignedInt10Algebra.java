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
package nom.bdezonia.zorbage.type.integer.int10;

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
public class SignedInt10Algebra
	implements
		Integer<SignedInt10Algebra, SignedInt10Member>,
		Bounded<SignedInt10Member>,
		BitOperations<SignedInt10Member>,
		Random<SignedInt10Member>,
		Tolerance<SignedInt10Member,SignedInt10Member>,
		ScaleByOneHalf<SignedInt10Member>,
		ScaleByTwo<SignedInt10Member>,
		Conjugate<SignedInt10Member>,
		ConstructibleFromBytes<SignedInt10Member>,
		ConstructibleFromShorts<SignedInt10Member>,
		ConstructibleFromInts<SignedInt10Member>,
		ConstructibleFromLongs<SignedInt10Member>,
		ConstructibleFromFloats<SignedInt10Member>,
		ConstructibleFromDoubles<SignedInt10Member>,
		ConstructibleFromBigIntegers<SignedInt10Member>,
		ConstructibleFromBigDecimals<SignedInt10Member>,
		ExactlyConstructibleFromBytes<SignedInt10Member>,
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
		return "10-bit signed int";
	}

	@Override
	public SignedInt10Member construct() {
		return new SignedInt10Member();
	}

	@Override
	public SignedInt10Member construct(SignedInt10Member other) {
		return new SignedInt10Member(other);
	}

	@Override
	public SignedInt10Member construct(String str) {
		return new SignedInt10Member(str);
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> EQ =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> NEQ =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt10Member, SignedInt10Member> ASSIGN =
			new Procedure2<SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt10Member> ZER =
			new Procedure1<SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt10Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt10Member,SignedInt10Member> NEG =
			new Procedure2<SignedInt10Member,SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			if (a.v == -512)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> ADD =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> SUB =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> MUL =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt10Member a, SignedInt10Member b) {
			PowerNonNegative.compute(G.INT10, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt10Member> UNITY =
			new Procedure1<SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt10Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> LESS =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> LE =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> GREAT =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt10Member, SignedInt10Member> GE =
			new Function2<Boolean, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a, SignedInt10Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt10Member, SignedInt10Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt10Member, SignedInt10Member> CMP =
			new Function2<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt10Member a, SignedInt10Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt10Member, SignedInt10Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt10Member> SIG =
			new Function1<java.lang.Integer, SignedInt10Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt10Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt10Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> MIN =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> MAX =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt10Member, SignedInt10Member> ABS =
			new Procedure2<SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			if (a.v == -512)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> GCD =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			SteinGcd.compute(G.INT10, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> LCM =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			SteinLcm.compute(G.INT10, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt10Member> EVEN =
			new Function1<Boolean, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt10Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt10Member> ODD =
			new Function1<Boolean, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt10Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> DIV =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member d) {
			if (b.v == -1 && a.v == -512)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> MOD =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt10Member, SignedInt10Member, SignedInt10Member, SignedInt10Member> DIVMOD =
			new Procedure4<SignedInt10Member, SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member d, SignedInt10Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt10Member, SignedInt10Member, SignedInt10Member, SignedInt10Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt10Member, SignedInt10Member> PRED =
			new Procedure2<SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			if (a.v == -512)
				b.v = 511;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt10Member, SignedInt10Member> SUCC =
			new Procedure2<SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			if (a.v == 511)
				b.v = -512;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> POW =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			PowerNonNegative.compute(G.INT10, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt10Member> RAND =
			new Procedure1<SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(1024)-512);
		}
	};
	
	@Override
	public Procedure1<SignedInt10Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> AND =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> OR =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> XOR =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt10Member, SignedInt10Member> NOT =
			new Procedure2<SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> ANDNOT =
			new Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a, SignedInt10Member b, SignedInt10Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt10Member a, SignedInt10Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 10;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt10Member a, SignedInt10Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt10Member a, SignedInt10Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt10Member> MAXBOUND =
			new Procedure1<SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<SignedInt10Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt10Member> MINBOUND =
			new Procedure1<SignedInt10Member>()
	{
		@Override
		public void call(SignedInt10Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<SignedInt10Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt10Member> ISZERO =
			new Function1<Boolean, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt10Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt10Member, SignedInt10Member, SignedInt10Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt10Member b, SignedInt10Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member> SBHPR =
			new Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt10Member b, SignedInt10Member c) {
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
	public Procedure3<HighPrecisionMember, SignedInt10Member, SignedInt10Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, SignedInt10Member, SignedInt10Member> SBR =
			new Procedure3<RationalMember, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt10Member b, SignedInt10Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt10Member, SignedInt10Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, SignedInt10Member, SignedInt10Member> SBD =
			new Procedure3<Double, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(Double a, SignedInt10Member b, SignedInt10Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt10Member, SignedInt10Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, SignedInt10Member, SignedInt10Member> SBDR =
			new Procedure3<Double, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(Double a, SignedInt10Member b, SignedInt10Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, SignedInt10Member, SignedInt10Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, SignedInt10Member, SignedInt10Member, SignedInt10Member> WITHIN =
			new Function3<Boolean, SignedInt10Member, SignedInt10Member, SignedInt10Member>()
	{
		
		@Override
		public Boolean call(SignedInt10Member tol, SignedInt10Member a, SignedInt10Member b) {
			return NumberWithin.compute(G.INT10, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt10Member, SignedInt10Member, SignedInt10Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> STWO =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt10Member a, SignedInt10Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> SHALF =
			new Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, SignedInt10Member a, SignedInt10Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, SignedInt10Member, SignedInt10Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, SignedInt10Member> ISUNITY =
			new Function1<Boolean, SignedInt10Member>()
	{
		@Override
		public Boolean call(SignedInt10Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, SignedInt10Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<SignedInt10Member, SignedInt10Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public SignedInt10Member constructExactly(byte... vals) {
		SignedInt10Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(BigDecimal... vals) {
		SignedInt10Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(BigInteger... vals) {
		SignedInt10Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(double... vals) {
		SignedInt10Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(float... vals) {
		SignedInt10Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(long... vals) {
		SignedInt10Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(int... vals) {
		SignedInt10Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(short... vals) {
		SignedInt10Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public SignedInt10Member construct(byte... vals) {
		SignedInt10Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
