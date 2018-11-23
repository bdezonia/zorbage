/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTaylorEstimateExp {

	@Test
	public void test() {
		Float64MatrixMember x = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		Float64MatrixMember result = G.DBL_MAT.construct();
		TaylorEstimateExp.compute(8, G.DBL_MAT, G.DBL, x, result);
		// TODO: check results
		assertTrue(true);
	}
	
	@Test
	public void test1() {
		Float64Member x = new Float64Member(0.135);
		Float64Member result = G.DBL.construct();
		TaylorEstimateExp.compute(11, G.DBL, G.DBL, x, result);
		assertEquals(Math.exp(x.v()), result.v(), 0.0000000000000001);
	}
	
	//@Test
	public void tmp() {
		Float64MatrixMember x = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		Float64MatrixMember result = G.DBL_MAT.construct();
		Float64Member one = G.DBL.construct();
		Float64Member two = G.DBL.construct();
		Float64Member three = G.DBL.construct();
		Float64Member four = G.DBL.construct();
		for (int i = 1; i <= 50; i++) {
			TaylorEstimateExp.compute(i, G.DBL_MAT, G.DBL, x, result);
			result.v(0, 0, one);
			result.v(0, 1, two);
			result.v(1, 0, three);
			result.v(1, 1, four);
			System.out.println(one.v()+","+two.v()+","+three.v()+","+four.v());
		}
	}
}
