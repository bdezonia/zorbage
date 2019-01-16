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
package nom.bdezonia.zorbage.type.data.int14;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt16;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt14 {

	private int v(int val) {
		int v = val % 16384;
		if (v < -8192)
			v += 16384;
		else if (v > 8191)
			v -= 16384;
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<?, SignedInt14Member> data) {
		
		SignedInt14Member in = new SignedInt14Member();
		SignedInt14Member out = new SignedInt14Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt14Member>(50000, G.INT14.construct()));
		testStorageMethods(new ArrayStorageSignedInt16<SignedInt14Member>(50000, G.INT14.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt14Member a = G.INT14.construct();
		SignedInt14Member b = G.INT14.construct();
		SignedInt14Member c = G.INT14.construct();
		for (int i = -8192; i < 8192; i++) {
			a.setV(i);
			
			for (int j = -8192; j < 8192; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				if (i != -8192) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					G.INT14.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.add().call(a,b,c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				if (j >= 0) {
					G.INT14.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%14)), c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				if (j >= 0) {
					G.INT14.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				if (j >= 0) {
					G.INT14.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT14.compare().call(a, b));
				
				SignedInt14Member v = G.INT14.construct();
				assertEquals(0, v.v);
				
				v = G.INT14.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT14.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					if (i != -8192 || j != -1) {
						G.INT14.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT14.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT14.isOdd().call(a));
				
				assertEquals(i==j,G.INT14.isEqual().call(a, b));
				assertEquals(i!=j,G.INT14.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT14.isLess().call(a, b));
				assertEquals(i<=j,G.INT14.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT14.isGreater().call(a, b));
				assertEquals(i>=j,G.INT14.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.maxBound().call(c);
				assertEquals(8191, c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.minBound().call(c);
				assertEquals(-8192, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					G.INT14.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				if (i != -8192) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					G.INT14.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					try {
						G.INT14.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					G.INT14.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					try {
						G.INT14.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT14.pred().call(c, c);
					G.INT14.pred().call(c, c);
					G.INT14.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(a, c);
				expected = (a.v == -8192) ? 8191 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.succ().call(a, c);
				expected = (a.v == 8191) ? -8192 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT14.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT14.signum().call(a));
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT14.pred().call(c, c);
				G.INT14.pred().call(c, c);
				G.INT14.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -8192) {
				G.INT14.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt14Member num = G.INT14.construct();
		for (int offset : new int[] {0, 16384, 32768, 49152, -16384, -32768, -49152}) {
			for (int i = -8192; i < 8192; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
