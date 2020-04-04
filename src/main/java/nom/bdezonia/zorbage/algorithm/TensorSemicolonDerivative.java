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
import nom.bdezonia.zorbage.type.algebra.TensorLikeMethods;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorSemicolonDerivative {

	// do not instantiate
	
	private TensorSemicolonDerivative() { }
	
	// http://mathworld.wolfram.com/CovariantDerivative.html
	// https://en.wikipedia.org/wiki/Covariant_derivative
	// https://en.wikipedia.org/wiki/Christoffel_symbols
		
	/**
	 * 
	 * @param <S>
	 * @param <TENSOR>
	 * @param <M>
	 * @param <NUMBER>
	 * @param tensAlg
	 * @param numAlg
	 * @param index
	 * @param a
	 * @param b
	 */
	public static <S extends Algebra<S,TENSOR> & TensorLikeMethods<TENSOR,NUMBER> & Addition<TENSOR>,
					TENSOR extends TensorMember<NUMBER>,
					M extends Algebra<M,NUMBER>, NUMBER>
		void compute(S tensAlg, M numAlg, Integer index, TENSOR a, TENSOR b)
	{
		
		TensorCommaDerivative.compute(tensAlg, numAlg, index, a, b);
		
		TENSOR sum = tensAlg.construct();
		TENSOR tmp = tensAlg.construct();
		TensorShape.compute(a, sum);
		TensorShape.compute(a, tmp);
		tensAlg.commaDerivative().call(index, a, sum);
		for (int i = 0; i < a.rank(); i++) {
			/*
			
			// TODO: since all my tensor impls are cartesian ones in euclidean space right now I think that the
			// semicolon derivative degenerates into the comma derivative because the christoffel symbols are
			// all zero. This might be wrong so should revisit.
			 
			if (a.indexIsUpper(i)) {
				// this position is an upper index
				christoffel(i, d, index, a, tmp);
				tensAlg.add().call(sum, tmp, sum);
			}
			else {
				// this position is a lower index
				christoffel(d, i, index, a, tmp);
				tensAlg.subtract().call(sum, tmp, sum);
			}
			 */
		}
		tensAlg.assign().call(sum, b);
	}
	
	/* future code
	private static <S extends Algebra<S,TENSOR> & TensorLikeMethods<TENSOR,NUMBER>,
						TENSOR extends TensorMember<NUMBER>,
						M extends Algebra<M,NUMBER>, NUMBER>
		void christoffel(int k, int i, int j, TENSOR a, TENSOR out)
	{
		
	}
	*/
}
