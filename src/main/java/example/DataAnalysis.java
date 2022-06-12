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
import nom.bdezonia.zorbage.algorithm.ApproxStdDev;
import nom.bdezonia.zorbage.algorithm.ApproxVariance;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.StdDev;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.algorithm.Variance;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Algebra;
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class DataAnalysis {

	// Zorbage has a few nice wrinkles for accurately calculating numbers from data.
	
	// When you are summing a lot of numbers most programs are susceptible to overflow.
	// However Zorbage can work around limitations like these.
	
	void example1() {
		
		IndexedDataSource<UnsignedInt32Member> uints =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.UINT32.construct(), Integer.MAX_VALUE);
		
		// elsewhere: fill the list with values
		
		// now sum all the numbers in the list
		
		UnsignedInt32Member sum = G.UINT32.construct();
		
		Sum.compute(G.UINT32, uints, sum);  // sum may have overflowed
		
		// so this is how we avoid overflow if we're worried about it
		
		HighPrecisionMember sum2 = G.HP.construct();
		
		ReadOnlyHighPrecisionDataSource<UnsignedInt32Algebra,UnsignedInt32Member> filteredData =
				new ReadOnlyHighPrecisionDataSource<>(G.UINT32, uints);
		
		Sum.compute(G.HP, filteredData, sum2);  // sum2 cannot overflow
	}
	
	// Zorbage can also avoid roundoff errors, especially when using large amounts of data
	
	void example2() {

		HighPrecisionAlgebra.setPrecision(150);
		
		IndexedDataSource<UnsignedInt32Member> uints =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.UINT32.construct(), Integer.MAX_VALUE);
		
		// elsewhere: fill the list with values
		
		// now sum all the numbers in the list avoiding overflow and rounding errors
		
		HighPrecisionMember sum = G.HP.construct();
		
		ReadOnlyHighPrecisionDataSource<UnsignedInt32Algebra,UnsignedInt32Member> filteredData =
				new ReadOnlyHighPrecisionDataSource<>(G.UINT32, uints);
		
		Sum.compute(G.HP, filteredData, sum);  // sum cannot have lost precision within 150 places
	}

	// This approach also makes sure means, variances, and stddevs are perfectly accurate
	
	void example3() {

		HighPrecisionAlgebra.setPrecision(150);
		
		IndexedDataSource<UnsignedInt32Member> uints =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.UINT32.construct(), Integer.MAX_VALUE);
		
		// elsewhere: fill the list with values
		
		// now sum all the numbers in the list avoiding overflow and rounding errors
		
		HighPrecisionMember mean = G.HP.construct();
		
		HighPrecisionMember variance = G.HP.construct();
		
		HighPrecisionMember stddev = G.HP.construct();
		
		ReadOnlyHighPrecisionDataSource<UnsignedInt32Algebra,UnsignedInt32Member> filteredData =
				new ReadOnlyHighPrecisionDataSource<>(G.UINT32, uints);
		
		Mean.compute(G.HP, filteredData, mean);  // accurate to 150 decimal places
		
		Variance.compute(G.HP, filteredData, variance);  // accurate to 150 decimal places
		
		StdDev.compute(G.HP, filteredData, stddev);  // accurate to 150 decimal places
	}

	// The exact calculations use naive mathematically correct implementations. When using the
	// high precision infrastructure you get accurate results. This comes at a cost of increased
	// processing time. If you need to trade off accuracy for speed you can use the approximate
	// algorithms. They guard against overflow and roundoff errors but their results are as a
	// consequence less precise. In fact some values simply cannot be calculated using these
	// methods (for instance doing math with numbers that approach max float values etc.).
	
	void example4() {

		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), 1000000);
		
		// elsewhere fill list with data
		
		// now calc approximate results
		
		Float64Member variance = G.DBL.construct();
		
		Float64Member stddev = G.DBL.construct();
		
		ApproxVariance.compute(G.DBL, data, variance);  // close to correct. faster than exact.
		
		ApproxStdDev.compute(G.DBL, data, stddev);  // close to correct. faster than exact.
		
	}
	
}
