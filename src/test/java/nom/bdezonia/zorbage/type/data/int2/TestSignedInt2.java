/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBit;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt8;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt2 {

	private int v(int val) {
		if (val >= -2) {
			val += 2;
			return ((val % 4) - 2);
		}
		else {
			// val < -2
			switch ((val % 4) + 2) {
			case -1:
				return 1;
			case 2:
				return 0;
			case 1:
				return -1;
			default: // case 0:
				return -2;
			}

		}
	}
	
	private void testStorageMethods(IndexedDataSource<?, SignedInt2Member> data) {
		
		SignedInt2Member in = new SignedInt2Member();
		SignedInt2Member out = new SignedInt2Member();
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
		testStorageMethods(new ArrayStorageBit<SignedInt2Member>(12000, G.INT2.construct()));
		testStorageMethods(new ArrayStorageSignedInt8<SignedInt2Member>(12000, G.INT2.construct()));
	}
	
	@Test
	public void testMathematicalMethods() {
		SignedInt2Member a = G.INT2.construct();
		SignedInt2Member b = G.INT2.construct();
		SignedInt2Member c = G.INT2.construct();
		for (int i = -2; i < 2; i++) {
			a.setV(i);
			
			for (int j = -2; j < 2; j++) {
				b.setV(j);

//				System.out.println(i+" op "+j);

				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.abs().call(a, c);
				assertEquals(a.v == -2 ? -2 : Math.abs(a.v),c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.add().call(a,b,c);
				assertEquals(v(i+j), (int)c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.assign().call(a, c);
				assertEquals(a.v,c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.bitAnd().call(a, b, c);
				assertEquals(v(i & j), c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.bitOr().call(a, b, c);
				assertEquals(v(i | j), c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				if (j >= 0) {
					G.INT2.bitShiftLeft().call(j, a, c);
					assertEquals(v(i << (j%2)), c.v);
				}
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				if (j >= 0) {
					G.INT2.bitShiftRight().call(j, a, c);
					assertEquals(v(i >> j), c.v);
				}
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				if (j >= 0) {
					G.INT2.bitShiftRightFillZero().call(j, a, c);
					assertEquals(v(i >>> j), c.v);
				}
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.bitXor().call(a, b, c);
				assertEquals(v(i ^ j), c.v);
				
				int expected;
				if (i > j) expected = 1;
				else if (i < j) expected = -1;
				else expected = 0;
				assertEquals(expected,(int)G.INT2.compare().call(a, b));
				
				SignedInt2Member v = G.INT2.construct();
				assertEquals(0, v.v);
				
				v = G.INT2.construct(""+(i+j));
				assertEquals(v(i+j), v.v);
				
				v = G.INT2.construct(a);
				assertEquals(i,v.v);
				
				if (j != 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					if (i != -2 || j != -1) {
						G.INT2.div().call(a, b, c);
						assertEquals((i / j), c.v);
					}
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals(((i&1) == 0), G.INT2.isEven().call(a));
				assertEquals(((i&1) == 1), G.INT2.isOdd().call(a));
				
				assertEquals(i==j,G.INT2.isEqual().call(a, b));
				assertEquals(i!=j,G.INT2.isNotEqual().call(a, b));
				assertEquals(i<j,G.INT2.isLess().call(a, b));
				assertEquals(i<=j,G.INT2.isLessEqual().call(a, b));
				assertEquals(i>j,G.INT2.isGreater().call(a, b));
				assertEquals(i>=j,G.INT2.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.max().call(a, b, c);
				assertEquals(Math.max(i, j), c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.min().call(a, b, c);
				assertEquals(Math.min(i, j), c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.maxBound().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.minBound().call(c);
				assertEquals(-2, c.v);
				
				if (j != 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					G.INT2.mod().call(a, b, c);
					assertEquals(i%j, c.v);
				}
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.multiply().call(a,b,c);
				assertEquals(v(i*j),c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.norm().call(a, c);
				assertEquals(i == -2 ? -2 : Math.abs(a.v), c.v);
				
				double p;
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					try {
						G.INT2.pow().call(a, b, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j >= 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					G.INT2.pow().call(a, b, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				if (i == 0 && j == 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					try {
						G.INT2.power().call(j, a, c);
						fail();
					} catch (IllegalArgumentException e) {
						assertTrue(true);
					}
				}
				else if (j > 0) {
					c.set(a);
					G.INT2.pred().call(c, c);
					G.INT2.pred().call(c, c);
					G.INT2.power().call(j, a, c);
					p = Math.pow(i, j);
					if (0 <= p && p <= Integer.MAX_VALUE)
						assertEquals(v((int)p), c.v);
				}
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(a, c);
				expected = (a.v == -2) ? 1 : a.v-1;
				assertEquals(expected, c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.succ().call(a, c);
				expected = (a.v == 1) ? -2 : a.v+1;
				assertEquals(expected, c.v);
				
				// TODO: random()
				
				if (i < 0) expected = -1;
				else if (i > 0) expected = 1;
				else expected = 0;
				assertEquals(expected, (int)G.INT2.signum().call(a));
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.subtract().call(a, b, c);
				assertEquals(v(i-j), c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.unity().call(c);
				assertEquals(1, c.v);
				
				c.set(a);
				G.INT2.pred().call(c, c);
				G.INT2.pred().call(c, c);
				G.INT2.zero().call(c);
				assertEquals(0, c.v);
			}
		}
	}
	
	@Test
	public void rollover() {
		SignedInt2Member num = G.INT2.construct();
		num.setV(-10); assertEquals(-2, num.v);
		num.setV(-9); assertEquals(-1, num.v);
		num.setV(-8); assertEquals(0, num.v);
		num.setV(-7); assertEquals(1, num.v);
		num.setV(-6); assertEquals(-2, num.v);
		num.setV(-5); assertEquals(-1, num.v);
		num.setV(-4); assertEquals(0, num.v);
		num.setV(-3); assertEquals(1, num.v);
		num.setV(-2); assertEquals(-2, num.v);
		num.setV(-1); assertEquals(-1, num.v);
		num.setV(0); assertEquals(0, num.v);
		num.setV(1); assertEquals(1, num.v);
		num.setV(2); assertEquals(-2, num.v);
		num.setV(3); assertEquals(-1, num.v);
		num.setV(4); assertEquals(0, num.v);
		num.setV(5); assertEquals(1, num.v);
		num.setV(6); assertEquals(-2, num.v);
		num.setV(7); assertEquals(-1, num.v);
		num.setV(8); assertEquals(0, num.v);
		num.setV(9); assertEquals(1, num.v);
		num.setV(10); assertEquals(-2, num.v);
	}
}
