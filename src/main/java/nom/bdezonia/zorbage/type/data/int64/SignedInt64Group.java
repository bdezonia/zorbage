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

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.PowerI;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt64Group
  implements
    Integer<SignedInt64Group, SignedInt64Member>,
    Bounded<SignedInt64Member>,
    BitOperations<SignedInt64Member>,
    Random<SignedInt64Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final SignedInt64Member ZERO = new SignedInt64Member();
	private static final SignedInt64Member ONE = new SignedInt64Member(1);
	
	public SignedInt64Group() {
	}
	
	@Override
	public boolean isEqual(SignedInt64Member a, SignedInt64Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(SignedInt64Member a, SignedInt64Member b) {
		return a.v() != b.v();
	}

	@Override
	public SignedInt64Member construct() {
		return new SignedInt64Member();
	}

	@Override
	public SignedInt64Member construct(SignedInt64Member other) {
		return new SignedInt64Member(other);
	}

	@Override
	public SignedInt64Member construct(String s) {
		return new SignedInt64Member(s);
	}

	@Override
	public void assign(SignedInt64Member from, SignedInt64Member to) {
		to.setV( from.v() );
	}

	@Override
	public void abs(SignedInt64Member a, SignedInt64Member b) {
		if (a.v() == Long.MIN_VALUE)
			throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
		b.setV( Math.abs(a.v()) );
	}

	@Override
	public void multiply(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, SignedInt64Member a, SignedInt64Member b) {
		PowerI.compute(this, power, a, b);
	}

	@Override
	public void zero(SignedInt64Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(SignedInt64Member a, SignedInt64Member b) {
		b.setV( -a.v() );
	}

	@Override
	public void add(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() + b.v() );
	}

	@Override
	public void subtract(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() - b.v() );
	}

	@Override
	public void unity(SignedInt64Member a) {
		a.setV( 1 );
	}

	@Override
	public boolean isLess(SignedInt64Member a, SignedInt64Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(SignedInt64Member a, SignedInt64Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(SignedInt64Member a, SignedInt64Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(SignedInt64Member a, SignedInt64Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(SignedInt64Member a, SignedInt64Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return 1;
		return 0;
	}

	@Override
	public int signum(SignedInt64Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return 1;
		return 0;
	}

	@Override
	public void div(SignedInt64Member a, SignedInt64Member b, SignedInt64Member d) {
		if (b.v() == -1 && a.v() == Long.MIN_VALUE)
			throw new IllegalArgumentException("cannot divide minint by -1");
		d.setV( a.v() / b.v() );
	}

	@Override
	public void mod(SignedInt64Member a, SignedInt64Member b, SignedInt64Member m) {
		m.setV( a.v() % b.v() );
	}

	@Override
	public void divMod(SignedInt64Member a, SignedInt64Member b, SignedInt64Member d, SignedInt64Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		Gcd.compute(this, a, b, c);
	}

	@Override
	public void lcm(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		Lcm.compute(this, a, b, c);
	}

	@Override
	public void norm(SignedInt64Member a, SignedInt64Member b) {
		abs(a,b);
	}

	@Override
	public boolean isEven(SignedInt64Member a) {
		return a.v() % 2 == 0;
	}

	@Override
	public boolean isOdd(SignedInt64Member a) {
		return a.v() % 2 == 1;
	}

	@Override
	public void pred(SignedInt64Member a, SignedInt64Member b) {
		b.setV( a.v() - 1 );
	}

	@Override
	public void succ(SignedInt64Member a, SignedInt64Member b) {
		b.setV( a.v() + 1 );
	}

	@Override
	public void maxBound(SignedInt64Member a) {
		a.setV( java.lang.Long.MAX_VALUE );
	}

	@Override
	public void minBound(SignedInt64Member a) {
		a.setV( java.lang.Long.MIN_VALUE );
	}

	@Override
	public void bitAnd(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() & b.v() );
	}

	@Override
	public void bitOr(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() | b.v() );
	}

	@Override
	public void bitXor(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		c.setV( a.v() ^ b.v() );
	}

	@Override
	public void bitNot(SignedInt64Member a, SignedInt64Member b) {
		b.setV( ~a.v() );
	}

	@Override
	public void bitShiftLeft(int count, SignedInt64Member a, SignedInt64Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else {
			count = count % 64;
			b.setV( a.v() << count );
		}
	}

	@Override
	public void bitShiftRight(int count, SignedInt64Member a, SignedInt64Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >> count );
	}

	public void bitShiftRightFillZero(int count, SignedInt64Member a, SignedInt64Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >>> count );
	}

	@Override
	public void min(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void random(SignedInt64Member a) {
		a.setV(rng.nextLong());
	}

	@Override
	public void pow(SignedInt64Member a, SignedInt64Member b, SignedInt64Member c) {
		if (b.v() < 0)
			throw new IllegalArgumentException("Cannot get negative powers from int64s");
		if (signum(a) == 0 && signum(b) == 0)
			throw new IllegalArgumentException("0^0 is not a number");
		SignedInt64Member tmp = new SignedInt64Member(ONE);
		SignedInt64Member pow = new SignedInt64Member(b);
		while (!isEqual(pow, ZERO)) {
			multiply(tmp, a, tmp);
			pred(pow,pow);
		}
		assign(tmp, c);
	}

}
