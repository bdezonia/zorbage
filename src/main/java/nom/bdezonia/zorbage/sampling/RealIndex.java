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
package nom.bdezonia.zorbage.sampling;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.DimensionCount;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.Settable;

import java.util.Arrays;

/**
 * {@link RealIndex} defines an n-dimensional real space point. Uses doubles as the basis
 * rather than floats;
 * 
 * @author Barry DeZonia
 *
 */
public class RealIndex
	implements
		Allocatable<RealIndex>, Duplicatable<RealIndex>, DimensionCount,
		Settable<RealIndex>, Gettable<RealIndex>, SupportsBoundsCalc<RealIndex>
{
	private final double[] index;
	
	public RealIndex(int numDims) {
		index = new double[numDims];
	}

	public RealIndex(double[] idx) {
		index = idx.clone();
	}

	@Override
	public void set(RealIndex other) {
		if (index.length != other.index.length)
			throw new IllegalArgumentException("mismatched dims in set()");
		for (int i = 0; i < index.length; i++)
			index[i] = other.index[i];
	}
	
	@Override
	public void get(RealIndex other) {
		if (index.length != other.index.length)
			throw new IllegalArgumentException("mismatched dims in get()");
		for (int i = 0; i < index.length; i++)
			other.index[i] = index[i];
	}
	
	public void set(int dim, double value) {
		index[dim] = value;
	}
	
	public double get(int dim) {
		return index[dim];
	}
	
	@Override
	public RealIndex duplicate() {
		return new RealIndex(index);
	}
	
	@Override
	public int numDimensions() { return index.length; }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof RealIndex) {
			RealIndex other = (RealIndex) obj;
			if (numDimensions() != other.numDimensions())
				return false;
			for (int i = 0; i < numDimensions(); i++) {
				if (get(i) != other.get(i))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void setMax() {
		Arrays.fill(index, Double.MAX_VALUE);
	}

	@Override
	public void setMin() {
		Arrays.fill(index, -Double.MAX_VALUE);
	}

	@Override
	public void updateMin(RealIndex tmp) {
		for (int i = 0; i < index.length; i++)
			index[i] = Math.min(index[i], tmp.get(i));
	}

	@Override
	public void updateMax(RealIndex tmp) {
		for (int i = 0; i < index.length; i++)
			index[i] = Math.max(index[i], tmp.get(i));
	}

	@Override
	public RealIndex allocate() {
		return new RealIndex(index.length);
	}
}
