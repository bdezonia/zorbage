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
package nom.bdezonia.zorbage.type.data.bigint;

import java.math.BigInteger;

import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Integer;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnboundedIntGroup
  implements
    Integer<UnboundedIntGroup, UnboundedIntMember>,
    BitOperations<UnboundedIntMember>
{
	private static final UnboundedIntMember ZERO = new UnboundedIntMember();
	private static final UnboundedIntMember ONE = new UnboundedIntMember(BigInteger.ONE);
	
	public UnboundedIntGroup() {
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> ABS =
			new Procedure2<UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			b.setV( a.v().abs() );
		}
	};
	
	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> abs() {
		return ABS;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> MUL =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().multiply(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer, UnboundedIntMember, UnboundedIntMember> POWER =
			new Procedure3<java.lang.Integer, UnboundedIntMember, UnboundedIntMember>()
	{
		@Override
		public void call(java.lang.Integer power, UnboundedIntMember a, UnboundedIntMember b) {
			if (power == 0 && a.v() == BigInteger.ZERO) throw new IllegalArgumentException("0^0 is not a number");
			if (power < 0) throw new IllegalArgumentException("Cannot get negative powers from integers");
			b.setV( a.v().pow(power) );
		}	
	};
	
	@Override
	public Procedure3<java.lang.Integer, UnboundedIntMember, UnboundedIntMember> power() {
		return POWER;
	}

	private final Procedure1<UnboundedIntMember> ZER =
			new Procedure1<UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a) {
			a.setV( BigInteger.ZERO );
		}
	};
			
	@Override
	public Procedure1<UnboundedIntMember> zero() {
		return ZER;
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> NEG =
			new Procedure2<UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			b.setV( a.v().negate() );
		}
	};

	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> negate() {
		return NEG;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> ADD =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().add(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> add() {
		return ADD;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> SUB =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().subtract(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> EQ =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{

		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) == 0;
		}

	};
	
	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> NEQ =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) != 0;
		}
	};
	
	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public UnboundedIntMember construct() {
		return new UnboundedIntMember();
	}

	@Override
	public UnboundedIntMember construct(UnboundedIntMember other) {
		return new UnboundedIntMember(other);
	}

	@Override
	public UnboundedIntMember construct(String s) {
		return new UnboundedIntMember(s);
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> ASSIGN =
		new Procedure2<UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember from, UnboundedIntMember to) {
			to.setV( from.v() );
		}
	};

	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<UnboundedIntMember> UNI =
			new Procedure1<UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a) {
			a.setV(BigInteger.ONE);
		}
	};

	@Override
	public Procedure1<UnboundedIntMember> unity() {
		return UNI;
	}
	
	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> LS =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) < 0;
		}
	};

	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isLess() {
		return LS;
	}

	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> LSE =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) <= 0;
		}
	};

	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isLessEqual() {
		return LSE;
	}

	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> GR =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) > 0;
		}
	};

	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isGreater() {
		return GR;
	}

	private final Function2<Boolean,UnboundedIntMember,UnboundedIntMember> GRE =
			new Function2<Boolean,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a, UnboundedIntMember b) {
			return compare().call(a,b) >= 0;
		}
	};

	@Override
	public Function2<Boolean,UnboundedIntMember,UnboundedIntMember> isGreaterEqual() {
		return GRE;
	}

	private final Function2<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> CMP =
			new Function2<java.lang.Integer,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public java.lang.Integer call(UnboundedIntMember a, UnboundedIntMember b) {
			return a.v().compareTo(b.v());
		}
	};
	
	@Override
	public Function2<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,UnboundedIntMember> SIG =
			new Function1<java.lang.Integer,UnboundedIntMember>()
	{
		@Override
		public java.lang.Integer call(UnboundedIntMember a) {
			return a.v().signum();
		}
	};

	@Override
	public Function1<java.lang.Integer,UnboundedIntMember> signum() {
		return SIG;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> DIV =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().divide(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> div() {
		return DIV;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> MOD =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().mod(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> mod() {
		return MOD;
	}

	private final Procedure4<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> DIVMOD =
			new Procedure4<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember d,UnboundedIntMember m) {
			BigInteger[] results = a.v().divideAndRemainder(b.v());
			d.setV(results[0]);
			m.setV(results[1]);
		}
	};
	
	@Override
	public Procedure4<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> divMod() {
		return DIVMOD;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> GCD =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			c.setV( a.v().gcd(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> gcd() {
		return GCD;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> LCM =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b,UnboundedIntMember c) {
			BigInteger n = a.v().multiply(b.v()).abs();
			BigInteger d = a.v().gcd(b.v());
			c.setV( n.divide(d) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> lcm() {
		return LCM;
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> NRM =
			new Procedure2<UnboundedIntMember, UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			abs().call(a,b);
		}
	};
			
	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> norm() {
		return NRM;
	}

	private final Function1<Boolean,UnboundedIntMember> EV =
			new Function1<Boolean, UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a) {
			return !isOdd().call(a);
		}
	};

	@Override
	public Function1<Boolean,UnboundedIntMember> isEven() {
		return EV;
	}

	private final Function1<Boolean,UnboundedIntMember> OD =
			new Function1<Boolean, UnboundedIntMember>()
	{
		@Override
		public Boolean call(UnboundedIntMember a) {
			return a.v().and(BigInteger.ONE).equals(BigInteger.ONE);
		}
	};

	@Override
	public Function1<Boolean,UnboundedIntMember> isOdd() {
		return OD;
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> PRD =
			new Procedure2<UnboundedIntMember, UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			subtract().call(a, ONE, b);
		}
	};
			
	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> pred() {
		return PRD;
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> SUC =
			new Procedure2<UnboundedIntMember, UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			add().call(a, ONE, b);
		}
	};
			
	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> succ() {
		return SUC;
	}

	private final Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> SHL =
			new Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(java.lang.Integer count, UnboundedIntMember a, UnboundedIntMember b) {
			b.setV( a.v().shiftLeft(count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> bitShiftLeft() {
		return SHL;
	}

	private final Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> SHR =
			new Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(java.lang.Integer count, UnboundedIntMember a, UnboundedIntMember b) {
			b.setV( a.v().shiftRight(count) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,UnboundedIntMember,UnboundedIntMember> bitShiftRight() {
		return SHR;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> AND =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			c.setV( a.v().and(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> bitAnd() {
		return AND;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> OR =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			c.setV( a.v().or(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> bitOr() {
		return OR;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> XOR =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			c.setV( a.v().xor(b.v()) );
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> bitXor() {
		return XOR;
	}

	private final Procedure2<UnboundedIntMember,UnboundedIntMember> NOT =
			new Procedure2<UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b) {
			b.setV( a.v().not() );
		}
	};
	
	@Override
	public Procedure2<UnboundedIntMember,UnboundedIntMember> bitNot() {
		return NOT;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> MIN =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			Min.compute(G.BIGINT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> min() {
		return MIN;
	}

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> MAX =
			new Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			Max.compute(G.BIGINT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> max() {
		return MAX;
	}

/*
 TODO - implement these 
  
  	public void andNot(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().andNot(b.v()));
	}
	
	public void bitCount(UnboundedIntMember a, SignedInt32Member b) {
		b.setV( a.v().bitCount() );
	}
	
	public void bitLength(UnboundedIntMember a, SignedInt32Member b) {
		b.setV( a.v().bitLength() );
	}
	
	public void getLowestSetBit(UnboundedIntMember a, SignedInt32Member b) {
		b.setV( a.v().getLowestSetBit() );
	}
	
	public void clearBit(int n, UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().clearBit(n) );
	}
	
	public void flipBit(int n, UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().flipBit(n) );
	}
	
	public boolean isProbablePrime(int certainty, UnboundedIntMember a) {
		return a.v().isProbablePrime(certainty);
	}
	
	public void modInverse(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().modInverse(b.v()) );
	}
	
	public void modPow(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c, UnboundedIntMember d) {
		d.setV( a.v().modPow(b.v(), c.v()) );
	}
	
	public void nextProbablePrime(UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().nextProbablePrime() );
	}
	
	public void setBit(int n, UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().setBit(n) );
	}
	
	public boolean testBit(int n, UnboundedIntMember a) {
		return a.v().testBit(n);
	}
*/

	private final Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> POW =
			new Procedure3<UnboundedIntMember, UnboundedIntMember, UnboundedIntMember>()
	{
		@Override
		public void call(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
			int cmp = b.v().compareTo(BigInteger.ZERO);
			if (cmp < 0)
				throw new IllegalArgumentException("negative powers not supported for unbounded ints");
			else if (cmp == 0) {
				if (a.v() == BigInteger.ZERO)
					throw new IllegalArgumentException("0^0 is not a number");
				else
					assign().call(ONE, c);
			}
			else if (b.v().compareTo(BigInteger.valueOf(java.lang.Integer.MAX_VALUE)) <= 0) {
				// speed optimization
				c.setV(a.v().pow(b.v().intValue()));
			}
			else { // huge power
				UnboundedIntMember tmp = new UnboundedIntMember(ONE);
				UnboundedIntMember power = new UnboundedIntMember(b);
				while (isGreater().call(power, ZERO)) {
					multiply().call(tmp, a, tmp);
					pred().call(power, power);
				}
				assign().call(tmp, c);
			}
		}
	};

	@Override
	public Procedure3<UnboundedIntMember,UnboundedIntMember,UnboundedIntMember> pow() {
		return POW;
	}	

}
