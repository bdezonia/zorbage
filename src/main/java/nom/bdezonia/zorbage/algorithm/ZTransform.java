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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ZTransform {

	// do not instantiate
	
	private ZTransform() { }

	/**
	 * Do a Z transform of an input data list into an output data list. Passing one
	 * list as both input and output is allowed. Note that it is assumed that all
	 * radius of convergence limitations have already been considered before passing
	 * data into this code.
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param cAlg The (complex or real or other) algebra to use for math operations.
	 * @param z The z value to scale terms by.
	 * @param a The input list of values (usually complex but could be other).
	 * @param b The output list of values (usually complex but could be other).
	 */
	public static <CA extends Algebra<CA,C> & Multiplication<C> & Invertible<C> & Unity<C>,
					C>
		void compute(CA cAlg, C z, IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		C scale = cAlg.construct();
		C tmp = cAlg.construct();
		cAlg.unity().call(scale);
		long N = a.size();
		for (long i = 0; i < N; i++) {
			a.get(i, tmp);
			cAlg.multiply().call(tmp, scale, tmp);
			b.set(i, tmp);
			cAlg.divide().call(scale, z, scale);
		}
	}
}
