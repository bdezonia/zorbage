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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.condition.LessThanEqualConstant;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReplaceIf {
	
	@Test
	public void test() {
		
		IndexedDataSource<?,Float64Member> a = ArrayStorage.allocateDoubles(
				new double[] {1,2,1,4,2,6,1,7,1});

		Condition<Float64Member> cond =
				new LessThanEqualConstant<Float64Group, Float64Member>(G.DBL, new Float64Member(2));
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
