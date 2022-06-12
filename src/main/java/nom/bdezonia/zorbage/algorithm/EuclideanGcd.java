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
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Settable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EuclideanGcd {

	// do not instantiate
	
	public EuclideanGcd() { }
	
	/**
	 * Calculate the greatest common divisor of two numbers (and number
	 * like constructions). Uses the Euclidean algorithm which is simple
	 * and quite fast but not optimal. But it works with fewer type
	 * constraints than other algorithms.
	 * 
	 * @param uAlg
	 * @param wAlg
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> & ModularDivision<U> & Norm<U,W>,
					U extends Settable<U>,
					V extends Algebra<V,W> & Ordered<W>,
					W extends Settable<W>>
		void compute(T uAlg, V wAlg, U a, U b, U result)
	{
		U x = uAlg.construct();
		U y = uAlg.construct();
		U t = uAlg.construct();
		W normA = wAlg.construct();
		W normB = wAlg.construct();
		W origNorm = wAlg.construct();
		W tmpNorm = wAlg.construct();
		uAlg.norm().call(a, normA);
		uAlg.norm().call(b, normB);
		if (wAlg.isGreater().call(normA, normB)) {
			x.set(a);
			y.set(b);
			origNorm.set(normA);
		}
		else {
			x.set(b);
			y.set(a);
			origNorm.set(normB);
		}
		while (!uAlg.isZero().call(y)) {
			t.set(y);
			uAlg.mod().call(x, y, y);
			x.set(t);
			uAlg.norm().call(x, tmpNorm);
			if (wAlg.isGreaterEqual().call(tmpNorm, origNorm))
				throw new IllegalArgumentException("euclidean gcd algorithm cannot converge for given inputs : due to overflow?");
		}
		result.set(x);
	}

}
