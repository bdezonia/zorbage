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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform3 {

	@Test
	public void test1() {
		
		BooleanMember tmp = G.BOOL.construct();
		
		IndexedDataSource<BooleanMember> a,b,c;
		
		a = Storage.allocate(G.BOOL.construct(), new boolean[] {false, false, true, true});
		b = Storage.allocate(G.BOOL.construct(), new boolean[] {false, true, false, true});
		c = Storage.allocate(G.BOOL.construct(), new boolean[4]);
		
		Transform3.compute(G.BOOL, G.BOOL, G.BOOL, G.BOOL.logicalAnd(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(false, tmp.v());
		c.get(1, tmp);
		assertEquals(false, tmp.v());
		c.get(2, tmp);
		assertEquals(false, tmp.v());
		c.get(3, tmp);
		assertEquals(true, tmp.v());
		
		a = Storage.allocate(G.BOOL.construct(), new boolean[] {false, false, true, true});
		b = Storage.allocate(G.BOOL.construct(), new boolean[] {false, true, false, true});
		c = Storage.allocate(G.BOOL.construct(), new boolean[4]);

		Transform3.compute(G.BOOL, G.BOOL, G.BOOL, G.BOOL.logicalOr(), a, b, c);
		
		c.get(0, tmp);
		assertEquals(false, tmp.v());
		c.get(1, tmp);
		assertEquals(true, tmp.v());
		c.get(2, tmp);
		assertEquals(true, tmp.v());
		c.get(3, tmp);
		assertEquals(true, tmp.v());
	}

	@Test
	public void test2() {
		
		UnsignedInt1Member tmp = G.UINT1.construct();
		
		IndexedDataSource<UnsignedInt1Member> a,b,c;
		
		a = ArrayStorage.allocate(tmp, 4);
		b = ArrayStorage.allocate(tmp, 4);
		c = ArrayStorage.allocate(tmp, 4);
		
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
		
		a = ArrayStorage.allocate(tmp, 4);
		b = ArrayStorage.allocate(tmp, 4);
		c = ArrayStorage.allocate(tmp, 4);
		
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
	}
	
	@Test
	public void test3() {
		
		SignedInt32Member tmp = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a,b,c;

		a = Storage.allocate(G.INT32.construct(), new int[] {0, 1, 2, 3});
		b = Storage.allocate(G.INT32.construct(), new int[] {-3, -1, 1, 3});
		c = Storage.allocate(G.INT32.construct(), new int[4]);

		Transform3.compute(G.INT32, G.INT32, G.INT32, G.INT32.add(), a, b, c);

		c.get(0, tmp);
		assertEquals(-3, tmp.v());
		c.get(1, tmp);
		assertEquals(0, tmp.v());
		c.get(2, tmp);
		assertEquals(3, tmp.v());
		c.get(3, tmp);
		assertEquals(6, tmp.v());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {0, 1, 2, 3});
		b = Storage.allocate(G.INT32.construct(), new int[] {-3, -1, 1, 3});
		c = Storage.allocate(G.INT32.construct(), new int[4]);

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

		a = Storage.allocate(G.DBL.construct(), new double[] {1, 2, 3, 5});
		b = Storage.allocate(G.DBL.construct(), new double[] {7, 6, 5, 4});
		c = Storage.allocate(G.DBL.construct(), new double[4]);

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
