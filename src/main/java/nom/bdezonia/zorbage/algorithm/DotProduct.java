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
import nom.bdezonia.zorbage.algebra.ScaleComponents;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.RModule;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.Ring;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DotProduct {

	// do not instantiate
	
	private DotProduct() { }
	
	/**
	 * 
	 * @param memberAlgebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends RModule<T,U,V,W> & Norm<U,Y>,
					U extends RModuleMember<W>,
					V extends Ring<V,W> & ScaleComponents<W, Y>,
					W,
					X extends Algebra<X,Y> & Ordered<Y> & Invertible<Y>,
					Y>
		void compute(T rmodAlgebra, V memberAlgebra, X componentAlgebra, U a, U b, W c)
	{
		final long min = Math.min(a.length(), b.length());
		W sum = memberAlgebra.construct();
		W tmpA = memberAlgebra.construct();
		W tmpB = memberAlgebra.construct();
		Y normA = componentAlgebra.construct();
		Y normB = componentAlgebra.construct();
		Y maxNorm = componentAlgebra.construct();
		Y scale = componentAlgebra.construct();
		rmodAlgebra.norm().call(a, normA);
		rmodAlgebra.norm().call(b, normB);
		Max.compute(componentAlgebra, normA, normB, maxNorm);
		if (componentAlgebra.isZero().call(maxNorm)) {
			memberAlgebra.zero().call(c);
			return;
		}
		componentAlgebra.invert().call(maxNorm, scale);
		for (long i = 0; i < min; i++) {
			a.getV(i, tmpA);
			b.getV(i, tmpB);
			memberAlgebra.scaleComponents().call(scale, tmpA, tmpA);
			memberAlgebra.scaleComponents().call(scale, tmpB, tmpB);
			memberAlgebra.multiply().call(tmpA, tmpB, tmpB);
			memberAlgebra.add().call(sum, tmpB, sum);
		}
		memberAlgebra.scaleComponents().call(maxNorm, sum, sum);
		memberAlgebra.scaleComponents().call(maxNorm, sum, sum);
		memberAlgebra.assign().call(sum, c);
	}
}
