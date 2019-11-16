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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.GetReal;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Roots;
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
	public static <T extends Algebra<T,U> & Multiplication<U> & Conjugate<U>,
					U extends GetReal<W>,
					V extends Algebra<V,W> & Addition<W> & Roots<W>,
					W extends SetReal<W>>
		void compute(T multicomponentAlg, V componentAlg, RModuleMember<U> a, W b)
	{
		// TODO: like the DotProduct algorithm I should be able to define code that avoids roundoff
		U aTmp = multicomponentAlg.construct();
		U conjATmp = multicomponentAlg.construct();
		U uTmp = multicomponentAlg.construct();
		W realPart = componentAlg.construct();
		W sum = componentAlg.construct();
		for (long i = 0; i < a.length(); i++) {
			a.v(i, aTmp);
			multicomponentAlg.conjugate().call(aTmp, conjATmp);
			multicomponentAlg.multiply().call(aTmp, conjATmp, uTmp);
			uTmp.getR(realPart);
			componentAlg.add().call(sum, realPart, sum);
		}
		componentAlg.sqrt().call(sum, sum);
		b.setR(sum);
	}
}
