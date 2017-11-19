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
package zorbage.type.data.int16;

import zorbage.type.algebra.BitOperations;
import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Integer;
import zorbage.type.algebra.Random;
import zorbage.type.data.int16.UnsignedInt16Member;
import zorbage.type.data.util.GcdLcmHelper;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt16Group
  implements
    Integer<UnsignedInt16Group, UnsignedInt16Member>,
    Bounded<UnsignedInt16Member>,
    BitOperations<UnsignedInt16Member>,
    Random<UnsignedInt16Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final UnsignedInt16Member ZERO = new UnsignedInt16Member();
	
	public UnsignedInt16Group() {
	}
	
	@Override
	public boolean isEqual(UnsignedInt16Member a, UnsignedInt16Member b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(UnsignedInt16Member a, UnsignedInt16Member b) {
		return a.v != b.v;
	}

	@Override
	public UnsignedInt16Member construct() {
		return new UnsignedInt16Member();
	}

	@Override
	public UnsignedInt16Member construct(UnsignedInt16Member other) {
		return new UnsignedInt16Member(other);
	}

	@Override
	public UnsignedInt16Member construct(String s) {
		return new UnsignedInt16Member(s);
	}

	@Override
	public void assign(UnsignedInt16Member from, UnsignedInt16Member to) {
		to.set(from);
	}

	@Override
	public void abs(UnsignedInt16Member a, UnsignedInt16Member b) {
		assign(a, b);
	}

	@Override
	public void multiply(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, UnsignedInt16Member a, UnsignedInt16Member b) {
		if (power == 0 && a.v == 0) throw new IllegalArgumentException("0^0 is not a number");
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
	public void zero(UnsignedInt16Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(UnsignedInt16Member a, UnsignedInt16Member b) {
		assign(a,b); // ignore
	}

	@Override
	public void add(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v + b.v );
	}

	@Override
	public void subtract(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v - b.v );
	}

	@Override
	public void unity(UnsignedInt16Member a) {
		a.setV( 1 );
	}

	@Override
	public boolean isLess(UnsignedInt16Member a, UnsignedInt16Member b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnsignedInt16Member a, UnsignedInt16Member b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnsignedInt16Member a, UnsignedInt16Member b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnsignedInt16Member a, UnsignedInt16Member b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnsignedInt16Member a, UnsignedInt16Member b) {
		int av = a.v();
		int bv = b.v();
		if (av < bv) return -1;
		if (av > bv) return 1;
		return 0;
	}

	@Override
	public int signum(UnsignedInt16Member a) {
		return compare(a,ZERO);
	}

	@Override
	public void div(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member d) {
		d.setV( a.v() / b.v() );
	}

	@Override
	public void mod(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member m) {
		m.setV( a.v() % b.v() );
	}

	@Override
	public void divMod(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member d, UnsignedInt16Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		GcdLcmHelper.findGcd(this, a, b, c);
	}

	@Override
	public void lcm(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		GcdLcmHelper.findLcm(this, a, b, c);
	}

	@Override
	public void norm(UnsignedInt16Member a, UnsignedInt16Member b) {
		assign(a, b);
	}

	@Override
	public boolean isEven(UnsignedInt16Member a) {
		return a.v % 2 == 0;
	}

	@Override
	public boolean isOdd(UnsignedInt16Member a) {
		return a.v % 2 == 1;
	}

	// TODO: test
	
	@Override
	public void pred(UnsignedInt16Member a, UnsignedInt16Member b) {
		if (a.v == 0)
			b.setV(0xffff);
		else
			b.setV( a.v() - 1 );
	}

	// TODO: test
	
	@Override
	public void succ(UnsignedInt16Member a, UnsignedInt16Member b) {
		if (a.v == -1)
			b.setV(0);
		else
			b.setV( a.v() + 1 );
	}

	@Override
	public void maxBound(UnsignedInt16Member a) {
		a.setV( 0xffff );
	}

	@Override
	public void minBound(UnsignedInt16Member a) {
		a.setV( 0 );
	}

	@Override
	public void bitAnd(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v & b.v );
	}

	@Override
	public void bitOr(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v | b.v );
	}

	@Override
	public void bitXor(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( a.v ^ b.v );
	}

	@Override
	public void bitNot(UnsignedInt16Member a, UnsignedInt16Member b) {
		b.setV( ~a.v );
	}

	@Override
	public void bitShiftLeft(int count, UnsignedInt16Member a, UnsignedInt16Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else {
			count = count % 16;
			b.setV( a.v() << count );
		}
	}

	@Override
	public void bitShiftRight(int count, UnsignedInt16Member a, UnsignedInt16Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >> count );
	}

	public void bitShiftRightFillZero(int count, UnsignedInt16Member a, UnsignedInt16Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else
			b.setV( a.v() >>> count );
	}

	@Override
	public void min(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( Math.min(a.v(), b.v()) );
	}

	@Override
	public void max(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		c.setV( Math.max(a.v(), b.v()) );
	}

	@Override
	public void random(UnsignedInt16Member a) {
		a.setV( rng.nextInt(0x10000) );
	}

	@Override
	public void pow(UnsignedInt16Member a, UnsignedInt16Member b, UnsignedInt16Member c) {
		power(b.v(), a, c);
	}

}
