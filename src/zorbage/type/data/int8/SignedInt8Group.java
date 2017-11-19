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
package zorbage.type.data.int8;

import zorbage.type.algebra.BitOperations;
import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Integer;
import zorbage.type.algebra.Random;
import zorbage.type.data.int8.SignedInt8Member;
import zorbage.type.data.util.GcdHelper;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SignedInt8Group
  implements
    Integer<SignedInt8Group, SignedInt8Member>,
    Bounded<SignedInt8Member>,
    BitOperations<SignedInt8Member>,
    Random<SignedInt8Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final SignedInt8Member ZERO = new SignedInt8Member();
	
	public SignedInt8Group() {
	}
	
	@Override
	public boolean isEqual(SignedInt8Member a, SignedInt8Member b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(SignedInt8Member a, SignedInt8Member b) {
		return a.v() != b.v();
	}

	@Override
	public SignedInt8Member construct() {
		return new SignedInt8Member();
	}

	@Override
	public SignedInt8Member construct(SignedInt8Member other) {
		return new SignedInt8Member(other);
	}

	@Override
	public SignedInt8Member construct(String s) {
		return new SignedInt8Member(s);
	}

	@Override
	public void assign(SignedInt8Member from, SignedInt8Member to) {
		to.setV( from.v() );
	}

	@Override
	public void abs(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte) Math.abs(a.v()) );
	}

	@Override
	public void multiply(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte) (a.v() * b.v()) );
	}

	@Override
	public void power(int power, SignedInt8Member a, SignedInt8Member b) {
		if (power == 0 && a.v() == 0) throw new IllegalArgumentException("0^0 is not a number");
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		int tmp = 1;
		if (power > 0) {
			for (int i = 1; i <= power; i++)
				tmp = tmp * a.v();
		}
		b.setV((byte)tmp);  // TODO: is this correct working in ints vs. bytes?
	}

	@Override
	public void zero(SignedInt8Member a) {
		a.setV( (byte) 0 );
	}

	@Override
	public void negate(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte) -a.v() );
	}

	@Override
	public void add(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte)(a.v() + b.v()) );
	}

	@Override
	public void subtract(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte)(a.v() - b.v()) );
	}

	@Override
	public void unity(SignedInt8Member a) {
		a.setV( (byte) 1 );
	}

	@Override
	public boolean isLess(SignedInt8Member a, SignedInt8Member b) {
		return a.v() < b.v();
	}

	@Override
	public boolean isLessEqual(SignedInt8Member a, SignedInt8Member b) {
		return a.v() <= b.v();
	}

	@Override
	public boolean isGreater(SignedInt8Member a, SignedInt8Member b) {
		return a.v() > b.v();
	}

	@Override
	public boolean isGreaterEqual(SignedInt8Member a, SignedInt8Member b) {
		return a.v() >= b.v();
	}

	@Override
	public int compare(SignedInt8Member a, SignedInt8Member b) {
		if (a.v() < b.v()) return -1;
		if (a.v() > b.v()) return 1;
		return 0;
	}

	@Override
	public int signum(SignedInt8Member a) {
		if (a.v() < 0) return -1;
		if (a.v() > 0) return 1;
		return 0;
	}

	@Override
	public void div(SignedInt8Member a, SignedInt8Member b, SignedInt8Member d) {
		d.setV( (byte)(a.v() / b.v()) );
	}

	@Override
	public void mod(SignedInt8Member a, SignedInt8Member b, SignedInt8Member m) {
		m.setV( (byte)(a.v() % b.v()) );
	}

	@Override
	public void divMod(SignedInt8Member a, SignedInt8Member b, SignedInt8Member d, SignedInt8Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		GcdHelper.findGcd(this, ZERO, a, b, c);
	}

	@Override
	public void lcm(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		SignedInt8Member n = new SignedInt8Member((byte) Math.abs(a.v() * b.v()));
		SignedInt8Member d = new SignedInt8Member();
		GcdHelper.findGcd(this, ZERO, a, b, d);
		div(n,d,c);
	}

	@Override
	public void norm(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)Math.abs(a.v()) );
	}

	@Override
	public boolean isEven(SignedInt8Member a) {
		return a.v() % 2 == 0;
	}

	@Override
	public boolean isOdd(SignedInt8Member a) {
		return a.v() % 2 == 1;
	}

	@Override
	public void pred(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)(a.v() - 1) );
	}

	@Override
	public void succ(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)(a.v() + 1) );
	}

	@Override
	public void maxBound(SignedInt8Member a) {
		a.setV( java.lang.Byte.MAX_VALUE );
	}

	@Override
	public void minBound(SignedInt8Member a) {
		a.setV( java.lang.Byte.MIN_VALUE );
	}

	@Override
	public void bitAnd(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte)(a.v() & b.v()) );
	}

	@Override
	public void bitOr(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte)(a.v() | b.v()) );
	}

	@Override
	public void bitXor(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte)(a.v() ^ b.v()) );
	}

	@Override
	public void bitNot(SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte) ~a.v() );
	}

	@Override
	public void bitShiftLeft(int count, SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)(a.v() << count) );
	}

	@Override
	public void bitShiftRight(int count, SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)(a.v() >> count) );
	}

	public void bitShiftRightFillZero(int count, SignedInt8Member a, SignedInt8Member b) {
		b.setV( (byte)(a.v() >>> count) );
	}

	@Override
	public void min(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte) Math.min(a.v(), b.v()) );
	}

	@Override
	public void max(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		c.setV( (byte) Math.max(a.v(), b.v()) );
	}

	@Override
	public void random(SignedInt8Member a) {
		a.setV( (byte) (java.lang.Byte.MIN_VALUE + rng.nextInt(0x100)));
	}

	@Override
	public void pow(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {
		power(b.v(), a, c);
	}

}
