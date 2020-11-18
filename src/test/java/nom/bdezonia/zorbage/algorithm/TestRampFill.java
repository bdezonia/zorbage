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

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRampFill {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt32Member> ints = Storage.allocate(G.INT32.construct(), 
				new int[75]);
		SignedInt32Member tmp = G.INT32.construct();
		SignedInt32Member startVal = G.INT32.construct();
		startVal.setV(-13);
		SignedInt32Member incBy = G.INT32.construct();
		incBy.setV(1);
		RampFill.compute(G.INT32, startVal, incBy, ints);

		ints.get(0, tmp);
		assertEquals(-13, tmp.v());

		ints.get(1, tmp);
		assertEquals(-12, tmp.v());

		ints.get(73, tmp);
		assertEquals(60, tmp.v());

		ints.get(74, tmp);
		assertEquals(61, tmp.v());
	}

	@Test
	public void test2() {
		
		IndexedDataSource<SignedInt32Member> ints = Storage.allocate(G.INT32.construct(), 
				new int[75]);
		SignedInt32Member tmp = G.INT32.construct();
		SignedInt32Member startVal = G.INT32.construct();
		startVal.setV(45);
		SignedInt32Member incBy = G.INT32.construct();
		incBy.setV(-1);
		RampFill.compute(G.INT32, startVal, incBy, ints);

		ints.get(0, tmp);
		assertEquals(45, tmp.v());

		ints.get(1, tmp);
		assertEquals(44, tmp.v());

		ints.get(73, tmp);
		assertEquals(-28, tmp.v());

		ints.get(74, tmp);
		assertEquals(-29, tmp.v());
	}
}
