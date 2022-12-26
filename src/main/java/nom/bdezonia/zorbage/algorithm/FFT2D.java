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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetComplex;
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
public class FFT2D {

	// do not instantiate
	
	private FFT2D() { }

	/**
	 * Do a 2-dimensional Fourier transform of 1 plane of a complex DataSource
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param cmplxAlg
	 * @param realAlg
	 * @param realData
	 * @param axis0
	 * @param axis1
	 * @param otherPositions
	 * @return
	 */
	public static
		<CA extends Algebra<CA,C> & Addition<C> & Multiplication<C>,
			C extends SetComplex<R> & Allocatable<C>,
			RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
				Multiplication<R> & Addition<R> & Invertible<R> & Unity<R>,
			R>
	DimensionedDataSource<C> compute(CA complexAlg, RA realAlg, DimensionedDataSource<C> complexData)
	{
		if (complexData.numDimensions() != 2)
			throw new IllegalArgumentException("FFT2D input should be a single 2-d plane of complex values");
		
		C complexValue = complexAlg.construct();
		
		// calc some important variables
		
		long cols = complexData.dimension(0);
		
		long rows = complexData.dimension(1);
		
		long sz = FFT.enclosingPowerOf2(Math.max(rows, cols));

		// create power of two square planes for calculations
		
		DimensionedDataSource<C> inputPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		DimensionedDataSource<C> tmpPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		DimensionedDataSource<C> outputPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		// Copy the rectangular input image into the square plane defined for it.
		// The other (padded) values are zero.
		
		TwoDView<C> complexVw1 = new TwoDView<>(complexData);
		
		TwoDView<C> complexVw2 = new TwoDView<>(inputPlane);
		
		for (long y = 0; y < rows; y++) {

			for (long x = 0; x < cols; x++) {
			
				complexVw1.get(x, y, complexValue);
				
				complexVw2.set(x, y, complexValue);
			}
		}
		
		// for each column of input do a 1-d FFT and store as a column in temp data
		
		for (long c = 0; c < sz; c++) {
			
			// setup the input col to do FFT on
			
			IndexedDataSource<C> inCol = new SequencedDataSource<>(inputPlane.rawData(), c, sz, sz);
			
			// setup the tmp col to place FFT results in

			IndexedDataSource<C> tmpCol = new SequencedDataSource<>(tmpPlane.rawData(), c, sz, sz);
			
			// do the fft into from the input col into the tmp col
			
			FFT.compute(complexAlg, realAlg, inCol, tmpCol);
		}
		
		
		// for each row of temp data do a 1-d FFT and store as a row in output
		
		for (long r = 0; r < sz; r++) {
					
			// setup the tmp row to do FFT on

			IndexedDataSource<C> tmpRow = new SequencedDataSource<>(tmpPlane.rawData(), r*sz, 1, sz);
			
			// setup the output row to place FFT results in

			IndexedDataSource<C> outRow = new SequencedDataSource<>(outputPlane.rawData(), r*sz, 1, sz);
			
			// do the fft from the tmp row into the output row
			
			FFT.compute(complexAlg, realAlg, tmpRow, outRow);
		}
		
		return outputPlane;
	}
}
