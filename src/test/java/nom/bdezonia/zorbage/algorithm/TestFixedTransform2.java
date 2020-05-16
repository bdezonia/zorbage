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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFixedTransform2 {

	// This test shows an example of multiplying a constant against list a into list b
	
	@Test
	public void test1() {
		SignedInt32Member value = G.INT32.construct();
		SignedInt32Member seven = G.INT32.construct("7");
		IndexedDataSource<SignedInt32Member> a = ArrayStorage.allocateInts(
				new int[] {1,2,3,4});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(a.size(), value);
		Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member> operation =
				new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
		{
			@Override
			public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
				c.setV(a.v() * b.v());
			}
		};

		FixedTransform2a.compute(G.INT32, seven, operation, a, b);
		
		assertEquals(4, b.size());
		b.get(0, value);
		assertEquals(7, value.v());
		b.get(1, value);
		assertEquals(14, value.v());
		b.get(2, value);
		assertEquals(21, value.v());
		b.get(3, value);
		assertEquals(28, value.v());
	}

	// This test shows an example of adding a constant to list a into list b
	
	@Test
	public void test2() {
		SignedInt32Member value = G.INT32.construct();
		SignedInt32Member seven = G.INT32.construct("14");
		IndexedDataSource<SignedInt32Member> a = ArrayStorage.allocateInts(
				new int[] {1,2,3,4});
		IndexedDataSource<SignedInt32Member> b = Storage.allocate(a.size(), value);
		Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member> operation =
				new Procedure3<SignedInt32Member, SignedInt32Member, SignedInt32Member>()
		{
			@Override
			public void call(SignedInt32Member a, SignedInt32Member b, SignedInt32Member c) {
				c.setV(a.v() + b.v());
			}
		};

		FixedTransform2a.compute(G.INT32, seven, operation, a, b);
		
		assertEquals(4, b.size());
		b.get(0, value);
		assertEquals(15, value.v());
		b.get(1, value);
		assertEquals(16, value.v());
		b.get(2, value);
		assertEquals(17, value.v());
		b.get(3, value);
		assertEquals(18, value.v());
	}
}
