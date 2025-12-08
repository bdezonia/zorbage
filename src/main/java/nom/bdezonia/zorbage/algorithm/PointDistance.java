/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.geom.point.Point;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PointDistance {

	// TODO this class is poorly named. What kind of distance? Euclidean? Put in name?
	// Is it a norm? L2?
	
	// TODO - relies on doubles. Could rely on a U type that currently is Float64Member.
	
	// do not instantiate
	
	private PointDistance() { }
	
	/**
	 * Compute the distance between two real n-dimensional {@link Point}s.
	 * 
	 * @param a
	 * @param b
	 */
	public static
		void compute(Point a, Point b, Float64Member result)
	{
		if (a.numDimensions() != b.numDimensions())
			throw new IllegalArgumentException("points do not share the same dimensionality");
		// Do a two pass hypot approach to avoid overflow
		double max = 0;
		for (int i = 0; i < a.numDimensions(); i++) {
			double term = Math.abs(a.component(i) - b.component(i));
			max = Math.max(term, max);
		}
		if (max == 0) {
			result.setV(0);
			return;
		}
		double sum = 0;
		for (int i = 0; i < a.numDimensions(); i++) {
			double term = (a.component(i) - b.component(i)) / max;
			sum += term * term; 
		}
		result.setV(max * Math.sqrt(sum));
	}
}
