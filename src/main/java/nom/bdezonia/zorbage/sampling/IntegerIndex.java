/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
 * {@link IntegerIndex} defines an n-dimensional integer space point. Uses longs as the basis
 * rather than ints;
 *
 * @author Barry DeZonia
 *
 */
public class IntegerIndex
	implements
		Allocatable<IntegerIndex>, Duplicatable<IntegerIndex>, DimensionCount,
		Settable<IntegerIndex>, Gettable<IntegerIndex>, SupportsBoundsCalc<IntegerIndex>
{
	private final long[] index;
	
	public IntegerIndex(int numDims) {
		index = new long[numDims];
	}

	public IntegerIndex(long[] idx) {
		index = idx.clone();
	}

	@Override
	public void set(IntegerIndex other) {
		if (index.length != other.index.length)
			throw new IllegalArgumentException("mismatched dims in set()");
		for (int i = 0; i < index.length; i++)
			index[i] = other.index[i];
	}
	
	@Override
	public void get(IntegerIndex other) {
		if (index.length != other.index.length)
			throw new IllegalArgumentException("mismatched dims in get()");
		for (int i = 0; i < index.length; i++)
			other.index[i] = index[i];
	}
	
	public void set(int dim, long value) {
		index[dim] = value;
	}
	
	public long get(int dim) {
		return index[dim];
	}
	
	@Override
	public IntegerIndex duplicate() {
		return new IntegerIndex(index);
	}
	
	@Override
	public int numDimensions() { return index.length; }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof IntegerIndex) {
			IntegerIndex other = (IntegerIndex) obj;
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
		Arrays.fill(index, Long.MAX_VALUE);
	}

	@Override
	public void setMin() {
		Arrays.fill(index, Long.MIN_VALUE);
	}

	@Override
	public void updateMin(IntegerIndex tmp) {
		for (int i = 0; i < index.length; i++)
			index[i] = Math.min(index[i], tmp.get(i));
	}

	@Override
	public void updateMax(IntegerIndex tmp) {
		for (int i = 0; i < index.length; i++)
			index[i] = Math.max(index[i], tmp.get(i));
	}

	@Override
	public IntegerIndex allocate() {
		return new IntegerIndex(index.length);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('{');
		for (int i = 0; i < index.length; i++) {
			if (i > 0)
				b.append(',');
			b.append(index[i]);
		}
		b.append('}');
		return b.toString();
	}
}
