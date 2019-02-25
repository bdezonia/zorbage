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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixReshape {

	private MatrixReshape() { }

	/**
	 * 
	 * @param matAlgebra
	 * @param numberAlgebra
	 * @param rows
	 * @param cols
	 * @param mat
	 */
	public static <T extends Algebra<T,U>, U extends MatrixMember<W>, V extends Algebra<V,W>, W>
		void compute(T matAlgebra, V numberAlgebra, long rows, long cols, U mat)
	{
		if (rows == mat.rows() && cols == mat.cols()) return;
		U orig = matAlgebra.construct(mat);
		mat.alloc(rows, cols);
		W value = numberAlgebra.construct();
		long minRows = Math.min(rows, orig.rows());
		long minCols = Math.min(cols, orig.cols());
		for (long r = 0; r < minRows; r++) {
			for (long c = 0; c < minCols; c++) {
				orig.v(r, c, value);
				mat.setV(r, c, value);
			}
		}
	}
}
