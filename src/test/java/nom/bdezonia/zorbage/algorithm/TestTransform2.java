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

import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform2 {

	@Test
	public void test1() {
		
		BooleanMember tmp = G.BOOL.construct();
		
		IndexedDataSource<BooleanMember> a,c;
		
		a = Storage.allocate(G.BOOL.construct(), new boolean[] {false, true});
		c = Storage.allocate(G.BOOL.construct(), new boolean[2]);

		Transform2.compute(G.BOOL, G.BOOL, G.BOOL.logicalNot(), a, c);
		
		c.get(0, tmp);
		assertEquals(true, tmp.v());
		c.get(1, tmp);
		assertEquals(false, tmp.v());
	}

	@Test
	public void test2() {
		
		UnsignedInt1Member tmp = G.UINT1.construct();
		
		IndexedDataSource<UnsignedInt1Member> a,c;
		
		a = ArrayStorage.allocate(tmp, 2);
		c = ArrayStorage.allocate(tmp, 2);

		tmp.setV(0);
		a.set(0, tmp);
		tmp.setV(1);
		a.set(1, tmp);

		Transform2.compute(G.UINT1, G.UINT1, G.UINT1.logicalNot(), a, c);
		
		c.get(0, tmp);
		assertEquals(1, tmp.v());
		c.get(1, tmp);
		assertEquals(0, tmp.v());
	}
	
	@Test
	public void test3() {
		
		SignedInt32Member tmp = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> a,c;

		a = Storage.allocate(G.INT32.construct(), new int[] {0, 1, 2, 3});
		c = Storage.allocate(G.INT32.construct(), new int[4]);

		Transform2.compute(G.INT32, G.INT32, G.INT32.negate(), a, c);

		c.get(0, tmp);
		assertEquals(0, tmp.v());
		c.get(1, tmp);
		assertEquals(-1, tmp.v());
		c.get(2, tmp);
		assertEquals(-2, tmp.v());
		c.get(3, tmp);
		assertEquals(-3, tmp.v());
		
	}
}
