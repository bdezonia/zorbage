/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestDataConvert {

	@Test
	public void test() {
		final int size = 100;
		SignedInt32Member tmpI = new SignedInt32Member();
		OctonionFloat64Member tmpO = new OctonionFloat64Member();
		IndexedDataSource<?,SignedInt32Member> in = ArrayStorage.allocate(size, tmpI);
		IndexedDataSource<?,OctonionFloat64Member> out = ArrayStorage.allocate(size, tmpO);
		assertEquals(size, in.size());
		assertEquals(size, out.size());
		for (int i = 0; i < size; i++) {
			tmpI.setV(i);
			in.set(i, tmpI);
		}
		DataConvert.compute(G.INT32, G.ODBL, in, out);
		for (int i = 0; i < size; i++) {
			out.get(i, tmpO);
			assertEquals(i, tmpO.r(), 0);
			assertEquals(0, tmpO.i(), 0);
			assertEquals(0, tmpO.j(), 0);
			assertEquals(0, tmpO.k(), 0);
			assertEquals(0, tmpO.l(), 0);
			assertEquals(0, tmpO.i0(), 0);
			assertEquals(0, tmpO.j0(), 0);
			assertEquals(0, tmpO.k0(), 0);
		}
	}
}
