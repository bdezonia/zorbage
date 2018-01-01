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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Rand;
import nom.bdezonia.zorbage.procedure.Sin;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.storage.linear.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTransform {

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

	// an algorithm that applies Sin() op to a list of any type that
	// supports sin()
	
	private <T extends Group<T,U> & Trigonometric<U> & Random<U>, U>
		void test(T group)
	{
		// generic allocation
		IndexedDataSource<?,U> a = ArrayStorage.allocate(100, group.construct());
		
		// set values of storage to random doubles between 0 and 1
		Rand<T, U> randOp = new Rand<T,U>(group);
		Generate.compute(group, randOp, a);
		
		// transform each input[i] value to be the sin(input[i])
		Sin<T, U> sinOp = new Sin<T,U>(group);
		Transform.compute(group, sinOp, a);
		
		assertTrue(true);
	}
}
