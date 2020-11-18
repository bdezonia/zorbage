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

import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUInt1ToBool {

	@Test
	public void test1() {
		UnsignedInt1Member intValue = new UnsignedInt1Member();
		BooleanMember boolValue = G.BOOL.construct();
		IndexedDataSource<UnsignedInt1Member> ints = Storage.allocate(intValue, 6);
		intValue.setV(0);
		ints.set(0, intValue);
		intValue.setV(1);
		ints.set(1, intValue);
		intValue.setV(0);
		ints.set(2, intValue);
		intValue.setV(0);
		ints.set(3, intValue);
		intValue.setV(1);
		ints.set(4, intValue);
		intValue.setV(1);
		ints.set(5, intValue);
		IndexedDataSource<BooleanMember> bools = Storage.allocate(boolValue, ints.size());
		UInt1ToBool.compute(ints, bools);
		bools.get(0, boolValue);
		assertEquals(false, boolValue.v());
		bools.get(1, boolValue);
		assertEquals(true, boolValue.v());
		bools.get(2, boolValue);
		assertEquals(false, boolValue.v());
		bools.get(3, boolValue);
		assertEquals(false, boolValue.v());
		bools.get(4, boolValue);
		assertEquals(true, boolValue.v());
		bools.get(5, boolValue);
		assertEquals(true, boolValue.v());
	}
}
