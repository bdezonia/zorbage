/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.storage.datasource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTrimmedDataSource {

	@Test
	public void test() {
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> ints = ArrayStorage.allocateInts(new int[]{1,2,3,4,5,6,7,8});
		IndexedDataSource<SignedInt32Member> trimmed;
		
		trimmed = new TrimmedDataSource<>(ints, 0, 0);
		assertEquals(1, trimmed.size());
		trimmed.get(0,  value);
		assertEquals(1, value.v());
		
		trimmed = new TrimmedDataSource<>(ints, 7, 7);
		assertEquals(1, trimmed.size());
		trimmed.get(0,  value);
		assertEquals(8, value.v());
		
		trimmed = new TrimmedDataSource<>(ints, 0, 4);
		assertEquals(5, trimmed.size());
		trimmed.get(0,  value);
		assertEquals(1, value.v());
		trimmed.get(1,  value);
		assertEquals(2, value.v());
		trimmed.get(2,  value);
		assertEquals(3, value.v());
		trimmed.get(3,  value);
		assertEquals(4, value.v());
		trimmed.get(4,  value);
		assertEquals(5, value.v());
		
		trimmed = new TrimmedDataSource<>(ints, 2, 6);
		assertEquals(5, trimmed.size());
		trimmed.get(0,  value);
		assertEquals(3, value.v());
		trimmed.get(1,  value);
		assertEquals(4, value.v());
		trimmed.get(2,  value);
		assertEquals(5, value.v());
		trimmed.get(3,  value);
		assertEquals(6, value.v());
		trimmed.get(4,  value);
		assertEquals(7, value.v());
		
		trimmed = new TrimmedDataSource<>(ints, 4, 6);
		assertEquals(3, trimmed.size());
		trimmed.get(0,  value);
		assertEquals(5, value.v());
		trimmed.get(1,  value);
		assertEquals(6, value.v());
		trimmed.get(2,  value);
		assertEquals(7, value.v());
	}
}