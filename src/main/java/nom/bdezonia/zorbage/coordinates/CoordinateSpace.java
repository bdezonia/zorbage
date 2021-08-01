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
package nom.bdezonia.zorbage.coordinates;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.DimensionCount;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public interface CoordinateSpace
	extends DimensionCount
{
	/**
	 * Project a single axis of a coordinate and return a projected space value.
	 * 
	 * @param coord A set of long valued coordinates within the coordinate space
	 * @param axis The desired axis (0 == x, 1 == y, 2 == z, etc.)
	 * @return The real coordinate value along the desired axis for the given coordinate
	 */
	BigDecimal project(long[] coord, int axis);

	/**
	 * Project a single axis of a coordinate and return a projected space value.
	 * 
	 * @param coord A set of long valued coordinates within the coordinate space
	 * @param axis The desired axis (0 == x, 1 == y, 2 == z, etc.)
	 * @return The real coordinate value along the desired axis for the given coordinate
	 */
	BigDecimal project(IntegerIndex coord, int axis);
	
	/**
	 * Project a whole coordinate point and set projected space coordinate values.
	 * Note that this method projects for all the dimensions in this CoordinateSpace.
	 * One can pass in an output point with more dimensions than this CoordinateSpace.
	 * 
	 * This is a convenience method for when you need all projected values at once.
	 * Individual implementers can design this method to speed up coordinate calculations
	 * when possible.
	 * 
	 * @param coord A set of long valued coordinates within the coordinate space
	 * @param output The output point that contains the coordinates after projection
	 */
	void project(long[] coord, BigDecimal[] output);
	
	/**
	 * Project a whole coordinate point and set projected space coordinate values.
	 * Note that this method projects for all the dimensions in this CoordinateSpace.
	 * One can pass in an output point with more dimensions than this CoordinateSpace.
	 * 
	 * This is a convenience method for when you need all projected values at once.
	 * Individual implementers can design this method to speed up coordinate calculations
	 * when possible.
	 * 
	 * @param coord A set of long valued coordinates within the coordinate space
	 * @param output The output point that contains the coordinates after projection
	 */
	void project(IntegerIndex coord, BigDecimal[] output);
	
	/**
	 * Set the decimal places of accuracy the coordinate transformation calculations
	 * will work in.
	 * 
	 * @param decimalPlaces
	 */
	void setPrecision(int decimalPlaces);
}
