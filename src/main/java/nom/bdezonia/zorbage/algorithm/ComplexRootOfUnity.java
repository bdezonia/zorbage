/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexRootOfUnity {

	// do not instantiate
	
	private ComplexRootOfUnity() {}
	
	/**
	 * Compute a root of unity given the order m and suborder n.
	 * @param m Order of the root. The m'th root of unity.
	 * @param n Suborder of the root. 1 LE n LE m.
	 * @param root Output value.
	 */
	public static
		void compute(int m, int n, ComplexFloat64Member root)
	{
		if (n < 1 || m < 1)
			throw new IllegalArgumentException("arguments must be positive");
		if (n > m)
			throw new IllegalArgumentException("n outside bounds of [1,m]: n = "+n+" m = "+m);
		ComplexFloat64Member e = G.CDBL.construct();
		ComplexFloat64Member power = G.CDBL.construct();
		ComplexFloat64Member two = G.CDBL.construct();
		ComplexFloat64Member I = G.CDBL.construct();
		ComplexFloat64Member M = G.CDBL.construct();
		ComplexFloat64Member N = G.CDBL.construct();
		two.setR(2);
		I.setI(1);
		M.setR(m);
		N.setR(n);
		G.CDBL.E().call(e);
		G.CDBL.PI().call(power);
		G.CDBL.multiply().call(power, two, power);
		G.CDBL.multiply().call(power, I, power);
		G.CDBL.divide().call(power, M, power);
		G.CDBL.multiply().call(power, N, power);
		G.CDBL.pow().call(e, power, root);
	}
}
