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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Barry DeZonia
 */
public class TestVariance {

	@Test
	public void test1() {

		double tol = 0.0000000000000001;

		Float64Member v = G.DBL.construct();
		Float64Member vn = G.DBL.construct();

		double[] values;
		IndexedDataSource<Float64Member> nums;

		values = new double[]{-44.33333, 100, -2.4, -3, 10000, 400, 250000.1};
		nums = ArrayStorage.allocateDoubles(values);

		VarianceApprox.compute(G.DBL, nums, v);
		Variance.compute(G.DBL, nums, vn);

		assertTrue(Math.abs(v.v() - vn.v()) < tol);

		values = new double[]{1.0e150, 0.00001};
		nums = ArrayStorage.allocateDoubles(values);

		// BOTH wrong
		VarianceApprox.compute(G.DBL, nums, v);
		Variance.compute(G.DBL, nums, vn);

		// CORRECT
		HighPrecisionMember vp = G.HP.construct();
		IndexedDataSource<HighPrecisionMember> numsPrecise = new ReadOnlyHighPrecisionDataSource<>(G.DBL, nums);
		Variance.compute(G.HP, numsPrecise, vp);

		//System.out.println("optimized " + v.v());
		//System.out.println("naive " + vn.v());
		//System.out.println("precise " + vp.v());

		//assertTrue(Math.abs(v.v() - vn.v()) < 0.1);
	}
}
