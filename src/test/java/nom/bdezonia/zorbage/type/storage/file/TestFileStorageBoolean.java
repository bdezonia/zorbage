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
package nom.bdezonia.zorbage.type.storage.file;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFileStorageBoolean {

	private class SomeType implements BooleanCoder, Allocatable<SomeType> {

		private boolean a, b, c;

		@Override
		public int booleanCount() {
			return 3;
		}

		@Override
		public void fromBooleanArray(boolean[] arr, int index) {
			a = arr[index+0];
			b = arr[index+1];
			c = arr[index+2];
		}

		@Override
		public void toBooleanArray(boolean[] arr, int index) {
			arr[index+0] = a;
			arr[index+1] = b;
			arr[index+2] = c;
		}

		@Override
		public void fromBooleanFile(RandomAccessFile raf) throws IOException {
			throw new IllegalArgumentException("should not need to implement for this example");
		}

		@Override
		public void toBooleanFile(RandomAccessFile raf) throws IOException {
			throw new IllegalArgumentException("should not need to implement for this example");
		}
		
		@Override
		public SomeType allocate() {
			return new SomeType();
		}
	}
	
	@Test
	public void test1() {

		final int SIZE = 100;
		
		SomeType v = new SomeType();
		
		FileStorageBoolean<SomeType> store = new FileStorageBoolean<SomeType>(SIZE, v);

		assertEquals(SIZE, store.size());
		
		for (long i = 0; i < store.size(); i++) {
			v.a = true;
			v.b = true;
			v.c = true;
			store.get(i, v);
			assertEquals(false, v.a);
			assertEquals(false, v.b);
			assertEquals(false, v.c);
		}
		
		for (long i = 0; i < store.size(); i++) {
			v.a = true;
			v.b = true;
			v.c = true;
			store.set(i, v);
		}

		for (long i = 0; i < store.size(); i++) {
			v.a = false;
			v.b = false;
			v.c = false;
			store.get(i, v);
			assertEquals(true, v.a);
			assertEquals(true, v.b);
			assertEquals(true, v.c);
		}
		
		FileStorageBoolean<SomeType> dup = store.duplicate();
		
		assertEquals(store.size(), dup.size());

		for (long i = 0; i < dup.size(); i++) {
			v.a = false;
			v.b = false;
			v.c = false;
			dup.get(i, v);
			assertEquals(true, v.a);
			assertEquals(true, v.b);
			assertEquals(true, v.c);
		}
		
	}
	
}
