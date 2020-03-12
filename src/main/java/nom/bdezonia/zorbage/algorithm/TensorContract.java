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
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

// https://en.wikipedia.org/wiki/Tensor_contraction

// Okay, I'm not finding predefined algorithms. I think in cartesian tensors each vector is its
// own dual. So contraction doesn't have to worry about upper and lower indices. Follow the
// general rules but treat every upper/lower alignment with simple addition.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorContract {

	// do not instantiate
	
	private TensorContract() { }
	
	/**
	 * 
	 * @param <M>
	 * @param <NUMBER>
	 * @param numberAlg
	 * @param aRank
	 * @param i
	 * @param j
	 * @param a
	 * @param b
	 */
	public static <M extends Algebra<M,NUMBER> & Addition<NUMBER>, NUMBER>
		void compute(M numberAlg, Integer aRank, Integer i, Integer j, TensorMember<NUMBER> a, TensorMember<NUMBER> b)
	{
		if (i == j)
			throw new IllegalArgumentException("cannot contract along a single axis");
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("negative contraction indices given");
		if (i >= aRank || j >= aRank)
			throw new IllegalArgumentException("contraction indices cannot be out of bounds of input tensor's rank");
		if (a == b)
			throw new IllegalArgumentException("src cannot equal dest: contraction is not an in place operation");
		int newRank = aRank - 2;
		long[] newDims = new long[newRank];
		for (int k = 0; k < newDims.length; k++) {
			newDims[k] = a.dimension(0);
		}
		b.alloc(newDims);
		if (newRank == 0) {
			NUMBER sum = numberAlg.construct();
			NUMBER tmp = numberAlg.construct();
			IntegerIndex pos = new IntegerIndex(2);
			for (int idx = 0; idx < a.dimension(0); idx++) {
				pos.set(i, idx);
				pos.set(j, idx);
				a.v(pos, tmp);
				numberAlg.add().call(sum, tmp, sum);
			}
			IntegerIndex origin = new IntegerIndex(0);
			// TODO: this assignment won't work cuz lower level code does not allow zero dim index.
			// Maybe I fix that in the TensorMember classes. Or I expose setV(0, sum). Investigate.
			b.setV(origin, sum);
			return;
		}
		IntegerIndex point1 = new IntegerIndex(newRank);
		IntegerIndex point2 = new IntegerIndex(newRank);
		for (int k = 0; k < newDims.length; k++) {
			point2.set(k, newDims[k] - 1);
		}
		SamplingCartesianIntegerGrid sampling = new SamplingCartesianIntegerGrid(point1, point2);
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		IntegerIndex contractedPos = new IntegerIndex(sampling.numDimensions());
		IntegerIndex origPos = new IntegerIndex(aRank);
		NUMBER sum = numberAlg.construct();
		NUMBER tmp = numberAlg.construct();
		while (iter.hasNext()) {
			iter.next(contractedPos);
			int p = 0;
			for (int r = 0; r < aRank; r++) {
				if (r == i)
					origPos.set(i, 0);
				else if (r == j)
					origPos.set(j, 0);
				else
					origPos.set(r, contractedPos.get(p++));
			}
			numberAlg.zero().call(sum);
			for (int idx = 0; idx < a.dimension(0); idx++) {
				origPos.set(i, idx);
				origPos.set(j, idx);
				a.v(origPos, tmp);
				numberAlg.add().call(sum, tmp, sum);
			}
			b.setV(contractedPos, sum);
		}
	}
}
