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
package zorbage.type.data.int32;

import zorbage.type.algebra.BitOperations;
import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Integer;
import zorbage.type.algebra.Random;
import zorbage.type.data.int32.UnsignedInt32Member;
import zorbage.type.data.util.GcdHelper;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt32Group
  implements
    Integer<UnsignedInt32Group, UnsignedInt32Member>,
    Bounded<UnsignedInt32Member>,
    BitOperations<UnsignedInt32Member>,
    Random<UnsignedInt32Member>
{

	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final UnsignedInt32Member ZERO = new UnsignedInt32Member();
	
	public UnsignedInt32Group() {
	}
	
	@Override
	public boolean isEqual(UnsignedInt32Member a, UnsignedInt32Member b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(UnsignedInt32Member a, UnsignedInt32Member b) {
		return a.v != b.v;
	}

	@Override
	public UnsignedInt32Member construct() {
		return new UnsignedInt32Member();
	}

	@Override
	public UnsignedInt32Member construct(UnsignedInt32Member other) {
		return new UnsignedInt32Member(other);
	}

	@Override
	public UnsignedInt32Member construct(String s) {
		return new UnsignedInt32Member(s);
	}

	@Override
	public void assign(UnsignedInt32Member from, UnsignedInt32Member to) {
		to.set( from );
	}

	@Override
	public void abs(UnsignedInt32Member a, UnsignedInt32Member b) {
		assign(a,b);
	}

	@Override
	public void multiply(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v() * b.v() );
	}

	@Override
	public void power(int power, UnsignedInt32Member a, UnsignedInt32Member b) {
		if (power == 0 && a.v == 0) throw new IllegalArgumentException("0^0 is not a number");
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		long tmp = 1;
		if (power > 0) {
			long av = a.v();
			for (int i = 1; i <= power; i++)
				tmp = tmp * av;
		}
		b.setV(tmp);
	}

	@Override
	public void zero(UnsignedInt32Member a) {
		a.setV( 0 );
	}

	@Override
	public void negate(UnsignedInt32Member a, UnsignedInt32Member b) {
		assign(a,b); // ignore
	}

	@Override
	public void add(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v + b.v );
	}

	@Override
	public void subtract(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v - b.v );
	}

	@Override
	public void unity(UnsignedInt32Member a) {
		a.setV( 1 );
	}

	@Override
	public boolean isLess(UnsignedInt32Member a, UnsignedInt32Member b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnsignedInt32Member a, UnsignedInt32Member b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnsignedInt32Member a, UnsignedInt32Member b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnsignedInt32Member a, UnsignedInt32Member b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnsignedInt32Member a, UnsignedInt32Member b) {
		long av = a.v();
		long bv = b.v();
		if (av < bv) return -1;
		if (av > bv) return 1;
		return 0;
	}

	@Override
	public int signum(UnsignedInt32Member a) {
		return compare(a,ZERO);
	}

	@Override
	public void div(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member d) {
		d.setV( a.v() / b.v() );
	}

	@Override
	public void mod(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member m) {
		m.setV( a.v() % b.v() );
	}

	@Override
	public void divMod(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member d, UnsignedInt32Member m) {
		div(a,b,d);
		mod(a,b,m);
	}

	// TODO: is this right?
	
	@Override
	public void gcd(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		GcdHelper.findGcd(this, ZERO, a, b, c);
	}

	// TODO: is this right?

	@Override
	public void lcm(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		UnsignedInt32Member n = new UnsignedInt32Member(Math.abs(a.v() * b.v()));
		UnsignedInt32Member d = new UnsignedInt32Member();
		GcdHelper.findGcd(this, ZERO, a, b, d);
		div(n,d,c);
	}

	@Override
	public void norm(UnsignedInt32Member a, UnsignedInt32Member b) {
		assign(a,b);
	}

	@Override
	public boolean isEven(UnsignedInt32Member a) {
		return a.v % 2 == 0;
	}

	@Override
	public boolean isOdd(UnsignedInt32Member a) {
		return a.v % 2 == 1;
	}

	// TODO: test
	
	@Override
	public void pred(UnsignedInt32Member a, UnsignedInt32Member b) {
		if (a.v == 0)
			b.setV(0xffffffff);
		else
			b.setV( a.v() - 1 );
	}

	// TODO: test
	
	@Override
	public void succ(UnsignedInt32Member a, UnsignedInt32Member b) {
		if (a.v == -1)
			b.setV(0);
		else
			b.setV( a.v() + 1 );
	}

	@Override
	public void maxBound(UnsignedInt32Member a) {
		a.setV( 0xffffffff );
	}

	@Override
	public void minBound(UnsignedInt32Member a) {
		a.setV( 0 );
	}

	@Override
	public void bitAnd(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v & b.v );
	}

	@Override
	public void bitOr(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v | b.v );
	}

	@Override
	public void bitXor(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( a.v ^ b.v );
	}

	@Override
	public void bitNot(UnsignedInt32Member a, UnsignedInt32Member b) {
		b.setV( ~a.v );
	}

	@Override
	public void bitShiftLeft(int count, UnsignedInt32Member a, UnsignedInt32Member b) {
		b.setV( a.v() << count );
	}

	@Override
	public void bitShiftRight(int count, UnsignedInt32Member a, UnsignedInt32Member b) {
		b.setV( a.v() >> count );
	}

	public void bitShiftRightFillZero(int count, UnsignedInt32Member a, UnsignedInt32Member b) {
		b.setV( a.v() >>> count );
	}

	@Override
	public void min(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( Math.min(a.v(), b.v()) );
	}

	@Override
	public void max(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		c.setV( Math.max(a.v(), b.v()) );
	}

	@Override
	public void random(UnsignedInt32Member a) {
		a.setV( rng.nextInt() );
	}

	@Override
	public void pow(UnsignedInt32Member a, UnsignedInt32Member b, UnsignedInt32Member c) {
		power((int)b.v(), a, c);
	}

}
