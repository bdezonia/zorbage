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
package nom.bdezonia.zorbage.type.data.int7;

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
public class TestUnsignedInt7 {
	
	private void testStorageMethods(IndexedDataSource<UnsignedInt7Member> data) {
		
		UnsignedInt7Member in = new UnsignedInt7Member();
		UnsignedInt7Member out = new UnsignedInt7Member();
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
		testStorageMethods(new ArrayStorageBit<UnsignedInt7Member>(6000, G.UINT7.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<UnsignedInt7Member>(6000, G.UINT7.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt7Member a = G.UINT7.construct();
		UnsignedInt7Member b = G.UINT7.construct();
		UnsignedInt7Member c = G.UINT7.construct();
		for (int i = 0; i < 128; i++) {
			a.setV(i);
			
			for (int j = 0; j < 128; j++) {
				b.setV(j);

				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.add().call(a, b, c);
				assertEquals((i+j) & 127, (int)c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitAnd().call(a, b, c);
				assertEquals((i & j)&127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitOr().call(a, b, c);
				assertEquals((i | j)&127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j%7))&127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.bitXor().call(a, b, c);
				assertEquals((i ^ j)&127, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.UINT7.compare().call(a, b));
				
				UnsignedInt7Member v = G.UINT7.construct();
				assertEquals(0, v.v);
				
				v = G.UINT7.construct(Integer.toString(i+j));
				assertEquals(((i+j) & 127), v.v);
				
				v = G.UINT7.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					G.UINT7.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT7.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT7.isOdd().call(a));
				
				assertEquals(i==j,G.UINT7.isEqual().call(a, b));
				assertEquals(i!=j,G.UINT7.isNotEqual().call(a, b));
				assertEquals(i<j,G.UINT7.isLess().call(a, b));
				assertEquals(i<=j,G.UINT7.isLessEqual().call(a, b));
				assertEquals(i>j,G.UINT7.isGreater().call(a, b));
				assertEquals(i>=j,G.UINT7.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.maxBound().call(c);
				assertEquals(127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					G.UINT7.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.multiply().call(a,b,c);
				assertEquals((i*j) & 127,c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					try {
						G.UINT7.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					G.UINT7.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 127, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					try {
						G.UINT7.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT7.pred().call(c, c);
					G.UINT7.pred().call(c, c);
					G.UINT7.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 127, c.v);
				}
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(a, c);
				expected = (a.v == 0) ? 127 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.succ().call(a, c);
				expected = (a.v == 127) ? 0 : a.v+1;
				assertEquals(expected, c.v);
				
				G.UINT7.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.UINT7.signum().call(a));
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.subtract().call(a, b, c);
				assertEquals((i-j) & 127, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.UINT7.pred().call(c, c);
				G.UINT7.pred().call(c, c);
				G.UINT7.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt7Member num = G.UINT7.construct();
		for (int offset : new int[] {0, 128, 256, 384, 512, 640, -128, -256, -384, -512, -640}) {
			for (int i = 0; i < 128; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
