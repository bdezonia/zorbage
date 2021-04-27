/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.integer.int1;

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
public class TestUnsignedInt1 {
	
	private void testStorageMethods(IndexedDataSource<UnsignedInt1Member> data) {
		
		UnsignedInt1Member in = new UnsignedInt1Member();
		UnsignedInt1Member out = new UnsignedInt1Member();
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
		testStorageMethods(new ArrayStorageBit<UnsignedInt1Member>(G.UINT1.construct(), 6000));
		testStorageMethods(new ArrayStorageSignedInt8<UnsignedInt1Member>(G.UINT1.construct(), 6000));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt1Member a = G.UINT1.construct();
		UnsignedInt1Member b = G.UINT1.construct();
		UnsignedInt1Member c = G.UINT1.construct();
		for (int i = 0; i < 2; i++) {
			a.setV(i);
			
			for (int j = 0; j < 2; j++) {
				b.setV(j);

				c.setV(1-i);
				G.UINT1.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.setV(1-i);
				G.UINT1.add().call(a, b, c);
				assertEquals((i+j) & 0x1, (int)c.v);
				
				c.setV(1-i);
				G.UINT1.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.setV(1-i);
				G.UINT1.bitAnd().call(a, b, c);
				assertEquals((i & j), c.v);
				
				c.setV(1-i);
				G.UINT1.bitOr().call(a, b, c);
				assertEquals((i | j), c.v);
				
				c.setV(1-i);
				G.UINT1.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j&1))&1, c.v);
				
				c.setV(1-i);
				G.UINT1.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&1, c.v);
				
				c.setV(1-i);
				G.UINT1.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&1, c.v);
				
				c.setV(1-i);
				G.UINT1.bitXor().call(a, b, c);
				assertEquals((i ^ j)&1, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.UINT1.compare().call(a, b));
				
				UnsignedInt1Member v = G.UINT1.construct();
				assertEquals(0, v.v);
				
				v = G.UINT1.construct(Integer.toString((i+j) & 1));
				assertEquals(((i+j) & 1), v.v);
				
				v = G.UINT1.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.setV(1-i);
					G.UINT1.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT1.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT1.isOdd().call(a));
				
				assertEquals(i==j, G.UINT1.isEqual().call(a, b));
				assertEquals(i!=j, G.UINT1.isNotEqual().call(a, b));
				assertEquals(i<j, G.UINT1.isLess().call(a, b));
				assertEquals(i<=j, G.UINT1.isLessEqual().call(a, b));
				assertEquals(i>j, G.UINT1.isGreater().call(a, b));
				assertEquals(i>=j, G.UINT1.isGreaterEqual().call(a, b));
				
				c.setV(1-i);
				G.UINT1.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.setV(1-i);
				G.UINT1.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.setV(1-i);
				G.UINT1.maxBound().call(c);
				assertEquals(1, c.v);
				
				c.setV(1-i);
				G.UINT1.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.setV(1-i);
					G.UINT1.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.setV(1-i);
				G.UINT1.multiply().call(a, b, c);
				assertEquals((i*j) & 1,c.v);
				
				c.setV(1-i);
				G.UINT1.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT1.pred().call(c, c);
					G.UINT1.pred().call(c, c);
					try {
						G.UINT1.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.setV(1-i);
					G.UINT1.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2,51))
						assertEquals(((long)p) & 1, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.setV(1-i);
					try {
						G.UINT1.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.setV(1-i);
					G.UINT1.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 1, c.v);
				}
				
				c.setV(1-i);
				G.UINT1.pred().call(a, c);
				expected = (a.v == 0) ? 1 : 0;
				assertEquals(expected, c.v);
				
				c.setV(1-i);
				G.UINT1.succ().call(a, c);
				expected = (a.v == 1) ? 0 : 1;
				assertEquals(expected, c.v);
				
				// TODO: how to test?
				G.UINT1.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.UINT1.signum().call(a));
				
				c.setV(1-i);
				G.UINT1.subtract().call(a, b, c);
				assertEquals((i-j) & 1, c.v);
				
				c.setV(0);
				G.UINT1.unity().call(c);
				assertEquals(1, c.v);
				
				c.setV(1);
				G.UINT1.zero().call(c);
				assertEquals(0, c.v);
				
				G.UINT1.logicalAnd().call(a, b, c);
				assertEquals((a.v==1) && (b.v==1), (c.v == 1));
				
				G.UINT1.logicalAndNot().call(a, b, c);
				assertEquals((a.v==1) && (b.v==0), (c.v == 1));
				
				G.UINT1.logicalNot().call(a, c);
				assertEquals((a.v==1 ? false : true), (c.v == 1));
				
				G.UINT1.logicalOr().call(a, b, c);
				assertEquals((a.v==1) || (b.v==1), (c.v == 1));
				
				G.UINT1.logicalXor().call(a, b, c);
				assertEquals((a.v==1) ^ (b.v==1), (c.v == 1));
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt1Member num = G.UINT1.construct();
		for (int offset : new int[] {0, 2, 4, 6, 8, 10, -2, -4, -6, -8, -10}) {
			for (int i = 0; i < 2; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
