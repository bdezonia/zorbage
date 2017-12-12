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

import nom.bdezonia.zorbage.type.algebra.IntegralDivision;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Ring;

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
	 * @param group
	 * @param mode
	 * @param delta
	 * @param a
	 * @param b
	 */
	public static <T extends Ring<T,U> & IntegralDivision<U> & Ordered<U>,U>
		void compute(T group, Mode mode, U delta, U a, U b)
	{
		// For symmetry provide a NONE option. This simplifies algorithms
		// from having to check if mode == NONE should I skip Round.
		if (mode == Mode.NONE) {
			group.assign(a,b);
			return;
		}
		U zero = group.construct();
		if (group.isLess(delta,zero))
			throw new IllegalArgumentException("rounding error: delta must be nonnegative");
		U d = group.construct();
		U m = group.construct();
		U bTmp = group.construct();
		U d1 = group.construct();
		group.divMod(a, delta, d, m);
		group.multiply(delta, d, bTmp);
		if (group.isNotEqual(m, zero)) {
			switch (mode) {
				case NEGATIVE:
					group.subtract(bTmp, delta, bTmp);
					break;
				case POSITIVE:
					group.add(bTmp, delta, bTmp);
					break;
				case TOWARDS_ORIGIN:
					if (group.isGreater(bTmp, zero))
						group.subtract(bTmp, delta, bTmp);
					else
						group.add(bTmp, delta, bTmp);
					break;
				case AWAY_FROM_ORIGIN:
					if (group.isGreater(bTmp, zero))
						group.add(bTmp, delta, bTmp);
					else
						group.subtract(bTmp, delta, bTmp);
					break;
				case HALF_UP:
					// towards the origin unless it's >= half and then away
					group.subtract(delta, m, d1);
					if (group.isGreater(bTmp, zero)) {
						if (group.isGreaterEqual(m, d1))
							group.add(bTmp, delta, bTmp);
					}
					else if (group.isLess(bTmp, zero)) {
						if (group.isGreaterEqual(m, d1))
							group.subtract(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_DOWN:
					// towards the origin unless it's > half and then away
					group.subtract(delta, m, d1);
					if (group.isGreater(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.add(bTmp, delta, bTmp);
					}
					else if (group.isLess(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.subtract(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_EVEN:
					// towards nearest boundary but prefer even result
					if (group.isGreater(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.add(bTmp, delta, bTmp);
						else if (group.isEqual(m, d1)) {
							// half case
							if (group.isOdd(bTmp)) {
								group.add(bTmp, delta, bTmp);
							}
						}
					}
					else if (group.isLess(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.subtract(bTmp, delta, bTmp);
						else if (group.isEqual(m, d1)) {
							// half case
							if (group.isOdd(bTmp)) {
								group.subtract(bTmp, delta, bTmp);
							}
						}
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_ODD:
					// towards nearest boundary but prefer odd result
					if (group.isGreater(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.add(bTmp, delta, bTmp);
						else if (group.isEqual(m, d1)) {
							// half case
							if (group.isEven(bTmp)) {
								group.add(bTmp, delta, bTmp);
							}
						}
					}
					else if (group.isLess(bTmp, zero)) {
						if (group.isGreater(m, d1))
							group.subtract(bTmp, delta, bTmp);
						else if (group.isEqual(m, d1)) {
							// half case
							if (group.isEven(bTmp)) {
								group.subtract(bTmp, delta, bTmp);
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
		group.assign(bTmp, b);
	}

}
