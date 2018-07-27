/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

// TODO; for a floating sum a Neumaier sum might be best.

//function NeumaierSum(input)
//var sum = input[1]
//var c = 0.0                 // A running compensation for lost low-order bits.
//for i = 2 to input.length do
//    var t = sum + input[i]
//    if |sum| >= |input[i]| do
//        c += (sum - t) + input[i] // If sum is bigger, low-order digits of input[i] are lost.
//    else
//        c += (input[i] - t) + sum // Else low-order digits of sum are lost
//    sum = t
//return sum + c              // Correction only applied once in the very end
	
// Note: for now will just recursively sum to eliminate some roundoff errors.
		
/**
 * 
 * @author Barry DeZonia
 *
 */
public class SumCount {

	private SumCount() {}
	
	/**
	 * 
	 * @param grp
	 * @param storage
	 * @param sum
	 * @param count
	 */
	public static <T extends Group<T,U> & Addition<U> & Unity<U>, U>
		void compute(T grp, IndexedDataSource<?,U> storage, U sum, U count)
	{
		U tmp = grp.construct();
		U one = grp.construct();
		grp.unity().call(one);
		grp.zero().call(sum);
		grp.zero().call(count);
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			grp.add().call(sum, tmp, sum);
			grp.add().call(count, one, count);
		}
	}
}
