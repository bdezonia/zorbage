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
package nom.bdezonia.zorbage.type.data.int16;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.PowerI;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Integer;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt16Group
  implements
    Integer<SignedInt16Group, SignedInt16Member>,
    Bounded<SignedInt16Member>,
    BitOperations<SignedInt16Member>,
    Random<SignedInt16Member>
{

	public SignedInt16Group() {
	}
	
	@Override
	public boolean isEqual(SignedInt16Member a, SignedInt16Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(SignedInt16Member a, SignedInt16Member b) {
		return a.v() != b.v();
	}

	@Override
	public SignedInt16Member construct() {
		return new SignedInt16Member();
	}

	@Override
	public SignedInt16Member construct(SignedInt16Member other) {
		return new SignedInt16Member(other);
	}

	@Override
	public SignedInt16Member construct(String s) {
		return new SignedInt16Member(s);
	}

	@Override
	public void assign(SignedInt16Member from, SignedInt16Member to) {
		to.setV( from.v() );
	}

	@Override
	public void abs(SignedInt16Member a, SignedInt16Member b) {
		if (a.v() == Short.MIN_VALUE)
			throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
		b.setV( (short) Math.abs(a.v()) );
	}

	@Override
	public void multiply(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short) (a.v() * b.v()) );
	}

	@Override
	public void power(int power, SignedInt16Member a, SignedInt16Member b) {
		PowerI.compute(this, power, a, b);
	}

	@Override
	public void zero(SignedInt16Member a) {
		a.setV( (short) 0 );
	}

	@Override
	public void negate(SignedInt16Member a, SignedInt16Member b) {
		b.setV( (short) -a.v() );
	}

	@Override
	public void add(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short)(a.v() + b.v()) );
	}

	@Override
	public void subtract(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short)(a.v() - b.v()) );
	}

	@Override
	public void unity(SignedInt16Member a) {
		a.setV( (short) 1 );
	}

	@Override
	public boolean isLess(SignedInt16Member a, SignedInt16Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(SignedInt16Member a, SignedInt16Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(SignedInt16Member a, SignedInt16Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(SignedInt16Member a, SignedInt16Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(SignedInt16Member a, SignedInt16Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return 1;
		return 0;
	}

	@Override
	public int signum(SignedInt16Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return 1;
		return 0;
	}

	@Override
	public void div(SignedInt16Member a, SignedInt16Member b, SignedInt16Member d) {
		if (b.v() == -1 && a.v() == Short.MIN_VALUE)
			throw new IllegalArgumentException("cannot divide minint by -1");
		d.setV( (short)(a.v() / b.v()) );
	}

	@Override
	public void mod(SignedInt16Member a, SignedInt16Member b, SignedInt16Member m) {
		m.setV( (short)(a.v() % b.v()) );
	}

	@Override
	public void divMod(SignedInt16Member a, SignedInt16Member b, SignedInt16Member d, SignedInt16Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		Gcd.compute(this, a, b, c);
	}

	@Override
	public void lcm(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		Lcm.compute(this, a, b, c);
	}

	@Override
	public void norm(SignedInt16Member a, SignedInt16Member b) {
		abs(a,b);
	}

	@Override
	public boolean isEven(SignedInt16Member a) {
		return a.v() % 2 == 0;
	}

	@Override
	public boolean isOdd(SignedInt16Member a) {
		return a.v() % 2 == 1;
	}

	@Override
	public void pred(SignedInt16Member a, SignedInt16Member b) {
		b.setV( (short)(a.v() - 1) );
	}

	@Override
	public void succ(SignedInt16Member a, SignedInt16Member b) {
		b.setV( (short)(a.v() + 1) );
	}

	@Override
	public void maxBound(SignedInt16Member a) {
		a.setV( java.lang.Short.MAX_VALUE );
	}

	@Override
	public void minBound(SignedInt16Member a) {
		a.setV( java.lang.Short.MIN_VALUE );
	}

	@Override
	public void bitAnd(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short)(a.v() & b.v()) );
	}

	@Override
	public void bitOr(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short)(a.v() | b.v()) );
	}

	@Override
	public void bitXor(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		c.setV( (short)(a.v() ^ b.v()) );
	}

	@Override
	public void bitNot(SignedInt16Member a, SignedInt16Member b) {
		b.setV( (short) ~a.v() );
	}

	@Override
	public void bitShiftLeft(int count, SignedInt16Member a, SignedInt16Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else {
			count = count % 16;
			b.setV( (short)(a.v() << count) );
		}
	}

	@Override
	public void bitShiftRight(int count, SignedInt16Member a, SignedInt16Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( (short)(a.v() >> count) );
	}

	public void bitShiftRightFillZero(int count, SignedInt16Member a, SignedInt16Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( (short)(a.v() >>> count) );
	}

	@Override
	public void min(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void random(SignedInt16Member a) {
		a.setV( (short) (java.lang.Short.MIN_VALUE + ThreadLocalRandom.current().nextInt(0x10000)));
	}

	@Override
	public void pow(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c) {
		power(b.v(), a, c);
	}

}
