/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.integer.int4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.storage.array.ArrayStorageSignedInt8;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt4 {

	private int v(int val) {
		int v = val % 16;
		if (val < 0) {
			if (v < -8) v += 16;
		}
		else {
			// val >= 0
			if (v > 7) v -= 16;
		}
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt4Member> data) {
		
		SignedInt4Member in = new SignedInt4Member();
		SignedInt4Member out = new SignedInt4Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt4Member>(G.INT4.construct(), 12000));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt4Member>(G.INT4.construct(), 12000));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt4Member a = G.INT4.construct();
		SignedInt4Member b = G.INT4.construct();
		SignedInt4Member c = G.INT4.construct();
		for (int i = -8; i < 8; i++) {
			a.setV(i);
			
			for (int j = -8; j < 8; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				if (i != -8) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					G.INT4.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.add().call(a, b, c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				if (j >= 0) {
					G.INT4.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%4)), c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				if (j >= 0) {
					G.INT4.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				if (j >= 0) {
					G.INT4.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.INT4.compare().call(a, b));
				
				SignedInt4Member v = G.INT4.construct();
				assertEquals(0, v.v);
				
				v = G.INT4.construct(Integer.toString(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT4.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					if (i != -8 || j != -1) {
						G.INT4.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT4.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT4.isOdd().call(a));
				
				assertEquals(i==j, G.INT4.isEqual().call(a, b));
				assertEquals(i!=j, G.INT4.isNotEqual().call(a, b));
				assertEquals(i<j, G.INT4.isLess().call(a, b));
				assertEquals(i<=j, G.INT4.isLessEqual().call(a, b));
				assertEquals(i>j, G.INT4.isGreater().call(a, b));
				assertEquals(i>=j, G.INT4.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.maxBound().call(c);
				assertEquals(7, c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.minBound().call(c);
				assertEquals(-8, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					G.INT4.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.multiply().call(a, b, c);
				assertEquals(v(i*j),c.v);
				
				if (i != -8) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					G.INT4.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					try {
						G.INT4.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					G.INT4.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					try {
						G.INT4.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT4.pred().call(c, c);
					G.INT4.pred().call(c, c);
					G.INT4.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(a, c);
				expected = (a.v == -8) ? 7 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.succ().call(a, c);
				expected = (a.v == 7) ? -8 : a.v+1;
				assertEquals(expected, c.v);
				
				// TODO: random()
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.INT4.signum().call(a));
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT4.pred().call(c, c);
				G.INT4.pred().call(c, c);
				G.INT4.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -8) {
				G.INT4.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt4Member num = G.INT4.construct();
		for (int offset : new int[] {0, 16, 32, 48, 64, 80, -16, -32, -48,-64, -80}) {
			for (int i = -8; i < 8; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
