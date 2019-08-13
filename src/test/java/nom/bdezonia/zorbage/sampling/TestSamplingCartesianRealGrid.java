/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSamplingCartesianRealGrid {

	@Test
	public void test1() {
		
		SamplingCartesianRealGrid sampling = new SamplingCartesianRealGrid(
				new double[] {-1,-2}, new double[] {1,2}, new long[] {5, 9});
		RealIndex tmp = new RealIndex(sampling.numDimensions());
		SamplingIterator<RealIndex> iter = sampling.iterator();
		assertTrue(iter.hasNext());
		double x = -1;
		double y = -2;
		int count = 0;
		while (iter.hasNext()) {
			iter.next(tmp);
			assertEquals(x, tmp.get(0), 0);
			assertEquals(y, tmp.get(1), 0);
			assertTrue(sampling.contains(tmp));
			x += 0.5;
			if (x > 1) {
				x = -1;
				y += 0.5;
			}
			count++;
		}
		assertEquals(45, count);
		
		tmp.set(0, -2);
		tmp.set(1, -2);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, 2);
		tmp.set(1, -2);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, -1);
		tmp.set(1, -3);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, -1);
		tmp.set(1, 3);
		assertFalse(sampling.contains(tmp));
	}
}
