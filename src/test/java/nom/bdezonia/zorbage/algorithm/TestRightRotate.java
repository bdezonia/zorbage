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
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRightRotate {

	@Test
	public void test() {
		
		SignedInt16Member value = G.INT16.construct();

		short[] numbers = new short[] {0, 10, 20, 30, 40, 50};
		
		IndexedDataSource<SignedInt16Member> data = ArrayStorage.allocateShorts(numbers);
		
		RightRotate.compute(G.INT16, 3, data);
		
		data.get(0, value);
		assertEquals(30, value.v());
		data.get(1, value);
		assertEquals(40, value.v());
		data.get(2, value);
		assertEquals(50, value.v());
		data.get(3, value);
		assertEquals(0, value.v());
		data.get(4, value);
		assertEquals(10, value.v());
		data.get(5, value);
		assertEquals(20, value.v());
		
		RightRotate.compute(G.INT16, -2, data);
		
		data.get(0, value);
		assertEquals(50, value.v());
		data.get(1, value);
		assertEquals(0, value.v());
		data.get(2, value);
		assertEquals(10, value.v());
		data.get(3, value);
		assertEquals(20, value.v());
		data.get(4, value);
		assertEquals(30, value.v());
		data.get(5, value);
		assertEquals(40, value.v());
		
	}
}
