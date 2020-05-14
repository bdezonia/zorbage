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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class TestIncludes {

	@Test
	public void test1() {
		IndexedDataSource<SignedInt32Member> list = ArrayStorage.allocateInts(
				new int[] {1,2,2,3,4,5,6,7,7,8,9,10});
		IndexedDataSource<SignedInt32Member> sublist = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5});
		assertTrue(Includes.compute(G.INT32, list, list));
		assertTrue(Includes.compute(G.INT32, sublist, sublist));
		assertTrue(Includes.compute(G.INT32, list, sublist));
		assertFalse(Includes.compute(G.INT32, sublist, list));
	}

	@Test
	public void test2() {
		IndexedDataSource<SignedInt32Member> list = ArrayStorage.allocateInts(
				new int[] {0,0,2,2,4,4,6,6});
		IndexedDataSource<SignedInt32Member> sublist = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5});
		assertTrue(Includes.compute(G.INT32, list, list));
		assertTrue(Includes.compute(G.INT32, sublist, sublist));
		assertFalse(Includes.compute(G.INT32, list, sublist));
		assertFalse(Includes.compute(G.INT32, sublist, list));
	}

	@Test
	public void test3() {
		IndexedDataSource<SignedInt32Member> list = ArrayStorage.allocateInts(
				new int[] {1,2,2,3,4,5,6,7,7,8,9,10});
		IndexedDataSource<SignedInt32Member> sublist = ArrayStorage.allocateInts(
				new int[] {5,6,7,7});
		assertTrue(Includes.compute(G.INT32, list, list));
		assertTrue(Includes.compute(G.INT32, sublist, sublist));
		assertTrue(Includes.compute(G.INT32, list, sublist));
		assertFalse(Includes.compute(G.INT32, sublist, list));
	}
}
