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
import java.math.MathContext;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Spherical3dCoordinateSystem
	implements CoordinateSystem
{
	private final BigDecimal rhoUnit;
	private final BigDecimal thetaUnit;
	private final BigDecimal phiUnit;
	private MathContext context;

	/**
	 * 
	 * @param rhoUnit The spacing between rho values.
	 * @param thetaUnit The spacing between theta values (in radians).
	 * @param phiUnit The spacing between phi values (in radians).
	 */
	public Spherical3dCoordinateSystem(BigDecimal rhoUnit, BigDecimal thetaUnit, BigDecimal phiUnit)
	{
		this.rhoUnit = rhoUnit;
		this.thetaUnit = thetaUnit;
		this.phiUnit = phiUnit;
		this.context = new MathContext(20);
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
			return x(coord[0], coord[1], coord[2]);
		}
		else if (axis == 1) {
			return y(coord[0], coord[1], coord[2]);
		}
		else { // axis == 2
			return z(coord[0], coord[2]);
		}
	}

	@Override
	public BigDecimal coordinateValue(IntegerIndex coord, int axis) {
		if (axis < 0 || axis > 2)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return x(coord.get(0), coord.get(1), coord.get(2));
		}
		else if (axis == 1) {
			return y(coord.get(0), coord.get(1), coord.get(2));
		}
		else { // axis == 2
			return z(coord.get(0), coord.get(2));
		}
	}

	public void setPrecision(int precision) {
		this.context = new MathContext(precision);
	}
	
	private BigDecimal x(long rh, long th, long ph) {
		BigDecimal rhoVal = rhoUnit.multiply(BigDecimal.valueOf(rh), context);
		BigDecimal thVal = thetaUnit.multiply(BigDecimal.valueOf(th), context);
		BigDecimal phiVal = phiUnit.multiply(BigDecimal.valueOf(ph), context);
		BigDecimal tmp = rhoVal.multiply(BigDecimalMath.cos(thVal, context));
		return tmp.multiply(BigDecimalMath.sin(phiVal, context));
	}
	
	private BigDecimal y(long rh, long th, long ph) {
		BigDecimal rhoVal = rhoUnit.multiply(BigDecimal.valueOf(rh), context);
		BigDecimal thVal = thetaUnit.multiply(BigDecimal.valueOf(th), context);
		BigDecimal phiVal = phiUnit.multiply(BigDecimal.valueOf(ph), context);
		BigDecimal tmp = rhoVal.multiply(BigDecimalMath.sin(thVal, context));
		return tmp.multiply(BigDecimalMath.sin(phiVal, context));
	}
	
	private BigDecimal z(long rh, long ph) {
		BigDecimal rhoVal = rhoUnit.multiply(BigDecimal.valueOf(rh), context);
		BigDecimal phiVal = phiUnit.multiply(BigDecimal.valueOf(ph), context);
		return rhoVal.multiply(BigDecimalMath.cos(phiVal, context));
	}
}
