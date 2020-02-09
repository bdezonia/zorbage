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
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.SetQuaternion;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionSpherical {

	private QuaternionSpherical() { }
		
	/**
	 * 
	 * @param rho
	 * @param theta
	 * @param phi1
	 * @param phi2
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Trigonometric<U> & Multiplication<U>, U>
		void compute(T alg, U rho, U theta, U phi1, U phi2, SetQuaternion<U> out)
	{
		U tmpThC = alg.construct();
		U tmpThS = alg.construct();
		U tmpPhi1C = alg.construct();
		U tmpPhi1S = alg.construct();
		U tmpPhi2C = alg.construct();
		U tmpPhi2S = alg.construct();
		
		alg.sinAndCos().call(theta, tmpThS, tmpThC);
		alg.sinAndCos().call(phi1, tmpPhi1S, tmpPhi1C);
		alg.sinAndCos().call(phi2, tmpPhi2S, tmpPhi2C);

		U r = alg.construct();
		U i = alg.construct();
		U j = alg.construct();
		U k = alg.construct();
		
		alg.multiply().call(tmpThC, tmpPhi1C, r);
		alg.multiply().call(r, tmpPhi2C, r);

		alg.multiply().call(tmpThS, tmpPhi1C, i);
		alg.multiply().call(i, tmpPhi2C, i);

		alg.multiply().call(tmpPhi1S, tmpPhi2C, j);
		
		alg.assign().call(tmpPhi2S, k);

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
