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
package nom.bdezonia.zorbage.type.data.int10;

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
public class UnsignedInt10Group
	implements
		Integer<UnsignedInt10Group, UnsignedInt10Member>,
		Bounded<UnsignedInt10Member>,
		BitOperations<UnsignedInt10Member>,
		Random<UnsignedInt10Member>
{

	@Override
	public UnsignedInt10Member construct() {
		return new UnsignedInt10Member();
	}

	@Override
	public UnsignedInt10Member construct(UnsignedInt10Member other) {
		return new UnsignedInt10Member(other);
	}

	@Override
	public UnsignedInt10Member construct(String str) {
		return new UnsignedInt10Member(str);
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> EQ =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> NEQ =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt10Member, UnsignedInt10Member> ASSIGN =
			new Procedure2<UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt10Member> ZER =
			new Procedure1<UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt10Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> ADD =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> SUB =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> MUL =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt10Member a, UnsignedInt10Member b) {
			PowerNonNegative.compute(G.UINT10, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt10Member> UNITY =
			new Procedure1<UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt10Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> LESS =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> LE =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> GREAT =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> GE =
			new Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a, UnsignedInt10Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt10Member, UnsignedInt10Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt10Member a, UnsignedInt10Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt10Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt10Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt10Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt10Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> MIN =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> MAX =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> GCD =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			Gcd.compute(G.UINT10, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> LCM =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			Lcm.compute(G.UINT10, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt10Member> EVEN =
			new Function1<Boolean, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt10Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt10Member> ODD =
			new Function1<Boolean, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt10Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> DIV =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> MOD =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> DIVMOD =
			new Procedure4<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member d, UnsignedInt10Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt10Member, UnsignedInt10Member> PRED =
			new Procedure2<UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b) {
			if (a.v == 0)
				b.v = 1023;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt10Member, UnsignedInt10Member> SUCC =
			new Procedure2<UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b) {
			if (a.v == 1023)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> POW =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			if (a.v == 0 && b.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			PowerNonNegative.compute(G.UINT10, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt10Member> RAND =
			new Procedure1<UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(1024) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt10Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> AND =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> OR =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> XOR =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt10Member, UnsignedInt10Member> NOT =
			new Procedure2<UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt10Member, UnsignedInt10Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> ANDNOT =
			new Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a, UnsignedInt10Member b, UnsignedInt10Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt10Member a, UnsignedInt10Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 10;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt10Member a, UnsignedInt10Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, UnsignedInt10Member, UnsignedInt10Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt10Member> MAXBOUND =
			new Procedure1<UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a) {
			a.v = 1023;
		}
	};

	@Override
	public Procedure1<UnsignedInt10Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt10Member> MINBOUND =
			new Procedure1<UnsignedInt10Member>()
	{
		@Override
		public void call(UnsignedInt10Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt10Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt10Member> ISZERO =
			new Function1<Boolean, UnsignedInt10Member>()
	{
		@Override
		public Boolean call(UnsignedInt10Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt10Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt10Member, UnsignedInt10Member, UnsignedInt10Member> scale() {
		return MUL;
	}

}
