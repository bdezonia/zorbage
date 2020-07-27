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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveConverter;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

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
	public static <T extends Algebra<T,U>,
					U extends PrimitiveConversion,
					V extends Algebra<V,W>,
					W extends PrimitiveConversion>
		void compute(T fromAlgebra, V toAlgebra, IndexedDataSource<U> fromList, IndexedDataSource<W> toList)
	{
		long fromSize = fromList.size();
		long toSize = toList.size();
		if (fromSize > toSize)
			throw new IllegalArgumentException("mismatched list sizes");
		int maxPieces = Runtime.getRuntime().availableProcessors();
		if (maxPieces > fromSize)
			maxPieces = 1;
		long start = 0;
		long count = fromSize / maxPieces;
		Thread[] threads = new Thread[maxPieces];
		for (int i = 0; i < maxPieces; i++) {
			if (i == maxPieces - 1) {
				count = fromSize - start;
			}
			Computer<T,U,V,W> computer =
					new Computer<>(fromAlgebra, toAlgebra, start, count, fromList, toList);
			threads[i] = new Thread(computer);
			start += count;
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		try {
			for (int i = 0; i < threads.length; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			throw new IllegalArgumentException("ouch");
		}
	}

	private static class Computer<T extends Algebra<T,U>,
									U extends PrimitiveConversion,
									V extends Algebra<V,W>,
									W extends PrimitiveConversion>
		implements Runnable
	{
		private final T algU;
		private final V algW;
		private final IndexedDataSource<U> src;
		private final IndexedDataSource<W> dst;
		
		public Computer(T algU, V algW, long start, long count, IndexedDataSource<U> src, IndexedDataSource<W> dst) {
			this.algU = algU;
			this.algW = algW;
			this.src = new TrimmedDataSource<>(src, start, count);
			this.dst = new TrimmedDataSource<>(dst, start, count);
		}
		
		@Override
		public void run() {
			U from = algU.construct();
			W to = algW.construct();
			int numD = Math.max(from.numDimensions(), to.numDimensions());
			IntegerIndex tmp1 = new IntegerIndex(numD);
			IntegerIndex tmp2 = new IntegerIndex(numD);
			IntegerIndex tmp3 = new IntegerIndex(numD);
			long fromSize = src.size();
			for (long i = 0; i < fromSize; i++) {
				src.get(i, from);
				PrimitiveConverter.convert(tmp1, tmp2, tmp3, from, to);
				dst.set(i, to);
			}
		}
		
	}
}
