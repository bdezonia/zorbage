/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Factorial {

	// do not instantiate
	
	private Factorial() {}

	/**
	 * Factorial.compute()
	 * 
	 * @param group
	 * @param a
	 * @param result
	 */
	public static <T extends RingWithUnity<T,U> & Ordered<U>, U>
		void compute(T group, U a, U result)
	{
		U tmp = group.construct();
		if (group.isLess(a, tmp))
			throw new IllegalArgumentException("Cannot take factorial of negative input");
		group.unity(tmp);
		if (group.isLessEqual(a, tmp))
			group.assign(tmp, result);
		else {
			U product = group.construct(tmp);
			U multiplier = group.construct(a);
			while (group.isGreater(multiplier, tmp)) {
				group.multiply(product, multiplier, product);
				group.subtract(multiplier, tmp, multiplier);
			}
			group.assign(product, result);
		}
	}
}
