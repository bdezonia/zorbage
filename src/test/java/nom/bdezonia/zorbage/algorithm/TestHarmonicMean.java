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

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestHarmonicMean {

	@Test
	public void test1() {
		// https://www.investopedia.com/terms/h/harmonicaverage.asp
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(new double[] {4,4,1});
		HarmonicMean.compute(G.DBL, nums, value);
		assertEquals(2, value.v(), 0);
	}

	@Test
	public void test2() {
		
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(new double[0]);
		HarmonicMean.compute(G.DBL, nums, value);
		assertTrue(Double.isNaN(value.v()));
	}

	@Test
	public void test3() {
		// https://www.brainkart.com/article/Harmonic-Mean-(H-M-)_35082/
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(new double[] {50,65,80,55});
		HarmonicMean.compute(G.DBL, nums, value);
		assertEquals(60.5, value.v(), 0.1);
	}

	@Test
	public void test4() {
		// https://www.emathzone.com/tutorials/basic-statistics/harmonic-mean.html
		Float64Member value = G.DBL.construct();
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(new double[] {13.2,14.2,14.8,15.2,16.1});
		HarmonicMean.compute(G.DBL, nums, value);
		assertEquals(14.63, value.v(), 0.01);
	}

}
