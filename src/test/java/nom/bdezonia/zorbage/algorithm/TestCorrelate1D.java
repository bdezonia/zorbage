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

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.oob.oned.ZeroOOB;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ProcedurePaddedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestCorrelate1D {

	@Test
	public void test1() {
		
		IndexedDataSource<Float64Member> filter = Storage.allocate(G.DBL.construct(), 
				new double[] {1.0/3, 1.0/3, 1.0/3}
			);
	
		IndexedDataSource<Float64Member> vals = Storage.allocate(G.DBL.construct(), 
					new double[] {5,4,2,3,7,4,6,5,3,6}
				);
		
		IndexedDataSource<Float64Member> results = Storage.allocate(G.DBL.construct(), 
				new double[(int)vals.size()]
			);
		
		Procedure2<Long, Float64Member> oobProc = new ZeroOOB<>(G.DBL, vals.size());
		
		ProcedurePaddedDataSource<Float64Algebra, Float64Member> paddedVals =
				new ProcedurePaddedDataSource<Float64Algebra, Float64Member>(G.DBL, vals, oobProc);
		
		Correlate1D.compute(G.DBL, filter, paddedVals, results);
		
		Float64Member value = G.DBL.construct();
		
		results.get(0, value);
		assertEquals(3, value.v(), 0.00001);
		
		results.get(1, value);
		assertEquals(3.666666666, value.v(), 0.00001);

		results.get(2, value);
		assertEquals(3, value.v(), 0.00001);

		results.get(3, value);
		assertEquals(4, value.v(), 0.00001);

		results.get(4, value);
		assertEquals(4.666666666, value.v(), 0.00001);

		results.get(5, value);
		assertEquals(5.666666666, value.v(), 0.00001);

		results.get(6, value);
		assertEquals(5, value.v(), 0.00001);

		results.get(7, value);
		assertEquals(4.666666666, value.v(), 0.00001);

		results.get(8, value);
		assertEquals(4.666666666, value.v(), 0.00001);

		results.get(9, value);
		assertEquals(3, value.v(), 0.00001);
	}

	@Test
	public void test2() {
		
		IndexedDataSource<Float64Member> filter = Storage.allocate(G.DBL.construct(), 
				new double[] {1, 2, 3}
			);
	
		IndexedDataSource<Float64Member> vals = Storage.allocate(G.DBL.construct(), 
					new double[] {5,4,2,3,7,4,6,5,3,6}
				);
		
		IndexedDataSource<Float64Member> results = Storage.allocate(G.DBL.construct(), 
				new double[(int)vals.size()]
			);
		
		Procedure2<Long, Float64Member> oobProc = new ZeroOOB<>(G.DBL, vals.size());
		
		ProcedurePaddedDataSource<Float64Algebra, Float64Member> paddedVals =
				new ProcedurePaddedDataSource<Float64Algebra, Float64Member>(G.DBL, vals, oobProc);
		
		Correlate1D.compute(G.DBL, filter, paddedVals, results);
		
		Float64Member value = G.DBL.construct();
		
		results.get(0, value);
		assertEquals(22, value.v(), 0);
		
		results.get(1, value);
		assertEquals(19, value.v(), 0);

		results.get(2, value);
		assertEquals(17, value.v(), 0);

		results.get(3, value);
		assertEquals(29, value.v(), 0);

		results.get(4, value);
		assertEquals(29, value.v(), 0);

		results.get(5, value);
		assertEquals(33, value.v(), 0);

		results.get(6, value);
		assertEquals(31, value.v(), 0);

		results.get(7, value);
		assertEquals(25, value.v(), 0);

		results.get(8, value);
		assertEquals(29, value.v(), 0);

		results.get(9, value);
		assertEquals(15, value.v(), 0);
	}
}
