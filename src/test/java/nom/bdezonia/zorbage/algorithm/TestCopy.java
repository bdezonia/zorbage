/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCopy {
	
	@Test
	public void test() {
		IndexedDataSource<?, SignedInt8Member> list1 = ArrayStorage.allocateBytes(
				new byte[] {0,1,2,3,0,0,0,0});
		IndexedDataSource<?, SignedInt8Member> list2 = ArrayStorage.allocateBytes(
				new byte[] {0,0,0,0,0,0,0,0});
		Copy.compute(G.INT8, list1, list2);
		SignedInt8Member value1 = G.INT8.construct();
		SignedInt8Member value2 = G.INT8.construct();
		for (int i = 0; i < list1.size(); i++) {
			list1.get(i, value1);
			list2.get(i, value2);
			assertEquals(value1.v(), value2.v());
		}
		IndexedDataSource<?, SignedInt8Member> list3 = ArrayStorage.allocateBytes(
				new byte[] {0,0,0,0,0,0,0,0});
		Copy.compute(G.INT8, 1, 0, 4, list1, list3);
		list3.get(0, value1);
		assertEquals(1, value1.v());
		list3.get(1, value1);
		assertEquals(2, value1.v());
		list3.get(2, value1);
		assertEquals(3, value1.v());
		list3.get(3, value1);
		assertEquals(0, value1.v());
	}
}
