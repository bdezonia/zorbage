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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RealIndex implements Duplicatable<RealIndex>, Dimensioned, Settable<RealIndex>, Bounded<RealIndex> {
	
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
		for (int i = 0; i < index.length; i++)
			index[i] = Double.MAX_VALUE;
	}

	@Override
	public void setMin() {
		for (int i = 0; i < index.length; i++)
			index[i] = Double.MIN_VALUE;
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
}
