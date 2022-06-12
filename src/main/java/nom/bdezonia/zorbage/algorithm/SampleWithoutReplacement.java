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

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SampleWithoutReplacement {

	// do not instantiate
	
	private SampleWithoutReplacement() { }
	
	/**
	 * Sample an input set n times and place the results in an output set.
	 * Never select the same input location more than once. 
	 * 
	 * @param algebra
	 * @param n
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algebra, long n, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (n > aSize)
			throw new IllegalArgumentException("cannot take "+n+" samples from "+aSize+" elements without replacement");
		if (n > bSize)
			throw new IllegalArgumentException("cannot fit "+n+" samples in "+bSize+" spaces");
		SignedInt64Member idx = G.INT64.construct();
		SignedInt64Member tmp = G.INT64.construct();
		IndexedDataSource<SignedInt64Member> indices = Storage.allocate(idx, n);
		long i = 0;
		while (i < n) {
			BigInteger num = new BigInteger(63, ThreadLocalRandom.current());
			num = num.mod(BigInteger.valueOf(n));
			idx.setV(num.longValue());
			boolean done = true;
			for (long j = 0; j < i; j++) {
				indices.get(j, tmp);
				if (tmp.v() == idx.v()) {
					done = false;
					break;
				}
			}
			if (done) {
				indices.set(i, idx);
				i++;
			}
		}
		U x = algebra.construct();
		for (i = 0; i < n; i++) {
			indices.get(i, idx);
			a.get(idx.v(), x);
			b.set(i, x);
		}
	}
}
