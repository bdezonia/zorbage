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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.*;

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCreateMask {

	@Test
	public void test() {
		float[] floats = new float[] {1,4,-3,0,3,-7,8,1,1,1,2};
		IndexedDataSource<Float32Member> nums = Storage.allocate(G.FLT.construct(), floats);
		Function1<Boolean,Float32Member> condition = new Function1<Boolean,Float32Member>() {
			@Override
			public Boolean call(Float32Member value) {
				return value.v() < 4;
			}
		};
		IndexedDataSource<UnsignedInt1Member> mask = CreateMask.compute(G.FLT, condition, nums);
		assertEquals(nums.size(), mask.size());
		UnsignedInt1Member value = G.UINT1.construct();
		mask.get(0, value);
		assertEquals(1, value.v());
		mask.get(1, value);
		assertEquals(0, value.v());
		mask.get(2, value);
		assertEquals(1, value.v());
		mask.get(3, value);
		assertEquals(1, value.v());
		mask.get(4, value);
		assertEquals(1, value.v());
		mask.get(5, value);
		assertEquals(1, value.v());
		mask.get(6, value);
		assertEquals(0, value.v());
		mask.get(7, value);
		assertEquals(1, value.v());
		mask.get(8, value);
		assertEquals(1, value.v());
		mask.get(9, value);
		assertEquals(1, value.v());
		mask.get(10, value);
		assertEquals(1, value.v());
	}

}
