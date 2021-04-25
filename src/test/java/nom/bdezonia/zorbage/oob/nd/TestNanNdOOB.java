/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.oob.nd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 *
 * @author Barry DeZonia
 *
 */
public class TestNanNdOOB {

	@Test
	public void test1() {
		Float64Member value = G.DBL.construct();
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(value, new long[] {5,5});
		NanNdOOB<Float64Algebra, Float64Member> oobProc =
				new NanNdOOB<Float64Algebra, Float64Member>(G.DBL, ds);
		ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<>(G.DBL, ds, oobProc);
		value.setV(6);
		Fill.compute(G.DBL, value, ds.rawData());
		IntegerIndex index = new IntegerIndex(ds.numDimensions());

		index.set(0, -1);
		index.set(1, 0);
		padded.get(index, value);
		assertTrue(G.DBL.isNaN().call(value));

		index.set(0, 0);
		index.set(1, -1);
		padded.get(index, value);
		assertTrue(G.DBL.isNaN().call(value));

		index.set(0, 5);
		index.set(1, 0);
		padded.get(index, value);
		assertTrue(G.DBL.isNaN().call(value));

		index.set(0, 0);
		index.set(1, 5);
		padded.get(index, value);
		assertTrue(G.DBL.isNaN().call(value));

		index.set(0, 0);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(6, value.v(), 0);
	}
}
