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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.SetComplex;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.Unity;
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
	 * Do a fast fourier transform taking data from source a and putting results in
	 * destination b. a and b can be the same list: in place transformation works.
	 * a and b are lists of complex numbers. Their length must match and be a power
	 * of 2.
	 * 
	 * @param cmplxAlg
	 * @param realAlg
	 * @param a Source list of data
	 * @param b Destination list of data
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>,
						U extends SetComplex<W>,
						V extends Algebra<V,W> & Trigonometric<W> & RealConstants<W> &
							Multiplication<W> & Addition<W> & Invertible<W> & Unity<W>,
						W>
		void compute(T cmplxAlg, V realAlg, IndexedDataSource<U> a, IndexedDataSource<U> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		
		U tmp1 = cmplxAlg.construct();
		U tmp2 = cmplxAlg.construct();

		// bit reversal permutation
		int shift = 1 + Long.numberOfLeadingZeros(aSize);
		for (long k = 0; k < aSize; k++) {
			long j = Long.reverse(k) >>> shift;
			a.get(j, tmp1);
			a.get(k, tmp2);
			if (j < k) {
				b.set(k, tmp1);
				b.set(j, tmp2);
			}
			else {
				b.set(j, tmp1);
				b.set(k, tmp2);
			}
		}

		U w = cmplxAlg.construct();
		U tao = cmplxAlg.construct();

		// butterfly updates
		W pi = realAlg.construct();
		realAlg.PI().call(pi);
		W cos = realAlg.construct();
		W sin = realAlg.construct();
		W one = realAlg.construct();
		W two = realAlg.construct();
		realAlg.unity().call(one);
		realAlg.add().call(one, one, two);
		W l = realAlg.construct(two);
		W k = realAlg.construct();
		W kth = realAlg.construct();
		for (long el = 2; el <= aSize; el = el+el) {
			realAlg.zero().call(k);
			for (long kay = 0; kay < el/2; kay++) {
				realAlg.negate().call(two, kth);
				realAlg.multiply().call(kth, k, kth);
				realAlg.multiply().call(kth, pi, kth);
				realAlg.divide().call(kth, l, kth);
				realAlg.cos().call(kth, cos);
				realAlg.sin().call(kth, sin);
				w.setR(cos);
				w.setI(sin);
				for (long j = 0; j < aSize/el; j++) {
					b.get(j*el + kay + el/2, tmp1);
					cmplxAlg.multiply().call(w, tmp1, tao);
					b.get(j*el + kay, tmp2);
					cmplxAlg.subtract().call(tmp2, tao, tmp1);
					b.set(j*el + kay + el/2, tmp1);
					cmplxAlg.add().call(tmp2, tao, tmp1);
					b.set(j*el + kay, tmp1);
				}
				realAlg.add().call(k, one, k);
			}
			realAlg.add().call(l, l, l);
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
