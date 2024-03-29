/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.EvenOdd;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.Unity;

// Adapted from an algorithm as published by Stepanov and Rose 2015.
// I believe it is a Russian Peasant approach.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Multiply {
	
	// do not instantiate
	
	private Multiply() { }
	
	/**
	 * Multiply is an algorithm for multiplying many kinds of types.
	 * In this code z = x * y.
	 *  
	 * @param algebra
	 * @param x
	 * @param y
	 * @param z
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U> & EvenOdd<U> &
						ScaleByOneHalf<U> & Bounded<U>,
					U>
		void compute(T algebra, U x, U y, U z)
	{
		// optimize zero calculations away
		
		U zero = algebra.construct();
		if (algebra.isZero().call(x) || algebra.isZero().call(y)) {
			algebra.assign().call(zero, z);
			return;
		}
		
		// This part of the code kind of assumes that the passed in type is a
		// modular integer
		
		U min = algebra.construct();
		algebra.minBound().call(min);
		if (algebra.isEqual().call(x, min)) {
			if (algebra.isOdd().call(y))
				algebra.assign().call(min, z);
			else
				algebra.assign().call(zero, z);
			return;
		}
		if (algebra.isEqual().call(y, min)) {
			if (algebra.isOdd().call(x))
				algebra.assign().call(min, z);
			else
				algebra.assign().call(zero, z);
			return;
		}

		// make sure inputs are positive. this code only applies to signed types.
		// unsigned types just pass through.
		
		boolean xNeg;
		boolean yNeg;
		U xPos = algebra.construct();
		U yPos = algebra.construct();
		if (algebra.isLess().call(x, zero)) {
			xNeg = true;
			algebra.negate().call(x, xPos);
		}
		else {
			xNeg = false;
			algebra.assign().call(x, xPos);
		}
		if (algebra.isLess().call(y, zero)) {
			yNeg = true;
			algebra.negate().call(y, yPos);
		}
		else {
			yNeg = false;
			algebra.assign().call(y, yPos);
		}

		// swap terms so that fastest form of input is used
		
		if (algebra.isGreater().call(xPos, yPos)) {
			Swap.compute(algebra, xPos, yPos);
		}
		
		// 1st Stepanov algorithm inline
		
		U one = algebra.construct();
		algebra.unity().call(one);
		while (!algebra.isOdd().call(xPos)) {
			algebra.add().call(yPos, yPos, yPos);
			algebra.scaleByOneHalf().call(1, xPos, xPos);
		}
		if (algebra.isEqual().call(one, xPos)) {
			algebra.assign().call(yPos, z);
			if (xNeg != yNeg) {
				// flip the result's sign once
				algebra.subtract().call(zero, z, z);
			}
			return;
		}
		
		// 2nd Stepanov algorithm inline
		
		U r = algebra.construct(yPos);
		U n = algebra.construct(xPos);
		algebra.subtract().call(n, one, n);
		algebra.scaleByOneHalf().call(1, n, n);
		U a = algebra.construct(yPos);
		algebra.add().call(a, a, a);
		while (true) {
			if (algebra.isOdd().call(n)) {
				algebra.add().call(r, a, r);
				if (algebra.isEqual().call(one, n)) {
					algebra.assign().call(r, z);
					if (xNeg != yNeg) {
						// flip the result's sign once
						algebra.subtract().call(zero, z, z);
					}
					return;
				}
			}
			algebra.scaleByOneHalf().call(1, n, n);
			algebra.add().call(a, a, a);
		}
	}

}
