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

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixDeterminant {

	@Test
	public void testReal2x2Det() {
		Float64Member val = G.DBL.construct();

		Float64MatrixMember a = new Float64MatrixMember(2, 2,
				new double[] {1,2,
						      3,4});

		MatrixDeterminant.compute(G.DBL_MAT, G.DBL, a, val);

		assertEquals(-2, val.v(), 0);
	}

	// In wolfram alpha type:
	//   "LU decomposition of {{4,3,2,1},{1,10,3,4},{5,3,2,-4},{4,8,7,9}}"
	// Then multiply the diagonal to get 602.
	
	@Test
	public void testReal4x4Det() {
		Float64Member val = G.DBL.construct();

		Float64MatrixMember a = new Float64MatrixMember(4, 4,
				new double[] {
						4,3,2,1,
						1,10,3,4,
						5,3,2,-4,
						4,8,7,9
				});
		
		MatrixDeterminant.compute(G.DBL_MAT, G.DBL, a, val);

		assertEquals(602, val.v(), 0);
	}
}
