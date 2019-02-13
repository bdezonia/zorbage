/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;

//Adapted from an algorithm as published by Stepanov and Rose 2015.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DivMod {

	/**
	 * 
	 * @param alg
	 * @param a
	 * @param b
	 * @param d
	 * @param m
	 */
	public static <T extends Algebra<T,U> & Ordered<U> & Unity<U> & Addition<U> & BitOperations<U>,U>
		void compute(T alg, U a, U b, U d, U m)
	{
		// TODO: need code that can handle -minint cases without negate() called
		
		
		if (alg.isZero().call(b))
			throw new IllegalArgumentException("divide by zero");
		
		// make sure inputs are positive. this code only applies to signed types.
		// unsigned types just pass through.
		
		U zero = alg.construct();

		boolean aNeg;
		boolean bNeg;
		U aPos = alg.construct();
		U bPos = alg.construct();
		if (alg.isLess().call(a, zero)) {
			aNeg = true;
			alg.negate().call(a, aPos);
		}
		else {
			aNeg = false;
			alg.assign().call(a, aPos);
		}
		if (alg.isLess().call(b, zero)) {
			bNeg = true;
			alg.negate().call(b, bPos);
		}
		else {
			bNeg = false;
			alg.assign().call(b, bPos);
		}

		if (alg.isLess().call(aPos, bPos)) 
		{
			// TODO : this is probably wrong. Fix.
			// flip signs?
			//if (aNeg != bNeg)
			//	alg.negate().call(aPos, aPos);
			alg.assign().call(zero, d);
			alg.assign().call(aPos, m);
			return;
		}
		U c = alg.construct();
		largestDoubling(alg,aPos,bPos,c);
		U one = alg.construct();
		alg.unity().call(one);
		U n = alg.construct(one);
		alg.subtract().call(aPos, c, aPos);
		while (!alg.isEqual().call(c,bPos)) {
			alg.bitShiftRight().call(1, c, c);
			alg.add().call(n, n, n);
			if (alg.isLessEqual().call(c, aPos)) {
				alg.subtract().call(aPos, c, aPos);
				alg.add().call(n, one, n);
			}
		}
		// TODO : this is probably wrong. Fix.
		// flip signs?
		//if (aNeg != bNeg)
		//	alg.negate().call(aPos, aPos);
		alg.assign().call(n, d);
		alg.assign().call(aPos, m);
	}
	
	private static <T extends Algebra<T,U> & Ordered<U> & Addition<U>,U>
		void largestDoubling(T alg, U a, U b, U c)
	{
		U tmpB = alg.construct(b);
		U diff = alg.construct();
		alg.subtract().call(a, tmpB, diff);
		while (alg.isGreaterEqual().call(diff, tmpB)) {
			alg.add().call(tmpB, tmpB, tmpB);
			alg.subtract().call(a, tmpB, diff);
		}
		alg.assign().call(tmpB, c);
	}
}
