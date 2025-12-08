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
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorNorm {

	// do not instantiate
	
	private TensorNorm() { }

	/**
	 * Calculate the norm of a tensor and put it in an output number
	 * 
	 * @param <M>
	 * @param <NUMBER>
	 * @param <B>
	 * @param <COMPONENT>
	 * @param numberAlg
	 * @param componentAlg
	 * @param a
	 */
	public static <M extends Algebra<M,NUMBER> & Norm<NUMBER,COMPONENT>,
					NUMBER,
					B extends Algebra<B,COMPONENT> & Ordered<COMPONENT> & Invertible<COMPONENT> & Addition<COMPONENT> & Multiplication<COMPONENT> & Roots<COMPONENT>,
					COMPONENT>
		void compute(M numberAlg, B componentAlg, IndexedDataSource<NUMBER> a, COMPONENT b)
	{
		
		// TODO: this algorithm needs testing. I kinda made it up on the spot and it might not be
		// mathematically correct. Dig deeper. Note I am avoiding a multiply the value by it's
		// conjugate in my code. This might be a mistake.

		NUMBER value = numberAlg.construct();
		COMPONENT max = componentAlg.construct();
		COMPONENT nrm = componentAlg.construct();
		long numElems = a.size();
		for (long i = 0; i < numElems; i++) {
			a.get(i, value);
			numberAlg.norm().call(value, nrm);
			if (componentAlg.isGreater().call(nrm, max))
				componentAlg.assign().call(nrm, max);
		}
		COMPONENT sum = componentAlg.construct();
		for (long i = 0; i < numElems; i++) {
			a.get(i, value);
			numberAlg.norm().call(value, nrm);
			componentAlg.divide().call(nrm, max, nrm);
			componentAlg.multiply().call(nrm, nrm, nrm);
			componentAlg.add().call(sum, nrm, sum);
		}
		componentAlg.sqrt().call(sum, sum);
		componentAlg.multiply().call(max, sum, sum);
		componentAlg.assign().call(sum, b);
	}
}
