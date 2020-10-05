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
package nom.bdezonia.zorbage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSequencedDataSource {

	@Test
	public void test() {
		SignedInt32Member value = G.INT32.construct();
		int[] tmp = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		IndexedDataSource<SignedInt32Member> ints = Storage.allocate(G.INT32.construct(), tmp);

		// test one extreme boundary condition of seq
		
		SequencedDataSource<SignedInt32Member> seq = new SequencedDataSource<>(ints, 0, 1, ints.size());
		assertEquals(ints.size(), seq.size());
		for (int i = 0; i < ints.size(); i++) {
			seq.get(i, value);
			assertEquals(tmp[i],value.v());
		}
		
		// test another extreme boundary condition of seq
		
		seq = new SequencedDataSource<>(ints, 5, 1, 0);
		assertEquals(0, seq.size());
		
		// test frontish to backish with gaps
		
		seq = new SequencedDataSource<>(ints, 3, 2, 5);
		assertEquals(5, seq.size());
		seq.get(0, value);
		assertEquals(3, value.v());
		seq.get(1, value);
		assertEquals(5, value.v());
		seq.get(2, value);
		assertEquals(7, value.v());
		seq.get(3, value);
		assertEquals(9, value.v());
		seq.get(4, value);
		assertEquals(11, value.v());

		try {
			seq = new SequencedDataSource<>(ints, 3, 2, 8);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// test backish to frontish with gaps
		
		seq = new SequencedDataSource<>(ints, 12, -3, 5);
		assertEquals(5, seq.size());
		seq.get(0, value);
		assertEquals(12, value.v());
		seq.get(1, value);
		assertEquals(9, value.v());
		seq.get(2, value);
		assertEquals(6, value.v());
		seq.get(3, value);
		assertEquals(3, value.v());
		seq.get(4, value);
		assertEquals(0, value.v());
		
		try {
			seq = new SequencedDataSource<>(ints, 12, -3, 6);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
