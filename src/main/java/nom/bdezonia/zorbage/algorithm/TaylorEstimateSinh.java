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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TaylorEstimateSinh {

	private TaylorEstimateSinh() {
		
	}
	
	/**
	 * 
	 * @param group
	 * @param one
	 * @param x
	 * @param result
	 */
	
	public static <T extends Group<T,U> & Unity<U> & Addition<U> & Multiplication<U> & Invertible<U>,
					U,
					V extends Group<V,W> & Addition<W> & Multiplication<W> & Scale<W, U>,
					W extends MatrixMember<U>>
		void compute(int numTerms, V matGroup, T numGroup, W x, W result)
	{
		if (numTerms < 1)
			throw new IllegalArgumentException("estimation requires 1 or more terms");

		// sin(x) = x + x^3/3! + x^5/5! + x^7/7! ...
		
		W sum = matGroup.construct();
		sum.alloc(x.rows(), x.cols());
		W term = matGroup.construct(x);
		U one = numGroup.construct();
		numGroup.unity().call(one);
		U factorial = numGroup.construct(one);
		U inc = numGroup.construct(one);
		U scale = numGroup.construct();
		for (int i = 0; i < numTerms; i++) {
			numGroup.divide().call(one, factorial, scale);
			matGroup.scale().call(scale, term, term);
			matGroup.add().call(sum, term, sum);
			matGroup.power().call(2*i+1, x, term); // a little wasteful
			numGroup.add().call(inc,one,inc);
			numGroup.multiply().call(factorial, inc, factorial);
			numGroup.add().call(inc,one,inc);
			numGroup.multiply().call(factorial, inc, factorial);
		}
		matGroup.assign().call(sum, result);
	}
}
