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
package nom.bdezonia.zorbage.multidim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

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
		IndexedDataSource<?,SignedInt32Member> data = ArrayStorage.allocateInts(new int[24]);
		MultiDimDataSource<?,SignedInt32Member> md = new MultiDimDataSource<>(dims, data);
		IndexedDataSource<?,SignedInt32Member> p = md.piped(1, new long[] {1,1,1});
		assertEquals(3, p.size());
		for (long i = 0; i < p.size(); i++) {
			p.set(i, value);
		}
		md.get(new long[] {0,0,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,0,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,1,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,1,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,2,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,2,0}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,0,1}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,0,1}, value);
		assertEquals(44, value.v());
		md.get(new long[] {0,1,1}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,1,1}, value);
		assertEquals(44, value.v());
		md.get(new long[] {0,2,1}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,2,1}, value);
		assertEquals(44, value.v());
		md.get(new long[] {0,0,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,0,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,1,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,1,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,2,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,2,2}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,0,3}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,0,3}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,1,3}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,1,3}, value);
		assertEquals(0, value.v());
		md.get(new long[] {0,2,3}, value);
		assertEquals(0, value.v());
		md.get(new long[] {1,2,3}, value);
		assertEquals(0, value.v());
	}

}
