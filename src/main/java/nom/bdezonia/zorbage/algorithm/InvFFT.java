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

import java.math.BigDecimal;
import java.math.MathContext;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
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
	 * Do an inverse fast Fourier transform taking data from source a and putting
	 * results in destination b. a and b can be the same list: in place transformation
	 * works. a and b are lists of complex numbers. Their length must match and be a
	 * power of 2.
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
	public static
	
		<CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> &
						Conjugate<C>,
						
			C extends SetR<R> & SetI<R>,
			
			RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
				Unity<R> & Multiplication<R> & Addition<R> & Invertible<R>,
				
			R>
	
	void compute(CA complexAlg,
					RA realAlg,
					IndexedDataSource<C> a,
					IndexedDataSource<C> b)
	{
		long aSize = a.size();
		long bSize = b.size();

		if (aSize != FFT.enclosingPowerOf2(aSize))
			throw new IllegalArgumentException("input size is not a power of 2");
		
		if (aSize != bSize)
			throw new IllegalArgumentException("output size does not match input size");
		
		C one_over_n = complexAlg.construct((BigDecimal.ONE.divide(BigDecimal.valueOf(aSize), new MathContext(100))).toString());
		nom.bdezonia.zorbage.algorithm.Conjugate.compute(complexAlg, a, b);
		FFT.compute(complexAlg, realAlg, b, b);
		nom.bdezonia.zorbage.algorithm.Conjugate.compute(complexAlg, b, b);
		Scale.compute(complexAlg, one_over_n, b, b);
	}
}
