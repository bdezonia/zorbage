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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform3 {

	private Transform3() { }

	/**
	 * 
	 * @param algU
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static final <T extends Algebra<T,U>, U>
		void compute(T algU, Procedure3<U,U,U> proc, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c)
	{
		compute(algU, algU, algU, proc, a, b, c);
	}
	
	/**
	 * 
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param a
	 * @param b
	 * @param c
	 */
	public static final <T extends Algebra<T,U>, U, V extends Algebra<V,W>, W, X extends Algebra<X,Y>, Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc, IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> c)
	{
		U valueU = algU.construct();
		W valueW = algW.construct();
		Y valueY = algY.construct();
		long aSize = a.size();
		for (long i = 0; i < aSize; i++) {
			a.get(i, valueU);
			b.get(i, valueW);
			proc.call(valueU, valueW, valueY);
			c.set(i, valueY);
		}
	}
}
