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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Clamp {

	// do not instantiate
	
	private Clamp() { }
	
	/**
	 * Clamp takes a value and a bounding range (two values). It sets
	 * the result to the left end of the bounding range if the input
	 * value is less than the left end bounding marker. It sets
	 * the result to the right end of the bounding range if the input
	 * value is greater than the right end bounding marker. Otherwise
	 * the input value is within the bounding range and it is returned
	 * as the result. Clamp makes sure any value passed in will result
	 * in a value clamped to within the bounding range.
	 * 
	 * @param algebra
	 * @param min
	 * @param max
	 * @param value
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		void compute(T algebra, U min, U max, U value, U result)
	{
		if (algebra.isLess().call(max, min))
			throw new IllegalArgumentException("Clamp boundaries are malformed");

		if (algebra.isLess().call(value, min))
			algebra.assign().call(min, result);
		else if (algebra.isGreater().call(value, max))
			algebra.assign().call(max, result);
		else
			algebra.assign().call(value, result);
	}
}
