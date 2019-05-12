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

import net.jafama.FastMath;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Algebra;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

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
	 * @param algebra
	 * @param a
	 * @param b
	 */
	public static
		void compute(ComplexFloat64Algebra algebra, IndexedDataSource<ComplexFloat64Member> a, IndexedDataSource<ComplexFloat64Member> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		
		ComplexFloat64Member tmp1 = algebra.construct();
		ComplexFloat64Member tmp2 = algebra.construct();

		// bit reversal permutation
		int shift = 1 + Long.numberOfLeadingZeros(aSize);
		for (long k = 0; k < aSize; k++) {
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

		ComplexFloat64Member w = algebra.construct();
		ComplexFloat64Member tao = algebra.construct();

		// butterfly updates
		for (long L = 2; L <= aSize; L = L+L) {
			for (long k = 0; k < L/2; k++) {
				double kth = -2 * k * Math.PI / L;
				w.setR(FastMath.cos(kth));
				w.setI(FastMath.sin(kth));
				for (long j = 0; j < aSize/L; j++) {
					b.get(j*L + k + L/2, tmp1);
					algebra.multiply().call(w, tmp1, tao);
					b.get(j*L + k, tmp2);
					algebra.subtract().call(tmp2, tao, tmp1);
					b.set(j*L + k + L/2, tmp1);
					algebra.add().call(tmp2, tao, tmp1);
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
		throw new IllegalArgumentException("number passed ("+num+") does not have an enclosing power of two that fits into a positive long");
	}
}
