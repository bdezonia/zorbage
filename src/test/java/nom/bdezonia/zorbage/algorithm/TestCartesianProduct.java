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

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCartesianProduct {

	@Test
	public void test1() {
		
		IndexedDataSource<Float64Member> a = ArrayStorage.allocateDoubles(new double[] {1,2,3});
		IndexedDataSource<Float64Member> b = ArrayStorage.allocateDoubles(new double[] {4,5,6});
		IndexedDataSource<Float64Member> result = ArrayStorage.allocateDoubles(new double[9]);
		Procedure3<Float64Member, Float64Member, Float64Member> proc =
				new Procedure3<Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member a, Float64Member b, Float64Member c) {
				c.setV(a.v() * b.v());
			}
		};
		CartesianProduct.compute(G.DBL, G.DBL, G.DBL, proc, a, b, result);
		Float64Member val = G.DBL.construct();
		result.get(0, val);
		assertEquals(4, val.v(), 0);
		result.get(1, val);
		assertEquals(5, val.v(), 0);
		result.get(2, val);
		assertEquals(6, val.v(), 0);
		result.get(3, val);
		assertEquals(8, val.v(), 0);
		result.get(4, val);
		assertEquals(10, val.v(), 0);
		result.get(5, val);
		assertEquals(12, val.v(), 0);
		result.get(6, val);
		assertEquals(12, val.v(), 0);
		result.get(7, val);
		assertEquals(15, val.v(), 0);
		result.get(8, val);
		assertEquals(18, val.v(), 0);
	}

	@Test
	public void test2() {
		
		Float64VectorMember a = new Float64VectorMember(new double[] {1,2,3});
		Float64VectorMember b = new Float64VectorMember(new double[] {4,5,6});
		Float64MatrixMember result = new Float64MatrixMember(3, 3, new double[9]);
		Procedure3<Float64Member, Float64Member, Float64Member> proc =
				new Procedure3<Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member a, Float64Member b, Float64Member c) {
				c.setV(a.v() * b.v());
			}
		};
		CartesianProduct.compute(G.DBL, G.DBL, G.DBL, proc, a, b, result);
		Float64Member val = G.DBL.construct();
		result.v(0, 0, val);
		assertEquals(4, val.v(), 0);
		result.v(0, 1, val);
		assertEquals(5, val.v(), 0);
		result.v(0, 2, val);
		assertEquals(6, val.v(), 0);
		result.v(1, 0, val);
		assertEquals(8, val.v(), 0);
		result.v(1, 1, val);
		assertEquals(10, val.v(), 0);
		result.v(1, 2, val);
		assertEquals(12, val.v(), 0);
		result.v(2, 0, val);
		assertEquals(12, val.v(), 0);
		result.v(2, 1, val);
		assertEquals(15, val.v(), 0);
		result.v(2, 2, val);
		assertEquals(18, val.v(), 0);
	}
}
