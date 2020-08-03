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
import nom.bdezonia.zorbage.algorithm.ApproxStdDev;
import nom.bdezonia.zorbage.algorithm.ApproxSumOfSquaredDeviationsWithCount;
import nom.bdezonia.zorbage.algorithm.ApproxVariance;
import nom.bdezonia.zorbage.algorithm.BasicStats;
import nom.bdezonia.zorbage.algorithm.MaxElement;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Median;
import nom.bdezonia.zorbage.algorithm.MinElement;
import nom.bdezonia.zorbage.algorithm.MinMaxElement;
import nom.bdezonia.zorbage.algorithm.NanMaxElement;
import nom.bdezonia.zorbage.algorithm.NanMean;
import nom.bdezonia.zorbage.algorithm.NanMedian;
import nom.bdezonia.zorbage.algorithm.NanMinElement;
import nom.bdezonia.zorbage.algorithm.NanMinMaxElement;
import nom.bdezonia.zorbage.algorithm.NanStdDev;
import nom.bdezonia.zorbage.algorithm.NanSum;
import nom.bdezonia.zorbage.algorithm.NanSumWithCount;
import nom.bdezonia.zorbage.algorithm.NanVariance;
import nom.bdezonia.zorbage.algorithm.NonNanValues;
import nom.bdezonia.zorbage.algorithm.Product;
import nom.bdezonia.zorbage.algorithm.SequenceL0Norm;
import nom.bdezonia.zorbage.algorithm.SequenceL1Norm;
import nom.bdezonia.zorbage.algorithm.SequenceL2Norm;
import nom.bdezonia.zorbage.algorithm.SequenceLInfinityNorm;
import nom.bdezonia.zorbage.algorithm.StdDev;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.algorithm.SumWithCount;
import nom.bdezonia.zorbage.algorithm.SummaryStats;
import nom.bdezonia.zorbage.algorithm.Variance;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * @author Barry DeZonia
 */
class Statistics {

	/*
	 * As of the writing of this example Zorbage's statistical methods are pretty basic.
	 * There are plans to add more as collaborators request thing they need related to
	 * their projects.
	 */
	
	// Zorbage can calculate basic statistics from lists of numbers. The numbers can
	// be of any of the many types supported in Zorbage.
	
	void example1() {
		
		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(10, new Float64Member());
		
		Float64Member result1 = G.DBL.construct();

		Float64Member result2 = G.DBL.construct();
		
		Mean.compute(G.DBL, data, result1);
		
		Median.compute(G.DBL, data, result2);
		
		StdDev.compute(G.DBL, data, result1);
		
		Variance.compute(G.DBL, data, result1);
		
		Sum.compute(G.DBL, data, result1);
		
		SumWithCount.compute(G.DBL, data, result1, result2);
		
		Product.compute(G.DBL, data, result1);
		
		MinElement.compute(G.DBL, data, result1);
		
		MaxElement.compute(G.DBL, data, result2);
		
		MinMaxElement.compute(G.DBL, data, result1, result2);
		
		SequenceL0Norm.compute(G.DBL, G.DBL, data, result1);
		
		SequenceL1Norm.compute(G.DBL, G.DBL, data, result1);
		
		SequenceL2Norm.compute(G.DBL, G.DBL, data, result1);
		
		SequenceLInfinityNorm.compute(G.DBL, G.DBL, data, result1);
	}
	
	/*
	 * One of the aspects of the above listed routines is that they are naive algorithms
	 * that compute the mathematically correct result. In practice this can lead to
	 * overflows, underflows, and rounding errors.
	 * 
	 * One way to avoid these issues is to compute the values using high precision
	 * floating point numbers in the calculations. These floating point numbers do not
	 * overflow, underflow, or lose precision. You can wrap any list in a filter that
	 * converts values read from a list into a high precision float. One can use the
	 * same naive algorithms and guarantee you will get the right results.
	 * 
	 */
	
	void example2() {
		
		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(10, new Float64Member());
		
		IndexedDataSource<HighPrecisionMember> filtered =
				new ReadOnlyHighPrecisionDataSource<>(G.DBL, data);
		
		HighPrecisionMember result1 = G.HP.construct();

		HighPrecisionMember result2 = G.HP.construct();
		
		Mean.compute(G.HP, filtered, result1);
		
		Median.compute(G.HP, filtered, result2);
		
		StdDev.compute(G.HP, filtered, result1);
		
		Variance.compute(G.HP, filtered, result1);
		
		Sum.compute(G.HP, filtered, result1);
		
		SumWithCount.compute(G.HP, filtered, result1, result2);
		
		Product.compute(G.HP, filtered, result1);
		
		MinElement.compute(G.HP, filtered, result1);
		
		MaxElement.compute(G.HP, filtered, result2);
		
		MinMaxElement.compute(G.HP, filtered, result1, result2);
		
		SequenceL0Norm.compute(G.HP, G.HP, filtered, result1);
		
		SequenceL1Norm.compute(G.HP, G.HP, filtered, result1);
		
		SequenceL2Norm.compute(G.HP, G.HP, filtered, result1);
		
		SequenceLInfinityNorm.compute(G.HP, G.HP, filtered, result1);
	}

	/*
	 * The high precision approach is useful when you have a large dataset. It does
	 * use a few more cpu cycles to calculate. If cpu cycles are at a premium you can
	 * use the "approximate" algorithms. These algorithms are written to avoid overflows
	 * while working in the native type. It's faster but the results are only approximate.
	 * They are plenty accurate but they may differ in the last places and may be
	 * significantly off for numbers whose square can exceed floating point limits.
	 */
	
	void example3() {

		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(10, new Float64Member());
		
		Float64Member result = G.DBL.construct();

		ApproxStdDev.compute(G.DBL, data, result);
		
		ApproxVariance.compute(G.DBL, data, result);
		
		Float64Member avg = G.DBL.construct();

		Float64Member sumSq = G.DBL.construct();

		Float64Member count = G.DBL.construct();
		
		ApproxSumOfSquaredDeviationsWithCount.compute(G.DBL, data, avg, sumSq, count);
	}

	/*
	 * Finally Zorbage provides methods that will calculate statistics on datasets that
	 * include NaN values. Most of the algorithms take no more space than the naive
	 * algorithms. They basically ignore NaN values and calculate the statistics upon
	 * the remaining data.
	 */
	
	void example4() {

		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(10, new Float64Member());
		
		Float64Member result1 = G.DBL.construct();

		Float64Member result2 = G.DBL.construct();
		
		NanMean.compute(G.DBL, data, result1);
		
		NanMedian.compute(G.DBL, data, result1);
		
		NanStdDev.compute(G.DBL, data, result1);
		
		NanVariance.compute(G.DBL, data, result1);
		
		NanSum.compute(G.DBL, data, result1);
		
		NanSumWithCount.compute(G.DBL, data, result1, result2);
		
		NanMinElement.compute(G.DBL, data, result1);
		
		NanMaxElement.compute(G.DBL, data, result2);
		
		NanMinMaxElement.compute(G.DBL, data, result1, result2);
		
	}
	
	/*
	 * Those NaN oriented algorithms use the naive statistical algorithms. You can use
	 * the method NonNanValues to get a list of values that are not NaN and then pass
	 * them to a naive or approximate algorithms and even convert them to high precision
	 * floats if you need. This takes some more memory to do but you have the flexibility
	 * you might want.
	 */
	
	void example5() {
		
		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(10, new Float64Member());
		
		IndexedDataSource<Float64Member> nonNan = NonNanValues.compute(G.DBL, data);
		
		// find an approximate variance value of the nonNan data
		
		Float64Member result1 = G.DBL.construct();

		ApproxVariance.compute(G.DBL, nonNan, result1);

		// find an exact variance value of the nonNan data
		
		IndexedDataSource<HighPrecisionMember> hiPrec =
				new ReadOnlyHighPrecisionDataSource<>(G.DBL, nonNan);
		
		HighPrecisionMember result2 = G.HP.construct();
		
		Variance.compute(G.HP, hiPrec, result2);
	}
	
	/*
	 * FYI here is one method for getting a quick statistical summary of a set of numbers.
	 */
	
	void example6() {
		
		IndexedDataSource<Float32Member> data = ArrayStorage.allocateFloats(new float[] {43,7,99,1,2,3,100,55,31});
		
		Float32Member min = G.FLT.construct();
		Float32Member q1 = G.FLT.construct();
		Float32Member median = G.FLT.construct();
		Float32Member mean = G.FLT.construct();
		Float32Member q3 = G.FLT.construct();
		Float32Member max = G.FLT.construct();
		
		SummaryStats.compute(G.FLT, data, min, q1, median, mean, q3, max);
		
		// If your contains NaN values you can calc values working around the NaNs
		
		SignedInt64Member numNoData = G.INT64.construct();

		SummaryStats.computeSafe(G.FLT, data, min, q1, median, mean, q3, max, numNoData);
	}

	/*
	 * FYI here is another method for getting a quick statistical summary of a set of numbers.
	 */
	
	void example7() {
		
		IndexedDataSource<Float32Member> data = ArrayStorage.allocateFloats(new float[] {43,7,99,1,2,3,100,55,31});
		
		Float32Member mean = G.FLT.construct();
		Float32Member stdErrMean = G.FLT.construct();
		Float32Member stddev = G.FLT.construct();
		Float32Member sampleVariance = G.FLT.construct();
		Float32Member sampleSkew = G.FLT.construct();
		Float32Member excessKurtosis = G.FLT.construct();

		BasicStats.compute(G.FLT, data, mean, stdErrMean, stddev, sampleVariance, sampleSkew, excessKurtosis);
	}
}
