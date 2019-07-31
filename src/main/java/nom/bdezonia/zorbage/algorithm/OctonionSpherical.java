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
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

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
	public static void compute(double rho, double theta, double phi1, double phi2, double phi3, double phi4, double phi5, double phi6, OctonionFloat64Member out) {
		
		double tmpThC = FastMath.cos(theta);
		double tmpThS = FastMath.sin(theta);
		double tmpPhi1C = FastMath.cos(phi1);
		double tmpPhi1S = FastMath.sin(phi1);
		double tmpPhi2C = FastMath.cos(phi2);
		double tmpPhi2S = FastMath.sin(phi2);
		double tmpPhi3C = FastMath.cos(phi3);
		double tmpPhi3S = FastMath.sin(phi3);
		double tmpPhi4C = FastMath.cos(phi4);
		double tmpPhi4S = FastMath.sin(phi4);
		double tmpPhi5C = FastMath.cos(phi5);
		double tmpPhi5S = FastMath.sin(phi5);
		double tmpPhi6C = FastMath.cos(phi6);
		double tmpPhi6S = FastMath.sin(phi6);
		
		double k0 = tmpPhi6S;
		double j0 = tmpPhi5S * tmpPhi6C;
		double i0 = tmpPhi4S * tmpPhi5C * tmpPhi6C;
		double l = tmpPhi3S * tmpPhi4C * tmpPhi5C * tmpPhi6C;
		double k = tmpPhi2S * tmpPhi3C * tmpPhi4C * tmpPhi5C * tmpPhi6C;
		double j = tmpPhi1S * tmpPhi2C * tmpPhi3C * tmpPhi4C * tmpPhi5C * tmpPhi6C;
		double i = tmpThS * tmpPhi1C * tmpPhi2C * tmpPhi3C * tmpPhi4C * tmpPhi5C * tmpPhi6C;
		double r = tmpThC * tmpPhi1C * tmpPhi2C * tmpPhi3C * tmpPhi4C * tmpPhi5C * tmpPhi6C;
		
		r *= rho;
		i *= rho;
		j *= rho;
		k *= rho;
		l *= rho;
		i0 *= rho;
		j0 *= rho;
		k0 *= rho;
		
		out.setR(r);
		out.setI(i);
		out.setJ(j);
		out.setK(k);
		out.setL(l);
		out.setI0(i0);
		out.setJ0(j0);
		out.setK0(k0);
	}

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
	public static void compute(Float64Member rho, Float64Member theta, Float64Member phi1, Float64Member phi2, Float64Member phi3, Float64Member phi4, Float64Member phi5, Float64Member phi6, OctonionFloat64Member out) {
		
		compute(rho.v(), theta.v(), phi1.v(), phi2.v(), phi3.v(), phi4.v(), phi5.v(), phi6.v(), out);
	
	}

}
