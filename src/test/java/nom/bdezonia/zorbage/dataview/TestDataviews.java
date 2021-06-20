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
package nom.bdezonia.zorbage.dataview;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.type.color.RgbMember;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestDataviews {

	@Test
	public void test1() {

		DimensionedDataSource<Float64Member> ds =
				DimensionedStorage.allocate(G.DBL.construct(), new long[] {50, 40, 16});
		
		ThreeDView<Float64Member> vw3 = new ThreeDView<Float64Member>(ds);
		
		DimensionedDataSource<Float64Member> plane =
				DimensionedStorage.allocate(G.DBL.construct(), new long[] {vw3.d0(), vw3.d1()});
		
		TwoDView<Float64Member> vw2 = new TwoDView<Float64Member>(plane);
		
		Float64Member value = G.DBL.construct();
		
		// grab a plane of data
		
		long planeNum = 7;
		
		for (long r = 0; r < vw3.d1(); r++) {
			for (long c = 0; c < vw3.d0(); c++) {
				vw3.get(c, r, planeNum, value);
				vw2.set(c, r, value);
			}
		}
		
	}

	@Test
	public void test2() {
		
		// take a 5d 3 channel image and make a 4d rgb image

		// x = 50, y = 40, z planes = 15, channels = 3, time points = 33
		
		long[] dims5d = new long[] {50, 40, 15, 3, 33};
		
		// construct that 5d data set of uint8 data
		
		DimensionedDataSource<UnsignedInt8Member> ds =
			DimensionedStorage.allocate(G.UINT8.construct(), dims5d);

		// make a useful accessor for it
		
		FiveDView<UnsignedInt8Member> vw5 = new FiveDView<>(ds);

		// dims of a compatible color dataset
		
		long[] dims4d = new long[] {dims5d[0], dims5d[1], dims5d[2], dims5d[4]};
		
		// construct that 4d data set of rgb data
		
		DimensionedDataSource<RgbMember> colorDs =
			DimensionedStorage.allocate(G.RGB.construct(), dims4d);

		// make a useful accessor for it
		
		FourDView<RgbMember> vw4 = new FourDView<>(colorDs);

		// copy all the pixel data
		
		UnsignedInt8Member num = G.UINT8.construct();
		RgbMember rgb = G.RGB.construct();

		for (long t = 0; t < vw5.d4(); t++) {
			for (long z = 0; z < vw5.d2(); z++) {
				for (long y = 0; y < vw5.d1(); y++) {
					for (long x = 0; x < vw5.d0(); x++) {
						
						// get channel 0
						vw5.get(x, y, z, 0, t, num);

						// save the value in a component of an rgb
						rgb.setR(num.v());
						
						// get channel 1
						vw5.get(x, y, z, 1, t, num);
						
						// save the value in a component of an rgb
						rgb.setG(num.v());
						
						// get channel 2
						vw5.get(x, y, z, 2, t, num);
						
						// save the value in a component of an rgb
						rgb.setB(num.v());
						
						// save the data into the 4d data set
						vw4.set(x, y, z, t, rgb);
					}
				}
			}			
		}
	}
}
