/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.data;

import java.math.BigInteger;

import zorbage.type.algebra.BitOperations;
import zorbage.type.algebra.Integer;

// TODO: BigInteger has some more methods than this class

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnboundedIntInteger
  implements
    Integer<UnboundedIntInteger, UnboundedIntMember>,
    BitOperations<UnboundedIntMember>
{

	private static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	
	@Override
	public void abs(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.abs();
	}

	@Override
	public void multiply(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.multiply(b.v);
	}

	@Override
	public void power(int power, UnboundedIntMember a, UnboundedIntMember b) {
		if (power < 0) throw new IllegalArgumentException("Cannot get negative powers from integers");
		BigInteger tmp = BigInteger.ONE;
		if (power > 0)
			for (int i = 1; i <= power; i++)
				tmp = tmp.multiply(a.v);
		b.v = tmp;
	}

	@Override
	public void zero(UnboundedIntMember z) {
		z.v = BigInteger.ZERO;
	}

	@Override
	public void negate(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.negate();
	}

	@Override
	public void add(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.add(b.v);
	}

	@Override
	public void subtract(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.subtract(b.v);
	}

	@Override
	public boolean isEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) == 0;
	}

	@Override
	public boolean isNotEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) != 0;
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
		to.v = from.v;
	}

	@Override
	public void unity(UnboundedIntMember a) {
		a.v = BigInteger.ONE;
	}

	@Override
	public boolean isLess(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) < 0;
	}

	@Override
	public boolean isLessEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) < 1;
	}

	@Override
	public boolean isGreater(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v) >= 0;
	}

	@Override
	public int compare(UnboundedIntMember a, UnboundedIntMember b) {
		return a.v.compareTo(b.v);
	}

	@Override
	public int signum(UnboundedIntMember a) {
		return a.v.signum();
	}

	@Override
	public void div(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember d) {
		d.v = a.v.divide(b.v);
	}

	@Override
	public void mod(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember m) {
		m.v = a.v.mod(b.v);
	}

	@Override
	public void divMod(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember d, UnboundedIntMember m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.gcd(b.v);
	}

	@Override
	public void lcm(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		BigInteger n = a.v.multiply(b.v).abs();
		BigInteger d = a.v.gcd(b.v);
		c.v = n.divide(d);
	}

	@Override
	public void norm(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.abs();
	}

	@Override
	public boolean isEven(UnboundedIntMember a) {
		return a.v.mod(TWO).equals(BigInteger.ZERO);
	}

	@Override
	public boolean isOdd(UnboundedIntMember a) {
		return a.v.mod(TWO).equals(BigInteger.ONE);
	}

	@Override
	public void pred(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.subtract(BigInteger.ONE);
	}

	@Override
	public void succ(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.add(BigInteger.ONE);
	}

	@Override
	public void bitShiftLeft(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.shiftLeft(1);
	}

	@Override
	public void bitShiftRight(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.shiftRight(1);
	}

	@Override
	public void bitAnd(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.and(b.v);
	}

	@Override
	public void bitOr(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.or(b.v);
	}

	@Override
	public void bitXor(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.xor(b.v);
	}

	@Override
	public void bitNot(UnboundedIntMember a, UnboundedIntMember b) {
		b.v = a.v.not();
	}

	@Override
	public void min(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.min(b.v);
	}

	@Override
	public void max(UnboundedIntMember a, UnboundedIntMember b, UnboundedIntMember c) {
		c.v = a.v.max(b.v);
	}

}
