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

// Note: this class inspired by Nifti's header docs on their affine coord xform

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Affine3dCoordinateSystem
	implements CoordinateSystem
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
	public Affine3dCoordinateSystem(
			BigDecimal x0, BigDecimal x1, BigDecimal x2, BigDecimal x3,
			BigDecimal y0, BigDecimal y1, BigDecimal y2, BigDecimal y3,
			BigDecimal z0, BigDecimal z1, BigDecimal z2, BigDecimal z3)
	{
		this.x0 = x0 == null ? BigDecimal.ZERO : x0;
		this.x1 = x1 == null ? BigDecimal.ZERO : x1;
		this.x2 = x2 == null ? BigDecimal.ZERO : x2;
		this.x3 = x3 == null ? BigDecimal.ZERO : x3;
		this.y0 = y0 == null ? BigDecimal.ZERO : y0;
		this.y1 = y1 == null ? BigDecimal.ZERO : y1;
		this.y2 = y2 == null ? BigDecimal.ZERO : y2;
		this.y3 = y3 == null ? BigDecimal.ZERO : y3;
		this.z0 = z0 == null ? BigDecimal.ZERO : z0;
		this.z1 = z1 == null ? BigDecimal.ZERO : z1;
		this.z2 = z2 == null ? BigDecimal.ZERO : z2;
		this.z3 = z3 == null ? BigDecimal.ZERO : z3;
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
			return z(coord[0], coord[1], coord[2]);
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
			return z(coord.get(0), coord.get(1), coord.get(2));
		}
	}
	
	private BigDecimal x(long i, long j, long k) {
		return transform(i, j, k, x0, x1, x2, x3);
	}

	private BigDecimal y(long i, long j, long k) {
		return transform(i, j, k, y0, y1, y2, y3);
	}

	private BigDecimal z(long i, long j, long k) {
		return transform(i, j, k, z0, z1, z2, z3);
	}

	private BigDecimal transform(long i, long j, long k, BigDecimal t0, BigDecimal t1, BigDecimal t2, BigDecimal t3) {
		BigDecimal tmp = BigDecimal.valueOf(i).multiply(t0);
		tmp = tmp.add(BigDecimal.valueOf(j).multiply(t1));
		tmp = tmp.add(BigDecimal.valueOf(k).multiply(t2));
		return tmp.add(t3);
	}
}
