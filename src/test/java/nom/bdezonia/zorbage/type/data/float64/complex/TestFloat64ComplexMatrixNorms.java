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
package nom.bdezonia.zorbage.type.data.float64.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteColumnSumNorm;
import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteRowSumNorm;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64ComplexMatrixNorms {

	@Test
	public void test1() {
		Float64Member norm = new Float64Member();
		ComplexFloat64MatrixMember matrix = new ComplexFloat64MatrixMember(
				2, 2, new double[] {4,0,-3, 0, 18, 0, -11, 0});
		MatrixMaximumAbsoluteColumnSumNorm.compute(G.CDBL, G.DBL, matrix, norm);
		assertEquals(22, norm.v(), 0);
	}

	@Test
	public void test2() {
		Float64Member norm = new Float64Member();
		ComplexFloat64MatrixMember matrix = new ComplexFloat64MatrixMember(
				2, 2, new double[] {4,0,-3, 0, 18, 0, -11, 0});
		MatrixMaximumAbsoluteRowSumNorm.compute(G.CDBL, G.DBL, matrix, norm);
		assertEquals(29, norm.v(), 0);
	}
}
