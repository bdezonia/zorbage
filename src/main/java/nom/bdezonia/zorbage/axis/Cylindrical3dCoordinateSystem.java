/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.axis;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Cylindrical3dCoordinateSystem
	implements CoordinateSystem
{
	private final BigDecimal rUnit;
	private final BigDecimal thetaUnit;
	private final BigDecimal zUnit;

	/**
	 * 
	 * @param rUnit The spacing between r values.
	 * @param thetaUnit The spacing between theta values (in radians).
	 * @param maxZs The spacing between z values.
	 */
	public Cylindrical3dCoordinateSystem(BigDecimal rUnit, BigDecimal thetaUnit, BigDecimal zUnit)
	{
		this.rUnit = rUnit;
		this.thetaUnit = thetaUnit;
		this.zUnit = zUnit;
	}
	
	@Override
	public int numDimensions() {
		return 3;
	}

	@Override
	public BigDecimal coordinateValue(long[] coord, int axis) {
		if (axis < 0 || axis > 2)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return transform(rUnit, coord[0]);
		}
		else if (axis == 1) {
			return transform(thetaUnit, coord[1]);
		}
		else { // axis == 2
			return transform(zUnit, coord[2]);
		}
	}

	@Override
	public BigDecimal coordinateValue(IntegerIndex coord, int axis) {
		if (axis < 0 || axis > 2)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return transform(rUnit, coord.get(0));
		}
		else if (axis == 1) {
			return transform(thetaUnit, coord.get(1));
		}
		else { // axis == 2
			return transform(zUnit, coord.get(2));
		}
	}
	
	private BigDecimal transform(BigDecimal unit, long i) {
		return unit.multiply(BigDecimal.valueOf(i));
	}
}
