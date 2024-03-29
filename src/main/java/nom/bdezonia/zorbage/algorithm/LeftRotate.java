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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LeftRotate {

	// do not instantiate
	
	private LeftRotate() { }
	
	/**
	 * Do a left rotation of a set of values.
	 * 
	 * @param algebra
	 * @param delta
	 * @param a
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T algebra, long delta, IndexedDataSource<U> a)
	{
		long aSize = a.size();
		if (aSize == 0) return;
		if (delta < 0) {
			RightRotate.compute(algebra, Math.abs(delta), a);
			return;
		}
		delta = delta % aSize;
		if (delta == 0) return; // nothing to do
		
		U tmp1 = algebra.construct();
		U tmp2 = algebra.construct();

		long n = aSize;
		
		for (long i = 0; i < gcd(delta, n); i++)
		{
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
