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
public class TestUnzip {

	@Test
	public void test() {
		Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member> alg = new Tuple2Algebra<>(G.INT64, G.FLT);
		Tuple2<SignedInt64Member, Float32Member> value = alg.construct();
		ArrayStorageGeneric<Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member>, Tuple2<SignedInt64Member,Float32Member>> store = new ArrayStorageGeneric<Tuple2Algebra<SignedInt64Algebra,SignedInt64Member,Float32Algebra,Float32Member>, Tuple2<SignedInt64Member,Float32Member>>(alg, 10);
		value.a().setV(1);
		value.b().setV(20);
		store.set(0, value);
		value.a().setV(2);
		value.b().setV(19);
		store.set(1, value);
		value.a().setV(3);
		value.b().setV(18);
		store.set(2, value);
		value.a().setV(4);
		value.b().setV(17);
		store.set(3, value);
		value.a().setV(5);
		value.b().setV(16);
		store.set(4, value);
		value.a().setV(6);
		value.b().setV(15);
		store.set(5, value);
		value.a().setV(7);
		value.b().setV(14);
		store.set(6, value);
		value.a().setV(8);
		value.b().setV(13);
		store.set(7, value);
		value.a().setV(9);
		value.b().setV(12);
		store.set(8, value);
		value.a().setV(10);
		value.b().setV(11);
		store.set(9, value);
		IndexedDataSource<SignedInt64Member> longs = Storage.allocate(G.INT64.construct(), new long[10]);
		IndexedDataSource<Float32Member> floats = Storage.allocate(G.FLT.construct(), new float[10]);
		Unzip.two(G.INT64, G.FLT, store, longs, floats);
		SignedInt64Member lValue = G.INT64.construct();
		Float32Member fValue = G.FLT.construct();
		longs.get(0, lValue);
		floats.get(0, fValue);
		assertEquals(1, lValue.v());
		assertEquals(20, fValue.v(), 0);
		longs.get(1, lValue);
		floats.get(1, fValue);
		assertEquals(2, lValue.v());
		assertEquals(19, fValue.v(), 0);
		longs.get(2, lValue);
		floats.get(2, fValue);
		assertEquals(3, lValue.v());
		assertEquals(18, fValue.v(), 0);
		longs.get(3, lValue);
		floats.get(3, fValue);
		assertEquals(4, lValue.v());
		assertEquals(17, fValue.v(), 0);
		longs.get(4, lValue);
		floats.get(4, fValue);
		assertEquals(5, lValue.v());
		assertEquals(16, fValue.v(), 0);
		longs.get(5, lValue);
		floats.get(5, fValue);
		assertEquals(6, lValue.v());
		assertEquals(15, fValue.v(), 0);
		longs.get(6, lValue);
		floats.get(6, fValue);
		assertEquals(7, lValue.v());
		assertEquals(14, fValue.v(), 0);
		longs.get(7, lValue);
		floats.get(7, fValue);
		assertEquals(8, lValue.v());
		assertEquals(13, fValue.v(), 0);
		longs.get(8, lValue);
		floats.get(8, fValue);
		assertEquals(9, lValue.v());
		assertEquals(12, fValue.v(), 0);
		longs.get(9, lValue);
		floats.get(9, fValue);
		assertEquals(10, value.a().v());
		assertEquals(11, value.b().v(), 0);
	}
	
}
