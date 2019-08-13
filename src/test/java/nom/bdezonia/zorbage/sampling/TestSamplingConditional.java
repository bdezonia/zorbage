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

import nom.bdezonia.zorbage.predicate.Predicate;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSamplingConditional {

	@Test
	public void test1() {
		SamplingCartesianIntegerGrid sampling = new SamplingCartesianIntegerGrid(
				new long[] {0}, new long[] {8});
		Predicate<IntegerIndex> evens = new Predicate<IntegerIndex>() {
			@Override
			public boolean isTrue(IntegerIndex value) {
				return value.get(0) % 2 == 0;
			}
		};
		Predicate<IntegerIndex> odds = new Predicate<IntegerIndex>() {
			@Override
			public boolean isTrue(IntegerIndex value) {
				return value.get(0) % 2 == 1;
			}
		};
		SamplingConditional<IntegerIndex> evenCond = new SamplingConditional<IntegerIndex>(evens, sampling, new IntegerIndex(1));
		SamplingConditional<IntegerIndex> oddCond = new SamplingConditional<IntegerIndex>(odds, sampling, new IntegerIndex(1));
		
		SamplingIterator<IntegerIndex> iter = null;
		IntegerIndex tmp = new IntegerIndex(1);
		
		iter = evenCond.iterator();
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(0, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(2, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(4, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(6, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(8, tmp.get(0));
		assertFalse(iter.hasNext());
		
		iter = oddCond.iterator();
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(1, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(3, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(5, tmp.get(0));
		assertTrue(iter.hasNext());
		iter.next(tmp);
		assertEquals(7, tmp.get(0));
		assertFalse(iter.hasNext());
	}
}
