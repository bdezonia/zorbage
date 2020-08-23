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

/**
 * @author Barry DeZonia
 */
class CppSTL {

	// Zorbage has implemented most of the algorithms as defined in the C++ Standard
	// Template library as documented on cppreference.com. See here for more info:
	//   https://en.cppreference.com/w/cpp/algorithm
	
	void example1() {
		
		// AllOf.compute(Algebra<T,U> algebra, Predicate<U> condition, IndexedDataSource<U> a);
		
		// NoneOf.compute(Algebra<T,U> algebra, Predicate<U> condition, IndexedDataSource<U> a);
		
		// AnyOf.compute(Algebra<T,U> algebra, Predicate<U> condition, IndexedDataSource<U> a);

		//   see https://en.cppreference.com/w/cpp/algorithm/all_any_none_of
	}

	void example2() {

		// ForEach.compute(Algebra<T,U> algU, Procedure2<U,U> proc, IndexedDataSource<U> a);
		
		//   see https://en.cppreference.com/w/cpp/algorithm/for_each
	}
	
	void example3() {
		
		// Count.compute(Algebra<T,U> algebra, Algebra<V,W> addAlgebra, U value, IndexedDataSource<U> a, W sum);
		
		// CountIf.compute(Algebra<T,U> algebra, Algebra<V,W> addAlgebra, Predicate<U> condition, IndexedDataSource<U> a, W sum);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/count
	}
	
	void example4() {
		
		// Mismatch.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		// Mismatch.compute(Algebra<T,U> algebra, Predicate<Tuple2<U,U>> cond, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/mismatch
	}
	
	void example5() {
		
		// Find.compute(Algebra<T,U> algebra, U value, IndexedDataSource<U> a);
		
		// Find.compute(Algebra<T,U> algebra, U value, long start, long count, IndexedDataSource<U> a);
		
		// FindIf.compute(Algebra<T,U> algebra, Predicate<U> condition, IndexedDataSource<U> a);
		
		// FindIfNot.compute(Algebra<T,U> algebra, Predicate<U> condition, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/find
	}

	void example6() {
		
		// FindEnd.compute(Algebra<T,U> algebra, IndexedDataSource<U> values, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/find_end
	}
	
	void example7() {
	
		// FindFirstOf.compute(Algebra<T,U> algebra, IndexedDataSource<U> elements, IndexedDataSource<U> a);
		
		// FindFirstOf.compute(Algebra<T,U> algebra, Predicate<Tuple2<U,U>> cond, IndexedDataSource<U> elements, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/find_first_of
	}
	
	void example8() {
		
		// AdjacentFind.compute(Algebra<T,U> algebra, IndexedDataSource<U> a);
		
		// AdjacentFind.compute(Algebra<T,U> algebra, long start, long count, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/adjacent_find
	}
	
	void example9() {
		
		// Search.compute(Algebra<T,U> algebra, IndexedDataSource<U> elements, IndexedDataSource<U> a);
		
		// Search.compute(Algebra<T,U> algebra, Predicate<Tuple2<U,U>> cond, IndexedDataSource<U> elements, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/search
	}
	
	void example10() {
	
		// SearchN.compute(Algebra<T,U> algebra, long count, U value, IndexedDataSource<U> a);
		
		// SearchN.compute(Algebra<T,U> algebra, Predicate<Tuple2<U,U>> cond, long count, U value, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/search_n
	}
	
	void example11() {
		
		// Copy.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		// CopyIf.compute(Algebra<T,U> algebra, Predicate<U> cond, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/copy
	}
	
	void example12() {
		
		// CopyBackward.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/copy_backward
	}
	
	void example13() {
	
		// Fill.compute(Algebra<T,U> algebra, Procedure1<U> proc, IndexedDataSource<U> storage);
		
		// Fill.compute(Algebra<T,U> algebra, U value, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/fill
	}
	
	void example14() {
		
		// The following commands are all loosely based upon the CPP methods. These here are
		// more powerful and flexible.
		
		// Transform1.compute(algU, proc, a);
		// Transform2.compute(algU, proc, a, b);
		// Transform2.compute(algU, algW, proc, a, b);
		// Transform3.compute(algU, proc, a, b, c);
		// Transform3.compute(algU, algW, algY, proc, a, b, c);
		// Transform4.compute(algU, proc, a, b, c, d);
		// Transform4.compute(algM, algO, algQ, algS, proc, a, b, c, d);
		
		// ParallelTransform1.compute(algU, proc, a);
		// ParallelTransform2.compute(algU, proc, a, b);
		// ParallelTransform2.compute(algU, algW, proc, a, b);
		// ParallelTransform3.compute(algU, proc, a, b, c);
		// ParallelTransform3.compute(algU, algW, algY, proc, a, b, c);
		// ParallelTransform4.compute(algU, proc, a, b, c, d);
		// ParallelTransform4.compute(algU, algW, algY, algA, proc, a, b, c, d);
		
		// FixedTransform2a.compute(algU, fixedValue, proc, a, b);
		// FixedTransform2b.compute(algU, fixedValue, proc, a, b);
		// FixedTransform2a.compute(algD, algF, fixedValue, proc, a, b);
		// FixedTransform2b.compute(algD, algF, fixedValue, proc, a, b);
	
		// InplaceTransform1.compute(alg, proc, a);
		// InplaceTransform2.compute(alg, proc, a);
		// InplaceTransform3.compute(alg, proc, a);
		// InplaceTransform4.compute(alg, proc, a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/transform
	}
	
	void example15() {
		
		// Generate.compute(Algebra<T,U> algU, Procedure<U> proc, IndexedDataSource<U> a, U... inputs);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/generate
	}
	
	void example16() {
		
		// Replace.compute(Algebra<T,U> algebra, U target, U replacement, IndexedDataSource<U> storage);

		// ReplaceIf.compute(Algebra<T,U> algebra, Predicate<U> cond, U replacement, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/replace
	}
	
	void example17() {
		
		// ReplaceCopy.compute(Algebra<T,U> algebra, U old_value, U new_value, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		// ReplaceCopyIf.compute(Algebra<T,U> algebra, Predicate<U> cond, U new_value, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/replace_copy
	}
	
	void example18() {
		
		// Swap.compute(Algebra<T,U> alg, U a, U b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/swap
	}
	
	void example19() {
		
		// SwapRanges.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/swap_ranges
	}
	
	void example20() {
		
		// Reverse.compute(Algebra<T,U> algebra, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/reverse
	}
	
	void example21() {
		
		// ReverseCopy.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/reverse_copy
	}
	
	void example22() {
		
		// RotateCopy.compute(Algebra<T,U> algebra, long delta, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/rotate_copy
	}
	
	void example23() {
		
		// Shuffle.compute(Algebra<T,U> algebra, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/random_shuffle
	}
	
	void example24() {
		
		// Sample.compute(Algebra<T,U> algebra, long n, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/sample
	}
	
	void example25() {
		
		// Unique.compute(Algebra<T,U> alg, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/unique
	}
	
	void example26() {
		
		// IsPartitioned.compute(Algebra<T,U> algebra, Predicate<U> cond, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/is_partitioned
	}
	
	void example27() {
		
		// Partition.compute(Algebra<T,U> alg, Predicate<U> cond, IndexedDataSource<U> storage);

		//  see https://en.cppreference.com/w/cpp/algorithm/partition
	}
	
	void example28() {
		
		// StablePartition.compute(Algebra<T,U> alg, Predicate<U> cond, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/stable_partition
	}
	
	void example29() {
		
		// PartitionPoint.compute(Algebra<T,U> alg, Predicate<U> cond, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/partition_point
	}
	
	void example30() {
		
		// IsSorted.compute(Algebra<T,U> alg, IndexedDataSource<U> storage);
		
		// IsSorted.compute(Algebra<T,U> alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/is_sorted
	}
	
	void example31() {
		
		// IsSortedUntil.compute(Algebra<T,U> alg, IndexedDataSource<U> storage);
		
		// IsSortedUntil.compute(Algebra<T,U> alg, Function2<Boolean,U,U> isLeftOf, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/is_sorted_until
	}
	
	void example32() {
		
		// Sort.compute(Algebra<T,U> alg, IndexedDataSource<U> storage);
		
		// Sort.compute(Algebra<T,U> alg, Function2<Boolean,U,U> compare, IndexedDataSource<U> storage);

		//  see https://en.cppreference.com/w/cpp/algorithm/sort
	}
	
	void example33() {
		
		// StableSort.compute(Algebra<T,U> alg, IndexedDataSource<U> storage);
		
		// StableSort.compute(Algebra<T,U> alg, Function2<Boolean,U,U> lessOrEqual, IndexedDataSource<U> storage);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/stable_sort
	}
	
	void example34() {
		
		// LowerBound.compute(Algebra<T,U> alg, U val, IndexedDataSource<U> a);
		
		// LowerBound.compute(Algebra<T,U> alg, U val, Predicate<Tuple2<U,U>> isLess, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/lower_bound
	}
	
	void example35() {
	
		// UpperBound.compute(Algebra<T,U> alg, U val, IndexedDataSource<U> a);
		
		// UpperBound.compute(Algebra<T,U> alg, U val, Predicate<Tuple2<U,U>> isLess, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/upper_bound
	}
	
	void example36() {
		
		// BinarySearch.compute(Algebra<T,U> algebra, U value, IndexedDataSource<U> data);
		
		// BinarySearchLeft.compute(Algebra<T,U> algebra, U value, IndexedDataSource<U> data);
		
		// BinarySearchRight.compute(Algebra<T,U> algebra, U value, IndexedDataSource<U> data);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/binary_search
	}
	
	void example37() {
		
		// EqualRange.compute(Algebra<T,U> alg, U val, IndexedDataSource<U> a);
		
		// EqualRange.compute(Algebra<T,U> alg, U val, Predicate<Tuple2<U,U>> isLess, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/equal_range
	}
	
	void example38() {
		
		// Merge.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/merge
	}
	
	void example39() {
		
		// Includes.compute(Algebra<T,U> alg, IndexedDataSource<U> list, IndexedDataSource<U> sublist);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/includes
	}
	
	void example40() {
		
		// SetDifference.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/set_difference
	}
	
	void example41() {
		
		// SetIntersection.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/set_intersection
	}
	
	void example42() {
		
		// SetSymmetricDifference.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/set_symmetric_difference
	}
	
	void example43() {
		
		// SetUnion.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/set_union
	}
	
	void example44() {
		
		// Max.compute(Algebra<T,U> algebra, U a, U b, U result);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/max
	}
	
	void example45() {
		
		// MaxElement.compute(Algebra<T,U> algebra, IndexedDataSource<U> storage, U max);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/max_element
	}
	void example46() {
		
		// Min.compute(Algebra<T,U> algebra, U a, U b, U result);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/min
	}
	
	void example47() {
		
		// MinElement.compute(Algebra<T,U> algebra, IndexedDataSource<U> storage, U max);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/min_element
	}
	void example48() {
		
		// MinMax.compute(Algebra<T,U> algebra, U a, U b, U min, U max);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/minmax
	}
	
	void example49() {
	
		// MinMaxElement.compute(Algebra<T,U> algebra, IndexedDataSource<U> storage, U min, U max);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/minmax_element
	}
	
	void example50() {
		
		// Clamp.compute(Algebra<T,U> algebra, U min, U max, U value, U result);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/clamp
	}
	
	void example51() {
		
		// Equal.compute(Algebra<T,U> algebra, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/equal
	}
	
	void example52() {
		
		// LexicographicalCompare.compute(Algebra<T,U> alg, IndexedDataSource<U> a, IndexedDataSource<U> b);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/lexicographical_compare
	}
	
	void example53() {
		
		// NextPermutation.compute(Algebra<T,U> alg, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/next_permutation
	}
	
	void example54() {
		
		// PrevPermutation.compute(Algebra<T,U> alg, IndexedDataSource<U> a);
		
		//  see https://en.cppreference.com/w/cpp/algorithm/prev_permutation
	}
}