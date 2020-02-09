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
package nom.bdezonia.zorbage.type.data.float32.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.data.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat32Complex {

	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00002;
		
		ComplexFloat32Member a = G.CFLT.construct();
		ComplexFloat32Member b = G.CFLT.construct();
		ComplexFloat32Member c = G.CFLT.construct();
		Float32Member d = G.FLT.construct();

		// G.CFLT.acos();
		
		// G.CFLT.acosh();
		
		// G.CFLT.acot();
		
		// G.CFLT.acoth();
		
		// G.CFLT.acsc();
		
		// G.CFLT.acsch();
		
		// G.CFLT.add();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CFLT.add().call(a, b, c);
		assertEquals(5, c.r(), 0);
		assertEquals(1, c.i(), 0);
		
		// G.CFLT.asec();
		
		// G.CFLT.asech();
		
		// G.CFLT.asin();
		
		// G.CFLT.asinh();
		
		// G.CFLT.assign();
		a.setR(66);
		a.setI(99);
		G.CFLT.assign().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(99,b.i(),0);
		
		// G.CFLT.atan();
		
		// G.CFLT.atanh();
		
		// G.CFLT.cbrt();
		a.setR(8);
		a.setI(0);
		G.CFLT.cbrt().call(a, b);
		assertEquals(2, b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.conjugate();
		a.setR(66);
		a.setI(99);
		G.CFLT.conjugate().call(a, b);
		assertEquals(66,b.r(),0);
		assertEquals(-99,b.i(),0);
		
		// G.CFLT.construct();
		a = G.CFLT.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);

		// G.CFLT.construct("{14,7}");
		a = G.CFLT.construct("{14,7}");
		assertEquals(14, a.r(), 0);
		assertEquals(7, a.i(), 0);
		
		// G.CFLT.construct(other);
		b = G.CFLT.construct(a);
		assertEquals(14, b.r(), 0);
		assertEquals(7, b.i(), 0);
		
		// G.CFLT.cos();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.cosh();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.cot();
		
		// G.CFLT.coth();
		
		// G.CFLT.csc();
		
		// G.CFLT.csch();
		
		// G.CFLT.divide();
		a = new ComplexFloat32Member(7,3);
		b = new ComplexFloat32Member(3, 0);
		G.CFLT.divide().call(a, b, c);
		assertEquals(7.0/3, c.r(), tol);
		assertEquals(1, c.i(), tol);

		// G.CFLT.E();
		G.CFLT.E().call(a);
		assertEquals(Math.E, a.r(), tol);
		assertEquals(0, a.i(), tol);
		
		// G.CFLT.exp();
		a.setR(4);
		a.setI(0);
		G.CFLT.exp().call(a, b);
		assertEquals(Math.exp(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.expm1();
		a.setR(4);
		a.setI(0);
		G.CFLT.expm1().call(a, b);
		assertEquals(Math.expm1(4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.infinite();
		a = G.CFLT.construct();
		assertFalse(G.CFLT.isInfinite().call(a));
		G.CFLT.infinite().call(a);
		assertTrue(G.CFLT.isInfinite().call(a));
		
		// G.CFLT.invert();
		a.setR(14);
		a.setI(-3);
		G.CFLT.invert().call(a, b);
		G.CFLT.unity().call(c);
		G.CFLT.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), tol);
		assertEquals(c.i(), b.i(), tol);
		
		// G.CFLT.isEqual();
		a = new ComplexFloat32Member(44,7);
		b = new ComplexFloat32Member(12,7);
		assertFalse(G.CFLT.isEqual().call(a, b));
		b = G.CFLT.construct(a);
		assertTrue(G.CFLT.isEqual().call(a, b));
		
		// G.CFLT.isInfinite();
		// tested by infinite() test above
		
		// G.CFLT.isNaN();
		// tested by nan() test below
		
		// G.CFLT.isNotEqual();
		a = new ComplexFloat32Member(44,7);
		b = new ComplexFloat32Member(12,7);
		assertTrue(G.CFLT.isNotEqual().call(a, b));
		b = G.CFLT.construct(a);
		assertFalse(G.CFLT.isNotEqual().call(a, b));
		
		// G.CFLT.isZero();
		// tested by zero() test below
		
		// G.CFLT.log();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.log().call(a, b);
		assertEquals(Math.log(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.log1p();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.log1p().call(a, b);
		assertEquals(Math.log1p(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.multiply();
		a = new ComplexFloat32Member(-8, 1);
		b = new ComplexFloat32Member(-2, 0);
		G.CFLT.multiply().call(a,b,c);
		assertEquals(16, c.r(), 0);
		assertEquals(-2, c.i(), 0);
		
		// G.CFLT.nan();
		a = new ComplexFloat32Member(44,7);
		assertFalse(G.CFLT.isNaN().call(a));
		G.CFLT.nan().call(a);
		assertTrue(G.CFLT.isNaN().call(a));
		
		// G.CFLT.negate();
		a = new ComplexFloat32Member(44,7);
		G.CFLT.negate().call(a, b);
		assertEquals(-44, b.r(), 0);
		assertEquals(-7, b.i(), 0);
		
		// G.CFLT.norm();
		a = new ComplexFloat32Member(3,4);
		G.CFLT.norm().call(a, d);
		assertEquals(5,d.v(),0);
		
		// G.CFLT.PI();
		G.CFLT.PI().call(a);
		assertEquals(Math.PI, a.r(), tol);
		assertEquals(0, a.i(), tol);
		
		// G.CFLT.pow();
		a = new ComplexFloat32Member(-7,-4);
		b = new ComplexFloat32Member(2,0);
		G.CFLT.pow().call(a, b, c);
		ComplexFloat32Member t = G.CFLT.construct();
		G.CFLT.multiply().call(a, a, t);
		assertEquals(t.r(), c.r(), tol);
		assertEquals(t.i(), c.i(), tol);
		
		// G.CFLT.power();
		a = new ComplexFloat32Member(-7,-4);
		G.CFLT.power().call(2, a, b);
		G.CFLT.multiply().call(a, a, t);
		assertEquals(t.r(), b.r(), tol);
		assertEquals(t.i(), b.i(), tol);
		
		// G.CFLT.random();
		// TODO: not sure how to test
		G.CFLT.random().call(a);
		
		// G.CFLT.real();
		a = new ComplexFloat32Member(0.1f, 0.9f);
		G.CFLT.real().call(a, d);
		assertEquals(0.1, d.v(), tol);
		
		// G.CFLT.round();
		d = new Float32Member(1);
		a = new ComplexFloat32Member(3.3f, -4.1f);
		G.CFLT.round().call(Mode.TOWARDS_ORIGIN, d, a, b);
		assertEquals(3, b.r(), 0);
		assertEquals(-4, b.i(), 0);
		
		// G.CFLT.scale();
		a = new ComplexFloat32Member(3, -4);
		b = new ComplexFloat32Member(3, 0);
		G.CFLT.scale().call(a,b,c);
		assertEquals(9, c.r(), 0);
		assertEquals(-12, c.i(), 0);
		
		// G.CFLT.sec();
		
		// G.CFLT.sech();
		
		// G.CFLT.sin();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.sinAndCos();
		
		// G.CFLT.sinc();
		
		// G.CFLT.sinch();
		
		// G.CFLT.sinchpi();
		
		// G.CFLT.sincpi();
		
		// G.CFLT.sinh();
		a.setR((float) Math.PI/2);
		a.setI(0);
		G.CFLT.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.sinhAndCosh();
		
		// G.CFLT.sqrt();
		a.setR(8);
		a.setI(0);
		G.CFLT.sqrt().call(a, b);
		assertEquals(Math.sqrt(8), b.r(), tol);
		assertEquals(0, b.i(), tol);
		
		// G.CFLT.subtract();
		a.setR(1);
		a.setI(2);
		b.setR(4);
		b.setI(-1);
		G.CFLT.subtract().call(a, b, c);
		assertEquals(-3, c.r(), 0);
		assertEquals(3, c.i(), 0);
		
		// G.CFLT.tan();
		a.setR((float) Math.PI/4);
		a.setI(0);
		G.CFLT.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CFLT.tanh();
		a.setR((float) Math.PI/4);
		a.setI(0);
		G.CFLT.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);

		// G.CFLT.unity();
		a = new ComplexFloat32Member(0.1f, 0.9f);
		G.CFLT.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		
		// G.CFLT.unreal();
		a = new ComplexFloat32Member(0.1f, 0.9f);
		G.CFLT.unreal().call(a, b);
		assertEquals(0, b.r(), tol);
		assertEquals(0.9, b.i(), tol);
		
		// G.CFLT.zero();
		a = new ComplexFloat32Member(0.1f, 0.9f);
		assertFalse(G.CFLT.isZero().call(a));
		G.CFLT.zero().call(a);
		assertTrue(G.CFLT.isZero().call(a));
	}

}
