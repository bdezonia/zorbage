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

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBinarySearch {

	@Test
	public void test() {
		
		IndexedDataSource<Float64Member> vals = Storage.allocate(G.DBL.construct(), 
				new double[] {1,7,14,25,39});
		
		Float64Member v = G.DBL.construct();

		v.setV(1);
		assertEquals(0, BinarySearch.compute(G.DBL, v, vals));

		v.setV(7);
		assertEquals(1, BinarySearch.compute(G.DBL, v, vals));

		v.setV(14);
		assertEquals(2, BinarySearch.compute(G.DBL, v, vals));

		v.setV(25);
		assertEquals(3, BinarySearch.compute(G.DBL, v, vals));

		v.setV(39);
		assertEquals(4, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(0);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(2);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(6);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(8);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(13);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(15);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(24);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(26);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(38);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(40);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(-100000);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
		
		v.setV(100000);
		assertEquals(-1, BinarySearch.compute(G.DBL, v, vals));
	}
}
