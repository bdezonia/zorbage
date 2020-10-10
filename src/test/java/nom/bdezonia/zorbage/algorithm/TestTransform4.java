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

import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform4 {

	@Test
	public void test1() {
		
		UnsignedInt4Member tmp = G.UINT4.construct();
		
		IndexedDataSource<UnsignedInt4Member> nums = Storage.allocate(tmp, 100);
		
		for (int i = 0; i < nums.size(); i++) {
			tmp.setV(i);
			nums.set(i, tmp);
		}
		
		Procedure4<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>
			proc = new Procedure4<UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member, UnsignedInt4Member>() {
				@Override
				public void call(UnsignedInt4Member a, UnsignedInt4Member b, UnsignedInt4Member c, UnsignedInt4Member d) {
					d.setV(a.v() + b.v() + c.v());
				}
			};
		
		Transform4.compute(G.UINT4, G.UINT4, G.UINT4, G.UINT4, proc, nums, nums, nums, nums);
		
		for (int i = 0; i < nums.size(); i++) {
			nums.get(i, tmp);
			int v = 3 * i;
			while (v >= 16)
				v -= 16;
			assertEquals(v, tmp.v());
		}
	}
}
