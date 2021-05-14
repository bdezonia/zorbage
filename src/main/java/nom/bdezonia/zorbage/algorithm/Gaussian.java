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

	// do not instantiate
	
	private Gaussian() { }
	
	/**
	 * Gaussian
	 * 
	 * This algorithm calculates values along the gaussian curve of the normal distribution. One can specify
	 * mean mu and deviation sigma of the normal curve. Then one passes in an x value that you want to
	 * determine the curve value of and after calculation this algorithm will provide the resulting value.
	 * 
	 * Reference: https://en.wikipedia.org/wiki/Gaussian_function
	 * 
	 * @param alg The algebra for the type of numbers the algorithm will calculate with
	 * @param mu The mean of this normally distributed distribution
	 * @param sigma The standard deviation of this normally distributed distribution
	 * @param x The value along the x axis that we want the y value for that lies on the normal curve
	 * @param result The y value on the normal curve that this algorithm computes
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
