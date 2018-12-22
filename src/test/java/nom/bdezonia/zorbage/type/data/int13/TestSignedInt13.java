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
package nom.bdezonia.zorbage.type.data.int13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt16;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt13 {

	private int v(int val) {
		int v = val % 8192;
		if (v < -4096)
			v += 8192;
		else if (v > 4095)
			v -= 8192;
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<?, SignedInt13Member> data) {
		
		SignedInt13Member in = new SignedInt13Member();
		SignedInt13Member out = new SignedInt13Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt13Member>(12000, G.INT13.construct()));
		testStorageMethods(new ArrayStorageSignedInt16<SignedInt13Member>(12000, G.INT13.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt13Member a = G.INT13.construct();
		SignedInt13Member b = G.INT13.construct();
		SignedInt13Member c = G.INT13.construct();
		for (int i = -4096; i < 4096; i++) {
			a.setV(i);
			
			for (int j = -4096; j < 4096; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.abs().call(a, c);
				assertEquals(a.v == -4096 ? -4096 : Math.abs(a.v),c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.add().call(a,b,c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				if (j >= 0) {
					G.INT13.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%13)), c.v);
				}
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				if (j >= 0) {
					G.INT13.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				if (j >= 0) {
					G.INT13.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT13.compare().call(a, b));
				
				SignedInt13Member v = G.INT13.construct();
				assertEquals(0, v.v);
				
				v = G.INT13.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT13.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					if (i != -4096 || j != -1) {
						G.INT13.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT13.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT13.isOdd().call(a));
				
				assertEquals(i==j,G.INT13.isEqual().call(a, b));
				assertEquals(i!=j,G.INT13.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT13.isLess().call(a, b));
				assertEquals(i<=j,G.INT13.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT13.isGreater().call(a, b));
				assertEquals(i>=j,G.INT13.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.maxBound().call(c);
				assertEquals(4095, c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.minBound().call(c);
				assertEquals(-4096, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					G.INT13.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.norm().call(a, c);
				assertEquals(i == -4096 ? -4096 : Math.abs(a.v), c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					try {
						G.INT13.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					G.INT13.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					try {
						G.INT13.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT13.pred().call(c, c);
					G.INT13.pred().call(c, c);
					G.INT13.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(a, c);
				expected = (a.v == -4096) ? 4095 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.succ().call(a, c);
				expected = (a.v == 4095) ? -4096 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT13.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT13.signum().call(a));
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT13.pred().call(c, c);
				G.INT13.pred().call(c, c);
				G.INT13.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt13Member num = G.INT13.construct();
		for (int offset : new int[] {0, 8192, 16384, 24576, -8192, -16384, -24576}) {
			for (int i = -4096; i < 4096; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
