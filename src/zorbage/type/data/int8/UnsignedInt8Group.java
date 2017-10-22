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
import zorbage.type.data.int8.UnsignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt8Group
  implements
    Integer<UnsignedInt8Group, UnsignedInt8Member>,
    Bounded<UnsignedInt8Member>,
    BitOperations<UnsignedInt8Member>,
    Random<UnsignedInt8Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final UnsignedInt8Member ZERO = new UnsignedInt8Member();
	
	public UnsignedInt8Group() {
	}
	
	@Override
	public boolean isEqual(UnsignedInt8Member a, UnsignedInt8Member b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(UnsignedInt8Member a, UnsignedInt8Member b) {
		return a.v != b.v;
	}

	@Override
	public UnsignedInt8Member construct() {
		return new UnsignedInt8Member();
	}

	@Override
	public UnsignedInt8Member construct(UnsignedInt8Member other) {
		return new UnsignedInt8Member(other);
	}

	@Override
	public UnsignedInt8Member construct(String s) {
		return new UnsignedInt8Member(s);
	}

	@Override
	public void assign(UnsignedInt8Member from, UnsignedInt8Member to) {
		to.set( from );
	}

	@Override
	public void abs(UnsignedInt8Member a, UnsignedInt8Member b) {
		assign(a,b);
	}

	@Override
	public void multiply(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, UnsignedInt8Member a, UnsignedInt8Member b) {
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		int tmp = 1;
		if (power > 0) {
			int av = a.v();
			for (int i = 1; i <= power; i++)
				tmp = tmp * av;
		}
		b.setV(tmp);
	}

	@Override
	public void zero(UnsignedInt8Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(UnsignedInt8Member a, UnsignedInt8Member b) {
		b.setV( a.v ); // TODO ignoring since no negative representations
	}

	@Override
	public void add(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v + b.v );
	}

	@Override
	public void subtract(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v - b.v );
	}

	@Override
	public void unity(UnsignedInt8Member a) {
		a.setV( 1 );
	}

	@Override
	public boolean isLess(UnsignedInt8Member a, UnsignedInt8Member b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnsignedInt8Member a, UnsignedInt8Member b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnsignedInt8Member a, UnsignedInt8Member b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnsignedInt8Member a, UnsignedInt8Member b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnsignedInt8Member a, UnsignedInt8Member b) {
		int av = a.v();
		int bv = b.v();
		if (av < bv) return -1;
		if (av > bv) return 1;
		return 0;
	}

	@Override
	public int signum(UnsignedInt8Member a) {
		return compare(a,ZERO);
	}

	@Override
	public void div(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member d) {
		d.setV( a.v() / b.v() );
	}

	@Override
	public void mod(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member m) {
		m.setV( a.v() % b.v() );
	}

	@Override
	public void divMod(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member d, UnsignedInt8Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	// TODO: is this right?
	
	@Override
	public void gcd(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( gcdHelper(a.v(), b.v()) );
	}

	// TODO: is this right?

	@Override
	public void lcm(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		int av = a.v();
		int bv = b.v();
		int n = Math.abs(av * bv);
		int d = gcdHelper(av, bv);
		c.setV( (byte)(n / d) );
	}

	// TODO: is this right?

	private int gcdHelper(int a, int b) {
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	@Override
	public void norm(UnsignedInt8Member a, UnsignedInt8Member b) {
		assign(a,b);
	}

	@Override
	public boolean isEven(UnsignedInt8Member a) {
		return a.v % 2 == 0;
	}

	@Override
	public boolean isOdd(UnsignedInt8Member a) {
		return a.v % 2 == 1;
	}

	// TODO: test
	
	@Override
	public void pred(UnsignedInt8Member a, UnsignedInt8Member b) {
		if (a.v == 0)
			b.setV(255);
		else
			b.setV( a.v() - 1 );
	}

	// TODO: test
	
	@Override
	public void succ(UnsignedInt8Member a, UnsignedInt8Member b) {
		if (a.v == -1)
			b.setV(0);
		else
			b.setV( a.v() + 1 );
	}

	@Override
	public void maxBound(UnsignedInt8Member a) {
		a.setV( 255 );
	}

	@Override
	public void minBound(UnsignedInt8Member a) {
		a.setV( 0 );
	}

	@Override
	public void bitAnd(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v & b.v );
	}

	@Override
	public void bitOr(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v | b.v );
	}

	@Override
	public void bitXor(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( a.v ^ b.v );
	}

	@Override
	public void bitNot(UnsignedInt8Member a, UnsignedInt8Member b) {
		b.setV( ~a.v );
	}

	@Override
	public void bitShiftLeft(int count, UnsignedInt8Member a, UnsignedInt8Member b) {
		b.setV( a.v() << count );
	}

	@Override
	public void bitShiftRight(int count, UnsignedInt8Member a, UnsignedInt8Member b) {
		b.setV( a.v() >> count );
	}

	public void bitShiftRightFillZero(int count, UnsignedInt8Member a, UnsignedInt8Member b) {
		b.setV( a.v() >>> count );
	}

	@Override
	public void min(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( Math.min(a.v(), b.v()) );
	}

	@Override
	public void max(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		c.setV( Math.max(a.v(), b.v()) );
	}

	@Override
	public void random(UnsignedInt8Member a) {
		a.setV( rng.nextInt(0x100) );
	}

	@Override
	public void pow(UnsignedInt8Member a, UnsignedInt8Member b, UnsignedInt8Member c) {
		power(b.v(), a, c);
	}

}
