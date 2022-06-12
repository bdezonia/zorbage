/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Constructible2dLong;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixDeterminant {

	// do not instantiate

	private MatrixDeterminant() {}
	
	/**
	 * Calculate the determinant of a matrix.
	 * 
	 * @param matAlgebra
	 * @param numAlgebra
	 * @param a
	 * @param det
	 */
	public static
	
	<BASETYPE, // the base type like Float64Member or Octonion etc.
	BASETYPE_ALGEBRA extends RingWithUnity<BASETYPE_ALGEBRA,BASETYPE> & Invertible<BASETYPE>,
	MATRIX_MEMBER extends MatrixMember<BASETYPE>,
	MATRIX_ALGEBRA extends Algebra<MATRIX_ALGEBRA,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
		
	void compute(MATRIX_ALGEBRA matAlgebra, BASETYPE_ALGEBRA numAlgebra, MATRIX_MEMBER a, BASETYPE det)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("determinant requires square matrix");
		MATRIX_MEMBER tmpMat = matAlgebra.construct(a);
		LUDecomp.compute(numAlgebra, matAlgebra, tmpMat);
		BASETYPE tmp = numAlgebra.construct();
		BASETYPE prod = numAlgebra.construct();
		numAlgebra.unity().call(prod);
		for (long i = 0; i < tmpMat.rows(); i++) {
			tmpMat.getV(i, i, tmp);
			numAlgebra.multiply().call(prod, tmp, prod);
		}
		numAlgebra.assign().call(prod, det);
	}
	
}
