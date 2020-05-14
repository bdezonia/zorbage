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
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSumSquareCount {
	
	private final double tol = 0.00000001;
	
	@Test
	public void test() {

		Float64Member sumSqDevs = G.DBL.construct();
		Float64Member count = G.DBL.construct();
		Float64Member avg = G.DBL.construct();

		IndexedDataSource<Float64Member> data = ArrayStorage.allocateDoubles(new double[] {-5,5,0,0,0,0,0,0,0,0});
		SumSquareCount.compute(G.DBL, data, avg, sumSqDevs, count);

		assertEquals(10, count.v(), 0);
		assertEquals(50, sumSqDevs.v(), tol);

		data = ArrayStorage.allocateDoubles(new double[] {18,28,23,23,23,23,23,23,23,23});
		SumSquareCount.compute(G.DBL, data, avg, sumSqDevs, count);

		assertEquals(10, count.v(), 0);
		assertEquals(50, sumSqDevs.v(), tol);
		
		data = ArrayStorage.allocateDoubles(new double[] {996,998,1000,1000,1000,1002,1004,1000,1000,1000});
		SumSquareCount.compute(G.DBL, data, avg, sumSqDevs, count);

		assertEquals(10, count.v(), 0);
		assertEquals(40, sumSqDevs.v(), tol);
		
		data = ArrayStorage.allocateDoubles(new double[] {-2000,-3000,-1000,-1000,-1000,0,1000,-1000,-1000,-1000});
		SumSquareCount.compute(G.DBL, data, avg, sumSqDevs, count);

		assertEquals(10, count.v(), 0);
		assertEquals(10000000, sumSqDevs.v(), tol);
		
	}

	@Test
	public void specific() {
		
		double[] nums = new double[] {-22, 1006, 30000, -587, 0};
		
		IndexedDataSource<Float64Member> data = ArrayStorage.allocateDoubles(nums);
		Float64Member tmp = G.DBL.construct();
		Float64Member sumSqDevs = G.DBL.construct();
		Float64Member count = G.DBL.construct();
		Float64Member avg = G.DBL.construct();
		SumSquareCount.compute(G.DBL, data, avg, sumSqDevs, count);
		double expSumSq = 0;
		for (int i = 0; i < nums.length; i++) {
			expSumSq += (nums[i] - avg.v()) * (nums[i] - avg.v());
		}
		assertEquals(expSumSq,sumSqDevs.v(),0.1);
		StdDev.compute(G.DBL, data, tmp);
		assertEquals(13384.333819806,tmp.v(),tol);
		// value according to https://www.calculator.net/statistics-calculator.html
	}
}
