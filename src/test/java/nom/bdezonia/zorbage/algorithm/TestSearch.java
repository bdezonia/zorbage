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
import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSearch {

	@Test
	public void test1() {
		Float64Member tmp = G.DBL.construct();
		IndexedDataSource<Float64Member> a = Storage.allocate(10, tmp);
		IndexedDataSource<Float64Member> elements = Storage.allocate(3, tmp);
		for (long i = 0; i < a.size(); i++) {
			tmp.setV(i+4);
			a.set(i, tmp);
		}
		for (long i = 0; i < elements.size(); i++) {
			tmp.setV(i+5);
			elements.set(i, tmp);
		}
		long x = Search.compute(G.DBL, elements, a);
		assertEquals(1,x);
	}

	@Test
	public void test2() {
		Float64Member tmp = G.DBL.construct();
		IndexedDataSource<Float64Member> a = Storage.allocate(10, tmp);
		IndexedDataSource<Float64Member> elements = Storage.allocate(3, tmp);
		for (long i = 0; i < a.size(); i++) {
			tmp.setV(i);
			a.set(i, tmp);
		}
		for (long i = 0; i < elements.size(); i++) {
			tmp.setV(i);
			elements.set(i, tmp);
		}
		Cond cond = new Cond();
		long x = Search.compute(G.DBL, cond, elements, a);
		assertEquals(3,x);
	}
	
	private class Cond implements Condition<Tuple2<Float64Member,Float64Member>> {

		@Override
		public boolean isTrue(Tuple2<Float64Member, Float64Member> value) {
			return value.a().v() == value.b().v() + 3;
		}
		
	}
}
