/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PrincipalComplexRootOfUnity {

	// do not instantiate
	
	private PrincipalComplexRootOfUnity() {}
	
	// TODO: for values of m very large this math will break down

	/**
	 * Compute the principal root of unity given the order m.
	 * @param m Order of principal root.
	 * @param principalRoot Output value.
	 */
	public static
		void compute(long m, ComplexFloat64Member principalRoot)
	{
		ComplexFloat64Member e = G.CDBL.construct();
		ComplexFloat64Member power = G.CDBL.construct();
		ComplexFloat64Member two = G.CDBL.construct();
		ComplexFloat64Member I = G.CDBL.construct();
		ComplexFloat64Member M = G.CDBL.construct();
		two.setR(2);
		I.setI(1);
		M.setR(m);
		G.CDBL.E(e);
		G.CDBL.PI(power);
		G.CDBL.multiply(power, two, power);
		G.CDBL.multiply(power, I, power);
		G.CDBL.divide(power, M, power);
		G.CDBL.pow(e, power, principalRoot);
	}
}
