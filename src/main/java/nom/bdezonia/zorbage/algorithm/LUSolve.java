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

import nom.bdezonia.zorbage.algebra.Constructible1dLong;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.RModule;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUSolve {

	// do not instantiate
	
	private LUSolve() {}
	
	/**
	 * LU solution. Sets the solution vector x given A and b from the matrix
	 * equation Ax = b. A is already assumed to be in LU form (using {@link LUDecomp}).
	 * @param a
	 * @param b
	 * @param x
	 */
	public static <BASETYPE, // the base type like Float64Member or Octonion etc.
					BASETYPE_ALGEBRA extends RingWithUnity<BASETYPE_ALGEBRA,BASETYPE> & Invertible<BASETYPE>,
					RMODULE_MEMBER extends RModuleMember<BASETYPE>,
					RMODULE_ALGEBRA extends RModule<RMODULE_ALGEBRA,RMODULE_MEMBER,BASETYPE_ALGEBRA,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
					MATRIX_MEMBER extends MatrixMember<BASETYPE>>
		void compute(BASETYPE_ALGEBRA numAlgebra, RMODULE_ALGEBRA rmodAlgebra, MATRIX_MEMBER a, RMODULE_MEMBER b, RMODULE_MEMBER x)
	{
		final long n = x.length();
		
		BASETYPE tmp = numAlgebra.construct();
		BASETYPE value1 = numAlgebra.construct();
		BASETYPE value2 = numAlgebra.construct();
		BASETYPE sum = numAlgebra.construct();
		BASETYPE term = numAlgebra.construct();
		
		// find solution of Ly = b
		RMODULE_MEMBER y = rmodAlgebra.construct(x.storageType(), n);
		for (long i = 0; i < n; i++)
		{
			numAlgebra.zero().call(sum);
			for (long k = 0; k < i; k++) {
				a.getV(i, k, value1);
				y.getV(k, value2);
				numAlgebra.multiply().call(value1, value2, term);
				numAlgebra.add().call(sum, term, sum);
			}
			b.getV(i, value1);
			numAlgebra.subtract().call(value1, sum, term);
			y.setV(i, term);
		}

		// find solution of Ux = y
		for (long i = n - 1; i >= 0; i--)
		{
			numAlgebra.zero().call(sum);
			for (long k = i + 1; k < n; k++) {
				a.getV(i, k, value1);
				x.getV(k, value2);
				numAlgebra.multiply().call(value1, value2, term);
				numAlgebra.add().call(sum, term, sum);
			}
			numAlgebra.unity().call(tmp);
			a.getV(i, i, value1);
			numAlgebra.divide().call(tmp, value1, value1);
			y.getV(i, value2);
			numAlgebra.subtract().call(value2, sum, value2);
			numAlgebra.multiply().call(value1, value2, term);
			x.setV(i, term);
		}
	}
	
}
