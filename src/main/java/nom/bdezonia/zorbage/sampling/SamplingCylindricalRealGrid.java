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
 * {@link SamplingCylindricalGrid} is a 3-d {@link Sampling} in real cylindrical space.
 * It defines the grid origin's r, z, and theta coordinates plus spacings and counts.
 * This allows one to build cylindrical coordinate grids that are partially complete
 * if so desired.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingCylindricalRealGrid implements Sampling<RealIndex> {

	private final SamplingGeneral<RealIndex> sampling;
	
	private double TOL = 0.000001;
	
	public SamplingCylindricalRealGrid(
			double dr, int rCount,
			double dtheta, int thetaCount,
			double dz, int zCount)
	{
		if (rCount < 1 || thetaCount < 1 || zCount < 1)
			throw new IllegalArgumentException("counts must be >= 1 in cylindrical grid");
		sampling = new SamplingGeneral<>(3);
		RealIndex value = new RealIndex(sampling.numDimensions());
		for (int z = 0; z < zCount; z++) {
			double zed = z * dz;
			for (int th = 0; th < thetaCount; th++) {
				double angle = th * dtheta;
				for (int r = 0; r < rCount; r++) {
					double radius = r * dr;
					value.set(0, FastMath.cos(angle) * radius);  // xcoord
					value.set(1, FastMath.sin(angle) * radius);  // ycoord
					value.set(2, zed);  // zcoord
					if ((r != 0) || (r == 0 && th == 0)) // only add origin once per z plane
						sampling.add(value);
				}
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
		return 3;
	}

	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != 3)
			throw new IllegalArgumentException("contains() sample point does not match dimensionality");
		RealIndex tmp = new RealIndex(sampling.numDimensions());
		SamplingIterator<RealIndex> iter = sampling.iterator();
		while (iter.hasNext()) {
			iter.next(tmp);
			if (RealUtils.distance3d(
					samplePoint.get(0), samplePoint.get(1), samplePoint.get(2),
					tmp.get(0), tmp.get(1), tmp.get(2)) < TOL)
				return true;
		}
		return false;
	}

	@Override
	public SamplingIterator<RealIndex> iterator() {
		return sampling.iterator();
	}
}
