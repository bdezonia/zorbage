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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.impl.Ramp;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Group;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFill {

	@Test
	public void test() {
		
		long size = 10000;
		SignedInt32Member type = new SignedInt32Member();
		IndexedDataSource<?, SignedInt32Member> data = Storage.allocate(size, type);
		assertEquals(size, data.size());
		Ramp<SignedInt32Group, SignedInt32Member> ramp1 = new Ramp<SignedInt32Group, SignedInt32Member>(G.INT32, new SignedInt32Member(-25), new SignedInt32Member(3));
		Fill.compute(G.INT32, data, ramp1);
		for (long i = 0; i < size; i++) {
			data.get(i, type);
			assertEquals(-25+3*i, type.v());
		}
		Ramp<SignedInt32Group, SignedInt32Member> ramp2 = new Ramp<SignedInt32Group, SignedInt32Member>(G.INT32, new SignedInt32Member(300), new SignedInt32Member(-6));
		Fill.compute(G.INT32, data, ramp2);
		for (long i = 0; i < size; i++) {
			data.get(i, type);
			assertEquals(300-6*i, type.v());
		}

	}
	
	@Test
	public void test2() {

		Float64Member type = new Float64Member();
		IndexedDataSource<?, Float64Member> data = Storage.allocate(1000, type);
		Fill.compute(G.DBL, data, G.DBL.zero());
		Fill.compute(G.DBL, data, G.DBL.unity());
		Fill.compute(G.DBL, data, G.DBL.minBound());
		Fill.compute(G.DBL, data, G.DBL.maxBound());
		Fill.compute(G.DBL, data, G.DBL.E());
		Fill.compute(G.DBL, data, G.DBL.PI());
		Fill.compute(G.DBL, data, G.DBL.random());
		Fill.compute(G.DBL, data, G.DBL.nan());
		Fill.compute(G.DBL, data, new Float64Member(17.4));
		Fill.compute(G.DBL, data, G.DBL.infinite());
		Fill.compute(G.DBL, data, G.DBL.negInfinite());
		assertTrue(true);

	}
}
