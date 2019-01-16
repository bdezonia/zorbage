/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.sampling;

import nom.bdezonia.zorbage.misc.RealUtils;

/**
 * 
 * {@link SamplingCartesianRealGrid } is an n-dimensional {@link Sampling} that spans a user
 * defined region of Euclidean space. It is defined by two n-dimensional real space points and
 * a set of n counts that determine the spacing along each dimensions.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingCartesianRealGrid implements Sampling<RealIndex> {

	private final int numD;
	private final RealIndex minPt;
	private final RealIndex maxPt;
	private final IntegerIndex dimCounts;
	
	// TODO: calc me from grid cell size
	
	private final double TOL = 0.000001;
	
	public SamplingCartesianRealGrid(double[] point1, double[] point2, long[] counts) {
		if ((point1.length != point2.length) || (point1.length != counts.length))
			throw new IllegalArgumentException("mismatched dimensions of input points");
		numD = point1.length;
		minPt = new RealIndex(numD);
		maxPt = new RealIndex(numD);
		dimCounts = new IntegerIndex(numD);
		for (int i = 0; i < numD; i++) {
			minPt.set(i, Math.min(point1[i], point2[i]));
			maxPt.set(i, Math.max(point1[i], point2[i]));
			dimCounts.set(i, Math.abs(counts[i]));
		}
	}

	public SamplingCartesianRealGrid(RealIndex point1, RealIndex point2, IntegerIndex counts) {
		if ((point1.numDimensions() != point2.numDimensions()) || (point1.numDimensions() != counts.numDimensions()))
			throw new IllegalArgumentException("mismatched dimensions of input points");
		numD = point1.numDimensions();
		minPt = point1.allocate();
		maxPt = point1.allocate();
		dimCounts = counts.allocate();
		for (int i = 0; i < numD; i++) {
			minPt.set(i, Math.min(point1.get(i), point2.get(i)));
			maxPt.set(i, Math.max(point1.get(i), point2.get(i)));
			dimCounts.set(i, Math.abs(counts.get(i)));
		}
	}

	@Override
	public int numDimensions() {
		return numD;
	}
	
	// TODO - write tests
	
	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != numD)
			throw new IllegalArgumentException("contains() expects input point to have same dimension as sampling");
		for (int i = 0; i < numD; i++) {
			if (!RealUtils.near((samplePoint.get(i) - minPt.get(i)) % ((maxPt.get(i)-minPt.get(i)) / dimCounts.get(i)), 0, TOL))
				return false;
		}
		return true;
	}

	@Override
	public SamplingIterator<RealIndex> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<RealIndex> {

		private final IntegerIndex index;
		
		private Iterator() {
			index = new IntegerIndex(numD);
			index.set(0, index.get(0) - 1);
		}
		
		@Override
		public boolean hasNext() {
			for (int i = 0; i < numD; i++) {
				if (index.get(i) >= dimCounts.get(i)-1)
					continue;
				return true;
			}
			return false;
		}

		@Override
		public void next(RealIndex value) {
			if (value.numDimensions() != numD)
				throw new IllegalArgumentException("mismatched dimensions of output point");
			for (int i = 0; i < numD; i++) {
				if (index.get(i) < dimCounts.get(i)) {
					index.set(i, index.get(i) + 1);
					for (int j = 0; j < numD; j++) {
						// TODO: does this visit every edge?
						value.set(j, (maxPt.get(j) - minPt.get(j)) * index.get(j) / dimCounts.get(j));
					}
					return;
				}
				else
					index.set(i, 0);
			}
			throw new IllegalArgumentException("next() called on sampling that does not hasNext()");
		}
		
	}

}
