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

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorCommaDerivative {

	// do not instantiate
	
	private TensorCommaDerivative() { }
	
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
	public static <S extends Algebra<S,TENSOR>,
					TENSOR extends TensorMember<NUMBER>,
					M extends Algebra<M, NUMBER>,
					NUMBER>
		void compute(S tensAlg, M numAlg, Integer index, TENSOR a, TENSOR b)
	{
		if (a.rank() == 0) {
			tensAlg.assign().call(a, b);
			return;
		}
		TENSOR tmp = tensAlg.construct();
		TensorShape.compute(a, tmp);
		NUMBER value = numAlg.construct();
		IntegerIndex min = new IntegerIndex(a.rank());
		IntegerIndex max = new IntegerIndex(a.rank());
		IntegerIndex tmpIdx = new IntegerIndex(a.rank());
		min.set(a.rank()-1, index);
		max.set(a.rank()-1, index);
		for (int i = 0; i < a.rank()-1; i++) {
			max.set(i, a.dimension()-1);
		}
		SamplingCartesianIntegerGrid grid = new SamplingCartesianIntegerGrid(min, max);
		SamplingIterator<IntegerIndex> iter = grid.iterator();
		while (iter.hasNext()) {
			iter.next(tmpIdx);
			a.v(tmpIdx, value);
			tmp.setV(tmpIdx, value);
		}
		tensAlg.assign().call(tmp, b);
	}
}
