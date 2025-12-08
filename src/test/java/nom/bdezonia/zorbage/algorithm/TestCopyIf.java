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

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.predicate.GreaterThanConstant;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCopyIf {

	@Test
	public void test() {
		IndexedDataSource<SignedInt16Member> a = Storage.allocate(G.INT16.construct(), 
				new short[] {1,2,3,4,5,6,7,8,9});
		IndexedDataSource<SignedInt16Member> b = Storage.allocate(G.INT16.construct(), 
				new short[] {0,0,0,0,0,0,0,0,0});
		Function1<Boolean,SignedInt16Member> cond = new GreaterThanConstant<SignedInt16Algebra,SignedInt16Member>(G.INT16, new SignedInt16Member(4));
		CopyIf.compute(G.INT16, cond, a, b);
		SignedInt16Member value = G.INT16.construct();
		b.get(0, value);
		assertEquals(0, value.v());
		b.get(1, value);
		assertEquals(0, value.v());
		b.get(2, value);
		assertEquals(0, value.v());
		b.get(3, value);
		assertEquals(0, value.v());
		b.get(4, value);
		assertEquals(5, value.v());
		b.get(5, value);
		assertEquals(6, value.v());
		b.get(6, value);
		assertEquals(7, value.v());
		b.get(7, value);
		assertEquals(8, value.v());
		b.get(8, value);
		assertEquals(9, value.v());
	}
}
