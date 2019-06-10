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
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Fibonacci {

	// do not instantiate
	
	private Fibonacci() {}

	/**
	 * Fibonacci.compute()
	 * 
	 * @param algebra
	 * @param n
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U>, U>
		void compute(T algebra, int n, U result)
	{
		if (n < 0)
			throw new IllegalArgumentException("Cannot get fibonacci value of negative input");
		if (n == 0) {
			algebra.zero().call(result);
		}
		else if (n == 1) {
			algebra.unity().call(result);
		}
		else {
			U prev2 = algebra.construct();
			U prev1 = algebra.construct();
			algebra.unity().call(prev1);
			U tmp = algebra.construct();
			for (int i = 2; i <= n; i++) {
				algebra.add().call(prev2, prev1, tmp);
				algebra.assign().call(prev1, prev2);
				algebra.assign().call(tmp, prev1);
			}
			algebra.assign().call(tmp, result);
		}
	}
}
