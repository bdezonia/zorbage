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
import nom.bdezonia.zorbage.algebra.GetComplex;
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
public class ComplexCorrelation {

	// do not instantiate
	
	private ComplexCorrelation() { }

	/**
	 * ComplexCorrelation.compute(realAlg, aV, bV, result)
	 * 
	 * @param realAlg The real algebra that backs the complex values use in call
	 * @param aV A complex value
	 * @param bV Another complex value
	 * @param result The real correlation value between them (between -1 and 1)
	 */
	public static <CA extends Algebra<CA,C>,
					C extends GetComplex<R>,
					RA extends Algebra<RA,R> & Ordered<R> & Unity<R> &
								NaN<R> & Addition<R> & Multiplication<R> &
								Invertible<R> & Trigonometric<R> & Roots<R> &
								RealConstants<R> & InverseTrigonometric<R> &
								AbsoluteValue<R,R>,
					R>
	
		void compute(RA realAlg, C aV, C bV, R result)
	{
		R zero = realAlg.construct();
		
		R one = realAlg.construct();
		
		R minus_one = realAlg.construct();
		
		R aMagnitude = realAlg.construct();
		
		R aPhase = realAlg.construct();
		
		R bMagnitude = realAlg.construct();
		
		R bPhase = realAlg.construct();
		
		R real = realAlg.construct();
		
		R imag = realAlg.construct();
		
		R angle = realAlg.construct();
		
		R projection = realAlg.construct();
		
		R cosAngle = realAlg.construct();
		
		R relativeValue = realAlg.construct();

		realAlg.unity().call(one);
		
		realAlg.subtract().call(zero, one, minus_one);
		
		aV.getR(real);
		
		aV.getI(imag);
		
		PolarCoords.magnitude(realAlg, real, imag, aMagnitude);
		
		PolarCoords.phase(realAlg, real, imag, aPhase);
		
		bV.getR(real);
		
		bV.getI(imag);
		
		PolarCoords.magnitude(realAlg, real, imag, bMagnitude);
		
		PolarCoords.phase(realAlg, real, imag, bPhase);

		R shortLeg, longLeg, shortPhase, longPhase;
		
		if (realAlg.isLess().call(aMagnitude, bMagnitude)) {
			
			shortLeg   = aMagnitude;
			shortPhase = aPhase;
			longLeg    = bMagnitude;
			longPhase  = bPhase;
		}
		else {
			
			shortLeg   = bMagnitude;
			shortPhase = bPhase;
			longLeg    = aMagnitude;
			longPhase  = aPhase;
		}
		
		if (realAlg.isZero().call(longLeg)) {

			// treat 0/0 as perfect correlation
			//   don't consider angles

			realAlg.unity().call(result);
			
			return;
		}
		
		// a phase of Nan means indeterminate angle
		
		if (realAlg.isNaN().call(shortPhase)) {
		
			shortPhase = zero;
		}

		if (realAlg.isNaN().call(longPhase)) {
			
			longPhase = zero;
		}

		// phases range from -pi to pi
		// so angle ranges from -2pi to 2pi
		
		realAlg.subtract().call(shortPhase, longPhase, angle);

		realAlg.cos().call(angle, cosAngle);

		realAlg.multiply().call(shortLeg, cosAngle, projection);

		realAlg.divide().call(projection, longLeg, relativeValue);
		
		if (realAlg.isLess().call(relativeValue, minus_one) ||
				realAlg.isGreater().call(relativeValue, one))
		{
			throw new IllegalArgumentException(
						"relative value out of legal bounds! "+relativeValue);
		}
		
		realAlg.assign().call(relativeValue, result);
	}
}
