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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform2 {

	/**
	 * 
	 * @param grpU
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T grpU, Procedure2<U,U> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		compute(grpU, grpU, proc, aStart, bStart, count, aStride, bStride, a, b);	
	}
	
	/**
	 * 
	 * @param grpU
	 * @param grpW
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W>
		void compute(T grpU, V grpW, Procedure2<U, W> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b)
	{
		final int numProcs = Runtime.getRuntime().availableProcessors();
		final Thread[] threads = new Thread[numProcs];
		long thAStart = aStart;
		long thBStart = bStart;
		long thCount;
		for (int i = 0; i < numProcs; i++) {
			if (i == numProcs-1) {
				thCount = (count / numProcs) + (count % numProcs);
			}
			else {
				thCount = count / numProcs;
			}
			Runnable r = new Computer<T,U,V,W>(grpU, grpW, proc, thAStart, thBStart, thCount, aStride, bStride, a, b);
			threads[i] = new Thread(r);
			thAStart += (aStride * thCount);
			thBStart += (bStride * thCount);
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
	
	private static class Computer<T extends Algebra<T,U>, U, V extends Algebra<V,W>, W>
		implements Runnable
	{
		private final T groupU;
		private final V groupW;
		private final IndexedDataSource<?,U> list1;
		private final IndexedDataSource<?,W> list2;
		private final Procedure2<U, W> proc;
		private final long aStart;
		private final long bStart;
		private final long count;
		private final long aStride;
		private final long bStride;
		
		Computer(T grpU, V grpW, Procedure2<U, W> proc, long aStart, long bStart, long count, long aStride, long bStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b) {
			groupU = grpU;
			groupW = grpW;
			list1 = a;
			list2 = b;
			this.proc = proc;
			this.aStart = aStart;
			this.bStart = bStart;
			this.count = count;
			this.aStride = aStride;
			this.bStride = bStride;
		}
		
		public void run() {
			Transform2.compute(groupU, groupW, proc, aStart, bStart, count, aStride, bStride, list1, list2);
		}
	}
}

