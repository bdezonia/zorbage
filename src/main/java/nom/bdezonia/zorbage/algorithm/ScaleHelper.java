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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ScaleHelper {

	// do not instantiate
	
	private ScaleHelper() { }
	
	/**
	 * ScaleHelper is a utility method that simplifies scaling a list
	 * of values by a number raised to a power.
	 *  
	 * @param typeAlg
	 * @param factorAlg
	 * @param num
	 * @param numTimes
	 * @param in
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Scale<U,W>,
					U,
					V extends Algebra<V,W> & Unity<W> & Multiplication<W>,
					W>
		void compute(T typeAlg, V factorAlg, W num, int numTimes, U in, U out)
	{
		// cannot scale a negative number of times
		
		if (numTimes < 0)
			throw new IllegalArgumentException("negative power count in ScaleHelper");
		
		// scale by power 0: scale by 1: copy input to output
		
		if (numTimes == 0) {
			typeAlg.assign().call(in, out);
			return;
		}
		
		// compute the scale factor: factor = num ^ numTimes
		
		W factor = factorAlg.construct();
		factorAlg.unity().call(factor);
		for (int i = 0; i < numTimes; i++) {
			factorAlg.multiply().call(factor, num, factor);
		}
		
		// scale the input by the scale factor

		typeAlg.scale().call(factor, in, out);
	}
}
