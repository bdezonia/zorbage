/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.int2;

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
public class TestUnsignedInt2 {
	
	private void testStorageMethods(IndexedDataSource<UnsignedInt2Member> data) {
		
		UnsignedInt2Member in = new UnsignedInt2Member();
		UnsignedInt2Member out = new UnsignedInt2Member();
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
		testStorageMethods(new ArrayStorageBit<UnsignedInt2Member>(6000, G.UINT2.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<UnsignedInt2Member>(6000, G.UINT2.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt2Member a = G.UINT2.construct();
		UnsignedInt2Member b = G.UINT2.construct();
		UnsignedInt2Member c = G.UINT2.construct();
		for (int i = 0; i < 4; i++) {
			a.setV(i);
			
			for (int j = 0; j < 4; j++) {
				b.setV(j);

				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.add().call(a, b, c);
				assertEquals((i+j) & 0x03, (int)c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitAnd().call(a, b, c);
				assertEquals((i & j)&0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitOr().call(a, b, c);
				assertEquals((i | j)&0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j%2))&0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.bitXor().call(a, b, c);
				assertEquals((i ^ j)&0x03, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.UINT2.compare().call(a, b));
				
				UnsignedInt2Member v = G.UINT2.construct();
				assertEquals(0, v.v);
				
				v = G.UINT2.construct(Integer.toString((i+j) & 0x03));
				assertEquals(((i+j) & 0x03), v.v);
				
				v = G.UINT2.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					G.UINT2.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT2.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT2.isOdd().call(a));
				
				assertEquals(i==j, G.UINT2.isEqual().call(a, b));
				assertEquals(i!=j, G.UINT2.isNotEqual().call(a, b));
				assertEquals(i<j, G.UINT2.isLess().call(a, b));
				assertEquals(i<=j, G.UINT2.isLessEqual().call(a, b));
				assertEquals(i>j, G.UINT2.isGreater().call(a, b));
				assertEquals(i>=j, G.UINT2.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.maxBound().call(c);
				assertEquals(0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					G.UINT2.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.multiply().call(a, b, c);
				assertEquals((i*j) & 0x03,c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					try {
						G.UINT2.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					G.UINT2.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2,51))
						assertEquals(((long)p) & 0x03, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					try {
						G.UINT2.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT2.pred().call(c, c);
					G.UINT2.pred().call(c, c);
					G.UINT2.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 0x03, c.v);
				}
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(a, c);
				expected = (a.v == 0) ? 0x03 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.succ().call(a, c);
				expected = (a.v == 0x03) ? 0 : a.v+1;
				assertEquals(expected, c.v);
				
				// TODO: random()
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.UINT2.signum().call(a));
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.subtract().call(a, b, c);
				assertEquals((i-j) & 0x03, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.UINT2.pred().call(c, c);
				G.UINT2.pred().call(c, c);
				G.UINT2.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt2Member num = G.UINT2.construct();
		for (int offset : new int[] {0, 4, 8, 12, 16, 20, -4, -8, -12,-16, -20}) {
			for (int i = 0; i < 4; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
