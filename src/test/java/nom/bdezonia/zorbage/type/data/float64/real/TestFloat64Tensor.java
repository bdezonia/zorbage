/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.real;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Tensor {

	@Test
	public void test() {
		Float64Member value = G.DBL.construct();
		Float64TensorProductMember a = G.DBL_TEN.construct("[[[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]]]");
		assertEquals(3, a.rank());
		assertEquals(3, a.dimension(0));
		assertEquals(3, a.dimension(1));
		assertEquals(3, a.dimension(2));

		G.DBL_TEN.unity().call(a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(1, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		Float64Member scale = new Float64Member(14);
		
		G.DBL_TEN.scale().call(scale, a, a);
		
		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		G.DBL_TEN.power().call(3, a, a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14*14*14, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		Float64TensorProductMember x = new Float64TensorProductMember(
				new long[] {3,2,2},
				new double[]{1,2,3,
								4,5,6,
								7,8,9,
								10,11,12});
		Float64TensorProductMember y = new Float64TensorProductMember(
				new long[] {2,3,2},
				new double[]{13,14,
								15,16,
								17,18,
								19,20,
								21,22,
								23,24
								});
		Float64TensorProductMember z = new Float64TensorProductMember();

		G.DBL_TEN.multiply().call(x, y, z);

		// from example at 
		//   https://www.tensorflow.org/api_docs/python/tf/linalg/matmul
		// which assumes multiply is a generalized mat mul. my code might not do this.
		//assertEquals(94, value.v(), 0);
		//assertEquals(100, value.v(), 0);
		//assertEquals(229, value.v(), 0);
		//assertEquals(244, value.v(), 0);
		//assertEquals(508, value.v(), 0);
		//assertEquals(532, value.v(), 0);
		//assertEquals(697, value.v(), 0);
		//assertEquals(732, value.v(), 0);
	}
}
