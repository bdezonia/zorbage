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
package nom.bdezonia.zorbage.oob.nd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.multidim.ProcedurePaddedMultiDimDataSource;
import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 *
 * @author Barry DeZonia
 *
 */
public class TestZeroNdOOB {

	@Test
	public void test1() {
		SignedInt32Member value = G.INT32.construct();
		MultiDimDataSource<SignedInt32Member> ds = MultiDimStorage.allocate(new long[] {5,5}, value);
		ZeroNdOOB<SignedInt32Algebra, SignedInt32Member> oobProc =
				new ZeroNdOOB<SignedInt32Algebra, SignedInt32Member>(G.INT32, ds);
		ProcedurePaddedMultiDimDataSource<SignedInt32Algebra, SignedInt32Member> padded =
				new ProcedurePaddedMultiDimDataSource<>(G.INT32, ds, oobProc);
		value.setV(6);
		Fill.compute(G.INT32, value, ds.rawData());
		IntegerIndex index = new IntegerIndex(2);

		index.set(0, -1);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(0, value.v());

		index.set(0, 0);
		index.set(1, -1);
		padded.get(index, value);
		assertEquals(0, value.v());

		index.set(0, 5);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(0, value.v());

		index.set(0, 0);
		index.set(1, 5);
		padded.get(index, value);
		assertEquals(0, value.v());

		index.set(0, 0);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(6, value.v());
	}
}
