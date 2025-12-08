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

import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Affine2dCoordinateSpace
	implements CoordinateSpace
{
	private final BigDecimal x0;
	private final BigDecimal x1;
	private final BigDecimal x2;
	private final BigDecimal y0;
	private final BigDecimal y1;
	private final BigDecimal y2;
	private MathContext context;
	
	/**
	 * 
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param y0
	 * @param y1
	 * @param y2
	 */
	public Affine2dCoordinateSpace(
			BigDecimal x0, BigDecimal x1, BigDecimal x2,
			BigDecimal y0, BigDecimal y1, BigDecimal y2)
	{
		this.x0 = BigDecimalUtils.value(x0);
		this.x1 = BigDecimalUtils.value(x1);
		this.x2 = BigDecimalUtils.value(x2);
		this.y0 = BigDecimalUtils.value(y0);
		this.y1 = BigDecimalUtils.value(y1);
		this.y2 = BigDecimalUtils.value(y2);
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
			return transform(coord[0], coord[1], x0, x1, x2);
		}
		else { // axis == 1
			return transform(coord[0], coord[1], y0, y1, y2);
		}
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		if (axis < 0 || axis > 1)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return transform(coord.get(0), coord.get(1), x0, x1, x2);
		}
		else { // axis == 1)
			return transform(coord.get(0), coord.get(1), y0, y1, y2);
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

	private BigDecimal transform(long i, long j, BigDecimal t0, BigDecimal t1, BigDecimal t2) {
		BigDecimal tmp = BigDecimal.valueOf(i).multiply(t0, context);
		tmp = tmp.add(BigDecimal.valueOf(j).multiply(t1, context), context);
		return tmp.add(t2, context);
	}
}
