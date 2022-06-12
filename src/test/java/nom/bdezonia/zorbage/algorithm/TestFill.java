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
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFill {

	@Test
	public void test1() {
		Float64Member type = new Float64Member();
		
		IndexedDataSource<Float64Member> data = Storage.allocate(type, 1000);
		
		Fill.compute(G.DBL, new Float64Member(17.4), data);
		data.get(999, type);
		assertEquals(17.4, type.v(), 0);
	}
	
	@Test
	public void test2() {

		Float64Member type = new Float64Member();
		
		IndexedDataSource<Float64Member> data = Storage.allocate(type, 1000);
		
		Fill.compute(G.DBL, G.DBL.zero(), data);
		data.get(999, type);
		assertEquals(0, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.unity(), data);
		data.get(999, type);
		assertEquals(1, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.minBound(), data);
		data.get(999, type);
		assertEquals(-Double.MAX_VALUE, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.maxBound(), data);
		data.get(999, type);
		assertEquals(Double.MAX_VALUE, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.E(), data);
		data.get(999, type);
		assertEquals(2.7182818284590452353602874713526624, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.PI(), data);
		data.get(999, type);
		assertEquals(3.1415926535897932384626433832795028, type.v(), 0);
		
		Fill.compute(G.DBL, G.DBL.random(), data);
		
		Fill.compute(G.DBL, G.DBL.nan(), data);
		data.get(999, type);
		assertTrue(Double.isNaN(type.v()));
		
		Fill.compute(G.DBL, G.DBL.infinite(), data);
		data.get(999, type);
		assertTrue(Double.isInfinite(type.v()));
		assertTrue(type.v() > 0);
		
		Fill.compute(G.DBL, G.DBL.negInfinite(), data);
		data.get(999, type);
		assertTrue(Double.isInfinite(type.v()));
		assertTrue(type.v() < 0);

	}
	
}
