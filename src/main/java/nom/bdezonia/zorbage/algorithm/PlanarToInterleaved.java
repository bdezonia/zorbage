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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PlanarToInterleaved {

	// do not instantiate
	
	private PlanarToInterleaved() { }
	
	/**
	 * 
	 * @param alg
	 * @param planar
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, DimensionedDataSource<U> planar)
	{
		int numD = planar.numDimensions();
		long[] newDims = new long[numD];
		newDims[0] = planar.dimension(numD-1);
		for (int i = 1; i < numD; i++) {
			newDims[i] = planar.dimension(i-1);
		}
		U value = alg.construct();
		DimensionedDataSource<U> interleaved =
				DimensionedStorage.allocate(planar.storageType(), newDims, value);
		long[] minPt = new long[numD];
		long[] maxPt = new long[numD];
		for (int i = 0; i < numD; i++) {
			maxPt[i] = planar.dimension(i) - 1;
		}
		IntegerIndex plan = new IntegerIndex(numD);
		IntegerIndex inter = new IntegerIndex(numD);
		SamplingCartesianIntegerGrid grid = new SamplingCartesianIntegerGrid(minPt, maxPt);
		SamplingIterator<IntegerIndex> iter = grid.iterator();
		while (iter.hasNext()) {
			iter.next(plan);
			for (int i = 0; i < numD; i++) {
				if (i == numD-1)
					inter.set(0, plan.get(numD-1));
				else
					inter.set(i+1, plan.get(i));
			}
			planar.get(inter, value);
			interleaved.set(plan, value);
		}
		return interleaved;
	}
}
