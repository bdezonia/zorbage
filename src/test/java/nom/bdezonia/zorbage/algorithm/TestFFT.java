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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.impl.Ramp;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Group;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFFT {

	@Test
	public void testPolyMult() {
		SignedInt32Member int32 = new SignedInt32Member();
		ComplexFloat64Member cdbl = new ComplexFloat64Member();
		IndexedDataSource<?,SignedInt32Member> poly1 =
				Storage.allocate(7, int32);
		IndexedDataSource<?,SignedInt32Member> poly2 =
				Storage.allocate(7, int32);
		SignedInt32Member start1 = new SignedInt32Member(3);
		SignedInt32Member start2 = new SignedInt32Member(-5);
		SignedInt32Member inc1 = new SignedInt32Member(-1);
		SignedInt32Member inc2 = new SignedInt32Member(1);
		Ramp<SignedInt32Group, SignedInt32Member> ramp1 =
				new Ramp<SignedInt32Group, SignedInt32Member>(G.INT32,start1,inc1);
		Ramp<SignedInt32Group, SignedInt32Member> ramp2 =
				new Ramp<SignedInt32Group, SignedInt32Member>(G.INT32,start2,inc2);
		FillN.compute(G.INT32, ramp1, 0, 3, poly1);
		FillN.compute(G.INT32, ramp2, 0, 3, poly2);
		long n = FFT.enclosingPowerOf2(poly1.size());
		IndexedDataSource<?,ComplexFloat64Member> data1 =
				Storage.allocate(n, cdbl);
		IndexedDataSource<?,ComplexFloat64Member> data2 =
				Storage.allocate(n, cdbl);
		DataConvert.compute(G.INT32, G.CDBL, poly1, data1);
		DataConvert.compute(G.INT32, G.CDBL, poly2, data2);
		FFT.compute(G.CDBL, data1, data1);
		FFT.compute(G.CDBL, data2, data2);
		Correlate.compute(G.CDBL, data1, data2, data1);
		InvFFT.compute(data1, data1);
		IndexedDataSource<?,SignedInt32Member> result =
				Storage.allocate(n, int32);
		DataConvert.compute(G.CDBL, G.INT32, data1, result);
		result.get(0, int32);
		assertEquals(-15, int32.v());
		result.get(1, int32);
		assertEquals(-22, int32.v());
		result.get(2, int32);
		assertEquals(-22, int32.v());
		result.get(3, int32);
		assertEquals(-10, int32.v());
		result.get(4, int32);
		assertEquals(-3, int32.v());
		for (int i = 5; i < n; i++) {
			result.get(i, int32);
			assertEquals(0, int32.v());
		}
	}
}
