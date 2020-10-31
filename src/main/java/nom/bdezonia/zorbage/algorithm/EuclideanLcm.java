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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Settable;

/**
 * Least Common Multiple algorithm
 * 
 * @author Barry DeZonia
 *
 */
public class EuclideanLcm {

	// do not instantiate
	
	private EuclideanLcm() { }
	
	/**
	 * 
	 * @param uAlg
	 * @param wAlg
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> & ModularDivision<U> & Norm<U,W> & Multiplication<U>,
					U extends Settable<U>,
					V extends Algebra<V,W> & Ordered<W>,
					W extends Settable<W>>
		void compute(T uAlg, V wAlg, U a, U b, U result)
	{
		U gcd = uAlg.construct();
		U a1 = uAlg.construct();
		U b1 = uAlg.construct();
		U tmp = uAlg.construct();
		
		EuclideanGcd.compute(uAlg, wAlg, a, b, gcd);
		
		// try to avoid overflow by factoring out gcd before multiply
		
		uAlg.div().call(a, gcd, a1);
		uAlg.div().call(b, gcd, b1);
		uAlg.multiply().call(a1, b1, tmp);
		
		//  the stein code had this. do I need it anymore?
		//    uAlg.abs().call(tmp, tmp);
		
		// now factor gcd back in
		uAlg.multiply().call(tmp, gcd, result);
	}
}