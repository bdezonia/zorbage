/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.ModularDivision;
import nom.bdezonia.zorbage.type.algebra.Ordered;

/**
 * Greatest Common Divisor algorithm
 * 
 * @author Barry DeZonia
 *
 */
public class Gcd {
	
	/**
	 * Do not instantiate. Private constructor for utility class.
	 */
	private Gcd() {}
	
	/**
	 * Sets the result to the greatest common divisor of a and b. Result is always nonnegative.
	 * 
	 * @param Algebra
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> & AbsoluteValue<U> & ModularDivision<U> & Ordered<U>, U>
		void compute(T Algebra, U a, U b, U result)
	{
		U aTmp = Algebra.construct(a);
		U bTmp = Algebra.construct(b);
		U t = Algebra.construct();
		U zero = Algebra.construct();
		Algebra.abs().call(aTmp, aTmp);
		Algebra.abs().call(bTmp, bTmp);
		if (Algebra.isLess().call(aTmp, bTmp)) {
			Algebra.assign().call(aTmp, t);
			Algebra.assign().call(bTmp, aTmp);
			Algebra.assign().call(t, bTmp);
		}
		while (Algebra.isNotEqual().call(bTmp, zero)) {
			Algebra.assign().call(bTmp, t);
			Algebra.mod().call(aTmp, bTmp, bTmp);
			Algebra.assign().call(t, aTmp);
		}
		Algebra.assign().call(aTmp, result);
	}
}
