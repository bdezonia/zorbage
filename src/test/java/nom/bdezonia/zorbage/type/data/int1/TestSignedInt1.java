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
package nom.bdezonia.zorbage.type.data.int1;

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
public class TestSignedInt1 {
	
	private static int v(int in) {
		if ((in & 1) == 1)
			return -1;
		return 0;
	}
	
	private void testStorageMethods(IndexedDataSource<?, SignedInt1Member> data) {
		
		SignedInt1Member in = new SignedInt1Member();
		SignedInt1Member out = new SignedInt1Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt1Member>(6000, G.INT1.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt1Member>(6000, G.INT1.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt1Member a = G.INT1.construct();
		SignedInt1Member b = G.INT1.construct();
		SignedInt1Member c = G.INT1.construct();
		for (int i = -1; i < 1; i++) {
			a.setV(i);
			
			for (int j = -1; j < 1; j++) {
				b.setV(j);

				if (i != -1) {
					c.setV(i+1);
					G.INT1.abs().call(a, c);
					assertEquals(a.v,c.v);
				}
				
				c.setV(i+1);
				G.INT1.add().call(a,b,c);
				assertEquals(((i+j) & 0x1) == 1 ? -1 : 0, (int)c.v);
				
				c.setV(i+1);
				G.INT1.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.setV(i+1);
				G.INT1.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.setV(i+1);
				G.INT1.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				if (j > -1) {
					c.setV(i+1);
					G.INT1.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << j), c.v);
				}
				
				if (j > -1) {
					c.setV(i+1);
					G.INT1.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
			
				if (j > -1) {
					c.setV(i+1);
					G.INT1.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.setV(i+1);
				G.INT1.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT1.compare().call(a, b));
				
				SignedInt1Member v = G.INT1.construct();
				assertEquals(0, v.v);
				
				v = G.INT1.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT1.construct(a);
				assertEquals(i,v.v);
				
				assertEquals(i == 0, G.INT1.isEven().call(a));
				assertEquals(i == -1, G.INT1.isOdd().call(a));
				
				assertEquals(i==j,G.INT1.isEqual().call(a, b));
				assertEquals(i!=j,G.INT1.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT1.isLess().call(a, b));
				assertEquals(i<=j,G.INT1.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT1.isGreater().call(a, b));
				assertEquals(i>=j,G.INT1.isGreaterEqual().call(a, b));
				
				c.setV(i+1);
				G.INT1.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.setV(i+1);
				G.INT1.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.setV(i+1);
				G.INT1.maxBound().call(c);
				assertEquals(0, c.v);
				
				c.setV(i+1);
				G.INT1.minBound().call(c);
				assertEquals(-1, c.v);
				
				c.setV(i+1);
				G.INT1.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				try {
					G.INT1.power().call(j, a, c);
					fail();
				} catch (IllegalArgumentException e) {
					assertTrue(true);
				}
				
				c.setV(i+1);
				G.INT1.pred().call(a, c);
				expected = (a.v == 0) ? -1 : 0;
				assertEquals(expected, c.v);
				
				c.setV(i+1);
				G.INT1.succ().call(a, c);
				expected = (a.v == -1) ? 0 : -1;
				assertEquals(expected, c.v);
				
				// TODO: how to test?
				G.INT1.random().call(c);
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT1.signum().call(a));
				
				c.setV(i+1);
				G.INT1.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.setV(-1);
				G.INT1.zero().call(c);
				assertEquals(0, c.v);
				
			}
			if (i != -1) {
				G.INT1.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt1Member num = G.INT1.construct();
		for (int offset : new int[] {0, 2, 4, 6, 8, 10, -2, -4, -6, -8, -10}) {
			for (int i = -1; i < 1; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v);
			}
		}
	}
}
