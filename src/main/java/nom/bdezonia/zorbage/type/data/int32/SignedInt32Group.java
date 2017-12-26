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
package nom.bdezonia.zorbage.type.data.int32;

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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt32Group
  implements
    Integer<SignedInt32Group, SignedInt32Member>,
    Bounded<SignedInt32Member>,
    BitOperations<SignedInt32Member>,
    Random<SignedInt32Member>
{

	public SignedInt32Group() {
	}
	
	@Override
	public boolean isEqual(SignedInt32Member a, SignedInt32Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(SignedInt32Member a, SignedInt32Member b) {
		return a.v() != b.v();
	}

	@Override
	public SignedInt32Member construct() {
		return new SignedInt32Member();
	}

	@Override
	public SignedInt32Member construct(SignedInt32Member other) {
		return new SignedInt32Member(other);
	}

	@Override
	public SignedInt32Member construct(String s) {
		return new SignedInt32Member(s);
	}

	@Override
	public void assign(SignedInt32Member from, SignedInt32Member to) {
		to.setV( from.v() );
	}

	@Override
	public void abs(SignedInt32Member a, SignedInt32Member b) {
		if (a.v() == java.lang.Integer.MIN_VALUE)
			throw new IllegalArgumentException("abs() cannot convert negative minint to positive value");
		b.setV( Math.abs(a.v()) );
	}

	@Override
	public void multiply(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, SignedInt32Member a, SignedInt32Member b) {
		PowerI.compute(this, power, a, b);
	}

	@Override
	public void zero(SignedInt32Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(SignedInt32Member a, SignedInt32Member b) {
		b.setV( -a.v() );
	}

	@Override
	public void add(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() + b.v() );
	}

	@Override
	public void subtract(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() - b.v() );
	}

	@Override
	public void unity(SignedInt32Member a) {
		a.setV( 1 );
	}

	@Override
	public boolean isLess(SignedInt32Member a, SignedInt32Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(SignedInt32Member a, SignedInt32Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(SignedInt32Member a, SignedInt32Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(SignedInt32Member a, SignedInt32Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(SignedInt32Member a, SignedInt32Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return 1;
		return 0;
	}

	@Override
	public int signum(SignedInt32Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return 1;
		return 0;
	}

	@Override
	public void div(SignedInt32Member a, SignedInt32Member b, SignedInt32Member d) {
		if (b.v() == -1 && a.v() == java.lang.Integer.MIN_VALUE)
			throw new IllegalArgumentException("cannot divide minint by -1");
		d.setV( a.v() / b.v() );
	}

	@Override
	public void mod(SignedInt32Member a, SignedInt32Member b, SignedInt32Member m) {
		m.setV( a.v() % b.v() );
	}

	@Override
	public void divMod(SignedInt32Member a, SignedInt32Member b, SignedInt32Member d, SignedInt32Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		Gcd.compute(this, a, b, c);
	}

	@Override
	public void lcm(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		Lcm.compute(this, a, b, c);
	}

	@Override
	public void norm(SignedInt32Member a, SignedInt32Member b) {
		abs(a,b);
	}

	@Override
	public boolean isEven(SignedInt32Member a) {
		return a.v() % 2 == 0;
	}

	@Override
	public boolean isOdd(SignedInt32Member a) {
		return a.v() % 2 == 1;
	}

	@Override
	public void pred(SignedInt32Member a, SignedInt32Member b) {
		b.setV( a.v() - 1 );
	}

	@Override
	public void succ(SignedInt32Member a, SignedInt32Member b) {
		b.setV( a.v() + 1 );
	}

	@Override
	public void maxBound(SignedInt32Member a) {
		a.setV( java.lang.Integer.MAX_VALUE );
	}

	@Override
	public void minBound(SignedInt32Member a) {
		a.setV( java.lang.Integer.MIN_VALUE );
	}

	@Override
	public void bitAnd(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() & b.v() );
	}

	@Override
	public void bitOr(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() | b.v() );
	}

	@Override
	public void bitXor(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		c.setV( a.v() ^ b.v() );
	}

	@Override
	public void bitNot(SignedInt32Member a, SignedInt32Member b) {
		b.setV( ~a.v() );
	}

	@Override
	public void bitShiftLeft(int count, SignedInt32Member a, SignedInt32Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else {
			count = count % 32;
			b.setV( a.v() << count );
		}
	}

	@Override
	public void bitShiftRight(int count, SignedInt32Member a, SignedInt32Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >> count );
	}

	public void bitShiftRightFillZero(int count, SignedInt32Member a, SignedInt32Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >>> count );
	}

	@Override
	public void min(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void random(SignedInt32Member a) {
		a.setV(ThreadLocalRandom.current().nextInt());
	}

	@Override
	public void pow(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
		power(b.v(), a, c);
	}

}
