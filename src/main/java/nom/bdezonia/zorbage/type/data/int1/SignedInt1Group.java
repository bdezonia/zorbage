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
package nom.bdezonia.zorbage.type.data.int1;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
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
public class SignedInt1Group
	implements
		Integer<SignedInt1Group, SignedInt1Member>,
		Bounded<SignedInt1Member>,
		BitOperations<SignedInt1Member>,
		Random<SignedInt1Member>
{

	@Override
	public SignedInt1Member construct() {
		return new SignedInt1Member();
	}

	@Override
	public SignedInt1Member construct(SignedInt1Member other) {
		return new SignedInt1Member(other);
	}

	@Override
	public SignedInt1Member construct(String str) {
		return new SignedInt1Member(str);
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> EQ =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> NEQ =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt1Member, SignedInt1Member> ASSIGN =
			new Procedure2<SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt1Member> ZER =
			new Procedure1<SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt1Member> zero() {
		return ZER;
	}

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> negate() {
		// in other types -minint negated stays at -minint. so I think this method has nothing to do.
		return ASSIGN;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> ADD =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> SUB =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> MUL =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt1Member a, SignedInt1Member b) {
			if (power == 0 && a.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			throw new IllegalArgumentException("signed int1's can't really support powers");
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt1Member> UNITY =
			new Procedure1<SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a) {
			throw new IllegalArgumentException("signed int1's do not really have Unity. Must fix hierarchy.");
		}
	};

	@Override
	public Procedure1<SignedInt1Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> LESS =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> LE =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> GREAT =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt1Member, SignedInt1Member> GE =
			new Function2<Boolean, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a, SignedInt1Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt1Member, SignedInt1Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt1Member, SignedInt1Member> CMP =
			new Function2<java.lang.Integer, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt1Member a, SignedInt1Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt1Member, SignedInt1Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt1Member> SIG =
			new Function1<java.lang.Integer, SignedInt1Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt1Member a) {
			if (a.v < 0)
				return -1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt1Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> MIN =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> MAX =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> max() {
		return MAX;
	}

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> abs() {
		// in other types abs -minint stays at -minint. so I think this method has nothing to do.
		return ASSIGN;
	}

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> norm() {
		// in other types norm -minint stays at -minint. so I think this method has nothing to do.
		// but norms might be illegal < 0. TODO investigate. Throw? Change hierarchy?
		return ASSIGN;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> GCD =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			Gcd.compute(G.INT1, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> LCM =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			Lcm.compute(G.INT1, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt1Member> EVEN =
			new Function1<Boolean, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a) {
			return a.v == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt1Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt1Member> ODD =
			new Function1<Boolean, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a) {
			return a.v == -1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt1Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> DIV =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member d) {
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> MOD =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt1Member, SignedInt1Member, SignedInt1Member, SignedInt1Member> DIVMOD =
			new Procedure4<SignedInt1Member, SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member d, SignedInt1Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt1Member, SignedInt1Member, SignedInt1Member, SignedInt1Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt1Member, SignedInt1Member> PRED =
			new Procedure2<SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b) {
			if (a.v == 0)
				b.v = -1;
			else
				b.v = 0;
		}
	};

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt1Member, SignedInt1Member> SUCC =
			new Procedure2<SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b) {
			if (a.v == -1)
				b.v = 0;
			else
				b.v = -1;
		}
	};

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> POW =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			if (a.v == 0 && b.v == 0)
				throw new IllegalArgumentException("0^0 is not a number");
			throw new IllegalArgumentException("signed int1's can't really support powers");
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt1Member> RAND =
			new Procedure1<SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			if (rng.nextBoolean())
				a.v = -1;
			else
				a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt1Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> AND =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> OR =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> XOR =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt1Member, SignedInt1Member> NOT =
			new Procedure2<SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b) {
			if (a.v == 0)
				b.v = -1;
			else
				b.v = 0;
		}
	};

	@Override
	public Procedure2<SignedInt1Member, SignedInt1Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> ANDNOT =
			new Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a, SignedInt1Member b, SignedInt1Member c) {
			if (a.v == 0)
				c.v = 0;
			else {
				// TODO: is this code right for the neg nums? adapted from unsigned code
				// a.v == -1
				if (b.v == 0)
					c.v = -1;
				else // b.v == -1
					c.v = 0;
			}
		}
	};

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt1Member a, SignedInt1Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count & 1;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member>()
		{				
			@Override
			public void call(java.lang.Integer count, SignedInt1Member a, SignedInt1Member b) {
				int val = a.v >> count;
				if (a.v < 0 && val == 0) {
					// sign extend
					b.v = -1;
				}
				else
					b.setV( val );
			}
		};

	@Override
	public Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt1Member a, SignedInt1Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, SignedInt1Member, SignedInt1Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt1Member> MAXBOUND =
			new Procedure1<SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a) {
			a.v = 0;
		}
	};

	@Override
	public Procedure1<SignedInt1Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt1Member> MINBOUND =
			new Procedure1<SignedInt1Member>()
	{
		@Override
		public void call(SignedInt1Member a) {
			a.v = -1;
		}
	};

	@Override
	public Procedure1<SignedInt1Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt1Member> ISZERO =
			new Function1<Boolean, SignedInt1Member>()
	{
		@Override
		public Boolean call(SignedInt1Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt1Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt1Member, SignedInt1Member, SignedInt1Member> scale() {
		return MUL;
	}
}
