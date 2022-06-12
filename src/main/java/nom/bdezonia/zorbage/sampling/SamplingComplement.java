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

/**
 * SamplingComplement is a sampling that represents the difference
 * between a sampling's bounds and it's actual members. This kind of
 * relationship only works well in integer indexed space.  This class
 * should not be confused with {@link SamplingDifference}.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingComplement implements Sampling<IntegerIndex> {

	private final Sampling<IntegerIndex> difference;
	
	public SamplingComplement(Sampling<IntegerIndex> sampling) {
		IntegerIndex min = new IntegerIndex(sampling.numDimensions());
		IntegerIndex max = new IntegerIndex(sampling.numDimensions());
		Bounds.find(sampling, min, max);
		Sampling<IntegerIndex> volume = new SamplingCartesianIntegerGrid(min, max);
		this.difference = new SamplingDifference<IntegerIndex>(volume, sampling, min);
	}
	
	@Override
	public int numDimensions() {
		return difference.numDimensions();
	}

	@Override
	public boolean contains(IntegerIndex samplePoint) {
		return difference.contains(samplePoint);
	}

	@Override
	public SamplingIterator<IntegerIndex> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements SamplingIterator<IntegerIndex> {

		private final SamplingIterator<IntegerIndex> iter;
		
		private Iterator() {
			iter = difference.iterator();
		}

		@Override
		public void reset() {
			iter.reset();
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public void next(IntegerIndex value) {
			iter.next(value);
		}
		
	}

}
