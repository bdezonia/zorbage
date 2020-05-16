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

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Rounding;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorRound {

	// do not instantiate
	
	private TensorRound() { }
	
	/**
	 * 
	 * @param <S>
	 * @param <TENSOR>
	 * @param <M>
	 * @param <NUMBER>
	 * @param <B>
	 * @param <COMPONENT>
	 * @param tensAlg
	 * @param numberAlg
	 * @param mode
	 * @param delta
	 * @param a
	 * @param b
	 */
	public static <S extends Algebra<S,TENSOR>,
					TENSOR extends TensorMember<NUMBER> & RawData<NUMBER>,
					M extends Algebra<M,NUMBER> & Rounding<COMPONENT,NUMBER>,
					NUMBER,
					B extends Algebra<B,COMPONENT>,
					COMPONENT>
		void compute(S tensAlg, M numberAlg, Mode mode, COMPONENT delta, TENSOR a, TENSOR b)
	{
		TensorShape.compute(a, b);
		NUMBER tmp = numberAlg.construct();
		IndexedDataSource<NUMBER> list = a.rawData();
		long numElems = list.size();
		for (long i = 0; i < numElems; i++) {
			list.get(i, tmp);
			numberAlg.round().call(mode, delta, tmp, tmp);
			list.set(i, tmp);
		}
	}
}
