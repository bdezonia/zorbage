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
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.SequencedDataSource;
import nom.bdezonia.zorbage.dataview.ThreeDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FFT3D {


	// do not instantiate
	
	private FFT3D() { }

	/**
	 * Do a 3-dimensional Fourier transform of 1 block of a complex DataSource
	 * 
	 * @param <CA>
	 * @param <C>
	 * @param <RA>
	 * @param <R>
	 * @param complexAlg
	 * @param realAlg
	 * @param inputData
	 * @return
	 */
	public static
	
		<CA extends Algebra<CA,C> & Addition<C> & Multiplication<C>,
		
			C extends  Allocatable<C> & SetR<R> & SetI<R>,
			
			RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
						Multiplication<R> & Addition<R> & Invertible<R> &
						Unity<R>,
				
			R>
	
		DimensionedDataSource<C>
			
			compute(CA complexAlg,
					RA realAlg,
					DimensionedDataSource<C> inputData)
	{
		if (inputData.numDimensions() != 3)
			throw new IllegalArgumentException("FFT3D input data should be a single 3-d block of complex values");
		
		C complexValue = complexAlg.construct();

		// calc some important variables
		
		long cols = inputData.dimension(0);
		
		long rows = inputData.dimension(1);
		
		long planes = inputData.dimension(2);
		
		long maxDim = Math.max(rows, cols);
		
		maxDim = Math.max(maxDim,  planes);
		
		long sz = FFT.enclosingPowerOf2(maxDim);

		long[] cubeDims = new long[] {sz,sz,sz};
		
		// create power of two cube planes for calculations
		
		DimensionedDataSource<C> complexData =
				DimensionedStorage.allocate(complexValue, cubeDims);

		DimensionedDataSource<C> tmpCube1 =
				DimensionedStorage.allocate(complexValue, cubeDims);

		DimensionedDataSource<C> tmpCube2 =
				DimensionedStorage.allocate(complexValue, cubeDims);

		DimensionedDataSource<C> outputCube =
				DimensionedStorage.allocate(complexValue, cubeDims);

		// Copy the hyperrectangular input image into the square edges cube defined
		// for it. The values outside the rectangular region will be zero (padding).
		
		ThreeDView<C> readView = new ThreeDView<>(inputData);
		
		ThreeDView<C> writeView = new ThreeDView<>(complexData);
		
		for (long y = 0; y < rows; y++) {

			for (long x = 0; x < cols; x++) {
			
				for (long z = 0; z < planes; z++) {
					
					readView.get(x, y, z, complexValue);
					
					writeView.set(x, y, z, complexValue);
				}
			}
		}
		
		// for each plane
		
		for (long z = 0; z < sz; z++) {
			
			// for each row
			
			for (long y = 0; y < sz; y++) {
			
				// calc the column piped FFTs

				// setup the input col to do FFT on
				
				IndexedDataSource<C> inCol = new SequencedDataSource<>(complexData.rawData(), z*sz*sz + y*sz, 1, sz);

				// setup the tmp col to place FFT results in

				IndexedDataSource<C> outCol = new SequencedDataSource<>(tmpCube1.rawData(), z*sz*sz + y*sz, 1, sz);

				// do the fft into from the in col into the out col
				
				FFT.compute(complexAlg, realAlg, inCol, outCol);
			}
		}
		
		// for each plane
		
		for (long z = 0; z < sz; z++) {
			
			// for each col
			
			for (long x = 0; x < sz; x++) {
				
				// calc the row piped FFTs

				// setup the input row to do FFT on
				
				IndexedDataSource<C> inRow = new SequencedDataSource<>(tmpCube1.rawData(), z*sz*sz + x, sz, sz);

				// setup the out row to place FFT results in

				IndexedDataSource<C> outRow = new SequencedDataSource<>(tmpCube2.rawData(), z*sz*sz + x, sz, sz);

				// do the fft into from the in row into the out row
				
				FFT.compute(complexAlg, realAlg, inRow, outRow);
			}
		}
		
		// for each row
		
		for (long y = 0; y < sz; y++) {
			
			// for each col
			
			for (long x = 0; x < sz; x++) {
				
				// calc the plane piped FFTs

				// setup the input piped to do FFT on
				
				IndexedDataSource<C> inPiped = new SequencedDataSource<>(tmpCube2.rawData(), y*sz + x, sz*sz, sz);

				// setup the out piped to place FFT results in

				IndexedDataSource<C> outPiped = new SequencedDataSource<>(outputCube.rawData(), y*sz + x, sz*sz, sz);

				// do the fft into from the in piped into the out piped
				
				FFT.compute(complexAlg, realAlg, inPiped, outPiped);
			}			
		}
		
		return outputCube;
	}

}
