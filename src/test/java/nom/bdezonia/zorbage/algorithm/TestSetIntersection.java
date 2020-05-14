/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSetIntersection {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = ArrayStorage.allocateInts(new int[] {});
		b = ArrayStorage.allocateInts(new int[] {});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = ArrayStorage.allocateInts(new int[] {1});
		b = ArrayStorage.allocateInts(new int[] {});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());

		a = ArrayStorage.allocateInts(new int[] {1,1});
		b = ArrayStorage.allocateInts(new int[] {});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = ArrayStorage.allocateInts(new int[] {1,2});
		b = ArrayStorage.allocateInts(new int[] {});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = ArrayStorage.allocateInts(new int[] {});
		b = ArrayStorage.allocateInts(new int[] {1});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = ArrayStorage.allocateInts(new int[] {});
		b = ArrayStorage.allocateInts(new int[] {1,1});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
		
		a = ArrayStorage.allocateInts(new int[] {});
		b = ArrayStorage.allocateInts(new int[] {1,2});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(0, res.size());
	}

	@Test
	public void test2() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a;
		IndexedDataSource<SignedInt32Member> b;
		IndexedDataSource<SignedInt32Member> res;
		
		a = ArrayStorage.allocateInts(new int[] {1,2,3,4});
		b = ArrayStorage.allocateInts(new int[] {2,5});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(2, value.v());
		
		a = ArrayStorage.allocateInts(new int[] {2,5});
		b = ArrayStorage.allocateInts(new int[] {1,2,3,4});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(2, value.v());
		
		a = ArrayStorage.allocateInts(new int[] {1,1,1,2,2,2,3,3,3,4,4,4});
		b = ArrayStorage.allocateInts(new int[] {2,5,2,5,2,5,2,5,2});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(2, value.v());
		
		a = ArrayStorage.allocateInts(new int[] {2,5,2,5,2,5,2,5,2});
		b = ArrayStorage.allocateInts(new int[] {1,1,1,2,2,2,3,3,3,4,4,4});
		res = SetIntersection.compute(G.INT32, a, b);
		assertEquals(1, res.size());
		res.get(0, value);
		assertEquals(2, value.v());
	}
}
