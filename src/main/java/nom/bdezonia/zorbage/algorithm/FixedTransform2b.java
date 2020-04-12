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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FixedTransform2b {

	private FixedTransform2b() {}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param algU
	 * @param fixedValue
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algU, U fixedValue, Procedure3<U,U,U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		compute(algU, algU, fixedValue, proc, a, b);
	}

	/**
	 * 
	 * @param <B>
	 * @param <C>
	 * @param <D>
	 * @param <E>
	 * @param <F>
	 * @param algD
	 * @param algF
	 * @param fixedValue
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <B, C extends Algebra<C,D>, D, E extends Algebra<E,F>, F>
		void compute(C algD, E algF, B fixedValue, Procedure3<D,B,F> proc, IndexedDataSource<D> a, IndexedDataSource<F> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != bSize)
			throw new IllegalArgumentException("mismatched list sizes");
		D tmp1 = algD.construct();
		F tmp2 = algF.construct();
		for (long i = 0; i < aSize; i++) {
			a.get(i, tmp1);
			proc.call(tmp1, fixedValue, tmp2);
			b.set(i, tmp2);
		}
	}
}
