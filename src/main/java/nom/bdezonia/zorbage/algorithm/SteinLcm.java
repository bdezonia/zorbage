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

import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.EvenOdd;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * Least Common Multiple algorithm
 * 
 * @author Barry DeZonia
 *
 */
public class SteinLcm {
	
	// do not instantiate
	
	private SteinLcm() {}
	
	/**
	 * Calculate the least common multiple of two numbers (and number
	 * like constructions). Uses the Stein algorithm which is faster
	 * than other methods but requires more type constraints than other
	 * algorithms.
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> &  AbsoluteValue<U,U> & ScaleByOneHalf<U> & ScaleByTwo<U> &
			Addition<U> & Ordered<U> & EvenOdd<U> & Multiplication<U> & ModularDivision<U> &
			Unity<U> & Bounded<U>, U>
		void compute(T algebra, U a, U b, U result)
	{
		U gcd = algebra.construct();
		U a1 = algebra.construct();
		U b1 = algebra.construct();
		U tmp = algebra.construct();
		SteinGcd.compute(algebra, a, b, gcd);
		// try to avoid overflow by factoring out gcd before multiply
		algebra.div().call(a, gcd, a1);
		algebra.div().call(b, gcd, b1);
		algebra.multiply().call(a1, b1, tmp);
		algebra.abs().call(tmp, tmp);
		// now factor gcd back in
		algebra.multiply().call(tmp, gcd, result);
	}

}
