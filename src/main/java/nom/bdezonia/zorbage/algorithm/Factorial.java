/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Factorial {

	// do not instantiate
	
	private Factorial() {}

	/**
	 * Factorial will return the value of the factorial function for an integer value n.
	 * One of the nice aspect of this method is that it works with all kinds of
	 * types. You can use numbers that can't overflow. Or you can use complex numbers
	 * so that you can get a complex value that equals n factorial (since your
	 * equation might work best working with complex numbers).
	 * 
	 * @param algebra
	 * @param n
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Unity<U> & Addition<U> & Multiplication<U>, U>
		void compute(T algebra, int n, U result)
	{
		if (n < 0)
			throw new IllegalArgumentException("factorial not defined for negative input");
		if (n == 0 || n == 1) {
			algebra.unity().call(result);
		}
		else {
			U tmp = algebra.construct();
			U factor = algebra.construct();
			U one = algebra.construct();
			algebra.unity().call(one);
			algebra.assign().call(one, tmp);
			algebra.assign().call(one, factor);
			for (int i = 2; i <= n; i++) {
				algebra.add().call(factor, one, factor);
				algebra.multiply().call(tmp, factor, tmp);
			}
			algebra.assign().call(tmp, result);
		}
	}
}
