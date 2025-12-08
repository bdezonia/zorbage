/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.quaternion.float32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat32Algebra {

	private static final double TOL = 0.00001;
	
	@Test
	public void run() {
		QuaternionFloat32Member q1 = G.QFLT.construct();
		
		// TODO define a ctor that takes four doubles
		q1.setR(1);
		q1.setI(-2);
		q1.setJ(3);
		q1.setK(2);
		
		Float32Member tmp = new Float32Member();
		
		G.QFLT.norm().call(q1, tmp);

		assertEquals(3*Math.sqrt(2),tmp.v(),TOL);
		
		QuaternionFloat32Member q2 = G.QFLT.construct();

		q2.setR(11);
		q2.setI(-2);
		q2.setJ(0);
		q2.setK(-2);

		G.QFLT.norm().call(q2, tmp);

		assertEquals(Math.sqrt(129),tmp.v(),TOL);
		
		QuaternionFloat32Member q3 = G.QFLT.construct();

		G.QFLT.add().call(q1, q2, q3);

		assertEquals(q1.r()+q2.r(), q3.r(), 0);
		assertEquals(q1.i()+q2.i(), q3.i(), 0);
		assertEquals(q1.j()+q2.j(), q3.j(), 0);
		assertEquals(q1.k()+q2.k(), q3.k(), 0);
		
		G.QFLT.subtract().call(q1, q2, q3);

		assertEquals(q1.r()-q2.r(), q3.r(), 0);
		assertEquals(q1.i()-q2.i(), q3.i(), 0);
		assertEquals(q1.j()-q2.j(), q3.j(), 0);
		assertEquals(q1.k()-q2.k(), q3.k(), 0);

		G.QFLT.multiply().call(q1, q2, q3);

		assertEquals(11, q3.r(), TOL);
		assertEquals(-30, q3.i(), TOL);
		assertEquals(25, q3.j(), TOL);
		assertEquals(26, q3.k(), TOL);

		G.QFLT.divide().call(q3, q2, q1);

		assertEquals(1, q1.r(), TOL);
		assertEquals(-2, q1.i(), TOL);
		assertEquals(3, q1.j(), TOL);
		assertEquals(2, q1.k(), TOL);
		
	}
	
	@Test
	public void testConjugate() {
		QuaternionFloat32Member a = new QuaternionFloat32Member(5,3,-1,-7);
		QuaternionFloat32Member b = new QuaternionFloat32Member();
		QuaternionFloat32Member c = new QuaternionFloat32Member();
		
		G.QFLT.conjugate().call(a, b);
		G.QFLT.multiply().call(a, b, c);
		assertEquals(84,c.r(), TOL);
		assertEquals(0,c.i(), TOL);
		assertEquals(0,c.j(), TOL);
		assertEquals(0,c.k(), TOL);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00001;
		
		QuaternionFloat32Member a = G.QFLT.construct();
		QuaternionFloat32Member b = G.QFLT.construct();
		QuaternionFloat32Member c = G.QFLT.construct();
		Float32Member t = G.FLT.construct();
		
		// G.OFLT.add();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QFLT.add().call(a, b, c);
		assertEquals(6, c.r(), 0);
		assertEquals(9, c.i(), 0);
		assertEquals(12, c.j(), 0);
		assertEquals(15, c.k(), 0);
		
		// G.OFLT.assign();
		G.QFLT.assign().call(a, b);
		assertEquals(a.r(), b.r(), 0);
		assertEquals(a.i(), b.i(), 0);
		assertEquals(a.j(), b.j(), 0);
		assertEquals(a.k(), b.k(), 0);
		
		// G.OFLT.cbrt();
		a.setR(27);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.cbrt().call(a, b);
		assertEquals(3, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OFLT.conjugate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.conjugate().call(a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OFLT.construct();
		a = G.QFLT.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OFLT.construct("");
		a = G.QFLT.construct("{3,-1,0,6}");
		assertEquals(3, a.r(), 0);
		assertEquals(-1, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(6, a.k(), 0);
		
		// G.OFLT.construct(other);
		b = G.QFLT.construct(a);
		assertEquals(3, b.r(), 0);
		assertEquals(-1, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(6, b.k(), 0);
		
		// G.OFLT.cos();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.cos().call(a, b);
		assertEquals(Math.cos(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.cosh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.cosh().call(a, b);
		assertEquals(Math.cosh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.divide();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(0.5f);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QFLT.divide().call(a, b, c);
		assertEquals(2, c.r(), 0);
		assertEquals(4, c.i(), 0);
		assertEquals(6, c.j(), 0);
		assertEquals(8, c.k(), 0);
		
		// G.OFLT.E();
		G.QFLT.E().call(a);
		assertEquals(Math.E, a.r(), tol);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OFLT.exp();
		a.setR(2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.exp().call(a, b);
		assertEquals(Math.exp(2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.infinite();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QFLT.isInfinite().call(a));
		G.QFLT.infinite().call(a);
		assertTrue(G.QFLT.isInfinite().call(a));
		
		// G.OFLT.invert();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.invert().call(a, b);
		G.QFLT.unity().call(c);
		G.QFLT.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), 0);
		assertEquals(c.i(), b.i(), 0);
		assertEquals(c.j(), b.j(), 0);
		assertEquals(c.k(), b.k(), 0);

		// G.OFLT.isEqual();
		G.QFLT.zero().call(b);
		assertFalse(G.QFLT.isEqual().call(a, b));
		G.QFLT.assign().call(a, b);
		assertTrue(G.QFLT.isEqual().call(a, b));
		
		// G.OFLT.isInfinite();
		//   tested by infinite() test above
		
		// G.OFLT.isNaN();
		//   tested by nan() test below
		
		// G.OFLT.isNotEqual();
		G.QFLT.zero().call(b);
		assertTrue(G.QFLT.isNotEqual().call(a, b));
		G.QFLT.assign().call(a, b);
		assertFalse(G.QFLT.isNotEqual().call(a, b));
		
		// G.OFLT.isZero();
		//   tested by zero() test below
		
		// G.OFLT.log();
		a.setR(4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.log().call(a, b);
		assertEquals(Math.log(4), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.multiply();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(7);
		b.setI(-4);
		b.setJ(8);
		b.setK(3);
		G.QFLT.multiply().call(a, b, c);
		G.QFLT.divide().call(c, b, b);
		assertEquals(a.r(), b.r(), tol);
		assertEquals(a.i(), b.i(), tol);
		assertEquals(a.j(), b.j(), tol);
		assertEquals(a.k(), b.k(), tol);
		
		// G.OFLT.nan();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QFLT.isNaN().call(a));
		G.QFLT.nan().call(a);
		assertTrue(G.QFLT.isNaN().call(a));
		
		// G.OFLT.negate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.negate().call(a, b);
		assertEquals(-1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OFLT.norm();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.norm().call(a, t);
		assertEquals(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4), t.v(), tol);
		
		// G.OFLT.PI();
		G.QFLT.PI().call(a);
		assertEquals(Math.PI, a.r(), tol);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OFLT.pow();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		b.setR(4);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QFLT.pow().call(a, b, c);
		assertEquals(7*7*7*7, c.r(), 0.1);
		assertEquals(0, c.i(), tol);
		assertEquals(0, c.j(), tol);
		assertEquals(0, c.k(), tol);
		
		// G.OFLT.power();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.power().call(4, a, b);
		assertEquals(7*7*7*7, b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.random();
		// TODO - how to test
		
		// G.OFLT.real();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.real().call(a, t);
		assertEquals(1, t.v(), 0);
		
		// G.OFLT.round();
		a.setR(1.1f);
		a.setI(-2.2f);
		a.setJ(3.3f);
		a.setK(-4.4f);
		t.setV(1);
		G.QFLT.round().call(Mode.HALF_EVEN, t, a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OFLT.scale();
		a.setR(1.1f);
		a.setI(-2.2f);
		a.setJ(3.3f);
		a.setK(-4.4f);
		b.setR(3);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QFLT.scale().call(b, a, c);
		assertEquals(3.3, c.r(), tol);
		assertEquals(-6.6, c.i(), tol);
		assertEquals(9.9, c.j(), tol);
		assertEquals(-13.2, c.k(), tol);
		
		// G.OFLT.sin();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.sin().call(a, b);
		assertEquals(Math.sin(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.sinAndCos();
		
		// G.OFLT.sinc();
		
		// G.OFLT.sinch();
		
		// G.OFLT.sinchpi();
		
		// G.OFLT.sincpi();
		
		// G.OFLT.sinh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.sinh().call(a, b);
		assertEquals(Math.sinh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.sinhAndCosh();
		
		// G.OFLT.sqrt();
		a.setR(25);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.sqrt().call(a, b);
		assertEquals(5, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OFLT.subtract();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QFLT.subtract().call(a, b, c);
		assertEquals(-4, c.r(), 0);
		assertEquals(-5, c.i(), 0);
		assertEquals(-6, c.j(), 0);
		assertEquals(-7, c.k(), 0);
		
		// G.OFLT.tan();
		a.setR((float)Math.PI/4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.tan().call(a, b);
		assertEquals(Math.tan(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OFLT.tanh();
		a.setR((float)Math.PI/4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QFLT.tanh().call(a, b);
		assertEquals(Math.tanh(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OFLT.unity();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OFLT.unreal();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QFLT.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(4, b.k(), 0);
		
		// G.OFLT.zero();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QFLT.isZero().call(a));
		G.QFLT.zero().call(a);
		assertTrue(G.QFLT.isZero().call(a));
	}
}
