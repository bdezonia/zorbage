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

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFind {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt8Member> list = Storage.allocate(
				G.INT8.construct(),
				new byte[] {1,7,0,3,9,2,2,2,5});
		SignedInt8Member value = G.INT8.construct();
		value.setV((byte)-1);
		assertEquals(9, Find.compute(G.INT8, value, list));
		value.setV((byte)0);
		assertEquals(2, Find.compute(G.INT8, value, list));
		value.setV((byte)1);
		assertEquals(0, Find.compute(G.INT8, value, list));
		value.setV((byte)2);
		assertEquals(5, Find.compute(G.INT8, value, list));
		value.setV((byte)3);
		assertEquals(3, Find.compute(G.INT8, value, list));
		value.setV((byte)4);
		assertEquals(9, Find.compute(G.INT8, value, list));
		value.setV((byte)5);
		assertEquals(8, Find.compute(G.INT8, value, list));
		value.setV((byte)6);
		assertEquals(9, Find.compute(G.INT8, value, list));
		value.setV((byte)7);
		assertEquals(1, Find.compute(G.INT8, value, list));
	}
	
	@Test
	public void test2() {
		
		IndexedDataSource<SignedInt8Member> list = Storage.allocate(
				G.INT8.construct(),
				new byte[] {1,7,0,3,9,2,2,2,5});
		SignedInt8Member value = G.INT8.construct();
		value.setV((byte)2);
		assertEquals(5, Find.compute(G.INT8, value, 3, 6, list));
		assertEquals(6, Find.compute(G.INT8, value, 6, 3, list));
		assertEquals(7, Find.compute(G.INT8, value, 7, 2, list));
		assertEquals(9, Find.compute(G.INT8, value, 8, 1, list));
	}
}
