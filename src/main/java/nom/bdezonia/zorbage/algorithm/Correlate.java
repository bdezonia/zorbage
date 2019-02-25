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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Correlate {

	// do not instantiate
	
	private Correlate() {}

	/**
	 * 
	 * @param Algebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U> & Multiplication<U>,U>
		void compute(T Algebra, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b, IndexedDataSource<?,U> c)
	{
		U tmpA = Algebra.construct();
		U tmpB = Algebra.construct();
		U tmpC = Algebra.construct();
		long aSize = a.size();
		long bSize = b.size();
		long cSize = c.size();
		if (aSize != bSize)
			throw new IllegalArgumentException("mismatched inputs");
		if (aSize != cSize)
			throw new IllegalArgumentException("mismatched input/output");
		for (long i = 0; i < aSize; i++) {
			a.get(i, tmpA);
			b.get(i, tmpB);
			Algebra.multiply().call(tmpA, tmpB, tmpC);
			c.set(i, tmpC);
		}
	}
}
