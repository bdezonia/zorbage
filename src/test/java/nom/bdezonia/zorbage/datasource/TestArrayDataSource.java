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
package nom.bdezonia.zorbage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestArrayDataSource {

	@Test
	public void test1() {

		Float32Member value = G.FLT.construct();
		
		Float32Member[] a = new Float32Member[5];
		
		a[0] = G.FLT.construct("1");
		a[1] = G.FLT.construct("2");
		a[2] = G.FLT.construct("3");
		a[3] = G.FLT.construct("4");
		a[4] = G.FLT.construct("5");

		IndexedDataSource<Float32Member> ds = new ArrayDataSource<Float32Algebra, Float32Member>(G.FLT, a);
		
		assertEquals(5, ds.size());
		ds.get(0, value);
		assertEquals(1, value.v(), 0);
		ds.get(1, value);
		assertEquals(2, value.v(), 0);
		ds.get(2, value);
		assertEquals(3, value.v(), 0);
		ds.get(3, value);
		assertEquals(4, value.v(), 0);
		ds.get(4, value);
		assertEquals(5, value.v(), 0);
		
		try {
			ds.get(-1, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			ds.get(5, value);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		value.setV(99);
		
		ds.set(4, value);
		
		assertEquals(99, a[4].v(), 0);
	}
}
