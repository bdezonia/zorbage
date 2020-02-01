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
package nom.bdezonia.zorbage.type.data.int6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt8;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt6 {

	private int v(int val) {
		int v = val % 64;
		if (val < 0) {
			if (v < -32) v += 64;
		}
		else {
			// val >= 0
			if (v > 31) v -= 64;
		}
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt6Member> data) {
		
		SignedInt6Member in = new SignedInt6Member();
		SignedInt6Member out = new SignedInt6Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt6Member>(12000, G.INT6.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt6Member>(12000, G.INT6.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt6Member a = G.INT6.construct();
		SignedInt6Member b = G.INT6.construct();
		SignedInt6Member c = G.INT6.construct();
		for (int i = -32; i < 32; i++) {
			a.setV(i);
			
			for (int j = -32; j < 32; j++) {
				b.setV(j);

				// System.out.println(i+" op "+j);

				if (i != -32) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					G.INT6.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.add().call(a, b, c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				if (j >= 0) {
					G.INT6.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%6)), c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				if (j >= 0) {
					G.INT6.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				if (j >= 0) {
					G.INT6.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT6.compare().call(a, b));
				
				SignedInt6Member v = G.INT6.construct();
				assertEquals(0, v.v);
				
				v = G.INT6.construct(Integer.toString(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT6.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					if (i != -32 || j != -1) {
						G.INT6.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT6.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT6.isOdd().call(a));
				
				assertEquals(i==j,G.INT6.isEqual().call(a, b));
				assertEquals(i!=j,G.INT6.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT6.isLess().call(a, b));
				assertEquals(i<=j,G.INT6.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT6.isGreater().call(a, b));
				assertEquals(i>=j,G.INT6.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.maxBound().call(c);
				assertEquals(31, c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.minBound().call(c);
				assertEquals(-32, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					G.INT6.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				if (i != -32) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					G.INT6.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					try {
						G.INT6.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					G.INT6.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					try {
						G.INT6.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT6.pred().call(c, c);
					G.INT6.pred().call(c, c);
					G.INT6.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(a, c);
				expected = (a.v == -32) ? 31 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.succ().call(a, c);
				expected = (a.v == 31) ? -32 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT6.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT6.signum().call(a));
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT6.pred().call(c, c);
				G.INT6.pred().call(c, c);
				G.INT6.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -32) {
				G.INT6.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt6Member num = G.INT6.construct();
		for (int offset : new int[] {0, 64, 128, 192, 256, 320, -64, -128, -192, -256, -320}) {
			for (int i = -32; i < 32; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
