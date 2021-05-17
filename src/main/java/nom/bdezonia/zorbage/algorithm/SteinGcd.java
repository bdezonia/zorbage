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

import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.EvenOdd;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.Unity;

// Stepanov and Rose: Stein gcd algorithm

/**
 * Calculate the greatest common divisor of two numbers (and number
 * like constructions). Uses the Stein algorithm which is faster
 * than other methods but requires more type constraints than other
 * algorithms.
 * 
 * @author Barry DeZonia
 *
 */
public class SteinGcd {
	
	// do not instantiate
	
	private SteinGcd() {}
	
	/**
	 * Sets the result to the greatest common divisor of a and b. Result is always nonnegative.
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> & AbsoluteValue<U,U> & ScaleByOneHalf<U> & ScaleByTwo<U> &
						Addition<U> & Ordered<U> & EvenOdd<U> & Bounded<U> & Unity<U> &
						ModularDivision<U>, U>
		void compute(T algebra, U a, U b, U result)
	{
		U t = algebra.construct(); // starts at zero
		U mTmp = algebra.construct(a);
		U nTmp = algebra.construct(b);
		U min = algebra.construct();
		U max = algebra.construct();
		
		algebra.minBound().call(min);
		algebra.maxBound().call(max);

		if (algebra.isLess().call(min, t) && algebra.isGreater().call(max, t)) {
			// looks like a signed type
			// see if either argument is -minint
			if (algebra.isEqual().call(min, mTmp) || algebra.isEqual().call(min, nTmp)) {
				U x = algebra.construct();
				U d = algebra.construct();
				U m = algebra.construct();
				algebra.unity().call(x);
				algebra.scaleByTwo().call(1, x, x);
				// x == 2
				int successes = 0;
				while (algebra.isGreater().call(x,t)) {
					if (algebra.isLess().call(mTmp, nTmp)) {
						algebra.divMod().call(nTmp, x, d, m);
						if (algebra.isEqual().call(m, t))
							successes++;
						else
							break;
					}
					else {  // mTmp >= nTmp
						algebra.divMod().call(mTmp, x, d, m);
						if (algebra.isEqual().call(m, t))
							successes++;
						else
							break;
					}
					algebra.scaleByTwo().call(1, x, x);
				}
				algebra.unity().call(t);
				algebra.scaleByTwo().call(successes, t, t);
				algebra.assign().call(t, result);
				return;
			}
		}
		
		algebra.abs().call(mTmp, mTmp);
		algebra.abs().call(nTmp, nTmp);
		
		if (algebra.isZero().call(mTmp)) {
			algebra.assign().call(nTmp, result);
			return;
		}
		
		if (algebra.isZero().call(nTmp)) {
			algebra.assign().call(mTmp, result);
			return;
		}
		
		// m > 0 && n > 0
		
		int d_m = 0;
		while (algebra.isEven().call(mTmp)) {
			algebra.scaleByOneHalf().call(1, mTmp, mTmp);
			d_m++;
		}
		
		int d_n = 0;
		while (algebra.isEven().call(nTmp)) {
			algebra.scaleByOneHalf().call(1, nTmp, nTmp);
			d_n++;
		}

		// odd m && odd n
		
		while (algebra.isNotEqual().call(mTmp, nTmp)) {
			if (algebra.isGreater().call(nTmp, mTmp)) {
				algebra.assign().call(nTmp, t);
				algebra.assign().call(mTmp, nTmp);
				algebra.assign().call(t, mTmp);				
			}
			algebra.subtract().call(mTmp, nTmp, mTmp);
			do {
				algebra.scaleByOneHalf().call(1, mTmp, mTmp);
			} while (algebra.isEven().call(mTmp));
		}
		
		// m == n
		
		algebra.scaleByTwo().call(Math.min(d_m, d_n), mTmp, result);
	}
}
