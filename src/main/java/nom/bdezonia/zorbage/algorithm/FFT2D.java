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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.SequencedDataSource;
import nom.bdezonia.zorbage.dataview.PlaneView;
import nom.bdezonia.zorbage.dataview.TwoDView;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FFT2D {

	// do not instantiate
	
	private FFT2D() { }

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param <V>
	 * @param <W>
	 * @param cmplxAlg
	 * @param realAlg
	 * @param realData
	 * @param axis0
	 * @param axis1
	 * @param otherPositions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static
		<T extends Algebra<T,U> & Addition<U> & Multiplication<U>,
			U extends SetComplex<W>,
			V extends Algebra<V,W> & Trigonometric<W> & RealConstants<W> &
				Multiplication<W> & Addition<W> & Invertible<W> & Unity<W>,
			W>
	DimensionedDataSource<U> compute(T cmplxAlg, V realAlg, DimensionedDataSource<W> realData, int axis0, int axis1, IntegerIndex otherPositions)
	{
		U complexValue = cmplxAlg.construct();
		
		W realValue = realAlg.construct();

		W zero = realAlg.construct();

		// grab the plane of data the user is interested in
		
		PlaneView<W> view = new PlaneView<W>(realData, axis0, axis1);
		
		for (int i = 0; i < otherPositions.numDimensions(); i++) {
			view.setPositionValue(i, otherPositions.get(i));
		}
		
		DimensionedDataSource<W> theData = view.copyPlane(realValue);
		
		// calc some important variables
		
		long cols = theData.dimension(0);
		
		long rows = theData.dimension(1);
		
		long sz = FFT.enclosingPowerOf2(Math.max(rows, cols));

		// create power of two square planes for calculations
		
		DimensionedDataSource<U> inputPlane = DimensionedStorage.allocate((Allocatable) complexValue, new long[] {sz,sz});

		DimensionedDataSource<U> tmpPlane = DimensionedStorage.allocate((Allocatable) complexValue, new long[] {sz,sz});

		DimensionedDataSource<U> outputPlane = DimensionedStorage.allocate((Allocatable) complexValue, new long[] {sz,sz});

		// Copy the rectangular input image into the square plane defined for it.
		// The other (padded) values are zero.
		
		TwoDView<W> realVw = new TwoDView<>(theData);
		
		TwoDView<U> complexVw = new TwoDView<>(inputPlane);
		
		for (long y = 0; y < rows; y++) {
			for (long x = 0; x < cols; x++) {
				realVw.get(x, y, realValue);
				complexValue.setR(realValue);
				complexValue.setI(zero);
				complexVw.set(x, y, complexValue);
			}
		}
		
		// for each column of input do a 1-d FFT and store as a column in temp data
		
		for (long c = 0; c < sz; c++) {
			
			// setup the padded input piped to do FFT on
			
			IndexedDataSource<U> inCol = new SequencedDataSource<>(inputPlane.rawData(), c, sz, sz);
			
			IndexedDataSource<U> inPiped = new FixedSizeZeroPaddedDataSource<T,U>(cmplxAlg, inCol, sz);
			
			// setup the padded tmp piped to place FFT results in

			IndexedDataSource<U> tmpCol = new SequencedDataSource<>(tmpPlane.rawData(), c, sz, sz);
			
			// do the fft into output col
			
			FFT.compute(cmplxAlg, realAlg, inPiped, tmpCol);
		}
		
		
		// for each row of temp data do a 1-d FFT and store as a row in output
		
		for (long r = 0; r < sz; r++) {
					
			// setup the padded tmp piped to do FFT on

			IndexedDataSource<U> tmpRow = new SequencedDataSource<>(tmpPlane.rawData(), r*sz, 1, sz);
			
			IndexedDataSource<U> tmpPiped = new FixedSizeZeroPaddedDataSource<T,U>(cmplxAlg, tmpRow, sz);
			
			// setup the padded output piped to place FFT results in

			IndexedDataSource<U> outRow = new SequencedDataSource<>(outputPlane.rawData(), r*sz, 1, sz);
			
			// do the fft into output row
			
			FFT.compute(cmplxAlg, realAlg, tmpPiped, outRow);
		}
		
		return outputPlane;
	}

	private static class FixedSizeZeroPaddedDataSource<M extends Algebra<M,N>, N>
		implements IndexedDataSource<N>
	{
		final M alg;
		final IndexedDataSource<N> data;
		final long paddedDataSize;
		final long dataSize;
		
		FixedSizeZeroPaddedDataSource(M alg, IndexedDataSource<N> ds, long powerOfTwoLimit) {
			
			if (powerOfTwoLimit <= 0)
				throw new IllegalArgumentException("power of two limit must b 1 or greater");

			if (powerOfTwoLimit < ds.size())
				throw new IllegalArgumentException("size of source not contained by size limit of piped");
			
			if (FFT.enclosingPowerOf2(powerOfTwoLimit) != powerOfTwoLimit)
				throw new IllegalArgumentException("Provided powerOfTwoLimit is not a power of two");
			
			this.alg = alg;

			this.data = ds;
			
			this.paddedDataSize = powerOfTwoLimit;
			
			this.dataSize = data.size();
		}

		@Override
		public IndexedDataSource<N> duplicate() {

			return new FixedSizeZeroPaddedDataSource<>(alg, data, paddedDataSize);
		}

		@Override
		public StorageConstruction storageType() {

			return data.storageType();
		}

		@Override
		public void set(long index, N value) {

			if (index >= 0 && index < dataSize) {
			
				data.set(index, value);
			}
		}

		@Override
		public void get(long index, N value) {
			
			if (index >= 0 && index < dataSize) {
			
				data.get(index, value);
			}
			else {
			
				alg.zero().call(value);
			}
		}

		@Override
		public long size() {

			return paddedDataSize;
		}

		@Override
		public boolean accessWithOneThread() {

			return data.accessWithOneThread();
		}
	}
}
