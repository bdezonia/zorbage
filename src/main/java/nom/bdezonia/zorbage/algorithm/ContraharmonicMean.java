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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ContraharmonicMean {

	// do not instantiate
	
	private ContraharmonicMean() { }
		
	/**
	 * Calculate the contraharmonic mean of a list of values.
	 * 
	 * See https://en.wikipedia.org/wiki/Contraharmonic_mean for more information
	 * about the contraharmonic mean.
	 * 
	 * @param alg
	 * @param list
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Invertible<U>, U>
		void compute(T alg, IndexedDataSource<U> list, U result)
	{
		long sz = list.size();
		if (sz == 0)
			throw new IllegalArgumentException("contraharmonic mean called on an empty list");
		
		U value = alg.construct();
		U numerSum = alg.construct();
		U denomSum = alg.construct();
		
		for (long i = 0; i < list.size(); i++) {
			list.get(i, value);
			alg.add().call(denomSum, value, denomSum);
			alg.multiply().call(value,value,value);
			alg.add().call(numerSum, value, numerSum);
		}
		
		alg.divide().call(numerSum, denomSum, result);
	}
}
