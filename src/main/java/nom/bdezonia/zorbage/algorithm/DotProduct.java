/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.ScaleComponents;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Ring;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DotProduct {

	private DotProduct() {
		// do not instantiate
	}
	
	/**
	 * 
	 * @param memberAlgebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends RModule<T,U,V,W> & Norm<U,Y>,
					U extends RModuleMember<W>,
					V extends Ring<V,W> & Invertible<W> & ScaleComponents<W, Y>,
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
		componentAlgebra.invert().call(maxNorm, scale);
		for (long i = 0; i < min; i++) {
			a.v(i, tmpA);
			b.v(i, tmpB);
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
