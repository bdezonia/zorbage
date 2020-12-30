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
package nom.bdezonia.zorbage.type.octonion.float64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestOctonionFloat64Algebra {

	private static final double TOL = 0.0000000001;
	
	@Test
	public void testConjugate() {
		// Question: is multiplying an oct by its conjugate always a purely real result?
		// This test proves the answer is yes. This will be a useful fact when we decide
		// to calculate matrix norms.
		
		OctonionFloat64Member a = new OctonionFloat64Member(-1,5,-2,7,-1,-2,-3,-4);
		OctonionFloat64Member b = new OctonionFloat64Member();
		OctonionFloat64Member c = new OctonionFloat64Member();
		
		G.ODBL.conjugate().call(a, b);
		G.ODBL.multiply().call(a, b, c);
		assertEquals(109,c.r(), TOL);
		assertEquals(0,c.i(), TOL);
		assertEquals(0,c.j(), TOL);
		assertEquals(0,c.k(), TOL);
		assertEquals(0,c.l(), TOL);
		assertEquals(0,c.i0(), TOL);
		assertEquals(0,c.j0(), TOL);
		assertEquals(0,c.k0(), TOL);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00000000001;
		
		OctonionFloat64Member a = G.ODBL.construct();
		OctonionFloat64Member b = G.ODBL.construct();
		OctonionFloat64Member c = G.ODBL.construct();
		Float64Member t = G.DBL.construct();
		
		// G.ODBL.add();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		b.setL(13);
		b.setI0(15);
		b.setJ0(17);
		b.setK0(19);
		G.ODBL.add().call(a, b, c);
		assertEquals(6, c.r(), 0);
		assertEquals(9, c.i(), 0);
		assertEquals(12, c.j(), 0);
		assertEquals(15, c.k(), 0);
		assertEquals(18, c.l(), 0);
		assertEquals(21, c.i0(), 0);
		assertEquals(24, c.j0(), 0);
		assertEquals(27, c.k0(), 0);
		
		// G.ODBL.assign();
		G.ODBL.assign().call(a, b);
		assertEquals(a.r(), b.r(), 0);
		assertEquals(a.i(), b.i(), 0);
		assertEquals(a.j(), b.j(), 0);
		assertEquals(a.k(), b.k(), 0);
		assertEquals(a.l(), b.l(), 0);
		assertEquals(a.i0(), b.i0(), 0);
		assertEquals(a.j0(), b.j0(), 0);
		assertEquals(a.k0(), b.k0(), 0);
		
		// G.ODBL.cbrt();
		a.setR(27);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.cbrt().call(a, b);
		assertEquals(3, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		assertEquals(0, b.l(), tol);
		assertEquals(0, b.i0(), tol);
		assertEquals(0, b.j0(), tol);
		assertEquals(0, b.k0(), tol);
		
		// G.ODBL.conjugate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.conjugate().call(a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		assertEquals(-5, b.l(), 0);
		assertEquals(-6, b.i0(), 0);
		assertEquals(-7, b.j0(), 0);
		assertEquals(-8, b.k0(), 0);
		
		// G.ODBL.construct();
		a = G.ODBL.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		assertEquals(0, a.l(), 0);
		assertEquals(0, a.i0(), 0);
		assertEquals(0, a.j0(), 0);
		assertEquals(0, a.k0(), 0);
		
		// G.ODBL.construct("");
		a = G.ODBL.construct("{3,-1,0,6,7,-9,0.1,-0.3}");
		assertEquals(3, a.r(), 0);
		assertEquals(-1, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(6, a.k(), 0);
		assertEquals(7, a.l(), 0);
		assertEquals(-9, a.i0(), 0);
		assertEquals(0.1, a.j0(), 0);
		assertEquals(-0.3, a.k0(), 0);
		
		// G.ODBL.construct(other);
		b = G.ODBL.construct(a);
		assertEquals(3, b.r(), 0);
		assertEquals(-1, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(6, b.k(), 0);
		assertEquals(7, b.l(), 0);
		assertEquals(-9, b.i0(), 0);
		assertEquals(0.1, b.j0(), 0);
		assertEquals(-0.3, b.k0(), 0);
		
		// G.ODBL.cos();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.cosh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.divide();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		b.setR(0.5);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		b.setL(0);
		b.setI0(0);
		b.setJ0(0);
		b.setK0(0);
		G.ODBL.divide().call(a, b, c);
		assertEquals(2, c.r(), 0);
		assertEquals(4, c.i(), 0);
		assertEquals(6, c.j(), 0);
		assertEquals(8, c.k(), 0);
		assertEquals(10, c.l(), 0);
		assertEquals(12, c.i0(), 0);
		assertEquals(14, c.j0(), 0);
		assertEquals(16, c.k0(), 0);
		
		// G.ODBL.E();
		G.ODBL.E().call(a);
		assertEquals(Math.E, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		assertEquals(0, a.l(), 0);
		assertEquals(0, a.i0(), 0);
		assertEquals(0, a.j0(), 0);
		assertEquals(0, a.k0(), 0);
		
		// G.ODBL.exp();
		a.setR(2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.exp().call(a, b);
		assertEquals(Math.exp(2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.infinite();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		assertFalse(G.ODBL.isInfinite().call(a));
		G.ODBL.infinite().call(a);
		assertTrue(G.ODBL.isInfinite().call(a));
		
		// G.ODBL.invert();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.invert().call(a, b);
		G.ODBL.unity().call(c);
		G.ODBL.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), 0);
		assertEquals(c.i(), b.i(), 0);
		assertEquals(c.j(), b.j(), 0);
		assertEquals(c.k(), b.k(), 0);
		assertEquals(c.l(), b.l(), 0);
		assertEquals(c.i0(), b.i0(), 0);
		assertEquals(c.j0(), b.j0(), 0);
		assertEquals(c.k0(), b.k0(), 0);

		// G.ODBL.isEqual();
		G.ODBL.zero().call(b);
		assertFalse(G.ODBL.isEqual().call(a, b));
		G.ODBL.assign().call(a, b);
		assertTrue(G.ODBL.isEqual().call(a, b));
		
		// G.ODBL.isInfinite();
		//   tested by infinite() test above
		
		// G.ODBL.isNaN();
		//   tested by nan() test below
		
		// G.ODBL.isNotEqual();
		G.ODBL.zero().call(b);
		assertTrue(G.ODBL.isNotEqual().call(a, b));
		G.ODBL.assign().call(a, b);
		assertFalse(G.ODBL.isNotEqual().call(a, b));
		
		// G.ODBL.isZero();
		//   tested by zero() test below
		
		// G.ODBL.log();
		a.setR(4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.log().call(a, b);
		assertEquals(Math.log(4), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.multiply();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		b.setR(7);
		b.setI(-4);
		b.setJ(8);
		b.setK(3);
		b.setL(0);
		b.setI0(-3);
		b.setJ0(1);
		b.setK0(9);
		G.ODBL.multiply().call(a, b, c);
		G.ODBL.divide().call(c, b, b);
		assertEquals(a.r(), b.r(), tol);
		assertEquals(a.i(), b.i(), tol);
		assertEquals(a.j(), b.j(), tol);
		assertEquals(a.k(), b.k(), tol);
		assertEquals(a.l(), b.l(), tol);
		assertEquals(a.i0(), b.i0(), tol);
		assertEquals(a.j0(), b.j0(), tol);
		assertEquals(a.k0(), b.k0(), tol);
		
		// G.ODBL.nan();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		assertFalse(G.ODBL.isNaN().call(a));
		G.ODBL.nan().call(a);
		assertTrue(G.ODBL.isNaN().call(a));
		
		// G.ODBL.negate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.negate().call(a, b);
		assertEquals(-1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		assertEquals(-5, b.l(), 0);
		assertEquals(-6, b.i0(), 0);
		assertEquals(-7, b.j0(), 0);
		assertEquals(-8, b.k0(), 0);
		
		// G.ODBL.norm();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.norm().call(a, t);
		assertEquals(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6 + 7*7 + 8*8), t.v(), 0);
		
		// G.ODBL.PI();
		G.ODBL.PI().call(a);
		assertEquals(Math.PI, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		assertEquals(0, a.l(), 0);
		assertEquals(0, a.i0(), 0);
		assertEquals(0, a.j0(), 0);
		assertEquals(0, a.k0(), 0);
		
		// G.ODBL.pow();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		b.setR(4);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		b.setL(0);
		b.setI0(0);
		b.setJ0(0);
		b.setK0(0);
		G.ODBL.pow().call(a, b, c);
		assertEquals(7*7*7*7, c.r(), tol);
		assertEquals(0, c.i(), tol);
		assertEquals(0, c.j(), tol);
		assertEquals(0, c.k(), tol);
		assertEquals(0, c.l(), tol);
		assertEquals(0, c.i0(), tol);
		assertEquals(0, c.j0(), tol);
		assertEquals(0, c.k0(), tol);
		
		// G.ODBL.power();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.power().call(4, a, b);
		assertEquals(7*7*7*7, b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.random();
		// TODO - how to test
		
		// G.ODBL.real();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.real().call(a, t);
		assertEquals(1, t.v(), 0);
		
		// G.ODBL.round();
		a.setR(1.1);
		a.setI(-2.2);
		a.setJ(3.3);
		a.setK(-4.4);
		a.setL(5.5);
		a.setI0(-6.6);
		a.setJ0(7.7);
		a.setK0(-8.8);
		t.setV(1);
		G.ODBL.round().call(Mode.HALF_EVEN, t, a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		assertEquals(6, b.l(), 0);
		assertEquals(-7, b.i0(), 0);
		assertEquals(8, b.j0(), 0);
		assertEquals(-9, b.k0(), 0);
		
		// G.ODBL.scale();
		a.setR(1.1);
		a.setI(-2.2);
		a.setJ(3.3);
		a.setK(-4.4);
		a.setL(5.5);
		a.setI0(-6.6);
		a.setJ0(7.7);
		a.setK0(-8.8);
		b.setR(3);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		b.setL(0);
		b.setI0(0);
		b.setJ0(0);
		b.setK0(0);
		G.ODBL.scale().call(b, a, c);
		assertEquals(3.3, c.r(), tol);
		assertEquals(-6.6, c.i(), tol);
		assertEquals(9.9, c.j(), tol);
		assertEquals(-13.2, c.k(), tol);
		assertEquals(16.5, c.l(), tol);
		assertEquals(-19.8, c.i0(), tol);
		assertEquals(23.1, c.j0(), tol);
		assertEquals(-26.4, c.k0(), tol);
		
		// G.ODBL.sin();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.sinAndCos();
		
		// G.ODBL.sinc();
		
		// G.ODBL.sinch();
		
		// G.ODBL.sinchpi();
		
		// G.ODBL.sincpi();
		
		// G.ODBL.sinh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.sinhAndCosh();
		
		// G.ODBL.sqrt();
		a.setR(25);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.sqrt().call(a, b);
		assertEquals(5, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		assertEquals(0, b.l(), tol);
		assertEquals(0, b.i0(), tol);
		assertEquals(0, b.j0(), tol);
		assertEquals(0, b.k0(), tol);
		
		// G.ODBL.subtract();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		b.setL(13);
		b.setI0(15);
		b.setJ0(17);
		b.setK0(19);
		G.ODBL.subtract().call(a, b, c);
		assertEquals(-4, c.r(), 0);
		assertEquals(-5, c.i(), 0);
		assertEquals(-6, c.j(), 0);
		assertEquals(-7, c.k(), 0);
		assertEquals(-8, c.l(), 0);
		assertEquals(-9, c.i0(), 0);
		assertEquals(-10, c.j0(), 0);
		assertEquals(-11, c.k0(), 0);
		
		// G.ODBL.tan();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.tan().call(a, b);
		assertEquals(Math.tan(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		assertEquals(0, b.l(), 0);
		assertEquals(0, b.i0(), 0);
		assertEquals(0, b.j0(), 0);
		assertEquals(0, b.k0(), 0);
		
		// G.ODBL.tanh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		a.setL(0);
		a.setI0(0);
		a.setJ0(0);
		a.setK0(0);
		G.ODBL.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		assertEquals(0, b.l(), tol);
		assertEquals(0, b.i0(), tol);
		assertEquals(0, b.j0(), tol);
		assertEquals(0, b.k0(), tol);
		
		// G.ODBL.unity();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		assertEquals(0, a.l(), 0);
		assertEquals(0, a.i0(), 0);
		assertEquals(0, a.j0(), 0);
		assertEquals(0, a.k0(), 0);
		
		// G.ODBL.unreal();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		G.ODBL.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(4, b.k(), 0);
		assertEquals(5, b.l(), 0);
		assertEquals(6, b.i0(), 0);
		assertEquals(7, b.j0(), 0);
		assertEquals(8, b.k0(), 0);
		
		// G.ODBL.zero();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		a.setL(5);
		a.setI0(6);
		a.setJ0(7);
		a.setK0(8);
		assertFalse(G.ODBL.isZero().call(a));
		G.ODBL.zero().call(a);
		assertTrue(G.ODBL.isZero().call(a));
	}
	
}
