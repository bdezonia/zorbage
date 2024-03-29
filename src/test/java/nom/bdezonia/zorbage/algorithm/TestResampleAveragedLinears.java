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

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestResampleAveragedLinears {

	@Test
	public void test1da() {
		double tol = 0.000000000000001;
		Float64Member value = G.DBL.construct();
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(value, new long[]{10});
		RampFill.compute(G.DBL, ds.rawData());
		Procedure2<IntegerIndex,Float64Member> proc =
				new Procedure2<IntegerIndex, Float64Member>()
		{
			@Override
			public void call(IntegerIndex a, Float64Member b) {
				G.DBL.zero().call(b);
			}
		};
		ProcedurePaddedDimensionedDataSource<Float64Algebra,Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<>(G.DBL, ds, proc);
		DimensionedDataSource<Float64Member> newDs = ResampleAveragedLinears.compute(G.DBL, new long[]{8}, padded);
		
		assertEquals(8, newDs.dimension(0));

		IntegerIndex idx = new IntegerIndex(newDs.numDimensions());
		idx.set(0, 0);
		newDs.get(idx, value);
		assertEquals(0*9.0/7.0, value.v(), tol);
		
		idx.set(0, 1);
		newDs.get(idx, value);
		assertEquals(1*9.0/7.0, value.v(), tol);
		
		idx.set(0, 2);
		newDs.get(idx, value);
		assertEquals(2*9.0/7.0, value.v(), tol);
		
		idx.set(0, 3);
		newDs.get(idx, value);
		assertEquals(3*9.0/7.0, value.v(), tol);
		
		idx.set(0, 4);
		newDs.get(idx, value);
		assertEquals(4*9.0/7.0, value.v(), tol);

		idx.set(0, 5);
		newDs.get(idx, value);
		assertEquals(5*9.0/7.0, value.v(), tol);
		
		idx.set(0, 6);
		newDs.get(idx, value);
		assertEquals(6*9.0/7.0, value.v(), tol);
		
		idx.set(0, 7);
		newDs.get(idx, value);
		assertEquals(7*9.0/7.0, value.v(), tol);
	}
	
	@Test
	public void test1db() {
		double tol = 0.000000000000001;
		Float64Member value = G.DBL.construct();
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(value, new long[]{8});
		RampFill.compute(G.DBL, ds.rawData());
		Procedure2<IntegerIndex,Float64Member> proc =
				new Procedure2<IntegerIndex, Float64Member>()
		{
			@Override
			public void call(IntegerIndex a, Float64Member b) {
				G.DBL.zero().call(b);
			}
		};
		ProcedurePaddedDimensionedDataSource<Float64Algebra,Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<>(G.DBL, ds, proc);
		DimensionedDataSource<Float64Member> newDs = ResampleAveragedLinears.compute(G.DBL, new long[]{10}, padded);
		
		assertEquals(10, newDs.dimension(0));

		IntegerIndex idx = new IntegerIndex(newDs.numDimensions());
		idx.set(0, 0);
		newDs.get(idx, value);
		assertEquals(0*7.0/9, value.v(), tol);
		
		idx.set(0, 1);
		newDs.get(idx, value);
		assertEquals(1*7.0/9, value.v(), tol);
		
		idx.set(0, 2);
		newDs.get(idx, value);
		assertEquals(2*7.0/9, value.v(), tol);
		
		idx.set(0, 3);
		newDs.get(idx, value);
		assertEquals(3*7.0/9, value.v(), tol);
		
		idx.set(0, 4);
		newDs.get(idx, value);
		assertEquals(4*7.0/9, value.v(), tol);

		idx.set(0, 5);
		newDs.get(idx, value);
		assertEquals(5*7.0/9, value.v(), tol);
		
		idx.set(0, 6);
		newDs.get(idx, value);
		assertEquals(6*7.0/9, value.v(), tol);
		
		idx.set(0, 7);
		newDs.get(idx, value);
		assertEquals(7*7.0/9, value.v(), tol);
		
		idx.set(0, 8);
		newDs.get(idx, value);
		assertEquals(8*7.0/9, value.v(), tol);
		
		idx.set(0, 9);
		newDs.get(idx, value);
		assertEquals(9*7.0/9, value.v(), tol);
	}
}
