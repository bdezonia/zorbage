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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Multiplication;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ParallelMatrixMultiply {

	/**
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>,U>
		void compute(T algebra, MatrixMember<U> a, MatrixMember<U> b, MatrixMember<U> c)
	{
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		
		long rows = a.rows();
		long cols = b.cols();
		c.alloc(rows, cols);

		long numRows = rows;
		long pieces = Runtime.getRuntime().availableProcessors();
		
		if (pieces > numRows)
			pieces = numRows; // 1 thread per row
		
		if (pieces > Integer.MAX_VALUE)
			pieces = Integer.MAX_VALUE;

		final Thread[] threads = new Thread[(int)pieces];
		long start = 0;
		for (int i = 0; i < pieces; i++) {
			long end;
			// last piece?
			if (i == pieces-1) {
				end = numRows;
			}
			else {
				end = start + (numRows/pieces);
			}
			Computer<T,U> computer = new Computer<T,U>(algebra, start, end, a, b, c);
			threads[i] = new Thread(computer);
			start = end;
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException e) {
				throw new IllegalArgumentException("Thread execution error in ParallelMatrixMultiply");
			}
		}
	}
	
	private static class Computer<T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U>
		implements Runnable
	{
		private final T algebra;
		private final long start;
		private final long end;
		private final MatrixMember<U> a;
		private final MatrixMember<U> b;
		private final MatrixMember<U> c;
		
		public Computer(T algebra, long start, long end, MatrixMember<U> a, MatrixMember<U> b, MatrixMember<U> c) {
			this.algebra = algebra;
			this.start = start;
			this.end = end;
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		@Override
		public void run() {
			U sum = algebra.construct();
			U atmp = algebra.construct();
			U btmp = algebra.construct();
			U term = algebra.construct();
			long cols = b.cols();
			long common = a.cols();
			for (long row = start; row < end; row++) {
				for (long col = 0; col < cols; col++) {
					algebra.zero().call(sum);
					for (long i = 0; i < common; i++) {
						a.v(row, i, atmp);
						b.v(i, col, btmp);
						algebra.multiply().call(atmp, btmp, term);
						algebra.add().call(sum, term, sum);
					}
					c.setV(row, col, sum);
				}
			}
		}
	}
}