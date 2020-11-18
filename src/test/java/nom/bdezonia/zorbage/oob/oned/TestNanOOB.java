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
package nom.bdezonia.zorbage.oob.oned;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

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
public class TestNanOOB {

	@Test
	public void test1() {
		
		Float64Member val = G.DBL.construct();
		IndexedDataSource<Float64Member> vals = Storage.allocate(G.DBL.construct(), new double[] {1,2,3});
		Procedure2<Long, Float64Member> oobProc = new NanOOB<Float64Algebra,Float64Member>(G.DBL, vals.size());
		ProcedurePaddedDataSource<Float64Algebra,Float64Member> pd = new ProcedurePaddedDataSource<Float64Algebra, Float64Member>(G.DBL, vals, oobProc);
		
		pd.get(0, val);
		assertEquals(1, val.v(), 0);
		
		pd.get(1, val);
		assertEquals(2, val.v(), 0);
		
		pd.get(2, val);
		assertEquals(3, val.v(), 0);
		
		pd.get(-1, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-2, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-3, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-4, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-5, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-6, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-7, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-8, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(-9, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(3, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(4, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(5, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(6, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(7, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(8, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(9, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(10, val);
		assertTrue(Double.isNaN(val.v()));
		
		pd.get(11, val);
		assertTrue(Double.isNaN(val.v()));
	}
}
