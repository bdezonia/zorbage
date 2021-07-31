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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.coordinates.Affine3dCoordinateSpace;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.coordinates.IdentityCoordinateSpace;
import nom.bdezonia.zorbage.coordinates.LinearNdCoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.color.ArgbMember;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MakeColorDatasource {
	
	// do not instantiate
	
	private MakeColorDatasource() { }
	
	/**
	 * 
	 * @param inputImage
	 * @param channelDimension
	 * @return
	 */
	public static 
		DimensionedDataSource<ArgbMember> compute(DimensionedDataSource<UnsignedInt8Member> inputImage, int channelDimension)
	{
		
		if (channelDimension < 0 || channelDimension >= inputImage.numDimensions())
			return null;
			//throw new IllegalArgumentException("channel dimension out of bounds");
		
		long numChannels = inputImage.dimension(channelDimension);
		
		if (numChannels < 3 || numChannels > 4)
			return null;
			//throw new IllegalArgumentException("num channels must be 3 or 4");
		
		long[] newDims = new long[inputImage.numDimensions() - 1];
		int idx = 0;
		for (int i = 0; i < inputImage.numDimensions(); i++) {
			if (i == channelDimension) continue;
			newDims[idx++] = inputImage.dimension(i);
		}
		
		DimensionedDataSource<ArgbMember> newData = DimensionedStorage.allocate(G.ARGB.construct(), newDims);
		
		IntegerIndex idx1 = new IntegerIndex(newData.numDimensions());
		IntegerIndex idx2 = new IntegerIndex(inputImage.numDimensions());

		UnsignedInt8Member channelVal = G.UINT8.construct();
		ArgbMember compositeVal = G.ARGB.construct();
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(newData);
		
		while (iter.hasNext()) {
			iter.next(idx1);
			int count = 0;
			for (int i = 0; i < idx2.numDimensions(); i++) {
				if (i == channelDimension) continue;
				idx2.set(i, idx1.get(count));
				count++;
			}
			for (int i = 0; i < numChannels; i++) {
				idx2.set(channelDimension, i);
				inputImage.get(idx2, channelVal);
				if (numChannels == 3) {
					compositeVal.setA(255);
					if (i == 0)
						compositeVal.setR(channelVal.v());
					else if (i == 1)
						compositeVal.setG(channelVal.v());
					else if (i == 2)
						compositeVal.setB(channelVal.v());
				}
				else {  // numChannels == 4
					if (i == 0)
						compositeVal.setA(channelVal.v());
					else if (i == 1)
						compositeVal.setR(channelVal.v());
					else if (i == 2)
						compositeVal.setG(channelVal.v());
					else if (i == 3)
						compositeVal.setB(channelVal.v());
				}
			}
			newData.set(idx1, compositeVal);
		}

		CoordinateSpace coords = inputImage.getCoordinateSpace();
		
		if (coords != null) {
			if (coords instanceof LinearNdCoordinateSpace) {
				LinearNdCoordinateSpace linSpace = (LinearNdCoordinateSpace) coords;
				newData.setCoordinateSpace(linSpace.reduce(channelDimension));
			}
			if (coords instanceof Affine3dCoordinateSpace) {
				Affine3dCoordinateSpace affSpace = (Affine3dCoordinateSpace) coords;
				newData.setCoordinateSpace(affSpace.reduce(channelDimension));
			}
			if (coords instanceof IdentityCoordinateSpace) {
				IdentityCoordinateSpace identitySpace = (IdentityCoordinateSpace) coords;
				newData.setCoordinateSpace(identitySpace.reduce(channelDimension));
			}
		}
		
		newData.setName("Color conversion of "+inputImage.getName());
		newData.setSource(inputImage.getSource());
		newData.setValueType(inputImage.getValueType());
		newData.setValueUnit(inputImage.getValueUnit());
		int count = 0;
		for (int i = 0; i < inputImage.numDimensions(); i++) {
			if (i == channelDimension) continue;
			newData.setAxisType(count, inputImage.getAxisType(i));
			newData.setAxisUnit(count, inputImage.getAxisUnit(i));
			count++;
		}
		
		return newData;
	}
}
