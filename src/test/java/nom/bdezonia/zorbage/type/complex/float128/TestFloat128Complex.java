/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.complex.float128;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128Complex {

	@Test
	public void mathematicalMethods() {
	
		BigDecimal tol = new BigDecimal("0.00000000000001");
		
		ComplexFloat128Member a = G.CQUAD.construct();
		ComplexFloat128Member b = G.CQUAD.construct();
		ComplexFloat128Member c = G.CQUAD.construct();
		Float128Member d = G.QUAD.construct();

		// G.CQUAD.acos();
		
		// G.CQUAD.acosh();
		
		// G.CQUAD.acot();
		
		// G.CQUAD.acoth();
		
		// G.CQUAD.acsc();
		
		// G.CQUAD.acsch();
		
		// G.CQUAD.add();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		b.setR(BigDecimal.valueOf(4));
		b.setI(BigDecimal.valueOf(-1));
		G.CQUAD.add().call(a, b, c);
		assertTrue(isNear(5, c.r().v(), tol));
		assertTrue(isNear(1, c.i().v(), tol));
		
		// G.CQUAD.asec();
		
		// G.CQUAD.asech();
		
		// G.CQUAD.asin();
		
		// G.CQUAD.asinh();
		
		// G.CQUAD.assign();
		a.setR(BigDecimal.valueOf(66));
		a.setI(BigDecimal.valueOf(99));
		G.CQUAD.assign().call(a, b);
		assertTrue(isNear(66, b.r().v(), tol));
		assertTrue(isNear(99, b.i().v(), tol));
		
		// G.CQUAD.atan();
		
		// G.CQUAD.atanh();
		
		// G.CQUAD.cbrt();
		a.setR(BigDecimal.valueOf(8));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.cbrt().call(a, b);
		assertTrue(isNear(2, b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.conjugate();
		a.setR(BigDecimal.valueOf(66));
		a.setI(BigDecimal.valueOf(99));
		G.CQUAD.conjugate().call(a, b);
		assertTrue(isNear(66, b.r().v(), tol));
		assertTrue(isNear(-99, b.i().v(), tol));
		
		// G.CQUAD.construct();
		a = G.CQUAD.construct();
		assertTrue(isNear(0, a.r().v(), tol));
		assertTrue(isNear(0, a.i().v(), tol));

		// G.CQUAD.construct("{14,7}");
		a = G.CQUAD.construct("{14,7}");
		assertTrue(isNear(14, a.r().v(), tol));
		assertTrue(isNear(7, a.i().v(), tol));
		
		// G.CQUAD.construct(other);
		b = G.CQUAD.construct(a);
		assertTrue(isNear(14, b.r().v(), tol));
		assertTrue(isNear(7, b.i().v(), tol));
		
		// G.CQUAD.cos();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.cos().call(a, b);
		assertTrue(isNear(Math.cos(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.cosh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.cosh().call(a, b);
		assertTrue(isNear(Math.cosh(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.cot();
		
		// G.CQUAD.coth();
		
		// G.CQUAD.csc();
		
		// G.CQUAD.csch();
		
		// G.CQUAD.divide();
		a = new ComplexFloat128Member(BigDecimal.valueOf(7),BigDecimal.valueOf(3));
		b = new ComplexFloat128Member(BigDecimal.valueOf(3), BigDecimal.valueOf(0));
		G.CQUAD.divide().call(a, b, c);
		assertTrue(isNear(7.0/3, c.r().v(), tol));
		assertTrue(isNear(1, c.i().v(), tol));

		// G.CQUAD.E();
		G.CQUAD.E().call(a);
		assertTrue(isNear(Math.E, a.r().v(), tol));
		assertTrue(isNear(0, a.i().v(), tol));
		
		// G.CQUAD.exp();
		a.setR(BigDecimal.valueOf(4));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.exp().call(a, b);
		assertTrue(isNear(Math.exp(4), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.expm1();
		a.setR(BigDecimal.valueOf(4));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.expm1().call(a, b);
		assertTrue(isNear(Math.expm1(4), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.infinite();
		a = G.CQUAD.construct();
		assertFalse(G.CQUAD.isInfinite().call(a));
		G.CQUAD.infinite().call(a);
		assertTrue(G.CQUAD.isInfinite().call(a));
		
		// G.CQUAD.invert();
		a.setR(BigDecimal.valueOf(14));
		a.setI(BigDecimal.valueOf(-3));
		G.CQUAD.invert().call(a, b);
		G.CQUAD.unity().call(c);
		G.CQUAD.divide().call(c, a, c);
		assertTrue(isNear(c.r().v().doubleValue(), b.r().v(), tol));
		assertTrue(isNear(c.i().v().doubleValue(), b.i().v(), tol));
		
		// G.CQUAD.isEqual();
		a = new ComplexFloat128Member(BigDecimal.valueOf(44),BigDecimal.valueOf(7));
		b = new ComplexFloat128Member(BigDecimal.valueOf(12),BigDecimal.valueOf(7));
		assertFalse(G.CQUAD.isEqual().call(a, b));
		b = G.CQUAD.construct(a);
		assertTrue(G.CQUAD.isEqual().call(a, b));
		
		// G.CQUAD.isInfinite();
		// tested by infinite() test above
		
		// G.CQUAD.isNaN();
		// tested by nan() test below
		
		// G.CQUAD.isNotEqual();
		a = new ComplexFloat128Member(BigDecimal.valueOf(44),BigDecimal.valueOf(7));
		b = new ComplexFloat128Member(BigDecimal.valueOf(12),BigDecimal.valueOf(7));
		assertTrue(G.CQUAD.isNotEqual().call(a, b));
		b = G.CQUAD.construct(a);
		assertFalse(G.CQUAD.isNotEqual().call(a, b));
		
		// G.CQUAD.isZero();
		// tested by zero() test below
		
		// G.CQUAD.log();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.log().call(a, b);
		assertTrue(isNear(Math.log(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));

		// G.CQUAD.log1p();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.log1p().call(a, b);
		assertTrue(isNear(Math.log1p(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.multiply();
		a = new ComplexFloat128Member(BigDecimal.valueOf(-8), BigDecimal.valueOf(1));
		b = new ComplexFloat128Member(BigDecimal.valueOf(-2), BigDecimal.valueOf(0));
		G.CQUAD.multiply().call(a, b, c);
		assertTrue(isNear(16, c.r().v(), tol));
		assertTrue(isNear(-2, c.i().v(), tol));
		
		// G.CQUAD.nan();
		a = new ComplexFloat128Member(BigDecimal.valueOf(44),BigDecimal.valueOf(7));
		assertFalse(G.CQUAD.isNaN().call(a));
		G.CQUAD.nan().call(a);
		assertTrue(G.CQUAD.isNaN().call(a));
		
		// G.CQUAD.negate();
		a = new ComplexFloat128Member(BigDecimal.valueOf(44),BigDecimal.valueOf(7));
		G.CQUAD.negate().call(a, b);
		assertTrue(isNear(-44, b.r().v(), tol));
		assertTrue(isNear(-7, b.i().v(), tol));
		
		// G.CQUAD.norm();
		a = new ComplexFloat128Member(BigDecimal.valueOf(3),BigDecimal.valueOf(4));
		G.CQUAD.norm().call(a, d);
		assertTrue(isNear(5, d.v(), tol));
		
		// G.CQUAD.PI();
		G.CQUAD.PI().call(a);
		assertTrue(isNear(Math.PI, a.r().v(), tol));
		assertTrue(isNear(0, a.i().v(), tol));
		
		// G.CQUAD.pow();
		a = new ComplexFloat128Member(BigDecimal.valueOf(-7),BigDecimal.valueOf(-4));
		b = new ComplexFloat128Member(BigDecimal.valueOf(2),BigDecimal.valueOf(0));
		G.CQUAD.pow().call(a, b, c);
		ComplexFloat128Member t = G.CQUAD.construct();
		G.CQUAD.multiply().call(a, a, t);
		assertTrue(isNear(t.r().v().doubleValue(), c.r().v(), tol));
		assertTrue(isNear(t.i().v().doubleValue(), c.i().v(), tol));
		
		// G.CQUAD.power();
		a = new ComplexFloat128Member(BigDecimal.valueOf(-7),BigDecimal.valueOf(-4));
		G.CQUAD.power().call(2, a, b);
		G.CQUAD.multiply().call(a, a, t);
		assertTrue(isNear(t.r().v().doubleValue(), b.r().v(), tol));
		assertTrue(isNear(t.i().v().doubleValue(), b.i().v(), tol));
		
		// G.CQUAD.random();
		G.CQUAD.random().call(a);
		assertTrue(a.r().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.r().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ONE) < 0);
		
		// G.CQUAD.real();
		a = new ComplexFloat128Member(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.9));
		G.CQUAD.real().call(a, d);
		assertTrue(isNear(0.1, d.v(), tol));
		
		// G.CQUAD.round();
		d = new Float128Member(BigDecimal.valueOf(1));
		a = new ComplexFloat128Member(BigDecimal.valueOf(3.3), BigDecimal.valueOf(-4.1));
		G.CQUAD.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertTrue(isNear(3, b.r().v(), tol));
		assertTrue(isNear(-4, b.i().v(), tol));
		
		// G.CQUAD.scale();
		a = new ComplexFloat128Member(BigDecimal.valueOf(3), BigDecimal.valueOf(-4));
		b = new ComplexFloat128Member(BigDecimal.valueOf(3), BigDecimal.valueOf(0));
		G.CQUAD.scale().call(a, b, c);
		assertTrue(isNear(9, c.r().v(), tol));
		assertTrue(isNear(-12, c.i().v(), tol));
		
		// G.CQUAD.sec();
		
		// G.CQUAD.sech();
		
		// G.CQUAD.sin();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.sin().call(a, b);
		assertTrue(isNear(Math.sin(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.sinAndCos();
		
		// G.CQUAD.sinc();
		
		// G.CQUAD.sinch();
		
		// G.CQUAD.sinchpi();
		
		// G.CQUAD.sincpi();
		
		// G.CQUAD.sinh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.sinh().call(a, b);
		assertTrue(isNear(Math.sinh(Math.PI/2), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.sinhAndCosh();
		
		// G.CQUAD.sqrt();
		a.setR(BigDecimal.valueOf(8));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.sqrt().call(a, b);
		assertTrue(isNear(Math.sqrt(8), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));
		
		// G.CQUAD.subtract();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		b.setR(BigDecimal.valueOf(4));
		b.setI(BigDecimal.valueOf(-1));
		G.CQUAD.subtract().call(a, b, c);
		assertTrue(isNear(-3, c.r().v(), tol));
		assertTrue(isNear(3, c.i().v(), tol));
		
		// G.CQUAD.tan();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.tan().call(a, b);
		assertTrue(isNear(Math.tan(Math.PI/16), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));

		// G.CQUAD.tanh();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		G.CQUAD.tanh().call(a, b);
		assertTrue(isNear(Math.tanh(Math.PI/16), b.r().v(), tol));
		assertTrue(isNear(0, b.i().v(), tol));

		// G.CQUAD.unity();
		a = new ComplexFloat128Member(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.9));
		G.CQUAD.unity().call(a);
		assertTrue(isNear(1, a.r().v(), tol));
		assertTrue(isNear(0, a.i().v(), tol));
		
		// G.CQUAD.unreal();
		a = new ComplexFloat128Member(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.9));
		G.CQUAD.unreal().call(a, b);
		assertTrue(isNear(0, b.r().v(), tol));
		assertTrue(isNear(0.9, b.i().v(), tol));
		
		// G.CQUAD.zero();
		a = new ComplexFloat128Member(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.9));
		assertFalse(G.CQUAD.isZero().call(a));
		G.CQUAD.zero().call(a);
		assertTrue(G.CQUAD.isZero().call(a));
	}

	@Test
	public void run() {
		long[] times = new long[20];
		for (int i = 0; i < times.length; i++) {
			System.out.println("dft iteration run number "+(i+1)+" of "+times.length);
			long a = System.currentTimeMillis();
			final int size = 500;
			IndexedDataSource<SignedInt32Member> inputData = makeIntData(size);
			fillIntData(inputData);
			IndexedDataSource<ComplexFloat128Member> outputComplexData = makeComplexData(size);
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
		return Storage.allocate(new SignedInt32Member(), size);
	}

	private IndexedDataSource<ComplexFloat128Member> makeComplexData(int size) {
		return Storage.allocate(new ComplexFloat128Member(), size);
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

	private void dft(IndexedDataSource<SignedInt32Member> input, IndexedDataSource<ComplexFloat128Member> output) {
		ComplexFloat128Member FofU = new ComplexFloat128Member();
		ComplexFloat128Member fOfX = new ComplexFloat128Member();
		ComplexFloat128Member sum = new ComplexFloat128Member();
		ComplexFloat128Member term = new ComplexFloat128Member();
		ComplexFloat128Member multiplier = new ComplexFloat128Member();
		ComplexFloat128Member power = new ComplexFloat128Member();
		ComplexFloat128Member uTmp = new ComplexFloat128Member();
		ComplexFloat128Member xTmp = new ComplexFloat128Member();
		ComplexFloat128Member I = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.ONE);
		ComplexFloat128Member MINUS_2_PI = new ComplexFloat128Member(BigDecimal.valueOf(-2*Math.PI),BigDecimal.ZERO);
		ComplexFloat128Member N = new ComplexFloat128Member(BigDecimal.valueOf(input.size()), BigDecimal.ZERO);
		ComplexFloat128Member E = new ComplexFloat128Member();
		ComplexFloat128Member PI = new ComplexFloat128Member();
		SignedInt32Member in = new SignedInt32Member();
		
		G.CQUAD.E().call(E);
		G.CQUAD.PI().call(PI);
		
		for (long u = 0; u < input.size(); u++) {
			G.CQUAD.zero().call(sum);
			uTmp.setR(BigDecimal.valueOf(u));
			for (long x = 0; x < input.size(); x++) {
				input.get(x, in);
				fOfX.setR(BigDecimal.valueOf(in.v()));
				xTmp.setR(BigDecimal.valueOf(x));
				G.CQUAD.multiply().call(MINUS_2_PI, I, power);
				G.CQUAD.multiply().call(power, xTmp, power);
				G.CQUAD.multiply().call(power, uTmp, power);
				G.CQUAD.divide().call(power, N, power);
				G.CQUAD.exp().call(power, multiplier);
				G.CQUAD.multiply().call(fOfX, multiplier, term);
				G.CQUAD.add().call(sum, term, sum);
			}
			G.CQUAD.divide().call(sum, N, FofU);
			output.set(u, FofU);
		}
	}

	private boolean isNear(double a, BigDecimal b, BigDecimal tol) {
		return BigDecimal.valueOf(a).subtract(b).abs().compareTo(tol) <= 0;
	}
}
