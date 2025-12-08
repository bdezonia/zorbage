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
package nom.bdezonia.zorbage.storage.file;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.algebra.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFileStorageFloat64 {

	private class SomeType implements DoubleCoder, Allocatable<SomeType> {

		private double a, b, c;

		@Override
		public int doubleCount() {
			return 3;
		}

		@Override
		public void fromDoubleArray(double[] arr, int index) {
			a = arr[index+0];
			b = arr[index+1];
			c = arr[index+2];
		}

		@Override
		public void toDoubleArray(double[] arr, int index) {
			arr[index+0] = a;
			arr[index+1] = b;
			arr[index+2] = c;
		}

		@Override
		public SomeType allocate() {
			return new SomeType();
		}
	}
	
	@Test
	public void test1() {

		final int SIZE = 5000;
		
		SomeType v = new SomeType();
		
		FileStorageFloat64<SomeType> store = new FileStorageFloat64<SomeType>(v, SIZE);

		assertEquals(SIZE, store.size());
		
		for (long i = 0; i < store.size(); i++) {
			v.a = -1;
			v.b = -1;
			v.c = -1;
			store.get(i, v);
			assertEquals(0, v.a, 0);
			assertEquals(0, v.b, 0);
			assertEquals(0, v.c, 0);
		}
		
		for (long i = 0; i < store.size(); i++) {
			v.a = (double) (i+0);
			v.b = (double) (i+1);
			v.c = (double) (i+2);
			store.set(i, v);
		}

		for (long i = 0; i < store.size(); i++) {
			v.a = -1;
			v.b = -1;
			v.c = -1;
			store.get(i, v);
			assertEquals((double)(i+0), v.a, 0);
			assertEquals((double)(i+1), v.b, 0);
			assertEquals((double)(i+2), v.c, 0);
		}
		
		FileStorageFloat64<SomeType> dup = store.duplicate();
		
		assertEquals(store.size(), dup.size());

		for (long i = 0; i < dup.size(); i++) {
			v.a = -1;
			v.b = -1;
			v.c = -1;
			dup.get(i, v);
			assertEquals((double)(i+0), v.a, 0);
			assertEquals((double)(i+1), v.b, 0);
			assertEquals((double)(i+2), v.c, 0);
		}
		
	}
	
}
