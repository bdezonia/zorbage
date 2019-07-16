/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConvolveND {

	/**
	 * 
	 * @param alg
	 * @param filter
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U>
		void compute(T alg, MultiDimDataSource<U> filter, MultiDimDataSource<U> a, MultiDimDataSource<U> b)
	{
		if (a == b)
			throw new IllegalArgumentException("source and dest datasets must be different");
		
		if (filter.numDimensions() != a.numDimensions())
			throw new IllegalArgumentException("filter and source have different number of dimensions!");
		
		if (filter.numElements() % 2 != 1)
			throw new IllegalArgumentException("filter dimensions should all be odd");

		U tmp = alg.construct();
		U f = alg.construct();
		U sum = alg.construct();
		IntegerIndex dataMin = new IntegerIndex(a.numDimensions());
		IntegerIndex dataMax = new IntegerIndex(a.numDimensions());
		IntegerIndex filterMin = new IntegerIndex(a.numDimensions());
		IntegerIndex filterMax = new IntegerIndex(a.numDimensions());
		IntegerIndex dataPoint = new IntegerIndex(a.numDimensions());
		IntegerIndex filterPoint = new IntegerIndex(a.numDimensions());
		IntegerIndex pt = new IntegerIndex(a.numDimensions());
		SamplingCartesianIntegerGrid dataBounds =
				new SamplingCartesianIntegerGrid(dataMin, dataMax);
		SamplingCartesianIntegerGrid filterBounds =
				new SamplingCartesianIntegerGrid(filterMin, filterMax);
		SamplingIterator<IntegerIndex> dataPoints = dataBounds.iterator();
		while (dataPoints.hasNext()) {
			dataPoints.next(dataPoint);
			SamplingIterator<IntegerIndex> filterPoints = filterBounds.iterator();
			alg.zero().call(sum);
			while (filterPoints.hasNext()) {
				filterPoints.next(filterPoint);
				for (int i = 0; i < filter.numDimensions(); i++) {
					// TODO: my next line is maybe needed to be pivoted around center of filter
					pt.set(i, dataPoint.get(i) - filterPoint.get(i) - filter.dimension(i));
				}
				a.get(pt, tmp);
				filter.get(filterPoint, f);
				alg.multiply().call(tmp, f, tmp);
				alg.add().call(sum, tmp, sum);
			}
			b.set(dataPoint, sum);
		}
	}
}
