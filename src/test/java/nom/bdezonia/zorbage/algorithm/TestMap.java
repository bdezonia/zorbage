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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMap {

	@Test
	public void test1() {
	
		IndexedDataSource<SignedInt32Member> in = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5,6});
		assertEquals(6, in.size());
		
		IndexedDataSource<Float64Member> out = Map.compute(G.INT32, G.DBL, proc, in);
		assertEquals(6, out.size());
		
		Float64Member tmp = G.DBL.construct();

		out.get(0, tmp);
		assertEquals(3, tmp.v(), 0);
		
		out.get(1, tmp);
		assertEquals(6, tmp.v(), 0);
		
		out.get(2, tmp);
		assertEquals(9, tmp.v(), 0);
		
		out.get(3, tmp);
		assertEquals(12, tmp.v(), 0);
		
		out.get(4, tmp);
		assertEquals(15, tmp.v(), 0);
		
		out.get(5, tmp);
		assertEquals(18, tmp.v(), 0);
		
	}
	
	private final Procedure2<SignedInt32Member, Float64Member> proc =
			new Procedure2<SignedInt32Member, Float64Member>()
	{
		@Override
		public void call(SignedInt32Member a, Float64Member b) {
			b.setV(a.v()*3);
		}
	};
}
