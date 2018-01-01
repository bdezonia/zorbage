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

import nom.bdezonia.zorbage.basic.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform {

	// TODO: this is a proof of concept. It should provide signatures for
	// all of what Transform class can do. That may take more than one
	// class.
	
	/**
	 * 
	 * @param grpU
	 * @param a
	 * @param b
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 */
	public static <T extends Group<T,U>, U>
		void compute(T grpU, Procedure2<U, U> proc, long aStart, long bStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
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
			Runnable c = new Computer<T,U>(grpU, proc, thAStart, thBStart, thCount, a, b);
			threads[i] = new Thread(c);
			thAStart += thCount;
			thBStart += thCount;
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
	
	private static class Computer<T extends Group<T,U>, U>
		implements Runnable
	{
		private final T group1;
		private final IndexedDataSource<?,U> list1;
		private final IndexedDataSource<?,U> list2;
		private final Procedure2<U, U> proc;
		private final long aStart;
		private final long bStart;
		private final long count;
		
		Computer(T grpU, Procedure2<U, U> proc, long aStart, long bStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b) {
			group1 = grpU;
			list1 = a;
			list2 = b;
			this.proc = proc;
			this.aStart = aStart;
			this.bStart = bStart;
			this.count = count;
		}
		
		public void run() {
			Transform.compute(group1, proc, aStart, bStart, count, list1, list2);
		}
	}
}

