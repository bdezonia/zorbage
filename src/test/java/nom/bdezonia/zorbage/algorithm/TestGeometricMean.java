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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGeometricMean {

	@Test
	public void test1() {
		// https://www.wikihow.com/Calculate-the-Geometric-Mean
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {7,9,12});
		GeometricMean.compute(G.DBL, nums, value);
		assertEquals(9.11, value.v(), 0.01);
	}

	@Test
	public void test2() {
		
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[0]);
		try {
			GeometricMean.compute(G.DBL, nums, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void test3() {
		// https://www.cliffsnotes.com/study-guides/geometry/right-triangles/geometric-mean
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {4,25});
		GeometricMean.compute(G.DBL, nums, value);
		assertEquals(10, value.v(), 0);
	}

	@Test
	public void test4() {
		// https://www.statisticshowto.com/geometric-mean-2/
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {4,8,3,9,17});
		GeometricMean.compute(G.DBL, nums, value);
		assertEquals(6.81, value.v(), 0.01);
	}

}
