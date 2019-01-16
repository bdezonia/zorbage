/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TaylorEstimateCosh {

	private TaylorEstimateCosh() {
		
	}

	/**
	 * 
	 * @param numTerms
	 * @param matAlgebra
	 * @param numAlgebra
	 * @param x
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Unity<U> & Addition<U> & Multiplication<U> & Invertible<U>,
					U,
					V extends Algebra<V,W> & Addition<W> & Multiplication<W> & Scale<W, U> & Unity<W>,
					W /*extends MatrixMember<U>*/>
		void compute(int numTerms, V matAlgebra, T numAlgebra, W x, W result)
	{
		if (numTerms < 1)
			throw new IllegalArgumentException("estimation requires 1 or more terms");

		// cosh(x) = 1 + x^2/2! + x^4/4! + x^6/6! ...
		
		W sum = matAlgebra.construct(x);
		matAlgebra.zero().call(sum);
		W term = matAlgebra.construct(x);
		matAlgebra.unity().call(term);
		W term2 = matAlgebra.construct();
		W term3 = matAlgebra.construct();
		U one = numAlgebra.construct();
		numAlgebra.unity().call(one);
		U factorial = numAlgebra.construct(one);
		U inc = numAlgebra.construct();
		U scale = numAlgebra.construct();
		for (int i = 0; i < numTerms; i++) {
			numAlgebra.divide().call(one, factorial, scale);
			matAlgebra.scale().call(scale, term, term2);
			matAlgebra.add().call(sum, term2, sum);
			matAlgebra.multiply().call(term, x, term3);
			matAlgebra.multiply().call(term3, x, term);
			numAlgebra.add().call(inc,one,inc);
			numAlgebra.multiply().call(factorial, inc, factorial);
			numAlgebra.add().call(inc,one,inc);
			numAlgebra.multiply().call(factorial, inc, factorial);
		}
		matAlgebra.assign().call(sum, result);
	}
}
