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

import static org.junit.Assert.assertTrue;

import nom.bdezonia.zorbage.algebra.*;
import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestParallelTransform2 {

	@Test
	public void testFloats()
	{
		test(G.DBL);
	}

	@Test
	public void testComplexes() {
		test(G.CDBL);
	}
	
	@Test
	public void testQuats() {
		test(G.QDBL);
	}
	
	@Test
	public void testOcts() {
		test(G.ODBL);
	}

	// an algorithm that applies a Algebra's sin() op to a list of any type that
	// supports sin()
	
	private <T extends Algebra<T,U> & Trigonometric<U> & Random<U>, U extends Allocatable<U>>
		void test(T algebra)
	{
		// generic allocation
		IndexedDataSource<U> a = ArrayStorage.allocate(algebra.construct(), 100);
		
		// set values of storage to random values whose components are in [0,1)
		ParallelTransform1.compute(algebra, algebra.random(), a);
		
		// transform each input[i] value to be the sin(input[i])
		ParallelTransform2.compute(algebra, algebra, algebra.sin(), a, a);

		U value = algebra.construct();
		for (long i = 0; i < a.size(); i++) {
			a.get(i, value);
			// NOTE: be aware is is possible but unlikely that this test could fail for
			// comp, quat, and oct.
			assertTrue(!algebra.isZero().call(value));
		}
	}
}
