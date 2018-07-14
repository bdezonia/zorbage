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

import nom.bdezonia.zorbage.type.algebra.AdditiveGroup;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Variance {

	private Variance() {}
	
	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param result
	 */
	public static <T extends AdditiveGroup<T,U> & Multiplication<U> & Unity<U> & Invertible<U>, U>
		void compute(T grp, IndexedDataSource<?,U> storage, U result)
	{
		U avg = grp.construct();
		U sum = grp.construct();
		U count = grp.construct();
		U one = grp.construct();
		grp.unity().call(one);
		Average.compute(grp, storage, avg);
		SumSquareCount.compute(grp, storage, avg, sum, count);
		grp.subtract().call(count, one, count);
		grp.divide().call(sum, count, result);
	}
}
