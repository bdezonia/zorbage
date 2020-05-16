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

import nom.bdezonia.zorbage.type.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixUnity {

	@Test
	public void test() {
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[4]);
		Float64MatrixMember b = new Float64MatrixMember(2, 3, new double[6]);
		Float64MatrixMember c = new Float64MatrixMember(3, 2, new double[6]);

		G.DBL_MAT.unity().call(a);
		assertValues(a);
		
		G.DBL_MAT.unity().call(b);
		assertValues(b);
		
		G.DBL_MAT.unity().call(c);
		assertValues(c);
	}
	
	private void assertValues(Float64MatrixMember a) {
		Float64Member value = G.DBL.construct();
		for (int i = 0; i < a.rows(); i++) {
			for (int j = 0; j < a.cols(); j++) {
				a.v(i, j, value);
				if (i == j)
					assertEquals(1, value.v(), 0);
				else
					assertEquals(0, value.v(), 0);
			}
		}
	}
}
