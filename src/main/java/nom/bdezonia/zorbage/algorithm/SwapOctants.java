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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.dataview.ThreeDView;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SwapOctants {

	// do not instantiate
	
	private SwapOctants() { }
	

	// TODO: make this more like Matlab's fftshift(). That routine can handle
	//   odd sized images and shifts a little unevenly. Read more about it,
	//   fix this code, and maybe even rename it to FFTShift.
	
	/**
	 * Swaps octant of a 3d data source. This is an in place operation.
	 * This algorithm can be useful for dealing with 3d FFT data.
	 * 
	 * @param alg The algebra that is associated with the type of data source being passed in.
	 * @param data The data source to swap in place
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, DimensionedDataSource<U> data)
	{
		if (data.numDimensions() != 3)
			throw new IllegalArgumentException("Swap octant algorithm only works with 3D data.");
		
		if (data.dimension(0) % 2 != 0 || data.dimension(1) % 2 != 0 || data.dimension(2) % 2 != 0)
			throw new IllegalArgumentException("Swap quadrant algorithm only works with images of even sized dimensions");
		
		U tmp1 = alg.construct();

		U tmp2 = alg.construct();
		
		ThreeDView<U> vw = new ThreeDView<U>(data);

		long xOctSize = vw.d0() / 2;

		long yOctSize = vw.d1() / 2;

		long zOctSize = vw.d2() / 2;

		// swap around two octants thru center
		
		for (long z = 0; z < zOctSize; z++) {
			for (long y = 0; y < yOctSize; y++) {
				for (long x = 0; x < xOctSize; x++) {
					vw.get(x,          y,          z,          tmp1);
					vw.get(x+xOctSize, y+yOctSize, z+zOctSize, tmp2);
					vw.set(x+xOctSize, y+yOctSize, z+zOctSize, tmp1);
					vw.set(x,          y,          z,          tmp2);
				}
			}
		}

		// swap around two octants thru center
		
		for (long z = 0; z < zOctSize; z++) {
			for (long y = 0; y < yOctSize; y++) {
				for (long x = 0; x < xOctSize; x++) {
					vw.get(x,          y+yOctSize, z,          tmp1);
					vw.get(x+xOctSize, y,          z+zOctSize, tmp2);
					vw.set(x+xOctSize, y,          z+zOctSize, tmp1);
					vw.set(x,          y+yOctSize, z,          tmp2);
				}
			}
		}

		// swap around two octants thru center
		
		for (long z = 0; z < zOctSize; z++) {
			for (long y = 0; y < yOctSize; y++) {
				for (long x = 0; x < xOctSize; x++) {
					vw.get(x+xOctSize, y+yOctSize, z,          tmp1);
					vw.get(x,          y,          z+zOctSize, tmp2);
					vw.set(x,          y,          z+zOctSize, tmp1);
					vw.set(x+xOctSize, y+yOctSize, z,          tmp2);
				}
			}
		}

		// swap around two octants thru center
		
		for (long z = 0; z < zOctSize; z++) {
			for (long y = 0; y < yOctSize; y++) {
				for (long x = 0; x < xOctSize; x++) {
					vw.get(x+xOctSize, y,          z,          tmp1);
					vw.get(x,          y+yOctSize, z+zOctSize, tmp2);
					vw.set(x,          y+yOctSize, z+zOctSize, tmp1);
					vw.set(x+xOctSize, y,          z,          tmp2);
				}
			}
		}
		
		// at this point all 8 octants have been swapped
	}
}
