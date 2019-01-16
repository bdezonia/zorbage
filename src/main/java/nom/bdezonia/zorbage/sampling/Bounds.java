/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.DimensionCount;
import nom.bdezonia.zorbage.type.ctor.Allocatable;

/**
 * {@link Bounds} is a private class with a static public calculation method for finding
 * the real or integer space bounds of a {@link Sampling}.
 * @author Barry DeZonia
 *
 */
public class Bounds {
	
	private Bounds() {
		// don't instantiate
	}

	/**
	 * Find the bounds of a sampling.
	 * 
	 * @param sampling
	 * @param min
	 * @param max
	 */
	public static <U extends Allocatable<U> & DimensionCount & SupportsBoundsCalc<U>>
		void find(Sampling<U> sampling, U min, U max)
	{
		if (min.numDimensions() != sampling.numDimensions() ||
				max.numDimensions() != sampling.numDimensions())
			throw new IllegalArgumentException("mismatched dimensions in Bounds::find()");
		min.setMax();
		max.setMin();
		SamplingIterator<U> iter = sampling.iterator();
		U tmp = min.allocate();
		while (iter.hasNext()) {
			iter.next(tmp);
			min.updateMin(tmp);
			max.updateMax(tmp);
		}
	}

}
