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

import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionCartesian {

	/**
	 * 
	 * @param r
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @param i0
	 * @param j0
	 * @param k0
	 * @param out
	 */
	public static void compute(double r, double i, double j, double k, double l, double i0, double j0, double k0, OctonionFloat64Member out) {

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
	 * @param r
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @param i0
	 * @param j0
	 * @param k0
	 * @param out
	 */
	public static void compute(Float64Member r, Float64Member i, Float64Member j, Float64Member k, Float64Member l, Float64Member i0, Float64Member j0, Float64Member k0, OctonionFloat64Member out) {
		
		compute(r.v(), i.v(), j.v(), k.v(), l.v(), i0.v(), j0.v(), k0.v(), out);
	
	}
}
