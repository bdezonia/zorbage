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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixDirectProduct {

	@Test
	public void test() {
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		Float64MatrixMember b = new Float64MatrixMember(2, 2, new double[] {5,6,7,8});
		Float64MatrixMember c = G.DBL_MAT.construct();
		
		G.DBL_MAT.directProduct().call(a, b, c);
		
		Float64Member value = G.DBL.construct();
		
		assertEquals(4, c.rows());
		assertEquals(4, c.cols());
		
		c.v(0, 0, value);
		assertEquals(5, value.v(), 0);
		c.v(0, 1, value);
		assertEquals(6, value.v(), 0);
		c.v(1, 0, value);
		assertEquals(7, value.v(), 0);
		c.v(1, 1, value);
		assertEquals(8, value.v(), 0);
		
		c.v(0, 2, value);
		assertEquals(10, value.v(), 0);
		c.v(0, 3, value);
		assertEquals(12, value.v(), 0);
		c.v(1, 2, value);
		assertEquals(14, value.v(), 0);
		c.v(1, 3, value);
		assertEquals(16, value.v(), 0);
		
		c.v(2, 0, value);
		assertEquals(15, value.v(), 0);
		c.v(2, 1, value);
		assertEquals(18, value.v(), 0);
		c.v(3, 0, value);
		assertEquals(21, value.v(), 0);
		c.v(3, 1, value);
		assertEquals(24, value.v(), 0);
		
		c.v(2, 2, value);
		assertEquals(20, value.v(), 0);
		c.v(2, 3, value);
		assertEquals(24, value.v(), 0);
		c.v(3, 2, value);
		assertEquals(28, value.v(), 0);
		c.v(3, 3, value);
		assertEquals(32, value.v(), 0);
		
	}
}
