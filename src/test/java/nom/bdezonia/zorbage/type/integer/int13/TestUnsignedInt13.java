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
package nom.bdezonia.zorbage.type.integer.int13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.storage.array.ArrayStorageSignedInt16;
import nom.bdezonia.zorbage.type.integer.int13.UnsignedInt13Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt13 {
	
	private void testStorageMethods(IndexedDataSource<UnsignedInt13Member> data) {
		
		UnsignedInt13Member in = new UnsignedInt13Member();
		UnsignedInt13Member out = new UnsignedInt13Member();
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
		testStorageMethods(new ArrayStorageBit<UnsignedInt13Member>(G.UINT13.construct(), 50000));
		testStorageMethods(new ArrayStorageSignedInt16<UnsignedInt13Member>(G.UINT13.construct(), 50000));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt13Member a = G.UINT13.construct();
		UnsignedInt13Member b = G.UINT13.construct();
		UnsignedInt13Member c = G.UINT13.construct();
		for (int i = 0; i < 8192; i++) {

			if (i % 100 == 0)
				System.out.println(i);
			
			a.setV(i);
			
			for (int j = 0; j < 8192; j++) {
				b.setV(j);

				//System.out.println(i+" op "+j);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.add().call(a, b, c);
				assertEquals((i+j) & 8191, (int)c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitAnd().call(a, b, c);
				assertEquals((i & j)&8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitOr().call(a, b, c);
				assertEquals((i | j)&8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j%13))&8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.bitXor().call(a, b, c);
				assertEquals((i ^ j)&8191, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.UINT13.compare().call(a, b));
				
				UnsignedInt13Member v = G.UINT13.construct();
				assertEquals(0, v.v);
				
				v = G.UINT13.construct(Integer.toString((i+j) & 8191));
				assertEquals(((i+j) & 8191), v.v);
				
				v = G.UINT13.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					G.UINT13.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT13.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT13.isOdd().call(a));
				
				assertEquals(i==j, G.UINT13.isEqual().call(a, b));
				assertEquals(i!=j, G.UINT13.isNotEqual().call(a, b));
				assertEquals(i<j, G.UINT13.isLess().call(a, b));
				assertEquals(i<=j, G.UINT13.isLessEqual().call(a, b));
				assertEquals(i>j, G.UINT13.isGreater().call(a, b));
				assertEquals(i>=j, G.UINT13.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.maxBound().call(c);
				assertEquals(8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					G.UINT13.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.multiply().call(a, b, c);
				assertEquals((i*j) & 8191,c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					try {
						G.UINT13.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					G.UINT13.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2,51))
						assertEquals(((long)p) & 8191, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					try {
						G.UINT13.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT13.pred().call(c, c);
					G.UINT13.pred().call(c, c);
					G.UINT13.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 8191, c.v);
				}
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(a, c);
				expected = (a.v == 0) ? 8191 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.succ().call(a, c);
				expected = (a.v == 8191) ? 0 : a.v+1;
				assertEquals(expected, c.v);
				
				G.UINT13.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.UINT13.signum().call(a));
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.subtract().call(a, b, c);
				assertEquals((i-j) & 8191, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.UINT13.pred().call(c, c);
				G.UINT13.pred().call(c, c);
				G.UINT13.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt13Member num = G.UINT13.construct();
		for (int offset : new int[] {0, 8192, 16384, 24576, -8192, -16384, -24576}) {
			for (int i = 0; i < 8192; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
