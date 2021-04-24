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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberWithin {

	private NumberWithin() { }
	
	/**
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 * @param tolerance
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U> & Addition<U>,U>
		boolean compute(T algebra, U tolerance, U a, U b)
	{
		U zero = algebra.construct();
		U diff = algebra.construct();
		U tol = algebra.construct(tolerance);
		if (algebra.isLess().call(tol, zero))
			throw new IllegalArgumentException("tolerance must be >= 0");
		int sigA = algebra.signum().call(a);
		int sigB = algebra.signum().call(b);
		if (sigA < 0) {
			if (sigB < 0) {
				if (algebra.isGreater().call(a, b))
					algebra.subtract().call(b, a, diff);
				else
					algebra.subtract().call(a, b, diff);
				if (algebra.isLess().call(diff, zero))
					algebra.negate().call(diff, diff);
				if (algebra.isGreater().call(diff, tol))
					return false;
				return true;
			}
			else if (sigB > 0) {

				if (algebra.isGreater().call(b, tol))
					return false;
				algebra.negate().call(tol, tol);
				if (algebra.isLess().call(a, tol))
					return false;
				// if here then they are within 2 tol of each other
				algebra.add().call(b, tol, diff);
				if (algebra.isGreater().call(diff, a))
					return false;
				return true;	
			}
			else { // sigB == 0
				algebra.negate().call(tol, tol);
				if (algebra.isLess().call(a, tol))
					return false;
				return true;
			}
		}
		else if (sigA > 0) {
			if (sigB < 0) {
				if (algebra.isGreater().call(a, tol))
					return false;
				algebra.negate().call(tol, tol);
				if (algebra.isLess().call(b, tol))
					return false;
				// if here then they are within 2 tol of each other
				algebra.add().call(a, tol, diff);
				if (algebra.isGreater().call(diff, b))
					return false;
				return true;	
			}
			else if (sigB > 0) {
				if (algebra.isLess().call(a, b))
					algebra.subtract().call(b, a, diff);
				else
					algebra.subtract().call(a, b, diff);
				if (algebra.isGreater().call(diff, tol))
					return false;
				return true;
			}
			else { // sigB == 0
				if (algebra.isGreater().call(a, tol))
					return false;
				return true;
			}
		}
		else { // sigA == 0
			if (sigB < 0) {
				algebra.negate().call(tol, tol);
				if (algebra.isLess().call(b, tol))
					return false;
				return true;
			}
			else if (sigB > 0) {
				if (algebra.isGreater().call(b, tol))
					return false;
				return true;
			}
			else { // sigB == 0
				// they both equal 0: they are within any nonnegative tolerance
				return true;
			}
		}
	}
}
