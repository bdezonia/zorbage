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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

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
	 * 
	 * @param numGroup
	 * @param matrix
	 * @param norm
	 */
	public static
		<S extends MatrixMember<U>,
		T extends Group<T,U> & Addition<U> & Norm<U,Float64Member>,
		U>
	void compute(T numGroup, S matrix, Float64Member norm)
	{
		Float64Member tmp = new Float64Member();
		Float64Member max = new Float64Member(); 
		for (long r = 0; r < matrix.rows(); r++) {
			Float64Member rowSum = new Float64Member();
			U value = numGroup.construct();
			for (long c = 0; c < matrix.cols(); c++) {
				matrix.v(r, c, value);
				numGroup.norm().call(value, tmp);
				G.DBL.add().call(rowSum, tmp, rowSum);
			}
			if (G.DBL.isGreater().call(rowSum, max))
				G.DBL.assign().call(rowSum, max);
		}
		G.DBL.assign().call(max, norm);
	}
}
