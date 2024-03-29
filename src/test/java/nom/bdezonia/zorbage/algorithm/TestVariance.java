/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.storage.Storage;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

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
		nums = Storage.allocate(G.DBL.construct(), values);

		ApproxVariance.compute(G.DBL, nums, v);
		Variance.compute(G.DBL, nums, vn);

		assertTrue(Math.abs(v.v() - vn.v()) < tol);

		values = new double[]{1.0e150, 0.00001};
		nums = Storage.allocate(G.DBL.construct(), values);

		// BOTH wrong
		ApproxVariance.compute(G.DBL, nums, v);
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
