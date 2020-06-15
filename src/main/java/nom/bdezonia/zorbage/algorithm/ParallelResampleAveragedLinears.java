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

import nom.bdezonia.zorbage.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.algorithm.resample.ResampleLinear;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelResampleAveragedLinears {

	private ParallelResampleAveragedLinears() { }

	/**
	 * Linearly resamples one multidim dataset into another multidim dataset using 2 points per axis.
	 * The algorithm computes a series of linear interpolated values, 1 per dimension, from the two
	 * nearest points along that dimension. Then that series of values is averaged.
	 * Note: The input datasource should be padded. This algorithm can poke outside the input boundaries.
	 * 
	 * @param alg
	 * @param newDims
	 * @param input
	 * @return
	 */
	public static <T extends Algebra<T,U> & Addition<U> & ScaleByDouble<U>,
					U extends Allocatable<U>>
		DimensionedDataSource<U> compute(T alg, long[] newDims, DimensionedDataSource<U> input)
	{
		return ResampleLinear.compute(alg, newDims, input, Runtime.getRuntime().availableProcessors());
	}
}
