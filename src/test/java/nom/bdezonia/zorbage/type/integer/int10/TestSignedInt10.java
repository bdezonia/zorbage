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
package nom.bdezonia.zorbage.type.integer.int10;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.storage.array.ArrayStorageSignedInt16;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt10 {

	private int v(int val) {
		int v = val % 1024;
		if (v < -512)
			v += 1024;
		else if (v > 511)
			v -= 1024;
		return v;
	}
	
	private void testStorageMethods(IndexedDataSource<SignedInt10Member> data) {
		
		SignedInt10Member in = new SignedInt10Member();
		SignedInt10Member out = new SignedInt10Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt10Member>(G.INT10.construct(),12000));
		testStorageMethods(new ArrayStorageSignedInt16<SignedInt10Member>(G.INT10.construct(),12000));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt10Member a = G.INT10.construct();
		SignedInt10Member b = G.INT10.construct();
		SignedInt10Member c = G.INT10.construct();
		for (int i = -512; i < 512; i++) {
			a.setV(i);
			
			for (int j = -512; j < 512; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				if ( i != -512) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.abs().call(a, c);
					assertEquals(Math.abs(a.v),c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.add().call(a, b, c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				if (j >= 0) {
					G.INT10.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%10)), c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				if (j >= 0) {
					G.INT10.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				if (j >= 0) {
					G.INT10.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int) G.INT10.compare().call(a, b));
				
				SignedInt10Member v = G.INT10.construct();
				assertEquals(0, v.v);
				
				v = G.INT10.construct(Integer.toString(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT10.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					if (i != -512 || j != -1) {
						G.INT10.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT10.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT10.isOdd().call(a));
				
				assertEquals(i==j, G.INT10.isEqual().call(a, b));
				assertEquals(i!=j, G.INT10.isNotEqual().call(a, b));
				assertEquals(i<j, G.INT10.isLess().call(a, b));
				assertEquals(i<=j, G.INT10.isLessEqual().call(a, b));
				assertEquals(i>j, G.INT10.isGreater().call(a, b));
				assertEquals(i>=j, G.INT10.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.maxBound().call(c);
				assertEquals(511, c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.minBound().call(c);
				assertEquals(-512, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.multiply().call(a, b, c);
				assertEquals(v(i*j),c.v);
				
				if (i != -512) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.norm().call(a, c);
					assertEquals(Math.abs(a.v), c.v);
				}
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.pow().call(a, b, c);
					assertEquals(1, c.v());
				}
				else if (j >= 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.power().call(j, a, c);
					assertEquals(1, c.v());
				}
				else if (j > 0) {
					c.set(a);
					G.INT10.pred().call(c, c);
					G.INT10.pred().call(c, c);
					G.INT10.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(a, c);
				expected = (a.v == -512) ? 511 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.succ().call(a, c);
				expected = (a.v == 511) ? -512 : a.v+1;
				assertEquals(expected, c.v);
				
				G.INT10.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int) G.INT10.signum().call(a));
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT10.pred().call(c, c);
				G.INT10.pred().call(c, c);
				G.INT10.zero().call(c);
				assertEquals(0, c.v);
			}
			if (i != -512) {
				G.INT10.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt10Member num = G.INT10.construct();
		for (int offset : new int[] {0, 1024, 2048, 3072, -1024, -2048, -3072}) {
			for (int i = -512; i < 512; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
