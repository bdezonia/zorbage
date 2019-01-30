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
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionCylindroSpherical {

	/**
	 * 
	 * @param r
	 * @param rad
	 * @param longitude
	 * @param latitude
	 * @param out
	 */
	public static void compute(double r, double rad, double longitude, double latitude, QuaternionFloat64Member out) {
		
		double tmpLngC = FastMath.cos(longitude);
		double tmpLngS = FastMath.sin(longitude);
		double tmpLatC = FastMath.cos(latitude);
		double tmpLatS = FastMath.sin(latitude);
		
		double i = rad * tmpLngC * tmpLatC;
		double j = rad * tmpLngS * tmpLatC;
		double k = rad * tmpLatS;
		
		out.setR(r);
		out.setI(i);
		out.setJ(j);
		out.setK(k);
	}

	/**
	 * 
	 * @param r
	 * @param rad
	 * @param longitude
	 * @param latitude
	 * @param out
	 */
	public static void compute(Float64Member r, Float64Member rad, Float64Member longitude, Float64Member latitude, QuaternionFloat64Member out) {
		
		compute(r.v(), rad.v(), longitude.v(), latitude.v(), out);
	
	}

}
