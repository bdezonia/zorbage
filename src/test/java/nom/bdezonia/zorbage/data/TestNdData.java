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
package nom.bdezonia.zorbage.data;

import static org.junit.Assert.*;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.axis.IdentityAxisEquation;
import nom.bdezonia.zorbage.axis.StringDefinedAxisEquation;
import nom.bdezonia.zorbage.data.NdData;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNdData {

	@Test
	public void test() {
		IndexedDataSource<SignedInt32Member> list = ArrayStorage.allocateInts(
				new int[] {1,2,3,4,5,6});
		NdData<SignedInt32Member> data = new NdData<>(new long[] {2,3}, list);
		
		assertEquals(list, data.rawData());
		
		assertEquals(2, data.numDimensions());
		
		assertEquals(2, data.dimension(0));
		assertEquals(3, data.dimension(1));
		
		assertEquals(6, data.numElements());
		
		IntegerIndex index = new IntegerIndex(data.numDimensions());
		SignedInt32Member v = G.INT32.construct();
		SignedInt32Member v2 = G.INT32.construct();
		
		index.set(0, 0);
		index.set(1, 0);
		data.get(index, v);
		assertEquals(1, v.v());
		index.set(0, 1);
		index.set(1, 0);
		data.get(index, v);
		assertEquals(2, v.v());
		index.set(0, 0);
		index.set(1, 1);
		data.get(index, v);
		assertEquals(3, v.v());
		index.set(0, 1);
		index.set(1, 1);
		data.get(index, v);
		assertEquals(4, v.v());
		index.set(0, 0);
		index.set(1, 2);
		data.get(index, v);
		assertEquals(5, v.v());
		index.set(0, 1);
		index.set(1, 2);
		data.get(index, v);
		assertEquals(6, v.v());

		Procedure2<Long,HighPrecisionMember> axis = null;
		axis = data.getAxisEquation(0);
		assertNotNull(axis);
		assertTrue(axis instanceof IdentityAxisEquation);
		axis = data.getAxisEquation(1);
		assertNotNull(axis);
		assertTrue(axis instanceof IdentityAxisEquation);
		
		data.setAxisEquation(0, new StringDefinedAxisEquation("$0^2"));
		axis = data.getAxisEquation(0);
		assertNotNull(axis);
		assertTrue(axis instanceof StringDefinedAxisEquation);
		axis = data.getAxisEquation(1);
		assertNotNull(axis);
		assertTrue(axis instanceof IdentityAxisEquation);

		v2.setV(45);
		
		index.set(0, 0);
		index.set(1, 0);
		assertFalse(data.oob(index));
		data.getSafe(index, v);
		assertEquals(1, v.v());
		data.setSafe(index, v2);
		data.getSafe(index, v);
		assertEquals(45, v.v());
		
		v2.setV(99);
		
		index.set(0, 1);
		index.set(1, 2);
		assertFalse(data.oob(index));
		data.getSafe(index, v);
		assertEquals(6, v.v());
		data.setSafe(index, v2);
		data.getSafe(index, v);
		assertEquals(99, v.v());

		index.set(0, 0);
		index.set(1, -1);
		assertTrue(data.oob(index));
		try {
			data.getSafe(index, v);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			data.setSafe(index, v2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		index.set(0, 2);
		index.set(1, 0);
		assertTrue(data.oob(index));
		try {
			data.getSafe(index, v);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			data.setSafe(index, v2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// Note: the contract of ndData.set() is that it assumes coords are in bounds.
		// Behavior is undefined if you pass in an oob index.
		
		v.setV(1000);
		index.set(0, 1);
		index.set(1, 1);
		data.get(index, v2);
		assertEquals(4, v2.v());
		data.set(index, v);
		data.get(index, v2);
		assertEquals(1000, v2.v());
		
		v.setV(82);
		index.set(0, 0);
		index.set(1, 2);
		data.get(index, v2);
		assertEquals(5, v2.v());
		data.set(index, v);
		data.get(index, v2);
		assertEquals(82, v2.v());
		
		IndexedDataSource<SignedInt32Member> pipe = null;

		
		for (int i = 0; i < list.size(); i++) {
			v.setV(i+1);
			list.set(i, v);
		}
		
		index.set(0, 0);
		pipe = data.piped(1, index);
		assertEquals(3, pipe.size());
		pipe.get(0, v);
		assertEquals(1, v.v());
		pipe.get(1, v);
		assertEquals(3, v.v());
		pipe.get(2, v);
		assertEquals(5, v.v());
		
		index.set(0, 1);
		pipe = data.piped(1, index);
		assertEquals(3, pipe.size());
		pipe.get(0, v);
		assertEquals(2, v.v());
		pipe.get(1, v);
		assertEquals(4, v.v());
		pipe.get(2, v);
		assertEquals(6, v.v());
		
		index.set(1, 0);
		pipe = data.piped(0, index);
		assertEquals(2, pipe.size());
		pipe.get(0, v);
		assertEquals(1, v.v());
		pipe.get(1, v);
		assertEquals(2, v.v());
		
		index.set(1, 1);
		pipe = data.piped(0, index);
		assertEquals(2, pipe.size());
		pipe.get(0, v);
		assertEquals(3, v.v());
		pipe.get(1, v);
		assertEquals(4, v.v());
		
		index.set(1, 2);
		pipe = data.piped(0, index);
		assertEquals(2, pipe.size());
		pipe.get(0, v);
		assertEquals(5, v.v());
		pipe.get(1, v);
		assertEquals(6, v.v());
	}

}