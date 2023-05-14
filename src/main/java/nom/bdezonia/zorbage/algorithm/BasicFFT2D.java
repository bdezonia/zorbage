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

import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.dataview.TwoDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BasicFFT2D {

	// do not instantiate
	
	private BasicFFT2D() { }
	
	/**
	 * BasicFFT2D takes a 2d data source any size of real data and returns
	 * a power of 2 square output data source of FFT transformed complex data.
	 * 
	 * @param <CA> Complex algebra type
	 * @param <C> Complex number type
	 * @param <RA> Real algebra type
	 * @param <R> Real number type
	 * @param algR Real algebra
	 * @param algC Complex algebra
	 * @param inputData Data source of real numbers
	 * @return Power of 2 sized data source of FFT transformed complex data
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C>,
					C extends SetR<R> & SetI<R> & Allocatable<C>,
					RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
								Multiplication<R> & Addition<R> & Invertible<R> & Unity<R>,
					R>
		DimensionedDataSource<C> compute(CA algC, RA algR, DimensionedDataSource<R> inputData)
	{
		if (inputData.numDimensions() != 2) {
			
			throw new IllegalArgumentException("BasicFFT2D only works with 2d input data");
		}
		
		long xSize = inputData.dimension(0);

		long ySize = inputData.dimension(1);
		
		long size = FFT.enclosingPowerOf2(Math.max(xSize, ySize));
		
		BigInteger bigSize = BigInteger.valueOf(size);
		
		BigInteger bigMaxValue = BigInteger.valueOf(Long.MAX_VALUE);
		
		if (bigSize.multiply(bigSize).compareTo(bigMaxValue) > 0) {
			
			throw new IllegalArgumentException("Destination complex data size is too big");
		}
	
		DimensionedDataSource<C> complexes = DimensionedStorage.allocate(algC.construct(), new long[] {size, size});

		TwoDView<C> complex2dView = new TwoDView<>(complexes); 

		TwoDView<R> real2dView = new TwoDView<>(inputData); 
		
		C complex = algC.construct();
		
		R real = algR.construct();
		
		for (long y = 0; y < ySize; y++) {
		
			for (long x = 0; x < xSize; x++) {
			
				real2dView.get(x, y, real);
				
				complex.setR(real);
				
				complex2dView.set(x, y, complex);
			}
		}
		
		return FFT2D.compute(algC, algR, complexes);
	}
}
