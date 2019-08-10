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
import nom.bdezonia.zorbage.type.algebra.SetOctonion;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionSpherical {

	private OctonionSpherical() { }
		
	/**
	 * 
	 * @param rho
	 * @param theta
	 * @param phi1
	 * @param phi2
	 * @param phi3
	 * @param phi4
	 * @param phi5
	 * @param phi6
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Trigonometric<U> & Multiplication<U>, U>
		void compute(T alg, U rho, U theta, U phi1, U phi2, U phi3, U phi4, U phi5, U phi6, SetOctonion<U> out)
	{
		U tmpThC = alg.construct();
		U tmpThS = alg.construct();
		U tmpPhi1C = alg.construct();
		U tmpPhi1S = alg.construct();
		U tmpPhi2C = alg.construct();
		U tmpPhi2S = alg.construct();
		U tmpPhi3C = alg.construct();
		U tmpPhi3S = alg.construct();
		U tmpPhi4C = alg.construct();
		U tmpPhi4S = alg.construct();
		U tmpPhi5C = alg.construct();
		U tmpPhi5S = alg.construct();
		U tmpPhi6C = alg.construct();
		U tmpPhi6S = alg.construct();
		
		alg.sinAndCos().call(theta, tmpThS, tmpThC);
		alg.sinAndCos().call(phi1, tmpPhi1S, tmpPhi1C);
		alg.sinAndCos().call(phi2, tmpPhi2S, tmpPhi2C);
		alg.sinAndCos().call(phi3, tmpPhi3S, tmpPhi3C);
		alg.sinAndCos().call(phi4, tmpPhi4S, tmpPhi4C);
		alg.sinAndCos().call(phi5, tmpPhi5S, tmpPhi5C);
		alg.sinAndCos().call(phi6, tmpPhi6S, tmpPhi6C);

		U r = alg.construct();
		U i = alg.construct();
		U j = alg.construct();
		U k = alg.construct();
		U l = alg.construct();
		U i0 = alg.construct();
		U j0 = alg.construct();
		U k0 = alg.construct();
		
		alg.assign().call(tmpPhi6S, k0);
		
		alg.multiply().call(tmpPhi5S, tmpPhi6C, j0);

		alg.multiply().call(tmpPhi4S, tmpPhi5C, i0);
		alg.multiply().call(i0, tmpPhi6C, i0);

		alg.multiply().call(tmpPhi3S, tmpPhi4C, l);
		alg.multiply().call(l, tmpPhi5C, l);
		alg.multiply().call(l, tmpPhi6C, l);

		alg.multiply().call(tmpPhi2S, tmpPhi3C, k);
		alg.multiply().call(k, tmpPhi4C, k);
		alg.multiply().call(k, tmpPhi5C, k);
		alg.multiply().call(k, tmpPhi6C, k);

		alg.multiply().call(tmpPhi1S, tmpPhi2C, j);
		alg.multiply().call(j, tmpPhi3C, j);
		alg.multiply().call(j, tmpPhi4C, j);
		alg.multiply().call(j, tmpPhi5C, j);
		alg.multiply().call(j, tmpPhi6C, j);

		alg.multiply().call(tmpThS, tmpPhi1C, i);
		alg.multiply().call(i, tmpPhi2C, i);
		alg.multiply().call(i, tmpPhi3C, i);
		alg.multiply().call(i, tmpPhi4C, i);
		alg.multiply().call(i, tmpPhi5C, i);
		alg.multiply().call(i, tmpPhi6C, i);
		
		alg.multiply().call(tmpThC, tmpPhi1C, r);
		alg.multiply().call(r, tmpPhi2C, r);
		alg.multiply().call(r, tmpPhi3C, r);
		alg.multiply().call(r, tmpPhi4C, r);
		alg.multiply().call(r, tmpPhi5C, r);
		alg.multiply().call(r, tmpPhi6C, r);
		
		alg.multiply().call(r, rho, r);
		alg.multiply().call(i, rho, i);
		alg.multiply().call(j, rho, j);
		alg.multiply().call(k, rho, k);
		alg.multiply().call(l, rho, l);
		alg.multiply().call(i0, rho, i0);
		alg.multiply().call(j0, rho, j0);
		alg.multiply().call(k0, rho, k0);
		
		out.setR(r);
		out.setI(i);
		out.setJ(j);
		out.setK(k);
		out.setL(l);
		out.setI0(i0);
		out.setJ0(j0);
		out.setK0(k0);
	}

}
