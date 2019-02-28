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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageGeneric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestZip {

	@Test
	public void test() {
		TupleAlgebra alg = new TupleAlgebra();
		ArrayStorageGeneric<TupleAlgebra, Tuple2<SignedInt64Member,Float32Member>> store = new ArrayStorageGeneric<TupleAlgebra, Tuple2<SignedInt64Member,Float32Member>>(10, alg);
		IndexedDataSource<?, SignedInt64Member> longs = ArrayStorage.allocateLongs(new long[] {1,2,3,4,5,6,7,8,9,10});
		IndexedDataSource<?, Float32Member> floats = ArrayStorage.allocateFloats(new float[] {20,19,18,17,16,15,14,13,12,11});
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
	
	private class TupleAlgebra implements Algebra<TupleAlgebra, Tuple2<SignedInt64Member,Float32Member>> {

		@Override
		public Tuple2<SignedInt64Member, Float32Member> construct() {
			return new Tuple2<SignedInt64Member,Float32Member>(G.INT64.construct(),G.FLT.construct());
		}

		@Override
		public Tuple2<SignedInt64Member, Float32Member> construct(Tuple2<SignedInt64Member, Float32Member> other) {
			return new Tuple2<SignedInt64Member,Float32Member>(other.a(),other.b());
		}

		@Override
		public Tuple2<SignedInt64Member, Float32Member> construct(String str) {
			// TODO: do something sensible
			return construct();
		}

		private final Function2<Boolean, Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> EQ =
				new Function2<Boolean, Tuple2<SignedInt64Member,Float32Member>, Tuple2<SignedInt64Member,Float32Member>>()
		{
			@Override
			public Boolean call(Tuple2<SignedInt64Member, Float32Member> a, Tuple2<SignedInt64Member, Float32Member> b) {
				return a.a() == b.a() && a.b() == b.b();
			}
		};

		@Override
		public Function2<Boolean, Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> isEqual() {
			return EQ;
		}

		private final Function2<Boolean, Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> NEQ =
				new Function2<Boolean, Tuple2<SignedInt64Member,Float32Member>, Tuple2<SignedInt64Member,Float32Member>>()
		{
			@Override
			public Boolean call(Tuple2<SignedInt64Member, Float32Member> a, Tuple2<SignedInt64Member, Float32Member> b) {
				return !isEqual().call(a, b);
			}
		};

		@Override
		public Function2<Boolean, Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> isNotEqual() {
			return NEQ;
		}

		private final Procedure2<Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> ASSIGN =
				new Procedure2<Tuple2<SignedInt64Member,Float32Member>, Tuple2<SignedInt64Member,Float32Member>>()
		{
			@Override
			public void call(Tuple2<SignedInt64Member, Float32Member> a, Tuple2<SignedInt64Member, Float32Member> b) {
				b.a().setV(a.a().v());
				b.b().setV(a.b().v());
			}
		};

		@Override
		public Procedure2<Tuple2<SignedInt64Member, Float32Member>, Tuple2<SignedInt64Member, Float32Member>> assign() {
			return ASSIGN;
		}

		private final Function1<Boolean, Tuple2<SignedInt64Member, Float32Member>> ISZERO =
				new Function1<Boolean, Tuple2<SignedInt64Member,Float32Member>>()
		{
			@Override
			public Boolean call(Tuple2<SignedInt64Member, Float32Member> a) {
				return a.a().v() == 0 && a.b().v() == 0;
			}
		};

		@Override
		public Function1<Boolean, Tuple2<SignedInt64Member, Float32Member>> isZero() {
			return ISZERO;
		}
		
	}
}
