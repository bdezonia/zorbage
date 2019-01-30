/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.condition.EqualConstant;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSearchN {

	@Test
	public void test1() {
		IndexedDataSource<?,SignedInt32Member> a = ArrayStorage.allocateInts(
				new int[] {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6});
		Condition<SignedInt32Member> cond1 =
				new EqualConstant<SignedInt32Algebra, SignedInt32Member>(G.INT32, new SignedInt32Member(7));
		assertEquals(16, SearchN.compute(G.INT32, 4, cond1, a));
		Condition<SignedInt32Member> cond2 =
				new EqualConstant<SignedInt32Algebra, SignedInt32Member>(G.INT32, new SignedInt32Member(4));
		assertEquals(6, SearchN.compute(G.INT32, 4, cond2, a));
	}

	@Test
	public void test2() {
		IndexedDataSource<?,SignedInt32Member> a = ArrayStorage.allocateInts(
				new int[] {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6});
		assertEquals(16, SearchN.compute(G.INT32, 3, new SignedInt32Member(-1), a));
		assertEquals(3, SearchN.compute(G.INT32, 3, new SignedInt32Member(3), a));
	}
}
