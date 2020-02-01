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
package nom.bdezonia.zorbage.type.data.int9;

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
public class TestUnsignedInt9 {
	
	private void testStorageMethods(IndexedDataSource<UnsignedInt9Member> data) {
		
		UnsignedInt9Member in = new UnsignedInt9Member();
		UnsignedInt9Member out = new UnsignedInt9Member();
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
		testStorageMethods(new ArrayStorageBit<UnsignedInt9Member>(6000, G.UINT9.construct()));
		testStorageMethods(new ArrayStorageSignedInt16<UnsignedInt9Member>(6000, G.UINT9.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt9Member a = G.UINT9.construct();
		UnsignedInt9Member b = G.UINT9.construct();
		UnsignedInt9Member c = G.UINT9.construct();
		for (int i = 0; i < 512; i++) {
			a.setV(i);
			
			for (int j = 0; j < 512; j++) {
				b.setV(j);

				//System.out.println(i+" op "+j);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.add().call(a, b, c);
				assertEquals((i+j) & 511, (int)c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitAnd().call(a, b, c);
				assertEquals((i & j)&511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitOr().call(a, b, c);
				assertEquals((i | j)&511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j%9))&511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.bitXor().call(a, b, c);
				assertEquals((i ^ j)&511, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.UINT9.compare().call(a, b));
				
				UnsignedInt9Member v = G.UINT9.construct();
				assertEquals(0, v.v);
				
				v = G.UINT9.construct(Integer.toString((i+j) & 511));
				assertEquals(((i+j) & 511), v.v);
				
				v = G.UINT9.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					G.UINT9.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT9.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT9.isOdd().call(a));
				
				assertEquals(i==j,G.UINT9.isEqual().call(a, b));
				assertEquals(i!=j,G.UINT9.isNotEqual().call(a, b));
				assertEquals(i<j,G.UINT9.isLess().call(a, b));
				assertEquals(i<=j,G.UINT9.isLessEqual().call(a, b));
				assertEquals(i>j,G.UINT9.isGreater().call(a, b));
				assertEquals(i>=j,G.UINT9.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.maxBound().call(c);
				assertEquals(511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					G.UINT9.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.multiply().call(a,b,c);
				assertEquals((i*j) & 511,c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					try {
						G.UINT9.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					G.UINT9.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2,51))
						assertEquals(((long)p) & 511, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					try {
						G.UINT9.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT9.pred().call(c, c);
					G.UINT9.pred().call(c, c);
					G.UINT9.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 511, c.v);
				}
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(a, c);
				expected = (a.v == 0) ? 511 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.succ().call(a, c);
				expected = (a.v == 511) ? 0 : a.v+1;
				assertEquals(expected, c.v);
				
				G.UINT9.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.UINT9.signum().call(a));
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.subtract().call(a, b, c);
				assertEquals((i-j) & 511, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.UINT9.pred().call(c, c);
				G.UINT9.pred().call(c, c);
				G.UINT9.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt9Member num = G.UINT9.construct();
		for (int offset : new int[] {0, 512, 1024, 1536, -512, -1024, -1536}) {
			for (int i = 0; i < 512; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
