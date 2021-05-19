/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RModuleMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleHermitianProduct {

	// do not instantiate
	
	private RModuleHermitianProduct() {}
	
	/**
	 * Calculates the Hermitian product between two rmodule/vectors and sets the
	 * result number for further processing.
	 * 
 	 * @param <T>
	 * @param <U>
	 * @param <W>
	 * @param algU
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U> & Conjugate<U> & Multiplication<U> & Addition<U>,
					U,
					W extends RModuleMember<U>>
		void compute(T algU, W a, W b, U c)
	{
		long len = a.length();
		if (b.length() != len)
			throw new IllegalArgumentException("misshapen arguments to RModuleHermitianProduct");
		U sum = algU.construct();
		U tmp = algU.construct();
		U aVal = algU.construct();
		U bVal = algU.construct();
		for (long i = 0; i < len; i++) {
			a.getV(i, aVal);
			b.getV(i, bVal);
			algU.conjugate().call(aVal, aVal);
			algU.multiply().call(aVal, bVal, tmp);
			algU.add().call(sum, tmp, sum);
		}
		algU.assign().call(sum, c);
	}
}
