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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PowerI {

	/**
	 * 
	 * @param group
	 * @param power
	 * @param a
	 * @param b
	 */
	public static <T extends Group<T,U> & Addition<U> & Multiplication<U> & Unity<U>, U>
		void compute(T group, int power, U a, U b)
	{
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		U zero = group.construct();
		if (power == 0 && group.isEqual(a,zero))
			throw new IllegalArgumentException("0^0 is not a number");
		// this code minimizes number of multiplies
		U sum = group.construct();
		U inc = group.construct();
		group.unity(inc);
		if (power == 0)
			group.assign(inc, b);
		// else power >= 1
		int hiBit = hiBit(power);
		U two = group.construct();
		group.add(inc, inc, two);
		for (int i = 0; i <= hiBit; i++) {
			if ((power & i) > 0)
				group.add(sum, inc, sum);
			group.multiply(inc, two, inc);
		}
		group.assign(sum, b);
	}
	
	private static int hiBit(int power) {
		int tmp = 0x80000000;
		for (int i = 31; i >= 0; i--) {
			if ((power & tmp) > 0)
				return i;
			tmp >>>= 1;
		}
		throw new IllegalArgumentException("should not be possible to get here");
	}

}
