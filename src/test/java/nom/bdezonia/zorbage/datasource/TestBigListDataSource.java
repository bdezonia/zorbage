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
package nom.bdezonia.zorbage.datasource;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.RampFill;
import nom.bdezonia.zorbage.type.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBigListDataSource {

	@Test
	public void test1() {
		
		BigListDataSource<SignedInt32Algebra, SignedInt32Member> list =
				new BigListDataSource<SignedInt32Algebra, SignedInt32Member>(G.INT32, 6);
		RampFill.compute(G.INT32, list);
		SignedInt32Member value = G.INT32.construct();
		assertEquals(6, list.size());
		list.get(0, value);
		assertEquals(0, value.v());
		list.get(1, value);
		assertEquals(1, value.v());
		list.get(2, value);
		assertEquals(2, value.v());
		list.get(3, value);
		assertEquals(3, value.v());
		list.get(4, value);
		assertEquals(4, value.v());
		list.get(5, value);
		assertEquals(5, value.v());
	}
}
