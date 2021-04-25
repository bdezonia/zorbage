/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSamplingGeneral {

	@Test
	public void test1() {
		
		IntegerIndex tmp = new IntegerIndex(1);
		SamplingGeneral<IntegerIndex> sampling = new SamplingGeneral<>(tmp.numDimensions());
		tmp.set(0, 14);
		sampling.add(tmp);
		tmp.set(0, -3);
		sampling.add(tmp);
		tmp.set(0, 5);
		sampling.add(tmp);
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(14, tmp.get(0));
		iter.next(tmp);
		assertEquals(-3, tmp.get(0));
		iter.next(tmp);
		assertEquals(5, tmp.get(0));
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void test2() {
		
		IntegerIndex tmp = new IntegerIndex(2);
		SamplingGeneral<IntegerIndex> sampling = new SamplingGeneral<>(tmp.numDimensions());
		for (int y = 0; y < 5; y++) {
			tmp.set(1, y);
			for (int x = 0; x < 3; x++) {
				tmp.set(0, x);
				sampling.add(tmp);
			}
		}
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		assertTrue(iter.hasNext());
		int x = 0;
		int y = 0;
		int count = 0;
		while (iter.hasNext()) {
			iter.next(tmp);
			assertEquals(x, tmp.get(0));
			assertEquals(y, tmp.get(1));
			assertTrue(sampling.contains(tmp));
			x += 1;
			if (x >= 3) {
				x = 0;
				y += 1;
			}
			count++;
		}
		assertEquals(15, count);
		
		tmp.set(0, -1);
		tmp.set(1, 0);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, 3);
		tmp.set(1, 0);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, 0);
		tmp.set(1, -1);
		assertFalse(sampling.contains(tmp));
		
		tmp.set(0, 0);
		tmp.set(1, 5);
		assertFalse(sampling.contains(tmp));
	}
	
	@Test
	public void test3() {
		
		SamplingCartesianIntegerGrid gridSampler = new SamplingCartesianIntegerGrid(
				new long[] {-1,-2}, new long[] {1,2});
		IntegerIndex tmp = new IntegerIndex(gridSampler.numDimensions());
		SamplingGeneral<IntegerIndex> sampling = SamplingGeneral.create(gridSampler, tmp);
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		assertTrue(iter.hasNext());
		int x = -1;
		int y = -2;
		int count = 0;
		while (iter.hasNext()) {
			iter.next(tmp);
			assertEquals(x, tmp.get(0));
			assertEquals(y, tmp.get(1));
			assertTrue(sampling.contains(tmp));
			x += 1;
			if (x > 1) {
				x = -1;
				y += 1;
			}
			count++;
		}
		assertEquals(15, count);
		
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
