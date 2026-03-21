/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.IndexType;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * @author Barry DeZonia
 */
public class TensorFlipIndex {

	// do not instantiate
	
	private TensorFlipIndex() { }

	/**
	 * 
	 * @param <NN>
	 * @param <NUMBER>
	 * @param numberAlg
	 * @param metric
	 * @param axis
	 * @param a
	 * @param b
	 * @param targetType
	 */
	public static <NN extends Algebra<NN,NUMBER> & Addition<NUMBER> & Multiplication<NUMBER>, NUMBER, TENSOR extends TensorMember<NUMBER> & Settable<TENSOR>>
		void compute(NN numberAlg, TENSOR metric, Integer axis, IndexType targetType, TENSOR a, TENSOR b)
	{
		if (metric.rank() != 2)
			throw new IllegalArgumentException("metric must be rank 2");

		if (axis < 0 || axis >= a.rank())
			throw new IllegalArgumentException("axis out of bounds");

		long n0 = metric.axisSize(0);
		long n1 = metric.axisSize(1);
		long na = a.axisSize(axis);

		if (n0 != n1)
			throw new IllegalArgumentException("metric must be square");

		if (n0 != na)
			throw new IllegalArgumentException("metric size must match selected tensor axis");

		if (a.indexType(axis) == targetType) {

			// already correctly aligned
			
			b.set(a);
			return;
		}

		int rank = a.rank();

		long[] dims = new long[rank];
		a.shape(dims);

		IndexType[] types = new IndexType[rank];
		a.indexTypes(types);

		types[axis] = targetType;

		b.alloc(dims, types);

		NUMBER sum = numberAlg.construct();
		NUMBER mVal = numberAlg.construct();
		NUMBER aVal = numberAlg.construct();
		NUMBER term = numberAlg.construct();

		// rank-0 tensor cannot have an axis to raise/lower, so no special scalar case needed

		IntegerIndex min = new IntegerIndex(rank);
		IntegerIndex max = new IntegerIndex(rank);
		for (int i = 0; i < rank; i++) {
			max.set(i, dims[i] - 1);
		}

		SamplingIterator<IntegerIndex> iter = GridIterator.compute(min, max);

		IntegerIndex outIdx = new IntegerIndex(rank);
		IntegerIndex aIdx = new IntegerIndex(rank);
		IntegerIndex gIdx = new IntegerIndex(2);

		while (iter.hasNext()) {
			iter.next(outIdx);

			// start with aIdx = outIdx
			for (int i = 0; i < rank; i++) {
				aIdx.set(i, outIdx.get(i));
			}

			long outCoord = outIdx.get(axis);

			numberAlg.zero().call(sum);

			for (long k = 0; k < na; k++) {
				gIdx.set(0, outCoord);
				gIdx.set(1, k);

				aIdx.set(axis, k);

				metric.getV(gIdx, mVal);
				a.getV(aIdx, aVal);

				numberAlg.multiply().call(mVal, aVal, term);
				numberAlg.add().call(sum, term, sum);
			}

			b.setV(outIdx, sum);
		}
	}
}