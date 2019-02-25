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
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUDecomp {

	// do not instantiate
	
	private LUDecomp() {}
	
	/**
	 * 
	 * @param numAlgebra The Algebra that can do primitive type math.
	 * @param matAlgebra The Algebra that can do matrix type math.
	 * @param a The matrix that will be modified into LU format.
	 */
	public static
	
		<BASETYPE, // the base type like Float64Member or Octonion etc.
		BASETYPE_ALGEBRA extends RingWithUnity<BASETYPE_ALGEBRA,BASETYPE> & Invertible<BASETYPE>,
		MATRIX_MEMBER extends MatrixMember<BASETYPE>,
		MATRIX_ALGEBRA extends Algebra<MATRIX_ALGEBRA,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
		
	void compute(BASETYPE_ALGEBRA numAlgebra, MATRIX_ALGEBRA matAlgebra, MATRIX_MEMBER a)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("LUDecomp requires square matrix input");
		
		final long n = a.rows();
	
		// decomposition of matrix
		
		MATRIX_MEMBER lu = matAlgebra.construct(a.storageType(), n, n);
		BASETYPE sum = numAlgebra.construct();
		BASETYPE value1 = numAlgebra.construct();
		BASETYPE value2 = numAlgebra.construct();
		BASETYPE term = numAlgebra.construct();
		BASETYPE tmp = numAlgebra.construct();
		
		for (long i = 0; i < n; i++)
		{
			for (long j = i; j < n; j++)
			{
				numAlgebra.zero().call(sum);
				for (long k = 0; k < i; k++) {
					lu.v(i, k, value1);
					lu.v(k, j, value2);
					numAlgebra.multiply().call(value1, value2, term);
					numAlgebra.add().call(sum, term, sum);
				}
				a.v(i, j, term);
				numAlgebra.subtract().call(term, sum, term);
				lu.setV(i, j, term);
			}
			for (long j = i + 1; j < n; j++)
			{
				numAlgebra.zero().call(sum);
				for (long k = 0; k < i; k++) {
					lu.v(j, k, value1);
					lu.v(k, i, value2);
					numAlgebra.multiply().call(value1, value2, term);
					numAlgebra.add().call(sum, term, sum);
				}
				numAlgebra.unity().call(value1);
				lu.v(i, i, tmp);
				numAlgebra.divide().call(value1, tmp, value1);
				a.v(j, i, tmp);
				numAlgebra.subtract().call(tmp, sum, value2);
				numAlgebra.multiply().call(value1, value2, term);
				lu.setV(j, i, term);
			}
		}
		
		matAlgebra.assign().call(lu, a);
	}
}