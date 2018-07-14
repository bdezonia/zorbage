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

import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUSolve {

	/**
	 * Do not instantiate. Private constructor for utility class.
	 */
	private LUSolve() {}
	
	/**
	 * LU solution. Sets the solution vector x given A and b from the matrix
	 * equation Ax = b. A is already assumed to be in LU form.
	 * @param a
	 * @param b
	 * @param x
	 */
	public static <BASETYPE, // the base type like Float64Member or Octonion etc.
					BASETYPE_GROUP extends RingWithUnity<BASETYPE_GROUP,BASETYPE> & Invertible<BASETYPE>,
					RMODULE_MEMBER extends RModuleMember<BASETYPE>,
					RMODULE_GROUP extends RModule<RMODULE_GROUP,RMODULE_MEMBER,BASETYPE_GROUP,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
					MATRIX_MEMBER extends MatrixMember<BASETYPE>>
		void compute(BASETYPE_GROUP numGroup, RMODULE_GROUP rmodGroup, MATRIX_MEMBER a, RMODULE_MEMBER b, RMODULE_MEMBER x)
	{
		final long n = x.length();
		
		BASETYPE tmp = numGroup.construct();
		BASETYPE value1 = numGroup.construct();
		BASETYPE value2 = numGroup.construct();
		BASETYPE sum = numGroup.construct();
		BASETYPE term = numGroup.construct();
		
		// find solution of Ly = b
		RMODULE_MEMBER y = rmodGroup.construct(x.storageType(), n);
		for (long i = 0; i < n; i++)
		{
			numGroup.zero(sum);
			for (long k = 0; k < i; k++) {
				a.v(i, k, value1);
				y.v(k, value2);
				numGroup.multiply(value1, value2, term);
				numGroup.add(sum, term, sum);
			}
			b.v(i, value1);
			numGroup.subtract(value1, sum, term);
			y.setV(i, term);
		}

		// find solution of Ux = y
		for (long i = n - 1; i >= 0; i--)
		{
			numGroup.zero(sum);
			for (long k = i + 1; k < n; k++) {
				a.v(i, k, value1);
				x.v(k, value2);
				numGroup.multiply(value1, value2, term);
				numGroup.add(sum, term, sum);
			}
			numGroup.unity(tmp);
			a.v(i, i, value1);
			numGroup.divide(tmp, value1, value1);
			y.v(i, value2);
			numGroup.subtract(value2, sum, value2);
			numGroup.multiply(value1, value2, term);
			x.setV(i, term);
		}

	}
	
}
