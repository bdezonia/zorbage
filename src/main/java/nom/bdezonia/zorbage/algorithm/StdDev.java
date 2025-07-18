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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * @author Barry DeZonia
 */
public class StdDev {

	// do not instantiate
	
	private StdDev() { }
	
	/**
	 * Compute the standard deviation of a list of values. This
	 * algorithm uses a naive approach that is mathematically correct
	 * but can result in precision issues for some data sets. This
	 * method is quite excellent for use with {@link nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember}
	 * numbers. They do not exhibit any rounding/precision loss and
	 * provide the most accurate results.
	 * 
	 * @param alg
	 * @param storage
	 * @param result
	 * @param <T>
	 * @param <U>
	 */
	public static <T extends Algebra<T,U> &
								Addition<U> &
								Multiplication<U> &
								Unity<U> &
								Roots<U>,
					U extends SetFromLongs>
		void compute(T alg, IndexedDataSource<U> storage, U result)
	{
		U variance = alg.construct();
		
		Variance.compute(alg, storage, variance);

		compute(alg, variance, result);
	}

	/**
	 * Compute the standard deviation from a known variance.
	 *  
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param knownVariance
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Roots<U>,
					U>
		void compute(T alg, U knownVariance, U result)
	{
		alg.sqrt().call(knownVariance, result);
	}

}
