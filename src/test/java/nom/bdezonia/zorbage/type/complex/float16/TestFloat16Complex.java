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
package nom.bdezonia.zorbage.type.complex.float16;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat16Complex {

	@Test
	public void mathematicalMethods() {
		
		double tol = 0.125;
		
		ComplexFloat16Member a = G.CHLF.construct();
		ComplexFloat16Member b = G.CHLF.construct();
		ComplexFloat16Member c = G.CHLF.construct();
		Float16Member d = G.HLF.construct();

		// G.CHLF.acos();
		
		// G.CHLF.acosh();
		
		// G.CHLF.acot();
		
		// G.CHLF.acoth();
		
		// G.CHLF.acsc();
		
		// G.CHLF.acsch();
		
		// G.CHLF.add();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CHLF.add().call(a, b, c);
		assertEquals(5, c.r(), 0);
		assertEquals(1, c.i(), 0);
		
		// G.CHLF.asec();
		
		// G.CHLF.asech();
		
		// G.CHLF.asin();
		
		// G.CHLF.asinh();
		
		// G.CHLF.assign();
		a.setR(66);
		a.setI(99);
		G.CHLF.assign().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(99,b.i(),0);
		
		// G.CHLF.atan();
		
		// G.CHLF.atanh();
		
		// G.CHLF.cbrt();
		a.setR(8);
		a.setI(0);
		G.CHLF.cbrt().call(a, b);
		assertEquals(2, b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.conjugate();
		a.setR(66);
		a.setI(99);
		G.CHLF.conjugate().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(-99,b.i(),0);
		
		// G.CHLF.construct();
		a = G.CHLF.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);

		// G.CHLF.construct("{14,7}");
		a = G.CHLF.construct("{14,7}");
		assertEquals(14, a.r(), 0);
		assertEquals(7, a.i(), 0);
		
		// G.CHLF.construct(other);
		b = G.CHLF.construct(a);
		assertEquals(14, b.r(), 0);
		assertEquals(7, b.i(), 0);
		
		// G.CHLF.cos();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.cosh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.cot();
		
		// G.CHLF.coth();
		
		// G.CHLF.csc();
		
		// G.CHLF.csch();
		
		// G.CHLF.divide();
		a = new ComplexFloat16Member(7,3);
		b = new ComplexFloat16Member(3, 0);
		G.CHLF.divide().call(a, b, c);
		assertEquals(7.0/3, c.r(), tol);
		assertEquals(1, c.i(), 0);

		// G.CHLF.E();
		G.CHLF.E().call(a);
		assertEquals(Math.E, a.r(), tol);
		assertEquals(0, a.i(), 0);
		
		// G.CHLF.exp();
		a.setR(4);
		a.setI(0);
		G.CHLF.exp().call(a, b);
		assertEquals(Math.exp(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.expm1();
		a.setR(4);
		a.setI(0);
		G.CHLF.expm1().call(a, b);
		assertEquals(Math.expm1(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.infinite();
		a = G.CHLF.construct();
		assertFalse(G.CHLF.isInfinite().call(a));
		G.CHLF.infinite().call(a);
		assertTrue(G.CHLF.isInfinite().call(a));
		
		// G.CHLF.invert();
		a.setR(14);
		a.setI(-3);
		G.CHLF.invert().call(a, b);
		G.CHLF.unity().call(c);
		G.CHLF.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), tol);
		assertEquals(c.i(), b.i(), tol);
		
		// G.CHLF.isEqual();
		a = new ComplexFloat16Member(44,7);
		b = new ComplexFloat16Member(12,7);
		assertFalse(G.CHLF.isEqual().call(a, b));
		b = G.CHLF.construct(a);
		assertTrue(G.CHLF.isEqual().call(a, b));
		
		// G.CHLF.isInfinite();
		// tested by infinite() test above
		
		// G.CHLF.isNaN();
		// tested by nan() test below
		
		// G.CHLF.isNotEqual();
		a = new ComplexFloat16Member(44,7);
		b = new ComplexFloat16Member(12,7);
		assertTrue(G.CHLF.isNotEqual().call(a, b));
		b = G.CHLF.construct(a);
		assertFalse(G.CHLF.isNotEqual().call(a, b));
		
		// G.CHLF.isZero();
		// tested by zero() test below
		
		// G.CHLF.log();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.log1p();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.multiply();
		a = new ComplexFloat16Member(-8, 1);
		b = new ComplexFloat16Member(-2, 0);
		G.CHLF.multiply().call(a, b, c);
		assertEquals(16, c.r(), 0);
		assertEquals(-2, c.i(), 0);
		
		// G.CHLF.nan();
		a = new ComplexFloat16Member(44,7);
		assertFalse(G.CHLF.isNaN().call(a));
		G.CHLF.nan().call(a);
		assertTrue(G.CHLF.isNaN().call(a));
		
		// G.CHLF.negate();
		a = new ComplexFloat16Member(44,7);
		G.CHLF.negate().call(a, b);
		assertEquals(-44, b.r(), 0);
		assertEquals(-7, b.i(), 0);
		
		// G.CHLF.norm();
		a = new ComplexFloat16Member(3,4);
		G.CHLF.norm().call(a, d);
		assertEquals(5,d.v(),0);
		
		// G.CHLF.PI();
		G.CHLF.PI().call(a);
		assertEquals(Math.PI, a.r(), tol);
		assertEquals(0, a.i(), 0);
		
		// G.CHLF.pow();
		a = new ComplexFloat16Member(-7,-4);
		b = new ComplexFloat16Member(2,0);
		G.CHLF.pow().call(a, b, c);
		ComplexFloat16Member t = G.CHLF.construct();
		G.CHLF.multiply().call(a, a, t);
		assertEquals(t.r(), c.r(), tol);
		assertEquals(t.i(), c.i(), tol);
		
		// G.CHLF.power();
		a = new ComplexFloat16Member(-7,-4);
		G.CHLF.power().call(2, a, b);
		G.CHLF.multiply().call(a, a, t);
		assertEquals(t.r(), b.r(), tol);
		assertEquals(t.i(), b.i(), tol);
		
		// G.CHLF.random();
		// TODO: not sure how to test
		G.CHLF.random().call(a);
		
		// G.CHLF.real();
		a = new ComplexFloat16Member(0.1f, 0.9f);
		G.CHLF.real().call(a, d);
		assertEquals(0.1, d.v(), tol);
		
		// G.CHLF.round();
		d = new Float16Member(1);
		a = new ComplexFloat16Member(3.3f, -4.1f);
		G.CHLF.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.r(), 0);
		assertEquals(-4, b.i(), 0);
		
		// G.CHLF.scale();
		a = new ComplexFloat16Member(3, -4);
		b = new ComplexFloat16Member(3, 0);
		G.CHLF.scale().call(a, b, c);
		assertEquals(9, c.r(), 0);
		assertEquals(-12, c.i(), 0);
		
		// G.CHLF.sec();
		
		// G.CHLF.sech();
		
		// G.CHLF.sin();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.sinAndCos();
		
		// G.CHLF.sinc();
		
		// G.CHLF.sinch();
		
		// G.CHLF.sinchpi();
		
		// G.CHLF.sincpi();
		
		// G.CHLF.sinh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		G.CHLF.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.sinhAndCosh();
		
		// G.CHLF.sqrt();
		a.setR(8);
		a.setI(0);
		G.CHLF.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CHLF.subtract();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CHLF.subtract().call(a, b, c);
		assertEquals(-3, c.r(), 0);
		assertEquals(3, c.i(), 0);
		
		// G.CHLF.tan();
		a.setR((float)Math.PI/4);
		a.setI(0);
		G.CHLF.tan().call(a, b);
		assertEquals(Math.tan(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CHLF.tanh();
		a.setR((float)Math.PI/4);
		a.setI(0);
		G.CHLF.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CHLF.unity();
		a = new ComplexFloat16Member(0.1f, 0.9f);
		G.CHLF.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		
		// G.CHLF.unreal();
		a = new ComplexFloat16Member(0.1f, 0.9f);
		G.CHLF.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(0.9, b.i(), tol);
		
		// G.CHLF.zero();
		a = new ComplexFloat16Member(0.1f, 0.9f);
		assertFalse(G.CHLF.isZero().call(a));
		G.CHLF.zero().call(a);
		assertTrue(G.CHLF.isZero().call(a));
	}
	
}
