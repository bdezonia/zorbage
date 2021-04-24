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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BasicStats {

	// do not instantiate
	
	private BasicStats() { }
	
	/**
	 * Calculate some basic data measures using naive algorithms. Passing a high precision backed
	 * data source will result in perfectly accurate results.
	 * 
	 * @param alg
	 * @param data
	 * @param mean
	 * @param stdErrMean
	 * @param stddev
	 * @param sampleVariance
	 * @param sampleSkew
	 * @param excessKurtosis
	 */
	public static <T extends Algebra<T,U> & Unity<U> & Addition<U> & Multiplication<U> & Invertible<U> & Roots<U>,
					U>
		void compute(T alg, IndexedDataSource<U> data, U mean, U stdErrMean, U stddev, U sampleVariance, U sampleSkew, U excessKurtosis)
	{
		if (data.size() < 2)
			throw new IllegalArgumentException("Basic statistics cannot be calculated from a data set with less than two elements");
		
		U value = alg.construct();
		U tmp = alg.construct();
		U sum = alg.construct();
		U n = alg.construct();
		U n_minus_one = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);

		// calc n etc.
		//   there is probably a simpler and faster way to do this. if not I should make one.
		alg.zero().call(sum);
		for (long i = 0; i < data.size(); i++) {
			alg.add().call(sum, one, sum);
		}
		alg.assign().call(sum, n);
		alg.subtract().call(sum, one, n_minus_one);
		
		// calc mean
		//   mean = sum (xi / n)
		alg.zero().call(sum);
		for (long i = 0; i < data.size(); i++) {
			data.get(i, tmp);
			alg.divide().call(tmp, n, value);
			alg.add().call(sum, value, sum);
		}
		alg.assign().call(sum, mean);
		
		// calc sample variance
		//   sample variance s^2 = sum ((xi - xbar)^2 / n-1)
		alg.zero().call(sum);
		for (long i = 0; i < data.size(); i++) {
			data.get(i, tmp);
			alg.subtract().call(tmp, mean, tmp);
			alg.multiply().call(tmp, tmp, value);
			alg.divide().call(value, n_minus_one, value);
			alg.add().call(sum, value, sum);
		}
		alg.assign().call(sum, sampleVariance);
		alg.sqrt().call(sampleVariance, stddev);
		alg.sqrt().call(n, tmp);
		alg.divide().call(stddev, tmp, stdErrMean);
		
		// calc sample skew
		//   sample skew = sum ((xi - xbar)^3 / n * s^3)
		alg.zero().call(sum);
		for (long i = 0; i < data.size(); i++) {
			data.get(i, tmp);
			alg.subtract().call(tmp, mean, tmp);
			alg.divide().call(tmp, stddev, tmp);
			alg.multiply().call(tmp, tmp, value);
			alg.multiply().call(value, tmp, value);
			alg.divide().call(value, n, value);
			alg.add().call(sum, value, sum);
		}
		alg.assign().call(sum, sampleSkew);
		
		// calc excess kurtosis
		//   excess kurtosis = sum ((xi - xbar)^4 / n * s^4) - 3
		alg.zero().call(sum);
		for (long i = 0; i < data.size(); i++) {
			data.get(i, tmp);
			alg.subtract().call(tmp, mean, tmp);
			alg.divide().call(tmp, stddev, tmp);
			alg.multiply().call(tmp, tmp, tmp);
			alg.multiply().call(tmp, tmp, value);
			alg.divide().call(value, n, value);
			alg.add().call(sum, value, sum);
		}
		alg.subtract().call(sum, one, sum);
		alg.subtract().call(sum, one, sum);
		alg.subtract().call(sum, one, sum);
		alg.assign().call(sum, excessKurtosis);
	}
}
