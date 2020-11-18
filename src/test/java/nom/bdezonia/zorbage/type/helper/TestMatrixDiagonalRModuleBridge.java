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
package nom.bdezonia.zorbage.type.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.RampFill;
import nom.bdezonia.zorbage.type.helper.MatrixDiagonalRModuleBridge.Origin;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixDiagonalRModuleBridge {

	@Test
	public void testDiags() {
		Float64MatrixMember matrix = new Float64MatrixMember(5,5,new double[25]);
		Float64Member value = new Float64Member();
		RampFill.compute(G.DBL, matrix.rawData());
		MatrixDiagonalRModuleBridge<Float64Member> diag = new MatrixDiagonalRModuleBridge<Float64Member>(G.DBL,matrix);
		assertEquals(5, diag.length());
		for (long i = 0; i < diag.length(); i++) {
			diag.getV(i, value);
			assertEquals(i*6,value.v(),0);
		}
		
		diag.setDiagonal(Origin.UpperLeft, 1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.UpperLeft, 2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.UpperLeft, 3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.UpperLeft, 4);
		assertEquals(1, diag.length());
		assertEquals(0, diag.row(0));
		assertEquals(4, diag.col(0));
		
		diag.setDiagonal(Origin.UpperLeft, -1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.UpperLeft, -2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.UpperLeft, -3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.UpperLeft, -4);
		assertEquals(1, diag.length());
		assertEquals(4, diag.row(0));
		assertEquals(0, diag.col(0));
		
		diag.setDiagonal(Origin.LowerLeft, 1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.LowerLeft, 2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.LowerLeft, 3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.LowerLeft, 4);
		assertEquals(1, diag.length());
		assertEquals(0, diag.row(0));
		assertEquals(0, diag.col(0));
		
		diag.setDiagonal(Origin.LowerLeft, -1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.LowerLeft, -2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.LowerLeft, -3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.LowerLeft, -4);
		assertEquals(1, diag.length());
		assertEquals(4, diag.row(0));
		assertEquals(4, diag.col(0));

		diag.setDiagonal(Origin.LowerRight, 1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.LowerRight, 2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.LowerRight, 3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.LowerRight, 4);
		assertEquals(1, diag.length());
		assertEquals(4, diag.row(0));
		assertEquals(0, diag.col(0));
		
		diag.setDiagonal(Origin.LowerRight, -1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.LowerRight, -2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.LowerRight, -3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.LowerRight, -4);
		assertEquals(1, diag.length());
		assertEquals(0, diag.row(0));
		assertEquals(4, diag.col(0));

		diag.setDiagonal(Origin.UpperRight, 1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.UpperRight, 2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.UpperRight, 3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.UpperRight, 4);
		assertEquals(1, diag.length());
		assertEquals(4, diag.row(0));
		assertEquals(4, diag.col(0));

		diag.setDiagonal(Origin.UpperRight, -1);
		assertEquals(4, diag.length());
		diag.setDiagonal(Origin.UpperRight, -2);
		assertEquals(3, diag.length());
		diag.setDiagonal(Origin.UpperRight, -3);
		assertEquals(2, diag.length());
		diag.setDiagonal(Origin.UpperRight, -4);
		assertEquals(1, diag.length());
		assertEquals(0, diag.row(0));
		assertEquals(0, diag.col(0));

		// TODO: test non-square matrices
		
		// TODO: test illegal arg edge conditions
	}
}
