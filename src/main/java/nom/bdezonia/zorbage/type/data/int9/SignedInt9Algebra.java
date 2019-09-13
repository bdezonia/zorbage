/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.int9;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class SignedInt9Algebra
	implements
		Integer<SignedInt9Algebra, SignedInt9Member>,
		Bounded<SignedInt9Member>,
		BitOperations<SignedInt9Member>,
		Random<SignedInt9Member>,
		Tolerance<SignedInt9Member,SignedInt9Member>
{

	@Override
	public SignedInt9Member construct() {
		return new SignedInt9Member();
	}

	@Override
	public SignedInt9Member construct(SignedInt9Member other) {
		return new SignedInt9Member(other);
	}

	@Override
	public SignedInt9Member construct(String str) {
		return new SignedInt9Member(str);
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> EQ =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v == b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> NEQ =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v != b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<SignedInt9Member, SignedInt9Member> ASSIGN =
			new Procedure2<SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<SignedInt9Member> ZER =
			new Procedure1<SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a) {
			a.v = 0;
		}
	};
	
	@Override
	public Procedure1<SignedInt9Member> zero() {
		return ZER;
	}

	private final Procedure2<SignedInt9Member,SignedInt9Member> NEG =
			new Procedure2<SignedInt9Member,SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			if (a.v == -256)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			b.v = (short) -a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> negate() {
		return NEG;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> ADD =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v + b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> add() {
		return ADD;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> SUB =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v - b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> subtract() {
		return SUB;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> MUL =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v * b.v);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> POWER =
			new Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer power, SignedInt9Member a, SignedInt9Member b) {
			PowerNonNegative.compute(G.INT9, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> power() {
		return POWER;
	}

	private final Procedure1<SignedInt9Member> UNITY =
			new Procedure1<SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a) {
			a.v = 1;
		}
	};

	@Override
	public Procedure1<SignedInt9Member> unity() {
		return UNITY;
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> LESS =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v < b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isLess() {
		return LESS;
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> LE =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v <= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> GREAT =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v > b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, SignedInt9Member, SignedInt9Member> GE =
			new Function2<Boolean, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a, SignedInt9Member b) {
			return a.v >= b.v;
		}
	};

	@Override
	public Function2<Boolean, SignedInt9Member, SignedInt9Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer, SignedInt9Member, SignedInt9Member> CMP =
			new Function2<java.lang.Integer, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt9Member a, SignedInt9Member b) {
			if (a.v < b.v)
				return -1;
			else if (a.v > b.v)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<java.lang.Integer, SignedInt9Member, SignedInt9Member> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer, SignedInt9Member> SIG =
			new Function1<java.lang.Integer, SignedInt9Member>()
	{
		@Override
		public java.lang.Integer call(SignedInt9Member a) {
			if (a.v < 0)
				return -1;
			else if (a.v > 0)
				return 1;
			else
				return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer, SignedInt9Member> signum() {
		return SIG;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> MIN =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			if (a.v < b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> min() {
		return MIN;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> MAX =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			if (a.v > b.v)
				c.set(a);
			else
				c.set(b);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> max() {
		return MAX;
	}

	private final Procedure2<SignedInt9Member, SignedInt9Member> ABS =
			new Procedure2<SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			if (a.v == -256)
				throw new IllegalArgumentException("Cannot convert -minint symmetrically");
			else if (a.v < 0)
				b.v = (short) -a.v;
			else
				b.v = a.v;
		}
	};
	
	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> abs() {
		return ABS;
	}

	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> norm() {
		return ABS;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> GCD =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			Gcd.compute(G.INT9, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> gcd() {
		return GCD;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> LCM =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			Lcm.compute(G.INT9, a, b, c);
		}
	};
	
	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> lcm() {
		return LCM;
	}

	private final Function1<Boolean, SignedInt9Member> EVEN =
			new Function1<Boolean, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a) {
			return (a.v & 1) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt9Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, SignedInt9Member> ODD =
			new Function1<Boolean, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a) {
			return (a.v & 1) == 1;
		}
	};
			
	@Override
	public Function1<Boolean, SignedInt9Member> isOdd() {
		return ODD;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> DIV =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member d) {
			if (b.v == -1 && a.v == -256)
				throw new IllegalArgumentException("cannot divide minint by -1");
			d.setV(a.v / b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> div() {
		return DIV;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> MOD =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member m) {
			m.setV(a.v % b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> mod() {
		return MOD;
	}

	private final Procedure4<SignedInt9Member, SignedInt9Member, SignedInt9Member, SignedInt9Member> DIVMOD =
			new Procedure4<SignedInt9Member, SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member d, SignedInt9Member m) {
			div().call(a, b, d);
			mod().call(a, b, m);
		}
	};

	@Override
	public Procedure4<SignedInt9Member, SignedInt9Member, SignedInt9Member, SignedInt9Member> divMod() {
		return DIVMOD;
	}

	private final Procedure2<SignedInt9Member, SignedInt9Member> PRED =
			new Procedure2<SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			if (a.v == -256)
				b.v = 255;
			else
				b.setV(a.v - 1);
		}
	};

	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> pred() {
		return PRED;
	}

	private final Procedure2<SignedInt9Member, SignedInt9Member> SUCC =
			new Procedure2<SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			if (a.v == 255)
				b.v = -256;
			else
				b.setV(a.v + 1);
		}
	};

	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> succ() {
		return SUCC;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> POW =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			PowerNonNegative.compute(G.INT9, b.v, a, c);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> pow() {
		return POW;
	}

	private final Procedure1<SignedInt9Member> RAND =
			new Procedure1<SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextInt(512)-256);
		}
	};
	
	@Override
	public Procedure1<SignedInt9Member> random() {
		return RAND;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> AND =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v & b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> bitAnd() {
		return AND;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> OR =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v | b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> bitOr() {
		return OR;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> XOR =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v ^ b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> bitXor() {
		return XOR;
	}

	private final Procedure2<SignedInt9Member, SignedInt9Member> NOT =
			new Procedure2<SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b) {
			b.setV(~a.v);
		}
	};

	@Override
	public Procedure2<SignedInt9Member, SignedInt9Member> bitNot() {
		return NOT;
	}

	private final Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> ANDNOT =
			new Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a, SignedInt9Member b, SignedInt9Member c) {
			c.setV(a.v & ~b.v);
		}
	};

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> bitAndNot() {
		return ANDNOT;
	}

	private final Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> SHL =
			new Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt9Member a, SignedInt9Member b) {
			if (count < 0)
				bitShiftRight().call(-count, a, b);
			else {
				count = count % 9;
				b.setV( a.v << count );
			}
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> SHR =
			new Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt9Member a, SignedInt9Member b) {
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
	public Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> SHRZ =
			new Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(java.lang.Integer count, SignedInt9Member a, SignedInt9Member b) {
			if (count < 0)
				bitShiftLeft().call(-count, a, b);
			else
				b.setV( a.v >>> count );
		}
	};

	@Override
	public Procedure3<java.lang.Integer, SignedInt9Member, SignedInt9Member> bitShiftRightFillZero() {
		return SHRZ;
	}

	private final Procedure1<SignedInt9Member> MAXBOUND =
			new Procedure1<SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a) {
			a.v = 255;
		}
	};

	@Override
	public Procedure1<SignedInt9Member> maxBound() {
		return MAXBOUND;
	}

	private final Procedure1<SignedInt9Member> MINBOUND =
			new Procedure1<SignedInt9Member>()
	{
		@Override
		public void call(SignedInt9Member a) {
			a.v = -256;
		}
	};

	@Override
	public Procedure1<SignedInt9Member> minBound() {
		return MINBOUND;
	}

	private final Function1<Boolean, SignedInt9Member> ISZERO =
			new Function1<Boolean, SignedInt9Member>()
	{
		@Override
		public Boolean call(SignedInt9Member a) {
			return a.v == 0;
		}
	};

	@Override
	public Function1<Boolean, SignedInt9Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<SignedInt9Member, SignedInt9Member, SignedInt9Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, SignedInt9Member, SignedInt9Member> SBHP =
			new Procedure3<HighPrecisionMember, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(HighPrecisionMember a, SignedInt9Member b, SignedInt9Member c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.v()));
			int signum = tmp.signum();
			if (signum < 0)
				tmp = tmp.subtract(G.ONE_HALF);
			else
				tmp = tmp.add(G.ONE_HALF);
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, SignedInt9Member, SignedInt9Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, SignedInt9Member, SignedInt9Member> SBR =
			new Procedure3<RationalMember, SignedInt9Member, SignedInt9Member>()
	{
		@Override
		public void call(RationalMember a, SignedInt9Member b, SignedInt9Member c) {
			BigInteger tmp = BigInteger.valueOf(b.v());
			tmp = tmp.multiply(a.n());
			tmp = tmp.divide(a.d());
			c.setV(tmp.intValue());
		}
	};

	@Override
	public Procedure3<RationalMember, SignedInt9Member, SignedInt9Member> scaleByRational() {
		return SBR;
	}

	private final Function3<Boolean, SignedInt9Member, SignedInt9Member, SignedInt9Member> WITHIN =
			new Function3<Boolean, SignedInt9Member, SignedInt9Member, SignedInt9Member>()
	{
		
		@Override
		public Boolean call(SignedInt9Member tol, SignedInt9Member a, SignedInt9Member b) {
			return NumberWithin.compute(G.INT9, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, SignedInt9Member, SignedInt9Member, SignedInt9Member> within() {
		return WITHIN;
	}

}
