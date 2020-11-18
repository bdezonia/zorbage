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
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReverseCopy {

	@Test
	public void test() {
		
		IndexedDataSource<Float64Member> a = Storage.allocate(G.DBL.construct(), 
				new double[] {1,4,5,2,2,6,9,3,17});
		IndexedDataSource<Float64Member> b = Storage.allocate(G.DBL.construct(), 
				new double[9]);
		Float64Member value = G.DBL.construct();

		ReverseCopy.compute(G.DBL, a, b);
		assertEquals(9, a.size());
		b.get(0, value);
		assertEquals(17, value.v(), 0);
		b.get(1, value);
		assertEquals(3, value.v(), 0);
		b.get(2, value);
		assertEquals(9, value.v(), 0);
		b.get(3, value);
		assertEquals(6, value.v(), 0);
		b.get(4, value);
		assertEquals(2, value.v(), 0);
		b.get(5, value);
		assertEquals(2, value.v(), 0);
		b.get(6, value);
		assertEquals(5, value.v(), 0);
		b.get(7, value);
		assertEquals(4, value.v(), 0);
		b.get(8, value);
		assertEquals(1, value.v(), 0);
	}
}
