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

import java.util.List;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConverter;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

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
	 * @param fromGroup
	 * @param toGroup
	 * @param from
	 * @param to
	 */
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V extends Group<V,W>, W extends PrimitiveConversion>
		void compute(T fromGroup, V toGroup, IndexedDataSource<?,U> from, IndexedDataSource<?,W> to)
	{
		if (from.size() != to.size())
			throw new IllegalArgumentException("mismatched list sizes");
		compute(fromGroup, toGroup, 0, 0, from.size(), from, to);
	}

	/**
	 * 
	 * @param fromGroup
	 * @param toGroup
	 * @param fromStart
	 * @param toStart
	 * @param count
	 * @param fromList
	 * @param toList
	 */
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V  extends Group<V,W>, W extends PrimitiveConversion>
		void compute(T fromGroup, V toGroup, long fromStart, long toStart, long count, IndexedDataSource<?,U> fromList, IndexedDataSource<?,W> toList)
	{
		U from = fromGroup.construct();
		W to = toGroup.construct();
		int numD = Math.max(from.numDimensions(), to.numDimensions());
		IntegerIndex tmp1 = new IntegerIndex(numD);
		IntegerIndex tmp2 = new IntegerIndex(numD);
		IntegerIndex tmp3 = new IntegerIndex(numD);
		for (long i = 0; i < count; i++) {
			fromList.get(fromStart+i, from);
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, from, to);
			toList.set(toStart+i, to);
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
		if (from.size() == 0 || to.size() == 0)
			throw new IllegalArgumentException("cannot work with empty lists");
		if (from.size() != to.size())
			throw new IllegalArgumentException("mismatched list sizes");
		int numD = Math.max(from.get(0).numDimensions(), to.get(0).numDimensions());
		IntegerIndex tmp1 = new IntegerIndex(numD);
		IntegerIndex tmp2 = new IntegerIndex(numD);
		IntegerIndex tmp3 = new IntegerIndex(numD);
		for (int i = 0; i < from.size(); i++) {
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, from.get(i), to.get(i));
		}
	}

}
