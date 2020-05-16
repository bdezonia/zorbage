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

import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.multidim.ProcedurePaddedMultiDimDataSource;
import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestResampleNearestNeighbor {

	@Test
	public void test1da() {
		double tol = 0;
		Float64Member value = G.DBL.construct();
		MultiDimDataSource<Float64Member> ds = MultiDimStorage.allocate(new long[]{10}, value);
		RampFill.compute(G.DBL, ds.rawData());
		Procedure2<IntegerIndex,Float64Member> proc = new ZeroNdOOB<Float64Algebra, Float64Member>(G.DBL, ds);
		ProcedurePaddedMultiDimDataSource<Float64Algebra,Float64Member> padded =
				new ProcedurePaddedMultiDimDataSource<>(G.DBL, ds, proc);
		MultiDimDataSource<Float64Member> newDs = ResampleNearestNeighbor.compute(G.DBL, new long[]{8}, padded);
		
		assertEquals(8, newDs.dimension(0));

		IntegerIndex idx = new IntegerIndex(newDs.numDimensions());
		idx.set(0, 0);
		newDs.get(idx, value);
		assertEquals(0, value.v(), tol);
		
		idx.set(0, 1);
		newDs.get(idx, value);
		assertEquals(1, value.v(), tol);
		
		idx.set(0, 2);
		newDs.get(idx, value);
		assertEquals(3.0, value.v(), tol);
		
		idx.set(0, 3);
		newDs.get(idx, value);
		assertEquals(4, value.v(), tol);
		
		idx.set(0, 4);
		newDs.get(idx, value);
		assertEquals(5, value.v(), tol);

		idx.set(0, 5);
		newDs.get(idx, value);
		assertEquals(6, value.v(), tol);
		
		idx.set(0, 6);
		newDs.get(idx, value);
		assertEquals(8, value.v(), tol);
		
		idx.set(0, 7);
		newDs.get(idx, value);
		assertEquals(9, value.v(), tol);
	}
	
	@Test
	public void test1db() {
		double tol = 0;
		Float64Member value = G.DBL.construct();
		MultiDimDataSource<Float64Member> ds = MultiDimStorage.allocate(new long[]{8}, value);
		RampFill.compute(G.DBL, ds.rawData());
		Procedure2<IntegerIndex,Float64Member> proc = new ZeroNdOOB<Float64Algebra, Float64Member>(G.DBL, ds);
		ProcedurePaddedMultiDimDataSource<Float64Algebra,Float64Member> padded =
				new ProcedurePaddedMultiDimDataSource<>(G.DBL, ds, proc);
		MultiDimDataSource<Float64Member> newDs = ResampleNearestNeighbor.compute(G.DBL, new long[]{10}, padded);
		
		assertEquals(10, newDs.dimension(0));

		IntegerIndex idx = new IntegerIndex(newDs.numDimensions());
		idx.set(0, 0);
		newDs.get(idx, value);
		assertEquals(0, value.v(), tol);
		
		idx.set(0, 1);
		newDs.get(idx, value);
		assertEquals(1, value.v(), tol);
		
		idx.set(0, 2);
		newDs.get(idx, value);
		assertEquals(2, value.v(), tol);
		
		idx.set(0, 3);
		newDs.get(idx, value);
		assertEquals(2, value.v(), tol);
		
		idx.set(0, 4);
		newDs.get(idx, value);
		assertEquals(3, value.v(), tol);

		idx.set(0, 5);
		newDs.get(idx, value);
		assertEquals(4, value.v(), tol);
		
		idx.set(0, 6);
		newDs.get(idx, value);
		assertEquals(5, value.v(), tol);
		
		idx.set(0, 7);
		newDs.get(idx, value);
		assertEquals(5, value.v(), tol);
		
		idx.set(0, 8);
		newDs.get(idx, value);
		assertEquals(6, value.v(), tol);
		
		idx.set(0, 9);
		newDs.get(idx, value);
		assertEquals(7, value.v(), tol);
	}
}
