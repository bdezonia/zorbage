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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform {

	@Test
	public void test1() {
		
		BooleanMember tmp = G.BOOL.construct();
		
		IndexedDataSource<BooleanMember> a,b,c;
		
		a = ArrayStorage.allocateBooleans(new boolean[] {false, false, true, true});
		b = ArrayStorage.allocateBooleans(new boolean[] {false, true, false, true});
		c = ArrayStorage.allocateBooleans(new boolean[4]);
		
		Transform3.compute(G.BOOL, G.BOOL, G.BOOL, G.BOOL.logicalAnd(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(false, tmp.v());
		c.get(1, tmp);
		assertEquals(false, tmp.v());
		c.get(2, tmp);
		assertEquals(false, tmp.v());
		c.get(3, tmp);
		assertEquals(true, tmp.v());
		
		a = ArrayStorage.allocateBooleans(new boolean[] {false, false, true, true});
		b = ArrayStorage.allocateBooleans(new boolean[] {false, true, false, true});
		c = ArrayStorage.allocateBooleans(new boolean[4]);

		Transform3.compute(G.BOOL, G.BOOL, G.BOOL, G.BOOL.logicalOr(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(false, tmp.v());
		c.get(1, tmp);
		assertEquals(true, tmp.v());
		c.get(2, tmp);
		assertEquals(true, tmp.v());
		c.get(3, tmp);
		assertEquals(true, tmp.v());
		
		a = ArrayStorage.allocateBooleans(new boolean[] {false, true});
		c = ArrayStorage.allocateBooleans(new boolean[2]);

		Transform2.compute(G.BOOL, G.BOOL, G.BOOL.logicalNot(), a, c);
		
		c.get(0, tmp);
		assertEquals(true, tmp.v());
		c.get(1, tmp);
		assertEquals(false, tmp.v());
	}

	@Test
	public void test2() {
		
		UnsignedInt1Member tmp = G.UINT1.construct();
		
		IndexedDataSource<UnsignedInt1Member> a,b,c;
		
		a = ArrayStorage.allocate(4, tmp);
		b = ArrayStorage.allocate(4, tmp);
		c = ArrayStorage.allocate(4, tmp);
		
		tmp.setV(0);
		a.set(0, tmp);
		a.set(1, tmp);
		b.set(0, tmp);
		b.set(2, tmp);
		tmp.setV(1);
		a.set(2, tmp);
		a.set(3, tmp);
		b.set(1, tmp);
		b.set(3, tmp);
		
		Transform3.compute(G.UINT1, G.UINT1, G.UINT1, G.UINT1.logicalAnd(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(0, tmp.v());
		c.get(1, tmp);
		assertEquals(0, tmp.v());
		c.get(2, tmp);
		assertEquals(0, tmp.v());
		c.get(3, tmp);
		assertEquals(1, tmp.v());
		
		a = ArrayStorage.allocate(4, tmp);
		b = ArrayStorage.allocate(4, tmp);
		c = ArrayStorage.allocate(4, tmp);
		
		tmp.setV(0);
		a.set(0, tmp);
		a.set(1, tmp);
		b.set(0, tmp);
		b.set(2, tmp);
		tmp.setV(1);
		a.set(2, tmp);
		a.set(3, tmp);
		b.set(1, tmp);
		b.set(3, tmp);

		Transform3.compute(G.UINT1, G.UINT1, G.UINT1, G.UINT1.logicalOr(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(0, tmp.v());
		c.get(1, tmp);
		assertEquals(1, tmp.v());
		c.get(2, tmp);
		assertEquals(1, tmp.v());
		c.get(3, tmp);
		assertEquals(1, tmp.v());
		
		a = ArrayStorage.allocate(2, tmp);
		c = ArrayStorage.allocate(2, tmp);

		tmp.setV(0);
		a.set(0, tmp);
		tmp.setV(1);
		a.set(1, tmp);

		Transform2.compute(G.UINT1, G.UINT1, G.UINT1.logicalNot(), a, c);
		
		c.get(0, tmp);
		assertEquals(1, tmp.v());
		c.get(1, tmp);
		assertEquals(0, tmp.v());
	}
	
	@Test
	public void test3() {
		
		SignedInt32Member tmp = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a,b,c;

		a = ArrayStorage.allocateInts(new int[] {0, 1, 2, 3});
		b = ArrayStorage.allocateInts(new int[] {-3, -1, 1, 3});
		c = ArrayStorage.allocateInts(new int[4]);

		Transform3.compute(G.INT32, G.INT32, G.INT32, G.INT32.add(), a, b, c);

		c.get(0, tmp);
		assertEquals(-3, tmp.v());
		c.get(1, tmp);
		assertEquals(0, tmp.v());
		c.get(2, tmp);
		assertEquals(3, tmp.v());
		c.get(3, tmp);
		assertEquals(6, tmp.v());
		
		a = ArrayStorage.allocateInts(new int[] {0, 1, 2, 3});
		b = ArrayStorage.allocateInts(new int[] {-3, -1, 1, 3});
		c = ArrayStorage.allocateInts(new int[4]);

		Transform3.compute(G.INT32, G.INT32, G.INT32, G.INT32.multiply(), a, b, c);

		c.get(0, tmp);
		assertEquals(0, tmp.v());
		c.get(1, tmp);
		assertEquals(-1, tmp.v());
		c.get(2, tmp);
		assertEquals(2, tmp.v());
		c.get(3, tmp);
		assertEquals(9, tmp.v());
	}
	
	@Test
	public void test4() {
		
		Float64Member tmp = G.DBL.construct();
		
		IndexedDataSource<Float64Member> a,b,c;

		a = ArrayStorage.allocateDoubles(new double[] {1, 2, 3, 5});
		b = ArrayStorage.allocateDoubles(new double[] {7, 6, 5, 4});
		c = ArrayStorage.allocateDoubles(new double[4]);

		Transform3.compute(G.DBL, G.DBL, G.DBL, G.DBL.pow(), a, b, c);

		c.get(0, tmp);
		assertEquals(1, tmp.v(), 0.0000001);
		c.get(1, tmp);
		assertEquals(64, tmp.v(), 0.0000001);
		c.get(2, tmp);
		assertEquals(243, tmp.v(), 0.0000001);
		c.get(3, tmp);
		assertEquals(625, tmp.v(), 0.0000001);
	}
}
