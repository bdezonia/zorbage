/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 * 
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.SequencedDataSource;
import nom.bdezonia.zorbage.dataview.TwoDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class InvFFT2D {

	// do not instantiate
	
	private InvFFT2D() { }

	/**
	 * Do a 2-dimensional inverse Fourier transform of 1 plane of a complex DataSource
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param complexAlg
	 * @param realAlg
	 * @param inputPlane
	 * @return
	 */
	public static
	
		<CA extends Algebra<CA,C> & Addition<C> & Multiplication<C> & Conjugate<C>,
	
			C extends  Allocatable<C> & SetR<R> & SetI<R>,
			
			RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
						Unity<R> & Multiplication<R> & Addition<R> &
						Invertible<R>,
			
			R>
	
		DimensionedDataSource<C>
	
			compute(CA complexAlg,
					RA realAlg,
					DimensionedDataSource<C> inputPlane)
	{
		if (inputPlane.numDimensions() != 2)
			throw new IllegalArgumentException("InvFFT2D input should be a single 2-d plane of complex values");
		
		C complexValue = complexAlg.construct();
		
		// calc some important variables
		
		long cols = inputPlane.dimension(0);
		
		long rows = inputPlane.dimension(1);
		
		long sz = FFT.enclosingPowerOf2(Math.max(rows, cols));
		
		long[] squareDims = new long[] {sz,sz};

		// create power of two square planes for calculations
		
		DimensionedDataSource<C> complexData =
				DimensionedStorage.allocate(complexValue, squareDims);

		DimensionedDataSource<C> tmpPlane =
				DimensionedStorage.allocate(complexValue, squareDims);

		DimensionedDataSource<C> outputPlane =
				DimensionedStorage.allocate(complexValue, squareDims);

		// Copy the rectangular input image into the square plane defined for it.
		// The values outside the rectangular region will be zero (padding).
		
		TwoDView<C> readView = new TwoDView<>(inputPlane);
		
		TwoDView<C> writeView = new TwoDView<>(complexData);
		
		for (long y = 0; y < rows; y++) {

			for (long x = 0; x < cols; x++) {
			
				readView.get(x, y, complexValue);
				
				writeView.set(x, y, complexValue);
			}
		}
		
		// for each column of input do a 1-d InvFFT and store as a column in temp data
		
		for (long c = 0; c < sz; c++) {
			
			// setup the input col to do InvFFT on
			
			IndexedDataSource<C> inCol = new SequencedDataSource<>(complexData.rawData(), c, sz, sz);
			
			// setup the tmp col to place InvFFT results in

			IndexedDataSource<C> tmpCol = new SequencedDataSource<>(tmpPlane.rawData(), c, sz, sz);
			
			// do the InvFFT into from the input col into the tmp col
			
			InvFFT.compute(complexAlg, realAlg, inCol, tmpCol);
		}
		
		// for each row of temp data do a 1-d InvFFT and store as a row in output
		
		for (long r = 0; r < sz; r++) {
					
			// setup the tmp row to do InvFFT on

			IndexedDataSource<C> tmpRow = new SequencedDataSource<>(tmpPlane.rawData(), r*sz, 1, sz);
			
			// setup the output row to place InvFFT results in

			IndexedDataSource<C> outRow = new SequencedDataSource<>(outputPlane.rawData(), r*sz, 1, sz);
			
			// do the InvFFT from the tmp row into the output row
			
			InvFFT.compute(complexAlg, realAlg, tmpRow, outRow);
		}
		
		return outputPlane;
	}
}
