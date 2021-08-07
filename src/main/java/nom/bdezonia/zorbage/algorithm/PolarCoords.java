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
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PolarCoords {

	/**
	 * Calculate the magnitude of a complex number given the real and imag
	 * components as the inputs.
	 * 
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
		// for a better approach to take in the future.
		
		realAlg.multiply().call(real, real, r2);
		realAlg.multiply().call(imag, imag, i2);

		realAlg.add().call(r2, i2, sum);
		
		realAlg.sqrt().call(sum, magnitude);
	}

	/**
	 * Calculate the phase of a complex number given the real and imag
	 * components as the inputs.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param realAlg
	 * @param real
	 * @param imag
	 * @param phase
	 */
	public static <T extends Algebra<T,U> & Invertible<U> & NaN<U> &
						InverseTrigonometric<U> & RealConstants<U> &
						Ordered<U> & Addition<U> & Unity<U>,
					U>
		void phase(T realAlg, U real, U imag, U phase)
	{
		int xSignum = realAlg.signum().call(real);

		int ySignum = realAlg.signum().call(imag);

		U quotient = realAlg.construct();
		
		U pi = realAlg.construct();
		
		U arctan = realAlg.construct();
		
		if (xSignum > 0) {

			realAlg.divide().call(imag, real, quotient);
			
			realAlg.atan().call(quotient, phase);
		}
		else if (xSignum < 0) {
			
			realAlg.PI().call(pi);

			if (ySignum >= 0) {
				
				realAlg.divide().call(imag, real, quotient);
				
				realAlg.atan().call(quotient, arctan);
				
				realAlg.add().call(quotient,  pi,  phase);
			}
			else {  // ySignum < 0
				
				realAlg.divide().call(imag, real, quotient);
				
				realAlg.atan().call(quotient, arctan);
				
				realAlg.subtract().call(quotient,  pi,  phase);
			}
		}
		else { // xSignum == 0
			
			if (ySignum > 0) {
			
				U one = realAlg.construct();

				U two = realAlg.construct();
				
				realAlg.unity().call(one);
				
				realAlg.add().call(one, one, two);
				
				realAlg.divide().call(pi, two, phase);
			}
			else if (ySignum < 0) {
				
				U one = realAlg.construct();

				U two = realAlg.construct();
				
				realAlg.unity().call(one);
				
				realAlg.add().call(one, one, two);
				
				realAlg.divide().call(pi, two, quotient);
				
				realAlg.negate().call(quotient, phase);
			}
			else { // ySignum == 0
				
				realAlg.nan().call(phase);
			}
		}
	}
	
	/**
	 * Calculate the real coordinate value of a complex number
	 * given the magnitude and phase components.
	 * 
	 * @param realAlg
	 * @param magnitude
	 * @param phase
	 * @param real
	 */
	public static <T extends Algebra<T,U> & Trigonometric<U> & Multiplication<U>, U>
		void real(T realAlg, U magnitude, U phase, U real)
	{
		U cosPhase = realAlg.construct();
		
		realAlg.cos().call(phase, cosPhase);
		
		realAlg.multiply().call(magnitude, cosPhase, real);
	}

	/**
	 * Calculate the imaginary coordinate value of a complex number
	 * given the magnitude and phase components.
	 * 
	 * @param realAlg
	 * @param magnitude
	 * @param phase
	 * @param imag
	 */
	public static <T extends Algebra<T,U> &
			Trigonometric<U> & Multiplication<U>,
			U>
		void imaginary(T realAlg, U magnitude, U phase, U imag)
	{
		U sinPhase = realAlg.construct();

		realAlg.sin().call(phase, sinPhase);
		
		realAlg.multiply().call(magnitude, sinPhase, imag);
	}
}
