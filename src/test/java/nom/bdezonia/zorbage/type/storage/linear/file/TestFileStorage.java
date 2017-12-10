/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.linear.file;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.storage.linear.file.FileStorageFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFileStorage {

	@Test
	public void run() {
		ComplexFloat64Member v = new ComplexFloat64Member();
		
		FileStorageFloat64<ComplexFloat64Member> store = new FileStorageFloat64<ComplexFloat64Member>(4000, new ComplexFloat64Member());
		
		for (long i = 0; i < store.size(); i++) {
			v.setR(i);
			v.setI(i+1);
			store.set(i, v);
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			assertEquals(i, v.r(), 0.00000001);
			assertEquals(i+1, v.i(), 0.00000001);
		}
		
		FileStorageFloat64<ComplexFloat64Member> dup = store.duplicate();
		
		assertEquals(store.size(), dup.size());

		for (long i = 0; i < dup.size(); i++) {
			dup.get(i, v);
			assertEquals(i, v.r(), 0.00000001);
			assertEquals(i+1, v.i(), 0.00000001);
		}
		
	}
}
