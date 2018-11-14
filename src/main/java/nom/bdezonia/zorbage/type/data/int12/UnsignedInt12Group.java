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
package nom.bdezonia.zorbage.type.data.int12;

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
public class UnsignedInt12Group
	implements
		Integer<UnsignedInt12Group, UnsignedInt12Member>,
		Bounded<UnsignedInt12Member>,
		BitOperations<UnsignedInt12Member>,
		Random<UnsignedInt12Member>
{

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
			Gcd.compute(G.UINT12, a, b, c);
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
			Lcm.compute(G.UINT12, a, b, c);
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
			if (a.v == 0 && b.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			c.setV((int)Math.pow(a.v, b.v));
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
				bitShiftRight().call(Math.abs(count), a, b);
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
				bitShiftLeft().call(Math.abs(count), a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, UnsignedInt12Member, UnsignedInt12Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt12Member> MAXBOUND =
			new Procedure1<UnsignedInt12Member>()
	{
		@Override
		public void call(UnsignedInt12Member a) {
			a.v = 0x0fff;
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
			a.v = 0;
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

}
