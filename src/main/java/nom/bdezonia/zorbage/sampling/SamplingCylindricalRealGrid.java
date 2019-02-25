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
 * {@link SamplingCylindricalGrid} is a 3-d {@link Sampling} in real cylindrical space.
 * It defines the grid origin's r, z, and theta coordinates plus spacings and counts.
 * This allows one to build cylindrical coordinate grids that are partially complete
 * if so desired.
 * .
 * @author Barry DeZonia
 *
 */
public class SamplingCylindricalRealGrid implements Sampling<RealIndex> {

	private final double r, dr, theta, dtheta, z, dz;
	private final int rCount, thetaCount, zCount;
	
	// TODO: calc me from grid cell size
	
	private final double TOL = 0.000001;
	
	public SamplingCylindricalRealGrid(
			double r, double dr, int rCount,
			double theta, double dtheta, int thetaCount,
			double z, double dz, int zCount)
	{
		this.r = r;
		this.dr = dr;
		this.theta = theta;
		this.dtheta = dtheta;
		this.z = z;
		this.dz = dz;
		this.rCount = rCount;
		this.thetaCount = thetaCount;
		this.zCount = zCount;
		if (rCount < 1 || thetaCount < 1 || zCount < 1)
			throw new IllegalArgumentException("counts must be >= 1 in cylindrical grid");
	}
	
	@Override
	public int numDimensions() {
		return 3;
	}

	// TODO - write tests
	
	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != 3)
			throw new IllegalArgumentException("contains() sample point does not match dimensionality");
		double x = samplePoint.get(0);
		double y = samplePoint.get(1);
		double z = samplePoint.get(2);
		double z1 = this.z;
		double z2 = this.z + dz*zCount;
		if (z < Math.min(z1, z2) - TOL) return false;
		if (z > Math.max(z1, z2) + TOL) return false;
		if (!RealUtils.near((z - this.z) % dz, 0, TOL)) return false;
		double r1 = this.r;
		double r2 = this.r + dr*rCount;
		double r = RealUtils.distance3d(0, 0, 0, x, y, z);
		if (r < Math.min(r1, r2) - TOL) return false;
		if (r > Math.max(r1, r2) + TOL) return false;
		if (!RealUtils.near((r - this.r) % dr, 0, TOL)) return false;
		double theta = Math.atan2(y, x);
		double theta1 = this.theta;
		double theta2 = this.theta + dtheta * thetaCount;
		while (theta < 0) theta += Math.PI * 2;
		while (theta1 < 0) theta1 += Math.PI * 2;
		while (theta2 < 0) theta2 += Math.PI * 2;
		if (theta1 < theta2) {
			if (theta > theta2 + TOL) return false;
			if (theta < theta1 - TOL) return false;
			if (!RealUtils.near((theta - theta1) % dtheta, 0, TOL)) return false;
		}
		else { // theta1 > theta2 since thetaCount >= 1
			if (theta > theta1 + TOL) return false;
			if (theta < theta2 - TOL) return false;
			if (!RealUtils.near((theta - theta2) % dtheta, 0, TOL)) return false;
		}
		return true;
	}

	@Override
	public SamplingIterator<RealIndex> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<RealIndex> {

		private int tr;
		private int ttheta;
		private int tz;
		
		private Iterator() {
			tr = -1;
			ttheta = 0;
			tz = 0;
		}
		
		@Override
		public boolean hasNext() {
			return !(tr == rCount-1 && ttheta == thetaCount-1 && tz == zCount-1);
		}

		// TODO will the origin be counted multiple times?
		
		@Override
		public void next(RealIndex value) {
			if (value.numDimensions() != 3)
				throw new IllegalArgumentException("value does not have correct dimensions");
			tr++;
			if (tr >= rCount) {
				tr = 0;
				ttheta++;
				if (ttheta >= thetaCount) {
					ttheta = 0;
					tz++;
					if (tz >= zCount)
						throw new IllegalArgumentException("next() called when do not hasNext()");
				}
			}
			final double radius = r + tr*dr;
			final double angle = theta + ttheta*dtheta;
			final double height = z + tz*dz;
			value.set(0, FastMath.cos(angle) * radius);  // xcoord
			value.set(1, FastMath.sin(angle) * radius);  // ycoord
			value.set(2, height);  // zcoord
		}
		
	}

}
