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
package zorbage.type.data.int128;

import zorbage.type.algebra.BitOperations;
import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Integer;
import zorbage.type.algebra.Random;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UnsignedInt128Group
	implements
		Integer<UnsignedInt128Group, UnsignedInt128Member>,
		Bounded<UnsignedInt128Member>,
		BitOperations<UnsignedInt128Member>,
		Random<UnsignedInt128Member>
{

	// TODO
	// 1) subtract
	// 2) multiply
	// 3) div
	// 4) mod
	// 5) convert byte references and constants to long references and constants
	// 6) speed test versus imglib
	// 7) optimize methods
	
	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());
	private static final UnsignedInt128Member ZERO = new UnsignedInt128Member();
	private static final UnsignedInt128Member ONE = new UnsignedInt128Member((byte)0,(byte)1);

	@Override
	public UnsignedInt128Member construct() {
		return new UnsignedInt128Member();
	}

	@Override
	public UnsignedInt128Member construct(UnsignedInt128Member other) {
		return new UnsignedInt128Member(other);
	}

	@Override
	public UnsignedInt128Member construct(String str) {
		return new UnsignedInt128Member(str);
	}

	@Override
	public boolean isEqual(UnsignedInt128Member a, UnsignedInt128Member b) {
		return a.lo == b.lo && a.hi == b.hi;
	}

	@Override
	public boolean isNotEqual(UnsignedInt128Member a, UnsignedInt128Member b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(UnsignedInt128Member from, UnsignedInt128Member to) {
		to.set(from);
	}

	@Override
	public void zero(UnsignedInt128Member a) {
		a.hi = a.lo = 0;
	}

	// TODO: this shows that unsigned numbers aren't quite ints. They should derive slightly differently
	// in the algebra hierarchy.
	
	@Override
	public void negate(UnsignedInt128Member a, UnsignedInt128Member b) {
		assign(a,b); // ignore
	}

	@Override
	public void add(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		byte aLoTmp = (byte) (a.lo & 127);
		byte bLoTmp = (byte) (b.lo & 127);
		byte loTmp = (byte) (aLoTmp + bLoTmp);
		byte loCarry = 0;
		if ((a.lo & 128) > 0) loCarry++;
		if ((b.lo & 128) > 0) loCarry++;
		if ((loTmp & 128) > 0) loCarry++;
		c.lo = (byte) (loTmp & 127);
		if (loCarry == 1 || loCarry == 3) {
			c.lo |= 128;
			loCarry -= 1;
		}
		// it is FALSE that loCarry only == 0 or 2 at this point
		byte aHiTmp = (byte) (a.hi & 127);
		byte bHiTmp = (byte) (b.hi & 127);
		byte hiTmp = (byte) (aHiTmp + bHiTmp);
		if (loCarry > 0) {
			hiTmp += 1;
		}
		byte hiCarry = 0;
		if ((a.hi & 128) > 0) hiCarry++;
		if ((b.hi & 128) > 0) hiCarry++;
		if ((hiTmp & 128) > 0) hiCarry++;
		c.hi = (byte) (hiTmp & 127);
		if (hiCarry == 1 || hiCarry == 3) {
			c.hi |= 128;
			// and ignore the extra bit if hiCarry == 3: overflow happened
		}
	}

	@Override
	public void subtract(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented yet");
	}

	@Override
	public void multiply(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented yet");
	}

	@Override
	public void power(int power, UnsignedInt128Member a, UnsignedInt128Member b) {
		if (power == 0 && isEqual(a, ZERO)) throw new IllegalArgumentException("0^0 is not a number");
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		UnsignedInt128Member tmp = new UnsignedInt128Member(ONE);
		if (power > 0) {
			for (int i = 1; i <= power; i++)
				multiply(tmp, a, tmp);
		}
		assign(tmp, b);
	}

	@Override
	public void unity(UnsignedInt128Member result) {
		result.lo = 1;
		result.hi = 0;
	}

	@Override
	public boolean isLess(UnsignedInt128Member a, UnsignedInt128Member b) {
		return compare(a,b) < 0;
	}

	@Override
	public boolean isLessEqual(UnsignedInt128Member a, UnsignedInt128Member b) {
		return compare(a,b) <= 0;
	}

	@Override
	public boolean isGreater(UnsignedInt128Member a, UnsignedInt128Member b) {
		return compare(a,b) > 0;
	}

	@Override
	public boolean isGreaterEqual(UnsignedInt128Member a, UnsignedInt128Member b) {
		return compare(a,b) >= 0;
	}

	@Override
	public int compare(UnsignedInt128Member a, UnsignedInt128Member b) {
		int abyte, bbyte;
		int ab = a.hi & 128;
		int bb = b.hi & 128;
		if (ab == 0 && bb != 0) {
			return -1;
		}
		else if (ab != 0 && bb == 0) {
			return 1;
		}
		else { // ab == bb
			abyte = a.hi & 127;
			bbyte = b.hi & 127;
			if (abyte < bbyte)
				return -1;
			else if (abyte > bbyte)
				return 1;
			else { // a.hi == b.hi
				ab = a.lo & 128;
				bb = b.lo & 128;
				if (ab == 0 && bb != 0) {
					return -1;
				}
				else if (ab != 0 && bb == 0) {
					return 1;
				}
				else { // ab == bb
					abyte = a.lo & 127;
					bbyte = b.lo & 127;
					if (abyte < bbyte)
						return -1;
					else if (abyte > bbyte)
						return 1;
					else
						return 0;
				}
			}
		}
	}

	@Override
	public int signum(UnsignedInt128Member a) {
		return compare(a,ZERO);
	}

	@Override
	public void min(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		if (compare(a,b) < 0)
			assign(a, c);
		else
			assign(b, c);
	}

	@Override
	public void max(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		if (compare(a,b) > 0)
			assign(a, c);
		else
			assign(b, c);
	}

	@Override
	public void abs(UnsignedInt128Member a, UnsignedInt128Member b) {
		assign(a, b);
	}

	@Override
	public void norm(UnsignedInt128Member a, UnsignedInt128Member b) {
		assign(a, b);
	}

	@Override
	public void div(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member d) {
		if (isEqual(b, ZERO))
			throw new IllegalArgumentException("divide by zero error in UnsignedInt128Group");
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented yet");
	}

	@Override
	public void mod(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member m) {
		if (isEqual(b, ZERO))
			throw new IllegalArgumentException("divide by zero error in UnsignedInt128Group");
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("not implemented yet");
	}

	// TODO: improve performance
	
	@Override
	public void divMod(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member d, UnsignedInt128Member m) {
		if (isEqual(b, ZERO))
			throw new IllegalArgumentException("divide by zero error in UnsignedInt128Group");
		div(a,b,d);
		mod(a,b,m);
	}

	@Override
	public void gcd(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		gcdHelper(a, b, c);
	}

	@Override
	public void lcm(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		UnsignedInt128Member n = new UnsignedInt128Member();
		UnsignedInt128Member d = new UnsignedInt128Member();
		multiply(a,b,n);
		gcdHelper(a, b, d);
		div(n, d, c);
	}

	@Override
	public boolean isEven(UnsignedInt128Member a) {
		return a.lo % 2 == 0;
	}

	@Override
	public boolean isOdd(UnsignedInt128Member a) {
		return a.lo % 2 == 1;
	}

	@Override
	public void pred(UnsignedInt128Member a, UnsignedInt128Member b) {
		subtract(a,ONE,b);
	}

	@Override
	public void succ(UnsignedInt128Member a, UnsignedInt128Member b) {
		add(a,ONE,b);
	}

	@Override
	public void pow(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		int cmp = compare(b,ZERO);
		if (cmp < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		UnsignedInt128Member tmp = new UnsignedInt128Member(ONE);
		if (cmp > 0) {
			UnsignedInt128Member pow = new UnsignedInt128Member(b);
			while (compare(pow, ZERO) > 0) {
				multiply(tmp, a, tmp);
				pred(pow,pow);
			}
		}
		assign(tmp, c);
	}

	@Override
	public void random(UnsignedInt128Member a) {
		a.lo = (byte) (rng.nextInt(256) - 128);
		a.hi = (byte) (rng.nextInt(256) - 128);
	}

	@Override
	public void bitAnd(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		c.lo = (byte) (a.lo & b.lo);
		c.hi = (byte) (a.hi & b.hi);
	}

	@Override
	public void bitOr(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		c.lo = (byte) (a.lo | b.lo);
		c.hi = (byte) (a.hi | b.hi);
	}

	@Override
	public void bitXor(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		c.lo = (byte) (a.lo ^ b.lo);
		c.hi = (byte) (a.hi ^ b.hi);
	}

	@Override
	public void bitNot(UnsignedInt128Member a, UnsignedInt128Member b) {
		b.lo = (byte) ~a.lo;
		b.hi = (byte) ~a.hi;
	}

	// TODO improve performance
	
	@Override
	public void bitShiftLeft(int count, UnsignedInt128Member a, UnsignedInt128Member b) {
		if (count < 0)
			bitShiftRight(Math.abs(count), a, b);
		else if (count > 127)
			assign(ZERO, b);  // TODO: test what 1<<32 does in java code and match behavior
		else {
			UnsignedInt128Member tmp = new UnsignedInt128Member(a);
			for (int i = 0; i < count; i++) {
				shiftLeftOneBit(tmp);
			}
			assign(tmp, b);
		}
	}

	// TODO improve performance
	
	@Override
	public void bitShiftRight(int count, UnsignedInt128Member a, UnsignedInt128Member b) {
		if (count < 0)
			bitShiftLeft(Math.abs(count), a, b);
		else if (count > 127)
			assign(ZERO, b);
		else {
			UnsignedInt128Member tmp = new UnsignedInt128Member(a);
			for (int i = 0; i < count; i++) {
				shiftRightOneBit(tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void maxBound(UnsignedInt128Member a) {
		a.lo = -1;
		a.hi = -1;
	}

	@Override
	public void minBound(UnsignedInt128Member a) {
		a.lo = 0;
		a.hi = 0;
	}

	private void shiftLeftOneBit(UnsignedInt128Member val) {
		boolean transitionBit = (val.lo & 128) > 0;
		val.lo = (byte) ((val.lo & 0xff) << 1);
		val.hi = (byte) ((val.hi & 0xff) << 1);
		if (transitionBit)
			val.hi |= 1;
	}

	private void shiftRightOneBit(UnsignedInt128Member val) {
		boolean transitionBit = (val.hi & 1) > 0;
		val.lo = (byte) ((val.lo & 0xff) >>> 1);
		val.hi = (byte) ((val.hi & 0xff) >>> 1);
		if (transitionBit)
			val.lo |= 128;
	}

	private void gcdHelper(UnsignedInt128Member a, UnsignedInt128Member b, UnsignedInt128Member c) {
		UnsignedInt128Member aTmp = new UnsignedInt128Member(a);
		UnsignedInt128Member bTmp = new UnsignedInt128Member(b);
		UnsignedInt128Member t = new UnsignedInt128Member();
		while (compare(bTmp, ZERO) != 0) {
			assign(bTmp, t);
			mod(aTmp,bTmp,bTmp);
			assign(t, aTmp);
		}
		assign(a, c);
	}
}
