/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
public class SignedInt6Algebra
	implements
		Integer<SignedInt6Algebra, SignedInt6Member>,
		Bounded<SignedInt6Member>,
		BitOperations<SignedInt6Member>,
		Random<SignedInt6Member>
{

	@Override
	public SignedInt6Member construct() {
		return new SignedInt6Member();
	}

	@Override
	public SignedInt6Member construct(SignedInt6Member other) {
		return new SignedInt6Member(other);
	}

	@Override
	public SignedInt6Member construct(String str) {
		return new SignedInt6Member(str);
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> EQ =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> NEQ =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> ASSIGN =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt6Member> ZER =
			new Procedure1<SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt6Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> NEG =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			if (a.v == -32)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (byte) -a.v;
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> ADD =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> SUB =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> MUL =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt6Member a, SignedInt6Member b) {
			PowerNonNegative.compute(G.INT6, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt6Member> UNITY =
			new Procedure1<SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt6Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> LESS =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> LE =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> GREAT =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt6Member, SignedInt6Member> GE =
			new Function2<Boolean, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a, SignedInt6Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt6Member, SignedInt6Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt6Member, SignedInt6Member> CMP =
			new Function2<java.lang.Integer, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt6Member a, SignedInt6Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt6Member, SignedInt6Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt6Member> SIG =
			new Function1<java.lang.Integer, SignedInt6Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt6Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt6Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> MIN =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> MAX =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> ABS =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			if (a.v == -32)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			if (a.v < 0)
				b.v = (byte) -a.v;
			else
				b.v = a.v;
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> GCD =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			Gcd.compute(G.INT6, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> LCM =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			Lcm.compute(G.INT6, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt6Member> EVEN =
			new Function1<Boolean, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt6Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt6Member> ODD =
			new Function1<Boolean, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt6Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> DIV =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member d) {
			if (b.v == -1 && a.v == -32)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> MOD =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt6Member, SignedInt6Member, SignedInt6Member, SignedInt6Member> DIVMOD =
			new Procedure4<SignedInt6Member, SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member d, SignedInt6Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt6Member, SignedInt6Member, SignedInt6Member, SignedInt6Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> PRED =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			if (a.v == -32)
				b.v = 31;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> SUCC =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			if (a.v == 31)
				b.v = -32;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> POW =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			PowerNonNegative.compute(G.INT6, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt6Member> RAND =
			new Procedure1<SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(64)-32);
		}
	};
	
	@Override
	public Procedure1<SignedInt6Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> AND =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> OR =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> XOR =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt6Member, SignedInt6Member> NOT =
			new Procedure2<SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt6Member, SignedInt6Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> ANDNOT =
			new Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a, SignedInt6Member b, SignedInt6Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt6Member a, SignedInt6Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 6;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt6Member a, SignedInt6Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt6Member a, SignedInt6Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	public Procedure3<java.lang.Integer, SignedInt6Member, SignedInt6Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt6Member> MAXBOUND =
			new Procedure1<SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a) {
			a.v = 31;
		}
	};

	@Override
	public Procedure1<SignedInt6Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt6Member> MINBOUND =
			new Procedure1<SignedInt6Member>()
	{
		@Override
		public void call(SignedInt6Member a) {
			a.v = -32;
		}
	};

	@Override
	public Procedure1<SignedInt6Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt6Member> ISZERO =
			new Function1<Boolean, SignedInt6Member>()
	{
		@Override
		public Boolean call(SignedInt6Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt6Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt6Member, SignedInt6Member, SignedInt6Member> scale() {
		return MUL;
	}

}