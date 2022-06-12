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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTaylorEstimateLog {

	@Test
	public void test1() {
		Float64MatrixMember x = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		Float64MatrixMember result = G.DBL_MAT.construct();
		TaylorEstimateLog.compute(8, G.DBL_MAT, G.DBL, x, result);
		// TODO: check results
		assertTrue(true);
	}
	
	@Test
	public void test2() {
		Float64Member x = new Float64Member(0.135);
		Float64Member result = G.DBL.construct();
		TaylorEstimateLog.compute(25, G.DBL, G.DBL, x, result);
		assertEquals(Math.log(0.135), result.v(), 0.0000001);
	}
	
	@Test
	public void tmp() {
		Float64MatrixMember x = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		Float64MatrixMember result = G.DBL_MAT.construct();
		Float64Member one = G.DBL.construct();
		Float64Member two = G.DBL.construct();
		Float64Member three = G.DBL.construct();
		Float64Member four = G.DBL.construct();
		for (int i = 1; i <= 50; i++) {
			TaylorEstimateLog.compute(i, G.DBL_MAT, G.DBL, x, result);
			result.getV(0, 0, one);
			result.getV(0, 1, two);
			result.getV(1, 0, three);
			result.getV(1, 1, four);
			System.out.println(one.v()+","+two.v()+","+three.v()+","+four.v());
		}
	}
}
