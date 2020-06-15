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
package nom.bdezonia.zorbage.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.NdData;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestPipedDataSource {

	@Test
	public void test() {
		SignedInt32Member value = G.INT32.construct("44");
		long[] dims = new long[] {2,3,4};
		IntegerIndex idx = new IntegerIndex(3);
		idx.set(0,1);
		idx.set(1,1);
		idx.set(2,1);
		IndexedDataSource<SignedInt32Member> data = ArrayStorage.allocateInts(new int[24]);
		DimensionedDataSource<SignedInt32Member> md = new NdData<>(dims, data);
		IndexedDataSource<SignedInt32Member> p = md.piped(1, idx);
		assertEquals(3, p.size());
		for (long i = 0; i < p.size(); i++) {
			p.set(i, value);
		}
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 0);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 1);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 1);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 2);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 0);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 0);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(44, value.v());
		idx.set(0, 0);
		idx.set(1, 1);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 1);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(44, value.v());
		idx.set(0, 0);
		idx.set(1, 2);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 1);
		md.get(idx, value);
		assertEquals(44, value.v());
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 0);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 1);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 1);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 2);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 2);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 0);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 1);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 1);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 0);
		idx.set(1, 2);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 3);
		md.get(idx, value);
		assertEquals(0, value.v());
	}

}
