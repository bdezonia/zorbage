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

import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Algebra;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.tuple.Tuple2Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorageGeneric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestZip {

	@Test
	public void test() {
		Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member> alg = new Tuple2Algebra<>(G.INT64, G.FLT);
		ArrayStorageGeneric<Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member>, Tuple2<SignedInt64Member,Float32Member>> store = new ArrayStorageGeneric<Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member>, Tuple2<SignedInt64Member,Float32Member>>(alg, 10);
		IndexedDataSource<SignedInt64Member> longs = Storage.allocate(G.INT64.construct(), new long[] {1,2,3,4,5,6,7,8,9,10});
		IndexedDataSource<Float32Member> floats = Storage.allocate(G.FLT.construct(), new float[] {20,19,18,17,16,15,14,13,12,11});
		Zip.two(G.INT64, G.FLT, longs, floats, store);
		Tuple2<SignedInt64Member, Float32Member> value = alg.construct();
		store.get(0, value);
		assertEquals(1, value.a().v());
		assertEquals(20, value.b().v(), 0);
		store.get(1, value);
		assertEquals(2, value.a().v());
		assertEquals(19, value.b().v(), 0);
		store.get(2, value);
		assertEquals(3, value.a().v());
		assertEquals(18, value.b().v(), 0);
		store.get(3, value);
		assertEquals(4, value.a().v());
		assertEquals(17, value.b().v(), 0);
		store.get(4, value);
		assertEquals(5, value.a().v());
		assertEquals(16, value.b().v(), 0);
		store.get(5, value);
		assertEquals(6, value.a().v());
		assertEquals(15, value.b().v(), 0);
		store.get(6, value);
		assertEquals(7, value.a().v());
		assertEquals(14, value.b().v(), 0);
		store.get(7, value);
		assertEquals(8, value.a().v());
		assertEquals(13, value.b().v(), 0);
		store.get(8, value);
		assertEquals(9, value.a().v());
		assertEquals(12, value.b().v(), 0);
		store.get(9, value);
		assertEquals(10, value.a().v());
		assertEquals(11, value.b().v(), 0);
	}
	
}
