/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt8;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt5 {

	private int v(int val) {
		int v = val % 32;
		if (val < 0) {
			if (v < -16) v += 32;
		}
		else {
			// val >= 0
			if (v > 15) v -= 32;
		}
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt5Member> data) {
		
		SignedInt5Member in = new SignedInt5Member();
		SignedInt5Member out = new SignedInt5Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt5Member>(12000, G.INT5.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt5Member>(12000, G.INT5.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt5Member a = G.INT5.construct();
		SignedInt5Member b = G.INT5.construct();
		SignedInt5Member c = G.INT5.construct();
		for (int i = -16; i < 16; i++) {
			a.setV(i);
			
			for (int j = -16; j < 16; j++) {
				b.setV(j);

				// System.out.println(i+" op "+j);

				if (i != -16) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					G.INT5.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.add().call(a,b,c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				if (j >= 0) {
					G.INT5.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%5)), c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				if (j >= 0) {
					G.INT5.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				if (j >= 0) {
					G.INT5.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT5.compare().call(a, b));
				
				SignedInt5Member v = G.INT5.construct();
				assertEquals(0, v.v);
				
				v = G.INT5.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT5.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					if (i != -16 || j != -1) {
						G.INT5.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT5.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT5.isOdd().call(a));
				
				assertEquals(i==j,G.INT5.isEqual().call(a, b));
				assertEquals(i!=j,G.INT5.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT5.isLess().call(a, b));
				assertEquals(i<=j,G.INT5.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT5.isGreater().call(a, b));
				assertEquals(i>=j,G.INT5.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.maxBound().call(c);
				assertEquals(15, c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.minBound().call(c);
				assertEquals(-16, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					G.INT5.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				if (i != -16) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					G.INT5.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					try {
						G.INT5.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					G.INT5.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					try {
						G.INT5.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT5.pred().call(c, c);
					G.INT5.pred().call(c, c);
					G.INT5.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(a, c);
				expected = (a.v == -16) ? 15 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.succ().call(a, c);
				expected = (a.v == 15) ? -16 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT5.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT5.signum().call(a));
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT5.pred().call(c, c);
				G.INT5.pred().call(c, c);
				G.INT5.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -16) {
				G.INT5.negate().call(a, b);
				assertEquals(-a.v(), b.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt5Member num = G.INT5.construct();
		for (int offset : new int[] {0, 32, 64, 96, 128, 160, -32, -64, -96, -128, -160}) {
			for (int i = -16; i < 16; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
