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
package nom.bdezonia.zorbage.type.data.int64;

import java.math.BigInteger;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt64Group
  implements
    Integer<UnsignedInt64Group, UnsignedInt64Member>,
    Bounded<UnsignedInt64Member>,
    BitOperations<UnsignedInt64Member>,
    Random<UnsignedInt64Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final UnsignedInt64Member ZERO = new UnsignedInt64Member();
	private static final UnsignedInt64Member ONE = new UnsignedInt64Member(BigInteger.ONE);
	
	public UnsignedInt64Group() {
	}
	
	@Override
	public boolean isEqual(UnsignedInt64Member a, UnsignedInt64Member b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(UnsignedInt64Member a, UnsignedInt64Member b) {
		return a.v != b.v;
	}

	@Override
	public UnsignedInt64Member construct() {
		return new UnsignedInt64Member();
	}

	@Override
	public UnsignedInt64Member construct(UnsignedInt64Member other) {
		return new UnsignedInt64Member(other);
	}

	@Override
	public UnsignedInt64Member construct(String s) {
		return new UnsignedInt64Member(s);
	}

	@Override
	public void assign(UnsignedInt64Member from, UnsignedInt64Member to) {
		to.set(from);
	}

	@Override
	public void abs(UnsignedInt64Member a, UnsignedInt64Member b) {
		assign(a, b);
	}

	@Override
	public void multiply(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v * b.v;
	}

	@Override
	public void power(int power, UnsignedInt64Member a, UnsignedInt64Member b) {
		if (power == 0 && a.v == 0) throw new IllegalArgumentException("0^0 is not a number");
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		long val = 1;
		for (int i = 0; i < power; i++) {
			val *= a.v;
		}
		b.v = val;
	}

	@Override
	public void zero(UnsignedInt64Member a) {
		a.v = 0;
	}

	@Override
	public void negate(UnsignedInt64Member a, UnsignedInt64Member b) {
		assign(a,b); // ignore
	}

	@Override
	public void add(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v + b.v;
	}

	@Override
	public void subtract(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v - b.v;
	}

	@Override
	public void unity(UnsignedInt64Member a) {
		a.v = 1;
	}

	@Override
	public boolean isLess(UnsignedInt64Member a, UnsignedInt64Member b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnsignedInt64Member a, UnsignedInt64Member b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnsignedInt64Member a, UnsignedInt64Member b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnsignedInt64Member a, UnsignedInt64Member b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnsignedInt64Member a, UnsignedInt64Member b) {
		long aHi = a.v & 0x8000000000000000L;
		long bHi = b.v & 0x8000000000000000L;
		if (aHi == 0 && bHi != 0) return -1;
		if (aHi != 0 && bHi == 0) return 1;
		long aLo = a.v & 0x7fffffffffffffffL;
		long bLo = b.v & 0x7fffffffffffffffL;
		if (aLo < bLo) return -1;
		if (aLo > bLo) return 1;
		return 0;
	}

	@Override
	public int signum(UnsignedInt64Member a) {
		if (a.v == 0) return 0;
		return 1;
	}

	@Override
	public void div(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member d) {
		d.v = a.v / b.v;
	}

	@Override
	public void mod(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member m) {
		m.v = a.v % b.v;
	}

	@Override
	public void divMod(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member d, UnsignedInt64Member m) {
		d.v = a.v / b.v;
		m.v = a.v % b.v;
	}

	@Override
	public void gcd(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		Gcd.compute(this, a, b, c);
	}

	@Override
	public void lcm(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		Lcm.compute(this, a, b, c);
	}

	@Override
	public void norm(UnsignedInt64Member a, UnsignedInt64Member b) {
		assign(a,b);
	}

	@Override
	public boolean isEven(UnsignedInt64Member a) {
		return a.v % 2 == 0;
	}

	@Override
	public boolean isOdd(UnsignedInt64Member a) {
		return a.v % 2 == 1;
	}

	@Override
	public void pred(UnsignedInt64Member a, UnsignedInt64Member b) {
		if (a.v == 0)
			b.v = -1;
		else
			b.v = a.v - 1;
	}

	@Override
	public void succ(UnsignedInt64Member a, UnsignedInt64Member b) {
		if (a.v == -1)
			b.v = 0;
		else
			b.v = a.v + 1;
	}

	@Override
	public void maxBound(UnsignedInt64Member a) {
		a.v = -1;
	}

	@Override
	public void minBound(UnsignedInt64Member a) {
		a.v = 0;
	}

	@Override
	public void bitAnd(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v & b.v;
	}

	@Override
	public void bitOr(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v | b.v;
	}

	@Override
	public void bitXor(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		c.v = a.v ^ b.v;
	}

	@Override
	public void bitNot(UnsignedInt64Member a, UnsignedInt64Member b) {
		b.v = ~a.v;
	}

	@Override
	public void bitShiftLeft(int count, UnsignedInt64Member a, UnsignedInt64Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else {
			count = count % 64;
			b.v = a.v << count;
		}
	}

	@Override
	public void bitShiftRight(int count, UnsignedInt64Member a, UnsignedInt64Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.v = a.v >> count;
	}

	public void bitShiftRightFillZero(int count, UnsignedInt64Member a, UnsignedInt64Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.v = a.v >>> count;
	}

	@Override
	public void min(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		if (compare(a,b) < 0)
			c.set(a);
		else
			c.set(b);
	}

	@Override
	public void max(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		if (compare(a,b) > 0)
			c.set(a);
		else
			c.set(b);
	}

	@Override
	public void random(UnsignedInt64Member a) {
		a.v = rng.nextLong();
	}

	@Override
	public void pow(UnsignedInt64Member a, UnsignedInt64Member b, UnsignedInt64Member c) {
		if (signum(a) == 0 && signum(b) == 0)
			throw new IllegalArgumentException("0^0 is not a number");
		UnsignedInt64Member tmp = new UnsignedInt64Member(ONE);
		UnsignedInt64Member pow = new UnsignedInt64Member(b);
		while (!isEqual(pow, ZERO)) {
			multiply(tmp, a, tmp);
			pred(pow,pow);
		}
		assign(tmp, c);
	}

}
