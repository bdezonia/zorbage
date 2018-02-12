/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Ring;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PerpDotProduct {

	private PerpDotProduct() {
		// do not instantiate
	}
	
	public static <T extends RModule<T,U,V,W>,
					U extends RModuleMember<W>,
					V extends Ring<V,W>,
					W>
		void compute(T rmodGroup, V memberGroup, U a, U b, W c)
	{
		if ((a.numDimensions() != 2) || (b.numDimensions() != 2))
			throw new UnsupportedOperationException("perp dot product only defined for 2 dimensions");
		W atmp = memberGroup.construct();
		W btmp = memberGroup.construct();
		W term1 = memberGroup.construct();
		W term2 = memberGroup.construct();
		a.v(1, atmp);
		b.v(0, btmp);
		memberGroup.negate(atmp, atmp);
		memberGroup.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(1, btmp);
		memberGroup.multiply(atmp, btmp, term2);
		memberGroup.add(term1, term2, c);
	}
}
