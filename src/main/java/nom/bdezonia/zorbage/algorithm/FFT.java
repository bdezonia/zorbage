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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.SetComplex;
import nom.bdezonia.zorbage.type.data.floatunlim.real.HighPrecisionMember;
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
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>, U extends SetComplex<HighPrecisionMember>>
		void compute(T algebra, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		
		U tmp1 = algebra.construct();
		U tmp2 = algebra.construct();

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

		U w = algebra.construct();
		U tao = algebra.construct();

		// butterfly updates
		HighPrecisionMember pi = G.FLOAT_UNLIM.construct();
		G.FLOAT_UNLIM.PI().call(pi);
		HighPrecisionMember tmp = G.FLOAT_UNLIM.construct();
		HighPrecisionMember cos = G.FLOAT_UNLIM.construct();
		HighPrecisionMember sin = G.FLOAT_UNLIM.construct();
		for (long L = 2; L <= aSize; L = L+L) {
			for (long k = 0; k < L/2; k++) {
				BigDecimal kth = BigDecimal.valueOf(-2 * k).multiply(pi.v()).divide(BigDecimal.valueOf(L));
				tmp.setV(kth);
				G.FLOAT_UNLIM.cos().call(tmp, cos);
				G.FLOAT_UNLIM.sin().call(tmp, sin);
				w.setR(cos);
				w.setI(sin);
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
