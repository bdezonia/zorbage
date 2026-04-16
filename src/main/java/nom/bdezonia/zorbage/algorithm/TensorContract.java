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

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.IndexType;
import nom.bdezonia.zorbage.algebra.TensorMember;

// https://en.wikipedia.org/wiki/Tensor_contraction

// Okay, I'm not finding predefined algorithms. Follow the
// general rules but treat every upper/lower alignment with
// simple addition.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorContract {

	// do not instantiate
	
	private TensorContract() { }
	
	/**
	 * Contract an input tensor and place results in an output tensor.
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
		void compute(M numberAlg, int i, int j, TensorMember<NUMBER> a, TensorMember<NUMBER> b)
	{
		final int rank = a.rank();

		if (i == j)
			throw new IllegalArgumentException("cannot contract along a single axis");
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("negative contraction indices given");
		if (i >= rank || j >= rank)
			throw new IllegalArgumentException("contraction indices cannot be out of bounds");
		if (a == b)
			throw new IllegalArgumentException("src cannot equal dest: contraction is not an in place operation");

		if (a.indexType(i) == a.indexType(j))
			throw new IllegalArgumentException("contraction requires one upper and one lower index");
			
		final long sizeI = a.axisSize(i);
		final long sizeJ = a.axisSize(j);

		if (sizeI != sizeJ)
			throw new IllegalArgumentException("contracted axes must have equal sizes");

		final int newRank = rank - 2;

		long[] newDims = new long[newRank];
		IndexType[] newTypes = new IndexType[newRank];

		int p = 0;
		for (int r = 0; r < rank; r++) {
			if (r != i && r != j) {
				newDims[p] = a.axisSize(r);
				newTypes[p] = a.indexType(r);
				p++;
			}
		}

		b.alloc(newDims, newTypes);

		NUMBER sum = numberAlg.construct();
		NUMBER tmp = numberAlg.construct();

		// Scalar result
		if (newRank == 0) {
			IntegerIndex srcIndex = new IntegerIndex(rank);
			IntegerIndex dstIndex = new IntegerIndex(0);

			numberAlg.zero().call(sum);
			for (long k = 0; k < sizeI; k++) {
				srcIndex.set(i, k);
				srcIndex.set(j, k);
				a.getV(srcIndex, tmp);
				numberAlg.add().call(sum, tmp, sum);
			}
			b.setV(dstIndex, sum);
			return;
		}

		IntegerIndex min = new IntegerIndex(newRank);
		IntegerIndex max = new IntegerIndex(newRank);
		for (int r = 0; r < newRank; r++) {
			max.set(r, newDims[r] - 1);
		}

		SamplingIterator<IntegerIndex> iter = GridIterator.compute(min, max);

		IntegerIndex dstIndex = new IntegerIndex(newRank);
		IntegerIndex srcIndex = new IntegerIndex(rank);

		while (iter.hasNext()) {
			iter.next(dstIndex);

			// Map output coordinates into source coordinates,
			// leaving the contracted axes to be filled by k.
			p = 0;
			for (int r = 0; r < rank; r++) {
				if (r == i || r == j) {
					srcIndex.set(r, 0);
				}
				else {
					srcIndex.set(r, dstIndex.get(p++));
				}
			}

			numberAlg.zero().call(sum);
			for (long k = 0; k < sizeI; k++) {
				srcIndex.set(i, k);
				srcIndex.set(j, k);
				a.getV(srcIndex, tmp);
				numberAlg.add().call(sum, tmp, sum);
			}

			b.setV(dstIndex, sum);
		}
	}
}
