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
package nom.bdezonia.zorbage.type.storage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Algebra;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestListDataSource {

	@Test
	public void test1() {

		Float32Member value = G.FLT.construct();
		
		List<Float32Member> a = new ArrayList<Float32Member>();
		
		a.add(G.FLT.construct("1"));
		a.add(G.FLT.construct("2"));
		a.add(G.FLT.construct("3"));
		a.add(G.FLT.construct("4"));
		a.add(G.FLT.construct("5"));

		IndexedDataSource<Float32Member> ds = new ListDataSource<Float32Algebra, Float32Member>(G.FLT, a);
		
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
		
		assertEquals(99, a.get(4).v(), 0);
	}
}
