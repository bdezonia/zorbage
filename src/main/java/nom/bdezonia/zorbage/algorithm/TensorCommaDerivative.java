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

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorCommaDerivative {

	// do not instantiate
	
	private TensorCommaDerivative() { }
	
	// https://mathworld.wolfram.com/CommaDerivative.html
	
	// My current understanding is this populates a tensor zero everywhere except where an input tensor
	// is sliced by the index value (0 .. dimension-1) in the outermost dimension. This may be incorrect
	// but that is what seems most logical when I look at the math definition above.
	
	/**
	 * Calculate the tensor comma derivative at a point of an input tensor
	 * and store in an output tensor.
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
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(min, max);
		while (iter.hasNext()) {
			iter.next(tmpIdx);
			a.getV(tmpIdx, value);
			tmp.setV(tmpIdx, value);
		}
		tensAlg.assign().call(tmp, b);
	}
}
