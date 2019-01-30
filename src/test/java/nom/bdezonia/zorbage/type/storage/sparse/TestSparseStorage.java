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
package nom.bdezonia.zorbage.type.storage.sparse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSparseStorage {

	private class Data implements BooleanCoder {

		private boolean a,b,c,d;
		
		@Override
		public int booleanCount() {
			return 4;
		}

		@Override
		public void fromBooleanArray(boolean[] arr, int index) {
			a = arr[index+0];
			b = arr[index+1];
			c = arr[index+2];
			d = arr[index+3];
		}

		@Override
		public void toBooleanArray(boolean[] arr, int index) {
			arr[index+0] = a;
			arr[index+1] = b;
			arr[index+2] = c;
			arr[index+3] = d;
		}

		@Override
		public void fromBooleanFile(RandomAccessFile raf) throws IOException {
			throw new UnsupportedOperationException("unimplemented for example");
		}

		@Override
		public void toBooleanFile(RandomAccessFile raf) throws IOException {
			throw new UnsupportedOperationException("unimplemented for example");
		}
		
	}

	@Test
	public void test() {
		Data elem = new Data();
		SparseStorageBoolean<Data> data = new SparseStorageBoolean<Data>(1000, elem);
		for (long i = 0; i < 16; i++) {
			elem.a = (i & 1) > 0;
			elem.b = (i & 2) > 0;
			elem.c = (i & 4) > 0;
			elem.d = (i & 8) > 0;
			data.set(i, elem);
		}
		
		data.get(0, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(1, elem);
		assertEquals(true, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(2, elem);
		assertEquals(false, elem.a);
		assertEquals(true, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(3, elem);
		assertEquals(true, elem.a);
		assertEquals(true, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(4, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(true, elem.c);
		assertEquals(false, elem.d);

		data.get(5, elem);
		assertEquals(true, elem.a);
		assertEquals(false, elem.b);
		assertEquals(true, elem.c);
		assertEquals(false, elem.d);

		data.get(6, elem);
		assertEquals(false, elem.a);
		assertEquals(true, elem.b);
		assertEquals(true, elem.c);
		assertEquals(false, elem.d);

		data.get(7, elem);
		assertEquals(true, elem.a);
		assertEquals(true, elem.b);
		assertEquals(true, elem.c);
		assertEquals(false, elem.d);

		data.get(8, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(true, elem.d);

		data.get(9, elem);
		assertEquals(true, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(true, elem.d);

		data.get(10, elem);
		assertEquals(false, elem.a);
		assertEquals(true, elem.b);
		assertEquals(false, elem.c);
		assertEquals(true, elem.d);

		data.get(11, elem);
		assertEquals(true, elem.a);
		assertEquals(true, elem.b);
		assertEquals(false, elem.c);
		assertEquals(true, elem.d);

		data.get(12, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(true, elem.c);
		assertEquals(true, elem.d);

		data.get(13, elem);
		assertEquals(true, elem.a);
		assertEquals(false, elem.b);
		assertEquals(true, elem.c);
		assertEquals(true, elem.d);

		data.get(14, elem);
		assertEquals(false, elem.a);
		assertEquals(true, elem.b);
		assertEquals(true, elem.c);
		assertEquals(true, elem.d);

		data.get(15, elem);
		assertEquals(true, elem.a);
		assertEquals(true, elem.b);
		assertEquals(true, elem.c);
		assertEquals(true, elem.d);

		data.get(16, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(17, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		data.get(999, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

		// access beyond ends of storage
		
		try {
			data.get(-1, elem);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			data.get(1000, elem);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		// erase an element by setting it to zero
		
		elem.a = false;
		elem.b = false;
		elem.c = false;
		elem.d = false;
		data.set(13, elem);
		elem.a = true;
		elem.b = true;
		elem.c = true;
		elem.d = true;
		data.get(13, elem);
		assertEquals(false, elem.a);
		assertEquals(false, elem.b);
		assertEquals(false, elem.c);
		assertEquals(false, elem.d);

	}
}
