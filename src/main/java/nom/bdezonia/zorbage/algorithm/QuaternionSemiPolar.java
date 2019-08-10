/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.SetQuaternion;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionSemiPolar {

	private QuaternionSemiPolar() { }
	
	/**
	 * 
	 * @param rho
	 * @param alpha
	 * @param theta1
	 * @param theta2
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Trigonometric<U> & Multiplication<U>, U>
		void compute(T alg, U rho, U alpha, U theta1, U theta2, SetQuaternion<U> out)
	{
		U tmpAlC = alg.construct();
		U tmpAlS = alg.construct();
		U tmpTh1C = alg.construct();
		U tmpTh1S = alg.construct();
		U tmpTh2C = alg.construct();
		U tmpTh2S = alg.construct();
		
		alg.sinAndCos().call(alpha, tmpAlS, tmpAlC);
		alg.sinAndCos().call(theta1, tmpTh1S, tmpTh1C);
		alg.sinAndCos().call(theta2, tmpTh2S, tmpTh2C);

		U r = alg.construct();
		U i = alg.construct();
		U j = alg.construct();
		U k = alg.construct();
		
		alg.multiply().call(tmpAlC, tmpTh1C, r);
		alg.multiply().call(tmpAlC, tmpTh1S, i);
		alg.multiply().call(tmpAlS, tmpTh2C, j);
		alg.multiply().call(tmpAlS, tmpTh2S, k);

		alg.multiply().call(r, rho, r);
		alg.multiply().call(i, rho, i);
		alg.multiply().call(j, rho, j);
		alg.multiply().call(k, rho, k);
		
		out.setR(r);
		out.setI(i);
		out.setJ(j);
		out.setK(k);
	}

}
