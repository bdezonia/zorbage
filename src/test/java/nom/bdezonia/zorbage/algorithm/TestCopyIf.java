/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.condition.GreaterThan;
import nom.bdezonia.zorbage.condition.GreaterThanConstant;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Group;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Group;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCopyIf {

	public void test() {
		IndexedDataSource<?, SignedInt16Member> a = ArrayStorage.allocateShorts(
				new short[] {1,2,3,4,5,6,7,8,9});
		IndexedDataSource<?, SignedInt16Member> b = ArrayStorage.allocateShorts(
				new short[] {0,0,0,0,0,0,0,0,0});
		Condition<SignedInt16Member> cond = new GreaterThanConstant<SignedInt16Group,SignedInt16Member>(G.INT16, new SignedInt16Member((short)4));
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
