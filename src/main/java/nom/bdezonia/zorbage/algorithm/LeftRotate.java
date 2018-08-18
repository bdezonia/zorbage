/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LeftRotate {

	/**
	 * Do a left rotation of a set of values.
	 * 
	 * @param group
	 * @param delta
	 * @param a
	 */
	public static <T extends Group<T,U>, U>
		void compute(T group, long delta, IndexedDataSource<?,U> a)
	{
		if (a.size() == 0) return;
		if (delta < 0) throw new IllegalArgumentException("delta too small");
		delta = delta % a.size();
		if (delta == 0) return; // nothing to do
		
		U tmp1 = group.construct();
		U tmp2 = group.construct();

		long n = a.size();
		
		for (long i = 0; i < gcd(delta, n); i++)
		{
			/* move i-th values of blocks */
			a.get(i, tmp1);
			long j = i;

			while (true)
			{
				long k = j + delta;
				if (k >= n)
					k = k - n;

				if (k == i)
					break;

				a.get(k, tmp2);
				a.set(j, tmp2);
				j = k;
			}
			a.set(j, tmp1);
		}
	}
	
	private static long gcd(long a, long b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a%b);
	}
}
