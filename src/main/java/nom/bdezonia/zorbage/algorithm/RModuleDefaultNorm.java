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
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.algebra.ScaleComponents;
import nom.bdezonia.zorbage.algebra.SetReal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleDefaultNorm {

	// do not instantiate
	
	private RModuleDefaultNorm() { }

	/**
	 * Runs the default algorithm for calculating the norm of an input rmodule/vector
	 * and placing it in an output real.
	 * 
	 * @param multicomponentAlg
	 * @param componentAlg
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Multiplication<U> & Conjugate<U> & Norm<U,W> & ScaleComponents<U,W>,
					U extends GetReal<W>,
					V extends Algebra<V,W> & Addition<W> & Roots<W> & Ordered<W> & Invertible<W> & Scale<W,W>,
					W extends SetReal<W>>
		void compute(T multicomponentAlg, V componentAlg, RModuleMember<U> a, W b)
	{
		// Note: this code does extra work to avoid overflow.
		U aTmp = multicomponentAlg.construct();
		U conjATmp = multicomponentAlg.construct();
		U uTmp = multicomponentAlg.construct();
		W realPart = componentAlg.construct();
		W sum = componentAlg.construct();
		W max = componentAlg.construct();
		W scale = componentAlg.construct();
		for (long i = 0; i < a.length(); i++) {
			a.getV(i, aTmp);
			multicomponentAlg.norm().call(aTmp, realPart);
			Max.compute(componentAlg, max, realPart, max);
		}
		if (componentAlg.isZero().call(max)) {
			componentAlg.zero().call(b);
			return;
		}
		componentAlg.invert().call(max, scale);
		for (long i = 0; i < a.length(); i++) {
			a.getV(i, aTmp);
			multicomponentAlg.conjugate().call(aTmp, conjATmp);
			multicomponentAlg.scaleComponents().call(scale, aTmp, aTmp);
			multicomponentAlg.scaleComponents().call(scale, conjATmp, conjATmp);
			multicomponentAlg.multiply().call(aTmp, conjATmp, uTmp);
			uTmp.getR(realPart);
			componentAlg.add().call(sum, realPart, sum);
		}
		componentAlg.sqrt().call(sum, sum);
		componentAlg.scale().call(max, sum, sum);
		b.setR(sum);
	}
}
