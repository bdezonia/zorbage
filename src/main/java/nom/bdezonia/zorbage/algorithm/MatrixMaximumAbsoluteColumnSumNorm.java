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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixMaximumAbsoluteColumnSumNorm {
	
	// http://mathworld.wolfram.com/MatrixNorm.html

	// do not instantiate
	
	private MatrixMaximumAbsoluteColumnSumNorm() {}

	/**
	 * 
	 * @param numAlgebra
	 * @param matrix
	 * @param norm
	 */
	public static
		<T extends Algebra<T,U> & Norm<U,W>, U, V extends Algebra<V,W> & Addition<W> & Ordered<W>, W>
	void compute(T Algebra1, V Algebra2, MatrixMember<U> matrix, W result)
	{
		W tmp = Algebra2.construct();
		W max = Algebra2.construct();
		for (long c = 0; c < matrix.cols(); c++) {
			W colSum = Algebra2.construct();
			U value = Algebra1.construct();
			for (long r = 0; r < matrix.rows(); r++) {
				matrix.v(r, c, value);
				Algebra1.norm().call(value, tmp);
				Algebra2.add().call(colSum, tmp, colSum);
			}
			if (Algebra2.isGreater().call(colSum, max))
				Algebra2.assign().call(colSum, max);
		}
		Algebra2.assign().call(max, result);
	}
}
