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
	 * @param numGroup The group that can do primitive type math.
	 * @param matGroup The group that can do matrix type math.
	 * @param a The matrix that will be modified into LU format.
	 */
	public static
	
		<BASETYPE, // the base type like Float64Member or Octonion etc.
		BASETYPE_GROUP extends RingWithUnity<BASETYPE_GROUP,BASETYPE> & Invertible<BASETYPE>,
		MATRIX_MEMBER extends MatrixMember<BASETYPE>,
		MATRIX_GROUP extends Algebra<MATRIX_GROUP,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
		
	void compute(BASETYPE_GROUP numGroup, MATRIX_GROUP matGroup, MATRIX_MEMBER a)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("LUDecomp requires square matrix input");
		
		final long n = a.rows();
	
		// decomposition of matrix
		
		MATRIX_MEMBER lu = matGroup.construct(a.storageType(), n, n);
		BASETYPE sum = numGroup.construct();
		BASETYPE value1 = numGroup.construct();
		BASETYPE value2 = numGroup.construct();
		BASETYPE term = numGroup.construct();
		BASETYPE tmp = numGroup.construct();
		
		for (long i = 0; i < n; i++)
		{
			for (long j = i; j < n; j++)
			{
				numGroup.zero().call(sum);
				for (long k = 0; k < i; k++) {
					lu.v(i, k, value1);
					lu.v(k, j, value2);
					numGroup.multiply().call(value1, value2, term);
					numGroup.add().call(sum, term, sum);
				}
				a.v(i, j, term);
				numGroup.subtract().call(term, sum, term);
				lu.setV(i, j, term);
			}
			for (long j = i + 1; j < n; j++)
			{
				numGroup.zero().call(sum);
				for (long k = 0; k < i; k++) {
					lu.v(j, k, value1);
					lu.v(k, i, value2);
					numGroup.multiply().call(value1, value2, term);
					numGroup.add().call(sum, term, sum);
				}
				numGroup.unity().call(value1);
				lu.v(i, i, tmp);
				numGroup.divide().call(value1, tmp, value1);
				a.v(j, i, tmp);
				numGroup.subtract().call(tmp, sum, value2);
				numGroup.multiply().call(value1, value2, term);
				lu.setV(j, i, term);
			}
		}
		
		matGroup.assign().call(lu, a);
	}
}