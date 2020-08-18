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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.KMeans;
import nom.bdezonia.zorbage.algorithm.RampFill;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequenceL0Norm;
import nom.bdezonia.zorbage.algorithm.SequenceL1Norm;
import nom.bdezonia.zorbage.algorithm.SequenceL2Norm;
import nom.bdezonia.zorbage.algorithm.SequenceLInfinityNorm;
import nom.bdezonia.zorbage.algorithm.WithinTolerance;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.point.Point;

/**
 * @author Barry DeZonia
 */
class MiscAlgorithms {

	/*
	 * Zorbage has a few miscellaneous algorithms that aren't detailed elsewhere
	 */

	// KMeans is a common algorithm in data science for clustering data that is spatially distributed
	
	void example1() {
		
		int numClusters = 22;
		
		// make a bunch of 2d points
		IndexedDataSource<Point> points = Storage.allocate(4500L, new Point(2));

		// make a bunch of fake clusters
		IndexedDataSource<SignedInt32Member> clusterIndices = Storage.allocate(points.size(), G.INT32.construct());
		
		// fill the point values with random values
		Fill.compute(G.POINT, G.POINT.random(), points);
	
		// assign the points to random clusters
		SignedInt32Member number = G.INT32.construct();
		for (long i = 0; i < points.size(); i++) {
			clusterIndices.get(i, number);
			number.setV((int)(i % clusterIndices.size()));
			clusterIndices.set(i, number);
		}
		
		// cluster the data into groups of points that are "close" together
		KMeans.compute(G.POINT, numClusters, points, clusterIndices);
	}
	
	// make a ramped set of data values
	
	void example2() {

		// make a bunch of numbers
		IndexedDataSource<Float64Member> nums = Storage.allocate(4500L, G.DBL.construct());

		// fill them with a ramp starting at 0 and incrementing by 1 with each step
		RampFill.compute(G.DBL, nums);

		// fill them with a ramp starting at 12.5 and incrementing by 0.25 with each step
		RampFill.compute(G.DBL, new Float64Member(12.5), new Float64Member(0.25), nums);

		// fill them with a ramp starting at 1000 and decrementing by 2 with each step
		RampFill.compute(G.DBL, new Float64Member(1000), new Float64Member(-2), nums);
	}
	
	// Zorbage has a number of algorithms that compute values from sequences of numbers

	void example3() {
		
		@SuppressWarnings("unused")
		boolean result;
		
		Float64Member value = G.DBL.construct();
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(1260, G.DBL.construct());
		
		// returns true if any of a sequence of numbers is infinite
		result = SequenceIsInf.compute(G.DBL, nums);
		
		// returns true if any of a sequence of numbers is nan
		result = SequenceIsNan.compute(G.DBL, nums);

		// returns true if all of a sequence of numbers is zero
		result = SequenceIsZero.compute(G.DBL, nums);
		
		// calc the L0, L1, L2, and LInfinity norms of the given sequences
		SequenceL0Norm.compute(G.DBL, G.DBL, nums, value);
		SequenceL1Norm.compute(G.DBL, G.DBL, nums, value);
		SequenceL2Norm.compute(G.DBL, G.DBL, nums, value);
		SequenceLInfinityNorm.compute(G.DBL, G.DBL, nums, value);
	}
	
	// Zorbage has a tolerancing algorithm for checking nearness
	
	void example4() {
		
		Function2<Boolean,UnsignedInt8Member,UnsignedInt8Member> func =
				new WithinTolerance<>(G.UINT8, G.UINT8, new UnsignedInt8Member(2));
		
		@SuppressWarnings("unused")
		boolean result;
		
		// true
		result = func.call(new UnsignedInt8Member(45), new UnsignedInt8Member(43));
		
		// false
		result = func.call(new UnsignedInt8Member(45), new UnsignedInt8Member(50));
	}
}
