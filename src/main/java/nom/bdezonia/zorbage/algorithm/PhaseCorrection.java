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
import nom.bdezonia.zorbage.algebra.Exponential;
import nom.bdezonia.zorbage.algebra.ImaginaryConstants;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.SetFromLong;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

// TODO: parallelize

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PhaseCorrection {

	// do not instantiate
	
	private PhaseCorrection() { }
	
	/**
	 * Correct the phases of a complex frequency data set.
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param cAlg The complex algebra used to manipulate values.
	 * @param rAlg The real algebra used to manipulate values.
	 * @param pivot Central term of input data source to pivot corrections about.
	 * @param theta0 The uniform phase correction to apply to each term (in radians).
	 * @param theta1 The relative phase correction to apply based on the distance of each term from the pivot term (in radians).
	 * @param a The input list of frequency values.
	 * @param b The output list of frequency values.
	 */
	public static <CA extends Algebra<CA,C> & Multiplication<C> & Exponential<C> &
								ImaginaryConstants<C>,
					C extends SetR<R>,
					RA extends Algebra<RA,R> & Addition<R> & Multiplication<R> &
								Invertible<R>,
					R extends SetFromLong>
		void compute(CA cAlg, RA rAlg, long pivot, R theta0, R theta1,
						IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		if (pivot < 0 || pivot >= a.size())
			throw new IllegalArgumentException("phase correction pivot out of bounds");
		
		//e i ( th0 + (n - pivot)th1/N)
		long N = a.size();
		R n = rAlg.construct();
		R scaleTerm = rAlg.construct();
		R linTerm = rAlg.construct();
		C I = cAlg.construct();
		C power = cAlg.construct();
		C correction = cAlg.construct();
		C tmp = cAlg.construct();
		cAlg.I().call(I);
		n.setFromLong(N);
		for (long i = 0; i < N; i++) {
			scaleTerm.setFromLong(i - pivot);
			rAlg.multiply().call(scaleTerm, theta1, scaleTerm);
			rAlg.divide().call(scaleTerm, n, scaleTerm);
			rAlg.add().call(theta0, scaleTerm, linTerm);
			cAlg.zero().call(power);
			power.setR(linTerm);
			cAlg.multiply().call(I, power, power);
			cAlg.exp().call(power, correction);
			a.get(i, tmp);
			cAlg.multiply().call(tmp, correction, tmp);
			b.set(i, tmp);
		}
	}
}
