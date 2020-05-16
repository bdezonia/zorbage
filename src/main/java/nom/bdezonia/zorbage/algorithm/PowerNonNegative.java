/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PowerNonNegative {

	private PowerNonNegative() {}
	
	/**
	 * 
	 * @param algebra
	 * @param power
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Multiplication<U> & Unity<U>, U>
		void compute(T algebra, int power, U a, U b)
	{
		if (power < 0)
			throw new IllegalArgumentException("Cannot get negative powers from integers");
		if (power == 0) {
			if (algebra.isZero().call(a))
				throw new IllegalArgumentException("0^0 is not a number");
		}
		pow(algebra, power, a, b);
	}
	
	private static <T extends Algebra<T,U> & Multiplication<U> & Unity<U>, U>
		void pow(T algebra, int pow, U a, U b)
	{
		if (pow == 0) {
			algebra.unity().call(b);
		}
		else if (pow == 1) {
			algebra.assign().call(a, b);
		}
		else {
			U tmp = algebra.construct();
			int halfPow = pow >>> 1;
			pow(algebra, halfPow, a, tmp);
			algebra.multiply().call(tmp, tmp, tmp);
			if (pow != (halfPow << 1))
				algebra.multiply().call(tmp, a, tmp);
			algebra.assign().call(tmp, b);
		}
	}

}
