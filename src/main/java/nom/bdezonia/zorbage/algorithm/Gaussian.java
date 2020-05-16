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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Exponential;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Gaussian {

	private Gaussian() { }
	
	/**
	 * 
	 * @param alg
	 * @param mu
	 * @param sigma
	 * @param x
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Exponential<U> &
					Unity<U> & RealConstants<U> & Roots<U> & Invertible<U> & Ordered<U>,
					U>
		void compute(T alg, U mu, U sigma, U x, U result)
	{
		if (alg.signum().call(sigma) < 0)
			throw new IllegalArgumentException("sigma must be >= 0");
		U one = alg.construct();
		U two = alg.construct();
		U pi = alg.construct();
		U two_pi = alg.construct();
		U root_two_pi = alg.construct();
		U scale = alg.construct();
		U exp = alg.construct();
		U numer = alg.construct();
		U denom = alg.construct();
		
		alg.unity().call(one);
		alg.add().call(one, one, two);
		alg.PI().call(pi);
		alg.multiply().call(two, pi, two_pi);
		alg.sqrt().call(two_pi, root_two_pi);
		alg.multiply().call(root_two_pi, sigma, scale);
		alg.divide().call(one, scale, scale);
		alg.subtract().call(x, mu, numer);
		alg.multiply().call(numer, numer, numer);
		alg.negate().call(numer, numer);
		alg.multiply().call(sigma, sigma, denom);
		alg.multiply().call(two, denom, denom);
		alg.divide().call(numer, denom, exp);
		alg.exp().call(exp, exp);
		alg.multiply().call(scale, exp, result);
	}
}
