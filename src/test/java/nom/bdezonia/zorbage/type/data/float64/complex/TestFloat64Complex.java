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
package nom.bdezonia.zorbage.type.data.float64.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt32;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Complex {

	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00000000001;
		
		ComplexFloat64Member a = G.CDBL.construct();
		ComplexFloat64Member b = G.CDBL.construct();
		ComplexFloat64Member c = G.CDBL.construct();
		Float64Member d = G.DBL.construct();

		// G.CDBL.acos();
		
		// G.CDBL.acosh();
		
		// G.CDBL.acot();
		
		// G.CDBL.acoth();
		
		// G.CDBL.acsc();
		
		// G.CDBL.acsch();
		
		// G.CDBL.add();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CDBL.add().call(a, b, c);
		assertEquals(5, c.r(), 0);
		assertEquals(1, c.i(), 0);
		
		// G.CDBL.asec();
		
		// G.CDBL.asech();
		
		// G.CDBL.asin();
		
		// G.CDBL.asinh();
		
		// G.CDBL.assign();
		a.setR(66);
		a.setI(99);
		G.CDBL.assign().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(99,b.i(),0);
		
		// G.CDBL.atan();
		
		// G.CDBL.atanh();
		
		// G.CDBL.cbrt();
		a.setR(8);
		a.setI(0);
		G.CDBL.cbrt().call(a, b);
		assertEquals(2, b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.conjugate();
		a.setR(66);
		a.setI(99);
		G.CDBL.conjugate().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(-99,b.i(),0);
		
		// G.CDBL.construct();
		a = G.CDBL.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);

		// G.CDBL.construct("{14,7}");
		a = G.CDBL.construct("{14,7}");
		assertEquals(14, a.r(), 0);
		assertEquals(7, a.i(), 0);
		
		// G.CDBL.construct(other);
		b = G.CDBL.construct(a);
		assertEquals(14, b.r(), 0);
		assertEquals(7, b.i(), 0);
		
		// G.CDBL.cos();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.cosh();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.cot();
		
		// G.CDBL.coth();
		
		// G.CDBL.csc();
		
		// G.CDBL.csch();
		
		// G.CDBL.divide();
		a = new ComplexFloat64Member(7,3);
		b = new ComplexFloat64Member(3, 0);
		G.CDBL.divide().call(a, b, c);
		assertEquals(7.0/3, c.r(), 0);
		assertEquals(1, c.i(), 0);

		// G.CDBL.E();
		G.CDBL.E().call(a);
		assertEquals(Math.E, a.r(), 0);
		assertEquals(0, a.i(), 0);
		
		// G.CDBL.exp();
		a.setR(4);
		a.setI(0);
		G.CDBL.exp().call(a, b);
		assertEquals(Math.exp(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.expm1();
		a.setR(4);
		a.setI(0);
		G.CDBL.expm1().call(a, b);
		assertEquals(Math.expm1(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.infinite();
		a = G.CDBL.construct();
		assertFalse(G.CDBL.isInfinite().call(a));
		G.CDBL.infinite().call(a);
		assertTrue(G.CDBL.isInfinite().call(a));
		
		// G.CDBL.invert();
		a.setR(14);
		a.setI(-3);
		G.CDBL.invert().call(a, b);
		G.CDBL.unity().call(c);
		G.CDBL.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), tol);
		assertEquals(c.i(), b.i(), tol);
		
		// G.CDBL.isEqual();
		a = new ComplexFloat64Member(44,7);
		b = new ComplexFloat64Member(12,7);
		assertFalse(G.CDBL.isEqual().call(a, b));
		b = G.CDBL.construct(a);
		assertTrue(G.CDBL.isEqual().call(a, b));
		
		// G.CDBL.isInfinite();
		// tested by infinite() test above
		
		// G.CDBL.isNaN();
		// tested by nan() test below
		
		// G.CDBL.isNotEqual();
		a = new ComplexFloat64Member(44,7);
		b = new ComplexFloat64Member(12,7);
		assertTrue(G.CDBL.isNotEqual().call(a, b));
		b = G.CDBL.construct(a);
		assertFalse(G.CDBL.isNotEqual().call(a, b));
		
		// G.CDBL.isZero();
		// tested by zero() test below
		
		// G.CDBL.log();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.log1p();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.multiply();
		a = new ComplexFloat64Member(-8, 1);
		b = new ComplexFloat64Member(-2, 0);
		G.CDBL.multiply().call(a, b, c);
		assertEquals(16, c.r(), 0);
		assertEquals(-2, c.i(), 0);
		
		// G.CDBL.nan();
		a = new ComplexFloat64Member(44,7);
		assertFalse(G.CDBL.isNaN().call(a));
		G.CDBL.nan().call(a);
		assertTrue(G.CDBL.isNaN().call(a));
		
		// G.CDBL.negate();
		a = new ComplexFloat64Member(44,7);
		G.CDBL.negate().call(a, b);
		assertEquals(-44, b.r(), 0);
		assertEquals(-7, b.i(), 0);
		
		// G.CDBL.norm();
		a = new ComplexFloat64Member(3,4);
		G.CDBL.norm().call(a, d);
		assertEquals(5,d.v(),0);
		
		// G.CDBL.PI();
		G.CDBL.PI().call(a);
		assertEquals(Math.PI, a.r(), 0);
		assertEquals(0, a.i(), 0);
		
		// G.CDBL.pow();
		a = new ComplexFloat64Member(-7,-4);
		b = new ComplexFloat64Member(2,0);
		G.CDBL.pow().call(a, b, c);
		ComplexFloat64Member t = G.CDBL.construct();
		G.CDBL.multiply().call(a, a, t);
		assertEquals(t.r(), c.r(), tol);
		assertEquals(t.i(), c.i(), tol);
		
		// G.CDBL.power();
		a = new ComplexFloat64Member(-7,-4);
		G.CDBL.power().call(2, a, b);
		G.CDBL.multiply().call(a, a, t);
		assertEquals(t.r(), b.r(), tol);
		assertEquals(t.i(), b.i(), tol);
		
		// G.CDBL.random();
		// TODO: not sure how to test
		G.CDBL.random().call(a);
		
		// G.CDBL.real();
		a = new ComplexFloat64Member(0.1, 0.9);
		G.CDBL.real().call(a, d);
		assertEquals(0.1, d.v(), 0);
		
		// G.CDBL.round();
		d = new Float64Member(1);
		a = new ComplexFloat64Member(3.3, -4.1);
		G.CDBL.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.r(), 0);
		assertEquals(-4, b.i(), 0);
		
		// G.CDBL.scale();
		a = new ComplexFloat64Member(3, -4);
		b = new ComplexFloat64Member(3, 0);
		G.CDBL.scale().call(a, b, c);
		assertEquals(9, c.r(), 0);
		assertEquals(-12, c.i(), 0);
		
		// G.CDBL.sec();
		
		// G.CDBL.sech();
		
		// G.CDBL.sin();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.sinAndCos();
		
		// G.CDBL.sinc();
		
		// G.CDBL.sinch();
		
		// G.CDBL.sinchpi();
		
		// G.CDBL.sincpi();
		
		// G.CDBL.sinh();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.sinhAndCosh();
		
		// G.CDBL.sqrt();
		a.setR(8);
		a.setI(0);
		G.CDBL.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CDBL.subtract();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CDBL.subtract().call(a, b, c);
		assertEquals(-3, c.r(), 0);
		assertEquals(3, c.i(), 0);
		
		// G.CDBL.tan();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CDBL.tanh();
		a.setR(Math.PI/2);
		a.setI(0);
		G.CDBL.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CDBL.unity();
		a = new ComplexFloat64Member(0.1, 0.9);
		G.CDBL.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		
		// G.CDBL.unreal();
		a = new ComplexFloat64Member(0.1, 0.9);
		G.CDBL.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(0.9, b.i(), 0);
		
		// G.CDBL.zero();
		a = new ComplexFloat64Member(0.1, 0.9);
		assertFalse(G.CDBL.isZero().call(a));
		G.CDBL.zero().call(a);
		assertTrue(G.CDBL.isZero().call(a));
	}
	
	@Test
	public void run() {
		long[] times = new long[20];
		for (int i = 0; i < times.length; i++) {
			long a = System.currentTimeMillis();
			final int size = 500;
			IndexedDataSource<SignedInt32Member> inputData = makeIntData(size);
			fillIntData(inputData);
			IndexedDataSource<ComplexFloat64Member> outputComplexData = makeComplexData(size);
			dft(inputData, outputComplexData);
			// TODO: apply inverse and test resulting values
			long b = System.currentTimeMillis();
			times[i] = b - a;
		}
		System.out.println("DFT time test");
		for (int i = 0; i < times.length; i++) {
			if (i > 0)
				System.out.print(",");
			System.out.print(times[i]);
		}
		System.out.println("]");
		assertTrue(true);
	}
	
	
	private IndexedDataSource<SignedInt32Member> makeIntData(int size) {
		return new ArrayStorageSignedInt32<SignedInt32Member>(size, new SignedInt32Member());
	}
	
	private IndexedDataSource<ComplexFloat64Member> makeComplexData(int size) {
		return new ArrayStorageFloat64<ComplexFloat64Member>(size, new ComplexFloat64Member());
	}
	
	private void fillIntData(IndexedDataSource<SignedInt32Member> input) {
		// an example of straight index access to low level data
		SignedInt32Member value = new SignedInt32Member();
		int count = 0;
		for (long i = 0; i < input.size(); i++) {
			value.setV(count++);
			input.set(i, value);
		}
	}
	
	// algorithm from https://users.cs.cf.ac.uk/Dave.Marshall/Multimedia/node228.html
	
	// note this could be further optimized by doing some multiplication in reals rather than complexes
	
	private void dft(IndexedDataSource<SignedInt32Member> input, IndexedDataSource<ComplexFloat64Member> output) {
		ComplexFloat64Member FofU = new ComplexFloat64Member();
		ComplexFloat64Member fOfX = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member term = new ComplexFloat64Member();
		ComplexFloat64Member multiplier = new ComplexFloat64Member();
		ComplexFloat64Member power = new ComplexFloat64Member();
		ComplexFloat64Member uTmp = new ComplexFloat64Member();
		ComplexFloat64Member xTmp = new ComplexFloat64Member();
		ComplexFloat64Member I = new ComplexFloat64Member(0,1);
		ComplexFloat64Member MINUS_2_PI = new ComplexFloat64Member(-2*Math.PI,0);
		ComplexFloat64Member N = new ComplexFloat64Member(input.size(), 0);
		ComplexFloat64Member E = new ComplexFloat64Member();
		ComplexFloat64Member PI = new ComplexFloat64Member();
		SignedInt32Member in = new SignedInt32Member();
		
		G.CDBL.E().call(E);
		G.CDBL.PI().call(PI);
		
		for (long u = 0; u < input.size(); u++) {
			G.CDBL.zero().call(sum);
			uTmp.setR(u);
			for (long x = 0; x < input.size(); x++) {
				input.get(x, in);
				fOfX.setR(in.v());
				xTmp.setR(x);
				G.CDBL.multiply().call(MINUS_2_PI, I, power);
				G.CDBL.multiply().call(power, xTmp, power);
				G.CDBL.multiply().call(power, uTmp, power);
				G.CDBL.divide().call(power, N, power);
				G.CDBL.exp().call(power, multiplier);
				G.CDBL.multiply().call(fOfX, multiplier, term);
				G.CDBL.add().call(sum, term, sum);
			}
			G.CDBL.divide().call(sum, N, FofU);
			output.set(u, FofU);
		}
	}

}
