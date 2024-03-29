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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.BasicFFT;
import nom.bdezonia.zorbage.algorithm.BasicInvFFT;
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
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.WithinTolerance;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.geom.point.Point;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class MiscAlgorithms {

	/*
	 * Zorbage has a few miscellaneous algorithms that aren't detailed elsewhere
	 */

	// BasicFFT/BasicInvFFT: work in the complex domain very easily
	
	void example1() {

		// I start with real data storage
		
		IndexedDataSource<Float64Member> data = Storage.allocate(G.DBL.construct(), 700);
		
		// ... at sone earlier point assume data has been filled with something valid

		// move from the real domain to the frequency domain
		
		IndexedDataSource<ComplexFloat64Member> cData =
				BasicFFT.compute(G.CDBL, G.DBL, data);
		
		// define a procedure that will scale any complex freq > 2000 to 2000
		
		Procedure2<ComplexFloat64Member,ComplexFloat64Member> freqLess2000 =
				new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
		{
			private Float64Member twoThou = G.DBL.construct(2000);
			private Float64Member norm = G.DBL.construct();
			private Float64Member scale = G.DBL.construct();
	
			@Override
			public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
				
				G.CDBL.norm().call(a, norm);
				
				if (G.DBL.isLessEqual().call(norm, twoThou)) {
					
					b.set(a);
				}
				else {
					
					G.DBL.divide().call(norm, twoThou, scale);
					G.DBL.invert().call(scale, scale);
					G.CDBL.scaleByDouble().call(scale.v(), a, b);
				}
			}
		};

		// apply it to the complex data
		
		Transform2.compute(G.CDBL, freqLess2000, cData, cData);
		
		// transform out of the frequency domain and back into the real domain
		
		BasicInvFFT.compute(G.CDBL, G.DBL, cData, data);
		
		// now data contains the original values with high variability lessened
	}
	
	// KMeans is a common algorithm in data science for clustering data that is spatially distributed
	
	void example2() {
		
		int numClusters = 22;
		
		// make a bunch of 2d points
		IndexedDataSource<Point> points = Storage.allocate(new Point(2), 4500L);

		// make a bunch of fake clusters
		IndexedDataSource<SignedInt32Member> clusterIndices = Storage.allocate(G.INT32.construct(), points.size());
		
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
		KMeans.compute(numClusters, points, clusterIndices);
	}
	
	// make a ramped set of data values
	
	void example3() {

		// make a bunch of numbers
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), 4500L);

		// fill them with a ramp starting at 0 and incrementing by 1 with each step
		RampFill.compute(G.DBL, nums);

		// fill them with a ramp starting at 12.5 and incrementing by 0.25 with each step
		RampFill.compute(G.DBL, new Float64Member(12.5), new Float64Member(0.25), nums);

		// fill them with a ramp starting at 1000 and decrementing by 2 with each step
		RampFill.compute(G.DBL, new Float64Member(1000), new Float64Member(-2), nums);
	}
	
	// Zorbage has a number of algorithms that compute values from sequences of numbers

	void example4() {
		
		@SuppressWarnings("unused")
		boolean result;
		
		Float64Member value = G.DBL.construct();
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), 1260);
		
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
	
	void example5() {
		
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
