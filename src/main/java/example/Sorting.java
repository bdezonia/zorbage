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

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.InsertionSort;
import nom.bdezonia.zorbage.algorithm.Partition;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.algorithm.StablePartition;
import nom.bdezonia.zorbage.algorithm.StableSort;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReversedDataSource;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * @author Barry DeZonia
 */
class Sorting {
	
	// Zorbage has a number of sorting methods available when you need to sort data.

	// basic sorting : notice that sorting is an inplace operation
	
	void example1() {
		IndexedDataSource<Float64Member> nums = ArrayStorage.allocateDoubles(
				new double[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Sort.compute(G.DBL, nums);  // this sort is QuickSort based
	}

	// stable sorting : stable sorting can be a little slower but has better performance
	//   on mostly sorted data

	void example2() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		StableSort.compute(G.INT32, nums);  // this sort is MergeSort based
	}
	
	// how to reverse sort: method 1
	
	void example3() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		Sort.compute(G.INT32, rev);
	}
	
	// how to reverse sort: method 2
	
	void example4() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		Sort.compute(G.INT32, G.INT32.isGreater(), rev);
	}
	
	// how to sort numbers with a custom sort routine

	void example5() {
		
		// normally complex numbers are not ordered and cannot be sorted
		
		IndexedDataSource<ComplexFloat32Member> nums = ArrayStorage.allocate(75, G.CFLT.construct());
		
		Fill.compute(G.CFLT, G.CFLT.random(), nums);
		
		// but we will define a custom function that sorts based upon the modulus of each complex number
		
		Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member> func =
				new Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member>()
		{
			@Override
			public Boolean call(ComplexFloat32Member a, ComplexFloat32Member b) {

				return FastMath.hypot(a.r(), a.i()) < FastMath.hypot(b.r(), b.i());
			}
		};
		
		Sort.compute(G.CFLT, func, nums);
	}

	// there is another sort method you might occasionally use
	
	void example6() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		InsertionSort.compute(G.INT32, rev);
	}
	
	// Related to sorts are partition functions. Zorbage supports a couple.
	
	// Partition: a one time partition: might be the fastest algo possible
	
	void example7() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Predicate<SignedInt32Member> lessThan4 =
				new Predicate<SignedInt32Member>()
		{
			@Override
			public boolean isTrue(SignedInt32Member value) {
				return value.v() < 4;
			}
		};
		Partition.compute(G.INT32, lessThan4, nums);
	}
	
	// StablePartition: something you can call repeatedly to partition a set of numbers by multiple criteria
	
	void example8() {
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Predicate<SignedInt32Member> lessThan4 =
				new Predicate<SignedInt32Member>()
		{
			@Override
			public boolean isTrue(SignedInt32Member value) {
				return value.v() < 4;
			}
		};
		StablePartition.compute(G.INT32, lessThan4, nums);
		Predicate<SignedInt32Member> isOdd =
				new Predicate<SignedInt32Member>()
		{
			@Override
			public boolean isTrue(SignedInt32Member value) {
				return (value.v() & 1) == 1;
			}
		};
		StablePartition.compute(G.INT32, isOdd, nums);
	}
}