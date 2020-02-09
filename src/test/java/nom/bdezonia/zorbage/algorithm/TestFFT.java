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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.TrimmedDataSource;

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
		IndexedDataSource<SignedInt32Member> poly1 =
				Storage.allocate(7, int32);
		IndexedDataSource<SignedInt32Member> poly2 =
				Storage.allocate(7, int32);
		SignedInt32Member start1 = new SignedInt32Member(3);
		SignedInt32Member start2 = new SignedInt32Member(-5);
		SignedInt32Member inc1 = new SignedInt32Member(-1);
		SignedInt32Member inc2 = new SignedInt32Member(1);
		TrimmedDataSource<SignedInt32Member> tr1 = new TrimmedDataSource<>(poly1, 0, 3);
		TrimmedDataSource<SignedInt32Member> tr2 = new TrimmedDataSource<>(poly2, 0, 3);
		RampFill.compute(G.INT32, start1, inc1, tr1);
		RampFill.compute(G.INT32, start2, inc2, tr2);
		long n = FFT.enclosingPowerOf2(poly1.size());
		IndexedDataSource<ComplexFloat64Member> data1 =
				Storage.allocate(n, cdbl);
		IndexedDataSource<ComplexFloat64Member> data2 =
				Storage.allocate(n, cdbl);
		DataConvert.compute(G.INT32, G.CDBL, poly1, data1);
		DataConvert.compute(G.INT32, G.CDBL, poly2, data2);
		FFT.compute(G.CDBL, G.DBL, data1, data1); // testing in place algo
		FFT.compute(G.CDBL, G.DBL, data2, data2); // testing in place algo
		ElementwiseOperation.compute(G.CDBL, G.CDBL.multiply(), data1, data2, data1);
		InvFFT.compute(G.CDBL, G.DBL, data1, data1); // testing in place algo
		IndexedDataSource<SignedInt32Member> result =
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
	
	@Test
	public void testEnclosingCalcs() {
		try {
			FFT.enclosingPowerOf2(-1);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			FFT.enclosingPowerOf2(0);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
		
		assertEquals(1, FFT.enclosingPowerOf2(1));
		
		assertEquals(2, FFT.enclosingPowerOf2(2));
		
		assertEquals(4, FFT.enclosingPowerOf2(3));
		assertEquals(4, FFT.enclosingPowerOf2(4));

		assertEquals(8, FFT.enclosingPowerOf2(5));
		assertEquals(8, FFT.enclosingPowerOf2(7));
		assertEquals(8, FFT.enclosingPowerOf2(8));

		assertEquals(16, FFT.enclosingPowerOf2(9));
		assertEquals(16, FFT.enclosingPowerOf2(15));
		assertEquals(16, FFT.enclosingPowerOf2(16));

		assertEquals(32, FFT.enclosingPowerOf2(17));
		assertEquals(32, FFT.enclosingPowerOf2(31));
		assertEquals(32, FFT.enclosingPowerOf2(32));

		assertEquals(64, FFT.enclosingPowerOf2(33));
		assertEquals(64, FFT.enclosingPowerOf2(63));
		assertEquals(64, FFT.enclosingPowerOf2(64));

		assertEquals(1L<<62, FFT.enclosingPowerOf2((1L<<62)-1));
		assertEquals(1L<<62, FFT.enclosingPowerOf2(1L<<62));
		try {
			FFT.enclosingPowerOf2((1L<<62)+1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testingNotes() {
		// Note that I used apache commons-math3-3.6.1's standard FFT transform algorithm
		// and generated transform data matching testKnownValues() below and compared
		// zorbage's values to apache's. The results for each of the 16 terms (32 components
		// total) matched to 14 decimal places.
	}

	@Test
	public void testKnownValues() {
		double tol = 0.00000000000001;
		ComplexFloat64Member value = G.CDBL.construct();
		IndexedDataSource<ComplexFloat64Member> a = Storage.allocate(16, value);
		value.setR(1);
		value.setI(2);
		a.set(0, value);
		value.setR(-1);
		value.setI(3);
		a.set(1, value);
		value.setR(4);
		value.setI(-2);
		a.set(2, value);
		value.setR(-7);
		value.setI(-3);
		a.set(3, value);
		value.setR(2);
		value.setI(4);
		a.set(4, value);
		value.setR(1);
		value.setI(-8);
		a.set(5, value);
		value.setR(-4);
		value.setI(8);
		a.set(6, value);
		value.setR(0);
		value.setI(0);
		a.set(7, value);
		value.setR(15);
		value.setI(1);
		a.set(8, value);
		value.setR(9);
		value.setI(-6);
		a.set(9, value);
		value.setR(10);
		value.setI(0);
		a.set(10, value);
		value.setR(18);
		value.setI(23);
		a.set(11, value);
		value.setR(-5);
		value.setI(11);
		a.set(12, value);
		value.setR(6);
		value.setI(6);
		a.set(13, value);
		value.setR(4);
		value.setI(0);
		a.set(14, value);
		value.setR(0);
		value.setI(9);
		a.set(15, value);
		
		IndexedDataSource<ComplexFloat64Member> b = Storage.allocate(a.size(), value);
		
		FFT.compute(G.CDBL, G.DBL, a, b); // testing NOT in place algo
		
		b.get(0, value);
		assertFalse(Math.abs(value.r() - 1) < tol && Math.abs(value.i() - 2) < tol);
		b.get(1, value);
		assertFalse(Math.abs(value.r() - -1) < tol && Math.abs(value.i() - 3) < tol);
		b.get(2, value);
		assertFalse(Math.abs(value.r() - 4) < tol && Math.abs(value.i() - -2) < tol);
		b.get(3, value);
		assertFalse(Math.abs(value.r() - -7) < tol && Math.abs(value.i() - -3) < tol);
		b.get(4, value);
		assertFalse(Math.abs(value.r() - 2) < tol && Math.abs(value.i() - 4) < tol);
		b.get(5, value);
		assertFalse(Math.abs(value.r() - 1) < tol && Math.abs(value.i() - -8) < tol);
		b.get(6, value);
		assertFalse(Math.abs(value.r() - -4) < tol && Math.abs(value.i() - 8) < tol);
		b.get(7, value);
		assertFalse(Math.abs(value.r() - 0) < tol && Math.abs(value.i() - 0) < tol);
		b.get(8, value);
		assertFalse(Math.abs(value.r() - 15) < tol && Math.abs(value.i() - 1) < tol);
		b.get(9, value);
		assertFalse(Math.abs(value.r() - 9) < tol && Math.abs(value.i() - -6) < tol);
		b.get(10, value);
		assertFalse(Math.abs(value.r() - 10) < tol && Math.abs(value.i() - 0) < tol);
		b.get(11, value);
		assertFalse(Math.abs(value.r() - 18) < tol && Math.abs(value.i() - 23) < tol);
		b.get(12, value);
		assertFalse(Math.abs(value.r() - -5) < tol && Math.abs(value.i() - 11) < tol);
		b.get(13, value);
		assertFalse(Math.abs(value.r() - 6) < tol && Math.abs(value.i() - 6) < tol);
		b.get(14, value);
		assertFalse(Math.abs(value.r() - 4) < tol && Math.abs(value.i() - 0) < tol);
		b.get(15, value);
		assertFalse(Math.abs(value.r() - 0) < tol && Math.abs(value.i() - 9) < tol);

		IndexedDataSource<ComplexFloat64Member> c = Storage.allocate(b.size(), value);
		
		InvFFT.compute(G.CDBL, G.DBL, b, c); // testing NOT in place algo
		
		c.get(0, value);
		assertEquals(1, value.r(), tol);
		assertEquals(2, value.i(), tol);
		c.get(1, value);
		assertEquals(-1, value.r(), tol);
		assertEquals(3, value.i(), tol);
		c.get(2, value);
		assertEquals(4, value.r(), tol);
		assertEquals(-2, value.i(), tol);
		c.get(3, value);
		assertEquals(-7, value.r(), tol);
		assertEquals(-3, value.i(), tol);
		c.get(4, value);
		assertEquals(2, value.r(), tol);
		assertEquals(4, value.i(), tol);
		c.get(5, value);
		assertEquals(1, value.r(), tol);
		assertEquals(-8, value.i(), tol);
		c.get(6, value);
		assertEquals(-4, value.r(), tol);
		assertEquals(8, value.i(), tol);
		c.get(7, value);
		assertEquals(0, value.r(), tol);
		assertEquals(0, value.i(), tol);
		c.get(8, value);
		assertEquals(15, value.r(), tol);
		assertEquals(1, value.i(), tol);
		c.get(9, value);
		assertEquals(9, value.r(), tol);
		assertEquals(-6, value.i(), tol);
		c.get(10, value);
		assertEquals(10, value.r(), tol);
		assertEquals(0, value.i(), tol);
		c.get(11, value);
		assertEquals(18, value.r(), tol);
		assertEquals(23, value.i(), tol);
		c.get(12, value);
		assertEquals(-5, value.r(), tol);
		assertEquals(11, value.i(), tol);
		c.get(13, value);
		assertEquals(6, value.r(), tol);
		assertEquals(6, value.i(), tol);
		c.get(14, value);
		assertEquals(4, value.r(), tol);
		assertEquals(0, value.i(), tol);
		c.get(15, value);
		assertEquals(0, value.r(), tol);
		assertEquals(9, value.i(), tol);
	}
}
