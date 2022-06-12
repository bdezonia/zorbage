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
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Exponential;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianFilter1D {

	// do not instantiate
	
	private GaussianFilter1D() { }
	
	/**
	 * GaussianFilter1D
	 * 
	 * Creates a 1-d gaussian filter given a radius and a spread parameter. This algorithm uses the
	 * Gaussian algorithm to calculate individual points.
	 * 
	 * @param alg The algebra for the type of numbers the algorithm will calculate with
	 * @param radius The radius of the 1-d filter. Filter size = 2 * radius + 1.
	 * @param sigma The standard deviation (spread factor) of the normal distribution of interest
	 * @return A data source containing the 1-d filter values
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> &
					Exponential<U> & Unity<U> & RealConstants<U> & Roots<U> &
					Invertible<U> & Ordered<U>,
					U extends Allocatable<U>>
		IndexedDataSource<U> compute(T alg, int radius, U sigma)
	{
		if (radius < 0)
			throw new IllegalArgumentException("radius must be >= 0");
		if (alg.signum().call(sigma) < 0)
			throw new IllegalArgumentException("sigma must be >= 0");
		U mu = alg.construct();
		U val = alg.construct();
		IndexedDataSource<U> result = Storage.allocate(val, radius*2+1);
		U x = alg.construct();
		Gaussian.compute(alg, mu, sigma, x, val);
		result.set(radius, val);
		for (int i = 1; i <= radius; i++) {
			x = alg.construct(Integer.toString(i));
			Gaussian.compute(alg, mu, sigma, x, val);
			result.set(radius-1, val);
			result.set(radius+1, val);
		}
		Sum.compute(alg, result, val);
		alg.invert().call(val, val);
		Scale.compute(alg, val, result, result);
		return result;
	}
}
