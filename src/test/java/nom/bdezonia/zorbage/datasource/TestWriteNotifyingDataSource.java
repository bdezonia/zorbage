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
package nom.bdezonia.zorbage.datasource;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestWriteNotifyingDataSource {

	@Test
	public void test1() {
		
		IndexedDataSource<SignedInt32Member> a = Storage.allocate(G.INT32.construct(), 
				new int[] {0,0,0});
		
		WriteNotifyingDataSource<SignedInt32Algebra, SignedInt32Member> ds =
				new WriteNotifyingDataSource<>(G.INT32, a);
		
		CustomListener listener = new CustomListener();

		ds.subscribe(listener);
		
		SignedInt32Member value = G.INT32.construct();
		
		value.setV(1);
		ds.set(0, value);
		value.setV(2);
		ds.set(0, value);
		value.setV(3);
		ds.set(0, value);
		
		listener.testIntegrity();
	}
	
	private class CustomListener implements DataSourceListener<SignedInt32Algebra, SignedInt32Member> {

		private boolean seenOne = false;
		private boolean seenTwo = false;
		private boolean seenThree = false;
		private final SignedInt32Member tmp = G.INT32.construct();

		@Override
		public void notify(SignedInt32Algebra alegbra, IndexedDataSource<SignedInt32Member> source, long index) {
			source.get(index, tmp);
			if (tmp.v() == 1) {
				if (seenOne) throw new IllegalArgumentException();
				seenOne = true;
			}
			else if (tmp.v() == 2) {
				if (seenTwo) throw new IllegalArgumentException();
				seenTwo = true;
			}
			else if (tmp.v() == 3) {
				if (seenThree) throw new IllegalArgumentException();
				seenThree = true;
			}
			else
				throw new IllegalArgumentException("unexpected value " + tmp.v());
		}
		
		public void testIntegrity() {
			if (!seenOne || !seenTwo || !seenThree)
				throw new IllegalArgumentException("did not see all the expected values");
		}
	}
}
