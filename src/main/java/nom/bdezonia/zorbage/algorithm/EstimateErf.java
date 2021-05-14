/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Power;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EstimateErf {

	// do not instantiate
	
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
		U zero = alg.construct();
		U tmp = alg.construct();
		U n = alg.construct();
		U one = alg.construct();
		U minus_one = alg.construct();
		U two = alg.construct();
		U minus_one_to_the_n = alg.construct();
		U two_n_plus_one = alg.construct();
		U n_factorial = alg.construct();
		U powTerm = alg.construct();
		U numer = alg.construct();
		U denom = alg.construct();
		U term = alg.construct();
		U sum = alg.construct();
		alg.unity().call(one);
		alg.add().call(one, one, two);
		alg.subtract().call(zero, one, minus_one);
		alg.assign().call(minus_one, minus_one_to_the_n);
		for (int i = 0; i < numTerms; i++) {
			
			alg.multiply().call(minus_one_to_the_n, minus_one, minus_one_to_the_n);
			
			alg.multiply().call(two, n, tmp);
			alg.add().call(tmp, one, two_n_plus_one);
			
			alg.pow().call(input, two_n_plus_one, powTerm);
			
			Factorial.compute(alg, i, n_factorial);
			
			alg.multiply().call(minus_one_to_the_n, powTerm, numer);
			alg.multiply().call(n_factorial, two_n_plus_one, denom);
			alg.divide().call(numer, denom, term);
			
			alg.add().call(sum, term, sum);

			alg.add().call(n, one, n);
		}
		U sqrt_pi = alg.construct();
		alg.PI().call(tmp);
		alg.sqrt().call(tmp, sqrt_pi);
		alg.divide().call(sum, sqrt_pi, sum);
		alg.multiply().call(sum, two, sum);
		alg.assign().call(sum, result);
	}
}
