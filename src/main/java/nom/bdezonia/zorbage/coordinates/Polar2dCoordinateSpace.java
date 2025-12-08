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
package nom.bdezonia.zorbage.coordinates;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Polar2dCoordinateSpace
	implements CoordinateSpace
{
	private final BigDecimal rUnit;
	private final BigDecimal thetaUnit;
	private MathContext context;

	/**
	 * 
	 * @param rUnit The spacing between r values.
	 * @param thetaUnit The spacing between theta values (in radians).
	 */
	public Polar2dCoordinateSpace(BigDecimal rUnit, BigDecimal thetaUnit)
	{
		this.rUnit = rUnit;
		this.thetaUnit = thetaUnit;
		this.context = new MathContext(20);
	}
	
	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public BigDecimal project(long[] coord, int axis) {
		if (axis < 0 || axis > 1)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return x(coord[0], coord[1]);
		}
		else { // axis == 1
			return y(coord[0], coord[1]);
		}
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		if (axis < 0 || axis > 1)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return x(coord.get(0), coord.get(1));
		}
		else { // axis == 1
			return y(coord.get(0), coord.get(1));
		}
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
	
	private BigDecimal x(long r, long th) {
		BigDecimal rVal = rUnit.multiply(BigDecimal.valueOf(r), context);
		BigDecimal thetaVal = thetaUnit.multiply(BigDecimal.valueOf(th), context);
		return rVal.multiply(BigDecimalMath.cos(thetaVal, context), context);
	}
	
	private BigDecimal y(long r, long th) {
		BigDecimal rVal = rUnit.multiply(BigDecimal.valueOf(r), context);
		BigDecimal thetaVal = thetaUnit.multiply(BigDecimal.valueOf(th), context);
		return rVal.multiply(BigDecimalMath.sin(thetaVal, context), context);
	}
}
