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
package nom.bdezonia.zorbage.storage.array;

import org.junit.Test;

import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.int8.SignedInt8Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestArrayStorageBit {

	// how slow is the synchronized access overhead for array storage bit class?
	
	@Test
	public void speed() {
		final long iterations = 1000000;
		final long size = 1000;
		BooleanMember bool = new BooleanMember();
		SignedInt8Member byt = new SignedInt8Member();
		IndexedDataSource<BooleanMember> bools = new ArrayStorageBit<BooleanMember>(size, bool);
		IndexedDataSource<SignedInt8Member> bytes = new ArrayStorageSignedInt8<SignedInt8Member>(size, byt);
		long a = System.currentTimeMillis();
		bool.setV(true);
		for (long i = 0; i < iterations; i++) {
			bools.set(i%size, bool);
		}
		long b = System.currentTimeMillis();
		long c = System.currentTimeMillis();
		byt.setV(1);
		for (long i = 0; i < iterations; i++) {
			bytes.set(i%size, byt);
		}
		long d = System.currentTimeMillis();
		System.out.println("Bit  storage: "+(b-a));
		System.out.println("Byte storage: "+(d-c));
	}
}
