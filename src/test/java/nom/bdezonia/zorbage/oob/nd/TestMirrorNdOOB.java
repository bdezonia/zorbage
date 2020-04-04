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
package nom.bdezonia.zorbage.oob.nd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.multidim.ProcedurePaddedMultiDimDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMirrorNdOOB {

	@Test
	public void test1() {
		SignedInt32Member value = G.INT32.construct();
		MultiDimDataSource<SignedInt32Member> ds = MultiDimStorage.allocate(new long[] {3,3}, value);
		MirrorNdOOB<SignedInt32Member> oobProc =
				new MirrorNdOOB<SignedInt32Member>(ds);
		ProcedurePaddedMultiDimDataSource<SignedInt32Algebra, SignedInt32Member> padded =
				new ProcedurePaddedMultiDimDataSource<>(G.INT32, ds, oobProc);
		IntegerIndex index = new IntegerIndex(ds.numDimensions());

		// set the original 3 x 3 values
		
		value.setV(1);
		index.set(0, 0);
		index.set(1, 0);
		ds.set(index, value);

		value.setV(2);
		index.set(0, 1);
		index.set(1, 0);
		ds.set(index, value);

		value.setV(3);
		index.set(0, 2);
		index.set(1, 0);
		ds.set(index, value);

		value.setV(4);
		index.set(0, 0);
		index.set(1, 1);
		ds.set(index, value);

		value.setV(5);
		index.set(0, 1);
		index.set(1, 1);
		ds.set(index, value);

		value.setV(6);
		index.set(0, 2);
		index.set(1, 1);
		ds.set(index, value);

		value.setV(7);
		index.set(0, 0);
		index.set(1, 2);
		ds.set(index, value);

		value.setV(8);
		index.set(0, 1);
		index.set(1, 2);
		ds.set(index, value);

		value.setV(9);
		index.set(0, 2);
		index.set(1, 2);
		ds.set(index, value);

		// now test row 1 extending left and right as needed
		
		index.set(0, 0);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(0, 1);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(0, 2);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(0, -1);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(0, -2);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(0, -3);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(0, -4);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(0, -5);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(0, -6);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(0, 3);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(0, 4);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(0, 5);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(0, 6);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(0, 7);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(0, 8);
		index.set(1, 0);
		padded.get(index, value);
		assertEquals(3, value.v());

		// now test row 2 extending left and right as needed
		
		index.set(0, 0);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(0, 1);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(0, 2);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(0, -1);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(0, -2);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(0, -3);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(0, -4);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(0, -5);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(0, -6);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(0, 3);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(0, 4);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(0, 5);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(0, 6);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(0, 7);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(0, 8);
		index.set(1, 1);
		padded.get(index, value);
		assertEquals(6, value.v());

		// now test row 3 extending left and right as needed
		
		index.set(0, 0);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(0, 1);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(0, 2);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(0, -1);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(0, -2);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(0, -3);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(0, -4);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(0, -5);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(0, -6);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(0, 3);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(0, 4);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(0, 5);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(0, 6);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(0, 7);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(0, 8);
		index.set(1, 2);
		padded.get(index, value);
		assertEquals(9, value.v());
		
		// now test col 1 extending up and down as needed
		
		index.set(1, 0);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, 1);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(1, 2);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(1, -1);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, -2);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(1, -3);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(1, -4);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(1, -5);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(1, -6);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, 3);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(1, 4);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(1, 5);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, 6);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, 7);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(4, value.v());

		index.set(1, 8);
		index.set(0, 0);
		padded.get(index, value);
		assertEquals(7, value.v());
		
		// now test col 2 extending up and down as needed
		
		index.set(1, 0);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(1, 1);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(1, 2);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(1, -1);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(1, -2);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(1, -3);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(1, -4);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(1, -5);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(1, -6);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(1, 3);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(8, value.v());

		index.set(1, 4);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(1, 5);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(1, 6);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(2, value.v());

		index.set(1, 7);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(5, value.v());

		index.set(1, 8);
		index.set(0, 1);
		padded.get(index, value);
		assertEquals(8, value.v());
		
		// now test col 3 extending up and down as needed

		index.set(1, 0);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, 1);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(1, 2);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(1, -1);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, -2);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(1, -3);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(1, -4);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(1, -5);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(1, -6);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, 3);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(9, value.v());

		index.set(1, 4);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(1, 5);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, 6);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, 7);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(6, value.v());

		index.set(1, 8);
		index.set(0, 2);
		padded.get(index, value);
		assertEquals(9, value.v());
		
		// now test the corner cases

		index.set(1, -1);
		index.set(0, -1);
		padded.get(index, value);
		assertEquals(1, value.v());

		index.set(1, -1);
		index.set(0, 3);
		padded.get(index, value);
		assertEquals(3, value.v());

		index.set(1, 3);
		index.set(0, -1);
		padded.get(index, value);
		assertEquals(7, value.v());

		index.set(1, 3);
		index.set(0, 3);
		padded.get(index, value);
		assertEquals(9, value.v());
	}
}
