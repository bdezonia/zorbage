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
import nom.bdezonia.zorbage.algebra.Settable;

/**
 * {@link SamplingUnion} is a {@link Sampling} that iterates the set of points
 * that make up the sum of the two sets of points of two other Samplings. A U B,
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 */
public class SamplingUnion<T extends Allocatable<T> & DimensionCount & Settable<T>> implements Sampling<T> {

	private final Sampling<T> first;
	private final Sampling<T> second;
	private final T example;
	
	public SamplingUnion(Sampling<T> first, Sampling<T> second, T example) {
		if (first.numDimensions() != second.numDimensions())
			throw new IllegalArgumentException("num dimensions do not match in SamplingUnion");
		if (first.numDimensions() != example.numDimensions())
			throw new IllegalArgumentException("example index does not have correct dimensions");
		this.first = first;
		this.second = second;
		this.example = example.allocate();
	}
	
	@Override
	public int numDimensions() {
		return first.numDimensions();
	}

	@Override
	public boolean contains(T samplePoint) {
		return first.contains(samplePoint) || second.contains(samplePoint);
	}

	@Override
	public SamplingIterator<T> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<T> {

		private final SamplingIterator<T> iter1;
		private final SamplingIterator<T> iter2;
		private final T index;
		private boolean cached;
		
		private Iterator() {
			iter1 = first.iterator();
			iter2 = second.iterator();
			index = example.allocate();
			cached = false;
		}

		private boolean positionToNext() {
			if (iter1.hasNext()) {
				iter1.next(index);
				cached = true;
				return true;
			}
			while (iter2.hasNext()) {
				iter2.next(index);
				if (!first.contains(index)) {
					cached = true;
					return true;
				}
			}
			return false;
		}
		
		@Override
		public void reset() {
			iter1.reset();
			iter2.reset();
			cached = false;
		}
		
		@Override
		public boolean hasNext() {
			if (cached)
				return true;
			else
				return positionToNext();
		}

		@Override
		public void next(T value) {
			if (value.numDimensions() != numDimensions())
				throw new IllegalArgumentException("next() has bad output value dimensions");
			if (!cached)
				throw new IllegalArgumentException("next() called when do not hasNext()");
			value.set(index);
			cached = false;
		}
		
	}

}
