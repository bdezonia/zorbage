/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FFT {

	// do not instantiate
	
	private FFT() {}

	/**
	 * Do a fast fourier transform taking data from source a and putting results in
	 * destination b. a and b can be the same list: in place transformation works.
	 * a and b are lists of complex numbers. Their length must match and be a power
	 * of 2.
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param complexAlg
	 * @param realAlg
	 * @param a Source list of complex data
	 * @param b Destination list of complex data
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C>,
						C extends SetComplex<R>,
						RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
							Multiplication<R> & Addition<R> & Invertible<R> & Unity<R>,
						R>
		void compute(CA complexAlg, RA realAlg, IndexedDataSource<C> a, IndexedDataSource<C> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		
		C tmp1 = complexAlg.construct();
		C tmp2 = complexAlg.construct();

		Copy.compute(complexAlg, a, b);
		
		// bit reversal permutation
		int shift = 1 + Long.numberOfLeadingZeros(aSize);
		for (long k = 0; k < aSize; k++) {
			long j = Long.reverse(k) >>> shift;
			if (j > k) {
				b.get(j, tmp1);
				b.get(k, tmp2);
				b.set(k, tmp1);
				b.set(j, tmp2);
			}
		}

		C w = complexAlg.construct();
		C tao = complexAlg.construct();

		// butterfly updates
		R pi = realAlg.construct();
		realAlg.PI().call(pi);
		R cos = realAlg.construct();
		R sin = realAlg.construct();
		R one = realAlg.construct();
		R two = realAlg.construct();
		realAlg.unity().call(one);
		realAlg.add().call(one, one, two);
		R jth = realAlg.construct();
		R j = realAlg.construct();
		R l = realAlg.construct(two);
		for (long el = 2; el <= aSize; el = el+el) {
			realAlg.zero().call(j);
			for (long jay = 0; jay < el/2; jay++) {
	 			realAlg.multiply().call(two, pi, jth);
	 			realAlg.divide().call(jth, l, jth);
	 			realAlg.multiply().call(jth, j, jth);
	 			realAlg.cos().call(jth, cos);
	 			realAlg.sin().call(jth, sin);
	 			realAlg.negate().call(sin, sin);
	 			w.setR(cos);
	 			w.setI(sin);
				for (long kay = 0; kay < aSize/el; kay++) {
					final long idx1 = kay*el + jay;
					final long idx2 = idx1 + el/2;
					b.get(idx2, tmp1);
					complexAlg.multiply().call(w, tmp1, tao);
					b.get(idx1, tmp2);
					complexAlg.subtract().call(tmp2, tao, tmp1);
					b.set(idx2, tmp1);
					complexAlg.add().call(tmp2, tao, tmp1);
					b.set(idx1, tmp1);
				}
				realAlg.add().call(j, one, j);
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
