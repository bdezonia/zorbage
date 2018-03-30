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
package nom.bdezonia.zorbage.type.storage.linear;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.LinearAccessor;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLinearAccessor {

	@Test
	public void testAccessor() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32<SignedInt32Member> storage = new ArrayStorageSignedInt32<SignedInt32Member>(10, new SignedInt32Member());
		LinearAccessor<SignedInt32Member> accessor = new LinearAccessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// visit the data in reverse order
		accessor.afterLast();
		while (accessor.hasPrev()) {
			accessor.back();
			accessor.get();
			assertEquals(value.v(), accessor.pos());
		}
	}

}
