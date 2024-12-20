/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;
import nom.bdezonia.zorbage.misc.ThreadingUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform3 {

	// do not instantiate
	
	private Transform3() { }
	
	/**
	 * Transform two lists into a third list using a function/procedure call at each point
	 * in the two lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param algA
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA algA, Procedure3<A,A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b, IndexedDataSource<A> c)
	{
		compute(algA, algA, algA, proc, a, b, c);	
	}
	
	/**
	 * Transform two lists into a third list using a function/procedure call at each point
	 * in the two lists. Use a parallel algorithm for extra speed.
	 * 
	 * @param algA
	 * @param algB
	 * @param algC
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C>
		void compute(AA algA, BB algB, CC algC, Procedure3<A,B,C> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c)
	{
		long sz = a.size();
		
		if (sz == 0)
			return;

		if (b.size() != sz || c.size() != sz)
			throw new IllegalArgumentException("mismatched list sizes");
		
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(a.size(),
										a.accessWithOneThread() ||
										b.accessWithOneThread() ||
										c.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		if (pieces == 1) {
			
			Runnable r = new Computer<AA,A,BB,B,CC,C>(algA, algB, algC, proc, a, b, c);
			r.run();
		}
		else {
			
			final Thread[] threads = new Thread[pieces];
			long start = 0;
			for (int i = 0; i < pieces; i++) {
				long count;
				if (i != pieces-1) {
					count = elemsPerPiece;
				}
				else {
					count = a.size() - start;
				}
				IndexedDataSource<A> aTrimmed = new TrimmedDataSource<>(a, start, count);
				IndexedDataSource<B> bTrimmed = new TrimmedDataSource<>(b, start, count);
				IndexedDataSource<C> cTrimmed = new TrimmedDataSource<>(c, start, count);
				Runnable r = new Computer<AA,A,BB,B,CC,C>(algA, algB, algC, proc, aTrimmed, bTrimmed, cTrimmed);
				threads[i] = new Thread(r);
				start += count;
			}
	
			for (int i = 0; i < pieces; i++) {
				threads[i].start();
			}
			
			for (int i = 0; i < pieces; i++) {
				try {
					threads[i].join();
				} catch(InterruptedException e) {
					throw new IllegalArgumentException("Thread execution error in ParallelTransform");
				}
			}
		}
	}
	
	private static class Computer<AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C>
		implements Runnable
	{
		private final AA algebraA;
		private final BB algebraB;
		private final CC algebraC;
		private final IndexedDataSource<A> listA;
		private final IndexedDataSource<B> listB;
		private final IndexedDataSource<C> listC;
		private final Procedure3<A,B,C> proc;
		
		Computer(AA algA, BB algB, CC algC, Procedure3<A,B,C> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c) {
			algebraA = algA;
			algebraB = algB;
			algebraC = algC;
			listA = a;
			listB = b;
			listC = c;
			this.proc = proc;
		}
		
		public void run() {
			transform(algebraA, algebraB, algebraC, proc, listA, listB, listC);
		}
	}

	private static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B, CC extends Algebra<CC,C>, C>
		void transform(AA algA, BB algB, CC algC, Procedure3<A,B,C> proc, IndexedDataSource<A> a, IndexedDataSource<B> b, IndexedDataSource<C> c)
	{
		A valueA = algA.construct();
		B valueB = algB.construct();
		C valueC = algC.construct();
	
		final long aSize = a.size();
		
		if (b.size() != aSize ||
				c.size() != aSize)
			throw new IllegalArgumentException("mismatched list sizes");
	
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			b.get(i, valueB);
			proc.call(valueA, valueB, valueC);
			c.set(i, valueC);
		}
	}
}

