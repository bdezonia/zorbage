/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

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

	@Override
	public void abs(UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().abs() );
	}

	@Override
	public void multiply(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().multiply(b.v()) );
	}

	@Override
	public void power(int power, UnboundedIntMember a, UnboundedIntMember b) {
		if (power == 0 && a.v() == BigInteger.ZERO) throw new IllegalArgumentException("0^0 is not a number");
		if (power < 0) throw new IllegalArgumentException("Cannot get negative powers from integers");
		b.setV( a.v().pow(power) );
	}

	@Override
	public void zero(UnboundedIntMember a) {
		a.setV( BigInteger.ZERO );
	}

	@Override
	public void negate(UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().negate() );
	}

	@Override
	public void add(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().add(b.v()) );
	}

	@Override
	public void subtract(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().subtract(b.v()) );
	}

	@Override
	public boolean isEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) == 0;
	}

	@Override
	public boolean isNotEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) != 0;
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

	@Override
	public void assign(UnboundedIntMember from, UnboundedIntMember to) {
		to.setV( from.v() );
	}

	@Override
	public void unity(UnboundedIntMember a) {
		assign(ONE, a);
	}

	@Override
	public boolean isLess(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v().compareTo(b.v());
	}

	@Override
	public int signum(UnboundedIntMember a) {
		return a.v().signum();
	}

	@Override
	public void div(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember d) {
		d.setV( a.v().divide(b.v()) );
	}

	@Override
	public void mod(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember m) {
		m.setV( a.v().mod(b.v()) );
	}

	@Override
	public void divMod(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember d, UnboundedIntMember m) {
		BigInteger[] results = a.v().divideAndRemainder(b.v());
		d.setV(results[0]);
		m.setV(results[1]);
	}

	@Override
	public void gcd(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().gcd(b.v()) );
	}

	@Override
	public void lcm(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		BigInteger n = a.v().multiply(b.v()).abs();
		BigInteger d = a.v().gcd(b.v());
		c.setV( n.divide(d) );
	}

	@Override
	public void norm(UnboundedIntMember a, UnboundedIntMember b) {
		abs(a,b);
	}

	@Override
	public boolean isEven(UnboundedIntMember a) {
		return !isOdd(a);
	}

	@Override
	public boolean isOdd(UnboundedIntMember a) {
		return a.v().and(BigInteger.ONE).equals(BigInteger.ONE);
	}

	@Override
	public void pred(UnboundedIntMember a, UnboundedIntMember b) {
		subtract(a, ONE, b);
	}

	@Override
	public void succ(UnboundedIntMember a, UnboundedIntMember b) {
		add(a, ONE, b);
	}

	@Override
	public void bitShiftLeft(int count, UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().shiftLeft(count) );
	}

	@Override
	public void bitShiftRight(int count, UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().shiftRight(count) );
	}

	@Override
	public void bitAnd(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().and(b.v()) );
	}

	@Override
	public void bitOr(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().or(b.v()) );
	}

	@Override
	public void bitXor(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.setV( a.v().xor(b.v()) );
	}

	@Override
	public void bitNot(UnboundedIntMember a, UnboundedIntMember b) {
		b.setV( a.v().not() );
	}

	@Override
	public void min(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		Max.compute(this, a, b, c);
	}

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

	@Override
	public void pow(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		int cmp = b.v().compareTo(BigInteger.ZERO);
		if (cmp < 0)
			throw new IllegalArgumentException("negative powers not supported for unbounded ints");
		else if (cmp == 0) {
			if (a.v() == BigInteger.ZERO)
				throw new IllegalArgumentException("0^0 is not a number");
			else
				assign(ONE, c);
		}
		else if (b.v().compareTo(BigInteger.valueOf(java.lang.Integer.MAX_VALUE)) <= 0) {
			// speed optimization
			c.setV(a.v().pow(b.v().intValue()));
		}
		else { // huge power
			UnboundedIntMember tmp = new UnboundedIntMember(ONE);
			UnboundedIntMember power = new UnboundedIntMember(b);
			while (isGreater(power, ZERO)) {
				multiply(tmp, a, tmp);
				pred(power, power);
			}
			assign(tmp, c);
		}
	}	

}
