/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.procedure.Procedure1;
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
public class Transform1 {

	// do not instantiate
	
	private Transform1() { }
	
	/**
	 * Transform one list in place by a function/procedure. Use a parallel
	 * algorithm for extra speed.
	 * 
	 * @param algA
	 * @param proc
	 * @param a
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA algA, Procedure1<A> proc, IndexedDataSource<A> a)
	{
		long sz = a.size();
		
		if (sz == 0)
			return;

		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(a.size(),
										a.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
		
		if (pieces == 1) {
			
			Runnable r = new Computer<AA,A>(algA, proc, a);
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
				Runnable r = new Computer<AA,A>(algA, proc, aTrimmed);
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
	
	private static class Computer<AA extends Algebra<AA,A>, A>
		implements Runnable
	{
		private final AA algebraA;
		private final IndexedDataSource<A> listA;
		private final Procedure1<A> proc;
		
		Computer(AA algA, Procedure1<A> proc, IndexedDataSource<A> a) {
			algebraA = algA;
			listA = a;
			this.proc = proc;
		}
		
		public void run() {
			transform(algebraA, proc, listA);
		}
	}
	
	private static <AA extends Algebra<AA,A>, A>
		void transform(AA algA, Procedure1<A> proc, IndexedDataSource<A> a)
	{
		A valueA = algA.construct();
		final long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			proc.call(valueA);
			a.set(i, valueA);
		}
	}
}
