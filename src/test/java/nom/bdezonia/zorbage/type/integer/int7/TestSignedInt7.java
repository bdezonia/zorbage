/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.integer.int7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.storage.array.ArrayStorageSignedInt8;
import nom.bdezonia.zorbage.type.integer.int7.SignedInt7Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt7 {

	private int v(int val) {
		int v = val % 128;
		if (val < 0) {
			if (v < -64) v += 128;
		}
		else {
			// val >= 0
			if (v > 63) v -= 128;
		}
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt7Member> data) {
		
		SignedInt7Member in = new SignedInt7Member();
		SignedInt7Member out = new SignedInt7Member();
		in.setV(0);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.v);
		}
		for (long i = 0; i < data.size(); i++) {
			in.setV((int)i);
			data.set(i, in);
			out.setV(in.v-1);
			data.get(i, out);
			assertEquals(in.v, out.v);
		}
		in.setV(0);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.v);
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setV((int)i);
			data.set(i, in);
			out.setV(in.v-1);
			data.get(i, out);
			assertEquals(in.v, out.v);
		}
		in.setV(0);
		for (long i = data.size()-1; i >= 0; i--) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.v);
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setV((int)i);
			data.set(i, in);
			out.setV(in.v-1);
			data.get(i, out);
			assertEquals(in.v, out.v);
		}
	}

	@Test
	public void testStorage() {
		testStorageMethods(new ArrayStorageBit<SignedInt7Member>(G.INT7.construct(), 12000));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt7Member>(G.INT7.construct(), 12000));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt7Member a = G.INT7.construct();
		SignedInt7Member b = G.INT7.construct();
		SignedInt7Member c = G.INT7.construct();
		for (int i = -64; i < 64; i++) {
			a.setV(i);
			
			for (int j = -64; j < 64; j++) {
				b.setV(j);

				// System.out.println(i+" op "+j);

				if (i != -64) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					G.INT7.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.add().call(a, b, c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				if (j >= 0) {
					G.INT7.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%7)), c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				if (j >= 0) {
					G.INT7.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				if (j >= 0) {
					G.INT7.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.INT7.compare().call(a, b));
				
				SignedInt7Member v = G.INT7.construct();
				assertEquals(0, v.v);
				
				v = G.INT7.construct(Integer.toString(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT7.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					if (i != -64 || j != -1) {
						G.INT7.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT7.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT7.isOdd().call(a));
				
				assertEquals(i==j, G.INT7.isEqual().call(a, b));
				assertEquals(i!=j, G.INT7.isNotEqual().call(a, b));
				assertEquals(i<j, G.INT7.isLess().call(a, b));
				assertEquals(i<=j, G.INT7.isLessEqual().call(a, b));
				assertEquals(i>j, G.INT7.isGreater().call(a, b));
				assertEquals(i>=j, G.INT7.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.maxBound().call(c);
				assertEquals(63, c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.minBound().call(c);
				assertEquals(-64, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					G.INT7.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.multiply().call(a, b, c);
				assertEquals(v(i*j),c.v);
				
				if (i != -64) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					G.INT7.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					try {
						G.INT7.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					G.INT7.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					try {
						G.INT7.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT7.pred().call(c, c);
					G.INT7.pred().call(c, c);
					G.INT7.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(a, c);
				expected = (a.v == -64) ? 63 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.succ().call(a, c);
				expected = (a.v == 63) ? -64 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT7.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.INT7.signum().call(a));
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT7.pred().call(c, c);
				G.INT7.pred().call(c, c);
				G.INT7.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -64) {
				G.INT7.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt7Member num = G.INT7.construct();
		for (int offset : new int[] {0, 128, 256, 384, 512, 640, -128, -256, -384, -512, -640}) {
			for (int i = -64; i < 64; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
