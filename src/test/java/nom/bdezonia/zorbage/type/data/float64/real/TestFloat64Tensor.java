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
		
		value.setV(2);
		a.setV(0, value);
		a.setV(13, value);
		a.setV(26, value);
		
		G.DBL_TEN.power().call(3, a, a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(8, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}
		
	}
}
