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

import zorbage.condition.Condition;
import zorbage.type.algebra.Dimensioned;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingConditional<T extends Allocatable<T> & Settable<T> & Dimensioned>
	implements Sampling<T>
{
	private final Condition<T> condition;
	private final Sampling<T> sampling;
	private final T example;
	
	public SamplingConditional(Condition<T> condition, Sampling<T> sampling, T example) {
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
		if (condition.isTrue(samplePoint))
			return sampling.contains(samplePoint);
		return false;
	}

	@Override
	public SamplingIterator<T> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<T> {

		private SamplingIterator<T> iter1 = sampling.iterator();
		private T index = example.allocate();
		private boolean cached = false;
		
		private boolean positionToNext() {
			
			while (iter1.hasNext()) {
				iter1.next(index);
				if (condition.isTrue(index)) {
					cached = true;
					return true;
				}
			}
			return false;
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
