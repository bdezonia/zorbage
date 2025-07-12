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
package nom.bdezonia.zorbage.coordinates;

import java.math.BigDecimal;
import java.math.MathContext;

import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LinearNdCoordinateSpace
	implements CoordinateSpace
{
	private final BigDecimal[] scales;
	private final BigDecimal[] offsets;
	private MathContext context;
	
	public LinearNdCoordinateSpace(BigDecimal[] scales, BigDecimal[] offsets) {
		if (scales.length != offsets.length)
			throw new IllegalArgumentException("inconsistent definition of a linear coord system");
		this.scales = new BigDecimal[scales.length];
		this.offsets = new BigDecimal[offsets.length];
		for (int i = 0; i < scales.length; i++) {
			this.scales[i] = BigDecimalUtils.value(scales[i]);
			this.offsets[i] = BigDecimalUtils.value(offsets[i]);
		}
		this.context = new MathContext(20);
	}

	@Override
	public int numDimensions() {
		return scales.length;
	}

	@Override
	public BigDecimal project(long[] coord, int axis) {
		return BigDecimal.valueOf(coord[axis]).multiply(scales[axis], context).add(offsets[axis], context);
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		return BigDecimal.valueOf(coord.get(axis)).multiply(scales[axis], context).add(offsets[axis], context);
	}

	@Override
	public void project(long[] coord, BigDecimal[] output) {
		
		for (int i = 0; i < numDimensions(); i++) {
			output[i] = project(coord, i);
		}
	}

	@Override
	public void project(IntegerIndex coord, BigDecimal[] output) {
		
		for (int i = 0; i < numDimensions(); i++) {
			output[i] = project(coord, i);
		}
	}

	@Override
	public void setPrecision(int precision) {
		this.context = new MathContext(precision);
	}

	public BigDecimal getScale(int axis) {
		return scales[axis];
	}

	public BigDecimal getOffset(int axis) {
		return offsets[axis];
	}
	
	public LinearNdCoordinateSpace reduce(int coordToDrop) {

		if (coordToDrop < 0 || coordToDrop >= numDimensions())
			throw new IllegalArgumentException("coordinate out of bounds");
		
		BigDecimal[] scales = new BigDecimal[numDimensions()-1];
		BigDecimal[] offsets = new BigDecimal[numDimensions()-1];
		int count = 0;
		for (int i = 0; i < numDimensions(); i++) {
			if (i == coordToDrop) continue;
			scales[count] = getScale(i);
			offsets[count] = getOffset(i);
			count++;
		}
		
		return new LinearNdCoordinateSpace(scales, offsets);
	}

	/**
	 * Translate a linear nd space to another one whose origin
	 * is in a different corner of the data.
	 * 
	 * @param dims
	 * @param currOriginInfo
	 * @param futureOriginInfo
	 * @param origSpace
	 * @return
	 */
	public static
		
		LinearNdCoordinateSpace
		
			translate(long[] dims, boolean[] currOriginInfo, boolean[] futureOriginInfo, LinearNdCoordinateSpace origSpace)
	{
		if (dims.length != currOriginInfo.length ||
				dims.length != futureOriginInfo.length ||
				dims.length != origSpace.numDimensions())
			throw new IllegalArgumentException("shift() has arguments of mismatching dimensionality");
		
		BigDecimal[] newOrigin = new BigDecimal[dims.length];
		BigDecimal[] newSpacings = new BigDecimal[dims.length];
		
		for (int d = 0; d < dims.length; d++) {
		
			if (currOriginInfo[d] == futureOriginInfo[d]) {
				
				newOrigin[d] = origSpace.getOffset(d);
				newSpacings[d] = origSpace.getScale(d);
			}
			else {

				long maxD = dims[d] - 1;				
				newOrigin[d] = origSpace.getOffset(d).add(origSpace.getScale(d).multiply(BigDecimal.valueOf(maxD)));
				newSpacings[d] = origSpace.getScale(d).negate();
			}
		}
		
		return new	

			LinearNdCoordinateSpace(newSpacings, newOrigin);
	}
}
