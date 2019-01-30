/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
public class OctonionMultiPolar {

	/**
	 * 
	 * @param rho1
	 * @param theta1
	 * @param rho2
	 * @param theta2
	 * @param rho3
	 * @param theta3
	 * @param rho4
	 * @param theta4
	 * @param out
	 */
	public static void compute(double rho1, double theta1, double rho2, double theta2, double rho3, double theta3, double rho4, double theta4, OctonionFloat64Member out) {
		
		double tmpTh1C = FastMath.cos(theta1);
		double tmpTh1S = FastMath.sin(theta1);
		double tmpTh2C = FastMath.cos(theta2);
		double tmpTh2S = FastMath.sin(theta2);
		double tmpTh3C = FastMath.cos(theta3);
		double tmpTh3S = FastMath.sin(theta3);
		double tmpTh4C = FastMath.cos(theta4);
		double tmpTh4S = FastMath.sin(theta4);
		
		double r = rho1 * tmpTh1C;
		double i = rho1 * tmpTh1S;
		double j = rho2 * tmpTh2C;
		double k = rho2 * tmpTh2S;
		double l = rho3 * tmpTh3C;
		double i0 = rho3 * tmpTh3S;
		double j0 = rho4 * tmpTh4C;
		double k0 = rho4 * tmpTh4S;
		
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
	 * @param rho1
	 * @param theta1
	 * @param rho2
	 * @param theta2
	 * @param rho3
	 * @param theta3
	 * @param rho4
	 * @param theta4
	 * @param out
	 */
	public static void compute(Float64Member rho1, Float64Member theta1, Float64Member rho2, Float64Member theta2, Float64Member rho3, Float64Member theta3, Float64Member rho4, Float64Member theta4, OctonionFloat64Member out) {
		
		compute(rho1.v(), theta1.v(), rho2.v(), theta2.v(), rho3.v(), theta3.v(), rho4.v(), theta4.v(), out);
	
	}

}
