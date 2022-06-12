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
package nom.bdezonia.zorbage.dataview;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
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

		// let's show two ways to grab a plane of data from a data source
		
		DimensionedDataSource<Float64Member> dataset3d =
				DimensionedStorage.allocate(G.DBL.construct(), new long[] {50, 40, 16});
		
		DimensionedDataSource<Float64Member> plane =
				DimensionedStorage.allocate(G.DBL.construct(), new long[] {50, 40});
		
		Float64Member value = G.DBL.construct();
		
		// grab a plane of data using views ------------------------------------------------
		
		ThreeDView<Float64Member> vw3 = new ThreeDView<>(dataset3d);
		
		TwoDView<Float64Member> vw2 = new TwoDView<>(plane);
		
		long planeNum = 7;
		
		for (long r = 0; r < vw3.d1(); r++) {
			for (long c = 0; c < vw3.d0(); c++) {
				vw3.get(c, r, planeNum, value);
				vw2.set(c, r, value);
			}
		}
		
		// alternate approach: use GridIterator --------------------------------------------
		
		long[] minPt = new long[dataset3d.numDimensions()];
		minPt[2] = planeNum;

		long[] maxPt = new long[dataset3d.numDimensions()];
		for (int i = 0; i < maxPt.length; i++) {
			maxPt[i] = dataset3d.dimension(i) - 1;
		}
		maxPt[2] = planeNum;

		SamplingIterator<IntegerIndex> iter = GridIterator.compute(minPt, maxPt);
		IntegerIndex threeDIdx = new IntegerIndex(dataset3d.numDimensions());
		IntegerIndex twoDIdx = new IntegerIndex(plane.numDimensions());
		
		while (iter.hasNext()) {
			iter.next(threeDIdx);
			dataset3d.get(threeDIdx, value);
			twoDIdx.set(0, threeDIdx.get(0));
			twoDIdx.set(1, threeDIdx.get(1));
			plane.set(twoDIdx, value);
		}
	}

	@Test
	public void test2() {
		
		// take a 5d 3 channel image and make a 4d rgb image
		//   Again we'll show two approaches

		// x = 50, y = 40, z planes = 15, channels = 3, time points = 33
		
		long[] dims5d = new long[] {50, 40, 15, 3, 33};
		
		// construct that 5d data set of uint8 data
		
		DimensionedDataSource<UnsignedInt8Member> ds5d =
			DimensionedStorage.allocate(G.UINT8.construct(), dims5d);

		// dims of a compatible color dataset
		
		long[] dims4d = new long[] {dims5d[0], dims5d[1], dims5d[2], dims5d[4]};
		
		// construct that 4d data set of rgb data
		
		DimensionedDataSource<RgbMember> colorDs =
			DimensionedStorage.allocate(G.RGB.construct(), dims4d);

		UnsignedInt8Member uint8 = G.UINT8.construct();

		RgbMember rgb = G.RGB.construct();

		// translate 3 channel data to rgb data using views --------------------------------
		
		// make a 5d view of original 3 channel data
		
		FiveDView<UnsignedInt8Member> vw5 = new FiveDView<>(ds5d);

		// make a 4d view of output rgb data
		
		FourDView<RgbMember> vw4 = new FourDView<>(colorDs);

		// copy all the pixel data
		
		for (long t = 0; t < vw5.d4(); t++) {
			for (long z = 0; z < vw5.d2(); z++) {
				for (long y = 0; y < vw5.d1(); y++) {
					for (long x = 0; x < vw5.d0(); x++) {
						
						// get channel 0
						vw5.get(x, y, z, 0, t, uint8);

						// save the value in a component of an rgb
						rgb.setR(uint8.v());
						
						// get channel 1
						vw5.get(x, y, z, 1, t, uint8);
						
						// save the value in a component of an rgb
						rgb.setG(uint8.v());
						
						// get channel 2
						vw5.get(x, y, z, 2, t, uint8);
						
						// save the value in a component of an rgb
						rgb.setB(uint8.v());
						
						// save the data into the 4d data set
						vw4.set(x, y, z, t, rgb);
					}
				}
			}			
		}
		
		// alternate approach: use GridIterator --------------------------------------------
		
		long[] minPt = new long[ds5d.numDimensions()];
		minPt[3] = 0; // we're going to iterate ignoring planes

		long[] maxPt = new long[ds5d.numDimensions()];
		for (int i = 0; i < maxPt.length; i++) {
			maxPt[i] = ds5d.dimension(i) - 1;
		}
		maxPt[3] = 0; // we're going to iterate ignoring planes

		SamplingIterator<IntegerIndex> iter = GridIterator.compute(minPt, maxPt);
		IntegerIndex fiveDIdx = new IntegerIndex(ds5d.numDimensions());
		IntegerIndex fourDIdx = new IntegerIndex(colorDs.numDimensions());
		
		while (iter.hasNext()) {
			iter.next(fiveDIdx); // visits al indices in 5d set but channel 0 only
			
			// grab channel 0 and store as part of rgb
			fiveDIdx.set(3, 0);
			ds5d.get(fiveDIdx, uint8);
			rgb.setR(uint8.v());
			
			// grab channel 1 and store as part of rgb
			fiveDIdx.set(3, 1);
			ds5d.get(fiveDIdx, uint8);
			rgb.setG(uint8.v());

			// grab channel 2 and store as part of rgb
			fiveDIdx.set(3, 2);
			ds5d.get(fiveDIdx, uint8);
			rgb.setB(uint8.v());
			
			// reset to channel 0 so iter.next() does not get confused
			fiveDIdx.set(3, 0);
			
			// set the 4d index values from the 5d coords ignoring the channel dim
			fourDIdx.set(0, fiveDIdx.get(0));
			fourDIdx.set(1, fiveDIdx.get(1));
			fourDIdx.set(2, fiveDIdx.get(2));
			fourDIdx.set(3, fiveDIdx.get(4));
			
			// store the value in the color ds
			colorDs.set(fourDIdx, rgb);
		}
	}
}
