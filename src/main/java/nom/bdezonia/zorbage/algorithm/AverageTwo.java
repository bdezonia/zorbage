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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.BitOperations;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class AverageTwo {

	/**
	 * Average two things without doing a division
	 * 
	 * @param alg
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U> & BitOperations<U> & Unity<U> & Addition<U>, U>
		void compute(T alg, U a, U b, U result)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		U tmp3 = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		alg.bitShiftRight().call(1, a, tmp1);
		alg.bitShiftRight().call(1, b, tmp2);
		alg.bitAnd().call(a, b, tmp3);
		alg.bitAnd().call(tmp3, one, tmp3);
		alg.add().call(tmp1, tmp2, tmp1);
		alg.add().call(tmp1, tmp3, result);
	}
}
