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

import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform1 {

	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure1<U> proc, IndexedDataSource<U> a)
	{
		compute(algU, proc, a, a);	
	}
	
	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure1<U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b)
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
			long thLast;
			if (i != numProcs-1) {
				thLast = thOffset + slice - 1;
			}
			else {
				thLast = aSize - 1;
			}
			IndexedDataSource<U> aTrimmed = new TrimmedDataSource<>(a, thOffset, thLast);
			IndexedDataSource<U> bTrimmed = new TrimmedDataSource<>(b, thOffset, thLast);
			Runnable r = new Computer<T,U>(algU, proc, aTrimmed, bTrimmed);
			threads[i] = new Thread(r);
			thOffset += slice;
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
	
	private static class Computer<T extends Algebra<T,U>, U>
		implements Runnable
	{
		private final T algebraU;
		private final IndexedDataSource<U> list1;
		private final IndexedDataSource<U> list2;
		private final Procedure1<U> proc;
		
		Computer(T algU, Procedure1<U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b) {
			algebraU = algU;
			list1 = a;
			list2 = b;
			this.proc = proc;
		}
		
		public void run() {
			Transform1.compute(algebraU, proc, list1, list2);
		}
	}
}
