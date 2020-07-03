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

import nom.bdezonia.zorbage.algebra.Dimensioned;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GridIterator {

	// do not instantiate
	
	private GridIterator() { }
	
	/**
	 * 
	 * @param minPt
	 * @param maxPt
	 * @return
	 */
	public static
		SamplingIterator<IntegerIndex> compute(long[] minPt, long[] maxPt)
	{
		return new SamplingCartesianIntegerGrid(minPt, maxPt).iterator();
	}
		
	/**
	 * 
	 * @param minPt
	 * @param maxPt
	 * @return
	 */
	public static
		SamplingIterator<IntegerIndex> compute(IntegerIndex minPt, IntegerIndex maxPt)
	{
		return new SamplingCartesianIntegerGrid(minPt, maxPt).iterator();
	}
		
	/**
	 * 
	 * @param dims
	 * @return
	 */
	public static
		SamplingIterator<IntegerIndex> compute(long[] dims)
	{
		int numD = dims.length;
		long[] minPt = new long[numD];
		long[] maxPt = new long[numD];
		for (int i = 0; i < numD; i++) {
			maxPt[i] = dims[i] - 1;
		}
		return compute(minPt, maxPt);
	}
		
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static
		SamplingIterator<IntegerIndex> compute(Dimensioned entity)
	{
		int numD = entity.numDimensions();
		
		long[] dims = new long[numD];
		
		for (int i = 0; i < numD; i++) {
			dims[i] = entity.dimension(i);
		}
		
		return compute(dims);
	}
		
}
