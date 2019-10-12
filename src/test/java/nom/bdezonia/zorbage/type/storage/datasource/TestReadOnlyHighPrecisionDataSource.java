/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.datasource;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestReadOnlyHighPrecisionDataSource {

	@Test
	public void test1() {
		HighPrecisionMember result = G.HP.construct();
		IndexedDataSource<Float64Member> doubles = ArrayStorage.allocateDoubles(new double[] {0,1,2,3,4,5,6,7,8,9});
		ReadOnlyHighPrecisionDataSource<Float64Algebra,Float64Member> hps =
				new ReadOnlyHighPrecisionDataSource<>(G.DBL, doubles);
		Sum.compute(G.HP, hps, result);
		assertEquals(BigDecimal.valueOf(45.0), result.v());
		
		assertEquals(doubles.size(), hps.size());

		hps.get(4, result);
		assertEquals(BigDecimal.valueOf(4.0), result.v());
		
		try {
			hps.set(0, result);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		ReadOnlyHighPrecisionDataSource<Float64Algebra,Float64Member> dup = hps.duplicate();
		
		assertNotNull(dup);
		assertEquals(hps.size(), dup.size());
		HighPrecisionMember a = G.HP.construct();
		HighPrecisionMember b = G.HP.construct();
		for (long i = 0; i < dup.size(); i++) {
			hps.get(i, a);
			dup.get(i, b);
			assertEquals(a.v(), b.v());
		}
		
	}

}
