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
public class TransformWithConstant {

	// do not instantiate
	
	private TransformWithConstant() {}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param algU
	 * @param proc
	 * @param fixedValue
	 * @param src
	 * @param dst
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure3<U,U,U> proc, U fixedValue, IndexedDataSource<U> src, IndexedDataSource<U> dst)
	{
		compute(algU, algU, proc, fixedValue, src, dst);
	}

	/**
	 * 
	 * @param <A>
	 * @param <BA>
	 * @param <B>
	 * @param <CA>
	 * @param <C>
	 * @param algB
	 * @param algC
	 * @param proc
	 * @param fixedValue
	 * @param src
	 * @param dst
	 */
	public static <A, BA extends Algebra<BA,B>, B, CA extends Algebra<CA,C>, C>
		void compute(BA algB, CA algC, Procedure3<A,B,C> proc, A fixedValue, IndexedDataSource<B> src, IndexedDataSource<C> dst)
	{
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(src.size(),
										src.accessWithOneThread() ||
										dst.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		if (pieces == 1) {
			
			Runnable r = new LeftComputer<>(algB, algC, fixedValue, proc, src, dst);
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
					count = src.size() - start;
				}
				IndexedDataSource<B> srcTrimmed = new TrimmedDataSource<>(src, start, count);
				IndexedDataSource<C> dstTrimmed = new TrimmedDataSource<>(dst, start, count);
				Runnable r = new LeftComputer<>(algB, algC, fixedValue, proc, srcTrimmed, dstTrimmed);
				threads[i] = new Thread(r);
				start += count;
			}
	
			for (int i = 0; i < pieces; i++) {
				threads[i].start();
			}
			
			for (int i = 0; i < pieces; i++) {
				try {
					threads[i].join();
				} catch(InterruptedException ex) {
					throw new IllegalArgumentException("Thread execution error");
				}
			}
		}
	}
	
	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param algU
	 * @param proc
	 * @param src
	 * @param fixedValue
	 * @param dst
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure3<U,U,U> proc, IndexedDataSource<U> src, U fixedValue, IndexedDataSource<U> dst)
	{
		compute(algU, algU, proc, src, fixedValue, dst);
	}

	/**
	 * 
	 * @param <AA>
	 * @param <A>
	 * @param <B>
	 * @param <CA>
	 * @param <C>
	 * @param algA
	 * @param algC
	 * @param proc
	 * @param src
	 * @param fixedValue
	 * @param dst
	 */
	public static <AA extends Algebra<AA,A>, A, B, CA extends Algebra<CA,C>, C>
		void compute(AA algA, CA algC, Procedure3<A,B,C> proc, IndexedDataSource<A> src, B fixedValue, IndexedDataSource<C> dst)
	{
		long sz = src.size();
		
		if (sz == 0)
			return;

		if (dst.size() != sz)
			throw new IllegalArgumentException("mismatched list sizes");
		
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(src.size(),
										src.accessWithOneThread() ||
										dst.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		if (pieces == 1) {
			
			Runnable r = new RightComputer<>(algA, algC, fixedValue, proc, src, dst);
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
					count = src.size() - start;
				}
				IndexedDataSource<A> srcTrimmed = new TrimmedDataSource<>(src, start, count);
				IndexedDataSource<C> dstTrimmed = new TrimmedDataSource<>(dst, start, count);
				Runnable r = new RightComputer<>(algA, algC, fixedValue, proc, srcTrimmed, dstTrimmed);
				threads[i] = new Thread(r);
				start += count;
			}
	
			for (int i = 0; i < pieces; i++) {
				threads[i].start();
			}
			
			for (int i = 0; i < pieces; i++) {
				try {
					threads[i].join();
				} catch(InterruptedException ex) {
					throw new IllegalArgumentException("Thread execution error");
				}
			}
		}
	}
	
	private static class LeftComputer<A,
								BA extends Algebra<BA,B>,
								B,
								CA extends Algebra<CA,C>,
								C>
		implements Runnable
	{
		private final A fixedValue;
		private final BA algB;
		private final CA algC;
		private final IndexedDataSource<B> src;
		private final IndexedDataSource<C> dst;
		private final Procedure3<A,B,C> proc;
		
		public LeftComputer(BA algB, CA algC, A fixedValue, Procedure3<A,B,C> proc,
			IndexedDataSource<B> src, IndexedDataSource<C> dst)
		{
			this.fixedValue = fixedValue;
			this.algB = algB;
			this.algC = algC;
			this.proc = proc;
			this.src = src;
			this.dst = dst;
		}
		
		public void run() {
			long sSize = src.size();
			long dSize = dst.size();
			if (sSize != dSize)
				throw new IllegalArgumentException("mismatched list sizes");
			B tmpB = algB.construct();
			C tmpC = algC.construct();
			for (long i = 0; i < sSize; i++) {
				src.get(i, tmpB);
				proc.call(fixedValue, tmpB, tmpC);
				dst.set(i, tmpC);
			}
		}
	}
	
	private static class RightComputer<AA extends Algebra<AA,A>,
								A,
								B,
								CA extends Algebra<CA,C>,
								C>
		implements Runnable
	{
		private final B fixedValue;
		private final AA algA;
		private final CA algC;
		private final IndexedDataSource<A> src;
		private final IndexedDataSource<C> dst;
		private final Procedure3<A,B,C> proc;
		
		public RightComputer(AA algA, CA algC, B fixedValue, Procedure3<A,B,C> proc,
			IndexedDataSource<A> src, IndexedDataSource<C> dst)
		{
			this.fixedValue = fixedValue;
			this.algA = algA;
			this.algC = algC;
			this.proc = proc;
			this.src = src;
			this.dst = dst;
		}
	
		public void run() {
			long sSize = src.size();
			long dSize = dst.size();
			if (sSize != dSize)
				throw new IllegalArgumentException("mismatched list sizes");
			A tmpA = algA.construct();
			C tmpC = algC.construct();
			for (long i = 0; i < sSize; i++) {
				src.get(i, tmpA);
				proc.call(tmpA, fixedValue, tmpC);
				dst.set(i, tmpC);
			}
		}
	}
}
