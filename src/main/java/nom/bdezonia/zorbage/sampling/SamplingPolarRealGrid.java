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

import net.jafama.FastMath;
import nom.bdezonia.zorbage.misc.RealUtils;

/**
 * {@link SamplingPolarRealGrid} is a 2-d {@link Sampling} in real polar space.
 * It defines the grid origin's r, and theta coordinates plus spacings and
 * counts. This allows one to build polar coordinate grids that are partially
 * complete if so desired.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingPolarRealGrid implements Sampling<RealIndex> {

	private final SamplingGeneral<RealIndex> sampling;
	
	private double TOL = 0.000001;
	
	public SamplingPolarRealGrid(
			double dr, int rCount,
			double dtheta, int thetaCount)
	{
		if (rCount < 1 || thetaCount < 1)
			throw new IllegalArgumentException("counts must be >= 1 in polar grid");
		sampling = new SamplingGeneral<>(2);
		RealIndex value = new RealIndex(sampling.numDimensions());
		for (int th = 0; th < thetaCount; th++) {
			double angle = th * dtheta;
			for (int r = 0; r < rCount; r++) {
				double radius = r * dr;
				value.set(0, FastMath.cos(angle) * radius);  // xcoord
				value.set(1, FastMath.sin(angle) * radius);  // ycoord
				if ((r != 0) || (r == 0 && th == 0)) // only add origin once
					sampling.add(value);
			}
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
		return 2;
	}

	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != 2)
			throw new IllegalArgumentException("contains() sample point does not match dimensionality");
		RealIndex tmp = new RealIndex(sampling.numDimensions());
		SamplingIterator<RealIndex> iter = sampling.iterator();
		while (iter.hasNext()) {
			iter.next(tmp);
			if (RealUtils.distance2d(
					samplePoint.get(0), samplePoint.get(1),	tmp.get(0), tmp.get(1)) < TOL)
				return true;
		}
		return false;
	}

	@Override
	public SamplingIterator<RealIndex> iterator() {
		return sampling.iterator();
	}
}
