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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform4 {

	@Test
	public void test() {
		Float64Member value = G.DBL.construct();
		IndexedDataSource<?,Float64Member> a = ArrayStorage.allocateDoubles(
				new double[] {1,2,3,4,5});
		Procedure4<Float64Member,Float64Member,Float64Member,Float64Member> proc =
				new Procedure4<Float64Member, Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member a, Float64Member b, Float64Member c, Float64Member d) {
				double val = a.v() + 2*b.v() + 3*c.v();
				d.setV(val);
			}
		};
		Transform4.compute(G.DBL, proc, a);
		a.get(0, value);
		assertEquals(6, value.v(), 0);
		a.get(1, value);
		assertEquals(12, value.v(), 0);
		a.get(2, value);
		assertEquals(18, value.v(), 0);
		a.get(3, value);
		assertEquals(24, value.v(), 0);
		a.get(4, value);
		assertEquals(30, value.v(), 0);
	}
}
