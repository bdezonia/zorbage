/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.sampling;

import nom.bdezonia.zorbage.misc.RealUtils;

/**
 * {@link SamplingSphericalRealGrid} is a 3-d {@link Sampling} in real spherical space.
 * It defines the grid origin's r, theta, and phi coordinates plus spacings and counts.
 * This allows one to build spherical coordinate grids that are partially complete
 * if so desired.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingSphericalRealGrid implements Sampling<RealIndex> {

	private final SamplingGeneral<RealIndex> sampling;
	
	private double TOL = 0.000001;
	
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
					double sineTheta = Math.sin(angleTheta);
					double cosineTheta = Math.cos(angleTheta);
					double sinePhi = Math.sin(anglePhi);
					double cosinePhi = Math.cos(anglePhi);
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
