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
package nom.bdezonia.zorbage.type.data.int6;

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
public class UnsignedInt6Group
	implements
		Integer<UnsignedInt6Group, UnsignedInt6Member>,
		Bounded<UnsignedInt6Member>,
		BitOperations<UnsignedInt6Member>,
		Random<UnsignedInt6Member>
{

	@Override
	public UnsignedInt6Member construct() {
		return new UnsignedInt6Member();
	}

	@Override
	public UnsignedInt6Member construct(UnsignedInt6Member other) {
		return new UnsignedInt6Member(other);
	}

	@Override
	public UnsignedInt6Member construct(String str) {
		return new UnsignedInt6Member(str);
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> EQ =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> NEQ =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<UnsignedInt6Member, UnsignedInt6Member> ASSIGN =
			new Procedure2<UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnsignedInt6Member> ZER =
			new Procedure1<UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<UnsignedInt6Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> negate() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> ADD =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> add() {
		return ADD;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> SUB =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> subtract() {
		return SUB;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> MUL =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> POWER =
			new Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer power, UnsignedInt6Member a, UnsignedInt6Member b) {
			PowerNonNegative.compute(G.UINT6, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> power() {
		return POWER;
	}

	private final Procedure1<UnsignedInt6Member> UNITY =
			new Procedure1<UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<UnsignedInt6Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> LESS =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> LE =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> GREAT =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> GE =
			new Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a, UnsignedInt6Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, UnsignedInt6Member, UnsignedInt6Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> CMP =
			new Function2<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt6Member a, UnsignedInt6Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, UnsignedInt6Member> SIG =
			new Function1<java.lang.Integer, UnsignedInt6Member>()
	{
		@Override
		public java.lang.Integer call(UnsignedInt6Member a) {
			if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, UnsignedInt6Member> signum() {
		return SIG;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> MIN =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> min() {
		return MIN;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> MAX =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> abs() {
		return ASSIGN;
	}

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> norm() {
		return ASSIGN;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> GCD =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			Gcd.compute(G.UINT6, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> gcd() {
		return GCD;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> LCM =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			Lcm.compute(G.UINT6, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, UnsignedInt6Member> EVEN =
			new Function1<Boolean, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt6Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, UnsignedInt6Member> ODD =
			new Function1<Boolean, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, UnsignedInt6Member> isOdd() {
		return ODD;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> DIV =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> div() {
		return DIV;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> MOD =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> mod() {
		return MOD;
	}

	private final Procedure4<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> DIVMOD =
			new Procedure4<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member d, UnsignedInt6Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<UnsignedInt6Member, UnsignedInt6Member> PRED =
			new Procedure2<UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b) {
			if (a.v == 0)
				b.v = 63;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> pred() {
		return PRED;
	}

	private final Procedure2<UnsignedInt6Member, UnsignedInt6Member> SUCC =
			new Procedure2<UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b) {
			if (a.v == 63)
				b.v = 0;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> succ() {
		return SUCC;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> POW =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			if (a.v == 0 && b.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			PowerNonNegative.compute(G.UINT6, b.v, a, c);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> pow() {
		return POW;
	}

	private final Procedure1<UnsignedInt6Member> RAND =
			new Procedure1<UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(64) );
		}
	};
	
	@Override
	public Procedure1<UnsignedInt6Member> random() {
		return RAND;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> AND =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> bitAnd() {
		return AND;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> OR =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> bitOr() {
		return OR;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> XOR =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> bitXor() {
		return XOR;
	}

	private final Procedure2<UnsignedInt6Member, UnsignedInt6Member> NOT =
			new Procedure2<UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<UnsignedInt6Member, UnsignedInt6Member> bitNot() {
		return NOT;
	}

	private final Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> ANDNOT =
			new Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a, UnsignedInt6Member b, UnsignedInt6Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> SHL =
			new Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt6Member a, UnsignedInt6Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 6;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> bitShiftLeft() {
		return SHL;
	}

	@Override
	public Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> bitShiftRight() {
		// since this type is unsigned should never shift ones into upper bit. just copy SHRZ.
		return SHRZ;
	}

	private final Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> SHRZ =
			new Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer count, UnsignedInt6Member a, UnsignedInt6Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, UnsignedInt6Member, UnsignedInt6Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<UnsignedInt6Member> MAXBOUND =
			new Procedure1<UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a) {
			a.v = 63;
		}
	};

	@Override
	public Procedure1<UnsignedInt6Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<UnsignedInt6Member> MINBOUND =
			new Procedure1<UnsignedInt6Member>()
	{
		@Override
		public void call(UnsignedInt6Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<UnsignedInt6Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, UnsignedInt6Member> ISZERO =
			new Function1<Boolean, UnsignedInt6Member>()
	{
		@Override
		public Boolean call(UnsignedInt6Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, UnsignedInt6Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<UnsignedInt6Member, UnsignedInt6Member, UnsignedInt6Member> scale() {
		return MUL;
	}

}