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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Dimensioned;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorPower {

	// don't instantiate
	
	private TensorPower() { }
	
	public static <T extends Algebra<T,U> & Multiplication<U> & Unity<U>, U extends Dimensioned & TensorMember<?>>
		void compute(T alg, int power, U a, U b)
	{
		// TODO - make much more efficient by copying style of MatrixMultiply algorithm
		
		if (power < 0) {
			// TODO: is this a valid limitation?
			throw new IllegalArgumentException("negative powers not supported");
		}
		else if (power == 0) {
			if (alg.isZero().call(a)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			TensorShape.compute(a, b); // set the shape of result
			alg.unity().call(b); // and make it have value 1
		}
		else if (power == 1) {
			alg.assign().call(a, b);
		}
		else {
			U tmp1 = alg.construct();
			U tmp2 = alg.construct();
			alg.multiply().call(a, a, tmp1);
			for (int i = 2; i < (power/2)*2; i += 2) {
				alg.multiply().call(tmp1, a, tmp2);
				alg.multiply().call(tmp2, a, tmp1);
			}
			// an odd power
			if (power > 2 && (power&1)==1) {
				alg.assign().call(tmp1, tmp2);
				alg.multiply().call(tmp2, a, tmp1);
			}
			alg.assign().call(tmp1, b);
		}
	}

}
