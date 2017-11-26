/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.algebra.AdditiveGroup;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Median {

	private Median() {}

	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param result
	 */
	public static <T extends AdditiveGroup<T,U> & Invertible<U> & Ordered<U> & Unity<U>, U>
		void compute(T grp, LinearStorage<?,U> storage, U result)
	{
		U tmp = grp.construct();
		U one = grp.construct();
		U sum = grp.construct();
		U count = grp.construct();
		grp.unity(one);
		LinearStorage<?,U> localStorage = storage.duplicate();
		Sort.compute(grp, localStorage);
		if (localStorage.size() % 2 == 0) {
			localStorage.get(localStorage.size()/2 - 1, tmp);
			grp.add(sum, tmp, sum);
			localStorage.get(localStorage.size()/2, tmp);
			grp.add(sum, tmp, sum);
			grp.add(one, one, count);
			grp.divide(sum, count, result);
		}
		else {
			localStorage.get(localStorage.size()/2, result);
		}
	}
}
