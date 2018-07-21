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
package nom.bdezonia.zorbage.type.data.int128;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.int128.UnsignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt128Group {

	// Note: UInt128 initially designed as 2 bytes rather than 2 longs. This was done to get
	// the logic right before making huge untestable numbers. This means the range is 0 to
	// 65535. I can exhaustively test that range to ensure code works. Once basic operations
	// are working I can change underlying types to longs and constants appropriately and code
	// should be correct.
	
	// These tests are slow so make it possible to turn them on or off
	private static final boolean RUN_EXHAUSTIVE_16_BIT_TESTS = false;

	// This test will exercise all ops for a subset of all possible numbers. The integer
	// sequence is different every time. This will help cover all of the range of the numbers
	// in testing eventually. When it fails it shows which number combo is problematic.
	
	@Test
	public void testAllOpsForSomeNumbers() {
		Random rng = new Random();
		int loop_max = 1000;
		BigInteger max = BigInteger.ONE.add(BigInteger.ONE).pow(128);
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		UnsignedInt128Member d = G.UINT128.construct();
		UnsignedInt128Member m = G.UINT128.construct();
		UnsignedInt128Member zero = G.UINT128.construct();
		String msg;
		BigInteger bigTmp;
		StringBuilder sb;
		for (int i = 0; i < loop_max; i++) {
			BigInteger abig = new BigInteger(128, rng);
			a.setV(abig);
			for (int j = 0; j < loop_max; j++) {
				BigInteger bbig = new BigInteger(128, rng);
				b.setV(bbig);
				sb = new StringBuilder();
				sb.append("Expecting compare of ");
				sb.append(abig);
				sb.append(" and ");
				sb.append(bbig);
				sb.append(" to match between BigInts and UInt128s");
				msg = sb.toString();
				assertEquals(msg, abig.compareTo(bbig), (int)G.UINT128.compare().call(a, b));
				G.UINT128.add().call(a, b, c);
				bigTmp = c.v();
				sb = new StringBuilder();
				sb.append("Expecting add of ");
				sb.append(abig);
				sb.append(" and ");
				sb.append(bbig);
				sb.append(" to equal ");
				sb.append(bigTmp);
				msg = sb.toString();
				assertEquals(msg, abig.add(bbig).mod(max), bigTmp);
				G.UINT128.subtract().call(a, b, c);
				bigTmp = c.v();
				sb = new StringBuilder();
				sb.append("Expecting sub of ");
				sb.append(abig);
				sb.append(" and ");
				sb.append(bbig);
				sb.append(" to equal ");
				sb.append(bigTmp);
				msg = sb.toString();
				BigInteger tmp = abig.subtract(bbig);
				if (tmp.compareTo(BigInteger.ZERO) < 0)
					tmp = tmp.add(max);
				assertEquals(msg, tmp, bigTmp);
				G.UINT128.multiply().call(a, b, c);
				bigTmp = c.v();
				sb = new StringBuilder();
				sb.append("Expecting mul of ");
				sb.append(abig);
				sb.append(" and ");
				sb.append(bbig);
				sb.append(" to equal ");
				sb.append(bigTmp);
				msg = sb.toString();
				assertEquals(msg, abig.multiply(bbig).mod(max), bigTmp);
				if (G.UINT128.isNotEqual().call(b, zero)) {
					G.UINT128.divMod().call(a, b, d, m);
					BigInteger[] dm = abig.divideAndRemainder(bbig);
					bigTmp = d.v();
					sb = new StringBuilder();
					sb.append("Expecting div of ");
					sb.append(abig);
					sb.append(" and ");
					sb.append(bbig);
					sb.append(" to equal ");
					sb.append(bigTmp);
					msg = sb.toString();
					assertEquals(msg, dm[0], bigTmp);
					bigTmp = m.v();
					sb = new StringBuilder();
					sb.append("Expecting mod of ");
					sb.append(abig);
					sb.append(" and ");
					sb.append(bbig);
					sb.append(" to equal ");
					sb.append(bigTmp);
					msg = sb.toString();
					assertEquals(msg, dm[1], bigTmp);
				}
			}
		}
	}

	// This test will exercise v()/setV() for a subset of all possible numbers. The integer
	// sequence is different every time. This will help cover all of the range of the numbers
	// in testing eventually. When it fails it shows which number is problematic.
	
	@Test
	public void testVsetV() {
		Random rng = new Random();
		int loop_max = 1000000;
		UnsignedInt128Member a = new UnsignedInt128Member();
		for (int i = 0; i < loop_max; i++) {
			BigInteger abig = new BigInteger(128, rng);
			a.setV(abig);
			BigInteger bigTmp = a.v();
			StringBuilder sb = new StringBuilder();
			sb.append("Expecting ");
			sb.append(bigTmp);
			sb.append(" to equal ");
			sb.append(abig);
			String msg = sb.toString();
			assertEquals(msg, abig, bigTmp);
		}
	}

	@Test
	public void exhaustive() {
		if (RUN_EXHAUSTIVE_16_BIT_TESTS) {
			System.out.println("Running exhaustive 16 bit tests");
			addTests();
			subtractTests();
			multiplyTests();
			divModTests();
			System.out.println("  Done running exhaustive 16 bit tests");
		}
	}

	private void addTests() {
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
				G.UINT128.add().call(a, b, c);
				assertEquals(I.add(J).mod(BASE),c.v());
			}			
		}
	}

	private void subtractTests() {
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
				G.UINT128.subtract().call(a, b, c);
				BigInteger result = I.subtract(J);
				if (result.compareTo(BigInteger.ZERO) < 0)
					result = result.add(BASE);
				assertEquals(result,c.v());
			}			
		}
	}

	private void multiplyTests() {
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
				G.UINT128.multiply().call(a, b, c);
				assertEquals(I.multiply(J).mod(BASE),c.v());
			}			
		}
	}

	private void divModTests() {
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
				G.UINT128.divMod().call(a, b, d, m);
				BigInteger[] dm = I.divideAndRemainder(J);
				assertEquals(dm[0], d.v());
				assertEquals(dm[1], m.v());
			}			
		}
	}
	
	// prove that I can divMod two numbers correctly
	@Test
	public void oneDivModTest() {
		UnsignedInt128Member a = G.UINT128.construct("525");
		UnsignedInt128Member b = G.UINT128.construct("20");
		UnsignedInt128Member d = G.UINT128.construct();
		UnsignedInt128Member m = G.UINT128.construct();
		G.UINT128.divMod().call(a, b, d, m);
		assertEquals(BigInteger.valueOf(26), d.v());
		assertEquals(BigInteger.valueOf(5), m.v());
	}

	@Test
	public void setVTest() {
		UnsignedInt128Member v = G.UINT128.construct();
		for (int i = 0; i < 65536; i++) {
			v.setV(BigInteger.valueOf(i));
			assertEquals(i, v.v().longValue());
		}
	}

	@Test
	public void speedVersusBigIntegerTest() {
		long a = System.currentTimeMillis();
		
		UnsignedInt128Member v = G.UINT128.construct();
		UnsignedInt128Member one = G.UINT128.construct();
		G.UINT128.unity().call(one);
		for (int i = 0; i < 1000000; i++) {
			G.UINT128.add().call(v, one, v);
		}

		long b = System.currentTimeMillis();

		BigInteger vb = BigInteger.ZERO;
		for (int i = 0; i < 1000000; i++) {
			vb = vb.add(BigInteger.ONE);
		}
		
		long c = System.currentTimeMillis();
		
		System.out.println("Add one 1000000 times: UInt128 " + (b-a) + " BigInteger " + (c-b));
		
		assertTrue(true);
	}
}