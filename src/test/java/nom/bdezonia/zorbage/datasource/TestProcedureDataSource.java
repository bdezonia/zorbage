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

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestProcedureDataSource {

	@Test
	public void test() {
		
		Float64Member result = G.DBL.construct();

		// a procedure that returns the square of its input
		
		Procedure2<Long,Float64Member> proc =
				new Procedure2<Long, Float64Member>()
		{
			@Override
			public void call(Long a, Float64Member b) {
				b.setV(a*a);
			}
		};

		// setup the computed data source
		
		IndexedDataSource<Float64Member> source = new ProcedureDataSource<Float64Member>(proc);
		
		// test size
		
		assertEquals(Long.MAX_VALUE, source.size());

		// specify the area of interest we want from the source: the consecutive squares 10^2 to 30^2
		
		IndexedDataSource<Float64Member> sublist = new TrimmedDataSource<Float64Member>(source, 10, 20);
		
		// calculate the mean of the function of the given range
		
		Mean.compute(G.DBL, sublist, result);
		
		assertEquals(413.5, result.v(), 0);

		// make sure you can't set values
		
		try {
			source.set(4, result);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	
		// test duplicate()
		
		IndexedDataSource<Float64Member> src2 = source.duplicate();
		
		sublist = new TrimmedDataSource<Float64Member>(src2, 10, 20);

		result.setV(0);
		
		Mean.compute(G.DBL, sublist, result);
		
		assertEquals(413.5, result.v(), 0);
	}
}
