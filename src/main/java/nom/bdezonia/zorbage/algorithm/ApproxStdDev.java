/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ApproxStdDev {

	// do not instantiate
	
	private ApproxStdDev() {}
	
	/**
	 * ApproxStdDev gives an approximate estimate of the standard deviation
	 * of a list of numbers. It is approximate in the sense that adjustments
	 * are made to avoid overall data loss at the expense of absolute
	 * accuracy. If you need absolute accuracy use the {@link StdDev}
	 * algorithm using {@link nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember}s to contain the results. 
	 * 
	 * @param storage
	 * @param result
	 */
	public static <T extends Algebra<T,U> &
								Addition<U> &
								Multiplication<U> &
								Unity<U> &
								Invertible<U> &
								Roots<U> &
								Ordered<U>,
					U extends SetFromLongs>
		void compute(T alg, IndexedDataSource<U> storage, U result)
	{
		ApproxVariance.compute(alg, storage, result);
		alg.sqrt().call(result, result);
	}
	
	/**
	 * Calculate the approximate standard deviation from a known approximate variance.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param knownApproxVariance
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Roots<U>,
					U>
		void compute(T alg, U knownApproxVariance, U result)
	{
		StdDev.compute(alg, knownApproxVariance, result);
	}

}
