/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.predicate.LessThanEqualConstant;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReplaceIf {
	
	@Test
	public void test() {
		
		IndexedDataSource<Float64Member> a = Storage.allocate(G.DBL.construct(), 
				new double[] {1,2,1,4,2,6,1,7,1});

		Function1<Boolean,Float64Member> cond =
				new LessThanEqualConstant<Float64Algebra, Float64Member>(G.DBL, new Float64Member(2));
		Float64Member value = G.DBL.construct();
		
		ReplaceIf.compute(G.DBL, cond, new Float64Member(103), a);
		assertEquals(9, a.size());
		a.get(0, value);
		assertEquals(103, value.v(), 0);
		a.get(1, value);
		assertEquals(103, value.v(), 0);
		a.get(2, value);
		assertEquals(103, value.v(), 0);
		a.get(3, value);
		assertEquals(4, value.v(), 0);
		a.get(4, value);
		assertEquals(103, value.v(), 0);
		a.get(5, value);
		assertEquals(6, value.v(), 0);
		a.get(6, value);
		assertEquals(103, value.v(), 0);
		a.get(7, value);
		assertEquals(7, value.v(), 0);
		a.get(8, value);
		assertEquals(103, value.v(), 0);
	}
}
