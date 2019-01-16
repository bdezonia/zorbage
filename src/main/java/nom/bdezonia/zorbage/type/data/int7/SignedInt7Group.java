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
package nom.bdezonia.zorbage.type.data.int7;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
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
public class SignedInt7Group
	implements
		Integer<SignedInt7Group, SignedInt7Member>,
		Bounded<SignedInt7Member>,
		BitOperations<SignedInt7Member>,
		Random<SignedInt7Member>
{

	@Override
	public SignedInt7Member construct() {
		return new SignedInt7Member();
	}

	@Override
	public SignedInt7Member construct(SignedInt7Member other) {
		return new SignedInt7Member(other);
	}

	@Override
	public SignedInt7Member construct(String str) {
		return new SignedInt7Member(str);
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> EQ =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> NEQ =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> ASSIGN =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt7Member> ZER =
			new Procedure1<SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt7Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> NEG =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			if (a.v == -64)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> ADD =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> SUB =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> MUL =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt7Member a, SignedInt7Member b) {
			PowerNonNegative.compute(G.INT7, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt7Member> UNITY =
			new Procedure1<SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt7Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> LESS =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> LE =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> GREAT =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt7Member, SignedInt7Member> GE =
			new Function2<Boolean, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a, SignedInt7Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt7Member, SignedInt7Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt7Member, SignedInt7Member> CMP =
			new Function2<java.lang.Integer, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt7Member a, SignedInt7Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt7Member, SignedInt7Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt7Member> SIG =
			new Function1<java.lang.Integer, SignedInt7Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt7Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt7Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> MIN =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> MAX =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> ABS =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			if (a.v == -64)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> GCD =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			Gcd.compute(G.INT7, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> LCM =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			Lcm.compute(G.INT7, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt7Member> EVEN =
			new Function1<Boolean, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt7Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt7Member> ODD =
			new Function1<Boolean, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt7Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> DIV =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member d) {
			if (b.v == -1 && a.v == -64)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> MOD =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt7Member, SignedInt7Member, SignedInt7Member, SignedInt7Member> DIVMOD =
			new Procedure4<SignedInt7Member, SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member d, SignedInt7Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt7Member, SignedInt7Member, SignedInt7Member, SignedInt7Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> PRED =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			if (a.v == -64)
				b.v = 63;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> SUCC =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			if (a.v == 63)
				b.v = -64;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> POW =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			PowerNonNegative.compute(G.INT7, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt7Member> RAND =
			new Procedure1<SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(128)-64);
		}
	};
	
	@Override
	public Procedure1<SignedInt7Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> AND =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> OR =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> XOR =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt7Member, SignedInt7Member> NOT =
			new Procedure2<SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt7Member, SignedInt7Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> ANDNOT =
			new Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a, SignedInt7Member b, SignedInt7Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt7Member a, SignedInt7Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 7;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt7Member a, SignedInt7Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt7Member a, SignedInt7Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, SignedInt7Member, SignedInt7Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt7Member> MAXBOUND =
			new Procedure1<SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a) {
			a.v = 63;
		}
	};

	@Override
	public Procedure1<SignedInt7Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt7Member> MINBOUND =
			new Procedure1<SignedInt7Member>()
	{
		@Override
		public void call(SignedInt7Member a) {
			a.v = -64;
		}
	};

	@Override
	public Procedure1<SignedInt7Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt7Member> ISZERO =
			new Function1<Boolean, SignedInt7Member>()
	{
		@Override
		public Boolean call(SignedInt7Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt7Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt7Member, SignedInt7Member, SignedInt7Member> scale() {
		return MUL;
	}

}
