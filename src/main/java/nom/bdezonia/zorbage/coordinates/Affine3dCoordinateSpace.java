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
import java.math.MathContext;

import nom.bdezonia.zorbage.sampling.IntegerIndex;

// Note: this class inspired by Nifti's header docs on their affine coord xform

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Affine3dCoordinateSpace
	implements CoordinateSpace
{
	private final BigDecimal x0;
	private final BigDecimal x1;
	private final BigDecimal x2;
	private final BigDecimal x3;
	private final BigDecimal y0;
	private final BigDecimal y1;
	private final BigDecimal y2;
	private final BigDecimal y3;
	private final BigDecimal z0;
	private final BigDecimal z1;
	private final BigDecimal z2;
	private final BigDecimal z3;
	private MathContext context;

	/**
	 * 
	 * @param x0
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param y0
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param z0
	 * @param z1
	 * @param z2
	 * @param z3
	 */
	public Affine3dCoordinateSpace(
			BigDecimal x0, BigDecimal x1, BigDecimal x2, BigDecimal x3,
			BigDecimal y0, BigDecimal y1, BigDecimal y2, BigDecimal y3,
			BigDecimal z0, BigDecimal z1, BigDecimal z2, BigDecimal z3)
	{
		this.x0 = value(x0);
		this.x1 = value(x1);
		this.x2 = value(x2);
		this.x3 = value(x3);
		this.y0 = value(y0);
		this.y1 = value(y1);
		this.y2 = value(y2);
		this.y3 = value(y3);
		this.z0 = value(z0);
		this.z1 = value(z1);
		this.z2 = value(z2);
		this.z3 = value(z3);
		this.context = new MathContext(20);
	}
	
	@Override
	public int numDimensions() {
		return 3;
	}

	@Override
	public BigDecimal project(long[] coord, int axis) {
		if (axis < 0 || axis > 2)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return transform(coord[0], coord[1], coord[2], x0, x1, x2, x3);
		}
		else if (axis == 1) {
			return transform(coord[0], coord[1], coord[2], y0, y1, y2, y3);
		}
		else { // axis == 2
			return transform(coord[0], coord[1], coord[2], z0, z1, z2, z3);
		}
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		if (axis < 0 || axis > 2)
			throw new IllegalArgumentException("axis out of bounds error");
		else if (axis == 0) {
			return transform(coord.get(0), coord.get(1), coord.get(2), x0, x1, x2, x3);
		}
		else if (axis == 1) {
			return transform(coord.get(0), coord.get(1), coord.get(2), y0, y1, y2, y3);
		}
		else { // axis == 2
			return transform(coord.get(0), coord.get(1), coord.get(2), z0, z1, z2, z3);
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

	public void setPrecision(int precision) {
		this.context = new MathContext(precision);
	}

	private BigDecimal transform(long i, long j, long k, BigDecimal t0, BigDecimal t1, BigDecimal t2, BigDecimal t3) {
		BigDecimal tmp = BigDecimal.valueOf(i).multiply(t0, context);
		tmp = tmp.add(BigDecimal.valueOf(j).multiply(t1, context), context);
		tmp = tmp.add(BigDecimal.valueOf(k).multiply(t2, context), context);
		return tmp.add(t3, context);
	}

	private BigDecimal value(BigDecimal v) {
		if (v == null)
			return BigDecimal.ZERO;
		return v;
	}
}
