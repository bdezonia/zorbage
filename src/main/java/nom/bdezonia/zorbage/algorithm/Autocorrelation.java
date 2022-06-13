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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// TODO:
// 1) find something better than an N^2 algorithm.
// 2) can the inner loop be cut in half and do less work?
// 3) should I do the sqrt calc more often? slower but maybe more accurate.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Autocorrelation {

	// do not instantiate
	
	private Autocorrelation() { }

	/**
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param alg
	 * @param a
	 * @return
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
								Conjugate<C> & ScaleByHighPrec<C>,
					C extends Allocatable<C>>
		IndexedDataSource<C> compute(CA alg, IndexedDataSource<C> a)
	{
		IndexedDataSource<C> b = Storage.allocate(alg.construct(), a.size());

		long pieceSize = a.size() / Runtime.getRuntime().availableProcessors();
		
		if (pieceSize == 0) pieceSize = a.size();
		
		long pieces = a.size() / pieceSize;
		
		if (a.size() % pieceSize != 0)
			pieces += 1;
		
		if (pieces > Integer.MAX_VALUE)
			pieces = Integer.MAX_VALUE;

		final Thread[] threads = new Thread[(int)pieces];
		long start = 0;
		for (int i = 0; i < pieces; i++) {
			long endPlusOne;
			// last piece?
			if (i == pieces-1) {
				endPlusOne = a.size();
			}
			else {
				endPlusOne = start + pieceSize;
			}
			Computer<CA,C> computer = new Computer<CA,C>(alg, start, endPlusOne, a, b);
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
				throw new IllegalArgumentException("Thread execution error in ParallelCorrConvND");
			}
		}
		
		return b;
	}
	
	private static class Computer<CA extends Algebra<CA,C> & Addition<C> &
									Multiplication<C> & Conjugate<C> &
									ScaleByHighPrec<C>,
									C>
		implements Runnable
	{
		final CA alg;
		final long start;
		final long endPlusOne;
		final IndexedDataSource<C> src;
		final IndexedDataSource<C> dst;
		
		Computer(CA alg, long start, long endPlusOne,
				IndexedDataSource<C> src, IndexedDataSource<C> dst)
		{
			this.alg = alg;
			this.start = start;
			this.endPlusOne = endPlusOne;
			this.src = src;
			this.dst = dst;
		}
		
		@Override
		public void run() {

			// NMR Data Processing, Hoch and Stern, p. 23
			
			long N = src.size();
			
			C a0 = alg.construct();
			C a1 = alg.construct();
			C term = alg.construct();
			C sum = alg.construct();

			HighPrecisionMember scale = G.HP.construct(N);
			G.HP.sqrt().call(scale, scale);
			G.HP.invert().call(scale, scale);

			for (long k = start; k < endPlusOne; k++) {

				alg.zero().call(sum);
				
				for (long i = 0; i < N; i++) {
					long j = N-1-i-k;
					if (j < 0) j += N;
					src.get(i, a0);
					src.get(j, a1);
					alg.conjugate().call(a1, a1);
					alg.multiply().call(a0, a1, term);
					alg.add().call(sum, term, sum);
				}
				
				alg.scaleByHighPrec().call(scale, sum, sum);

				dst.set(k, sum);
			}
		}
	}
	
}
