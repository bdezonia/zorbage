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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform3 {

	/**
	 * 
	 * @param grpU
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T grpU, Procedure3<U,U,U> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b, IndexedDataSource<?,U> c)
	{
		compute(grpU, grpU, grpU, proc, aStart, bStart, cStart, count, aStride, bStride, cStride, a, b, c);
	}
	
	/**
	 * 
	 * @param grpU
	 * @param grpW
	 * @param grpY
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y>
		void compute(T grpU, V grpW, X grpY, Procedure3<U, W, Y> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c)
	{
		final int numProcs = Runtime.getRuntime().availableProcessors();
		final Thread[] threads = new Thread[numProcs];
		long thAStart = aStart;
		long thBStart = bStart;
		long thCStart = cStart;
		long thCount;
		for (int i = 0; i < numProcs; i++) {
			if (i == numProcs-1) {
				thCount = (count / numProcs) + (count % numProcs);
			}
			else {
				thCount = count / numProcs;
			}
			Runnable r = new Computer<T,U,V,W,X,Y>(grpU, grpW, grpY, proc, thAStart, thBStart, thCStart, thCount, aStride, bStride, cStride, a, b, c);
			threads[i] = new Thread(r);
			thAStart += (aStride * thCount);
			thBStart += (bStride * thCount);
			thCStart += (cStride * thCount);
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
		private final T groupU;
		private final V groupW;
		private final X groupY;
		private final IndexedDataSource<?,U> list1;
		private final IndexedDataSource<?,W> list2;
		private final IndexedDataSource<?,Y> list3;
		private final Procedure3<U, W, Y> proc;
		private final long aStart;
		private final long bStart;
		private final long cStart;
		private final long count;
		private final long aStride;
		private final long bStride;
		private final long cStride;
		
		Computer(T grpU, V grpW, X grpY, Procedure3<U, W, Y> proc, long aStart, long bStart, long cStart, long count, long aStride, long bStride, long cStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c) {
			groupU = grpU;
			groupW = grpW;
			groupY = grpY;
			list1 = a;
			list2 = b;
			list3 = c;
			this.proc = proc;
			this.aStart = aStart;
			this.bStart = bStart;
			this.cStart = cStart;
			this.count = count;
			this.aStride = aStride;
			this.bStride = bStride;
			this.cStride = cStride;
		}
		
		public void run() {
			Transform3.compute(groupU, groupW, groupY, proc, aStart, bStart, cStart, count, aStride, bStride, cStride, list1, list2, list3);
		}
	}
}

