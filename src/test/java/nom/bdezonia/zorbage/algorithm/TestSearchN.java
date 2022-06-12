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

import nom.bdezonia.zorbage.predicate.Equal;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSearchN {

	@Test
	public void test1() {
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6});
		assertEquals(16, SearchN.compute(G.INT32, new Equal<>(G.INT32), 4, new SignedInt32Member(7), a));
		assertEquals(6, SearchN.compute(G.INT32, new Equal<>(G.INT32), 4, new SignedInt32Member(4), a));
	}

	@Test
	public void test2() {
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6});
		assertEquals(16, SearchN.compute(G.INT32, 3, new SignedInt32Member(-1), a));
		assertEquals(3, SearchN.compute(G.INT32, 3, new SignedInt32Member(3), a));
	}
}
