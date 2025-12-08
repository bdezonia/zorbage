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
import nom.bdezonia.zorbage.function.Function1;

/**
 * {@link SamplingConditional} is a {@link Sampling} that includes the
 * points of another Sampling that match a specified predicate.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingConditional<T extends Allocatable<T> & Settable<T> & DimensionCount>
	implements Sampling<T>
{
	private final Function1<Boolean,T> condition;
	private final Sampling<T> sampling;
	private final T example;
	
	public SamplingConditional(Function1<Boolean,T> condition, Sampling<T> sampling, T example) {
		this.condition = condition;
		this.sampling = sampling;
		this.example = example.allocate();
	}
	
	@Override
	public int numDimensions() {
		return sampling.numDimensions();
	}

	@Override
	public boolean contains(T samplePoint) {
		if (condition.call(samplePoint))
			return sampling.contains(samplePoint);
		return false;
	}

	@Override
	public SamplingIterator<T> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<T> {

		private final SamplingIterator<T> iter1 = sampling.iterator();
		private final T index = example.allocate();
		private boolean cached = false;
		
		private boolean positionToNext() {
			
			while (iter1.hasNext()) {
				iter1.next(index);
				if (condition.call(index)) {
					cached = true;
					return true;
				}
			}
			return false;
		}

		@Override
		public void reset() {
			iter1.reset();
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
