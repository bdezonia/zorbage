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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.RModuleMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleAdd {

	// do not instantiate
	
	private RModuleAdd() { }
	
	/**
	 * Add two rmodule/vectors into a third rmodule/vector.
	 * 
	 * @param memberAlgebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <U extends RModuleMember<W>, V extends Algebra<V,W> & Addition<W>, W>
		void compute(V memberAlgebra, U a, U b, U c)
	{
		W tmp1 = memberAlgebra.construct();
		W tmp2 = memberAlgebra.construct();
		final long maxLength = Math.max(a.length(), b.length());
		final long minLength = Math.min(a.length(), b.length());
		c.alloc(maxLength);
		for (long i = 0; i < minLength; i++) {
			a.getV(i, tmp1);
			b.getV(i, tmp2);
			memberAlgebra.add().call(tmp1, tmp2, tmp2);
			c.setV(i, tmp2);
		}
		for (long i = minLength; i < maxLength; i++) {
			if (a.length() > minLength)
				a.getV(i, tmp2);
			else
				b.getV(i, tmp2);
			c.setV(i, tmp2);
		}
	}
}
