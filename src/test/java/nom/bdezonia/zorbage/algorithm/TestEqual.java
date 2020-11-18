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

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestEqual {
	
	@Test
	public void test() {
		
		IndexedDataSource<SignedInt16Member> a3 = Storage.allocate(G.INT16.construct(), 
				new short[] {1,2,3});
		IndexedDataSource<SignedInt16Member> a4 = Storage.allocate(G.INT16.construct(), 
				new short[] {1,2,3,4});
		IndexedDataSource<SignedInt16Member> a5 = Storage.allocate(G.INT16.construct(), 
				new short[] {1,2,3,4,5});
		IndexedDataSource<SignedInt16Member> b4 = Storage.allocate(G.INT16.construct(), 
				new short[] {1,2,3,4});
		assertEquals(false, Equal.compute(G.INT16, a3, a4));
		assertEquals(false, Equal.compute(G.INT16, a3, a5));
		assertEquals(false, Equal.compute(G.INT16, a4, a5));
		assertEquals(true, Equal.compute(G.INT16, a4, b4));
	}
}
