/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelTransform4 {

	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param dStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param dStride
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure4<U,U,U,U> proc, long aStart, long bStart, long cStart, long dStart, long count, long aStride, long bStride, long cStride, long dStride, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b, IndexedDataSource<?,U> c, IndexedDataSource<?,U> d)
	{
		compute(algU, algU, algU, algU, proc, aStart, bStart, cStart, dStart, count, aStride, bStride, cStride, dStride, a, b, c, d);
	}
	
	/**
	 * 
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param algA
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param dStart
	 * @param count
	 * @param aStride
	 * @param bStride
	 * @param cStride
	 * @param dStride
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public static <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y, Z extends Algebra<Z,A>, A>
		void compute(T algU, V algW, X algY, Z algA, Procedure4<U, W, Y, A> proc, long aStart, long bStart, long cStart, long dStart, long count, long aStride, long bStride, long cStride, long dStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c, IndexedDataSource<?,A> d)
	{
		final int numProcs = Runtime.getRuntime().availableProcessors();
		final Thread[] threads = new Thread[numProcs];
		long thAStart = aStart;
		long thBStart = bStart;
		long thCStart = cStart;
		long thDStart = dStart;
		long thCount;
		for (int i = 0; i < numProcs; i++) {
			if (i == numProcs-1) {
				thCount = (count / numProcs) + (count % numProcs);
			}
			else {
				thCount = count / numProcs;
			}
			Runnable r = new Computer<T,U,V,W,X,Y,Z,A>(algU, algW, algY, algA, proc, thAStart, thBStart, thCStart, thDStart, thCount, aStride, bStride, cStride, dStride, a, b, c, d);
			threads[i] = new Thread(r);
			thAStart += (aStride * thCount);
			thBStart += (bStride * thCount);
			thCStart += (cStride * thCount);
			thDStart += (dStride * thCount);
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
	
	private static class Computer<T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y, Z extends Algebra<Z,A>, A>
		implements Runnable
	{
		private final T AlgebraU;
		private final V AlgebraW;
		private final X AlgebraY;
		private final Z AlgebraA;
		private final IndexedDataSource<?,U> list1;
		private final IndexedDataSource<?,W> list2;
		private final IndexedDataSource<?,Y> list3;
		private final IndexedDataSource<?,A> list4;
		private final Procedure4<U, W, Y, A> proc;
		private final long aStart;
		private final long bStart;
		private final long cStart;
		private final long dStart;
		private final long count;
		private final long aStride;
		private final long bStride;
		private final long cStride;
		private final long dStride;
		
		Computer(T algU, V algW, X algY, Z algA, Procedure4<U, W, Y, A> proc, long aStart, long bStart, long cStart, long dStart, long count, long aStride, long bStride, long cStride, long dStride, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c, IndexedDataSource<?,A> d) {
			AlgebraU = algU;
			AlgebraW = algW;
			AlgebraY = algY;
			AlgebraA = algA;
			list1 = a;
			list2 = b;
			list3 = c;
			list4 = d;
			this.proc = proc;
			this.aStart = aStart;
			this.bStart = bStart;
			this.cStart = cStart;
			this.dStart = dStart;
			this.count = count;
			this.aStride = aStride;
			this.bStride = bStride;
			this.cStride = cStride;
			this.dStride = dStride;
		}
		
		public void run() {
			Transform4.compute(AlgebraU, AlgebraW, AlgebraY, AlgebraA, proc, aStart, bStart, cStart, dStart, count, aStride, bStride, cStride, dStride, list1, list2, list3, list4);
		}
	}
}

