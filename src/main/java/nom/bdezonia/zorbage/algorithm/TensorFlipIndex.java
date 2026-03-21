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
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;
import nom.bdezonia.zorbage.algebra.IndexType;

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
	 * @param <T>
	 * @param numberAlg
	 * @param metric
	 * @param axis
	 * @param a
	 * @param b
	 * @param targetType
	 */
	public static <NN extends Algebra<NN,NUMBER> & Addition<NUMBER> & Multiplication<NUMBER>, NUMBER,
					T extends TensorMember<NUMBER> & RawData<NUMBER> & Settable<T>>
		void compute(NN numberAlg, T metric, int axis, T a, T b, IndexType targetType)
	{
		if (metric.rank() != 2)
			throw new IllegalArgumentException("metric must be rank 2");

		final int rank = a.rank();

		if (axis < 0 || axis >= rank)
			throw new IllegalArgumentException("axis out of bounds");

		final long n0 = metric.axisSize(0);
		final long n1 = metric.axisSize(1);
		final long na = a.axisSize(axis);

		if (n0 != n1)
			throw new IllegalArgumentException("metric must be square");

		if (n0 != na)
			throw new IllegalArgumentException("metric size must match selected tensor axis");

		// strict check on raising or lowering when already done
		
		if (targetType == a.indexType(axis)) {
		
			b.set(a);
			return;
		}
		
		// Optional strict checks:
		// if (targetType == IndexType.LOWER && a.indexType(axis) != IndexType.UPPER)
		//     throw new IllegalArgumentException("can only lower an upper index");
		// if (targetType == IndexType.UPPER && a.indexType(axis) != IndexType.LOWER)
		//     throw new IllegalArgumentException("can only raise a lower index");

		long[] axisSizes = new long[rank];
		a.shape(axisSizes);

		IndexType[] outTypes = new IndexType[rank];
		a.indexTypes(outTypes);
		outTypes[axis] = targetType;

		b.alloc(axisSizes, outTypes);

		final long[] aStrides = rowMajorStrides(axisSizes);

		long[] metricDims = new long[2];
		metric.shape(metricDims);
		final long[] metricStrides = rowMajorStrides(metricDims);

		final IndexedDataSource<NUMBER> aData = a.rawData();
		final IndexedDataSource<NUMBER> bData = b.rawData();
		final IndexedDataSource<NUMBER> gData = metric.rawData();

		final long total = aData.size();
		final long axisLen = axisSizes[axis];
		final long axisStride = aStrides[axis];

		NUMBER sum = numberAlg.construct();
		NUMBER gVal = numberAlg.construct();
		NUMBER aVal = numberAlg.construct();
		NUMBER term = numberAlg.construct();

		for (long outFlat = 0; outFlat < total; outFlat++) {

			// coordinate i at the chosen axis in the output index
			final long iCoord = (outFlat / axisStride) % axisLen;

			// flat position with the chosen axis removed from contribution
			final long base = outFlat - iCoord * axisStride;

			numberAlg.zero().call(sum);

			for (long k = 0; k < axisLen; k++) {

				// metric[iCoord, k]
				final long gFlat = iCoord * metricStrides[0] + k * metricStrides[1];

				// a[..., k, ...]
				final long aFlat = base + k * axisStride;

				gData.get(gFlat, gVal);
				aData.get(aFlat, aVal);

				numberAlg.multiply().call(gVal, aVal, term);
				numberAlg.add().call(sum, term, sum);
			}

			bData.set(outFlat, sum);
		}
	}

	private static long[] rowMajorStrides(long[] dims) {
		long[] strides = new long[dims.length];
		long stride = 1;
		for (int i = dims.length - 1; i >= 0; i--) {
			strides[i] = stride;
			stride *= dims[i];
		}
		return strides;
	}
}