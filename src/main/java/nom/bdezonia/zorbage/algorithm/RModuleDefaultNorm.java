/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.GetReal;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleComponents;
import nom.bdezonia.zorbage.type.algebra.SetReal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleDefaultNorm {

	private RModuleDefaultNorm() { }

	/**
	 * 
	 * @param rmodAlg
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
			a.v(i, aTmp);
			multicomponentAlg.norm().call(aTmp, realPart);
			Max.compute(componentAlg, max, realPart, max);
		}
		if (componentAlg.isZero().call(max)) {
			componentAlg.zero().call(b);
			return;
		}
		componentAlg.invert().call(max, scale);
		for (long i = 0; i < a.length(); i++) {
			a.v(i, aTmp);
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
