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

import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Group;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FFT {

	// don't instantiate
	
	private FFT() {}

	/**
	 * 
	 * @param group
	 * @param a
	 * @param b
	 */
	public static
		void compute(ComplexFloat64Group group, IndexedDataSource<?,ComplexFloat64Member> a, IndexedDataSource<?,ComplexFloat64Member> b)
	{
		if (a.size() != FFT.enclosingPowerOf2(a.size()))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (a.size() != b.size())
			throw new IllegalArgumentException("output size does not match input size");
		
		ComplexFloat64Member tmp1 = group.construct();
		ComplexFloat64Member tmp2 = group.construct();

		// bit reversal permutation
		int shift = 1 + Long.numberOfLeadingZeros(a.size());
		for (long k = 0; k < a.size(); k++) {
			long j = Long.reverse(k) >>> shift;
			a.get(j, tmp1);
			a.get(k, tmp2);
			if (j > k) {
				b.set(k, tmp1);
				b.set(j, tmp2);
			}
			else {
				b.set(j, tmp1);
				b.set(k, tmp2);
			}
		}

		ComplexFloat64Member w = group.construct();
		ComplexFloat64Member tao = group.construct();

		// butterfly updates
		for (long L = 2; L <= a.size(); L = L+L) {
			for (long k = 0; k < L/2; k++) {
				double kth = -2 * k * Math.PI / L;
				w.setR(Math.cos(kth));
				w.setI(Math.sin(kth));
				for (long j = 0; j < a.size()/L; j++) {
					b.get(j*L + k + L/2, tmp1);
					group.multiply(w, tmp1, tao);
					b.get(j*L + k, tmp2);
					group.subtract(tmp2, tao, tmp1);
					b.set(j*L + k + L/2, tmp1);
					group.add(tmp2, tao, tmp1);
					b.set(j*L + k, tmp1);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static long enclosingPowerOf2(long num) {
		if (num <= 0)
			throw new IllegalArgumentException("num too small");
		long max = 1;
		for (int i = 0; i < 63; i++) {
			if (num <= max) return max;
			max <<= 1;
		}
		throw new IllegalArgumentException("num too big");
	}
}
