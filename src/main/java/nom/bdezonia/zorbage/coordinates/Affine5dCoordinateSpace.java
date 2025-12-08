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
public class Affine5dCoordinateSpace
	implements CoordinateSpace
{
	private final BigDecimal x0;
	private final BigDecimal x1;
	private final BigDecimal x2;
	private final BigDecimal x3;
	private final BigDecimal x4;
	private final BigDecimal x5;
	private final BigDecimal y0;
	private final BigDecimal y1;
	private final BigDecimal y2;
	private final BigDecimal y3;
	private final BigDecimal y4;
	private final BigDecimal y5;
	private final BigDecimal z0;
	private final BigDecimal z1;
	private final BigDecimal z2;
	private final BigDecimal z3;
	private final BigDecimal z4;
	private final BigDecimal z5;
	private final BigDecimal t0;
	private final BigDecimal t1;
	private final BigDecimal t2;
	private final BigDecimal t3;
	private final BigDecimal t4;
	private final BigDecimal t5;
	private final BigDecimal c0;
	private final BigDecimal c1;
	private final BigDecimal c2;
	private final BigDecimal c3;
	private final BigDecimal c4;
	private final BigDecimal c5;
	private MathContext context;

	public Affine5dCoordinateSpace(
			BigDecimal x0, BigDecimal x1, BigDecimal x2, BigDecimal x3, BigDecimal x4, BigDecimal x5,
			BigDecimal y0, BigDecimal y1, BigDecimal y2, BigDecimal y3, BigDecimal y4, BigDecimal y5,
			BigDecimal z0, BigDecimal z1, BigDecimal z2, BigDecimal z3, BigDecimal z4, BigDecimal z5,
			BigDecimal t0, BigDecimal t1, BigDecimal t2, BigDecimal t3, BigDecimal t4, BigDecimal t5,
			BigDecimal c0, BigDecimal c1, BigDecimal c2, BigDecimal c3, BigDecimal c4, BigDecimal c5)
	{
		this.x0 = BigDecimalUtils.value(x0);
		this.x1 = BigDecimalUtils.value(x1);
		this.x2 = BigDecimalUtils.value(x2);
		this.x3 = BigDecimalUtils.value(x3);
		this.x4 = BigDecimalUtils.value(x4);
		this.x5 = BigDecimalUtils.value(x5);
		this.y0 = BigDecimalUtils.value(y0);
		this.y1 = BigDecimalUtils.value(y1);
		this.y2 = BigDecimalUtils.value(y2);
		this.y3 = BigDecimalUtils.value(y3);
		this.y4 = BigDecimalUtils.value(y4);
		this.y5 = BigDecimalUtils.value(y5);
		this.z0 = BigDecimalUtils.value(z0);
		this.z1 = BigDecimalUtils.value(z1);
		this.z2 = BigDecimalUtils.value(z2);
		this.z3 = BigDecimalUtils.value(z3);
		this.z4 = BigDecimalUtils.value(z4);
		this.z5 = BigDecimalUtils.value(z5);
		this.t0 = BigDecimalUtils.value(t0);
		this.t1 = BigDecimalUtils.value(t1);
		this.t2 = BigDecimalUtils.value(t2);
		this.t3 = BigDecimalUtils.value(t3);
		this.t4 = BigDecimalUtils.value(t4);
		this.t5 = BigDecimalUtils.value(t5);
		this.c0 = BigDecimalUtils.value(c0);
		this.c1 = BigDecimalUtils.value(c1);
		this.c2 = BigDecimalUtils.value(c2);
		this.c3 = BigDecimalUtils.value(c3);
		this.c4 = BigDecimalUtils.value(c4);
		this.c5 = BigDecimalUtils.value(c5);
		this.context = new MathContext(20);
	}
	
	@Override
	public int numDimensions() {
		return 5;
	}

	@Override
	public BigDecimal project(long[] coord, int axis) {
		
		if (axis < 0 || axis >= numDimensions())
			throw new IllegalArgumentException("axis out of bounds error");
		
		if (axis == 0) {
			return transform(coord[0], coord[1], coord[2], coord[3], coord[4], x0, x1, x2, x3, x4, x5);
		}
		else if (axis == 1) {
			return transform(coord[0], coord[1], coord[2], coord[3], coord[4], y0, y1, y2, y3, y4, y5);
		}
		else if (axis == 2) {
			return transform(coord[0], coord[1], coord[2], coord[3], coord[4], z0, z1, z2, z3, z4, z5);
		}
		else if (axis == 3) {
			return transform(coord[0], coord[1], coord[2], coord[3], coord[4], t0, t1, t2, t3, t4, t5);
		}
		else { // axis == 4
			return transform(coord[0], coord[1], coord[2], coord[3], coord[4], c0, c1, c2, c3, c4, c5);
		}
	}

	@Override
	public BigDecimal project(IntegerIndex coord, int axis) {
		
		if (axis < 0 || axis >= numDimensions())
			throw new IllegalArgumentException("axis out of bounds error");
		
		if (axis == 0) {
			return transform(coord.get(0), coord.get(1), coord.get(2), coord.get(3), coord.get(4), x0, x1, x2, x3, x4, x5);
		}
		else if (axis == 1) {
			return transform(coord.get(0), coord.get(1), coord.get(2), coord.get(3), coord.get(4), y0, y1, y2, y3, y4, y5);
		}
		else if (axis == 2) {
			return transform(coord.get(0), coord.get(1), coord.get(2), coord.get(3), coord.get(4), z0, z1, z2, z3, z4, z5);
		}
		else if (axis == 3) {
			return transform(coord.get(0), coord.get(1), coord.get(2), coord.get(3), coord.get(4), t0, t1, t2, t3, t4, t5);
		}
		else { // axis == 4
			return transform(coord.get(0), coord.get(1), coord.get(2), coord.get(3), coord.get(4), t0, t1, t2, t3, t4, t5);
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

	public Affine4dCoordinateSpace reduce(int coordToDrop) {

		if (coordToDrop < 0 || coordToDrop >= numDimensions())
			throw new IllegalArgumentException("coordinate out of bounds");

		BigDecimal x0, x1, x2, x3, x4, y0, y1, y2, y3, y4, z0, z1, z2, z3, z4, t0, t1, t2, t3, t4;
		
		if (coordToDrop == 0) {
			x0 = this.y1;
			x1 = this.y2;
			x2 = this.y3;
			x3 = this.y4;
			x4 = this.y5;
			y0 = this.z1;
			y1 = this.z2;
			y2 = this.z3;
			y3 = this.z4;
			y4 = this.z5;
			z0 = this.t1;
			z1 = this.t2;
			z2 = this.t3;
			z3 = this.t4;
			z4 = this.t5;
			t0 = this.c1;
			t1 = this.c2;
			t2 = this.c3;
			t3 = this.c4;
			t4 = this.c5;
		}
		else if (coordToDrop == 1) {
			x0 = this.x0;
			x1 = this.x2;
			x2 = this.x3;
			x3 = this.x4;
			x4 = this.x5;
			y0 = this.z0;
			y1 = this.z2;
			y2 = this.z3;
			y3 = this.z4;
			y4 = this.z5;
			z0 = this.t0;
			z1 = this.t2;
			z2 = this.t3;
			z3 = this.t4;
			z4 = this.t5;
			t0 = this.c0;
			t1 = this.c2;
			t2 = this.c3;
			t3 = this.c4;
			t4 = this.c5;
		}
		else if (coordToDrop == 2) {
			x0 = this.x0;
			x1 = this.x1;
			x2 = this.x3;
			x3 = this.x4;
			x4 = this.x5;
			y0 = this.y0;
			y1 = this.y1;
			y2 = this.y3;
			y3 = this.y4;
			y4 = this.y5;
			z0 = this.t0;
			z1 = this.t1;
			z2 = this.t3;
			z3 = this.t4;
			z4 = this.t5;
			t0 = this.c0;
			t1 = this.c1;
			t2 = this.c3;
			t3 = this.c4;
			t4 = this.c5;
		}
		else if (coordToDrop == 3) {
			x0 = this.x0;
			x1 = this.x1;
			x2 = this.x2;
			x3 = this.x4;
			x4 = this.x5;
			y0 = this.y0;
			y1 = this.y1;
			y2 = this.y2;
			y3 = this.y4;
			y4 = this.y5;
			z0 = this.z0;
			z1 = this.z1;
			z2 = this.z2;
			z3 = this.z4;
			z4 = this.z5;
			t0 = this.c0;
			t1 = this.c1;
			t2 = this.c2;
			t3 = this.c4;
			t4 = this.c5;
		}
		else {  // coordToDrop == 4)
			x0 = this.x0;
			x1 = this.x1;
			x2 = this.x2;
			x3 = this.x3;
			x4 = this.x5;
			y0 = this.y0;
			y1 = this.y1;
			y2 = this.y2;
			y3 = this.y3;
			y4 = this.y5;
			z0 = this.z0;
			z1 = this.z1;
			z2 = this.z2;
			z3 = this.z3;
			z4 = this.z5;
			t0 = this.t0;
			t1 = this.t1;
			t2 = this.t2;
			t3 = this.t3;
			t4 = this.t5;
		}
		
		return new Affine4dCoordinateSpace(x0, x1, x2, x3, x4, y0, y1, y2, y3, y4, z0, z1, z2, z3, z4, t0, t1, t2, t3, t4);
	}
	
	private BigDecimal transform(long i, long j, long k, long l, long m, BigDecimal t0, BigDecimal t1, BigDecimal t2, BigDecimal t3, BigDecimal t4, BigDecimal t5) {
		
		BigDecimal tmp = BigDecimal.valueOf(i).multiply(t0, context);
		tmp = tmp.add(BigDecimal.valueOf(j).multiply(t1, context), context);
		tmp = tmp.add(BigDecimal.valueOf(k).multiply(t2, context), context);
		tmp = tmp.add(BigDecimal.valueOf(l).multiply(t3, context), context);
		tmp = tmp.add(BigDecimal.valueOf(m).multiply(t4, context), context);
		return tmp.add(t5, context);
	}
}
