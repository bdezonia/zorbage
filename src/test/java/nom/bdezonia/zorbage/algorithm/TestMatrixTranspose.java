/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixTranspose {

	@Test
	public void square() {
		Float64Member value = new Float64Member();
		Float64MatrixMember a = new Float64MatrixMember(3,3,new double[] {1,2,3,4,5,6,7,8,9});
		Float64MatrixMember b = new Float64MatrixMember();

		MatrixTranspose.compute(G.DBL, a, b);
		
		assertEquals(3, b.rows());
		assertEquals(3, b.cols());
		b.v(0, 0, value);
		assertEquals(1, value.v(), 0);
		b.v(0, 1, value);
		assertEquals(4, value.v(), 0);
		b.v(0, 2, value);
		assertEquals(7, value.v(), 0);
		b.v(1, 0, value);
		assertEquals(2, value.v(), 0);
		b.v(1, 1, value);
		assertEquals(5, value.v(), 0);
		b.v(1, 2, value);
		assertEquals(8, value.v(), 0);
		b.v(2, 0, value);
		assertEquals(3, value.v(), 0);
		b.v(2, 1, value);
		assertEquals(6, value.v(), 0);
		b.v(2, 2, value);
		assertEquals(9, value.v(), 0);
		
		MatrixTranspose.compute(G.DBL, a, a);
		assertEquals(3, a.rows());
		assertEquals(3, a.cols());
		a.v(0, 0, value);
		assertEquals(1, value.v(), 0);
		a.v(0, 1, value);
		assertEquals(4, value.v(), 0);
		a.v(0, 2, value);
		assertEquals(7, value.v(), 0);
		a.v(1, 0, value);
		assertEquals(2, value.v(), 0);
		a.v(1, 1, value);
		assertEquals(5, value.v(), 0);
		a.v(1, 2, value);
		assertEquals(8, value.v(), 0);
		a.v(2, 0, value);
		assertEquals(3, value.v(), 0);
		a.v(2, 1, value);
		assertEquals(6, value.v(), 0);
		a.v(2, 2, value);
		assertEquals(9, value.v(), 0);
	}
	
	@Test
	public void nonsquare() {
		Float64Member value = new Float64Member();
		Float64MatrixMember a = new Float64MatrixMember(2,4,new double[] {1,2,3,4,5,6,7,8});
		Float64MatrixMember b = new Float64MatrixMember();

		MatrixTranspose.compute(G.DBL, a, b);
		
		assertEquals(4, b.rows());
		assertEquals(2, b.cols());
		b.v(0, 0, value);
		assertEquals(1, value.v(), 0);
		b.v(0, 1, value);
		assertEquals(5, value.v(), 0);
		b.v(1, 0, value);
		assertEquals(2, value.v(), 0);
		b.v(1, 1, value);
		assertEquals(6, value.v(), 0);
		b.v(2, 0, value);
		assertEquals(3, value.v(), 0);
		b.v(2, 1, value);
		assertEquals(7, value.v(), 0);
		b.v(3, 0, value);
		assertEquals(4, value.v(), 0);
		b.v(3, 1, value);
		assertEquals(8, value.v(), 0);
		
		try {
			MatrixTranspose.compute(G.DBL, a, a);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
