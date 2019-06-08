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

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.impl.ToHighPrec;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.floatunlim.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.TransformedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransformedDataSource {

	@Test
	public void test1() {
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource<Float64Member,Float64Member> wrappedData = new TransformedDataSource<>(G.DBL, doubles, ident, ident);
		Float64Member value = G.DBL.construct();
		for (long i = 0; i < doubles.size(); i++) {
			wrappedData.get(i, value);
			assertEquals(i, value.v(), 0);
		}
		for (long i = 0; i < doubles.size(); i++) {
			value.setV(i*4);
			wrappedData.set(i, value);
			doubles.get(i, value);
			assertEquals(i*4, value.v(), 0);
		}
	}
	
	@Test
	public void test2() {
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource<Float64Member,Float64Member> wrappedData = new TransformedDataSource<>(G.DBL, doubles, dbl, half);
		Float64Member value = G.DBL.construct();
		for (long i = 0; i < doubles.size(); i++) {
			doubles.get(i, value);
			assertEquals(i, value.v(), 0);
			wrappedData.get(i, value);
			assertEquals(i*2.0, value.v(), 0);
		}
		for (long i = 0; i < doubles.size(); i++) {
			value.setV(i);
			wrappedData.set(i, value);
			doubles.get(i, value);
			assertEquals(i/2.0, value.v(), 0);
			wrappedData.get(i, value);
			assertEquals(i, value.v(), 0);
		}
	}
	
	@Test
	public void test3() {
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource<Float64Member,Float64Member> wrappedData = new TransformedDataSource<>(G.DBL, doubles, ident, ident);
		IndexedDataSource<Float64Member> dupe = wrappedData.duplicate();
		Float64Member value = G.DBL.construct();
		for (long i = 0; i < doubles.size(); i++) {
			dupe.get(i, value);
			assertEquals(i, value.v(), 0);
		}
	}
	
	@Test
	public void test4() {
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource<Float64Member,SignedInt32Member> wrappedData = new TransformedDataSource<>(G.DBL, doubles, dblToInt, intToDbl);
		SignedInt32Member value = G.INT32.construct();
		for (long i = 0; i < doubles.size(); i++) {
			wrappedData.get(i, value);
			assertEquals(i, value.v());
		}
		// Do this just to test warning free compilation of duplicate() with mixed types
		IndexedDataSource<SignedInt32Member> tmp = wrappedData.duplicate();
		assertEquals(doubles.size(), tmp.size());
	}
	
	@Test
	public void test5() {
		HighPrecisionMember result = G.FLOAT_UNLIM.construct();
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		ToHighPrec<Float64Member> toHighPrec = new ToHighPrec<Float64Member>();
		TransformedDataSource<Float64Member, HighPrecisionMember> hps =
				new TransformedDataSource<>(G.DBL, doubles, toHighPrec, null);
		Sum.compute(G.FLOAT_UNLIM, hps, result);
		assertEquals(BigDecimal.valueOf(45.0), result.v());
	}
	
	private Procedure2<Float64Member,Float64Member> ident = new Procedure2<Float64Member, Float64Member>() {
		
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.set(a);
		}
	};
	
	private Procedure2<Float64Member,Float64Member> half = new Procedure2<Float64Member, Float64Member>() {
		
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(a.v() / 2.0);
		}
	};
	
	private Procedure2<Float64Member,Float64Member> dbl = new Procedure2<Float64Member, Float64Member>() {
		
		@Override
		public void call(Float64Member a, Float64Member b) {
			b.setV(a.v() * 2.0);
		}
	};

	private Procedure2<SignedInt32Member,Float64Member> intToDbl = new Procedure2<SignedInt32Member, Float64Member>() {
		
		@Override
		public void call(SignedInt32Member a, Float64Member b) {
			
			b.setV(a.v());
		}
	};

	private Procedure2<Float64Member,SignedInt32Member> dblToInt = new Procedure2<Float64Member, SignedInt32Member>() {
		
		@Override
		public void call(Float64Member a, SignedInt32Member b) {
			
			b.setV((int)a.v());
		}
	};
}
