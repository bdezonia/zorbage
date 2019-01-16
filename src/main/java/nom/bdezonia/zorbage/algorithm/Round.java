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
import nom.bdezonia.zorbage.type.algebra.ModularDivision;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;

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
	 * @param Algebra
	 * @param mode
	 * @param delta
	 * @param a
	 * @param b
	 */
	public static <T extends RingWithUnity<T,U> & ModularDivision<U> & Ordered<U> & AbsoluteValue<U>,U>
		void compute(T Algebra, Mode mode, U delta, U a, U b)
	{
		// For symmetry provide a NONE option. This simplifies algorithms
		// from having to check "if mode == NONE should I skip Round?".
		if (mode == Mode.NONE) {
			Algebra.assign().call(a,b);
			return;
		}
		U zero = Algebra.construct();
		if (Algebra.isLessEqual().call(delta,zero))
			throw new IllegalArgumentException("rounding error: delta must be > 0");
		U d = Algebra.construct();
		U m = Algebra.construct();
		U absM = Algebra.construct();
		U bTmp = Algebra.construct();
		U d1 = Algebra.construct();
		U m1 = Algebra.construct();
		U two = Algebra.construct();
		Algebra.unity().call(two);
		Algebra.add().call(two, two, two);
		Algebra.divMod().call(a, delta, d, m);
		Algebra.multiply().call(delta, d, bTmp);
		if (Algebra.isNotEqual().call(m, zero)) {
			switch (mode) {
				case NEGATIVE:
					if (Algebra.isEqual().call(bTmp, zero)) {
						if (Algebra.isLess().call(m, zero))
							Algebra.subtract().call(bTmp, delta, bTmp);
					}
					else if (Algebra.isLess().call(bTmp, zero))
						Algebra.subtract().call(bTmp, delta, bTmp);
					break;
				case POSITIVE:
					if (Algebra.isEqual().call(bTmp, zero)) {
						if (Algebra.isGreater().call(m, zero))
							Algebra.add().call(bTmp, delta, bTmp);
					}
					else if (Algebra.isGreater().call(bTmp, zero))
						Algebra.add().call(bTmp, delta, bTmp);
					break;
				case TOWARDS_ORIGIN:
					// nothing to do: modular math has already chopped b
					break;
				case AWAY_FROM_ORIGIN:
					if (Algebra.isEqual().call(bTmp, zero)) {
						if (Algebra.isGreater().call(m, zero))
							Algebra.add().call(bTmp, delta, bTmp);
						else
							Algebra.subtract().call(bTmp, delta, bTmp);
					}
					else if (Algebra.isGreater().call(bTmp, zero))
						Algebra.add().call(bTmp, delta, bTmp);
					else
						Algebra.subtract().call(bTmp, delta, bTmp);
					break;
				case HALF_UP:
					// towards the origin unless it's >= half and then away
					Algebra.abs().call(m, absM);
					Algebra.subtract().call(delta, absM, d1);
					if (Algebra.isGreater().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isGreater().call(m, zero))) {
						if (Algebra.isGreaterEqual().call(absM, d1))
							Algebra.add().call(bTmp, delta, bTmp);
					}
					else if (Algebra.isLess().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isLess().call(m, zero))) {
						if (Algebra.isGreaterEqual().call(absM, d1))
							Algebra.subtract().call(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_DOWN:
					// towards the origin unless it's > half and then away
					Algebra.abs().call(m, absM);
					Algebra.subtract().call(delta, absM, d1);
					if (Algebra.isGreater().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isGreater().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.add().call(bTmp, delta, bTmp);
					}
					else if (Algebra.isLess().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isLess().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.subtract().call(bTmp, delta, bTmp);
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_EVEN:
					// towards nearest boundary but prefer even result
					Algebra.abs().call(m, absM);
					Algebra.subtract().call(delta, absM, d1);
					if (Algebra.isGreater().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isGreater().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.add().call(bTmp, delta, bTmp);
						else if (Algebra.isEqual().call(absM, d1)) {
							// half case
							Algebra.mod().call(d, two, m1);
							// if is odd number of deltas from origin
							if (Algebra.isNotEqual().call(m1, zero)) {
								Algebra.add().call(bTmp, delta, bTmp);
							}
						}
					}
					else if (Algebra.isLess().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isLess().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.subtract().call(bTmp, delta, bTmp);
						else if (Algebra.isEqual().call(absM, d1)) {
							// half case
							Algebra.mod().call(d, two, m1);
							// if is odd number of deltas from origin
							if (Algebra.isNotEqual().call(m1, zero)) {
								Algebra.subtract().call(bTmp, delta, bTmp);
							}
						}
					}
					// else bTmp == 0 and no rounding needed
					break;
				case HALF_ODD:
					// towards nearest boundary but prefer odd result
					Algebra.abs().call(m, absM);
					Algebra.subtract().call(delta, absM, d1);
					if (Algebra.isGreater().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isGreater().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.add().call(bTmp, delta, bTmp);
						else if (Algebra.isEqual().call(absM, d1)) {
							// half case
							Algebra.mod().call(d, two, m1);
							// if is even number of deltas from origin
							if (Algebra.isEqual().call(m1, zero)) {
								Algebra.add().call(bTmp, delta, bTmp);
							}
						}
					}
					else if (Algebra.isLess().call(bTmp, zero) || (Algebra.isEqual().call(bTmp, zero) && Algebra.isLess().call(m, zero))) {
						if (Algebra.isGreater().call(absM, d1))
							Algebra.subtract().call(bTmp, delta, bTmp);
						else if (Algebra.isEqual().call(absM, d1)) {
							// half case
							Algebra.mod().call(d, two, m1);
							// if is even number of deltas from origin
							if (Algebra.isEqual().call(m1, zero)) {
								Algebra.subtract().call(bTmp, delta, bTmp);
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
		Algebra.assign().call(bTmp, b);
	}

}
