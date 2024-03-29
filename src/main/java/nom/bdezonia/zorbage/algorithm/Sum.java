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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

//TODO: for a floating sum a Neumaier sum might be best.

//function NeumaierSum(input)
//var sum = input[1]
//var c = 0.0                 // A running compensation for lost low-order bits.
//for i = 2 to input.length do
// var t = sum + input[i]
// if |sum| >= |input[i]| do
//     c += (sum - t) + input[i] // If sum is bigger, low-order digits of input[i] are lost.
// else
//     c += (input[i] - t) + sum // Else low-order digits of sum are lost
// sum = t
//return sum + c              // Correction only applied once in the very end
	
/**
 * 
 * @author Barry DeZonia
 *
 */
public class Sum {

	// do not instantiate
	
	private Sum() {}
	
	/**
	 * Calculate the sum of a list of values
	 * 
	 * @param alg
	 * @param storage
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U>, U>
		void compute(T alg, IndexedDataSource<U> storage, U result)
	{
		// OLD WAY : too slow
		//
		//sum(alg, 0, storage.size(), storage, result);
		
		U sum = alg.construct();
		U tmp = alg.construct();
		final long sz = storage.size();
		for (long i = 0; i < sz; i++) {
			storage.get(i, tmp);
			alg.add().call(sum, tmp, sum);
		}
		alg.assign().call(sum, result);
	}

	/*  OLD WAY TOO SLOW

	// Note: for now will just recursively sum to eliminate some roundoff errors. This is not
	// great for summing a virtual storage structure. Maybe need a StraightlineSum algo for
	// summing virtual structures. Maybe all sum oriented algos could switch on a boolean or
	// on the passed storage type's backing data strategy.
	// Later note: by using print statements where array is accessed it seems this code will
	// visit a list in ascending order in all cases. So it is fine for a virtual backed data
	// structure.
	
	private static <T extends Algebra<T,U> & Addition<U>, U>
		void sum(T alg, long start, long count, IndexedDataSource<U> storage, U result)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		if (count == 0)
			alg.zero().call(result);
		else if (count == 1) {
			storage.get(start, result);
		}
		else if (count == 2) {
			storage.get(start, tmp1);
			storage.get(start+1, tmp2);
			alg.add().call(tmp1, tmp2, result);
		}
		else {
			long cnt1 = count/2;
			long cnt2 = count - cnt1;
			long st1 = start;
			long st2 = start + cnt1;
			
			sum(alg, st1, cnt1, storage, tmp1);
			sum(alg, st2, cnt2, storage, tmp2);
			
			alg.add().call(tmp1, tmp2, result);
		}
	}
	 */
}
