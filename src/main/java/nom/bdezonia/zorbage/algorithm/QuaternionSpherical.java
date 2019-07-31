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

import net.jafama.FastMath;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

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
	public static void compute(double rho, double theta, double phi1, double phi2, QuaternionFloat64Member out) {
		
		double tmpThC = FastMath.cos(theta);
		double tmpThS = FastMath.sin(theta);
		double tmpPhi1C = FastMath.cos(phi1);
		double tmpPhi1S = FastMath.sin(phi1);
		double tmpPhi2C = FastMath.cos(phi2);
		double tmpPhi2S = FastMath.sin(phi2);
		
		double r = tmpThC * tmpPhi1C * tmpPhi2C;
		double i = tmpThS * tmpPhi1C * tmpPhi2C;
		double j = tmpPhi1S * tmpPhi2C;
		double k = tmpPhi2S;
		
		r *= rho;
		i *= rho;
		j *= rho;
		k *= rho;
		
		out.setR(r);
		out.setI(i);
		out.setJ(j);
		out.setK(k);
	}

	/**
	 * 
	 * @param rho
	 * @param theta
	 * @param phi1
	 * @param phi2
	 * @param out
	 */
	public static void compute(Float64Member rho, Float64Member theta, Float64Member phi1, Float64Member phi2, QuaternionFloat64Member out) {
		
		compute(rho.v(), theta.v(), phi1.v(), phi2.v(), out);
	
	}

}
