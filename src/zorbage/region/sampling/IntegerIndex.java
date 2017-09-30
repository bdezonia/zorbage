/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.region.sampling;

import zorbage.type.algebra.Dimensioned;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Duplicatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IntegerIndex implements Duplicatable<IntegerIndex>, Dimensioned, Settable<IntegerIndex>, Bounded<IntegerIndex> {
	
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
		for (int i = 0; i < index.length; i++)
			index[i] = Long.MAX_VALUE;
	}

	@Override
	public void setMin() {
		for (int i = 0; i < index.length; i++)
			index[i] = Long.MIN_VALUE;
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
}
