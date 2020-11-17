/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform2 {

	private Transform2() { }

	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <AA extends Algebra<AA,A>, A>
		void compute(AA alg, Procedure2<A,A> proc, IndexedDataSource<A> a, IndexedDataSource<A> b)
	{
		compute(alg, alg, proc, a, b);
	}
	
	/**
	 * 
	 * @param algA
	 * @param algB
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <AA extends Algebra<AA,A>,A,BB extends Algebra<BB,B>,B>
		void compute(AA algA, BB algB, Procedure2<A,B> proc, IndexedDataSource<A> a, IndexedDataSource<B> b)
	{
		A valueA = algA.construct();
		B valueB = algB.construct();
		long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueA);
			proc.call(valueA, valueB);
			b.set(i, valueB);
		}
	}

}
