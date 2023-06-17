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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.dataview.TwoDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SwapQuadrants {

	// do not instantiate
	
	private SwapQuadrants() { }
	

	// TODO: make this more like Matlab's fftshift(). That routine can handle
	//   odd sized images and shifts a little unevenly. Read more about it,
	//   fix this code, and maybe even rename it to FFTShift.
	
	/**
	 * Swaps quadrants of a 2d data source. This is an in place operation.
	 * The lower left and upper right quadrants are exchanged. So are the upper left
	 * and lower right. This operation can be useful for dealing with 2d FFT data.
	 * 
	 * @param alg The algebra that is associated with the type of data source being passed in.
	 * @param data The data source to swap in place
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, DimensionedDataSource<U> data)
	{
		if (data.numDimensions() != 2)
			throw new IllegalArgumentException("Swap quadrant algorithm only works with 2D data.");
		
		if (data.dimension(0) % 2 != 0 || data.dimension(1) % 2 != 0)
			throw new IllegalArgumentException("Swap quadrant algorithm only works with images of even sized X and Y dimensions");
		
		U tmp1 = alg.construct();

		U tmp2 = alg.construct();
		
		TwoDView<U> vw = new TwoDView<U>(data);

		long xQuadSize = vw.d0() / 2;

		long yQuadSize = vw.d1() / 2;

		// swap ul and lr
		
		for (long y = 0; y < yQuadSize; y++) {
			for (long x = 0; x < xQuadSize; x++) {
				vw.get(x, y, tmp1);
				vw.get(x+xQuadSize, y+yQuadSize, tmp2);
				vw.set(x+xQuadSize, y+yQuadSize, tmp1);
				vw.set(x, y, tmp2);
			}
		}
	
		// swap ur and ll

		for (long y = 0; y < yQuadSize; y++) {
			for (long x = xQuadSize; x < vw.d0(); x++) {
				vw.get(x, y, tmp1);
				vw.get(x-xQuadSize, y+yQuadSize, tmp2);
				vw.set(x-xQuadSize, y+yQuadSize, tmp1);
				vw.set(x, y, tmp2);
			}
		}
	}
}
