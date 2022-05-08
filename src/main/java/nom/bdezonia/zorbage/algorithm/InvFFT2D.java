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
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.FFTDataSource;
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
	 * Do a 2-dimensional inverse fourier transform of 1 plane of a complex DataSource
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <V>
	 * @param <W>
	 * @param cmplxAlg
	 * @param realAlg
	 * @param cmplxData
	 * @param axis0
	 * @param axis1
	 * @param otherPositions
	 * @return
	 */
	public static
		<T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Conjugate<U>,
			U extends SetComplex<W> & Allocatable<U>,
			V extends Algebra<V,W> & Trigonometric<W> & RealConstants<W> & Unity<W> &
						Multiplication<W> & Addition<W> & Invertible<W>,
			W>
	DimensionedDataSource<U> compute(T complexAlg, V realAlg, DimensionedDataSource<U> complexData)
	{
		if (complexData.numDimensions() != 2)
			throw new IllegalArgumentException("InvFFT2D input should be a single 2-d plane of complex values");
		
		U complexValue = complexAlg.construct();
		
		// calc some important variables
		
		long cols = complexData.dimension(0);
		
		long rows = complexData.dimension(1);
		
		long sz = FFT.enclosingPowerOf2(Math.max(rows, cols));

		// create power of two square planes for calculations
		
		DimensionedDataSource<U> inputPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		DimensionedDataSource<U> tmpPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		DimensionedDataSource<U> outputPlane = DimensionedStorage.allocate(complexValue, new long[] {sz,sz});

		// Copy the rectangular input image into the square plane defined for it.
		// The other (padded) values are zero.
		
		TwoDView<U> complexVw1 = new TwoDView<>(complexData);
		
		TwoDView<U> complexVw2 = new TwoDView<>(inputPlane);
		
		for (long y = 0; y < rows; y++) {
			for (long x = 0; x < cols; x++) {
				complexVw1.get(x, y, complexValue);
				complexVw2.set(x, y, complexValue);
			}
		}
		
		// for each column of input do a 1-d FFT and store as a column in temp data
		
		for (long c = 0; c < sz; c++) {
			
			// setup the padded input piped to do FFT on
			
			IndexedDataSource<U> inCol = new SequencedDataSource<>(inputPlane.rawData(), c, sz, sz);
			
			IndexedDataSource<U> inPiped = new FFTDataSource<T,U>(complexAlg, inCol, sz);
			
			// setup the padded tmp piped to place FFT results in

			IndexedDataSource<U> tmpCol = new SequencedDataSource<>(tmpPlane.rawData(), c, sz, sz);
			
			// do the inv fft into output col
			
			InvFFT.compute(complexAlg, realAlg, inPiped, tmpCol);
		}
		
		// for each row of temp data do a 1-d FFT and store as a row in output
		
		for (long r = 0; r < sz; r++) {
					
			// setup the padded tmp piped to do FFT on

			IndexedDataSource<U> tmpRow = new SequencedDataSource<>(tmpPlane.rawData(), r*sz, 1, sz);
			
			IndexedDataSource<U> tmpPiped = new FFTDataSource<T,U>(complexAlg, tmpRow, sz);
			
			// setup the padded output piped to place FFT results in

			IndexedDataSource<U> outRow = new SequencedDataSource<>(outputPlane.rawData(), r*sz, 1, sz);
			
			// do the inv fft into output row
			
			InvFFT.compute(complexAlg, realAlg, tmpPiped, outRow);
		}
		
		return outputPlane;
	}
}
