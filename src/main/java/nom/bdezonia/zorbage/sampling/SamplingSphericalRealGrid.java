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
package nom.bdezonia.zorbage.sampling;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.misc.RealUtils;

/**
 * {@link SamplingSphericalGrid} is a 3-d {@link Sampling} in real spherical space.
 * It defines the grid origin's r, theta, and phi coordinates plus spacings and counts.
 * This allows one to build spherical coordinate grids that are partially complete
 * if so desired.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingSphericalRealGrid implements Sampling<RealIndex> {

	private final SamplingGeneral<RealIndex> sampling;
	
	// TODO: calc me from grid cell size
	
	private final double TOL = 0.000001;
	
	public SamplingSphericalRealGrid(
			double dr, int rCount,
			double dtheta, int thetaCount,
			double dphi, int phiCount)
	{
		if (rCount < 1 || thetaCount < 1 || phiCount < 1)
			throw new IllegalArgumentException("counts must be >= 1 in spherical grid");
		sampling = new SamplingGeneral<>(3);
		RealIndex value = new RealIndex(sampling.numDimensions());
		boolean originAdded = false;
		for (int p = 0; p < phiCount; p++) {
			double anglePhi = p * dphi;
			for (int th = 0; th < thetaCount; th++) {
				double angleTheta = th * dtheta;
				for (int r = 0; r < rCount; r++) {
					double radius = r * dr;
					double sineTheta = FastMath.sin(angleTheta);
					double cosineTheta = FastMath.cos(angleTheta);
					double sinePhi = FastMath.sin(anglePhi);
					double cosinePhi = FastMath.cos(anglePhi);
					// http://tutorial.math.lamar.edu/Classes/CalcIII/SphericalCoords.aspx
					value.set(0, radius * sinePhi * cosineTheta); // xcoord
					value.set(1, radius * sinePhi * sineTheta);  // ycoord
					value.set(2, radius * cosinePhi);  // zcoord
					if (value.get(0) == 0 && value.get(1) == 0 && value.get(2) == 0) {
						if (!originAdded) {
							sampling.add(value);
							originAdded = true;
						}
					}
					else
						sampling.add(value);
				}
			}
		}
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
