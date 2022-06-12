/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGenerate {

	// Basic usage: passing a Proc that takes no params: tested by TestTransform1
	
	// Advanced usage: passing a Proc that takes params
	
	@Test
	public void test() {
		Float64Member tmp = G.DBL.construct();
		IndexedDataSource<Float64Member> storage = ArrayStorage.allocate(tmp, 10001);
		Generate.compute(G.DBL, proc, storage, new Float64Member(7), new Float64Member(77));
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			assertEquals(84, tmp.v(), 0);
		}
	}
	
	private final Procedure<Float64Member> proc = new Procedure<Float64Member>() {
		@Override
		public void call(Float64Member result, Float64Member... inputs) {
			assertEquals(2, inputs.length);
			G.DBL.add().call(inputs[0], inputs[1], result);
		}
	};
}
