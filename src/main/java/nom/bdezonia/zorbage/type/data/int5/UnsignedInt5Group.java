/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.data.int5;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class UnsignedInt5Group
	implements
		Integer<UnsignedInt5Group, UnsignedInt5Member>,
		Bounded<UnsignedInt5Member>,
		BitOperations<UnsignedInt5Member>,
		Random<UnsignedInt5Member>
{

	@Override
	public UnsignedInt5Member construct() {
		return new UnsignedInt5Member();
	}

	@Override
	public UnsignedInt5Member construct(UnsignedInt5Member other) {
		return new UnsignedInt5Member(other);
	}

	@Override
	public UnsignedInt5Member construct(String str) {
		return new UnsignedInt5Member(str);
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> EQ =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> NEQ =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> ASSIGN =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt5Member> ZER =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt5Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> ADD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> SUB =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MUL =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt5Member a, UnsignedInt5Member b) {
			PowerNonNegative.compute(G.UINT5, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt5Member> UNITY =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> LESS =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> LE =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> GREAT =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> GE =
			new Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a, UnsignedInt5Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt5Member, UnsignedInt5Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt5Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt5Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt5Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt5Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MIN =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MAX =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> GCD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			Gcd.compute(G.UINT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> LCM =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			Lcm.compute(G.UINT5, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt5Member> EVEN =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt5Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt5Member> ODD =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt5Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> DIV =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> MOD =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> DIVMOD =
			new Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member d, UnsignedInt5Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> PRED =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v == 0)
				b.v = 31;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> SUCC =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			if (a.v == 31)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> POW =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			if (a.v == 0 && b.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			PowerNonNegative.compute(G.UINT5, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt5Member> RAND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(32) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt5Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> AND =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> OR =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> XOR =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt5Member, UnsignedInt5Member> NOT =
			new Procedure2<UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt5Member, UnsignedInt5Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> ANDNOT =
			new Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a, UnsignedInt5Member b, UnsignedInt5Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt5Member a, UnsignedInt5Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 5;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt5Member a, UnsignedInt5Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, UnsignedInt5Member, UnsignedInt5Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt5Member> MAXBOUND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 31;
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt5Member> MINBOUND =
			new Procedure1<UnsignedInt5Member>()
	{
		@Override
		public void call(UnsignedInt5Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt5Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt5Member> ISZERO =
			new Function1<Boolean, UnsignedInt5Member>()
	{
		@Override
		public Boolean call(UnsignedInt5Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt5Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt5Member, UnsignedInt5Member, UnsignedInt5Member> scale() {
		return MUL;
	}

}