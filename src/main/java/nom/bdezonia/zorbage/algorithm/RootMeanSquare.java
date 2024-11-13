/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.ConstructibleFromLongs;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * @author Barry DeZonia
 */
public class RootMeanSquare {

	// do not instantiate
	
	private RootMeanSquare() { }

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param data
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> &
						Invertible<U> & Roots<U> & ConstructibleFromLongs<U>,
					U>
	
		void compute(T alg, IndexedDataSource<U> data, U result)

	{
		long sz = data.size();
		
		U sum = alg.construct();
		
		U tmp = alg.construct();
		
		U tmp2 = alg.construct();
		
		U mean = alg.construct();
		
		U n = alg.construct(sz);
		
		for (long i = 0; i < sz; i++) {
			
			data.get(i, tmp);
			
			alg.multiply().call(tmp, tmp, tmp2);
			
			alg.add().call(sum, tmp2, sum);
		}
		
		alg.divide().call(sum, n, mean);
		
		alg.sqrt().call(mean, result);
	}
}
