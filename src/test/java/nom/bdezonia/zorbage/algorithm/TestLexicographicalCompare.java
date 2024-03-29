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

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLexicographicalCompare {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(G.INT32.construct(), 
				new int[] {1});
		IndexedDataSource<SignedInt32Member> c = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2});
		IndexedDataSource<SignedInt32Member> d = Storage.allocate(G.INT32.construct(), 
				new int[] {1,3});
		IndexedDataSource<SignedInt32Member> e = Storage.allocate(G.INT32.construct(), 
				new int[] {2,1});
		IndexedDataSource<SignedInt32Member> f = Storage.allocate(G.INT32.construct(), 
				new int[] {2,2});
		IndexedDataSource<SignedInt32Member> g = Storage.allocate(G.INT32.construct(), 
				new int[] {3,9,9,1,2});
		IndexedDataSource<SignedInt32Member> h = Storage.allocate(G.INT32.construct(), 
				new int[] {3,9,9,2,1});
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, a, a));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, b));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, c));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, d));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, e));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, a, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, b, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, b, b));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, c));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, d));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, e));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, b, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, c, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, c, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, c, c));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, c, d));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, c, e));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, c, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, c, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, c, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, d, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, d, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, d, c));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, d, d));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, d, e));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, d, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, d, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, d, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, e, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, e, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, e, c));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, e, d));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, e, e));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, e, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, e, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, e, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, c));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, d));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, e));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, f, f));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, f, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, f, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, c));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, d));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, e));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, f));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, g, g));
		assertEquals(true, LexicographicalCompare.compute(G.INT32, g, h));
		
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, a));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, b));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, c));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, d));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, e));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, f));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, g));
		assertEquals(false, LexicographicalCompare.compute(G.INT32, h, h));
	}
}
