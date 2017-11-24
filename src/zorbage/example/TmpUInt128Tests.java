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
package zorbage.example;

import java.math.BigInteger;

import zorbage.groups.G;
import zorbage.type.data.int128.UnsignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TmpUInt128Tests {

	// Note: UInt128 initially designed as 2 bytes rather than 2 longs. This was done to get
	// the logic right before making huge untestable numbers. This means the range is 0 to
	// 65535. I can exhaustively test that range to ensure code works. Once basic operations
	// are working I can change underlying types to longs and constants appropriately and code
	// should be correct.
	
	// These tests are slow so make it possible to turn them on or off
	
	private static final boolean RUN_EXHAUSTIVE_16_BIT_TESTS = false;
	
	public void run() {
		if (RUN_EXHAUSTIVE_16_BIT_TESTS) {
			System.out.println("Running exhaustive 16 bit tests");
			addTests();
			subtractTests();
			multiplyTests();
			divModTests();
			System.out.println("  Done running exhaustive 16 bit tests");
		}
	}
	
	private static void test(boolean b, int testNum) {
		if (!b)
			throw new IllegalArgumentException("test "+testNum+" failed");
	}
	
	private static void addTests() {
		System.out.println("  Add tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.add(a, b, c);
				test(c.v().equals(I.add(J).mod(BASE)),1);
			}			
		}
	}

	private static void subtractTests() {
		System.out.println("  Subtract tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.subtract(a, b, c);
				BigInteger result = I.subtract(J);
				if (result.compareTo(BigInteger.ZERO) < 0)
					result = result.add(BASE);
				test(c.v().equals(result),2);
			}			
		}
	}

	private static void multiplyTests() {
		System.out.println("  Multiply tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.multiply(a, b, c);
				test(c.v().equals(I.multiply(J).mod(BASE)),3);
			}			
		}
	}

	private static void divModTests() {
		System.out.println("  Divide tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member d = G.UINT128.construct();
		UnsignedInt128Member m = G.UINT128.construct();
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 1; j < 65536; j++) { // avoid divide by zero
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.divMod(a, b, d, m);
				BigInteger[] dm = I.divideAndRemainder(J);
				test(d.v().equals(dm[0]),4);
				test(m.v().equals(dm[1]),5);
			}			
		}
	}

}
