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

import java.math.BigDecimal;
import java.math.MathContext;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Conjugate;
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
public class InvFFT {

	// do not instantiate
	
	private InvFFT() {}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <V>
	 * @param <W>
	 * @param cmplxAlg
	 * @param realAlg
	 * @param a
	 * @param b
	 */
	public static
		<T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Conjugate<U>,
			U extends SetComplex<W>,
			V extends Algebra<V,W> & Trigonometric<W> & RealConstants<W> & Unity<W> &
				Multiplication<W> & Addition<W> & Invertible<W>,
			W>
	void compute(T cmplxAlg, V realAlg, IndexedDataSource<U> a,IndexedDataSource<U> b)
	{
		long aSize = a.size();
		long bSize = b.size();
		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		U one_over_n = cmplxAlg.construct((BigDecimal.ONE.divide(BigDecimal.valueOf(aSize), new MathContext(100))).toString());
		nom.bdezonia.zorbage.algorithm.Conjugate.compute(cmplxAlg, a, b);
		FFT.compute(cmplxAlg, realAlg, b, b);
		nom.bdezonia.zorbage.algorithm.Conjugate.compute(cmplxAlg, b, b);
		Scale.compute(cmplxAlg, one_over_n, b, b);
	}
}
