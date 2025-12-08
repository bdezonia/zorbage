/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSearch {

	@Test
	public void test1() {
		Float64Member tmp = G.DBL.construct();
		IndexedDataSource<Float64Member> a = Storage.allocate(tmp, 10);
		IndexedDataSource<Float64Member> elements = Storage.allocate(tmp, 3);
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
		IndexedDataSource<Float64Member> a = Storage.allocate(tmp, 10);
		IndexedDataSource<Float64Member> elements = Storage.allocate(tmp, 3);
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
	
	private class Cond implements Function1<Boolean,Tuple2<Float64Member,Float64Member>> {

		@Override
		public Boolean call(Tuple2<Float64Member, Float64Member> value) {
			return value.a().v() == value.b().v() + 3;
		}
		
	}
}
