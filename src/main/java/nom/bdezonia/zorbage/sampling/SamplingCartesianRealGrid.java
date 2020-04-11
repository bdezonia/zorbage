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
package nom.bdezonia.zorbage.sampling;

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

	private final SamplingGeneral<RealIndex> sampling;

	private double TOL = 0.000001;

	public SamplingCartesianRealGrid(double[] point1, double[] point2, long[] counts) {
		if ((point1.length != point2.length) || (point1.length != counts.length))
			throw new IllegalArgumentException("mismatched dimensions of input points");
		int numD = point1.length;
		sampling = new SamplingGeneral<>(numD);
		RealIndex minPt = new RealIndex(numD);
		RealIndex maxPt = new RealIndex(numD);
		IntegerIndex dimCounts = new IntegerIndex(numD);
		for (int i = 0; i < numD; i++) {
			minPt.set(i, Math.min(point1[i], point2[i]));
			maxPt.set(i, Math.max(point1[i], point2[i]));
			dimCounts.set(i, Math.abs(counts[i]));
		}
		IntegerIndex index = dimCounts.allocate();
		index.set(0, index.get(0) - 1);
		RealIndex pt = minPt.allocate();
		while (hasNext(index, dimCounts)) {
			next(index, dimCounts, minPt, maxPt, pt);
			sampling.add(pt);
		}
	}

	public SamplingCartesianRealGrid(RealIndex point1, RealIndex point2, IntegerIndex counts) {
		if ((point1.numDimensions() != point2.numDimensions()) ||
				(point1.numDimensions() != counts.numDimensions()))
			throw new IllegalArgumentException("mismatched dimensions of input points");
		int numD = point1.numDimensions();
		sampling = new SamplingGeneral<>(numD);
		RealIndex minPt = new RealIndex(numD);
		RealIndex maxPt = new RealIndex(numD);
		IntegerIndex dimCounts = new IntegerIndex(numD);
		for (int i = 0; i < numD; i++) {
			minPt.set(i, Math.min(point1.get(i), point2.get(i)));
			maxPt.set(i, Math.max(point1.get(i), point2.get(i)));
			dimCounts.set(i, Math.abs(counts.get(i)));
		}
		IntegerIndex index = dimCounts.allocate();
		index.set(0, index.get(0) - 1);
		RealIndex pt = minPt.allocate();
		while (hasNext(index, dimCounts)) {
			next(index, dimCounts, minPt, maxPt, pt);
			sampling.add(pt);
		}
	}

	public double tolerance() {
		return TOL;
	}
	
	public void setTolerance(double tol) {
		if (tol < 0)
			throw new IllegalArgumentException("tolerance must be >= 0");
		TOL = tol;
	}
	
	@Override
	public int numDimensions() {
		return sampling.numDimensions();
	}

	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != sampling.numDimensions())
			throw new IllegalArgumentException("contains() sample point does not match dimensionality");
		RealIndex tmp = new RealIndex(sampling.numDimensions());
		SamplingIterator<RealIndex> iter = sampling.iterator();
		while (iter.hasNext()) {
			iter.next(tmp);
			double dist = 0;
			for (int i = 0; i < tmp.numDimensions(); i++) {
				double delta = samplePoint.get(i) - tmp.get(i);
				dist += (delta * delta);
			}
			dist = Math.sqrt(dist);
			if (dist < TOL)
				return true;
		}
		return false;
	}

	@Override
	public SamplingIterator<RealIndex> iterator() {
		return sampling.iterator();
	}

	private boolean hasNext(IntegerIndex index, IntegerIndex dimCounts) {
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) >= dimCounts.get(i)-1)
				continue;
			return true;
		}
		return false;
	}

	private void next(IntegerIndex index, IntegerIndex dimCounts, RealIndex minPt, RealIndex maxPt, RealIndex value) {
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) < dimCounts.get(i)-1) {
				index.set(i, index.get(i) + 1);
				for (int j = 0; j < index.numDimensions(); j++) {
					double val = minPt.get(j);
					val += (maxPt.get(j) - minPt.get(j)) / (dimCounts.get(j) - 1) * index.get(j);
					value.set(j, val);
				}
				return;
			}
			else
				index.set(i, 0);
		}
		throw new IllegalArgumentException("next() called on sampling that does not hasNext()");
	}
}
