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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.misc.ThreadingUtils;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Apodize {

	// do not instantiate
	
	private Apodize() { }
	
	/**
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param alg
	 * @param windowFunc
	 * @param a
	 * @param b
	 */
	public static <CA extends Algebra<CA,C> & Multiplication<C>,
					C>
		void compute(CA alg, Procedure2<Long,C> windowFunc, IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		if (a.size() != b.size())
			throw new IllegalArgumentException("mismatched list sizes");
		
		Tuple2<Integer,Long> arrangement =
				ThreadingUtils.arrange(a.size(),
										a.accessWithOneThread() ||
										b.accessWithOneThread());
		int pieces = arrangement.a();
		long elemsPerPiece = arrangement.b();
	
		final Thread[] threads = new Thread[(int)pieces];
		long start = 0;
		for (int i = 0; i < pieces; i++) {
			long endPlusOne;
			// last piece?
			if (i == pieces-1) {
				endPlusOne = a.size();
			}
			else {
				endPlusOne = start + elemsPerPiece;
			}
			Computer<CA,C> computer = new Computer<CA,C>(alg, start, endPlusOne, windowFunc, a, b);
			threads[i] = new Thread(computer);
			start = endPlusOne;
		}
	
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException e) {
				throw new IllegalArgumentException("Thread execution error in ParallelResampler");
			}
		}
	}

	private static class Computer<CA extends Algebra<CA,C> & Multiplication<C>, C>
		implements Runnable
	{
		private final CA alg;
		private final IndexedDataSource<C> a;
		private final IndexedDataSource<C> b;
		private final long start;
		private final long endPlusOne;
		private final Procedure2<Long,C> windowFunc;
	
		Computer(CA alg, long start, long endPlusOne, Procedure2<Long,C> windowFunc, IndexedDataSource<C> a, IndexedDataSource<C> b) {
			this.alg = alg;
			this.a = a;
			this.b = b;
			this.start = start;
			this.endPlusOne = endPlusOne;
			this.windowFunc = windowFunc;
		}
		
		@Override
		public void run() {
			C tmp = alg.construct();
			C ak = alg.construct();
			for (long i = start; i < endPlusOne; i++) {
				windowFunc.call(i, ak);
				a.get(i, tmp);
				alg.multiply().call(tmp, ak, tmp);
				b.set(i, tmp);
			}
		}
	}
}