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
package nom.bdezonia.zorbage.multidim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestProcedurePaddedMultiDimDataSource {

	@Test
	public void test() {
		IndexedDataSource<SignedInt32Member> data = ArrayStorage.allocateInts(new int[] {1,2,3,4});
		MultiDimDataSource<?,SignedInt32Member> md = new MultiDimDataSource<>(new long[] {2,2}, data);
		ProcedurePaddedMultiDimDataSource<?,SignedInt32Member> pad =
				new ProcedurePaddedMultiDimDataSource<>(G.INT32, md, proc);
		SignedInt32Member value = G.INT32.construct();
		
		IntegerIndex idx = new IntegerIndex(2);
		
		// inbounds
		
		idx.set(0,0);
		idx.set(1,0);
		pad.get(idx, value);
		assertEquals(1,value.v());
		
		idx.set(0,1);
		idx.set(1,0);
		pad.get(idx, value);
		assertEquals(2,value.v());
		
		idx.set(0,0);
		idx.set(1,1);
		pad.get(idx, value);
		assertEquals(3,value.v());
		
		idx.set(0,1);
		idx.set(1,1);
		pad.get(idx, value);
		assertEquals(4,value.v());
		
		// out of bounds
		
		value.setV(0);
		idx.set(0,-1);
		idx.set(1,0);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,2);
		idx.set(1,0);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,-1);
		idx.set(1,1);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,2);
		idx.set(1,1);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,0);
		idx.set(1,-1);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,0);
		idx.set(1,2);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,1);
		idx.set(1,-1);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,1);
		idx.set(1,2);
		pad.get(idx, value);
		assertEquals(1000,value.v());
		
		value.setV(0);
		idx.set(0,1000000);
		idx.set(1,-500000);
		pad.get(idx, value);
		assertEquals(1000,value.v());
	}
	
	private final Procedure2<IntegerIndex,SignedInt32Member> proc = new Procedure2<IntegerIndex, SignedInt32Member>()
	{
		@Override
		public void call(IntegerIndex a, SignedInt32Member b) {
			b.setV(1000);
		}
	};
}
