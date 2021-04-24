/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Generate {

	private Generate() { }
	
	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param inputs
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure<U> proc, IndexedDataSource<U> a, U...inputs)
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
			long thSize;
			if (i != numProcs-1) {
				thSize = slice;
			}
			else {
				thSize = aSize;
			}
			IndexedDataSource<U> aTrimmed = new TrimmedDataSource<>(a, thOffset, thSize);
			Runnable r = new Computer<T,U>(algU, proc, aTrimmed, inputs);
			threads[i] = new Thread(r);
			thOffset += slice;
			aSize -= slice;
		}
		for (int i = 0; i < numProcs; i++) {
			threads[i].start();
		}
		for (int i = 0; i < numProcs; i++) {
			try {
				threads[i].join();
			} catch(InterruptedException e) {
				throw new IllegalArgumentException("Thread execution error in Generate");
			}
		}
	}

	private static class Computer<T extends Algebra<T,U>, U>
		implements Runnable
	{
		private final T algebraU;
		private final IndexedDataSource<U> list;
		private final Procedure<U> proc;
		private final U[] inputs;
		
		Computer(T algU, Procedure<U> proc, IndexedDataSource<U> a, U[] inputs) {
			this.algebraU = algU;
			this.list = a;
			this.proc = proc;
			this.inputs = inputs;
		}
		
		public void run() {
			U tmp = algebraU.construct();
			for (long i = 0; i < list.size(); i++) {
				proc.call(tmp, inputs);
				list.set(i, tmp);
			}
		}
	}

}
