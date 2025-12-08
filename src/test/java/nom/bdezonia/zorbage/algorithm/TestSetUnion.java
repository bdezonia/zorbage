/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSetUnion {

	@Test
	public void test1() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = Storage.allocate(G.INT32.construct(), new int[] {});
		b = Storage.allocate(G.INT32.construct(), new int[] {});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {1});
		b = Storage.allocate(G.INT32.construct(), new int[] {});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(1, value.v());

		a = Storage.allocate(G.INT32.construct(), new int[] {1,1});
		b = Storage.allocate(G.INT32.construct(), new int[] {});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {1,2});
		b = Storage.allocate(G.INT32.construct(), new int[] {});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(2, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		res.get(1, value);
		assertEquals(2, value.v());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {});
		b = Storage.allocate(G.INT32.construct(), new int[] {1});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {});
		b = Storage.allocate(G.INT32.construct(), new int[] {1,1});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		
		a = Storage.allocate(G.INT32.construct(), new int[] {});
		b = Storage.allocate(G.INT32.construct(), new int[] {1,2});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(2, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		res.get(1, value);
		assertEquals(2, value.v());
	}

	@Test
	public void test2() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4});
		b = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(4, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		res.get(1, value);
		assertEquals(2, value.v());
		res.get(2, value);
		assertEquals(3, value.v());
		res.get(3, value);
		assertEquals(4, value.v());
	}

	@Test
	public void test3() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = Storage.allocate(G.INT32.construct(), new int[] {1,2,5,6});
		b = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(6, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		res.get(1, value);
		assertEquals(2, value.v());
		res.get(2, value);
		assertEquals(3, value.v());
		res.get(3, value);
		assertEquals(4, value.v());
		res.get(4, value);
		assertEquals(5, value.v());
		res.get(5, value);
		assertEquals(6, value.v());
	}

	@Test
	public void test4() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = Storage.allocate(G.INT32.construct(), new int[] {1,7,9,3,3,1,5});
		b = Storage.allocate(G.INT32.construct(), new int[] {8,7,6,5,4,3,2,1});
		res = SetUnion.compute(G.INT32, a, b);
		assertEquals(9, res.size());
		res.get(0, value);
		assertEquals(1, value.v());
		res.get(1, value);
		assertEquals(2, value.v());
		res.get(2, value);
		assertEquals(3, value.v());
		res.get(3, value);
		assertEquals(4, value.v());
		res.get(4, value);
		assertEquals(5, value.v());
		res.get(5, value);
		assertEquals(6, value.v());
		res.get(6, value);
		assertEquals(7, value.v());
		res.get(7, value);
		assertEquals(8, value.v());
		res.get(8, value);
		assertEquals(9, value.v());
	}
}
