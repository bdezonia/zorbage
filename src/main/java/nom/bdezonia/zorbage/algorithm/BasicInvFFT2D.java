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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.GetR;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.dataview.TwoDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BasicInvFFT2D {

	// do not instantiate
	
	private BasicInvFFT2D() { }

	/**
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param algC
	 * @param algR
	 * @param input
	 * @param output
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> & Conjugate<C>,
					C extends SetR<R> & SetI<R> & GetR<R> & Allocatable<C>,
					RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> & Unity<R> &
								Multiplication<R> & Addition<R> & Invertible<R>,
					R extends SetR<R>>

		void compute(CA algC,
						RA algR,
						DimensionedDataSource<C> input,
						DimensionedDataSource<R> output)
	{
		if (input.numDimensions() != 2) {
			
			throw new IllegalArgumentException("BasicInvFFT2D requires 2d input");
		}

		if (output.numDimensions() != 2) {
			
			throw new IllegalArgumentException("BasicInvFFT2D requires 2d output");
		}

		long xSize = output.dimension(0);
		
		long ySize = output.dimension(1);

		long pow2XSize = FFT.enclosingPowerOf2(xSize);
		
		long pow2YSize = FFT.enclosingPowerOf2(ySize);
		
		long pow2Size = Math.max(pow2XSize, pow2YSize);
		
		if (pow2Size != input.dimension(0)) {
			throw new IllegalArgumentException("input complex data xSize is not correct power of 2");
		}
		
		if (pow2Size != input.dimension(1)) {
			throw new IllegalArgumentException("input complex data ySize is not correct power of 2");
		}
		
		DimensionedDataSource<C> complexData = InvFFT2D.compute(algC, algR, input);

		TwoDView<C> complex2dView = new TwoDView<>(complexData); 

		TwoDView<R> real2dView = new TwoDView<>(output); 
		
		C complex = algC.construct();
		
		R real = algR.construct();
		
		for (long y = 0; y < ySize; y++) {
		
			for (long x = 0; x < xSize; x++) {
				
				complex2dView.get(x, y, complex);
			
				complex.getR(real);
				
				real2dView.set(x, y, real);
			}
		}
	}
}
