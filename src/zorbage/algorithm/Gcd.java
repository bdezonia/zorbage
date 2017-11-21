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
package zorbage.algorithm;

import zorbage.type.algebra.AbsoluteValue;
import zorbage.type.algebra.Group;
import zorbage.type.algebra.IntegralDivision;
import zorbage.type.algebra.Ordered;

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
	 * Sets the result to the greatest common divisor of a and b. Result is always positive.
	 * 
	 * @param group
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Group<T,U> & AbsoluteValue<U> & IntegralDivision<U> & Ordered<U>, U>
		void findGcd(T group, U a, U b, U result)
	{
		U aTmp = group.construct(a);
		U bTmp = group.construct(b);
		U t = group.construct();
		U zero = group.construct();
		group.abs(aTmp, aTmp);
		group.abs(bTmp, bTmp);
		if (group.isLess(aTmp, bTmp)) {
			group.assign(aTmp, t);
			group.assign(bTmp, aTmp);
			group.assign(t, bTmp);
		}
		while (group.isNotEqual(bTmp, zero)) {
			group.assign(bTmp, t);
			group.mod(aTmp, bTmp, bTmp);
			group.assign(t, aTmp);
		}
		group.assign(aTmp, result);
	}
}
