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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMerge {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2,2,2,3,5,8,19});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(G.INT32.construct(), 
				new int[] {1,3,3,4,4,5,5,13});
		assertTrue(IsSorted.compute(G.INT32, a));
		assertTrue(IsSorted.compute(G.INT32, b));
		
		IndexedDataSource<SignedInt32Member> c = Storage.allocate(G.INT32.construct(), 
				new int[(int)(a.size()+b.size())]);
		Merge.compute(G.INT32, a, b, c);
		assertTrue(IsSorted.compute(G.INT32, c));
		
		SignedInt32Member value = G.INT32.construct();
		c.get(0, value);
		assertEquals(1, value.v());
		c.get(c.size()-1, value);
		assertEquals(19, value.v());
	}
	@Test
	public void test2() {
		
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2,2,2,3,5,8,19});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(G.INT32.construct(), 
				new int[] {});
		assertTrue(IsSorted.compute(G.INT32, a));
		assertTrue(IsSorted.compute(G.INT32, b));
		
		IndexedDataSource<SignedInt32Member> c = Storage.allocate(G.INT32.construct(), 
				new int[(int)(a.size()+b.size())]);
		Merge.compute(G.INT32, a, b, c);
		assertTrue(IsSorted.compute(G.INT32, c));
		
		SignedInt32Member value = G.INT32.construct();
		c.get(0, value);
		assertEquals(1, value.v());
		c.get(c.size()-1, value);
		assertEquals(19, value.v());
	}
	@Test
	public void test3() {
		
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2,2,2,3,5,8,19});
		assertTrue(IsSorted.compute(G.INT32, a));
		assertTrue(IsSorted.compute(G.INT32, b));
		
		IndexedDataSource<SignedInt32Member> c = Storage.allocate(G.INT32.construct(), 
				new int[(int)(a.size()+b.size())]);
		Merge.compute(G.INT32, a, b, c);
		assertTrue(IsSorted.compute(G.INT32, c));
		
		SignedInt32Member value = G.INT32.construct();
		c.get(0, value);
		assertEquals(1, value.v());
		c.get(c.size()-1, value);
		assertEquals(19, value.v());
	}
}
