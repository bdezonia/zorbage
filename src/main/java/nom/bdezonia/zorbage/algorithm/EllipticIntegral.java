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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EllipticIntegral {

	// do not instantiate
	
	private EllipticIntegral() { }

	/**
	 * Calculate the value of an elliptic integral (of the first or second kinds)
	 * for a given value of k.
	 * 
	 * @param <RA> A real-like algebra.
	 * @param <R> A real-like type.
	 * @param alg The algebra used for calculating intermediate values.
	 * @param k The integral parameter k.
	 * @param tol The tolerance that defines when we'll accept the solution.
	 * @param maxIters The max number of iterations to try before giving up.
	 * @param result The value of the integral if a solution is found.
	 * @return True if a solution was found in <= maxIters, else false.
	 */
	public static <RA extends Algebra<RA,R> & Tolerance<R,R> & Unity<R> & Roots<R> &
								Addition<R> & Multiplication<R> & RealConstants<R> &
								Invertible<R>,
					R>
		boolean compute(RA alg, R k, R tol, long maxIters, R result)
	{
		R pi = alg.construct();
		R one = alg.construct();
		R two = alg.construct();
		R a = alg.construct();
		R b = alg.construct();
		R an = alg.construct();
		R bn = alg.construct();
		R denom = alg.construct();

		// calc constants
		
		alg.unity().call(one);
		alg.add().call(one, one, two);
		alg.PI().call(pi);
		
		// setup initial values for iteration
		
		// a = 1
		
		alg.assign().call(one, a);
		
		// b = sqrt(1 - k^2)
		
		alg.multiply().call(k, k, b);
		alg.subtract().call(one, b, b);
		alg.sqrt().call(b, b);
		
		// now iterate
		
		for (long i = 0; i < maxIters; i++) {
			
			// arithmetic mean of a and b
		
			alg.add().call(a, b, an);
			alg.divide().call(an, two, an);
			
			// geometric mean of a and b
			
			alg.multiply().call(a, b, bn);
			alg.sqrt().call(bn, bn);
		
			// are we converged?
			
			if (alg.within().call(tol, an, bn)) {
				
				// yes - calc final result
			
				alg.multiply().call(an, two, denom);
				alg.divide().call(pi, denom, result);
				
				// signify we found the value
				
				return true;
			}
			
			// no - setup for next iteration
			
			alg.assign().call(an, a);
			alg.assign().call(bn, b);
		}
		
		// clear the result
		
		alg.zero().call(result);
		
		// signify we did not find the value
		
		return false;
	}
}
