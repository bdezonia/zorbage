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
package nom.bdezonia.zorbage.type.integer.int15;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.BoundedType;
import nom.bdezonia.zorbage.algebra.type.markers.EnumerableType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.IntegerType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnsignedType;
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
public class UnsignedInt15Algebra
	implements
		Integer<UnsignedInt15Algebra, UnsignedInt15Member>,
		Bounded<UnsignedInt15Member>,
		BitOperations<UnsignedInt15Member>,
		Random<UnsignedInt15Member>,
		Tolerance<UnsignedInt15Member,UnsignedInt15Member>,
		ScaleByOneHalf<UnsignedInt15Member>,
		ScaleByTwo<UnsignedInt15Member>,
		ConstructibleFromBytes<UnsignedInt15Member>,
		ConstructibleFromShorts<UnsignedInt15Member>,
		ConstructibleFromInts<UnsignedInt15Member>,
		ConstructibleFromLongs<UnsignedInt15Member>,
		ConstructibleFromFloats<UnsignedInt15Member>,
		ConstructibleFromDoubles<UnsignedInt15Member>,
		ConstructibleFromBigIntegers<UnsignedInt15Member>,
		ConstructibleFromBigDecimals<UnsignedInt15Member>,
		Conjugate<UnsignedInt15Member>,
		BoundedType,
		EnumerableType,
		ExactType,
		IntegerType,
		NumberType,
		UnityIncludedType,
		UnsignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "15-bit unsigned int";
	}

	@Override
	public UnsignedInt15Member construct() {
		return new UnsignedInt15Member();
	}

	@Override
	public UnsignedInt15Member construct(UnsignedInt15Member other) {
		return new UnsignedInt15Member(other);
	}

	@Override
	public UnsignedInt15Member construct(String str) {
		return new UnsignedInt15Member(str);
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> EQ =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> NEQ =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt15Member, UnsignedInt15Member> ASSIGN =
			new Procedure2<UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt15Member> ZER =
			new Procedure1<UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt15Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> ADD =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> SUB =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> MUL =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt15Member a, UnsignedInt15Member b) {
			PowerNonNegative.compute(G.UINT15, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt15Member> UNITY =
			new Procedure1<UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt15Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> LESS =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> LE =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> GREAT =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> GE =
			new Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a, UnsignedInt15Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt15Member, UnsignedInt15Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt15Member a, UnsignedInt15Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt15Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt15Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt15Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt15Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> MIN =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> MAX =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> GCD =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			SteinGcd.compute(G.UINT15, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> LCM =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			SteinLcm.compute(G.UINT15, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt15Member> EVEN =
			new Function1<Boolean, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt15Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt15Member> ODD =
			new Function1<Boolean, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt15Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> DIV =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> MOD =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> DIVMOD =
			new Procedure4<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member d, UnsignedInt15Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt15Member, UnsignedInt15Member> PRED =
			new Procedure2<UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b) {
			if (a.v == 0)
				b.v = 32767;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt15Member, UnsignedInt15Member> SUCC =
			new Procedure2<UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b) {
			if (a.v == 32767)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> POW =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			PowerNonNegative.compute(G.UINT15, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt15Member> RAND =
			new Procedure1<UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(32768) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt15Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> AND =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> OR =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> XOR =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt15Member, UnsignedInt15Member> NOT =
			new Procedure2<UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> ANDNOT =
			new Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt15Member a, UnsignedInt15Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 15;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt15Member a, UnsignedInt15Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt15Member> MAXBOUND =
			new Procedure1<UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<UnsignedInt15Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt15Member> MINBOUND =
			new Procedure1<UnsignedInt15Member>()
	{
		@Override
		public void call(UnsignedInt15Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<UnsignedInt15Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt15Member> ISZERO =
			new Function1<Boolean, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt15Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt15Member b, UnsignedInt15Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt15Member b, UnsignedInt15Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt15Member, UnsignedInt15Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt15Member, UnsignedInt15Member> SBR =
			new Procedure3<RationalMember, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt15Member b, UnsignedInt15Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt15Member, UnsignedInt15Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member> SBD =
			new Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(Double a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member> SBDR =
			new Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(Double a, UnsignedInt15Member b, UnsignedInt15Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt15Member, UnsignedInt15Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> WITHIN =
			new Function3<Boolean, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt15Member tol, UnsignedInt15Member a, UnsignedInt15Member b) {
			return NumberWithin.compute(G.UINT15, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt15Member, UnsignedInt15Member, UnsignedInt15Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt15Member a, UnsignedInt15Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt15Member a, UnsignedInt15Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt15Member, UnsignedInt15Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt15Member> ISUNITY =
			new Function1<Boolean, UnsignedInt15Member>()
	{
		@Override
		public Boolean call(UnsignedInt15Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt15Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<UnsignedInt15Member, UnsignedInt15Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public UnsignedInt15Member construct(BigDecimal... vals) {
		UnsignedInt15Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(BigInteger... vals) {
		UnsignedInt15Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(double... vals) {
		UnsignedInt15Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(float... vals) {
		UnsignedInt15Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(long... vals) {
		UnsignedInt15Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(int... vals) {
		UnsignedInt15Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(short... vals) {
		UnsignedInt15Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public UnsignedInt15Member construct(byte... vals) {
		UnsignedInt15Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
