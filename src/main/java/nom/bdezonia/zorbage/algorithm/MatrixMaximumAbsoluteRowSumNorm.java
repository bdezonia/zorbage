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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixMaximumAbsoluteRowSumNorm {
	
	// http://mathworld.wolfram.com/MatrixNorm.html

	// do not instantiate
	
	private MatrixMaximumAbsoluteRowSumNorm() {}

	/**
	 * @param algebra1
	 * @param algebra2
	 * @param matrix
	 * @param result
	 */
	public static
		<T extends Algebra<T,U> & Norm<U,W>, U, V extends Algebra<V,W> & Addition<W> & Ordered<W>, W>
	void compute(T algebra1, V algebra2, MatrixMember<U> matrix, W result)
	{
		W tmp = algebra2.construct();
		W max = algebra2.construct();
		W rowSum = algebra2.construct();
		U value = algebra1.construct();
		for (long r = 0; r < matrix.rows(); r++) {
			algebra2.zero().call(rowSum);
			for (long c = 0; c < matrix.cols(); c++) {
				matrix.v(r, c, value);
				algebra1.norm().call(value, tmp);
				algebra2.add().call(rowSum, tmp, rowSum);
			}
			if (algebra2.isGreater().call(rowSum, max))
				algebra2.assign().call(rowSum, max);
		}
		algebra2.assign().call(max, result);
	}
}
