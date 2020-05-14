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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Merge {

	private Merge() { }
	
	/**
	 * Merge two sorted lists into a third sorted list
	 * 
	 * @param alg
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U> & Ordered<U>,U>
		void compute(T alg, IndexedDataSource<U> a, IndexedDataSource<U> b, IndexedDataSource<U> c)
	{
		long aSize = a.size();
		long bSize = b.size();
		long cSize = c.size();
		if (aSize+bSize != cSize)
			throw new IllegalArgumentException("mismatched list sizes");
		long ai = 0;
		long bi = 0;
		long ci = 0;
		U tmpA = alg.construct();
		U tmpB = alg.construct();
		boolean mustLoadA = true;
		boolean mustLoadB = true;
		while (true) {
			if (ai >= aSize) {
				for (long i = bi; i < bSize; i++) {
					b.get(i, tmpB);
					c.set(ci++, tmpB);
				}
				return;
			}
			if (bi >= bSize) {
				for (long i = ai; i < aSize; i++) {
					a.get(i, tmpA);
					c.set(ci++, tmpA);
				}
				return;
			}
			if (mustLoadA) {
				a.get(ai, tmpA);
				mustLoadA = false;
			}
			if (mustLoadB) {
				b.get(bi, tmpB);
				mustLoadB = false;
			}
			if (alg.isLess().call(tmpB, tmpA)) {
				bi++;
				c.set(ci++, tmpB);
				mustLoadB = true;
			}
			else {
				ai++;
				c.set(ci++, tmpA);
				mustLoadA = true;
			}
		}
	}
}
