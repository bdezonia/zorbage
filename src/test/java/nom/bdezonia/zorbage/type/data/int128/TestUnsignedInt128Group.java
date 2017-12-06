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
package nom.bdezonia.zorbage.type.data.int128;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

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
			G.UINT128.random(a);
			BigInteger abig = a.v();
			for (int j = 0; j < loop_max; j++) {
				G.UINT128.random(b);
				BigInteger bbig = b.v();
				bigTmp = c.v();
				sb = new StringBuilder();
				sb.append("Expecting compare of ");
				sb.append(abig);
				sb.append(" and ");
				sb.append(bbig);
				sb.append(" to match between BigInts and UInt128s");
				msg = sb.toString();
				assertEquals(msg, abig.compareTo(bbig), G.UINT128.compare(a, b));
				G.UINT128.add(a, b, c);
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
				G.UINT128.subtract(a, b, c);
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
				G.UINT128.multiply(a, b, c);
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
				if (G.UINT128.isNotEqual(b, zero)) {
					G.UINT128.divMod(a, b, d, m);
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

	@Test
	public void exhaustive() {
		//testMultiplies();
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
				G.UINT128.add(a, b, c);
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
				G.UINT128.subtract(a, b, c);
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
				G.UINT128.multiply(a, b, c);
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
				G.UINT128.divMod(a, b, d, m);
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
		G.UINT128.divMod(a, b, d, m);
		assertEquals(BigInteger.valueOf(26), d.v());
		assertEquals(BigInteger.valueOf(5), m.v());
	}

	/* Now long backed: this test too resource intensive
	@Test
	public void addOneAndVTest() {
		ArrayList<Long> list = new ArrayList<Long>();
		
		UnsignedInt128Member zero = G.UINT128.construct();
		UnsignedInt128Member min = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.minBound(min);
		G.UINT128.maxBound(max);
		
		assertTrue(G.UINT128.isEqual(min, zero));

		// TODO this will fail when going from byte backed to long backed
		UnsignedInt128Member v = G.UINT128.construct();
		while (G.UINT128.isLess(v, max)) {
			list.add(v.v().longValue());
			G.UINT128.succ(v, v);
		}
		list.add(v.v().longValue());

		G.UINT128.succ(v, v);
		assertTrue(G.UINT128.isEqual(v, zero));
		for (int i = 0; i < list.size(); i++) {
			assertEquals(i, list.get(i).longValue());
		}
	}
	*/
	
	/* Now long backed: this test too resource intensive
	@Test
	public void subtractOneAndVTest() {
		ArrayList<Long> list = new ArrayList<Long>();
		
		UnsignedInt128Member zero = G.UINT128.construct();
		UnsignedInt128Member min = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.minBound(min);
		G.UINT128.maxBound(max);
		
		assertTrue(G.UINT128.isEqual(min, zero));
		
		// TODO this will fail when going from byte backed to long backed
		UnsignedInt128Member v = new UnsignedInt128Member(max);
		while (G.UINT128.isGreater(v, zero)) {
			list.add(v.v().longValue());
			G.UINT128.pred(v, v);
		}
		list.add(v.v().longValue());

		G.UINT128.pred(v, v);
		assertTrue(G.UINT128.isEqual(v, max));

		for (int i = 0; i < list.size(); i++) {
			assertEquals(65535-i, list.get(i).longValue());
		}
	}
	*/
	
	@Test
	public void setVTest() {
		UnsignedInt128Member v = G.UINT128.construct();
		for (int i = 0; i < 65536; i++) {
			v.setV(BigInteger.valueOf(i));
			assertEquals(i, v.v().longValue());
		}
	}

	/* Now long backed: this test too resource intensive
	@Test
	public void compareTest() {
		ArrayList<UnsignedInt128Member> list = new ArrayList<UnsignedInt128Member>();
		
		UnsignedInt128Member v = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.maxBound(max);
		
		while (G.UINT128.isLess(v, max)) {
			list.add(v.duplicate());
			G.UINT128.succ(v, v);
		}
		list.add(v.duplicate());

		for (int i = 0; i < list.size(); i++) {
			UnsignedInt128Member x = list.get(i);
			if (i >= 1) {
				UnsignedInt128Member y = list.get(i-1);
				assertTrue(G.UINT128.isGreater(x, y));
				assertFalse(G.UINT128.isLessEqual(x, y));
			}
			assertTrue(G.UINT128.isEqual(x,x));
			assertFalse(G.UINT128.isNotEqual(x,x));
			assertTrue(G.UINT128.isGreaterEqual(x,x));
			assertTrue(G.UINT128.isLessEqual(x,x));
			if (i < list.size()-1) {
				UnsignedInt128Member y = list.get(i+1);
				assertTrue(G.UINT128.isLess(x, y));
				assertFalse(G.UINT128.isGreaterEqual(x, y));
			}
		}
	}
	*/
	
	@Test
	public void versusTest() {
		long a = System.currentTimeMillis();
		
		UnsignedInt128Member v = G.UINT128.construct();
		UnsignedInt128Member one = G.UINT128.construct();
		G.UINT128.unity(one);
		for (int i = 0; i < 1000000; i++) {
			G.UINT128.add(v, one, v);
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