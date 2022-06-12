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
package nom.bdezonia.zorbage.region;

import nom.bdezonia.zorbage.misc.RealUtils;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.sampling.RealIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RegionCircle implements Region<RealIndex> {

	private final double cx, cy, radius;
	
	public RegionCircle(double cx, double cy, double radius) {
		if (radius < 0)
			throw new IllegalArgumentException("negative radius in RegionCircle");
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}
	
	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public boolean contains(RealIndex samplePoint) {
		if (samplePoint.numDimensions() != 2)
			throw new IllegalArgumentException("incorrect dimensions of sample point in RegionCircle::contains()");
		return RealUtils.distance2d(samplePoint.get(0), samplePoint.get(1), cx, cy) <= radius;
	}

	private final Procedure1<RealIndex> MAX_BOUND = new Procedure1<RealIndex>() {
		@Override
		public void call(RealIndex max) {
			if (max.numDimensions() != 2)
				throw new IllegalArgumentException("incorrect dimensions of point in RegionCircle::maxBound()");
			max.set(0, cx + radius);
			max.set(1, cy + radius);
		}
	};
	
	@Override
	public Procedure1<RealIndex> maxBound() {
		return MAX_BOUND;
	}

	private final Procedure1<RealIndex> MIN_BOUND = new Procedure1<RealIndex>() {
		@Override
		public void call(RealIndex min) {
			if (min.numDimensions() != 2)
				throw new IllegalArgumentException("incorrect dimensions of point in RegionCircle::minBound()");
			min.set(0, cx - radius);
			min.set(1, cy - radius);
		}
	};
	
	@Override
	public Procedure1<RealIndex> minBound() {
		return MIN_BOUND;
	}

}
