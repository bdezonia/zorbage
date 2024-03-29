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

import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IdentityCoordinateSpace
	implements CoordinateSpace
{
	private final int numDims;
	
	public IdentityCoordinateSpace(int numDims) {
		if (numDims < 0)
			throw new IllegalArgumentException("coordinate system dimensionality must be >= 0");
		this.numDims = numDims;
	}
	
	@Override
	public int numDimensions() {
		return numDims;
	}

	@Override
	public BigDecimal project(long[] coord, int axis) {
		return BigDecimal.valueOf(coord[axis]);
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		return BigDecimal.valueOf(coord.get(axis));
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
	public void setPrecision(int decimalPlaces) {
		// nothing to do here
	}

	public IdentityCoordinateSpace reduce(int coordToDrop) {

		if (coordToDrop < 0 || coordToDrop >= numDimensions())
			throw new IllegalArgumentException("coordinate out of bounds");
		
		return new IdentityCoordinateSpace(numDimensions() - 1);
	}
}
