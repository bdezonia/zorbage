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

import java.util.List;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConverter;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DataConvert {

	// do not instantiate
	
	private DataConvert() {}

	/**
	 * 
	 * @param fromAlgebra
	 * @param toAlgebra
	 * @param fromList
	 * @param toList
	 */
	public static <T extends Algebra<T,U>, U extends PrimitiveConversion, V  extends Algebra<V,W>, W extends PrimitiveConversion>
		void compute(T fromAlgebra, V toAlgebra, IndexedDataSource<U> fromList, IndexedDataSource<W> toList)
	{
		U from = fromAlgebra.construct();
		W to = toAlgebra.construct();
		int numD = Math.max(from.numDimensions(), to.numDimensions());
		IntegerIndex tmp1 = new IntegerIndex(numD);
		IntegerIndex tmp2 = new IntegerIndex(numD);
		IntegerIndex tmp3 = new IntegerIndex(numD);
		long fromSize = fromList.size();
		for (long i = 0; i < fromSize; i++) {
			fromList.get(i, from);
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, from, to);
			toList.set(i, to);
		}
	}

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public static <U extends PrimitiveConversion, W extends PrimitiveConversion>
		void compute(List<U> from, List<W> to)
	{
		long fromSize = from.size();
		long toSize = to.size();
		if (fromSize == 0 || toSize == 0)
			throw new IllegalArgumentException("cannot work with empty lists");
		if (fromSize > toSize)
			throw new IllegalArgumentException("mismatched list sizes");
		int numD = Math.max(from.get(0).numDimensions(), to.get(0).numDimensions());
		IntegerIndex tmp1 = new IntegerIndex(numD);
		IntegerIndex tmp2 = new IntegerIndex(numD);
		IntegerIndex tmp3 = new IntegerIndex(numD);
		for (int i = 0; i < fromSize; i++) {
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, from.get(i), to.get(i));
		}
	}

}
