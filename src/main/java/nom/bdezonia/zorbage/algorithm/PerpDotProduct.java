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

import nom.bdezonia.zorbage.algebra.RModule;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.Ring;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PerpDotProduct {

	private PerpDotProduct() {
		// do not instantiate
	}
	
	/**
	 * 
	 * @param rmodAlgebra
	 * @param memberAlgebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends RModule<T,U,V,W>,
					U extends RModuleMember<W>,
					V extends Ring<V,W>,
					W>
		void compute(T rmodAlgebra, V memberAlgebra, U a, U b, W c)
	{
		if ((a.length() != 2) || (b.length() != 2))
			throw new UnsupportedOperationException("perp dot product only defined for 2 dimensions");
		W atmp = memberAlgebra.construct();
		W btmp = memberAlgebra.construct();
		W term1 = memberAlgebra.construct();
		W term2 = memberAlgebra.construct();
		a.getV(1, atmp);
		b.getV(0, btmp);
		memberAlgebra.negate().call(atmp, atmp);
		memberAlgebra.multiply().call(atmp, btmp, term1);
		a.getV(0, atmp);
		b.getV(1, btmp);
		memberAlgebra.multiply().call(atmp, btmp, term2);
		memberAlgebra.add().call(term1, term2, c);
	}
}
