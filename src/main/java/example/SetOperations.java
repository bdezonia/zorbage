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
import nom.bdezonia.zorbage.algorithm.SetDifference;
import nom.bdezonia.zorbage.algorithm.SetIntersection;
import nom.bdezonia.zorbage.algorithm.SetSymmetricDifference;
import nom.bdezonia.zorbage.algorithm.SetUnion;
import nom.bdezonia.zorbage.algorithm.Unique;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * @author Barry DeZonia
 */
class SetOperations {

	/*
	 * Zorbage allows you to do set operations on lists of data if you need to.
	 */

	// SetDifference : the result is the elements in set a that are not in set b
	
	void example1() {
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {1,2,3,4});
		IndexedDataSource<Float64Member> b =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {2,4});
		IndexedDataSource<Float64Member> result = SetDifference.compute(G.DBL, a, b);
		System.out.println(result);  // {1,3}
	}
	
	// SetIntersection : the result set is the elements common to both sets a and b
	
	void example2() {
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {1,2,3,4});
		IndexedDataSource<Float64Member> b =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {2,4});
		IndexedDataSource<Float64Member> result = SetIntersection.compute(G.DBL, a, b);
		System.out.println(result);  // {2,4}
	}
	
	// SetSymmetricDifference : (a - b) union (b - a)
	
	void example3() {
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {1,2,3,4});
		IndexedDataSource<Float64Member> b = 
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {3,4,5,6});
		IndexedDataSource<Float64Member> result = SetSymmetricDifference.compute(G.DBL, a, b);
		System.out.println(result);  // {1,2,5,6}
	}
	
	// SetUnion : the result set contains all elements from both sets a and b
	
	void example4() {
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {1,2,3,4});
		IndexedDataSource<Float64Member> b =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {5,6});
		IndexedDataSource<Float64Member> result = SetUnion.compute(G.DBL, a, b);
		System.out.println(result);  // {1,2,3,4,5,6}
	}
	
	// Unique : return a list which is a copy of another list with duplicates eliminated
	
	void example5() {
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), new double[] {1,2,2,3,3,3,4,4,4,4});
		IndexedDataSource<Float64Member> result = Unique.compute(G.DBL, a);
		System.out.println(result);  // {1,2,3,4}
	}
	
}
