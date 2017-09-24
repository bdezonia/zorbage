/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.region.sampling;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingCartesianIntegerGrid implements Sampling<IntegerIndex> {

	private final int numD;
	private final IntegerIndex minPt;
	private final IntegerIndex maxPt;
	
	public SamplingCartesianIntegerGrid(long[] point1, long[] point2) {
		if (point1.length != point2.length)
			throw new IllegalArgumentException("mismatched dimensions of input points");
		numD = point1.length;
		minPt = new IntegerIndex(numD);
		maxPt = new IntegerIndex(numD);
		for (int i = 0; i < numD; i++) {
			minPt.set(i, Math.min(point1[i], point2[i]));
			maxPt.set(i, Math.max(point1[i], point2[i]));
		}
	}

	@Override
	public int numDimensions() {
		return numD;
	}
	
	@Override
	public boolean contains(IntegerIndex samplePoint) {
		if (samplePoint.numDimensions() != numD)
			throw new IllegalArgumentException("contains() expects input point to have same dimension as sampling");
		for (int i = 0; i < numD; i++) {
			if (samplePoint.get(i) < minPt.get(i)) return false;
			if (samplePoint.get(i) > maxPt.get(i)) return false;
		}
		return true;
	}

	@Override
	public SamplingIterator<IntegerIndex> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<IntegerIndex> {

		private final IntegerIndex index;
		
		private Iterator() {
			// Note: the last line in this method will fail if coord[0] == Long.MIN_VALUE
			if (minPt.get(0) == Long.MIN_VALUE)
				throw new IllegalArgumentException("cannot handle min point at Long.MIN_VALUE");
			index = minPt.duplicate();
			index.set(0, index.get(0) - 1);
		}
		
		@Override
		public boolean hasNext() {
			return !index.equals(maxPt);
		}

		@Override
		public void next(IntegerIndex value) {
			if (value.numDimensions() != numD)
				throw new IllegalArgumentException("mismatched dimensions of output point");
			for (int i = 0; i < numD; i++) {
				if (index.get(i) < maxPt.get(i)) {
					index.set(i, index.get(i) + 1);
					for (int j = 0; j < numD; j++) {
						value.set(j, index.get(j));
					}
					return;
				}
				else
					index.set(i, minPt.get(i));
			}
			throw new IllegalArgumentException("next() called on sampling that does not hasNext()");
		}
		
	}

}
