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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Roots;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleL2Norm {

	private RModuleL2Norm() { }

	/**
	 * 
	 * @param rmodGroup
	 * @param numGroup
	 * @param rmod
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Norm<U,W>, U, V extends Algebra<V,W> & Addition<W> & Multiplication<W> & Roots<W>, W>
		void compute(T rmodGroup, V numGroup, RModuleMember<U> rmod, W result)
	{
		U value = rmodGroup.construct();
		W sum = numGroup.construct();
		W tmp = numGroup.construct();
		// TODO: this code does not avoid overflow which it could do in a hypot-like fashion
		for (long i = 0; i < rmod.length(); i++) {
			rmod.v(i, value);
			rmodGroup.norm().call(value, tmp);
			numGroup.multiply().call(tmp, tmp, tmp);
			numGroup.add().call(sum, tmp, sum);
		}
		numGroup.sqrt().call(sum, sum);
		numGroup.assign().call(sum, result);
	}
}
