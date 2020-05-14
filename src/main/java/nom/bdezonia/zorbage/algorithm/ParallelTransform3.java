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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform3 {

	private ParallelTransform3() { }
	
	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure3<U,U,U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c)
	{
		compute(algU, algU, algU, proc, a, b, c);	
	}
	
	/**
	 * 
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc, IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> c)
	{
		long aSize = a.size();
		int numProcs = Runtime.getRuntime().availableProcessors();
		if (aSize < numProcs) {
			numProcs = (int) aSize;
		}
		final Thread[] threads = new Thread[numProcs];
		long thOffset = 0;
		long slice = aSize / numProcs;
		for (int i = 0; i < numProcs; i++) {
			long thSize;
			if (i != numProcs-1) {
				thSize = slice;
			}
			else {
				thSize = aSize;
			}
			IndexedDataSource<U> aTrimmed = new TrimmedDataSource<>(a, thOffset, thSize);
			IndexedDataSource<W> bTrimmed = new TrimmedDataSource<>(b, thOffset, thSize);
			IndexedDataSource<Y> cTrimmed = new TrimmedDataSource<>(c, thOffset, thSize);
			Runnable r = new Computer<T,U,V,W,X,Y>(algU, algW, algY, proc, aTrimmed, bTrimmed, cTrimmed);
			threads[i] = new Thread(r);
			thOffset += slice;
			aSize -= slice;
		}
		for (int i = 0; i < numProcs; i++) {
			threads[i].start();
		}
		for (int i = 0; i < numProcs; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException e) {
				throw new IllegalArgumentException("Thread execution error in ParallelTransform");
			}
		}
	}
	
	private static class Computer<T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y>
		implements Runnable
	{
		private final T algebraU;
		private final V algebraW;
		private final X algebraY;
		private final IndexedDataSource<U> list1;
		private final IndexedDataSource<W> list2;
		private final IndexedDataSource<Y> list3;
		private final Procedure3<U,W,Y> proc;
		
		Computer(T algU, V algW, X algY, Procedure3<U,W,Y> proc, IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> c) {
			algebraU = algU;
			algebraW = algW;
			algebraY = algY;
			list1 = a;
			list2 = b;
			list3 = c;
			this.proc = proc;
		}
		
		public void run() {
			Transform3.compute(algebraU, algebraW, algebraY, proc, list1, list2, list3);
		}
	}

}

