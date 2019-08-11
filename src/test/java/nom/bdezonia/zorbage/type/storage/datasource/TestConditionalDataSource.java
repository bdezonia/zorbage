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
package nom.bdezonia.zorbage.type.storage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestConditionalDataSource {

	@Test
	public void test1() {

		IndexedDataSource<SignedInt32Member> a = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5,6,7});
		
		Predicate<SignedInt32Member> odd = new Predicate<SignedInt32Member>()
		{
			@Override
			public boolean isTrue(SignedInt32Member value) {
				return value.v() % 2 == 1;
			}
		};
		IndexedDataSource<SignedInt32Member> odds = new ConditionalDataSource<SignedInt32Algebra, SignedInt32Member>(G.INT32, a, odd);
		
		SignedInt32Member value = G.INT32.construct();
		
		assertEquals(4, odds.size());
		odds.get(0, value);
		assertEquals(1, value.v());
		odds.get(1, value);
		assertEquals(3, value.v());
		odds.get(2, value);
		assertEquals(5, value.v());
		odds.get(3, value);
		assertEquals(7, value.v());

		Predicate<SignedInt32Member> even = new Predicate<SignedInt32Member>()
		{
			@Override
			public boolean isTrue(SignedInt32Member value) {
				return value.v() % 2 == 0;
			}
		};
		IndexedDataSource<SignedInt32Member> evens = new ConditionalDataSource<SignedInt32Algebra, SignedInt32Member>(G.INT32, a, even);
		
		assertEquals(3, evens.size());
		evens.get(0, value);
		assertEquals(2, value.v());
		evens.get(1, value);
		assertEquals(4, value.v());
		evens.get(2, value);
		assertEquals(6, value.v());
		
		value.setV(8);
		try {
			odds.set(3, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		evens.set(0, value);
		value.setV(0);
		a.get(1, value);
		assertEquals(8, value.v());
		
		value.setV(9);
		try {
			evens.set(3, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		odds.set(3, value);
		value.setV(0);
		a.get(6, value);
		assertEquals(9, value.v());
	}
}
