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

import nom.bdezonia.zorbage.algebra.AbsoluteValue;
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

	// do not instantiate
	
	private PolarCoords() { }
	
	/**
	 * Calculate the magnitude of a complex number given the real and imag
	 * components as the inputs.
	 * 
	 * @param <RA>
	 * @param <R>
	 * @param realAlg
	 * @param real
	 * @param imag
	 * @param magnitude
	 */
	public static
	
		<RA extends Algebra<RA,R> & Addition<R> & Multiplication<R> &
					Roots<R> & Ordered<R> & AbsoluteValue<R,R> &
					Invertible<R>,
			R>
	
		void magnitude(RA realAlg, R real, R imag, R magnitude)
	{
		R max = realAlg.construct();

		R r2 = realAlg.construct();
		
		R i2 = realAlg.construct();
		
		R sum = realAlg.construct();
		
		realAlg.max().call(real, imag, max);
		
		if (realAlg.isZero().call(max)) {
		
			realAlg.zero().call(max);
			return;
		}
		
		realAlg.abs().call(max, max);

		realAlg.divide().call(real, max, r2);
		
		realAlg.divide().call(imag, max, i2);
		
		realAlg.multiply().call(r2, r2, r2);
		
		realAlg.multiply().call(i2, i2, i2);
		
		realAlg.add().call(r2, i2, sum);
		
		realAlg.sqrt().call(sum, sum);
		
		realAlg.multiply().call(sum, max, magnitude);
	}

	/**
	 * Calculate the phase of a complex number given the real and imag components as the inputs.
	 * Phase is always returned from within the half open range (-pi, pi].
	 * 
	 * @param <RA>
	 * @param <R>
	 * @param realAlg
	 * @param real
	 * @param imag
	 * @param phase
	 */
	public static
	
		<RA extends Algebra<RA,R> & Invertible<R> & NaN<R> &
						InverseTrigonometric<R> & RealConstants<R> &
						Ordered<R> & Addition<R> & Unity<R>,
			R>
	
		void phase(RA realAlg, R real, R imag, R phase)
	{
		int xSignum = realAlg.signum().call(real);

		int ySignum = realAlg.signum().call(imag);

		R quotient = realAlg.construct();
		
		R pi = realAlg.construct();
		
		R one = realAlg.construct();

		R two = realAlg.construct();

		if (xSignum == 0 && ySignum == 0) {
			
			realAlg.nan().call(phase);
		}
		else if (xSignum == 0) {  // and ySignum does not

			realAlg.PI().call(pi);

			realAlg.unity().call(one);
				
			realAlg.add().call(one, one, two);
					
			realAlg.divide().call(pi, two, quotient);
					
			if (ySignum > 0) {

				realAlg.assign().call(quotient, phase);
			}
			else {

				realAlg.negate().call(quotient, phase);
			}
		}
		else if (ySignum == 0) {  // and xSignum does not
				
			if (xSignum < 0) {
					
				realAlg.PI().call(phase);
			}
			else {

				realAlg.zero().call(phase);
			}
		}
		else {
			
			realAlg.divide().call(imag, real, quotient);
			
			realAlg.atan().call(quotient, phase);
			
			if (xSignum < 0) {

				realAlg.PI().call(pi);

				if (ySignum < 0) {

					realAlg.subtract().call(phase, pi, phase);
				}
				else {

					realAlg.add().call(phase, pi, phase);
				}
			}
		}
	}
	
	/**
	 * Calculate the real coordinate value of a complex number
	 * given the magnitude and phase components.
	 * 
	 * @param <RA>
	 * @param <R>
	 * @param realAlg
	 * @param magnitude
	 * @param phase
	 * @param real
	 */
	public static
	
		<RA extends Algebra<RA,R> & Trigonometric<R> & Multiplication<R>,
			R>
	
		void real(RA realAlg, R magnitude, R phase, R real)
	{
		R cosPhase = realAlg.construct();
		
		realAlg.cos().call(phase, cosPhase);
		
		realAlg.multiply().call(magnitude, cosPhase, real);
	}

	/**
	 * Calculate the imaginary coordinate value of a complex number
	 * given the magnitude and phase components.
	 * 
	 * @param <RA>
	 * @param <R>
	 * @param realAlg
	 * @param magnitude
	 * @param phase
	 * @param imag
	 */
	public static
		
		<RA extends Algebra<RA,R> & Trigonometric<R> & Multiplication<R>,
			R>
	
		void imaginary(RA realAlg, R magnitude, R phase, R imag)
	{
		R sinPhase = realAlg.construct();

		realAlg.sin().call(phase, sinPhase);
		
		realAlg.multiply().call(magnitude, sinPhase, imag);
	}
}
