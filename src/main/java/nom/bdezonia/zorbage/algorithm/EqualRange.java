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

import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EqualRange {

	/**
	 * 
	 * @param alg
	 * @param val
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		Tuple2<Long,Long> compute(T alg, U val, IndexedDataSource<U> a)
	{
		long lower = LowerBound.compute(alg, val, a);
		long upper = UpperBound.compute(alg, val, a);
		return new Tuple2<Long,Long>(lower, upper);
	}

	/**
	 * 
	 * @param alg
	 * @param val
	 * @param isLess
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>, U>
		Tuple2<Long,Long> compute(T alg, U val, Predicate<Tuple2<U,U>> isLess, IndexedDataSource<U> a)
	{
		long lower = LowerBound.compute(alg, val, isLess, a);
		long upper = UpperBound.compute(alg, val, isLess, a);
		return new Tuple2<Long,Long>(lower, upper);
	}
}