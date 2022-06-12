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
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.InsertionSort;
import nom.bdezonia.zorbage.algorithm.Partition;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.algorithm.StablePartition;
import nom.bdezonia.zorbage.algorithm.StableSort;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReversedDataSource;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class Sorting {
	
	// Zorbage has a number of sorting methods available when you need to sort data.

	// basic sorting : notice that sorting is an inplace operation
	
	void example1() {
		IndexedDataSource<Float64Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(
						G.DBL.construct(), 
						new double[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Sort.compute(G.DBL, nums);  // this sort is QuickSort based
	}

	// stable sorting : stable sorting can be a little slower but has better performance
	//   on mostly sorted data

	void example2() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		StableSort.compute(G.INT32, nums);  // this sort is MergeSort based
	}
	
	// how to reverse sort: method 1
	
	void example3() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		Sort.compute(G.INT32, rev);
	}
	
	// how to reverse sort: method 2
	
	void example4() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		Sort.compute(G.INT32, G.INT32.isGreater(), rev);
	}
	
	// how to sort numbers with a custom sort routine

	void example5() {
		
		// normally complex numbers are not ordered and cannot be sorted
		
		IndexedDataSource<ComplexFloat32Member> nums = ArrayStorage.allocate(G.CFLT.construct(), 75);
		
		Fill.compute(G.CFLT, G.CFLT.random(), nums);
		
		// but we will define a custom function that sorts based upon the modulus of each complex number
		
		Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member> func =
				new Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member>()
		{
			@Override
			public Boolean call(ComplexFloat32Member a, ComplexFloat32Member b) {

				return Math.hypot(a.r(), a.i()) < Math.hypot(b.r(), b.i());
			}
		};
		
		Sort.compute(G.CFLT, func, nums);
	}

	// there is another sort method you might occasionally use
	
	void example6() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		IndexedDataSource<SignedInt32Member> rev = new ReversedDataSource<>(nums);
		InsertionSort.compute(G.INT32, rev);
	}
	
	// Related to sorts are partition functions. Zorbage supports a couple.
	
	// Partition: a one time partition: might be the fastest algo possible
	
	void example7() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Function1<Boolean,SignedInt32Member> lessThan4 =
				new Function1<Boolean,SignedInt32Member>()
		{
			@Override
			public Boolean call(SignedInt32Member value) {
				return value.v() < 4;
			}
		};
		Partition.compute(G.INT32, lessThan4, nums);
	}
	
	// StablePartition: something you can call repeatedly to partition a set of numbers by multiple criteria
	
	void example8() {
		IndexedDataSource<SignedInt32Member> nums =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), 
				new int[] {-3,1,17,9,-4,5,12,-6,7,7,7}
			);
		Function1<Boolean,SignedInt32Member> lessThan4 =
				new Function1<Boolean,SignedInt32Member>()
		{
			@Override
			public Boolean call(SignedInt32Member value) {
				return value.v() < 4;
			}
		};
		StablePartition.compute(G.INT32, lessThan4, nums);
		Function1<Boolean,SignedInt32Member> isOdd =
				new Function1<Boolean,SignedInt32Member>()
		{
			@Override
			public Boolean call(SignedInt32Member value) {
				return (value.v() & 1) == 1;
			}
		};
		StablePartition.compute(G.INT32, isOdd, nums);
	}
}