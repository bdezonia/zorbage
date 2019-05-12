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
package nom.bdezonia.zorbage.type.data.int15;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt16;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt15 {

	private int v(int val) {
		int v = val % 32768;
		if (v < -16384)
			v += 32768;
		else if (v > 16383)
			v -= 32768;
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt15Member> data) {
		
		SignedInt15Member in = new SignedInt15Member();
		SignedInt15Member out = new SignedInt15Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt15Member>(50000, G.INT15.construct()));
		testStorageMethods(new ArrayStorageSignedInt16<SignedInt15Member>(50000, G.INT15.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt15Member a = G.INT15.construct();
		SignedInt15Member b = G.INT15.construct();
		SignedInt15Member c = G.INT15.construct();
		for (int i = -16384; i < 16384; i++) {
			a.setV(i);
			
			for (int j = -16384; j < 16384; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				if (i != -16384) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					G.INT15.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.add().call(a,b,c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				if (j >= 0) {
					G.INT15.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%15)), c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				if (j >= 0) {
					G.INT15.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				if (j >= 0) {
					G.INT15.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT15.compare().call(a, b));
				
				SignedInt15Member v = G.INT15.construct();
				assertEquals(0, v.v);
				
				v = G.INT15.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT15.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					if (i != -16384 || j != -1) {
						G.INT15.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT15.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT15.isOdd().call(a));
				
				assertEquals(i==j,G.INT15.isEqual().call(a, b));
				assertEquals(i!=j,G.INT15.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT15.isLess().call(a, b));
				assertEquals(i<=j,G.INT15.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT15.isGreater().call(a, b));
				assertEquals(i>=j,G.INT15.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.maxBound().call(c);
				assertEquals(16383, c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.minBound().call(c);
				assertEquals(-16384, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					G.INT15.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				if (i != -16384) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					G.INT15.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					try {
						G.INT15.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					G.INT15.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					try {
						G.INT15.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT15.pred().call(c, c);
					G.INT15.pred().call(c, c);
					G.INT15.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(a, c);
				expected = (a.v == -16384) ? 16383 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.succ().call(a, c);
				expected = (a.v == 16383) ? -16384 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT15.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT15.signum().call(a));
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT15.pred().call(c, c);
				G.INT15.pred().call(c, c);
				G.INT15.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -16384) {
				G.INT15.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt15Member num = G.INT15.construct();
		for (int offset : new int[] {0, 32768, 65536, 98304, -32768, -65536, -98304}) {
			for (int i = -16384; i < 16384; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
