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
	 * Do a one sided z transform operation on a list of (usually) complex values
	 * and place the resulting terms in an output list. The input list and output
	 * list can be the same list as this code works safely in place one term at a
	 * time. The descTermCount is the number of descending power terms to include
	 * in the summation of individual values. There are no ascending power terms
	 * included in this summation code. Users of this routine must make sure the
	 * radius of convergence of the series is not violated by passing an incorrect
	 * number for descTermCount.
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param cAlg The (complex or real or other) algebra to use for math operations.
	 * @param z The z value to scale terms by.
	 * @param descTermCount Number of decending power terms to generate in summation.
	 * @param a The list of input values.
	 * @param b The list to place the resulting output values.
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Invertible<C> & Unity<C>,
						C>
		void oneSided(CA cAlg, C z, long descTermCount, IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		if (descTermCount < 0)
			throw new IllegalArgumentException("term count cannot be negative");
		
		C in = cAlg.construct();
		C out = cAlg.construct();
		long N = a.size();
		for (long i = 0; i < N; i++) {
			a.get(i, in);
			calcOneSidedValue(cAlg, descTermCount, in, z, out);
			b.set(i, out);
		}
	}
	
	/**
	 * Do a two sided z transform operation on a list of (usually) complex values
	 * and place the resulting terms in an output list. The input list and output
	 * list can be the same list as this code works safely in place one term at a
	 * time. The descTermCount is the number of descending power terms to include
	 * in the summation of individual values. There are an equal number of ascending
	 * power terms included in this summation code. Users of this routine must make
	 * sure the radius of convergence of the series is not violated by passing an
	 * incorrect number for descTermCount.
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param cAlg The (complex or real or other) algebra to use for math operations.
	 * @param z The z value to scale terms by.
	 * @param descTermCount Number of descending power terms to generate in summation.
	 * @param a The list of input values.
	 * @param b The list to place the resulting output values.
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Invertible<C> & Unity<C>,
						C>
		void twoSided(CA cAlg, C z, long descTermCount, IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		if (descTermCount < 0)
			throw new IllegalArgumentException("term count cannot be negative");
	
		C in = cAlg.construct();
		C out = cAlg.construct();
		long N = a.size();
		for (long i = 0; i < N; i++) {
			a.get(i, in);
			calcTwoSidedValue(cAlg, descTermCount, in, z, out);
			b.set(i, out);
		}
	}
	
	// ----------------------------------------------------------------- //
	
	private static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Invertible<C> & Unity<C>,
						C>
		void calcOneSidedValue(CA cAlg, long descTermCount, C in, C z, C out)
	{
		C scale = cAlg.construct();
		C tmp = cAlg.construct(in);
		C sum = cAlg.construct(in);
		cAlg.unity().call(scale);
		for (long i = 1; i <= descTermCount; i++) {
			cAlg.divide().call(scale, z, scale);
			cAlg.multiply().call(tmp, scale, tmp);
			cAlg.add().call(sum, tmp, sum);
		}
		cAlg.assign().call(sum, out);
	}
	
	// ----------------------------------------------------------------- //
	
	private static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Invertible<C> & Unity<C>,
						C>
		void calcTwoSidedValue(CA cAlg, long descTermCount, C in, C z, C out)
	{
		C scale = cAlg.construct();
		C tmp = cAlg.construct(in);
		C sum = cAlg.construct(in);
		cAlg.unity().call(scale);
		for (long i = 1; i <= descTermCount; i++) {
			cAlg.divide().call(scale, z, scale);
			cAlg.multiply().call(tmp, scale, tmp);
			cAlg.add().call(sum, tmp, sum);
		}
		cAlg.unity().call(scale);
		for (long i = 1; i <= descTermCount; i++) {
			cAlg.multiply().call(scale, z, scale);
			cAlg.multiply().call(tmp, scale, tmp);
			cAlg.add().call(sum, tmp, sum);
		}
		cAlg.assign().call(sum, out);
	}
}
