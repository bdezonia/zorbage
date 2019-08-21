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
public class TestSamplingDifference {

	@Test
	public void test1() {
		IntegerIndex tmp = new IntegerIndex(1);
		SamplingGeneral<IntegerIndex> a = new SamplingGeneral<>(tmp.numDimensions());
		tmp.set(0, 1);
		a.add(tmp);
		tmp.set(0, 2);
		a.add(tmp);
		tmp.set(0, 3);
		a.add(tmp);
		tmp.set(0, 4);
		a.add(tmp);
		SamplingGeneral<IntegerIndex> b = new SamplingGeneral<>(tmp.numDimensions());
		tmp.set(0, 3);
		b.add(tmp);
		tmp.set(0, 4);
		b.add(tmp);
		tmp.set(0, 5);
		b.add(tmp);
		tmp.set(0, 6);
		b.add(tmp);

		IntegerIndex tmp2 = new IntegerIndex(1);
		SamplingDifference<IntegerIndex> diff = new SamplingDifference<IntegerIndex>(a, b, tmp2);
		SamplingIterator<IntegerIndex> iter = diff.iterator();

		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertTrue(diff.contains(tmp));
		assertEquals(1, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertTrue(diff.contains(tmp));
		assertEquals(2, tmp.get(0));
		assertFalse(iter.hasNext());

		diff = new SamplingDifference<IntegerIndex>(b, a, tmp2);
		iter = diff.iterator();

		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertTrue(diff.contains(tmp));
		assertEquals(5, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertTrue(diff.contains(tmp));
		assertEquals(6, tmp.get(0));
		assertFalse(iter.hasNext());
		
		tmp.set(0, 3);
		assertFalse(diff.contains(tmp));
		tmp.set(0, 4);
		assertFalse(diff.contains(tmp));
	}
}