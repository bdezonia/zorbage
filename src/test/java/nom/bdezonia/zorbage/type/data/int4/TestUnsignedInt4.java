/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.int4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt8;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt4 {
	
	private void testStorageMethods(IndexedDataSource<?, UnsignedInt4Member> data) {
		
		UnsignedInt4Member in = new UnsignedInt4Member();
		UnsignedInt4Member out = new UnsignedInt4Member();
		in.setV(0);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(0, out.v);
		}
		for (long i = 0; i < data.size(); i++) {
			in.setV((int)i);
			data.set(i, in);
			out.setV(in.v-1);;
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
			out.setV(in.v-1);;
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
			out.setV(in.v-1);;
			data.get(i, out);
			assertEquals(in.v, out.v);
		}
	}

	@Test
	public void testStorage() {
		testStorageMethods(new ArrayStorageBit<UnsignedInt4Member>(6000, G.UINT4.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<UnsignedInt4Member>(6000, G.UINT4.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		UnsignedInt4Member a = G.UINT4.construct();
		UnsignedInt4Member b = G.UINT4.construct();
		UnsignedInt4Member c = G.UINT4.construct();
		for (int i = 0; i < 16; i++) {
			a.setV(i);
			
			for (int j = 0; j < 16; j++) {
				b.setV(j);

				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.abs().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.add().call(a,b,c);
				assertEquals((i+j) & 0xf, (int)c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitAnd().call(a, b, c);
				assertEquals((i & j)&0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitOr().call(a, b, c);
				assertEquals((i | j)&0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitShiftLeft().call(j, a, c);
				assertEquals((i << (j%4))&0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitShiftRight().call(j, a, c);
				assertEquals((i >> j)&0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.bitXor().call(a, b, c);
				assertEquals((i ^ j)&0xf, c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.UINT4.compare().call(a, b));
				
				UnsignedInt4Member v = G.UINT4.construct();
				assertEquals(0, v.v);
				
				v = G.UINT4.construct(""+((i+j) & 0xf));
				assertEquals(((i+j) & 0xf), v.v);
				
				v = G.UINT4.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					G.UINT4.div().call(a, b, c);
					assertEquals((i / j), c.v);
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((i&1) == 0, G.UINT4.isEven().call(a));
				assertEquals((i&1) == 1, G.UINT4.isOdd().call(a));
				
				assertEquals(i==j,G.UINT4.isEqual().call(a, b));
				assertEquals(i!=j,G.UINT4.isNotEqual().call(a, b));
				assertEquals(i<j,G.UINT4.isLess().call(a, b));
				assertEquals(i<=j,G.UINT4.isLessEqual().call(a, b));
				assertEquals(i>j,G.UINT4.isGreater().call(a, b));
				assertEquals(i>=j,G.UINT4.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.maxBound().call(c);
				assertEquals(0xf, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.minBound().call(c);
				assertEquals(0, c.v);
				
				if (j != 0) {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					G.UINT4.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.multiply().call(a,b,c);
				assertEquals((i*j) & 0xf,c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.norm().call(a, c);
				assertEquals(i, c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					try {
						G.UINT4.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					G.UINT4.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 0xf, c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					try {
						G.UINT4.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else {
					c.set(a);
					G.UINT4.pred().call(c, c);
					G.UINT4.pred().call(c, c);
					G.UINT4.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Math.pow(2, 51))
						assertEquals(((long)p) & 0xf, c.v);
				}
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(a, c);
				expected = (a.v == 0) ? 0xf : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.succ().call(a, c);
				expected = (a.v == 0xf) ? 0 : a.v+1;
				assertEquals(expected, c.v);
				
				// TODO: random()
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.UINT4.signum().call(a));
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.subtract().call(a, b, c);
				assertEquals((i-j) & 0x0f, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.UINT4.pred().call(c, c);
				G.UINT4.pred().call(c, c);
				G.UINT4.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		UnsignedInt4Member num = G.UINT4.construct();
		for (int offset : new int[] {0, 16, 32, 48, 64, 80, -16, -32, -48,-64, -80}) {
			for (int i = 0; i < 16; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
