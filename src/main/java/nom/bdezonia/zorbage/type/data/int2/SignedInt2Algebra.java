/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int2;

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
public class SignedInt2Algebra
	implements
		Integer<SignedInt2Algebra, SignedInt2Member>,
		Bounded<SignedInt2Member>,
		BitOperations<SignedInt2Member>,
		Random<SignedInt2Member>
{

	@Override
	public SignedInt2Member construct() {
		return new SignedInt2Member();
	}

	@Override
	public SignedInt2Member construct(SignedInt2Member other) {
		return new SignedInt2Member(other);
	}

	@Override
	public SignedInt2Member construct(String str) {
		return new SignedInt2Member(str);
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> EQ =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> NEQ =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> ASSIGN =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt2Member> ZER =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt2Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> NEG =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> ADD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> SUB =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MUL =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt2Member a, SignedInt2Member b) {
			PowerNonNegative.compute(G.INT2, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt2Member> UNITY =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt2Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> LESS =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> LE =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> GREAT =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt2Member, SignedInt2Member> GE =
			new Function2<Boolean, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a, SignedInt2Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt2Member, SignedInt2Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member> CMP =
			new Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt2Member, SignedInt2Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt2Member> SIG =
			new Function1<java.lang.Integer, SignedInt2Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt2Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt2Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MIN =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MAX =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> ABS =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> GCD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			Gcd.compute(G.INT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> LCM =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			Lcm.compute(G.INT2, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt2Member> EVEN =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt2Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt2Member> ODD =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt2Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> DIV =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member d) {
			if (b.v == -1 && a.v == -2)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> MOD =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member> DIVMOD =
			new Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member d, SignedInt2Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt2Member, SignedInt2Member, SignedInt2Member, SignedInt2Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> PRED =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == -2)
				b.v = 1;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> SUCC =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			if (a.v == 1)
				b.v = -2;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> POW =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			PowerNonNegative.compute(G.INT2, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt2Member> RAND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(4)-2);
		}
	};
	
	@Override
	public Procedure1<SignedInt2Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> AND =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> OR =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> XOR =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt2Member, SignedInt2Member> NOT =
			new Procedure2<SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt2Member, SignedInt2Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> ANDNOT =
			new Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a, SignedInt2Member b, SignedInt2Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 2;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt2Member a, SignedInt2Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, SignedInt2Member, SignedInt2Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt2Member> MAXBOUND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt2Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt2Member> MINBOUND =
			new Procedure1<SignedInt2Member>()
	{
		@Override
		public void call(SignedInt2Member a) {
			a.v = -2;
		}
	};

	@Override
	public Procedure1<SignedInt2Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt2Member> ISZERO =
			new Function1<Boolean, SignedInt2Member>()
	{
		@Override
		public Boolean call(SignedInt2Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt2Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt2Member, SignedInt2Member, SignedInt2Member> scale() {
		return MUL;
	}

}
