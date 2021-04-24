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
			this.scales[i] = value(scales[i]);
			this.offsets[i] = value(offsets[i]);
		}
		this.context = new MathContext(20);
	}

	@Override
	public int numDimensions() {
		return scales.length;
	}

	@Override
	public BigDecimal toRn(long[] coord, int axis) {
		return BigDecimal.valueOf(coord[axis]).multiply(scales[axis], context).add(offsets[axis], context);
	}

	@Override
	public BigDecimal toRn(IntegerIndex coord, int axis) {
		return BigDecimal.valueOf(coord.get(axis)).multiply(scales[axis], context).add(offsets[axis], context);
	}

	public void setPrecision(int precision) {
		this.context = new MathContext(precision);
	}

	public BigDecimal getScale(int axis) {
		return scales[axis];
	}

	public BigDecimal getOffset(int axis) {
		return offsets[axis];
	}
	
	private BigDecimal value(BigDecimal v) {
		if (v == null)
			return BigDecimal.ZERO;
		return v;
	}
}
