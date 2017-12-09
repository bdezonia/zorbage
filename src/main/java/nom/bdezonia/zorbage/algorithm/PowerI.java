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

	private PowerI() {}
	
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
		U one = group.construct();
		group.unity(one);
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		if (power == 0) {
			U zero = group.construct();
			if (group.isEqual(a,zero))
				throw new IllegalArgumentException("0^0 is not a number");
			group.assign(one, b);
			return;
		}
		// else power >= 1
		// this code minimizes number of multiplies
		U sum = group.construct(one);
		U term = group.construct(a);
		while (power != 0) {
			if ((power & 1) > 0)
				group.multiply(sum, term, sum);
			group.multiply(term, term, term);
			power >>= 1;
		}

		// pow bits
		// 1   (0001) is 2^1
		// 2   (0010) is 2^2
		// 3   (0011) is 2^2 * 2^1
		// 4   (0100) is 2^4
		// 5   (0101) is 2^4 * 2^1
		// 6   (0110) is 2^4 * 2^2
		// 7   (0111) is 2^4 * 2^2 * 2^1
		// 8   (1000) is 2^8
		// 9   (1001) is 2^8 * 2^1 
		// 10  (1010) is 2^8 * 2^2 
		
		group.assign(sum, b);
	}
	
}
