/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CountIf {

	private CountIf() {}

	/**
	 * 
	 * @param Algebra
	 * @param addAlgebra
	 * @param a
	 * @param condition
	 * @param sum
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W> & Addition<W> & Unity<W>, W>
		void compute(T algebra, V addAlgebra, Condition<U> condition, IndexedDataSource<?,U> a, W sum)
	{
		compute(algebra, addAlgebra, condition, 0, a.size(), a, sum);
	}

	/**
	 * 
	 * @param Algebra
	 * @param addAlgebra
	 * @param a
	 * @param start
	 * @param count
	 * @param condition
	 * @param sum
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W> & Addition<W> & Unity<W>, W>
		void compute(T algebra, V addAlgebra, Condition<U> condition, long start, long count, IndexedDataSource<?,U> a, W sum)
	{
		U tmp = algebra.construct();
		W tmpSum = addAlgebra.construct();
		W one = addAlgebra.construct();
		addAlgebra.unity().call(one);
		for (long i = 0; i < count; i++) {
			a.get(start+i, tmp);
			if (condition.isTrue(tmp))
				addAlgebra.add().call(tmpSum, one, tmpSum);
		}
		addAlgebra.assign().call(tmpSum, sum);
	}

}
