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
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EstimateErf {

	private EstimateErf() { }
	
	/**
	 * 
	 * @param alg
	 * @param numTerms
	 * @param input
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Unity<U> & Addition<U> & Multiplication<U> &
					Invertible<U> & Power<U> & RealConstants<U> & Roots<U>, U>
		void compute(T alg, int numTerms, U input, U result)
	{
		if (numTerms <= 0)
			throw new IllegalArgumentException("numTerms must be greater than zero");
		
		U n = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		U minus_one = alg.construct();
		alg.subtract().call(n, one, minus_one);
		U two = alg.construct();
		alg.add().call(one, one, two);
		U a = alg.construct();
		U b = alg.construct();
		U c = alg.construct();
		U d = alg.construct();
		U term = alg.construct();
		U sum = alg.construct();
		alg.assign().call(minus_one, a);
		for (int i = 0; i < numTerms; i++) {
			
			alg.multiply().call(a, minus_one, a);
			
			alg.multiply().call(n, two, d);
			alg.add().call(d, one, d);
			
			alg.pow().call(input, d, b);
			
			Factorial.compute(alg, i, c);
			
			alg.multiply().call(a, b, b);
			alg.multiply().call(c, d, d);
			alg.divide().call(b, d, term);
			
			alg.add().call(sum, term, sum);

			alg.add().call(n, one, n);
		}
		U pi = alg.construct();
		alg.multiply().call(sum, two, sum);
		alg.PI().call(pi);
		alg.sqrt().call(pi, pi);
		alg.divide().call(sum, pi, sum);
		alg.assign().call(sum, result);
	}
}
