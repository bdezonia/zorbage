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
package nom.bdezonia.zorbage.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMaskedDataSource {

	@Test
	public void zero() {

		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {});
		try {
			new MaskedDataSource<>(list, mask);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void one() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}

	@Test
	public void two() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}

	@Test
	public void three() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(3, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}

	@Test
	public void four() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(3, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(2, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());

		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}

	@Test
	public void five() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(4, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}

	@Test
	public void six() {
		
		SignedInt32Member value = G.INT32.construct();
		
		IndexedDataSource<SignedInt32Member> list = Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<BooleanMember> mask;
		IndexedDataSource<SignedInt32Member> result;
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(0, result.size());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(4, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,false,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(3, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {false,true,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(2, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(1, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(4, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,false,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(3, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(2, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,false,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(4, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,false,false,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(3, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,false,true,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(5, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,true,false,true});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(4, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		
		mask = Storage.allocate(G.BOOL.construct(), new boolean[] {true,true,true,true,true,false});
		result = new MaskedDataSource<>(list, mask);
		assertEquals(5, result.size());
		result.get(0, value);
		assertEquals(1, value.v());
		result.get(1, value);
		assertEquals(2, value.v());
		result.get(2, value);
		assertEquals(3, value.v());
		result.get(3, value);
		assertEquals(4, value.v());
		result.get(4, value);
		assertEquals(5, value.v());
	}
}
