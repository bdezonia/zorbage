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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.NdData;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CreateMask {

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param condition
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		IndexedDataSource<UnsignedInt1Member> compute(T alg, Predicate<U> condition, IndexedDataSource<U> a)
	{
		U value = alg.construct();
		UnsignedInt1Member zero = G.UINT1.construct();
		UnsignedInt1Member one = G.UINT1.construct();
		G.UINT1.unity().call(one);
		IndexedDataSource<UnsignedInt1Member> result = Storage.allocate(a.size(), zero);
		for (long i = 0; i < a.size(); i++) {
			a.get(i, value);
			if (condition.isTrue(value))
				result.set(i, one);
			else
				result.set(i, zero);
		}
		return result;
	}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param condition
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		MultiDimDataSource<UnsignedInt1Member> compute(T alg, Predicate<U> condition, MultiDimDataSource<U> a)
	{
		long[] dims = new long[a.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = a.dimension(i);
		}
		IndexedDataSource<UnsignedInt1Member> data = compute(alg, condition, a.rawData());
		return new NdData<>(dims, data);
	}
}