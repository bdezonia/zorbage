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

import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Round {

	/**
	 * 
	 */
	public enum Mode {
		NONE, NEGATIVE, POSITIVE, TOWARDS_ORIGIN, AWAY_FROM_ORIGIN,
		HALF_UP, HALF_DOWN, HALF_EVEN, HALF_ODD, EXACT
	}

	// do not instantiate
	
	private Round() {}

	/**
	 * Round.compute()
	 * 
	 * @param algebra
	 * @param mode
	 * @param delta
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Unity<U> & ModularDivision<U> & Ordered<U> & AbsoluteValue<U>,U>
		void compute(T algebra, Mode mode, U delta, U a, U b)
	{
		// For symmetry provide a NONE option. This simplifies algorithms
		// from having to check "if mode == NONE should I skip Round?".
		if (mode == Mode.NONE) {
			algebra.assign().call(a, b);
			return;
		}
		U zero = algebra.construct();
		if (algebra.isLessEqual().call(delta,zero))
			throw new IllegalArgumentException("rounding error: delta must be > 0");
		U d = algebra.construct();
		U m = algebra.construct();
		U absM = algebra.construct();
		U bTmp = algebra.construct();
		U d1 = algebra.construct();
		U m1 = algebra.construct();
		U two = algebra.construct();
		algebra.unity().call(two);
		algebra.add().call(two, two, two);
		algebra.divMod().call(a, delta, d, m);
		algebra.multiply().call(delta, d, bTmp);
		if (algebra.isNotEqual().call(m, zero)) {
			switch (mode) {
				case NEGATIVE:
					if (algebra.isEqual().call(bTmp, zero)) {
						if (algebra.isLess().call(m, zero))
							algebra.subtract().call(bTmp, delta, bTmp);
					}
					else if (algebra.isLess().call(bTmp, zero))
						algebra.subtract().call(bTmp, delta, bTmp);
					break;
				case POSITIVE:
					if (algebra.isEqual().call(bTmp, zero)) {
						if (algebra.isGreater().call(m, zero))
							algebra.add().call(bTmp, delta, bTmp);
					}
					else if (algebra.isGreater().call(bTmp, zero))
						algebra.add().call(bTmp, delta, bTmp);
					break;
				case TOWARDS_ORIGIN:
					// nothing to do: modular math has already chopped b
					break;
				case AWAY_FROM_ORIGIN:
					if (algebra.isEqual().call(bTmp, zero)) {
						if (algebra.isGreater().call(m, zero))
							algebra.add().call(bTmp, delta, bTmp);
						else
							algebra.subtract().call(bTmp, delta, bTmp);
					}
					else if (algebra.isGreater().call(bTmp, zero))
						algebra.add().call(bTmp, delta, bTmp);
					else
						algebra.subtract().call(bTmp, delta, bTmp);
					break;
				case HALF_UP:
					// towards the origin unless it's >= half and then away
					algebra.abs().call(m, absM);
					algebra.subtract().call(delta, absM, d1);
					if (algebra.isGreater().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isGreater().call(m, zero))) {
						if (algebra.isGreaterEqual().call(absM, d1))
							algebra.add().call(bTmp, delta, bTmp);
					}
					else if (algebra.isLess().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isLess().call(m, zero))) {
						if (algebra.isGreaterEqual().call(absM, d1))
							algebra.subtract().call(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_DOWN:
					// towards the origin unless it's > half and then away
					algebra.abs().call(m, absM);
					algebra.subtract().call(delta, absM, d1);
					if (algebra.isGreater().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isGreater().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.add().call(bTmp, delta, bTmp);
					}
					else if (algebra.isLess().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isLess().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.subtract().call(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_EVEN:
					// towards nearest boundary but prefer even result
					algebra.abs().call(m, absM);
					algebra.subtract().call(delta, absM, d1);
					if (algebra.isGreater().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isGreater().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.add().call(bTmp, delta, bTmp);
						else if (algebra.isEqual().call(absM, d1)) {
							// half case
							algebra.mod().call(d, two, m1);
							// if is odd number of deltas from origin
							if (algebra.isNotEqual().call(m1, zero)) {
								algebra.add().call(bTmp, delta, bTmp);
							}
						}
					}
					else if (algebra.isLess().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isLess().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.subtract().call(bTmp, delta, bTmp);
						else if (algebra.isEqual().call(absM, d1)) {
							// half case
							algebra.mod().call(d, two, m1);
							// if is odd number of deltas from origin
							if (algebra.isNotEqual().call(m1, zero)) {
								algebra.subtract().call(bTmp, delta, bTmp);
							}
						}
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_ODD:
					// towards nearest boundary but prefer odd result
					algebra.abs().call(m, absM);
					algebra.subtract().call(delta, absM, d1);
					if (algebra.isGreater().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isGreater().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.add().call(bTmp, delta, bTmp);
						else if (algebra.isEqual().call(absM, d1)) {
							// half case
							algebra.mod().call(d, two, m1);
							// if is even number of deltas from origin
							if (algebra.isEqual().call(m1, zero)) {
								algebra.add().call(bTmp, delta, bTmp);
							}
						}
					}
					else if (algebra.isLess().call(bTmp, zero) || (algebra.isEqual().call(bTmp, zero) && algebra.isLess().call(m, zero))) {
						if (algebra.isGreater().call(absM, d1))
							algebra.subtract().call(bTmp, delta, bTmp);
						else if (algebra.isEqual().call(absM, d1)) {
							// half case
							algebra.mod().call(d, two, m1);
							// if is even number of deltas from origin
							if (algebra.isEqual().call(m1, zero)) {
								algebra.subtract().call(bTmp, delta, bTmp);
							}
						}
					}
					// else bTmp == 0 and no rounding needed
					break;
				case EXACT:
					throw new ArithmeticException("exact rounding check failed");
				default:
					if (mode != Mode.NONE)
						throw new IllegalArgumentException(
								"Unknown rounding mode: "+mode);
			}
		}
		algebra.assign().call(bTmp, b);
	}

}
