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
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Autocorrelation {

	// do not instantiate
	
	private Autocorrelation() { }

	/**
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param alg
	 * @param a
	 * @return
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Conjugate<C> & ScaleByHighPrec<C>,
					C extends Allocatable<C>>
		IndexedDataSource<C> compute(CA alg, IndexedDataSource<C> a)
	{
		// NMR Data Processing, Hoch and Stern, p. 23
		
		IndexedDataSource<C> b = Storage.allocate(alg.construct(), a.size());

		C a0 = alg.construct();
		C a1 = alg.construct();
		C term = alg.construct();
		C sum = alg.construct();
		
		HighPrecisionMember scale = G.HP.construct(a.size());
		G.HP.sqrt().call(scale, scale);
		G.HP.invert().call(scale, scale);

		for (long k = 0; k < a.size(); k++) {

			alg.zero().call(sum);
			
			for (long i = 0; i < a.size(); i++) {
				a.get(i, a0);
				a.get(a.size()-i-1-k, a1);
				alg.conjugate().call(a1, a1);
				alg.multiply().call(a0, a1, term);
				alg.add().call(sum, term, sum);
			}
			
			alg.scaleByHighPrec().call(scale, sum, sum);

			b.set(k, sum);
		}
		
		return b;
	}
}
