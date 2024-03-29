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

import nom.bdezonia.zorbage.predicate.GreaterThanConstant;
import nom.bdezonia.zorbage.predicate.LessThanConstant;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestStablePartition {

	@Test
	public void test1() {
		IndexedDataSource<SignedInt32Member> nums = Storage.allocate(G.INT32.construct(), new int[] {3, -2, -5, 7, 6, 8, 9, -4, 2, -1});
		Function1<Boolean,SignedInt32Member> cond =
				new LessThanConstant<SignedInt32Algebra, SignedInt32Member>(G.INT32, G.INT32.construct());
		StablePartition.compute(G.INT32, cond, nums);
		SignedInt32Member tmp = G.INT32.construct();
		nums.get(0, tmp);
		assertEquals(-2, tmp.v());
		nums.get(1, tmp);
		assertEquals(-5, tmp.v());
		nums.get(2, tmp);
		assertEquals(-4, tmp.v());
		nums.get(3, tmp);
		assertEquals(-1, tmp.v());
		nums.get(4, tmp);
		assertEquals(3, tmp.v());
		nums.get(5, tmp);
		assertEquals(7, tmp.v());
		nums.get(6, tmp);
		assertEquals(6, tmp.v());
		nums.get(7, tmp);
		assertEquals(8, tmp.v());
		nums.get(8, tmp);
		assertEquals(9, tmp.v());
		nums.get(9, tmp);
		assertEquals(2, tmp.v());
	}

	@Test
	public void test2() {
		IndexedDataSource<SignedInt32Member> nums = Storage.allocate(G.INT32.construct(), new int[] {0, 0, 3, 0, 2, 4, 5, 0, 7});
		Function1<Boolean,SignedInt32Member> cond =
				new GreaterThanConstant<SignedInt32Algebra, SignedInt32Member>(G.INT32, G.INT32.construct());
		StablePartition.compute(G.INT32, cond, nums);
		SignedInt32Member tmp = G.INT32.construct();
		nums.get(0, tmp);
		assertEquals(3, tmp.v());
		nums.get(1, tmp);
		assertEquals(2, tmp.v());
		nums.get(2, tmp);
		assertEquals(4, tmp.v());
		nums.get(3, tmp);
		assertEquals(5, tmp.v());
		nums.get(4, tmp);
		assertEquals(7, tmp.v());
		nums.get(5, tmp);
		assertEquals(0, tmp.v());
		nums.get(6, tmp);
		assertEquals(0, tmp.v());
		nums.get(7, tmp);
		assertEquals(0, tmp.v());
		nums.get(8, tmp);
		assertEquals(0, tmp.v());
	}
}
