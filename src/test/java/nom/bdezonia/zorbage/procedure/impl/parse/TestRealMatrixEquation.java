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
package nom.bdezonia.zorbage.procedure.impl.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.float64.real.Float64Matrix;
import nom.bdezonia.zorbage.type.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRealMatrixEquation {

	@Test
	public void test1() {
		
		EquationParser<Float64Matrix,Float64MatrixMember> parser =
				new EquationParser<Float64Matrix,Float64MatrixMember>();
		Tuple2<String, Procedure<Float64MatrixMember>> result =
				parser.parse(G.DBL_MAT, "[[1,2],[3,4]]");
		assertEquals(null, result.a());
		
		Float64MatrixMember mat = G.DBL_MAT.construct();
		result.b().call(mat);
		
		assertEquals(2, mat.numDimensions());
		assertEquals(2, mat.dimension(0));
		assertEquals(2, mat.dimension(1));

		Float64Member tmp = G.DBL.construct();
		
		mat.getV(0, 0, tmp);
		assertEquals(1, tmp.v(), 0);
		
		mat.getV(0, 1, tmp);
		assertEquals(2, tmp.v(), 0);
		
		mat.getV(1, 0, tmp);
		assertEquals(3, tmp.v(), 0);
		
		mat.getV(1, 1, tmp);
		assertEquals(4, tmp.v(), 0);
	}
}
