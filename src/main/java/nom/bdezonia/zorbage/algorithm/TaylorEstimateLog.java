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
public class TaylorEstimateLog {

	private TaylorEstimateLog() { }

	/**
	 * 
	 * @param numTerms
	 * @param elemAlgebra
	 * @param numAlgebra
	 * @param x
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Unity<U> & Addition<U> & Multiplication<U> & Invertible<U>,
					U,
					V extends Algebra<V,W> & Addition<W> & Multiplication<W> & Scale<W, U> & Unity<W> & Invertible<W>,
					W>
		void compute(int numTerms, V elemAlgebra, T numAlgebra, W x, W result)
	{
		if (numTerms < 1)
			throw new IllegalArgumentException("estimation requires 1 or more terms");

		// first formulation: not converging
		
		// ln(x) = (x-1)/1 - (x-1)^2/2 + (x-1)^3/3 + ...
		
		// second formulation: also not converging
		
		// ln(x) = 2 * [(x-1)/(x+1) + (1/3)(x-1)^3/(x+1)^3) + (1/5)(x-1)^5/(x+1)^5) + ...]
		// x > 0
		
		// TODO: Matrix logs are complicated algorithms. Investigate further and implement
		// something to replace this code. This code might work in limited circumstances.
		
		W xMinusI = elemAlgebra.construct();
		W xPlusI = elemAlgebra.construct();
		W I = elemAlgebra.construct(x);
		elemAlgebra.unity().call(I);
		elemAlgebra.add().call(x, I, xPlusI);
		elemAlgebra.subtract().call(x, I, xMinusI);
		W sum = elemAlgebra.construct(x);
		elemAlgebra.zero().call(sum);
		W subTerm = elemAlgebra.construct();
		elemAlgebra.divide().call(xMinusI, xPlusI, subTerm);
		W term = elemAlgebra.construct(subTerm);
		W term2 = elemAlgebra.construct();
		W term3 = elemAlgebra.construct();
		U one = numAlgebra.construct();
		numAlgebra.unity().call(one);
		U inc = numAlgebra.construct(one);
		U scale = numAlgebra.construct();
		for (int i = 0; i < numTerms; i++) {
			numAlgebra.divide().call(one, inc, scale);
			elemAlgebra.scale().call(scale, term, term2);
			elemAlgebra.add().call(sum, term2, sum);
			elemAlgebra.multiply().call(term, subTerm, term3);
			elemAlgebra.multiply().call(term3, subTerm, term);
			numAlgebra.add().call(inc, one, inc);
			numAlgebra.add().call(inc, one, inc);
		}
		U two = numAlgebra.construct();
		numAlgebra.add().call(one, one, two);
		elemAlgebra.scale().call(two, sum, sum);
		elemAlgebra.assign().call(sum, result);
	}
}
