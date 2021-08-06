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
import nom.bdezonia.zorbage.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexPolar {

	// do not instantiate
	
	private ComplexPolar() { }

	/**
	 * 
	 * @param r
	 * @param theta
	 */
	public static <T extends Algebra<T,U> & Trigonometric<U> & Multiplication<U>, U>
		void compute(T algebra, U r, U theta, SetComplex<U> out)
	{
		U tmp = algebra.construct();
		U sinTheta = algebra.construct();
		U cosTheta = algebra.construct();
		algebra.sinAndCos().call(theta, sinTheta, cosTheta);
		algebra.multiply().call(r, cosTheta, tmp);
		out.setR(tmp);
		algebra.multiply().call(r, sinTheta, tmp);
		out.setI(tmp);
	}

	/**
	 * @param <T>
	 * @param <U>
	 * @param realAlg
	 * @param real
	 * @param imag
	 * @param magnitude
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Roots<U>, U>
		void magnitude(T realAlg, U real, U imag, U magnitude)
	{
		U sum = realAlg.construct();

		U r2 = realAlg.construct();
		U i2 = realAlg.construct();
		
		// TODO: this is the super naive algo. look at the L2 Norm code
		// for a better approach to tak in the future.
		
		realAlg.multiply().call(real, real, r2);
		realAlg.multiply().call(imag, imag, i2);

		realAlg.add().call(r2, i2, sum);
		
		realAlg.sqrt().call(sum, magnitude);
	}

	/**
	 * @param <T>
	 * @param <U>
	 * @param realAlg
	 * @param real
	 * @param imag
	 * @param phase
	 */
	public static <T extends Algebra<T,U> & Invertible<U> & NaN<U> &
						InverseTrigonometric<U> & RealConstants<U> &
						Ordered<U> & Addition<U>,
					U>
		void phase(T realAlg, U real, U imag, U phase)
	{
		U quotient = realAlg.construct();

		if (realAlg.isZero().call(real)) {

			int signum = realAlg.signum().call(imag);
			
			if (signum < 0) {

				U pi = realAlg.construct();
				
				realAlg.PI().call(pi);
				
				realAlg.negate().call(pi, phase);
			}
			else if (signum > 0) {

				realAlg.PI().call(phase);
			}
			else { // signum == 0
				
				realAlg.nan().call(phase);
			}
		}
		else {

			realAlg.divide().call(imag, real, quotient);
			
			realAlg.atan().call(quotient, phase);
		}
	}
}
