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
package nom.bdezonia.zorbage.sampling;

import java.util.ArrayList;

import nom.bdezonia.zorbage.type.algebra.DimensionCount;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;

/**
 * {@link SamplingGeneral} is a {@link Sampling} where the point coordinates are added
 * individually and can include grids of any shape.
 * 
 * @author Barry DeZonia
 *
 */
public class SamplingGeneral<U extends Duplicatable<U> & DimensionCount & Settable<U>>
	implements Sampling<U>
{
	private final int numD;
	private final ArrayList<U> points;
	
	public SamplingGeneral(int numD) {
		this.numD = numD;
		this.points = new ArrayList<U>();
	}
	
	public void add(U pt) {
		if (pt.numDimensions() != numDimensions())
			throw new IllegalArgumentException("mismatched dimensions in SamplingGeneral::add()");
		points.add(pt.duplicate());
	}

	@Override
	public int numDimensions() {
		return numD;
	}

	@Override
	public boolean contains(U samplePoint) {
		if (samplePoint.numDimensions() != numDimensions())
			throw new IllegalArgumentException("mismatched dimensions in SamplingGeneral::contains()");
		int pointsSize = points.size();
		for (int i = 0; i < pointsSize; i++) {
			if (points.get(i).equals(samplePoint))
				return true;
		}
		return false;
	}

	@Override
	public SamplingIterator<U> iterator() {
		return new Iterator();
	}
	
	/**
	 * Create a {@link SamplingGeneral} from any other (possibly compound) {@link Sampling}.
	 * 
	 * @param other
	 * @param scratch
	 * @return
	 */
	public static <T extends Settable<T> & Duplicatable<T> & DimensionCount>
		SamplingGeneral<T> create(Sampling<T> other, T scratch)
	{
		SamplingGeneral<T> sampling = new SamplingGeneral<T>(other.numDimensions());
		SamplingIterator<T> iter = other.iterator();
		while (iter.hasNext()) {
			iter.next(scratch);
			sampling.add(scratch);
		}
		return sampling;
	}
	
	private class Iterator implements SamplingIterator<U> {

		private final java.util.Iterator<U> iter = points.iterator();
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public void next(U value) {
			value.set(iter.next());
		}
		
	}

}
