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
package nom.bdezonia.zorbage.type.integer.int12;

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
public class UnsignedInt12Algebra
	implements
		Integer<UnsignedInt12Algebra, UnsignedInt12Member>,
		Bounded<UnsignedInt12Member>,
		BitOperations<UnsignedInt12Member>,
		Random<UnsignedInt12Member>,
		Tolerance<UnsignedInt12Member,UnsignedInt12Member>,
		ScaleByOneHalf<UnsignedInt12Member>,
		ScaleByTwo<UnsignedInt12Member>,
		ConstructibleFromBytes<UnsignedInt12Member>,
		ConstructibleFromShorts<UnsignedInt12Member>,
		ConstructibleFromInts<UnsignedInt12Member>,
		ConstructibleFromLongs<UnsignedInt12Member>,
		ConstructibleFromFloats<UnsignedInt12Member>,
		ConstructibleFromDoubles<UnsignedInt12Member>,
		ConstructibleFromBigIntegers<UnsignedInt12Member>,
		ConstructibleFromBigDecimals<UnsignedInt12Member>,
		Conjugate<UnsignedInt12Member>,
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
		return "12-bit unsigned int";
	}

	@Override
	public UnsignedInt12Member construct() {
		return new UnsignedInt12Member();
	}

	@Override
	public UnsignedInt12Member construct(UnsignedInt12Member other) {
		return new UnsignedInt12Member(other);
	}

	@Override
	public UnsignedInt12Member construct(String str) {
		return new UnsignedInt12Member(str);
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> EQ =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> NEQ =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt12Member, UnsignedInt12Member> ASSIGN =
			new Procedure2<UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt12Member> ZER =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt12Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> ADD =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> SUB =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> MUL =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt12Member a, UnsignedInt12Member b) {
			PowerNonNegative.compute(G.UINT12, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt12Member> UNITY =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt12Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> LESS =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> LE =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> GREAT =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> GE =
			new Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a, UnsignedInt12Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt12Member, UnsignedInt12Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt12Member a, UnsignedInt12Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt12Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt12Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt12Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt12Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> MIN =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> MAX =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> GCD =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			SteinGcd.compute(G.UINT12, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> LCM =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			SteinLcm.compute(G.UINT12, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt12Member> EVEN =
			new Function1<Boolean, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt12Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt12Member> ODD =
			new Function1<Boolean, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt12Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> DIV =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> MOD =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> DIVMOD =
			new Procedure4<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member d, UnsignedInt12Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt12Member, UnsignedInt12Member> PRED =
			new Procedure2<UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b) {
			if (a.v == 0)
				b.v = 0x0fff;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt12Member, UnsignedInt12Member> SUCC =
			new Procedure2<UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b) {
			if (a.v == 0x0fff)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> POW =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			PowerNonNegative.compute(G.UINT12, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt12Member> RAND =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(0x1000) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt12Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> AND =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> OR =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> XOR =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt12Member, UnsignedInt12Member> NOT =
			new Procedure2<UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> ANDNOT =
			new Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt12Member a, UnsignedInt12Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 12;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt12Member a, UnsignedInt12Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt12Member> MAXBOUND =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			a.v = a.componentMax();
		}
	};

	@Override
	public Procedure1<UnsignedInt12Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt12Member> MINBOUND =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			a.v = a.componentMin();
		}
	};

	@Override
	public Procedure1<UnsignedInt12Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt12Member> ISZERO =
			new Function1<Boolean, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt12Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member> SBHP =
			new Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt12Member b, UnsignedInt12Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member> SBHPR =
			new Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(HighPrecisionMember a, UnsignedInt12Member b, UnsignedInt12Member c) {
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
	public Procedure3<HighPrecisionMember, UnsignedInt12Member, UnsignedInt12Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<RationalMember, UnsignedInt12Member, UnsignedInt12Member> SBR =
			new Procedure3<RationalMember, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(RationalMember a, UnsignedInt12Member b, UnsignedInt12Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, UnsignedInt12Member, UnsignedInt12Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member> SBD =
			new Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(Double a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV((int)(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member> SBDR =
			new Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(Double a, UnsignedInt12Member b, UnsignedInt12Member c) {
			c.setV((int) Math.round(a * b.v()));
		}
	};

	@Override
	public Procedure3<Double, UnsignedInt12Member, UnsignedInt12Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Function3<Boolean, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> WITHIN =
			new Function3<Boolean, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member>()
	{
		
		@Override
		public Boolean call(UnsignedInt12Member tol, UnsignedInt12Member a, UnsignedInt12Member b) {
			return NumberWithin.compute(G.UINT12, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, UnsignedInt12Member, UnsignedInt12Member, UnsignedInt12Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> STWO =
			new Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt12Member a, UnsignedInt12Member b) {
			b.setV(a.v << numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> SHALF =
			new Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, UnsignedInt12Member a, UnsignedInt12Member b) {
			b.setV(a.v >> numTimes);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, UnsignedInt12Member> ISUNITY =
			new Function1<Boolean, UnsignedInt12Member>()
	{
		@Override
		public Boolean call(UnsignedInt12Member a) {
			return a.v == 1;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt12Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<UnsignedInt12Member, UnsignedInt12Member> conjugate() {
		return ASSIGN;
	}

	@Override
	public UnsignedInt12Member construct(BigDecimal... vals) {
		UnsignedInt12Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(BigInteger... vals) {
		UnsignedInt12Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(double... vals) {
		UnsignedInt12Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(float... vals) {
		UnsignedInt12Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(long... vals) {
		UnsignedInt12Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(int... vals) {
		UnsignedInt12Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(short... vals) {
		UnsignedInt12Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public UnsignedInt12Member construct(byte... vals) {
		UnsignedInt12Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
