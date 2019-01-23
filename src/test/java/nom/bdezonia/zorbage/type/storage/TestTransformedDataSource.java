/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

// TODO declaration of TransformedDataSource seems to be impossible to instantiate in a type
// safe way that will compile correctly. Must investigate.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransformedDataSource {

	@SuppressWarnings({"unchecked","rawtypes"})
	@Test
	public void test1() {
		IndexedDataSource<?, Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource wrappedData = new TransformedDataSource<>(doubles, G.DBL, ident, ident);
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
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Test
	public void test2() {
		IndexedDataSource<?, Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		TransformedDataSource wrappedData = new TransformedDataSource<>(doubles, G.DBL, half, dbl);
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
}
