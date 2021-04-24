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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorPower {

	// don't instantiate
	
	private TensorPower() { }
	
	/**
	 * 
	 * @param <S>
	 * @param <TENSOR>
	 * @param tensAlg
	 * @param power
	 * @param a
	 * @param b
	 */
	public static <S extends Algebra<S,TENSOR> & Multiplication<TENSOR> & Unity<TENSOR>, TENSOR extends TensorMember<?>>
		void compute(S tensAlg, int power, TENSOR a, TENSOR b)
	{
		// TODO - make much more efficient by copying style of MatrixMultiply algorithm
		
		if (power < 0) {
			// TODO: is this a valid limitation?
			throw new IllegalArgumentException("negative powers not supported");
		}
		else if (power == 0) {
			if (tensAlg.isZero().call(a)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			TensorShape.compute(a, b); // set the shape of result
			tensAlg.unity().call(b); // and make it have value 1
		}
		else if (power == 1) {
			tensAlg.assign().call(a, b);
		}
		else {
			TENSOR tmp1 = tensAlg.construct();
			TENSOR tmp2 = tensAlg.construct();
			tensAlg.multiply().call(a, a, tmp1);
			for (int i = 2; i < (power/2)*2; i += 2) {
				tensAlg.multiply().call(tmp1, a, tmp2);
				tensAlg.multiply().call(tmp2, a, tmp1);
			}
			// an odd power
			if (power > 2 && (power&1)==1) {
				tensAlg.assign().call(tmp1, tmp2);
				tensAlg.multiply().call(tmp2, a, tmp1);
			}
			tensAlg.assign().call(tmp1, b);
		}
	}

}
